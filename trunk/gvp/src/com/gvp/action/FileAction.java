package com.gvp.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gvp.core.Action;
import com.gvp.core.BaseAction;
import com.gvp.core.MyFile;
import com.gvp.core.MyUtils;
import com.gvp.core.Page;
import com.gvp.po.QuoteInfo;
import com.gvp.po.RefFiles;
import com.gvp.service.IPublicService;

/**
 * FileAction.java Create on 2008-12-18 上午09:16:22
 * 
 * 说明:文件处理
 * 
 * Copyright (c) 2008 by yourgame.
 * 
 * @author 廖瀚卿
 * @version 1.0
 */
@SuppressWarnings("serial")
public class FileAction extends BaseAction {
	private Map<String, Object> infos = new HashMap<String, Object>();

	public static final String ROOT = "upload\\";

	private File myUpload;

	private String myUploadContentType;

	private String myUploadFileName;

	private String path;

	private String node;

	private List nodes;

	private Page page;

	private String name;

	private String[] paths;

	private boolean success;

	private QuoteInfo quoteInfo;

	private IPublicService publicService;

	public QuoteInfo getQuoteInfo() {
		return quoteInfo;
	}

	public void setQuoteInfo(QuoteInfo quoteInfo) {
		this.quoteInfo = quoteInfo;
	}

	/**
	 * 处理中文下载名
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String getDownloadFileName() throws UnsupportedEncodingException {
		String named = new String(name.getBytes(), "ISO8859-1");
		return named;
	}

	/**
	 * 获得文件下载流
	 * 
	 * @return
	 * @throws FileNotFoundException
	 */
	public InputStream getInputStream() throws FileNotFoundException {
		return getServletContext().getResourceAsStream(ROOT + path + "/" + name);
	}

	/**
	 * 下载文件
	 * 
	 * @return
	 */
	public String download() {
		return SUCCESS;
	}

	/**
	 * 创建文件夹
	 * 
	 * @return
	 */
	public String createFolder() {
		String rootPath = getSession().getServletContext().getRealPath("/");
		rootPath += ROOT;
		String createPath = rootPath + getPath() + "/";
		setSuccess(MyUtils.mkDirectory(createPath + getName()));
		return SUCCESS;
	}

	/**
	 * 上传文件
	 * 
	 * @return
	 */
	@Action(description="上传文档到服务器")
	public String uploadFiles() {
		String rootPath = getSession().getServletContext().getRealPath("/");
		rootPath += ROOT;
		String sp = rootPath + getPath();
		String fileName = null;
		MyUtils.mkDirectory(sp);
		try {
			fileName = MyUtils.upload(getMyUploadFileName(), sp, getMyUpload());
			RefFiles r = new RefFiles();
			r.setRemark(getMyUploadFileName());
			r.setQid(quoteInfo.getQid());
			r.setUrl(ROOT + getPath() + fileName);
			publicService.saveEntity(r,r.getQid());
			this.success = true;
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}



	/**
	 * 2008-12-18-下午02:00:17
	 * 
	 * 功能:获得指定目录下的所有目录信息
	 * 
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public String getDirectories() throws FileNotFoundException, IOException {
		String rootPath = getSession().getServletContext().getRealPath("/");
		rootPath += ROOT;
		File file = new File(rootPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		file = null;
		nodes = listFiles(rootPath, node, true);
		return SUCCESS;
	}

	/**
	 * 多文件删除
	 * 
	 * @return
	 */
	@Action(description="删除文档")
	public String deleteFiles() {
		String rootPath = getSession().getServletContext().getRealPath("/");
		rootPath += ROOT;
		boolean flag = false;
		try {
			for (String path : paths) {
				flag = MyUtils.delFiles(rootPath + path);
				if (!flag) {
					break;
				}
			}
		} catch (RuntimeException e) {
			flag = false;
			e.printStackTrace();
		}
		setSuccess(flag);
		return SUCCESS;
	}

	/**
	 * 2008-12-18-下午04:52:19
	 * 
	 * 功能:获得指定路径下大所有文件和文件夹信息，把数据封装到nodes返回
	 * 
	 * @param folder
	 *            当前要访问的文件夹目录名称
	 * @param onlyDirectory
	 *            null:获得所有信息，true:只获得文件夹,false:只获得文件信息
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	@SuppressWarnings("unchecked")
	private List listFiles(String rootPath, String folder, boolean onlyDirectory) throws FileNotFoundException,
			IOException {
		List filelist = new ArrayList();
		File[] arrFiles = new File(rootPath + folder).listFiles();
		MyFile nd = null;
		if (arrFiles != null) {
			for (File f : arrFiles) {
				String id = f.getAbsolutePath();
				nd = new MyFile();
				nd.setId(id.substring(rootPath.length()));
				nd.setName(f.getName());
				nd.setLeaf(f.isFile());
				nd.setUrl(id.substring(rootPath.length()));
				if (f.isFile()) {
					int size = new FileInputStream(f).available();
					nd.setSize(size);
				} else {
					nd.setSize(0);
				}
				nd.setLastmod(f.lastModified());
				if (onlyDirectory && !f.isDirectory()) {
					continue;
				}
				filelist.add(nd);
			}
		}
		return filelist;
	}

	/**
	 * 2008-12-18-下午05:17:38
	 * 
	 * 功能:获得指定文件夹下面的所有文件和文件夹信息
	 * 
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public String getFiles() throws FileNotFoundException, IOException {
		String rootPath = getSession().getServletContext().getRealPath("/");
		rootPath += ROOT;
		File file = new File(rootPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		file = null;
		node = node == null ? "" : node;
		page.setRoot(this.listFiles(rootPath, node, false));
		int length = new File(rootPath + node).list().length;
		page.setTotalProperty(length);
		return SUCCESS;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node.equals("*") ? "" : node; // 处理根结点特殊id
	}

	public List getNodes() {
		return nodes;
	}

	public void setNodes(List files) {
		this.nodes = files;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) throws UnsupportedEncodingException {
		this.path = URLDecoder.decode(path, "UTF-8");
	}

	public File getMyUpload() {
		return myUpload;
	}

	public void setMyUpload(File myUpload) {
		this.myUpload = myUpload;
	}

	public String getMyUploadContentType() {
		return myUploadContentType;
	}

	public void setMyUploadContentType(String myUploadContentType) {
		this.myUploadContentType = myUploadContentType;
	}

	public String getMyUploadFileName() {
		return myUploadFileName;
	}

	public void setMyUploadFileName(String myUploadFileName) {
		this.myUploadFileName = myUploadFileName;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getName() {
		return name;
	}

	public void setName(String fileName) throws UnsupportedEncodingException {
		this.name = URLDecoder.decode(fileName, "UTF-8");
	}

	public String[] getPaths() {
		return paths;
	}

	public void setPaths(String[] names) {
		this.paths = names;
	}

	public Map<String, Object> getInfos() {
		return infos;
	}

	public void setInfos(Map<String, Object> infos) {
		this.infos = infos;
	}

	public void setPublicService(IPublicService publicService) {
		this.publicService = publicService;
	}
}
