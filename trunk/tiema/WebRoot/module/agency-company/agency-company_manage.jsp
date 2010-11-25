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
		<title>中介公司设置</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<link rel="stylesheet" type="text/css" href="css/main.css"/>
		<link rel="stylesheet" type="text/css" href="css/skin<s:property value="#session.user.skin"/>.css" class="skin"/>
		<link rel="stylesheet" type="text/css" href="libraries/jquery/plugin/pagination/pagination.css"/>
		<link rel="stylesheet" type="text/css" href="libraries/jquery/plugin/thickbox/thickbox.css" />
		<link rel="stylesheet" type="text/css" href="libraries/dtree/dtree.css"/>
		
		<%@ include file="/commons/common_css.jsp"%>
		<%@ include file="/commons/common_js.jsp"%>
		
		<script type="text/javascript" src="libraries/dtree/dtree.js"></script>
		<script type="text/javascript" src="module/agency-company/js/agency.company.manage.js"></script>
	</head>
	<body class="tbody" id="list">
		<div class="txt_title">
			中介公司设置
		</div>
		
		
		<div class="toolbg1 toolbgline toolheight nowrap" style="min-width: 750px">
			<div class="right">
				&nbsp;
			</div>
			<div class="nowrap left">
				<input id="btn_show_add" value="增加中介公司" class="wd4 btn" type="button" />
				<input id="btn_show_update" value="修改中介公司" class="wd4 btn" disabled="disabled" type="button" />
				<input id="btn_change_state" value="取消中介公司" class="wd4 btn" disabled="disabled" type="button" />
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
					<th class="o_title2">中介公司</th>
					<th class="o_title2" style="width: 60px;">联系人</th>
					<th class="o_title2 sorttable_nosort">固定电话</th>
					<th class="o_title2 sorttable_nosort">移动电话</th>
					<th class="o_title2 sorttable_nosort">公司地址</th>
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
				<label><input type="checkbox" id="only_one_detail"/>同时只显示一个中介公司的价格列表</label>
			</div>
		</div>
		<div class="toolbg1 toolbgline toolheight nowrap" id="page_infos" style="min-width: 750px;"></div>
		
		<div id="div_agency_price_add" style="display: none;">
			<div class="container attbg2 bd_upload">
				<ol></ol>
			</div>
			<div class="form-container">
				<form method="post" action="json/agency-price/entity-add" id="form_agency_price_add">
					<fieldset>
						<legend>&nbsp;增加中介公司价格&nbsp;</legend>
						<table width="100%">
							<tr>
								<td valign="top">
									<div>
										<label for="agencyPrice_dailyCost_add" class="label">平日成本价<em>*</em></label>
										<input id="agencyPrice_dailyCost_add" name="agencyPrice.dailyCost" class="input" type="text"/>
										<input id="agencyPrice_agencyCompany_id_add" name="agencyPrice.agencyCompany.id" type="hidden"/>
									</div>
									<div>
										<label for="agencyPrice_holidayCost_add" class="label">假日成本价<em>*</em></label>
										<input id="agencyPrice_holidayCost_add" name="agencyPrice.holidayCost" class="input" type="text"/>
									</div>
									<div>
										<label for="agencyPrice_startDate_add" class="label">开始日期<em>*</em></label>
										<input id="agencyPrice_startDate_add" name="agencyPrice.startDate" onfocus="var d5222=$dp.$('agencyPrice_endDate_add');WdatePicker({onpicked:function(){if(d5222)d5222.focus();},maxDate:'#F{$dp.$D(\'agencyPrice_endDate_add\')}'})" class="input" type="text"/>
									</div>
								</td>
								<td valign="top">
									<div>
										<label for="agencyPrice_weekdayPrice_add" class="label">平日价格<em>*</em></label>
										<input id="agencyPrice_weekdayPrice_add" name="agencyPrice.weekdayPrice" class="input" type="text"/>
									</div>
									<div>
										<label for="agencyPrice_holidayPrice_add" class="label">假日价格<em>*</em></label>
										<input id="agencyPrice_holidayPrice_add" name="agencyPrice.holidayPrice" class="input" type="text"/>
									</div>
									<div>
										<label for="agencyPrice_endDate_add" class="label">结束日期<em>*</em></label>
										<input id="agencyPrice_endDate_add" name="agencyPrice.endDate" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'agencyPrice_startDate_add\')}'})" class="input" type="text"/>
									</div>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<div>
										<label for="agencyPrice_remark_add" class="label">备注</label>
										<textarea name="agencyPrice.remark" id="agencyPrice_remark_add" style="width:508px;height:60px;"></textarea>
									</div>
								</td>
							</tr>
						</table>
					</fieldset>
					<div class="buttonrow" style="text-align: right;">
						<br/>
						<input id="btn_agency_price_add" class="wd2 btn" value="新增" type="submit" />
					</div>
				</form>
			</div>
		</div>
		
		<div id="div_agency_price_update" style="display: none;">
			<div class="container attbg2 bd_upload">
				<ol></ol>
			</div>
			<div class="form-container">
				<form method="post" action="json/agency-price/entity-update" id="form_agency_price_update">
					<fieldset>
						<legend>&nbsp;修改中介公司价格&nbsp;</legend>
						<table width="100%">
							<tr>
								<td valign="top">
									<div>
										<label for="agencyPrice_dailyCost_update" class="label">平日成本价<em>*</em></label>
										<input id="agencyPrice_dailyCost_update" name="agencyPrice.dailyCost" class="input" type="text"/>
										<input name="agencyPrice.id" type="hidden"/>
										<input id="agencyPrice_agencyCompany_id_update" name="agencyPrice.agencyCompany.id" type="hidden"/>
									</div>
									<div>
										<label for="agencyPrice_holidayCost_update" class="label">假日成本价<em>*</em></label>
										<input id="agencyPrice_holidayCost_update" name="agencyPrice.holidayCost" class="input" type="text"/>
									</div>
									<div>
										<label for="agencyPrice_startDate_update" class="label">开始日期<em>*</em></label>
										<input id="agencyPrice_startDate_update" name="agencyPrice.startDate" onfocus="var d5222=$dp.$('agencyPrice_endDate_update');WdatePicker({onpicked:function(){if(d5222)d5222.focus();},maxDate:'#F{$dp.$D(\'agencyPrice_endDate_update\')}'})" class="input" type="text"/>
									</div>
								</td>
								<td valign="top">
									<div>
										<label for="agencyPrice_weekdayPrice_update" class="label">平日价格<em>*</em></label>
										<input id="agencyPrice_weekdayPrice_update" name="agencyPrice.weekdayPrice" class="input" type="text"/>
									</div>
									<div>
										<label for="agencyPrice_holidayPrice_update" class="label">假日价格<em>*</em></label>
										<input id="agencyPrice_holidayPrice_update" name="agencyPrice.holidayPrice" class="input" type="text"/>
									</div>
									<div>
										<label for="agencyPrice_endDate_update" class="label">结束日期<em>*</em></label>
										<input id="agencyPrice_endDate_update" name="agencyPrice.endDate" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'agencyPrice_startDate_update\')}'})" class="input" type="text"/>
									</div>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<div>
										<label for="agencyPrice_remark_update" class="label">备注</label>
										<textarea name="agencyPrice.remark" id="agencyPrice_remark_update" style="width:508px;height:60px;"></textarea>
									</div>
								</td>
							</tr>
						</table>
					</fieldset>
					<div class="buttonrow" style="text-align: right;">
						<br/>
						<input id="btn_agency_price_update" class="wd2 btn" value="新增" type="submit" />
					</div>
				</form>
			</div>
		</div>
		
		
		<div id="div_add" style="display: none;">
			<div class="container attbg2 bd_upload">
				<ol></ol>
			</div>
			<div class="form-container">
				<form method="post" action="json/agency-company/entity-add" id="form_add">
					<fieldset>
						<legend>&nbsp;增加中介公司&nbsp;</legend>
						<table width="100%">
							<tr>
								<td valign="top">
									<div>
										<label for="agency_companyName_add" class="label">中介公司<em>*</em></label>
										<input id="agency_companyName_add" name="agencyCompany.companyName" class="input" type="text"/>
									</div>
									<div>
										<label for="agency_telephone_add" class="label">固定电话</label>
										<input id="agency_telephone_add" name="agencyCompany.telephone" class="input" type="text"/>
									</div>
									<div>
										<label for="agency_fax_add" class="label">传真号码</label>
										<input id="agency_fax_add" name="agencyCompany.fax" class="input" type="text"/>
									</div>
									<div>
										<label for="agency_im_add" class="label">QQ/MSN</label>
										<input id="agency_im_add" name="agencyCompany.im" class="input" type="text"/>
									</div>
								</td>
								<td valign="top">
									<div>
										<label for="agency_contact_add" class="label">联系人</label>
										<input id="agency_contact_add" name="agencyCompany.contact" class="input" type="text"/>
									</div>
									<div>
										<label for="agency_mobile_add" class="label">移动电话</label>
										<input id="agency_mobile_add" name="agencyCompany.mobile" class="input" type="text"/>
									</div>
									<div>
										<label for="agency_zipCode_add" class="label">邮政编码</label>
										<input id="agency_zipCode_add" name="agencyCompany.zipCode" class="input" type="text"/>
									</div>
									<div>
										<label for="agency_email_add" class="label">电子邮件</label>
										<input id="agency_email_add" name="agencyCompany.email" class="input" type="text"/>
									</div>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<div>
										<label for="agency_address_add" class="label">公司地址</label>
										<input id="agency_address_add" name="agencyCompany.address" class="input" type="text" style="width:508px;"/>
									</div>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<div>
										<label for="agency_remark_add" class="label">备注</label>
										<textarea name="agencyCompany.remark" id="agency_remark_add" style="width:508px;height:60px;"></textarea>
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
				<form method="post" action="json/agency-company/entity-update" id="form_update">
					<fieldset>
						<legend>&nbsp;修改中介公司&nbsp;</legend>
						<table width="100%">
							<tr>
								<td valign="top">
									<div>
										<label for="agency_companyName_update" class="label">中介公司<em>*</em></label>
										<input id="agency_companyName_update" name="agencyCompany.companyName" class="input" type="text"/>
										<input name="agencyCompany.id" type="hidden"/>
									</div>
									<div>
										<label for="agency_telephone_update" class="label">固定电话</label>
										<input id="agency_telephone_update" name="agencyCompany.telephone" class="input" type="text"/>
									</div>
									<div>
										<label for="agency_fax_update" class="label">传真号码</label>
										<input id="agency_fax_update" name="agencyCompany.fax" class="input" type="text"/>
									</div>
									<div>
										<label for="agency_im_update" class="label">QQ/MSN</label>
										<input id="agency_im_update" name="agencyCompany.im" class="input" type="text"/>
									</div>
								</td>
								<td valign="top">
									<div>
										<label for="agency_contact_update" class="label">联系人</label>
										<input id="agency_contact_update" name="agencyCompany.contact" class="input" type="text"/>
									</div>
									<div>
										<label for="agency_mobile_update" class="label">移动电话</label>
										<input id="agency_mobile_update" name="agencyCompany.mobile" class="input" type="text"/>
									</div>
									<div>
										<label for="agency_zipCode_update" class="label">邮政编码</label>
										<input id="agency_zipCode_update" name="agencyCompany.zipCode" class="input" type="text"/>
									</div>
									<div>
										<label for="agency_email_update" class="label">电子邮件</label>
										<input id="agency_email_update" name="agencyCompany.email" class="input" type="text"/>
									</div>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<div>
										<label for="agency_updateress_update" class="label">公司地址</label>
										<input id="agency_updateress_update" name="agencyCompany.address" class="input" type="text" style="width:508px;"/>
									</div>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<div>
										<label for="agency_remark_update" class="label">备注</label>
										<textarea name="agencyCompany.remark" id="agency_remark_update" style="width:508px;height:60px;"></textarea>
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
				<form method="post" action="json/agency-company/page!find" id="form_query">
					<fieldset>
						<legend>&nbsp;综合查询条件&nbsp;</legend>
						<table width="100%">
							<tr>
								<td valign="top">
									<div>
										<label for="agency_companyName_query" class="label">中介公司</label>
										<input id="agency_companyName_query" name="agencyCompany.companyName" class="input" type="text"/>
									</div>
									<div>
										<label for="agency_contact_query" class="label">联系人</label>
										<input id="agency_contact_query" name="agencyCompany.contact" class="input" type="text"/>
									</div>
									<div>
										<label for="agency_telephone_query" class="label">固定电话</label>
										<input id="agency_telephone_query" name="agencyCompany.telephone" class="input" type="text"/>
									</div>
									<div>
										<label for="agency_mobile_query" class="label">移动电话</label>
										<input id="agency_mobile_query" name="agencyCompany.mobile" class="input" type="text"/>
									</div>
									<div>
										<label for="agency_state_query" class="label">中介公司状态</label>
										<select id="agency_state_query" name="agencyCompany.state" class="input" style="width:190px">
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