package com.crawler.main;

import java.io.*;

public class WriterInFile {
	
	public static boolean writeIntoFile(String content, String filePath, 
            boolean isAppend) { 
        boolean isSuccess = true; 
        // �����ļ��� 
        int index = filePath.lastIndexOf("/"); 
        String dir = filePath.substring(0, index); 
        // �����ļ���·�� 
        File fileDir = new File(dir); 
        fileDir.mkdirs(); 
        // �ٴ���·���µ��ļ� 
        File file = null; 
        try { 
            file = new File(filePath); 
            file.createNewFile(); 
        } catch (IOException e) { 
            isSuccess = false; 
            e.printStackTrace(); 
        } 
        // д���ļ� 
        FileWriter fileWriter = null; 
        try { 
            fileWriter = new FileWriter(file, isAppend); 
            fileWriter.write(content); 
            fileWriter.flush(); 
        } catch (IOException e) { 
            isSuccess = false; 
            e.printStackTrace(); 
        } finally { 
            try { 
                if (fileWriter != null) 
                    fileWriter.close(); 
            } catch (IOException e) { 
                e.printStackTrace(); 
            } 
        } 
 
        return isSuccess; 
    }  

}
