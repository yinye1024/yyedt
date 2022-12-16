package com.yinye.yyedt.copyReplace.bs;

import java.util.LinkedHashMap;
import java.util.Map;

public class CRTokenData {

    private LinkedHashMap<String,String> textTokenMap;

    private LinkedHashMap<String,String> fnTokenMap;

    private LinkedHashMap<String,String> dnTokenMap;

    public LinkedHashMap<String, String> getTextTokenMap() {
        return textTokenMap;
    }

    public CRTokenData withTextTokenMap(LinkedHashMap<String, String> textTokenMap) {
        this.textTokenMap = textTokenMap;
        return this;
    }

    public LinkedHashMap<String, String> getFnTokenMap() {
        return fnTokenMap;
    }

    public CRTokenData withFnTokenMap(LinkedHashMap<String, String> fnTokenMap) {
        this.fnTokenMap = fnTokenMap;
        return this;
    }

    public LinkedHashMap<String, String> getDnTokenMap() {
        return dnTokenMap;
    }

    public CRTokenData withDnTokenMap(LinkedHashMap<String, String> dnTokenMap) {
        this.dnTokenMap = dnTokenMap;
        return this;
    }


    public CRTokenData buildToTmpTokenData() {
        CRTokenData tokenData = new CRTokenData();
        LinkedHashMap<String, String> dnTokenMap = makeTempValueUnique(this.getDnTokenMap());
        LinkedHashMap<String, String> fnTokenMap = makeTempValueUnique(this.getFnTokenMap());
        LinkedHashMap<String, String> textTokenMap = makeTempValueUnique(this.getTextTokenMap());
        return tokenData.withDnTokenMap(dnTokenMap)
                .withFnTokenMap(fnTokenMap)
                .withTextTokenMap(textTokenMap);
    }

    public CRTokenData buildToOutputTokenData() {
        CRTokenData tokenData = new CRTokenData();
//        LinkedHashMap<String, String> dnTokenMap = makeKeyMatchTempValue(this.getDnTokenMap());
//        LinkedHashMap<String, String> fnTokenMap = makeKeyMatchTempValue(this.getFnTokenMap());
//        LinkedHashMap<String, String> textTokenMap = makeKeyMatchTempValue(this.getTextTokenMap());
        return tokenData.withDnTokenMap(this.dnTokenMap)
                .withFnTokenMap(this.fnTokenMap)
                .withTextTokenMap(this.textTokenMap);
    }

    // 让替换的value唯一 = {key}，以免后面的token部分替换掉前面的token 比方 <"abc","a1">  <"a","ef">
    private LinkedHashMap<String, String> makeTempValueUnique(LinkedHashMap<String,String> tokenMap){
        final LinkedHashMap<String, String> replaceMap = new LinkedHashMap<String, String>();
        for (Map.Entry<String, String> kv:tokenMap.entrySet()) {
            replaceMap.put(kv.getKey(), "{"+kv.getKey()+"}");
        }
        return replaceMap;
    }
    // 匹配上面的修正key，这样可以保证大概率不会出错。
    private LinkedHashMap<String, String> makeKeyMatchTempValue(LinkedHashMap<String,String> tokenMap){
        final LinkedHashMap<String, String> replaceMap = new LinkedHashMap<String, String>();
        for (Map.Entry<String, String> kv:tokenMap.entrySet()) {
            replaceMap.put("{"+kv.getKey()+"}", kv.getValue());
        }
        return replaceMap;
    }

}
