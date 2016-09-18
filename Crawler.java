package com.crawler.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Crawler {
	
	public static String sendGet(String url){

			//存储得到的内容
			String result = "";
			
			BufferedReader in = null;
			
			try {
				
				URL realUrl = new URL(url);
				//初始化连接
				URLConnection openConnection = realUrl.openConnection();
				//开启连接
				openConnection.connect();
				//读取URL的响应
				in  = new BufferedReader(new InputStreamReader(openConnection.getInputStream(),"UTF-8"));
				
				//将URL的响应存储起来
				String next = "";
				while(in.readLine()!=null){
					result = result + in.readLine();
				}
			} catch (Exception e) {
				System.out.println("请求发生异常" + e);
				e.printStackTrace();
			}finally{
				if(in != null){
					try {
						in.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			return result;
		
	}
	//利用正则表达式找到想要获取的内容  reult:内容 
	public static ArrayList<Zhihu> RegexString(String reult){
		
		ArrayList<Zhihu> list  = new ArrayList<Zhihu>();
		/*//匹配标题
			// 定义正则
		  Pattern pattern = Pattern.compile("question_link.+?>(.+?)<");
		  // 定义一个matcher用来做匹配
		  Matcher matcher = pattern.matcher(reult);
		*/
		  //匹配链接
			// 定义正则
		  Pattern urlPattern  = Pattern.compile("<h2>.+?question_link.+?href=\"(.+?)\".+?</h2>");
		  // 定义一个matcher用来做匹配
		  Matcher urlmatcher = urlPattern.matcher(reult);
		  boolean find = urlmatcher.find();
		  while(find){
			// 定义一个对象来存储抓取到的信息
			   Zhihu zhuhu = new Zhihu(urlmatcher.group(1));
			//   zhuhu.question = urlmatcher.group(1);
			   zhuhu.zhihuUrl = "http://www.zhihu.com" + urlmatcher.group(1);
			//   System.out.println(urlmatcher.group(1));
			  list.add(zhuhu);
			  find =  urlmatcher.find();
			  
		  }

		  return list;
		
	}
	
	 public static void main(String[] args) {
		  // 定义即将访问的链接
		  String url = "http://www.zhihu.com/explore/recommendations";
		
		  // 访问链接并获取页面内容
		  String result = sendGet(url);
		  System.out.println(result);
		
		  //使用正则匹配想要的内容
		  ArrayList<Zhihu>  imgSrc = RegexString(result);
		//  ArrayList<String> imgSrc2 = RegexString(result, "post-link.+?>(.+?)<");
		//  imgSrc.addAll(imgSrc2);
		  //打印结果
		  for(int i=0;i<imgSrc.size();i++){
		  System.out.println(imgSrc.get(i));
		  }
		  
		  for (Zhihu zhihu : imgSrc) { 
			  WriterInFile.writeIntoFile(zhihu.writeString(), 
	                    "G:/知乎_编辑推荐.txt", true); 
	        }  

		//  System.out.println(imgSrc);
	 }
	

}
