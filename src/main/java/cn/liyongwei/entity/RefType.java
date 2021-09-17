package cn.liyongwei.entity;

import cn.liyongwei.dao.RefTypeDao;

import java.io.Serial;
import java.io.Serializable;

public class RefType implements Serializable {
    @Serial
    private static final long serialVersionUID = -26707011922599825L;

    private int id;
    private String refTypeName;

    public RefType() {}

    public RefType(int id) {
        String refTypeName = RefTypeDao.getRefTypeName(id);
        this.id = id;
        this.refTypeName = refTypeName;
    }

    public RefType(int id, String refTypeName) {
        this.id = id;
        this.refTypeName = refTypeName;
    }

    @Override
    public String toString() {
        return refTypeName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRefTypeName() {
        return refTypeName;
    }

    public void setRefTypeName(String refTypeName) {
        this.refTypeName = refTypeName;
    }
}
