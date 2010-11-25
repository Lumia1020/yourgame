/**
 * This jQuery plugin displays pagination links inside the selected elements.
 *
 * @author Gabriel Birke (birke *at* d-scribe *dot* de)
 * @version 1.1
 * @param {int} maxentries Number of entries to paginate
 * @param {Object} opts Several options (see README for documentation)
 * @return {Object} jQuery Object
 */
jQuery.fn.pagination = function(maxentries, opts){
	opts = jQuery.extend({
		items_per_page:10,
		num_display_entries:10,
		current_page:0,
		num_edge_entries:0,
		link_to:"javascript:void(0);",
		first_text:"First",//Radys Add this link  more info  http://radys.cn
		last_text:"Last", //Radys Add this link
		prev_text:"Prev",
		next_text:"Next",
		ellipse_text:"...",
		prev_show_always:true,
		next_show_always:true,
		callback:function(){return false;}
	},opts||{});
	
	return this.each(function() {
		/**
		 * Calculate the maximum number of pages
		 */
		function numPages() {
			return Math.ceil(maxentries/opts.items_per_page);
		}
		
		/**
		 * Calculate start and end point of pagination links depending on 
		 * current_page and num_display_entries.
		 * @return {Array}
		 */
		function getInterval()  {
			var ne_half = Math.ceil(opts.num_display_entries/2);
			var np = numPages();
			var upper_limit = np-opts.num_display_entries;
			var start = current_page>ne_half?Math.max(Math.min(current_page-ne_half, upper_limit), 0):0;
			var end = current_page>ne_half?Math.min(current_page+ne_half, np):Math.min(opts.num_display_entries, np);
			return [start,end];
		}
		
		/**
		 * This is the event handling function for the pagination links. 
		 * @param {int} page_id The new page number
		 */
		function pageSelected(page_id, evt){
			current_page = page_id;
			drawLinks();
			var continuePropagation = opts.callback(page_id, panel);
			if (!continuePropagation) {
				if (evt.stopPropagation) {
					evt.stopPropagation();
				}
				else {
					evt.cancelBubble = true;
				}
			}
			return continuePropagation;
		}
		
		/**
		 * This function inserts the pagination links into the container element
		 */
		function drawLinks() {
			panel.empty();			
			var interval = getInterval();
			var np = numPages();
			$('div.page_number_info',$(opts.wrapId)).text(current_page + 1 + ' / ' + np + ' 页');
			// This helper function returns a handler function that calls pageSelected with the right page_id
			var getClickHandler = function(page_id) {
				return function(evt){ return pageSelected(page_id,evt); }
			}
			// Helper function for generating a single link (or a span tag if it'S the current page)
			var appendItem = function(page_id, appendopts){
				page_id = page_id<0?0:(page_id<np?page_id:np-1); // Normalize page id to sane value
				appendopts = jQuery.extend({text:page_id+1, classes:""}, appendopts||{});
				if(page_id == current_page){
					var lnk = $("<span class='current'>"+(appendopts.text)+"</span>");
				}
				else
				{
					var lnk = $("<a>"+(appendopts.text)+"</a>")
						.bind("click", getClickHandler(page_id))
						.attr('href', opts.link_to.replace(/__id__/,page_id));												
				}
				if(appendopts.classes){lnk.addClass(appendopts.classes);}
				panel.append(lnk);
			}

            // Radys Add
			// Generate "First"-Link
			if(opts.first_text && (current_page > 0 || opts.prev_show_always)){
				appendItem(0,{text:opts.first_text, classes:"prev"});
			}

			// Generate "Previous"-Link
			if(opts.prev_text && (current_page > 0 || opts.prev_show_always)){
				appendItem(current_page-1,{text:opts.prev_text, classes:"prev"});
			}
			
			// Generate "Next"-Link
			if(opts.next_text && (current_page < np-1 || opts.next_show_always)){
				appendItem(current_page+1,{text:opts.next_text, classes:"next"});
			}

            // Radys Add
			// Generate "Last"-Link
			if(opts.last_text && (current_page < np-1 || opts.next_show_always)){
				appendItem(np-1,{text:opts.last_text, classes:"next"});
			}
			// Generate starting points
			if (interval[0] > 0 && opts.num_edge_entries > 0)
			{
				var end = Math.min(opts.num_edge_entries, interval[0]);
				for(var i=0; i<end; i++) {
					appendItem(i);
				}
				if(opts.num_edge_entries < interval[0] && opts.ellipse_text)
				{
					jQuery("<span>"+opts.ellipse_text+"</span>").appendTo(panel);
				}
			}
			// Generate interval links
			for(var i=interval[0]; i<interval[1]; i++) {
				appendItem(i);
			}
			// Generate ending points
			if (interval[1] < np && opts.num_edge_entries > 0)
			{
				if(np-opts.num_edge_entries > interval[1]&& opts.ellipse_text)
				{
					jQuery("<span>"+opts.ellipse_text+"</span>").appendTo(panel);
				}
				var begin = Math.max(np-opts.num_edge_entries, interval[1]);
				for(var i=begin; i<np; i++) {
					appendItem(i);
				}
				
			}
			

		}
		
		// Extract current_page from options
		var current_page = opts.current_page;
		// Create a sane value for maxentries and items_per_page
		maxentries = (!maxentries || maxentries < 0)?1:maxentries;
		opts.items_per_page = (!opts.items_per_page || opts.items_per_page < 0)?1:opts.items_per_page;
		// Store DOM element for easy access from all inner functions
		var panel = jQuery(this);
		// Attach control functions to the DOM element 
		this.selectPage = function(page_id){ pageSelected(page_id);}
		this.prevPage = function(){ 
			if (current_page > 0) {
				pageSelected(current_page - 1);
				return true;
			}
			else {
				return false;
			}
		}
		this.nextPage = function(){ 
			if(current_page < numPages()-1) {
				pageSelected(current_page+1);
				return true;
			}
			else {
				return false;
			}
		}
		// When all initialisation is done, draw the links
		drawLinks();
	});
}


/**
 * 初始化分页组件,这个方法是对分页的jquery插件做了进一步的封装,增极了跳转分页的两种方式
 * @param wrapId 需要创建分页组件的页面元素
 * @param arr_page_numbers 分页组件第一个页面下拉框中标量每页多少条记录的数组
 * @param callback 查询数据进行分页的函数
 * @param immediately_execute_callback 是否在初始化分页组件后立马执行数据的查询
 */
function createPagination(wrapId,arr_page_numbers,callback,immediately_execute_callback){
	var paginationHtml = ''; //分页组件html组装
	paginationHtml += '<div class="page_number_info right"></div>';
	paginationHtml += '<div class="nowrap left">';
	paginationHtml += '<div class="left" style="padding:4px 5px 0 0">';
	paginationHtml += '<select class="page_select_jump">';
	for(var i = 0; i < arr_page_numbers.length; i++){
		paginationHtml += '<option value="' + arr_page_numbers[i] + '">每页' + arr_page_numbers[i] + '条</option>';
	}
	paginationHtml += '</select>';
	paginationHtml += '</div>';
	paginationHtml += '<div class="pagination left" style="padding-top: 5px;"></div>';
	paginationHtml += '<div style="padding:4px 5px 0 5px;" class="left">';
	paginationHtml += '<span class="current prev">跳转至</span>';
	paginationHtml += '<input type="text" class="page_input_jump" style="width: 28px;height:13px"/>页<input type="hidden" name="maxNo"/>';
	paginationHtml += '</div>';
	paginationHtml += '</div><div class="clr">&nbsp;</div>';
	
	$(wrapId).html(paginationHtml);//将组装的html实例化到指定的页面元素中
	
	//banging分页记录数的下拉框跳转事件
	$(wrapId).find('select.page_select_jump').change(function(){
		callback(wrapId,0,true,renderPagination);
	});
	
	$(wrapId).find('input.page_input_jump').keyup(function(event){
		if(event.keyCode == 13){
			var page_no = eval($(this).val());
			var max_no = eval($('input[name="maxNo"]',wrapId).val());
			page_no = page_no <= 0 ? 1:page_no;
			page_no = page_no >= max_no ? max_no:page_no;
			callback(wrapId,page_no - 1, true,renderPagination);
			$(this).val(page_no).select();
		}
	});
	
	//判断是否立即执行回调函数进行分页数据的查询
	if(immediately_execute_callback){callback(wrapId,0,true,renderPagination);}
	
	/*
	 * 渲染分页号码
	 */
	function renderPagination(wrapId,total_count,current_page,page_size){
		$('input[name="maxNo"]',wrapId).val(Math.ceil(total_count/page_size));
		$('div.pagination',wrapId).pagination(total_count, {
			first_text : "&nbsp;  «  &nbsp;",
			prev_text : "&nbsp; ‹ &nbsp;",
			next_text : "&nbsp; › &nbsp;",
			last_text : "&nbsp; » &nbsp;",
			current_page : current_page || 0,
			items_per_page : page_size || 10,
			num_edge_entries : 3,
			num_display_entries : 4,
			wrapId:wrapId,
			callback : function(page_no) {
				callback(wrapId,page_no, false,renderPagination);
			}
		});
	}
}


