/*
 * Created by JFormDesigner on Tue Sep 14 18:58:28 CST 2021
 */

package cn.liyongwei.view.component;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import cn.liyongwei.dao.RefDao;
import cn.liyongwei.dao.RefTypeDao;
import cn.liyongwei.entity.Ref;
import cn.liyongwei.entity.RefType;
import net.miginfocom.swing.*;

/**
 * @author 1
 */
public class RefList extends JPanel {

    public RefList() {
        initComponents();
        refDetail = new RefDetail();
        fillerItem();
    }

    /**
     * 刷新表格
     * @param refs  文献数组
     */
    private void fillTable(Ref[] refs) {
        DefaultTableModel dtm = (DefaultTableModel) table1.getModel();
        dtm.setRowCount(0);
        if (refs.length == 0) {
            table1.setModel(dtm);
            return;
        }
        Vector<Object> rowItem;
        for(Ref ref:refs) {
            rowItem = new Vector<>();
            rowItem.add(ref.getId());
            rowItem.add(ref.getRefName());
            rowItem.add(ref.getRefAuthor());
            rowItem.add(ref.getRefType());
            rowItem.add(ref.getRefDate());
            dtm.addRow(rowItem);
        }
        table1.setModel(dtm);
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
        //添加默认类别为全部
        this.refTypeCB.addItem(new RefType(0, "全部"));
        for(RefType r:rs) {
            this.refTypeCB.addItem(r);
        }
    }

    /**
     * 搜索事件
     */
    private void searchActionPerformed(ActionEvent e) {
        // TODO add your code here
        //获取搜索框内容
        String s = this.refNameTxt.getText();
        //获取搜索类型
        RefType refType = (RefType) this.refTypeCB.getSelectedItem();
        //调用fillTable填充表格
        if (refType == null) {
            refType = new RefType();
        }
        Ref[] result = RefDao.list(new Ref(s, refType.getId()));
        this.fillTable(result);
        this.fillerItem();
    }

    /**
     * 表格选中行事件
     */
    private void table1MousePressed(MouseEvent e) {
        // TODO add your code here
        if ( this.table1.getRowCount()==0 ) {
            return;
        }
        //获取当前选中行的行数，若值为-1，则表示未选中任何行
        int row = this.table1.getSelectedRow();
        if (row == -1) {
            return;
        }
        //获取当前行的文献id
        int id = ((Integer) table1.getValueAt(row,0));
        //开始根据获取的id寻找指定文献
        Ref targetRef = RefDao.search(id);
        //将文献实体传入文献详情列表并刷新
        refDetail.setRefOfPanel(targetRef);
        refDetail.showRefDetail();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        panel1 = new JPanel();
        refNameTxt = new JTextField();
        refTypeCB = new JComboBox<>();
        button1 = new JButton();
        scrollPane1 = new JScrollPane();
        table1 = new JTable();

        //======== this ========
        setMinimumSize(new Dimension(350, 70));
        setPreferredSize(new Dimension(400, 472));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //======== panel1 ========
        {
            panel1.setMinimumSize(new Dimension(2147483647, 50));
            panel1.setMaximumSize(new Dimension(2147483647, 50));
            panel1.setPreferredSize(new Dimension(2147483647, 45));
            panel1.setLayout(new MigLayout(
                "hidemode 3,alignx center,gapy 10",
                // columns
                "[fill]" +
                "[fill]" +
                "[fill]",
                // rows
                "[]"));

            //---- refNameTxt ----
            refNameTxt.setMinimumSize(new Dimension(200, 30));
            refNameTxt.setMaximumSize(new Dimension(1000, 30));
            refNameTxt.setToolTipText("\u6587\u732e\u6807\u9898\u6216\u6458\u8981\u7247\u6bb5");
            panel1.add(refNameTxt, "cell 0 0");

            //---- refTypeCB ----
            refTypeCB.setMaximumSize(new Dimension(70, 32767));
            refTypeCB.setMinimumSize(new Dimension(70, 30));
            panel1.add(refTypeCB, "cell 1 0");

            //---- button1 ----
            button1.setText("\u641c\u7d22");
            button1.setMaximumSize(new Dimension(70, 30));
            button1.setMinimumSize(new Dimension(70, 30));
            button1.addActionListener(e -> searchActionPerformed(e));
            panel1.add(button1, "cell 2 0");
        }
        add(panel1);

        //======== scrollPane1 ========
        {

            //---- table1 ----
            table1.setModel(new DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                    "id", "\u6807\u9898", "\u4f5c\u8005", "\u7c7b\u578b", "\u65e5\u671f"
                }
            ) {
                boolean[] columnEditable = new boolean[] {
                    false, false, false, false, false
                };
                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return columnEditable[columnIndex];
                }
            });
            table1.setRowSorter(null);
            table1.setAutoCreateRowSorter(true);
            table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            table1.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    table1MousePressed(e);
                }
            });
            scrollPane1.setViewportView(table1);
        }
        add(scrollPane1);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel panel1;
    private JTextField refNameTxt;
    private JComboBox<RefType> refTypeCB;
    private JButton button1;
    private JScrollPane scrollPane1;
    private JTable table1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    private final RefDetail refDetail;

    public RefDetail getRefDetail() {
        return refDetail;
    }
}
