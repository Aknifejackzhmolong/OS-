package com.os;

import java.util.HashMap;
import java.util.Map;

public class FileEntity extends Entity {
    private String text;

    public FileEntity() {
    }

    public FileEntity(Inode inode) {
        this.inode = inode;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
