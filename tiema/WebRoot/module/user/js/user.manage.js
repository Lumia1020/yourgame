$(function() {
	
	//$('.legend_set_role').data('height',150).click();
	$('.legend_other_user_infos').data('height',290).click();
	
	//创建分页组件,创建完毕后执行分页查询
	createPagination('#page_infos',[15,20,10,5],findAllUsers,true);
	
	/**
	 * 获得分页数据
	 * 
	 * @param wrap_id 分页组件渲染的id
	 * @param page_no 分页开始页码
	 * @param init 是否渲染页码
	 * @param callback 渲染页码的回调函数
	 */
	function findAllUsers(wrap_id,page_no,init,callback) {
		var page_size = eval($('select.page_select_jump',wrap_id).val());
		$('#form_query').ajaxSubmit({
			dataType : "json",
			data : {'page.pageNo' : (page_no || 0) + 1,'page.pageSize' : page_size},
			success : function(json) {
				if(json.success){ 
					var datas = json.result;
					var tbody = "";
					for (var i = 0; i < datas.length; i++) {tbody += renderDataTr(datas[i],false);}
					if (datas.length == 0) {tbody = renderNoDataTr();}
					$("#table_list > tbody").html(tbody);
					if (init) {callback(wrap_id,json.totalCount,json.pageNo - 1,page_size);}
					changeToolbarButtonState();
				}
			}
		});
	}

	
	/**
	 * 渲染行
	 * @param n 行数据
	 * @param b 是否给行加上选中的背景样式
	 */
	function renderDataTr(n,b) {
		var id = n.id;
		var chineseName = $.t(n.chineseName);
		var username = n.username;
		var sex = n.sex.label;
		var jobTitle = $.t(n.jobTitle);
		var mobile = $.t(n.mobile);
		var stateName = n.state.label;
		var roleNames = $.t(n.roleNames);
		var remark = $.t(n.remark);
		
		return "<tr id='tr_" + id + "' class='i M " + (b ? 'B' : '') + "'><td class='cx'><input type='radio' name='radio' sid='" + id + "'/></td><td class='tf' title="
				+ chineseName + ">" + chineseName + "</td><td class='tf' title=" + username + ">" + username + "</td><td class='tf' title=" + sex + ">" + sex
				+ "</td><td class='tf' title=" + jobTitle + ">" + jobTitle + "</td><td class='tf' title=" + mobile + ">" + mobile + "</td><td class='tf' title=" + stateName + ">"
				+ stateName + "</td><td class='tf' title=" + roleNames + ">" + roleNames + "</td><td class='tf' title=" + remark + ">" + remark + "</td></tr>";
	}
	
	//渲染列表没有数据时的显示内容
	function renderNoDataTr(){
		return "<tr class='nodata'><td colspan='9' align='center'><div class='nomail'><b>无数据</b></div></td></tr>";
	}
	
	//验证添加表单
	addEntity('#form_add','#table_list','userView',renderDataTr,changeToolbarButtonState);
	
	//验证修改表单
	updateEntity('#form_update','userView',"#tr_",renderDataTr,changeToolbarButtonState);
	
	//删除记录
	deleteEntity('#btn_delete','#table_list','input[name="radio"]:checked','#tr_','json/user/success!delete','userId',changeToolbarButtonState,renderNoDataTr,function(jsons){
		if(jsons.messages){
			alert(jsons.messages);
		}
	});
	
	//打开新增div
	$('#btn_show_add').click(function(){
		tb_show('新增用户','#TB_inline?height=450&width=700&inlineId=div_add',false);		
	});
	
	//打开查询div
	$('#btn_show_query').click(function(){
		tb_show('综合查询','#TB_inline?height=220&width=370&inlineId=div_query',false);
	});
	
	//触发查询按钮
	$('#btn_query').click(function(){
		tb_remove();
		findAllUsers('#page_infos',0,false);
	});
	
	
	//打开编辑div
	$('#btn_show_update').click(function(){
		var id = $('#table_list input[name="radio"]:checked').attr('sid');
		if (id) {
			$.waiting('#btn_update',true,'加载中...');
			$.ajax({
				type : "POST",
				url : 'json/user/entity!load',
				dataType : "json",
				data : {"userId" : id},
				success : function(json) {
					if (json.success) {
						var obj = json.userView;
						for(field in obj){
							var el = $("#form_update [name='user." + field + "']");
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
						$('#user_password2_update').val(obj['password']);
						if(obj.roleIds){
							$.each(obj.roleIds.split(','),function(i,item){
								$('#div_roles_update input:checkbox.checkitem[value=' + item + ']').attr('checked','checked');
							});
						}
					}
					$.waiting('#btn_update',false,'更新');
				}
			});
			$.clearValidationErrors($('#form_update'));
			tb_show('修改用户','#TB_inline?height=450&width=700&inlineId=div_update',false);
		} else {
			alert('请先选择要操作的行');
		}
	});
	
	//根据当前选中的记录来设定工具栏按钮状态
	$('#table_list input[name="radio"]').live('click',function(){changeToolbarButtonState();});
	
	//切换工具栏按钮的状态
	function changeToolbarButtonState(){
		var id = $('#table_list input[name="radio"]:checked').attr('sid');
		var v = id ? '':'disabled';
		
		$('#btn_show_update').attr('disabled',v);
		$('#btn_delete').attr('disabled',v);
	}
});

