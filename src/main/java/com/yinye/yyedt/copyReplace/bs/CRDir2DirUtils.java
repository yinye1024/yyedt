package com.yinye.yyedt.copyReplace.bs;

import com.yinye.yyedt.utils.FileUtils;
import com.yinye.yyedt.utils.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;


public class CRDir2DirUtils {

	public static void doCR(String inputDirPath,String outputDirPath,CRTokenData crTokenData) throws IOException {
		File inputDir = new File(inputDirPath);
		File outputDir = new File(outputDirPath);
		FileUtils.cloneDir(inputDir.getAbsolutePath(),outputDir.getAbsolutePath());

		itRenameDirs(outputDir,crTokenData.getDnTokenMap());
		itRenameFiles(outputDir,crTokenData.getFnTokenMap());
		replaceText(outputDir,crTokenData.getTextTokenMap());
	}



	private static void itRenameDirs(File file, Map<String, String> tokenMap){
		if(file.isDirectory()){
			File newDir = renameDir(file,tokenMap);
			File[] fileList = newDir.listFiles();
			if(fileList != null){
				for (File fileTmp : fileList) {
					if (fileTmp.isDirectory()) {
						//迭代改名
						itRenameDirs(fileTmp,tokenMap);
					}
				}
			}
		}
	}

	private static File renameDir(File dirFile, Map<String, String> tokenMap){
		String dirPathName = dirFile.getAbsolutePath();
		String dirName = dirFile.getName();
		// 替换目录文件
		String parentPath = StringUtils.substringBeforeLast(dirPathName,dirName);
		String newName = dirName;
		for (String token : tokenMap.keySet()) {
			newName = newName.replace(token, tokenMap.get(token));
		}
		File dest = new File(parentPath + newName);
		dirFile.renameTo(dest);
		return dest;
	}

	private static void itRenameFiles(File file, Map<String, String> tokenMap){
		if(file.isDirectory()){
			File[] fileList = file.listFiles();
			if(fileList != null){
				for (File fileTmp : fileList) {
					if (fileTmp.isDirectory()) {
						//迭代改名
						itRenameFiles(fileTmp,tokenMap);
					}else{
						renameFile(fileTmp,tokenMap);
					}
				}
			}
		}else{
			renameFile(file,tokenMap);
		}
	}
	private static void renameFile(File file,Map<String, String> tokenMap){
		if(file.isFile()){
			String name = file.getName();
			String abPathName = file.getAbsolutePath();
			String parentPath = StringUtils.substringBeforeLast(abPathName,name);
			String newName = name;
			for (String token : tokenMap.keySet()) {
				newName = newName.replace(token, tokenMap.get(token));
			}
			File dest = new File(parentPath + newName);
			file.renameTo(dest);

		}
	}

	private static void replaceText(File file, Map<String, String> tokenMap){
		if(file.isDirectory()){
			File[] fileList = file.listFiles();
			if(fileList != null){
				for (File fileTmp : fileList) {
					if (fileTmp.isDirectory()) {
						//迭代改名
						replaceText(fileTmp,tokenMap);
					}else{
						FileUtils.replaceText(fileTmp, tokenMap);
					}
				}
			}
		}else{
			FileUtils.replaceText(file, tokenMap);
		}
	}



}
