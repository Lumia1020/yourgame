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
		<!--[if IE]>
			<link rel="stylesheet" type="text/css" href="css/public_ie.css">
		<![endif]-->
		
		<!--[if !IE]><!-->
			<link rel="stylesheet" type="text/css" href="css/public_ff.css">
		<!--<![endif]--> 
		<link rel="stylesheet" type="text/css" href="css/jquery-ui-1.7.2.custom.css">
		<script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>
		<script type="text/javascript" src="js/jquery.checkboxes.pack.js"></script>
		<script type="text/javascript" src="js/jquery-ui-1.7.2.custom.min.js"></script>
		<script type="text/javascript" src="js/ui.datepicker-zh-CN.js"></script>
		<script type="text/javascript" src="js/dispense.js"></script>
		<script type="text/javascript" src="js/station.js"></script>
	</head>
	<body style='overflow:scroll;overflow-x:hidden' topmargin="5">
		<form action="page/showDispenses.action?page.start=0&page.limit=10" method="post" id="dispenseForm" name="dispenseForm">
			<p>
				<label>接收日期</label>
				<input id="start_date" type="text" name="page.params.startDate" value="${requestScope.page.params.startDate}"/>
				到
				<input id="end_date" type="text" name="page.params.endDate" value="${requestScope.page.params.endDate}"/>
				<label style="padding-left:20px">接收状态</label>
				<select name="page.params.flag">
					<option value="">全部</option>
					<option value="0" ${requestScope.page.params.flag eq "0" ? "selected='selected'":"" }>未接收</option>
					<option value="1" ${requestScope.page.params.flag eq "1" ? "selected='selected'":"" }>已接收未打印</option>
					<option value="2" ${requestScope.page.params.flag eq "2" ? "selected='selected'":"" }>已接收已打印</option>
				</select>
				<input type="submit" value="查询"/>
				<br/>
			</p>
		</form>
		<div id="itsthetable">
			<table>
				<thead>
					<tr>
						<th scope="col" align="right">
							<nobr><button id="select_all">全选</button>&nbsp;<button id="select_toggle">反选</button></nobr>
						</th>
						<th scope="col">站名称</th>
						<th scope="col">段名称</th>
						<th scope="col">报刊代号</th>
						<th scope="col">报刊名称</th>
						<th scope="col">期别</th>
						<th scope="col">实际分发份数</th>
					</tr>
				</thead>	
				<tfoot>
					<tr>
						<th scope="row"><button id="update_flag" start="${requestScope.page.nextPage}" limit="${requestScope.page.limit}">批量接收</button></th>
						<td align="right" colspan="6">
							<nobr>
								总计 ${requestScope.page.totalProperty} 记录
								第 ${requestScope.page.currentPage}/${requestScope.page.totalProperty le requestScope.page.limit ? "1":requestScope.page.totalPage} 页,
						      	<c:choose>
									<c:when test="${requestScope.page.start gt 1}">
										<a href='page/showDispenses.action?page.start=0&page.limit=10' style="color:#fff">首页</a>
										&nbsp;&nbsp;
										<a href='page/showDispenses.action?page.start=${requestScope.page.prePage}&page.limit=10' style="color:#fff">上一页</a>
									</c:when>
									<c:otherwise>
										首页&nbsp;&nbsp;上一页
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test="${(requestScope.page.currentPage eq requestScope.page.totalPage)}">
										下一页&nbsp;&nbsp;尾页
									</c:when>
									<c:otherwise>
										<a href='page/showDispenses.action?page.start=${requestScope.page.nextPage}&page.limit=10' style="color:#fff">下一页</a>
										&nbsp;&nbsp;
										<a href='page/showDispenses.action?page.start=${requestScope.page.endPage}&page.limit=10' style="color:#fff">尾页</a>
									</c:otherwise>
								</c:choose>
							</nobr>
						</td>
					</tr>
				</tfoot>
				<tbody id="checkboxs">
					<c:forEach items="${requestScope.page.root}" var="c" varStatus="i">
						<tr ${i.count % 2 == 0 ? "class=odd":""}>
							<th>
								<c:choose>
									<c:when test="${c.rflag eq '0'}">
										未接收<input type="checkbox" uuid="${c.uuid}" flag="row" />
									</c:when>
									<c:when test="${c.rflag eq '1'}">
										已接收未打印
									</c:when>
									<c:when test="${c.rflag eq '2'}">
										已接收已打印
									</c:when>
								</c:choose>
							</th>
							<td>${c.stationName }</td>
							<td>${c.sectionName }</td>
							<td>${c.pressCode }</td>
							<td>${c.pressName }</td>
							<td>${c.pressTimeStep}</td>
							<td>${c.actNumber }</td>
						</tr>
					</c:forEach>
				</tbody>		
			</table>
		</div>
	</body>
</html>
