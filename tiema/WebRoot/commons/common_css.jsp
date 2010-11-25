<style type="text/css">
	/*列表和分页*/
	table.O3{border-top:1px solid #FFF;color:#3B3B3B;}
	table.O3 tbody td{overflow:hidden;white-space:nowrap;}
	table.O3 thead th,table.O3 thead td{background-color:#F2F4F6;border-bottom:1px solid #C1C8D2;padding-top:2px;cursor: pointer;}
	tr.i td.cx{vertical-align:top;width:24px;padding:3px 0 1px 5px;}
	.toarea .F,.toarea .M{border-bottom-width:1px;}
	.toarea tr:hover{background-color:#f3f3f3;}
	tr.details:hover{background-color: white;}
	td.details_line{background: url('images/detail_line.gif') no-repeat 37px 0;}
	th.settingtd{padding-left: 0}
	tr.M td{border-bottom:1px solid #E3E6EB;clear:both;font-weight:400;padding-left:13px;height:24px;}
	tr.i{table-layout:fixed;width:100%;}
	#paginations a,#paginations span{display:block;float:left;margin-right:5px;margin-bottom:5px;padding:0 0.5em;}
	
	
	/*表单*/
	div.form-container{background-color:#FFF;color:#222;margin:10px;/*padding:5px;*/}
	div.form-container form fieldset div{padding:0.25em 0;}
	div.form-container form legend{color:#666;font-weight:700;cursor: pointer;}
	div.form-container label.label em,div.form-container span.label em{color:#C00;font-size:120%;font-style:normal;right:0;}
	div.form-container label.label,div.form-container span.label{display:block;float:left;margin-right:5px;text-align:right;width:100px;}
	div.form-container .input, div.form-container select, div.form-container textarea {padding:4px;}
	.input{width:180px}
	
	input.error,select.error,textarea.error{background: #FFFFFF url("<%=request.getContextPath()%>/images/invalid_line.gif") repeat-x scroll center bottom;border-color:#CC3300 !important;}
	.cmxform fieldset p.error label{color:red;}
	.container label.error{display:inline;}
	.ubb li{cursor:pointer;float:left;list-style-type:none;margin:0 3px 3px 0;padding:3px 5px;text-align:center;}
	div.container{display:none;margin:10px;padding:10px;}
	div.container ol li{list-style-type:disc;margin-left:20px;}
	form.cmxform label.error{display:block;margin-left:1em;width:auto;}
	form.cmxform label.error,label.error{color:red;/*font-style:italic;*/}
	h1,h2,h3,h4,h5,h6,ul,ol,dl,li,dt,dd{margin:0;padding:0;}
	h4{font-weight:700;}
	
	
	/*双排下拉框*/
	fieldset.comboselect{border:0 !important;display:block;margin:0 0 10px 0!important;}
	fieldset.comboselect *{display:block;float:left;width:40%;}
	fieldset.comboselect select{height:100px;}
	fieldset.comboselect fieldset{border:0 !important;width:10%;height:100px;padding:25px 10px 10px;}
	fieldset.comboselect fieldset input{width:100%;clear:left;margin:5px 0;}
	fieldset.comboselect select option{width:100% !important;border:0;border-bottom:1px dotted #959DB7;}
</style>
