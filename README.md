# JavaURLCrawler
## JavaURL爬虫实验
实验目的：利用Java URL类爬取网页html源代码，并对其中内容进行提取。  
实践的内容或要求：  
1.编写界面，输入一个网址，能够爬取该网址上所有的HTML源代码。  
2.对网址中的文本进行提取。    
3.建立敏感词库，用文本文件保存。  
4.将该网址所对应的文本中的敏感词提取并高亮显示。  
5.编写文本文件，可以存入多个网址；程序可爬取这些网址中的文本内容，将敏感词记录存入另一个文件，格式自定。  
6.编写一个主界面,整合上述功能。  

## 其他功能  
除实验要求外增加的功能：  
1、文件保存。可以保存从html源码中提取的内容。  
2、可以下载部分网页图片（img标签与background属性）  

## 简单说明
Crawler：开线程爬html源码、提取内容、下载图片  
BasicFrame：界面显示    
hlListener：高亮  
rfListener：读取文件  
sfListener：保存文件  
main：主函数  
GUIUtil：居中  
Probar：进度条显示  

## **
lib中beautyeye_lnf.jar来自于[https://github.com/JackJiang2011/beautyeye](https://github.com/JackJiang2011/beautyeye). 
