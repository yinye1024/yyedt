package com.yinye.yyedt.copyReplace.bs;

import com.yinye.yyedt.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

public class CRContext {

	private File inputDir;
	private File outputDir;
	
	public static CRContext newInstance(File inputDir, File outputDir){
		CRContext target = new CRContext();
		target.inputDir = inputDir;
		target.outputDir = outputDir;
		return target;
	}

	public void doCR(CRTokenData crTokenData) throws IOException {
		CRTokenData toOutputTokenData = crTokenData.buildToOutputTokenData();
		this.doGenOutput(toOutputTokenData);
	}

	private void doGenOutput(CRTokenData toOutputTokenDataP) throws IOException {
		String input = this.inputDir.getAbsolutePath();
		String output = this.outputDir.getAbsolutePath();
		CRDir2DirUtils.doCR(input,output,toOutputTokenDataP);
	}

	public static void main(String[] args) throws IOException {
		String workPath = "D:\\allen_test\\dev_tools\\code_gen_test";
		File codeGenDir = new File(workPath + "\\codeGenTmpDir");
		FileUtils.createDir(codeGenDir);

		File codeGenInputDir = new File(workPath + "\\codeGenTmpDir\\input");
//		File codeGenTempDir = new File(workPath + "\\codeGenTmpDir\\temp");
		File codeGenOutputDir = new File(workPath + "\\codeGenTmpDir\\output");

		FileUtils.createDir(codeGenInputDir);

//		FileUtils.deleteAll(codeGenTempDir);
//		FileUtils.createDir(codeGenTempDir);

		FileUtils.deleteAll(codeGenOutputDir);
		FileUtils.createDir(codeGenOutputDir);

		LinkedHashMap<String, String> textTokenMap = new LinkedHashMap<>();
		textTokenMap.put("yyes","yntext");
		LinkedHashMap<String, String> dnTokenMap = new LinkedHashMap<>();
		dnTokenMap.put("yyes","yndn");
		LinkedHashMap<String, String> fnTokenMap = new LinkedHashMap<>();
		fnTokenMap.put("yyes","ynfn");
		CRTokenData crTokenData = new CRTokenData()
				.withTextTokenMap(textTokenMap).withDnTokenMap(dnTokenMap).withFnTokenMap(fnTokenMap);
		CRContext.newInstance(codeGenInputDir,codeGenOutputDir)
				.doCR(crTokenData);
	}

}
