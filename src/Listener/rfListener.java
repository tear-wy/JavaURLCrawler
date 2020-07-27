package Listener;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

public class rfListener implements ActionListener {
    private String kind=null;
    private JTextField textField=null;

    public rfListener(String kind, JTextField textField) {
        this.kind = kind;
        this.textField = textField;
    }

    public String chooseFile(String str) {
        JFileChooser jfc = new JFileChooser("E:\\URL爬虫测试");
        jfc.setDialogTitle("请选择" + str + "文件");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("文本文件(*.txt;*.kcd)", "txt", "kcd");
        jfc.setFileFilter(filter);//只允许选择文本文件类型
        int returnValue = jfc.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            return jfc.getSelectedFile().getAbsolutePath();
        } else if (returnValue == JFileChooser.ERROR_OPTION) {
            JOptionPane.showMessageDialog(null, "文件打开错误");
            return null;
        }
        return null;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        BufferedReader reader=null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(chooseFile(kind)),"utf-8"));
            String temp;
            String allWebs = "";
            while ((temp = reader.readLine()) != null) {
                allWebs += temp + ";";
            }
            //同时显示到tfWebsites上
            textField.setText(allWebs);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
