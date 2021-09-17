package cn.liyongwei.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtil1 {

    public static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String URL = "jdbc:mysql://localhost:3306/db_ref";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "123456";

    /**
     * 获取数据库连接
     *
     * @return java.sql.Connection
     */
    public static Connection getConnection(){
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("数据库驱动加载失败");
            e.printStackTrace();
        }
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException throwables) {
            System.out.println("数据库连接失败，请检查数据库url，用户名和密码是否正确");
            throwables.printStackTrace();
        }
        return conn;
    }

    /**
     * 关闭数据库连接
     *
     * @param connection 数据库连接
     */
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
}
