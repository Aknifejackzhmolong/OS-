package com.os;


import java.util.ArrayList;

public class IndexEntity extends Entity {
    private ArrayList<Entity> list = null;

    public IndexEntity() {
    }

    public IndexEntity(Inode inode) {
        this.inode = inode;
    }

    public ArrayList<Entity> getList() {
        return list;
    }

    public void setList(ArrayList<Entity> list) {
        this.list = list;
    }
}
