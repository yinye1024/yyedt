package com.yinye.yyedt.checkRepeatMod;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CRModDialog extends JDialog{
    private JPanel contentPane;
    private JTextArea modNames;
    private JButton okButton;

    public CRModDialog(String modNames) {
        setTitle("重复模块名列表");
        setSize(400,400); // 设置大小

        this.modNames.setText(modNames);
//        this.modNameList.setModel(buildListModel(modNameList));
        setLocationRelativeTo(null); // 居中

        setContentPane(contentPane);
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
        setModal(true);
    }

    private void onOK() {
        dispose();
    }

    private void onCancel() {
        dispose();
    }
    public DefaultListModel<String> buildListModel(List<String> list){
        DefaultListModel<String> dlm = new DefaultListModel<String>();
        for (String s: list) {
            dlm.addElement(s);
        };
        return dlm;
    }
}
