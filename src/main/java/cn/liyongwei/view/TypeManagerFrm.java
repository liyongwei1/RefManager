/*
 * Created by JFormDesigner on Thu Sep 16 19:28:55 CST 2021
 */

package cn.liyongwei.view;

import java.awt.event.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.*;

import cn.liyongwei.dao.RefDao;
import cn.liyongwei.dao.RefTypeDao;
import cn.liyongwei.entity.Ref;
import cn.liyongwei.entity.RefType;
import net.miginfocom.swing.*;

/**
 * @author 1
 */
public class TypeManagerFrm extends JFrame {
    public TypeManagerFrm() {
        initComponents();
        fillTable();
    }

    private void button1ActionPerformed(ActionEvent e) {
        // TODO add your code here
        RefType refType = new RefType();
        refType.setRefTypeName(this.textField1.getText());
        int result = RefTypeDao.add(refType);
        if (result <= 0) {
            JOptionPane.showMessageDialog(null, "添加失败");
        } else {
            fillTable();
        }
    }

    private void fillTable() {
        //获取所有文献类型
        RefType[] refTypes = RefTypeDao.list(new RefType());
        //清空表格
        DefaultTableModel dtm = (DefaultTableModel) table1.getModel();
        dtm.setRowCount(0);
        //
        Vector<Object> rowItem;
        for(RefType refType:refTypes) {
            rowItem = new Vector<>();
            rowItem.add(refType.getId());
            rowItem.add(refType.getRefTypeName());
            dtm.addRow(rowItem);
        }
        table1.setModel(dtm);
    }

    private void button2ActionPerformed(ActionEvent e) {
        // TODO add your code here
        //获取当前选中行的行数，若值为-1，则表示未选中任何行
        int row = this.table1.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "当前未选中任何文献类型");
            return;
        }
        //获取当前行的文献id
        int id = ((Integer) table1.getValueAt(row,0));
        //判断该类型下是否存在文献
        if (RefTypeDao.existRef(id)) {
            JOptionPane.showMessageDialog(null, "该类型下仍存在文献，不可以删除");
            return;
        }
        //开始根据获取的id删除文献类型
        int result = RefTypeDao.delete(id);
        if (result <= 0) {
            JOptionPane.showMessageDialog(null, "删除失败");
        } else {
            JOptionPane.showMessageDialog(null, "删除成功");
            fillTable();
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        scrollPane1 = new JScrollPane();
        table1 = new JTable();
        button1 = new JButton();
        button2 = new JButton();
        textField1 = new JTextField();

        //======== this ========
        setTitle("\u6587\u732e\u7c7b\u578b\u7ba1\u7406");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        var contentPane = getContentPane();

        //======== scrollPane1 ========
        {

            //---- table1 ----
            table1.setModel(new DefaultTableModel(
                new Object[][] {
                    {null, null},
                    {null, null},
                },
                new String[] {
                    "id", "\u7c7b\u578b"
                }
            ) {
                boolean[] columnEditable = new boolean[] {
                    false, false
                };
                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return columnEditable[columnIndex];
                }
            });
            scrollPane1.setViewportView(table1);
        }

        //---- button1 ----
        button1.setText("\u65b0\u589e");
        button1.addActionListener(e -> button1ActionPerformed(e));

        //---- button2 ----
        button2.setText("\u5220\u9664");
        button2.addActionListener(e -> button2ActionPerformed(e));

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(78, 78, 78)
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addComponent(textField1, GroupLayout.PREFERRED_SIZE, 341, GroupLayout.PREFERRED_SIZE)
                        .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 341, GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(79, Short.MAX_VALUE))
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(110, 110, 110)
                    .addComponent(button1)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 125, Short.MAX_VALUE)
                    .addComponent(button2)
                    .addGap(107, 107, 107))
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(25, 25, 25)
                    .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 184, GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(textField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(button1)
                        .addComponent(button2))
                    .addContainerGap(33, Short.MAX_VALUE))
        );
        setSize(500, 370);
        setLocationRelativeTo(null);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JScrollPane scrollPane1;
    private JTable table1;
    private JButton button1;
    private JButton button2;
    private JTextField textField1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
