package me.lewei.logic;

import java.util.Map;

import me.lewei.obj.WriteContext;



public interface WriteService {

	public Map<String, WriteContext>  readNameListFromExcel(String path) throws Exception;

	public void processRenameFiles(Map<String, WriteContext> fileMap) throws Exception;

	public void validateRenameFiles(Map<String, WriteContext> fileMap) throws Exception;

	public void exportResultToExcel(Map<String, WriteContext> fileMap) throws Exception;
}
