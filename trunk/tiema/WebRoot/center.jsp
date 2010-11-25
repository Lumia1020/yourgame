<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
	<head>
		<base href="<%=basePath%>" />

		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="css/main.css">
		<link rel="stylesheet" type="text/css" href="css/skin<s:property value="#session.user.skin"/>.css" class="skin">
		<link rel="stylesheet" type="text/css" href="css/center.css">
		
		<script type="text/javascript" src="libraries/jquery/jquery-1.4.2.min.js"></script>
		<script type="text/javascript">
			var skin_id = <s:property value="#session.user.skin"/>;
			var basePath = '<%=basePath%>';
		</script>
		<script type="text/javascript" src="js/center.js"></script>
	</head>
	<body class="todaybody">
		<div class="todayMain" id="todayMain">
			<div class="updateInfo">
				<ul class="leftpanel" style="margin-bottom: 0pt; border: medium none; min-height: 110px; height: auto;" id="TodayInBox">
					<li class="welcomeinfo t_left1" id="TodayWelcome">
						<div class="">
							下午好，
							<a class="black" href="#"><s:property value="#session.user.chineseName" /></a>。
							<span id="accountType"></span><span id="spanGreeting" class="f_size normal addrtitle nowrap"></span>
						</div>
						<div class="oneheight"></div>
						<div class="level level_no">
						</div>
					</li>
					<li class="totalmail">
						<div class="sepinfo">
							<input type="button" class="ico_unreads ico_input" onfocus="this.blur()">
							会员：
							<a href="#">会员生日信息<b>(0)</b>
							</a>&nbsp;|&nbsp;
							<a href="#">当前会员信息<b>(290)</b>
							</a>&nbsp;|&nbsp;
							<a href="#">历史会员信息<b>(20)</b>
							</a>
						</div>
					</li>
					<li class="totalmail">
						<div class="sepinfo">
							<input type="button" class="ico_unreads ico_input" onfocus="this.blur()">
							会员：
							<a href="#">会员生日信息<b>(0)</b>
							</a>&nbsp;|&nbsp;
							<a href="#">当前会员信息<b>(290)</b>
							</a>&nbsp;|&nbsp;
							<a href="#">历史会员信息<b>(20)</b>
							</a>
						</div>
					</li>
					<li class="totalmail">
						<div class="sepinfo">
							<input type="button" class="ico_unreads ico_input" onfocus="this.blur()">
							会员：
							<a href="#">会员生日信息<b>(0)</b>
							</a>&nbsp;|&nbsp;
							<a href="#">当前会员信息<b>(290)</b>
							</a>&nbsp;|&nbsp;
							<a href="#">历史会员信息<b>(20)</b>
							</a>
						</div>
					</li>
				</ul>
			</div>
		</div>
		<div class="clr" style="height: 10px;"></div>
		<div style="margin-right: 10px;" class="todayMain" id="todayMain2">
			<div class="bd tabpanel"  style="height: auto">
				<div id="rightPanelTitle_normal" class="tipstitle_title settingtable bold bd todayInfoPanelTab">
					<div style="position: absolute;">
						<div class="tab_list bd pointer">
							快捷方式
						</div>
						<div class="tab_list bd actived">
							个性换肤
						</div>
					</div>
				</div>
				<div class="tab_content" style="display: none; height: 350px;">
				</div>
				<div class="tab_content" style="overflow:hidden;">
					<div style="height: 16px; overflow: hidden;background: none;" class="settingtable"></div>
					<input type="hidden" id="tmplstyle" value="<s:property value="#session.user.skin"/>">
					<div class="settingtable" style="background: none">
						<div style="margin-left: 31px; height: 26px;">
							<span class="addrtitle">给 <s:property value="#session.user.chineseName"/> 的帐户设置皮肤：</span>
						</div>
						<div class="skinsetting" style="overflow-x:hidden;height:283px;overflow:auto;background: none;">
							<div id="divlayer_24"  style="margin: 1px;">
								<table cellspacing="0" cellpadding="0" border="0" class="pointer clr" onclick="chooseStyle('24');">
									<tbody>
										<tr>
											<td>
												<img class="face24" src="images/spacer3475l.gif">
											</td>
										</tr>
										<tr>
											<td  id="label_24">
												极简
											</td>
										</tr>
									</tbody>
								</table>
							</div>
							<div id="divlayer_22" style="margin: 1px;" >
								<table cellspacing="0" cellpadding="0" border="0" class="pointer clr" onclick="chooseStyle('22');">
									<tbody>
										<tr>
											<td>
												<img class="face22" src="images/spacer3475l.gif">
											</td>
										</tr>
										<tr>
											<td  id="label_22">
												iSkin
											</td>
										</tr>
									</tbody>
								</table>
							</div>
							<div id="divlayer_210">
								<table width="84" cellspacing="0" cellpadding="0" border="0" class="pointer" onclick="chooseStyle('210');">
									<tbody>
										<tr>
											<td>
												<img class="face210" src="images/spacer3475l.gif">
											</td>
										</tr>
										<tr>
											<td style="font-weight: normal;" id="label_210">
												深蓝
											</td>
										</tr>
									</tbody>
								</table>
							</div>
							<div class="clr">
							&nbsp;
						</div>
					</div>
					<div style="border-top: 1px solid rgb(255, 255, 255);line-height: 22px;border-bottom: none;" class="toolbg toolbgline barspace4">
				 		点击图片即可直接切换皮肤，切换后，设置自动保存。
					</div>
				</div>
			</div>
		</div>
	</body>
</html>