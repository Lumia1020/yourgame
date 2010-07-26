$(function () {
	
	
	//修改权限
	$(':checkbox').click(function(){
		var pid = $(this).attr('pid');
		var field = $(this).attr('field');
		var checked = $(this).attr('checked');
		if(pid){
			$.post('updatePermissions.action',{
				'page.params.field':field,
				'page.params.value':checked,
				'page.params.pid':pid
			},function(data){
				if(data.success){
				}else{
				}
			},'json');
		}
	});
	
	
	//打开权限修改窗口
	$('.permissions').click(function(){
		var pid = $(this).attr('pid');
		window.open('/npbps/page/showPermissions.action?permissions.pid=' + pid,'','toolbar=no,scrollbars=no,width=650,height=300');
	});
	
	
	//单击修改按钮
	$('.modify').click(function () {
        var id = $(this).attr('user');
        id = id == undefined ? $(this).attr('role') : id;
        $('#' + id + '_modify').removeClass("view");
        $('#' + id + '_view').addClass('view');
    });
    
    //单击更新按钮
    $('.update').click(function () {
        var id = $(this).attr('user');
        if(id == undefined){
         	id = $(this).attr('role');
         	var values = "";	//修改后选中复选框的value(权限菜单)
         	$("input[pid='" + id + "']:checked").each(function(){	//遍历指定pid的所有选中的复选框的value
         		values += $(this).val();
         		values += "、";
         	});
         	values = values.length > 0 ? values.substring(0,values.length - 1):values;
         	var roleName = $('#' + id + '_input_rolename').val();
         	$.post('updatePermissions.action',{
				'page.params.field':'roleName',
				'page.params.value':roleName,
				'page.params.pid':id
			},function(data){
				if(data.success){        	
		            $('#' + id + '_view_rolename').text(roleName);
		            $('#' + id + '_view_permissions').text(values);
		            $('#' + id + '_modify').addClass("view");
		            $('#' + id + '_view').removeClass('view');
				}else{
					alert("修改失败!");
				}
			},'json');
        }else{
	        var userName = $('#' + id + '_input_username').val();
	        var password = $('#' + id + '_input_password').val();
	        $.post("updateUser.action", {
	            'user.userName': userName,
	            'user.password': password,
	            'user.uuid': id
	        },
	        function (data) {
				if(data.success){        	
		            $('#' + id + '_view_username').text(userName);
		            $('#' + id + '_view_password').text(password);
		            $('#' + id + '_modify').addClass("view");
		            $('#' + id + '_view').removeClass('view');
				}else{
					alert("修改失败!");
				}
	        },'json');
        }
    });
    
    //单击取消按钮
    $('.cancel').click(function () {
    	var id = $(this).attr('user');
    	id = id == undefined ? $(this).attr('role') : id;
    	$('#' + id + '_modify').addClass("view");
        $('#' + id + '_view').removeClass('view');
    });
    
    //单击删除按钮
    $('.delete').click(function () {
        if (confirm("确定删除?")) {
            var id = $(this).attr('user');

            if(id == undefined){
            	id = id == undefined ? $(this).attr('role') : id;
            	$.post("deleteRole.action",{
            		'permissions.pid':id
            	},function(data){
            		if(data.success){
	        			$('#' + id + '_view').remove();
	        			alert("删除成功!");
					}else{
						alert("删除失败!");
					}
            	},'json');
            }else{
	            $.post("deleteUser.action", {
	                'user.uuid': id
	            },function(data){
            		if(data.success){
	        			$('#' + id + '_view').remove();
	        			alert("删除成功!");
					}else{
						alert("删除失败!");
					}
            	},'json');
            }
        }
    });
    
    $('#role_add').click(function(){
    	var roleName = $.trim($('#roleName').val());
    	var msg = "";
    	if(roleName.length == 0 || roleName == ''){
    		msg += "角色名称不能为空!";
    	}
    	if(msg == ''){
	    	if(confirm("确认提交?")){
				 $.post("addRole.action", {
				 	'permissions.roleName' : $('#roleName').val(),
				 	'permissions.seat' : $('#seat').attr('checked'),
	                'permissions.grid': $('#grid').attr('checked'),
	                'permissions.station': $('#station').attr('checked'),
	                'permissions.user': $('#user').attr('checked'),
	                'permissions.dept': $('#dept').attr('checked'),
	                'permissions.role': $('#role').attr('checked'),
	                'permissions.verification': $('#verification').attr('checked'),
	                'permissions.delivery': $('#delivery').attr('checked')
	            },
	            function (data) {
	            	if(data.success){
	        			alert("添加成功!");
	        			document.addRoleForm.reset();
					}else{
						alert("添加角色失败!");
					}
	            },'json');
			}
    	}else{
    		alert(msg);
    	}
    });
    
    //添加用户
    $('#user_add').click(function () {
    	var userName = $.trim($('#userName').val());
    	var password = $.trim($('#password').val());
    	var password2 = $.trim($('#password2').val());
    	var userId = $.trim($('#userId').val());
    	var dept = $('#dept').val();
    	var pid = $('#pid').val();
    	var role = $('#role').val();
    	var msg = '';
    	if(userName.length == 0 || userName == ''){
    		msg += "用户名不能为空!\n\n";
    	}
    	if(password.length == 0 || password == ''){
    		msg += "密码不能为空!\n\n";
    	}
    	if(password != password2) {
    		msg += "两次输入的密码不一致!";
    	}
    	if(msg == ''){
    		if(confirm("确认提交?")){
    			 $.post("addUser.action", {
    			 	'user.userId' : userId,
	                'user.userName': userName,
	                'user.password': password,
	                'user.pid': pid,
	                'user.role':role,
	                'user.seatId':'1',
	                'user.deptId': dept
	            },
	            function (data) {
	            	if(data.success){
	        			alert("添加成功!");
	        			document.addUserForm.reset();
					}else{
						alert("添加用户失败!\n\n" + data.infos.msg);
					}
	            },'json');
    		}
    	}else{
    		alert(msg);
    	}
    });
});

