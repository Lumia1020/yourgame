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
		<title>客户资料设置</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<link rel="stylesheet" type="text/css" href="css/main.css"/>
		<link rel="stylesheet" type="text/css" href="css/skin<s:property value="#session.user.skin"/>.css" class="skin"/>
		<link rel="stylesheet" type="text/css" href="libraries/jquery/plugin/pagination/pagination.css"/>
		<link rel="stylesheet" type="text/css" href="libraries/jquery/plugin/thickbox/thickbox.css" />
		<link rel="stylesheet" type="text/css" href="libraries/dtree/dtree.css"/>
		
		<%@ include file="/commons/common_css.jsp"%>
		<%@ include file="/commons/common_js.jsp"%>
		
		<script type="text/javascript" src="libraries/dtree/dtree.js"></script>
		<script type="text/javascript" src="module/customer/js/customer.manage.js"></script>
	</head>
	<body class="tbody" id="list">
		<div class="txt_title">
			客户资料设置
		</div>
		
		
		<div class="toolbg1 toolbgline toolheight nowrap" style="min-width: 750px">
			<div class="right">
				&nbsp;
			</div>
			<div class="nowrap left">
				<input id="btn_show_add" value="增加客户资料" class="wd4 btn" type="button" />
				<input id="btn_show_update" value="修改客户资料" class="wd4 btn" disabled="disabled" type="button" />
				<input id="btn_delete" value="删除客户资料" class="wd4 btn" disabled="disabled" type="button" />
				<input id="btn_show_query" value="综合查询" class="wd3 btn" type="button" />
			</div>
			<div class="clr">&nbsp;</div>
		</div>
		
		<table style="table-layout: fixed; width: 100%;min-width: 750px" class="sortable O2" cellpadding="0" cellspacing="0" id="table_list">
			<thead>
				<tr>
					<th style="padding: 1px 0pt 1px 5px;width:27px;height:20px" class="sorttable_nosort">&nbsp;</th>
					<th class="o_title2" style="width: 120px;">客户编号</th>
					<th class="o_title2" style="width: 100px;">客户姓名</th>
					<th class="o_title2">会籍种类</th>
					<th class="o_title2" style="width: 100px;">出生日期</th>
					<th class="o_title2" style="width: 100px;">原始客户</th>
					<th class="o_title2 sorttable_nosort">传真号码</th>
				</tr>
			</thead>
			<tbody class="toarea"></tbody>
			<tfoot></tfoot>
		</table>
		
		<div class="list_btline">
			<div style="height: 19px; padding-top: 5px;" class="f_size selbar_bt barspace2">
				<span class="addrtitle">&nbsp;</span>&nbsp;
			</div>
		</div>
		<div class="toolbg1 toolbgline toolheight nowrap" id="page_infos" style="min-width: 750px;"></div>
		
		
		
		<div id="div_add" style="display: none;">
			<div class="container attbg2 bd_upload">
				<ol></ol>
			</div>
			<div class="form-container">
				<form method="post" action="json/customer/entity-add" id="form_add">
					<fieldset>
						<legend>&nbsp;增加客户资料&nbsp;</legend>
						<table width="100%">
							<tr>
								<td valign="top">
									<div>
										<label for="customer_customerNumber_add" class="label">客户编号<em>*</em></label>
										<input id="customer_customerNumber_add" name="customer.customerNumber" class="input" type="text"/>
									</div>
									<div style="padding:7px 0">
										<label for="customer_sex_add" class="label">性别</label>
										<s:iterator value="@com.tiema.core.constant.Sex@values()" var="s" begin="0">
											<label>
												<input value="<s:property value="#s"/>" name="customer.sex" type="radio" checked="checked"/>
												<s:property value="#s.label"/>&nbsp;&nbsp;&nbsp;&nbsp;
											</label>
										</s:iterator>
									</div>
									<div>
										<label for="customer_membershipCategory_id_add" class="label">会籍种类</label>
										<select id="customer_membershipCategory_id_add" name="customer.membershipCategory.id" class="input" style="width:190px;">
											<s:iterator value="membershipCategories" var="c" status="i">
												<option value="<s:property value="id" />"><s:property value="categoryName" /></option>
											</s:iterator>
										</select>
									</div>
									<div>
										<label for="customer_fax_add" class="label">传真号码</label>
										<input id="customer_fax_add" name="customer.fax" class="input" type="text"/>
									</div>
									<div>
										<label for="customer_address_add" class="label">通讯地址</label>
										<input id="customer_address_add" name="customer.address" class="input" type="text"/>
									</div>
									<div>
										<label for="customer_zipCode_add" class="label">邮政编码</label>
										<input id="customer_zipCode_add" name="customer.zipCode" class="input" type="text"/>
									</div>
									<div>
										<label for="customer_buyer_add" class="label">买方</label>
										<input id="customer_buyer_add" name="customer.buyer" class="input" type="text"/>
									</div>
								</td>
								<td valign="top">
									<div>
										<label for="customer_customerName_add" class="label">客户姓名<em>*</em></label>
										<input id="customer_customerName_add" name="customer.customerName" class="input" type="text"/>
									</div>
									<div>
										<label for="customer_birthday_add" class="label">生日</label>
										<input id="customer_birthday_add" name="customer.birthday" onfocus="WdatePicker({startDate:'1970-05-01'})" class="input" type="text"/>
									</div>
									<div>
										<label for="customer_idCard_add" class="label">身份证号</label>
										<input id="customer_idCard_add" name="customer.idCard" class="input" type="text"/>
									</div>
									<div>
										<label for="customer_telephone_add" class="label">固定电话</label>
										<input id="customer_telephone_add" name="customer.telephone" class="input" type="text"/>
									</div>
									<div>
										<label for="customer_originalCustomer_add" class="label">原始客户</label>
										<input id="customer_originalCustomer_add" name="customer.originalCustomer" class="input" type="text"/>
									</div>
									<div>
										<label for="customer_transactionDate_add" class="label">交易日期</label>
										<input id="customer_transactionDate_add" name="customer.transactionDate" onfocus="WdatePicker()" class="input" type="text"/>
									</div>
									<div>
										<label for="customer_seller_add" class="label">卖方</label>
										<input id="customer_seller_add" name="customer.seller" class="input" type="text"/>
									</div>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<div>
										<label for="customer_url_add" class="label">球会网址</label>
										<input id="customer_url_add" name="customer.url" class="input" type="text" style="width:508px"/>
									</div>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<div>
										<label for="customer_remark_add" class="label">备注</label>
										<textarea name="customer.remark" id="customer_remark_add" style="width:508px;height:55px;"></textarea>
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
				<form method="post" action="json/customer/entity-update" id="form_update">
					<fieldset>
						<legend>&nbsp;修改客户资料&nbsp;</legend>
						<table width="100%">
							<tr>
								<td valign="top">
									<div>
										<label for="customer_customerNumber_update" class="label">客户编号<em>*</em></label>
										<input id="customer_customerNumber_update" name="customer.customerNumber" class="input" type="text" disabled="disabled"/>
										<input name="customer.id" type="hidden"/>
									</div>
									<div style="padding:7px 0">
										<label for="customer_sex_update" class="label">性别</label>
										<s:iterator value="@com.tiema.core.constant.Sex@values()" var="s" begin="0">
											<label>
												<input value="<s:property value="#s"/>" name="customer.sex" type="radio" checked="checked"/>
												<s:property value="#s.label"/>&nbsp;&nbsp;&nbsp;&nbsp;
											</label>
										</s:iterator>
									</div>
									<div>
										<label for="customer_membershipCategory_id_update" class="label">会籍种类</label>
										<select id="customer_membershipCategory_id_update" name="customer.membershipCategory.id" class="input" style="width:190px;">
											<s:iterator value="membershipCategories" var="c" status="i">
												<option value="<s:property value="id" />"><s:property value="categoryName" /></option>
											</s:iterator>
										</select>
									</div>
									<div>
										<label for="customer_fax_update" class="label">传真号码</label>
										<input id="customer_fax_update" name="customer.fax" class="input" type="text"/>
									</div>
									<div>
										<label for="customer_updateress_update" class="label">通讯地址</label>
										<input id="customer_updateress_update" name="customer.address" class="input" type="text"/>
									</div>
									<div>
										<label for="customer_zipCode_update" class="label">邮政编码</label>
										<input id="customer_zipCode_update" name="customer.zipCode" class="input" type="text"/>
									</div>
									<div>
										<label for="customer_buyer_update" class="label">买方</label>
										<input id="customer_buyer_update" name="customer.buyer" class="input" type="text"/>
									</div>
								</td>
								<td valign="top">
									<div>
										<label for="customer_customerName_update" class="label">客户姓名<em>*</em></label>
										<input id="customer_customerName_update" name="customer.customerName" class="input" type="text"/>
									</div>
									<div>
										<label for="customer_birthday_update" class="label">生日</label>
										<input id="customer_birthday_update" name="customer.birthday" onfocus="WdatePicker({startDate:'1970-05-01'})" class="input" type="text"/>
									</div>
									<div>
										<label for="customer_idCard_update" class="label">身份证号</label>
										<input id="customer_idCard_update" name="customer.idCard" class="input" type="text"/>
									</div>
									<div>
										<label for="customer_telephone_update" class="label">固定电话</label>
										<input id="customer_telephone_update" name="customer.telephone" class="input" type="text"/>
									</div>
									<div>
										<label for="customer_originalCustomer_update" class="label">原始客户</label>
										<input id="customer_originalCustomer_update" name="customer.originalCustomer" class="input" type="text"/>
									</div>
									<div>
										<label for="customer_transactionDate_update" class="label">交易日期</label>
										<input id="customer_transactionDate_update" name="customer.transactionDate" onfocus="WdatePicker()" class="input" type="text"/>
									</div>
									<div>
										<label for="customer_seller_update" class="label">卖方</label>
										<input id="customer_seller_update" name="customer.seller" class="input" type="text"/>
									</div>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<div>
										<label for="customer_url_update" class="label">球会网址</label>
										<input id="customer_url_update" name="customer.url" class="input" type="text" style="width:508px;"/>
									</div>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<div>
										<label for="customer_remark_update" class="label">备注</label>
										<textarea name="customer.remark" id="customer_remark_update" style="width:508px;height:55px;"></textarea>
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
				<form method="post" action="json/customer/page!find" id="form_query">
					<fieldset>
						<legend>&nbsp;综合查询条件&nbsp;</legend>
						<table width="100%">
							<tr>
								<td valign="top">
									<div>
										<label for="customer_customerNumber_query" class="label">客户编号</label>
										<input id="customer_customerNumber_query" name="customer.customerNumber" class="input" type="text"/>
									</div>
									<div>
										<label for="customer_customerName_query" class="label">客户姓名</label>
										<input id="customer_customerName_query" name="customer.customerName" class="input" type="text"/>
									</div>
									<div>
										<label for="customer_fax_query" class="label">传真号码</label>
										<input id="customer_fax_query" name="customer.fax" class="input" type="text"/>
									</div>
									<div>
										<label for="customer_telephone_query" class="label">固定电话</label>
										<input id="customer_telephone_query" name="customer.telephone" class="input" type="text"/>
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