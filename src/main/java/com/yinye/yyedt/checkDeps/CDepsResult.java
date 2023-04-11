package com.yinye.yyedt.checkDeps;

import com.yinye.yyedt.utils.StringUtils;
import com.yinye.yyedt.utils.TextUtils;

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

    public String toText(List<String> filterList,List<String>  includeList){
        List<String> depModFilterList = TextUtils.getInstance().doTextListFilter(this.depModList,filterList,includeList);
        List<String> refedModFilterList = TextUtils.getInstance().doTextListFilter(this.refedModList,filterList,includeList);
        return  buildResult(depModFilterList,refedModFilterList);
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
