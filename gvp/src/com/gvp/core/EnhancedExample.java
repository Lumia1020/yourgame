package com.gvp.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.EntityMode;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.hibernate.engine.TypedValue;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.type.AbstractComponentType;
import org.hibernate.type.Type;
import org.hibernate.util.StringHelper;

/**
 * A copy of Hibernate's Example class, with modifications that allow you to
 * include many-to-one and one-to-one associations in Query By Example (QBE)
 * queries.
 * 
 * @author original code by Gavin King, modified by jkelly, enhanced again by
 *         George Wei
 * @see <a href="http://forum.hibernate.org/viewtopic.php?t=942872">Example and
 *      association class </a>
 */
public class EnhancedExample implements Criterion {

	private final Object entity;

	private boolean isAndRelations;

	private final Set excludedProperties = new HashSet();

	private PropertySelector selector;

	private static final RestrictionHolder holder = new DefaultRestrictionHolder();

	private boolean isLikeEnabled;

	private boolean isIgnoreCaseEnabled;

	private MatchMode matchMode;

	private boolean includeAssociations = true;

	/**
	 * A strategy for choosing property values for inclusion in the query
	 * criteria
	 */

	public static interface PropertySelector {
		public boolean include(Object propertyValue, String propertyName, Type type);
	}

	private static final PropertySelector NOT_NULL = new NotNullPropertySelector();

	private static final PropertySelector ALL = new AllPropertySelector();

	private static final PropertySelector NOT_NULL_OR_ZERO = new NotNullOrZeroPropertySelector();

	static final class AllPropertySelector implements PropertySelector, Serializable {
		public boolean include(Object object, String propertyName, Type type) {
			return true;
		}
	}

	static final class NotNullPropertySelector implements PropertySelector, Serializable {
		public boolean include(Object object, String propertyName, Type type) {
			return object != null;
		}
	}

	static final class NotNullOrZeroPropertySelector implements PropertySelector, Serializable {
		public boolean include(Object object, String propertyName, Type type) {
			return object != null && (!(object instanceof Number) || ((Number) object).longValue() != 0);
		}
	}

	/**
	 * Set the property selector
	 */
	public EnhancedExample setPropertySelector(PropertySelector selector) {
		this.selector = selector;
		return this;
	}

	/**
	 * Exclude zero-valued properties
	 */
	public EnhancedExample excludeZeroes() {
		setPropertySelector(NOT_NULL_OR_ZERO);
		return this;
	}

	/**
	 * Don't exclude null or zero-valued properties
	 */
	public EnhancedExample excludeNone() {
		setPropertySelector(ALL);
		return this;
	}

	/**
	 * Restriction strategy definitions
	 */
	public static enum RestrictionStrategy {
		eq, ne, gt, lt, ge, le
	}

	/**
	 * Restriction strategy holder for the query criteria
	 */
	public static interface RestrictionHolder {
		/**
		 * Set a restriction strategy for a POJO's property
		 */
		public RestrictionHolder set(String propertyName, RestrictionStrategy strategy);

		/**
		 * Get the restriction strategy of the property
		 */
		public RestrictionStrategy get(String propertyName);
	}

	static final class DefaultRestrictionHolder implements RestrictionHolder {
		private Map<String, RestrictionStrategy> strategies = new HashMap<String, RestrictionStrategy>();

		public RestrictionHolder set(String propertyName, RestrictionStrategy strategy) {
			strategies.put(propertyName, strategy);
			return this;
		}

		public RestrictionStrategy get(String propertyName) {
			return strategies.get(propertyName);
		}
	}

	/**
	 * Get the restriction strategy holder
	 */
	public RestrictionHolder getRestrictionHolder() {
		return holder;
	}

	/**
	 * Use the "like" operator for all string-valued properties
	 */
	public EnhancedExample enableLike(MatchMode matchMode) {
		isLikeEnabled = true;
		this.matchMode = matchMode;
		return this;
	}

	/**
	 * Use the "like" operator for all string-valued properties
	 */
	public EnhancedExample enableLike() {
		return enableLike(MatchMode.EXACT);
	}

	/**
	 * Ignore case for all string-valued properties
	 */
	public EnhancedExample ignoreCase() {
		isIgnoreCaseEnabled = true;
		return this;
	}

	/**
	 * Exclude a particular named property
	 */
	public EnhancedExample excludeProperty(String name) {
		excludedProperties.add(name);
		return this;
	}

	/**
	 * Create a new instance, which includes all non-null properties by default
	 * 
	 * @param entity
	 * @return a new instance of <tt>Example</tt>
	 */
	public static EnhancedExample create(Object entity) {
		if (entity == null)
			throw new NullPointerException("null EnhancedExample");
		return new EnhancedExample(entity, NOT_NULL);
	}

	public static EnhancedExample create(Object entity, boolean isAndRelations) {
		if (entity == null)
			throw new NullPointerException("null EnhancedExample");
		return new EnhancedExample(entity, NOT_NULL, isAndRelations);
	}

	public static EnhancedExample createDefault(Object entity) {
		if (entity == null)
			throw new NullPointerException("null EnhancedExample");
		return new EnhancedExample(entity, NOT_NULL, false).enableLike(MatchMode.ANYWHERE).ignoreCase()
				.excludeZeroes();
	}
	
	public static EnhancedExample createDefault(Object entity, boolean isAndRelations){
		if (entity == null)
			throw new NullPointerException("null EnhancedExample");
		return new EnhancedExample(entity, NOT_NULL, isAndRelations).enableLike(MatchMode.ANYWHERE).ignoreCase()
				.excludeZeroes();
	}

	protected EnhancedExample(Object entity, PropertySelector selector) {
		this.entity = entity;
		this.selector = selector;
	}

	protected EnhancedExample(Object entity, PropertySelector selector, boolean isAndRelations) {
		this.entity = entity;
		this.selector = selector;
		this.isAndRelations = isAndRelations;
	}

	public String toString() {
		return "example (" + entity + ')';
	}

	private boolean isPropertyIncluded(Object value, String name, Type type) {
		return !excludedProperties.contains(name)
				&& selector.include(value, name, type)
				&& (!type.isAssociationType() || (type.isAssociationType() && includeAssociations && !type
						.isCollectionType()));
	}

	public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws HibernateException {

		StringBuffer buf = new StringBuffer().append('(');
		EntityPersister meta = criteriaQuery.getFactory().getEntityPersister(criteriaQuery.getEntityName(criteria));
		String[] propertyNames = meta.getPropertyNames();
		Type[] propertyTypes = meta.getPropertyTypes();
		Object[] propertyValues = meta.getPropertyValues(entity, getEntityMode(criteria, criteriaQuery));
		for (int i = 0; i < propertyNames.length; i++) {
			Object propertyValue = propertyValues[i];
			String propertyName = propertyNames[i];

			boolean isPropertyIncluded = i != meta.getVersionProperty()
					&& isPropertyIncluded(propertyValue, propertyName, propertyTypes[i]);
			if (isPropertyIncluded) {
				if (propertyTypes[i].isComponentType()) {
					appendComponentCondition(propertyName, propertyValue, (AbstractComponentType) propertyTypes[i],
							criteria, criteriaQuery, buf);
				} else {
					appendPropertyCondition(propertyName, propertyValue, criteria, criteriaQuery, buf);
				}
			}
		}
		if (buf.length() == 1)
			buf.append("1=1"); // yuck!
		return buf.append(')').toString();
	}

	private static final Object[] TYPED_VALUES = new TypedValue[0];

	public TypedValue[] getTypedValues(Criteria criteria, CriteriaQuery criteriaQuery) throws HibernateException {

		EntityPersister meta = criteriaQuery.getFactory().getEntityPersister(criteriaQuery.getEntityName(criteria));
		String[] propertyNames = meta.getPropertyNames();
		Type[] propertyTypes = meta.getPropertyTypes();
		Object[] values = meta.getPropertyValues(entity, getEntityMode(criteria, criteriaQuery));
		List list = new ArrayList();
		for (int i = 0; i < propertyNames.length; i++) {
			Object value = values[i];
			Type type = propertyTypes[i];
			String name = propertyNames[i];

			boolean isPropertyIncluded = i != meta.getVersionProperty() && isPropertyIncluded(value, name, type);

			if (isPropertyIncluded) {
				if (propertyTypes[i].isComponentType()) {
					addComponentTypedValues(name, value, (AbstractComponentType) type, list, criteria, criteriaQuery);
				} else {
					addPropertyTypedValue(value, type, list);
				}
			}
		}
		return (TypedValue[]) list.toArray(TYPED_VALUES);
	}

	private EntityMode getEntityMode(Criteria criteria, CriteriaQuery criteriaQuery) {
		EntityPersister meta = criteriaQuery.getFactory().getEntityPersister(criteriaQuery.getEntityName(criteria));
		EntityMode result = meta.guessEntityMode(entity);
		if (result == null) {
			throw new ClassCastException(entity.getClass().getName());
		}
		return result;
	}

	protected void addPropertyTypedValue(Object value, Type type, List list) {
		if (value != null) {
			if (value instanceof String) {
				String string = (String) value;
				if (isIgnoreCaseEnabled)
					string = string.toLowerCase();
				if (isLikeEnabled)
					string = matchMode.toMatchString(string);
				value = string;
			}
			list.add(new TypedValue(type, value, null));
		}
	}

	protected void addComponentTypedValues(String path, Object component, AbstractComponentType type, List list,
			Criteria criteria, CriteriaQuery criteriaQuery) throws HibernateException {

		if (component != null) {
			String[] propertyNames = type.getPropertyNames();
			Type[] subtypes = type.getSubtypes();
			Object[] values = type.getPropertyValues(component, getEntityMode(criteria, criteriaQuery));
			for (int i = 0; i < propertyNames.length; i++) {
				Object value = values[i];
				Type subtype = subtypes[i];
				String subpath = StringHelper.qualify(path, propertyNames[i]);
				if (isPropertyIncluded(value, subpath, subtype)) {
					if (subtype.isComponentType()) {
						addComponentTypedValues(subpath, value, (AbstractComponentType) subtype, list, criteria,
								criteriaQuery);
					} else {
						addPropertyTypedValue(value, subtype, list);
					}
				}
			}
		}
	}

	protected void appendPropertyCondition(String propertyName, Object propertyValue, Criteria criteria,
			CriteriaQuery cq, StringBuffer buf) throws HibernateException {
		Criterion crit;
		if (propertyValue != null) {
			RestrictionStrategy strategy = holder.get(propertyName);
			if (strategy != null) {
				switch (strategy) {
				// case eq: crit = Restrictions.eq(propertyName, propertyValue);
				case ne:
					crit = Restrictions.ne(propertyName, propertyValue);
					break;
				case gt:
					crit = Restrictions.gt(propertyName, propertyValue);
					break;
				case lt:
					crit = Restrictions.lt(propertyName, propertyValue);
					break;
				case ge:
					crit = Restrictions.ge(propertyName, propertyValue);
					break;
				case le:
					crit = Restrictions.le(propertyName, propertyValue);
					break;
				default:
					crit = Restrictions.eq(propertyName, propertyValue);
				}
				;
			} else {
				boolean isString = propertyValue instanceof String;
				SimpleExpression se = (isLikeEnabled && isString) ? Restrictions.like(propertyName, propertyValue)
						: Restrictions.eq(propertyName, propertyValue);
				crit = (isIgnoreCaseEnabled && isString) ? se.ignoreCase() : se;
			}
		} else {
			crit = Restrictions.isNull(propertyName);
		}
		String critCondition = crit.toSqlString(criteria, cq);
		if (buf.length() > 1 && critCondition.trim().length() > 0)
			buf.append(this.isAndRelations ? " and " : " or ");
		buf.append(critCondition);
	}

	protected void appendComponentCondition(String path, Object component, AbstractComponentType type,
			Criteria criteria, CriteriaQuery criteriaQuery, StringBuffer buf) throws HibernateException {

		if (component != null) {
			String[] propertyNames = type.getPropertyNames();
			Object[] values = type.getPropertyValues(component, getEntityMode(criteria, criteriaQuery));
			Type[] subtypes = type.getSubtypes();
			for (int i = 0; i < propertyNames.length; i++) {
				String subpath = StringHelper.qualify(path, propertyNames[i]);
				Object value = values[i];
				if (isPropertyIncluded(value, subpath, subtypes[i])) {
					Type subtype = subtypes[i];
					if (subtype.isComponentType()) {
						appendComponentCondition(subpath, value, (AbstractComponentType) subtype, criteria,
								criteriaQuery, buf);
					} else {
						appendPropertyCondition(subpath, value, criteria, criteriaQuery, buf);
					}
				}
			}
		}
	}

	public boolean isIncludeAssociations() {
		return includeAssociations;
	}

	public void setIncludeAssociations(boolean includeAssociations) {
		this.includeAssociations = includeAssociations;
	}
}