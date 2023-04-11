import com.yinye.yyedt.CheckUnused.bs.CUnusedModInfo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

    public static void main(String[] args) throws IOException {
//        String input = "-export([get_tcp_gen/0, switch_tcp_gen/1,ttt/1,switch_tcp_gen/1]).";
        File file = new File("D:\\allen_github\\yinye1024\\yyedt\\test\\files\\bs_role_online_mgr.erl");
        CUnusedModInfo modinfo = CUnusedModInfo.fromFile(file);
        System.out.print(modinfo.getModName());
    }




}
