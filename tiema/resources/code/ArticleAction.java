package code;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.*;
import com.opensymphony.xwork2.util.Element;
import com.opensymphony.xwork2.util.KeyProperty;
import m_ylf.cs.sicau.portal.domain.*;
import m_ylf.cs.sicau.portal.globals.ActionMessageGlobals;
import m_ylf.cs.sicau.portal.globals.ActionReturnStringsGlobals;
import m_ylf.cs.sicau.portal.globals.AjaxMessageGlobals;
import m_ylf.cs.sicau.portal.globals.ProjectConfigureGlobals;
import m_ylf.cs.sicau.portal.service.IService;
import m_ylf.cs.sicau.portal.set.AjaxReturnSet;
import m_ylf.cs.sicau.portal.util.AjaxResponse;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Fly_m
 */
@Validation
public class ArticleAction extends ActionSupport {

	/**
	 * 业务层组件对象,负责提供相应的数据,和相应的逻辑操作 *
	 */
	private IService service;

	/**
	 * 文章对象,负责从页面传递相应的数据并向页面返回相应的数据 *
	 */
	private Article article;

	/**
	 * 页面组件,负责对页面上的请求对数据进行分页,并返回页面上的分页逻辑 *
	 */
	private Page page;

	/**
	 * 向页面传递多个文章时运用,并尝试从页面取得多个对象,在进行批处理对象时运用 *
	 */
	@Element(Article.class)
	@KeyProperty("id")
	private List<Article> articleList;

	/**
	 * 特殊追加字段,此字段在某些处理单个对象的时候运用,以避免对某些细节方面的访问,通用单个关键值
	 * 对象id 值访问.在页面中,通常处理某些问题只需要提供相应的key值,如id,则此属性提供这种途径来
	 * 提供相应的支持,此避免页面可能出现比如article.id这种暴露性的提示字段.同时也简化了对相应
	 * 数据的处理.
	 */
	private int id;

	/**
	 * 置顶组件,此字段用于置顶一些文章或者取消置顶所用.由于设计的原因,导致置顶标记不能与文章产生
	 * 一对一的关系,而成为多对一的关系.故type不能直接被删除,为一个文章置顶之时,就会产生一个新的
	 * type对象,而取消置顶时,仅仅是把关联关系去掉.而因为type的作用范围很小,故不会对type对象进行
	 * 其它的操作.此对象是相对数据库来说安全的.
	 */
	private ArticleTopType type;

	/**
	 * 在查询时,表示是否是级联查询,如果是true,表示是级联,否则其它的数据(空值)均表示不能级联查询.
	 */
	private String cascade;

	public void setService(IService service) {
		this.service = service;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public void setArticleList(List<Article> articleList) {
		this.articleList = articleList;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Article getArticle() {
		return article;
	}

	public List<Article> getArticleList() {
		return articleList;
	}

	public String getCascade() {
		return cascade;
	}

	public int getId() {
		return id;
	}

	public Page getPage() {
		return page;
	}

	public IService getService() {
		return service;
	}

	public ArticleTopType getType() {
		return type;
	}


	/**
	 * 保存一篇文章,根据此文章的类别对文章进行分类,以及建立索引.并对此文章进行分页保存.
	 * 必须条件:除了文章的各项内容之外,还需要此文章的类别,且保证此类别是存在于数据库中的,此外,创
	 * 建此文章的作者也是需要的.
	 */
	@Validations(requiredStrings = {
	@RequiredStringValidator(fieldName = "article.title", key = "article.title.requiredstring", message = "null"),
	@RequiredStringValidator(fieldName = "article.content", message = "null", key = "article.content.requiredstring"),
	@RequiredStringValidator(fieldName = "article.titleColor", key = "article.titleColor.requiredstring", message = "null"),
	@RequiredStringValidator(fieldName = "article.tags", key = "article.tags.requiredstring", message = "null")})
	public String saveArticle() throws Exception {
		/** 查找此文章类别,此类别在下文中对查询标记,或者路径标记均有相关作用,故为必须的 **/
		Category category = this.service.getCategory(id);
		if (category == null) {
			this.addActionError(this.getText(ActionMessageGlobals.ARTICLEACTION_SAVEARTICLE_NOCATEGORY_FAIL));
			return ActionReturnStringsGlobals.ARTICLEACTION_SAVEARTICLE_FAIL;
		}

		/** 设置此文章的路径标记,标记同一篇文章 **/
		String pathMark = category.getName() + new SimpleDateFormat("-yyyy/MM/dd-HH/mm/ss-SSS").format(new Date());
		/** 设置此文章的类别查询标记,标记同一类别文章 **/
		String categoryChain = category.getCategoryChain();
		/** 设置文章作者ip **/
		String ip = ServletActionContext.getRequest().getRemoteAddr();
		/** 设置来源,此默认来源由于国际化的需要,而引用相关资源文件key值 **/
		String source = this.article.getSource() == null ? this.getText(ProjectConfigureGlobals.ARTICLE_DEFAULT_LOCALSOURCE_KEY) : this.article.getSource();
		/** 设置文章摘要,默认有文章的前50个字符 **/
		String summary = this.article.getSummary() == null ? (this.article.getContent().length() < 50 ? this.article.getContent() : this.article.getContent().substring(0, 50)) : this.article.getSummary();
		/** 得到文章内容数组,因为文章可能是分页的,所以用分隔符将其隔开,分成几个页面 **/
		String[] contents = this.article.getContent().split(ProjectConfigureGlobals.ARTICLE_DEFAULT_SEPARAROR);

		/** 开始保存文章 **/
		int pageTotalCount = contents.length; //文章总页数
		int pageCurrentIndex = 1; //文章当前页数
		User user = service.getUser(1);
		for (int i = 0; i < pageTotalCount; i++) {
			Article a = new Article();
			a.setTitle(this.article.getTitle());
			a.setPathMark(pathMark);
			a.setTitleColor(this.article.getTitleColor());
			a.setSummary(summary);
			a.setSource(source);
			a.setIp(ip);
			a.setContent(contents[pageCurrentIndex - 1]);
			a.setPageCount(pageTotalCount);
			a.setPageCurrentIndex(pageCurrentIndex);
			a.setCreateUser(user);
			a.setCategory(category);
			a.setCategoryChain(categoryChain);
			a.setTags(this.article.getTags());
			this.service.saveArticle(a);
			pageCurrentIndex++;
		}

		this.addActionMessage(this.getText(ActionMessageGlobals.ARTICLEACTION_SAVEARTICLE_SUCCESS, new String[]{this.article.getTitle()}));
		return ActionReturnStringsGlobals.ARTICLEACTION_SAVEARTICLE_SUCCESS;
	}

	/**
	 * 显示文章,此方法除了处理显示第一页面的文章之外,还处理显示后续页面文章的能力.这就要求,从文章
	 * id值,进而得到文章的pathMark值,从而得到下一篇/上一篇文章.
	 * 要求参数,文章的id值,第几页的页面数,默认为第一页.
	 */
	@Validations(requiredFields = @RequiredFieldValidator(fieldName = "article.id", key = "article.id.required", message = "null"),
			conversionErrorFields = @ConversionErrorFieldValidator(fieldName = "article.id", message = "null", key = "article.id.int"))
	public String showArticle() throws Exception {
		/** 处理id值,此id值为文章第几页数 **/
		id = (id == 0 ? 1 : id);

		/** 得到文章对象,由文章id值,得到pathMark值,进而得到应该传递的article对象 **/
		this.article = this.service.getArticle(this.article.getId());
		if (this.article != null) {
			this.article = this.service.getArticleByPathMarkAndPageCurrentIndex(this.article.getPathMark(), id);
		}
		/** 如果文章对象为空,追加对文章最终标志地判断 **/
		if (this.article == null || this.article.isShiftDeleted()) {
			this.addActionError(this.getText(ActionMessageGlobals.ARTICLEACTION_SHOWARTICLE_NOARTICLE_FAIL));
			return ActionReturnStringsGlobals.ARTICLEACTION_SHOWARTICLE_FAIL;
		}

		/** 更新文章对象 **/
		this.article.setHits(this.article.getHits() + 1);
		this.service.updateArticle(this.article);

		return ActionReturnStringsGlobals.ARTICLEACTION_SHOWARTICLE_SUCCESS;
	}

	/**
	 * 删除文章,此方法为第一次删除文章,所以仅是把文章作一个删除标记.
	 * 要求参数,文章id值,在此为id字段,并由此删除其相连续的文章页面.
	 */
	public String firstDeleteArticle() throws Exception {
		this.article = this.service.getArticle(id);
		if (this.article != null) {
			this.service.deleteFirstArticle(this.article);
		}
		this.addActionMessage(this.getText(ActionMessageGlobals.ARTICLEACTION_FIRSTDELETEARTICLE_SUCCESS));
		return ActionReturnStringsGlobals.ARTICLEACTION_FIRSTDELETEARTICLE_SUCCESS;
	}

	/**
	 * 删除文章,此方法为第一次删除文章,ajax操作
	 * 要求参数,文章id值,以id表示.
	 */
	public void firstDeleteArticleAjax() throws Exception {
		AjaxReturnSet<String> ajaxReturnSet = new AjaxReturnSet<String>();
		this.article = this.service.getArticle(this.id);
		if (this.article != null) {
			this.service.deleteFirstArticle(this.article);
		}

		ajaxReturnSet.setBool(true);
		AjaxResponse.sendAjaxText(ajaxReturnSet, ServletActionContext.getResponse());
	}

	/**
	 * 恢复文章,仅恢复由于第一次删除后的文章,如果是最终删除的文章,则不被恢复.
	 * 要求参数,文章id值,在此为id字段,并由此恢复相连续的文章页面.
	 */
	public String firstRecoverArticle() throws Exception {
		this.article = this.service.getArticle(id);
		if (this.article == null || this.article.isShiftDeleted()) {
			this.addActionError(this.getText(ActionMessageGlobals.ARTICLEACTION_FIRSTRECOVERARTICLE_NOARTICLE_FAIL));
			return ActionReturnStringsGlobals.ARTICLEACTION_FIRSTRECOVERARTICLE_FAIL;
		}

		/** 开始恢复操作 **/
		this.service.updateArticleByRecover(this.article);

		this.addActionMessage(this.getText(ActionMessageGlobals.ARTICLEACTION_FIRSTRECOVERARTICLE_SUCCESS, this.article.getTitle()));
		return ActionReturnStringsGlobals.ARTICLEACTION_FIRSTRECOVERARTICLE_SUCCESS;
	}

	/**
	 * 恢复文章,ajax操作
	 * 要求参数,文章id值,以id表示
	 */
	public void firstRecoverArticleAjax() throws Exception {
		AjaxReturnSet<String> ajaxReturnSet = new AjaxReturnSet<String>();
		this.article = this.service.getArticle(this.id);
		if (this.article == null || this.article.isShiftDeleted()) {
			ajaxReturnSet.setBool(false);
			ajaxReturnSet.setResult(this.getText(AjaxMessageGlobals.ARTICLEACTION_FIRSTRECOVERARTICLEAJAX_NOARTICLE_FAIL));
			AjaxResponse.sendAjaxText(ajaxReturnSet, ServletActionContext.getResponse());
		}

		/** 开始恢复操作 **/
		this.service.updateArticleByRecover(this.article);

		ajaxReturnSet.setBool(true);
		AjaxResponse.sendAjaxText(ajaxReturnSet, ServletActionContext.getResponse());
	}

	/**
	 * 删除文章,此方法为最终删除文章,删除之后,将无法再检索出文章.
	 * 要求参数,文章id值,在此为id字段,并由此删除其相连续的文章页面.
	 */
	public String shiftDeleteArticle() throws Exception {
		this.article = this.service.getArticle(id);
		if (this.article != null) {
			this.service.deleteShiftArticle(this.article);
		}
		this.addActionMessage(this.getText(ActionMessageGlobals.ARTICLEACTION_SHIFTDELETEARTICLE_SUCCESS));
		return ActionReturnStringsGlobals.ARTICLEACTION_SHIFTDELETEARTICLE_SUCCESS;
	}

	/**
	 * 删除文章,此方法为最终删除文章,删除之后的文章不可被检索.
	 * 要求参数,文章id值,此为id.
	 */
	public void shiftDeleteArticleAjax() throws Exception {
		this.article = this.service.getArticle(this.id);
		if (this.article != null) {
			this.service.deleteShiftArticle(this.article);
		}
		AjaxReturnSet<String> ajaxReturnSet = new AjaxReturnSet<String>();
		ajaxReturnSet.setBool(true);
		AjaxResponse.sendAjaxText(ajaxReturnSet, ServletActionContext.getResponse());
	}

	/**
	 * 编辑文章页面,此方法将返回添加文章页面,将各个参数组合在一起(主要是文章内容),然后返回至页面
	 * 要求参数,文章id值,在此为id字段.
	 */
	public String editArticleView() throws Exception {
		/** 得到文章列表,此列表是因为可能的几张页面文章 **/
		this.article = this.service.getArticle(id);
		if (article == null) {
			this.addActionError(this.getText(ActionMessageGlobals.ARTICLEACTION_EDITARTICLEVIEW_NOARTICLELIST_FAIL));
			return ActionReturnStringsGlobals.ARTICLEACTION_EDITARTICLEVIEW_FAIL;
		}
		List<Article> articleListTemp = this.service.getArticleListByArticlePathMark(this.article.getPathMark());

		/** 如果文章列表为空 **/
		if (articleListTemp == null || articleListTemp.size() < 1) {
			this.addActionError(this.getText(ActionMessageGlobals.ARTICLEACTION_EDITARTICLEVIEW_NOARTICLELIST_FAIL));
			return ActionReturnStringsGlobals.ARTICLEACTION_EDITARTICLEVIEW_FAIL;
		}

		/** 组合内容 **/
		String content = "";
		for (Article a : articleListTemp) {
			if (!content.equals("")) {
				content += ProjectConfigureGlobals.ARTICLE_DEFAULT_SEPARAROR;
			}
			content += a.getContent();
		}
		this.article = articleListTemp.get(0);
		this.article.setContent(content);
		return ActionReturnStringsGlobals.ARTICLEACTION_EDITARTICLEVIEW_SUCCESS;
	}

	/**
	 * 将修改后的文章重新保存至数据库,从原则上来说,修改文章只是修改其内容.然而,保留对可能的其他
	 * 方面的修改,甚至类别.以至重新建立此文章,及可能的相关的数据.
	 * 要求参数,修改文章的id值,由此来指定特定的文章.以及相关的用户.仍以id值表示,所表示的类别值.
	 */
	@Validations(requiredStrings = {
	@RequiredStringValidator(fieldName = "article.title", key = "article.title", message = "null"),
	@RequiredStringValidator(fieldName = "article.content", key = "article.content", message = "null"),
	@RequiredStringValidator(fieldName = "article.titleColor", key = "article.titleColor.requiredstring", message = "null"),
	@RequiredStringValidator(fieldName = "article.tags", key = "article.tags.requiredstring", message = "null")
			}, requiredFields = @RequiredFieldValidator(fieldName = "article.id", key = "article.id.required", message = "null"),
			conversionErrorFields = @ConversionErrorFieldValidator(fieldName = "article.id", key = "article.id.int", message = "null"))
	public String editArticleSave() throws Exception {
		/** 查找类别,以重装生成相关的属性. **/
		Category category = this.service.getCategory(this.id);
		if (category == null) {
			this.addActionError(this.getText(ActionMessageGlobals.ARTICLEACTION_EDITARTICLESAVE_NOCATEGORY_FAIL));
			return ActionReturnStringsGlobals.ARTICLEACTION_EDITARTICLESAVE_FAIL;
		}

		/** 如果articleTemp为空,则定义为非修改文章,启动保存文章操作,注:此步可能由权限设置被改动 **/
		Article articleTemp = this.service.getArticle(this.article.getId());
		if (articleTemp == null) {
			return saveArticle();
		}

		/** 查看原始的相关文章属性 **/
		List<Article> articleListTemp = this.service.getArticleListByArticlePathMark(articleTemp.getPathMark());

		/** 如果articleListTemp 为空,实际上,由于前一步对articleTemp的判断,则使articleListTemp不可能为空值.则启动保存文章操作,注:此步可能由于权限的设置会被改动! **/
		if (articleListTemp == null || articleListTemp.size() < 1) {
			return saveArticle();
		}
		/** 测试其类别属性是否已经修改,此测试为减少相关可能的计算量 **/
		boolean categoryChangeFlag = false;
		if (articleListTemp.get(0).getCategory().getId() != category.getId()) {
			categoryChangeFlag = true;
		}

		String[] contents = this.article.getContent().split(ProjectConfigureGlobals.ARTICLE_DEFAULT_SEPARAROR);
		/** 排列先前的articleListTemp,为可能的文章页数的改变作准备 **/
		Collections.sort(articleListTemp, new Comparator<Article>() {

			public int compare(Article o1, Article o2) {
				return o1.getPageCurrentIndex() - o2.getPageCurrentIndex();
			}
		});

		/** 动态增加或者删除列表中的对象 **/
		int contentsLength = contents.length;
		int articleListTempLength = articleListTemp.size();
		if (contentsLength < articleListTempLength) {
			//先从数据库中删除相关对象,不然,在以后会再次取出相关对象
			for (int i = contentsLength; i < articleListTempLength; i++) {
				Article a = articleListTemp.get(i);
				this.service.deleteArticle(a);
			}

			articleListTemp = articleListTemp.subList(0, contentsLength);

		} else if (contentsLength > articleListTempLength) {
			articleTemp = articleListTemp.get(0);
			for (int i = articleListTempLength; i < contentsLength; i++) {
				Article a = new Article();
				/** 为增加对象设置不变的属性,不变的属性指在该下来的修改中不被改变的属性 **/
				a.setPathMark(articleTemp.getPathMark());
				a.setPageCurrentIndex(i + 1);
				a.setCreateUser(articleTemp.getCreateUser());
				a.setIp(articleTemp.getIp());
				/** 设置可能会改变的类别属性 **/
				a.setCategory(articleTemp.getCategory());
				a.setCategoryChain(articleTemp.getCategoryChain());
				articleListTemp.add(a);
			}
		}

		/** 设置更新之文章来源 **/
		String source = !StringUtils.hasText(this.article.getSource()) ? articleListTemp.get(0).getSummary() : this.article.getSource();
		/** 设置更新之文章摘要 **/
		String summary = !StringUtils.hasText(this.article.getSummary()) ? (this.article.getContent().length() < 50 ? this.article.getContent() : this.article.getContent().substring(0, 50)) : this.article.getSummary();
		/** 开始更新数据 **/
		for (int i = 0; i < contentsLength; i++) {
			Article a = articleListTemp.get(i);
			a.setTitle(this.article.getTitle());
			a.setTitleColor(this.article.getTitleColor());
			a.setSummary(summary);
			a.setSource(source);
			a.setContent(contents[i]);
			a.setPageCount(contentsLength);
			a.setTags(this.article.getTags());
			/** 增加修改作者这一项,注:这一项可能会被权限方面所修改 **/
			a.setEditUser(this.article.getEditUser());
			/** 增加修改日期这一项 **/
			a.setModifyDate(new Date());
			/** 进行类别是否改变判断,并作相应处理 **/
			if (categoryChangeFlag) {
				a.setCategory(category);
				a.setCategoryChain(category.getCategoryChain());
			}
			this.service.updateArticle(a);
		}

		this.addActionMessage(this.getText(ActionMessageGlobals.ARTICLEACTION_EDITARTICLESAVE_SUCCESS, new String[]{this.article.getTitle()}));
		return ActionReturnStringsGlobals.ARTICLEACTION_EDITARTICLESAVE_SUCCESS;
	}

	/**
	 * 置顶一篇文章,根据所产生的置顶对象对文章进行相应时间段的置顶操作.
	 * 要求参数,文章id值,此处以id值表示,置顶(可以为空,为空时为取消置顶操作)所需要的前后时间段
	 */
	public String topArticle() throws Exception {
		/** 如果type非空,则为置顶操作,保存其操作,并返回之 **/
		if (this.type != null) {
			this.type.setId((Integer) this.service.saveArticleTopType(this.type));
		}
		this.article = this.service.getArticle(id);
		if (this.article == null) {
			this.addActionError(this.getText(ActionMessageGlobals.ARTICLEACTION_TOPARTICLE_NOARTICLE_FAIL));
			return ActionReturnStringsGlobals.ARTICLEACTION_TOPARTICLE_FAIL;
		}

		/** 更新操作 **/
		this.service.updateArticleByArticleTopType(this.article, this.type);

		this.addActionMessage(this.getText(ActionMessageGlobals.ARTICLEACTION_TOPARTICLE_SUCCESS, new String[]{this.article.getTitle(), this.type == null ? this.getText(ActionMessageGlobals.ARTICLEACTION_TOPARTICLE_TOP_FLAG_N) : this.getText(ActionMessageGlobals.ARTICLEACTION_TOPARTICLE_TOP_FLAG_Y)}));
		return ActionReturnStringsGlobals.ARTICLEACTION_TOPARTICLE_SUCCESS;
	}

	/**
	 * 取消置顶一篇文章,ajax操作
	 * 要求参数:文章id值,以id表示.
	 */
	public void topArticleAjax() throws Exception {
		AjaxReturnSet<String> ajaxReturnSet = new AjaxReturnSet<String>();
		/** 得到相关的article对象 **/
		this.article = this.service.getArticle(this.id);
		if (this.article == null) {
			ajaxReturnSet.setBool(false);
			ajaxReturnSet.setResult(AjaxMessageGlobals.ARTICLEACTION_TOPARTICLEAJAX_NOARTICLE_FAIL);
			AjaxResponse.sendAjaxText(ajaxReturnSet, ServletActionContext.getResponse());
			return;
		}

		/**开始更新数据 **/
		this.service.updateArticleByArticleTopType(this.article, null);
		ajaxReturnSet.setBool(true);
		AjaxResponse.sendAjaxText(ajaxReturnSet, ServletActionContext.getResponse());
	}

	/**
	 * 审核一篇文章,对一篇文章进行审核或者取消审核
	 * 要求参数,文章id值,此处以article.id表示,审核标志,此处以id值标识
	 */
	public String auditArtilce() throws Exception {
		this.article = this.service.getArticle(this.article.getId());
		if (this.article == null) {
			this.addActionError(this.getText(ActionMessageGlobals.ARTICLEACTION_AUDITARTICLE_NOARTICLE_FAIL));
			return ActionReturnStringsGlobals.ARTICLEACTION_AUDITARTICLE_FAIL;
		}

		/** 更新操作 **/
		this.service.updateArticleByAudit(this.article, id != 0);

		this.addActionMessage(this.getText(ActionMessageGlobals.ARTICLEACTION_AUDITARTICLE_SUCCESS, new String[]{this.article.getTitle(), this.id != 0 ? this.getText(ActionMessageGlobals.ARTICLEACTION_AUDITARTICLE_AUDIT_FLAG_Y) : this.getText(ActionMessageGlobals.ARTICLEACTION_AUDITARTICLE_AUDIT_FLAG_N)}));
		return ActionReturnStringsGlobals.ARTICLEACTION_AUDITARTICLE_SUCCESS;
	}

	/**
	 * 审核一篇文章,对一篇文章进行审核或者取消审核,ajax操作
	 * 要求参数,文章id值,以article.id表示,审核标志,以id值标识
	 */
	public void auditArticleAjax() throws Exception {
		AjaxReturnSet<String> ajaxReturnSet = new AjaxReturnSet<String>();
		if (this.article != null) {
			this.article = this.service.getArticle(this.article.getId());
		}
		if (this.article == null) {

			ajaxReturnSet.setBool(false);
			ajaxReturnSet.setResult(this.getText(AjaxMessageGlobals.ARTICLEACTION_AUDITARTICLEAJAX_NOARTICLE_FAIL));
			AjaxResponse.sendAjaxText(ajaxReturnSet, ServletActionContext.getResponse());
		}

		/** 更新数据 **/
		this.service.updateArticleByAudit(this.article, id != 0);
		ajaxReturnSet.setBool(true);
		ajaxReturnSet.setResult(id != 0 ? "true" : "false");
		AjaxResponse.sendAjaxText(ajaxReturnSet, ServletActionContext.getResponse());
	}

	/**
	 * 批处理取消置顶文章,此操作只能取消置顶,不能置顶,原则上也不允许批置顶文章.
	 * 要求参数,文章列表数
	 */
	public String topArticleBatch() throws Exception {
		if (this.articleList == null || this.articleList.size() < 1) {
			this.addActionError(this.getText(ActionMessageGlobals.ARTICLEACTION_TOPARTICLEBATCH_NOARTICLELIST_FAIL));
			return ActionReturnStringsGlobals.ARTICLEACTION_TOPARTICLEBATCH_FAIL;
		}

		/** 重新创建列表并填充相关数据,并去除可能的null值对象 **/
		List<Article> articleListTemp = new ArrayList<Article>();
		for (Article a : this.articleList) {
			if (a == null) {
				continue;
			}
			a = this.service.getArticle(a.getId());

			if (a != null) {
				articleListTemp.add(a);
			}
		}

		/** 批处理更新操作 **/
		this.service.updateArticleListByTop(articleListTemp, null);

		this.addActionMessage(this.getText(ActionMessageGlobals.ARTICLEACTION_TOPARTICLEBATCH_SUCCESS));
		return ActionReturnStringsGlobals.ARTICLEACTION_TOPARTICLEBATCH_SUCCESS;
	}

	/**
	 * 批处理审核文章,此操作可批处理进行文章的审核和取消审核操作.
	 * 要求参数,文章列表数,这里以articleList来表示,审核标记,这里以id值来表示.
	 */
	public String auditArticleBatch() throws Exception {
		if (this.articleList == null || this.articleList.size() < 1) {
			this.addActionError(this.getText(ActionMessageGlobals.ARTICLEACTION_AUDITARTICLEBATCH_NOARTICLELIST_FAIL));
			return ActionReturnStringsGlobals.ARTICLEACTION_AUDITARTICLEBATCH_FAIL;
		}

		/** 重新创建列表并填充相关数据,并去除可能的null值对象 **/
		List<Article> articleListTemp = new ArrayList<Article>();
		for (Article a : this.articleList) {
			if (a == null) {
				continue;
			}
			a = this.service.getArticle(a.getId());

			if (a != null) {
				articleListTemp.add(a);
			}
		}

		/** 批处理更新操作 **/
		this.service.updateArticleListByAudit(articleListTemp, this.id != 0);

		this.addActionMessage(this.getText(ActionMessageGlobals.ARTICLEACTION_AUDITARTICLEBATCH_SUCCESS, new String[]{this.id != 0 ? this.getText(ActionMessageGlobals.ARTICLEACTION_AUDITARTICLEBATCH_AUDIT_FLAG_Y) : this.getText(ActionMessageGlobals.ARTICLEACTION_AUDITARTICLEBATCH_AUDIT_FLAG_N)}));
		return ActionReturnStringsGlobals.ARTICLEACTION_AUDITARTICLEBATCH_SUCCESS;
	}


	/**
	 * 查询数据,此查询为前台服务,要求文章必须是已经审核过的,且未被删除的,并进行分页显示
	 * 要求参数,要查询的文章的类的id,此处以id字段表示.是否是级联查询,此处用cascade表示
	 */
	public String queryArticleFromCategoryFront() {
		Category category = this.service.getCategory(id);
		if (category == null) {
			this.addActionError(this.getText(ActionMessageGlobals.ARTICLEACTION_QUERYARTICLEFROMCATEGORYFRONT_NOCATEGORY_FAIL));
			return ActionReturnStringsGlobals.ARTICLEACTION_QUERYARTICLEFROMCATEGORYFRONT_FAIL;
		}

		/** 初始化分页对象 **/
		this.page = this.page == null ? new Page() : this.page;

		/** 构建查询语句 **/
		DetachedCriteria criteria = DetachedCriteria.forClass(Article.class);
		/** 初始化条件,显示第一页,已审核,未被删除 **/
		criteria.add(Restrictions.eq("pageCurrentIndex", 1)).add(Restrictions.eq("audited", true)).add(Restrictions.eq("firstDeleted", false)).add(Restrictions.eq("shiftDeleted", false));
		/** 追加级联条件 **/
		if (this.cascade != null && this.cascade.equals("true")) {
			criteria.add(Restrictions.like("categoryChain", category.getCategoryChain(), MatchMode.START));
		} else {
			criteria.add(Restrictions.eq("category", category));
		}
		/** 追加排序标准,按时间进行排序 **/
		criteria.addOrder(Order.desc("createDate"));

		/** 开始查询 **/
		this.articleList = this.service.queryArticleListByCriteria(criteria, page);
		if (this.articleList == null || this.articleList.size() < 1) {
			this.addActionError(this.getText(ActionMessageGlobals.ARTICLEACTION_QUERYARTICLEFROMCATEGORYFRONT_NOARTICLELIST_FAIL));
			return ActionReturnStringsGlobals.ARTICLEACTION_QUERYARTICLEFROMCATEGORYFRONT_FAIL;
		}

		/** 如果是第一页,进入置顶查询,并将其加入到文章列表中 **/
		if (page.getCurrentPage() == 1) {
			/** 追加置顶查询条件 **/
			List<ArticleTopType> articleTopTypeList = this.service.getArticleTopTypeListNowValid();
			if (articleTopTypeList != null && articleTopTypeList.size() > 0) {
				criteria.add(Restrictions.in("articleTopType", articleTopTypeList));
			}
			List<Article> articleTopListTemp = this.service.queryArticleListByCriteria(criteria);
			/** 进行列表处理,将置顶对象加入最前面,如果原列表中有相应的对象,则将其删除 **/
			if (articleTopListTemp != null && articleTopListTemp.size() > 0) {
				/** 第一步,删去可能已经存在于列表中的对象 **/
				for (Article a : articleTopListTemp) {
					this.articleList.remove(a);
				}
				/** 第二步,增加置顶对象 **/
				for (Article a : articleTopListTemp) {
					this.articleList.add(0, a);
				}
			}
			//缩减列表大小
			if (this.articleList.size() > this.page.getPageSize()) {
				this.articleList = this.articleList.subList(0, this.page.getPageSize());
			}
		}

		return ActionReturnStringsGlobals.ARTICLEACTION_QUERYARTICLEFROMCATEGORYFRONT_SUCCESS;
	}

	/**
	 * 查询数据,此查询为后台服务,要求文章可以进行分页显示,对附加的条件如是否审核,是否已经第一次删除,
	 * 此由加入的article对象来进行判断,当然如果文章已经第一次删除,由审核与删除不能共存.
	 * 要求参数,要查询的文章的类的id,此处以id字段表示.是否是级联查询,此处用cascade表示,以及可选
	 * 的article对象,如果对象非空,此表示已经带有相关的审核条件或者删除条件.
	 * 2007-8-22注:这里删除条件删除,因为一篇文章被删除却没有删除其类别,但其类别却是不可控制,而寻找一篇被
	 * 删除的文章不需要指定其类别.
	 * 注明:这个方法和前台查询方法基本一致,但分开进行编写.主要考虑到安全性以及权限方面的扩展性,
	 * 如果用参数来表示前台和后台,则容易造成可能的攻击,且对安全方面的设计不好控制.
	 */
	public String queryArticleFromCategoryBack() throws Exception {
		Category category = this.service.getCategory(this.id);
		if (category == null) {
			this.addActionError(this.getText(ActionMessageGlobals.ARTICLEACTION_QUERYARTICLEFROMCATEGORYBACK_NOCATEGORY_FAIL));
			return ActionReturnStringsGlobals.ARTICLEACTION_QUERYARTICLEFROMCATEGORYBACK_FAIL;
		}

		/** 初始化页面组件 **/
		this.page = this.page == null ? new Page() : this.page;

		/** 构建查询条件语句 **/
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Article.class);
		/** 构建查询语句,第一页面,非最终删除 **/
		detachedCriteria.add(Restrictions.eq("pageCurrentIndex", 1)).add(Restrictions.eq("shiftDeleted", false)).add(Restrictions.eq("firstDeleted", false));
		/** 追加级联条件 **/
		if (this.cascade != null && this.cascade.equals("true")) {
			detachedCriteria.add(Restrictions.like("categoryChain", category.getCategoryChain(), MatchMode.START));
		} else {
			detachedCriteria.add(Restrictions.eq("category", category));
		}
		/** 追加可能的审核条件**/
		if (this.article != null) {
			detachedCriteria.add(Restrictions.eq("audited", this.article.isAudited()));
		}
		/** 追加排序条件 **/
		detachedCriteria.addOrder(Order.desc("createDate"));

		/** 开始查询 **/
		this.articleList = this.service.queryArticleListByCriteria(detachedCriteria, page);
		if (this.articleList == null || this.articleList.size() < 1) {
			this.addActionError(this.getText(ActionMessageGlobals.ARTICLEACTION_QUERYARTICLEFROMCATEGORYBACK_NOARTICLELIST_FAIL));
			return ActionReturnStringsGlobals.ARTICLEACTION_QUERYARTICLEFROMCATEGORYBACK_FAIL;
		}

		/** 如果是第一页,进入置顶查询,并加入文章列表中 **/
		if (page.getCurrentPage() == 1 && this.article != null) {
			/** 追加置顶查询条件 **/
			List<ArticleTopType> articleTopTypeList = this.service.getArticleTopTypeListNowValid();
			if (articleTopTypeList != null && articleTopTypeList.size() > 0) {
				detachedCriteria.add(Restrictions.in("articleTopType", articleTopTypeList));
			}
			List<Article> articleTopListTemp = this.service.queryArticleListByCriteria(detachedCriteria);
			/** 进行列表处理,将置顶对象加入最前面,如果原列表中有相应的对象,则将其删除 **/
			if (articleTopListTemp != null && articleTopListTemp.size() > 0) {
				/** 第一步,删去可能已经存在于列表中的对象 **/
				for (Article a : articleTopListTemp) {
					this.articleList.remove(a);
				}
				/** 第二步,增加置顶对象 **/
				for (Article a : articleTopListTemp) {
					this.articleList.add(0, a);
				}
			}
			//缩减列表大小
			if (this.articleList.size() > this.page.getPageSize()) {
				this.articleList = this.articleList.subList(0, this.page.getPageSize());
			}
		}

		return ActionReturnStringsGlobals.ARTICLEACTION_QUERYARTICLEFROMCATEGORYBACK_SUCCESS;
	}

	/**
	 * 预览文章,此操作发生在保存文章之前,依照目前的相关的条件,对文件进行分类,划分分页,以及定义相关
	 * 的显示参数.此显示结果力求与最终显示结果一样.
	 * 要求参数,文章的各项参数,仍以id作为类别的选择
	 */
	public String previewArticle() throws Exception {
		/** 预览类别 **/
		Category category = this.service.getCategory(id);
		/** 设置来源,此默认来源由于国际化的需要,而引用相关资源文件key值 **/
		String source = this.article.getSource() == null ? this.getText(ProjectConfigureGlobals.ARTICLE_DEFAULT_LOCALSOURCE_KEY) : this.article.getSource();
		/** 设置文章摘要,默认有文章的前50个字符 **/
		String summary = this.article.getSummary() == null ? (this.article.getContent().length() < 50 ? this.article.getContent() : this.article.getContent().substring(0, 50)) : this.article.getSummary();
		/** 得到文章内容数组,因为文章可能是分页的,所以用分隔符将其隔开,分成几个页面 **/
		String[] contents = this.article.getContent().split(ProjectConfigureGlobals.ARTICLE_DEFAULT_SEPARAROR);
		/** 设置发表作者 **/
		User user = this.article.getEditUser() == null ? null : this.service.getUser(this.article.getEditUser().getId());

		/** 开始保存文章列表 **/
		int pageTotalCount = contents.length; //文章总页数
		int pageCurrentIndex = 1; //文章当前页数
		for (int i = 0; i < pageTotalCount; i++) {
			Article a = new Article();
			a.setTitle(this.article.getTitle());
			a.setTitleColor(this.article.getTitleColor());
			a.setSummary(summary);
			a.setSource(source);
			a.setContent(contents[pageCurrentIndex - 1]);
			a.setPageCount(pageTotalCount);
			a.setPageCurrentIndex(pageCurrentIndex);
			a.setCreateUser(user);
			a.setCategory(category);
			a.setTags(this.article.getTags());
			this.articleList.add(a);
			pageCurrentIndex++;
		}

		return ActionReturnStringsGlobals.ARTICLEACTION_PREVIEWARTICLE_SUCCESS;
	}

	/**
	 * 查询文章,根据各种条件查询相关的文章.
	 * 现在的查询条件只开放三个,标题,tags,和摘要查询,三者查询只能三选一,不能重复.
	 * 要求参数,标题,tags,或者摘要.
	 */
	public String queryArticleByDynamik() throws Exception {
		if (this.article == null || (!StringUtils.hasText(this.article.getTitle()) && !StringUtils.hasText(this.article.getTags()) && !StringUtils.hasText(this.article.getSummary()))) {
			this.addActionError(this.getText(ActionMessageGlobals.ARTICLEACTION_QUERYARTICLEBYDYNAMIC_NOARTICLE_FAIL));
			return ActionReturnStringsGlobals.ARTICLEACTION_QUERYARTICLEBYDYNAMIC_FAIL;
		}

		/** 初始化分页组件 **/
		this.page = this.page == null ? new Page() : this.page;

		/** 构造查询条件 **/
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Article.class);
		/** 相关条件:显示为第一页,非删除,已审核,类别不能是已删除 **/
		detachedCriteria.add(Restrictions.eq("pageCurrentIndex", 1)).add(Restrictions.eq("firstDeleted", false)).add(Restrictions.eq("shiftDeleted", false)).add(Restrictions.eq("audited", true)).add(Restrictions.isNotNull("category"));
		/** 追加查询条件 **/
		if (StringUtils.hasText(this.article.getTitle())) {
			//如果有查询标题
			detachedCriteria.add(Restrictions.like("title", this.article.getTitle(), MatchMode.ANYWHERE));
		} else if (StringUtils.hasText(this.article.getTags())) {
			//如果有tags
			detachedCriteria.add(Restrictions.like("tags", this.article.getTags(), MatchMode.ANYWHERE));
		} else {
			detachedCriteria.add(Restrictions.like("summary", this.article.getSummary(), MatchMode.ANYWHERE));
		}
		/** 追加排序标准 **/
		detachedCriteria.addOrder(Order.desc("createDate"));

		/**开始查询 **/
		this.articleList = this.service.queryArticleListByCriteria(detachedCriteria, page);
		if (this.articleList == null || this.articleList.size() < 1) {
			this.addActionError(this.getText(ActionMessageGlobals.ARTICLEACTION_QUERYARTICLEBYDYNAMIC_NOARTICLELIST_FAIL));
			return ActionReturnStringsGlobals.ARTICLEACTION_QUERYARTICLEBYDYNAMIC_FAIL;
		}

		return ActionReturnStringsGlobals.ARTICLEACTION_QUERYARTICLEBYDYNAMIC_SUCCESS;
	}

	/**
	 * 为一篇文章添加置顶信息而显示该文章,因此不必考虑其是否是第一页或者其他相关的信息,只需要取得当前文章的
	 * id信息,和相关的title信息即可.
	 * 要求参数,该文章的id,以id表示.
	 */
	public String showArticleForAddArticleTopType() throws Exception {
		/** 取得当前文章 **/
		this.article = this.service.getArticle(this.id);
		if (this.article == null || this.article.isFirstDeleted() || this.article.isShiftDeleted()) {
			this.addActionError(this.getText(ActionMessageGlobals.ARTICLEACTION_SHOWARTICLEFORADDARTICLETOPTYPE_NOARTICLE_FAIL));
			return ActionReturnStringsGlobals.ARTICLEACTION_SHOWARTICLEFORADDARTICLETOPTYPE_FAIL;
		}

		return ActionReturnStringsGlobals.ARTICLEACTION_SHOWARTICLEFORADDARTICLETOPTYPE_SUCCESS;
	}

	public void setCascade(String cascade) {
		this.cascade = cascade;
	}

	public void setType(ArticleTopType type) {
		this.type = type;
	}

	/**
	 * 查询文章,这些文章是被第一次删除之后的文章,这些文章满足,不是最终删除的,类别是存在的.
	 * 要求参数,可能的相关分页组件参数
	 */
	public String queryArticleFirstDeletedBack() throws Exception {
		/** 初始化分页组件 **/
		this.page = this.page == null ? new Page() : this.page;
		/** 构建查询条件 **/
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Article.class);
		/** 追加条件,第一页,非最终删除,第一次被删除,类别存在 **/
		detachedCriteria.add(Restrictions.eq("pageCurrentIndex", 1)).add(Restrictions.eq("shiftDeleted", false)).add(Restrictions.eq("firstDeleted", true)).add(Restrictions.isNotNull("category"));
		/** 追加排序条件 **/
		detachedCriteria.addOrder(Order.desc("createDate"));

		/** 开始查询 **/
		this.articleList = this.service.queryArticleListByCriteria(detachedCriteria, this.page);
		if (this.articleList == null || this.articleList.size() < 1) {
			this.addActionError(this.getText(ActionMessageGlobals.ARTICLEACTION_QUERYARTICLEFIRSTDELETEDBACK_NOARTICLELIST_FAIL));
			return ActionReturnStringsGlobals.ARTICLEACTION_QUERYARTICLEFIRSTDELETEDBACK_FAIL;
		}

		return ActionReturnStringsGlobals.ARTICLEACTION_QUERYARTICLEFIRSTDELETEDBACK_SUCCESS;
	}

	/**
	 * 查询文章,这些文章没有被最终删除,也没有被第一次删除,但是类别不存在.
	 * 要求参数,可能的分页信息
	 */
	public String queryArticleNoCategoryBack() throws Exception {
		/** 初始化分页组件信息 **/
		this.page = this.page == null ? new Page() : this.page;
		/** 构建查询条件 **/
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Article.class);
		/** 追加查询条件,第一页,未被删除,类别不存在. **/
		detachedCriteria.add(Restrictions.eq("firstDeleted", false)).add(Restrictions.eq("shiftDeleted", false)).add(Restrictions.eq("pageCurrentIndex", 1)).add(Restrictions.isNull("category"));
		/** 追加排序条件 **/
		detachedCriteria.addOrder(Order.desc("createDate"));

		/** 开始查询 **/
		this.articleList = this.service.queryArticleListByCriteria(detachedCriteria, this.page);
		if (this.articleList == null || this.articleList.size() < 1) {
			this.addActionError(this.getText(ActionMessageGlobals.ARTICLEACTION_QUERYARTICLENOCATEGORYBACK_NOARTICLELIST_FAIL));
			return ActionReturnStringsGlobals.ARTICLEACTION_QUERYARTICLENOCATEGORYBACK_FAIL;
		}

		return ActionReturnStringsGlobals.ARTICLEACTION_QUERYARTICLENOCATEGORYBACK_SUCCESS;
	}

	/**
	 * 将没有类别的文章转移到一个新的类别里面,这些文章都是将类别删除之后其自身的类别属性被删除,所以将其转移到一个新
	 * 的类别下面,当然在转移之前,保证这个类别是存在的.
	 * 要求参数,要转移的文章,以articleList[].id的形式表示,转移到的类别的id,以id表示.
	 */
	public String changeArticleCategoryBatch() throws Exception {
		/** 是否已经选择了转移数据 **/
		if (this.articleList == null) {
			this.addActionError(this.getText(ActionMessageGlobals.ARTICLEACTION_CHANGEARTICLECATEGORYBATCH_NOARTICLELIST_FAIL));
			return ActionReturnStringsGlobals.ARTICLEACTION_CHANGEARTICLECATEGORYBATCH_FAIL;
		}

		/** 查询要转移的类别 **/
		Category category = this.service.getCategory(this.id);
		if (category == null) {
			this.addActionError(this.getText(ActionMessageGlobals.ARTICLEACTION_CHANGEARTICLECATEGORYBATCH_NOCATEGORY_FAIL));
			return ActionReturnStringsGlobals.ARTICLEACTION_CHANGEARTICLECATEGORYBATCH_FAIL;
		}

		/** 重新创建列表并填充相关数据,并去除可能的null值对象 **/
		List<Article> articleListTemp = new ArrayList<Article>();
		for (Article a : this.articleList) {
			if (a == null) {
				continue;
			}
			a = this.service.getArticle(a.getId());

			if (a != null) {
				articleListTemp.add(a);
			}
		}

		/** 开始转移数据 **/
		this.service.updateArticleListByCategory(articleListTemp, category);

		this.addActionMessage(this.getText(ActionMessageGlobals.ARTICLEACTION_CHANGEARTICLECATEGORYBATCH_SUCCESS, new String[]{category.getName()}));
		return ActionReturnStringsGlobals.ARTICLEACTION_CHANGEARTICLECATEGORYBATCH_SUCCESS;
	}
}
