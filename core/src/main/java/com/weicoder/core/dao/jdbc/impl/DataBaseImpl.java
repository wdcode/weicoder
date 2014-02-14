package com.weicoder.core.dao.jdbc.impl;

import java.io.Closeable;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.constants.StringConstants;

import com.weicoder.common.interfaces.Close;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.lang.Maps;
import com.weicoder.core.log.Logs;
import com.weicoder.common.util.CloseUtil;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.util.BeanUtil;
import com.weicoder.common.util.StringUtil;
import com.weicoder.core.dao.jdbc.DataBase;
import com.weicoder.core.dao.datasource.DataSource;

/**
 * JDBC实现 数据库操作实现<br/>
 * <h2>注: 内部使用</h2>
 * @author WD
 * @since JDK7
 * @version 1.0 2009-04-30
 */
public final class DataBaseImpl implements DataBase {
	// 声明DataSource
	private DataSource	dataSource;

	/**
	 * 构造函数
	 * @param dataSource DataSource对象
	 */
	public DataBaseImpl(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * 完成数据库的增删改操作，要求传入的sql语句必须为insert,update或delete <br/>
	 * 以List包含数组表示的多条条件 可使用单条sql批量插入
	 * @param sql 传入的sql语句
	 * @param parame SQL语句的参数 List包含数组 按顺序赋值给sql中的?
	 * @return 更新的行数
	 */
	public int execute(String sql, List<Object> parame) {
		return execute(sql, parame.toArray());
	}

	/**
	 * 完成数据库的增删改操作，要求传入的sql语句必须为insert,update或delete
	 * @param sql 传入的sql语句
	 * @param parame SQL语句的参数 按顺序赋值给sql中的?
	 * @return 更新的行数
	 */
	public int execute(String sql, Object... parame) {
		return execute(sql, new Object[][] { parame });
	}

	/**
	 * 完成数据库的增删改操作，要求传入的sql语句必须为insert,update或delete<br/>
	 * 以二维数组表示的多条条件 可使用单条sql批量插入
	 * @param sql 传入的sql语句
	 * @param parame SQL语句的参数 二维数组 按顺序赋值给sql中的?
	 * @return 更新的行数
	 */
	public int execute(String sql, Object[][] parame) {
		// 声明Connection
		Connection conn = null;
		// 声明PreparedStatement
		PreparedStatement pstmt = null;
		try {
			// 获得连接
			conn = getConnection();
			// 获得PreparedStatement实例
			pstmt = getStatement(conn, sql, parame);
			// 返回影响行数
			return executeBatch(conn, pstmt);
		} catch (SQLException e) {
			// 回滚
			rollback(conn);
			// 记录日志
			Logs.error(e);
			// 返回空列表
			return -1;
		} finally {
			// 关闭资源
			CloseUtil.close(pstmt, conn);
		}
	}

	/**
	 * 传入一组sql语句，完成数据库的增删改操作 要求传入的sqlArr数组中的SQL语句必须为insert,update或delete <br/>
	 * 传入的parame为多个条件 每个一唯数组给一条sql附值
	 * @param sql 传入的sql语句数组 可执行多条语句
	 * @param parame SQL语句的参数 按顺序赋值给sql中的?
	 * @return 更新的行数
	 */
	public int execute(String[] sql, Object[]... parame) {
		// 声明一个列表 长度为1
		List<Object[][]> list = Lists.getList(parame.length);
		// 循环参数
		for (int i = 0; i < parame.length; i++) {
			// 添加二唯数组
			list.add(new Object[][] { parame[i] });
		}
		// 调用方法
		return execute(sql, list);
	}

	/**
	 * 完成数据库的增删改操作，要求传入的sql语句必须为insert,update或delete <br/>
	 * 以一个List包含二维数组 可使用多条sql批量插入
	 * @param sql 传入的sql语句 可执行多条语句
	 * @param parame SQL语句的参数 List包含二维数组 按顺序赋值给sql中的?
	 * @return 更新的行数
	 */
	public int execute(String[] sql, List<Object[][]> parame) {
		// 声明Connection
		Connection conn = null;
		// 声明结果集 初始化为0
		int result = 0;
		// 声明PreparedStatement
		PreparedStatement pstmt = null;
		try {
			// 获得连接
			conn = getConnection();
			// 循环sql
			for (int i = 0; i < sql.length; i++) {
				// 获得PreparedStatement
				pstmt = getStatement(conn, sql[i], (parame.get(i)));
				// 执行批
				result += executeBatch(conn, pstmt);
			}
		} catch (SQLException e) {
			// 回滚事务
			rollback(conn);
			// 记录日志
			Logs.error(e);
			// 返回空列表
			return -1;
		} finally {
			// 关闭资源
			CloseUtil.close(pstmt, conn);
		}
		// 返回影响行数
		return result;
	}

	/**
	 * 执行指定过程 用于非查询
	 * @param call 执行过程的SQL语句
	 * @param parame 参数
	 * @return 返回影响行数
	 */
	public int executeCall(String call, Object... parame) {
		// 声明Connection
		Connection conn = null;
		// 声明CallableStatement
		CallableStatement cstmt = null;
		try {
			// 获得连接
			conn = getConnection();
			// 设置不自动提交
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			// 获得CallableStatement
			cstmt = conn.prepareCall(call);
			// 判断参数不为空
			if (parame != null) {
				// 循环参数
				for (int i = 0; i < parame.length; i++) {
					// 设置参数
					setParame(cstmt, i + 1, parame[i]);
				}
			}
			// 提交
			conn.commit();
			// 执行Call返回影响行数
			return cstmt.executeUpdate();
		} catch (SQLException e) {
			// 回滚
			rollback(conn);
			// 记录日志
			Logs.error(e);
			// 返回空列表
			return -1;
		} finally {
			// 关闭资源
			CloseUtil.close(cstmt, conn);
		}
	}

	/**
	 * 执行指定过程 用于查询
	 * @param call 执行过程的SQL语句
	 * @param parame 参数
	 * @return 返回结果集
	 */
	public List<Map<String, Object>> queryCall(String call, Object... parame) {
		// 声明Connection
		Connection conn = null;
		// 声明CallableStatement
		CallableStatement cstmt = null;
		// 声明ResultSet
		ResultSet rs = null;
		try {
			// 获得连接
			conn = getConnection();
			// 设置不自动提交
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			// 获得CallableStatement
			cstmt = conn.prepareCall(call);
			// 判断参数不为空
			if (!EmptyUtil.isEmpty(parame)) {
				// 循环参数
				for (int i = 0; i < parame.length; i++) {
					// 设置参数
					setParame(cstmt, i + 1, parame[i]);
				}
			}
			// 获得结果集
			rs = cstmt.executeQuery();
			// 获得列名的名称
			String[] colName = getColName(rs);
			// 声明List用于保存结果集
			List<Map<String, Object>> list = Lists.getList();
			// 循环结果集
			while (rs.next()) {
				// 添加行记录
				list.add(getRowValue(colName, rs));
			}
			// 提交
			conn.commit();
			// 返回数据列表
			return list;
		} catch (SQLException e) {
			// 回滚
			rollback(conn);
			// 记录日志
			Logs.error(e);
			// 返回空列表
			return Lists.emptyList();
		} finally {
			// 关闭资源
			CloseUtil.close(rs, cstmt, conn);
		}
	}

	/**
	 * 查询结果
	 * @param sql 查询语句
	 * @param parame SQL语句的参数
	 * @param c 返回的实体类Class
	 * @return List
	 */
	public <E> List<E> query(String sql, Class<E> c, Object... parame) {
		return BeanUtil.copyProperties(c, queryMultiRowMultiCol(sql, parame));
	}

	/**
	 * 返回一行一列查询结果,如果SQL语句查询出多条，只返回第一条记录
	 * @param sql String 查询语句
	 * @param parame SQL语句的参数
	 * @return String 列值
	 */
	public String querySnglRowSnglCol(String sql, Object... parame) {
		// 声明Connection
		Connection conn = null;
		// 声明PreparedStatement
		PreparedStatement pstmt = null;
		// 声明ResultSet
		ResultSet rs = null;
		try {
			// 获得连接
			conn = getConnection();
			// 获得PreparedStatement
			pstmt = getStatement(conn, sql, parame);
			// 获得ResultSet
			rs = pstmt.executeQuery();
			// 判断有结果
			if (rs.next()) {
				// 返回值
				return rs.getString(1);
			}
		} catch (SQLException e) {
			// 记录日志
			Logs.error(e);
			// 返回空列表
			return StringConstants.EMPTY;
		} finally {
			// 关闭资源
			CloseUtil.close(rs, pstmt, conn);
		}
		// 返回""串
		return StringConstants.EMPTY;
	}

	/**
	 * 返回一行多列查询结果,如果SQL语句查询出多条，只返回第一条记录
	 * @param sql String 查询语句
	 * @param parame SQL语句的参数
	 * @return Map 返回的数据集合 key列名(列名都小写) value(值)
	 */
	public Map<String, Object> querySnglRowMultiCol(String sql, Object... parame) {
		// 声明Connection
		Connection conn = null;
		// 声明PreparedStatement
		PreparedStatement pstmt = null;
		// 声明ResultSet
		ResultSet rs = null;
		try {
			// 获得连接
			conn = getConnection();
			// 获得PreparedStatement
			pstmt = getStatement(conn, sql, parame);
			// 获得结果集
			rs = pstmt.executeQuery();
			// 获得列名的名称
			String[] colName = getColName(rs);
			// 判断结果集不为空
			if (rs.next()) {
				// 返回行数据
				return getRowValue(colName, rs);
			}
		} catch (SQLException e) {
			// 记录日志
			Logs.error(e);
			// 返回空列表
			return Maps.emptyMap();
		} finally {
			// 关闭资源
			CloseUtil.close(rs, pstmt, conn);
		}
		// 返回结果
		return Collections.emptyMap();
	}

	/**
	 * 返回多行一列查询结果
	 * @param sql String 查询语句
	 * @param parame SQL语句的参数
	 * @return List 值
	 */
	public List<Object> queryMultiRowSnglCol(String sql, Object... parame) {
		// 声明Connection
		Connection conn = null;
		// 声明PreparedStatement
		PreparedStatement pstmt = null;
		// 声明ResultSet
		ResultSet rs = null;
		try {
			// 获得连接
			conn = getConnection();
			// 获得PreparedStatement
			pstmt = getStatement(conn, sql, parame);
			// 获得结果集
			rs = pstmt.executeQuery();
			// 实例化列表
			List<Object> list = Lists.getList();
			// 循环结果集
			while (rs.next()) {
				// 添加结果
				list.add(rs.getObject(1));
			}
			// 返回结果
			return list;
		} catch (SQLException e) {
			// 记录日志
			Logs.error(e);
			// 返回空列表
			return Lists.emptyList();
		} finally {
			// 关闭资源
			CloseUtil.close(rs, pstmt, conn);
		}
	}

	/**
	 * 返回多行多列查询结果
	 * @param sql String 查询语句
	 * @param parame SQL语句的参数
	 * @return List 返回的数据集合 Map key列名(列名都小写) value(值)
	 */
	public List<Map<String, Object>> queryMultiRowMultiCol(String sql, Object... parame) {
		// 声明Connection
		Connection conn = null;
		// 声明PreparedStatement
		PreparedStatement pstmt = null;
		// 声明ResultSet
		ResultSet rs = null;
		try {
			// 获得连接
			conn = getConnection();
			// 获得PreparedStatement
			pstmt = getStatement(conn, sql, parame);
			// 获得结果集
			rs = pstmt.executeQuery();
			// 获得列名的名称
			String[] colName = getColName(rs);
			// 声明列表
			List<Map<String, Object>> list = Lists.getList();
			// 循环结果集
			while (rs.next()) {
				// 添加行数据
				list.add(getRowValue(colName, rs));
			}
			// 返回结果集
			return list;
		} catch (SQLException e) {
			// 记录日志
			Logs.error(e);
			// 返回空列表
			return Lists.emptyList();
		} finally {
			// 关闭资源
			CloseUtil.close(rs, pstmt, conn);
		}
	}

	/**
	 * 获得Connection对象
	 * @return Connection
	 */
	public Connection getConnection() {
		try {
			return dataSource.getConnection();
		} catch (Exception e) {
			// 记录日志
			Logs.error(e);
			// 返回null
			return null;
		}
	}

	/**
	 * 获得数据源
	 * @return DataSource 数据源
	 */
	public DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * 设置数据源
	 * @param dataSource 数据源
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * 释放资源
	 */
	public void close() {
		// 关闭连接
		if (dataSource instanceof Close) {
			CloseUtil.close((Close) dataSource);
		} else if (dataSource instanceof Closeable) {
			CloseUtil.close((Closeable) dataSource);
		}
	}

	/**
	 * 回滚事务
	 * @param conn Connection
	 */
	private void rollback(Connection conn) {
		// 回滚事务
		try {
			conn.rollback();
		} catch (SQLException se) {
			// 记录日志
			Logs.error(se);
		}
	}

	/**
	 * 执行批处理
	 * @param conn Connection
	 * @param pstmt PreparedStatement
	 * @return 执行后的结果数
	 * @throws SQLException 数据库异常
	 */
	private int executeBatch(Connection conn, PreparedStatement pstmt) throws SQLException {
		// 声明结果集 初始化为0
		int result = 0;
		// 执行批
		int[] count = pstmt.executeBatch();
		// 循环批结果获得 影响行数
		for (int i = 0; i < count.length; i++) {
			// 累加影响行数
			result += count[i];
		}
		// 提交事务
		conn.commit();
		// 返回结果数
		return result;
	}

	/**
	 * 获得PreparedStatement 并赋值完参数
	 * @param conn Connection
	 * @param sql sql语句
	 * @param parame 参数
	 * @return PreparedStatement
	 * @throws SQLException 数据库异常
	 */
	private PreparedStatement getStatement(Connection conn, String sql, Object[][] parame) throws SQLException {
		// 设置不自动提交
		if (conn.getAutoCommit()) {
			conn.setAutoCommit(false);
		}
		// 获得PreparedStatement实例
		PreparedStatement pstmt = conn.prepareStatement(sql);
		// 判断参数不为空
		if (EmptyUtil.isEmpty(parame)) {
			// 添加到批里
			pstmt.addBatch();
		} else {
			// 声明字符串数组 给sql每条的条件预备
			Object[] objs = null;
			// 循环外围数组,表示多少条的条件
			for (int i = 0; i < parame.length; i++) {
				// 获得每条的条件
				objs = parame[i];
				// 循环设置每条条件
				for (int j = 0; j < objs.length; j++) {
					// 设置参数
					setParame(pstmt, j + 1, objs[j]);
				}
				// 添加到批里
				pstmt.addBatch();
			}
		}
		// 返回PreparedStatement实例
		return pstmt;
	}

	/**
	 * 获得PreparedStatement 并赋值完参数
	 * @param conn Connection
	 * @param sql sql语句
	 * @param parame 参数
	 * @return PreparedStatement
	 * @throws SQLException 数据库异常
	 */
	private PreparedStatement getStatement(Connection conn, String sql, Object[] parame) throws SQLException {
		// 获得PreparedStatement实例
		PreparedStatement pstmt = conn.prepareStatement(sql);
		// 判断参数不为空F
		if (!EmptyUtil.isEmpty(parame)) {
			// 循环设置每条条件
			for (int i = 0; i < parame.length; i++) {
				// 设置参数
				setParame(pstmt, i + 1, parame[i]);
			}
		}
		// 返回PreparedStatement实例
		return pstmt;
	}

	/**
	 * 获得每行的值
	 * @param colName
	 * @param rs
	 * @return
	 */
	private Map<String, Object> getRowValue(String[] colName, ResultSet rs) {
		// 声明Map 用于保存返回结果
		Map<String, Object> map = Maps.getMap();
		try {
			// 循环列
			for (int i = 0; i < colName.length; i++) {
				// 保存列数据
				map.put(colName[i], rs.getObject(i + 1));
			}
		} catch (SQLException e) {
			// 记录异常
			Logs.error(e);
			// 返回-1
			return Collections.emptyMap();
		}
		// 返回Map
		return map;
	}

	/**
	 * 获得列名
	 * @param rs 结果集
	 * @return String[] 列名
	 */
	private String[] getColName(ResultSet rs) {
		try {
			// 获得ResultSetMetaData
			ResultSetMetaData rsmd = rs.getMetaData();
			// 取得记录列数
			int colCount = rsmd.getColumnCount();
			// 实例化String[]
			String[] colName = new String[colCount];
			// 循环列
			for (int i = 0; i < colCount; i++) {
				// 获得列名
				colName[i] = StringUtil.convert(rsmd.getColumnName(i + 1));
			}
			// 返回列名
			return colName;
		} catch (SQLException e) {
			// 记录异常
			Logs.error(e);
		}
		// 返回列名
		return ArrayConstants.STRING_EMPTY;
	}

	/**
	 * 设置参数
	 * @param pstmt PreparedStatement
	 * @param index 位置
	 * @param parame 参数
	 * @throws SQLException 设置参数错误
	 */
	private void setParame(PreparedStatement pstmt, int index, Object parame) throws SQLException {
		// 判断参数类型
		if (parame == null) {
			// null
			pstmt.setNull(index, java.sql.Types.NULL);
		} else if (parame instanceof String) {
			// String
			pstmt.setString(index, Conversion.toString(parame));
		} else if (parame instanceof Integer) {
			// Integer
			pstmt.setInt(index, Conversion.toInt(parame));
		} else if (parame instanceof Long) {
			// Long
			pstmt.setLong(index, Conversion.toLong(parame));
		} else if (parame instanceof Double) {
			// Double
			pstmt.setDouble(index, Conversion.toDouble(parame));
		} else if (parame instanceof Float) {
			// Float
			pstmt.setFloat(index, Conversion.toFloat(parame));
		} else if (parame instanceof Short) {
			// Short
			pstmt.setShort(index, Conversion.toShort(parame));
		} else if (parame instanceof Byte) {
			// Byte
			pstmt.setByte(index, Conversion.toByte(parame));
		} else if (parame instanceof Boolean) {
			// Boolean
			pstmt.setBoolean(index, Conversion.toBoolean(parame));
		} else if (parame instanceof BigDecimal) {
			// BigDecimal
			pstmt.setBigDecimal(index, Conversion.toBigDecimal(parame));
		} else if (parame instanceof Blob) {
			// Blob
			pstmt.setBlob(index, (Blob) parame);
		} else if (parame instanceof Clob) {
			// Clob
			pstmt.setClob(index, (Clob) parame);
		} else if (parame instanceof Date) {
			// Date
			pstmt.setDate(index, (Date) parame);
		} else if (parame instanceof Array) {
			// Array
			pstmt.setArray(index, (Array) parame);
		} else {
			// Object
			pstmt.setObject(index, parame);
		}
	}
}
