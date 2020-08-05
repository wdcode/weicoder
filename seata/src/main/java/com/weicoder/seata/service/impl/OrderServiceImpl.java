package com.weicoder.seata.service.impl;

import java.sql.SQLException;

import com.weicoder.seata.service.AccountService;
import com.weicoder.seata.service.OrderService;
import com.weicoder.seata.utils.DataSourceUtil;

/**
 * The type Order service.
 *
 * @author jimin.jm @alibaba-inc.com
 * @date 2019 /08/21
 */
public class OrderServiceImpl implements OrderService {

    /**
     * The constant DB_KEY.
     */
    public static final String DB_KEY = "order";
    private AccountService accountService;

    @Override
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void create(String userId, String commodityCode, Integer count) throws SQLException {
        int money = count * 200;
        String sql = "insert into order_tbl (user_id, commodity_code, count, money) values ('" + userId + "','"
            + commodityCode + "'," + count + "," + money + ")";
        DataSourceUtil.executeUpdate(DB_KEY, sql);
        accountService.reduce(userId, money);

    }

    @Override
    public void reset(String key, String value) throws SQLException {
        String deleteSql = "delete from order_tbl";
        DataSourceUtil.executeUpdate(DB_KEY, deleteSql);
    }
}
