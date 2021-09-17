/*
 * Created by JFormDesigner on Tue Sep 14 16:14:53 CST 2021
 */

package cn.liyongwei.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import cn.liyongwei.dao.UserDao;
import cn.liyongwei.entity.User;
import cn.liyongwei.util.StringUtil;
import net.miginfocom.swing.*;

/**
 * @author 1
 */
public class SignupFrm extends JFrame {
    public SignupFrm() {
        initComponents();
    }

    private void backActionPerformed(ActionEvent e) {
        // TODO add your code here
        dispose();
        new LoginFrm().setVisible(true);
    }

    private void signupActionPerformed(ActionEvent e) {
        // TODO add your code here
        String userName = this.userNameTxt.getText();
        String password1 = new String(this.passwordTxt1.getPassword());
        String password2 = new String(this.passwordTxt2.getPassword());
        if (StringUtil.isEmpty(userName)) {
            JOptionPane.showMessageDialog(null, "用户名不能为空");
            return;
        }
        if (StringUtil.isEmpty(password1)) {
            JOptionPane.showMessageDialog(null, "密码不能为空");
            return;
        }
        if (!StringUtil.isEquals(password1, password2)) {
            JOptionPane.showMessageDialog(null, "两次密码输入不相同");
            return;
        }
        User user = new User(userName, password1);
        int result = UserDao.add(user);
        if (result > 0) {
            JOptionPane.showMessageDialog(null, "注册成功");
            dispose();
            new LoginFrm().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "注册失败");
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        label1 = new JLabel();
        userNameTxt = new JTextField();
        label2 = new JLabel();
        passwordTxt1 = new JPasswordField();
        label3 = new JLabel();
        passwordTxt2 = new JPasswordField();
        signup = new JButton();
        back = new JButton();

        //======== this ========
        setTitle("\u6ce8\u518c");
        setIconImage(new ImageIcon(getClass().getResource("/images/logo.png")).getImage());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        var contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "hidemode 3,align center center,gapx 40",
            // columns
            "[fill]" +
            "[fill]",
            // rows
            "[]" +
            "[]" +
            "[]" +
            "[]"));

        //---- label1 ----
        label1.setText("\u7528 \u6237 \u540d\uff1a");
        label1.setMinimumSize(new Dimension(60, 17));
        label1.setMaximumSize(new Dimension(60, 17));
        contentPane.add(label1, "cell 0 0 3 1");
        contentPane.add(userNameTxt, "cell 0 0 3 1");

        //---- label2 ----
        label2.setText("\u5bc6\u7801 \uff1a");
        label2.setMinimumSize(new Dimension(60, 17));
        label2.setMaximumSize(new Dimension(60, 17));
        contentPane.add(label2, "cell 0 1 3 1");
        contentPane.add(passwordTxt1, "cell 0 1 2 1");

        //---- label3 ----
        label3.setText("\u518d\u6b21\u8f93\u5165\uff1a");
        label3.setMinimumSize(new Dimension(60, 17));
        label3.setMaximumSize(new Dimension(60, 17));
        contentPane.add(label3, "cell 0 2 3 1");
        contentPane.add(passwordTxt2, "cell 0 2 2 1");

        //---- signup ----
        signup.setText("\u6ce8\u518c");
        signup.addActionListener(e -> signupActionPerformed(e));
        contentPane.add(signup, "cell 0 3");

        //---- back ----
        back.setText("\u8fd4\u56de");
        back.addActionListener(e -> backActionPerformed(e));
        contentPane.add(back, "cell 1 3");
        setSize(500, 400);
        setLocationRelativeTo(null);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel label1;
    private JTextField userNameTxt;
    private JLabel label2;
    private JPasswordField passwordTxt1;
    private JLabel label3;
    private JPasswordField passwordTxt2;
    private JButton signup;
    private JButton back;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
