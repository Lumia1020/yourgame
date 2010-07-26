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
		<script type="text/javascript" src="js/grid.js"></script>
		<!--[if IE]>
			<link rel="stylesheet" type="text/css" href="css/public_ie.css">
		<![endif]-->
		<!--[if !IE]><!-->
			<link rel="stylesheet" type="text/css" href="css/public_ff.css">
		<!--<![endif]--> 
	</head>
	<body style='overflow:scroll;overflow-x:hidden' topmargin="5">
		<form action="page/showGridsPage.action?page.start=0&page.limit=10" method="post">
			<p>
				<label for="condition">片区名称</label>
				<input id="condition" type="text" size="30" name="page.params.condition" value="${requestScope.page.params.condition}"/>
				<input type="submit" value="查询"/>
				<br/>
			</p>
		</form>
		<div id="itsthetable">
			<table>
				<thead>
					<tr>
						<th scope="col"><nobr>片区名称</nobr></th>
						<th scope="row">站名</th>
						<th scope="row">段名</th>
						<th scope="row">台席</th>
						<th scope="col">
							<div align="right">
								<button onclick="window.open('<%=basePath%>page/addGridPage.action','','toolbar=yes,scrollbars=yes,width=600,height=200')">添加格口</button>
							</div>
						</th>
					</tr>
				</thead>	
				<tfoot>
					<tr>
						<th scope="row">总计</th>
						<td>${requestScope.page.totalProperty} 记录</td>
						<td align="right" colspan="3">
							<nobr>
								第 ${requestScope.page.currentPage}/${requestScope.page.totalPage} 页,
						      	<c:choose>
									<c:when test="${requestScope.page.start gt 1}">
										<a href='page/showGridsPage.action?page.start=0&page.limit=10' style="color:#fff">首页</a>
										&nbsp;&nbsp;
										<a href='page/showGridsPage.action?page.start=${requestScope.page.prePage}&page.limit=10' style="color:#fff">上一页</a>
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
										<a href='page/showGridsPage.action?page.start=${requestScope.page.nextPage}&page.limit=10' style="color:#fff">下一页</a>
										&nbsp;&nbsp;
										<a href='page/showGridsPage.action?page.start=${requestScope.page.endPage}&page.limit=10' style="color:#fff">尾页</a>
									</c:otherwise>
								</c:choose>
							</nobr>
						</td>
					</tr>
				</tfoot>
				<tbody>
					<c:forEach items="${requestScope.page.root}" var="c" varStatus="i">
						<tr id="${c.uuid}_view" ${i.count % 2 == 0 ? "class=odd":""}>
							<th id="${c.uuid}_view_area">${c.area }</th>
							<td id="${c.uuid}_view_stationname">
								<c:forEach items="${requestScope.stationPage.root}" var="station">
									<c:if test="${station[0] eq c.stationId}">${station[1]}</c:if>
								</c:forEach>
							</td>
							<td id="${c.uuid}_view_sectionname">
								<c:forEach items="${requestScope.sectionPage.root}" var="station">
									<c:if test="${station[0] eq c.sectionId}">${station[1]}</c:if>
								</c:forEach>
							</td>
							<td id="${c.uuid}_view_seatname">
								<c:forEach items="${requestScope.seatPage.root}" var="d">
									<c:if test="${d.seatId eq c.seatId}">${d.seatName}</c:if>
								</c:forEach>
							</td>
							<td align="right">
								<nobr>
								<button grid="${c.uuid}" class="modify">修改</button>
								<button grid="${c.uuid}" class="delete">删除</button>
								</nobr>
							</td>
						</tr>
						<tr class='view ${i.count % 2 == 0 ? "odd":""}' id="${c.uuid}_modify">
							<th>
								<select id="${c.uuid}_select_area">
									<option value="市区" ${c.area eq "市区" ? "selected='selected'":"" }>市区</option>
									<option value="郊区" ${c.area eq "郊区" ? "selected='selected'":"" }>郊区</option>
								</select>
							</th>
							<td>
								<select id="${c.uuid}_select_stationid">
									<c:forEach items="${requestScope.stationPage.root}" var="station">
										<option value="${station[0]}" ${station[0] eq c.stationId ? "selected='selected'":"" }>${station[1]}</option>
									</c:forEach>
								</select>
							</td>
							<td>
								<select id="${c.uuid}_select_sectionid">
									<c:forEach items="${requestScope.sectionPage.root}" var="station">
										<option value="${station[0]}" ${station[0] eq c.sectionId ? "selected='selected'":"" }>${station[1]}</option>
									</c:forEach>
								</select>
							</td>
							<td>
								<select id="${c.uuid}_select_seatid">
									<c:forEach items="${requestScope.seatPage.root}" var="d">
										<option value="${d.seatId}" ${d.seatId eq c.seatId ? "selected='selected'":"" }>${d.seatName}</option>
									</c:forEach>
								</select>
							</td>
							<td align="right">
								<nobr>
								<button grid="${c.uuid}" class="update">确定</button>
								<button grid="${c.uuid}" class="cancel">取消</button>
								</nobr>
							</td>
						</tr>
					</c:forEach>
				</tbody>		
			</table>
		</div>
	</body>
</html>
