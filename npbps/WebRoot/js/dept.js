$(function () {
	
	//单击修改按钮
	$('.modify').click(function () {
        var id = $(this).attr('dept');
        $('#' + id + '_modify').removeClass("view");
        $('#' + id + '_view').addClass('view');
    });
    
    //单击更新按钮
    $('.update').click(function () {
        var id = $(this).attr('dept');
        var deptName = $('#' + id + '_input_deptname').val();
        $.post("updateDept.action", {
            'dept.deptName': deptName,
            'dept.uuid': id
        },
        function (data) {
			if(data.success){        	
	            $('#' + id + '_view_deptname').text(deptName);
	            $('#' + id + '_modify').addClass("view");
	            $('#' + id + '_view').removeClass('view');
			}else{
				alert("修改失败!");
			}
        },'json');
    });
    
    //单击取消按钮
    $('.cancel').click(function () {
    	var id = $(this).attr('dept');
    	$('#' + id + '_modify').addClass("view");
        $('#' + id + '_view').removeClass('view');
    });
    
    //单击删除按钮
    $('.delete').click(function () {
        if (confirm("确定删除?")) {
            var id = $(this).attr('dept');
            $.post("deleteDept.action", {
                'dept.uuid': id
            },function(data){
        		if(data.success){
        			$('#' + id + '_view').remove();
        			alert("删除成功!");
				}else{
					alert("删除失败!");
				}
        	},'json');
        }
    });
    
    //添加部门
    $('#dept_add').click(function(){
    	var deptName = $.trim($('#deptName').val());
    	var msg = "";
    	if(deptName.length == 0 || deptName == ''){
    		msg += "部门名称不能为空!";
    	}
    	if(msg == ''){
	    	if(confirm("确认提交?")){
				 $.post("addDept.action", {
	                'dept.deptName': deptName
	            },
	            function (data) {
	            	if(data.success){
	        			alert("添加成功!");
	        			document.addDeptForm.reset();
					}else{
						alert("添加部门失败!");
					}
	            },'json');
			}
    	}else{
    		alert(msg);
    	}
    });
});

