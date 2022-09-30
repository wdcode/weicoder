package com.weicoder.dao.base;

import java.util.List;

import static com.weicoder.dao.params.DaoParams.*;

import com.weicoder.common.lang.W;
import com.weicoder.common.thread.T;
import com.weicoder.common.util.U;
import com.weicoder.dao.Dao;

/**
 * Dao的基础功能实现
 * 
 * @author wdcode
 *
 */
public abstract class BaseDao implements Dao {
	@Override
	public <E> E insert(final E entity) {
		return W.L.get(insert(W.L.list(entity)), 0);
	}

	@Override
	public <E> List<E> insert(List<E> entitys, int step) {
		// 分步更新
		W.L.slice(entitys, step).forEach(ls -> {
			insert(ls);
			T.sleep(SETP_SLEEP);
		});
		return entitys;
	}

	@Override
	public <E> List<E> update(List<E> entitys, int step) {
		// 分步更新
		W.L.slice(entitys, step).forEach(ls -> {
			update(ls);
			T.sleep(SETP_SLEEP);
		});
		return entitys;
	}

	@Override
	public <E> E update(final E entity) {
		return update(W.L.list(entity)).get(0);
	}

	@Override
	public <E> List<E> insertOrUpdate(List<E> entitys, int step) {
		// 分步更新
		W.L.slice(entitys, step).forEach(ls -> {
			insertOrUpdate(ls);
			T.sleep(SETP_SLEEP);
		});
		return entitys;
	}

	@Override
	public <E> E insertOrUpdate(E entity) {
		return insertOrUpdate(W.L.list(entity)).get(0);
	}

	@Override
	public <E> E delete(E entity) {
		return delete(W.L.list(entity)).get(0);
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
