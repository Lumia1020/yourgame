<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<base href="<%=basePath%>" />
		<title>非邮发报刊业务处理系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
			<style type="text/css"> 
			 .align-center{  
			     margin:0 auto;      
			     background:#fdfdfd url('images/login.png') no-repeat;
			     height: 500px;
			     width:700px;
			 }   
			 .inner_div{
			 	margin: 165px 400px 100px 240px;
			 }
			 body {
				background-color: #fdfdfd;
				font-size: 12px;
				font-family: "微软雅黑";
				overflow: hidden;
			 }
	 		</style>
	</head>

	<body>
		<form id="LoginForm" name="LoginForm" action="<%=basePath%>page/login.action" method="post">
			<div class="align-center">
				<div class="inner_div">
					<table border="0" cellpadding="8" style="padding: 165px 400px 100px 0px;">
						<tr>
							<td style="color: white">
								<nobr>用户名</nobr>
							</td>
							<td>
								<input id="username" name="user.userId" maxlength="128" type="text" style="width:180px;" />
							</td>
						</tr>
						<tr>
							<td  style="color: white">
								密&nbsp;&nbsp;码
							</td>
							<td>
								<input id="password" name="user.password" maxlength="128" type="password" style="width:180px;" />
							</td>
						</tr>
						<tr>
							<td colspan="2" align="right">
								<input type="submit" value="登录" style="width:60px"/>
								<input type="reset" value="重置" style="width:60px"/>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</form>
	</body>
	<%--
	
--%>
</html>
