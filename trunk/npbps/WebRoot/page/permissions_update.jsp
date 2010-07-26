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
		<title>修改用户权限</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<style>
			*{font-size:12px;margin:0;padding:0;}
			fieldset{color:#333;border:#3A6180 1px solid;margin:10px;padding:10px;}
			legend{color:#3A6180;font-weight:800;background:#fff;}
			ul{list-style-type:none;margin:8px 0 4px;}
			li{margin-top:4px;}
		</style>
		<script src="js/jquery-1.3.2.min.js"></script>
		<script src="js/user.js"></script>
	</head>
	<body>
		<fieldset> 
			<legend>投递管理</legend>
			<div style="float: left;">
				<ul> 
					<li><input field="delivery" type="checkbox" pid="${requestScope.permissions.pid}" ${requestScope.permissions.delivery eq "true" ? "checked='checked'":""}/><label for="delivery">投递接收</label></li> 
					<li><input field="verification" type="checkbox" pid="${requestScope.permissions.pid}" ${requestScope.permissions.verification eq "true" ? "checked='checked'":""}/><label for="verification">投递清单打印</label></li> 
				</ul>
			</div>
		</fieldset>
		<fieldset> 
			<legend>信息管理</legend>
			<div style="float: left;">
				<ul> 
					<li><input field="seat" type="checkbox" pid="${requestScope.permissions.pid}" ${requestScope.permissions.seat eq "true" ? "checked='checked'":""}/>&nbsp;<label for="seat">台席信息管理</label></li> 
					<li><input field="grid" type="checkbox" pid="${requestScope.permissions.pid}" ${requestScope.permissions.grid eq "true" ? "checked='checked'":""}/>&nbsp;<label for="grid">格扣信息管理</label></li> 
					<li><input field="station" type="checkbox" pid="${requestScope.permissions.pid}" ${requestScope.permissions.station eq "true" ? "checked='checked'":""}/>&nbsp;<label for="station">站段信息管理</label></li> 
					<li><input field="user" type="checkbox" pid="${requestScope.permissions.pid}" ${requestScope.permissions.user eq "true" ? "checked='checked'":""}/>&nbsp;<label for="user">用户信息管理</label></li> 
				</ul>
			</div>
			<div style="float: left;padding-left: 30px">
				<ul> 
					<li><input field="dept" type="checkbox" pid="${requestScope.permissions.pid}" ${requestScope.permissions.dept eq "true" ? "checked='checked'":""}/>&nbsp;<label for="dept">部门信息管理</label></li> 
					<li><input field="role" type="checkbox" pid="${requestScope.permissions.pid}" ${requestScope.permissions.role eq "true" ? "checked='checked'":""}/>&nbsp;<label for="role">角色管理</label></li> 
					<li><input field="verification" type="checkbox" pid="${requestScope.permissions.pid}" ${requestScope.permissions.verification eq "true" ? "checked='checked'":""}/>&nbsp;<label for="verification">查验管理</label></li> 
					<li><input field="delivery" type="checkbox" pid="${requestScope.permissions.pid}" ${requestScope.permissions.delivery eq "true" ? "checked='checked'":""}/>&nbsp;<label for="delivery">投递管理</label></li> 
				</ul>
			</div>
		</fieldset>
		
		<div align="center"><button onclick="window.close();">确定</button></div>
	</body>
</html>
