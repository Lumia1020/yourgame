<%@ page language="java" pageEncoding="UTF-8" import="util.DateUtil,java.util.Date;" isELIgnored="false" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	String now = DateUtil.toDateString(new Date());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head id=container name="container">
		<TITLE>FGT平台</TITLE>
		<META content="text/html; charset=utf-8" http-equiv=Content-Type>
		<META content=no-cache http-equiv=cache-control>
		<META content="mon, 22 jul 2002 11:12:01 GMT" http-equiv=expires>
		<META content=no-cache http-equiv=pragma>
		<link rel="stylesheet" type="text/css" href="css/firstebox.css" />
		<link rel="stylesheet" type="text/css" href="css/comm20100913l.css"/>
		<link rel="stylesheet" type="text/css" href="css/skin${syscount.skin}.css" class="skin"/>
		<link rel="stylesheet" type="text/css" href="js/jquery/validation/screen.css" />
		<style type=text/css>
			html{overflow:hidden;height:100%;margin:0;padding:0;}
			body{overflow:hidden;margin:0;padding:0;}
			.toggle a{font-weight:700;}
		</style>
		
		<%@ include file="/common/include/commonjs.jsp"%>
		<script type="text/javascript" src="js/main/main.js"></script>
	</head>
	<body class=frame_class>
		
		<div id="addDiv" style="display: none;">
			<div class="container attbg2 bd_upload">
				<h4>在你提交的表单中有如下错误.</h4>
				<ol>
					<li style="display: none"><label for="password" class="error"><b>原始密码</b>不允许为空,长度需1-20位之间</label></li>
					<li style="display: none"><label for="newPwd" class="error"><b>新密码</b>不允许为空,长度需6-20位之间</label></li>
					<li style="display: none"><label for="newPwds" class="error"><b>确定密码</b>不允许为空,长度需6-20位之间,并且要和<b>新密码</b>相配</label></li>
				</ol>
			</div>
			<div class="form-container">
				<c:choose>
					<c:when test="${loginParameter == 'sys'}">
						<form method="post" action="/swap/syscount.do?m=updateSysAccountPwd" id="form_update_pwd_sys">
							<fieldset>
								<legend>&nbsp;修改密码&nbsp;</legend>
								<div>
									<div>
										<label class="label">登录账号</label>
										<input type="text"  value="${syscount.name}" disabled="disabled" style="width:200px;"/>
							    		<input type="hidden" name="syscount.name" value="${syscount.name}" />
									</div>
									<div>
										<label for="password" class="label">原始密码<em>*</em></label>
										<input type="password" id="password" name="syscount.password" class="{required:true,rangelength:[1,20]}"  style="width:200px;"/>
									</div>
									<div>
										<label for="newPwd" class="label">新密码<em>*</em></label>
										<input type="password" id="newPwd" name="sys_newPwd" class="{required:true,rangelength:[6,20]}" style="width:200px;" />
									</div>
									<div>
										<label for="newPwds" class="label">确定密码<em>*</em></label>
										<input type="password" id="newPwds" name="sys_newPwds" class="{required:true,rangelength:[6,20]}"  style="width:200px;"/>
									</div>
								</div>
							</fieldset>
							<div class="buttonrow" style="text-align: right;">
								<input type="submit" value=" 保 存 " class="wd2 btn btn_add"/>
							</div>
						</form>
					</c:when>
					<c:when test="${loginParameter == 'member'}">
						<form method="post" action="/swap/member.do?m=updateMemberInfoPwd" id="form_update_pwd_member">
							<fieldset>
								<legend>&nbsp;会员修改密码&nbsp;</legend>
								<div>
									<div>
										<label class="label">登录账号</label>
										<input type="text"  value="${card}" disabled="disabled" style="width:200px;"/>
							    		<input type="hidden" name="member.cardNumber" value="${card}" />
									</div>
									<div>
										<label for="password" class="label">原始密码<em>*</em></label>
										<input type="password" id="password" name="member.password" class="{required:true,rangelength:[1,20]}"  style="width:200px;"/>
									</div>
									<div>
										<label for="newPwd" class="label">新密码<em>*</em></label>
										<input type="password" id="newPwd" name="member_newPwd" class="{required:true,rangelength:[6,20]}"  style="width:200px;"/>
									</div>
									<div>
										<label for="newPwds" class="label">确定密码<em>*</em></label>
										<input type="password" id="newPwds" name="member_newPwds" class="{required:true,rangelength:[6,20]}"  style="width:200px;"/>
									</div>
								</div>
							</fieldset>
							<div class="buttonrow" style="text-align: right;">
								<input type="submit" value=" 保 存 " class="wd2 btn btn_add"/>
							</div>
						</form>
					</c:when>
				</c:choose>
			</div>
		</div>
		
		
		<div id=topDataTd class=getuserdata>
			<div class="topdata">
				<div style="height: 66px;" class="topbg">
					<div id="SetInfo" class="setinfo addrtitle">
						<div class="right">
							<a class="toptitle a_change_password" href="#">修改密码</a><span class="addrtitle"> | </span>
							<a class="toptitle a_change_feedback" target="mainFrame" href="/swap/reserva.do?m=queryMemberReservation&paramet=feedback">意见反馈</a><span class="addrtitle"> | </span>
							<a class="toptitle" target="_blank" href="#">帮助中心</a><span class="addrtitle"> | </span>
							<a class="toptitle" target="_parent" href="syscount.do?m=exit">退出</a>
						</div>
						<div class="search_subject nowrap">
							<span class="addrtitle" style="font-size: 11px; padding-right: 8px;">&copy; 1997 - 2010 FGT Inc. All Rights Reserved | Support: FGT IT</span>
						</div>
					</div>
				</div>
				<div id="logotips" class="lgoo">
					<a title="邮箱首页" onfocus="this.blur();" target="mainFrame" href="#" class="imglogo pointer"> <img height="60" src="images/logo/logo_${syscount.skin}.gif" id="imglogo"> </a>
					<div class="switch">
						<c:if test="${loginParameter=='member'}">
							<div style="margin-top: 1px;" class="left">
								<b id="useralias">${memberName }</b>
								<span class="pointer" id="useraddrcontainer">&lt;<span>${card }</span>&gt;</span>
							</div>
						</c:if>
						<c:if test="${loginParameter=='sys'}">
							<div style="margin-top: 1px;" class="left">
								<b id="useralias">${syscount.chineseName }</b>
								<span class="pointer" id="useraddrcontainer">&lt;<span>${syscount.name }</span>&gt;</span>
								<br>
								<span class="addrtitle">
									<a target="mainFrame" href="syscount.do?m=homePage">平台首页</a>
								</span>
							</div>
						</c:if>
					</div>
				</div>
			</div>
		</DIV>
		<DIV id=sepLineTd class=topline_height>
			<DIV class=topline>
				<DIV id=imgLine class="toplineimg left"></DIV>
			</DIV>
		</DIV>
		<DIV class="fdbody bodybgbt"></DIV>
		<DIV id=leftPanel class=newskinbody>
			<DIV class="listbg listflow">
				<DIV id=folder class=folderDiv>
					<DIV id=OutFolder>
						<div id="SysFolderList"></div>
						<div id="ScrollFoder" style="overflow: auto;">
							<div style="height: 3px; overflow: hidden;"></div>
							<c:forEach items="${parentId}" var="pId">
								<ul class="fdul toggle">
									<li class="fs">
										<a onfocus="this.blur()" href="#">${pId.C_FUNCATION_NAME}</a>
										<img onfocus="this.blur()" class="fd_on" src="css/spacer3475l.gif">
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
							</c:forEach>
						</div>
					</DIV>
				</DIV>
			</DIV>
			<div id="navBottomTd" class="navbottom"></div>
		</DIV>
		<DIV id="mainFrameContainer">
			<script type="text/javascript">
				var parameter = "";
				if(${loginParameter=='member'}){
					parameter="/swap/reserva.do?m=queryMemberReservation&paramet=queryMemberReservationForMemberLogin";
				}if(${loginParameter=='sys'}){
					parameter = "center.jsp";
				}
				if ($.browser.mozilla) {
				    document.write('<iframe src='+parameter+' name="mainFrame" id="mainFrame" frameborder="no" scrolling="auto" hidefocus/><' + '/iframe>');
				} else {
				    document.write('<iframe src='+parameter+' name="mainFrame" id="mainFrame" frameborder="no" scrolling="yes" hidefocus /><' + '/iframe>');
				}
			</script>
		</DIV>
	</BODY>
</HTML>
