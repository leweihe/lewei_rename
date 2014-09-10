package me.lewei.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.lewei.core.ProcerConstants;
import me.lewei.obj.ReadContext;
import me.lewei.obj.WriteContext;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;

public class CommonExcelUtil {
	private POIFSFileSystem fs;
	private HSSFWorkbook wb;
	private HSSFSheet sheet;
	private HSSFRow row;

	private int fromColumn;
	private int toColumn;
	private int pathColumn;

	public Map<String, WriteContext> readExcelFileMaps(InputStream is) {
		Map<String, WriteContext> fileMaps = new HashMap<String, WriteContext>();
		
		try {
			fs = new POIFSFileSystem(is);
			wb = new HSSFWorkbook(fs);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sheet = wb.getSheetAt(0);
		int rowNum = sheet.getLastRowNum();
		row = sheet.getRow(0);
		int colNum = row.getPhysicalNumberOfCells();
		for (int i = 1; i <= rowNum; i++) {
			WriteContext wc = new WriteContext();
			row = sheet.getRow(i);
			int j = 0;
			String tmpValue = "";
			while (j < colNum) {
				if (j == fromColumn) {
					tmpValue = getStringCellValue(row.getCell(j)).trim();
					wc.setFromName(tmpValue);
				}
				if (j == toColumn) {
					tmpValue = getCellFormatValue(row.getCell(j)).trim();
					wc.setToName(tmpValue);
				}
				if (j == pathColumn) {
					tmpValue = getStringCellValue(row.getCell(j)).trim();
					wc.setOrgFullPath(tmpValue);
				}
				
				j++;
			}
			wc.generateNewFullPath();
			fileMaps.put(wc.getFromName(), wc);
		}
		return fileMaps;
	}

	private List<String> getColumnContex(File f, int i) {
		String seq = "" + (i + 1);
		String prefix = "";
		String fullName = f.getName();
		String space = "";
		String suffix = fullName.substring(fullName
				.lastIndexOf(ProcerConstants.SYMBOL.DOT));
		String midName = fullName.replace(suffix, "");
		String toFullName = "CONCATENATE(" + "D" + (i + 2) + ",E" + (i + 2) + ",F" + (i + 2) + ")";
		String path = f.getPath();

		List<String> result = new ArrayList<String>();
		result.add(seq);
		result.add(fullName);
		result.add(space);
		result.add(prefix);
		result.add(midName);
		result.add(suffix);
		result.add(toFullName);
		result.add(path);

		return result;
	}

	public void creatExcel4ReadedFile(ReadContext readContex) throws Exception {
		List<File> files = readContex.getFiles();

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		sheet.setDefaultColumnWidth(15);

		row = sheet.createRow(0);
		HSSFRow header = sheet.createRow(0);
		HSSFCell cell0 = header.createCell(0);
		HSSFCell cell1 = header.createCell(1);
		HSSFCell cell2 = header.createCell(2);
		HSSFCell cell3 = header.createCell(3);
		HSSFCell cell4 = header.createCell(4);
		HSSFCell cell5 = header.createCell(5);
		HSSFCell cell6 = header.createCell(6);
		HSSFCell cell7 = header.createCell(7);

		// TODO should get from some properties
		cell0.setCellValue("Seq");
		cell1.setCellValue("From_Name");
		cell2.setCellValue(">>>");
		cell3.setCellValue("Prefix");
		cell4.setCellValue("Mid_Name");
		cell5.setCellValue("Suffix");
		cell6.setCellValue("To_Name");
		cell7.setCellValue("Path(Don't edit)");
		
		for (int i = 0; i < files.size(); i++) {
			HSSFRow row = sheet.createRow(i + 1);
			File f = files.get(i);
			List<String> columns = getColumnContex(f, i);

			for (int j = 0; j < columns.size(); j++) {
				HSSFCell cell = row.createCell(j);

				if (j == toColumn) {
					cell.setCellFormula(columns.get(j));
				} else {
					cell.setCellType(Cell.CELL_TYPE_STRING); 
					cell.setCellValue(columns.get(j));
				}
			}
		}
		FileOutputStream fout = new FileOutputStream(readContex.getInputPath()
				+ ProcerConstants.WORKING_FILE_NAME);
		wb.write(fout);
		fout.close();
	}

	public String getStringCellValue(HSSFCell cell) {
		String strCell = "";
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:
			strCell = cell.getStringCellValue();
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			strCell = String.valueOf(cell.getNumericCellValue());
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			strCell = String.valueOf(cell.getBooleanCellValue());
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			strCell = "";
			break;
		default:
			strCell = "";
			break;
		}
		if (strCell.equals("") || strCell == null) {
			return "";
		}
		return strCell;
	}

	public String getCellFormatValue(HSSFCell cell) {
		String cellvalue = "";
		if (cell != null) {
			switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_NUMERIC:
			case HSSFCell.CELL_TYPE_FORMULA: {
				cellvalue = cell.getRichStringCellValue().toString();
				break;
			}
			case HSSFCell.CELL_TYPE_STRING:
				cellvalue = cell.getRichStringCellValue().getString();
				break;
			default:
				cellvalue = " ";
			}
		} else {
			cellvalue = "";
		}
		return cellvalue;
	}

	public int getFromColumn() {
		return fromColumn;
	}

	public void setFromColumn(int fromColumn) {
		this.fromColumn = fromColumn;
	}

	public int getToColumn() {
		return toColumn;
	}

	public void setToColumn(int toColumn) {
		this.toColumn = toColumn;
	}

	public int getPathColumn() {
		return pathColumn;
	}

	public void setPathColumn(int pathColumn) {
		this.pathColumn = pathColumn;
	}

}