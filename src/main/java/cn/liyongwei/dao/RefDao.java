package cn.liyongwei.dao;

import cn.liyongwei.entity.Ref;
import cn.liyongwei.util.DbUtil;
import cn.liyongwei.util.StringUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class RefDao {
    /**
     * 添加文献
     * @param ref 文献实体
     * @return  状态id
     */
    public static int add(Ref ref){
        Connection connection = DbUtil.getConnection();;
        String sql = "insert into ref values(null,?,?,?,?,?,?,?)";
        int result = 0;
        try {
            PreparedStatement psmt = connection.prepareStatement(sql);
            psmt.setString(1, ref.getRefName());
            psmt.setString(2, ref.getRefAuthor());
            psmt.setInt(3, ref.getRefTypeId());
            psmt.setString(4, ref.getRefDate());
            psmt.setString(5, ref.getRefPath());
            psmt.setString(6, ref.getRefDesc());
            psmt.setString(7, ref.getRefPublisher());
            result = psmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.closeConnection(connection);
        }
        return result;
    }

    public static int add(List<Ref> refs){
        Connection connection = DbUtil.getConnection();;
        String sql = "insert into ref values(null,?,?,?,?,?,?,?)";
        int result = 0;
        for(Ref ref:refs) {
            try {
                PreparedStatement psmt = connection.prepareStatement(sql);
                psmt.setString(1, ref.getRefName());
                psmt.setString(2, ref.getRefAuthor());
                psmt.setInt(3, ref.getRefTypeId());
                psmt.setString(4, ref.getRefDate());
                psmt.setString(5, ref.getRefPath());
                psmt.setString(6, ref.getRefDesc());
                psmt.setString(7, ref.getRefPublisher());
                if (psmt.executeUpdate() <=0) {
                    result--;
                };
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        DbUtil.closeConnection(connection);
        return result;
    }

    /**
     * 修改某一指定文献
     * @param ref 文献实体
     * @return 状态id
     */
    public static int update(Ref ref) {
        Connection connection = null;
        String sql = "update ref set refName=?, refAuthor=?, refTypeId=?, refDate=?, refPath=?, refDesc=?, refPublisher=? where id=?";
        int result = 0;
        try {
            connection = DbUtil.getConnection();
            PreparedStatement psmt = connection.prepareStatement(sql);
            psmt.setString(1, ref.getRefName());
            psmt.setString(2, ref.getRefAuthor());
            psmt.setInt(3, ref.getRefTypeId());
            psmt.setString(4, ref.getRefDate());
            psmt.setString(5, ref.getRefPath());
            psmt.setString(6, ref.getRefDesc());
            psmt.setString(7, ref.getRefPublisher());
            psmt.setInt(8, ref.getId());
            result = psmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtil.closeConnection(connection);
        }
        return result;
    }

    /**
     * 查找指定文献
     * @param id 文献id
     * @return  文献实体
     */
    public static Ref search(int id){
        Ref rf = new Ref();
        if (id <= 0) {return rf;}
        String sql = "select * from ref r where r.id=?";
        Connection conn = DbUtil.getConnection();
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                rf.setId(rs.getInt("id"));
                rf.setRefName(rs.getString("refName"));
                rf.setRefAuthor(rs.getString("refAuthor"));
                rf.setRefTypeId(rs.getInt("refTypeId"));
                rf.setRefDate(rs.getString("refDate"));
                rf.setRefPath(rs.getString("refPath"));
                rf.setRefDesc(rs.getString("refDesc"));
                rf.setRefPublisher(rs.getString("refPublisher"));
            }
        } catch (SQLException e) {
            System.out.println("数据库发生错误或数据库连接已关闭");
            e.printStackTrace();
        } finally {
            DbUtil.closeConnection(conn);
        }
        return rf;
    }

    /**
     * 列出某一类型的文献
     *
     * @param ref 文献实体
     * @return java.sql.ResultSet
     * @since 2020/4/12
     */
    public static Ref[] list(Ref ref) {
        //获取连接
        Connection conn = DbUtil.getConnection();
        //准备数据库搜索命令
        StringBuilder sb = new StringBuilder("select * from ref r,table_reftype rt where r.refTypeId=rt.id");
        if (!StringUtil.isEmpty(ref.getRefName())) {
            sb.append(" and (r.refName like '%").append(ref.getRefName()).append("%'");
            sb.append(" or r.refDesc like '%").append(ref.getRefName()).append("%'").append(")");
        }
        if (ref.getRefTypeId() > 0) {
            sb.append(" and r.refTypeId=").append(ref.getRefTypeId());
        }
        //搜索数据库
        ResultSet resultSet = null;
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sb.toString());
            resultSet = pst.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //处理搜索结果
        if (resultSet == null) {
            return new Ref[0];
        }
        List<Ref> refList = new LinkedList<>();
        Ref currentRef;
        while (true) {
            try {
                if (!resultSet.next()) break;
                currentRef = convertResultSet(resultSet);
                refList.add(currentRef);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        Ref[] refs = new Ref[refList.size()];
        int i = 0;
        for(Ref r : refList) {
            refs[i++] = r;
        }
        //关闭数据库连接
        DbUtil.closeConnection(conn);
        return refs;
    }

    private static Ref convertResultSet(ResultSet resultSet) {
        Ref ref = new Ref();
        try {
            ref.setId(resultSet.getInt("id"));
            ref.setRefName(resultSet.getString("refName"));
            ref.setRefAuthor(resultSet.getString("refAuthor"));
            ref.setRefTypeId(resultSet.getInt("refTypeId"));
            ref.setRefDate(resultSet.getString("refDate"));
            ref.setRefPath(resultSet.getString("refPath"));
            ref.setRefDesc(resultSet.getString("refDesc"));
            ref.setRefPublisher(resultSet.getString("refPublisher"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ref;

    }

    /**
     * 删除文献信息
     *
     * @param id   文献编号
     * @return int
     * @since 2020/4/12
     */
    public static int delete(int id){
        Connection connection = DbUtil.getConnection();
        String sql = "delete from ref where id=?";
        PreparedStatement pst = null;
        int result = 0;
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            result = pst.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DbUtil.closeConnection(connection);
        }
        return result;
    }
}
