Ext.onReady(function() {
	Ext.EventManager.onWindowResize(centerPanel);
	
	var u = new Ext.form.TextField({
		applyTo:'username',
		listeners: {
            specialkey: function(field, e){
                if (e.getKey() == e.ENTER) {
                   login()
                }
            }
        }
	});
	u.focus();
	
	new Ext.form.TextField({
		applyTo:'password',
		listeners: {
            specialkey: function(field, e){
                if (e.getKey() == e.ENTER) {
                   login()
                }
            }
        }
	});
	
	var loginPanel = Ext.get("qo-login-panel");

	var loginBtn = Ext.get("submitBtn");
	loginBtn.on({
		'click' : {
			fn : login
		},
		'mouseover' : {
			fn : function() {
				loginBtn.addClass('qo-login-submit-over');
			}
		},
		'mouseout' : {
			fn : function() {
				loginBtn.removeClass('qo-login-submit-over');
			}
		}
	});

	centerPanel();

	function centerPanel() {
		loginPanel.center();
	}

	function login() {
		var username = Ext.get("username").dom.value;
		var password = Ext.get("password").dom.value;
		if (validate(username) === false) {
			alert("你的用帐号是必须的!");
			return false;
		}
		if (validate(password) === false) {
			alert("你的密码是必须的!");
			return false;
		}
		loginPanel.mask('请稍等...', 'x-mask-loading');
		Ext.Ajax.request({
			url : 'login.action',
			params : {
				'user.username' : username,
				'user.password' : password
			},
			success : function(o) {
				loginPanel.unmask();
				if (typeof o == 'object') {
					var d = Ext.decode(o.responseText);
					if (typeof d == 'object') {
						if (d.success == true) {
							loginPanel.mask('重定向中...', 'x-mask-loading');
							window.location = d.infos.path;
						} else {
							if (d.infos && d.infos.error_msg != null) {
								alert(d.infos.error_msg);
							} else {
								alert('服务器发生错误.');
							}
						}
					}
				}
			},
			failure : function() {
				loginPanel.unmask();
				alert('连接服务器失败.');
			}
		});
	}

	function validate(field) {
		if (field === "") {
			return false;
		}
		return true;
	}
});