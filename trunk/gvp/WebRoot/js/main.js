Ext.onReady(function() {
	Ext.QuickTips.init();
	
	Ext.form.Field.prototype.msgTarget = 'side';

	Ext.BLANK_IMAGE_URL = '../resources/images/default/s.gif';
	
	var resultTpl = new Ext.XTemplate(
		'<tpl for=".">',
				'<div class="x-combo-list-item">',
					'<div style="font-size: 12px;float:left;"><b>{speciesName}</b></div>',
					'<div style="font-size: 12px;float:right;color:gray">{stuffName}</div>',
				'</div>',
		 '</tpl>'
	);
	 
	var specificationTpl = new Ext.XTemplate(
		'<tpl for=".">',
				'<div class="x-combo-list-item">',
					'<div style="font-size: 12px;float:left;width:40%;"><b>{specName}</b></div>',
					'<div style="font-size: 12px;float:left;color:green;width:10%;">{price}</div>',
					'<div style="font-size: 12px;float:left;color:gray;width:25%;">{speciesName}</div>',
					'<div style="font-size: 12px;float:right;color:gray;width:25%;">{stuffName}</div>',
				'</div>',
		 '</tpl>'
	);
	
	var workflowTpl = new Ext.XTemplate(
		'<table width="100%" border="0" cellspacing="0" cellpadding="4">',
			'<tr><th class="td_l"><b>说明</b></th><th class="td_l"><b>状态</b></th><th class="td_l"><b>操作人</b></th><th class="td_l td_r"><b>时间</b></th></tr>',
			'<tpl for=".">',
				'<tr><td class="td_l">{description}</td><td class="td_l">{state}</td><td class="td_l">{username}</td><td class="td_l td_r">{recordTime}</td></tr>',
			'</tpl>',
			'<tr><td colspan="4" class="td_l td_r td_b">&nbsp;</td></tr>',
		'</table>'
	);
	
	/**
	 * 客户信息
	 * @param callback 回调函数 回调可以接受一个最后添加成功客户的对象
	 */
	function showCustomerWinAdd(callback){
		var objCustomer = null; //回调函数的参数
		new Ext.Window({
			title: '添加客户信息',
			width: 300,
			height: 300,
			resizable:false,
			modal : true,
			layout: 'fit',
			items: [{
				xtype: 'form',
				url : 'saveCustomer.action',
				labelWidth: 90,
				bodyStyle:'padding:15px 10px 10px 10px',
				border: false,
				baseCls: 'x-plain',
				defaultType: 'textfield',
				defaults: {anchor:'92%'},
				items: [{
                    fieldLabel: '客户名称',
                    name: 'customer.customerName',allowBlank:false
                },{
                	fieldLabel:'客户类别',
                	xtype:'combo',
                	name:'customer.customerType',
                	typeAhead: true,
                	allowBlank:false,
                	editable:false,
				    triggerAction: 'all',
				    mode: 'local',
				    store: new Ext.data.ArrayStore({
				        fields: ['text'],
				        data: [['主要客户'], ['其他客户']]
				    }),
				    valueField: 'text',
				    displayField: 'text'
                },{
                	fieldLabel:'产品分组字符串',
                	name:'customer.productCode'
                	,allowBlank:false
                },{
                	fieldLabel:'地址',
                	name:'customer.address'
                },{
                	fieldLabel:'电话',
                	name:'customer.tell'
                },{
                	fieldLabel:'传真',
                	name:'customer.fax'
                },{
                	fieldLabel:'电子邮件',
                	name:'customer.email'
                }]
			}],
			fbar: [{
		        text: '添加',
		        handler: function(){
		        	var form = this.ownerCt.ownerCt.get(0);
		        	if(form.getForm().isValid()){
			        	form.getForm().submit({
							waitTitle : '请稍候',
							waitMsg : '正在提交表单数据,请稍候...',
							success : function(f, action) {
								if(action.result.success){
									if(action.result.infos.customer){ //把添加成功的部门数据赋值作为回调函数的参数传出去
										objCustomer = action.result.infos.customer;
									}
								}else{
									Ext.Msg.show({
										title : '错误提示',
										msg : action.result.infos.msg||'添加失败',
										buttons : Ext.Msg.OK,
										icon : Ext.Msg.ERROR
									});
								}
								form.ownerCt.close();
							},
							failure : function(f,action) {
								Ext.Msg.show({
									title : '错误提示',
									msg : action.result.infos.msg||'添加失败',
									buttons : Ext.Msg.OK,
									icon : Ext.Msg.ERROR
								});
							}
						}); 
		        	}
		        }
		    },{
		        text: '取消',
		        handler: function(){
		        	this.ownerCt.ownerCt.close();
		        }
		    }],
		    listeners:{
		    	'close':function(){
		    		if(callback && objCustomer){
		    			callback(objCustomer);//回调
		    		}
		    	}
		    }
		}).show();
	}
	
	/**
	 * 初始化报时单其他报价grid
	 */
	function initOtherPriceGrid(quoteInfoRecord,height,flag){
		var quoteInfo = quoteInfoRecord.data;
		var store = new Ext.data.Store({
			url: 'findOtherPriceList.action',
			paramNames:{start:'page.start',limit:'page.limit'},
			baseParams:{'page.start':0,'page.limit':0,'otherPrice.qid':quoteInfo.qid},
			autoLoad:flag,
			reader: new Ext.data.JsonReader({totalProperty: 'totalProperty',root: 'root'},
			[
				{name: 'oid'},
				{name: 'price',allowBlank:false,type:'float'},
				{name: 'remark'},
				{name: 'qid'}
			]),
			listeners:{
				'update': function(thiz, record, operation){
					if(operation == Ext.data.Record.EDIT){
						if(record.data.oid){
							Ext.Ajax.request({
							    url: 'updateOtherPrice.action',
							    params: {
									'otherPrice.oid': record.data.oid,		
									'otherPrice.price': record.data.price,
									'otherPrice.remark': record.data.remark,
									'otherPrice.qid':quoteInfo.qid
								},
							    success: function(response) {
							    	var obj = Ext.decode(response.responseText);
							    	if(obj.success){
							    		record.beginEdit();
							    		record.data = obj.infos.otherPrice;
							    		record.commit();
							    		thiz.commitChanges();
							    	}else{
							    		thiz.rejectChanges();
							    	}
							    },
							    failure: function(response) {
							    	Ext.Msg.alert('错误','server-side failure with status code ' + response.status);
							    	thiz.rejectChanges();
							    }
						    });
						}else{
							Ext.Ajax.request({
							    url: 'saveOtherPrice.action',
							    params: {
							    	'otherPrice.qid':quoteInfo.qid,
									'otherPrice.price': record.data.price,
									'otherPrice.remark': record.data.remark
								},
							    success: function(response) {
							    	var obj = Ext.decode(response.responseText);
							    	if(obj.success){
							    		record.beginEdit();
							    		record.data = obj.infos.otherPrice;
							    		record.commit();
							    		grid.getSelectionModel().selectRow(0);
							    		Ext.get(grid.getView().getRow(0)).frame('green',2,{duration: .3});
							    	}
							    },
							    failure: function(response) {
							    	Ext.Msg.alert('错误','server-side failure with status code ' + response.status);
							    	thiz.rejectChanges();
							    }
						    });
						}
					}
				}
			}
		});
	    
	    var editor = initEditor(null,null,2);
	    editor.on('invalid',function(record,sd){
	    	Ext.get(grid.getView().getRow(0)).fadeOut({
			    endOpacity: 0, 
			    easing: 'easeOut',
			    duration: .5,
			    remove: false,
			    useDisplay: false,
			    callback:function(){
			    	sd.remove(record);
			    }
			});
	    });
	    
	    var sm = new Ext.grid.CheckboxSelectionModel({
	        listeners: {
	            selectionchange: function(sm) {
	                if (sm.getCount()) {
	                    grid.removeButton.enable();
	                } else {
	                    grid.removeButton.disable();
	                }
	            }
	        }
	    });
	    
	    var expandId = Ext.id();
	     
		var grid = new Ext.grid.GridPanel({
			height : height,
			store: store,
		    plugins : editor,
		    autoExpandColumn: expandId,
			sm: sm,
			border:false,
			colModel:new Ext.grid.ColumnModel({
				defaultSortable:true,
				defaultWidth:60,
				columns: [sm,
			        {header: '单价',dataIndex: 'price',editor:{xtype : 'numberfield',decimalPrecision:6,maxLength:20,allowBlank:false,name:'price'}},
			        {header: '备注',dataIndex: 'remark',editor:{xtype:'textfield',maxLength:400,name:'remark'},id:expandId}
			    ]
			}),
		    tbar : [{text :'添加'
	    		,iconCls:'silk-add'
	    		,ref:'../addButton'
	    		,handler:function(){
	    			var otherPrice = new grid.store.recordType({
						remark:''
			        });
			        editor.stopEditing();
			        grid.store.insert(0, otherPrice);
			        editor.startEditing(0);
	    		}
	    	},{
				text	: '删除',
				iconCls : 'silk-delete',
				ref : '../removeButton',
				disabled:true,
				handler : function(){
					editor.stopEditing(false);
					deleteRecords('deleteOtherPrice.action','page.params.ids','oid',grid,store,null,quoteInfo.qid,quoteInfoRecord);
				}
			},'-',{
				text	: '刷新',
				iconCls : 'x-tbar-loading',
				handler : function(){
					editor.stopEditing(false);
					store.reload();
				}
			}]
		});
		
		return grid;
	}
	
	/**
	 * 初始化外发加工信息grid
	 * @param quoteInfoRecord 报时表对象
	 * @param height GridPanel高度
	 */
	function initFoundryGrid(quoteInfoRecord,height,flag){
		var quoteInfo = quoteInfoRecord.data;
		var store = new Ext.data.Store({
			url: 'findFoundryList.action',
			paramNames:{start:'page.start',limit:'page.limit'},
			baseParams:{'page.start':0,'page.limit':0,'foundry.qid':quoteInfo.qid},
			autoLoad:flag,
			reader: new Ext.data.JsonReader({totalProperty: 'totalProperty',root: 'root'},
			[
				{name: 'fid'},
				{name: 'plateMerchant'},
				{name: 'attritionRate',allowBlank:false},
				{name: 'processPrice',allowBlank:false,type:'float'},
				{name: 'remark'},
				{name: 'qid'}
			]),
			listeners:{
				'update': function(thiz, record, operation){
					if(operation == Ext.data.Record.EDIT){
						if(record.data.fid){
							Ext.Ajax.request({
							    url: 'updateFoundry.action',
							    params: {
									'foundry.fid': record.data.fid,		
									'foundry.plateMerchant': record.data.plateMerchant,		
									'foundry.attritionRate': record.data.attritionRate,
									'foundry.processPrice': record.data.processPrice,
									'foundry.remark': record.data.remark,
									'foundry.qid':quoteInfo.qid
								},
							    success: function(response) {
							    	var obj = Ext.decode(response.responseText);
							    	if(obj.success){
							    		record.beginEdit();
							    		record.data = obj.infos.foundry;
							    		record.commit();
							    		thiz.commitChanges();
							    		quoteInfoRecord.set('price',obj.infos.quoteInfo[0].price);
							    	}else{
							    		thiz.rejectChanges();
							    	}
							    },
							    failure: function(response) {
							    	Ext.Msg.alert('错误','server-side failure with status code ' + response.status);
							    	thiz.rejectChanges();
							    }
						    });
						}else{
							Ext.Ajax.request({
							    url: 'saveFoundry.action',
							    params: {
							    	'foundry.qid':quoteInfo.qid,
									'foundry.plateMerchant': record.data.plateMerchant,		
									'foundry.attritionRate': record.data.attritionRate,
									'foundry.processPrice': record.data.processPrice,
									'foundry.remark': record.data.remark
								},
							    success: function(response) {
							    	var obj = Ext.decode(response.responseText);
							    	if(obj.success){
							    		record.beginEdit();
							    		record.data = obj.infos.foundry;
							    		record.commit();
							    		quoteInfoRecord.set('price',obj.infos.quoteInfo[0].price);
							    		quoteInfoRecord.commit();
							    		grid.getSelectionModel().selectRow(0);
							    		Ext.get(grid.getView().getRow(0)).frame('green',2,{duration: .3});
							    	}
							    },
							    failure: function(response) {
							    	Ext.Msg.alert('错误','server-side failure with status code ' + response.status);
							    	thiz.rejectChanges();
							    }
						    });
						}
					}
				}
			}
		});
	    
	    var editor = initEditor(null,null,2);
	    editor.on('invalid',function(record,sd){
	    	Ext.get(grid.getView().getRow(0)).fadeOut({
			    endOpacity: 0, 
			    easing: 'easeOut',
			    duration: .5,
			    remove: false,
			    useDisplay: false,
			    callback:function(){
			    	sd.remove(record);
			    }
			});
	    });
	    
	    var sm = new Ext.grid.CheckboxSelectionModel({
	        listeners: {
	            selectionchange: function(sm) {
	                if (sm.getCount()) {
	                    grid.removeButton.enable();
	                } else {
	                    grid.removeButton.disable();
	                }
	            }
	        }
	    });
	    
	    var expandId = Ext.id();
	     
		var grid = new Ext.grid.GridPanel({
			height : height,
			store: store,
		    plugins : editor,
		    autoExpandColumn: expandId,
			sm: sm,
			border:false,
			colModel:new Ext.grid.ColumnModel({
				defaultSortable:true,
				defaultWidth:60,
				columns: [sm,
			        {header: '外发加工类别',dataIndex:'plateMerchant',id:expandId,editor:{xtype : 'textfield',maxLength:200,name:'plateMerchant'}},
			        {header: '损耗率',dataIndex: 'attritionRate',renderer:function(v){return v + ' %';},editor:{xtype : 'textfield',maxLength:50,allowBlank:false,name:'attritionRate'}},
			        {header: '加工单价',dataIndex: 'processPrice',editor:{xtype : 'numberfield',decimalPrecision:6,maxLength:20,allowBlank:false,name:'processPrice'}},
			        {header: '备注',dataIndex: 'remark',editor:{xtype:'textfield',maxLength:400,name:'remark'}}
			    ]
			}),
		    tbar : [{text :'添加'
	    		,iconCls:'silk-add'
	    		,ref:'../addButton'
	    		,handler:function(){
	    			var foundry = new grid.store.recordType({
	    				plateMerchant:'',		
						attritionRate:'',	
						remark:''
			        });
			        editor.stopEditing();
			        grid.store.insert(0, foundry);
			        editor.startEditing(0);
	    		}
	    	},{
				text	: '删除',
				iconCls : 'silk-delete',
				ref : '../removeButton',
				disabled:true,
				handler : function(){
					editor.stopEditing(false);
					deleteRecords('deleteFoundry.action','page.params.ids','fid',grid,store,null,quoteInfo.qid,quoteInfoRecord);
				}
			},'-',{
				text	: '刷新',
				iconCls : 'x-tbar-loading',
				handler : function(){
					editor.stopEditing(false);
					store.reload();
				}
			}]
		});
		
		return grid;
	}
	
	/**
	 * 初始化外发加工信息grid仅用于展示
	 * @param quoteInfo 报时表对象
	 * @param height GridPanel高度
	 * @param hide 是否影藏单价
	 */
	function showOtherPriceGrid(quoteInfo,height,flag){
		var store = new Ext.data.Store({
			url: 'findOtherPriceList.action',
			paramNames:{start:'page.start',limit:'page.limit'},
			baseParams:{'page.start':0,'page.limit':0,'otherPrice.qid':quoteInfo.qid},
			autoLoad:flag,
			reader: new Ext.data.JsonReader({totalProperty: 'totalProperty',root: 'root'},
			[
				{name: 'oid'},
				{name: 'price',allowBlank:false,type:'float'},
				{name: 'remark'},
				{name: 'qid'}
			])
		});
	    
	    
	    var expandId = Ext.id();
	     
		var grid = new Ext.grid.GridPanel({
			hideLabel:true,
			title : '其他报价信息',
			isFormField : true,
			store: store,
			height:height,
			autoScroll:true,
		    autoExpandColumn: expandId,
			sm: new Ext.grid.RowSelectionModel(),
			colModel:new Ext.grid.ColumnModel({
				defaults:{
					sortable:true,
					width:150,
					menuDisabled:true
				},
				columns: [
			        {header: '单价',hidden:!Ext.ROLE_R08,dataIndex: 'price'},
			        {header: '备注',dataIndex: 'remark',id:expandId}
			    ]
			})
		});
		
		return grid;
	}
	
	/**
	 * 初始化外发加工信息grid仅用于展示
	 * @param quoteInfo 报时表对象
	 * @param height GridPanel高度
	 * @param hide 是否影藏单价
	 */
	function showFoundryGrid(quoteInfo,height,flag){
		var store = new Ext.data.Store({
			url: 'findFoundryList.action',
			paramNames:{start:'page.start',limit:'page.limit'},
			baseParams:{'page.start':0,'page.limit':0,'foundry.qid':quoteInfo.qid},
			autoLoad:flag,
			reader: new Ext.data.JsonReader({totalProperty: 'totalProperty',root: 'root'},
			[
				{name: 'fid'},
				{name: 'plateMerchant'},
				{name: 'attritionRate',allowBlank:false},
				{name: 'processPrice',allowBlank:false,type:'float'},
				{name: 'remark'},
				{name: 'qid'}
			])
		});
	    
	    
	    var expandId = Ext.id();
	     
		var grid = new Ext.grid.GridPanel({
			hideLabel:true,
			title : '外发加工信息',
			isFormField : true,
			store: store,
			height:height,
			autoScroll:true,
		    autoExpandColumn: expandId,
			sm: new Ext.grid.RowSelectionModel(),
			colModel:new Ext.grid.ColumnModel({
				defaults:{
					sortable:true,
					width:60,
					menuDisabled:true
				},
				columns: [
			        {header: '电镀商',dataIndex:'plateMerchant',id:expandId},
			        {header: '损耗率',dataIndex: 'attritionRate'},
			        {header: '加工单价',hidden:!Ext.ROLE_R08,dataIndex: 'processPrice'},
			        {header: '备注',dataIndex: 'remark'}
			    ]
			})
		});
		
		return grid;
	}
	
	/**
	 * 初始化加工信息grid
	 * @param quoteInfoRecord 报时表信息
	 * @param height GridPanel高度
	 */
	function initProcessInfoGrid(quoteInfoRecord,height,flag){
		var quoteInfo = quoteInfoRecord.data;
		var store = new Ext.data.Store({
			url: 'findProcessInfoList.action',
			paramNames:{start:'page.start',limit:'page.limit'},
			baseParams:{'page.start':0,'page.limit':0,'process.qid':quoteInfo.qid},
			autoLoad:flag,
			reader: new Ext.data.JsonReader({totalProperty: 'totalProperty',root: 'root'},
			[
				{name: 'pid'},
				{name: 'models'},
				{name: 'processName'},
				{name: 'dayOutput',allowBlank:false},
				{name: 'processTime',allowBlank:false},
				{name: 'attritionRate',allowBlank:false},
				{name: 'processPrice',allowBlank:false},
				{name: 'conditioners'},
				{name: 'qid'}
			]),
			listeners:{
				'update': function(thiz, record, operation){
					if(operation == Ext.data.Record.EDIT){
						if(record.data.pid){
							Ext.Ajax.request({
							    url: 'updateProcessInfo.action',
							    params: {
									'process.pid': record.data.pid,		
									'process.models': record.data.models,		
									'process.processName': record.data.processName,		
									'process.dayOutput': record.data.dayOutput,		
									'process.processTime': record.data.processTime,		
									'process.attritionRate': record.data.attritionRate,		
									'process.conditioners': record.data.conditioners,
									'process.processPrice': record.data.processPrice,
									'process.qid':quoteInfo.qid 
								},
							    success: function(response) {
							    	var obj = Ext.decode(response.responseText);
							    	if(obj.success){
							    		record.beginEdit();
							    		record.data = obj.infos.process;
							    		record.commit();
							    		thiz.commitChanges();
							    		quoteInfoRecord.set('price',obj.infos.quoteInfo[0].price);
							    		quoteInfoRecord.commit();
							    	}else{
							    		thiz.rejectChanges();
							    	}
							    },
							    failure: function(response) {
							    	Ext.Msg.alert('错误','server-side failure with status code ' + response.status);
							    	thiz.rejectChanges();
							    }
						    });
						}else{
							Ext.Ajax.request({
							    url: 'saveProcessInfo.action',
							    params: {
							    	'process.qid':quoteInfo.qid,
									'process.models': record.data.models,
									'process.processName': record.data.processName,
									'process.dayOutput': record.data.dayOutput,
									'process.processTime': record.data.processTime,
									'process.attritionRate': record.data.attritionRate,
									'process.conditioners': record.data.conditioners,
									'process.processPrice': record.data.processPrice
								},
							    success: function(response) {
							    	var obj = Ext.decode(response.responseText);
							    	if(obj.success){
							    		record.beginEdit();
							    		record.data = obj.infos.process;
							    		record.commit();
							    		quoteInfoRecord.set('price',obj.infos.quoteInfo[0].price);
							    		quoteInfoRecord.commit();
							    		grid.getSelectionModel().selectRow(0);
							    		Ext.get(grid.getView().getRow(0)).frame('green',2,{duration: .3});
							    	}
							    },
							    failure: function(response) {
							    	Ext.Msg.alert('错误','server-side failure with status code ' + response.status);
							    	thiz.rejectChanges();
							    }
						    });
						}
					}
				}
			}
		});
	    
	    var editor = initEditor(null,null,2);
	    editor.on('invalid',function(record,sd){
	    	Ext.get(grid.getView().getRow(0)).fadeOut({
			    endOpacity: 0, 
			    easing: 'easeOut',
			    duration: .5,
			    remove: false,
			    useDisplay: false,
			    callback:function(){
			    	sd.remove(record);
			    }
			});
	    });
	    
	    var sm = new Ext.grid.CheckboxSelectionModel({
	        listeners: {
	            selectionchange: function(sm) {
	                if (sm.getCount()) {
	                    grid.removeButton.enable();
	                } else {
	                    grid.removeButton.disable();
	                }
	            }
	        }
	    });
	    
	    var expandId = Ext.id();
	     
		var grid = new Ext.grid.GridPanel({
			height : height,
			store: store,
		    plugins : editor,
		    border:false,
		    autoExpandColumn: expandId,
			sm: sm,
			colModel:new Ext.grid.ColumnModel({
				defaultSortable:true,
				defaultWidth:60,
				columns: [sm,
			        {header: '机种',dataIndex:'models',id:expandId,editor:{xtype : 'textfield',maxLength:20,name:'models'}},
			        {header: '工序',dataIndex: 'processName',editor:{xtype : 'textfield',maxLength:20,name:'processName'}},
			        {header: '加工时间',dataIndex: 'processTime',editor:{xtype : 'textfield',maxLength:20,allowBlank:false,name:'processTime'}},
			        {header: '估计日量',dataIndex: 'dayOutput',editor:{xtype : 'textfield',maxLength:20,allowBlank:false,name:'dayOutput'}},
			        {header: '损耗率',dataIndex: 'attritionRate',renderer:function(v){return v + ' %';},editor:{xtype : 'textfield',maxLength:20,allowBlank:false,name:'attritionRate'}},
			        {header: '调机品',dataIndex: 'conditioners',editor:{xtype:'textfield',maxLength:20,name:'conditioners'}},
			        {header: '加工单价',dataIndex: 'processPrice',editor:{xtype : 'numberfield',decimalPrecision:6,maxLength:20,allowBlank:false,name:'processPrice'}}
			    ]
			}),
		    tbar : [{text :'添加'
	    		,iconCls:'silk-add'
	    		,ref:'../addButton'
	    		,handler:function(){
	    			var process = new grid.store.recordType({
	    				pid:'',
	    				models:'',
	    				processName:'',
	    				dayOutput:'',
	    				processTime:'',
	    				attritionRate:'',
	    				conditioners:'',
	    				processPrice:0
			        });
			        editor.stopEditing();
			        grid.store.insert(0, process);
			        editor.startEditing(0);
	    		}
	    	},{
				text	: '删除',
				iconCls : 'silk-delete',
				ref : '../removeButton',
				disabled:true,
				handler : function(){
					editor.stopEditing(false);
					deleteRecords('deleteProcessInfo.action','page.params.ids','pid',grid,store,null,quoteInfo.qid,quoteInfoRecord);
				}
			},'-',{
				text	: '刷新',
				iconCls : 'x-tbar-loading',
				handler : function(){
					editor.stopEditing(false);
					store.reload();
				}
			}]
		});
		
		return grid;
	}
	
	/**
	 * 初始化加工信息grid仅显示
	 * @param quoteInfo 报时表信息
	 * @param height GridPanel高度
	 */
	function showProcessInfoGrid(quoteInfo,height,flag){
		var store = new Ext.data.Store({
			url: 'findProcessInfoList.action',
			paramNames:{start:'page.start',limit:'page.limit'},
			baseParams:{'page.start':0,'page.limit':0,'process.qid':quoteInfo.qid},
			autoLoad:flag,
			reader: new Ext.data.JsonReader({totalProperty: 'totalProperty',root: 'root'},
			[
				{name: 'pid'},
				{name: 'models'},
				{name: 'processName'},
				{name: 'dayOutput',allowBlank:false},
				{name: 'processTime',allowBlank:false},
				{name: 'attritionRate',allowBlank:false},
				{name: 'processPrice',allowBlank:false},
				{name: 'conditioners'},
				{name: 'qid'}
			])
		});
	    
	    
	    var expandId = Ext.id();
	     
		var grid = new Ext.grid.GridPanel({
			title : '加工信息',
			store: store,
			hideLabel:true,
			isFormField : true,
			height:height,
		    autoExpandColumn: expandId,
			sm: new Ext.grid.RowSelectionModel(),
			colModel:new Ext.grid.ColumnModel({
				defaults:{
					sortable:true,
					width:60,
					menuDisabled:true
				},
				columns: [
			        {header: '机种',dataIndex:'models',id:expandId},
			        {header: '工序',dataIndex: 'processName'},
			        {header: '加工时间',dataIndex: 'processTime'},
			        {header: '估计日量',dataIndex: 'dayOutput'},
			        {header: '损耗率',dataIndex: 'attritionRate',renderer:function(v){return v + ' %';}},
			        {header: '调机品',dataIndex: 'conditioners'},
			        {header: '加工单价',dataIndex: 'processPrice',hidden:!Ext.ROLE_R07}
			    ]
			})
		});
		
		return grid;
	}
	
	/**
	 * 初始辅助工具信息grid
	 * @param quoteInfoRecord 报时表信息
	 * @param height GridPanel高度
	 */
	function initAidsGrid(quoteInfoRecord,height,flag){
		var quoteInfo = quoteInfoRecord.data;
		var store = new Ext.data.Store({
			url: 'findAidsList.action',
			paramNames:{start:'page.start',limit:'page.limit'},
			baseParams:{'page.start':0,'page.limit':0,'aids.qid':quoteInfo.qid},
			autoLoad:flag,
			reader: new Ext.data.JsonReader({totalProperty: 'totalProperty',root: 'root'},
			[
				{name: 'aid'},
				{name: 'aidsName',allowBlank:false},
				{name: 'originalPrice',type:'float',allowBlank:false},
				{name: 'price',type:'float',allowBlank:false}
			]),
			listeners:{
				'update': function(thiz, record, operation){
					if(operation == Ext.data.Record.EDIT){
						if(record.data.aid){
							Ext.Ajax.request({
							    url: 'updateAids.action',
							    params: {
									'aids.aid': record.data.aid,		
									'aids.aidsName': record.data.aidsName,		
									'aids.originalPrice': record.data.originalPrice,		
									'aids.price': record.data.price,
									'aids.qid':quoteInfo.qid
								},
							    success: function(response) {
							    	var obj = Ext.decode(response.responseText);
							    	if(obj.success){
							    		record.beginEdit();
							    		record.data = obj.infos.aids;
							    		record.commit();
							    		thiz.commitChanges();
							    		quoteInfoRecord.set('price',obj.infos.quoteInfo[0].price);
							    		quoteInfoRecord.commit();
							    	}else{
							    		thiz.rejectChanges();
							    	}
							    },
							    failure: function(response) {
							    	Ext.Msg.alert('错误','server-side failure with status code ' + response.status);
							    	thiz.rejectChanges();
							    }
						    });
						}else{
							Ext.Ajax.request({
							    url: 'saveAids.action',
							    params: {
							    	'aids.qid':quoteInfo.qid,
									'aids.aidsName': record.data.aidsName,		
									'aids.originalPrice': record.data.originalPrice,		
									'aids.price': record.data.price	
								},
							    success: function(response) {
							    	var obj = Ext.decode(response.responseText);
							    	if(obj.success){
							    		record.beginEdit();
							    		record.data = obj.infos.aids;
							    		record.commit();
							    		quoteInfoRecord.set('price',obj.infos.quoteInfo[0].price);
							    		quoteInfoRecord.commit();
							    		grid.getSelectionModel().selectRow(0);
							    		Ext.get(grid.getView().getRow(0)).frame('green',2,{duration: .3});
							    	}
							    },
							    failure: function(response) {
							    	Ext.Msg.alert('错误','server-side failure with status code ' + response.status);
							    	thiz.rejectChanges();
							    }
						    });
						}
					}
				}
			}
		});
	    
	    var editor = initEditor(null,null,2);
	    editor.on('invalid',function(record,sd){
	    	Ext.get(grid.getView().getRow(0)).fadeOut({
			    endOpacity: 0, 
			    easing: 'easeOut',
			    duration: .5,
			    remove: false,
			    useDisplay: false,
			    callback:function(){
			    	sd.remove(record);
			    }
			});
	    });
	    
	    var sm = new Ext.grid.CheckboxSelectionModel({
	        listeners: {
	            selectionchange: function(sm) {
	                if (sm.getCount()) {
	                    grid.removeButton.enable();
	                } else {
	                    grid.removeButton.disable();
	                }
	            }
	        }
	    });
	    
	    var expandId = Ext.id();
	     
		var grid = new Ext.grid.GridPanel({
			height : height,
			store: store,
		    plugins : editor,
		    autoExpandColumn: expandId,
			sm: sm,
			border:false,
			colModel:new Ext.grid.ColumnModel({
				defaultSortable:true,
				defaultWidth:60,
				columns: [sm,
			        {header: '工具名称',dataIndex:'aidsName',id:expandId,editor:{xtype : 'textfield',maxLength:20,allowBlank:false,name:'aidsName'}},
			        {header: '原价',dataIndex: 'originalPrice',editor:{xtype : 'numberfield',decimalPrecision:6,maxLength:20,allowBlank:false,name:'originalPrice'}},
			        {header: '加工单价',dataIndex: 'price',editor:{xtype : 'numberfield',decimalPrecision:6,maxLength:20,allowBlank:false,name:'price'}}
			    ]
			}),
		    tbar : [{text :'添加'
	    		,iconCls:'silk-add'
	    		,ref:'../addButton'
	    		,handler:function(){
	    			var aids = new grid.store.recordType({
	    				aid:'',
	    				aidsName:'',
	    				originalPrice:'',
	    				price:''
			        });
			        editor.stopEditing();
			        grid.store.insert(0, aids);
			        editor.startEditing(0);
	    		}
	    	},{
				text	: '删除',
				iconCls : 'silk-delete',
				ref : '../removeButton',
				disabled:true,
				handler : function(){
					editor.stopEditing(false);
					deleteRecords('deleteAids.action','page.params.ids','aid',grid,store,null,quoteInfo.qid,quoteInfoRecord);
				}
			},'-',{
				text	: '刷新',
				iconCls : 'x-tbar-loading',
				handler : function(){
					editor.stopEditing(false);
					store.reload();
				}
			}]
		});
		
		return grid;
	}
	
	/**
	 * 初始辅助工具信息grid仅显示
	 * @param quoteInfo 报时表信息
	 * @param height GridPanel高度
	 */
	function showAidsGrid(quoteInfo,height,flag){
		var store = new Ext.data.Store({
			url: 'findAidsList.action',
			paramNames:{start:'page.start',limit:'page.limit'},
			baseParams:{'page.start':0,'page.limit':0,'aids.qid':quoteInfo.qid},
			autoLoad:flag,
			reader: new Ext.data.JsonReader({totalProperty: 'totalProperty',root: 'root'},
			[
				{name: 'aid'},
				{name: 'aidsName',allowBlank:false},
				{name: 'originalPrice',type:'float',allowBlank:false},
				{name: 'price',type:'float',allowBlank:false}
			])
		});
	    
	    var expandId = Ext.id();
	     
		var grid = new Ext.grid.GridPanel({
			title : '辅助信息',
			store: store,
			hideLabel:true,
			isFormField : true,
			height:height,
		    autoExpandColumn: expandId,
			sm: new Ext.grid.RowSelectionModel(),
			colModel:new Ext.grid.ColumnModel({
				defaults:{
					sortable:true,
					width:60,
					menuDisabled:true
				},
				columns: [
			        {header: '工具名称',dataIndex:'aidsName',id:expandId},
			        {header: '原价',hidden:!Ext.ROLE_R08,dataIndex: 'originalPrice'},
			        {header: '加工单价',dataIndex: 'price',hidden:!Ext.ROLE_R08}
			    ]
			})
		});
		
		return grid;
		
	}
	
	/**
	 * 显示文件上传窗体
	 */
	function showUploadWin(quoteInfo,callback){
		var win = new Ext.Window({
			title:'文件上传',
			width:500,
			modal:true,
			resizable:false,
			layout:'fit',
			items:[{
				xtype:'uploadpanel',
				uploadUrl : 'uploadFiles.action',
				filePostName : 'myUpload', //这里很重要，默认值为'fileData',这里匹配action中的setMyUpload 属性
				flashUrl : '../js/swfupload.swf',
				fileSize : '500 MB', 
				height:400,
				border:false,
				fileTypes : '*.*',
				fileTypesDescription : '所有文件',
				postParams:{
					path:quoteInfo.qid + '\\',
					'quoteInfo.qid':quoteInfo.qid
				}
			}],
			listeners:{
				'close':function(){
					if(callback){callback();}
				}
			},
			fbar:[{
				text:'关闭',
				handler:function(){
					win.close();
				}
			}]
		});
		
		win.show();
	}
	
	/**
	 * 初始参考信息的图纸文件等grid
	 * @param quoteInfo 报时表信息
	 * @param height GridPanel高度
	 */
	function initFilesGrid(quoteInfo,height,flag){
		var store = new Ext.data.Store({
			url: 'findRefFilesList.action',
			paramNames:{start:'page.start',limit:'page.limit'},
			baseParams:{'page.start':0,'page.limit':0,'refFiles.qid':quoteInfo.qid},
			autoLoad:flag,
			reader: new Ext.data.JsonReader({totalProperty: 'totalProperty',root: 'root'},
			[
				{name: 'fid'},
				{name: 'url'},
				{name: 'remark'},
				{name: 'qid'}
			]),
			listeners:{
				'update': function(thiz, record, operation){
					if(operation == Ext.data.Record.EDIT){
						if(record.data.fid){
							Ext.Ajax.request({
							    url: 'updateRefFiles.action',
							    params: {
									'refFiles.fid': record.data.fid,		
									'refFiles.url': record.data.url,		
									'refFiles.remark': record.data.remark,		
									'refFiles.qid': record.data.qid		
								},
							    success: function(response) {
							    	var obj = Ext.decode(response.responseText);
							    	if(obj.success){
							    		record.beginEdit();
							    		record.data = obj.infos.refFiles;
							    		record.commit();
							    		thiz.commitChanges();
							    	}else{
							    		thiz.rejectChanges();
							    	}
							    },
							    failure: function(response) {
							    	Ext.Msg.alert('错误','server-side failure with status code ' + response.status);
							    	thiz.rejectChanges();
							    }
						    });
						}
					}
				}
			}
		});
	    
	    var editor = initEditor(null,null,2);
	    editor.on('invalid',function(record,sd){
	    	Ext.get(grid.getView().getRow(0)).fadeOut({
			    endOpacity: 0, 
			    easing: 'easeOut',
			    duration: .5,
			    remove: false,
			    useDisplay: false,
			    callback:function(){
			    	sd.remove(record);
			    }
			});
	    });
	    
	    var sm = new Ext.grid.CheckboxSelectionModel({
	        listeners: {
	            selectionchange: function(sm) {
	                if (sm.getCount()) {
	                    grid.removeButton.enable();
	                } else {
	                    grid.removeButton.disable();
	                }
	            }
	        }
	    });
	    
	    
	    var expandId = Ext.id();
	     
		var grid = new Ext.grid.GridPanel({
			height : height,
			store: store,
		    plugins : editor,
		    border:false,
		    autoExpandColumn: expandId,
			sm: sm,
			colModel:new Ext.grid.ColumnModel({
				defaultSortable:true,
				defaultWidth:60,
				columns: [sm,{header: '文件',dataIndex:'url',width:40,editor:{xtype:'hidden',name:'url'},
					renderer:function(_v, cellmeta, record){
							var extensionName = _v.substring(_v.lastIndexOf('.') + 1);
							if(_v){
								var css = '.db-ft-' + extensionName.toLowerCase() + '-small';
								return '<div class="db-ft-' + extensionName.toLowerCase() + '-small" style="height: 16px;background-repeat: no-repeat;"/></div>';
							}
							return '<div class="db-ft-unknown-small" style="height: 16px;background-repeat: no-repeat;"/></div>';	
						}
					},
			        {header: '资料说明',dataIndex: 'remark',id:expandId,editor:{xtype : 'textfield',maxLength:20,name:'remark'}}
			    ]
			}),
		    tbar : [{text :'添加'
	    		,iconCls:'silk-add'
	    		,ref:'../addButton'
	    		,handler:function(){
			        editor.stopEditing();
			        showUploadWin(quoteInfo,function(){
			        	store.reload();
			        });
	    		}
	    	},{
	    		text : '删除'
	    		,iconCls:'silk-delete'
	    		,ref:'../removeButton'
	    		,disabled:true
	    		,handler:function(){
	    			editor.stopEditing();
					deleteRecords('deleteRefFiles.action','page.params.ids','fid',grid,store);
	    		}
	    	},'-',{
				text	: '刷新',
				iconCls : 'x-tbar-loading',
				handler : function(){
					editor.stopEditing(false);
					store.reload();
				}
			}]
		});
		
		return grid;
	}
	
	
	/**
	 * 显示关联报时记录
	 * @param quoteInfo 报时表信息
	 * @param height GridPanel高度
	 */
	function showRelationQuoteInfos(quoteInfo,height,flag){
		var store = new Ext.data.Store({
			url: 'findRelationQuoteInfoList.action',
			paramNames:{start:'page.start',	limit:'page.limit'},
			baseParams:{'page.start':0,'page.limit':20,'quoteInfo.qid':quoteInfo.qid},
			autoLoad:flag,
			reader: new Ext.data.JsonReader({totalProperty: 'totalProperty',root: 'root'},
			[
				{name: 'qid'},
				{name: 'cid',allowBlank:false},
				{name: 'customerName',allowBlank:false},
				{name: 'customerCode',allowBlank:false},
				{name: 'quoter'},
				{name: 'price'},
				{name: 'recordTime',type:'date',dateFormat: "Y-m-d H:i:s"},
				{name: 'modifyTime',type:'date',dateFormat: "Y-m-d H:i:s"},
				{name: 'ownerId'}
			])
	    });
	    
	    
	    var expandId = Ext.id();
	    
	    var grid = new Ext.grid.GridPanel({
	    	title:'关联报时记录',
	        trackMouseOver:false, 
	        isFormField:true,
	        hideLabel:true,
	        loadMask:true,
	    	store: store,
	    	height:height,
	    	autoExpandColumn: expandId,
	        sm: new Ext.grid.RowSelectionModel(),
	        colModel:new Ext.grid.ColumnModel({
				defaults:{
					sortable:true,
					width:80,
					menuDisabled:true
				},
				columns: [
					{header: '客户名称',dataIndex:'customerName',id:expandId,renderer:function(v, m, r){
						if(quoteInfo.ownerId == r.data.qid){
							return '<div style="background-color:#99CC00">' + v + '</div>';
						}
						return v;
					}},
					{header: '客户编码',dataIndex:'customerCode',renderer:function(v,m,r){
						if(quoteInfo.ownerId == r.data.qid){
							return '<div style="background-color:#99CC00">' + v + '</div>';	
						}
						return v;
					}},
					{header: '报时人',dataIndex:'quoter',renderer:function(v, m, r){
						if(quoteInfo.ownerId == r.data.qid){
							return '<div style="background-color:#99CC00">' + v + '</div>';
						}
						return v;
					}},
					{header: '单价',hidden:!Ext.ROLE_R06,dataIndex:'price',renderer:function(v, m, r){
						if(quoteInfo.ownerId == r.data.qid){
							return '<div style="background-color:#99CC00">' + v + '</div>';
						}
						return v;
					}}, 
					{header: '填单日期',dataIndex:'recordTime',renderer:function(v, m, r){
						v = Ext.util.Format.date(v, 'Y-m-d');
						if(quoteInfo.ownerId == r.data.qid){
							return '<div style="background-color:#99CC00">' + v + '</div>';
						}
						return v;
					}},
					{header: '更新日期',dataIndex:'modifyTime',renderer:function(v, m, r){
						v = Ext.util.Format.date(v, 'Y-m-d');
						if(quoteInfo.ownerId == r.data.qid){
							return '<div style="background-color:#99CC00">' + v + '</div>';
						}
						return v;
					}}
	    	]}),
	    	listeners:{
	    		'rowdblclick':function(g,i,e){
	    			var record = g.getSelectionModel().getSelected();
	    			if(record){
	    				showQuoteInfos(record,store)
	    			}else{
	    				Ext.Msg.alert('操作提示','请先选择要操作的行.');
	    			}
	    		}
	    	}
	    });
	    
	    return grid;
	}
	
	
	/**
	 * 显示PDF文档
	 */
	function showPdfWin(url){
		url = String(url).replace(/\\/g, "/");
		var win = new Ext.Window({
			title:'图纸显示',
			width:800,
			height:600,
			constrainHeader : true,
			maximizable:true,
			html:Ext.isIE ? '<object classid="clsid:CA8A9780-280D-11CF-A24D-444553540000" width="100%" height="100%"><param name="src" value="'
						+ url + '"></object>':'<embed width="100%" height="100%" src="' + url + '"/>',
			bbar:[{
				text:'下载',
				handler:function(){
					window.open(url);
				}
			}]
		});
		
		win.show();
	}
	
	/**
	 * 显示图片
	 */
	function showImageWin(url){
		url = String(url).replace(/\\/g, "/");
		var id = Ext.id();
		var pid = Ext.id();
		var up = Ext.id();
		var down = Ext.id();
		var left = Ext.id();
		var right = Ext.id();
		var _in = Ext.id();
		var out = Ext.id();
		var zoom = Ext.id();
		var rotate = Ext.id();
		var win = new Ext.Window({
			title:'图纸显示',
			width:800,
			bodyStyle : 'background-color:#E5E3DF;',
			html:'<div id="' + id + '" style="display:none"><div class="nav"><div class="up" id="'+ up + '"></div><div class="right" id="' + right +'"></div><div class="down" id="' + down + '"></div><div class="left" id="' + left + '"></div><div class="zoom" id="' + zoom + '"></div><div class="in" id="'+ _in + '"></div><div class="out" id="' + out + '"></div></div><img id="' + pid + '" src="#" border="0" style="display:none"> </div>',
			height:600,
			constrainHeader : true,
			autoScrol:true,
			maximizable:true,
			listeners:{
				hide:function(){
					Ext.fly(id).hide();
				},
				show:{
					fn:function(){
						var image = Ext.get(pid);
						image.dom.src = url;
						image.show();
						new Ext.dd.DD(image, 'pic');
						Ext.fly(id).show();
						pageInit(id,pid,up,down,left,right,_in,out,zoom);
					},
					defer:1000
				}
			},bbar:[{
				text:'下载',
				handler:function(){
					window.open(url);
				}
			}]
		});
		
		win.show();
	}
	
	/**
	 * 初始参考信息的图纸文件等grid仅显示
	 * @param quoteInfo 报时表信息
	 * @param height GridPanel高度
	 */
	function showFilesGrid(quoteInfo,height,flag){
		var store = new Ext.data.Store({
			url: 'findRefFilesList.action',
			paramNames:{start:'page.start',limit:'page.limit'},
			baseParams:{'page.start':0,'page.limit':0,'refFiles.qid':quoteInfo.qid},
			autoLoad:flag,
			reader: new Ext.data.JsonReader({totalProperty: 'totalProperty',root: 'root'},
			[
				{name: 'fid'},
				{name: 'url'},
				{name: 'remark'},
				{name: 'qid'}
			])
		});
	    
	    
	    var expandId = Ext.id();
	     
		var grid = new Ext.grid.GridPanel({
			height : height,
			store: store,
			title:'参考文件',
			hideLabel:true,
			hidden:!Ext.ROLE_R10,
			isFormField:true,
		    autoExpandColumn: expandId,
			sm: new Ext.grid.RowSelectionModel(),
			colModel:new Ext.grid.ColumnModel({
				defaultSortable:true,
				defaultWidth:60,
				columns: [{header: '文件',dataIndex:'url',width:40,renderer:function(_v, cellmeta, record){
							var extensionName = _v.substring(_v.lastIndexOf('.') + 1);
							if(_v){
								var css = '.db-ft-' + extensionName.toLowerCase() + '-small';
								return '<div class="db-ft-' + extensionName.toLowerCase() + '-small" style="height: 16px;background-repeat: no-repeat;"/></div>';
							}
							return '<div class="db-ft-unknown-small" style="height: 16px;background-repeat: no-repeat;"/></div>';	
						}
					},
			        {header: '资料说明',dataIndex: 'remark',id:expandId}
			    ]
			}),
			listeners:{
				'rowdblclick':function(g,i,e){
					var r = g.getSelectionModel().getSelected();
					var _v = r.data.url;
					var extensionName = (_v.substring(_v.lastIndexOf('.') + 1)).toLowerCase();
					if(extensionName == 'pdf'){
						showPdfWin('../' + _v);	
						return;
					}
					if(extensionName == 'jpg' || extensionName == 'png' || extensionName == 'gif'){
						showImageWin('../' + _v);
						return;
					}
					window.open('../' + String(_v).replace(/\\/g, '/'));
				}
			}
		});
		
		return grid;
	}
	
	
	/**
	 * 报时表相关信息修改
	 */
	function showQuoteInfoCompleteCopyWin(r,store){
		Ext.Ajax.request({
		    url: 'copyQuoteInfos.action',
		    params: {'quoteInfo.qid': r.data.qid},
		    success: function(response){
				var obj = Ext.decode(response.responseText);
				var quoteInfo = obj.infos.quoteInfo;
				if(obj.success){
					var recordTime = quoteInfo.recordTime ? quoteInfo.recordTime.substring(0,10):new Date().clearTime();
					var modifyTime = quoteInfo.modifyTime ? quoteInfo.modifyTime.substring(0,10):new Date().clearTime();
					var innerWin = new Ext.Window({
						title:'编辑已复制的报时表',
						width:650,
						modal:true,
						resizable:false,
						height:245,
						bodyStyle:'overflow-x:hidden',
						listeners:{
							'close':function(){
								var record = new store.recordType(quoteInfo);
								record.set('recordTime',Date.parseDate(quoteInfo.recordTime,'Y-m-d H:i:s'));
								record.set('modifyTime',Date.parseDate(quoteInfo.modifyTime,'Y-m-d H:i:s'));
								record.commit();
								store.add(record);
							}
						},
						items:[{
							xtype:'form',
							labelWidth: 100,
							bodyStyle:'padding:15px 10px 10px 10px',
							border: false,
							items:[{
								xtype:'fieldset',
					            title: '报价资料',
					            items :[{
						            layout:'column',
						            defaults:{layout:'form',columnWidth:.5,border:false},
						            border:false,
						            items:[{
						            	defaults:{xtype:'textfield',anchor:'90%'},
						                items: [{
						                    fieldLabel: '客户信息',
						                    disabled:true,
						                    value:quoteInfo.customerName,
						                    name: 'quoteInfo.customerName'
						                }, {
						                    fieldLabel: '报时人',
						                    name: 'quoteInfo.quoter',
						                    value:quoteInfo.quoter
						                },{
						                	fieldLabel:'文件夹页码',
						                	name:'quoteInfo.pageNo',
						                	value:quoteInfo.pageNo
						                },{
						                    fieldLabel: '更新日期',
						                    xtype:'datefield',
						                    format:'Y-m-d',
						                    name: 'quoteInfo.modifyTime',
						                    value:modifyTime
						                },{
						                	xtype:'hidden',
						                    name: 'quoteInfo.qid',
						                    value: quoteInfo.qid
						                }]
						            },{
						            	defaults:{xtype:'textfield',anchor:'93%'},
						                items: [{
						                	fieldLabel:'客户类别',
						                	xtype:'combo',
						                	name:'quoteInfo.customerType',
						                	value:quoteInfo.customerType,
						                	typeAhead: true,
						                	allowBlank:false,
						                	disabled:true,
										    triggerAction: 'all',
										    mode: 'local',
										    store: new Ext.data.ArrayStore({
										        fields: ['text'],
										        data: [['主要客户'], ['其他客户']]
										    }),
										    valueField: 'text',
										    displayField: 'text'
						                },{
						                    fieldLabel: '产品编码',
						                    name: 'quoteInfo.productCode',
						                    value:quoteInfo.productCode
						                },{
						                	fieldLabel:'DCC-',
						                	name: 'quoteInfo.dccNo',
						                	value:quoteInfo.dccNo
						                },{
						                    fieldLabel: '报时日期',
						                    xtype:'datefield',
						                    format:'Y-m-d',
						                    name: 'quoteInfo.recordTime',
						                    value: recordTime
						                },{
						                	xtype:'hidden',
						                	name:'quoteInfo.price',
						                	value:quoteInfo.price
						                }]
						            }]
						        }]
					        }]
						}],
						fbar:[{
							text:'修改',
							handler:function(){
								var form = innerWin.get(0).getForm();
								if(form.isValid()){
									form.submit({
										waitTitle : '请稍候',
										waitMsg : '正在提交表单数据,请稍候...',
										url: 'updateQuoteInfo.action',
									    success: function(form, action) {
									    	if(action.result.success){
									    		Ext.Msg.alert('操作提示','修改成功');
									    		innerWin.close();
									    		store.reload();
									    	}
									    },
									    failure: function(form, action) {
									       	if (action.result) {
									        	Ext.Msg.show({
												   title:'错误信息',
												   msg:'发生错误了!',
												   value: action.result.infos.msg,
												   width:500,
												   buttons: Ext.Msg.OK,
												   multiline: true,
												   icon: Ext.MessageBox.ERROR
												});
									       }
									    }
									});						
								}
							}
						},{
							text:'取消',
							handler:function(){
								innerWin.close();
							}
						}]
					});
					
					innerWin.show();
				}
			},
		    failure: function(response){
				Ext.Msg.show('错误提示','数据加载失败');
			}
		});
	}
	
	/**
	 * 报时表相关信息修改
	 */
	function showQuoteInfoCompleteEditWin(r){
		Ext.Ajax.request({
		    url: 'findQuoteInfoById.action',
		    params: {'quoteInfo.qid': r.data.qid,'page.params.relation':false},
		    success: function(response){
				var obj = Ext.decode(response.responseText);
				if(obj.success){
					var quoteInfo = obj.infos.quoteInfo[0];
					var recordTime = quoteInfo.recordTime ? quoteInfo.recordTime.substring(0,10):new Date().clearTime();
					var modifyTime = quoteInfo.modifyTime ? quoteInfo.modifyTime.substring(0,10):new Date().clearTime();
					var innerWin = new Ext.Window({
						title:'修改报时表',
						width:650,
						modal:true,
						resizable:false,
						height:245,
						bodyStyle:'overflow-x:hidden',
						items:[{
							xtype:'form',
							labelWidth: 100,
							bodyStyle:'padding:15px 10px 10px 10px',
							border: false,
							items:[{
								xtype:'fieldset',
					            title: '报价资料',
					            items :[{
						            layout:'column',
						            defaults:{layout:'form',columnWidth:.5,border:false},
						            border:false,
						            items:[{
						            	defaults:{xtype:'textfield',anchor:'90%'},
						                items: [{
						                    fieldLabel: '客户信息',
						                    disabled:true,
						                    value:quoteInfo.customerName,
						                    name: 'quoteInfo.customerName'
						                }, {
						                    fieldLabel: '报时人',
						                    name: 'quoteInfo.quoter',
						                    value:quoteInfo.quoter
						                },{
						                	fieldLabel:'文件夹页码',
						                	name:'quoteInfo.pageNo',
						                	value:quoteInfo.pageNo
						                },{
						                    fieldLabel: '更新日期',
						                    xtype:'datefield',
						                    format:'Y-m-d',
						                    name: 'quoteInfo.modifyTime',
						                    value:modifyTime
						                },{
						                	xtype:'hidden',
						                    name: 'quoteInfo.qid',
						                    value: quoteInfo.qid
						                }]
						            },{
						            	defaults:{xtype:'textfield',anchor:'93%'},
						                items: [{
						                	fieldLabel:'客户类别',
						                	xtype:'combo',
						                	name:'quoteInfo.customerType',
						                	value:quoteInfo.customerType,
						                	typeAhead: true,
						                	allowBlank:false,
						                	disabled:true,
										    triggerAction: 'all',
										    mode: 'local',
										    store: new Ext.data.ArrayStore({
										        fields: ['text'],
										        data: [['主要客户'], ['其他客户']]
										    }),
										    valueField: 'text',
										    displayField: 'text'
						                },{
						                    fieldLabel: '产品编码',
						                    name: 'quoteInfo.productCode',
						                    value:quoteInfo.productCode
						                },{
						                	fieldLabel:'DCC-',
						                	name: 'quoteInfo.dccNo',
						                	value:quoteInfo.dccNo
						                },{
						                    fieldLabel: '报时日期',
						                    xtype:'datefield',
						                    format:'Y-m-d',
						                    name: 'quoteInfo.recordTime',
						                    value: recordTime
						                },{
						                	xtype:'hidden',
						                	name:'quoteInfo.price',
						                	value:quoteInfo.price
						                }]
						            }]
						        }]
					        }]
						}],
						fbar:[{
							text:'修改',
							handler:function(){
								var form = innerWin.get(0).getForm();
								if(form.isValid()){
									form.submit({
										waitTitle : '请稍候',
										waitMsg : '正在提交表单数据,请稍候...',
										url: 'updateQuoteInfo.action',
									    success: function(form, action) {
									    	if(action.result.success){
									    		Ext.Msg.alert('操作提示','修改成功');
									    		r.data = action.result.infos.quoteInfo;
									    		r.set('recordTime',Date.parseDate(action.result.infos.quoteInfo.recordTime,'Y-m-d H:i:s'));
									    		r.set('modifyTime',Date.parseDate(action.result.infos.quoteInfo.modifyTime,'Y-m-d H:i:s'));
									    		r.commit();
									    		innerWin.close();
									    	}
									    },
									    failure: function(form, action) {
									       	if (action.result) {
									        	Ext.Msg.show({
												   title:'错误信息',
												   msg:'发生错误了!',
												   value: action.result.infos.msg,
												   width:500,
												   buttons: Ext.Msg.OK,
												   multiline: true,
												   icon: Ext.MessageBox.ERROR
												});
									       }
									    }
									});						
								}
							}
						},{
							text:'取消',
							handler:function(){
								innerWin.close();
							}
						}]
					});
					
					innerWin.show();
				}
			},
		    failure: function(response){
				Ext.Msg.show('错误提示','数据加载失败');
			}
		});
	}
	
	/**
	 * 添加报时表基础信息
	 */
	function showQuoteInfoWinAdd(store){
		var innerWin = new Ext.Window({
			title:'添加报时表',
			width:650,
			modal:true,
			resizable:false,
			height:245,
			autoScroll:true,
			bodyStyle:'overflow-x:hidden',
			items:[{
				xtype:'form',
				url : 'saveQuoteInfo.action',
				labelWidth: 100,
				bodyStyle:'padding:15px 10px 10px 10px',
				border: false,
				items:[{
					xtype:'fieldset',
		            title: '报价资料',
		            items :[{
			            layout:'column',
			            defaults:{layout:'form',columnWidth:.5,border:false},
			            border:false,
			            items:[{
			            	defaults:{xtype:'textfield',anchor:'90%'},
			                items: [{
								xtype:'combo',
								fieldLabel:'客户名称',
								name: 'quoteInfo.customerName',
				            	valueField:'customerName',
				            	displayField:'customerName',
						        emptyText:'请选择客户...',
						        triggerAction: 'all',
					        	typeAhead: true,
						        forceSelection: true,
						        selectOnFocus:false,
						        editable:false,
						        allowBlank:false,
				            	store:{
				            		xtype: 'store',
									url: 'findCustomerList.action',
									paramNames:{start:'page.start',	limit:'page.limit'},
									baseParams:{'page.start':0,'page.limit':0},
									reader: new Ext.data.JsonReader(
										{totalProperty: 'totalProperty',root: 'root'},
										[{name:'cid'},{name:'customerName'},{name:'customerCode'},{name:'customerType'}]
									),
									listeners:{
										'load':function(){
											if(Ext.ROLE_R17){
												this.insert(0,new this.recordType({customerName:'<font color="red">--添加--</font>'}));
											}
										}
									}
							    },
							    listeners:{
							    	'beforeselect' : function(combo,record,index){
										if(index == 0 && record.data.customerName == '<font color="red">--添加--</font>'){
											var thiz = this;
											thiz.collapse();
											showCustomerWinAdd(function(customer){	//回调加载部门列表数据
												thiz.getStore().load({callback:function(r){
													if(r.length > 0){
														thiz.setValue(customer.customerName); //把增加的部门设定为当前value
														thiz.ownerCt.get(4).setValue(customer.cid);
														thiz.ownerCt.get(5).setValue(customer.customerType);
														thiz.el.frame('green',3,{duration: .2});
													}
												}});
											});
											return false;
										}
									},
							    	'select':function(combo,record){
							    		this.ownerCt.get(4).setValue(record.data.cid);
							    		this.ownerCt.get(5).setValue(record.data.customerType);
							    	}
							    }
							},{
			                    fieldLabel: '报时人',
			                    name: 'quoteInfo.quoter',
			                    value:'李显强'
			                },{
			                	fieldLabel:'文件夹页码',
			                	name:'quoteInfo.pageNo'
			                },{
			                    fieldLabel: '更新日期',
			                     xtype:'datefield'
			                    ,format:'Y-m-d',
			                    name: 'quoteInfo.modifyTime'
			                },{xtype:'hidden',name:'quoteInfo.cid'},{xtype:'hidden',name:'quoteInfo.customerType'}]
			            },{
			            	defaults:{xtype:'textfield',anchor:'93%'},
			                items: [{
			                    fieldLabel: '产品编码',
			                    name: 'quoteInfo.productCode'
			                },{
			                	fieldLabel:'DCC-',
			                	name: 'quoteInfo.dccNo'
			                },{
			                    fieldLabel: '报时日期',
			                    xtype:'datefield'
			                    ,format:'Y-m-d',
			                    value:new Date().clearTime(),
			                    name: 'quoteInfo.recordTime'
			                },{
			                	xtype:'hidden',
			                	name:'quoteInfo.price',
			                	value:0
			                },{
			                	xtype:'hidden',
			                	name:'quoteInfo.state',
			                	value:'未提交审核申请'
			                }]
			            }]
			        }]
		        }]
			}],
			fbar:[{
		        text: '添加',
		        handler: function(){
		        	var form = this.ownerCt.ownerCt.get(0);
		        	if(form.getForm().isValid()){
			        	form.getForm().submit({
							waitTitle : '请稍候',
							waitMsg : '正在提交表单数据,请稍候...',
							success : function(f, action) {
								if(action.result.success){
									if(action.result.infos.quoteInfo){
										Ext.Msg.show({
											title : '操作提示',
											msg : action.result.infos.msg||'添加成功',
											buttons : Ext.Msg.OK,
											icon : Ext.Msg.INFO
										});
										var record = new store.recordType(action.result.infos.quoteInfo);
							    		record.set('recordTime',Date.parseDate(action.result.infos.quoteInfo.recordTime,'Y-m-d H:i:s'));
							    		record.set('modifyTime',Date.parseDate(action.result.infos.quoteInfo.modifyTime,'Y-m-d H:i:s'));
							    		record.commit();
							    		store.insert(0,record);
									}
								}
								form.ownerCt.close();
							},
							failure : function(f,action) {
								Ext.Msg.show({
									title : '错误提示',
									msg : action.result.infos.msg||'添加失败',
									buttons : Ext.Msg.OK,
									icon : Ext.Msg.ERROR
								});
							}
						}); 
			        }
		        }
		    },{
				text:'取消',
				handler:function(){
					innerWin.close();
				}
			}]
		});
		
		innerWin.show();
	}
	

	/***
	 * 生产材料信息管理
	 */
	function showMaterialsWinManage(grid){
		var quoteInfoRecord = grid.getSelectionModel().getSelected();
		var quoteInfo = quoteInfoRecord.data;
		Ext.Ajax.request({
		   url: 'findMaterialsById.action',
		   params: {'quoteInfo.qid': quoteInfo.qid},
		   success: function(response){
				var obj = Ext.decode(response.responseText);
				if(obj.success){
					var materials = obj.infos.materials || {};
					var innerWin = new Ext.Window({
						title:materials.mid ? '编辑生产材料信息':'添加生产材料信息',
						width:650,
						modal:true,
						resizable:false,
						height:340,
						autoScroll:true,
						bodyStyle:'overflow-x:hidden',
						items:[{
							xtype:'form',
							url : materials.mid ? 'updateMaterials.action':'saveMaterials.action',
							labelWidth: 100,
							bodyStyle:'padding:15px 10px 10px 10px',
							border: false,
							items:[{
					        	xtype:'fieldset',
					            title: '生产材料信息',
					            items :[{
						            layout:'column',
						            defaults:{layout:'form',columnWidth:.5,border:false},
						            border:false,
						            items:[{
						            	defaults:{xtype:'textfield',anchor:'90%'},
						                items: [{
						                	fieldLabel: '材质',
						                	xtype:'combo',
								            valueField:'stuffid',
								        	displayField:'stuffName',
								        	queryParam:'stuff.stuffName',
									        typeAhead: true,
											triggerAction: 'all',
									        minChars:1,
									        listWidth:250,
									        allowBlank:false,
									        selectOnFocus:true,
									        submitValue:true,
									        pageSize:10,
								            id:'materials.stuffName',
									        name: 'materials.stuffName',
								            hiddenId:'materials.stuffid',
								            hiddenName:'materials.stuffid',
									        value: materials.stuffName,
									        hiddenValue:materials.stuffid,
									        ref: '../../../../comboStuff',
								        	store:{
								        		xtype: 'store',
												url: 'findStuffList.action',
												paramNames:{start:'page.start',	limit:'page.limit'},
												baseParams:{'page.start':0,'page.limit':10},
												reader: new Ext.data.JsonReader(
													{totalProperty: 'totalProperty',root: 'root'},
													[{name : 'stuffid'}, {name : 'stuffName'}]
												)
										    },
											listeners:{
												'select':function(){
													var comboSpecies = innerWin.comboSpecies;
													comboSpecies.enable();
													comboSpecies.clearValue();
													var sd = comboSpecies.getStore();
													sd.removeAll();
													sd.load();
													comboSpecies.onTriggerClick();
												}
											}
						                },{
						                	xtype:'combo',
						                	fieldLabel: '规格',
								            valueField:'specid',
								        	displayField:'specName',
								        	queryParam:'specification.specName',
									        typeAhead: true,
									        disabled: materials.mid ? false:true,
											triggerAction: 'all',
									        minChars:1,
									        tpl: specificationTpl,
									        listWidth:250,
									        selectOnFocus:true,
									        allowBlank:false,
									        pageSize:10,
									        ref: '../../../../comboSpecification',
									        id:'materials.specName',
									        name: 'materials.specName',
									        hiddenId:'materials.specid',
									        hiddenName:'materials.specid',
									        submitValue:true,
									        value: materials.specName,
									        hiddenValue:materials.specid,
								        	store:{
								        		xtype: 'store',
												url: 'findSpecificationList.action',
												paramNames:{start:'page.start',	limit:'page.limit'},
												baseParams:{'page.start':0,'page.limit':10},
												reader: new Ext.data.JsonReader(
													{totalProperty: 'totalProperty',root: 'root'},
													[{name : 'specid'},{name : 'specName'},{name : 'speciesid'},{name:'speciesName'},{name : 'stuffName'},{name:'price'}]
												),
												listeners:{
													'beforeload':function(sd){
														sd.setBaseParam('specification.stuffid',innerWin.comboStuff.hiddenField.value);
														sd.setBaseParam('specification.speciesid',innerWin.comboSpecies.hiddenField.value);
													}
												}
										    },
										    listeners: {
										    	'select':function(combo,record){
										    		innerWin.textfieldMaterialPrice.setValue(record.data.price);
										    	}
										    }
						                },{
						                    fieldLabel: '产品名称',
						                    allowBlank:false,
						                    name: 'materials.productsName',
						                    value:materials.productsName
						                },{
						                	fieldLabel: '支数',
						                	name: 'materials.count',
						                	xtype:'numberfield',
						                	decimalPrecision:6,
						                	minValue:0,
						                	value: materials.count
						                },{
						                    fieldLabel: '尺寸',
						                    name:'materials.size',
						                    value:materials.size
						                },{
						                	fieldLabel:'共损耗(%)',
						                	name:'materials.loss',
						                	value:materials.loss
						                },{
						                    fieldLabel: '产品材料单价(后台计算)',
						                    allowBlank:false,
						                    xtype:'numberfield',
						                    decimalPrecision:6,
						                    minValue:0,
						                    value:0,
						                    disabled:true,
						                    name: 'materials.price',
						                    value: materials.price
						                }]
						            },{
						            	defaults:{xtype:'textfield',anchor:'93%'},
						                items: [{
						                	xtype:'combo',
						                	fieldLabel: '种类',
								            valueField:'speciesid',
								        	displayField:'speciesName',
								        	queryParam:'species.speciesName',
									        typeAhead: true,
									        disabled:materials.mid ? false:true,
											triggerAction: 'all',
									        minChars:1,
									        tpl: resultTpl,
									        listWidth:250,
									        selectOnFocus:true,
									        allowBlank:false,
									        pageSize:10,
									        ref: '../../../../comboSpecies',
									        id:'materials.speciesName',
									        name:'materials.speciesName',
									        hiddenId: 'materials.speciesid',
									        hiddenName: 'materials.speciesid',
									        submitValue:true,
									        value: materials.speciesName,
									        hiddenValue:materials.speciesid,
								        	store:{
								        		xtype: 'store',
												url: 'findSpeciesList.action',
												paramNames:{start:'page.start',	limit:'page.limit'},
												baseParams:{'page.start':0,'page.limit':10},
												reader: new Ext.data.JsonReader(
													{totalProperty: 'totalProperty',root: 'root'},
													[{name : 'speciesid'},{name:'speciesName'},{name : 'stuffName'}]
												),
												listeners:{
													'beforeload':function(sd,options){
														sd.setBaseParam('species.stuffid',innerWin.comboStuff.hiddenField.value);
													}
												}
										    },
										    listeners:{
										    	'select':function(){
										    		var comboSpecification = innerWin.comboSpecification;
													comboSpecification.enable();
													comboSpecification.clearValue();
													var sd = comboSpecification.getStore();
													sd.removeAll();
													sd.load();
													comboSpecification.onTriggerClick();
										    	}
										    }
						                },{
						                    fieldLabel: '供应商材料单价',
						                    name:'materials.materialPrice',
						                    value:materials.materialPrice,
						                    xtype:'numberfield',
						                	decimalPrecision:6,
//						                    readOnly:true,
//						                    editable:false,
						                    ref: '../../../../textfieldMaterialPrice'
						                },{
						                    fieldLabel: '材料名称',
						                    allowBlank:false,
						                    name: 'materials.materialsName',
						                    value: materials.materialsName
						                }, {
						                    fieldLabel: '重量',
						                    name:'materials.weight',
						                    xtype:'numberfield',
					                    	decimalPrecision:6,
						                    value:materials.weight
						                },{
						                	fieldLabel: '减沙',
						                    xtype:'numberfield',
						                    decimalPrecision:6,
						                    allowBlank:false,
						                    minValue:0,
						                    value:0,
						                    name: 'materials.jiansha',
						                    value: materials.jiansha
						                },{
						                    fieldLabel: '直径',
						                    name: 'materials.diameter',
						                    value: materials.diameter
						                },{
						                	xtype:'hidden',
						                	name:'materials.qid',
						                	value:quoteInfo.qid
						                },{
						                	hidden:true,
						                	name:'materials.mid',
						                	value:materials.mid
						                },]
						            }]
						        }]
					        } ]
						}],
						fbar:[{
					        text: materials.mid ? '编辑':'添加',
					        handler: function(){
					        	var form = this.ownerCt.ownerCt.get(0);
					        	if(form.getForm().isValid()){
						        	form.getForm().submit({
										waitTitle : '请稍候',
										waitMsg : '正在提交表单数据,请稍候...',
										success : function(f, action) {
											if(action.result.success){
												if(action.result.infos.materials){
													Ext.Msg.show({
														title : '操作提示',
														msg : '操作成功',
														buttons : Ext.Msg.OK,
														icon : Ext.Msg.INFO
													});
												}
												if(action.result.infos.quoteInfo){
													quoteInfoRecord.set('price',action.result.infos.quoteInfo[0].price);
													quoteInfoRecord.commit();
												}
											}
											form.ownerCt.close();
										},
										failure : function(f,action) {
											Ext.Msg.show({
												title : '错误提示',
												msg : action.result.infos.msg||'添加失败',
												buttons : Ext.Msg.OK,
												icon : Ext.Msg.ERROR
											});
										}
									}); 
						        }
					        }
					    },{
							text:'取消',
							handler:function(){
								innerWin.close();
							}
						}]
					});
					innerWin.show();
					if(!materials.mid){innerWin.comboStuff.onTriggerClick()}
				}
		   }
		});
	}
	
	/***
	 * 参考信息管理
	 */
	function showReferenceInfoWinManage(grid){
		var quoteInfoRecord = grid.getSelectionModel().getSelected();
		var quoteInfo = quoteInfoRecord.data;
		Ext.Ajax.request({
		   url: 'findReferenceInfoById.action',
		   params: {'quoteInfo.qid': quoteInfo.qid},
		   success: function(response){
				var obj = Ext.decode(response.responseText);
				if(obj.success){
					var reference = obj.infos.reference || {};
					var innerWin = new Ext.Window({
						title:reference.rid ? '编辑参考信息':'添加参考信息',
						width:650,
						modal:true,
						//resizable:false,
						height:355,
						autoScroll:true,
						bodyStyle:'overflow-x:hidden',
						items:[{
							xtype:'form',
							url : reference.rid ? 'updateReferenceInfo.action':'saveReferenceInfo.action',
							labelWidth: 100,
							bodyStyle:'padding:15px 10px 10px 10px',
							border: false,
							items:[{
					        	xtype:'fieldset',
					            title: '参考信息',
					            items :[{
						            layout:'column',
						            defaults:{layout:'form',columnWidth:.5,border:false},
						            border:false,
						            items:[{
						            	defaults:{xtype:'textfield',anchor:'90%'},
						                items: [{
						                    fieldLabel: '产品总长',
						                    name: 'reference.general',
						                    maxLength:40,
						                    value: reference.general
						                }, {
						                    fieldLabel: '成品重量',
						                    allowBlank:false,
						                    maxLength:40,
						                    name: 'reference.finishedWeight',
						                    value: reference.finishedWeight
						                },{
						                    fieldLabel: '运费',
						                    name:'reference.freight',
						                    value:reference.freight,
						                    allowBlank:false,
						                    xtype:'numberfield',
						                    maxLength:40,
						                    decimalPrecision:6,
						                    minValue:0
					                    },{
						                	hidden:true,
						                	name:'reference.rid',
						                	value:reference.rid
						                }]
						            },{
						            	defaults:{xtype:'textfield',anchor:'93%'},
						                items: [{
						                    fieldLabel: '尾料',
						                    name: 'reference.residualMaterial',
						                    maxLength:40,
						                    value: reference.residualMaterial
						                },{
						                    fieldLabel: '一支料产量数',
						                    name:'reference.output',
						                    maxLength:40,
						                    value:reference.output
						                },{
						                    fieldLabel: 'MOQ',
						                    name:'reference.moq',
						                    maxLength:40,
						                    value:reference.moq
						                },{ 
						                	xtype:'hidden',
						                	name:'reference.qid',
						                	value:quoteInfo.qid
						                }]
						            }]
						        },{
				                    fieldLabel: '咭刀',
				                    xtype:'textarea',
				                    anchor:'97%',
				                    maxLength:400,
				                    name: 'reference.cardKnife',
				                    value: reference.cardKnife
				                },{
				                    fieldLabel: '机器型号',
				                    xtype:'textarea',
				                    anchor:'97%',
				                    maxLength:400,
				                    name: 'reference.machineModel',
				                    value: reference.machineModel
				                }]
					        }]
						}],
						fbar:[{
					        text: reference.rid ? '编辑':'添加',
					        handler: function(){
					        	var form = this.ownerCt.ownerCt.get(0);
					        	if(form.getForm().isValid()){
						        	form.getForm().submit({
										waitTitle : '请稍候',
										waitMsg : '正在提交表单数据,请稍候...',
										success : function(f, action) {
											if(action.result.success){
												if(action.result.infos.reference){
													Ext.Msg.show({
														title : '操作提示',
														msg : '操作成功',
														buttons : Ext.Msg.OK,
														icon : Ext.Msg.INFO
													});
												}
												if(action.result.infos.quoteInfo){
													quoteInfoRecord.set('price',action.result.infos.quoteInfo[0].price);
													quoteInfoRecord.commit();
												}
											}
											form.ownerCt.close();
										},
										failure : function(f,action) {
											Ext.Msg.show({
												title : '错误提示',
												msg : action.result.infos.msg||'添加失败',
												buttons : Ext.Msg.OK,
												icon : Ext.Msg.ERROR
											});
										}
									}); 
						        }
					        }
					    },{
							text:'取消',
							handler:function(){
								innerWin.close();
							}
						}]
					});
					innerWin.show();
				}
		   }
		});
	}
	
	
	/***
	 * 加工信息管理
	 */
	function showProcessInfoWinManage(grid){
		var quoteInfoRecord = grid.getSelectionModel().getSelected();
		var innerWin = new Ext.Window({
			title:'加工信息管理',
			width:650,
			modal:true,
			resizable:false,
			height:270,
			autoScroll:true,
			bodyStyle:'overflow-x:hidden',
			items:[initProcessInfoGrid(quoteInfoRecord,200,true)],
			fbar:[{
				text:'关闭',
				handler:function(){
					innerWin.close();
				}
			}]
		});
		innerWin.show();
	}
	
	
	/**
	 * 其他报价管理
	 */
	function showOtherPriceWinManage(grid){
		var quoteInfoRecord = grid.getSelectionModel().getSelected();
		var innerWin = new Ext.Window({
			title:'其他报价管理',
			width:650,
			modal:true,
			resizable:false,
			height:270,
			autoScroll:true,
			bodyStyle:'overflow-x:hidden',
			items:[initOtherPriceGrid(quoteInfoRecord,200,true)],
			fbar:[{
				text:'关闭',
				handler:function(){
					innerWin.close();
				}
			}]
		});
		innerWin.show();
	}
	
	/***
	 * 外发加工管理
	 */
	function showFoundryWinManage(grid){
		var quoteInfoRecord = grid.getSelectionModel().getSelected();
		var innerWin = new Ext.Window({
			title:'外发加工管理',
			width:650,
			modal:true,
			resizable:false,
			height:270,
			autoScroll:true,
			bodyStyle:'overflow-x:hidden',
			items:[initFoundryGrid(quoteInfoRecord,200,true)],
			fbar:[{
				text:'关闭',
				handler:function(){
					innerWin.close();
				}
			}]
		});
		innerWin.show();
	}
	
	/***
	 * 辅助信息管理
	 */
	function showAidsWinManage(grid){
		var quoteInfoRecord = grid.getSelectionModel().getSelected();
		var innerWin = new Ext.Window({
			title:'辅助信息管理',
			width:650,
			modal:true,
			resizable:false,
			height:270,
			autoScroll:true,
			bodyStyle:'overflow-x:hidden',
			items:[initAidsGrid(quoteInfoRecord,200,true)],
			fbar:[{
				text:'关闭',
				handler:function(){
					innerWin.close();
				}
			}]
		});
		innerWin.show();
	}
	
	
	
	/***
	 * 图纸管理
	 */
	function showFilesWinManage(grid){
		var quoteInfo = grid.getSelectionModel().getSelected().data;
		var innerWin = new Ext.Window({
			title:'图纸管理',
			width:650,
			modal:true,
			resizable:false,
			height:270,
			autoScroll:true,
			bodyStyle:'overflow-x:hidden',
			items:[initFilesGrid(quoteInfo,200,true)],
			fbar:[{
				text:'关闭',
				handler:function(){
					innerWin.close();
				}
			}]
		});
		innerWin.show();
	}
	
	/**
	 * 查看报时单流程日志
	 */
	function showWorkflowLogWin(grid){
		var quoteInfo = grid.getSelectionModel().getSelected().data;
		var win = new Ext.Window({
			title:'报时单审核流程操作日志',
			width:450,
			height: 300,
			autoScroll:true,
			bodyStyle:'overflow-x:hidden',
			bodyStyle:'padding:5px',
			listeners:{
				render:function(){
					var body = this.body;
					Ext.Ajax.request({
						url:'findWorkflowByQid.action',
						params:{'workflow.qid':quoteInfo.qid},
						success: function(response) {
					    	var obj = Ext.decode(response.responseText);
					    	if(obj.success){
					    		workflowTpl.append(body,obj.infos.workflowList);
					    	}else{
					    		Ext.Msg.alert('错误','查找日志发生出错!请刷新后重试!');
					    		win.close();
					    	}
					    },
					    failure: function(response) {
					    	Ext.Msg.alert('错误','server-side failure with status code ' + response.status);
					    }
					});
				}
			},
			fbar:[{
				text:'关闭',
				handler:function(){
					win.close();
				}
			}]
		});
		
		win.show();
	}
	
	/**
	 * 提交到审核窗体
	 */
	function showWorkflowSubmitWin(grid,title,state,msg,callback){
		var record = grid.getSelectionModel().getSelected();
		var quoteInfo = record.data;
		var win = new Ext.Window({
			title:title,
			width:450,
			modal:true,
			resizable:false,
			height: 160,
			autoScroll:true,
			bodyStyle:'overflow-x:hidden',
			layout:'fit',
			items:[{
				xtype:'form',
				labelWidth: 60,
				bodyStyle:'padding:15px 10px 10px 10px',
				url:'saveWorkflowLog.action',
				border:false,
				items:[{
					xtype:'textarea',
					fieldLabel:'说明',
					maxLength:200,
					width:340,
					name:'workflow.description'
				}]
			}],
			fbar:[{
				text: '提交',
				handler:function(){
					win.get(0).getForm().submit({
						waitTitle : '请稍候',
						waitMsg : '正在提交表单数据,请稍候...',
						params: {
							'workflow.qid':quoteInfo.qid,
							'workflow.state':state
						},
						success:function(form,action){
							if(action.result.success){
								var workflow = action.result.infos.workflow;
								record.set('state',workflow.state);
								record.commit();
					    		Ext.Msg.alert('操作提示', (msg || '操作成功'));
					    		win.close();
					    		if(callback){
					    			callback();
					    		}
					    	}
						},
					    failure: function(form, action) {
					       	if (action.result) {
					        	Ext.Msg.show({
								   title:'错误信息',
								   msg:'发生错误了!',
								   value: action.result.infos.msg,
								   width:500,
								   buttons: Ext.Msg.OK,
								   multiline: true,
								   icon: Ext.MessageBox.ERROR
								});
					       }
					    }
					});
				}
			},{
				text:'关闭',
				handler:function(){
					win.close();
				}
			}]
		});
		
		win.show();
	}
	
	/**
	 * 供应商材料单价调整添加窗体
	 */
	function showPriceListWinAdd(grid){
		var innerWin = new Ext.Window({
			title:'报时单副本原材料单价调节管理',
			width:400,
			modal:true,
			resizable:false,
			height:290,
			autoScroll:true,
			closeAciton:'close',
			bodyStyle:'overflow-x:hidden',
			layout:'fit',
			fbar:[{
				text:'调节',
				handler:function(){
					innerWin.get(0).getForm().submit({
						 waitTitle : '请稍候',
						 waitMsg : '正在提交表单数据,请稍候...',
						 success: function(form, action) {
					     	innerWin.close();
					     	var store = grid.getStore();
					     	var record = new store.recordType(action.result.infos.priceList);
					     	record.set('recordTime',Date.parseDate(action.result.infos.priceList.recordTime,'Y-m-d H:i:s'));
					     	record.commit();
					     	store.insert(0,record);
					     	grid.getSelectionModel().selectRow(0);
					     	Ext.get(grid.getView().getRow(0)).frame('green',2,{duration: .3});
					     },
					     failure: function(form, action) {
					        switch (action.failureType) {
					            case Ext.form.Action.CLIENT_INVALID:
					                Ext.Msg.alert('失败', '表单中可能包含无效内容');
					                break;
					            case Ext.form.Action.CONNECT_FAILURE:
					                Ext.Msg.alert('失败', 'Ajax连接失败');
					                break;
					            case Ext.form.Action.SERVER_INVALID:
					               Ext.Msg.alert('失败', action.result.msg);
					       }
					    }

					});
				}
			},{
				text:'关闭',
				handler:function(){
					innerWin.close();
				}
			}],
			items:[{
				xtype:'form',
				url : 'adjustQuoteInfos.action',
				labelWidth: 100,
				bodyStyle:'padding:15px 10px 10px 10px',
				border: false,
				items:[{
		        	xtype:'fieldset',
		            title: '供应商材料信息',
		            items :[{
			            layout:'column',
			            defaults:{layout:'form',columnWidth:1,border:false},
			            border:false,
			            items:[{
			            	defaults:{xtype:'textfield',anchor:'90%'},
			                items: [{
			                	fieldLabel: '材质',
			                	xtype:'combo',
					            valueField:'stuffid',
					        	displayField:'stuffName',
					        	queryParam:'stuff.stuffName',
						        typeAhead: true,
								triggerAction: 'all',
						        minChars:1,
						        listWidth:250,
						        selectOnFocus:true,
						        submitValue:true,
						        pageSize:10,
					            id:'materials.stuffName',
						        name: 'materials.stuffName',
					            hiddenId:'priceList.stuffid',
					            hiddenName:'priceList.stuffid',
						        ref: '../../../../comboStuff',
					        	store:{
					        		xtype: 'store',
									url: 'findStuffList.action',
									paramNames:{start:'page.start',	limit:'page.limit'},
									baseParams:{'page.start':0,'page.limit':10},
									reader: new Ext.data.JsonReader(
										{totalProperty: 'totalProperty',root: 'root'},
										[{name : 'stuffid'}, {name : 'stuffName'}]
									)
							    },
								listeners:{
									'select':function(){
										var comboSpecies = innerWin.comboSpecies;
										comboSpecies.enable();
										comboSpecies.clearValue();
										var sd = comboSpecies.getStore();
										sd.removeAll();
										sd.load();
										comboSpecies.onTriggerClick();
									}
								}
			                },{
			                	xtype:'combo',
			                	fieldLabel: '种类',
					            valueField:'speciesid',
					        	displayField:'speciesName',
					        	queryParam:'species.speciesName',
						        typeAhead: true,
						        disabled:true,
								triggerAction: 'all',
						        minChars:1,
						        tpl: resultTpl,
						        listWidth:250,
						        selectOnFocus:true,
						        pageSize:10,
						        ref: '../../../../comboSpecies',
						        id:'materials.speciesName',
						        name:'materials.speciesName',
						        hiddenId: 'priceList.speciesid',
						        hiddenName: 'priceList.speciesid',
						        submitValue:true,
					        	store:{
					        		xtype: 'store',
									url: 'findSpeciesList.action',
									paramNames:{start:'page.start',	limit:'page.limit'},
									baseParams:{'page.start':0,'page.limit':10},
									reader: new Ext.data.JsonReader(
										{totalProperty: 'totalProperty',root: 'root'},
										[{name : 'speciesid'},{name:'speciesName'},{name : 'stuffName'}]
									),
									listeners:{
										'beforeload':function(sd,options){
											sd.setBaseParam('species.stuffid',innerWin.comboStuff.hiddenField.value);
										}
									}
							    },
							    listeners:{
							    	'select':function(){
							    		var comboSpecification = innerWin.comboSpecification;
										comboSpecification.enable();
										comboSpecification.clearValue();
										var sd = comboSpecification.getStore();
										sd.removeAll();
										sd.load();
										comboSpecification.onTriggerClick();
							    	}
							    }
			                },{
			                	xtype:'combo',
			                	fieldLabel: '规格',
					            valueField:'specid',
					        	displayField:'specName',
					        	queryParam:'specification.specName',
						        typeAhead: true,
						        disabled:true,
								triggerAction: 'all',
						        minChars:1,
						        tpl: specificationTpl,
						        listWidth:250,
						        selectOnFocus:true,
						        allowBlank:false,
						        pageSize:10,
						        ref: '../../../../comboSpecification',
						        id:'priceList.specName',
						        name: 'priceList.specName',
						        hiddenId:'priceList.specid',
						        hiddenName:'priceList.specid',
						        submitValue:true,
					        	store:{
					        		xtype: 'store',
									url: 'findSpecificationList.action',
									paramNames:{start:'page.start',	limit:'page.limit'},
									baseParams:{'page.start':0,'page.limit':10},
									reader: new Ext.data.JsonReader(
										{totalProperty: 'totalProperty',root: 'root'},
										[{name : 'specid'},{name : 'specName'},{name : 'speciesid'},{name:'speciesName'},{name : 'stuffName'},{name:'price'}]
									),
									listeners:{
										'beforeload':function(sd){
											sd.setBaseParam('specification.stuffid',innerWin.comboStuff.hiddenField.value);
											sd.setBaseParam('specification.speciesid',innerWin.comboSpecies.hiddenField.value);
										}
									}
							    },
							    listeners: {
							    	'select':function(combo,record){
							    		innerWin.textfieldMaterialPrice.setValue(record.data.price);
							    	}
							    }
			                },{
			                    fieldLabel: '当前材料单价',
			                    disabled:true,
			                    ref: '../../../../textfieldMaterialPrice'
			                },{
			                	fieldLabel: '修改后的价格',
			                	name:'priceList.price',
			                	ref:'../../../../textfieldPriceList',
			                    allowBlank:false,
			                    xtype:'numberfield',
			                    decimalPrecision:6,
			                    minValue:0
			                },{
			                	fieldLabel: '说明',
			                	name:'priceList.remark'
			                }]
			            }]
			        }]
		        } ]
			}]
		});
		
		innerWin.show();
	}
	
	
	/**
	 * 显示信息
	 */
	function showQuoteInfos(r,store){
		Ext.Ajax.request({
		    url: 'findQuoteInfoById.action',
		    params: {'quoteInfo.qid': r.data.qid,'page.params.relation':true},
		    success: function(response){
				var obj = Ext.decode(response.responseText);
				if(obj.success){
					var quoteInfo = obj.infos.quoteInfo[0];
					var recordTime = quoteInfo.recordTime ? quoteInfo.recordTime.substring(0,10):'';
					var modifyTime = quoteInfo.modifyTime ? quoteInfo.modifyTime.substring(0,10):'';
					
					var aidsList = obj.infos.aids;
					var foundryList = obj.infos.foundry;
					var otherPriceList = obj.infos.otherPrice;
					var processList = obj.infos.process;
					var materials = obj.infos.materials[0] || {};
					var reference = obj.infos.reference[0] || {};
					var refFilesList = obj.infos.refFiles;
					var innerWin = new Ext.Window({
						title:'查看报时表',
						width:650,
						resizable:true,
						height:500,
						disabled:true,
						autoScroll:true,
						maximizable:true,
						listeners:{
							render:{
								fn:function(){
									function addData(store,array,callback){
										Ext.each(array,function(data){
											store.add([new store.recordType(data)]);
										});
									}
									var otherPriceStore = innerWin.get(0).get(2).getStore();
									addData(otherPriceStore,otherPriceList);
									var processStore = innerWin.get(0).get(5).getStore();
									addData(processStore,processList);
									var foundryStore = innerWin.get(0).get(6).getStore();
									addData(foundryStore,foundryList);
									var aidsStore = innerWin.get(0).get(7).getStore();
									addData(aidsStore,aidsList);
									var refFilesStore = innerWin.get(0).get(12).getStore();
									addData(refFilesStore,refFilesList);
									innerWin.enable();
								},
								delay: 1000
							},
							bodyresize:function(){
								innerWin.get(0).get(1).doLayout();
								innerWin.get(0).get(3).doLayout();
								innerWin.get(0).get(8).doLayout();
								innerWin.get(0).doLayout();
								
								var processGrid = innerWin.get(0).get(4);
								processGrid.fireEvent('headerclick',processGrid,0);
								
								var foundryGrid = innerWin.get(0).get(5);
								foundryGrid.fireEvent('headerclick',foundryGrid,0);
								
								var aidsGrid = innerWin.get(0).get(6);
								aidsGrid.fireEvent('headerclick',aidsGrid,0);
								
								var refFilesGrid = innerWin.get(0).get(10);
								refFilesGrid.fireEvent('headerclick',refFilesGrid,0);
								
								var relationQuoteInfosGrid = innerWin.get(0).get(11);
								relationQuoteInfosGrid.fireEvent('headerclick',relationQuoteInfosGrid,0);
							}
						},
						items:[{
							xtype:'form',
							labelWidth: 95,
							bodyStyle:'padding:15px 10px 10px 10px',
							border: false,
							items:[{
								html:'<b>报价资料</b><br><hr><br>',
								border:false 
							},{
					            layout:'column',
					            defaults:{layout:'form',columnWidth:.5,border:false},
					            border:false,
					            items:[{
					            	defaults:{readOnly:true,xtype:'textfield',anchor:'90%'},
					                items: [{
					                    fieldLabel: '客户信息',
					                    value:quoteInfo.customerName,
					                    name: 'quoteInfo.customerName'
					                }, {
					                    fieldLabel: '报时人',
					                    name: 'quoteInfo.quoter',
					                    value:quoteInfo.quoter
					                }, {
					                	fieldLabel:'文件夹页码',
					                	name:'quoteInfo.pageNo',
					                	value:quoteInfo.pageNo
					                },{
					                    fieldLabel: '更新日期',
					                    name: 'quoteInfo.modifyTime',
					                    value:modifyTime
					                },{
					                    fieldLabel: '单价',
					                    xtype:'numberfield',
					                    decimalPrecision:6,
					                    hidden:!Ext.ROLE_R06,
					                    hideLabel:!Ext.ROLE_R06,
					                    minValue:0,
					                    name:'quoteInfo.price',
					                    value:quoteInfo.price
					                },{
					                	xtype:'hidden',
					                    name: 'quoteInfo.qid',
					                    value: quoteInfo.qid
					                }]
					            },{
					            	defaults:{readOnly:true,xtype:'textfield',anchor:'93%'},
					                items: [{
					                	fieldLabel:'客户类别',
					                	name:'quoteInfo.customerType',
					                	value:quoteInfo.customerType
					                },{
					                    fieldLabel: '产品编码',
					                    name: 'quoteInfo.productCode',
					                    value:quoteInfo.productCode
					                },{
					                	fieldLabel:'DCC-',
					                	name: 'quoteInfo.dccNo',
					                	value:quoteInfo.dccNo
					                },{
					                    fieldLabel: '报时日期',
					                    name: 'quoteInfo.recordTime',
					                    value: recordTime
					                }]
					            }]
					        },showOtherPriceGrid(quoteInfo,150,false),{
					       	 	html:'<br><b>生产材料信息</b><br><hr><br>',
								border:false 
					        },{
					            layout:'column',
					            defaults:{layout:'form',columnWidth:.5,border:false},
					            border:false,
					            items:[{
					            	defaults:{readOnly:true,xtype:'textfield',anchor:'90%'},
					                items: [{
					                    fieldLabel: '产品名称',
					                    name: 'materials.productsName',
					                    value:materials.productsName
					                }, {
					                    fieldLabel: '直径',
					                    name: 'materials.diameter',
					                    value: materials.diameter
					                },{
					                    fieldLabel: '尺寸',
					                    name:'materials.size',
					                    value:materials.size
					                },{
					                	fieldLabel:'共损耗(%)',
					                	name:'materials.loss',
					                	value:materials.loss
					                }, {
					                    fieldLabel: '产品材料单价',
					                    xtype:'numberfield',
					                    decimalPrecision:6,
					                    value:materials.price,
					                    hidden:!Ext.ROLE_R08,
					                    hideLabel:!Ext.ROLE_R08,
					                    name: 'materials.price'
					                }]
					            },{
					            	defaults:{readOnly:true,xtype:'textfield',anchor:'93%'},
					                items: [{
					                    fieldLabel: '材料名称',
					                    name: 'materials.materialsName',
					                    value: materials.materialsName
					                },{
					                    fieldLabel: '重量',
					                    name:'materials.weight',
					                    value:materials.weight
					                },{
					                	fieldLabel: '减沙',
					                    xtype:'numberfield',
					                    decimalPrecision:6,
					                    hidden:!Ext.ROLE_R08,
					                    hideLabel:!Ext.ROLE_R08,
					                    name: 'materials.jiansha',
					                    value: materials.jiansha
					                },{
					                    fieldLabel: '供应商材料单价',
					                    name:'materials.materialPrice',
					                    hidden:!Ext.ROLE_R08,
					                    hideLabel:!Ext.ROLE_R08,
					                    value:materials.materialPrice
					                }]
						        }]
					        },showProcessInfoGrid(quoteInfo,150,false),showFoundryGrid(quoteInfo,150,false),showAidsGrid(quoteInfo,150,false),{
					       	 	html:'<br><b>参考信息</b><br><hr><br>',
								border:false 
					        },{
					            layout:'column',
					            defaults:{layout:'form',columnWidth:.5,border:false},
					            border:false,
					            items:[{
					            	defaults:{readOnly:true,xtype:'textfield',anchor:'90%'},
					                items: [{
					                    fieldLabel: '产品总长',
					                    name: 'reference.general',
					                    value: reference.general
					                }, {
					                    fieldLabel: '成品重量',
					                    allowBlank:false,
					                    name: 'reference.finishedWeight',
					                    value: reference.finishedWeight
					                },{
					                    fieldLabel: '运费',
					                    hidden:!Ext.ROLE_R08,
					                    hideLabel:!Ext.ROLE_R08,
					                    name:'reference.freight',
					                    value:reference.freight
				                    }]
					            },{
					            	defaults:{readOnly:true,xtype:'textfield',anchor:'93%'},
					                items: [{
					                    fieldLabel: '尾料',
					                    name: 'reference.residualMaterial',
					                    value: reference.residualMaterial
					                },{
					                    fieldLabel: '一支料产量数',
					                    name:'reference.output',
					                    value:reference.output
					                },{
					                    fieldLabel: 'MOQ',
					                    name:'reference.moq',
					                    value:reference.moq
				                    }]
						        }]
					        },{
			                    fieldLabel: '咭刀',
			                    xtype:'textarea',
			                    anchor:'97%',
			                    readOnly:true,
			                    name: 'reference.cardKnife',
			                    value: reference.cardKnife
			                }, {
			                    fieldLabel: '机器型号',
			                    xtype:'textarea',
			                    anchor:'97%',
			                    readOnly:true,
			                    name: 'reference.machineModel',
			                    value: reference.machineModel
			                },showFilesGrid(quoteInfo,150,false),showRelationQuoteInfos(quoteInfo,150,true)]
						}],
						fbar:[{
							text:'关闭',
							handler:function(){
								innerWin.close();
							}
						}]
					});
					
					innerWin.show();
				}
			},
		    failure: function(response){
				Ext.Msg.show('错误提示','数据加载失败');
			}
		});
	}
	
	
	/**
	 * 初始化报时grid
	 */
	function initQuoteInfoCheckGrid(){
		var store = new Ext.data.Store({
			url: 'findUnCheckedQuoteInfoList.action',
			paramNames:{start:'page.start',	limit:'page.limit'},
			baseParams:{'page.start':0,'page.limit':20},
			autoLoad:true,
			reader: new Ext.data.JsonReader({totalProperty: 'totalProperty',root: 'root'},
			[
				{name: 'qid'},
				{name: 'customerName',allowBlank:false},
				{name: 'customerType',allowBlank:false},
				{name: 'productCode',allowBlank:false},
				{name: 'pageNo'},
				{name: 'quoter'},
				{name: 'dccNo'},
				{name: 'price',type:'float'},
				{name: 'recordTime',type:'date',dateFormat: "Y-m-d H:i:s"},
				{name: 'modifyTime',type:'date',dateFormat: "Y-m-d H:i:s"},
				{name: 'state'},
				{name: 'ownerId'}
			])
	    });
	    
	    
	    var expandId = Ext.id();
	    
	    var sm = new Ext.grid.RowSelectionModel({
	    	singleSelect :true,
	        listeners: {
	            selectionchange: function(sm) {
	                if (sm.getCount()) {
	                    grid.tickButton.enable();
	                    grid.crossButton.enable();
	                } else {
	                    grid.tickButton.disable();
	                    grid.crossButton.disable();
	                }
	            }
	        }
	    });
	    
	    var grid = new Ext.grid.GridPanel({
	    	region:'center',
	        trackMouseOver:false, 
	        loadMask:true,
	    	store: store,
	    	margins:'-1 -1 -1 0',
	    	autoExpandColumn: expandId,
	        sm: sm,
	        colModel:new Ext.grid.ColumnModel({
				defaultSortable:true,
				defaults:{menuDisabled:true},
				defaultWidth:80,
				columns: [
					{header: '客户名称',dataIndex:'customerName',id:expandId},
					{header: '客户类别',dataIndex:'customerType'},
					{header: '产品编码',dataIndex:'productCode'},
					{header: '文件夹页码',dataIndex:'pageNo'},
					{header: '报时人',dataIndex:'quoter'},
					{header: 'DCC-编号',dataIndex:'dccNo'},
					{header: '单价',dataIndex:'price',xtype:'numbercolumn',hidden:!Ext.ROLE_R06,format:'0,000.000000'}, 
					{header: '填单日期',dataIndex:'recordTime',renderer:Ext.util.Format.dateRenderer('Y-m-d')},
					{header: '更新日期',dataIndex:'modifyTime',renderer:Ext.util.Format.dateRenderer('Y-m-d')},
					{header: '状态',dataIndex:'state',width:150}
	    	]}),
	    	tbar:[{
	    		text :'审核通过'
	    		,iconCls:'silk-tick'
	    		,ref:'../tickButton'
	    		,disabled:true
	    		,handler:function(){
	    			var record = grid.getSelectionModel().getSelected();
	    			if(record){
	    				showWorkflowSubmitWin(grid,'审核报时单[通过]','已审核','报时单已经成功审核!',function(){
	    					grid.getStore().remove(record);
	    				});
	    			}else{
	    				Ext.Msg.alert('操作提示','请先选择要操作的行.');
	    			}
	    		}
	    	},{
	    		text :'退回'
	    		,iconCls:'silk-cross'
	    		,ref:'../crossButton'
	    		,disabled:true
	    		,handler:function(){
	    			var record = grid.getSelectionModel().getSelected();
	    			if(record){
	    				showWorkflowSubmitWin(grid,'审核报时单[退回]','已退回','报时单已退回!',function(){
	    					grid.getStore().remove(record);
	    				});
	    			}else{
	    				Ext.Msg.alert('操作提示','请先选择要操作的行.');
	    			}
	    		}
	    	}],
	    	bbar:new Ext.PagingToolbar({
	    		store: store,
	    		displayInfo: true,
	    		pageSize:20
	    	})
	    });
	    
	    return grid;
	}
	
	/**
	 * 初始化报时grid
	 */
	function initQuoteInfoGrid(){
		var store = new Ext.data.Store({
			url: 'findQuoteInfoList.action',
			paramNames:{start:'page.start',	limit:'page.limit'},
			baseParams:{'page.start':0,'page.limit':20},
			reader: new Ext.data.JsonReader({totalProperty: 'totalProperty',root: 'root'},
			[
				{name: 'qid'},
				{name: 'customerName',allowBlank:false},
				{name: 'customerType',allowBlank:false},
				{name: 'productCode',allowBlank:false},
				{name: 'pageNo'},
				{name: 'quoter'},
				{name: 'dccNo'},
				{name: 'price',type:'float'},
				{name: 'recordTime',type:'date',dateFormat: "Y-m-d H:i:s"},
				{name: 'modifyTime',type:'date',dateFormat: "Y-m-d H:i:s"},
				{name: 'state'},
				{name: 'ownerId'}
			]),
			listeners:{
				beforeload:function(sd,option){
					var node = grid.previousSibling().getSelectionModel().getSelectedNode();
					if(node){
						var method = node.attributes.method;
						if(method == "findCustomerList"){
							sd.setBaseParam('page.params.queryLevel','customerType');
							sd.setBaseParam('page.params.queryValue',node.attributes.text);
						}
						if(method == "getYears"){
							sd.setBaseParam('page.params.queryLevel','quoteInfo');
							sd.setBaseParam('page.params.queryValue',node.attributes.field);
						}
						if(method == "getProductType"){
							sd.setBaseParam('page.params.queryLevel','quoteInfo');
							sd.setBaseParam('page.params.queryValue',node.parentNode.attributes.field)
						}
						if(method == "findQuoteInfoList"){
							sd.setBaseParam('page.params.queryLevel','quoteInfo');
							sd.setBaseParam('page.params.queryValue',node.parentNode.parentNode.attributes.field)
						}
					}else{
						sd.setBaseParam('page.params.queryLevel',null);
						sd.setBaseParam('page.params.queryValue',null);
					}
					
					return true;
				}
			}
	    });
	    
	    
	    var expandId = Ext.id();
	    
	    var sm = new Ext.grid.CheckboxSelectionModel({
	        listeners: {
	            selectionchange: function(sm) {
	            	var record = sm.getSelected();
	            	
	            	if(record){
		            	var state = record.data.state;
		            	if(state == '未提交审核申请'){
		            		grid.editButton.enable();
		            		grid.removeButton.enable();
		            		grid.addMaterialsButton.enable();
		            		grid.addOtherPriceButton.enable();
		                    grid.addProcessInfoButton.enable();
		                    grid.addFoundryButton.enable();
		                    grid.addAidsButton.enable();
		                    grid.addReferenceInfoButton.enable();
		                    grid.addFilesButton.enable();
		                	grid.submitCheckButton.enable();
		            	}
		            	if(state == '已提交审核申请'){
		            		grid.removeButton.disable();
		                    grid.editButton.disable();
		                    grid.copyButton.disable();
		                    grid.addMaterialsButton.disable();
		                    grid.addOtherPriceButton.disable();
		                    grid.addProcessInfoButton.disable();
		                    grid.addFoundryButton.disable();
		                    grid.addAidsButton.disable();
		                    grid.addReferenceInfoButton.disable();
		                    grid.addFilesButton.disable();
		            		grid.submitCheckButton.disable();
		            	}
		            	if(state == '已审核'){
		            		grid.editButton.enable();
		                	grid.copyButton.enable();
		                	grid.removeButton.disable();
		                	grid.addMaterialsButton.enable();
		                	grid.addOtherPriceButton.enable();
		                    grid.addProcessInfoButton.enable();
		                    grid.addFoundryButton.enable();
		                    grid.addAidsButton.enable();
		                    grid.addReferenceInfoButton.enable();
		                    grid.addFilesButton.enable();
		                    grid.submitCheckButton.disable();
		            	}
		            	if(state == '已退回'){
		            		grid.editButton.enable();
		                	grid.copyButton.disable();
		                    grid.removeButton.enable();
		                    grid.addMaterialsButton.enable();
		                    grid.addOtherPriceButton.enable();
		                    grid.addProcessInfoButton.enable();
		                    grid.addFoundryButton.enable();
		                    grid.addAidsButton.enable();
		                    grid.addReferenceInfoButton.enable();
		                    grid.addFilesButton.enable();
		                	grid.submitCheckButton.enable();
		            	}
		            }else{
		            	grid.removeButton.disable();
	                    grid.editButton.disable();
	                    grid.copyButton.disable();
	                    grid.addMaterialsButton.disable();
	                    grid.addOtherPriceButton.disable();
	                    grid.addProcessInfoButton.disable();
	                    grid.addFoundryButton.disable();
	                    grid.addAidsButton.disable();
	                    grid.addReferenceInfoButton.disable();
	                    grid.addFilesButton.disable();
	                    grid.submitCheckButton.disable();
		            }
	            }
	        }
	    });
	    
	    var grid = new Ext.grid.GridPanel({
	    	region:'center',
	        trackMouseOver:false, 
	        loadMask:true,
	    	store: store,
	    	margins:'-1 -1 -1 0',
	    	autoExpandColumn: expandId,
	        sm: sm,
	        colModel:new Ext.grid.ColumnModel({
				defaultSortable:true,
				defaults:{menuDisabled:true},
				defaultWidth:80,
				columns: [sm,
					{header: '客户名称',dataIndex:'customerName',id:expandId},
					{header: '客户类别',dataIndex:'customerType'},
					{header: '产品编码',dataIndex:'productCode'},
					{header: '文件夹页码',dataIndex:'pageNo'},
					{header: '报时人',dataIndex:'quoter'},
					{header: 'DCC-编号',dataIndex:'dccNo'},
					{header: '单价',dataIndex:'price',xtype:'numbercolumn',hidden:!Ext.ROLE_R06,format:'0,000.000000'}, 
					{header: '填单日期',dataIndex:'recordTime',renderer:Ext.util.Format.dateRenderer('Y-m-d')},
					{header: '更新日期',dataIndex:'modifyTime',renderer:Ext.util.Format.dateRenderer('Y-m-d')},
					{header: '状态',dataIndex:'state',width:150}
	    	]}),
	    	listeners:{
	    		'rowdblclick':function(g,i,e){
	    			var record = g.getSelectionModel().getSelected();
	    			if(record){
	    				showQuoteInfos(record,store)
	    			}else{
	    				Ext.Msg.alert('操作提示','请先选择要操作的行.');
	    			}
	    		}
	    	},
	    	tbar:{
	    		xtype:'toolbar',
	    		enableOverflow: true,
	    		items:['条件',initSearchField(store,null,[
		    		'page.params.condition',
		    		'quoteInfo.customerName',
		    		'quoteInfo.customerType',
		    		'quoteInfo.pageNo',
		    		'quoteInfo.price',
		    		'quoteInfo.productCode',
		    		'quoteInfo.quoter',
		    		'quoteInfo.dccNo',
		    		'refFiles.remark',
		    		'materials.productsName',
		    		'materials.materialsName',
		    		'materials.diameter'
		    	]),
		    	'->',{
		    		text :'添加'
		    		,iconCls:'silk-add'
		    		,ref:'../addButton'
		    		,hidden:!Ext.ROLE_R02
		    		,handler:function(){
		    			showQuoteInfoWinAdd(store);
		    		}
		    	},{
		    		text:'修改',
		    		disabled:true,
		    		hidden:!Ext.ROLE_R03,
		    		iconCls : 'silk-application-edit',
		    		ref:'../editButton',
		    		handler:function(){
		    			var record = grid.getSelectionModel().getSelected();
		    			if(record){
		    				showQuoteInfoCompleteEditWin(record)
		    			}else{
		    				Ext.Msg.alert('操作提示','请先选择要编辑的行.');
		    			}
		    		}
		    	},{xtype: 'tbseparator',hidden:!Ext.ROLE_R03},{
		    		text : '复制',
		    		disabled:true,
		    		hidden:!Ext.ROLE_R04,
		    		iconCls : 'silk-table-multiple',
		    		ref:'../copyButton',
		    		handler:function(){
						var record = grid.getSelectionModel().getSelected();
		    			if(record){
		    				Ext.Msg.confirm('操作提示', '<nobr>确定要复制当前选中的报时单?</nobr>', function(btn) {
								if ('yes' == btn) {
		    						showQuoteInfoCompleteCopyWin(record,store)
								}
		    				});
		    			}else{
		    				Ext.Msg.alert('操作提示','请先选择要复制的行.');
		    			}
		    		}
		    	},{
					text : '删除',
					disabled:true,
					hidden:!Ext.ROLE_R05,
					iconCls : 'silk-delete',
					ref: '../removeButton',
					handler:function(){
						deleteRecords('deleteQuoteInfo.action','page.params.ids','qid',grid,store);
					}
				},{xtype: 'tbseparator',hidden:!Ext.ROLE_R05},{
					text:'其他报价管理',
					ref:'../addOtherPriceButton',
					disabled:true,
					hidden:!Ext.ROLE_R12,
					handler:function(){
		    			var record = grid.getSelectionModel().getSelected();
		    			if(record){
		    				showOtherPriceWinManage(grid);
		    			}else{
		    				Ext.Msg.alert('操作提示','请先选择要操作的行.');
		    			}
		    		}
				},{
		    		text:'生产材料信息',
		    		iconCls:'silk-cog',
		    		ref:'../addMaterialsButton',
		    		disabled:true,
		    		hidden:!Ext.ROLE_R11,
		    		handler:function(){
		    			var record = grid.getSelectionModel().getSelected();
		    			if(record){
		    				showMaterialsWinManage(grid);
		    			}else{
		    				Ext.Msg.alert('操作提示','请先选择要操作的行.');
		    			}
		    		}
		    	},{
		    		text : '加工信息',
		    		iconCls:'silk-cog',
		    		ref:'../addProcessInfoButton',
		    		disabled:true,
		    		hidden:!Ext.ROLE_R12,
		    		handler:function(){
		    			var record = grid.getSelectionModel().getSelected();
		    			if(record){
		    				showProcessInfoWinManage(grid);
		    			}else{
		    				Ext.Msg.alert('操作提示','请先选择要操作的行.');
		    			}
		    		}
		    	},{
		    		text : '外发加工',
		    		iconCls:'silk-lorry',
		    		ref:'../addFoundryButton',
		    		disabled:true,
		    		hidden:!Ext.ROLE_R13,
		    		handler:function(){
		    			var record = grid.getSelectionModel().getSelected();
		    			if(record){
		    				showFoundryWinManage(grid);
		    			}else{
		    				Ext.Msg.alert('操作提示','请先选择要操作的行.');
		    			}
		    		}
		    	},{xtype: 'tbseparator',hidden:!Ext.ROLE_R13},{
		    		text : '辅助信息',
		    		iconCls:'silk-tag-blue-edit',
		    		ref:'../addAidsButton',
		    		disabled:true,
		    		hidden:!Ext.ROLE_R14,
		    		handler:function(){
		    			var record = grid.getSelectionModel().getSelected();
		    			if(record){
		    				showAidsWinManage(grid);
		    			}else{
		    				Ext.Msg.alert('操作提示','请先选择要操作的行.');
		    			}
		    		}
		    	},{xtype: 'tbseparator',hidden:!Ext.ROLE_R14},{
		    		text : '参考信息',
		    		iconCls:'silk-application-view-list',
		    		ref:'../addReferenceInfoButton',
		    		disabled:true,
		    		hidden:!Ext.ROLE_R15,
		    		handler:function(){
		    			var record = grid.getSelectionModel().getSelected();
		    			if(record){
		    				showReferenceInfoWinManage(grid);
		    			}else{
		    				Ext.Msg.alert('操作提示','请先选择要操作的行.');
		    			}
		    		}
		    	},{
		    		text : '图纸',
		    		iconCls:'silk-application-view-gallery',
		    		ref:'../addFilesButton',
		    		disabled:true,
		    		hidden:!Ext.ROLE_R16,
		    		handler:function(){
		    			var record = grid.getSelectionModel().getSelected();
		    			if(record){
		    				showFilesWinManage(grid);
		    			}else{
		    				Ext.Msg.alert('操作提示','请先选择要操作的行.');
		    			}
		    		}
		    	},{xtype: 'tbseparator',hidden:!Ext.ROLE_R16},{
		    		text : '提交审核',
		    		iconCls:'silk-page-go',
		    		ref:'../submitCheckButton',
		    		disabled:true,
		    		handler:function(){
		    			var record = grid.getSelectionModel().getSelected();
		    			if(record){
		    				showWorkflowSubmitWin(grid,'提交审核申请','已提交审核申请','成功提交审核申请!',function(){
		    					grid.getSelectionModel().fireEvent('selectionchange',grid.getSelectionModel());
		    				});
		    			}else{
		    				Ext.Msg.alert('操作提示','请先选择要操作的行.');
		    			}
		    		}
		    	},{
		    		text : '流程日志',
		    		iconCls:'icon-collapse-all',
		    		ref:'../workflowLogButton',
		    		handler:function(){
		    			var record = grid.getSelectionModel().getSelected();
		    			if(record){
		    				showWorkflowLogWin(grid);
		    			}else{
		    				Ext.Msg.alert('操作提示','请先选择要操作的行.');
		    			}
		    		}
		    	}]
	    	},
	    	bbar:new Ext.PagingToolbar({
	    		store: store,
	    		displayInfo: true,
	    		pageSize:20
	    	})
	    });
	    
	     var panel = {
	    	xtype:'panel',
	    	layout:'border',
	    	region:'center',
	    	items:[{
	    		region:'west',
	    		xtype:'treepanel',
	    		split: true,
	    		width:180,
	    		collapsible: true,		 
				animCollapse:false,
				margins:'-1 0 -1 -1',
				collapseMode: 'mini',
				autoScroll:true,
                containerScroll: true,
                root : {
                	nodeType: 'async',
                	expanded: true,
		            children: [{
		                text: '主要客户',
		                singleClickExpand:true,
		                method: 'findCustomerList'
		            }, {
		                text: '其他客户',
		                singleClickExpand:true,
		                method: 'findCustomerList'
		            },{
		            	text: '已审核',
		            	method: 'checked',
		            	leaf: true
		            },{
		            	text: '已提交审核申请(未审核)',
		            	method: 'submited',
		            	leaf: true
		            },{
		            	text: '未提交审核申请',
		            	method: 'unsubmited',
		            	leaf: true
		            },{
		            	text: '已退回',
		            	method: 'returned',
		            	leaf: true
		            },{
		            	text: '原始',
		            	method: 'original',
		            	leaf: true
		            },{
		            	text: '非原始',
		            	method: 'unoriginal',
		            	leaf: true
		            }]
				},
				listeners:{
					'click':function(node){
            			if(node.isLeaf()){
            				var sd = node.getOwnerTree().nextSibling().getStore();
            				if(node.attributes.method == 'checked'){
            					sd.load({params:{'quoteInfo.state':'已审核','page.params.sub':true}});
            					return;
            				}
            				if(node.attributes.method == 'submited'){
            					sd.load({params:{'quoteInfo.state':'已提交审核申请','page.params.sub':true}});
            					return;
            				}
            				if(node.attributes.method == 'unsubmited'){
            					sd.load({params:{'quoteInfo.state':'未提交审核申请','page.params.sub':true}});
            					return;
            				}
            				if(node.attributes.method == 'returned'){
            					sd.load({params:{'quoteInfo.state':'已退回','page.params.sub':true}});
            					return;
            				}
            				if(node.attributes.method == 'original'){
            					sd.load({params:{'page.params.version':'native','page.params.sub':true}});
            					return;
            				}
            				if(node.attributes.method == 'unoriginal'){
            					sd.load({params:{'page.params.version':'copy','page.params.sub':true}});
            					return;
            				}
            				
            				sd.load({params:{'quoteInfo.qid':node.id}});
            			}
            		}
				},
                rootVisible: false,
                tbar:['->',{
                	text:'刷新',
                	iconCls : 'x-tbar-loading',
                	handler:function(){
                		this.ownerCt.ownerCt.setRootNode({
                			nodeType: 'async',
		                	expanded: true,
				            children: [{
				                text: '主要客户',
				                singleClickExpand:true,
				                method: 'findCustomerList'
				            }, {
				                text: '其他客户',
				                singleClickExpand:true,
				                method: 'findCustomerList'
				            }]
                		});
                		this.ownerCt.ownerCt.getSelectionModel().clearSelections();
                	}
                }],
                loader: new Ext.ux.tree.JsonPluginTreeLoader({
                	url:'findTreeData.action',
                	listeners : {
                		'beforeload':function(loader, node){
                			if(node.attributes.method == 'findCustomerList'){
                				loader.baseParams = {
                					'page.params.method' : 'findCustomerList',
                					'page.params.field': node.text
                				};
                			}
                			if(node.attributes.method == 'getYears'){
                				loader.baseParams = {
                					'page.params.method' : 'getYears',
                					'page.params.field' : node.text
                				}
                			}
                			if(node.attributes.method == 'getProductType'){
                				loader.baseParams = {
                					'page.params.method' : 'getProductType',
                					'page.params.field' : node.attributes.field,
                					'page.params.year' : node.attributes.text,
                					'page.params.cid':node.parentNode.attributes.field
                				}
                			}
                			if(node.attributes.method == 'findQuoteInfoList'){
                				loader.baseParams = {
                					'page.params.method' : 'findQuoteInfoList',
                					'page.params.field' : node.attributes.field,
                					'page.params.year' : node.parentNode.attributes.text,
                					'page.params.cid':node.parentNode.parentNode.attributes.field
                				}	
                			}
                		}
                	}
                })
	    	},grid]
	    }
	    
	    return panel;
	}
	
	
	/**
	 * 查看系统日志
	 */
	function initSystemLogGrid(){
		var store = new Ext.data.Store({
			url: 'findSystemLogList.action',
			paramNames:{start:'page.start',	limit:'page.limit'},
			baseParams:{'page.start':0,'page.limit':20},
			autoLoad:true,
			reader: new Ext.data.JsonReader({totalProperty: 'totalProperty',root: 'root'},
			[
				{name: 'logid'},
				{name: 'userid'},
				{name: 'username'},
				{name: 'exeOperation'},
				{name: 'recordTime',type:'date',dateFormat: "Y-m-d H:i:s"},
			])
	    });
	    
	    
	    var expandId = Ext.id();
	    var grid = new Ext.grid.GridPanel({
	    	region:'center',
	    	border: false,
	        trackMouseOver:false, 
	        loadMask:true,
	    	store: store,
	    	autoExpandColumn: expandId,
	        sm: new Ext.grid.RowSelectionModel(),
	        colModel:new Ext.grid.ColumnModel({
				defaultSortable:true,
				defaultWidth:120,
				columns: [
					{header: '操作人',dataIndex:'userid',renderer:function(value,cellmeta,record){return record.data.username;}},
					{header: '操作说明',dataIndex:'exeOperation',id:expandId},
					{header: '操作时间',dataIndex:'recordTime',renderer:Ext.util.Format.dateRenderer('Y-m-d H:i:s')}
	    	]}),
	    	tbar:['查询条件',initSearchField(store,null,['log.username','log.exeOperation'])],
	    	bbar:new Ext.PagingToolbar({
	    		store: store,
	    		displayInfo: true,
	    		pageSize:20
	    	})
	    });
	    
	    return grid;
	}
	
	/**
	 * 原材料价格调节功能
	 */
	function initPriceListGrid(){
		var store = new Ext.data.Store({
			url: 'findPriceList.action',
			paramNames:{start:'page.start',	limit:'page.limit'},
			baseParams:{'page.start':0,'page.limit':20},
			autoLoad:true,
			reader: new Ext.data.JsonReader({totalProperty: 'totalProperty',root: 'root'},
			[
				{name: 'listid'},
				{name: 'stuffName'},
				{name: 'speciesName'},
				{name: 'specName'},
				{name: 'price',type:'float'},
				{name: 'remark'},
				{name: 'recordTime',type:'date',dateFormat: "Y-m-d H:i:s"},
			])
	    });
	    
	    
	    var expandId = Ext.id();
	    var grid = new Ext.grid.GridPanel({
	    	region:'center',
	    	border: false,
	        trackMouseOver:false, 
	        loadMask:true,
	    	store: store,
	    	autoExpandColumn: expandId,
	        sm: new Ext.grid.RowSelectionModel(),
	        colModel:new Ext.grid.ColumnModel({
				defaultSortable:true,
				defaultWidth:120,
				columns: [
					{header: '材质名称',dataIndex:'stuffName'},
					{header: '种类名称',dataIndex:'speciesName'},
					{header: '规格名称',dataIndex:'specName'},
					{header: '说明',dataIndex:'remark',id:expandId},
					{header: '单价',dataIndex:'price',xtype:'numbercolumn',hidden:!Ext.ROLE_R06,format:'0,000.000000'}, 
					{header: '调整日期',dataIndex:'recordTime',renderer:Ext.util.Format.dateRenderer('Y-m-d')}
	    	]}),
	    	tbar:['说明',initSearchField(store,null,['priceList.remark']),'->',{
	    		text :'调节'
	    		,iconCls:'silk-add'
	    		,ref:'../addButton'
	    		,handler:function(){
	    			showPriceListWinAdd(grid);
	    		}
	    	}],
	    	bbar:new Ext.PagingToolbar({
	    		store: store,
	    		displayInfo: true,
	    		pageSize:20
	    	})
	    });
	    
	    return grid;
	
		
	}
	
	/**
	 * 初始化规格grid
	 */
	function initSpecificationGrid(){
		var store = new Ext.data.Store({
			url: 'findSpecificationList.action',
			paramNames:{start:'page.start',	limit:'page.limit'},
			baseParams:{'page.start':0,'page.limit':20},
			autoLoad:true,
			reader: new Ext.data.JsonReader({totalProperty: 'totalProperty',root: 'root'},
			[
				{name: 'specid'},
				{name: 'stuffid',allowBlank:false},
				{name: 'stuffName',allowBlank:false},
				{name: 'speciesid',allowBlank:false},
				{name: 'speciesName',allowBlank:false},
				{name: 'specName',allowBlank:false},
				{name: 'price',allowBlank:false}
			]),
			listeners:{
				'update': function(thiz, record, operation){
					var editorStuff = getEditor('stuffid',store,grid);
					var editorSpecies = getEditor('speciesid',store,grid);
					
					if(operation == Ext.data.Record.EDIT){
						if(record.data.specid){
							Ext.Ajax.request({
							    url: 'updateSpecification.action',
							    params: {
									'specification.specid': record.data.specid,		
									'specification.stuffid': editorStuff.hiddenField.value,		
									'specification.stuffName': editorStuff.getRawValue(),
							    	'specification.speciesid':editorSpecies.hiddenField.value,
									'specification.speciesName': editorSpecies.getRawValue(),
									'specification.specName': record.data.specName,
									'specification.price': record.data.price
								},
							    success: function(response) {
							    	var obj = Ext.decode(response.responseText);
							    	if(obj.success){
							    		record.beginEdit();
							    		record.data = obj.infos.specification;
							    		record.commit();
							    		thiz.commitChanges();
							    	}else{
							    		thiz.rejectChanges();
							    	}
							    },
							    failure: function(response) {
							    	Ext.Msg.alert('错误','server-side failure with status code ' + response.status);
							    	thiz.rejectChanges();
							    }
						    });
						}else{//无id则为新增
							Ext.Ajax.request({
							    url: 'saveSpecification.action',
							    params: {
									'specification.stuffid': record.data.stuffid,		
									'specification.stuffName': editorStuff.getRawValue(),
							    	'specification.speciesid':record.data.speciesid,
									'specification.speciesName': editorSpecies.getRawValue(),
									'specification.specName': record.data.specName,
									'specification.price': record.data.price
								},
							    success: function(response) {
							    	var obj = Ext.decode(response.responseText);
							    	if(obj.success){
							    		record.beginEdit();
							    		record.data = obj.infos.specification;
							    		record.commit();
							    		grid.getSelectionModel().selectRow(0);
							    		Ext.get(grid.getView().getRow(0)).frame('green',2,{duration: .3});
							    	}
							    },
							    failure: function(response) {
							    	Ext.Msg.alert('错误','server-side failure with status code ' + response.status);
							    	thiz.rejectChanges();
							    }
						    });
						}
						
					}
				}
			}
	    });
	    
	    
	    var expandId = Ext.id();
	    
	    var sm = new Ext.grid.CheckboxSelectionModel({
	        listeners: {
	            selectionchange: function(sm) {
	                if (sm.getCount()) {
	                    grid.removeButton.enable();
	                } else {
	                    grid.removeButton.disable();
	                }
	            }
	        }
	    });
	    
	    var editor = initEditor(null,null,2);
	    editor.on({
	    	'invalid':function(record,sd){
		    	Ext.get(grid.getView().getRow(0)).fadeOut({
				    endOpacity: 0, 
				    easing: 'easeOut',
				    duration: .5,
				    remove: false,
				    useDisplay: false,
				    callback:function(){
				    	sd.remove(record);
				    }
				});
		    },
		    'beforeedit':function(roweditor,rowIndex){
		    	if(roweditor.items == null){
		    		 roweditor.render(roweditor.grid.getView().getEditorParent());
		    		   if(!this.initialized){
			               this.initFields();
			           }
		    	}
		    	roweditor.items.get(2).getStore().reload();
		    }
	    });
	    
	    
	    var grid = new Ext.grid.GridPanel({
	    	region:'center',
	    	border: false,
	        trackMouseOver:false, 
	        loadMask:true,
	    	store: store,
	    	autoExpandColumn: expandId,
	        sm: sm,
	        plugins:editor,
	        colModel:new Ext.grid.ColumnModel({
				defaultSortable:true,
				defaultWidth:120,
				columns: [sm,
					{header: '材质',dataIndex:'stuffid',editor:{
						xtype:'combo',
			            valueField:'stuffid',
			        	displayField:'stuffName',
			        	queryParam:'stuff.stuffName',
				        typeAhead: true,
						triggerAction: 'all',
				        minChars:1,
				        listWidth:250,
				        allowBlank:false,
				        selectOnFocus:true,
				        id:'stuff.stuffName',
				        name:'stuff.stuffName',
				        hiddenId:'stuff.stuffid',
				        hiddenName:'stuff.stuffid',
				        submitValue:true,
				        pageSize:10,
			        	store:{
			        		xtype: 'store',
							url: 'findStuffList.action',
							paramNames:{start:'page.start',	limit:'page.limit'},
							baseParams:{'page.start':0,'page.limit':10},
							reader: new Ext.data.JsonReader(
								{totalProperty: 'totalProperty',root: 'root'},
								[{name : 'stuffid'}, {name : 'stuffName'}]
							)
					    },
						listeners:{
							'select':function(combo){
								var index = grid.getStore().indexOf(grid.getSelectionModel().getSelected());
								var editorSpecies = getEditor('speciesid',index,grid);
								editorSpecies.enable();
								editorSpecies.clearValue();
								var sd = editorSpecies.getStore();
								sd.removeAll();
								sd.load();
								editorSpecies.onTriggerClick();
							}
						}
					},renderer:function(value, cellmeta, record){
				    	return record.data.stuffName;
				    }},
					{header: '种类名称',dataIndex:'speciesid',editor:{
						xtype:'combo',
			            valueField:'speciesid',
			        	displayField:'speciesName',
			        	queryParam:'species.speciesName',
				        typeAhead: true,
						triggerAction: 'all',
				        minChars:1,
				        tpl: resultTpl,
				        listWidth:250,
				        selectOnFocus:true,
				        allowBlank:false,
				        pageSize:10,
				        id:'species.speciesName',
				        name:'species.speciesName',
				        hiddenId:'species.speciesid',
				        hiddenName:'species.speciesid',
				        submitValue:true,
			        	store:{
			        		xtype: 'store',
							url: 'findSpeciesList.action',
							paramNames:{start:'page.start',	limit:'page.limit'},
							baseParams:{'page.start':0,'page.limit':10},
							reader: new Ext.data.JsonReader(
								{totalProperty: 'totalProperty',root: 'root'},
								[{name : 'speciesid'},{name:'speciesName'},{name : 'stuffName'}]
							),
							listeners:{
								'beforeload':function(sd){
									var index = grid.getStore().indexOf(grid.getSelectionModel().getSelected());
									var editorStuff = getEditor('stuffid',index,grid);
									if(editorStuff.rendered){
										sd.setBaseParam('species.stuffid',editorStuff.hiddenField.value);
									}
								}
							}
					    }
					},renderer:function(value, cellmeta, record){
				    	return record.data.speciesName;
					}}, 
	    			{header: '规格名称',dataIndex:'specName',id:expandId,editor:{xtype:'textfield',maxLength:20,allowBlank:false}},
					{header: '供应商材料单价',dataIndex:'price',editor:{xtype:'numberfield',name:'price',allowBlank:false,decimalPrecision:6}}
	    		]
	    	}),
	    	tbar:['查询条件',initSearchField(store,null,['specification.specName','specification.stuffName','specification.speciesName']),'->',{
	    		text :'添加'
	    		,iconCls:'silk-add'
	    		,ref:'../addButton'
	    		,handler:function(){
	    			var specification = new grid.store.recordType({
	    				stuffid:'',
	    				stuffName:'',
	    				speciesid:'',
			            speciesName : '',
			            specName : '',
			            price: ''
			        });
			        editor.stopEditing();
			        grid.store.insert(0, specification);
			        editor.startEditing(0);
	    		}
	    	},{
				text : '删除',
				disabled:true,
				iconCls : 'silk-delete',
				ref: '../removeButton',
				handler:function(){
					editor.stopEditing(false);
					deleteRecords('deleteSpecification.action','page.params.ids','specid',grid,store);
				}
			}],
	    	bbar:new Ext.PagingToolbar({
	    		store: store,
	    		displayInfo: true,
	    		pageSize:20
	    	})
	    });
	    
	    return grid;
	}
	
	/**
	 * 初始化种类grid
	 */
	function initSpeciesGrid(){
		var store = new Ext.data.Store({
			url: 'findSpeciesList.action',
			paramNames:{start:'page.start',	limit:'page.limit'},
			autoLoad:true,
			baseParams:{'page.start':0,'page.limit':20},
			reader: new Ext.data.JsonReader({totalProperty: 'totalProperty',root: 'root'},
			[
				{name: 'speciesid'},
				{name: 'speciesName',allowBlank:false},
				{name: 'stuffid',allowBlank:false},
				{name: 'stuffName'}
			]),
			listeners:{
				'update': function(thiz, record, operation){
					var editorStuff = getEditor('stuffid',store,grid);
					if(operation == Ext.data.Record.EDIT){
						if(record.data.speciesid){	 
							Ext.Ajax.request({
							    url: 'updateSpecies.action',
							    params: {
							    	'species.speciesid':record.data.speciesid,
									'species.speciesName': record.data.speciesName,	
									'species.stuffid': record.data.stuffid,	
									'species.stuffName': editorStuff.getRawValue()	
								},
							    success: function(response) {
							    	var obj = Ext.decode(response.responseText);
							    	if(obj.success){
							    		record.beginEdit();
							    		record.data = obj.infos.species;
							    		record.commit();
							    		thiz.commitChanges();
							    	}else{
							    		thiz.rejectChanges();
							    	}
							    },
							    failure: function(response) {
							    	Ext.Msg.alert('错误','server-side failure with status code ' + response.status);
							    	thiz.rejectChanges();
							    }
						    });
						}else{
							Ext.Ajax.request({
							    url: 'saveSpecies.action',
							    params: {
									'species.speciesName': record.data.speciesName,	
									'species.stuffid': record.data.stuffid,	
									'species.stuffName': editorStuff.getRawValue()	
								},
							    success: function(response) {
							    	var obj = Ext.decode(response.responseText);
							    	if(obj.success){
							    		record.beginEdit();
							    		record.data = obj.infos.species;
							    		record.commit();
							    		grid.getSelectionModel().selectRow(0);
							    		Ext.get(grid.getView().getRow(0)).frame('green',2,{duration: .3});
							    	}
							    },
							    failure: function(response) {
							    	Ext.Msg.alert('错误','server-side failure with status code ' + response.status);
							    	thiz.rejectChanges();
							    }
						    });
						}
						
					}
				}
			}
	    });
	    
	    
	    var expandId = Ext.id();
	    
	    var sm = new Ext.grid.CheckboxSelectionModel({
	        listeners: {
	            selectionchange: function(sm) {
	                if (sm.getCount()) {
	                    grid.removeButton.enable();
	                } else {
	                    grid.removeButton.disable();
	                }
	            }
	        }
	    });
	    
	    var editor = initEditor(null,null,2);
	    editor.on('invalid',function(record,sd){
	    	Ext.get(grid.getView().getRow(0)).fadeOut({
			    endOpacity: 0, 
			    easing: 'easeOut',
			    duration: .5,
			    remove: false,
			    useDisplay: false,
			    callback:function(){
			    	sd.remove(record);
			    }
			});
	    });
	    
	    
	    var grid = new Ext.grid.GridPanel({
	    	region:'center',
	    	border: false,
	        trackMouseOver:false, 
	        loadMask:true,
	    	store: store,
	    	autoExpandColumn: expandId,
	        sm: sm,
	        plugins:editor,
	        colModel:new Ext.grid.ColumnModel({
				defaultSortable:true,
				defaultWidth:80,
				columns: [sm,
					{header: '材质名称',dataIndex:'stuffid',editor:{
						xtype:'combo',
			            valueField:'stuffid',
			        	displayField:'stuffName',
				        emptyText:'请选择材质...',
				        typeAhead: true,
				        editable:false,
				        allowBlank:false,
						triggerAction: 'all',
			        	store:{
			        		xtype: 'store',
							url: 'findStuffList.action',
							baseParams:{'page.start':0,'page.limit':0},
							reader: new Ext.data.JsonReader(
								{totalProperty: 'totalProperty',root: 'root'},
								[{name : 'stuffid'},{name:'stuffName'}]
							)
					    }
					},renderer:function(value, cellmeta, record){
				    	return record.data.stuffName;
				    }},
				    {header: '种类名称',dataIndex:'speciesName',id:expandId,editor:{xtype:'textfield',allowBlank:false}},
	    	]}),
	    	bbar:new Ext.PagingToolbar({
	    		store: store,
	    		displayInfo: true,
	    		pageSize:20
	    	}),
	    	tbar:['种类名称',initSearchField(store,null,['species.speciesName','species.stuffName']),
	    	'->',{
	    		text :'添加'
	    		,iconCls:'silk-add'
	    		,ref:'../addButton'
	    		,handler:function(){
	    			var species = new grid.store.recordType({
	    				speciesName:'',
						stuffid:'',	
						stuffName:''
			        });
			        editor.stopEditing();
			        grid.store.insert(0, species);
			        editor.startEditing(0);
	    		}
	    	},{
				text : '删除',
				disabled:true,
				iconCls : 'silk-delete',
				ref: '../removeButton',
				handler:function(){
					editor.stopEditing(false);
					deleteRecords('deleteSpecies.action','page.params.ids','speciesid',grid,store);
				}
			}]
	    });
	    
	    return grid;
	}
	
	/**
	 * 材质管理
	 */
	function initStuffGrid(){
		var store = new Ext.data.Store({
			url: 'findStuffList.action',
			paramNames:{start:'page.start',	limit:'page.limit'},
			autoLoad:true,
			baseParams:{'page.start':0,'page.limit':20},
			reader: new Ext.data.JsonReader({totalProperty: 'totalProperty',root: 'root'},
			[
				{name: 'stuffid'},
				{name: 'stuffName',allowBlank:false},
			]),
			listeners:{
				'update': function(thiz, record, operation){
					if(operation == Ext.data.Record.EDIT){
						if(record.data.stuffid){	//有id则为更新
							Ext.Ajax.request({
							    url: 'updateStuff.action',
							    params: {
							    	'stuff.stuffid':record.data.stuffid,
									'stuff.stuffName': record.data.stuffName	
								},
							    success: function(response) {
							    	var obj = Ext.decode(response.responseText);
							    	if(obj.success){
							    		record.beginEdit();
							    		record.data = obj.infos.customer;
							    		record.commit();
							    		thiz.commitChanges();
							    	}else{
							    		thiz.rejectChanges();
							    	}
							    },
							    failure: function(response) {
							    	Ext.Msg.alert('错误','server-side failure with status code ' + response.status);
							    	thiz.rejectChanges();
							    }
						    });
						}else{
							Ext.Ajax.request({
							    url: 'saveStuff.action',
							    params: {
									'stuff.stuffName': record.data.stuffName
								},
							    success: function(response) {
							    	var obj = Ext.decode(response.responseText);
							    	if(obj.success){
							    		record.beginEdit();
							    		record.data = obj.infos.stuff;
							    		record.commit();
							    		grid.getSelectionModel().selectRow(0);
							    		Ext.get(grid.getView().getRow(0)).frame('green',2,{duration: .3});
							    	}else{
							    		Ext.Msg.alert('操作提示',obj.infos.msg);
							    		thiz.rejectChanges();
							    		thiz.remove(record);
							    	}
							    },
							    failure: function(response) {
							    	Ext.Msg.alert('错误','server-side failure with status code ' + response.status);
							    	thiz.rejectChanges();
							    }
						    });
						}
						
					}
				}
			}
	    });
	    
	    
	    var expandId = Ext.id();
	    
	    var sm = new Ext.grid.CheckboxSelectionModel({
	        listeners: {
	            selectionchange: function(sm) {
	                if (sm.getCount()) {
	                    grid.removeButton.enable();
	                } else {
	                    grid.removeButton.disable();
	                }
	            }
	        }
	    });
	    
	    var editor = initEditor(null,null,2);
	    editor.on('invalid',function(record,sd){
	    	Ext.get(grid.getView().getRow(0)).fadeOut({
			    endOpacity: 0, 
			    easing: 'easeOut',
			    duration: .5,
			    remove: false,
			    useDisplay: false,
			    callback:function(){
			    	sd.remove(record);
			    }
			});
	    });
	    
	    
	    var grid = new Ext.grid.GridPanel({
	    	region:'center',
	    	border: false,
	        trackMouseOver:false, 
	        loadMask:true,
	    	store: store,
	    	autoExpandColumn: expandId,
	        sm: sm,
	        plugins:editor,
	        colModel:new Ext.grid.ColumnModel({
				defaultSortable:true,
				defaultWidth:80,
				columns: [sm,
					{header: '材质名称',dataIndex:'stuffName',id:expandId,editor:{xtype:'textfield',maxLength:20,allowBlank:false}}
	    	]}),
	    	bbar:new Ext.PagingToolbar({
	    		store: store,
	    		displayInfo: true,
	    		pageSize:20
	    	}),
	    	tbar:['材质名称',initSearchField(store,null,['stuff.stuffName']),
	    	'->',{
	    		text :'添加'
	    		,iconCls:'silk-add'
	    		,ref:'../addButton'
	    		,handler:function(){
	    			var sutff = new grid.store.recordType({
			            stuffName : ''
			        });
			        editor.stopEditing();
			        grid.store.insert(0, sutff);
			        editor.startEditing(0);
	    		}
	    	},{
				text : '删除',
				disabled:true,
				iconCls : 'silk-delete',
				ref: '../removeButton',
				handler:function(){
					editor.stopEditing(false);
					deleteRecords('deleteStuff.action','page.params.ids','stuffid',grid,store);
				}
			}]
	    });
	    
	    return grid;
	
	}
	
	
	/**
	 * 初始化客户管理
	 */
	function initCustomerManageGrid(){
		var store = new Ext.data.Store({
			url: 'findCustomerList.action',
			paramNames:{start:'page.start',	limit:'page.limit'},
			autoLoad:true,
			baseParams:{'page.start':0,'page.limit':20},
			reader: new Ext.data.JsonReader({totalProperty: 'totalProperty',root: 'root'},
			[
				{name: 'cid'},
				{name: 'customerName',allowBlank:false},
				{name: 'customerType',allowBlank:false},
				{name: 'productCode',allowBlank:false},
				{name: 'address'},
				{name: 'tell'},
				{name: 'fax'},
				{name: 'email'}
			]),
			listeners:{
				'update': function(thiz, record, operation){
					if(operation == Ext.data.Record.EDIT){
						if(record.data.cid){	//有id则为更新
							Ext.Ajax.request({
							    url: 'updateCustomer.action',
							    params: {
							    	'customer.cid':record.data.cid,
									'customer.customerName': record.data.customerName,		
									'customer.customerType': record.data.customerType,
									'customer.productCode': record.data.productCode,
									'customer.address': record.data.address,
									'customer.tell': record.data.tell,
									'customer.fax': record.data.fax,
									'customer.email': record.data.email
								},
							    success: function(response) {
							    	var obj = Ext.decode(response.responseText);
							    	if(obj.success){
							    		record.beginEdit();
							    		record.data = obj.infos.customer;
							    		record.commit();
							    		thiz.commitChanges();
							    	}else{
							    		thiz.rejectChanges();
							    	}
							    },
							    failure: function(response) {
							    	Ext.Msg.alert('错误','server-side failure with status code ' + response.status);
							    	thiz.rejectChanges();
							    }
						    });
						}else{//无id则为新增
							Ext.Ajax.request({
							    url: 'saveCustomer.action',
							    params: {
									'customer.customerName': record.data.customerName,
									'customer.customerType': record.data.customerType,
									'customer.productCode': record.data.productCode,
									'customer.address': record.data.address,
									'customer.tell': record.data.tell,
									'customer.fax': record.data.fax,
									'customer.email': record.data.email
								},
							    success: function(response) {
							    	var obj = Ext.decode(response.responseText);
							    	if(obj.success){
							    		record.beginEdit();
							    		record.data = obj.infos.customer;
							    		record.commit();
							    		grid.getSelectionModel().selectRow(0);
							    		Ext.get(grid.getView().getRow(0)).frame('green',2,{duration: .3});
							    	}else{
							    		Ext.Msg.alert('操作提示',obj.infos.msg);
							    		thiz.rejectChanges();
							    		thiz.remove(record);
							    	}
							    },
							    failure: function(response) {
							    	Ext.Msg.alert('错误','server-side failure with status code ' + response.status);
							    	thiz.rejectChanges();
							    }
						    });
						}
						
					}
				}
			}
	    });
	    
	    
	    var expandId = Ext.id();
	    
	    var sm = new Ext.grid.CheckboxSelectionModel({
	        listeners: {
	            selectionchange: function(sm) {
	                if (sm.getCount()) {
	                    grid.removeButton.enable();
	                } else {
	                    grid.removeButton.disable();
	                }
	            }
	        }
	    });
	    
	    var editor = initEditor(null,null,2);
	    editor.on('invalid',function(record,sd){
	    	Ext.get(grid.getView().getRow(0)).fadeOut({
			    endOpacity: 0, 
			    easing: 'easeOut',
			    duration: .5,
			    remove: false,
			    useDisplay: false,
			    callback:function(){
			    	sd.remove(record);
			    }
			});
	    });
	    
	    
	    var grid = new Ext.grid.GridPanel({
	    	region:'center',
	    	border: false,
	        trackMouseOver:false, 
	        loadMask:true,
	    	store: store,
	    	autoExpandColumn: expandId,
	        sm: sm,
	        plugins:editor,
	        colModel:new Ext.grid.ColumnModel({
				defaultSortable:true,
				defaultWidth:80,
				columns: [sm,
					{header: '客户名称',dataIndex:'customerName',editor:{xtype:'textfield',maxLength:20,allowBlank:false}},
					{header: '客户类别',dataIndex:'customerType',editor:{
						xtype:'combo',
	                	name:'customerType',
	                	typeAhead: true,
	                	allowBlank:false,
	                	editable:false,
					    triggerAction: 'all',
					    mode: 'local',
					    store: new Ext.data.ArrayStore({
					        fields: ['text'],
					        data: [['主要客户'], ['其他客户']]
					    }),
					    valueField: 'text',
					    displayField: 'text'
					}},
					{header: '产品类型编组',dataIndex:'productCode',id:expandId,editor:{xtype:'textfield',allowBlank:false}},
					{header: '地址',dataIndex:'address',editor:{xtype:'textfield',maxLength:40}},
					{header: '电话',dataIndex:'tell',editor:{xtype:'textfield',maxLength:20}},
					{header: '传真',dataIndex:'fax',editor:{xtype:'textfield',maxLength:20}}, 
					{header: '邮件地址',dataIndex:'email',editor:{xtype:'textfield',maxLength:40}}
	    	]}),
	    	bbar:new Ext.PagingToolbar({
	    		store: store,
	    		displayInfo: true,
	    		pageSize:20
	    	}),
	    	tbar:['客户信息',initSearchField(store,null,['customer.customerName','customer.customerType','customer.productCode','customer.address','customer.tell','customer.fax','customer.email']),
	    	'->',{
	    		text :'添加'
	    		,iconCls:'silk-add'
	    		,ref:'../addButton'
	    		,handler:function(){
	    			var customer = new grid.store.recordType({
			            customerName : '',
			            customerType: '',
			            productCode: '',
			            address: '',
			            tell: '',
			            fax : '',
			            email : ''
			        });
			        editor.stopEditing();
			        grid.store.insert(0, customer);
			        editor.startEditing(0);
	    		}
	    	},{
				text : '删除',
				disabled:true,
				iconCls : 'silk-delete',
				ref: '../removeButton',
				handler:function(){
					editor.stopEditing(false);
					deleteRecords('deleteCustomer.action','page.params.ids','cid',grid,store);
				}
			}]
	    });
	    
	    return grid;
	}
	
	/**
	 * 初始化用户管理
	 */
	function initUserGrid(){
		var store = new Ext.data.Store({
			url: 'findUserList.action',
			paramNames:{start:'page.start',	limit:'page.limit'},
			baseParams:{'page.start':0,'page.limit':20},
			autoLoad:true,
			reader: new Ext.data.JsonReader({totalProperty: 'totalProperty',root: 'root'},
			[
				{name: 'userid'},
				{name: 'username',allowBlank:false},
				{name: 'password',allowBlank:false},
				{name: 'name',allowBlank:false},
				{name: 'rid'}
			]),
			listeners:{
				'update': function(thiz, record, operation){
					if(operation == Ext.data.Record.EDIT){
						if(record.data.userid){
							Ext.Ajax.request({
							    url: 'updateUser.action',
							    params: {
									'user.userid': record.data.userid,		
									'user.password': record.data.password,
									'user.name': record.data.name
								},
							    success: function(response) {
							    	var obj = Ext.decode(response.responseText);
							    	if(obj.success){
							    		record.beginEdit();
							    		record.data = obj.infos.user;
							    		record.commit();
							    		thiz.commitChanges();
							    	}else{
							    		thiz.rejectChanges();
							    	}
							    },
							    failure: function(response) {
							    	Ext.Msg.alert('错误','server-side failure with status code ' + response.status);
							    	thiz.rejectChanges();
							    }
						    });
						}else{//无id则为新增
							Ext.Ajax.request({
							    url: 'saveUser.action',
							    params: {
									'user.username': record.data.username,
									'user.password': record.data.password,
									'user.name': record.data.name
								},
							    success: function(response) {
							    	var obj = Ext.decode(response.responseText);
							    	if(obj.success){
							    		record.beginEdit();
							    		record.data = obj.infos.user;
							    		record.commit();
							    		grid.getSelectionModel().selectRow(0);
							    		Ext.get(grid.getView().getRow(0)).frame('green',2,{duration: .3});
							    	}else{
							    		Ext.Msg.show({
											title : '错误提示',
											msg : obj.infos.msg||'添加失败',
											buttons : Ext.Msg.OK,
											icon : Ext.Msg.ERROR
										});
							    		thiz.rejectChanges();
							    		thiz.remove(thiz.getAt(0));
							    	}
							    },
							    failure: function(response) {
							    	Ext.Msg.alert('错误','server-side failure with status code ' + response.status);
							    	thiz.rejectChanges();
							    }
						    });
						}
						
					}
				}
			}
	    });
	    
	    var expandId = Ext.id();
	    
	    var sm = new Ext.grid.CheckboxSelectionModel({
	        listeners: {
	            selectionchange: function(sm) {
	                if (sm.getCount()) {
	                	var user = sm.getSelected().data;
	                	if(user.userid == Ext.USER_ID){
	                		grid.roleButton.disable();
	                		grid.removeButton.disable();
	                	}else{
		                    grid.removeButton.enable();
		                    if(user.rid){
			                    grid.roleButton.enable();
		                    }else{
		                    	grid.roleButton.disable();
		                    }
	                    }
	                }else {
	                    grid.removeButton.disable();
	                    grid.roleButton.disable();
	                }
	            }
	        }
	    });
	    
	    var editor = initEditor(null,null,2);
	    editor.on('invalid',function(record,sd){
	    	Ext.get(grid.getView().getRow(0)).fadeOut({
			    endOpacity: 0, 
			    easing: 'easeOut',
			    duration: .5,
			    remove: false,
			    useDisplay: false,
			    callback:function(){
			    	sd.remove(record);
			    }
			});
	    });
	    
	    var grid = new Ext.grid.GridPanel({
	    	region:'center',
	    	border: false,
	        trackMouseOver:false, 
	        loadMask:true,
	    	store: store,
	    	autoExpandColumn: expandId,
	        sm: sm,
	        plugins:editor,
	        colModel:new Ext.grid.ColumnModel({
				defaultSortable:true,
				defaultWidth:120,
				columns: [sm,
					{header: '帐号',dataIndex:'username',editor:{xtype:'textfield',maxLength:20,allowBlank:false,disabled:true}},
					{header: '密码',dataIndex:'password',id:expandId,editor:{xtype:'textfield',maxLength:50}},
					{header: '姓名',dataIndex:'name',editor:{xtype:'textfield',maxLength:20}}
	    		]
	    	}),
	    	tbar:['用户信息',initSearchField(store,null,['user.name','user.username']),'->',{
	    		text : '权限管理',
	    		iconCls : 'silk-key',
	    		ref : '../roleButton',
	    		disabled:true,
	    		handler:function(){
	    			showRoleManageWindow(grid);
	    		}
	    	},'-',{
	    		text :'添加'
	    		,iconCls:'silk-add'
	    		,ref:'../addButton'
	    		,handler:function(){
	    			var user = new grid.store.recordType({
			            username : '',
			            password: '',
			            name: '',
			            manager: '',
			            tell : ''
			        });
			        editor.stopEditing();
			        grid.store.insert(0, user);
			        editor.startEditing(0);
			        var _edit = getEditor('username',store,grid);
			        _edit.enable();
	    		}
	    	},{
				text : '删除',
				disabled:true,
				iconCls : 'silk-delete',
				ref: '../removeButton',
				handler:function(){
					editor.stopEditing(false);
					deleteRecords('deleteUser.action','page.params.ids','userid',grid,store);
				}
			}],
	    	bbar:new Ext.PagingToolbar({
	    		store: store,
	    		displayInfo: true,
	    		pageSize:20
	    	})
	    });
	    
	    return grid;
	}
	
	/**
	 * 打开权限管理窗体
	 * @param grid 弹出窗体所属的GridPanel
	 */
	function showRoleManageWindow(grid){
		var record = grid.getSelectionModel().getSelected();
		var win = new Ext.Window({
			title : '权限管理',
			iconCls:'silk-key',
			width:540,
			height:230,
			modal:true,
			//resizable:false,
			items : [{
				xtype :'form',
				defaults:{xtype :'checkboxgroup',hideLabel:true},
				border:false,
				bodyStyle:'padding:10px',
				items : [{
					ref:'../group1',
		            items: [
		                {boxLabel: '用户管理', ref:'../userManage2',name: 'r01',handler:function(){
		                	win.userManage.setValue(this.checked);
		                }},
		                {boxLabel: '添加报时表', name: 'r02',handler:function(){
		                	win.addQuoteInfo.setValue(this.checked);
		                }},
		                {boxLabel: '编辑报时表', name: 'r03',handler:function(){
		                	win.editQuoteInfo.setValue(this.checked);
		                }},
		                {boxLabel: '复制报时表', name: 'r04',handler:function(){
		                	win.copyQuoteInfo.setValue(this.checked);
		                }}
		            ]
				},{
					ref:'../group2',
		            items: [
		                {boxLabel: '删除报时表', name: 'r05',handler:function(){
		                	win.deleteQuoteInfo.setValue(this.checked);
		                }},
		                {boxLabel: '显示总价', name: 'r06',handler:function(){
		                	win.showTotalPrice.setValue(this.checked);
		                }},
		                {boxLabel: '显示生产单价', name: 'r07',handler:function(){
		                	win.showMaterialsPrice.setValue(this.checked);
		                }},
		                {boxLabel: '显示辅助单价', name: 'r08',handler:function(){
		                	win.showAidsPrice.setValue(this.checked);
		                }}
		            ]
				},{
					ref:'../group3',
		            items: [
		                {boxLabel: '密码修改', name: 'r09',handler:function(){
		                	win.changePassword.setValue(this.checked);
		                }},
		                {boxLabel: '显示图纸', name: 'r10',handler:function(){
		                	win.showRefFiles.setValue(this.checked);
		                }},
		                {boxLabel: '生产材料信息管理', name: 'r11',handler:function(){
		                	win.materialsManage.setValue(this.checked);
		                }},
		                {boxLabel: '加工信息管理', name: 'r12',handler:function(){
		                	win.processInfoManage.setValue(this.checked);
		                }}
		            ]
				},{
					ref:'../group4',
		            items: [
		                {boxLabel: '外发加工管理', name: 'r13',handler:function(){
		                	win.foundryManage.setValue(this.checked);
		                }},
		                {boxLabel: '辅助信息管理', name: 'r14',handler:function(){
		                	win.aidsManage.setValue(this.checked);
		                }},
		                {boxLabel: '参考信息管理', name: 'r15',handler:function(){
		                	win.referenceInfoManage.setValue(this.checked);
		                }},
		                {boxLabel: '图纸管理', name: 'r16',handler:function(){
		                	win.refFilesManage.setValue(this.checked);
		                }}
		            ]
				},{
					ref:'../group5',
		            items: [
		                {boxLabel: '客户信息管理', name: 'r17',handler:function(){
		                	win.customerManage.setValue(this.checked);
		                }},
		                {boxLabel: '审核报时表', name: 'r18',handler:function(){
		                	win.checkQuoteInfo.setValue(this.checked);
		                }},
		                {boxLabel: '材料种类规格管理', name: 'r19',handler:function(){
		                	win.stuffSpeciesSpecificationManage.setValue(this.checked);
		                }},
		                {boxLabel: '原材料价格调节', name: 'r20',handler:function(){
		                	win.adjustMaterialPrice.setValue(this.checked);
		                }}
		            ]
				},{
					ref:'../group6',
					items:[
						{boxLabel: '系统日志查看', name: 'r21',handler:function(){
		                	win.showSystemLog.setValue(this.checked);
		                }},
		                {boxLabel: '其他报价管理', name: 'r22',handler:function(){
		                	win.showOtherPrice.setValue(this.checked);
		                }}
		            ]
				},
					{xtype : 'hidden',name : 'role.rid',ref:'../rid'},
					{xtype : 'hidden',name : 'role.r01',ref:'../userManage'},
					{xtype : 'hidden',name : 'role.r02',ref:'../addQuoteInfo'},
					{xtype : 'hidden',name : 'role.r03',ref:'../editQuoteInfo'},
					{xtype : 'hidden',name : 'role.r04',ref:'../copyQuoteInfo'},
					{xtype : 'hidden',name : 'role.r05',ref:'../deleteQuoteInfo'},
					{xtype : 'hidden',name : 'role.r06',ref:'../showTotalPrice'},
					{xtype : 'hidden',name : 'role.r07',ref:'../showMaterialsPrice'},
					{xtype : 'hidden',name : 'role.r08',ref:'../showAidsPrice'},
					{xtype : 'hidden',name : 'role.r09',ref:'../changePassword'},
					{xtype : 'hidden',name : 'role.r10',ref:'../showRefFiles'},
					{xtype : 'hidden',name : 'role.r11',ref:'../materialsManage'},
					{xtype : 'hidden',name : 'role.r12',ref:'../processInfoManage'},
					{xtype : 'hidden',name : 'role.r13',ref:'../foundryManage'},
					{xtype : 'hidden',name : 'role.r14',ref:'../aidsManage'},
					{xtype : 'hidden',name : 'role.r15',ref:'../referenceInfoManage'},
					{xtype : 'hidden',name : 'role.r16',ref:'../refFilesManage'},
					{xtype : 'hidden',name : 'role.r17',ref:'../customerManage'},
					{xtype : 'hidden',name : 'role.r18',ref:'../checkQuoteInfo'},
					{xtype : 'hidden',name : 'role.r19',ref:'../stuffSpeciesSpecificationManage'},
					{xtype : 'hidden',name : 'role.r20',ref:'../adjustMaterialPrice'},
					{xtype : 'hidden',name : 'role.r21',ref:'../showSystemLog'},
					{xtype : 'hidden',name : 'role.r22',ref:'../showOtherPrice'}
				],
				listeners : {
					render : function(){
						Ext.Ajax.request({
							url: 'findRoleById.action',
						    params: {'role.rid': record.data.rid},
						    success: function(response) {
						    	var obj = Ext.decode(response.responseText);
						    	if(obj.success){
						    		var role = obj.infos.role;
						    		win.group1.items.get(0).setValue(role.r01);
						    		win.group1.items.get(1).setValue(role.r02);
						    		win.group1.items.get(2).setValue(role.r03);
						    		win.group1.items.get(3).setValue(role.r04);
						    		win.group2.items.get(0).setValue(role.r05);
						    		win.group2.items.get(1).setValue(role.r06);
						    		win.group2.items.get(2).setValue(role.r07);
						    		win.group2.items.get(3).setValue(role.r08);
						    		win.group3.items.get(0).setValue(role.r09);
						    		win.group3.items.get(1).setValue(role.r10);
						    		win.group3.items.get(2).setValue(role.r11);
						    		win.group3.items.get(3).setValue(role.r12);
						    		win.group4.items.get(0).setValue(role.r13);
						    		win.group4.items.get(1).setValue(role.r14);
						    		win.group4.items.get(2).setValue(role.r15);
						    		win.group4.items.get(3).setValue(role.r16);
						    		win.group5.items.get(0).setValue(role.r17);
						    		win.group5.items.get(1).setValue(role.r18);
						    		win.group5.items.get(2).setValue(role.r19);
						    		win.group5.items.get(3).setValue(role.r20);
						    		win.group6.items.get(0).setValue(role.r21);
						    		win.group6.items.get(1).setValue(role.r22);
						    		win.rid.setValue(role.rid);
						    	}
						    },
						    failure: function(response) {
						    	Ext.Msg.alert('错误','server-side failure with status code ' + response.status);
						    },
						    scope:this
						});
					}
				}
			}],
			fbar:[{
				text : '提交',
				handler : function(){
					win.get(0).getForm().submit({
						waitTitle : '请稍候',
						waitMsg : '正在提交表单数据,请稍候...',
						url: 'updateRole.action',
					    success: function(form, action) {
					    	if(action.result.success){
					    		Ext.Msg.alert('操作提示','修改成功');
					    		win.close();
					    	}
					    },
					    failure: function(form, action) {
					       	if (action.result) {
					        	Ext.Msg.show({
								   title:'错误信息',
								   msg:'发生错误了!',
								   value: action.result.infos.msg,
								   width:500,
								   buttons: Ext.Msg.OK,
								   multiline: true,
								   icon: Ext.MessageBox.ERROR
								});
					       }
					    }
					});
				}
			},{
				text : '关闭',
				handler : function(){
					win.close();
				}
			}]
		});
		
		win.show();
	}

	
	/**
	 * 初始化左边树布局
	 */
	function initWestPanel(){
		var west = new Ext.Panel({
			title: ' ',
			region: 'west',
			margins: '5 0 5 5', 	 
			split: true,			 
			titleCollapse: true,	 
			collapsible: true,		 
			animCollapse:false,
			collapseMode: 'mini',	 
			width: 200,
			minSize: 200,
			maxSize: 350,
			layout: 'border',
			items: [{
				xtype: 'treepanel',
				region: 'center',
				border: false,
				autoScroll: true,
				loader: new Ext.tree.TreeLoader({
					preloadChildren: true,
					clearOnLoad: false
				}),
				root:{
					nodeType: 'async',
					id: 'root',
					text: '报时系统',
					expanded: true,
					children: Ext.gvp.tree.data.manager
				},
				tbar: ['当前用户:' + Ext.CURRENT_USER],
				listeners: {
					'click': function(node, e) {
						var ctop = this.ownerCt.previousSibling().items.itemAt(0);
						if (node.isLeaf()) {
							var tab = ctop.getComponent(node.id + '_tab');
							if (tab == null) {
								var grid;
								
								//报时表
								if(node.id == 'gvp_quote'){
									grid = initQuoteInfoGrid();
								}
								
								//报时表审核
								if(node.id == 'gvp_check'){
									grid = initQuoteInfoCheckGrid();
								}
								
								//用户管理
								if(node.id == 'gvp_user'){
									grid = initUserGrid();
								}
								
								//客户管理
								if(node.id == 'gvp_customer'){
									grid = initCustomerManageGrid();
								}
								
								//材质管理
								if(node.id == 'gvp_stuff'){
									grid = initStuffGrid();
								}
								
								//种类管理
								if(node.id == 'gvp_species'){
									grid = initSpeciesGrid();
								}
								
								//规格管理
								if(node.id == 'gvp_specification'){
									grid = initSpecificationGrid();
								}
								
								//原材料价格调整
								if(node.id == 'gvp_price_list'){
									grid = initPriceListGrid();
								}
								
								//查看系统日志
								if(node.id == 'gvp_system_log'){
									grid = initSystemLogGrid();	
								}
								
								//修改密码
								if(node.id == 'gvp_password'){
									showChangePasswordWindow();
									return ;
								}
								
								
								tab = ctop.add({
									id: node.id + '_tab',
									title: node.text,
									autoScroll: true,
									iconCls: 'tab-preview-hide',
									closable: true,
									layout: 'border',
									hideBorders : true,
									listeners: {
										'activate': function(p) {
											node.getOwnerTree().selectPath(node.getPath());
										},
										'bodyresize' : function(p){
											p.doLayout();
										}
									},
									items: [grid]
								});
							}
							try {
								ctop.setActiveTab(tab);
							} catch (e) {
							}
						}
					}
				}
			}],
			bbar: [{
				iconCls: 'icon-exit',
				text: '退出系统',
				handler: function() {
					Ext.Msg.confirm('操作提示', '<nobr>您确定要退出本系统?</nobr>', function(btn) {
						if ('yes' == btn) {
							Ext.Ajax.request({
								url: 'logout.action',	//退出登陆
								success: function() {
									location = '../index.html';
								},
								failure: function() {
									location = '../index.html';
								}
							});
						}
					});
				}
			}]
		});
		
		return west;
	}
	
	 /**
	 * 初始化主体界面
	 */
	function initCenterPanel(){
		var center = new Ext.Panel({
			region: 'center',
			margins: '5 5 5 0',
			layout: 'border',
			border: false,
			items: [{
				xtype: 'tabpanel',
				region: 'center',
				activeTab: 0,
				items: [{
					title: '欢迎使用',
					bodyCssClass:'bg'
				}]
			}]
		});
		
		return center;
	}
	
	/**
	 * 修改密码
	 */
	function showChangePasswordWindow(){
		var id = Ext.id();
		var win = new Ext.Window({
			title : '密码修改',
			width:350,
			height:170,
			resizable:false,
			modal:true, 
			items:[{
				xtype:'form',
				baseCls:'x-plain',
				bodyStyle:'padding:10px',
				defaults:{xtype:'textfield',anchor:'93%'},
				items:[{
					fieldLabel:'原始密码',
					inputType:'password',
					name:'page.params.pwd_old'
				},{
					fieldLabel:'新密码',
					inputType:'password',
					name:'page.params.pwd1',
					id:id
				},{
					fieldLabel:'重复新密码',
					inputType:'password',
					name:'page.params.pwd2',
					vtype:'password',
	                vtypeText:'两次密码不一致',
	                confirmTo:id
				}]
			}],
			fbar:[{
				text:'修改'
				,handler:function(){
					win.get(0).getForm().submit({
						waitTitle : '请稍候',
						waitMsg : '正在提交表单数据,请稍候...',
						url:'changePassword.action',
						success: function(form, action) {
					    	if(action.result.success){
					    		Ext.Msg.alert('操作提示','修改成功');
					    		win.close();
					    	}
					    },
					    failure: function(form, action) {
					       	if (action.result) {
					        	Ext.Msg.show({
								   title:'错误信息',
								   msg:'发生错误了!',
								   value: action.result.infos.msg,
								   width:500,
								   buttons: Ext.Msg.OK,
								   multiline: true,
								   icon: Ext.MessageBox.ERROR
								});
					       }
					    }
					});
				}
			},{
				text:'关闭',
				handler:function(){
					win.close();
				}
			}]
		});
		
		win.show();
	}

	
	var view = new Ext.Viewport({
		layout: 'border',
		id : 'viewport',
		flag : true,
		items: [{
			xtype: 'box',
			region: 'north',
			applyTo: 'header',
			height: 53
		}, initCenterPanel(), initWestPanel()]
	});

	
	/**
	 * 批量删除记录
	 * @param url 请求连接
	 * @param idsName ids请求参数名 	Example: page.params.iaids
	 * @param id 记录的id 			Example:record.data.iaid
	 * @param grid 网格
	 * @param store 数据源
	 * @param msg 删除的提示信息
	 * @param qid 
	 */
	function deleteRecords(url,idsName,id,grid,store,msg,qid,quoteInfoRecord){
		var o = {};
		Ext.Msg.confirm('删除提示',msg || '确定删除选中的记录吗?',function(btn){
			if(btn == 'yes'){
				var ids = new Array();
				var records = grid.getSelectionModel().getSelections();
				Ext.each(records,function(record){
					ids.push(record.data[id]);
				});
				o[idsName] = ids;
				if(qid){
					o['page.params.qid'] = qid;	
				}
				Ext.Ajax.request({
					url:url,
					params:o,
					success:function(response){
						var obj = Ext.decode(response.responseText);
						if(obj.success){
							if(store){
								Ext.each(records,function(record){
									store.remove(record);
									store.totalLength -= 1;
									var bbar = grid.getBottomToolbar();
									if(bbar){
										bbar.updateInfo();	
									}
								});
							}
						}else{
							Ext.Msg.show({
							   title:'错误信息',
							   msg:'发生错误了!',
							   value: obj.infos.msg,
							   width:500,
							   buttons: Ext.Msg.OK,
							   multiline: true,
							   icon: Ext.MessageBox.ERROR
							});
						}
					}
				});
			}
		});
	}
	
	/**
     * 获得行编辑
	 */
    function getEditor(id,index,grid){
    	var columns = grid.getColumnModel().getColumnsBy(function(c){
		  	return c.dataIndex == id;
		});
		return columns[0].getCellEditor(index).field;
    }
	
    
    /**
	 * 初始化行编辑器
	 */
	function initEditor(saveText,cancelText,clicksToEdit){
		var editor = new Ext.ux.grid.RowEditor({
	        saveText: saveText || '保存',
	        cancelText: cancelText || '取消',
	        errorSummary: false,
	        clicksToEdit:clicksToEdit ? 'auto' : clicksToEdit
	    });
	    return editor;
	}

	/**
	 * 返回查询组件 重用
	 * @param store 数据源
	 * @param width 组件宽度
	 */
	function initSearchField(store,width,paramNames){
		var field = new Ext.form.TwinTriggerField({
			validationEvent: false,
			validateOnBlur: false,
			trigger1Class: 'x-form-clear-trigger',
			trigger2Class: 'x-form-search-trigger',
			hideTrigger1: true,
			width: width || 180,
			hasSearch: false,
			listeners: {
				'specialkey': function(f, e) {
					if (e.getKey() == e.ENTER) {this.onTrigger2Click();}
					if (e.getKey() == e.ESC) {
						this.hasSearch = true;
						this.onTrigger1Click();
					}
				}
			},
			onTrigger1Click: function() {
				if (this.hasSearch) {
					this.el.dom.value = '';
					this.triggers[0].hide();
					this.hasSearch = false;
					this.focus();
				}
			},
			onTrigger2Click: function() {
				var v = this.getRawValue();
				v = v == '' ? null:v;
				for(var i = 0; i< paramNames.length; i++){
					store.setBaseParam(paramNames[i],v);
				}
				store.setBaseParam('page.params.condition',v);
				store.reload({params: {'page.start': 0}});
				this.hasSearch = true;
				this.triggers[0].show();
				this.focus();
			}
		});
		
		return field;
	}
	
	
	/**
	 * 初始化
	 */
	function pageInit(id,pid,up,down,left,right,_in,out,z){
		var image = Ext.get(pid);
	
		Ext.get(pid).on({
			'dblclick':{fn:function(){
				zoom(image,true,1.2);
			}
		}});
		new Ext.dd.DD(image, 'pic');
		
	
		Ext.get(up).on('click',function(){imageMove('up',image);}); 		//向上移动
		Ext.get(down).on('click',function(){imageMove('down',image);});	//向下移动
		Ext.get(left).on('click',function(){imageMove('left',image);});	//左移
		Ext.get(right).on('click',function(){imageMove('right',image);});	//右移动
		Ext.get(_in).on('click',function(){zoom(image,true,1.5);});		//放大
		Ext.get(out).on('click',function(){zoom(image,false,1.5);});		//缩小
		Ext.get(z).on('click',function(){rotate(pid)});			//旋转
	};
	
	function rotate(pid){
		$('#' + pid).rotateRight(90);	
		var image = Ext.get(pid);
	
		Ext.get(pid).on({
			'dblclick':{fn:function(){
				zoom(image,true,1.2);
			}
		}});
		new Ext.dd.DD(image, 'pic');
	}
	
	/**
	 * 图片移动
	 */
	function imageMove(direction, el) {
		el.move(direction, 50, true);
	}
	
	
	/**
	 * 
	 * @param el 图片对象
	 * @param type true放大,false缩小
	 * @param offset 量
	 */
	function zoom(el,type,offset){
		var width = el.getWidth();
		var height = el.getHeight();
		var nwidth = type ? (width * offset) : (width / offset);
		var nheight = type ? (height * offset) : (height / offset);
		var left = type ? -((nwidth - width) / 2):((width - nwidth) / 2);
		var top =  type ? -((nheight - height) / 2):((height - nheight) / 2); 
		el.animate(
			{
		        height: {to: nheight, from: height},
		        width: {to: nwidth, from: width},
		        left: {by:left},
		        top: {by:top}
	        },
	        null,      
		    null    
		);
	}


	Ext.Ajax.on('requestcomplete',function(conn,response,options){
		var obj = Ext.decode(response.responseText);
		try{
			if(obj.infos != null){
				if(obj.infos.tip != null){
					Ext.Msg.show({								
						title : '错误',
						msg : obj.infos.tip,
						buttons : Ext.Msg.OK,
						icon : Ext.Msg.ERROR,
						fn:function(){
							location = "../index.html";
						}
					});
				}
			}
		}catch(e){}
	});
});



Ext.apply(Ext.form.VTypes,{
    password:function(val,field){ 
      if(field.confirmTo){ 
          var pwd=Ext.get(field.confirmTo); 
          return (val==pwd.getValue());
      }
      return true;
    }
});


//自定义组件命名空间
Ext.ns('Ext.ux.tree');

//=========================TreeLoader扩展,支持josn-plugin返回的json对象中包含的数组值============//
Ext.ux.tree.JsonPluginTreeLoader = function (config) {
	this.rootName = 'root';
    Ext.ux.tree.JsonPluginTreeLoader.superclass.constructor.call(this, config);
}

Ext.extend(Ext.ux.tree.JsonPluginTreeLoader, Ext.tree.TreeLoader, {
    processResponse: function (response, node, callback,scope) {
    	var json = response.responseText;
		try {
		    var o = response.responseData || Ext.decode(json);
		   	if(Ext.type(o) == 'object'){
		   		o = o[this.rootName || 'root'];
		   	}
		    node.beginUpdate();
		    if(o){
			    for(var i = 0, len = o.length; i < len; i++){
			        var n = this.createNode(o[i]);
			        if(n){
			            node.appendChild(n);
			        }
			    }
			}
		    node.endUpdate();
		    this.runCallback(callback, scope || node, [node]);
		}catch(e){
		    this.handleFailure(response);
		}
    }
});


//TreeLoader end


//=========================ComboRenderer============//
Ext.ns("Ext.ux.renderer");

Ext.ux.renderer.ComboRenderer = function(options) {
    var value = options.value;
    var combo = options.combo;

    var returnValue = value;
    var valueField = combo.valueField;
        
    var idx = combo.store.findBy(function(record) {
        if(record.get(valueField) == value) {
            returnValue = record.get(combo.displayField);
            return true;
        }
    });
    
    // This is our application specific and might need to be removed for your apps
    // 这是我们应用中所特定的可能需要从你的应用中移除
    if(idx < 0 && value == 0) {
        returnValue = '';
    }
    
    return returnValue;
};

Ext.ux.renderer.Combo = function(combo) {
    return function(value, meta, record) {
        return Ext.ux.renderer.ComboRenderer({value: value, meta: meta, record: record, combo: combo});
    };
}
