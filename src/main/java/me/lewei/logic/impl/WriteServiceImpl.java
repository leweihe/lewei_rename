package me.lewei.logic.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import me.lewei.core.ProcerConstants;
import me.lewei.logic.WriteService;
import me.lewei.obj.WriteContext;
import me.lewei.util.CommonExcelUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class WriteServiceImpl implements WriteService {

	private static final Log log = LogFactory.getLog(WriteServiceImpl.class);

	@Override
	public Map<String, WriteContext> readNameListFromExcel(String path) throws Exception {
		Map<String, WriteContext> fileMap = null;
		try {
			CommonExcelUtil ceu = new CommonExcelUtil();

			ceu.setFromColumn(1);// TODO should get from some properties files
			ceu.setToColumn(6);// TODO should get from some properties files
			ceu.setPathColumn(7);// TODO should get from some properties files

			InputStream is = new FileInputStream(path + ProcerConstants.WORKING_FILE_NAME);
			fileMap = ceu.readExcelFileMaps(is);

			log.info("[ readNameListFromExcel ] Totally " + fileMap.size());

		} catch (FileNotFoundException e) {
			log.error("", e);
		}

		return fileMap;
	}

	@Override
	public void processRenameFiles(Map<String, WriteContext> fileMap) throws Exception {

		if (fileMap == null || fileMap.size() <= 0) {
			throw new Exception("[ processRenameFiles ] Nothing to change for fileMaps is empty");
		}

		Iterator<Entry<String, WriteContext>> it = fileMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, WriteContext> entry = (Map.Entry<String, WriteContext>) it.next();
			String key = entry.getKey();
			WriteContext wc = (WriteContext) entry.getValue();
			
			File orgFile = new File(wc.getOrgFullPath());
			File newFile = new File(wc.getNewFullPath());
			orgFile.renameTo(newFile);
			
		}

	}


	@Override
	public void validateRenameFiles(Map<String, WriteContext> fileMap) throws Exception {

		if (fileMap == null || fileMap.size() <= 0) {
			throw new Exception("[ processRenameFiles ] Nothing to change for fileMaps is empty");
		}

		Iterator<Entry<String, WriteContext>> it = fileMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, WriteContext> entry = (Map.Entry<String, WriteContext>) it.next();
			String key = entry.getKey();
			WriteContext wc = (WriteContext) entry.getValue();
			
			File orgFile = new File(wc.getOrgFullPath());
			File newFile = new File(wc.getNewFullPath());

			if(!orgFile.exists()) {
				throw new Exception("[ processRenameFiles ] Sources file is not exist: " + orgFile.getPath());
			}
			if (newFile.exists()) {
				throw new Exception("[ processRenameFiles ] Target file is already exist: " + newFile.getPath());
			}
			
		}
	}

	@Override
	public void exportResultToExcel(Map<String, WriteContext> fileMap) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
