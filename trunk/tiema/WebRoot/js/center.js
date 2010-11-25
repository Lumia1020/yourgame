$(function() {
	$('#divlayer_' + skin_id).addClass('graptitle_tab_ bd').css({
		margin : " 0pt"
	});

	initTabs('.tabpanel');
});

function S(ak, dc) {
	try {
		return (dc && (dc.document || dc) || document).getElementById(ak);
	} catch (aL) {
		return null;
	}
}

function chooseStyle(_aID) {
	var _curId = S('tmplstyle').value;
	if (_curId == _aID)
		return;

	if (S("label_" + _curId)) {
		S("label_" + _curId).style.cssText = 'font-wieght:noraml;';
		S("divlayer_" + _curId).className = '';
		S("divlayer_" + _curId).style.cssText = 'margin:1px;';
	}

	S("label_" + _aID).style.cssText = 'font-weight:bold;';
	S('divlayer_' + _aID).className = 'grptitle_tab_ bd';
	S("divlayer_" + _aID).style.cssText = 'margin:0;*margin:1px;';

	var _scrollTop = document.body.scrollTop;
	setTimeout(function() {
		document.body.scrollTop = _scrollTop;
	}, 100);

	previousChange(_aID);

	S('tmplstyle').value = _aID;
}

function setActiveStyleSheet(id) {
	var skin = basePath + 'css/skin' + id + '.css';
	var parent = $(window.parent.document);

	$.ajax({
		type : "POST",
		dataType : 'json',
		url : "json/user/success!updateUserSkin",
		data : {
			'skin' : id
		},
		success : function(json) {
			if (json.success) {
				parent.find('.skin').attr('href', skin);
				parent.find('#imglogo').attr('src', basePath + 'images/logo/logo_' + id + '.gif');
				$('.skin').attr('href', skin);
			}
		}
	});
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

function initTabs(container_selector) {
	var titles = $('.tab_list', container_selector); // 获得上面的tab标签集合
	var contents = $('.tab_content', container_selector); // 获得内容集合
	$('.tab_list', container_selector).each(function(index) {
		$(this).click(function() {
			$(this).removeClass('pointer').addClass('actived').siblings().removeClass('actived').addClass('pointer');
			$(contents[index]).show().siblings('.tab_content').hide();
		});
	});
}
