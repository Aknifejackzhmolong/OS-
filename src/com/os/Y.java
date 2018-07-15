package com.os;

import net.sf.json.JSONObject;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class Y extends JFrame implements ActionListener, KeyListener{
    private String uid;
    private SystemOperate so;
    private static String rootName = "我的电脑";
    private FileEntity openFile;
    private Item openPath;
    private String tempName;
    int count1=1;
    static int count2=1;
    int empty=1024;//磁盘剩余空间
    int now = 1,p = 0;
    static int []cp = new int[1024];
    //static File1 [] f = new File1[1000];
    public static int[] getArray(){
        return cp;
    }

    static DefaultMutableTreeNode m_rootNode = new DefaultMutableTreeNode(rootName);  //根目录
    static DefaultTreeModel m_model = new DefaultTreeModel(m_rootNode);   //树结构
    static JTree tree = new JTree(m_model);
    TextAreaMenu textArea = new TextAreaMenu();
    JScrollPane jsp = new JScrollPane(textArea);
    JButton button = new JButton("加/解密");
    JButton button_1 = new JButton("保存");
    JButton button_2 = new JButton("修改属性");
    JButton button_3 = new JButton("查询");
    JButton button_4 = new JButton("新建文件");
    JButton button_5 = new JButton("新建文件夹");
    JTextArea textArea_1 = new JTextArea();


    private JPanel contentPane;
    private JTextField textField;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Y frame = new Y();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */


    public Y(){
        uid=JOptionPane.showInputDialog("输入用户名");
        BasicConf.loadBasicConf();
        if(BasicConf.basicConf.vaildUID(uid)) so = new SystemOperate(uid);
        else System.exit(0);
        initNode();
        // 界面设置
        this.setTitle("文件管理系统");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 733, 457);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);


        tree.setBounds(33, 68, 172, 192);
        contentPane.add(tree);
//        m_model.addTreeModelListener(new TreeModelListener(){
//            public void treeNodesChanged(TreeModelEvent e){
//                TreePath treePath = e.getTreePath();
//                System.out.println(treePath);
//                //下面這行由TreeModelEvent取得的DefaultMutableTreeNode為節點的父節點，而不是用戶點選
//                //的節點，這點讀者要特別注意。要取得真正的節點需要再加寫下面6行代碼.
//                DefaultMutableTreeNode node = (DefaultMutableTreeNode) treePath.getLastPathComponent();
//                try {
//                    //getChildIndices()方法會返回目前修改節點的索引值。由於我們只修改一個節點，因此節點索引值就放在index[0]
//                    //的位置，若點選的節點為root node,則getChildIndices()的返回值為null,程式下面的第二行就在處理點選root
//                    //node產生的NullPointerException問題.
//                    int[] index = e.getChildIndices();
//                    //由DefaultMutableTreeNode類的getChildAt()方法取得修改的節點對象.
//                    node = (DefaultMutableTreeNode) node.getChildAt(index[0]);
//
//                    Stack<String> stack = new Stack<>();
//                    String parentName = node.toString();
//                    TreeNode selnode = node;
//                    while(stack.size() > 1) {
//                        stack.push(parentName);
//                        selnode = selnode.getParent();
//                        parentName = selnode.toString();
//                    }
//                    Item path = so.getRoot();
//                    while (!stack.isEmpty()) {
//                        path = path.getList().get(stack.pop());
//                    }
//                    Item child = path.getList().get(stack.pop());
//
//                } catch (Exception exc) {
//                    System.out.println(exc.getMessage());
//                }
//            }
//            public void treeNodesInserted(TreeModelEvent e) {
//            }
//
//            public void treeNodesRemoved(TreeModelEvent e) {
//            }
//
//            public void treeStructureChanged(TreeModelEvent e) {
//            }
//        });


//        textArea.setBounds(220, 67, 341, 193);
        jsp.setBounds(220, 67, 341, 193);
        contentPane.add(jsp);


        button.setBounds(576, 68, 109, 30);
        contentPane.add(button);

        button_1.setBounds(576, 113, 109, 30);
        contentPane.add(button_1);

        button_2.setBounds(576, 158, 109, 30);
        contentPane.add(button_2);

        textField = new JTextField();
        textField.setBounds(33, 15, 269, 38);
        contentPane.add(textField);
        textField.setColumns(10);

        button_3.setBounds(306, 14, 112, 38);
        contentPane.add(button_3);

        button_4.setBounds(430, 14, 112, 38);
        contentPane.add(button_4);

        button_5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            }
        });
        button_5.setBounds(557, 14, 128, 38);
        contentPane.add(button_5);

        textArea_1.setBounds(33, 275, 652, 80);
        contentPane.add(textArea_1);

        JButton button_6 = new JButton("删除");
        button_6.setBounds(576, 203, 109, 30);
        contentPane.add(button_6);



        JMenuBar mBar = new JMenuBar();
        JMenu Menu1 = new JMenu("文件");
        JMenu Menu2 = new JMenu("编辑");
        JMenu Menu3 = new JMenu("查看");

        //添加一级菜单
        mBar.add(Menu1);
        mBar.add(Menu2);
        mBar.add(Menu3);


        //添加二级菜单
        JMenuItem m11=new JMenuItem("打开");
        JMenuItem m12=new JMenuItem("退出");

        JMenuItem m21=new JMenuItem("编辑/锁定");
        JMenuItem m22=new JMenuItem("刷新");


        JMenuItem m31=new JMenuItem("属性");
        JMenuItem m32 = new JMenuItem("磁盘占用");

        Menu1.add(m11);
        Menu1.add(m12);

        Menu2.add(m21);
        Menu2.add(m22);

        Menu3.add(m31);
        Menu3.add(m32);


        mBar.setBounds(29, 15, 81, 21);
        setJMenuBar(mBar);


        m11.addActionListener(this);
        m12.addActionListener(this);

        m21.addActionListener(this);
        m22.addActionListener(this);

        m31.addActionListener(this);
        m32.addActionListener(this);


        tree.addMouseListener(mouseListener);
        tree.setEditable(true);
        tree.setSelectionRow(0);
        tree.addKeyListener(this);
        //
        //加解密

        button.addActionListener(new ActionListener()
                                 {   public void actionPerformed(ActionEvent e) {
                                     char[] c = textArea.getText().toCharArray();
                                     for(int i=0;i<c.length;i++){
                                         c[i] = (char)(c[i]^10000);
                                     }
                                     StringBuffer sb = new StringBuffer();
                                     for(int i = 0; i < c.length; i++){
                                         sb. append(c[i]);
                                     }
                                     String s = sb.toString();
                                     textArea.setText(s);

                                 }
                                 }
        );

        //保存
        button_1.addActionListener(new ActionListener()
                                   {   public void actionPerformed(ActionEvent e) {

                                       if(openFile==null) {
                                           JOptionPane.showMessageDialog(null,"未打开文件");
                                           return;
                                       }
                                       else so.updateFile(textArea.getText(),openFile);
                                       try {
                                           so.getBlockDisk(openPath, openFile.getInode().getUsedSpace());
                                           so.writeFile(openFile, openPath.getBegin());
                                           openFile.getInode().Modify();
                                           so.saveFAT();
                                           JOptionPane.showMessageDialog(null,"保存成功");
                                       }catch (Exception ex){
                                           System.out.println(ex.getMessage());
                                       }
                                   }}
        );
        //属性更改
        button_2.addActionListener(new ActionListener()
                                   {
                                       public void actionPerformed(ActionEvent e) {

                                           DefaultMutableTreeNode selNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                                           Stack<String> stack = new Stack<>();
                                           String parentName = selNode.toString();
                                           if (parentName == rootName) return;
                                           TreeNode selnode = selNode;
                                           while(parentName != rootName) {
                                               stack.push(parentName);
                                               selnode = selnode.getParent();
                                               parentName = selnode.toString();
                                           }
                                           Item path = so.getRoot();
                                           while(!stack.isEmpty()) {
                                               path = path.getList().get(stack.pop());
                                           }
                                           try {
                                               Entity entity;
                                               if(path.isIndex()) entity = so.readFile(path.getBegin(), path.getEnd(), "Index");
                                               else entity = so.readFile(path.getBegin(), path.getEnd(), "file");
                                               entity.getInode().Modify();
                                               if (entity.getInode().getUID().equals(uid)) new shux(entity, path);

                                           }catch (Exception ex){
                                               System.out.println(ex.getMessage());
                                               JOptionPane.showMessageDialog(null, "非法操作");
                                           }


                                       }

                                   }
        );

        //创建文件
        button_4.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                DefaultMutableTreeNode selNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                Stack<String> stack = new Stack<>();
                String parentName = selNode.toString();
                TreeNode selnode = selNode;
                while(parentName != rootName) {
                    stack.push(parentName);
                    selnode = selnode.getParent();
                    parentName = selnode.toString();
                }
                Item path = so.getRoot();
                while(!stack.isEmpty()) {
                    path = path.getList().get(stack.pop());
                }
                if (path.isIndex()) {

                    int i = 1;
                    String str = "NewFile " + i;
                    while (path.getList() != null && (path.getList().keySet().contains(str) || path.getList().keySet().contains(str + ".txt"))) {
                        i++;
                        str = "NewFile " + i;
                    }
                    str = str + ".txt";
                    DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(
                            str);
                    m_model.insertNodeInto(newNode, selNode, selNode
                            .getChildCount());

                    TreeNode[] nodes = m_model.getPathToRoot(newNode);
                    TreePath treePathpath = new TreePath(nodes);
                    tree.scrollPathToVisible(treePathpath);
                    tree.setSelectionPath(treePathpath);
                    tree.startEditingAtPath(treePathpath);


                    FileEntity entity = so.createFile("text", str, 6);
                    Item item = new Item();
                    for (i = 0; i < 1024; i++) if (cp[i] == 0) break;
                    item.setBegin(i);
                    item.setEnd(i);
                    SystemOperate.diskList.add(i);
                    SystemOperate.diskList.add(i);
                    cp[i] = 2;
                    item.setName(str);
                    if (path.getList() == null) {
                        Map<String, Item> list = new HashMap<>();
                        list.put(str, item);
                        path.setList(list);
                    } else path.getList().put(str, item);
                    try {
                        so.saveFAT();
                        so.writeFile(entity, i);
                    } catch (Exception ex) {
                    }
                }
            }
        });

        //新建文件夹
        button_5.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                //获取选中节点
                DefaultMutableTreeNode selNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                Stack<String> stack = new Stack<>();
                String parentName = selNode.toString();
                TreeNode selnode = selNode;
                while (parentName != rootName) {
                    stack.push(parentName);
                    selnode = selnode.getParent();
                    parentName = selnode.toString();
                }
                Item path = so.getRoot();
                while (!stack.isEmpty()) {
                    path = path.getList().get(stack.pop());
                }
                if (path.isIndex()) {
                    int i = 1;
                    String str = "NewFile " + i;
                    while (path.getList() != null && (path.getList().keySet().contains(str) || path.getList().keySet().contains(str + ".txt"))) {
                        i++;
                        str = "NewFile " + i;
                    }
                    DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(
                            str);
                    m_model.insertNodeInto(newNode, selNode, selNode
                            .getChildCount());

                    TreeNode[] nodes = m_model.getPathToRoot(newNode);
                    TreePath treePathpath = new TreePath(nodes);
                    tree.scrollPathToVisible(treePathpath);
                    tree.setSelectionPath(treePathpath);
                    tree.startEditingAtPath(treePathpath);


                    IndexEntity entity = so.createIndex("Index", str, 6);
                    Item item = new Item();
                    for (i = 0; i < 1024; i++) if (cp[i] == 0) break;
                    item.setBegin(i);
                    item.setEnd(i);
                    item.setIndex(true);
                    cp[i] = 2;
                    item.setName(str);
                    if (path.getList() == null) {
                        Map<String, Item> list = new HashMap<>();
                        list.put(str, item);
                        path.setList(list);
                    } else path.getList().put(str, item);
                    try {
                        so.saveFAT();
                        so.writeFile(entity, i);
                    } catch (Exception ex) {
                    }
                }
            }
        });

        //查询
        button_3.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                DefaultMutableTreeNode node = searchNode(textField.getText());
                if (node != null)
                {
                    TreeNode[] nodes = m_model.getPathToRoot(node);
                    TreePath path = new TreePath(nodes);
                    tree.scrollPathToVisible(path);
                    tree.setSelectionPath(path);
                    textArea_1.setText("查询成功！");
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"文件"
                            + textField.getText() + " 未找到");
                }
            }
        });

        //删除
        button_6.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                DefaultMutableTreeNode selNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

                Stack<String> stack = new Stack<>();
                String parentName = selNode.toString();
                TreeNode selnode = selNode;
                while(parentName != rootName) {
                    stack.push(parentName);
                    selnode = selnode.getParent();
                    parentName = selnode.toString();
                }
                Item path = so.getRoot();
                String str;
                while(stack.size() > 1) {
                    path = path.getList().get(stack.pop());
                }
                path.getList().remove(stack.pop());
                try{
                    so.saveFAT();
                }catch (Exception ex){}
                removeNode(selNode);
            }
        });
    }
    //初始化目录
    private void initNode(){
        try{
            so.loadFAT();
            so.bfs(m_model, m_rootNode);
        }catch (Exception e){}
    }
    public static void visitAllNodes(TreeNode node) {
        // node is visited exactly once
        if (node.getChildCount() >= 0) {//判断是否有子节点
            for (Enumeration e=node.children(); e.hasMoreElements(); ) {
                int index = 0;
                DefaultMutableTreeNode n = (DefaultMutableTreeNode)e.nextElement();
                String name=n.toString();
                String parentname=n.getParent().toString();
                visitAllNodes(n);//若有子节点则再次查找
            }
        }
    }
    public DefaultMutableTreeNode searchNode(String nodeStr)
    {
        DefaultMutableTreeNode node = null;
        Enumeration e = m_rootNode.breadthFirstEnumeration();
        while (e.hasMoreElements())
        {
            node = (DefaultMutableTreeNode) e.nextElement();
            if (nodeStr.equals(node.getUserObject().toString()))
            {
                return node;
            }
        }
        return null;
    }
    //删除结点
    public static void removeNode(DefaultMutableTreeNode selNode)
    {
        if (selNode == null)
        {
            return;
        }
        MutableTreeNode parent = (MutableTreeNode) (selNode.getParent());
        if (parent == null)
        {
            return;
        }
        MutableTreeNode toBeSelNode = getSibling(selNode);
        if (toBeSelNode == null)
        {
            toBeSelNode = parent;
        }
        TreeNode[] nodes = m_model.getPathToRoot(toBeSelNode);
        TreePath path = new TreePath(nodes);
        tree.scrollPathToVisible(path);
        tree.setSelectionPath(path);
        m_model.removeNodeFromParent(selNode);
    }

    private static MutableTreeNode getSibling(DefaultMutableTreeNode selNode)
    {
        MutableTreeNode sibling = (MutableTreeNode) selNode
                .getPreviousSibling();
        if (sibling == null)
        {
            sibling = (MutableTreeNode) selNode.getNextSibling();
        }
        return sibling;
    }

    class shux extends JFrame implements ItemListener{
        JCheckBox jb;
        JCheckBox jb1;
        int ele;
        Entity entity;
        Item path;
        private JPanel jpl = new JPanel();
        public shux(Entity entity, Item path) {
            this.setTitle("属性设置");
            this.entity = entity;
            this.path = path;
            ele = entity.getInode().getElement();
            if(ele%2==0) {
                jb=new JCheckBox("可读",true);
            }
            else {
                jb=new JCheckBox("可读",false);
            }
            if(ele%3==0) {
                jb1=new JCheckBox("可写",true);
            }
            else{
                jb1=new JCheckBox("可写",false);
            }
            jpl.add(jb);
            jpl.add(jb1);
            jb.addItemListener(this);
            jb1.addItemListener(this);
            getContentPane().add(jpl);

            this.setSize(400, 100);
            this.setVisible(true);
        }

        public void itemStateChanged(ItemEvent e){
            int i = 1;
            if (jb.isSelected())
                i = i * 2;

            if (jb1.isSelected())
                i = i * 3;

            entity.getInode().setElement(i);
            try {
                so.writeFile(entity, path.getBegin());
                path.setEnd(path.getBegin() + (int) entity.getInode().getUsedSpace() / 4096);
            }catch (Exception ex){}
        }


    }


    MouseListener mouseListener = new MouseAdapter() {
        public void mouseClicked(MouseEvent mouseEvent) {
            if(mouseEvent.getClickCount() == 2) {
                open();
            }
            if(mouseEvent.getClickCount() == 1) {
                DefaultMutableTreeNode selNode = (DefaultMutableTreeNode) tree
                        .getLastSelectedPathComponent();
                if (selNode == null)
                {
                    return;
                }
                tempName=selNode.toString();
            }
        }
    };
    void open() {
        DefaultMutableTreeNode selNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        if (selNode == null)
        {
            return;
        }
        Stack<String> stack = new Stack<>();
        String parentName = selNode.toString();
        TreeNode selnode = selNode;
        while(parentName != rootName) {
            stack.push(parentName);
            selnode = selnode.getParent();
            parentName = selnode.toString();
        }
        int index = 0;
        textArea.setText("");
        textArea_1.setText("");
        openPath = so.getRoot();
        while(!stack.isEmpty()) openPath = openPath.getList().get(stack.pop());
        if(openPath.isIndex()) {
            openFile = null;
            openPath = null;
            textArea.setEnabled(false);
        }
        else {
            openFile = (FileEntity) so.readFile(openPath.getBegin(), openPath.getEnd(), "file");

            if(openFile.getInode().getElement()%2 == 0) {
                textArea.setText(openFile.getText());
                openFile.getInode().Access();
            }
            else JOptionPane.showMessageDialog(null, "权限限制：无法读取！");
            if(openFile.getInode().getElement()%3 == 0) textArea.setEnabled(true);
            else textArea.setEnabled(false);
            //显示磁盘调度部分：
            textArea_1.append("磁盘调度："+openPath.getBegin()+"-"+openPath.getEnd());
        }
    }

    class showSX extends JFrame{
        private JPanel jpl = new JPanel();
        private JLabel[] jlb = new JLabel[10];
        private JTextField[] jtf = new JTextField[10];
        showSX() {
            super("属性");
            DefaultMutableTreeNode selNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            Stack<String> stack = new Stack<>();
            String parentName = selNode.toString();
            TreeNode selnode = selNode;
            while(parentName != rootName) {
                stack.push(parentName);
                selnode = selnode.getParent();
                parentName = selnode.toString();
            }
            Item path = so.getRoot();
            while(!stack.isEmpty()) {
                path = path.getList().get(stack.pop());
            }
            Inode inode = new Inode();
            try {
                Entity entity;
                if(path.isIndex()) entity = so.readFile(path.getBegin(), path.getEnd(), "Index");
                else entity = so.readFile(path.getBegin(), path.getEnd(), "file");
                inode = entity.getInode();
                entity.getInode().Modify();
            }catch (Exception ex){
                System.out.println(ex.getMessage());
                JOptionPane.showMessageDialog(null, "非法操作");
            }
            jlb[0] = new JLabel("名称："); jlb[1] = new JLabel(inode.getName());
            jlb[2] = new JLabel("大小："); jlb[3] = new JLabel(""+inode.getUsedSpace());
            jlb[4] = new JLabel("创建时间："); jlb[5] = new JLabel(inode.getCreateTime());
            jlb[6] = new JLabel("修改时间："); jlb[7] = new JLabel(inode.getEditTime());
            jlb[8] = new JLabel("文件位置："); jlb[9] = new JLabel("这里！！！");
            jpl.setLayout(new GridLayout(4,2));
            for(int i=0;i<8;i++) {
                jpl.add(jlb[i]);
            }
            getContentPane().add(jpl);
            this.setSize(400, 300);
            this.setVisible(true);
            Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
            this.setLocation((int)screensize.getWidth(),(int)screensize.getHeight());
            this.setLocationRelativeTo(null);
            this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }
    }

    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ENTER) {//捕获键盘事件中得回车按键
            //获取当前结点
            DefaultMutableTreeNode selNode = (DefaultMutableTreeNode) tree
                    .getLastSelectedPathComponent();

//            f[p].setName(nodeName);

            Stack<String> stack = new Stack<>();
            String parentName = selNode.toString();
            if (parentName == rootName) return;
            TreeNode selnode = selNode;
            while(parentName != rootName) {
                stack.push(parentName);
                selnode = selnode.getParent();
                parentName = selnode.toString();
            }
            Item path = so.getRoot();
            while(stack.size() > 1) {
                path = path.getList().get(stack.pop());
            }
            Item tempItem = path.getList().get(tempName);
            tempItem.setName(selNode.toString());
            path.getList().remove(tempName);
            path.getList().put(selNode.toString(),tempItem);
            try {
                Entity entity;
                if(path.isIndex()) entity = so.readFile(path.getBegin(), path.getEnd(), "Index");
                else entity = so.readFile(path.getBegin(), path.getEnd(), "file");
                entity.getInode().setName(selNode.toString());
                entity.getInode().Modify();
            }catch (Exception ex){
                System.out.println(ex.getMessage());
                JOptionPane.showMessageDialog(null, "非法操作");
            }



            //寻找父结点
            TreeNode treePar=selNode.getParent();
            int count=treePar.getChildCount();
            for(int i=0;i<count;i++) {
                TreeNode child=treePar.getChildAt(i);
                if(child.equals(selNode))
                {
                    continue;
                }
                String childName=child.toString();
                if(childName.equals(selNode)) {
                    JOptionPane.showMessageDialog(null, "文件重名");
                    break;
                }

            }
        }
    }
    @Override
    public void actionPerformed(ActionEvent ee) {
        char[] c = textArea.getText().toCharArray();
        if   (ee.getActionCommand().equals("退出") )  System.exit(0);
        else if(ee.getActionCommand().equals("刷新")) {
            textArea.setText("");
            textArea_1.setText("");
        }
        else if(ee.getActionCommand().equals("打开")) open();
        else if(ee.getActionCommand().equals("编辑/锁定")) {
            if(textArea.isEditable())
                textArea.setEditable(false);
            else textArea.setEditable(true);
        }
        else if(ee.getActionCommand().equals("磁盘占用"))  new showCB();
        else if(ee.getActionCommand().equals("属性")) new showSX();

    }
    @Override
    public void keyPressed(KeyEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        // TODO Auto-generated method stub

    }
}

