package com.weicoder.base.action;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import com.weicoder.base.entity.Entity;
import com.weicoder.base.entity.EntityIp;
import com.weicoder.base.entity.EntityStartEndTime;
import com.weicoder.base.entity.EntityFile;
import com.weicoder.base.entity.EntityFiles;
import com.weicoder.base.entity.EntityTime;
import com.weicoder.base.entity.EntityUserId;
import com.weicoder.base.service.QueryService;
import com.weicoder.base.service.SuperService;
import com.weicoder.base.token.AuthToken;
import com.weicoder.common.constants.DateConstants;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.lang.Maps;
import com.weicoder.base.bean.Pagination;
import com.weicoder.common.util.BeanUtil;
import com.weicoder.common.util.DateUtil;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.util.StringUtil;
import com.weicoder.core.json.JsonEngine;
import com.weicoder.core.log.Logs;
import com.weicoder.web.constants.HttpConstants;

/**
 * 超级通用Action
 * @author WD
 * @since JDK7
 * @version 1.0 2012-07-4
 */
public abstract class SuperAction extends BasicAction {
	// 时间字段
	protected final static String	TIME_FIELD	= "time";
	// 通用业务接口
	@Resource
	protected SuperService			service;
	// 查询器
	@Resource
	protected QueryService			query;
	// 验证登录标识
	protected AuthToken				token;
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
	@Resource
	protected Pagination			pager;
	// 排序参数
	protected Map<String, Object>	orders;
	// 实体是否初始化
	protected boolean				isEntity;

	@PostConstruct
	protected void init() {
		// 父类初始化
		super.init();
		// 初始化空排序
		orders = Maps.getMap();
		// 获得实体类
		entityClass = context.getClass(module);
		// 获得ContentType
		String contentType = request.getContentType();
		// 判断为上传文件表单
		if (!EmptyUtil.isEmpty(contentType) && contentType.indexOf(HttpConstants.CONTENT_TYPE_FILE) > -1) {
			isEntity = true;
			// 获得实体
			entity = entityClass == null ? null : context.getBean(module, entityClass);
		} else {
			// 是否初始化实体
			for (Map.Entry<String, String[]> e : request.getParameterMap().entrySet()) {
				if (e.getKey().indexOf("entity") > -1) {
					isEntity = true;
					// 获得实体
					entity = entityClass == null ? null : context.getBean(module, entityClass);
					break;
				}
			}
		}
		// 如果查询自己的数据 添加登录用户名
		if (entity == null && entityClass != null && EntityUserId.class.isAssignableFrom(entityClass)) {
			entity = context.getBean(module, entityClass);
		}
		if (entity instanceof EntityUserId) {
			((EntityUserId) entity).setUserId(token.getId());
		}
	}

	/**
	 * 重置缓存
	 * @return 跳转
	 * @throws Exception
	 */
	public String load() throws Exception {
		// 重载数据
		if (entityClass == null) {
			service.load();
		} else {
			service.load(entityClass);
		}
		// 返回到成功页
		return callback(SUCCESS);
	}

	/**
	 * 重置缓存
	 * @return 跳转
	 * @throws Exception
	 */
	public String cache() throws Exception {
		// 重载缓存
		if (entityClass == null) {
			service.cache();
		} else {
			service.load(entityClass);
		}
		// 返回到成功页
		return callback(SUCCESS);
	}

	/**
	 * 添加
	 * @return 跳转
	 * @throws Exception
	 */
	public String add() throws Exception {
		Logs.info("add entity=" + entity);
		return callback(entity = service.insert(add(entity)));
	}

	/**
	 * 添加或修改
	 * @return 跳转
	 * @throws Exception
	 */
	public String addOrUpdata() throws Exception {
		Logs.info("addOrUpdata entity=" + entity);
		return callback(entity = service.insertOrUpdate(add(entity)));
	}

	/**
	 * 添加
	 * @return 跳转
	 * @throws Exception
	 */
	public String adds() throws Exception {
		Logs.info("adds entity=" + entitys + ";key=" + key);
		// 如果实体列表为空 并且key不为空
		if (EmptyUtil.isEmpty(entitys) && !EmptyUtil.isEmpty(key)) {
			entitys = JsonEngine.toList(Conversion.toString(key), entityClass);
		}
		// 循环实体数组
		for (Entity e : entitys) {
			add(e);
		}
		// 添加并返回结果
		return callback(service.insert(entitys));
	}

	/**
	 * 修改
	 * @return 跳转
	 * @throws Exception
	 */
	public String edit() throws Exception {
		Logs.info("edit entity=" + entity);
		// 获得要更像的实体
		Entity e = service.get(entityClass, entity.getKey());
		// 实体不为空 更新 否则返回错误
		return callback(entity = service.update(BeanUtil.copyProperties(upload(entity), e)));
	}

	/**
	 * 修改
	 * @return 跳转
	 * @throws Exception
	 */
	public String edits() throws Exception {
		Logs.info("edits entity=" + entitys + ";key=" + key);
		// 如果实体列表为空 并且key不为空
		if (EmptyUtil.isEmpty(entitys) && !EmptyUtil.isEmpty(key)) {
			entitys = JsonEngine.toList(Conversion.toString(key), entityClass);
		}
		// 实体列表不为空
		if (!EmptyUtil.isEmpty(entitys)) {
			// 获得列表长度
			int size = entitys.size();
			// 声明修改实体数组
			List<Entity> es = Lists.getList(size);
			// 循环获取持久化数据实体
			for (int i = 0; i < size; i++) {
				// 获得修改实体
				Entity e = entitys.get(i);
				// 把新修改的值赋值给修改是实体
				es.add(BeanUtil.copyProperties(e, service.get(entityClass, e.getKey())));
			}
			// 修改实体
			entitys = service.update(es);
		}
		// 实体不为空 更新 否则返回错误
		return callback(entitys);
	}

	/**
	 * 删除
	 * @return 跳转
	 * @throws Exception
	 */
	public String del() throws Exception {
		Logs.info("del entity=" + entity + ";key=" + key);
		// key为空
		if (EmptyUtil.isEmpty(key)) {
			// 实体不为空
			if (entity != null) {
				// 实体主键为空
				if (EmptyUtil.isEmpty(entity.getKey())) {
					// 按实体查询出相关列表 在删除
					entitys = service.delete(entity);
				} else {
					// 按实体主键删除
					entitys = service.delete(entityClass, entity.getKey());
				}
			}
		} else {
			// 按key删除
			entitys = service.delete(entityClass, key);
		}
		return callback(EmptyUtil.isEmpty(entitys) ? ERROR : (entity = entitys.get(0)));
	}

	/**
	 * 删除多个
	 * @return 跳转
	 * @throws Exception
	 */
	public String dels() throws Exception {
		Logs.info("dels entity=" + entitys);
		return callback(EmptyUtil.isEmpty(entitys = service.delete(entityClass, keys)) ? ERROR : mode);
	}

	/**
	 * 查询所有
	 * @return 跳转list
	 * @throws Exception
	 */
	public String all() throws Exception {
		return callback(entitys = service.all(entityClass));
	}

	/**
	 * 分页查询
	 * @return 跳转list
	 * @throws Exception
	 */
	public String page() throws Exception {
		Logs.info("page entity=" + entity + ";pager=" + pager);
		// 查询实体列表
		entitys = (entity == null ? service.list(entityClass, pager) : service.list(entity, pager));
		// 声明返回列表
		Map<String, Object> map = Maps.getMap();
		map.put("pager", pager);
		map.put("entitys", entitys);
		// 返回列表
		return callback(map);
	}

	/**
	 * 分页查询
	 * @return 跳转list
	 * @throws Exception
	 */
	public String order() throws Exception {
		Logs.info("page entity=" + entity + ";pager=" + pager);
		return callback(entitys = entity == null ? service.order(entityClass, orders, pager) : service.order(entity, orders, pager));
	}

	/**
	 * 分页查询
	 * @return 跳转list
	 * @throws Exception
	 */
	public String date() throws Exception {
		Logs.info("page entity=" + entity + ";pager=" + pager + ";start=" + startDate + ";end=" + endDate);
		// 如果开始时间和结束时间都为空
		if (EmptyUtil.isEmpty(startDate) && EmptyUtil.isEmpty(endDate)) {
			// 直接分页查询
			entitys = service.list(entity, pager);
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
			entitys = service.between(entity, TIME_FIELD, DateUtil.getTime(startDate), DateUtil.getTime(endDate) + DateConstants.DAY, pager);
		}
		// 返回列表页
		return callback(entitys);
	}

	/**
	 * 实体条件查询出所有
	 * @return 跳转SUCCESS
	 * @throws Exception
	 */
	public String get() throws Exception {
		Logs.info("get key=" + key);
		return callback(entity = key == null ? null : service.get(entityClass, key));
	}

	/**
	 * 实体条件查询出所有
	 * @return 跳转SUCCESS
	 * @throws Exception
	 */
	public String gets() throws Exception {
		Logs.info("get keys=" + keys);
		return keys == null ? callback(entitys = service.gets(entityClass, key)) : callback(entitys = service.gets(entityClass, keys));
	}

	/**
	 * 实体条件查询出所有
	 * @return 跳转SUCCESS
	 * @throws Exception
	 */
	public String entity() throws Exception {
		Logs.info("entity=" + entity);
		return callback(entity = entity == null ? null : service.get(entity));
	}

	/**
	 * 实体条件查询出所有
	 * @return 跳转list
	 * @throws Exception
	 */
	public String entitys() throws Exception {
		Logs.info("entitys entity=" + entity + ";pager=" + pager);
		return callback(entity == null ? LIST : (entitys = service.list(entity, pager)));
	}

	/**
	 * 跳转到修改页
	 * @return 跳转
	 * @throws Exception
	 */
	public String theme() throws Exception {
		Logs.info("theme entity=" + entity);
		return callback(!EmptyUtil.isEmpty(entity = service.get(entityClass, entity.getKey())));
	}

	/**
	 * 跳转到列表页
	 * @return 跳转
	 * @throws Exception
	 */
	public String list() throws Exception {
		Logs.info("list entity=" + entity + ";pager=" + pager + ";orders=" + orders);
		// 排序参数为空
		if (EmptyUtil.isEmpty(orders)) {
			entitys = entity == null ? service.list(entityClass, pager) : service.list(entity, pager);
		} else {
			entitys = entity == null ? service.order(entityClass, orders, pager) : service.order(entity, orders, pager);
		}
		// 返回结果
		return callback(entitys);
	}

	/**
	 * 获得数量
	 * @return 跳转
	 * @throws Exception
	 */
	public String count() throws Exception {
		Logs.info("count entity=" + entity);
		return callback(entity == null ? service.count(entityClass) : service.count(entity));
	}

	/**
	 * 跳转到列表页
	 * @return 跳转
	 * @throws Exception
	 */
	public String search() throws Exception {
		Logs.info("theme search=" + entity + ";pager=" + pager);
		return callback(entitys = service.search(entity, pager));
	}

	/**
	 * 直接跳转
	 * @return
	 * @throws Exception
	 */
	public String to() throws Exception {
		return SUCCESS;
	}

	/**
	 * 直接跳转
	 * @return
	 * @throws Exception
	 */
	public String tos() throws Exception {
		return LIST;
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
	public Pagination getPager() {
		return pager;
	}

	/**
	 * 设置分页Bean
	 * @param pager 分页Bean
	 */
	public void setPager(Pagination pager) {
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

	/**
	 * 获得业务
	 * @return 业务
	 */
	public SuperService getService() {
		return service;
	}

	/**
	 * 获得查询器
	 * @return 查询器
	 */
	public QueryService getQuery() {
		return query;
	}

	/**
	 * 获得验证登录标识
	 * @return 验证登录标识
	 */
	public AuthToken getToken() {
		return token;
	}

	/**
	 * 添加实体
	 * @param e
	 * @return
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
	 * @param e
	 * @return
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
		return upload(e);
	}

	/**
	 * 上次文件
	 * @param e
	 * @return
	 */
	protected Entity upload(Entity e) {
		if (e instanceof EntityFile) {
			// 上次文件
			String path = upload(file, fileFileName);
			// 路径不为空
			if (!EmptyUtil.isEmpty(path)) {
				((EntityFile) e).setPath(path);
			}
		}
		if (e instanceof EntityFiles) {
			// 上次文件
			String[] paths = uploads(files, filesFileName);
			// 路径不为空
			if (!EmptyUtil.isEmpty(paths)) {
				((EntityFiles) e).setPaths(paths);
			}
		}
		return e;
	}

	/**
	 * 获得验证登录凭证
	 */
	protected abstract AuthToken auth();
}
