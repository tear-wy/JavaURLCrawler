package Frame;

import Crawl.Crawler;
import Listener.hlListener;
import Listener.rfListener;
import Listener.sfListener;
import util.GUIUtil;
import util.Probar;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Vector;


public class BasicFrame extends JFrame implements ActionListener{
    /******************************** 数据变量 *************************************/
    private Vector<String> htmls=new Vector<String>();
    private Vector<String> results=new Vector<String>();
    private Vector<String> Webs=new Vector<String>();
    private Vector<String> Sensitives = new Vector<String>();
    private Vector<JButton> outButtons=new Vector<JButton>();
    /******************************** 组件定义 *************************************/
    private Image imageIcon = Toolkit.getDefaultToolkit().createImage("E:\\URL爬虫测试\\爬虫作业\\src\\Frame\\imgs\\IconImage.jpg");
    private ImageIcon BackgroundIcon = new ImageIcon("E:\\URL爬虫测试\\爬虫作业\\src\\Frame\\imgs\\BackgroundIcon.jpg");
    private ImageIcon FileIcon = new ImageIcon("E:\\URL爬虫测试\\爬虫作业\\src\\Frame\\imgs\\FileIcon.jpg");
    private JLabel lbBackground;
    private JPanel Jp = new JPanel();
    //输入部分组件
    private JLabel lbWebsite = new JLabel("输入网址：");
    private JTextField tfWebsite = new JTextField(50);
    private JButton btWebFile = new JButton("选择文件");
    private JLabel Webtips = new JLabel();
    private JLabel lbSensitive = new JLabel("输入敏感词：");
    private JTextField tfSenstive = new JTextField(50);
    private JButton btSenFile = new JButton("选择文件");
    private JLabel Sentips = new JLabel();//敏感词输入提示
    private JButton btBegin = new JButton("开始爬取");
    private JButton btExit = new JButton("退出");
    //结果部分组件
    private JLabel lbTitle=new JLabel("爬取结果");
    private JButton btHtml=new JButton("html源码");
    private JButton btResult=new JButton("结果");
    private JTextPane tpResult=new JTextPane();
    private JScrollPane spResult=new JScrollPane(tpResult);
    private JButton btHeightLight=new JButton("高亮");
    private JButton btSaveFile=new JButton("保存");
    //进度条
    private Probar probar=null;

    public BasicFrame() {
        /***************************** 界面初始化  **************************************/
        super("JavaURL爬虫测试");
        initFrame();
        /*********************************** 增加监听 ******************************************/
        btWebFile.addActionListener(new rfListener("网址库",tfWebsite));
        btSenFile.addActionListener(new rfListener("敏感词",tfSenstive));
        btBegin.addActionListener(this);
        btExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"谢谢使用");
                System.exit(0);
            }
        });
        btHtml.addActionListener(this);
        btResult.addActionListener(this);
        btHeightLight.addActionListener(new hlListener(tpResult, results,Sensitives));
        btSaveFile.addActionListener(new sfListener(results));
    }

    public void initFrame() {
        this.setSize(1500, 1000);
        /**** 设置背景 ****/
        BackgroundIcon.setImage(BackgroundIcon.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_DEFAULT));
        lbBackground = new JLabel(BackgroundIcon);
        lbBackground.setBounds(0, 0, BackgroundIcon.getIconWidth(), BackgroundIcon.getIconHeight());
        lbBackground.setSize(this.getWidth(), this.getHeight());
        //获取窗口的第二层，将label放入
        this.getLayeredPane().add(lbBackground, new Integer(Integer.MIN_VALUE));
        //获取容器顶层，并设置为透明
        JPanel jtop = (JPanel) this.getContentPane();
        jtop.setOpaque(false);

        /**** 组件设置 ****/
        Font fontLabel = new Font("微软雅黑", Font.BOLD, 20);
        Font fontTextfield = new Font("微软雅黑", Font.PLAIN, 18);
        Font fontButton = new Font("微软雅黑", Font.BOLD, 16);
        Font fontTips = new Font("微软雅黑", Font.BOLD, 14);
        Jp.setLayout(null);

        lbWebsite.setSize(100, 50);
        lbWebsite.setLocation(65, 10);
        lbWebsite.setFont(fontLabel);
        lbWebsite.setForeground(Color.red);
        Jp.add(lbWebsite);

        tfWebsite.setSize(1100, 45);
        tfWebsite.setLocation(180, 15);
        tfWebsite.setFont(fontTextfield);
        tfWebsite.setText("www.baidu.com");
        Jp.add(tfWebsite);

        btWebFile.setSize(120, 40);
        btWebFile.setLocation(1300, 15);
        btWebFile.setFont(fontButton);
        FileIcon.setImage(FileIcon.getImage().getScaledInstance(btWebFile.getWidth() / 4, btWebFile.getHeight() - 10, Image.SCALE_DEFAULT));
        btWebFile.setIcon(FileIcon);
        Jp.add(btWebFile);

        Webtips.setFont(fontTips);
        Webtips.setText("Tips：网址输入无需添加https://或者http://; 输入多个网址，请使用英文\";\"将网址进行分隔。例如：www.baidu.com;www.sina.com.cn");
        Webtips.setSize(650, 30);
        Webtips.setLocation(180, 70);
        Jp.add(Webtips);

        lbSensitive.setSize(120, 50);
        lbSensitive.setLocation(45, 115);
        lbSensitive.setFont(fontLabel);
        lbSensitive.setForeground(Color.red);
        Jp.add(lbSensitive);

        tfSenstive.setSize(1100, 45);
        tfSenstive.setLocation(180, 115);
        tfSenstive.setText("百度");
        tfSenstive.setFont(fontTextfield);
        Jp.add(tfSenstive);

        btSenFile.setSize(120, 40);
        btSenFile.setLocation(1300, 115);
        btSenFile.setFont(fontButton);
        btSenFile.setIcon(FileIcon);
        Jp.add(btSenFile);

        Sentips.setFont(fontTips);
        Sentips.setText("Tips:输入多个敏感词，请使用英文\";\"将敏感词进行分隔。例如：百度;Tear;新闻");
        Sentips.setSize(650, 30);
        Sentips.setLocation(180, 175);
        Jp.add(Sentips);

        btBegin.setSize(130, 40);
        btBegin.setLocation(500, 215);
        ImageIcon BeginIcon = new ImageIcon("E:\\URL爬虫测试\\爬虫作业\\src\\util\\imgs\\BeginIcon.jpg");
        BeginIcon.setImage(BeginIcon.getImage().getScaledInstance(btBegin.getWidth() / 4, btBegin.getHeight() - 10, Image.SCALE_DEFAULT));
        btBegin.setIcon(BeginIcon);
        btBegin.setFont(fontButton);
        Jp.add(btBegin);

        btExit.setSize(130, 40);
        btExit.setLocation(850, 215);
        ImageIcon ExitIcon = new ImageIcon("E:\\URL爬虫测试\\爬虫作业\\src\\util\\imgs\\ExitIcon.jpg");
        ExitIcon.setImage(ExitIcon.getImage().getScaledInstance(btExit.getWidth() / 4, btExit.getHeight() - 10, Image.SCALE_DEFAULT));
        btExit.setIcon(ExitIcon);
        btExit.setFont(fontButton);
        Jp.add(btExit);

        lbTitle.setSize(100,50);
        lbTitle.setLocation(70,240);
        lbTitle.setFont(fontLabel);
        lbTitle.setForeground(Color.red);
        this.add(lbTitle);

        btHtml.setSize(100,35);
        btHtml.setLocation(65,300);
        btHtml.setFont(fontButton);
        btHtml.setEnabled(false);
        this.add(btHtml);

        btResult.setSize(100,35);
        btResult.setLocation(180,300);
        btResult.setFont(fontButton);
        btResult.setEnabled(false);
        this.add(btResult);

        tpResult.setEnabled(false);
        tpResult.setOpaque(false);
        tpResult.setFont(fontTextfield);
        spResult.setOpaque(false);
        spResult.setSize(1350,550);
        spResult.setLocation(65,345);
        spResult.setFont(fontTextfield);
        this.add(spResult);

        btHeightLight.setSize(80,40);
        btHeightLight.setLocation(450,900);
        btHeightLight.setFont(fontButton);
        btHeightLight.setEnabled(false);
        this.add(btHeightLight);

        btSaveFile.setSize(80,40);
        btSaveFile.setLocation(900,900);
        btSaveFile.setFont(fontButton);
        btSaveFile.setEnabled(false);
        this.add(btSaveFile);

        outButtons.add(btHtml);
        outButtons.add(btResult);
        outButtons.add(btHeightLight);
        outButtons.add(btSaveFile);
        Jp.setOpaque(false);
        this.add(Jp);
        this.setIconImage(imageIcon);
        GUIUtil.toCenter(this);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
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

    public void showByHtml(){
        btHtml.setForeground(Color.red);
        btResult.setForeground(Color.BLACK);
        tpResult.setText("");
        String htmlAll="";
        for(int i=0;i<htmls.size();i++){
            htmlAll+=htmls.get(i);
        }
        insertText(htmlAll,Color.white);
    }

    public void showByResult(){
        btResult.setForeground(Color.red);
        btHtml.setForeground(Color.BLACK);
        tpResult.setText("");
        String resultAll="";
        for(int i=0;i<results.size();i++){
            resultAll+=results.get(i);
        }

        insertText(resultAll,Color.white);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btBegin) {
            //清空
            Webs.clear();
            Sensitives.clear();
            htmls.clear();
            results.clear();
            //获取webs和sentives
            String[] webs=tfWebsite.getText().split(";");
            Webs.addAll(Arrays.asList(webs));
            String[] senstives=tfSenstive.getText().split(";");
            Sensitives.addAll(Arrays.asList(senstives));
            //开启进度条线程
            probar=new Probar(this);
            new Thread(probar).start();
            //开启爬虫线程
            Crawler crawler=new Crawler(Webs,htmls,results,probar,outButtons);
            crawler.start();
        }else if(e.getSource()==btHtml){
            showByHtml();
        }else if(e.getSource()==btResult){
            showByResult();
        }
    }
}