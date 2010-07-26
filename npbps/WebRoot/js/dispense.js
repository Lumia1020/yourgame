$(function(){
	$('#select_toggle').click(function(){$("#checkboxs").toggleCheckboxes();});
	$('#select_all').click(function(){$("#checkboxs").checkCheckboxes();});
	$('#update_flag').click(function(){
		var uuids = "";
		var start = $(this).attr('start');
		var limit = $(this).attr('limit');
		start = (eval(start) - limit );
		$("input[type=checkbox]:checked").each(function(){
			uuids += "'";
			uuids += $(this).attr('uuid');
			uuids += "',";
		});
		if(uuids != '' && uuids.length > 0){
			uuids = uuids.substring(0,uuids.length - 1);
			$.post("updateFlags.action", {
	            'page.params.uuids': uuids,
	            'page.params.value':1
	        },
	        function (data) {
				if(data.success){
					alert('接收成功!')
					var action = document.dispenseForm.action;//http://localhost:8080/npbps/page/showDispenses.action?page.start=0&page.limit=10
					document.dispenseForm.action = action.replace('start=0','start=' + start);
					document.dispenseForm.submit();
				}else{
					alert("接收失败!");
				}
	        },'json');
		}
	});
	
	$('#_print').click(function(){
		var startDate = $('#start_date').val();
		var endDate = $('#end_date').val();
		var flag = $('#flag').val();
		var url = '/npbps/page/printGetData.action?page.limit=0&page.params.flag=' + flag + '&page.params.startDate=' + startDate + 'page.params.endDate=' + endDate;
		window.open(url,'','toolbar=yes,scrollbars=yes,width=800,height=600');
	});
	
	$('#start_date').datepicker();
	$('#end_date').datepicker();
	
});