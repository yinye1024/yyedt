package com.yinye.yyedt.copyReplace;

import java.io.File;
import java.io.IOException;
import java.util.*;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.yinye.yyedt.copyReplace.bs.CRContext;
import com.yinye.yyedt.copyReplace.bs.CRTokenData;
import com.yinye.yyedt.utils.FileUtils;

public class CRMgr {

    private static CRMgr instance = null;
    private static final Logger eventLogger =  Logger.getInstance(CRMgr.class);
    public static CRMgr newInstance(Project project){
        instance = new CRMgr();
        instance.project = project;
        return instance;
    }
    public static CRMgr getInstance(){
        return instance;
    }
    private Project project;
    private File codeGenInputDir;
    private File codeGenOutputDir;

    // 返回第一个
    public void initCRDir(String workPath) throws IOException {

        File codeGenDir =  FileUtils.newWorkspaceDir(workPath , "codeGenTmpDir");
        FileUtils.deleteAll(codeGenDir);
        FileUtils.createDir(codeGenDir);

        codeGenInputDir = new File(codeGenDir, "input");
        codeGenOutputDir = new File(codeGenDir,"output");

        FileUtils.createDir(codeGenInputDir);
        FileUtils.createDir(codeGenOutputDir);
    }

    public void copy2InputDir(String selectedFilePath) throws IOException {
        List<File> selectedFileList = new ArrayList<File>();
        File file = new File(selectedFilePath);
        selectedFileList.add(file);
        privCopy2InputDir(selectedFileList, codeGenInputDir);
    }
    private void privCopy2InputDir(List<File> fileList, File codeGenInputDir) throws IOException {

        for (File fileTmp : fileList) {
            String fileName = fileTmp.getName();
            String targetFilePath = codeGenInputDir.getAbsolutePath() + "\\" + fileName;
//            System.out.println("targetFilePath" + targetFilePath);
            if (fileTmp.isDirectory()) {
                FileUtils.cloneDir(fileTmp.getAbsolutePath(), targetFilePath);
            } else {
                FileUtils.copyFile(fileTmp, new File(targetFilePath));
            }
        }

    }

    public void doCR(String filePath, CRDialogData dialogData) throws IOException {
        CRTokenData crTokenData = dialogData.buildCRTokenData();
        CRContext.newInstance(this.codeGenInputDir,this.codeGenOutputDir)
                .doCR(crTokenData);

        String outputPath = this.codeGenOutputDir.getAbsolutePath();
//        CRMgr.getInstance().showMsgBox("完成信息","源目录:"
//                +System.getProperty("line.separator")+
//                filePath +
//                System.getProperty("line.separator") +
//                "目标目录:"+
//                System.getProperty("line.separator")+outputPath);
//        showEventLog("源目录:"
//                +System.getProperty("line.separator")+
//                filePath +
//                System.getProperty("line.separator") +
//                "目标目录:"+
//                System.getProperty("line.separator")+outputPath);
        FileUtils.cmdOpenFolder(outputPath);
    }

    public void showMsgBox(String title, String msg){
        Messages.showInfoMessage(project,msg,title);
    }
    public void showEventLog(String msg){
        eventLogger.info(msg);
    }


}


