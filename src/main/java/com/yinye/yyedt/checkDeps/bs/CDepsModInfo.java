package com.yinye.yyedt.checkDeps.bs;

import com.yinye.yyedt.utils.FileUtils;
import com.yinye.yyedt.utils.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CDepsModInfo {

    private String modName;
    private Map<String,Integer> depModMap = new HashMap<String, Integer>();

    public static CDepsModInfo fromFile(File file) throws IOException {
        CDepsModInfo instance = new CDepsModInfo();
        HashMap<Integer, String> lineMap = FileUtils.getLineMap(file);
        String modName = StringUtils.substringBeforeLast(file.getName(),".erl");

        Map<String, Integer> accDepModMap = new HashMap<String, Integer>();
        for (Map.Entry<Integer,String> entry:lineMap.entrySet()){
//            Integer index = entry.getKey();
            String lineText = entry.getValue();
            lineText = StringUtils.substringBefore(lineText,'%');
            accDepModMap(lineText,accDepModMap);
        }
        instance.modName = modName;
        instance.depModMap = accDepModMap;
        return instance;
    }
    private static void accDepModMap(String lineText, Map<String, Integer> accDepModMap) {
        Pattern p = Pattern.compile("(\\w+):(\\w+)");//正则表达式
        Matcher m = p.matcher(lineText);//
        while (m.find()) {
            String depMod = m.group(1);
//            String depMethod = m.group(2);
//            String modAndMethod = m.group(0);
            accDepModMap.put(depMod,1);
        }
    }

    public String getModName() {
        return modName;
    }

    public Map<String, Integer> getDepModMap() {
        return depModMap;
    }

    @Override
    public boolean equals(Object obj) {
        return this.modName.equals(((CDepsModInfo)obj).getModName());
    }
}
