package com.os;

import jdk.nashorn.internal.parser.JSONParser;
import net.sf.json.JSON;
import net.sf.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws Exception{
        RandomAccessFile f = new RandomAccessFile("D:\\Users\\曾\\Workspaces\\MyEclipse 2017 CI\\FileOS\\disk","rw");
        for (int i = 0;i < 1024; i++) {
            f.write(new byte[4096]);
            f.writeChar('\n');
        }
        String uid = "root";
        SystemOperate so = new SystemOperate(uid);
        so.temp();
        f.close();
        RandomAccessFile raf = new RandomAccessFile("D:\\Users\\曾\\Workspaces\\MyEclipse 2017 CI\\FileOS\\basic.conf","rw");
        List<String> set = new ArrayList<>();
        set.add(uid);
        set.add("Aknife");
        set.add("Tian");
        set.add("Yi");
        BasicConf bc = new BasicConf();
        bc.setUid(set);
        raf.write(JSONObject.fromObject(bc).toString().getBytes());
        raf.close();
//        so.loadFAT();
//        Item root = so.getRoot();
//        JSONObject index = JSONObject.fromObject(root.getList().get("etc").getList().get("test.txt").getList());
//        System.out.println(index.toString());
//        JSONObject root = JSONObject.fromObject(so.getRoot());
//        System.out.println(root.toString());
//        JSONObject temp1 = root.getJSONObject("list");
//        System.out.println(temp1.toString());
//        JSONObject temp2 = temp1.getJSONObject("etc");
//        System.out.println(temp2.toString());
//
//
//        JSONObject temp3 = temp2.getJSONObject("list");
//        System.out.println(temp3.toString());
//        JSONObject temp4 = temp3.getJSONObject("test.txt");
//        System.out.println(temp4.toString());
//
//        Object index = temp4.get("list");
//        System.out.println(index==null);

//        FileEntity fe = new FileEntity();
//        fe.setText("这是一个测试文件");
//        fe.setInode(inode);
////        so.writeFile(fe,1);
////        so.writeFile(fe,11);
////        so.writeFile(fe,21);
////        so.writeFile(fe,31);
//        FileEntity f = (FileEntity) so.readFile(2,3,"file");
//
//        System.out.println(f.getInode().getType()+f.getInode().getName()+f.getInode().getCreateTime()+f.getText());
    }
}
