<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>铁马高尔夫预定信息系统</title>
		<link href="css/login.css" rel="stylesheet" type="text/css" />
		<script src="libraries/jquery/jquery-1.4.2.min.js" type="text/javascript"></script>
		<script>
			$(function(){
				$('#btn_submit').click(function(){
					var username = $('#username').val();
					var password = $('#password').val();
					var msg = '';
					if($.trim(username).length == 0){
						msg += '用户名不能为空!\n\n';
					}
					if($.trim(password).length == 0){
						msg += '密码不能为空!\n';
					}
					
					if($.trim(msg).length > 0){
						alert(msg);
						return;
					}
					document.form.submit();
				});
				document.form.submit();
			});
		</script>
	</head>

	<body>
		<form method="post" action="default/user/user!login" name="form" id="login_form">
			<div class="layout">
				<dl>
					<dt>
						用户名
					</dt>
					<dd>
						<input name="user.username" id="username" type="text" class="loginInput" value="test"/>
					</dd>
					<dt>
						密码
					</dt>
					<dd>
						<input name="user.password" id="password" type="password" class="loginInput" value="test" />
					</dd>
				</dl>
				<p class="loginButton">
					<nobr><font color="red"><s:property value="errors.login_fail[0]"/></font><img src="images/login/button.jpg" id="btn_submit"/></nobr>
				</p>
			</div>
		</form>
		<p class="copying">
			版权及版本说明版权及版本说明
		</p>
	</body>
</html>
