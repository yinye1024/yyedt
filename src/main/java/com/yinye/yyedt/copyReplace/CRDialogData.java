package com.yinye.yyedt.copyReplace;

import com.yinye.yyedt.copyReplace.bs.CRTokenData;

import java.util.LinkedHashMap;

public class CRDialogData {

    private String textReplace;

    private String fnReplace;

    private String dnReplace;

    public CRDialogData(String textReplace, String fnReplace, String dnReplace) {
        this.textReplace = textReplace;
        this.fnReplace = fnReplace;
        this.dnReplace = dnReplace;
    }

    public CRTokenData buildCRTokenData(){
        LinkedHashMap<String,String> textTokenMap = getTokenMap(this.textReplace);
        LinkedHashMap<String,String> fnTokenMap = getTokenMap(this.fnReplace);
        LinkedHashMap<String,String> dnTokenMap = getTokenMap(this.dnReplace);
        CRTokenData tokenData = new CRTokenData();
        return tokenData.withDnTokenMap(dnTokenMap)
                .withFnTokenMap(fnTokenMap)
                .withTextTokenMap(textTokenMap);
    }

    /**
     * 得到文本 token
     * @return
     */
    private LinkedHashMap<String, String> getTokenMap(String text){
        LinkedHashMap<String, String> tokenMap = new LinkedHashMap<>();
        //因为是ui，所以要用"\\r?\\n" 做换行符，不能用 System.lineSeparator()
        String[] split = text.split("\\r?\\n");
        String[] temSplit = null;
        String srcToken = null;
        String tarToken = null;
        for (String ss:split) {
            temSplit = ss.split("-");
            if(temSplit.length == 2){
                srcToken = temSplit[0].trim();
                tarToken = temSplit[1].trim();
                if(!srcToken.equals("") && !tarToken.equals("")){
                    tokenMap.put(srcToken, tarToken);
                }
            }
        }
        return tokenMap;
    }

}
