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
		<title>添加台席</title>
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
			button{width: 40px}
			select{height:100px;min-width:100px}
		</style>
		<script src="js/jquery-1.3.2.min.js"></script>
		<script src="js/listtolist.js"></script>
		<script src="js/seat.js"></script>
	</head>
	<body>
		<div class="left" style="width: 93%;margin: 20px 30px 10px 30px;">
			<form id="addSeatForm" name="addSeatForm" method="get" onsubmit="return false;">
				<table width="600px" id="yd_form" border="0" cellpadding="4" cellspacing="0">
					<tr>
						<td>台席名称</td>
						<td><input type="text" id="seatName" maxlength="20"/><em>*</em></td>
					</tr>
					<tr>
						<td>含有的站</td>
						<td>
							<div style="min-width: 300px">
							  	<div style="float: left">
							  		<div style="float: clear">可选站</div>
							  		<div>	
								  		<select id="left" multiple="multiple" class="select_left">
								  			<c:forEach items="${requestScope.page.root}" var="c" varStatus="i">
								  				<option value="${c[0]}">${c[1]}</option>
								  			</c:forEach>
									  	</select>
							  		</div>
							  	</div>
							  	<div style="float:left;padding: 20px 10px 0 10px;">
							  		<button sel="left" class="btn_right">
							  			&gt;
							  		</button>
							  		<br>
							  		<button sel="left" class="btn_right_all">
							  			&gt;&gt;
							  		</button>
							  		<br>
							  		<button sel="right" class="btn_left">
							  			&lt;
							  		</button>
							  		<br>
							  		<button sel="right" class="btn_left_all">
							  			&lt;&lt;
							  		</button>
							  	</div>
							  	<div style="float: left">
							  		<div style="float: clear">已选站</div>
							  		<div>	
								  		<select id="right" multiple="multiple" class="select_right">
									  	</select>
							  		</div>
							  	</div>
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="4" style="padding:8px 0px 10px 10px" align="center">
							<input id="seat_add" type="button" value="添  加" style="width:70px"/>
							<input type="reset" value="重  置" style="width:70px"/>
						</td>
					</tr>	
				</table>
			</form>
		</div>
	</body>
</html>
