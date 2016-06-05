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

			//�洢�õ�������
			String result = "";
			
			BufferedReader in = null;
			
			try {
				
				URL realUrl = new URL(url);
				//��ʼ������
				URLConnection openConnection = realUrl.openConnection();
				//��������
				openConnection.connect();
				//��ȡURL����Ӧ
				in  = new BufferedReader(new InputStreamReader(openConnection.getInputStream(),"UTF-8"));
				
				//��URL����Ӧ�洢����
				String next = "";
				while(in.readLine()!=null){
					result = result + in.readLine();
				}
			} catch (Exception e) {
				System.out.println("�������쳣" + e);
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
	//����������ʽ�ҵ���Ҫ��ȡ������  reult:���� 
	public static ArrayList<Zhihu> RegexString(String reult){
		
		ArrayList<Zhihu> list  = new ArrayList<Zhihu>();
		/*//ƥ�����
			// ��������
		  Pattern pattern = Pattern.compile("question_link.+?>(.+?)<");
		  // ����һ��matcher������ƥ��
		  Matcher matcher = pattern.matcher(reult);
		*/
		  //ƥ������
			// ��������
		  Pattern urlPattern  = Pattern.compile("<h2>.+?question_link.+?href=\"(.+?)\".+?</h2>");
		  // ����һ��matcher������ƥ��
		  Matcher urlmatcher = urlPattern.matcher(reult);
		  boolean find = urlmatcher.find();
		  while(find){
			// ����һ���������洢ץȡ������Ϣ
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
		  // ���弴�����ʵ�����
		  String url = "http://www.zhihu.com/explore/recommendations";
		
		  // �������Ӳ���ȡҳ������
		  String result = sendGet(url);
		  System.out.println(result);
		
		  //ʹ������ƥ����Ҫ������
		  ArrayList<Zhihu>  imgSrc = RegexString(result);
		//  ArrayList<String> imgSrc2 = RegexString(result, "post-link.+?>(.+?)<");
		//  imgSrc.addAll(imgSrc2);
		  //��ӡ���
		  for(int i=0;i<imgSrc.size();i++){
		  System.out.println(imgSrc.get(i));
		  }
		  
		  for (Zhihu zhihu : imgSrc) { 
			  WriterInFile.writeIntoFile(zhihu.writeString(), 
	                    "G:/֪��_�༭�Ƽ�.txt", true); 
	        }  

		//  System.out.println(imgSrc);
	 }
	

}
