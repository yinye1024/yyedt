package com.yinye.yyedt.utils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 用作插件业务数据的本地文件存储
 *  数据结构用map
 */
public class PluginFileDao {

    private static PluginFileDao instance = new PluginFileDao();

    public static PluginFileDao getInstance() {
        return instance;
    }

    @SuppressWarnings("rawtypes")
    public Map getData(String workPath, String dbName){
        File dbDir = getDbDir(workPath);
        File dbFile = new File(dbDir,dbName);
        if(dbFile.exists()){
            return (Map) loadObjFromFile(dbFile);
        }else{
            return new HashMap<>();
        }
    }
    @SuppressWarnings({"rawtypes", "ResultOfMethodCallIgnored"})
    public void saveData(String workPath, String dbName, Map dataMap) throws IOException {
        File dbDir = getDbDir(workPath);
        File dbFile = new File(dbDir,dbName);
        if(!dbFile.exists()){
            dbFile.createNewFile();
        }
        saveObjToFile(dbFile,dataMap);
    }

    private File getDbDir(String workPath){
        String DbName = "db";
        File dbDir = FileUtils.newWorkspaceDir(workPath,DbName);
        return dbDir;
    }

    private Object loadObjFromFile(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object ob = ois.readObject();
            return ob;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveObjToFile(File filePath,Object ob) {
        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            ObjectOutputStream ops = new ObjectOutputStream(fos);
            ops.writeObject(ob);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) throws IOException {

        String workPath = "D:\\allen_github\\yinye1024\\yyedt\\test";
        String dbName = "testdb";
        Map<Object,Object> dataMap = PluginFileDao.getInstance().getData(workPath,dbName);
        dataMap.put("111","aaa");
        dataMap.put("121","aaa2");
        dataMap.put(11,"aaa");
        dataMap.put(12,"aaa");
        PluginFileDao.getInstance().saveData(workPath,dbName,dataMap);
        Map dataMap2 = PluginFileDao.getInstance().getData(workPath,dbName);
        System.out.print(dataMap2.get(11));
    }
}
