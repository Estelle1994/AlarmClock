package com.dev.alarmclock.util;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by ${Estelle} on 2018/6/26.
 */

public class FileManager {
    private String SD_PATH;
    private String fileName;
    private String dirName;

    public String getSD_PATH() {
        return SD_PATH;
    }
    public FileManager() {
        //得到当前外部存储设备的目录
        SD_PATH = Environment.getExternalStorageDirectory() + "/";
    }
    /**
     * 在SD卡上创建文件
     *
     * @throws IOException
     */
    public File creatSDFile(String dirName , String fileName) throws IOException {
        this.fileName = fileName;
        this.dirName = dirName;
        File file = new File(creatSDDir(dirName), fileName);
        return file;
    }


    public String getFileWholePath(){
        return SD_PATH + dirName + "/" + fileName;
    }

    /**
     * 在SD卡上创建目录
     *
     * @param dirName
     */
    public File creatSDDir(String dirName) {
        File dir = new File(SD_PATH + dirName);
        dir.mkdir();
        return dir;
    }



    /**
     * 判断SD卡上的文件夹是否存在
     */
    public boolean isFileExist(String fileName ,String dirName){
        File file = new File(SD_PATH +dirName+"/"+ fileName);
        return file.exists();
    }

    /**
     * 将一个InputStream里面的数据写入到SD卡中
     */
    public File saveToSdCard(String dirName,String fileName,InputStream input){
        File file = null;
        OutputStream output = null;
        try{
            //创建目录
            creatSDDir(dirName);
            //建立文件
            file = creatSDFile(dirName ,fileName);
            output = new FileOutputStream(file);
            byte buffer [] = new byte[5 * 1024];
            int count = 0;
            while((count = input.read(buffer)) != -1){
                String str = new String(buffer,0,count);
                //str = new String(str.getBytes("iso-8859-1"),"utf-8");
                System.out.println("---------File Manager----start--------");
                System.out.println(str);
                System.out.println("---------File Manager-----end-------");
                output.write(buffer);
            }
            output.flush();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            try{
                output.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        return file;
    }

}
