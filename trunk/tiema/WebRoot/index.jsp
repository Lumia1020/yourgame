<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>铁马高尔夫预定信息系统</title>
		<link rel="stylesheet" type="text/css" href="css/login.css"/>
		<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
	</head>

	<body>
		<form method="post" action="user/user!login" name="form">
				Username:<input type="text" name="user.username" value="test" id="username"/><br/>
				Password:<input type="password" class="inputBorder" name="user.password" value="test" maxlength="20" id="password"/>
				<input type="submit" value="Login"/>
		</form>

	</body>
</html>
