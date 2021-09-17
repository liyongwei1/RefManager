/*
 * Created by JFormDesigner on Wed Sep 15 14:08:43 CST 2021
 */

package cn.liyongwei.view.component;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import cn.liyongwei.dao.RefDao;
import cn.liyongwei.dao.RefTypeDao;
import cn.liyongwei.entity.Ref;
import cn.liyongwei.entity.RefType;
import cn.liyongwei.util.DbUtil;
import cn.liyongwei.util.StringUtil;
import net.miginfocom.swing.*;

/**
 * @author 1
 */
public class RefDetail extends JPanel {

    public RefDetail() {
        refOfPanel = new Ref();
        initComponents();
    }

    /**
     * 设置所有文本框的编辑状态
     * @param state 编辑状态
     */
    private void setAllEditable(boolean state) {
        // 共八个可编辑的内容框，标题，作者，类型，日期，出版社，路径，备注
        // 类型为下拉选择，日期为选择日期，路径为选择系统文件，这三个不修改编辑状态
        // PS 类型的下拉选择修改可用状态
        this.textPaneDesc.setEditable(state);
        this.textFieldPublisher.setEditable(state);
        this.textFieldDate.setEditable(state);
        this.textFieldAuthor.setEditable(state);
        this.textFieldName.setEditable(state);
        //设置类型下拉框是否可用，若可用，则填充下拉框
        this.refTypeCB.setEnabled(state);
        if (state) {
            fillerItem();
        }
    }

    /**
     * 刷新面板，根据私有文献实体来刷新面板
     */
    public void showRefDetail() {
        //若处于编辑状态，则不刷新面板
        if (this.textFieldName.isEditable()) {
            return;
        }
        //若ref未初始化，则初始化
        if (refOfPanel == null) {
            refOfPanel = new Ref();
        }
        this.textFieldName.setText(refOfPanel.getRefName());
        this.textFieldAuthor.setText(refOfPanel.getRefAuthor());
        this.textFieldDate.setText(refOfPanel.getRefDate());
        this.textFieldPath.setText(refOfPanel.getRefPath());
        this.textFieldPublisher.setText(refOfPanel.getRefPublisher());
        this.textPaneDesc.setText(refOfPanel.getRefDesc());
        this.refTypeCB.removeAllItems();
        this.refTypeCB.addItem(new RefType(refOfPanel.getRefTypeId()));
    }

    /**
     * 路径文本框鼠标双击事件处理
     * @param e 事件
     */
    private void textFieldPathMouseClicked(MouseEvent e) {
        // TODO add your code here
        //检测是否为可编辑状态,如果不是，则直接退出
        if (!this.textFieldName.isEditable()) {
            return;
        }
        //否则，打开一个选择文件对话框
        JFileChooser chooser = new JFileChooser("My Documents");
        FileFilter filter = new FileNameExtensionFilter("文献文档（PDF/DjVu）","pdf", "djvu");
        chooser.setFileFilter(filter);
        int result = chooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            String refPath = chooser.getSelectedFile().getPath();
            this.textFieldPath.setText(refPath);
        }
    }

    /**
     * 新建按钮事件处理
     * @param e 事件
     */
    private void newRefActionPerformed(ActionEvent e) {
        // TODO add your code here
        newRef();
    }

    /**
     * 新建文献
     */
    public void newRef() {
        if (this.textFieldName.isEditable()) {
            return;
        }
        refOfPanel = new Ref();
        showRefDetail();
        setAllEditable(true);
        this.textFieldName.requestFocusInWindow();
    }

    /**
     * 修改按钮事件处理
     * @param e 事件
     */
    private void modifyRefActionPerformed(ActionEvent e) {
        // TODO add your code here
        //若已经处于编辑状态，则直接退出
        if (this.textFieldName.isEditable()) {
            return;
        }
        //若面板的文献实体id为0，则表示当前显示的不是已存在的id，无法修改，直接退出
        if (this.refOfPanel.getId() == 0) {
            return;
        }
        setAllEditable(true);
    }

    /**
     * 保存按钮事件处理
     * @param e 事件
     */
    private void saveRefActionPerformed(ActionEvent e) {
        // TODO add your code here
        if (!this.textFieldName.isEditable()) {
            return;
        }
        setAllEditable(false);
        Ref ref = new Ref();
        ref.setId(this.refOfPanel.getId());
        ref.setRefName(this.textFieldName.getText());
        ref.setRefAuthor(this.textFieldAuthor.getText());
        ref.setRefPath(this.textFieldPath.getText());
        ref.setRefDate(this.textFieldDate.getText());
        ref.setRefDesc(this.textPaneDesc.getText());
        ref.setRefPublisher(this.textFieldPublisher.getText());
        //获取下拉框选中内容
        RefType refType = (RefType) this.refTypeCB.getSelectedItem();
        ref.setRefTypeId(refType.getId());
        //若未进行修改或者全为0，则直接退出
        if (ref.equals(refOfPanel)){
            return;
        }
        if (StringUtil.isEmpty(ref.getRefName()) && StringUtil.isEmpty(ref.getRefPath())) {
            return;
        }
        if (ref.getId() == 0) {
            //新建文献
            RefDao.add(ref);
        } else {
            //修改文献
            RefDao.update(ref);
        }
        this.refOfPanel = ref;
        showRefDetail();
    }

    /**
     * 打开文献按钮事件处理
     * @param e 事件
     */
    private void openRefActionPerformed(ActionEvent e) {
        // TODO add your code here
        //获取文献路径
        String refPath = this.textFieldPath.getText();
        //获取阅读器路径
        if (StringUtil.isEmpty(refPath)) {
            JOptionPane.showMessageDialog(null,"文献为空或该文献不存在文件路径!");
            return;
        }
        //调用阅读器打开文献
        if (StringUtil.isEmpty(readerPath)) {
            readerPath = "SumatraPDF/SumatraPDF-3.3.3-64.exe";
        }

        try {
            Runtime.getRuntime().exec(readerPath + " \"" + refPath + "\"");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * 删除按钮事件处理
     * @param e 事件
     */
    private void deleteRefActionPerformed(ActionEvent e) {
        // TODO add your code here
        //若处于编辑状态，则不进行操作
        if (this.textFieldName.isEditable()) {
            return;
        }
        int opt = JOptionPane.showConfirmDialog(null,
                "确认要删除该文献吗？", "确认信息",
                JOptionPane.YES_NO_OPTION);
        if (opt == JOptionPane.YES_OPTION) {
            if (refOfPanel.getId() == 0) {
                JOptionPane.showMessageDialog(null,"删除失败");
                return;
            }
            int result = RefDao.delete(this.refOfPanel.getId());
            if (result>0) {
                JOptionPane.showMessageDialog(null,"删除成功");
                refOfPanel = new Ref();
                showRefDetail();
            } else {
                JOptionPane.showMessageDialog(null,"删除失败");
            }
        }
    }

    /**
     * 填充下拉框
     *
     * @since 2020/4/12
     */
    private void fillerItem() {
        //获取到所有文献类别
        RefType[] rs = RefTypeDao.list(new RefType());
        //删除JComBox中的所有列表项
        this.refTypeCB.removeAllItems();
        for(RefType r:rs) {
            this.refTypeCB.addItem(r);
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        panel1 = new JPanel();
        button3 = new JButton();
        button4 = new JButton();
        button5 = new JButton();
        button1 = new JButton();
        button6 = new JButton();
        panel2 = new JPanel();
        label1 = new JLabel();
        textFieldName = new JTextField();
        label2 = new JLabel();
        textFieldAuthor = new JTextField();
        label3 = new JLabel();
        refTypeCB = new JComboBox<>();
        label4 = new JLabel();
        textFieldDate = new JTextField();
        label5 = new JLabel();
        textFieldPublisher = new JTextField();
        label6 = new JLabel();
        textFieldPath = new JTextField();
        label7 = new JLabel();
        scrollPane1 = new JScrollPane();
        textPaneDesc = new JTextPane();

        //======== this ========
        setMinimumSize(new Dimension(500, 296));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //======== panel1 ========
        {
            panel1.setMinimumSize(new Dimension(444, 30));
            panel1.setMaximumSize(new Dimension(2147483647, 30));
            panel1.setLayout(new MigLayout(
                "hidemode 3,alignx right,gapx 45",
                // columns
                "[fill]" +
                "[fill]",
                // rows
                "[]"));

            //---- button3 ----
            button3.setText("\u65b0\u5efa");
            button3.addActionListener(e -> newRefActionPerformed(e));
            panel1.add(button3, "cell 0 0");

            //---- button4 ----
            button4.setText("\u4fee\u6539");
            button4.addActionListener(e -> modifyRefActionPerformed(e));
            panel1.add(button4, "cell 0 0");

            //---- button5 ----
            button5.setText("\u5220\u9664");
            button5.addActionListener(e -> deleteRefActionPerformed(e));
            panel1.add(button5, "cell 0 0");

            //---- button1 ----
            button1.setText("\u4fdd\u5b58");
            button1.addActionListener(e -> saveRefActionPerformed(e));
            panel1.add(button1, "cell 0 0");

            //---- button6 ----
            button6.setText("\u6253\u5f00\u6587\u732e");
            button6.addActionListener(e -> openRefActionPerformed(e));
            panel1.add(button6, "cell 1 0");
        }
        add(panel1);

        //======== panel2 ========
        {
            panel2.setLayout(new MigLayout(
                "fill,hidemode 3,align center top",
                // columns
                "[fill]" +
                "[fill]",
                // rows
                "[30:31]" +
                "[39]" +
                "[39]" +
                "[31]" +
                "[31]" +
                "[32]" +
                "[]"));

            //---- label1 ----
            label1.setText("\u6807\u9898\uff1a");
            label1.setMaximumSize(new Dimension(50, 30));
            label1.setMinimumSize(new Dimension(50, 30));
            label1.setPreferredSize(new Dimension(36, 30));
            panel2.add(label1, "cell 0 0");

            //---- textFieldName ----
            textFieldName.setPreferredSize(new Dimension(200, 30));
            textFieldName.setMaximumSize(new Dimension(2147483647, 30));
            textFieldName.setMinimumSize(new Dimension(100, 30));
            textFieldName.setEditable(false);
            panel2.add(textFieldName, "cell 1 0");

            //---- label2 ----
            label2.setText("\u4f5c\u8005\uff1a");
            label2.setMaximumSize(new Dimension(50, 17));
            label2.setMinimumSize(new Dimension(50, 17));
            panel2.add(label2, "cell 0 1");

            //---- textFieldAuthor ----
            textFieldAuthor.setPreferredSize(new Dimension(1000, 30));
            textFieldAuthor.setMinimumSize(new Dimension(100, 30));
            textFieldAuthor.setEditable(false);
            panel2.add(textFieldAuthor, "cell 1 1");

            //---- label3 ----
            label3.setText("\u7c7b\u578b\uff1a");
            label3.setMaximumSize(new Dimension(50, 17));
            label3.setMinimumSize(new Dimension(50, 17));
            panel2.add(label3, "cell 0 2");

            //---- refTypeCB ----
            refTypeCB.setEnabled(false);
            panel2.add(refTypeCB, "cell 1 2");

            //---- label4 ----
            label4.setText("\u65e5\u671f\uff1a");
            label4.setMaximumSize(new Dimension(50, 17));
            label4.setMinimumSize(new Dimension(50, 17));
            panel2.add(label4, "cell 0 3");

            //---- textFieldDate ----
            textFieldDate.setMinimumSize(new Dimension(100, 30));
            textFieldDate.setEditable(false);
            panel2.add(textFieldDate, "cell 1 3");

            //---- label5 ----
            label5.setText("\u51fa\u7248\u793e\uff1a");
            label5.setMaximumSize(new Dimension(50, 17));
            label5.setMinimumSize(new Dimension(50, 17));
            panel2.add(label5, "cell 0 4");

            //---- textFieldPublisher ----
            textFieldPublisher.setMinimumSize(new Dimension(100, 30));
            textFieldPublisher.setEditable(false);
            panel2.add(textFieldPublisher, "cell 1 4");

            //---- label6 ----
            label6.setText("\u8def\u5f84\uff1a");
            label6.setMaximumSize(new Dimension(50, 17));
            label6.setMinimumSize(new Dimension(50, 17));
            panel2.add(label6, "cell 0 5");

            //---- textFieldPath ----
            textFieldPath.setMinimumSize(new Dimension(100, 30));
            textFieldPath.setEditable(false);
            textFieldPath.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    textFieldPathMouseClicked(e);
                }
            });
            panel2.add(textFieldPath, "cell 1 5");

            //---- label7 ----
            label7.setText("\u6982\u8ff0\uff1a");
            label7.setMaximumSize(new Dimension(50, 17));
            label7.setMinimumSize(new Dimension(50, 17));
            panel2.add(label7, "cell 0 6");

            //======== scrollPane1 ========
            {
                scrollPane1.setMinimumSize(new Dimension(100, 30));
                scrollPane1.setMaximumSize(new Dimension(32767, 3277));
                scrollPane1.setPreferredSize(new Dimension(200, 500));

                //---- textPaneDesc ----
                textPaneDesc.setEditable(false);
                scrollPane1.setViewportView(textPaneDesc);
            }
            panel2.add(scrollPane1, "cell 1 6");
        }
        add(panel2);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel panel1;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button1;
    private JButton button6;
    private JPanel panel2;
    private JLabel label1;
    private JTextField textFieldName;
    private JLabel label2;
    private JTextField textFieldAuthor;
    private JLabel label3;
    private JComboBox<RefType> refTypeCB;
    private JLabel label4;
    private JTextField textFieldDate;
    private JLabel label5;
    private JTextField textFieldPublisher;
    private JLabel label6;
    private JTextField textFieldPath;
    private JLabel label7;
    private JScrollPane scrollPane1;
    private JTextPane textPaneDesc;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    private Ref refOfPanel;
    private String readerPath;

    public Ref getRefOfPanel() {
        return refOfPanel;
    }

    public void setRefOfPanel(Ref ref) {
        this.refOfPanel = ref;
    }

    public String getReaderPath() {
        return readerPath;
    }

    public void setReaderPath(String readerPath) {
        this.readerPath = readerPath;
    }
}
