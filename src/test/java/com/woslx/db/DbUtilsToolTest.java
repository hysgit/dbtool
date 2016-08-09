package com.woslx.db;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by hy on 8/9/16.
 */
public class DbUtilsToolTest {
    private DbUtilsTool dbUtilsTool;

    @Before
    public void setUp() {
        dbUtilsTool = new DbUtilsTool("MySQL", "localhost", "3306", "mysql", "root", "mysql");
    }

    @Test
    public void testQueryArray() throws Exception {
        String sql = "select * from user";

        List<Object[]> objects = dbUtilsTool.queryArrayList(sql, null);
        System.out.println(objects);

        sql = "desc user";
        objects = dbUtilsTool.queryArrayList(sql, null);
        System.out.println(objects);
    }

    @Test
    public void testQueryArrayList() throws Exception {

    }

    @Test
    public void testQueryMap() throws Exception {

    }

    @Test
    public void testQueryMapList() throws Exception {

    }

    @Test
    public void testQueryBean() throws Exception {

    }

    @Test
    public void testQueryBeanList() throws Exception {

    }

    @Test
    public void testQueryColumn() throws Exception {

    }

    @Test
    public void testQueryColumnList() throws Exception {

    }

    @Test
    public void testQueryKeyMap() throws Exception {

    }

    @Test
    public void testUpdate() throws Exception {

    }
}