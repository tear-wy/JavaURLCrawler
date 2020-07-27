package Listener;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class hlListener implements ActionListener {
    JTextPane tpResult=null;
    Vector<String> results=null;
    Vector<String> Senstives=null;

    public hlListener(JTextPane tpResult, Vector<String> results,Vector<String> Senstives) {
        this.tpResult = tpResult;
        this.results = results;
        this.Senstives=Senstives;
    }

    public void insertText(String str, Color color){
        SimpleAttributeSet set = new SimpleAttributeSet();
        StyleConstants.setBackground(set, color);//设置文字颜色

        StyledDocument doc = tpResult.getStyledDocument();
        try {
            doc.insertString(doc.getLength(), str, set);//插入文字
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        tpResult.setText("");
        Vector<Vector<String>> afterOperate=new Vector<Vector<String>>();
        for(String result:results){
            for(String sens:Senstives){
                result=result.replaceAll(sens,";"+sens+";");
            }
            String[] strs=result.split(";");
            for(String str:strs){
                if(Senstives.contains(str)){
                    //带颜色加入
                    insertText(str,Color.YELLOW);
                }
                else{
                    //不带颜色加入
                    insertText(str,Color.white);
                }
            }

        }


    }
}
