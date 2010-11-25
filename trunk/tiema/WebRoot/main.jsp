<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML>
<html>
	<head id="container" name="container">
		<base href="<%=basePath%>" />
		
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="mon, 22 jul 2002 11:12:01 GMT">
		<meta http-equiv="pragma" content="no-cache">
		
		<title>铁马高尔夫预定信息系统</title>
		<link rel="stylesheet" type="text/css" href="css/main.css">
		<link rel="stylesheet" type="text/css" href="css/skin<s:property value="#session.user.skin"/>.css" class="skin">
		
		<%@ include file="/commons/common_css.jsp"%>
		<%@ include file="/commons/common_js.jsp"%>
		
		<script language="javascript" src="js/main.js"></script>
		<script>
		
			$(function(){
				window.frames['mainFrame'].document.location = $('#test').attr('href');
			});
		</script>
		<style type="text/css">
		html,body {
			padding: 0;
			margin: 0;
			overflow: auto;
			overflow-x: hidden;
		}
		
		html {
			height: 100%;
		}
		</style>
	</head>
	<body class="frame_class">
		<div id=topDataTd class=getuserdata>
			<div class="topdata">
				<div style="height: 66px;" class="topbg">
					<div id="SetInfo" class="setinfo addrtitle">
						<div class="right">
							<a class="toptitle a_change_password" href="#">修改密码</a><span class="addrtitle"> | </span>
							<a class="toptitle a_change_feedback" target="mainFrame" href="#">意见反馈</a><span class="addrtitle"> | </span>
							<a class="toptitle" target="_blank" href="#">帮助中心</a><span class="addrtitle"> | </span>
							<a class="toptitle" target="_parent" href="default/user/user!logout">退出</a>
						</div>
						<div class="search_subject nowrap">
							<span class="addrtitle" style="font-size: 11px; padding-right: 8px;">&copy; 1997 - 2010 FGT Inc. All Rights Reserved | Support: FGT IT</span>
						</div>
					</div>
				</div>
				<div id="logotips" class="lgoo">
					<a onfocus="this.blur();" href="javascript:void(0);" class="imglogo pointer"> <img height="60" src="images/logo/logo_<s:property value="#session.user.skin"/>.gif" id="imglogo"> </a>
					<div class="switch">
						<div style="margin-top: 1px;" class="left">
							<b id="useralias"><s:property value="#session.user.chineseName"/></b>
							<span class="pointer" id="useraddrcontainer">&lt;<span><s:property value="#session.user.chineseName"/></span>&gt;</span>
							<br>
							<span class="addrtitle">
								<a href="javascript:void(0);" id="hide_leftPanel">隐藏导航菜单</a>
								<a target="mainFrame" href="default/user/center">平台首页</a>
							</span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="topline_height" id="sepLineTd">
			<div class="topline">
				<div class="toplineimg left" id="imgLine"></div>
			</div>
		</div>
		<div class="fdbody bodybgbt"></div>
		<div class="newskinbody" id="leftPanel">
			<div id="navMidBar" class="listbg listflow">
				<div style="overflow: hidden;" id="folder" class="folderDiv">
					<div id="OutFolder">
						<div id="SysFolderList">
							<div style="height: 3px; overflow: hidden;"></div>
							<ul class="fdul toggle">
								<li class="fs">
									<a href="javascript:void(0);" onfocus="this.blur()"><b>预定管理</b></a>
									<img src="images/spacer3475l.gif" class="fd_on" onfocus="this.blur()"/>
								</li>
							</ul>
							<ul class="fdul" style="overflow: hidden; height: auto; display: none;">
								<li class="fs">
									<a href="jsp/general_manager_report/member_all_distribution.jsp?a=a&amp;functionId=33" target="mainFrame" onfocus="this.blur()" class="fdlist_width">
										<div class="txtflow fdwidthmax">预定管理</div> 
									</a>
								</li>
								<li class="fs">
									<a href="jsp/general_manager_report/member_all_distribution.jsp?a=a&amp;functionId=33" target="mainFrame" onfocus="this.blur()" class="fdlist_width">
										<div class="txtflow fdwidthmax">催款提醒</div> 
									</a>
								</li>
								<li class="fs">
									<a href="jsp/general_manager_report/member_all_distribution.jsp?a=a&amp;functionId=33" target="mainFrame" onfocus="this.blur()" class="fdlist_width">
										<div class="txtflow fdwidthmax">中介公司价格查询</div> 
									</a>
								</li>
							</ul>
							<ul class="fdul toggle">
								<li class="fs">
									<a href="javascript:void(0);" onfocus="this.blur()"><b>财务管理</b></a>
									<img src="images/spacer3475l.gif" class="fd_on" onfocus="this.blur()">
								</li>
							</ul>
							<ul class="fdul" style="overflow: hidden; height: auto; display: none;">
								<li class="fs">
									<a href="jsp/general_manager_report/member_all_distribution.jsp?a=a&amp;functionId=33" target="mainFrame" onfocus="this.blur()" class="fdlist_width">
										<div class="txtflow fdwidthmax">付款单管理</div> 
									</a>
								</li>
							</ul>
							<ul class="fdul toggle">
								<li class="fs">
									<a href="javascript:void(0);" onfocus="this.blur()"><b>客户管理</b></a>
									<img src="images/spacer3475l.gif" class="fd_off" onfocus="this.blur()">
								</li>
							</ul>
							<ul class="fdul" style="overflow: hidden; height: auto; display: block;">
								<li class="fs">
									<a href="default/membership-category/customer!findAll" target="mainFrame" onfocus="this.blur()" class="fdlist_width">
										<div class="txtflow fdwidthmax">客户资料管理</div> 
									</a>
								</li>
							</ul>
							<ul class="fdul toggle">
								<li class="fs">
									<a href="javascript:void(0);" onfocus="this.blur()"><b>系统管理</b></a>
									<img src="images/spacer3475l.gif" class="fd_off" onfocus="this.blur()">
								</li>
							</ul>
							<ul class="fdul" style="overflow: hidden; height: auto; display: block;">
								<li class="fs">
									<a href="default/role/user-manage" target="mainFrame" onfocus="this.blur()" class="fdlist_width" >
										<div class="txtflow fdwidthmax">用户设置</div> 
									</a>
								</li>
								<li class="fs">
									<a href="default/role/role" target="mainFrame" onfocus="this.blur()" class="fdlist_width" >
										<div class="txtflow fdwidthmax">角色设置</div> 
									</a>
								</li>
								<li class="fs">
									<a href="default/golf-club/golf-club" target="mainFrame" onfocus="this.blur()" class="fdlist_width">
										<div class="txtflow fdwidthmax">高尔夫俱乐部设置</div> 
									</a>
								</li>
								<li class="fs">
									<a href="default/agency-company/agency-company" target="mainFrame" onfocus="this.blur()" class="fdlist_width" >
										<div class="txtflow fdwidthmax">中介公司设置</div> 
									</a>
								</li>
								<li class="fs">
									<a href="default/seller/seller" target="mainFrame" onfocus="this.blur()" class="fdlist_width" >
										<div class="txtflow fdwidthmax">销售员设置</div> 
									</a>
								</li>
								<li class="fs">
									<a href="default/membership-category/membership-category" target="mainFrame" onfocus="this.blur()" class="fdlist_width">
										<div class="txtflow fdwidthmax">会籍种类设置</div> 
									</a>
								</li>
								<li class="fs">
									<a href="default/payment-category/payment-category" target="mainFrame" onfocus="this.blur()" class="fdlist_width" id="test">
										<div class="txtflow fdwidthmax">付款方式设置</div> 
									</a>
								</li>
							</ul>
							<!-- 
							<c:forEach items="${parentId}" var="pId">
								<ul class="fdul toggle">
									<li class="fs">
										<a onfocus="this.blur()" href="#">${pId.C_FUNCATION_NAME}</a>
										<img onfocus="this.blur()" class="fd_on" src="images/spacer3475l.gif">
									</li>
								</ul>
								<ul style="overflow: hidden; height: auto; display: none;" class="fdul">
									<c:forEach items="${parent}" var="p">
										<c:if test="${pId.C_FUNCTION_ID==p.C_FUNCTION_PARENT_ID}">
											<li class="fs">
												<a class="fdlist_width" onfocus="this.blur()" target="mainFrame" href="${p.C_JSP_FILE}&functionId=${p.C_FUNCTION_ID}">
													<div class="txtflow fdwidthmax">${p.C_FUNCATION_NAME}</div> 
												</a>
											</li>
										</c:if>
									</c:forEach>
								</ul>
							</c:forEach> -->
						</div>
						<div id="ScrollFoder" style="overflow: auto;">
						</div>
					</div>
				</div>
			</div>
			<div id="navBottomTd" class="navbottom"></div>
		</div>
		<div id="mainFrameContainer">
			<iframe src="center.jsp" name="mainFrame" id="mainFrame" scrolling="auto" frameborder="no"></iframe>
		</div>
		<iframe name="leftFrame" id="leftFrame" src="javascript:'';" style="display: none;"></iframe>
		<script type="text/javascript">
			(window.onresize = resizeFolderList)();
		</script>
	</body>
</html>