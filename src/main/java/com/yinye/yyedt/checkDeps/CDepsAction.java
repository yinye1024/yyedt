package com.yinye.yyedt.checkDeps;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.File;
import java.io.IOException;

public class CDepsAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {

        Project project = e.getData(PlatformDataKeys.PROJECT);
        VirtualFile vFile = e.getData(PlatformDataKeys.VIRTUAL_FILE);
        if(vFile!=null){
            String filePath = vFile.getPath();
            File selectFile = new File(filePath);
            showDialog(project,selectFile);
        }
    }


    private void showDialog(Project project, File selectFile){
        try {
            String workPath = project.getBasePath(); // 获取工作空间路径
            //初始化单例
            CDepsMgr.newInstance(project);

            CDepsDialog depsDialog = new CDepsDialog(new CDepsDialog.DialogCallBack() {
                @Override
                public CDepsResult checkDeps(CDepsDialogData dialogData) {
                    try {
                        return doCheckDeps(dialogData);
                    } catch (Exception e) {
                        e.printStackTrace();
                        CDepsMgr.getInstance().showMsgBox("错误信息","生成过程有异常，请查看console的错误日志" );
                        return null;
                    }
                }

            },selectFile);
            depsDialog.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 1 检查源目录引用了目标目录的哪些模块 check destDir mods used by oriDir mods
    // 2 检查源目录被目标目录引用了哪些模块  check oriDir mods used by destDir mods
    private CDepsResult doCheckDeps(CDepsDialogData dialogData) throws IOException {
        CDepsResult result = CDepsMgr.getInstance().doCheckDeps(dialogData.getOriDirPath(),dialogData.getDestDirPath());
        return result;
    }




}
