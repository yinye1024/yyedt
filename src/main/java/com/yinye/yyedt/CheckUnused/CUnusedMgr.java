package com.yinye.yyedt.CheckUnused;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.yinye.yyedt.CheckUnused.bs.CUnusedDao;
import com.yinye.yyedt.CheckUnused.bs.CUnusedModInfoHelper;
import com.yinye.yyedt.utils.PluginFileDao;

import java.io.IOException;
import java.util.List;

public class CUnusedMgr {

    private static CUnusedMgr instance = null;
    public static CUnusedMgr newInstance(Project project){
        instance = new CUnusedMgr();
        instance.project = project;
        return instance;
    }
    public static CUnusedMgr getInstance(){
        return instance;
    }
    private Project project;

    public void showMsgBox(String title, String msg){
        Messages.showInfoMessage(project,msg,title);
    }

    public CUnusedResult checkUnused(String oriDirPath, String destDirPath) throws IOException {
        return CUnusedModInfoHelper.getInstance().checkUnused(oriDirPath,destDirPath);
    }

    public List<String> loadFilter(){
        String workPath = this.project.getBasePath();
        return CUnusedDao.getInstance().loadFilter(workPath);
    }
    public void saveFilter(String filterText,String includeText) throws IOException {
        String workPath = this.project.getBasePath();
        CUnusedDao.getInstance().saveFilter(workPath,filterText,includeText);
    }

}


