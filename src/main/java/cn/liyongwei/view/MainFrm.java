/*
 * Created by JFormDesigner on Tue Sep 14 16:11:20 CST 2021
 */

package cn.liyongwei.view;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.List;
import java.util.LinkedList;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import cn.liyongwei.dao.RefDao;
import cn.liyongwei.entity.Ref;
import cn.liyongwei.util.StringUtil;
import cn.liyongwei.view.component.*;

/**
 * @author liyw
 */
public class MainFrm extends JFrame {

    /**
     * 无参构造函数
     */
    public MainFrm() {
        initComponents();
        panel2.add(refList1.getRefDetail(), "card1");
    }

    /**
     * 菜单栏-注销响应事件
     * @param e 事件
     */
    private void returnLoginActionPerformed(ActionEvent e) {
        // TODO add your code here
        dispose();
        new LoginFrm().setVisible(true);
    }

    /**
     * 菜单栏-退出响应事件
     * @param e 事件
     */
    private void exitsActionPerformed(ActionEvent e) {
        // TODO add your code here
        dispose();
    }

    /**
     * 工具栏-文献响应事件
     * @param e 事件
     */
    private void button1ActionPerformed(ActionEvent e) {
        // TODO add your code here
        CardLayout c1 = (CardLayout) panel1.getLayout();
        c1.show(panel1, "card1");
        CardLayout c2 = (CardLayout) panel2.getLayout();
        c2.show(panel2, "card1");

    }

    /**
     * 工具栏-收藏响应事件处理
     * @param e 事件
     */
    private void button2ActionPerformed(ActionEvent e) {
        // TODO add your code here
        CardLayout c1 = (CardLayout) panel1.getLayout();
        c1.show(panel1, "card1");
        CardLayout c2 = (CardLayout) panel2.getLayout();
        c2.show(panel2, "card1");
    }

    /**
     * 菜单栏-设置外部阅读器响应事件处理
     */
    private void externalReaderActionPerformed(ActionEvent e) {
        // TODO add your code here
        String readerPath = null;
        JFileChooser chooser = new JFileChooser("My Documents");
        FileFilter filter = new FileNameExtensionFilter("应用程序","exe");
        chooser.setFileFilter(filter);
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            readerPath = chooser.getSelectedFile().getPath();
        }
        this.refList1.getRefDetail().setReaderPath(readerPath);
    }

    private void menuItem6ActionPerformed(ActionEvent e) {
        // TODO add your code here
        new TypeManagerFrm().setVisible(true);
    }

    private void menuItemNewRefActionPerformed(ActionEvent e) {
        // TODO add your code here
        this.refList1.getRefDetail().requestFocusInWindow();
        this.refList1.getRefDetail().newRef();
    }

    /**
     * 菜单栏-批量导入文献响应事件
     * @param e 事件
     */
    private void menuItem5ActionPerformed(ActionEvent e) {
        // TODO add your code here
        //设置选中文件夹对话框
        JFileChooser chooser = new JFileChooser("My Documents");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //打开对话框
        int result = chooser.showOpenDialog(null);
        String refPath = "";
        if (result == JFileChooser.APPROVE_OPTION) {
            refPath = chooser.getSelectedFile().getPath();
        }
        //判断路径是否为空
        if (StringUtil.isEmpty(refPath)) {
            return;
        }
        //读取所有pdf和djvu文档
        File[] fileList = new File(refPath).listFiles();
        List<Ref> refList = new LinkedList<Ref>();
        Ref ref;
        if (fileList == null || fileList.length==0) {
            JOptionPane.showMessageDialog(null, "文件夹读取错误或文件夹内不含文件");
        }
        for(File file:fileList) {
            if (file.isFile()) {
                if (file.getName().endsWith(".pdf") || file.getName().endsWith(".djvu")) {
                    ref = new Ref(file.getName(), file.getPath());
                    refList.add(ref);
                }
            }
        }
        if (refList.size() > 0) {
            RefDao.add(refList);
            JOptionPane.showMessageDialog(null, "共" + refList.size() + "个文献添加成功");
        }
    }
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        menuBar1 = new JMenuBar();
        menu1 = new JMenu();
        menuItemNewRef = new JMenuItem();
        menuItem5 = new JMenuItem();
        separator1 = new JSeparator();
        menuItem6 = new JMenuItem();
        separator3 = new JSeparator();
        menu3 = new JMenu();
        menuItem7 = new JMenuItem();
        separator2 = new JSeparator();
        menuItem1 = new JMenuItem();
        menuItem2 = new JMenuItem();
        menu2 = new JMenu();
        menuItem3 = new JMenuItem();
        toolBar1 = new JToolBar();
        button1 = new JButton();
        button2 = new JButton();
        button3 = new JButton();
        splitPane1 = new JSplitPane();
        panel1 = new JPanel();
        refList1 = new RefList();
        panel2 = new JPanel();

        //======== this ========
        setTitle("\u6587\u732e\u7ba1\u7406\u7cfb\u7edf");
        setIconImage(new ImageIcon(getClass().getResource("/images/logo.png")).getImage());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1000, 800));
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== menuBar1 ========
        {

            //======== menu1 ========
            {
                menu1.setText("\u6587\u4ef6");

                //---- menuItemNewRef ----
                menuItemNewRef.setText("\u65b0\u5efa\u6587\u732e");
                menuItemNewRef.addActionListener(e -> menuItemNewRefActionPerformed(e));
                menu1.add(menuItemNewRef);

                //---- menuItem5 ----
                menuItem5.setText("\u6279\u91cf\u5bfc\u5165\u6587\u732e");
                menuItem5.addActionListener(e -> menuItem5ActionPerformed(e));
                menu1.add(menuItem5);
                menu1.add(separator1);

                //---- menuItem6 ----
                menuItem6.setText("\u6587\u732e\u7c7b\u578b\u7ba1\u7406");
                menuItem6.addActionListener(e -> menuItem6ActionPerformed(e));
                menu1.add(menuItem6);
                menu1.add(separator3);

                //======== menu3 ========
                {
                    menu3.setText("\u8bbe\u7f6e");

                    //---- menuItem7 ----
                    menuItem7.setText("\u5916\u90e8\u9605\u8bfb\u5668\u8def\u5f84");
                    menuItem7.addActionListener(e -> externalReaderActionPerformed(e));
                    menu3.add(menuItem7);
                }
                menu1.add(menu3);
                menu1.add(separator2);

                //---- menuItem1 ----
                menuItem1.setText("\u6ce8\u9500");
                menuItem1.addActionListener(e -> returnLoginActionPerformed(e));
                menu1.add(menuItem1);

                //---- menuItem2 ----
                menuItem2.setText("\u9000\u51fa");
                menuItem2.addActionListener(e -> exitsActionPerformed(e));
                menu1.add(menuItem2);
            }
            menuBar1.add(menu1);

            //======== menu2 ========
            {
                menu2.setText("\u5e2e\u52a9");

                //---- menuItem3 ----
                menuItem3.setText("\u5173\u4e8e");
                menu2.add(menuItem3);
            }
            menuBar1.add(menu2);
        }
        setJMenuBar(menuBar1);

        //======== toolBar1 ========
        {
            toolBar1.setFloatable(false);
            toolBar1.setOrientation(SwingConstants.VERTICAL);
            toolBar1.setMaximumSize(new Dimension(80, 2000));
            toolBar1.setMinimumSize(new Dimension(80, 600));
            toolBar1.setPreferredSize(new Dimension(80, 600));
            toolBar1.setMargin(new Insets(2, 2, 2, 2));

            //---- button1 ----
            button1.setText("\u6587\u732e");
            button1.setMaximumSize(new Dimension(80, 80));
            button1.setMinimumSize(new Dimension(80, 80));
            button1.addActionListener(e -> button1ActionPerformed(e));
            toolBar1.add(button1);

            //---- button2 ----
            button2.setText("\u6536\u85cf");
            button2.setMaximumSize(new Dimension(80, 80));
            button2.setMinimumSize(new Dimension(80, 80));
            button2.addActionListener(e -> button2ActionPerformed(e));
            toolBar1.add(button2);

            //---- button3 ----
            button3.setText("\u5f15\u7528");
            button3.setMaximumSize(new Dimension(80, 80));
            button3.setMinimumSize(new Dimension(80, 80));
            toolBar1.add(button3);
        }
        contentPane.add(toolBar1, BorderLayout.WEST);

        //======== splitPane1 ========
        {
            splitPane1.setContinuousLayout(true);
            splitPane1.setDividerLocation(400);
            splitPane1.setDividerSize(7);

            //======== panel1 ========
            {
                panel1.setLayout(new CardLayout());
                panel1.add(refList1, "card1");
            }
            splitPane1.setLeftComponent(panel1);

            //======== panel2 ========
            {
                panel2.setLayout(new CardLayout());
            }
            splitPane1.setRightComponent(panel2);
        }
        contentPane.add(splitPane1, BorderLayout.CENTER);
        setSize(620, 500);
        setLocationRelativeTo(null);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JMenuBar menuBar1;
    private JMenu menu1;
    private JMenuItem menuItemNewRef;
    private JMenuItem menuItem5;
    private JSeparator separator1;
    private JMenuItem menuItem6;
    private JSeparator separator3;
    private JMenu menu3;
    private JMenuItem menuItem7;
    private JSeparator separator2;
    private JMenuItem menuItem1;
    private JMenuItem menuItem2;
    private JMenu menu2;
    private JMenuItem menuItem3;
    private JToolBar toolBar1;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JSplitPane splitPane1;
    private JPanel panel1;
    private RefList refList1;
    private JPanel panel2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

}
