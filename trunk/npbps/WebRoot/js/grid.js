$(function () {
	
	//单击修改按钮
	$('.modify').click(function () {
        var id = $(this).attr('grid');
        $('#' + id + '_modify').removeClass("view");
        $('#' + id + '_view').addClass('view');
    });
    
    //单击更新按钮
    $('.update').click(function () {
        var id = $(this).attr('grid');
        var area = $('#' + id + '_select_area').val();
        var stationId = $('#' + id + '_select_stationid').val();
        var sectionId = $('#' + id + '_select_sectionid').val();
        var seatId = $('#' + id + '_select_seatid').val();
        
        var stationName = $('#' + id + '_select_stationid option:selected').text();
        var sectionName = $('#' + id + '_select_sectionid option:selected').text();
        var seatName = $('#' + id + '_select_seatid option:selected').text();
        
        $.post("updateGrid.action", {
            'grid.stationId': stationId,
            'grid.stationId': stationId,
            'grid.seatId': seatId,
            'grid.area': area,
            'grid.uuid': id
        },
        function (data) {
			if(data.success){        	
	            $('#' + id + '_view_area').text(area);
	            $('#' + id + '_view_stationname').text(stationName);
	            $('#' + id + '_view_sectionname').text(sectionName);
	            $('#' + id + '_view_seatname').text(seatName);
	            $('#' + id + '_modify').addClass("view");
	            $('#' + id + '_view').removeClass('view');
			}else{
				alert("修改失败!");
			}
        },'json');
    });
    
    //单击取消按钮
    $('.cancel').click(function () {
    	var id = $(this).attr('grid');
    	$('#' + id + '_modify').addClass("view");
        $('#' + id + '_view').removeClass('view');
    });
    
    //单击删除按钮
    $('.delete').click(function () {
        if (confirm("确定删除?")) {
            var id = $(this).attr('grid');
            $.post("deleteGrid.action", {
                'grid.uuid': id
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
    
    //添加
    $('#grid_add').click(function(){ 
    	var area = $('#area').val();
    	var stationId = $('#stationId').val();
    	var sectionId = $('#sectionId').val();
    	var seatId = $('#seatId').val();
    	
    	var msg = "";
    	if(area.length == 0 || area == ''){
    		msg += "片区名称不能为空!\n\n";
    	}
    	if(stationId.length == 0 || stationId == ''){
    		msg += "站名称不能为空!\n\n";
    	}
    	if(sectionId.length == 0 || sectionId == ''){
    		msg += "段名称不能为空!\n\n";
    	}
    	if(seatId.length == 0 || seatId == ''){
    		msg += "台席名称不能为空!";
    	}
    	
    	if(msg == ''){
	    	if(confirm("确认提交?")){
				$.post("addGrid.action", {
	                'grid.stationId': stationId,
	                'grid.sectionId': sectionId,
	                'grid.area':area,
	                'grid.seatId':seatId
	            },
	            function (data) {
	            	if(data.success){
	        			alert("添加成功!");
	        			document.addGridForm.reset();
					}else{
						alert("添加失败!");
					}
	            },'json');
			}
    	}else{
    		alert(msg);
    	}
    });
});

