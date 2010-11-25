var d = new dTree('d');

$(function() {

	// 创建分页组件,创建完毕后执行分页查询
	createPagination('#page_infos', [15, 20, 10, 5], findAllRoles, true);

	/**
	 * 获得分页数据
	 * 
	 * @param wrap_id  分页组件渲染的id
	 * @param page_no  分页开始页码
	 * @param init   是否渲染页码
	 * @param callback  渲染页码的回调函数
	 */
	function findAllRoles(wrap_id, page_no, init, callback) {
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
	 * @param n  行数据
	 * @param b  是否给行加上选中的背景样式
	 */
	function renderDataTr(n, b) {
		return "<tr id='tr_" + n.id + "' class='i M " + (b ? 'B' : '') + "'><td class='cx'><input type='radio' name='radio' sid='" + n.id + "'/></td><td class='tf'>"
				+ $.t(n.roleName) + "</td><td class='tf'>" + $.t(n.remark) + "</td></tr>";
	}

	// 渲染列表没有数据时的显示内容
	function renderNoDataTr() {
		return "<tr class='nodata'><td colspan='3' align='center'><div class='nomail'><b>无数据</b></div></td></tr>";
	}

	// 验证添加表单
	addEntity('#form_add','#table_list','role',renderDataTr,changeToolbarButtonState);

	// 验证修改表单
	updateEntity('#form_update','role',"#tr_",renderDataTr,changeToolbarButtonState);

	// 删除角色
	deleteEntity('#btn_delete','#table_list','input[name="radio"]:checked','#tr_','json/role/success!delete','roleId',changeToolbarButtonState,renderNoDataTr,function(jsons){
		if(jsons.users){
			alert(jsons.messages + '当前角色关联了如下用户： [' + jsons.users[0].CHINESENAME + ']');
		}
	});
	
	// 打开新增div
	$('#btn_show_add').click(function() {
		tb_show('新增用户', '#TB_inline?height=260&width=370&inlineId=div_add', false);
	});

	// 打开查询div
	$('#btn_show_query').click(function() {
		tb_show('综合查询', '#TB_inline?height=260&width=370&inlineId=div_query', false);
	});

	// 触发查询按钮
	$('#btn_query').click(function() {
		tb_remove();
		findAllRoles('#page_infos', 0, false);
	});


	// 打开编辑div
	$('#btn_show_update').click(function() {
		var id = $('#table_list input:checked').attr('sid');
		if (id) {
			$.waiting('#btn_update', true, '加载中...');
			$.ajax({
				type : "POST",
				url : 'json/role/entity-load',
				dataType : "json",
				data : {"roleId" : id},
				success : function(json) {
					if (json.success) {
						var obj = json.role;
						for (field in obj) {$("#form_update [name='role." + field + "']").val(obj[field]);}
					}
					$.waiting('#btn_update', false, '更新');
				}
			});
			$.clearValidationErrors($('#form_update'));
			tb_show('修改角色', '#TB_inline?height=260&width=370&inlineId=div_update', false);
		} else {
			alert('请先选择要操作的行');
		}
	});

	// 根据当前选中的记录来设定工具栏按钮状态
	$('#table_list input[name="radio"]').live('click', function() {changeToolbarButtonState();});

	// 切换工具栏按钮的状态
	function changeToolbarButtonState() {
		var id = $('#table_list input[name="radio"]:checked').attr('sid');
		var v = id ? '' : 'disabled';

		$('#btn_show_update').attr('disabled', v);
		$('#btn_delete').attr('disabled', v);
		$('#btn_show_role_setup').attr('disabled', v);
	}

	// -------------权限管理-----------------
	// 折叠权限树菜单
	$('#btn_tree_toggle').toggle(function() {
		$(this).val('展开树');
		d.closeAll();
	}, function() {
		$(this).val('折叠树');
		d.openAll();
	});
	
	// 权限设置
	$('#btn_show_role_setup').click(function() {
		var id = $('#table_list input[name="radio"]:checked').attr('sid');
		if (id) {
			$.ajax({
				type : "POST",
				url : 'json/role/map!findRolePermission',
				dataType : "json",
				data : {
					"roleId" : id
				},
				success : function(json) {
					var apl = json.allPermissionList;
					var rpl = json.rolePermissionList;
					if (apl.length > 0) {
						d.add(0, -1, '角色权限设置', 'javascript:void(0);');
						for (var i = 0; i < apl.length; i++) {
							var obj = apl[i];
							d.add(obj.ID, (obj.PARENT_ID == null ? 0 : obj.PARENT_ID), 'permissionIds', obj.ID, obj.NAME, false, false, 'javascript:void(0);', obj.NAME);
						}
						$('#div_role_tree').empty().html(d.toString());
						d.openAll();
						$.each(rpl, function() {
							$('#permissionIds_' + this.PERMISSION_ID).attr('checked', 'checked');
						});
					}
					tb_show('修改角色的权限', '#TB_inline?height=450&width=270&inlineId=div_role', false);
				}
			});

		} else {
			alert('请先选择要操作的行');
		}
	});

	// 设置修改权限
	$('#btn_cancel').click(function() {tb_remove();});
	$('#btn_setup').click(function() {
		$.waiting('#btn_setup', true, '修改中...');
		var id = $('#table_list input[name="radio"]:checked').attr('sid');
		if (id) {
			$("#form_update_role_permission").ajaxSubmit({
				dataType : 'json',
				url : 'json/role/success!updateRolePermissions?&p=1&p=2&roleId=' + id,
				success : function(n) {
					if (n.success) {
						alert('设置成功!');
						$.waiting('#btn_setup', false, '设置');
					}
					tb_remove();
				}
			});
		}
	});

});
