<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="../css/accordian.css"></link>
		<script type="text/javascript" src="../js/accordian.pack.js"></script>
		<base target="centerFrame" />
	</head>
	<body onload="new Accordian('basic-accordian',5,'header_highlight');">
		<div id="basic-accordian">
			<div class="accordian_border">
				<div id="test-header" class="accordion_headings  header_highlight">
					投递管理
				</div>
				<div id="test-content" class="content">
					<c:if test='${sessionScope.permissions.delivery eq "true"}'>
						<div class="accordion_child">
							<a href="showDispenses.action?page.start=0&page.limit=10">投递接收</a>
						</div>
					</c:if>
					<c:if test='${sessionScope.permissions.verification eq "true"}'>
						<div class="accordion_child">
							<a href="showGetDatas.action?page.start=0&page.limit=10">投递清单打印</a>
						</div>
					</c:if>
				</div>
				<div id="test1-header" class="accordion_headings">
					信息管理
				</div>
				<div id="test1-content">
					<c:if test='${sessionScope.permissions.station eq "true"}'>
						<div class="accordion_child">
							<a href="showStations.action?page.start=0&page.limit=10">站段信息管理</a>
						</div>
					</c:if>
					<c:if test="${sessionScope.permissions.seat eq 'true'}">
						<div class="accordion_child">
							<a href="showSeats.action?page.start=0&page.limit=10">台席信息管理</a>
						</div>
					</c:if>
					<c:if test='${sessionScope.permissions.grid eq "true"}'>
						<div class="accordion_child">
							<a href="showGridsPage.action?page.start=0&page.limit=10">格口信息管理</a>
						</div>
					</c:if>
					<c:if test='${sessionScope.permissions.dept eq "true"}'>
						<div class="accordion_child">
							<a href="showDepts.action?page.start=0&page.limit=10">部门信息管理</a>
						</div>
					</c:if>
					<c:if test='${sessionScope.permissions.role eq "true"}'>
						<div class="accordion_child">
							<a href="showRoles.action?page.start=0&page.limit=10">角色管理</a>
						</div>
					</c:if>
					<c:if test='${sessionScope.permissions.user eq "true"}'>
						<div class="accordion_child">
							<a href="showUsers.action?page.start=0&page.limit=10">用户信息管理</a>
						</div>
					</c:if>
					<c:if test='${true}'>
						<div class="accordion_child">
							<a href="getdata_import.jsp">导入要数表</a>
						</div>
					</c:if>
				</div>
			</div>
		</div>
	</body>
</html>
