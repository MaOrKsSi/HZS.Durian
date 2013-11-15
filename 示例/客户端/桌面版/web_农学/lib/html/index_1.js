d主程序 = {
    指令0: "主程序",
    d主界面: null,
    d界面: null,
    d导航栏: null,
    d工作区: null,
    i当前指令_i: null,
    i通用數據集: null
};

d主程序.d登入 = {
    指令0: "登入",
    d界面: null,
    //
    jg登入: null,
    jg斠验: null
};

Ext.define('h.view.main', {
    extend: 'Ext.container.Viewport',
    requires: [
        'Ext.panel.Panel',
        'Ext.form.Label',
        'Ext.Img',
        'Ext.button.Button',
        'Ext.toolbar.Toolbar',
        'Ext.form.field.Display'
    ],
    layout: 'fit',
    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'panel',
                    id: 'main',
                    width: 150,
                    layout: 'border',
                    bodyStyle: 'border:0px;',
                    items: [
                        {
                            xtype: 'label',
                            region: 'north',
                            hidden: true,
                            id: 'main.install_text'
                        },
                        {
                            xtype: 'container',
                            region: 'north',
                            height: 40,
                            layout: {
                                type: 'hbox',
                                align: 'stretch'
                            },
                            items: [
                                {
                                    xtype: 'image',
                                    flex: 1,
                                    src: './desktop/themes/default/images/head--zz.jpg'
                                },
                                {
                                    xtype: 'button',
                                    id: 'd',
                                    ui: '1',
                                    width: 40,
                                    text: '<img width=100% height=100% src="./desktop/themes/default/images/help.png"/>'
                                },
                                {
                                    xtype: 'button'
                                }
                            ]
                        },
                        {
                            xtype: 'panel',
                            region: 'west',
                            split: true,
                            id: 'main.dd3hg2',
                            width: 200,
                            layout: 'accordion',
                            collapsible: true,
                            title: '导航菜单'
                        },
                        {
                            xtype: 'container',
                            region: 'center',
                            id: 'main.working',
                            layout: 'fit'
                        },
                        {
                            xtype: 'toolbar',
                            region: 'south',
                            style: 'border:0px;',
                            items: [
                                {
                                    xtype: 'displayfield',
                                    id: 'main_cd1zo4yc2',
                                    width: 120,
                                    fieldLabel: '操作员',
                                    labelWidth: 46
                                },
                                {
                                    xtype: 'displayfield',
                                    id: 'main_ji1gp4',
                                    width: 280,
                                    fieldLabel: '公司',
                                    labelWidth: 46
                                },
                                {
                                    xtype: 'displayfield',
                                    id: 'main_ho4dj3',
                                    width: 280,
                                    fieldLabel: '部门',
                                    labelWidth: 46
                                }
                            ]
                        }
                    ]
                }
            ]
        });

        me.callParent(arguments);
    }

});

Ext.define('h.view.main.login', {
    extend: 'Ext.window.Window',
    requires: [
        'Ext.toolbar.Toolbar',
        'Ext.form.Label',
        'Ext.button.Button',
        'Ext.form.field.Text'
    ],
    height: 253,
    width: 544,
    bodyPadding: 8,
    closable: false,
    title: '登入',
    modal: true,
    layout: {
        type: 'vbox',
        align: 'stretch'
    },
    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            dockedItems: [
                {
                    xtype: 'toolbar',
                    dock: 'bottom',
                    items: [
                        {
                            xtype: 'label',
                            flex: 1,
                            id: 'main.login_ti2ii4'
                        },
                        {
                            xtype: 'button',
                            handler: function(button, event) {
                                d主程序.d登入.jg登入(me);
                            },
                            disabled: true,
                            id: 'main.login_dt1ru4',
                            width: 100,
                            text: '登入'
                        }
                    ]
                }
            ],
            items: [
                {
                    xtype: 'container',
                    flex: 1,
                    margins: '0 0 8 0',
                    layout: {
                        type: 'hbox',
                        align: 'stretch'
                    },
                    items: [
                        {
                            xtype: 'label',
                            margins: '0 5 0 0',
                            id: 'main.login_tu2xq2ma3',
                            width: 250
                        },
                        {
                            xtype: 'container',
                            flex: 1,
                            layout: {
                                type: 'vbox',
                                align: 'stretch'
                            },
                            items: [
                                {
                                    xtype: 'textfield',
                                    id: 'main.login_gy1hd4',
                                    fieldLabel: '<FONT COLOR=blue>手机</FONT><FONT COLOR=red>*</FONT>',
                                    labelWidth: 52,
                                    allowBlank: false,
                                    enableKeyEvents: true,
                                    regex: /^((13)|(15)|(18))\d{9}$/,
                                    regexText: '请录入正确手机号！',
                                    selectOnFocus: true,
                                    listeners: {
                                        keypress: {
                                            fn: me.onlogin_gy1hd41Keypress,
                                            scope: me
                                        }
                                    }
                                },
                                {
                                    xtype: 'textfield',
                                    id: 'main.login_kp3lq4',
                                    fieldLabel: '<FONT COLOR=blue>口令</FONT><FONT COLOR=red>*</FONT>',
                                    labelWidth: 52,
                                    inputType: 'password',
                                    allowBlank: false,
                                    enableKeyEvents: true,
                                    selectOnFocus: true,
                                    listeners: {
                                        keypress: {
                                            fn: me.onlogin_gy1hd41Keypress1,
                                            scope: me
                                        }
                                    }
                                },
                                {
                                    xtype: 'container',
                                    margins: '0 0 5 0',
                                    layout: {
                                        type: 'hbox',
                                        align: 'stretch'
                                    },
                                    items: [
                                        {
                                            xtype: 'textfield',
                                            flex: 1,
                                            id: 'main.login_yf4vt4ma3',
                                            fieldLabel: '<FONT COLOR=blue>验证码</FONT><FONT COLOR=red>*</FONT>',
                                            labelWidth: 52,
                                            allowBlank: false,
                                            enableKeyEvents: true,
                                            selectOnFocus: true,
                                            listeners: {
                                                keypress: {
                                                    fn: me.onlogin_kp3lq4Keypress,
                                                    scope: me
                                                }
                                            }
                                        },
                                        {
                                            xtype: 'button',
                                            handler: function(button, event) {
                                                d主程序.d登入.jg握手();
                                            },
                                            id: 'main.login_yf4vt4',
                                            width: 60,
                                            text: '验证'
                                        }
                                    ]
                                }
                            ]
                        }
                    ]
                },
                {
                    xtype: 'textfield',
                    id: 'mail.login_ji1gp4',
                    fieldLabel: '公司',
                    labelWidth: 38,
                    readOnly: true
                },
                {
                    xtype: 'textfield',
                    margins: '6 0 6 0',
                    id: 'mail.login_ho4dj3',
                    fieldLabel: '部门',
                    labelWidth: 38,
                    readOnly: true
                },
                {
                    xtype: 'textfield',
                    id: 'main.login_xq4mq2',
                    fieldLabel: '姓名',
                    labelWidth: 38,
                    readOnly: true
                }
            ]
        });

        me.callParent(arguments);
    },
    onlogin_gy1hd41Keypress: function(textfield, e, eOpts) {
        if (e.getKey() === e.ENTER) {
            Ext.getCmp('main.login_kp3lq4').focus(true, true);
        }
    },
    onlogin_gy1hd41Keypress1: function(textfield, e, eOpts) {
        if (e.getKey() === e.ENTER) {
            Ext.getCmp('main.login_yf4vt4ma3').focus(true, true);
        }
    },
    onlogin_kp3lq4Keypress: function(textfield, e, eOpts) {
        if (e.getKey() === e.ENTER) {
            Ext.getCmp('main.login_yf4vt4').focus(true, true);
        }
    }

});

d主程序.g会晤服务器 = function(ci指令_JSON, ci参數_JSON) {
    var ji服务器返回值_JSON = null;
    if (Ext.isEmpty(ci参數_JSON)) {
        ji服务器返回值_JSON = Ext.JSON.decode(applet.g会晤服务器(Ext.JSON.encode(ci指令_JSON), "{}"));
    } else {
        ji服务器返回值_JSON = Ext.JSON.decode(applet.g会晤服务器(Ext.JSON.encode(ci指令_JSON), Ext.JSON.encode(ci参數_JSON)));
    }
    if (ji服务器返回值_JSON.success) {
        return ji服务器返回值_JSON._;
    }
    applet.g蜂鸣();
    Ext.MessageBox.alert('错误提示', ji服务器返回值_JSON._, function callBack(id) {
        applet.g退出();
    });
    return null;
};

d主程序.d登入.jg登入 = function() {
    d主程序.d登入.d界面.close();
    var ji = d主程序.g会晤服务器({
        指令0: "入团"
    }, {
        _: '登入'
    });//返回团证编号
    if (ji == null) {
        return;
    }
    //
    var ji = d主程序.g会晤服务器({
        指令1: "登入"
    });
    if (ji == null) {
        return;
    }
	Ext.MessageBox.alert('',ji);
};

d主程序.d登入.jg握手 = function() {
    Ext.getCmp('main.login_dt1ru4').enable();
};
////////////////////////////////////////////////////////////////////////////////////////////////////////////
//浏览器出错
window.onerror = function() {
    return true;
};