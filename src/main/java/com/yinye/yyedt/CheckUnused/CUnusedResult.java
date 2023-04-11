package com.yinye.yyedt.CheckUnused;

import com.yinye.yyedt.utils.StringUtils;
import com.yinye.yyedt.utils.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class CUnusedResult {

    private List<String> unusedList ;

    public static CUnusedResult newInstance(List<String> unusedList ) {
        CUnusedResult instance = new CUnusedResult();
        instance.unusedList = unusedList;
        return instance;
    }

    public String toText(List<String> filterList,List<String> includeList){
        List<String> unusedListP = TextUtils.getInstance().doTextListFilter(this.unusedList, filterList,includeList);
        return  buildResult(unusedListP);
    }

    private String buildResult(List<String> unusedListP) {
        StringBuilder sb = new StringBuilder();
        sb.append("无引用方法列表:").append(StringUtils.getLineSeparator());
        if(unusedListP.size()>0){
            unusedListP.forEach(depModTmp->sb.append(depModTmp).append(StringUtils.getLineSeparator()));
        }else{
            sb.append("无").append(StringUtils.getLineSeparator());
        }

        sb.append(StringUtils.getLineSeparator());
        return sb.toString();
    }
}
