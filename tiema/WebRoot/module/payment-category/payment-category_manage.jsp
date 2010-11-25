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
		<title>付款种类设置</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<link rel="stylesheet" type="text/css" href="css/main.css"/>
		<link rel="stylesheet" type="text/css" href="css/skin<s:property value="#session.user.skin"/>.css" class="skin"/>
		<link rel="stylesheet" type="text/css" href="libraries/jquery/plugin/pagination/pagination.css"/>
		<link rel="stylesheet" type="text/css" href="libraries/jquery/plugin/thickbox/thickbox.css" />
		<link rel="stylesheet" type="text/css" href="libraries/dtree/dtree.css"/>
		
		<%@ include file="/commons/common_css.jsp"%>
		<%@ include file="/commons/common_js.jsp"%>

		<script type="text/javascript" src="libraries/dtree/dtree.js"></script>
		<script type="text/javascript" src="module/payment-category/js/payment.category.manage.js"></script>
	</head>
	<body class="tbody" id="list">
		<div class="txt_title">
			付款种类设置
		</div>
		
		
		<div class="toolbg1 toolbgline toolheight nowrap" style="min-width: 750px">
			<div class="right">
				&nbsp;
			</div>
			<div class="nowrap left">
				<input id="btn_show_add" value="增加付款种类" class="wd4 btn" type="button" />
				<input id="btn_show_update" value="修改付款种类" class="wd4 btn" disabled="disabled" type="button" />
				<input id="btn_change_state" value="取消付款种类" class="wd4 btn" disabled="disabled" type="button" />
				<input type="button" style="vertical-align: top; margin-top: 3px;" class="btn_sepline"/>
				<input id="btn_show_payment_mode_add" value="新增付款方式" class="wd3 btn" disabled="disabled" type="button"/>
				<input id="btn_show_payment_mode_update" value="修改付款方式" class="wd3 btn" disabled="disabled" type="button"/>
				<input id="btn_cancel_payment_mode_state" value="取消付款方式" class="wd3 btn" disabled="disabled" type="button"/>
				<input type="button" style="vertical-align: top; margin-top: 3px;" class="btn_sepline"/>
				<input id="btn_show_query" value="综合查询" class="wd3 btn" type="button" />
			</div>
			<div class="clr">&nbsp;</div>
		</div>
		
		<table style="table-layout: fixed; width: 100%;min-width: 750px" class="sortable O2" cellpadding="0" cellspacing="0" id="table_list">
			<thead>
				<tr>
					<th style="padding: 1px 0pt 1px 5px;width:45px;height:20px;" class="sorttable_nosort">&nbsp;</th>
					<th class="o_title2">付款种类名称</th>
					<th class="o_title2" style="width: 150px;">类型</th>
					<th class="o_title2" style="width: 100px;">状态</th>
					<th class="o_title2 sorttable_nosort">备注</th>
				</tr>
			</thead>
			<tbody class="toarea"></tbody>
			<tfoot></tfoot>
		</table>
		
		<div class="list_btline">
			<div class="f_size selbar_bt barspace2" style="height: 19px; padding-top: 5px;">
				<label><input type="checkbox" id="only_one_detail"/>同时只显示一个付款方式列表</label>
			</div>
		</div>
		<div class="toolbg1 toolbgline toolheight nowrap" id="page_infos" style="min-width: 750px;"></div>
		
		
		<div id="div_payment_mode_add" style="display: none;">
			<div class="container attbg2 bd_upload">
				<ol></ol>
			</div>
			<div class="form-container">
				<form method="post" action="json/payment-mode/entity-add" id="form_payment_mode_add">
					<fieldset>
						<legend>&nbsp;增加付款方式&nbsp;</legend>
						<table width="100%">
							<tr>
								<td valign="top">
									<div>
										<label for="paymentMode_modeName_add" class="label">付款方式名称<em>*</em></label>
										<input id="paymentMode_modeName_add" name="paymentMode.modeName" class="input" type="text"/>
										<input id="paymentMode_paymentCategory_id_add" name="paymentMode.paymentCategory.id" type="hidden"/>
									</div>
								</td>
							</tr>
							<tr>
								<td>
									<div>
										<label for="paymentMode_remark_add" class="label">备注</label>
										<textarea name="paymentMode.remark" id="paymentMode_remark_add" style="width:180px;height:80px;"></textarea>
									</div>
								</td>
							</tr>
						</table>
					</fieldset>
					<div class="buttonrow" style="text-align: right;">
						<br/>
						<input id="btn_payment_mode_add" class="wd2 btn" value="新增" type="submit" />
					</div>
				</form>
			</div>
		</div>
		
		<div id="div_payment_mode_update" style="display: none;">
			<div class="container attbg2 bd_upload">
				<ol></ol>
			</div>
			<div class="form-container">
				<form method="post" action="json/payment-mode/entity-update" id="form_payment_mode_update">
					<fieldset>
						<legend>&nbsp;修改付款方式&nbsp;</legend>
						<table width="100%">
							<tr>
								<td valign="top">
									<div>
										<label for="paymentMode_modeName_update" class="label">付款方式名称<em>*</em></label>
										<input id="paymentMode_modeName_update" name="paymentMode.modeName" class="input" type="text"/>
										<input id="paymentMode_paymentCategory_id_update" name="paymentMode.paymentCategory.id" type="hidden"/>
									</div>
									<div>
										<label for="paymentMode_remark_update" class="label">备注</label>
										<textarea name="paymentMode.remark" id="paymentMode_remark_update" style="width:254px;height:60px;"></textarea>
									</div>
								</td>
							</tr>
						</table>
					</fieldset>
					<div class="buttonrow" style="text-align: right;">
						<br/>
						<input id="btn_payment_mode_update" class="wd2 btn" value="新增" type="submit" />
					</div>
				</form>
			</div>
		</div>
		
		<div id="div_add" style="display: none;">
			<div class="container attbg2 bd_upload">
				<ol></ol>
			</div>
			<div class="form-container">
				<form method="post" action="json/payment-category/entity-add" id="form_add">
					<fieldset>
						<legend>&nbsp;增加付款种类&nbsp;</legend>
						<table width="100%">
							<tr>
								<td valign="top">
									<div>
										<label for="paymentCategory_categoryName_add" class="label">付款种类名称<em>*</em></label>
										<input id="paymentCategory_categoryName_add" name="paymentCategory.categoryName" class="input" type="text"/>
									</div>
									<div>
										<label for="paymentCategory_paymentType_add" class="label">类型</label>
										<select id="paymentCategory_paymentType_add" name="paymentCategory.paymentType" class="input" style="width:190px">
											<s:iterator value="@com.tiema.payment.constant.PaymentType@values()" var="s" begin="1">
												<option value="<s:property value="#s"/>"><s:property value="#s.label"/></option>
											</s:iterator>
										</select>
									</div>
									<div>
										<label for="paymentCategory_remark_add" class="label">备注</label>
										<textarea name="paymentCategory.remark" id="paymentCategory_remark_add" style="width:180px;height:60px;"></textarea>
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
				<form method="post" action="json/payment-category/entity-update" id="form_update">
					<fieldset>
						<legend>&nbsp;修改付款种类&nbsp;</legend>
						<table width="100%">
							<tr>
								<td valign="top">
									<div>
										<label for="paymentCategory_categoryName_update" class="label">付款种类名称<em>*</em></label>
										<input id="paymentCategory_categoryName_update" name="paymentCategory.categoryName" class="input" type="text"/>
										<input  name="paymentCategory.id" type="hidden"/>
									</div>
									<div>
										<label for="paymentCategory_paymentType_update" class="label">类型</label>
										<select id="paymentCategory_paymentType_update" name="paymentCategory.paymentType" class="input" style="width:190px">
											<s:iterator value="@com.tiema.payment.constant.PaymentType@values()" var="s" begin="1">
												<option value="<s:property value="#s"/>"><s:property value="#s.label"/></option>
											</s:iterator>
										</select>
									</div>
									<div>
										<label for="paymentCategory_remark_update" class="label">备注</label>
										<textarea name="paymentCategory.remark" id="paymentCategory_remark_update" style="width:180px;height:60px;"></textarea>
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
				<form method="post" action="json/payment-category/page!find" id="form_query">
					<fieldset>
						<legend>&nbsp;综合查询条件&nbsp;</legend>
						<table width="100%">
							<tr>
								<td valign="top">
									<div>
										<label for="paymentCategory_categoryName_query" class="label">付款种类名称<em>*</em></label>
										<input id="paymentCategory_categoryName_query" name="paymentCategory.categoryName" class="input" type="text"/>
									</div>
									<div>
										<label for="paymentCategory_paymentType_query" class="label">类型</label>
										<select id="paymentCategory_paymentType_query" name="paymentCategory.paymentType" class="input" style="width:190px">
											<option value="">所有类型</option>
											<s:iterator value="@com.tiema.payment.constant.PaymentType@values()" var="s" begin="1">
												<option value="<s:property value="#s"/>"><s:property value="#s.label"/></option>
											</s:iterator>
										</select>
									</div>
									<div>
										<label for="paymentCategory_state_query" class="label">状态</label>
										<select id="paymentCategory_state_query" name="paymentCategory.state" class="input" style="width:190px">
											<option value="">所有状态</option>
											<s:iterator value="@com.tiema.core.constant.State@values()" var="s" begin="1">
												<option value="<s:property value="#s"/>"><s:property value="#s.label"/></option>
											</s:iterator>
										</select>
									</div>
									<div>
										<label for="paymentCategory_remark_query" class="label">备注</label>
										<textarea name="paymentCategory.remark" id="paymentCategory_remark_query" style="width:180px;height:60px;"></textarea>
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