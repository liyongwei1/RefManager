package cn.liyongwei;

import cn.liyongwei.view.LoginFrm;
import com.formdev.flatlaf.FlatDarculaLaf;

public class Main {
    public static void main(String[] args) {
        FlatDarculaLaf.install();
        new LoginFrm().setVisible(true);
    }
}
