package com.yinye.yyedt.checkRepeatMod;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.yinye.yyedt.utils.FileUtils;
import com.yinye.yyedt.utils.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CRModAction extends AnAction {

    private Project project;
    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getData(PlatformDataKeys.PROJECT);
        CRModMgr.newInstance(project);
        VirtualFile vFile = e.getData(PlatformDataKeys.VIRTUAL_FILE);
        if(vFile==null){
            CRModMgr.getInstance().showMsgBox("提示","先选择目录");
        }else{
            String selectedFilePath = vFile.getPath();
            doCheck(selectedFilePath);
        }
    }

    private void doCheck(String dirPath){
        try {
            List<File> erlFileList = FileUtils.getAllErlFiles(dirPath);
            List<String> repeatModList = checkRepeatMod(erlFileList);

            if(repeatModList.size() == 0){
                CRModMgr.getInstance().showMsgBox("完成","无重复");
            }else{
                String modNames = buildMsg(repeatModList);
                CRModDialog myDialog = new CRModDialog(modNames);
                myDialog.setVisible(true);
            }
        } catch (Exception e1) {
            CRModMgr.getInstance().showMsgBox("未知错误",e1.getMessage());
        }
    }

    private List<String> checkRepeatMod(List<File> erlFileList){
        List<String> repeatModList = new ArrayList<String>();
        Map<String,String> modNameMap = new HashMap<String,String>();
        erlFileList.forEach(item->{
            String modName = item.getName();
            if(modNameMap.containsKey(modName)){
                repeatModList.add(StringUtils.substringBeforeLast(modName,".erl"));
            }else{
                modNameMap.put(modName,"1");
            }

        });
        return repeatModList;
    }

    private String buildMsg(List<String> repeatedModList){
        StringBuilder stringBuilder = new StringBuilder();
        repeatedModList.forEach(item->{
            stringBuilder.append(item);
            stringBuilder.append(StringUtils.getLineSeparator());
        });
        return stringBuilder.toString();
    }



    public void  getHashAllKeys(HashMap<File,File> hashMap, List<String> list1, List<String> list2) {

        for (Map.Entry<File, File> entry : hashMap.entrySet()) {
            list1.add(entry.getKey().getPath());
            list2.add(entry.getValue().getPath());
        }
    }


    public void showMsgBox(String title, String msg){
        Messages.showInfoMessage(project,msg,title);
    }

}
