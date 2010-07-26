<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
	<head>
		<base href="<%=basePath%>">
		<title>添加角色</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<!--[if IE]>
			<link rel="stylesheet" type="text/css" href="css/public_ie.css">
		<![endif]-->
		<!--[if !IE]><!-->
			<link rel="stylesheet" type="text/css" href="css/public_ff.css">
		<!--<![endif]--> 
		<style type="text/css">
			*{font-family: "Lucida Grande","Lucida Sans Unicode",Arial,Verdana,sans-serif;font-size: 12px;}
			em{color:red}
			#yd_form {
				border-top-width: 1px;
				border-left-width: 1px;
				border-top-style: solid;
				border-left-style: solid;
				border-top-color: #CCCCCC;
				border-left-color: #CCCCCC;
			} #yd_form td {
				border-right-width: 1px;
				border-bottom-width: 1px;
				border-right-style: solid;
				border-bottom-style: solid;
				border-right-color: #CCCCCC;
				border-bottom-color: #CCCCCC;
			}
		</style>
		<script src="js/jquery-1.3.2.min.js"></script>
		<script src="js/user.js"></script>
	</head>
	<body>
		<div class="left" style="width: 93%;margin: 20px 30px 10px 30px;">
			<form id="addRoleForm" name="addRoleForm" method="get">
				<table width="600px" id="yd_form" border="0" cellpadding="4" cellspacing="0">
					<tr>
						<td>角色名称</td>
						<td><input type="text" id="roleName" maxlength="20"/><em>*</em></td>
					</tr>
					<tr>
						<td>权限</td>
						<td>
							<fieldset> 
								<legend>投递管理</legend>
								<div style="float: left;">
									<ul> 
										<li><input field="delivery" type="checkbox" id="delivery"/><label for="delivery">投递接收</label></li> 
										<li><input field="verification" type="checkbox" id="verification" /><label for="verification">投递清单打印</label></li> 
									</ul>
								</div>
							</fieldset>
							<fieldset> 
								<legend>信息管理</legend>
								<div style="float: left;">
									<ul> 
										<li><input field="seat" type="checkbox" id="seat"/>&nbsp;<label for="seat">台席信息管理</label></li> 
										<li><input field="grid" type="checkbox" id="grid"/>&nbsp;<label for="grid">格口信息管理</label></li> 
										<li><input field="station" type="checkbox" id="station"/>&nbsp;<label for="station">站段信息管理</label></li> 
										<li><input field="user" type="checkbox" id="user"/>&nbsp;<label for="user">用户信息管理</label></li> 
									</ul>
								</div>
								<div style="float: left;padding-left: 30px">
									<ul> 
										<li><input field="dept" type="checkbox" id="dept"/>&nbsp;<label for="dept">部门信息管理</label></li> 
										<li><input field="role" type="checkbox" id="role"/>&nbsp;<label for="role">角色管理</label></li> 
										<li><input field="verification" type="checkbox" id="verification"/>&nbsp;<label for="verification">查验管理</label></li> 
										<li><input field="delivery" type="checkbox" id="delivery"/>&nbsp;<label for="delivery">投递管理</label></li> 
									</ul>
								</div>
							</fieldset>
						</td>
					</tr>
					<tr>
						<td colspan="4" style="padding:8px 0px 10px 10px" align="center">
							<input id="role_add" type="button" value="添  加" style="width:70px"/>
							<input type="reset" value="重  置" style="width:70px"/>
						</td>
					</tr>	
				</table>
			</form>
		</div>
	</body>
</html>
