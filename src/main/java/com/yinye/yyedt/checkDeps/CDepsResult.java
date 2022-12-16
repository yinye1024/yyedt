package com.yinye.yyedt.checkDeps;

import com.yinye.yyedt.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CDepsResult {

    private List<String> depModList;
    private List<String> refedModList;

    public static CDepsResult newInstance(List<String> depModListP,List<String> refedModListP) {
        CDepsResult instance = new CDepsResult();
        instance.depModList = depModListP;
        instance.refedModList = refedModListP;
        return instance;
    }

    public String toText(List<String> filterList){
        List<String> depModFilterList = doFilter(this.depModList,filterList);
        List<String> refedModFilterList = doFilter(this.refedModList,filterList);
        return  buildResult(depModFilterList,refedModFilterList);
    }

    private List<String> doFilter(List<String> modList, List<String> filterList) {
        List<String> newList = new ArrayList<>();
        for (String modTmp : modList) {
            if(!filterList.contains(modTmp)){
                newList.add(modTmp);
            }
        }
        return newList;
    }

    private String buildResult(List<String> depModList, List<String> refedModList) {
        StringBuilder sb = new StringBuilder();
        sb.append("依赖模块列表:").append(StringUtils.getLineSeparator());
        if(depModList.size()>0){
            depModList.forEach(depModTmp->sb.append(depModTmp).append(StringUtils.getLineSeparator()));
        }else{
            sb.append("无").append(StringUtils.getLineSeparator());
        }

        sb.append(StringUtils.getLineSeparator());

        sb.append("被引用模块列表:").append(StringUtils.getLineSeparator());
        if(refedModList.size()>0){
            refedModList.forEach(depModTmp->sb.append(depModTmp).append(StringUtils.getLineSeparator()));
        }else{
            sb.append("无").append(StringUtils.getLineSeparator());
        }
        return sb.toString();
    }
}
