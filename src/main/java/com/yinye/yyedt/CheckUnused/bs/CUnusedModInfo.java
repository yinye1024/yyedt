package com.yinye.yyedt.CheckUnused.bs;

import com.yinye.yyedt.utils.FileUtils;
import com.yinye.yyedt.utils.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CUnusedModInfo {

    private String modName;

    private List<String> methodList = new ArrayList<>();
    private Map<String,Integer> depModMap = new HashMap<String, Integer>();

    public static CUnusedModInfo fromFile(File file) throws IOException {
        CUnusedModInfo instance = new CUnusedModInfo();
        HashMap<Integer, String> lineMap = FileUtils.getLineMap(file);
        String modName = StringUtils.substringBeforeLast(file.getName(),".erl");

        Map<String, Integer> accDepModMap = new HashMap<String, Integer>();
        List<String> accMethodList = new ArrayList<>();
        for (Map.Entry<Integer,String> entry:lineMap.entrySet()){
//            Integer index = entry.getKey();
            String lineText = entry.getValue();
            lineText = StringUtils.substringBefore(lineText,'%');
            accDepModMap(lineText,accDepModMap);
            accMethodList(lineText,accMethodList);
        }
        instance.modName = modName;
        instance.depModMap = accDepModMap;
        instance.methodList = accMethodList;
        return instance;
    }
    private static void accDepModMap(String lineText, Map<String, Integer> accDepsMap) {
        Pattern p = Pattern.compile("(\\w+):(\\w+)");//正则表达式
        Matcher m = p.matcher(lineText);//
        while (m.find()) {
//            String depMod = m.group(1);
//            String depMethod = m.group(2);
            String modAndMethod = m.group(0);
            accDepsMap.put(modAndMethod,1);
        }
    }
    private static void accMethodList(String lineText, List<String> accMethodList) {
        List<String> depList = getDepsFromLine(lineText);
        List<String> methodList = getMethodsFromLIne(lineText, depList);
        accMethodList.addAll(methodList);
    }

    private static List<String> getDepsFromLine(String lineText){
        List<String> depList = new ArrayList<>();
        Pattern pattern = Pattern.compile(":(\\w+)/(\\d+)");
        Matcher matcher = pattern.matcher(lineText);
        while (matcher.find()) {
            depList.add(matcher.group(1));
        }
        return depList;
    }
    private static List<String> getMethodsFromLIne(String lineText, List<String> DepList){
        List<String> methodList = new ArrayList<>();
        Pattern pattern = Pattern.compile("(\\w+)/(\\d+)");
        Matcher matcher = pattern.matcher(lineText);
        while (matcher.find()) {
            String method = matcher.group(1);
            if(!DepList.contains(method)){
                methodList.add(method);
            }
        }
        return methodList;
    }


    public String getModName() {
        return modName;
    }

    public Map<String, Integer> getDepModMap() {
        return depModMap;
    }

    @Override
    public boolean equals(Object obj) {
        return this.modName.equals(((CUnusedModInfo)obj).getModName());
    }

    public List<String> getMethodList() {
        return methodList;
    }
}
