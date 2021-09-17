package cn.liyongwei.dao;

import cn.liyongwei.entity.RefType;
import cn.liyongwei.util.DbUtil;
import cn.liyongwei.util.StringUtil;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class RefTypeDao {

    /**
     * 添加文献类型
     * @param refType 文献类型
     * @return 状态
     */
    public static int add(RefType refType){
        Connection connection = DbUtil.getConnection();
        String sql = "insert into table_reftype values(null,?)";
        PreparedStatement pst = null;
        int result = 0;
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, refType.getRefTypeName());
            result = pst.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    /**
     * 查询文献类别
     * @param refType 传入文献类别，若为空值，则返回所有类别，若不为空，在返回相近的类型
     * @return  文献类型数组
     */
    public static RefType[] list(RefType refType){
        //获取连接
        Connection conn = DbUtil.getConnection();
        //准备数据库搜索命令
        StringBuilder sql = new StringBuilder("select * from table_reftype");
        if (!StringUtil.isEmpty(refType.getRefTypeName())) {
            sql.append(" where refTypeName like '%").append(refType.getRefTypeName()).append("%'");
        }
        //搜索数据库
        ResultSet resultSet = null;
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql.toString());
            resultSet = pst.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //处理搜索结果
        if (resultSet == null) {
            return new RefType[0];
        }
        List<RefType> refTypeList = new LinkedList<>();
        RefType currentRefType;
        while (true) {
            try {
                if (!resultSet.next()) break;
                currentRefType = convertResultSet(resultSet);
                refTypeList.add(currentRefType);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        RefType[] refTypes = new RefType[refTypeList.size()];
        int i = 0;
        for(RefType r : refTypeList) {
            refTypes[i++] = r;
        }
        //关闭数据库连接
        DbUtil.closeConnection(conn);
        return refTypes;
    }

    /**
     * 将结果集转化为文献类型
     * @param resultSet 数据库结果集
     * @return  文献类型
     */
    private static RefType convertResultSet(ResultSet resultSet) {
        RefType refType = new RefType();
        try {
            refType.setId(resultSet.getInt("id"));
            refType.setRefTypeName(resultSet.getString("refTypeName"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return refType;
    }

    /**
     * 删除指定文献类型
     * @param id 文献类型id
     * @return 状态
     */
    public static int delete(int id){
        Connection conn = DbUtil.getConnection();
        String sql = "delete from table_reftype where id=?";
        PreparedStatement pst = null;
        int result = 0;
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            result = pst.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    /**
     * 判断指定类别下是否有文献
     *
     * @param id   文献类别编号
     * @return boolean
     */
    public static boolean existRef(int id){
        Connection conn = DbUtil.getConnection();
        String sql = "select * from ref where refTypeid=?";
        PreparedStatement pst = null;
        boolean state = false;
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            state = rs.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return state;
    }

    /**
     * 获取文献类型名
     * @param id 文献类型id
     * @return  文献类型名
     */
    public static String getRefTypeName(int id){
        String refTypeName = "";
        if (id <= 0) {
            return refTypeName;
        }
        String sql = "select * from table_reftype where id=?";
        Connection conn = DbUtil.getConnection();
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                refTypeName = rs.getString("refTypeName");
            }
        } catch (SQLException throwables) {
            System.out.println("数据库发生错误或数据库连接已关闭");
            throwables.printStackTrace();
        } finally {
            DbUtil.closeConnection(conn);
        }
        return refTypeName;
    }

    /**
     * 根据文献类型名返回id
     * @param refTypeName   数据库类型名
     * @return 类型id
     */
    public static int isRefTypeId(String refTypeName) {
        Connection conn = null;
        String sql = "select * from table_reftype where refTypeName=?";
        int id = 0;
        try {
            conn = DbUtil.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, refTypeName);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                id = rs.getInt("id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                DbUtil.closeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (id==0) {
            JOptionPane.showMessageDialog(null, "文献类型名填写错误");
        }
        return id;
    }

}
