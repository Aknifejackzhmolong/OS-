package com.os;

import java.util.List;
import java.util.Map;

public class Item {
    private String name = null;
    private boolean isIndex = false;
    private int begin = 0;
    private int end = 0;
    private Map<String,Item> list = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIndex() {
        return isIndex;
    }

    public void setIndex(boolean index) {
        isIndex = index;
    }

    public int getBegin() {
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public Map<String, Item> getList() {
        return list;
    }

    public void setList(Map<String, Item> list) {
        this.list = list;
    }
}
