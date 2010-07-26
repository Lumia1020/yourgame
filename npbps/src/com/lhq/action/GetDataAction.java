package com.lhq.action;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import com.lhq.core.BaseAction;
import com.lhq.core.MyUtils;
import com.lhq.core.Page;
import com.lhq.po.Dept;
import com.lhq.po.GetData;
import com.lhq.service.IGetDataService;

@SuppressWarnings("serial")
public class GetDataAction extends BaseAction {

	private IGetDataService getDataService;

	private GetData getData;

	private Page page;

	private boolean success;

	private File data;// 上传的文件

	private String dataContentType;// 上传问文件类型

	private String dataFileName; // 上传文件名

	private String allowedTypes;// 允许上传的文件类型列表

	private String savePath;// 文件保存路径,通过ioc注入

	private Map<String, Object> infos = new HashMap<String, Object>();

	public String importGetData() {
		if (data != null) {
			String rootPath = getSession().getServletContext().getRealPath("/");
			String savePath = rootPath + getSavePath();
			MyUtils.mkDirectory(savePath);
			String fileName = MyUtils.upload(getDataFileName(), savePath, getData()); // 上传,然后返回文件名
			boolean success = getDataService.importGetData(savePath + fileName);// 读取上传后的文件然后导入数据,返回成功与否
			this.jsonString = "{\"success\":" + ((success ? "\"true\"" : "\"false\"") + "}"); // 因为有上传,所有用这种方式返回extjs,这里将跳转到
		}
		return SUCCESS;
	}

	public String updateFlags() {
		this.success = getDataService.updateFlags(page);
		return SUCCESS;
	}

	/**
	 * 更新分发
	 * 
	 * @return
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public String updateGetData() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		this.success = getDataService.updateGetData(getData);
		return SUCCESS;
	}

	/**
	 * 删除分发
	 * 
	 * @return
	 */
	public String deleteGetData() {
		this.success = getDataService.deleteGetData(getData);
		return SUCCESS;
	}

	/**
	 * 添加分发
	 * 
	 * @return
	 */
	public String addGetData() {
		try {
			this.success = getDataService.addGetData(getData);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String printGetData() {
		Dept dept = (Dept) getSession().getAttribute("dept");
		page.setObj(dept);
		page.getParams().put("print", "print");
		page = getDataService.getGetDataList(page);
		getRequest().setAttribute("page", page);
		return SUCCESS;
	}

	/**
	 * 列表分发
	 * 
	 * @return
	 * @throws ParseException
	 */
	public String showGetDatas() throws ParseException {
		Dept dept = (Dept) getSession().getAttribute("dept");
		page.setObj(dept);
		page = getDataService.getGetDataList(page);
		page.initPageInfo();
		getRequest().setAttribute("page", page);
		return SUCCESS;
	}

	public Map<String, Object> getInfos() {
		return infos;
	}

	public void setInfos(Map<String, Object> infos) {
		this.infos = infos;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public GetData getGetData() {
		return getData;
	}

	public void setGetData(GetData getData) {
		this.getData = getData;
	}

	public void setGetDataService(IGetDataService getDataService) {
		this.getDataService = getDataService;
	}

	public String getAllowedTypes() {
		return allowedTypes;
	}

	public void setAllowedTypes(String allowedTypes) {
		this.allowedTypes = allowedTypes;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public File getData() {
		return data;
	}

	public void setData(File upload) {
		this.data = upload;
	}

	public String getDataContentType() {
		return dataContentType;
	}

	public void setDataContentType(String uploadContentType) {
		this.dataContentType = uploadContentType;
	}

	public String getDataFileName() {
		return dataFileName;
	}

	public void setDataFileName(String uploadFileName) {
		this.dataFileName = uploadFileName;
	}

}
