package com.weicoder.frame.action;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
 
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.weicoder.frame.engine.LoginEngine;
import com.weicoder.frame.entity.Entity;
import com.weicoder.frame.entity.EntityIp;
import com.weicoder.frame.entity.EntityStartEndTime;
import com.weicoder.frame.entity.EntityTime;
import com.weicoder.frame.entity.EntityUserId; 
import com.weicoder.web.util.RequestUtil; 
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.log.Logs;
import com.weicoder.common.token.TokenBean; 
import com.weicoder.common.util.BeanUtil;
import com.weicoder.common.util.DateUtil;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.util.StringUtil;
import com.weicoder.common.bean.Pages;
import com.weicoder.json.JsonEngine;
import com.weicoder.dao.service.SuperService;

/**
 * 超级通用Action
 * @author WD
 * 
 * @version 1.0 2012-07-4
 */
public class SuperAction extends BasicAction {
	// 成功
	protected static final String	SUCCESS		= "success";
	// 错误
	protected static final String	ERROR		= "error";
	// 登录页
	protected static final String	LOGIN		= "login";
	// LIST
	protected final static String	LIST		= "list";
	// 时间字段
	protected final static String	TIME_FIELD	= "time";
	// 验证登录标识
	protected TokenBean				token;
	// 通用实体
	protected Entity				entity;
	// 实体列表
	protected List<Entity>			entitys;

	// 开始时间
	protected String				startDate;
	// 结束时间
	protected String				endDate;
	// 实体类
	protected Class<Entity>			entityClass;
	// 分页Bean 
	protected Pages			pager;
	// 排序参数
	protected Map<String, Object>	orders;
	// 实体是否初始化
	protected boolean				isEntity;

	// 主键
	protected Serializable			key;
	// 主键数组
	protected Serializable[]		keys;

	// HttpServletRequest
	protected HttpServletRequest	request;
	// HttpServletResponse
	protected HttpServletResponse	response;

	// 错误信息
	protected List<String>			error		= Lists.newList();
	// 错误信息
	protected List<String>			message		= Lists.newList();

	// 模板名
	protected String				module;
	// 方法名
	protected String				method;
	// 返回模式名
	protected String				mode;

	/**
	 * 初始化Action
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param actionName action名称
	 */
	protected void init(HttpServletRequest request, HttpServletResponse response, String actionName) {
		// 父类初始化
		try {
			// 获得request与response
			this.request = request;
			this.response = response;
			// 分解提交action
			String[] action = StringUtil.split(actionName, StringConstants.UNDERLINE);
			// 获得模板名
			module = action[0];
			// 方法名
			method = action.length > 1 ? action[1] : action[0];
			// 获得方法名
			mode = EmptyUtil.isEmpty(mode) ? action.length > 2 ? action[2] : action.length == 2 ? action[1] : action[0]
					: mode;
			// 如果mode为空
			if (EmptyUtil.isEmpty(mode)) {
				mode = "call";
			}
			// 初始化空排序
			orders = Maps.newMap();
			// 获得实体类
//			entityClass = context.getClass(module);
			// 获得ContentType
			String contentType = request.getContentType();
			// 判断为上传文件表单
			if (!EmptyUtil.isEmpty(contentType) && contentType.indexOf("multipart/form-data") > -1) {
				isEntity = true;
				// 获得实体
//				entity = entityClass == null ? null : context.getBean(module, entityClass);
			} else {
				// 是否初始化实体
				for (Map.Entry<String, String[]> e : request.getParameterMap().entrySet()) {
					if (e.getKey().indexOf("entity") > -1) {
						isEntity = true;
						// 获得实体
//						entity = entityClass == null ? null : context.getBean(module, entityClass);
						break;
					}
				}
			}
			// 如果查询自己的数据 添加登录用户名
			if (entity == null && entityClass != null && EntityUserId.class.isAssignableFrom(entityClass)) {
//				entity = context.getBean(module, entityClass);
			}
			if (entity instanceof EntityUserId) {
				((EntityUserId) entity).setUserId(token.getId());
			}
		} catch (Exception e) {
			Logs.error(e);
		}
	}

	/**
	 * 重置缓存
	 * @return 跳转
	 */
	public String load() {
		// 重载数据
		if (entityClass == null) {
//			SuperService.DAO.load();
		} else {
//			SuperService.DAO.load(entityClass);
		}
		// 返回到成功页
		return callback(SUCCESS);
	}

	/**
	 * 重置缓存
	 * @return 跳转
	 */
	public String cache() {
		// 重载缓存
		if (entityClass == null) {
//			SuperService.DAO.cache();
		} else {
//			SuperService.DAO.load(entityClass);
		}
		// 返回到成功页
		return callback(SUCCESS);
	}

	/**
	 * 添加
	 * @return 跳转
	 */
	public String add() {
		Logs.info("add entity={}", entity);
		return callback(entity = SuperService.DAO.insert(add(entity)));
	}

	/**
	 * 添加或修改
	 * @return 跳转
	 */
	public String addOrUpdata() {
		Logs.info("addOrUpdata entity={}", entity);
		return callback(entity = SuperService.DAO.insertOrUpdate(add(entity)));
	}

	/**
	 * 添加
	 * @return 跳转
	 */
	public String adds() {
		Logs.info("adds entity={};key={}", entitys, key);
		// 如果实体列表为空 并且key不为空
		if (EmptyUtil.isEmpty(entitys) && !EmptyUtil.isEmpty(key)) {
			entitys = JsonEngine.toList(Conversion.toString(key), entityClass);
		}
		// 循环实体数组
		for (Entity e : entitys) {
			add(e);
		}
		// 添加并返回结果
		return callback(SuperService.DAO.insert(entitys));
	}

	/**
	 * 修改
	 * @return 跳转
	 */
	public String edit() {
		Logs.info("edit entity={}", entity);
		// 获得要更像的实体
		Entity e = SuperService.DAO.get(entityClass, entity.getKey());
		// 实体不为空 更新 否则返回错误
		return callback(entity = SuperService.DAO.update(BeanUtil.copy(upload(request, entity), e)));
	}

	/**
	 * 修改
	 * @return 跳转
	 */
	public String edits() {
		Logs.info("edits entity={};key={}", entitys, key);
		// 如果实体列表为空 并且key不为空
		if (EmptyUtil.isEmpty(entitys) && !EmptyUtil.isEmpty(key)) {
			entitys = JsonEngine.toList(Conversion.toString(key), entityClass);
		}
		// 实体列表不为空
		if (!EmptyUtil.isEmpty(entitys)) {
			// 获得列表长度
			int size = entitys.size();
			// 声明修改实体数组
			List<Entity> es = Lists.newList(size);
			// 循环获取持久化数据实体
			for (int i = 0; i < size; i++) {
				// 获得修改实体
				Entity e = entitys.get(i);
				// 把新修改的值赋值给修改是实体
				es.add(BeanUtil.copy(e, SuperService.DAO.get(entityClass, e.getKey())));
			}
			// 修改实体
			entitys = SuperService.DAO.update(es);
		}
		// 实体不为空 更新 否则返回错误
		return callback(entitys);
	}

	/**
	 * 删除
	 * @return 跳转
	 */
	public String del() {
		Logs.info("del entity=" + entity + ";key=" + key);
		// key为空
		if (EmptyUtil.isEmpty(key)) {
			// 实体不为空
			if (entity != null) {
				// 实体主键为空
				if (EmptyUtil.isEmpty(entity.getKey())) {
					// 按实体查询出相关列表 在删除
//					entitys = SuperService.DAO.delete(entity);
				} else {
					// 按实体主键删除
//					entitys = SuperService.DAO.delete(entityClass, entity.getKey());
				}
			}
		} else {
			// 按key删除
//			entitys = SuperService.DAO.delete(entityClass, key);
		}
		return callback(EmptyUtil.isEmpty(entitys) ? ERROR : (entity = entitys.get(0)));
	}

	/**
	 * 删除多个
	 * @return 跳转
	 */
	public String dels() {
		Logs.info("dels entity=" + entitys);
		return "";
//		return callback(EmptyUtil.isEmpty(entitys = SuperService.DAO.delete(entityClass, keys)) ? ERROR : SUCCESS);
		// return callback(EmptyUtil.isEmpty(entitys = SuperService.DAO.delete(entityClass, keys)) ? ERROR :
		// mode);
	}

	/**
	 * 查询所有
	 * @return 跳转list
	 */
	public String all() {
		return callback(entitys = SuperService.all(entityClass));
	}

	/**
	 * 分页查询
	 * @return 跳转list
	 */
	public String page() {
		Logs.info("page entity=" + entity + ";pager=" + pager);
		// 查询实体列表
//		entitys = (entity == null ? SuperService.list(entityClass, pager) : SuperService.list(entity, pager));
		// 声明返回列表
		Map<String, Object> map = Maps.newMap();
		map.put("pager", pager);
		map.put("entitys", entitys);
		// 返回列表
		return callback(map);
	}

	/**
	 * 分页查询
	 * @return 跳转list
	 */
	public String order() {
		Logs.info("page entity=" + entity + ";pager=" + pager);
		return callback(entity == null ? SuperService.order(entityClass, orders, pager).getList()
				: SuperService.order(entity, orders, pager)); 
	}

	/**
	 * 分页查询
	 * @return 跳转list
	 */
	public String date() {
		Logs.info("page entity=" + entity + ";pager=" + pager + ";start=" + startDate + ";end=" + endDate);
		// 如果开始时间和结束时间都为空
		if (EmptyUtil.isEmpty(startDate) && EmptyUtil.isEmpty(endDate)) {
			// 直接分页查询
//			entitys = SuperService.list(entity, pager);
		} else {
			// 判断开始时间为空 为当前时间
			if (EmptyUtil.isEmpty(startDate)) {
				startDate = DateUtil.getShortDate();
			}
			// 判断结束时间为空 为当前时间
			if (EmptyUtil.isEmpty(endDate)) {
				endDate = DateUtil.getShortDate();
			}
			// 按时间查询
//			entitys = SuperService.DAO.between(entity, TIME_FIELD, DateUtil.getTime(startDate),
//					DateUtil.getTime(endDate) + DateConstants.DAY, pager);
		}
		// 返回列表页
		return callback(entitys);
	}

	/**
	 * 实体条件查询出所有
	 * @return 跳转SUCCESS
	 */
	public String get() {
		Logs.info("get key=" + key);
		return callback(entity = key == null ? null : SuperService.DAO.get(entityClass, key));
	}

	/**
	 * 实体条件查询出所有
	 * @return 跳转SUCCESS
	 */
	public String gets() {
		Logs.info("get keys=" + keys);
		return keys == null ? callback(entitys = SuperService.DAO.gets(entityClass, key))
				: callback(entitys = SuperService.DAO.gets(entityClass, keys));
	}

	/**
	 * 实体条件查询出所有
	 * @return 跳转SUCCESS
	 */
	public String entity() {
		Logs.info("entity=" + entity);
		return callback(entity = entity == null ? null : SuperService.DAO.get(entity));
	}

	/**
	 * 实体条件查询出所有
	 * @return 跳转list
	 */
	public String entitys() {
		Logs.info("entitys entity=" + entity + ";pager=" + pager);
//		return callback(entity == null ? LIST : (entitys = SuperService.list(entity, pager)));
		return "";
	}

	/**
	 * 跳转到修改页
	 * @return 跳转
	 */
	public String theme() {
		Logs.info("theme entity=" + entity);
		return callback(!EmptyUtil.isEmpty(entity = SuperService.DAO.get(entityClass, entity.getKey())));
	}

	/**
	 * 跳转到列表页
	 * @return 跳转
	 */
	public String list() {
		Logs.info("list entity=" + entity + ";pager=" + pager + ";orders=" + orders);
//		// 排序参数为空
//		if (EmptyUtil.isEmpty(orders)) {
//			entitys = entity == null ? SuperService.list(entityClass, pager) : SuperService.list(entity, pager);
//		} else {
//			entitys = entity == null ? SuperService.order(entityClass, orders, pager) : SuperService.order(entity, orders, pager);
//		}
		// 返回结果
		return callback(entitys);
	}

	/**
	 * 获得数量
	 * @return 跳转
	 */
	public String count() {
		Logs.info("count entity=" + entity);
		return callback(entity == null ? SuperService.DAO.count(entityClass) : SuperService.DAO.count(entity));
	}

	/**
	 * 跳转到列表页
	 * @return 跳转
	 */
	public String search() {
		Logs.info("theme search=" + entity + ";pager=" + pager);
//		return callback(entitys = SuperService.search(entity, pager));
		return "";
	}

	// /**
	// * 获得Action方法名 只保留x_x
	// * @return Action方法名
	// */
	// public String getLink() {
	// // 获得提交Action地址
	// String actionName = getActionName();
	// // 分解名称
	// String[] name = StringUtil.split(actionName, StringConstants.UNDERLINE);
	// // 返回链接名
	// return name.length > 2 ? name[0] + StringConstants.UNDERLINE + name[1] : actionName;
	// }

	/**
	 * 直接跳转
	 * @return 跳转
	 */
	public String to() {
		return SUCCESS;
	}

	/**
	 * 直接跳转
	 * @return 跳转
	 */
	public String tos() {
		return LIST;
	}

	/**
	 * 获得提交IP
	 * @return 提交IP
	 */
	public String getIp() {
		return RequestUtil.getIp(request);
	}

	/**
	 * 获得通用实体
	 * @return 通用实体
	 */
	public Entity getEntity() {
		return entity;
	}

	/**
	 * 设置通用实体
	 * @param entity 通用实体
	 */
	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	/**
	 * 获得通用实体列表
	 * @return 通用实体列表
	 */
	public List<Entity> getEntitys() {
		return entitys;
	}

	/**
	 * 设置通用实体列表
	 * @param entitys 通用实体列表
	 */
	public void setEntitys(List<Entity> entitys) {
		this.entitys = entitys;
	}

	/**
	 * 获得开始时间
	 * @return 开始时间
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * 设置开始时间
	 * @param startDate 开始时间
	 */
	public void setStartDate(String startDate) {
		this.startDate = StringUtil.trim(startDate);
	}

	/**
	 * 获得结束时间
	 * @return 结束时间
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * 设置结束时间
	 * @param endDate 结束时间
	 */
	public void setEndDate(String endDate) {
		this.endDate = StringUtil.trim(endDate);
	}

	/**
	 * 获得分页Bean
	 * @return 分页Bean
	 */
	public Pages getPager() {
		return pager;
	}

	/**
	 * 设置分页Bean
	 * @param pager 分页Bean
	 */
	public void setPager(Pages pager) {
		this.pager = pager;
	}

	/**
	 * 获得排序参数
	 * @return 排序参数
	 */
	public Map<String, Object> getOrders() {
		return orders;
	}

	/**
	 * 设置排序参数
	 * @param orders 排序参数
	 */
	public void setOrders(String orders) {
		this.orders = JsonEngine.toMap(orders);
	}

//	/**
//	 * 获得业务
//	 * @return 业务
//	 */
//	public SuperService getService() {
//		return SuperService.DAO;
//	}
//
//	/**
//	 * 获得查询器
//	 * @return 查询器
//	 */
//	public QueryService getQuery() {
//		return query;
//	}

	/**
	 * 获得验证登录标识
	 * @return 验证登录标识
	 */
	public TokenBean getToken() {
		return token;
	}

	/**
	 * 获得主键
	 * @return 主键
	 */
	public Serializable getKey() {
		return key;
	}

	/**
	 * 设置主键
	 * @param key 主键
	 */
	public void setKey(Serializable key) {
		// 如果传递进来的是数组
		if (key.getClass().isArray()) {
			// 转换成数组
			Serializable[] keys = (Serializable[]) key;
			// 如果只有一个值 赋值给key 否则赋值给keys
			if (keys.length == 1) {
				setKey(keys[0]);
			} else {
				setKeys(keys);
			}
		} else if (key instanceof String) {
			// 传的是字符串
			String s = Conversion.toString(key);
			// 如果是json串
			if (!JsonEngine.isJson(s) && s.indexOf(StringConstants.COMMA) > -1) {
				// ,号分割的字符串 转换成数组setKey
				setKey(s.split(StringConstants.COMMA));
			} else {
				this.key = s;
			}
		} else {
			this.key = key;
		}
	}

	/**
	 * 获得主键数组
	 * @return 主键数组
	 */
	public Serializable[] getKeys() {
		return keys;
	}

	/**
	 * 设置主键数组
	 * @param keys 主键数组
	 */
	public void setKeys(Serializable[] keys) {
		this.keys = keys;
	}

	/**
	 * 方法回调 所有直接Action回调的方法 一边统一处理
	 * @param obj 处理对象
	 * @return 返回标识
	 */
	public String callback(Object obj) {
		return call(response, obj);
	}

	/**
	 * 方法回调 所有直接Action回调的方法 一边统一处理
	 * @param response HttpServletResponse
	 * @param obj 处理对象
	 * @return 返回标识
	 */
	public String call(HttpServletResponse response, Object obj) {
		if (obj == null) {
			return addMessage(ERROR);
		} else if ("ajax".equals(mode)) {
			return ajax(response, obj);
		} else if (obj instanceof String) {
			String re = Conversion.toString(obj);
			return SUCCESS.equals(re) || ERROR.equals(re) ? addMessage(re) : re;
		} else if (obj instanceof List<?> || obj instanceof Map<?, ?>) {
			return LIST;
		} else if (obj instanceof Boolean) {
			return Conversion.toBoolean(obj) ? SUCCESS : ERROR;
		} else if (obj instanceof Integer) {
			return EmptyUtil.isEmpty(obj) ? ERROR : SUCCESS;
		} else {
			return addMessage(SUCCESS);
		}
	}

	/**
	 * 添加错误信息 错误Field=key value=国际化value
	 * @param key 国际化文件的Key
	 * @return 字符串
	 */
	public String addError(String key) {
		error.add(key);
		return key;
	}

	/**
	 * 添加信息 调用addActionMessage做国际化处理
	 * @param key 国际化文件的Key
	 * @return 字符串
	 */
	public String addMessage(String key) {
		message.add(key);
		return key;
	}

	/**
	 * 添加实体
	 * @param e Entity
	 * @return Entity
	 */
	protected Entity theme(Entity e) {
		// 判断e==null 直接返回
		if (e == null) {
			return e;
		}
		// 判断是否EntityStartEndTime
		if (e instanceof EntityStartEndTime) {
			// 开始时间
			if (((EntityStartEndTime) e).getStartTime() != null) {
				startDate = DateUtil.toString(((EntityStartEndTime) e).getStartTime());
			}
			// 结束时间
			if (((EntityStartEndTime) e).getEndTime() != null) {
				endDate = DateUtil.toString(((EntityStartEndTime) e).getEndTime());
			}
		}
		return e;
	}

	/**
	 * 添加实体
	 * @param e Entity
	 * @return Entity
	 */
	protected Entity add(Entity e) {
		// 判断实体类型
		if (e instanceof EntityTime && EmptyUtil.isEmpty(((EntityTime) e).getTime())) {
			((EntityTime) e).setTime(DateUtil.getTime());
		}
		if (e instanceof EntityIp && EmptyUtil.isEmpty(((EntityIp) e).getIp())) {
			if (!EmptyUtil.isEmpty(((EntityIp) e).getIp())) {
				((EntityIp) e).setIp(getIp());
			}
		}
		if (e instanceof EntityStartEndTime) {
			// 开始时间
			if (!EmptyUtil.isEmpty(startDate) && EmptyUtil.isEmpty(((EntityStartEndTime) e).getStartTime())) {
				((EntityStartEndTime) e).setStartTime(DateUtil.getTime(startDate));
			}
			// 结束时间
			if (!EmptyUtil.isEmpty(endDate) && EmptyUtil.isEmpty(((EntityStartEndTime) e).getEndTime())) {
				((EntityStartEndTime) e).setEndTime(DateUtil.getTime(endDate));
			}
		}
		if (e instanceof EntityUserId) {
			((EntityUserId) e).setUserId(token.getId());
		}
		// 返回E
		return upload(request, e);
	}

	/**
	 * 上传文件
	 * @param request HttpServletRequest
	 * @param e Entity
	 * @return Entity
	 */
	protected Entity upload(HttpServletRequest request, Entity e) {
		// if (e instanceof EntityFile) {
		// // 上次文件
		// String path = upload(request, file, fileFileName);
		// // 路径不为空
		// if (!EmptyUtil.isEmpty(path)) {
		// ((EntityFile) e).setPath(path);
		// }
		// }
		// if (e instanceof EntityFiles) {
		// // 上次文件
		// String[] paths = uploads(request, files, filesFileName);
		// // 路径不为空
		// if (!EmptyUtil.isEmpty(paths)) {
		// ((EntityFiles) e).setPaths(paths);
		// }
		// }
		return e;
	}

	/**
	 * 以sign模式输出数据到客户端方法
	 * @param response HttpServletResponse
	 * @param obj 对象
	 * @return 字符串
	 */
	protected String sign(HttpServletResponse response, Object obj) {
		return ajax(response,
				obj instanceof String || obj instanceof Number ? obj : EmptyUtil.isEmpty(obj) ? ERROR : SUCCESS);
	}

	/**
	 * 以key模式输出数据到客户端方法
	 * @param response HttpServletResponse
	 * @param obj 对象
	 * @return 字符串
	 */
	protected String key(HttpServletResponse response, Object obj) {
		return ajax(response, obj instanceof String || obj instanceof Number ? obj
				: obj instanceof Entity ? ((Entity) obj).getKey() : ERROR);
	}

	/**
	 * 获得验证登录凭证
	 * @return Token
	 */
	protected TokenBean auth() {
		return LoginEngine.getLoginBean(request, "user");
	}
}
