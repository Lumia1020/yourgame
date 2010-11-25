$(function() {
 
	// 创建分页组件,创建完毕后执行分页查询
	createPagination('#page_infos', [15, 20, 10, 5], findAllAgencyCompanies, true);

	/**
	 * 获得分页数据
	 * 
	 * @param wrap_id  分页组件渲染的id
	 * @param page_no  分页开始页码
	 * @param init  是否渲染页码
	 * @param callback 渲染页码的回调函数
	 */
	function findAllAgencyCompanies(wrap_id, page_no, init, callback) {
		var page_size = eval($('select.page_select_jump', wrap_id).val());
		$('#form_query').ajaxSubmit({
			dataType : "json",
			data : {'page.pageNo' : (page_no || 0) + 1,'page.pageSize' : page_size},
			success : function(json) {
				if (json.success) {
					var datas = json.result;
					var tbody = "";
					for (var i = 0; i < datas.length; i++) {tbody += renderDataTr(datas[i], false);}
					if (datas.length == 0) {tbody = renderNoDataTr();}
					$("#table_list > tbody").html(tbody);
					if (init) {callback(wrap_id, json.totalCount, json.pageNo - 1, page_size);}
					changeToolbarButtonState();
				}
			}
		});
	}
 
	/**
	 * 渲染行
	 * 
	 * @param n 行数据
	 * @param b 是否给行加上选中的背景样式
	 */
	function renderDataTr(n, b) {
		n = $.isArray(n) ? n[0]:n;
		var cs = 'tf';
		
		if(n.state._name == 'INVALID'){
			cs += ' del_line';
		}
		return "<tr id='tr_" + n.id + "' class='i M " + (b ? 'B' : '') + "'><td class='cx'><input type='radio' name='radio' sid='" + n.id
				+ "' state='" + n.state._name + "'/><img src='images/spacer3475l.gif' class='fd_on' onfocus='this.blur()'/></td><td class='" + cs + "'>"
				+ n.companyName + "</td><td class='" + cs + "'>" + $.t(n.contact) + "</td><td class='" + cs + "'>" + $.t(n.telephone)
				+ "</td><td class='" + cs + "'>" + $.t(n.mobile) + "</td><td class='" + cs + "'>" + $.t(n.address) + "</td><td class='" + cs + "'>"
				+ $.t(n.zipCode) + "</td><td class='" + cs + "'>" + n.state.label + "</td><td class='" + cs + "'>" + $.t(n.remark) + "</td></tr>";
	}
	function renderDataDetailsTr(cp, b,d) {
		cp = $.isArray(cp) ? cp[0]:cp;
		var cs = 'tf';
		
		if(cp.state._name == 'INVALID'){
			cs += ' del_line';
		}
		return '<tr id="tr_details_' + cp.id + '" class="i M ' + (b ? 'B':'') + '"><td><input ' + d + ' type="radio" name="radio_details" ' + d + ' sid="' + cp.id + '" state="' + cp.state._name
				+ '"/></td><td class="' + cs + '">' + cp.dailyCost + '</td><td class="' + cs + '">' + cp.weekdayPrice + '</td><td class="' + cs + '">' + cp.holidayCost
				+ '</td><td class="' + cs + '">' + cp.holidayPrice + '</td><td class="' + cs + '">' + cp.startDate + '</td><td class="' + cs + '">' + cp.endDate
				+ '</td><td class="' + cs + '">' + cp.state.label + '</td><td class="' + cs + '" title="' + cp.remark + '">' + $.wordLimit(cp.remark, 20) + '</td></tr>';
	}
	
	// 当新增记录的时候,详情中是没有数据的时候先调用这个函数
	function renderDetailsHead(){
		return '<tr class="details"><td colspan="9" valign="top" class="details_line"><div style="padding:5px 0 10px 50px;"><table style="table-layout: fixed; width:100%;min-width: 745px" cellspacing="0" cellpadding="6" border="0" class="bd_upload O2">'
			+ '<thead><tr class="addrtitle attbg o_title2"><th class="no_bt tf" style="width: 30px;height:22px;padding: 0 0 0 6px">&nbsp;</th>'
			+ '<th class="no_bt tf o_title2" style="width:12%">&nbsp;平日成本价&nbsp;</th>'
			+ '<th class="no_bt tf o_title2" style="width:10%">&nbsp;平日价格&nbsp;</th>'
			+ '<th class="no_bt tf o_title2" style="width:12%">&nbsp;假日成本价&nbsp;</th>'
			+ '<th class="no_bt tf o_title2" style="width:10%">&nbsp;假日价格&nbsp;</th>'
			+ '<th class="no_bt tf o_title2" style="width:15%">&nbsp;开始日期&nbsp;</th>'
			+ '<th class="no_bt tf o_title2" style="width:15%">&nbsp;结束日期&nbsp;</th>'
			+ '<th class="no_bt tf o_title2" style="width:10%">&nbsp;状态&nbsp;</th>'
			+ '<th class="no_bt tf o_title2" style="width:16%">&nbsp;备注&nbsp;</th></tr></thead><tbody></tbody></table></div></td></tr>';
	}

	// 渲染列表没有数据时的显示内容
	function renderNoDataTr() {
		return "<tr class='nodata'><td colspan='9' align='center'><div class='nomail'><b>无数据</b></div></td></tr>";
	}
	function renderNoDetailsDataTr(){
		return "<tr class='details nodata'><td colspan='9' align='center'><div class='nomail'><b>无数据</b></div></td></tr>";
	}
	
	//验证添加表单
	addEntity('#form_add','#table_list','agencyCompany',renderDataTr,changeToolbarButtonState);
	addEntity('#form_agency_price_add',$('#table_list'),'agencyPrice',renderDataDetailsTr,changeToolbarButtonStateByDetailsRow,renderDetailsHead);
	
	//验证修改表单
	updateEntity('#form_update','agencyCompany',"#tr_",renderDataTr,changeToolbarButtonState);
	updateEntity('#form_agency_price_update','agencyPrice',"#tr_details_",renderDataDetailsTr,changeToolbarButtonStateByDetailsRow);
	
	// 删除俱乐部价格记录
	deleteEntity('#btn_delete_price_state',$('#table_list'),'input[name="radio_details"]:checked','#tr_details_','json/agency-price/success!delete','agencyPriceId',changeToolbarButtonStateByDetailsRow,renderNoDetailsDataTr);
	
	//激活或者取消中介公司
	$('#btn_change_state').click(function() {
		var id = $('input[name="radio"]:checked','#table_list').attr('sid');
		var state = $('input[name="radio"]:checked','#table_list').attr('state');
		state = state == 'VALID' ? 'INVALID':'VALID';
		var btn_text = $('#btn_change_state').val();
		
		if (id) {
			if (confirm('确定要' + btn_text + '吗？')) {
				$.ajax({
					type : "POST",
					dataType : "json",
					url : 'json/agency-company/success!update',
					data : {'agencyCompany.id':id,'agencyCompany.state':state},
					success : function(result) {
						if (result.success) {
							var g = result.agencyCompany;
							var tr = $('#tr_' + id);
							var tds = tr.children();
							
							switch(g.state._name){
								case 'VALID':
									tds.removeClass('del_line');
									tr.next('tr.details').find('input:radio').attr('disabled','');
									break;
								case 'INVALID':
									tds.addClass(' del_line');
									tr.next('tr.details').find('input:radio').attr('checked','').attr('disabled','disabled');
									break;
							}
							
							tr.find('input:radio').attr('state',g.state._name);
							tds.last().prev().text(result.agencyCompany.state._name);
							
							changeToolbarButtonState();
						}else if(result.jsons.messages){
							alert(result.jsons.messages);
						}
					}
				});
			}
		} else {
			alert('请先选择要' + btn_text + '的数据行!');
		}
	});
	
//	$('#btn_change_state').click(function() {
//		var id = $('input:checked','#table_list').attr('id');
//		var state = $('input:checked','#table_list').attr('state');
//		state = state == 1 ? 2:1;
//		var btn_text = $('#btn_change_state').val();
//		
//		if (id) {
//			if (confirm('确定要' + btn_text + '吗？')) {
//				$.ajax({
//					type : "POST",
//					dataType : "json",
//					url : 'json/agency/success!update',
//					data : {'agency.id':id,'agency.state':state},
//					success : function(result) {
//						if (result.success) {
//							$('#tr_' + id).replaceWith(renderDataTr(result.agency,false));
//							changeToolbarButtonState();
//						}else if(result.jsons.messages){
//							alert(result.jsons.messages);
//						}
//					}
//				});
//			}
//		} else {
//			alert('请先选择要' + btn_text + '的数据行!');
//		}
//	});
//	
	
	//新增中介公司价格
	$('#btn_show_price_add').click(function(){
		var id = $('input[name="radio"]:checked','#table_list').attr('sid');
		if(id){
			$('#agencyPrice_agencyCompany_id_add').val(id);
			tb_show('新增中介公司价格', '#TB_inline?height=310&width=700&inlineId=div_agency_price_add', false);
		}else{
			alert('请先选择要操作的中介公司记录!');
		}
	});
	
	// 打开新增div
	$('#btn_show_add').click(function() {
		tb_show('新增中介公司资料', '#TB_inline?height=360&width=700&inlineId=div_add', false);
	});

	// 打开查询div
	$('#btn_show_query').click(function() {
		tb_show('综合查询', '#TB_inline?height=280&width=370&inlineId=div_query', false);
	});

	// 触发查询按钮
	$('#btn_query').click(function() {
		tb_remove();
		findAllAgencyCompanies('#page_infos', 0, false);
	});

	// 打开编辑div
	$('#btn_show_update').click(function() {
		var id = $('#table_list input[name="radio"]:checked').attr('sid');
		if (id) {
			$.waiting('#btn_update', true, '加载中...');
			$.ajax({
				type : "POST",
				url : 'json/agency-company/entity-load',
				dataType : "json",
				data : {"agencyCompanyId" : id},
				success : function(json) {
					if (json.success) {
						var obj = json.agencyCompany;
						for (field in obj) {$("#form_update [name='agencyCompany." + field + "']").val(obj[field]);}
					}
					$.waiting('#btn_update', false, '更新');
				}
			});
			$.clearValidationErrors($('#form_update'));
			tb_show('修改中介公司资料', '#TB_inline?height=360&width=700&inlineId=div_update', false);
		} else {
			alert('请先选择要操作的行');
		}
	});
	
	//修改中介公司价格
	$('#btn_show_price_update').click(function(){
		var id = $('#table_list input[name="radio_details"]:checked').attr('sid');
		if (id) {
			$.waiting('#btn_agency_price_update', true, '加载中...');
			$.ajax({
				type : "POST",
				url : 'json/agency-price/entity-load',
				dataType : "json",
				data : {"agencyPriceId" : id},
				success : function(json) {
					if (json.success) {
						var obj = json.agencyPrice;
						for (field in obj) {$("#form_agency_price_update [name='agencyPrice." + field + "']").val(obj[field]);}
						$('#agencyPrice_agencyCompany_id_update').val(obj.agencyCompany.id);
					}
					$.waiting('#btn_agency_price_update', false, '更新');
				}
			});
			$.clearValidationErrors($('#form_agency_price_update'));
			tb_show('修改中介公司价格', '#TB_inline?height=310&width=700&inlineId=div_agency_price_update', false);
		} else {
			alert('请先选择要操作的行');
		}
	});
	
	// 处理中介公司节点单击事件
	$('#table_list tbody.toarea img').live('click', function(e) {
		var img = $(e.target);
		toggleInnerData(img.parents('tr'),img);
	});
	
	// 切换隐藏数据
	function toggleInnerData(tr,img) {
		if (!tr.next().hasClass('details')) {
			var id = tr.attr('id').substring('tr_'.length);
			if (id) {
				$.$waiting(img,true,6);
				$.ajax({
					type : "POST",
					url : "json/agency-price/findAllByAgencyCompanyId",
					dataType : 'json',
					data : {'agencyPrice.agencyCompany.id' : id},
					success : function(jsons) {
						if(jsons.success){
							if (jsons.agencyPrices.length > 0) {
								var aps = jsons.agencyPrices;
								var innerTable = '<tr class="details"><td colspan="9" valign="top" class="details_line"><div style="' + (aps.length > 6 ? 'overflow-x:hidden;overflow-y:auto;height:150px' : '')
										+ ';padding:5px 0 10px 50px;"><table style="table-layout: fixed; width:100%;min-width: 745px" cellspacing="0" cellpadding="6" border="0" class="bd_upload O2">'
										+ '<thead><tr class="addrtitle attbg o_title2"><th class="no_bt tf" style="width: 30px;height:22px;padding: 0 0 0 6px">&nbsp;</th>'
										+ '<th class="no_bt tf o_title2" style="width:12%">&nbsp;平日成本价&nbsp;</th>'
										+ '<th class="no_bt tf o_title2" style="width:10%">&nbsp;平日价格&nbsp;</th>'
										+ '<th class="no_bt tf o_title2" style="width:12%">&nbsp;假日成本价&nbsp;</th>'
										+ '<th class="no_bt tf o_title2" style="width:10%">&nbsp;假日价格&nbsp;</th>'
										+ '<th class="no_bt tf o_title2" style="width:15%">&nbsp;开始日期&nbsp;</th>'
										+ '<th class="no_bt tf o_title2" style="width:15%">&nbsp;结束日期&nbsp;</th>'
										+ '<th class="no_bt tf o_title2" style="width:10%">&nbsp;状态&nbsp;</th>'
										+ '<th class="no_bt tf o_title2" style="width:16%">&nbsp;备注&nbsp;</th></tr></thead><tbody>';
	
								var state = $(img.prev()).attr('state');
								var d = (state == 'VALID' ? '':'disabled="disabled"');
								
								for (var i = 0; i < aps.length; i++) {
									innerTable += renderDataDetailsTr(aps[i],false,d);
								}
								innerTable + '</tbody></table></div></td></tr>';
								tr.after(innerTable).addClass('B').find('img').removeClass('fd_on').addClass('fd_off');
							}else{
								tr.after(renderNoDetailsDataTr());
								tr.addClass('B').find('img').removeClass('fd_on').addClass('fd_off');
							}
							if ($('#only_one_detail').attr('checked')) {
								tr.siblings().find('img').removeClass('fd_off').addClass('fd_on');
								tr.siblings().next('.details').hide();
							}
							$.$waiting(img,false);
						}
					}
				});
			}
		} else {
			tr.hasClass('B') ? tr.removeClass('B') : tr.addClass('B');
			var img = tr.find('img');

			if (img.hasClass('fd_on')) {
				img.removeClass('fd_on').addClass('fd_off');
			} else {
				img.removeClass('fd_off').addClass('fd_on');
			}

			tr.next().toggle();
			if ($('#only_one_detail').attr('checked')) {
				tr.siblings().find('img').removeClass('fd_off').addClass('fd_on');
				tr.siblings().next('.details').hide();
			}
		}
	}
	

	// 根据当前选中的记录来设定工具栏按钮状态
	$('#table_list input[name="radio"]').live('click', function() {changeToolbarButtonState();});
	$('#table_list input[name="radio_details"]').live('click',function(){changeToolbarButtonStateByDetailsRow();})
	
	// 切换工具栏按钮的状态
	function changeToolbarButtonState() {
		var radio = $('#table_list input[name="radio"]:checked');

		var state = radio.attr('state');
		var sv = state == 'VALID' ? '':'disabled';
		var v = state ? '':'disabled';

		$('#btn_show_update').attr('disabled', v);
		$('#btn_change_state').attr('disabled', v);
		$('#btn_show_price_add').attr('disabled', sv);
		$('#btn_show_price_update').attr('disabled', 'disabled');
		$('#btn_delete_price_state').attr('disabled', 'disabled');

		switch(state){
			case "VALID":
				$('#btn_change_state').val('取消中介公司');
				break;
			case "INVALID":
				$('#btn_change_state').val('激活中介公司');
				break;
		}
	}
	
	//根据详情中行选择来切换工具栏按钮状态
	function changeToolbarButtonStateByDetailsRow(){
		var radio = $('#table_list input[name="radio_details"]:checked');
		var parent_radio = radio.parents('tr.details').prev().find('td.cx input[name="radio"]').attr('checked','checked');
		changeToolbarButtonState();
		
		var parent_radio_state = parent_radio.attr('state');
	
		var sv = parent_radio_state == 'VALID' ? '':'disabled';
		
		$('#btn_show_price_add').attr('disabled', sv);
		$('#btn_show_price_update').attr('disabled', sv);
		$('#btn_delete_price_state').attr('disabled', sv);
	}
});
