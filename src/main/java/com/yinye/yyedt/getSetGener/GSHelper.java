package com.yinye.yyedt.getSetGener;


import java.util.ArrayList;
import java.util.List;

public class GSHelper {

    public String getMessage(String message, int flag, boolean isGet, boolean pri) {

        List<String> list = new ArrayList<String>();

        list = getFiledStrList(message);

        list = getFiledListByList(list);

        String reMessage = getStringByList(list, flag, isGet, pri);

        return reMessage;
    }

    public List<String> getFiledStrList(String Message) {
        String[] listByLine = Message.split("\\n");

        List<String> list = new ArrayList<String>();
        String temString = "";
        String[] temList = null;
        for (int i = 0; i < listByLine.length; i++) {
            temString = fileTextByAnnotation(listByLine[i], '%');
            temList = temString.split(",");
            for (int j = 0; j < temList.length; j++) {
                list.add(temList[j]);
            }
        }
        return list;
    }
    /**
     * 过滤注释掉的文件
     * 百分号规则，暂时不考虑 ‘%’ “%”
     * @return
     */
    private String fileTextByAnnotation(String s, char c){

        char[] charList = s.toCharArray();
        int len = charList.length;
        int end = -1;
        for (int i = 0; i < len; i ++ ){
            if(charList[i] == c){
                break;
            }
            end = i;
        }
        return new String(charList,0,end + 1);
    }

    public List<String> getFiledListByList(List<String> list) {

        List<String> filedList = new ArrayList<String>();

        String[] temList = {};

        for (String s : list) {


            temList = s.split("=>");

            if (temList.length > 1) {

                String temFiled = temList[0].trim().replace("\"", "");

                if (temFiled.length() > 0 && "_".equals(temFiled.substring(0, 1))) {

                    temFiled = temFiled.substring(1);
                }

                filedList.add(temFiled);

            }
        }

        return filedList;

    }

    public String getStringByList(List<String> list, int flag, boolean isGet, boolean pri) {

        StringBuffer sb = new StringBuffer();
        if (flag == 1) {

            sb.append("-export([");

            for (int i = 0; i < list.size(); i++) {


                if (i == (list.size() - 1)) {

                    sb.append(getExport(list.get(i), isGet, pri));

                } else {

                    sb.append(getExport(list.get(i), isGet, pri) + ", ");

                }

            }

            sb.append("]).\n");
        } else {

            sb.append("-export([\n");

            for (int i = 0; i < list.size(); i++) {

                if (i == (list.size() - 1)) {

                    sb.append("  " + getExport(list.get(i), isGet, pri));

                } else {

                    sb.append("  " + getExport(list.get(i), isGet, pri) + ",\n");

                }

            }

            sb.append("\n]).\n");

        }


        for (String s : list) {

            sb.append(getFun(s, isGet, pri));

        }
        return sb.toString();

    }

    private String getExport(String field, boolean isGet, boolean pri) {
        String str = "";
        if (pri) {
            return str;
        } else {
            if (isGet) {
                return "get_" + field + "/1";
            } else {
                return "get_" + field + "/1,set_" + field + "/2";
            }
        }
    }

    private String getFun(String field, boolean isGet, boolean pri) {
        String str = "";
        if (pri) {
            str = "priv_";
        }
        if (isGet) {
            return str + "get_" + field + "(SelfMap) ->\n" +
                    "  yyu_map:get_value(" + field + ", SelfMap).\n\n";
        } else {
            return str + "get_" + field + "(SelfMap) ->\n" +
                    "  yyu_map:get_value(" + field + ", SelfMap).\n\n" +
                    str + "set_" + field + "(Value, SelfMap) ->\n" +
                    "  yyu_map:put_value(" + field + ", Value, SelfMap).\n\n";
        }
    }
}
