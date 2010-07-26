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
		<script type="text/javascript" src="js/user.js"></script>
		<!--[if IE]>
			<link rel="stylesheet" type="text/css" href="css/public_ie.css">
		<![endif]-->
		<!--[if !IE]><!-->
			<link rel="stylesheet" type="text/css" href="css/public_ff.css">
		<!--<![endif]--> 
		<style>
			fieldset{color:#333;border:#3A6180 1px solid;}
			legend{color:#3A6180;font-weight:800;background:#fff;}
			ul{list-style-type:none;margin:8px 0 4px;}
			li{margin-top:4px;}
		</style>
	</head>
	<body style='overflow:scroll;overflow-x:hidden' topmargin="5">
		<form action="page/showRoles.action?page.start=0&page.limit=10" id="user_form" method="post">
			<p>
				<label for="condition">角色名称</label>
				<input id="condition" type="text" size="30" name="page.params.condition" value="${requestScope.page.params.condition}"/>
				<input id="submit" type="submit" value="查询"/>
				<br/>
			</p>
		</form>
		<div id="itsthetable">
			<table>
				<thead>
					<tr>
						<th scope="col">角色名称</th>
						<th scope="col">权限菜单</th>
						<th scope="col">
							<div align="right">
								<button onclick="window.open('<%=basePath%>page/role_add.jsp','','toolbar=no,scrollbars=no,width=690,height=300')">添加角色</button>
							</div>
						</th>
					</tr>
				</thead>	
				<tfoot>
					<tr>
						<th scope="row"><nobr>总计${requestScope.page.totalProperty} 记录</nobr></th>
						<td align="right" colspan="2">
							<nobr>
								第 ${requestScope.page.currentPage}/${requestScope.page.totalPage} 页,
						      	<c:choose>
									<c:when test="${requestScope.page.start gt 1}">
										<a href='page/showRoles.action?page.start=0&page.limit=10' style="color:#fff">首页</a>
										&nbsp;&nbsp;
										<a href='page/showRoles.action?page.start=${requestScope.page.prePage}&page.limit=10' style="color:#fff">上一页</a>
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
										<a href='page/showRoles.action?page.start=${requestScope.page.nextPage}&page.limit=10' style="color:#fff">下一页</a>
										&nbsp;&nbsp;
										<a href='page/showRoles.action?page.start=${requestScope.page.endPage}&page.limit=10' style="color:#fff">尾页</a>
									</c:otherwise>
								</c:choose>
							</nobr>
						</td>
					</tr>
				</tfoot>
				<tbody>
					<c:forEach items="${requestScope.page.root}" var="c" varStatus="i">
						<tr id="${c.pid}_view" ${i.count % 2 == 0 ? "class=odd":""}>
							<th id="${c.pid}_view_rolename">${c.roleName }</th>
							<td id="${c.pid}_view_permissions">
								${c.station eq "true" ? "站段信息管理、":""}
								${c.seat eq "true" ? "台席信息管理、":""}
								${c.grid eq "true" ? "格扣信息管理、":""}
								${c.dept eq "true" ? "部门信息管理、":""}
								${c.role eq "true" ? "角色管理、":""}
								${c.user eq "true" ? "用户信息管理、":""}
								${c.verification eq "true" ? "查验管理、":""}
								${c.delivery eq "true" ? "投递管理":""}
							</td>
							<td align="right">
								<button role="${c.pid}" class="modify">修改</button>
								<button role="${c.pid}" class="delete">删除</button>
							</td>
						</tr>
						<tr class='view ${i.count % 2 == 0 ? "odd":""}' id="${c.pid}_modify">
							<th><input type="text" value="${c.roleName }" id="${c.pid}_input_rolename"/></th>
							<td>
								<fieldset> 
									<legend>投递管理</legend>
									<div style="float: left;">
										<ul> 
											<li><input field="delivery" type="checkbox" pid="${c.pid}" ${c.delivery eq "true" ? "checked='checked'":""} id="delivery${i.index}" value="投递接收"/>&nbsp;<label for="delivery${i.index}">投递接收</label></li> 
											<li><input field="verification" type="checkbox" pid="${c.pid}" ${c.verification eq "true" ? "checked='checked'":""} id="verification${i.index}" value="投递清单打印"/>&nbsp;<label for="verification${i.index}">投递清单打印</label></li> 
										</ul>
									</div>
								</fieldset>
								<fieldset> 
									<legend>信息管理</legend>
									<div style="float: left;">
										<ul> 
											<li><input field="station" type="checkbox" pid="${c.pid}" ${c.station eq "true" ? "checked='checked'":""} id="station${i.index}" value="站段信息管理"/>&nbsp;<label for="station${i.index}">站段信息管理</label></li> 
											<li><input field="seat" type="checkbox" pid="${c.pid}" ${c.seat eq "true" ? "checked='checked'":""} id="seat${i.index}" value="台席信息管理"/>&nbsp;<label for="seat${i.index}">台席信息管理</label></li> 
											<li><input field="grid" type="checkbox" pid="${c.pid}" ${c.grid eq "true" ? "checked='checked'":""} id="grid${i.index}" value="格口信息管理"/>&nbsp;<label for="grid${i.index}">格口信息管理</label></li> 
										</ul>
									</div>
									<div style="float: left;padding-left: 20px">
										<ul> 
											<li><input field="dept" type="checkbox" pid="${c.pid}" ${c.dept eq "true" ? "checked='checked'":""} id="dept${i.index}" value="部门信息管理"/>&nbsp;<label for="dept${i.index}">部门信息管理</label></li> 
											<li><input field="role" type="checkbox" pid="${c.pid}" ${c.role eq "true" ? "checked='checked'":""} id="role${i.index}" value="角色管理"/>&nbsp;<label for="role${i.index}">角色管理</label></li> 
											<li><input field="user" type="checkbox" pid="${c.pid}" ${c.user eq "true" ? "checked='checked'":""} id="user${i.index}" value="用户信息管理"/>&nbsp;<label for="user${i.index}">用户信息管理</label></li> 
										</ul>
									</div>
								</fieldset>
							</td>
							<td align="right">
								<button role="${c.pid}" class="update">确定</button>
							</td>
						</tr>
					</c:forEach>
				</tbody>		
			</table>
		</div>
	</body>
</html>
