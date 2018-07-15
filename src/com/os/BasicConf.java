package com.os;

import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

public class BasicConf {
    private List<String> uid;
    private String diskSpace = "4MB";

    public boolean vaildUID(String UID){
        return uid.contains(UID);
    }

    public static BasicConf basicConf;
    public static void loadBasicConf(){
        try {
            File file = new File("D:\\Users\\曾\\Workspaces\\MyEclipse 2017 CI\\FileOS\\basic.conf");
            FileInputStream in = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            JSONObject json = JSONObject.fromObject(br.readLine().trim());
            basicConf = (BasicConf)JSONObject.toBean(json, BasicConf.class);
            in.close();
            br.close();
        }catch (Exception e){ }
    }

    public String getDiskSpace() {
        return diskSpace;
    }

    public void setDiskSpace(String diskSpace) {
        this.diskSpace = diskSpace;
    }

    public List<String> getUid() {
        return uid;
    }

    public void setUid(List<String> uid) {
        this.uid = uid;
    }
}
