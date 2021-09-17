package cn.liyongwei.dao;

import cn.liyongwei.entity.User;
import cn.liyongwei.util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    /**
     * 登录
     * @param user 用户提供的账号密码
     * @return 若正确,返回登录成功，以及账户实体
     */
    public static User login(User user){
        //获取数据库连接
        Connection connection = DbUtil.getConnection();
        //准备数据库查询命令并查询
        String sql = "select * from table_user where userName=? and password=?";
        PreparedStatement pmt = null;
        ResultSet resultSet = null;
        try {
            pmt = connection.prepareStatement(sql);
            pmt.setString(1, user.getUserName());
            pmt.setString(2, user.getPassword());
            resultSet = pmt.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //处理数据库结果
        User resultUser = null;
        if (resultSet != null) {
            try {
                if (resultSet.next()) {
                    resultUser = new User();
                    resultUser.setId(resultSet.getInt("id"));
                    resultUser.setUserName(resultSet.getString("userName"));
                    resultUser.setPassword(resultSet.getString("password"));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        //关闭数据库连接
        DbUtil.closeConnection(connection);
        return resultUser;
    }

    /**
     * 添加用户
     * @param user 设置的用户
     * @return  状态
     */
    public static int add(User user) {
        //获取数据库连接
        Connection connection = DbUtil.getConnection();
        //准备数据库查询命令
        String sql = "insert into table_user values(null,?,?)";
        PreparedStatement pst = null;
        int result = 0;
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, user.getUserName());
            pst.setString(2, user.getPassword());
            result = pst.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }
}
