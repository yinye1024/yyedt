package com.yinye.yyedt.CheckUnused.bs;

import com.yinye.yyedt.CheckUnused.CUnusedResult;
import com.yinye.yyedt.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CUnusedModInfoHelper {

    private static CUnusedModInfoHelper instance = new CUnusedModInfoHelper();
    public static CUnusedModInfoHelper getInstance(){
        return instance;
    }

    public CUnusedResult checkUnused(String oriDirPath, String destDirPath) throws IOException {
        List<CUnusedModInfo> destModInfoList = getModInfoList(destDirPath);
        Map<String,Integer> allUsedMap = getAllUsedMap(destModInfoList);  // <"mod:method",1>

        List<CUnusedModInfo> oriModInfoList = getModInfoList(oriDirPath);
        List<String> allUnusedList = getAllUnusedList(oriModInfoList,allUsedMap);

        return CUnusedResult.newInstance(allUnusedList);
    }

    private Map<String,Integer> getAllUsedMap(List<CUnusedModInfo> modInfoList) throws IOException {
        Map<String, Integer> usedMap = new HashMap<String, Integer>();
        for (CUnusedModInfo modInfoTmp : modInfoList) {
            Map<String, Integer> depMap = modInfoTmp.getDepModMap();
            ArrayList<String> depList = new ArrayList<String>(depMap.keySet());
            for (String dep : depList) {
                usedMap.put(dep, 1);
            }
        }
        return usedMap;
    }
    private List<String> getAllUnusedList(List<CUnusedModInfo> modInfoList,  Map<String,Integer> allUsedMap ) throws IOException {
        List<String> unusedList = new ArrayList<>();
        for (CUnusedModInfo modInfo : modInfoList) {
            List<String> methodList = modInfo.getMethodList();
            for (String method:methodList){
                String dep = modInfo.getModName()+":"+method;
                if(!allUsedMap.containsKey(dep)){
                    unusedList.add(dep);
                }
            }
        }
        return unusedList;
    }

    private List<CUnusedModInfo> getModInfoList(String dirPath) throws IOException {
        Map<String,CUnusedModInfo> modInfoMap = new HashMap<String,CUnusedModInfo>();
        List<File>  erlFileList = FileUtils.getAllErlFiles(dirPath);
        for (File fileTmp : erlFileList) {
            CUnusedModInfo modInfoTmp = CUnusedModInfo.fromFile(fileTmp);
            modInfoMap.put(modInfoTmp.getModName(),modInfoTmp);
        }
        return new ArrayList<>(modInfoMap.values());
    }

    public static void main(String[] args) throws IOException {
        String oriDirPath = "D:\\allen_test\\dev_tools\\code_gen_test\\codeGenTmpDir\\input\\yymg\\cursor";
        String destDirPath = "D:\\allen_test\\dev_tools\\code_gen_test\\codeGenTmpDir\\input\\yymg";
        CUnusedResult result = CUnusedModInfoHelper.getInstance().checkUnused(oriDirPath,destDirPath);
        System.out.print(result.toText(new ArrayList<>(),new ArrayList<>()));
    }
}


