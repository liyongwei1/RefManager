/*
 * Created by JFormDesigner on Tue Sep 14 15:58:21 CST 2021
 */

package cn.liyongwei.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import cn.liyongwei.dao.UserDao;
import cn.liyongwei.entity.User;
import cn.liyongwei.util.StringUtil;
import net.miginfocom.swing.*;

public class LoginFrm extends JFrame {
    public LoginFrm() {
        initComponents();
    }

    /**
     * 注册按钮响应事件
     * @param e 事件
     */
    private void signupActionPerformed(ActionEvent e) {
        // TODO add your code here
        dispose();
        new SignupFrm().setVisible(true);
    }

    /**
     * 登录按钮响应事件
     * @param e 事件
     */
    private void loginActionPerformed(ActionEvent e) {
        // TODO add your code here
        String userName = this.userNameTxt.getText();
        String password = new String(this.passwordTxt.getPassword());
        if (StringUtil.isEmpty(userName)) {
            JOptionPane.showMessageDialog(null, "用户名不能为空");
            return;
        }
        if (StringUtil.isEmpty(password)) {
            JOptionPane.showMessageDialog(null, "密码不能为空");
            return;
        }
        User user = new User(userName, password);
        User currentUser = UserDao.login(user);
        if (currentUser != null) {
            dispose();
            new MainFrm().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "用户名或密码错误");
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        label1 = new JLabel();
        userNameTxt = new JTextField();
        label2 = new JLabel();
        passwordTxt = new JPasswordField();
        button1 = new JButton();
        label3 = new JLabel();
        button2 = new JButton();

        //======== this ========
        setTitle("\u767b\u5f55");
        setIconImage(new ImageIcon(getClass().getResource("/images/logo.png")).getImage());
        setResizable(false);
        setMinimumSize(new Dimension(50, 37));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        var contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "insets 0,hidemode 3,align center center,gap 20 10",
            // columns
            "[fill]" +
            "[fill]" +
            "[fill]",
            // rows
            "[]" +
            "[]" +
            "[]"));

        //---- label1 ----
        label1.setText("\u7528\u6237\u540d\uff1a");
        contentPane.add(label1, "cell 0 0 3 1,alignx right,growx 0");
        contentPane.add(userNameTxt, "cell 0 0 3 1");

        //---- label2 ----
        label2.setText("\u5bc6   \u7801\uff1a");
        contentPane.add(label2, "cell 0 1 3 1,alignx right,growx 0");
        contentPane.add(passwordTxt, "cell 0 1 3 1");

        //---- button1 ----
        button1.setText("\u767b\u5f55");
        button1.addActionListener(e -> loginActionPerformed(e));
        contentPane.add(button1, "cell 0 2");
        contentPane.add(label3, "cell 1 2");

        //---- button2 ----
        button2.setText("\u6ce8\u518c");
        button2.addActionListener(e -> signupActionPerformed(e));
        contentPane.add(button2, "cell 2 2");
        setSize(500, 400);
        setLocationRelativeTo(null);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel label1;
    private JTextField userNameTxt;
    private JLabel label2;
    private JPasswordField passwordTxt;
    private JButton button1;
    private JLabel label3;
    private JButton button2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
