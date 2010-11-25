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
		<title>用户设置</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" type="text/css" href="css/main.css"/>
		<link rel="stylesheet" type="text/css" href="css/skin<s:property value="#session.user.skin"/>.css" class="skin"/>
		<link rel="stylesheet" type="text/css" href="libraries/jquery/plugin/pagination/pagination.css"/>
		<link rel="stylesheet" type="text/css" href="libraries/jquery/plugin/thickbox/thickbox.css" />
		
		<%@ include file="/commons/common_css.jsp"%>
		<%@ include file="/commons/common_js.jsp"%>
		<script type="text/javascript" src="module/user/js/user.manage.js"></script>
	</head>
	<body class="tbody" id="list">
		<div class="txt_title">
			用户设置
		</div>
		
		<div class="toolbg1 toolbgline toolheight nowrap" style="min-width: 750px">
			<div class="right">
				&nbsp;
			</div>
			<div class="nowrap left">
				<input id="btn_show_add" value="增加用户" class="wd3 btn" type="button" />
				<input id="btn_show_update" value="修改用户" class="wd3 btn" disabled="disabled" type="button" />
				<input id="btn_delete" value="删除用户" class="wd3 btn" disabled="disabled" type="button" />
				<input id="btn_show_query" value="综合查询" class="wd3 btn" type="button" />
			</div>
			<div class="clr">&nbsp;</div>
		</div>
		
		<table style="table-layout: fixed; width: 100%;min-width: 750px" class="sortable O2" cellpadding="0" cellspacing="0" id="table_list">
			<thead>
				<tr>
					<th style="padding: 1px 0pt 1px 5px;width:27px;height:20px" class="sorttable_nosort">&nbsp;</th>
					<th class="o_title2" style="width: 100px;">中文名</th>
					<th class="o_title2" style="width: 80px;">用户名</th>
					<th class="o_title2" style="width: 48px;">性别</th>
					<th class="o_title2" style="width: 80px;">职务</th>
					<th class="o_title2" style="width: 120px;">移动电话</th>
					<th class="o_title2" style="width: 48px;">状态</th>
					<th class="o_title2">角色</th>
					<th class="o_title2">备注</th>
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
				<form method="post" action="json/user/entity-add" id="form_add">
					<fieldset>
						<legend>&nbsp;账户信息&nbsp;</legend>
						<table width="100%">
							<tr>
								<td valign="top">
									<div>
										<label for="user_chineseName_add" class="label">中文名<em>*</em></label>
										<input id="user_chineseName_add" name="user.chineseName" class="input" type="text"/>
									</div>
								</td>
								<td valign="top">
									<div>
										<label for="user_username_add" class="label">用户名<em>*</em></label>
										<input id="user_username_add" name="user.username" class="input" type="text"/>
									</div>
								</td>
							</tr>
							<tr>
								<td valign="top">
									<div>
										<label for="user_password_add" class="label">用户密码<em>*</em></label>
										<input id="user_password_add" name="user.password" class="input" type="text"/>
									</div>
								</td>
								<td valign="top">
									<div>
										<label for="user_password2_add" class="label">确认密码<em>*</em></label>
										<input id="user_password2_add" name="user_password2" class="input" type="text"/>
									</div>
								</td>
							</tr>
						</table>
					</fieldset>
					
					<fieldset>
						<legend class="legend_other_user_infos">&nbsp;用户其他信息&nbsp;</legend>
						<table width="100%">
							<tr>
								<td valign="top">
									<div style="padding:7px 0">
										<label for="user_sex_add" class="label">性别</label>
										<s:iterator value="@com.tiema.core.constant.Sex@values()" var="s" begin="1">
											<label>
												<input value="<s:property value="#s"/>" name="user.sex" type="radio" checked/>
												<s:property value="#s.label"/>&nbsp;&nbsp;&nbsp;&nbsp;
											</label>
										</s:iterator>
									</div>
									<div>
										<label for="user_jobTitle_add" class="label">职称</label>
										<input id="user_jobTitle_add" name="user.jobTitle" class="input" type="text"/>
									</div>
									<div>
										<label for="user_homeAddress_add" class="label">家庭住址</label>
										<input id="user_homeAddress_add" name="user.homeAddress" class="input" type="text"/>
									</div>
									<div>
										<label for="user_email_add" class="label">电子邮件</label>
										<input id="user_email_add" name="user.email" class="input" type="text"/>
									</div>
									<div>
										<label for="user_entryDate_add" class="label">入职日期</label>
										<input id="user_entryDate_add" name="user.entryDate" onfocus="WdatePicker()" class="input" type="text"/>
									</div>
								</td>
								<td valign="top">
									<div>
										<label for="user_mobile_add" class="label">移动电话</label>
										<input id="user_mobile_add" name="user.mobile" class="input" type="text"/>
									</div>
									<div>
										<label for="user_birthday_add" class="label">生日</label>
										<input id="user_birthday_add" name="user.birthday" onfocus="WdatePicker({startDate:'1970-05-01'})" class="input" type="text"/>
									</div>
									<div>
										<label for="user_zipCode_add" class="label">邮政编码</label>
										<input id="user_zipCode_add" name="user.zipCode" class="input" type="text"/>
									</div>
									<div>
										<label for="user_emergencyContacts_add" class="label">紧急情况联系人</label>
										<input id="user_emergencyContacts_add" name="user.emergencyContacts" class="input" type="text"/>
									</div>
									<div>
										<label for="user_emergencyPhone_add" class="label">紧急情况联系电话</label>
										<input id="user_emergencyPhone_add" name="user.emergencyPhone" class="input" type="text"/>
									</div>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<div>
										<label for="user_remark_add" class="label">备注</label>
										<textarea name="user.remark" id="user_remark_add" style="width:500px;height:80px;"></textarea>
									</div>
								</td>
							</tr>
						</table>
					</fieldset>
					
					<fieldset>
						<legend class="legend_set_role">&nbsp;设定用户角色&nbsp;</legend>
						<table width="100%">
							<tr>
								<td valign="top" colspan="2">
									<div>
										<label class="label">角色列表</label>
										<div style="overflow-x: hidden; overflow: auto; height: 140px; width: 508px;" class="bd_upload checkbox_container">
											<div class="div_list">
												<label style="cursor: pointer; font-weight: bold;">
													<input type="checkbox" class="checkhead" />
													全选
												</label>
											</div>
											<s:iterator value="roles" var="r" status="st">
												<div class="div_list">
													<label style="cursor: pointer;">
														<input type="checkbox" name="user.roles.makeNew[<s:property value="#st.index "/>].id" value='<s:property value="id"/>' class="checkitem" />
														<s:property value="roleName" />
														<span class="addrtitle">&nbsp;&nbsp;<s:property value="remark" />
														</span>&nbsp;
													</label>
												</div>
											</s:iterator>
										</div>
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
				<form method="post" action="json/user/entity-update" id="form_update">
					<fieldset>
						<legend>&nbsp;账户信息&nbsp;</legend>
						<table width="100%">
							<tr>
								<td valign="top">
									<div>
										<label for="user_chineseName_update" class="label">中文名<em>*</em></label>
										<input id="user_chineseName_update" name="user.chineseName" class="input" type="text"/>
									</div>
								</td>
								<td valign="top">
									<div>
										<label for="user_username_update" class="label">用户名<em>*</em></label>
										<input id="user_username_update" name="user.username" class="input" type="text"/>
										<input id="user_id_update" name="user.id" type="hidden"/>
									</div>
								</td>
							</tr>
							<tr>
								<td valign="top">
									<div>
										<label for="user_password_update" class="label">用户密码<em>*</em></label>
										<input id="user_password_update" name="user.password" class="input" type="text"/>
									</div>
								</td>
								<td valign="top">
									<div>
										<label for="user_password2_update" class="label">确认密码<em>*</em></label>
										<input id="user_password2_update" name="user_password2" class="input" type="text"/>
									</div>
								</td>
							</tr>
						</table>
					</fieldset>
					<fieldset>
						<legend class="legend_other_user_infos">&nbsp;用户其他信息&nbsp;</legend>
						<table width="100%">
							<tr>
								<td valign="top">
									<div style="padding:7px 0">
										<label for="user_sex_update" class="label">性别</label>
										<s:iterator value="@com.tiema.core.constant.Sex@values()" var="s" begin="1">
											<label>
												<input value="<s:property value="#s"/>" name="user.sex" type="radio" checked/>
												<s:property value="#s.label"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											</label>
										</s:iterator>
									</div>
									<div>
										<label for="user_jobTitle_update" class="label">职称</label>
										<input id="user_jobTitle_update" name="user.jobTitle" class="input" type="text"/>
									</div>
									<div>
										<label for="user_homeAddress_update" class="label">家庭住址</label>
										<input id="user_homeAddress_update" name="user.homeAddress" class="input" type="text"/>
									</div>
									<div>
										<label for="user_email_update" class="label">电子邮件</label>
										<input id="user_email_update" name="user.email" class="input" type="text"/>
									</div>
									<div>
										<label for="user_entryDate_update" class="label">入职日期</label>
										<input id="user_entryDate_update" name="user.entryDate" onfocus="WdatePicker()" class="input" type="text"/>
									</div>
								</td>
								<td valign="top">
									<div>
										<label for="user_mobile_update" class="label">移动电话</label>
										<input id="user_mobile_update" name="user.mobile" class="input" type="text"/>
									</div>
									<div>
										<label for="user_birthday_update" class="label">生日</label>
										<input id="user_birthday_update" name="user.birthday" onfocus="WdatePicker({startDate:'1970-05-01'})" class="input" type="text"/>
									</div>
									<div>
										<label for="user_zipCode_update" class="label">邮政编码</label>
										<input id="user_zipCode_update" name="user.zipCode" class="input" type="text"/>
									</div>
									<div>
										<label for="user_emergencyContacts_update" class="label">紧急情况联系人</label>
										<input id="user_emergencyContacts_update" name="user.emergencyContacts" class="input" type="text"/>
									</div>
									<div>
										<label for="user_emergencyPhone_update" class="label">紧急情况联系电话</label>
										<input id="user_emergencyPhone_update" name="user.emergencyPhone" class="input" type="text"/>
									</div>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<div>
										<label for="user_remark_update" class="label">备注</label>
										<textarea name="user.remark" id="user_remark_update" style="width:500px;height:80px;"></textarea>
									</div>
								</td>
							</tr>
						</table>
					</fieldset>
					
					<fieldset>
						<legend class="legend_set_role">&nbsp;设定用户角色&nbsp;</legend>
						<table width="100%">
							<tr>
								<td valign="top" colspan="2">
									<div>
										<label class="label">角色列表</label>
										<div style="overflow-x:hidden;overflow:auto;height:140px;width:508px;" class="bd_upload checkbox_container" id="div_roles_update"> 
											<div class="div_list">
												<label style="cursor: pointer; font-weight: bold;">
													<input type="checkbox" class="checkhead" />
													全选
												</label>
											</div>
											<s:iterator value="roles" var="r" status="st">
												<div class="div_list">
													<label style="cursor: pointer;">
														<input type="checkbox" name="user.roles.makeNew[<s:property value="#st.index "/>].id" value='<s:property value="id"/>' class="checkitem" />
														<s:property value="roleName" />
														<span class="addrtitle">&nbsp;&nbsp;<s:property value="remark" /></span>&nbsp;
													</label>
												</div>
											</s:iterator>
										</div>
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
				<form method="post" action="json/user/findUserViewPage" id="form_query">
					<fieldset>
						<legend>&nbsp;综合查询条件&nbsp;</legend>
						<table width="100%">
							<tr>
								<td valign="top">
									<div>
										<label for="userView_chineseName_query" class="label">中文名</label>
										<input id="userView_chineseName_query" name="userView.chineseName" class="input" type="text"/>
									</div>
									<div>
										<label for="userView_username_query" class="label">用户名</label>
										<input id="userView_username_query" name="userView.username" class="input" type="text"/>
									</div>
									<div>
										<label for="userView_mobile_query" class="label">移动电话</label>
										<input id="userView_mobile_query" name="userView.mobile" class="input" type="text"/>
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