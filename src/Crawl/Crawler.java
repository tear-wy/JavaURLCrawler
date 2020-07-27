package Crawl;

import util.Probar;

import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Vector;

public class Crawler extends Thread{
    private static int filesNum=0;
    private Vector<String> Webs=null;
    private Vector<String> htmls=null;
    private Vector<String> results=null;
    private Probar probar=null;
    private Vector<JButton> outButtons=null;

    public Crawler(Vector<String>Webs,Vector<String> htmls,Vector<String> results,Probar probar,Vector<JButton> outButtons){
        this.Webs=Webs;
        this.htmls=htmls;
        this.results=results;
        this.probar=probar;
        this.outButtons=outButtons;
    }

    //依据webURL提取html
    public String crawler(String web){
        try {
            String charset="utf-8";
            if(web.contains("tmall")||web.contains("taobao"))
                charset="gbk";

            URL url=new URL(web);
            URLConnection urlConnection=url.openConnection();
            InputStream inputStream=urlConnection.getInputStream();
            BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream,charset));
            String s=reader.readLine();
            String html="";
            while(s!=null){
                html+=s+"\n";
                s=reader.readLine();
            }
            return html;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "This Is Not a Right Url";

    }

    private int kindOfImg(String url){
        int stratIndex=url.lastIndexOf(".");
        char kind=url.substring(stratIndex).charAt(1);
        switch (kind){
            case 'j':return 0;
            case 'g':return 1;
            case 'p':return 2;
            default: return 3;
        }
    }

    //依据Html提取ImgURL
    public void crawlerImg(String html){
        String[] strs=html.split("<");
        ArrayList<String> ImgUrls=new ArrayList<String>();
        for(String str:strs){
            str=str.replaceAll("\\s*|\t|\r|\n","");//去除空格
            if(str.startsWith("img"))
            {
                int startIndex=str.indexOf("src=");
                int endIndex;
                int kind=kindOfImg(str);
                switch (kind){
                    case 0://.jpg|.jpeg类格式
                        endIndex=str.indexOf(".j");
                        str=str.substring(startIndex,endIndex)+".jpg";
                        break;
                    case 1://.gif类格式
                        endIndex=str.indexOf(".g");
                        str=str.substring(startIndex,endIndex)+".gif";
                        break;
                    case 2://.png类格式
                        endIndex=str.indexOf(".p");
                        str=str.substring(startIndex,endIndex)+".png";
                        break;
                    case 3:
                        continue;
                }
                str=str.replace("src=","").replace("\"","");
                ImgUrls.add(str);
            }
            else if(str.contains("background:url(")){
                int startIndex=str.indexOf("background:url(");
                int endIndex=str.indexOf(")");
                str=str.substring(startIndex,endIndex);
                str=str.replace("background:url(","");
                ImgUrls.add(str);
            }
        }
        //开始下载
        int i=0;
        //创建文件夹
        String filesPath="E:\\URL爬虫测试\\Img\\"+"web"+Integer.toString(filesNum);//文件目录
        File files=new File(filesPath);
        if(!files.exists()){
            files.mkdirs();
        }

        for(String imgUrl:ImgUrls){
            try {
                if(imgUrl.startsWith("//")){
                    imgUrl="https:"+imgUrl;
                }else if(imgUrl.startsWith("http")){

                }else{
                    imgUrl="https://"+imgUrl;
                }

                System.out.println("IMG:  "+imgUrl);
                HttpURLConnection httpURLConnection=(HttpURLConnection) new URL(imgUrl).openConnection();
                httpURLConnection.setRequestMethod("GET");//请求方式
                httpURLConnection.setConnectTimeout(15000);
                httpURLConnection.setReadTimeout(20000);
                InputStream inputStream=httpURLConnection.getInputStream();
                //开始下载
                String suffix;
                int kind=kindOfImg(imgUrl);
                switch (kind){
                    case 0:
                        suffix=".jpg";
                        break;
                    case 1:
                        suffix=".gif";
                        break;
                    case 2:
                        suffix=".png";
                        break;
                    default:
                        suffix=".txt";
                        break;
                }
                String fileName=Integer.toString(i)+suffix;//文件名
                i++;
                File file=new File(filesPath+"\\"+fileName);
                FileOutputStream out=new FileOutputStream(file);
                int data=0;
                while((data=inputStream.read())!=-1){
                    out.write(data);
                }
                probar.insertLog(imgUrl+"爬取成功");
                inputStream.close();
                out.close();
            } catch (IOException e) {
                probar.insertLog(imgUrl+"爬取失败");
                e.printStackTrace();
            }
        }
        probar.insertLog("存储位置："+"E:\\URL爬虫测试\\Img\\"+"web"+Integer.toString(filesNum));
        filesNum++;
    }

    //根据Html提取文字
    public static String analysis(String html){
        String htmlReg="<[^>]+>";//除html标签
        String scriptReg="<script[^>]*?>[\\s\\S]*?</script>";//除script
        String spReg="&[a-zA-Z]{1,10};";//除特殊字符
        String sp2Reg= "\\s*|\t|\r|\n";//除换行、空格
        String styleReg="<style[^>]*?>[\\s\\S]*?</style>";//除style
        String result=html.replaceAll(scriptReg,"").replaceAll(styleReg,"").replaceAll(sp2Reg,"").replaceAll(htmlReg,"").replaceAll(spReg,"");
        return result;
    }

    @Override
    public void run() {
        super.run();
        for(String web:Webs){
            //爬取html源码和Img
            String html=crawler("https://"+web);
            htmls.add("*** "+web+" ***\n"+html+"\n");
            results.add("*** "+web+" ***\n"+analysis(html)+"\n");
            probar.insertLog("正在获取 "+web+" 的图片");
            crawlerImg(html);
        }
        probar.setCanRun(false);
        for(JButton bt:outButtons){
            bt.setEnabled(true);
        }
    }
}
