package com.yinye.yyedt.checkDeps.bs;

import com.yinye.yyedt.checkDeps.CDepsResult;
import com.yinye.yyedt.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CDepsModInfoHelper {

    private static CDepsModInfoHelper instance = new CDepsModInfoHelper();
    public static CDepsModInfoHelper getInstance(){
        return instance;
    }

    public CDepsResult checkDeps(String oriDirPath, String destDirPath) throws IOException {
        // 依赖模块列表
        List<String> depModList =  findDepModList(oriDirPath, destDirPath);
        // 被引用模块列表
        List<String> refedModList = findRefedModList(oriDirPath,destDirPath);
        return CDepsResult.newInstance(depModList, refedModList);
    }


    private List<String> findDepModList(String oriDirPath, String destDirPath) throws IOException {
        List<CDepsModInfo> oriModInfoList = getModInfoList(oriDirPath);
        List<String> oriModList = getModList(oriModInfoList);
        List<String> oriModDepList = getDepList(oriModInfoList);

        List<CDepsModInfo> destModInfoList = getModInfoList(destDirPath);
        List<String> destModList = getModList(destModInfoList);
        destModList.removeAll(oriModList);
        List<String> depModList = findOriInDest(oriModDepList,destModList);
        return depModList;
    }
    private List<String> findRefedModList(String oriDirPath, String destDirPath) throws IOException {
        Map<String, CDepsModInfo> oriModInfoMap = getModInfoMap(oriDirPath);
        Map<String, CDepsModInfo> destModInfoMap = getModInfoMap(destDirPath);
        //从 destModInfoMap 移除掉包含oriModInfoMap 里的mod， 也就是处理 dest 目录包含 ori 目录 的情况
        destModInfoMap.keySet().removeAll(oriModInfoMap.keySet());

        List<CDepsModInfo> destModInfoList = new ArrayList<>(destModInfoMap.values());
        List<String> destModDepList = getDepList(destModInfoList);

        List<CDepsModInfo> oriModInfoList = getModInfoList(oriDirPath);
        List<String> oriModList = getModList(oriModInfoList);
        List<String> refedModList = findOriInDest(destModDepList,oriModList);
        return refedModList;
    }

    private List<String> getModList(List<CDepsModInfo> modInfoList) {
        Map<String,Integer> modMap = new HashMap<String,Integer>();
        for (CDepsModInfo modInfoTmp : modInfoList) {
            modMap.put(modInfoTmp.getModName(),1);
        }
        return new ArrayList<String>(modMap.keySet());
    }

    private List<String> findOriInDest(List<String> depModList, List<String> checkModList) {
        List<String> modList = depModList.stream()
                .filter(modTmp-> checkModList.contains(modTmp)).collect(Collectors.toList());
        return modList;
    }

    private List<String> getDepList(List<CDepsModInfo> modInfoList) {
        Map<String,Integer> depMap = new HashMap<String,Integer>();
        for (CDepsModInfo modInfoTmp : modInfoList) {
            depMap.putAll(modInfoTmp.getDepModMap());
        }
        return new ArrayList<>(depMap.keySet());
    }
    private List<CDepsModInfo> getModInfoList(String dirPath) throws IOException {
        Map<String, CDepsModInfo> modInfoMap = getModInfoMap(dirPath);
        return new ArrayList<>(modInfoMap.values());
    }
    private Map<String, CDepsModInfo> getModInfoMap(String dirPath) throws IOException {
        Map<String,CDepsModInfo> modInfoMap = new HashMap<String,CDepsModInfo>();
        List<File>  erlFileList = FileUtils.getAllErlFiles(dirPath);
        for (File fileTmp : erlFileList) {
            CDepsModInfo modInfoTmp = CDepsModInfo.fromFile(fileTmp);
            modInfoMap.put(modInfoTmp.getModName(),modInfoTmp);
        }
        return modInfoMap;
    }

}


