package com.crawler.main;

import java.io.*;

public class WriterInFile {
	
	public static boolean writeIntoFile(String content, String filePath, 
            boolean isAppend) { 
        boolean isSuccess = true; 
        // 过滤文件名 
        int index = filePath.lastIndexOf("/"); 
        String dir = filePath.substring(0, index); 
        // 创建文件的路径 
        File fileDir = new File(dir); 
        fileDir.mkdirs(); 
        // 再创建路径下的文件 
        File file = null; 
        try { 
            file = new File(filePath); 
            file.createNewFile(); 
        } catch (IOException e) { 
            isSuccess = false; 
            e.printStackTrace(); 
        } 
        // 写入文件 
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
