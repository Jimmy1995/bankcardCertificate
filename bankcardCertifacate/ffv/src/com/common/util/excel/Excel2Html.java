package com.common.util.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import jxl.Cell;
import jxl.Range;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.CellFormat;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;

public class Excel2Html {
//	public static void main(String[] args) {
//		try {
//			System.out.println(getExcelInfo("D://1.xls"));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	public static String getExcelInfo(String xlsPath) throws Exception {
		StringBuffer sb = new StringBuffer();
		File sourcefile = new File(xlsPath);
		InputStream is = new FileInputStream(sourcefile);
		Workbook rwb = Workbook.getWorkbook(is);
		Sheet sheet = rwb.getSheet(0);
		int colnum = sheet.getColumns();
		int rownum = sheet.getRows();
		Map<String, String> map[] = getRowSpanColSpanMap(sheet);
		sb.append("<table style='border-collapse:collapse;border-spacing:0;'>");
		for (int row = 0; row < rownum; row++) {
			sb.append("<tr>");
			for (int col = 0; col < colnum; col++) {
				Cell cell = sheet.getCell(col, row);
				String content = cell.getContents();
				CellFormat cellFormat = cell.getCellFormat();
				if (map[0].containsKey(row + "," + col)) {
					String pointString = map[0].get(row + "," + col);
					map[0].remove(row + "," + col);
					int bottomeRow = Integer.valueOf(pointString.split(",")[0]);
					int bottomeCol = Integer.valueOf(pointString.split(",")[1]);
					int rowSpan = bottomeRow - row + 1;
					int colSpan = bottomeCol - col + 1;
					sb.append("<td rowspan= '" + rowSpan + "' colspan= '"+ colSpan + "' ");
				} else if (map[1].containsKey(row + "," + col)) {
					map[1].remove(row + "," + col);
					continue;
				} else {
					sb.append("<td ");
				}
				if (cellFormat != null) {
					Alignment alignment = cellFormat.getAlignment();
					sb.append("align='" + convertToHtmlGrammer(alignment)+ "' ");
					VerticalAlignment verticalAlignment = cellFormat.getVerticalAlignment();
					sb.append("valign='"+ convertToHtmlGrammer(verticalAlignment) + "' ");
					sb.append("style='color:"+ convertToHtmlGrammer(cellFormat.getFont().getColour()) + ";");
					Colour bgcolour = cellFormat.getBackgroundColour();
					sb.append("background-color:"+ convertToHtmlGrammer(bgcolour) + ";");
					sb.append("font-size:"+ cellFormat.getFont().getPointSize() + "pt;");
					sb.append("font-weight:"+ cellFormat.getFont().getBoldWeight() + ";");
					BorderLineStyle bottomStyle=cellFormat.getBorder(Border.BOTTOM);
					BorderLineStyle leftStyle=cellFormat.getBorder(Border.LEFT);
					BorderLineStyle rightStyle=cellFormat.getBorder(Border.RIGHT);
					BorderLineStyle topStyle=cellFormat.getBorder(Border.TOP);
					if(!leftStyle.getDescription().equals("none"))
						sb.append("border-left:"+leftStyle.getDescription() + " solid "+cellFormat.getBorderColour(Border.ALL).getDescription() +" ;");
					if(!rightStyle.getDescription().equals("none"))
						sb.append("border-right:"+rightStyle.getDescription()+ " solid " + cellFormat.getBorderColour(Border.ALL).getDescription() +";");
					if(!topStyle.getDescription().equals("none"))
						sb.append("border-top:"+topStyle.getDescription()+ " solid "+ cellFormat.getBorderColour(Border.ALL).getDescription()  +";");
					if(!bottomStyle.getDescription().equals("none"))
						sb.append("border-bottom:"+bottomStyle.getDescription()+" solid "+ cellFormat.getBorderColour(Border.ALL).getDescription()  + ";");
					sb.append("' ");
				}
				sb.append(">");
				if (content == null || "".equals(content.trim())) {
					sb.append(" &nbsp; ");
				} else {
					sb.append(content);
				}
				sb.append("</td>");
			}
			sb.append("</tr>");
		}
		sb.append("</table>");
		rwb.close();
		is.close();
		return sb.toString();
	}

	@SuppressWarnings("unchecked")
	private static Map<String, String>[] getRowSpanColSpanMap(Sheet sheet) {
		Map<String, String> map0 = new HashMap<String, String>();
		Map<String, String> map1 = new HashMap<String, String>();
		Range[] range = sheet.getMergedCells();
		for (int i = 0; i < range.length; i++) {
			Cell topCell = range[i].getTopLeft();
			Cell bottomCell = range[i].getBottomRight();
			int topRow = topCell.getRow();
			int topCol = topCell.getColumn();
			int bottomRow = bottomCell.getRow();
			int bottomCol = bottomCell.getColumn();
			map0.put(topRow + "," + topCol, bottomRow + "," + bottomCol);
			int tempRow = topRow;
			while (tempRow <= bottomRow) {
				int tempCol = topCol;
				while (tempCol <= bottomCol) {
					map1.put(tempRow + "," + tempCol, "");
					tempCol++;
				}
				tempRow++;
			}
			map1.remove(topRow + "," + topCol);
		}
		Map[] map = { map0, map1 };
		return map;
	}
	private static String convertToHtmlGrammer(Alignment alignment) {
		String align = "left";
		switch (alignment.getValue()) {
			case 1:align = "left";break;
			case 2:align = "center";break;
			case 3:align = "right";break;
			case 5:align = "justify";break;
			default:break;
		}
		return align;
	}
	private static String convertToHtmlGrammer(VerticalAlignment verticalAlignment) {
		String valign = "middle";
		switch (verticalAlignment.getValue()) {
			case 1:valign = "middle";break;
			case 2:valign = "bottom";break;
			case 3:valign = "top";break;
			default:break;
		}
		return valign;
	}

	private static String convertToHtmlGrammer(Colour colour) {
		StringBuffer sb = new StringBuffer("");
		if (colour != null&& !"default background".equalsIgnoreCase(colour.getDescription())) {
			sb.append("#");
			sb.append(fillWithZero(Integer.toHexString(colour.getDefaultRGB().getRed())));
			sb.append(fillWithZero(Integer.toHexString(colour.getDefaultRGB().getGreen())));
			sb.append(fillWithZero(Integer.toHexString(colour.getDefaultRGB().getBlue())));
		}
		return sb.toString();
	}
	private static String fillWithZero(String str) {
		if (str != null && str.length() < 2) {
			return "0" + str;
		}
		return str;
	}
}