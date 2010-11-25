/*＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
 * 
 * 所有模块的公共js类库，封装了通用的增加，更新的业务方法
 *
 * ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝*/


/**
 * 封装通用的新增业务方法，表单id命名遵守 
 * “form_add\form_user_add\form_role_add、form_update\form_user_update..、form_query\form_user_query.."等命名约定。
 * @param {String} form_id 对应新增数据的表单id，注意参数传递前面带 “＃” 前缀，例如 “#form_id”
 * @param {String} list_id 列表id，对应数据展示列表的id，例如 “#table_list”
 * @param {String} entity 设定校验错误结果中，预设的实体对象的变量名称如 “role、user” 等，通常对用Action中的 “private Role role;private User user;” 等
 * @param {Function} render_data_tr_callback 这是一个回调函数，用来对数据成功提交后，在当前列表中第一行插入一条记录
 * @param {Function} check_toolbar_callback 这是一个回调函数，用来对数据添加完成后，列表工具栏按钮的状态设定
 */
function addEntity(form_id,list_id,entity,render_data_tr_callback,check_toolbar_callback,render_details_head_callback) {
	$(form_id).submit(function() {
		var btn_add_id = form_id.replace(/form/g,'btn');	//获得表单新增按钮的id
		$.waiting(btn_add_id, true, '校验中....');
		var form = $(this); //当前新增数据的表单
		
		//发起ajax请求到后台对当前提交的表单进行数据的校验
		$.ajax( {
			type : 'post',
			url : form.attr('action'), //获得表单的action值
			data : form.serialize() + '&struts.validateOnly=true&struts.enableJSONValidation=true',	//序列化表单值到ajax校验的参数列表
			complete : function(request) {
				var errorsObject = $.getValidationErrors(request.responseText);	//获得校验反馈的json对象

				if (errorsObject.fieldErrors) {	//如果校验结果种包含了错误信息
					$.clearValidationErrors(form);	//清空表单现有的所有错误信息和文本框种错误提示的红色下划波浪线
					$.showValidationErrors($.getFormErrorContainer(form), errorsObject, '_add');	//显示新的校验错误的结果
					$.waiting(btn_add_id, false, '新增');
				} else {
					$.waiting(btn_add_id, true, '提交中....');
					
					//验证通过后，对数据表单进行ajax提交
					form.ajaxSubmit( {
						dataType : 'json',
						resetForm : true,
						success : function(json) {
							if (json.success) {	//表单成功提交后，将提交后的数据插入到列表中的第一行
								if(typeof(list_id) == 'object'){	//如果是对象表示是对details中增加记录
									var tr = $('input[name="radio"]:checked',list_id).parents('tr:first');
									var _list_id = tr.next('tr.details').find('table');
									if($.isFunction(render_details_head_callback)){
										if(_list_id.length == 0){
											tr.next('tr.details').replaceWith(render_details_head_callback());
										}
									}
									_list_id = tr.next('tr.details').find('table');
									if($.isFunction(render_data_tr_callback)){ 
										$("tbody",_list_id).append(render_data_tr_callback(json[entity], true));
									}
									$(_list_id).find('tr.nodata').remove();//默认移除 “无数据” 的提示行（如果有）
								}else{
									if($.isFunction(render_data_tr_callback)){ 
										$("tbody tr:first",list_id).before(render_data_tr_callback(json[entity], true));
									}
									$(list_id).find('tr.nodata').remove();//默认移除 “无数据” 的提示行（如果有）
								}
								
								tb_remove();
								if($.isFunction(check_toolbar_callback)){ 
									check_toolbar_callback(); //调用工具栏按钮状态检查函数
								}
								$.waiting(btn_add_id, false, '新增');
								$.clearValidationErrors(form); //清空表单中的错误信息
							}else if(json.jsons.messages){
								alert(json.jsons.messages);
							}
						}
					});
				}
			}
		});

		return false;
	});
}



/**
 * 封装通用的修改业务方法，表单id命名遵守 
 * “form_add\form_user_add\form_role_add、form_update\form_user_update..、form_query\form_user_query.."等命名约定。
 * 
 * @param {String} form_id 对应修改数据的表单id，注意参数传递前面带 “＃” 前缀，例如 “#form_id”
 * @param {String} entity 设定校验错误结果中，预设的实体对象的变量名称如 “role、user” 等，通常对用Action中的 “private Role role;private User user;” 等
 * @param {String} prefix talbe行的id前缀 '#tr_'  '#tr_details_'
 * @param {Function} render_data_tr_callback 这是一个回调函数，用来对数据成功提交后，在当前列表中第一行插入一条记录
 * @param {Function} check_toolbar_callback 这是一个回调函数，用来对数据修改完成后，列表工具栏按钮的状态设定
 */
function updateEntity(form_id,entity,prefix,render_data_tr_callback,check_toolbar_callback){
	$(form_id).submit(function() {
		var btn_update_id = form_id.replace(/form/g,'btn');
		
		$.waiting(btn_update_id, true, '校验中...');
		var form = $(this);
		$.ajax({
			type : 'post',
			url : form.attr('action'),
			data : form.serialize() + '&struts.validateOnly=true&struts.enableJSONValidation=true',
			complete : function(request) {
				var errorsObject = $.getValidationErrors(request.responseText);

				if (errorsObject.fieldErrors) {
					$.clearValidationErrors(form);
					$.showValidationErrors($.getFormErrorContainer(form), errorsObject, '_update');
					$.waiting(btn_update_id, false, '更新');
				} else {
					$.waiting(btn_update_id, true, '更新中...');
					form.ajaxSubmit({
						dataType : 'json',
						resetForm : true,
						success : function(json) {
							if (json.success) {
								var obj = json[entity];
								$(prefix + obj.id).replaceWith(render_data_tr_callback(obj, true));
							}
							tb_remove();
							check_toolbar_callback();
							$.waiting(btn_update_id, false, '更新');
							$.clearValidationErrors(form);
						}
					});
				}
			}
		});
		return false;
	});
}

/**
 * 删除列表数据的通用方法
 * @param {String} btn_delete_id 要绑定的删除按钮id
 * @param {String} list_id 数据展示的列表id
 * @param {String} radio_selector radio按钮的选择器 input[name="radio"]:checked input[name="radio_details"]:checked
 * @param {String} prefix talbe行的id前缀 '#tr_'  '#tr_details_'
 * @param {String} action_url 请求删除数据的action
 * @param {String} id_name 实体对象的id传递到后台作为删除条件的那个参数名字
 * @param {Function} check_toolbar_callback 这是一个回调函数，在删除数据后调整列表工具栏的状态。
 * @param {Function} render_nodata_tr_callback 这是一个回调函数，当列表数据被删光的时候渲一行 “无数据” 的tr
 * @param {Function} show_error_callback 这个是一个回调函数，当删除失败后，自定义显示提示给用户的错误信息
 */
function deleteEntity(btn_delete_id,list_id,radio_selector,prefix,action_url,id_name,check_toolbar_callback,render_nodata_tr_callback,show_error_callback){
	$(btn_delete_id).click(function() {
		var id = $(radio_selector,list_id).attr('sid');
		var table = null;
		if(typeof(list_id) == 'object'){	//如果是对象表示是对details中增加记录
			table = $(radio_selector,list_id).parents('table:first');
		}else{
			table = $(list_id);
		}
		if (id) {
			if (confirm('确定删除吗？')) {
				$.ajax({
					type : "POST",
					dataType : "json",
					url : action_url,
					data : '&' + id_name + '=' + id,
					success : function(result) {
						if (result.success) {
							$(prefix + id).remove();
							if($.isFunction(check_toolbar_callback)){
								check_toolbar_callback();
							}
							if($.isFunction(render_nodata_tr_callback)){
								if(typeof(list_id) == 'object'){	//如果是对象表示是对details中增加记录
									table = $('input[name="radio"]:checked',list_id).parents('tr:first').next('tr.details').find('table');
								}else{
									table = $(list_id);
								}
								if (table.find('tbody tr').size() == 0) {
									if(typeof(list_id) == 'object'){
										table.parents('tr.details').replaceWith(render_nodata_tr_callback())
									}else{
										$('tbody',table).html(render_nodata_tr_callback());
									}
								}
							}
						}else if(result.jsons){
							if($.isFunction(show_error_callback)){
								show_error_callback(result.jsons);
							}
						}
					}
				});
			}
		} else {
			alert('请先选择要删除的行!');
		}
	});
}


