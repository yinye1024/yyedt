import com.yinye.yyedt.CheckUnused.bs.CUnusedModInfo;
import com.yinye.yyedt.utils.FileUtils;
import com.yinye.yyedt.utils.PluginFileDao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

    public static void main(String[] args) throws IOException {

        String workPath = "D:\\allen_test\\dev_tools\\demo";
        File dir = FileUtils.newWorkspaceDir(workPath,"test");
        System.out.print(dir.getAbsolutePath());
    }




}
