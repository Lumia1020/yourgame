package com.tiema.core;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.xwork.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: Page
 * @Description: 与具体ORM实现无关的分页参数及查询结果封装.注意所有序号从1开始.
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Oct 25, 2010 11:22:43 PM
 * @param <T>Page中记录的类型.
 */
public class Page<T> implements Serializable {

	private static final long serialVersionUID = 8612719319348373085L;
	private static final Logger log = LoggerFactory.getLogger(Page.class);

	/* -- 公共变量 -- */
	public static final String ASC = "asc";
	public static final String DESC = "desc";

	/* -- 分页参数 -- */
	protected int pageNo = 1;
	protected int pageSize = 1;
	protected String orderBy = null;
	protected String order = null;
	protected boolean autoCount = true;

	/* -- 返回结果 -- */
	protected List<T> result = Collections.emptyList();
	protected long totalCount = -1;

	public Page() {
	}

	public Page(int pageSize) {
		this.pageSize = pageSize;
	}

	/* -- 访问查询参数函数 -- */
	/**
	 * @Title: getPageNo
	 * @Description: 获得当前页的页号,序号从1开始,默认为1.
	 * @return 页码
	 */
	public int getPageNo() {
		return pageNo;
	}

	/**
	 * @Title: setPageNo
	 * @Description: 设置当前页的页号,序号从1开始,低于1时自动调整为1.
	 * @param pageNo
	 */
	public void setPageNo(final int pageNo) {
		this.pageNo = pageNo;

		if (pageNo < 1) {
			this.pageNo = 1;
		}
	}

	public Page<T> pageNo(final int thePageNo) {
		setPageNo(thePageNo);
		return this;
	}

	/**
	 * @Title: getPageSize
	 * @Description: 获得每页的记录数量,默认为1.
	 * @return 每页多少
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @Title: setPageSize
	 * @Description: 设置每页的记录数量,低于1时自动调整为1.
	 * @param pageSize
	 */
	public void setPageSize(final int pageSize) {
		this.pageSize = pageSize;

		if (pageSize < 1) {
			this.pageSize = 1;
		}
	}

	public Page<T> pageSize(final int thePageSize) {
		setPageSize(thePageSize);
		return this;
	}

	/**
	 * @Title: getFirst
	 * @Description: 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从1开始.
	 * @return 第一页
	 */
	public int getFirst() {
		return ((pageNo - 1) * pageSize) + 1;
	}

	/**
	 * @Title: getOrderBy
	 * @Description: 获得排序字段,无默认值.多个排序字段时用','分隔.
	 * @return 排序方式
	 */
	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * @Title: setOrderBy
	 * @Description: 设置排序字段,多个排序字段时用','分隔.
	 * @param orderBy
	 *            排序
	 */
	public void setOrderBy(final String orderBy) {
		this.orderBy = orderBy;
	}

	public Page<T> orderBy(final String theOrderBy) {
		setOrderBy(theOrderBy);
		return this;
	}

	/**
	 * @Title: getOrder
	 * @Description: 获得排序方向.
	 * @return 排序方向
	 */
	public String getOrder() {
		return order;
	}

	/**
	 * 设置排序方式向.
	 * 
	 * @param order
	 *            可选值为desc或asc,多个排序字段时用','分隔.
	 */
	public void setOrder(final String order) {
		// 检查order字符串的合法值
		String[] orders = StringUtils.split(StringUtils.lowerCase(order), ',');
		for (String orderStr : orders) {
			if (!StringUtils.equals(DESC, orderStr) && !StringUtils.equals(ASC, orderStr)) {
				log.debug("排序方向" + orderStr + "不是合法值");
				throw new IllegalArgumentException("排序方向" + orderStr + "不是合法值");
			}
		}

		this.order = StringUtils.lowerCase(order);
	}

	public Page<T> order(final String theOrder) {
		setOrder(theOrder);
		return this;
	}

	/**
	 * @Title: isOrderBySetted
	 * @Description: 是否已设置排序字段,无默认值.
	 * @return 排序是否已经设置
	 */
	public boolean isOrderBySetted() {
		return (StringUtils.isNotBlank(orderBy) && StringUtils.isNotBlank(order));
	}

	/**
	 * @Title: isAutoCount
	 * @Description: 查询对象时是否自动另外执行count查询获取总记录数, 默认为false.
	 * @return 是否自动获取总记录数
	 */
	public boolean isAutoCount() {
		return autoCount;
	}

	/**
	 * @Title: setAutoCount
	 * @Description: 查询对象时是否自动另外执行count查询获取总记录数.
	 * @param autoCount
	 *            设置自动获取总记录数
	 */
	public void setAutoCount(final boolean autoCount) {
		this.autoCount = autoCount;
	}

	public Page<T> autoCount(final boolean theAutoCount) {
		setAutoCount(theAutoCount);
		return this;
	}

	/* -- 访问查询结果函数 -- */

	/**
	 * @Title: getResult
	 * @Description: 取得页内的记录列表.
	 * @return 分页结果
	 */
	public List<T> getResult() {
		return result;
	}

	/**
	 * @Title: setResult
	 * @Description: 设置页内的记录列表.
	 * @param result
	 *            某一次分页的数据列表
	 */
	public void setResult(final List<T> result) {
		this.result = result;
	}

	/**
	 * @Title: getTotalCount
	 * @Description: 取得总记录数, 默认值为-1.
	 * @return 总记录数
	 */
	public long getTotalCount() {
		return totalCount;
	}

	/**
	 * @Title: setTotalCount
	 * @Description: 设置总记录数.
	 * @param totalCount
	 */
	public void setTotalCount(final long totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * @Title: getTotalPages
	 * @Description: 根据pageSize与totalCount计算总页数, 默认值为-1.
	 * @return
	 */
	public long getTotalPages() {
		if (totalCount < 0) {
			return -1;
		}

		long count = totalCount / pageSize;
		if (totalCount % pageSize > 0) {
			count++;
		}
		return count;
	}

	/**
	 * @Title: isHasNext
	 * @Description: 是否还有下一页.
	 * @return
	 */
	public boolean isHasNext() {
		return (pageNo + 1 <= getTotalPages());
	}

	/**
	 * @Title: getNextPage
	 * @Description: 取得下页的页号, 序号从1开始. 当前页为尾页时仍返回尾页序号.
	 * @return
	 */
	public int getNextPage() {
		if (isHasNext()) {
			return pageNo + 1;
		} else {
			return pageNo;
		}
	}

	/**
	 * @Title: isHasPre
	 * @Description: 是否还有上一页.
	 * @return
	 */
	public boolean isHasPre() {
		return (pageNo - 1 >= 1);
	}

	/**
	 * @Title: getPrePage
	 * @Description: 取得上页的页号, 序号从1开始. 当前页为首页时返回首页序号.
	 * @return
	 */
	public int getPrePage() {
		if (isHasPre()) {
			return pageNo - 1;
		} else {
			return pageNo;
		}
	}

}
