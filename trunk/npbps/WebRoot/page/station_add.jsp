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
		<title>添加站段</title>
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
		<script src="js/station.js"></script>
	</head>
	<body>
		<div class="left" style="width: 93%;margin: 20px 30px 10px 30px;">
			<form id="addStationForm" name="addStationForm" method="get">
				<table width="600px" id="yd_form" border="0" cellpadding="4" cellspacing="0">
					<tr>
						<td>站名称</td>
						<td><input type="text" id="stationName" maxlength="20"/><em>*</em></td>
						<td>段名称</td>
						<td><input type="text" id="sectionName" maxlength="20"/><em>*</em></td>
					</tr>
					<tr>
						<td colspan="4" style="padding:8px 0px 10px 10px" align="center">
							<input id="station_add" type="button" value="添  加" style="width:70px"/>
							<input type="reset" value="重  置" style="width:70px"/>
						</td>
					</tr>	
				</table>
			</form>
		</div>
	</body>
</html>
