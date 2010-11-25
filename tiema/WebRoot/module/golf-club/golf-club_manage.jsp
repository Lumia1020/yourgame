<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath %>"/>
		<title>高尔夫俱乐部设置</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<link rel="stylesheet" type="text/css" href="css/main.css"/>
		<link rel="stylesheet" type="text/css" href="css/skin<s:property value="#session.user.skin"/>.css" class="skin"/>
		<link rel="stylesheet" type="text/css" href="libraries/jquery/plugin/pagination/pagination.css"/>
		<link rel="stylesheet" type="text/css" href="libraries/jquery/plugin/thickbox/thickbox.css" />
		<link rel="stylesheet" type="text/css" href="libraries/dtree/dtree.css"/>
		
		<%@ include file="/commons/common_css.jsp"%>
		<%@ include file="/commons/common_js.jsp"%>

		<script type="text/javascript" src="libraries/dtree/dtree.js"></script>
		<script type="text/javascript" src="module/golf-club/js/golf.club.manage.js"></script>
	</head>
	<body class="tbody" id="list">
		<div class="txt_title">
			高尔夫俱乐部设置
		</div>
		
		
		<div class="toolbg1 toolbgline toolheight nowrap" style="min-width: 750px">
			<div class="right">
				&nbsp;
			</div>
			<div class="nowrap left">
				<input id="btn_show_add" value="增加俱乐部" class="wd4 btn" type="button" />
				<input id="btn_show_update" value="修改俱乐部" class="wd4 btn" disabled="disabled" type="button" />
				<input id="btn_change_state" value="取消俱乐部" class="wd4 btn" disabled="disabled" type="button" />
				<input type="button" style="vertical-align: top; margin-top: 3px;" class="btn_sepline"/>
				<input id="btn_show_price_add" value="新增价格" class="wd3 btn" disabled="disabled" type="button"/>
				<input id="btn_show_price_update" value="修改价格" class="wd3 btn" disabled="disabled" type="button"/>
				<input id="btn_delete_price_state" value="删除价格" class="wd3 btn" disabled="disabled" type="button"/>
				<input type="button" style="vertical-align: top; margin-top: 3px;" class="btn_sepline"/>
				<input id="btn_show_query" value="综合查询" class="wd3 btn" type="button" />
			</div>
			<div class="clr">&nbsp;</div>
		</div>
		
		<table style="table-layout: fixed; width: 100%;min-width: 750px" class="sortable O2" cellpadding="0" cellspacing="0" id="table_list">
			<thead>
				<tr>
					<th style="padding: 1px 0pt 1px 5px;width:45px;height:20px;" class="sorttable_nosort">&nbsp;</th>
					<th class="o_title2">俱乐部名称</th>
					<th class="o_title2" style="width: 60px;">联系人</th>
					<th class="o_title2 sorttable_nosort">固定电话</th>
					<th class="o_title2 sorttable_nosort">移动电话</th>
					<th class="o_title2 sorttable_nosort">俱乐部地址</th>
					<th class="o_title2 sorttable_nosort" style="width: 50px;">邮编</th>
					<th class="o_title2" style="width: 40px;">状态</th>
					<th class="o_title2 sorttable_nosort">备注</th>
				</tr>
			</thead>
			<tbody class="toarea"></tbody>
			<tfoot></tfoot>
		</table>
		
		<div class="list_btline">
			<div class="f_size selbar_bt barspace2" style="height: 19px; padding-top: 5px;">
				<label><input type="checkbox" id="only_one_detail"/>同时只显示一个球会的价格列表</label>
			</div>
		</div>
		<div class="toolbg1 toolbgline toolheight nowrap" id="page_infos" style="min-width: 750px;"></div>
		
		
		<div id="div_club_price_add" style="display: none;">
			<div class="container attbg2 bd_upload">
				<ol></ol>
			</div>
			<div class="form-container">
				<form method="post" action="json/club-price/entity-add" id="form_club_price_add">
					<fieldset>
						<legend>&nbsp;增加俱乐部价格&nbsp;</legend>
						<table width="100%">
							<tr>
								<td valign="top">
									<div>
										<label for="clubPrice_dailyCost_add" class="label">平日成本价<em>*</em></label>
										<input id="clubPrice_dailyCost_add" name="clubPrice.dailyCost" class="input" type="text"/>
										<input id="clubPrice_golfClub_id_add" name="clubPrice.golfClub.id" type="hidden"/>
									</div>
									<div>
										<label for="clubPrice_holidayCost_add" class="label">假日成本价<em>*</em></label>
										<input id="clubPrice_holidayCost_add" name="clubPrice.holidayCost" class="input" type="text"/>
									</div>
									<div>
										<label for="clubPrice_startDate_add" class="label">开始日期<em>*</em></label>
										<input id="clubPrice_startDate_add" name="clubPrice.startDate" onfocus="var d5222=$dp.$('clubPrice_endDate_add');WdatePicker({onpicked:function(){if(d5222)d5222.focus();},maxDate:'#F{$dp.$D(\'clubPrice_endDate_add\')}'})" class="input" type="text"/>
									</div>
								</td>
								<td valign="top">
									<div>
										<label for="clubPrice_weekdayPrice_add" class="label">平日价格<em>*</em></label>
										<input id="clubPrice_weekdayPrice_add" name="clubPrice.weekdayPrice" class="input" type="text"/>
									</div>
									<div>
										<label for="clubPrice_holidayPrice_add" class="label">假日价格<em>*</em></label>
										<input id="clubPrice_holidayPrice_add" name="clubPrice.holidayPrice" class="input" type="text"/>
									</div>
									<div>
										<label for="clubPrice_endDate_add" class="label">结束日期<em>*</em></label>
										<input id="clubPrice_endDate_add" name="clubPrice.endDate" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'clubPrice_startDate_add\')}'})" class="input" type="text"/>
									</div>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<div>
										<label for="clubPrice_remark_add" class="label">备注</label>
										<textarea name="clubPrice.remark" id="clubPrice_remark_add" style="width:508px;height:60px;"></textarea>
									</div>
								</td>
							</tr>
						</table>
					</fieldset>
					<div class="buttonrow" style="text-align: right;">
						<br/>
						<input id="btn_club_price_add" class="wd2 btn" value="新增" type="submit" />
					</div>
				</form>
			</div>
		</div>
		
		<div id="div_club_price_update" style="display: none;">
			<div class="container attbg2 bd_upload">
				<ol></ol>
			</div>
			<div class="form-container">
				<form method="post" action="json/club-price/entity-update" id="form_club_price_update">
					<fieldset>
						<legend>&nbsp;修改俱乐部价格&nbsp;</legend>
						<table width="100%">
							<tr>
								<td valign="top">
									<div>
										<label for="clubPrice_dailyCost_update" class="label">平日成本价<em>*</em></label>
										<input id="clubPrice_dailyCost_update" name="clubPrice.dailyCost" class="input" type="text"/>
										<input name="clubPrice.id" type="hidden"/>
										<input id="clubPrice_golfClub_id_update" name="clubPrice.golfClub.id" type="hidden"/>
									</div>
									<div>
										<label for="clubPrice_holidayCost_update" class="label">假日成本价<em>*</em></label>
										<input id="clubPrice_holidayCost_update" name="clubPrice.holidayCost" class="input" type="text"/>
									</div>
									<div>
										<label for="clubPrice_startDate_update" class="label">开始日期<em>*</em></label>
										<input id="clubPrice_startDate_update" name="clubPrice.startDate" onfocus="var d5222=$dp.$('clubPrice_endDate_update');WdatePicker({onpicked:function(){if(d5222)d5222.focus();},maxDate:'#F{$dp.$D(\'clubPrice_endDate_update\')}'})" class="input" type="text"/>
									</div>
								</td>
								<td valign="top">
									<div>
										<label for="clubPrice_weekdayPrice_update" class="label">平日价格<em>*</em></label>
										<input id="clubPrice_weekdayPrice_update" name="clubPrice.weekdayPrice" class="input" type="text"/>
									</div>
									<div>
										<label for="clubPrice_holidayPrice_update" class="label">假日价格<em>*</em></label>
										<input id="clubPrice_holidayPrice_update" name="clubPrice.holidayPrice" class="input" type="text"/>
									</div>
									<div>
										<label for="clubPrice_endDate_update" class="label">结束日期<em>*</em></label>
										<input id="clubPrice_endDate_update" name="clubPrice.endDate" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'clubPrice_startDate_update\')}'})" class="input" type="text"/>
									</div>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<div>
										<label for="clubPrice_remark_update" class="label">备注</label>
										<textarea name="clubPrice.remark" id="clubPrice_remark_update" style="width:508px;height:60px;"></textarea>
									</div>
								</td>
							</tr>
						</table>
					</fieldset>
					<div class="buttonrow" style="text-align: right;">
						<br/>
						<input id="btn_club_price_update" class="wd2 btn" value="新增" type="submit" />
					</div>
				</form>
			</div>
		</div>
		
		<div id="div_add" style="display: none;">
			<div class="container attbg2 bd_upload">
				<ol></ol>
			</div>
			<div class="form-container">
				<form method="post" action="json/golf-club/entity-add" id="form_add">
					<fieldset>
						<legend>&nbsp;增加高尔夫俱乐部&nbsp;</legend>
						<table width="100%">
							<tr>
								<td valign="top">
									<div>
										<label for="golfClub_clubName_add" class="label">俱乐部名称<em>*</em></label>
										<input id="golfClub_clubName_add" name="golfClub.clubName" class="input" type="text"/>
									</div>
									<div>
										<label for="golfClub_telephone_add" class="label">固定电话</label>
										<input id="golfClub_telephone_add" name="golfClub.telephone" class="input" type="text"/>
									</div>
									<div>
										<label for="golfClub_im_add" class="label">QQ/MSN</label>
										<input id="golfClub_im_add" name="golfClub.im" class="input" type="text"/>
									</div>
									<div>
										<label for="golfClub_zipCode_add" class="label">邮政编码</label>
										<input id="golfClub_zipCode_add" name="golfClub.zipCode" class="input" type="text"/>
									</div>
								</td>
								<td valign="top">
									<div>
										<label for="golfClub_contact_add" class="label">联系人</label>
										<input id="golfClub_contact_add" name="golfClub.contact" class="input" type="text"/>
									</div>
									<div>
										<label for="golfClub_fax_add" class="label">传真号码</label>
										<input id="golfClub_fax_add" name="golfClub.fax" class="input" type="text"/>
									</div>
									<div>
										<label for="golfClub_url_add" class="label">球会网址</label>
										<input id="golfClub_url_add" name="golfClub.url" class="input" type="text"/>
									</div>
									<div>
										<label for="golfClub_route_add" class="label">交通线路</label>
										<input id="golfClub_route_add" name="golfClub.route" class="input" type="text"/>
									</div>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<div>
										<label for="golfClub_address_add" class="label">俱乐部地址</label>
										<input id="golfClub_address_add" name="golfClub.address" class="input" type="text" style="width:508px;"/>
									</div>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<div>
										<label for="golfClub_remark_add" class="label">备注</label>
										<textarea name="golfClub.remark" id="golfClub_remark_add" style="width:508px;height:60px;"></textarea>
									</div>
								</td>
							</tr>
						</table>
					</fieldset>
					<div class="buttonrow" style="text-align: right;">
						<br/>
						<input id="btn_add" class="wd2 btn" value="新增" type="submit" />
					</div>
				</form>
			</div>
		</div>
		
		
		
		<div id="div_update" style="display: none;">
			<div class="container attbg2 bd_upload">
				<ol></ol>
			</div>
			<div class="form-container">
				<form method="post" action="json/golf-club/entity-update" id="form_update">
					<fieldset>
						<legend>&nbsp;修改高尔夫俱乐部&nbsp;</legend>
						<table width="100%">
							<tr>
								<td valign="top">
									<div>
										<label for="golfClub_clubName_update" class="label">俱乐部名称<em>*</em></label>
										<input id="golfClub_clubName_update" name="golfClub.clubName" class="input" type="text"/>
										<input name="golfClub.id" type="hidden"/>
									</div>
									<div>
										<label for="golfClub_telephone_update" class="label">固定电话</label>
										<input id="golfClub_telephone_update" name="golfClub.telephone" class="input" type="text"/>
									</div>
									<div>
										<label for="golfClub_im_update" class="label">QQ/MSN</label>
										<input id="golfClub_im_update" name="golfClub.im" class="input" type="text"/>
									</div>
									<div>
										<label for="golfClub_zipCode_update" class="label">邮政编码</label>
										<input id="golfClub_zipCode_update" name="golfClub.zipCode" class="input" type="text"/>
									</div>
								</td>
								<td valign="top">
									<div>
										<label for="golfClub_contact_update" class="label">联系人</label>
										<input id="golfClub_contact_update" name="golfClub.contact" class="input" type="text"/>
									</div>
									<div>
										<label for="golfClub_fax_update" class="label">移动电话</label>
										<input id="golfClub_fax_update" name="golfClub.fax" class="input" type="text"/>
									</div>
									<div>
										<label for="golfClub_url_update" class="label">球会网址</label>
										<input id="golfClub_url_update" name="golfClub.url" class="input" type="text"/>
									</div>
									<div>
										<label for="golfClub_route_update" class="label">交通线路</label>
										<input id="golfClub_route_update" name="golfClub.route" class="input" type="text"/>
									</div>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<div>
										<label for="golfClub_address_update" class="label">俱乐部地址</label>
										<input id="golfClub_address_update" name="golfClub.address" class="input" type="text" style="width:508px;"/>
									</div>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<div>
										<label for="golfClub_remark_update" class="label">备注</label>
										<textarea name="golfClub.remark" id="golfClub_remark_update" style="width:508px;height:60px;"></textarea>
									</div>
								</td>
							</tr>
						</table>
					</fieldset>
					<div class="buttonrow" style="text-align: right;">
						<br/>
						<input id="btn_update" class="wd2 btn" value="更新" type="submit" />
					</div>
				</form>
			</div>
		</div>
		
		
		<div id="div_query" style="display: none;">
			<div class="form-container">
				<form method="post" action="json/golf-club/page!find" id="form_query">
					<fieldset>
						<legend>&nbsp;综合查询条件&nbsp;</legend>
						<table width="100%">
							<tr>
								<td valign="top">
									<div>
										<label for="golfClub_clubName_query" class="label">俱乐部名称</label>
										<input id="golfClub_clubName_query" name="golfClub.clubName" class="input" type="text"/>
									</div>
									<div>
										<label for="golfClub_contact_query" class="label">联系人</label>
										<input id="golfClub_contact_query" name="golfClub.contact" class="input" type="text"/>
									</div>
									<div>
										<label for="golfClub_mobile_query" class="label">移动电话</label>
										<input id="golfClub_mobile_query" name="golfClub.mobile" class="input" type="text"/>
									</div>
									<div>
										<label for="golfClub_telephone_query" class="label">固定电话</label>
										<input id="golfClub_telephone_query" name="golfClub.telephone" class="input" type="text"/>
									</div>
									<div>
										<label for="golfClub_state_query" class="label">俱乐部状态</label>
										<select id="golfClub_state_query" name="golfClub.state" class="input" style="width:190px">
											<option value="">所有状态</option>
											<s:iterator value="@com.tiema.core.constant.State@values()" var="s" begin="1">
												<option value="<s:property value="#s"/>"><s:property value="#s.label"/></option>
											</s:iterator>
										</select>
									</div>
								</td>
							</tr>
						</table>
					</fieldset>
					<div class="buttonrow" style="text-align: right;">
						<br/>
						<input id="btn_query" value="查询" class="wd2 btn" type="button"/>
					</div>
				</form>
			</div>
		</div>
		
	</body>
</html>