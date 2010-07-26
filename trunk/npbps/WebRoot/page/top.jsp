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
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<style>
			#logo {
				-moz-background-clip:border;
				-moz-background-inline-policy:continuous;
				-moz-background-origin:padding;
				background:transparent url(<%=basePath%>images/logo.png) repeat scroll 0 0;
				display:block;
				float:left;
				height:69px;
				margin-left:10px;
				overflow:hidden;
				text-indent:-100em;
				width:431px;
			}
		</style>
		<script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>
		<script type="text/javascript">
			$(function(){
				$('#logout').click(function(){
					$.post("<%=basePath%>logout.action", {'a':1},
				        function () {
				        	parent.location='<%=basePath%>';
				        } 
			        );
		    	});
		    });
		</script>
	</head>

	<body topmargin="0" leftmargin="0" rightmargin="0">
		<div style="background: url('images/top_bg.png') repeat-x;height: 69px;font-family:'微软雅黑',Arial,Helvetica, sans-serif;font-size: 12px;color:#fff;">
			<a id="logo" href="javascript:void(0);">非邮发</a>
			<div align="right" style="padding: 10px"><span style="color:#9c0;" align="right">${sessionScope.user.userName}</span>,欢迎您! <a href="javascript:void(0);" id="logout" style="color:#fff;">退出</a></div>
		</div>
	</body>
</html>
