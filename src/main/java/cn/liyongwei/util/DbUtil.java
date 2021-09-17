package cn.liyongwei.util;

import java.io.File;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtil {
    private static final String DriverName = "org.sqlite.JDBC";
    private static final String URL = "jdbc:sqlite:sql/data.db";

    public static Connection getConnection() {
        try {
            Class.forName(DriverName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
        } catch (SQLException throwables) {
            System.out.println("数据库连接失败");
            throwables.printStackTrace();
        }
        return conn;
    }

    public static void closeConnection(Connection connection){
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException throwables) {
                System.out.println("数据库连接关闭失败");
                throwables.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        DbUtil.getConnection();
    }
}
