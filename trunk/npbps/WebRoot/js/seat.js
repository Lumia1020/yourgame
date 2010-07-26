$(function () {
	
	//左列表
	$('.select_left').dblclick(function(){
		var selectLId = $(this).attr('id');
		var selectRId = selectLId.replace(/left/,'right');
		$.listTolist(selectLId,selectRId,"move",false);
	});
	
	//右列表
	$('.select_right').dblclick(function(){
		var selectRId = $(this).attr('id');
		var selectLId = selectRId.replace(/right/,'left');
		$.listTolist(selectRId,selectLId,"move",false);
	});
	
	//>
	$('.btn_right').click(function(){
		var selectLId = $(this).attr('sel');
		var selectRId = selectLId.replace(/left/,'right');
		$.listTolist(selectLId,selectRId,"move",false);
	});
	
	//>>
	$('.btn_right_all').click(function(){
		var selectLId = $(this).attr('sel');
		var selectRId = selectLId.replace(/left/,'right');
		$.listTolist(selectLId,selectRId,"move",true);
	});
	
	//<
	$('.btn_left').click(function(){
		var selectRId = $(this).attr('sel');
		var selectLId = selectRId.replace(/right/,'left');
		$.listTolist(selectRId,selectLId,"move",false);
	});
	
	//<<
	$('.btn_left_all').click(function(){
		var selectLId = $(this).attr('sel');
		var selectRId = selectLId.replace(/right/,'left');
		$.listTolist(selectLId,selectRId,"move",true);
	});
	
	//单击修改按钮
	$('.modify').click(function () {
        var id = $(this).attr('seat');
        $('#' + id + '_modify').removeClass("view");
        $('#' + id + '_view').addClass('view');
    });
    
    //单击更新按钮
    $('.update').click(function () {
        var id = $(this).attr('seat');
        var seatName = $('#' + id + '_input_seatname').val();
        var stationId = "";
        var stationName = "";
        var msg = "";
    	$("#" + id + "_right option").each(function(){
    		var value = $(this).val();
    		stationId += "'";
    		stationId += value;
    		stationId += "'."
    		
    		var text = $(this).text();
    		stationName += text;
    		stationName += "、";
    	});
    	if(seatName.length == 0 || seatName == ''){
    		msg += "台席名称不能为空!\n\n";
    	}
    	if(stationId.length == 0 || stationId == ''){
    		msg += "至少选择一个站.";
    	}
    	if(msg == ''){
    		stationId = stationId.substring(0,stationId.length - 1);
	        $.post("updateSeat.action", {
	            'seat.seatName': seatName,
	            'seat.stationId': stationId,
	            'seat.uuid': id
	        },
	        function (data) {
				if(data.success){
					stationName = stationName.substring(0,stationName.length -1);
		            $('#' + id + '_view_seatname').text(seatName);
		            $('#' + id + '_view_stationname').text(stationName);
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
    	var id = $(this).attr('seat');
    	$('#' + id + '_modify').addClass("view");
        $('#' + id + '_view').removeClass('view');
    });
    
    //单击删除按钮
    $('.delete').click(function () {
        if (confirm("确定删除?")) {
            var id = $(this).attr('seat');
            $.post("deleteSeat.action", {
                'seat.uuid': id
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
    $('#seat_add').click(function(){
    	var seatName = $.trim($('#seatName').val());
    	var stationId = "";
    	var msg = "";
    	$("#right option").each(function(){
    		var id = $(this).val();
    		stationId += "'";
    		stationId += id;
    		stationId += "'."
    	});
    	if(seatName.length == 0 || seatName == ''){
    		msg += "台席名称不能为空!\n\n";
    	}
    	if(stationId.length == 0 || stationId == ''){
    		msg += "至少选择一个站.";
    	}
    	if(msg == ''){
    		stationId = stationId.substring(0,stationId.length - 1);
	    	if(confirm("确认提交?")){
				 $.post("addSeat.action", {
	                'seat.seatName': seatName,
	                'seat.stationId': stationId
	            },
	            function (data) {
	            	if(data.success){
	        			alert("添加成功!");
	        			document.addSeatForm.reset();
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

