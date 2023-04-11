package com.yinye.yyedt.utils;

import java.util.ArrayList;
import java.util.List;

public class TextUtils {

    public static TextUtils instance = new TextUtils();

    public static TextUtils getInstance() {
        return instance;
    }

    public List<String> doTextListFilter(List<String> textList, List<String> filterList, List<String> includeList) {
        List<String> newList = new ArrayList<>();
        for (String textTmp : textList) {
            if(!depContainsFilterText(textTmp,filterList) && depContainsIncludeText(textTmp,includeList)){
                newList.add(textTmp);
            }
        }
        return newList;
    }
    private boolean depContainsFilterText(String text, List<String> filterList) {
        boolean flag = false;
        for (String filterTmp : filterList) {
            if(!StringUtils.isEmpty(filterTmp) && text.contains(filterTmp)){
                flag = true;
                break;
            }
        }
        return flag;
    }
    private boolean depContainsIncludeText(String text, List<String> includeList) {
        boolean flag = false;
        if(includeList.isEmpty()){
            flag = true;
        }else{
            for (String includeTmp : includeList) {
                if(text.contains(includeTmp)){
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }


}
