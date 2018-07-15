package com.os;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.io.*;
import java.lang.reflect.Array;
import java.rmi.server.UID;
import java.util.*;

public class SystemOperate {
    private String uid;
    private Item root = new Item();
    private Item relativePathroot = new Item();
    private FileAccessTableInfo FATINFO;
    private int DiskSpace;
    public static ArrayList<Integer> diskList = new ArrayList<>();

    public SystemOperate(String uid){
        this.uid = uid;
    }
    public void temp()throws Exception{
        FATINFO = new FileAccessTableInfo();
        FATINFO.setBegin(1);
        FATINFO.setEnd(1);
        FATINFO.setOperateSystm("UNIX");
        Map<String,Object> mmmap = new HashMap<>();
        Map<String,Item> etc = new HashMap<>();

        Item etcItem = new Item();
        IndexEntity ie = createIndex("Index","etc",6);
        etcItem.setBegin(2);
        etcItem.setEnd(2);
        etcItem.setName("etc");
        etcItem.setIndex(true);
        Item test = new Item();
        test.setBegin(5);
        test.setEnd(5);
        test.setName("test.txt");
        FileEntity fe = createFile("text","test.txt",6);
        updateFile("这是一个测试文件",fe);
        writeFile(fe,5);
        Map<String,Item> list0 = new HashMap<>();
        list0.put(test.getName(),test);
        etcItem.setList(list0);
        updateIndex(etcItem, ie);
        writeFile(ie,2);

        Item usrItem = new Item();
        ie = createIndex("Index","usr",5);
        writeFile(ie,3);
        usrItem.setName("usr");
        usrItem.setIndex(true);
        usrItem.setBegin(3);
        usrItem.setEnd(3);

        Item devItem = new Item();
        ie = createIndex("Index","dev",2);
        writeFile(ie,4);
        devItem.setName("dev");
        devItem.setIndex(true);
        devItem.setBegin(4);
        devItem.setEnd(4);
//        IndexItem rootItem = new IndexItem();
        Map<String,Item> list = new HashMap<>();
        list.put("etc",etcItem);
        list.put("usr",usrItem);
        list.put("dev",devItem);
        root.setList(list);
//        root = new JSONObject();
//        json.put("/",rootItem);
        saveFAT();
        RandomAccessFile raf = new RandomAccessFile("D:\\Users\\曾\\Workspaces\\MyEclipse 2017 CI\\FileOS\\disk","rw");
        Item aknife = new Item();
        FATINFO = new FileAccessTableInfo();
        FATINFO.setBegin(1);
        FATINFO.setEnd(1);
        FATINFO.setOperateSystm("UNIX");
        mmmap.put("FileAccessTable",JSONObject.fromObject(FATINFO));
        System.out.println(mmmap);
        raf.seek(0);
        byte[] bytes = mmmap.toString().getBytes();
        raf.write(bytes);
        int n = 4096 - bytes.length;
        byte[] placehold = new byte[n];
        raf.write(placehold);
        raf.close();
    }
    public void init()throws Exception{
        File file = new File("D:\\Users\\曾\\Workspaces\\MyEclipse 2017 CI\\FileOS\\basic.conf");
        FileInputStream in = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String content = br.readLine();
        JSONObject item = JSONObject.fromObject(content);
        in.close();
        br.close();
    }

    public void loadFAT()throws Exception{
        String content = "";
        String str;
        Entity f = null;
        try {
            File file = new File("D:\\Users\\曾\\Workspaces\\MyEclipse 2017 CI\\FileOS\\disk");
            FileInputStream in = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            JSONObject fat = JSONObject.fromObject(br.readLine().trim());
            fat = (JSONObject) fat.get("FileAccessTable");
            FATINFO = (FileAccessTableInfo) JSONObject.toBean(fat, FileAccessTableInfo.class);
            int i = 1, begin = FATINFO.getBegin(), end = FATINFO.getEnd();
            while ((str = br.readLine())!=null){
                if(i > end) break;
                if(i >= begin)
                    content = content + str.trim();
                i++;
            }
            in.close();
            br.close();
        }catch (Exception e){ }
        JSONObject fat = JSONObject.fromObject(content);
        root = (Item) JSONObject.toBean(fat, Item.class);
        root.setIndex(true);
        root.setName("/");
    }
    public void bfs(DefaultTreeModel m_model, DefaultMutableTreeNode oldNode) {
        Queue<Item> q=new LinkedList<>();
        Queue<DefaultMutableTreeNode> nq=new LinkedList<>();
        q.add(root);//将root作为起始顶点加入队列
        nq.add(oldNode);
        while(!q.isEmpty())
        {
            Item top = q.poll();//取出队首元素
            oldNode = nq.poll();
            if(top.getList() == null) continue;
            for (int i = top.getBegin();i <= top.getEnd();i++) Y.cp[i] = 1;
            reset(top);
            Map<String,Item> map = top.getList();
            for (String in : map.keySet()) {
                Item temp = map.get(in);
                DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(
                        temp.getName());

                //在选中位置插入新节点
                m_model.insertNodeInto(newNode, oldNode, oldNode
                        .getChildCount());
                q.add(temp);
                nq.add(newNode);
                int begin = temp.getBegin();
                int end = temp.getEnd();
                diskList.add(begin);
                diskList.add(end);
                for (int i = begin;i <= end;i++) Y.cp[i] = 2;
            }
        }
        int begin = FATINFO.getBegin();
        int end = FATINFO.getEnd();
        diskList.add(begin);
        diskList.add(end);
        for (int i = begin;i <= end;i++) Y.cp[i] = 1;
        Y.cp[0] = 1;
        diskList.add(0);
        diskList.add(0);
        diskList.add(0);
        diskList.add(1023);
    }
    private void reset(Item item){
        Map<String,Item> map = item.getList();
        Map<String,Item> list = new HashMap<>();
        for (String in : map.keySet()) {
            JSONObject json = JSONObject.fromObject(map.get(in));
            Item temp = (Item)JSONObject.toBean(json, Item.class);
            list.put(in,temp);
        }
        item.setList(list);
    }
    public void saveFAT()throws Exception{
        RandomAccessFile raf = new RandomAccessFile("D:\\Users\\曾\\Workspaces\\MyEclipse 2017 CI\\FileOS\\disk","rw");
        JSONObject rootJSON = JSONObject.fromObject(root);
        byte[] bytes = rootJSON.toString().getBytes();
        long l = bytes.length;
        int x,y;
        x = (int) l / 4096;
        y = (int) l % 4096;
        for(int i = FATINFO.getBegin();i > 0;i--) raf.readLine();
        for(int i = 0;i < x;i++) {
            raf.write(bytes, i * 4096, 4096);
            raf.readLine();
        }
        raf.write(bytes,x * 4096,y);
        int n = 4096 - y;
        byte[] placehold = new byte[n];
        raf.write(placehold);
        raf.close();
    }
    public void getBlockDisk(Item item, long size){
        int begin = item.getBegin(), end = item.getEnd();
        int x = (int) size / 4096;
        if(x <= end - begin) {
            for (int i = item.getEnd();i > begin + x;i--) Y.cp[i]=0;
            diskList.remove(diskList.indexOf(end));
            item.setEnd(begin + x);
            diskList.add(begin + x);
        }
        else {
            diskList.remove(diskList.indexOf(begin));
            diskList.remove(diskList.indexOf(end));
            for (int i = begin; i <= end; i++) Y.cp[i] = 0;
            x++;
            final int s = diskList.size();
            Integer[] a = (Integer[]) diskList.toArray(new Integer[s]);
            Arrays.sort(a);
            int n = a.length / 2;
            for (int i = 0; i < n; i++) {
                if (a[2 * i + 1] - a[2 * i] > x) {
                    begin = a[2 * i] + 1;
                    end = a[2 * i] + x;
                    item.setBegin(begin);
                    item.setEnd(end);
                    break;
                }
            }
            diskList.add(begin);
            diskList.add(end);
            for (int i = begin; i <= end; i++) Y.cp[i] = 2;
            Y.cp[0] = 2;
        }
    }
    public FileEntity createFile(String type, String name, int element){
        Inode inode = new Inode(type,name,element);
        inode.setUID(uid);
        FileEntity entity = new FileEntity(inode);
        return entity;
    }
    public IndexEntity createIndex(String type, String name, int element){
        Inode inode = new Inode(type,name,element);
        IndexEntity entity = new IndexEntity(inode);
        inode.setUID(uid);
        return entity;
    }
    public Entity readFile(int begin, int end, String type){
        String content = "";
        String str;
        Entity f = null;
        int i = 0;
        try {
            File file = new File("D:\\Users\\曾\\Workspaces\\MyEclipse 2017 CI\\FileOS\\disk");
            FileInputStream in = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            while ((str = br.readLine())!=null){
                if(i > end) break;
                if(i >= begin)
                content = content + str.trim();
                i++;
            }
            in.close();
            br.close();
        }catch (Exception e){
        }
        JSONObject json = JSONObject.fromObject(content);
        if(type.equals("Index")) f = (Entity) JSONObject.toBean(json,IndexEntity.class);
        else f = (Entity) JSONObject.toBean(json,FileEntity.class);
        return f;
    }
    public void writeFile(Entity e,int n)throws Exception{
        JSONObject json = JSONObject.fromObject(e);
        RandomAccessFile f = new RandomAccessFile("D:\\Users\\曾\\Workspaces\\MyEclipse 2017 CI\\FileOS\\disk","rw");
        byte[] bytes = json.toString().getBytes();
        long l = bytes.length;
        int x,y;
        x = (int) l / 4096;
        y = (int) l % 4096;
        for(int i = 0;i < n;i++) {
            f.readLine();
        }
        for(int i = 0;i < x;i++) {
            f.write(bytes, i * 4096, 4096);
            f.readLine();
        }
        f.write(bytes,x * 4096,y);
        int m = 4096 - y;
        byte[] placehold = new byte[m];
        f.write(placehold);
        f.close();
    }
    public void updateFile(String content, FileEntity entity){
        entity.setText(content);
        entity.getInode().setSize(content.getBytes().length);
        JSONObject json = JSONObject.fromObject(entity);
        entity.getInode().setUsedSpace(json.toString().getBytes().length);
    }
    public void updateIndex(Item item, IndexEntity entity){
        long size = 0;
        long UserSpace = 0;
        Map<String, Item> map = item.getList();
        Entity temp;
        for (String in : map.keySet()) {
            if(map.get(in).isIndex()) temp = readFile(map.get(in).getBegin(),map.get(in).getEnd(),"Index");
            else temp = readFile(map.get(in).getBegin(),map.get(in).getEnd(),"file");
            size = size + temp.getInode().getSize();
            UserSpace = UserSpace + temp.getInode().getUsedSpace();
        }
        entity.getInode().setSize(size);
        entity.getInode().setUsedSpace(UserSpace);
    }
    public void deleteFile(){}
    public void deleteIndex(){}

    public Item getRoot() {
        return root;
    }
}