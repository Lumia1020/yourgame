<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/include/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>FGT</title>
		<meta content="text/html; charset=UTF-8" http-equiv="Content-Type">
		<link rel=stylesheet type=text/css href="css/comm20100913l.css">
		<link rel="stylesheet" type="text/css" href="css/skin${syscount.skin}.css" class="skin">
		<link rel=stylesheet type=text/css href="css/setting0913l.css">
		<link rel=stylesheet type=text/css href="css/center.css">
		
		<link type="text/css" href="css/jquery/ui/demos.css" rel="stylesheet" />
		<link type="text/css" href="css/jquery/ui/jquery.ui.core.css" rel="stylesheet" />
		<link type="text/css" href="css/jquery/ui/jquery.ui.theme.css" rel="stylesheet" />
		
		<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
		<script type="text/javascript" src="js/jquery/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="js/jquery/ui/jquery.ui.widget.js"></script>
		<script type="text/javascript" src="js/jquery/ui/jquery.ui.mouse.js"></script>
		<script type="text/javascript" src="js/jquery/ui/jquery.ui.draggable.js"></script>
		<script type="text/javascript" src="js/jquery/ui/jquery.ui.droppable.js"></script>
		<script type="text/javascript" src="js/jquery/ui/jquery.ui.resizable.js"></script>
		<script type="text/javascript" src="js/jquery/ui/jquery.ui.dialog.js"></script>
		<script type="text/javascript" src="js/jquery/plugin/jquery.easing.1.3.js"></script>
		<script type="text/javascript">var skin_id = ${syscount.skin}; </script>
		<script type="text/javascript" src="js/center.js"></script>
	</head>
	<body class="todaybody"> 
		<div id="todayMain" class="todayMain">
			<div class="updateInfo">
				<br>
				<ul id="TodayInBox" style="margin-bottom: 0pt; border: medium none;" class="leftpanel">
					<li class="welcomeinfo t_left1" id="TodayWelcome">
						<div class="">
							下午好，
							<a class="black" href="#">${syscount.chineseName}</a>。
							<span id="accountType"></span><span id="spanGreeting" class="f_size normal addrtitle nowrap"></span>
						</div>
						<div class="oneheight"></div>
						<div class="level level_no">
						</div>
					</li>
					<li class="totalmail" >
						<div class="sepinfo">
							<input type="button" onfocus="this.blur()" class="ico_unreads ico_input">
							会员：
							<a href="member.do?m=queryMemberInfo&paramet=memberList&templateType=1&birthStatistics=1&functionId=5">会员生日信息<b>(${birthdayStatistics})</b></a>&nbsp;|&nbsp;
							<a href="member.do?m=queryMemberInfo&paramet=memberList&templateType=1&functionId=5">当前会员信息<b>(${memberStatistics })</b></a>&nbsp;|&nbsp;
							<a href="member.do?m=queryMemberInfo&paramet=historyMemberList&functionId=5">历史会员信息<b>(${historyMemberStatistics })</b></a> 
						</div>
					</li>
					<li class="totalmail" >
						<div class="sepinfo">
							<input type="button" onfocus="this.blur()" class="ico_unreads ico_input">
							订单：
							<a href="reserva.do?m=queryMemberReservation&status=1&paramet=queryMemberReservation&functionId=9">待审核订单信息<b>(${reservationStatistics1 })</b></a>&nbsp;|&nbsp;
							<a href="reserva.do?m=queryMemberReservation&status=2&paramet=queryMemberReservation&functionId=9">审核通过订单信息<b>(${reservationStatistics2 })</b></a>&nbsp;|&nbsp;
							<a href="reserva.do?m=queryMemberReservation&status=3&paramet=queryMemberReservation&functionId=9">审核未通过订单信息<b>(${reservationStatistics3 })</b></a>&nbsp;|&nbsp;
							<a href="reserva.do?m=queryMemberReservation&status=4&paramet=queryMemberReservation&functionId=9">取消订单信息<b>(${reservationStatistics4 })</b></a> 
						</div>
					</li>
					<li class="totalmail">
						<div class="sepinfo">
							<input type="button" onfocus="this.blur()" class="ico_unreads ico_input">
							其他：
							<a href="reserva.do?m=queryMemberReservation&status=5&paramet=queryMemberReservation&functionId=9">确认来场订单信息<b>(${reservationStatistics5 })</b></a>
						</div>
						<div style="height: 15px;" class="oneheight"></div>
					</li>
				</ul>
			</div>
		</div>
		<div id=todayMain2 class="todayMain" style="padding-bottom: 10px;">
			<div class="bd tabpanel"  style="height: auto">
				<div  class="tipstitle_title settingtable bold bd todayInfoPanelTab" id="rightPanelTitle_normal">
					<div style="position: absolute;">
						<div class="tab_list bd actived">
							快捷方式
						</div>
						<div class="tab_list bd pointer">
							个性换肤
						</div>
					</div>
				</div>
				<div class="tab_content" style="height:350px;overflow:hidden;">
					<div>
						<div class="demo ui-widget ui-helper-clearfix">
							<ul id="gallery" class="gallery ui-helper-reset ui-helper-clearfix bd_upload" style="overflow-x:hidden;height:290px;overflow:auto;"> 
								<c:forEach items="${links}" var="link">
									<c:if test="${link.actived == 1}"> 
										<li class="ui-widget-content ui-corner-tr bd_upload" title="可以拖动图标" id="relation_${link.relationId}">
											<h5 class="tipstitle2 bd_upload" style="color:white;font-weight: bold;" onclick='location.href = "${link.url}"'>${link.title }</h5>
											<img src="img/link/${link.img}" onclick='location.href = "${link.url}"'/>
											<!--img src="img/link/${link.img}"  width="100" height="72" onclick='location.href = "${link.url}"'/-->
										</li>	
									</c:if>
								</c:forEach>
							</ul>
							<div id="trash" class="ui-widget-content attbg2">
								<h4 class="tipstitle2 bd_upload" style="color:white;font-weight: bold;cursor: pointer;"><span class="ui-icon ui-icon-folder-collapsed"></span> 快捷功能库</h4>
								<ul style="height: 238px; overflow: auto;" class="gallery ui-helper-reset">
									<c:forEach items="${links}" var="link">
										<c:if test="${link.actived == 0}"> 
											<li id="relation_${link.relationId}" title="可以拖动图标" class="ui-widget-content ui-corner-tr bd_upload ui-draggable" style="display: list-item; width: 48px;">
												<h5 class="tipstitle2 bd_upload" style="color:white;font-weight: bold;" onclick='location.href = "${link.url}"'>${link.title }</h5>
												<img onclick='location.href = "${link.url}"' src="img/link/${link.img}" style="display: inline;">
												<!-- img width="96" height="72" onclick='location.href = "${link.url}"' src="img/link/${link.img}" style="height: 36px; display: inline;" -->
										</c:if>
									</c:forEach>
								</ul>
							</div>
						</div>
					</div>
					<div style="border-top: 1px solid rgb(255, 255, 255); line-height: 22px;border-bottom: none;" class="toolbg toolbgline barspace4">
				 		拖动快捷功能图标即可设置
					</div>
				</div>
				<div class="tab_content" style="display: none;">
					<div style="height: 16px; overflow: hidden;background: none;" class="settingtable"></div>
					<input type="hidden" id="tmplstyle" value="${syscount.skin}">
					<div class="settingtable" style="background: none">
						<div style="margin-left: 31px; height: 26px;">
							<span class="addrtitle">给 ${syscount.chineseName} 的帐户设置皮肤：</span>
						</div>
						<div class="skinsetting" style="overflow-x:hidden;height:283px;overflow:auto;background: none;">
							<%--<div id="divlayer_0"  style="margin: 1px;">
								<table cellspacing="0" cellpadding="0" border="0" class="pointer clr" onclick="chooseStyle('0');">
									<tbody>
										<tr>
											<td>
												<img class="face0" src="images/spacer3475l.gif">
											</td>
										</tr>
										<tr>
											<td  id="label_0">
												默认皮肤
											</td>
										</tr>
									</tbody>
								</table>
							</div>
							<div id="divlayer_8"  style="margin: 1px;">
								<table cellspacing="0" cellpadding="0" border="0" class="pointer clr" onclick="chooseStyle('8');">
									<tbody>
										<tr>
											<td>
												<img class="face8" src="images/spacer3475l.gif">
											</td>
										</tr>
										<tr>
											<td  id="label_8">
												贺新年
											</td>
										</tr>
									</tbody>
								</table>
							</div>
							--%><div id="divlayer_24"  style="margin: 1px;">
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
							<%--<div id="divlayer_23"  style="margin: 1px;">
								<table cellspacing="0" cellpadding="0" border="0" class="pointer clr" onclick="chooseStyle('23');">
									<tbody>
										<tr>
											<td>
												<img class="face23" src="images/spacer3475l.gif">
											</td>
										</tr>
										<tr>
											<td  id="label_23">
												蓝色高原
											</td>
										</tr>
									</tbody>
								</table>
							</div>
							<div id="divlayer_16"  style="margin: 1px;">
								<table cellspacing="0" cellpadding="0" border="0" class="pointer clr" onclick="chooseStyle('16');">
									<tbody>
										<tr>
											<td>
												<img class="face16" src="images/spacer3475l.gif">
											</td>
										</tr>
										<tr>
											<td  id="label_16">
												绿野
											</td>
										</tr>
									</tbody>
								</table>
							</div>
							<div id="divlayer_17"  style="margin: 1px;">
								<table cellspacing="0" cellpadding="0" border="0" class="pointer clr" onclick="chooseStyle('17');">
									<tbody>
										<tr>
											<td>
												<img class="face17" src="images/spacer3475l.gif">
											</td>
										</tr>
										<tr>
											<td  id="label_17">
												墨影
											</td>
										</tr>
									</tbody>
								</table>
							</div>
							<div id="divlayer_19"  style="margin: 1px;">
								<table cellspacing="0" cellpadding="0" border="0" class="pointer clr" onclick="chooseStyle('19');">
									<tbody>
										<tr>
											<td>
												<img class="face19" src="images/spacer3475l.gif">
											</td>
										</tr>
										<tr>
											<td  id="label_19">
												天台
											</td>
										</tr>
									</tbody>
								</table>
							</div>
							--%><div id="divlayer_22" style="margin: 1px;" >
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
							<%--<div id="divlayer_21"  style="margin: 1px;">
								<table width="84" cellspacing="0" cellpadding="0" border="0" class="pointer" onclick="chooseStyle('21');">
									<tbody>
										<tr>
											<td>
												<img class="face21" src="images/spacer3475l.gif">
											</td>
										</tr>
										<tr>
											<td  id="label_21">
												星空
											</td>
										</tr>
									</tbody>
								</table>
							</div>
							<div id="divlayer_2"  style="margin: 1px;">
								<table width="84" cellspacing="0" cellpadding="0" border="0" class="pointer" onclick="chooseStyle('2');">
									<tbody>
										<tr>
											<td>
												<img class="face2" src="images/spacer3475l.gif">
											</td>
										</tr>
										<tr>
											<td  id="label_2">
												枫叶秋
											</td>
										</tr>
									</tbody>
								</table>
							</div>
							<div id="divlayer_1"  style="margin: 1px;">
								<table width="84" cellspacing="0" cellpadding="0" border="0" class="pointer" onclick="chooseStyle('1');">
									<tbody>
										<tr>
											<td>
												<img class="face1" src="images/spacer3475l.gif">
											</td>
										</tr>
										<tr>
											<td  id="label_1">
												藤蔓绿
											</td>
										</tr>
									</tbody>
								</table>
							</div>
							<div id="divlayer_3"  style="margin: 1px;">
								<table width="84" cellspacing="0" cellpadding="0" border="0" class="pointer" onclick="chooseStyle('3');">
									<tbody>
										<tr>
											<td>
												<img class="face3" src="images/spacer3475l.gif">
											</td>
										</tr>
										<tr>
											<td  id="label_3">
												粉红猪
											</td>
										</tr>
									</tbody>
								</table>
							</div>
							<div id="divlayer_4"  style="margin: 1px;">
								<table width="84" cellspacing="0" cellpadding="0" border="0" class="pointer" onclick="chooseStyle('4');">
									<tbody>
										<tr>
											<td>
												<img class="face4" src="images/spacer3475l.gif">
											</td>
										</tr>
										<tr>
											<td  id="label_4">
												甜蜜橙
											</td>
										</tr>
									</tbody>
								</table>
							</div>
							<div id="divlayer_5"  style="margin: 1px;">
								<table width="84" cellspacing="0" cellpadding="0" border="0" class="pointer" onclick="chooseStyle('5');">
									<tbody>
										<tr>
											<td>
												<img class="face5" src="images/spacer3475l.gif">
											</td>
										</tr>
										<tr>
											<td  id="label_5">
												梦幻紫
											</td>
										</tr>
									</tbody>
								</table>
							</div>
							<div id="divlayer_6">
								<table width="84" cellspacing="0" cellpadding="0" border="0" class="pointer" onclick="chooseStyle('6');">
									<tbody>
										<tr>
											<td>
												<img class="face6" src="images/spacer3475l.gif">
											</td>
										</tr>
										<tr>
											<td style="font-weight: bold;" id="label_6">
												梦星海
											</td>
										</tr>
									</tbody>
								</table>
							</div>
							<div id="divlayer_7"  style="margin: 1px;">
								<table width="84" cellspacing="0" cellpadding="0" border="0" class="pointer" onclick="chooseStyle('7');">
									<tbody>
										<tr>
											<td>
												<img class="face7" src="images/spacer3475l.gif">
											</td>
										</tr>
										<tr>
											<td  id="label_7">
												淡雅灰
											</td>
										</tr>
									</tbody>
								</table>
							</div>
							--%><div id="divlayer_13"  style="margin: 1px;">
								<table width="84" cellspacing="0" cellpadding="0" border="0" class="pointer" onclick="chooseStyle('13');">
									<tbody>
										<tr>
											<td>
												<img class="face13" src="images/spacer3475l.gif">
											</td>
										</tr>
										<tr>
											<td  id="label_13">
												清风竹
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
							<%--<div id="divlayer_14"  style="margin: 1px;">
								<table width="84" cellspacing="0" cellpadding="0" border="0" class="pointer" onclick="chooseStyle('14');">
									<tbody>
										<tr>
											<td>
												<img class="face14" src="images/spacer3475l.gif">
											</td>
										</tr>
										<tr>
											<td  id="label_14">
												水墨情
											</td>
										</tr>
									</tbody>
								</table>
							</div>
							<div id="divlayer_15"  style="margin: 1px;">
								<table width="84" cellspacing="0" cellpadding="0" border="0" class="pointer" onclick="chooseStyle('15');">
									<tbody>
										<tr>
											<td>
												<img class="face15" src="images/spacer3475l.gif">
											</td>
										</tr>
										<tr>
											<td  id="label_15">
												桃花红
											</td>
										</tr>
									</tbody>
								</table>
							</div>
							<div id="divlayer_10"  style="margin: 1px;">
								<table width="84" cellspacing="0" cellpadding="0" border="0" class="pointer" onclick="chooseStyle('10');">
									<tbody>
										<tr>
											<td>
												<img class="face10" src="images/spacer3475l.gif">
											</td>
										</tr>
										<tr>
											<td  id="label_10">
												蜘蛛侠3
											</td>
										</tr>
									</tbody>
								</table>
							</div>
							<div id="divlayer_18"  style="margin: 1px;">
								<table width="84" cellspacing="0" cellpadding="0" border="0" class="pointer" onclick="chooseStyle('18');">
									<tbody>
										<tr>
											<td>
												<img class="face18" src="images/spacer3475l.gif">
											</td>
										</tr>
										<tr>
											<td  id="label_18">
												木纹
											</td>
										</tr>
									</tbody>
								</table>
							</div>
							<div id="divlayer_20"  style="margin: 1px;">
								<table width="84" cellspacing="0" cellpadding="0" border="0" class="pointer" onclick="chooseStyle('20');">
									<tbody>
										<tr>
											<td>
												<img class="face20" src="images/spacer3475l.gif">
											</td>
										</tr>
										<tr>
											<td  id="label_20">
												远古
											</td>
										</tr>
									</tbody>
								</table>
							</div>
							--%>
							<%--<div style="height: 12px;" class="clr"></div>
							<fieldset style="border-width: 1px 0pt 0pt; height: 15px; background: none repeat scroll 0% 0% transparent;" class="clr graytext bd">
								<legend style="margin-left: 6px; padding: 0pt 2px;">
									<span class="black">Foxmail皮肤</span>
								</legend>
							</fieldset>
							
							<div id="divlayer_211">
								<table width="84" cellspacing="0" cellpadding="0" border="0" class="pointer" onclick="chooseStyle('211');">
									<tbody>
										<tr>
											<td>
												<img class="face211" src="images/spacer3475l.gif">
											</td>
										</tr>
										<tr>
											<td style="font-weight: normal;" id="label_211">
												夕阳
											</td>
										</tr>
									</tbody>
								</table>
							</div>
							<div style="height: 12px;" class="clr"></div>
							<fieldset style="border-width: 1px 0pt 0pt; height: 15px; background: none repeat scroll 0% 0% transparent;" class="clr graytext bd">
								<legend style="margin-left: 6px; padding: 0pt 2px;">
									<span class="black">VIP皮肤</span> (QQ会员尊享)
								</legend>
							</fieldset>
							<div id="divlayer_80">
								<table width="84" cellspacing="0" cellpadding="0" border="0" class="pointer" onclick="chooseStyle('80');">
									<tbody>
										<tr>
											<td>
												<img class="face80" src="images/spacer3475l.gif">
											</td>
										</tr>
										<tr>
											<td style="font-weight: normal;" id="label_80">
												经典灰
											</td>
										</tr>
									</tbody>
								</table>
							</div>
							<div id="divlayer_82">
								<table width="84" cellspacing="0" cellpadding="0" border="0" class="pointer" onclick="chooseStyle('82');">
									<tbody>
										<tr>
											<td>
												<img class="face82" src="images/spacer3475l.gif">
											</td>
										</tr>
										<tr>
											<td style="font-weight: normal;" id="label_82">
												梦幻光影
											</td>
										</tr>
									</tbody>
								</table>
							</div>
							<div id="divlayer_83">
								<table width="84" cellspacing="0" cellpadding="0" border="0" class="pointer" onclick="chooseStyle('83');">
									<tbody>
										<tr>
											<td>
												<img class="face83" src="images/spacer3475l.gif">
											</td>
										</tr>
										<tr>
											<td style="font-weight: normal;" id="label_83">
												红色斑斓
											</td>
										</tr>
									</tbody>
								</table>
							</div>
							<div id="divlayer_84">
								<table width="84" cellspacing="0" cellpadding="0" border="0" class="pointer" onclick="chooseStyle('84');">
									<tbody>
										<tr>
											<td>
												<img class="face84" src="images/spacer3475l.gif">
											</td>
										</tr>
										<tr>
											<td style="font-weight: normal;" id="label_84">
												老街
											</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
						--%><div class="clr">
							&nbsp;
						</div>
					</div>
					<div style="border-top: 1px solid rgb(255, 255, 255); line-height: 22px;border-bottom: none;" class="toolbg toolbgline barspace4">
				 		点击图片即可直接切换皮肤，切换后，设置自动保存。
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
