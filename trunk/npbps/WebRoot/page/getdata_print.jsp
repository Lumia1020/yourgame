<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<jsp:directive.page import="java.util.Date"/>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	request.setAttribute("now",new Date());
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>打印</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="css/print.css">
		<script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>
		<script type="text/javascript" src="js/jquery.jPrintArea.js"></script>
		<script text="text/javascript">
			var uuids = "<c:forEach items='${requestScope.page.root3}' var='g'><c:if test='${g.rflag ==1}'>'${g.uuid}',</c:if></c:forEach>";
			uuids = uuids.substring(0,uuids.length -1);
			function printGetData(){
				jQuery.jPrintArea('#itsthetable');
				$.post("updateFlags.action", {
		            'page.params.uuids': uuids,
		            'page.params.value':2
		        },
		        function (data) {
		        },'json');
			}
			
			$(function() {
				$('#start_date').datepicker();
				$('#end_date').datepicker();
			});
		</script>		
	</head>
	<body>
		<input type="button" value="打印" onclick="printGetData()"/>
		<div id="itsthetable">
			<table class="yd_form" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td colspan="10">
						<div style="padding:10px 0;">
							<div style="float: left">[${sessionScope.dept.deptName }]投递站非邮发投递清单 </div> 
							<div style="padding:0 20px;float:left">打印日期:<fmt:formatDate value="${now}" pattern="yyyy年MM月dd日HH点mm分ss秒"/></div>
							<div>打印人:${sessionScope.user.userName}</div>
						</div>
					</td>
				</tr>
				<c:forEach items="${requestScope.page.root2}" var="g">
					<tr>
						<td colspan="10">
							${g }段
						</td>
					</tr>
					<tr>
						<th class="first th" scope="col">站名</th>
						<th class="th" scope="col">刊号</th>
						<th class="th" scope="col">刊名</th>
						<th class="th" scope="col">刊期</th>
						<th class="th" scope="col">份数</th>
						<th class="th" scope="col">单位</th>
						<th class="th" scope="col">地址</th>
						<th class="th" scope="col">邮编</th>
						<th class="th" scope="col">订起日期</th>
						<th class="th" scope="col">订止日期</th>
					</tr>
					<c:forEach items="${requestScope.page.root}" var="c" varStatus="i">
						<c:if test="${g eq c.sectionName}">
							<c:set var="count" scope="page" value="${pageScope.count + 1}"/>    
							<tr ${i.count % 2 == 0 ? "class=odd":""}>
								<td class="first td"><nobr>${c.stationName }&nbsp;</nobr></td>
								<td class="td">${c.pressCode}&nbsp;</td>
								<td class="td">${c.pressName }&nbsp;</td>
								<td class="td">${c.pressInfo }&nbsp;</td>
								<td class="td">${c.outNumber }&nbsp;</td>
								<td class="td">${c.outDepart }&nbsp;</td>
								<td class="td">${c.outAddress }&nbsp;</td>
								<td class="td">${c.outPostCode }&nbsp;</td>
								<td class="td">${c.startDate}&nbsp;</td>
								<td class="td">${c.endDate }&nbsp;</td>
							</tr>
						</c:if>
					</c:forEach>
					<tr><td colspan="10">合计:${pageScope.count }份</td></tr>
					<c:set var="count" scope="page" value="0"/>  
					<tr><td colspan="10">&nbsp;</td></tr>
					<tr><td colspan="10">&nbsp;</td></tr>
					<tr><td colspan="10">&nbsp;</td></tr>
					<tr><td colspan="10">&nbsp;</td></tr>
					<tr><td colspan="10">&nbsp;</td></tr>
				</c:forEach>
			</table>
		</div>
	</body>
</html>
