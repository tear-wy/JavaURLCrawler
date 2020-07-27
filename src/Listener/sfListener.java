package Listener;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Vector;

public class sfListener implements ActionListener {
    Vector<String> results=null;

    public sfListener(Vector<String> results) {
        this.results = results;
    }

    public String chooseSavePath() {
        JFileChooser jfc = new JFileChooser("E:\\URL爬虫测试");
        jfc.setDialogTitle("请选择保存文件位置");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("文本文件(*.txt;*.kcd)", "txt", "kcd");
        jfc.setFileFilter(filter);//只允许选择文本文件类型
        int returnValue = jfc.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            return jfc.getSelectedFile().getAbsolutePath();
        } else if (returnValue == JFileChooser.ERROR_OPTION) {
            JOptionPane.showMessageDialog(null, "路径错误");
            return null;
        }
        return null;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String path=chooseSavePath();
        PrintStream writer=null;
        try {
            FileOutputStream output=new FileOutputStream(path);
            writer=new PrintStream(output);

            for(String result:results){
                writer.println(result);
            }
            JOptionPane.showMessageDialog(null,"保存成功");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }finally {
            writer.close();
        }
    }
}
