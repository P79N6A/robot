package com.bossbutler.pojo.regional;
import java.io.Serializable;

/**
 * 身份证信息封装bean
 *
 * @author: yeliangliang
 * @data: 2016/10/19.
 */

public class IdCardInfoBean implements Serializable {
    private String name;//姓名
    private String sex;//性别
    private String nation;//民族
    private String birth;//生日
    private String address;//地址
    private String idNo;//身份证号
    private String department;//发证机关
    private String effectDate;//有效日期-开始
    private String expireDate;//有效日期-结束
    private int[] img;//头像
    /**
     * {@link com.bbw.desktop.desktopapplication.function.constant.SwipCardConstant }
     */
    private int resultCode;//结果码

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEffectDate() {
        return effectDate;
    }

    public void setEffectDate(String effectDate) {
        this.effectDate = effectDate;
    }

    public int[] getImg() {
        return img;
    }

    public void setImg(int[] img) {
        this.img = img;
    }
}
