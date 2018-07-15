package com.os;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Inode {
    private String type = null;        //类型
    private String name = null;        //名称
    private String UID = "root";       //属主名
    private String url = null;         //路径
    private long size = 0;             //大小
    private long usedSpace = 0;       //占用空间
    private String createTime = null; //创建时间
    private String editTime = null;   //修改时间
    private String acessTime = null;  //访问时间
    private int element = 1;           //属性,可读&2=0，可写%3=0，隐藏%5=0,可见%5!=0

    public Inode() {
    }

    public Inode(String type, String name, int element) {
        this.type = type;
        this.name = name;
        this.element = element;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        this.createTime = df.format(new Date());// new Date()为获取当前系统时间
        Access();Modify();
    }

    public void Access(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        this.acessTime = df.format(new Date());// new Date()为获取当前系统时间
    }

    public void Modify(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        this.editTime = df.format(new Date());// new Date()为获取当前系统时间
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getUsedSpace() {
        return usedSpace;
    }

    public void setUsedSpace(long usedSpace) {
        this.usedSpace = usedSpace;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEditTime() {
        return editTime;
    }

    public void setEditTime(String editTime) {
        this.editTime = editTime;
    }

    public String getAcessTime() {
        return acessTime;
    }

    public void setAcessTime(String acessTime) {
        this.acessTime = acessTime;
    }

    public int getElement() {
        return element;
    }

    public void setElement(int element) {
        this.element = element;
    }
}
