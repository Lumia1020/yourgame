<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<title>永钜精密五金厂--报时系统</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="../resources/css/ext-all.css">
		<link rel="stylesheet" type="text/css" href="../css/RowEditor.css">
		<link rel="stylesheet" type="text/css" href="../css/silk.css">
		<link rel="stylesheet" type="text/css" href="../css/main.css">
		<link rel="stylesheet" type="text/css" href="../css/chooser.css" />
		<link rel="stylesheet" type="text/css" href="../css/UploadPanel.css">
	
		<script type="text/javascript" src="../js/ext-base.js"></script>
		<script type="text/javascript" src="../js/ext-all.js"></script>
		<script type="text/javascript"> 
			Ext.USER_ID = '<s:property value="#session.user.userid"/>';
			Ext.CURRENT_USER = '<s:property value="#session.user.name"/>';
			Ext.ROLE_R01 = '<s:property value="#session.role.r01"/>' == 'true' ? true:false;
			Ext.ROLE_R02 = '<s:property value="#session.role.r02"/>' == 'true' ? true:false;
			Ext.ROLE_R03 = '<s:property value="#session.role.r03"/>' == 'true' ? true:false;
			Ext.ROLE_R04 = '<s:property value="#session.role.r04"/>' == 'true' ? true:false;
			Ext.ROLE_R05 = '<s:property value="#session.role.r05"/>' == 'true' ? true:false;
			Ext.ROLE_R06 = '<s:property value="#session.role.r06"/>' == 'true' ? true:false;
			Ext.ROLE_R07 = '<s:property value="#session.role.r07"/>' == 'true' ? true:false;
			Ext.ROLE_R08 = '<s:property value="#session.role.r08"/>' == 'true' ? true:false;
			Ext.ROLE_R09 = '<s:property value="#session.role.r09"/>' == 'true' ? true:false;
			Ext.ROLE_R10 = '<s:property value="#session.role.r10"/>' == 'true' ? true:false;
			Ext.ROLE_R11 = '<s:property value="#session.role.r11"/>' == 'true' ? true:false;
			Ext.ROLE_R12 = '<s:property value="#session.role.r12"/>' == 'true' ? true:false;
			Ext.ROLE_R13 = '<s:property value="#session.role.r13"/>' == 'true' ? true:false;
			Ext.ROLE_R14 = '<s:property value="#session.role.r14"/>' == 'true' ? true:false;
			Ext.ROLE_R15 = '<s:property value="#session.role.r15"/>' == 'true' ? true:false;
			Ext.ROLE_R16 = '<s:property value="#session.role.r16"/>' == 'true' ? true:false;
			Ext.ROLE_R17 = '<s:property value="#session.role.r17"/>' == 'true' ? true:false;
			Ext.ROLE_R18 = '<s:property value="#session.role.r18"/>' == 'true' ? true:false;
			Ext.ROLE_R19 = '<s:property value="#session.role.r19"/>' == 'true' ? true:false;
			Ext.ROLE_R20 = '<s:property value="#session.role.r20"/>' == 'true' ? true:false;
			Ext.ROLE_R21 = '<s:property value="#session.role.r21"/>' == 'true' ? true:false;
			Ext.ROLE_R22 = '<s:property value="#session.role.r22"/>' == 'true' ? true:false;
			Ext.ROLE_R23 = '<s:property value="#session.role.r23"/>' == 'true' ? true:false;
			
			Ext.ROLE_R24 = '<s:property value="#session.role.r24"/>' == 'true' ? true:false;
			Ext.ROLE_R25 = '<s:property value="#session.role.r25"/>' == 'true' ? true:false;
			Ext.ROLE_R26 = '<s:property value="#session.role.r26"/>' == 'true' ? true:false;
			Ext.ROLE_R27 = '<s:property value="#session.role.r27"/>' == 'true' ? true:false;
			Ext.ROLE_R28 = '<s:property value="#session.role.r28"/>' == 'true' ? true:false;
			Ext.ROLE_R29 = '<s:property value="#session.role.r29"/>' == 'true' ? true:false;
			Ext.ROLE_R30 = '<s:property value="#session.role.r30"/>' == 'true' ? true:false;
			/*Ext.ROLE_R31 = '<s:property value="#session.role.r31"/>' == 'true' ? true:false;*/
			
		</script>
		<script type="text/javascript" src="../js/ext-lang-zh_CN.js"></script>
		<script type="text/javascript" src="../js/swfupload.js"></script>
		<script type="text/javascript" src="../js/RowEditor.js"></script>
		<script type="text/javascript" src="../js/UploadPanel.js"></script>
		<script type="text/javascript" src="../js/treeData.js"></script>
		<script type="text/javascript" src="../js/jquery-1.3.2.min.js"></script>
		<script type="text/javascript" src="../js/jquery.rotate.1-1.js"></script>
		<script type="text/javascript" src="../js/main.js"></script>
	</head>
	<body oncontextmenu="return false">
		<div id="header">
			<table id="header-table" cellspacing="0" cellpadding="0" border="0">
				<tbody>
					<tr>
						<td id="header-left">
							&nbsp;
						</td>
						<td id="header-right">
							&nbsp;
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<%--<div id="mapPic" style="display:none">
			<div class="nav">
				<div class="up" id="up"></div>
				<div class="right" id="right"></div>
				<div class="down" id="down"></div>
				<div class="left" id="left"></div>
				<div class="zoom" id="zoom"></div>
				<div class="in" id="in"></div>
				<div class="out" id="out"></div>
				<div class="silk-arrow-refresh" id="rotate"></div>
			</div>
			<img id='image' src='#' border='0' style="display:none"> 
		</div>
	--%></body>
</html>

