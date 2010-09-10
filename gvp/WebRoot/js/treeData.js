Ext.ns('Ext.gvp.tree.data');
Ext.gvp.tree.data.manager = [{
	text : '报时表',
	leaf : true,
	id : 'gvp_quote'
}, {
	text : '审核报时表',
	leaf : true,
	hidden:!Ext.ROLE_R18,
	id : 'gvp_check'
},{
	text : '客户信息管理',
	id : 'gvp_customer',
	hidden:!Ext.ROLE_R17,
	leaf : true
}, {
	text : '材质信息管理',
	id : 'gvp_stuff',
	hidden:!Ext.ROLE_R19,
	leaf : true
}, {
	text : '种类信息管理',
	id : 'gvp_species',
	hidden:!Ext.ROLE_R19,
	leaf : true
}, {
	text : '规格信息管理',
	id : 'gvp_specification',
	hidden:!Ext.ROLE_R19,
	leaf : true
},{
	text : '原材料价格调节',
	id : 'gvp_price_list',
	hidden:!Ext.ROLE_R20,
	leaf : true
},{
	text : '密码修改',
	id : 'gvp_password',
	hidden:!Ext.ROLE_R09,
	leaf : true
}, {
	text : '用户管理',
	id : 'gvp_user',
	hidden:!Ext.ROLE_R01,
	leaf : true
}, {
	text : '系统日志',
	id : 'gvp_system_log',
	hidden:!Ext.ROLE_R21,
	leaf : true
}];

Ext.gvp.tree.data.simple = [{
	text : '报时表',
	leaf : true,
	id : 'gvp_quote'
}, {
	text : '密码修改',
	id : 'gvp_password',
	leaf : true
}];
