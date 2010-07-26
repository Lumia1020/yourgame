<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>导入要数表</title>

		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link href="css/print.css" rel="stylesheet" type="text/css">
		<link href="css/default.css" rel="stylesheet" type="text/css" />
		<link href="css/uploadify.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>
		<script type="text/javascript" src="js/swfobject.js"></script>
		<script type="text/javascript" src="js/jquery.uploadify.v2.1.0.min.js"></script>
		<script type="text/javascript">
		$(document).ready(function() {
			$("#data").uploadify({
				'uploader'       : '<%=basePath%>js/uploadify.swf',
				'script'         : '<%=basePath%>importGetData.action',
				'cancelImg'      : '<%=basePath%>images/cancel.png',
				//'buttonImg'		 : '<%=basePath%>images/cancel.png',
				'folder'         : 'uploads',
				'fileDataName'   : 'data',
				'queueID'        : 'fileQueue',
				'auto'           : false,
				'multi'          : false,
				'fileDesc'		 : 'Microsoft Excel 2003 文件',
				'fileExt'  		 : '*.xls',
				onError : function(){
					alert('导入文件出现错误');
				},
				onAllComplete : function(e,result){
					//alert(result.filesUploaded  + '个文件上传成功!\n\n' + result.errors + '个文件上传失败!');
					if(result.filesUploaded >= 1){
						alert('文件导入完成');
					}
				}
			});
		});
		</script>
	</head>
	<body bgcolor="#F4F1EA">
		<div id="fileQueue"></div>
		<input type="file" name="data" id="data" />
		<p><button onclick="jQuery('#data').uploadifyUpload()">开始导入</button></p>
	</body>
</html>
