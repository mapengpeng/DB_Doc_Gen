package com.mapp.db.gen.util;

import cn.hutool.core.io.resource.ResourceUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * JDBC 工具类
 *
 * @author mapp
 */
@Slf4j
public class JdbcUtil {

    public static final String DB_URL;
    public static final String DB_USERNAME;
    public static final String DB_PASSWORD;
    public static final String DB_DRIVER;

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }


    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    static {
        Properties properties = new Properties();
        try {
            properties.load(ResourceUtil.getStreamSafe("db.properties"));
        } catch (IOException e) {
            log.error("加载db.properties失败！");
        }
        DB_URL = properties.getProperty("db.url");
        DB_USERNAME = properties.getProperty("db.username");
        DB_PASSWORD = properties.getProperty("db.password");
        DB_DRIVER = properties.getProperty("db.driver");

        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            log.error("数据库驱动加载失败！");
        }
    }
}
