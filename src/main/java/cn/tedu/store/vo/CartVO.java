package cn.tedu.store.vo;

import java.io.Serializable;
import java.util.Objects;

public class CartVO implements Serializable {
    private static final long serialVersionUID = 6386309442974137447L;
    private Integer uid;
    private Integer pid;
    private Integer cid;
    private String title;
    private String image;
    private Long price;
    private Long realPrice;
    private Integer num;

    // SET/GET/基于cid的hashCode()与equals()/toString()

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(Long realPrice) {
        this.realPrice = realPrice;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartVO cartVO = (CartVO) o;
        return Objects.equals(cid, cartVO.cid);
    }

    @Override
    public int hashCode() {

        return Objects.hash(cid);
    }

    @Override
    public String toString() {
        return "CartVO{" +
                "uid=" + uid +
                ", pid=" + pid +
                ", cid=" + cid +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", price=" + price +
                ", realPrice=" + realPrice +
                ", num=" + num +
                '}';
    }
}
