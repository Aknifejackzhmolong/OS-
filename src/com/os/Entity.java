package com.os;

abstract public class Entity {
    public Inode inode;

    public Inode getInode() {
        return inode;
    }

    public void setInode(Inode inode) {
        this.inode = inode;
    }

}
