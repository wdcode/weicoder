# 初始化角色 
INSERT INTO role(name) VALUES('超级管理员');
INSERT INTO role(name) VALUES('管理员');

# 初始化超级管理员 
INSERT INTO admin(name,password,role_id,state,time) VALUES('admin','2d918c58516f16a52487',1,1,0);

# 初始化菜单 
INSERT INTO menu(name,menu_id) VALUES('系统管理',0);
INSERT INTO menu(name,menu_id) VALUES('权限管理',0);
INSERT INTO menu(name,menu_id) VALUES('菜单管理',0); 
INSERT INTO menu(name,menu_id) VALUES('日志管理',0);
INSERT INTO menu(name,menu_id) VALUES('用户管理',0); 
				
# 系统管理  
INSERT INTO menu(name,menu_id,url) VALUES('修改主题',1,'system_toThemes_theme');   

# 权限管理   
INSERT INTO menu(name,menu_id,url) VALUES('角色列表',2,'role_page');   
INSERT INTO menu(name,menu_id,url) VALUES('操作列表',2,'operate_page');

# 菜单管理  
INSERT INTO menu(name,menu_id,url) VALUES('菜单列表',3,'menu_page');
 
# 日志管理 
INSERT INTO menu(name,menu_id,url) VALUES('操作日志列表',4,'logsOperate_page');
INSERT INTO menu(name,menu_id,url) VALUES('登录日志列表',4,'logsLogin_page');

# 用户管理  
INSERT INTO menu(name,menu_id,url) VALUES('管理员列表',5,'admin_page');  
  
# 初始化操作 
# 系统    
INSERT INTO operate(name,link) VALUES('修改模板','system_temples'); 

# 权限
INSERT INTO operate(name,link) VALUES('添加角色','role_add');
INSERT INTO operate(name,link) VALUES('修改角色','role_edit');
INSERT INTO operate(name,link) VALUES('删除角色','role_dels'); 
INSERT INTO operate(name,link) VALUES('添加操作','operate_add');
INSERT INTO operate(name,link) VALUES('修改操作','operate_edit');
INSERT INTO operate(name,link) VALUES('删除操作','operate_dels');

# 菜单 
INSERT INTO operate(name,link) VALUES('添加菜单','menu_add');
INSERT INTO operate(name,link) VALUES('修改菜单','menu_edit');
INSERT INTO operate(name,link) VALUES('删除菜单','menu_dels');
 
# 日志 
INSERT INTO operate(name,link) VALUES('删除日志','logs_dels');
INSERT INTO operate(name,link) VALUES('删除登录日志','loginLogs_dels');
INSERT INTO operate(name,link) VALUES('删除页面日志','pageLogs_dels');
INSERT INTO operate(name,link) VALUES('清空日志','logs_trun');
INSERT INTO operate(name,link) VALUES('清空登录日志','loginLogs_trun');
INSERT INTO operate(name,link) VALUES('清空页面日志','pageLogs_trun');

# 用户 
INSERT INTO operate(name,link) VALUES('添加管理员','admin_add');
INSERT INTO operate(name,link) VALUES('修改管理员','admin_edit');
INSERT INTO operate(name,link) VALUES('删除管理员','admin_dels'); 