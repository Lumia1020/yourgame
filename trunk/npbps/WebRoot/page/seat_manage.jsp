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
		<style>
			select{height:100px;min-width:100px}
		</style>
		<script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>
		<script type="text/javascript" src="js/listtolist.js"></script>
		<script type="text/javascript" src="js/seat.js"></script>
	</head>
	<body style='overflow:scroll;overflow-x:hidden' topmargin="5">
		<form action="page/showSeats.action?page.start=0&page.limit=10" method="post">
			<p>
				<label for="condition">台席信息</label>
				<input id="condition" type="text" size="30" name="page.params.condition" value="${requestScope.page.params.condition}"/>
				<input type="submit" value="查询"/>
				<br/>
			</p>
		</form>
		<div id="itsthetable">
			<table>
				<thead>
					<tr>
						<th scope="col">台席名称</th>
						<th scope="col">包含站</th>
						<th scope="col">
							<div align="right">
								<nobr>
									<button onclick="window.open('<%=basePath%>page/addSeatPage.action?page.limit=0','','toolbar=no,scrollbars=no,width=550,height=300')">添加台席</button>
								</nobr>
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
										<a href='page/showSeats.action?page.start=0&page.limit=10' style="color:#fff">首页</a>
										&nbsp;&nbsp;
										<a href='page/showSeats.action?page.start=${requestScope.page.prePage}&page.limit=10' style="color:#fff">上一页</a>
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
										<a href='page/showSeats.action?page.start=${requestScope.page.nextPage}&page.limit=10' style="color:#fff">下一页</a>
										&nbsp;&nbsp;
										<a href='page/showSeats.action?page.start=${requestScope.page.endPage}&page.limit=10' style="color:#fff">尾页</a>
									</c:otherwise>
								</c:choose>
							</nobr>
						</td>
					</tr>
				</tfoot>
				<tbody>
					<c:forEach items="${requestScope.page.root}" var="c" varStatus="i">
						<tr id="${c.uuid}_view" ${i.count % 2 == 0 ? "class=odd":""}>
							<th id="${c.uuid}_view_seatname"><nobr>${c.seatName }</nobr></th>
							<td id="${c.uuid}_view_stationname"> 
								<c:forTokens items="${c.stationId}" delims=".'" var="stationId" varStatus="states">
						  			<c:forEach items="${requestScope.stationPage.root}" var="s">
						  				<c:if test="${stationId eq s[0]}">
						  					${s[1]}<c:if test="${!states.last}">、</c:if>
						  				</c:if>
						  			</c:forEach>
						  		</c:forTokens>
							</td>
							<td align="right">
								<button seat="${c.uuid}" class="modify">修改</button>
								<button seat="${c.uuid}" class="delete">删除</button>
							</td>
						</tr>
						<tr class='view ${i.count % 2 == 0 ? "odd":""}' id="${c.uuid}_modify">
							<th><input type="text" value="${c.seatName }" id="${c.uuid}_input_seatname"/></th>
							<td>
							  	<div style="float: left">
							  		<div style="float: clear">可选站</div>
							  		<div>	
								  		<select id="${c.uuid}_left" multiple="multiple" class="select_left">
								  			<c:forEach items="${requestScope.stationPage.root}" var="s">
									  			<option value="${s[0]}">${s[1]}</option>
								  			</c:forEach>
									  	</select>
							  		</div>
							  	</div>
							  	<div style="float:left;padding: 20px 10px 0 10px;">
							  		<button sel="${c.uuid}_left" class="btn_right" style="width: 40px">
							  			&gt;
							  		</button>
							  		<br>
							  		<button sel="${c.uuid}_left" class="btn_right_all" style="width: 40px">
							  			&gt;&gt;
							  		</button>
							  		<br>
							  		<button sel="${c.uuid}_right" class="btn_left" style="width: 40px">
							  			&lt;
							  		</button>
							  		<br>
							  		<button sel="${c.uuid}_right" class="btn_left_all" style="width: 40px">
							  			&lt;&lt;
							  		</button>
							  	</div>
							  	<div style="float: left">
							  		<div style="float: clear">已选站</div>
							  		<div>	
								  		<select id="${c.uuid}_right" multiple="multiple" class="select_right">
								  			<c:forTokens items="${c.stationId}" delims=".'" var="stationId">
									  			<c:forEach items="${requestScope.stationPage.root}" var="s">
									  				<c:if test="${stationId eq s[0]}">
									  					<option value="${s[0]}">${s[1]}</option>
									  				</c:if>
									  			</c:forEach>
									  		</c:forTokens>
									  	</select>
							  		</div>
							  	</div>
							</td>
							<td align="right">
								<button seat="${c.uuid}" class="update">确定</button>
								<button seat="${c.uuid}" class="cancel">取消</button>
							</td>
						</tr>
					</c:forEach>
				</tbody>		
			</table>
		</div>
	</body>
</html>
