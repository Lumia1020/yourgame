$(function () {
	
	//单击修改按钮
	$('.modify').click(function () {
        var id = $(this).attr('station');
        $('#' + id + '_modify').removeClass("view");
        $('#' + id + '_view').addClass('view');
    });
    
    //单击更新按钮
    $('.update').click(function () {
        var id = $(this).attr('station');
        var stationName = $('#' + id + '_input_stationname').val();
        var sectionName = $('#' + id + '_input_sectionname').val();
    	var msg = "";
    	if(stationName.length == 0 || stationName == ''){
    		msg += "站名称不能为空!\n\n";
    	}
    	if(sectionName.length == 0 || sectionName == ''){
    		msg += "段名称不能为空!";
    	}
    	if(msg == ''){
    		$.post("updateStation.action", {
	            'station.stationName': stationName,
	            'station.sectionName': sectionName,
	            'station.uuid': id
	        },
	        function (data) {
				if(data.success){        	
		            $('#' + id + '_view_stationname').text(stationName);
		            $('#' + id + '_view_sectionname').text(sectionName);
		            $('#' + id + '_modify').addClass("view");
		            $('#' + id + '_view').removeClass('view');
				}else{
					alert("修改失败!");
				}
	        },'json');
    	}else{
    		alert(msg);
    	}
        
    });
    
    //单击取消按钮
    $('.cancel').click(function () {
    	var id = $(this).attr('station');
    	$('#' + id + '_modify').addClass("view");
        $('#' + id + '_view').removeClass('view');
    });
    
    //单击删除按钮
    $('.delete').click(function () {
        if (confirm("确定删除?")) {
            var id = $(this).attr('station');
            $.post("deleteStation.action", {
                'station.uuid': id
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
    $('#station_add').click(function(){
    	var stationName = $.trim($('#stationName').val());
    	var sectionName = $.trim($('#sectionName').val());
    	var msg = "";
    	if(stationName.length == 0 || stationName == ''){
    		msg += "站名称不能为空!\n\n";
    	}
    	if(sectionName.length == 0 || sectionName == ''){
    		msg += "段名称不能为空!";
    	}
    	if(msg == ''){
	    	if(confirm("确认提交?")){
				 $.post("addStation.action", {
	                'station.stationName': stationName,
	                'station.sectionName': sectionName
	            },
	            function (data) {
	            	if(data.success){
	        			alert("添加成功!");
	        			document.addStationForm.reset();
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

