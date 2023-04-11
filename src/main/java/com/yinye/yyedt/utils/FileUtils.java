/*
 * $HeadURL: http://150.236.80.220/dev/dsc/dmm/branches/dmm-2.1.1-13-AOP321-14-maint/src/com/drutt/dmm/util/FileUtils.java $
 * $Id: FileUtils.java 858830 2010-09-15 15:12:04Z eligchn $
 * Copyright (c) 2009 by Ericsson, all rights reserved.
 */

package com.yinye.yyedt.utils;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import com.intellij.openapi.project.Project;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


public class FileUtils {


	/** Revision of the class */
	public static final String _REV_ID_ = "$Revision: 858830 $";

	public static String upFirstChar(String input) {
		return input.substring(0, 1).toUpperCase() + input.substring(1);
	}
	
	public static String lowFirstChar(String input) {
		return input.substring(0, 1).toLowerCase() + input.substring(1);
	}
	
	public static void cmdOpenFolder(String filePath){
		try {  
            String[] cmd = new String[5];  
            cmd[0] = "cmd";  
            cmd[1] = "/c";  
            cmd[2] = "start";  
            cmd[3] = " ";  
            cmd[4] = filePath;  
            Runtime.getRuntime().exec(cmd);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
	}
	//迭代拷贝所有的文件和目录,保持目录结构
	public static void cloneDir(String oriDirPath, String destDirPath) throws IOException {
		createDir(destDirPath);

		File oriDir = new File(oriDirPath);
		String[] fileNameArray = oriDir.list();
		if(fileNameArray != null){
			for (int i = 0; i < fileNameArray.length; i++) {
				String oriFilePathTmp = oriDirPath + File.separator + fileNameArray[i];
				String destFilePathTmp = destDirPath + File.separator + fileNameArray[i];
				File oriFileTmp = new File(oriFilePathTmp);
				if (oriFileTmp.isDirectory()) {
					cloneDir(oriFilePathTmp, destFilePathTmp);
				}else if (oriFileTmp.isFile()){
					copyFile(oriFilePathTmp, destFilePathTmp);
				}

			}
		}
	}

	public static File newWorkspaceDir(String workPath,String dirName){
		File wsTmp = new File(workPath,".yyedt");
		createDir(wsTmp);
		File targetTmp = new File(wsTmp,dirName);
		createDir(targetTmp);
		return targetTmp;
	}

	public static File createDir(String path){
		File dirFile = new File(path);
		return createDir(dirFile);
	}

	@NotNull
	public static File createDir(File dirFile) {
		if (!dirFile.exists()) {
			dirFile.mkdir();
		}
		return dirFile;
	}

	//迭代删除所有的文件和目录,包括自己
	public static void deleteAll(File file) {
		if (!file.exists()) {
			return;
		}

		if (file.isFile()) {
			file.delete();
		} else {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				deleteAll(files[i]);
				files[i].delete();
			}
		}
		//删除完后检查自己是否可删除
		if(file.isDirectory()&& file.list().length == 0){
			file.delete();
		}
	}
	
	/**
	 * Whether the specified folder is empty.
	 * 
	 * @param folder
	 * @return
	 */
	public static boolean isEmptyDir(String folder) {
		boolean isEmpty = true;
		File f = new File(folder);
		if (f.exists()) {
			String[] fileNames = f.list();
			if (fileNames != null && fileNames.length > 0) {
				isEmpty = false;
			}
		}
		return isEmpty;
	}

	public static void copyFile(String oriPath, String destPath) throws IOException {
		File oriFile = new File(oriPath);
		File destFile = new File(destPath);
		copyFile(oriFile, destFile);
	}
	/**
	 * Will copy a file from source to destination.
	 *
	 * @param ori
	 *            Source file
	 * @param dest
	 *            Destination file
	 * @return True if nothing goes wrong
	 */
	public static boolean copyFile(File ori, File dest) {
		try {
			// 改用 字符流
			FileWriter os = new FileWriter(dest);
			FileReader is = new FileReader(ori);
			char[] buf = new char[1024];
			int len;
			while ((len = is.read(buf)) > 0) {
				os.write(buf, 0, len);
			}
			os.flush();
			os.close();
			is.close();
		} catch (Exception x) {
			return false;
		}
		return true;
	}

	//替换文件内容
	public static void replaceText(File targetFile, Map<String, String> replaceMap) {
		try {
			Set<String> tokenSet = replaceMap.keySet();

			BufferedReader bufReader = new BufferedReader(new FileReader(targetFile));

			StringBuffer strBuf = new StringBuffer();

			String lineTmp = null;
			while ((lineTmp = bufReader.readLine()) != null) {
				for (String token : tokenSet) {
					lineTmp = lineTmp.replace(token, replaceMap.get(token));
				}
				strBuf.append(lineTmp);
				strBuf.append(System.getProperty("line.separator"));
			}

			bufReader.close();

			BufferedWriter bufWriter = new BufferedWriter( new FileWriter(targetFile));
			bufWriter.write(strBuf.toString());

			bufWriter.flush();
			bufWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String readTxt(File file) throws Exception {
		final String encoding = "UTF-8";
		StringBuilder content = new StringBuilder();
		if (file.isFile() && file.exists()) { // 判断文件是否存在
			InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
			BufferedReader bufferedReader = null;
			try {
				bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					content.append(lineTxt);
				}
			} finally {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			}
		}
		return content.toString();
	}

	public static void writeTxt(File file, String fileContent) throws Exception {
		final String encoding = "UTF-8";
		if (!file.exists()) {
			file.createNewFile();
		}
		OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(file), encoding);
		BufferedWriter writer = new BufferedWriter(write);
		try {
			writer.write(fileContent);
			writer.flush();
		} finally {
			writer.close();
		}
	}

	public static List<File> getAllErlFiles(String dirPath){
		List<File> fileList = getAllFiles(dirPath);
		List<File> erlFileList = fileList.stream()
				.filter(fileTmp -> fileTmp.getName().endsWith(".erl")).collect(Collectors.toList());
		return erlFileList;
	}

	public static List<File> getAllFiles(String path) {
		List<File> filesList = new ArrayList<File>();
		File file = new File(path);
		if(file.exists()){
			putFiles2List(file, filesList);
		}
		return filesList;
	}


	private static void putFiles2List(File file, List<File> accFileList) {
		// 如果这个路径是文件夹
		if (file.isDirectory()) {
			File[] files = file.listFiles();
//			fileslist.add(file); 不要目录，只要文件
			// 获取路径下的所有文件
			for (int i = 0; i < files.length; i++) {
				// 如果还是文件夹 递归获取里面的文件 文件夹
				if (files[i].isDirectory()) {
					putFiles2List(files[i],accFileList);
				} else {
					accFileList.add(files[i]);
				}

			}
		} else {
			accFileList.add(file);
		}
	}

	public static HashMap<File,File> getRepeatFileMap(List<File> fileList) throws IOException {
		HashMap<File,File> accFileMap = new HashMap<File,File>();
		for (File f:fileList) {
			putRepeatFiles2Map(f,fileList,accFileMap);
		}
		return accFileMap;
	}

	private static void putRepeatFiles2Map(File targetFile, List<File> fileList, HashMap<File,File> accFileMap)  throws IOException{
		String targetName = targetFile.getName();
		for (int i = 0;i < fileList.size(); i++){
			File fileTmp = fileList.get(i);
			String fileNameTmp = fileTmp.getName();
			if(fileNameTmp.equals(targetName) && !fileTmp.getPath().equals(targetFile.getPath())){
				if(!accFileMap.containsKey(fileTmp)){
					accFileMap.put(targetFile,fileTmp);
				}
			}
		}
	}

	/**
	 * 得到文本文件的每一行并且以《行号，String 内容》 返回map
	 * @return
	 */
	public static HashMap<Integer,String> getLineMap(File filePath) throws IOException {
		HashMap<Integer,String> lineMap = new HashMap<Integer,String>();
//		File filePath = new File("D:\\AB.txt");
		String s = null;

		BufferedReader br=new BufferedReader(new FileReader(filePath));

		Integer index = 0; // 行数计数器
		while ((s = br.readLine()) != null){
			index ++;
			lineMap.put(index,s.toString());
//			System.out.println(index + "----" + s + "\r\n");
		}
        return lineMap;

	}

	public static String getFileName(String filePath) {
		File targetFile = new File(filePath);
		String absolutePath = targetFile.getAbsolutePath();
		return StringUtils.substringAfterLast(absolutePath, "\\");
	}


	public static void main(String[] args) throws Exception {
		File file1 = new File("D:\\bstest\\ErlangPlugin\\src\\com\\myerlangplugin\\PluginHelper.java");
//		getrepeatFiles(getAllFilePathByDir(file1));
//		File file2 = new File("D:\\bstest\\ErlangPlugin\\src\\com\\text\\Test2.java");
////		getAllFilePathByDir("D:\\bstest\\ErlangPlugin\\src");
		FileUtils.getLineMap(file1);
//		System.out.println(readTxt(file1, "UTF-8"));
	}

}
