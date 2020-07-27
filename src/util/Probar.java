package util;


import javax.swing.*;
import java.awt.*;


public class Probar extends JDialog implements Runnable{
    private boolean canRun=true;
    private JLabel jl1=new JLabel("正在加载中，请稍后");
    private JProgressBar pb=new JProgressBar(JProgressBar.HORIZONTAL);//进度条
    private JLabel lbLog=new JLabel("消息日志");
    private JTextArea taLog=new JTextArea();
    private JScrollPane spLog=new JScrollPane(taLog);
    public Probar(Component parentComponent){
        super((JFrame) parentComponent);
        this.setSize(800,650);
        this.setLayout(null);

        jl1.setSize(150,70);
        jl1.setLocation(340,0);
        jl1.setFont(new Font("黑体",Font.BOLD,24));
        this.add(jl1);

        pb.setLocation(100,70);
        pb.setSize(600,15);
        pb.setBorderPainted(true);
        pb.setIndeterminate(true);
        pb.setVisible(true);
        this.add(pb);

        lbLog.setLocation(47,75);
        lbLog.setSize(120,70);
        lbLog.setFont(new Font("黑体",Font.PLAIN,20));
        this.add(lbLog);

        taLog.setFont(new Font("微软雅黑",Font.PLAIN,18));
        taLog.setEnabled(false);
        spLog.setLocation(50,123);
        spLog.setSize(680,450);
        this.add(spLog);

        this.setVisible(true);
        GUIUtil.toCenter(this);
    }

    public void setCanRun(boolean canRun) {
        this.canRun = canRun;
    }

    public void insertLog(String msg){
        taLog.append(msg+"\n");
    }

    @Override
    public void run() {
        while(canRun) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        JOptionPane.showMessageDialog(this,"爬取完成\n图片请见 "+"\"E:\\URL爬虫测试\\Img\""+" 文件夹");
        this.dispose();
    }

    public static void main(String[] args) {
        new Probar(null);
    }

}
