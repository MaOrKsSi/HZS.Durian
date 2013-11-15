Ext.onReady(function() {
   Ext.QuickTips.init();//初始化信息提示功能
   d主程序.d主界面 = Ext.create('h.view.main');
   d主程序.d界面 = Ext.getCmp('main');
   d主程序.d导航栏 = Ext.getCmp('main.dd3hg2');
   d主程序.d工作区 = Ext.getCmp('main.working');
   d主程序.d登入.d界面 = Ext.create('h.view.main.login');
   d主程序.d登入.d界面.show();
   Ext.getCmp('main.login_gy1hd4').focus(false, true);
});