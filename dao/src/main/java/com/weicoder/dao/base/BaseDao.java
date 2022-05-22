package com.weicoder.dao.base;

import java.util.List;

import com.weicoder.common.U;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.util.ThreadUtil;
import com.weicoder.dao.Dao;
import com.weicoder.dao.params.DaoParams;

/**
 * Dao的基础功能实现
 * 
 * @author wdcode
 *
 */
public abstract class BaseDao implements Dao {
	@Override
	public <E> E insert(final E entity) {
		return Lists.get(insert(Lists.newList(entity)), 0);
	}
	
	@Override
	public <E> List<E> insert(List<E> entitys, int step) {
		// 分步更新
		Lists.slice(entitys, step).forEach(ls -> {
			insert(ls);
			ThreadUtil.sleep(DaoParams.SETP_SLEEP);
		});
		return entitys;
	}
	
	@Override
	public <E> List<E> update(List<E> entitys, int step) {
		// 分步更新
		Lists.slice(entitys, step).forEach(ls -> {
			update(ls);
			ThreadUtil.sleep(DaoParams.SETP_SLEEP);
		});
		return entitys;
	}

	@Override
	public <E> E update(final E entity) {
		return update(Lists.newList(entity)).get(0);
	}
	
	@Override
	public <E> List<E> insertOrUpdate(List<E> entitys, int step) {
		// 分步更新
		Lists.slice(entitys, step).forEach(ls -> {
			insertOrUpdate(ls);
			ThreadUtil.sleep(DaoParams.SETP_SLEEP);
		});
		return entitys;
	}

	@Override
	public <E> E insertOrUpdate(E entity) {
		return insertOrUpdate(Lists.newList(entity)).get(0);
	}
	
	@Override
	public <E> E delete(E entity) {
		return delete(Lists.newList(entity)).get(0);
	}
	
	@Override
	public <E> E get(final E entity) {
		// 获得结果
		List<E> list = list(entity, 0, 1);
		// 返回结果
		return U.E.isEmpty(list) ? null : list.get(0);
	}
	
	@Override
	public int count(Class<?> entityClass) {
		return count(entityClass, null, null);
	}
}
