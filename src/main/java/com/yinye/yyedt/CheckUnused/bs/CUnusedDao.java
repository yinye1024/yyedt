package com.yinye.yyedt.CheckUnused.bs;

import com.yinye.yyedt.utils.PluginFileDao;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CUnusedDao {

    final private static CUnusedDao instance = new CUnusedDao();

    public static CUnusedDao getInstance() {
        return instance;
    }

    final private static int KEY_FILTER = 1;
    final private static int KEY_INCLUDE = 2;

    final private String dbName = CUnusedDao.class.getName();
    @SuppressWarnings("rawtypes")
    public List<String> loadFilter(String workPath){
        Map dataMap = PluginFileDao.getInstance().getData(workPath,this.dbName);
        String filterText = (String) dataMap.get(KEY_FILTER);
        String includeText = (String) dataMap.get(KEY_INCLUDE);
        if(dataMap.isEmpty()){
            return null;
        }else{
            return Arrays.asList(filterText,includeText);
        }
    }
    @SuppressWarnings("rawtype")
    public void saveFilter(String workPath,String filterText,String includeText) throws IOException {
        Map<Integer,String> dataMap = PluginFileDao.getInstance().getData(workPath,this.dbName);
        dataMap.put(KEY_FILTER,filterText);
        dataMap.put(KEY_INCLUDE,includeText);
        PluginFileDao.getInstance().saveData(workPath,this.dbName,dataMap);
    }


}
