package me.lewei.worker;

import java.util.Map;

import me.lewei.app.BaseWorker;
import me.lewei.logic.WriteService;
import me.lewei.obj.JobContext;
import me.lewei.obj.WriteContext;

public class WriteWorker extends BaseWorker {

	JobContext jobContext;

	private WriteService writeService;

	@Override
	public void workerRun() throws Exception {
		
		this.jobContext = this.getJobContext();
		
		String inputPath = jobContext.getPath();
//		inputPath = inputPath.replaceAll("/", "\\");
		if (!inputPath.endsWith("/")) {
			inputPath = inputPath + "/";
		}

		Map<String, WriteContext> fileMap = writeService.readNameListFromExcel(inputPath);
		
		writeService.validateRenameFiles(fileMap);
		
		writeService.processRenameFiles(fileMap);
		
//		writeService.exportResultToExcel(wc);//TODO
	}

	public WriteService getWriteService() {
		return writeService;
	}

	public void setWriteService(WriteService writeService) {
		this.writeService = writeService;
	}

}
