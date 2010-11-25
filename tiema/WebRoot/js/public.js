function openwindow(url, title, width, Height, menubar, toolbar, location, scrollbars, status, resizable) {
	var popwin;
	var params = "";
	var left = (screen.availWidth - width) / 2;
	var top = (screen.availHeight - Height) / 2;
	if (menubar == "" || menubar == false || menubar == "no" || menubar == 0) {
		params += "menubar=no,";
	} else {
		if (menubar == true || menubar == "yes" || menubar == 1) {
			params += "menubar=yes,";
		}
	}
	if (toolbar == "" || toolbar == false || toolbar == "no" || toolbar == 0) {
		params += "toolbar=no,";
	} else {
		if (toolbar == true || toolbar == "yes" || toolbar == 1) {
			params += "toolbar=yes,";
		}
	}
	if (location == "" || location == false || location == "no" || location == 0) {
		params += "location=no,";
	} else {
		if (location == true || location == "yes" || location == 1) {
			params += "location=yes,";
		}
	}
	if (scrollbars == "" || scrollbars == false || scrollbars == "no" || scrollbars == 0) {
		params += "scrollbars=no,";
	} else {
		if (scrollbars == true || scrollbars == "yes" || scrollbars == 1) {
			params += "scrollbars=yes,";
		}
	}
	if (status == "" || status == false || status == "no" || status == 0) {
		params += "status=no,";
	} else {
		if (status == true || status == "yes" || status == 1) {
			params += "status=yes,";
		}
	}
	if (resizable == "" || resizable == false || resizable == "no" || resizable == 0) {
		params += "resizable=no,";
	} else {
		if (resizable == true || resizable == "yes" || resizable == 1) {
			params += "resizable=yes,";
		}
	}
	popwin = window.open(url, title, params + "left=" + left + ",top=" + top + ",width=" + width + ",height=" + Height);
	popwin.focus();
	if (popwin == "[object]") {
		return true;
	} else {
		return false;
	}
}

/**
 * 对Date的扩展，将 Date 转化为指定格式的String 月(M)、日(d)、12小时(h)、24小时(H)、分(m)、秒(s)、周(E)、季度(q)
 * 可以用 1-2 个占位符 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) eg: (new
 * Date()).pattern("yyyy-MM-dd hh:mm:ss.S") ==> 2007-07-02 08:09:04.423 (new
 * Date()).pattern("yyyy-MM-dd E HH:mm:ss") ==> 2007-03-10 二 20:09:04 (new
 * Date()).pattern("yyyy-MM-dd EE hh:mm:ss") ==> 2007-03-10 周二 08:09:04 (new
 * Date()).pattern("yyyy-MM-dd EEE hh:mm:ss") ==> 2007-03-10 星期二 08:09:04 (new
 * Date()).pattern("yyyy-M-d h:m:s.S") ==> 2007-7-2 8:9:4.18
 */
Date.prototype.format = function(fmt) {
	var o = {
		"M+" : this.getMonth() + 1,
		"d+" : this.getDate(),
		"h+" : this.getHours() % 12 == 0 ? 12 : this.getHours() % 12,
		"H+" : this.getHours(),
		"m+" : this.getMinutes(),
		"s+" : this.getSeconds(),
		"q+" : Math.floor((this.getMonth() + 3) / 3),
		"S" : this.getMilliseconds()
	};
	var week = {
		"0" : "\u65e5",
		"1" : "\u4e00",
		"2" : "\u4e8c",
		"3" : "\u4e09",
		"4" : "\u56db",
		"5" : "\u4e94",
		"6" : "\u516d"
	};
	if (/(y+)/.test(fmt)) {
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	}
	if (/(E+)/.test(fmt)) {
		fmt = fmt.replace(RegExp.$1, ((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "\u661f\u671f" : "\u5468") : "") + week[this.getDay() + ""]);
	}
	for (var k in o) {
		if (new RegExp("(" + k + ")").test(fmt)) {
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
		}
	}
	return fmt;
}

// 根据字符串得到日期 '2010-01-01 12:33:33'
function stringToDate(string) {
	var f = string.split(' ', 2);
	var d = (f[0] ? f[0] : '').split('-', 3);
	var t = (f[1] ? f[1] : '').split(':', 3);

	return (new Date(parseInt(d[0], 10) || null, (parseInt(d[1], 10) || 1) - 1, parseInt(d[2], 10) || null, parseInt(t[0], 10) || null, parseInt(t[1], 10) || null, parseInt(t[2],
			10
	)
			|| null));
}

// 初始化页面中的下拉菜单
function init_dropmenu() {
	// 绑定下拉的单击事件到应用了 .dropdown_selector 类的元素
	$('.dropdown_selector').click(function() {
		var list = $(this).nextAll('.dropdown:first'); // 获得紧接后面的下拉列表
		list.show();
	});
	$('.menu_close').bind({
		'click' : function() {
			var list = $(this).parents('.dropdown:first');
			list.hide();
			$(this).trigger('hide', [list]);
		}
	});
}

jQuery.extend({
	uniqueArray : function(a) {
		var r = [];
		for (var i = 0, l = a.length; i < l; ++i)
			jQuery.inArray(a[i], r) < 0 && r.push(a[i]);
		return r;
	},
	t : function(s) { // 当字符为null时返回''
		if (s == null) { return '&nbsp;'; }
		return s;
	},
	wordLimit:function(text,limit){
		text = $.t(text);
		if(text.length > limit){
			text = text.substr(0,limit);
			return text + '...';
		}
		return text;
	},
	waiting : function(el, state, text) {// 设置按钮状态
		if (state) {
			$(el).attr('disabled', 'disabled').val(text);
		} else {
			$(el).attr('disabled', '').val(text);
		}
	},
	$waiting : function(img,flag,no){ //设定img元素为加载图片，no：0－9
		if(flag){
			img.data('src',img.attr('src'));
			img.attr('class','').attr('src','images/loading' + no + '.gif');
		}else{
			img.attr('src',img.data('src'));	
		}
	},
	getValidationErrors : function(data) {
		if (data.indexOf("/* {") == 0) {
			return eval("( " + data.substring(2, data.length - 2) + " )");
		} else {
			return null;
		}
	},
	getFormErrorContainer:function(form){return form.parent().prev('div.container');},
	clearValidationErrors:function(form){
		var errorContainer = this.getFormErrorContainer(form);
		errorContainer.find('ol').empty();
		errorContainer.hide();
		form.find('.error').removeClass('error');
	},
	showValidationErrors:function(errorContainer, errors,suffix){
		var olList = errorContainer.find('ol');
		olList.empty();
		if(errors.fieldErrors){
			for (var fieldName in errors.fieldErrors) {
				for (var i = 0; i < errors.fieldErrors[fieldName].length; i++) {
//					console.log(fieldName,errors.fieldErrors[fieldName][i]);
					var id = fieldName.replace(/\./g,"_") + suffix;
					olList.append('<li><label for="' + id + '" generated="true" class="error" style="display: block;">' + errors.fieldErrors[fieldName][i] + '</label></li>');
					$('#' + id).addClass('error');
				}
			}
			errorContainer.show();
		}
	}
});

/* 删除重复项 */
function uniqueOptions(id) {
	var oSel = document.getElementById(id);
	var i = 0;
	while (i < oSel.options.length) {
		var j = i + 1;
		while (j < oSel.options.length) {
			if (oSel.options[i].text == oSel.options[j].text) {
				oSel.options[j] = null;
			} else {
				j++;
			}
		}
		i++;
	}
	oSel.setAttribute("size", i);
}

$(function() {
	// init_dropmenu('.dropdown_selector');
	// 折叠fieldset表单
	$("legend").toggle(function() {
		var thiz = $(this);
		var fieldset = thiz.parent();
		if (!thiz.data('height')) {
			thiz.data('height', fieldset.height());
		}
		thiz.next().hide();
		fieldset.addClass('attbg2');
		thiz.addClass('attbg2');
		fieldset.stop();
		fieldset.animate({
			height : 22
		}, {
			duration : 1000,
			easing : 'easeInOutElastic'
		});
	}, function() {
		var thiz = $(this);
		var fieldset = thiz.parent();
		fieldset.stop();
		fieldset.animate({
			height : thiz.data('height')
		}, {
			duration : 1000,
			easing : 'easeOutBounce',
			complete : function() {
				thiz.next().show();
				fieldset.removeClass('attbg2');
				thiz.removeClass('attbg2');
			}
		});
	});

	// 处理复选框全选
	$('.checkhead').live('click', function() {
		$(this).parents('table:first').find(':checkbox[class*="checkitem"]').attr('checked', this.checked);
	});
});
