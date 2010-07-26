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
		<script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>
		<script type="text/javascript" src="js/station.js"></script>
		<!--[if IE]>
			<link rel="stylesheet" type="text/css" href="css/public_ie.css">
		<![endif]-->
		
		<!--[if !IE]><!-->
			<link rel="stylesheet" type="text/css" href="css/public_ff.css">
		<!--<![endif]--> 
	</head>
	<body style='overflow:scroll;overflow-x:hidden' topmargin="5">
		<form action="page/showStations.action?page.start=0&page.limit=10" method="post">
			<p>
				<label for="condition">站段信息</label>
				<input id="condition" type="text" size="30" name="page.params.condition" value="${requestScope.page.params.condition}"/>
				<input type="submit" value="查询"/>
				<br/>
			</p>
		</form>
		<div id="itsthetable">
			<table>
				<thead>
					<tr>
						<th scope="col">站名</th>
						<th scope="col">段名</th>
						<th scope="col" >
							<div align="right">
								<button onclick="window.open('<%=basePath%>page/station_add.jsp','','toolbar=no,scrollbars=no,width=600,height=150')">添加站段</button>
							</div>
						</th>
					</tr>
				</thead>	
				<tfoot>
					<tr>
						<th scope="row">总计 ${requestScope.page.totalProperty} 记录</th>
						<td align="right" colspan="2">
							<nobr>
								第 ${requestScope.page.currentPage}/${requestScope.page.totalPage} 页,
						      	<c:choose>
									<c:when test="${requestScope.page.start gt 1}">
										<a href='page/showStations.action?page.start=0&page.limit=10' style="color:#fff">首页</a>
										&nbsp;&nbsp;
										<a href='page/showStations.action?page.start=${requestScope.page.prePage}&page.limit=10' style="color:#fff">上一页</a>
									</c:when>
									<c:otherwise>
										首页&nbsp;&nbsp;上一页
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test="${(requestScope.page.currentPage eq requestScope.page.totalPage)}">
										下一页&nbsp;&nbsp;尾页
									</c:when>
									<c:otherwise><a href='page/showStations.action?page.start=${requestScope.page.nextPage}&page.limit=10' style="color:#fff">下一页</a>
										&nbsp;&nbsp;
										<a href='page/showStations.action?page.start=${requestScope.page.endPage}&page.limit=10' style="color:#fff">尾页</a>
									</c:otherwise>
								</c:choose>
							</nobr>
						</td>
					</tr>
				</tfoot>
				<tbody>
					<c:forEach items="${requestScope.page.root}" var="c" varStatus="i">
						<tr id="${c.uuid}_view" ${i.count % 2 == 0 ? "class=odd":""}>
							<th id="${c.uuid}_view_stationname">${c.stationName }</th>
							<td id="${c.uuid}_view_sectionname">${c.sectionName }</td>
							<td align="right">
								<button station="${c.uuid}" class="modify">修改</button>
								<button station="${c.uuid}" class="delete">删除</button>
							</td>
						</tr>
						<tr class='view ${i.count % 2 == 0 ? "odd":""}' id="${c.uuid}_modify">
							<th><input type="text" value="${c.stationName }" id="${c.uuid}_input_stationname"/></th>
							<td><input type="text" value="${c.sectionName }" id="${c.uuid}_input_sectionname"/></td>
							<td align="right">
								<button station="${c.uuid}" class="update">确定</button>
								<button station="${c.uuid}" class="cancel">取消</button>
							</td>
						</tr>
					</c:forEach>
				</tbody>		
			</table>
		</div>
	</body>
</html>
