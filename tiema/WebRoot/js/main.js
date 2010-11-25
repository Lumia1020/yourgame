$(function() {

	$(window).resize(function(){
		resizeFolderList();
		
		setTimeout('500',function(){
			$('#mainFrame').css({width:$(document).width() - 192});
		});
	});

	//左边导航菜单a标签和图片分别触发菜单展开和折叠时的单击事件
	$('.toggle > li > a,.toggle > li > img').click(toggleFolder);

	// 左边导航点击后替换背景
	$('.fs').click(function() {
		if ($(this).parent().hasClass('toggle'))
			return;
		$('.fn').removeClass('fn').addClass('fs');
		$(this).toggleClass(function() {
			if ($(this).hasClass('fs')) {
				$(this).removeClass('fs');
				return 'fn';
			} else {
				$(this).removeClass('fn');
				return 'fs';
			}
		});
	});
	
	// 导航菜单控制开关
	$('#imglogo').click(function(){
		$('#hide_leftPanel').trigger('click');
	});
	$('#hide_leftPanel').toggle(function(){
		$('#mainFrameContainer').css({left:'0px'});
		$('#mainFrame').stop();
		$('#mainFrameContainer').stop();
		$('#mainFrameContainer').animate({
			left:0
		},{
			duration : 500,
			easing : 'easeInOutElastic'
		});
		$('#mainFrame').animate({
			width : $(document).width()
		}, {
			duration : 500,
			easing : 'easeInOutElastic'
		});
		$('#hide_leftPanel').text('显示导航菜单');
	},function(){
		$('#mainFrame').stop();
		$('#mainFrameContainer').stop();
		$('#mainFrameContainer').animate({
			left:192
		},{
			duration : 500,
			easing : 'easeOutBounce'
		});
		$('#mainFrame').animate({
			width : $(document).width() - 192
		}, {
			duration : 500,
			easing : 'easeOutBounce'
		});
		$('#hide_leftPanel').text('隐藏导航菜单');
	});

});

function S(aw, dZ) {
	try {
		return (dZ && (dZ.document || dZ) || document).getElementById(aw);
	} catch (aK) {
		return null;
	}
}

function resizeFolderList() {
	var aSu = S("SysFolderList"), fU = S("folder");
	if (aSu && fU) {
		var att = ["auto", "hidden"], aPP = fU.clientHeight, aIV = aSu.offsetHeight, Oi = aPP - aIV, ayr = Oi < 50 ? 0 : 1;
		fU.style.overflow = att[ayr];
		fU.style.overflowX = att[1];
	}
}

// 展开或者合并左边 + - 号图片
function toggleFolder() {
	function toggle(el) {
		el.toggleClass(function() {
			if ($(this).hasClass('fd_on')) {
				$(this).removeClass('fd_on');
				$(this).parents('.toggle:first').next().show();
				return 'fd_off';
			} else {
				$(this).removeClass('fd_off');
				$(this).parents('.toggle:first').next().hide();
				return 'fd_on';
			}
		});
	}

	if ($(this).is('a')) {
		toggle($(this).next());
	}
	if ($(this).is('img')) {
		toggle($(this));
	}
}

function setActiveStyleSheet(id) {
	var skin = 'css/skin' + id + '.css';
	$(document).find('.skin').attr('href', skin);
	$(document).find('#imglogo').attr('src', 'images/logo/logo_' + id + '.gif');
}

function previousChange(_asId) {
	var _self = arguments.callee;

	if (!_self.lock) {
		_self.lock = true;

		_self.waitId = -1;

		setActiveStyleSheet(_asId);

		setTimeout(function() {
			_self.lock = false;
			if (_self.waitId != -1) {
				previousChange(_self.waitId);
			}
		}, 500);
	} else {
		_self.waitId = _asId;
	}
}
