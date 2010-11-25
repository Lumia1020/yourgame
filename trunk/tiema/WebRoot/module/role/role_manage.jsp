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
		<title>角色设置</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="css/main.css">
		<link rel="stylesheet" type="text/css" href="css/skin<s:property value="#session.user.skin"/>.css" class="skin">
		<link rel="stylesheet" type="text/css" href="libraries/jquery/plugin/pagination/pagination.css">
		<link rel="stylesheet" type="text/css" href="libraries/jquery/plugin/thickbox/thickbox.css" />
		<link rel="stylesheet" type="text/css" href="libraries/dtree/dtree.css"/>
		
		<%@ include file="/commons/common_css.jsp"%>
		<%@ include file="/commons/common_js.jsp"%>
		
		<script type="text/javascript" src="libraries/dtree/dtree.js"></script>
		<script type="text/javascript" src="module/role/js/role.manage.js"></script>
	</head>
	<body class="tbody" id="list">
		<div class="txt_title">
			角色设置
		</div>
		
		
		<div class="toolbg1 toolbgline toolheight nowrap" style="min-width: 750px">
			<div class="right">
				&nbsp;
			</div>
			<div class="nowrap left">
				<input id="btn_show_add" value="增加角色" class="wd3 btn" type="button" />
				<input id="btn_show_update" value="修改角色" class="wd3 btn" disabled="disabled" type="button" />
				<input id="btn_delete" value="删除角色" class="wd3 btn" disabled="disabled" type="button" />
				<input id="btn_show_role_setup" value="权限设置" class="wd3 btn" disabled="disabled" type="button" />
				<input id="btn_show_query" value="综合查询" class="wd3 btn" type="button" />
			</div>
			<div class="clr">&nbsp;</div>
		</div>
		
		<table style="table-layout: fixed; width: 100%;min-width: 750px" class="sortable O2" cellpadding="0" cellspacing="0" id="table_list">
			<thead>
				<tr>
					<th style="padding: 1px 0pt 1px 5px;width:27px;height:20px" class="sorttable_nosort">&nbsp;</th>
					<th class="o_title2" style="width: 100px;">角色名称</th>
					<th class="o_title2 sorttable_nosort">角色说明</th>
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
				<form method="post" action="json/role/entity-add" id="form_add">
					<fieldset>
						<legend>&nbsp;增加角色&nbsp;</legend>
						<table width="100%">
							<tr>
								<td valign="top">
									<div>
										<label for="role_roleName_add" class="label">角色名<em>*</em></label>
										<input id="role_roleName_add" name="role.roleName" class="input" type="text"/>
									</div>
								</td>
							</tr>
							<tr>
								<td>
									<div>
										<label for="role_remark_add" class="label">备注</label>
										<textarea id="role_remark_add" name="role.remark" style="width:180px;height:80px;"></textarea>
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
				<ol>
				</ol>
			</div>
			<div class="form-container">
				<form method="post" action="json/role/entity-update" id="form_update">
					<fieldset>
						<legend>&nbsp;修改角色&nbsp;</legend>
						<table width="100%">
							<tr>
								<td valign="top">
									<div>
										<label for="role_roleName_update" class="label">角色名<em>*</em></label>
										<input id="role_roleName_update" name="role.roleName" class="input" type="text"/>
										<input id="role_id_update" name="role.id" type="hidden"/>
									</div>
								</td>
							</tr>
							<tr>
								<td>
									<div>
										<label for="role_remark_update" class="label">备注</label>
										<textarea id="role_remark_update" name="role.remark" style="width:180px;height:80px;"></textarea>
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
				<form method="post" action="json/role/page!find" id="form_query">
					<fieldset>
						<legend>&nbsp;综合查询条件&nbsp;</legend>
						<table width="100%">
							<tr>
								<td valign="top">
									<div>
										<label for="role_roleName_query" class="label">角色名</label>
										<input id="role_roleName_query" name="role.roleName" class="input" type="text"/>
										<input type="text" style="display: none;"/>
									</div>
								</td>
							</tr>
							<tr>
								<td>
									<div>
										<label for="role_remark_query" class="label">备注</label>
										<textarea id="role_remark_query" name="role.remark" style="width:180px;height:80px;"></textarea>
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
		
		
		<div id="div_role" style="display:none;">
			<div class="form-container">
				<form method="post" action="json/role/success!updateRolePermissions" id="form_update_role_permission">
					<input type="hidden" id="roleId" />
					<fieldset>
						<legend>&nbsp;角色权限树&nbsp;</legend>
						<div>
							<input id="btn_tree_toggle" type="button" value="折叠树" class="wd4 btn"/>
							<div id="div_role_tree"></div>
						</div>
					</fieldset>
					<div class="buttonrow" style="text-align: right;">
						<input id="btn_setup" type="button" value="设置" class="wd2 btn"/>
						<input id="btn_cancel" type="button" value="取消" class="wd2 btn"/>
					</div>
				</form>
			</div>
		</div>
		
		
	</body>
</html>