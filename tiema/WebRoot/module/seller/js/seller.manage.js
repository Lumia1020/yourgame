$(function() {
 
	// 创建分页组件,创建完毕后执行分页查询
	createPagination('#page_infos', [15, 20, 10, 5], findAllSellers, true);

	/**
	 * 获得分页数据
	 * 
	 * @param wrap_id  分页组件渲染的id
	 * @param page_no  分页开始页码
	 * @param init  是否渲染页码
	 * @param callback 渲染页码的回调函数
	 */
	function findAllSellers(wrap_id, page_no, init, callback) {
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
		return "<tr id='tr_" + n.id + "' class='i M " + (b ? 'B' : '') + "'><td class='cx'><input type='radio' name='radio' sid='" + n.id + "' state='" + n.state._name
				+ "'/></td><td class='" + cs + "'>" + n.sellerName + "</td><td class='" + cs + "'>" + n.sex.label + "</td><td class='" + cs + "'>" + $.t(n.deptName) + "</td><td class='"
				+ cs + "'>" + $.t(n.mobile) + "</td><td class='" + cs + "'>" + $.t(n.jobTitle) + "</td><td class='" + cs + "'>" + n.state.label + "</td><td class='" + cs + "'>"
				+ $.t(n.remark) + "</td></tr>";
	}

	// 渲染列表没有数据时的显示内容
	function renderNoDataTr() {
		return "<tr class='nodata'><td colspan='8' align='center'><div class='nomail'><b>无数据</b></div></td></tr>";
	}
	
	//验证添加表单
	addEntity('#form_add','#table_list','seller',renderDataTr,changeToolbarButtonState);
	
	//验证修改表单
	updateEntity('#form_update','seller',"#tr_",renderDataTr,changeToolbarButtonState);
	
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
					url : 'json/seller/success!update',
					data : {'seller.id':id,'seller.state':state},
					success : function(result) {
						if (result.success) {
							$('#tr_' + id).replaceWith(renderDataTr(result.seller,false));
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
	
	
	// 打开新增div
	$('#btn_show_add').click(function() {
		tb_show('新增销售员资料', '#TB_inline?height=340&width=700&inlineId=div_add', false);
	});

	// 打开查询div
	$('#btn_show_query').click(function() {
		tb_show('综合查询', '#TB_inline?height=280&width=370&inlineId=div_query', false);
	});

	// 触发查询按钮
	$('#btn_query').click(function() {
		tb_remove();
		findAllSellers('#page_infos', 0, false);
	});

	// 打开编辑div
	$('#btn_show_update').click(function() {
		var id = $('#table_list input[name="radio"]:checked').attr('sid');
		if (id) {
			$.waiting('#btn_update', true, '加载中...');
			$.ajax({
				type : "POST",
				url : 'json/seller/entity-load',
				dataType : "json",
				data : {"sellerId" : id},
				success : function(json) {
					if (json.success) {
						var obj = json.seller;
						for (field in obj) {
							var el = $("#form_update [name='seller." + field + "']");
							if(el.is('input:radio')){
								el.each(function(index,item){
									var $item = $(item);
									if($item.val() == obj[field]._name){
										$item.attr('checked','checked');
									}
								});
							}else{
								el.val(obj[field]);
							}
						}
					}
					$.waiting('#btn_update', false, '更新');
				}
			});
			$.clearValidationErrors($('#form_update'));
			tb_show('修改销售员资料', '#TB_inline?height=340&width=700&inlineId=div_update', false);
		} else {
			alert('请先选择要操作的行');
		}
	});

	// 根据当前选中的记录来设定工具栏按钮状态
	$('#table_list input[name="radio"]').live('click', function() {changeToolbarButtonState();});

	// 切换工具栏按钮的状态
	function changeToolbarButtonState() {
		var id = $('#table_list input[name="radio"]:checked').attr('sid');
		var state = $('#table_list input[name="radio"]:checked').attr('state');
		var v = id ? '' : 'disabled';

		$('#btn_show_update').attr('disabled', v);
		$('#btn_change_state').attr('disabled', v);

		switch(state){
			case "VALID":
				$('#btn_change_state').val('取消销售员');
				break;
			case "INVALID":
				$('#btn_change_state').val('激活销售员');
				break;
		}
	}
});
