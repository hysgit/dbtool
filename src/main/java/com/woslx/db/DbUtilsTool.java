package com.woslx.db;

/**
 * Created by hy on 7/28/16.
 */

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class DbUtilsTool {
    private final QueryRunner runner = new QueryRunner();
    private Connection conn;

    public DbUtilsTool(String type,        //数据库类型
                       String host,        //主机ip
                       String port,        //主机端口
                       String name,        //数据库名
                       String username,    //用户名
                       String password)    //密码
    {
        try {
            String driver;
            String url;
            if (type.equalsIgnoreCase("MySQL")) {
                driver = "com.mysql.jdbc.Driver";
                url = "jdbc:mysql://" + host + ":" + port + "/" + name;
            } else if (type.equalsIgnoreCase("Oracle")) {
                driver = "oracle.jdbc.driver.OracleDriver";
                url = "jdbc:oracle:thin:@" + host + ":" + port + ":" + name;
            } else if (type.equalsIgnoreCase("SQLServer")) {
                driver = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
                url = "jdbc:sqlserver://" + host + ":" + port + ";databaseName=" + name;
            } else {
                throw new RuntimeException("不支持的数据库类型");
            }
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /* 关闭数据库连接 */
    public void closeConn() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /* 查询（返回Array结果） */
    public Object[] queryArray(String sql, Object... params) {
        Object[] result = null;
        try {
            result = runner.query(conn, sql, new ArrayHandler(), params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /* 查询（返回ArrayList结果） */
    public List<Object[]> queryArrayList(String sql, Object... params) {
        List<Object[]> result = null;
        try {
            result = runner.query(conn, sql, new ArrayListHandler(), params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /* 查询（返回Map结果） */
    public Map<String, Object> queryMap(String sql, Object... params) {
        Map<String, Object> result = null;
        try {
            result = runner.query(conn, sql, new MapHandler(), params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /* 查询（返回MapList结果） */
    public List<Map<String, Object>> queryMapList(String sql, Object... params) {
        List<Map<String, Object>> result = null;
        try {
            result = runner.query(conn, sql, new MapListHandler(), params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /* 查询（返回Bean结果） */
    public <T> T queryBean(Class<T> cls, Map<String, String> map, String sql, Object... params) {
        T result = null;
        try {
            if (map != null) {
                result = runner.query(conn, sql, new BeanHandler<T>(cls, new BasicRowProcessor(new BeanProcessor(map))), params);
            } else {
                result = runner.query(conn, sql, new BeanHandler<T>(cls), params);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /* 查询（返回BeanList结果） */
    public <T> List<T> queryBeanList(Class<T> cls, Map<String, String> map, String sql, Object... params) {
        List<T> result = null;
        try {
            if (map != null) {
                result = runner.query(conn, sql, new BeanListHandler<T>(cls, new BasicRowProcessor(new BeanProcessor(map))), params);
            } else {
                result = runner.query(conn, sql, new BeanListHandler<T>(cls), params);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /* 查询指定列名的值（单条数据） */
    public <T> T queryColumn(String column, String sql, Object... params) {
        T result = null;
        try {
            result = runner.query(conn, sql, new ScalarHandler<T>(column), params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /* 查询指定列名的值（多条数据） */
    public <T> List<T> queryColumnList(String column, String sql, Object... params) {
        List<T> result = null;
        try {
            result = runner.query(conn, sql, new ColumnListHandler<T>(column), params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /* 查询指定列名对应的记录映射 */
    public <T> Map<T, Map<String, Object>> queryKeyMap(String column, String sql, Object... params) {
        Map<T, Map<String, Object>> result = null;
        try {
            result = runner.query(conn, sql, new KeyedHandler<T>(column), params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /* 更新（包括UPDATE、INSERT、DELETE，返回受影响的行数） */
    public int update(String sql, Object... params) {
        int result = 0;
        try {
            result = runner.update(conn, sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        closeConn();
    }
}
