package cn.liyongwei.entity;

import cn.liyongwei.dao.RefTypeDao;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Ref implements Serializable {
    //序列化ID
    @Serial
    private static final long serialVersionUID = 920269858498853741L;
    //文献属性
    private int id;
    private String refName;
    private String refAuthor;
    private int refTypeId;
    private String refDate = "1900-01-01";
    private String refPath;
    private String refDesc;
    private String refPublisher;
    private String refType;
    //构造函数
    public Ref() {}

    public Ref(String refName, String refPath) {
        this.refName = refName;
        this.refPath = refPath;
        this.refDate = "1900-01-01";
        this.refTypeId = 1;
    }

    public Ref(String refName, Integer refTypeId) {
        this.refName = refName;
        this.refTypeId = refTypeId;
    }

    //Getter Setter方法
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRefName() {
        return refName;
    }

    public void setRefName(String refName) {
        this.refName = refName;
    }

    public String getRefAuthor() {
        return refAuthor;
    }

    public void setRefAuthor(String refAuthor) {
        this.refAuthor = refAuthor;
    }

    public int getRefTypeId() {
        return refTypeId;
    }

    public void setRefTypeId(int refTypeId) {
        this.refTypeId = refTypeId;
    }

    public String getRefDate() {
        return refDate;
    }

    public void setRefDate(String refDate) {
        this.refDate = refDate;
    }

    public String getRefDesc() {
        return refDesc;
    }

    public void setRefDesc(String refDesc) {
        this.refDesc = refDesc;
    }

    public String getRefPath() {
        return refPath;
    }

    public void setRefPath(String refPath) {
        this.refPath = refPath;
    }

    public String getRefPublisher() {
        return refPublisher;
    }

    public void setRefPublisher(String refPublisher) {
        this.refPublisher = refPublisher;
    }

    public String getRefType() {
        //判断是否已有值
        if (refType != null) {
            return refType;
        }
        //判断文献是否设置类型
        if (refTypeId == 0) {
            this.refType = "";
        } else {
            this.refType = RefTypeDao.getRefTypeName(this.refTypeId);
        }
        return refType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ref ref = (Ref) o;
        return refTypeId == ref.refTypeId && Objects.equals(refName, ref.refName) && Objects.equals(refAuthor, ref.refAuthor) && Objects.equals(refDate, ref.refDate) && Objects.equals(refPath, ref.refPath) && Objects.equals(refDesc, ref.refDesc) && Objects.equals(refPublisher, ref.refPublisher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(refName, refAuthor, refTypeId, refDate, refPath, refDesc, refPublisher);
    }
}
