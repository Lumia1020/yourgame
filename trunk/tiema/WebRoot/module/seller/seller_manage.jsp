<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath %>"/>
		<title>销售员设置</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<link rel="stylesheet" type="text/css" href="css/main.css"/>
		<link rel="stylesheet" type="text/css" href="css/skin<s:property value="#session.user.skin"/>.css" class="skin"/>
		<link rel="stylesheet" type="text/css" href="libraries/jquery/plugin/pagination/pagination.css"/>
		<link rel="stylesheet" type="text/css" href="libraries/jquery/plugin/thickbox/thickbox.css" />
		<link rel="stylesheet" type="text/css" href="libraries/dtree/dtree.css"/>
		
		<%@ include file="/commons/common_css.jsp"%>
		<%@ include file="/commons/common_js.jsp"%>
		
		<script type="text/javascript" src="libraries/dtree/dtree.js"></script>
		<script type="text/javascript" src="module/seller/js/seller.manage.js"></script>
	</head>
	<body class="tbody" id="list">
		<div class="txt_title">
			销售员设置
		</div>
		
		
		<div class="toolbg1 toolbgline toolheight nowrap" style="min-width: 750px">
			<div class="right">
				&nbsp;
			</div>
			<div class="nowrap left">
				<input id="btn_show_add" value="增加销售员" class="wd4 btn" type="button" />
				<input id="btn_show_update" value="修改销售员" class="wd4 btn" disabled="disabled" type="button" />
				<input id="btn_change_state" value="取消销售员" class="wd4 btn" disabled="disabled" type="button" />
				<input id="btn_show_query" value="综合查询" class="wd3 btn" type="button" />
			</div>
			<div class="clr">&nbsp;</div>
		</div>
		
		<table style="table-layout: fixed; width: 100%;min-width: 750px" class="sortable O2" cellpadding="0" cellspacing="0" id="table_list">
			<thead>
				<tr>
					<th style="padding: 1px 0pt 1px 5px;width:27px;height:20px" class="sorttable_nosort">&nbsp;</th>
					<th class="o_title2">姓名</th>
					<th class="o_title2" style="width: 60px;">性别</th>
					<th class="o_title2 sorttable_nosort">部门</th>
					<th class="o_title2 sorttable_nosort">移动电话</th>
					<th class="o_title2 sorttable_nosort">职务</th>
					<th class="o_title2" style="width: 80px;">销售员状态</th>
					<th class="o_title2 sorttable_nosort">备注</th>
				</tr>
			</thead>
			<tbody class="toarea"></tbody>
			<tfoot></tfoot>
		</table>
		
		<div class="list_btline">
			<div style="height: 19px; padding-top: 5px;" class="f_size selbar_bt barspace2">
				<span class="addrtitle">&nbsp;</span>&nbsp;
			</div>
		</div>
		<div class="toolbg1 toolbgline toolheight nowrap" id="page_infos" style="min-width: 750px;"></div>
		
		
		<div id="div_add" style="display: none;">
			<div class="container attbg2 bd_upload">
				<ol></ol>
			</div>
			<div class="form-container">
				<form method="post" action="json/seller/entity-add" id="form_add">
					<fieldset>
						<legend>&nbsp;增加销售员&nbsp;</legend>
						<table width="100%">
							<tr>
								<td valign="top">
									<div>
										<label for="seller_sellerName_add" class="label">姓名<em>*</em></label>
										<input id="seller_sellerName_add" name="seller.sellerName" class="input" type="text"/>
									</div>
									<div>
										<label for="seller_deptName_add" class="label">部门</label>
										<input id="seller_deptName_add" name="seller.deptName" class="input" type="text"/>
									</div>
									<div>
										<label for="seller_mobile_add" class="label">移动电话</label>
										<input id="seller_mobile_add" name="seller.mobile" class="input" type="text"/>
									</div>
								</td>
								<td valign="top">
									<div style="padding:0 0 7px 0">
										<label for="seller_sex_add" class="label">性别</label>
										<s:iterator value="@com.tiema.core.constant.Sex@values()" var="s" begin="0">
											<label>
												<input value="<s:property value="#s"/>" name="seller.sex" type="radio" checked="checked"/>
												<s:property value="#s.label"/>&nbsp;&nbsp;&nbsp;&nbsp;
											</label>
										</s:iterator>
									</div>
									<div>
										<label for="seller_jobTitle_add" class="label">职务</label>
										<input id="seller_jobTitle_add" name="seller.jobTitle" class="input" type="text"/>
									</div>
									<div>
										<label for="seller_im_add" class="label">QQ/MSN</label>
										<input id="seller_im_add" name="seller.im" class="input" type="text"/>
									</div>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<div>
										<label for="seller_email_add" class="label">电子邮件</label>
										<input id="seller_email_add" name="seller.email" class="input" type="text" style="width:508px;"/>
									</div>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<div>
										<label for="seller_remark_add" class="label">备注</label>
										<textarea name="seller.remark" id="seller_remark_add" style="width:508px;height:60px;"></textarea>
									</div>
								</td>
							</tr>
						</table>
					</fieldset>
					<div class="buttonrow" style="text-align: right;">
						<br/>
						<input id="btn_add" class="wd2 btn" value="新增" type="submit" />
					</div>
				</form>
			</div>
		</div>
		
		
		
		<div id="div_update" style="display: none;">
			<div class="container attbg2 bd_upload">
				<ol></ol>
			</div>
			<div class="form-container">
				<form method="post" action="json/seller/entity-update" id="form_update">
					<fieldset>
						<legend>&nbsp;修改销售员&nbsp;</legend>
						<table width="100%">
							<tr>
								<td valign="top">
									<div>
										<label for="seller_sellerName_update" class="label">姓名<em>*</em></label>
										<input id="seller_sellerName_update" name="seller.sellerName" class="input" type="text"/>
										<input name="seller.id" type="hidden"/>
									</div>
									<div>
										<label for="seller_deptName_update" class="label">部门</label>
										<input id="seller_deptName_update" name="seller.deptName" class="input" type="text"/>
									</div>
									<div>
										<label for="seller_mobile_update" class="label">移动电话</label>
										<input id="seller_mobile_update" name="seller.mobile" class="input" type="text"/>
									</div>
								</td>
								<td valign="top">
									<div style="padding:0 0 7px 0">
										<label for="seller_sex_update" class="label">性别</label>
										<s:iterator value="@com.tiema.core.constant.Sex@values()" var="s" begin="0">
											<label>
												<input value="<s:property value="#s"/>" name="seller.sex" type="radio" checked="checked"/>
												<s:property value="#s.label"/>&nbsp;&nbsp;&nbsp;&nbsp;
											</label>
										</s:iterator>
									</div>
									<div>
										<label for="seller_jobTitle_update" class="label">职务</label>
										<input id="seller_jobTitle_update" name="seller.jobTitle" class="input" type="text"/>
									</div>
									<div>
										<label for="seller_im_update" class="label">QQ/MSN</label>
										<input id="seller_im_update" name="seller.im" class="input" type="text"/>
									</div>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<div>
										<label for="seller_email_update" class="label">电子邮件</label>
										<input id="seller_email_update" name="seller.email" class="input" type="text" style="width:508px;"/>
									</div>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<div>
										<label for="seller_remark_update" class="label">备注</label>
										<textarea name="seller.remark" id="seller_remark_update" style="width:508px;height:60px;"></textarea>
									</div>
								</td>
							</tr>
						</table>
					</fieldset>
					<div class="buttonrow" style="text-align: right;">
						<br/>
						<input id="btn_update" class="wd2 btn" value="更新" type="submit" />
					</div>
				</form>
			</div>
		</div>
		
		
		<div id="div_query" style="display: none;">
			<div class="form-container">
				<form method="post" action="json/seller/page!find" id="form_query">
					<fieldset>
						<legend>&nbsp;综合查询条件&nbsp;</legend>
						<table width="100%">
							<tr>
								<td valign="top">
									<div>
										<label for="seller_sellerName_query" class="label">姓名</label>
										<input id="seller_sellerName_query" name="seller.sellerName" class="input" type="text"/>
									</div>
									<div>
										<label for="seller_deptName_query" class="label">部门</label>
										<input id="seller_deptName_query" name="seller.deptName" class="input" type="text"/>
									</div>
									<div>
										<label for="seller_mobile_query" class="label">移动电话</label>
										<input id="seller_mobile_query" name="seller.mobile" class="input" type="text"/>
									</div>
									<div>
										<label for="seller_jobTitle_query" class="label">职务</label>
										<input id="seller_jobTitle_query" name="seller.jobTitle" class="input" type="text"/>
									</div>
									<div>
										<label for="seller_state_query" class="label">销售员状态</label>
										<select id="seller_state_query" name="seller.state" class="input" style="width:190px">
											<option value="">所有状态</option>
											<s:iterator value="@com.tiema.core.constant.State@values()" var="s" begin="1">
												<option value="<s:property value="#s"/>"><s:property value="#s.label"/></option>
											</s:iterator>
										</select>
									</div>
								</td>
							</tr>
						</table>
					</fieldset>
					<div class="buttonrow" style="text-align: right;">
						<br/>
						<input id="btn_query" value="查询" class="wd2 btn" type="button"/>
					</div>
				</form>
			</div>
		</div>
		
	</body>
</html>