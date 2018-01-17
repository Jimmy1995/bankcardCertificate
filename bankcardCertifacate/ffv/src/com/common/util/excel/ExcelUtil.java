package com.common.util.excel;

import java.util.Date;

import jxl.CellType;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WriteException;

public class ExcelUtil {
	/**
	 * 初始化表格属性
	 * 
	 * @param sheet
	 */
	public void initialSheetSetting(WritableSheet sheet) {
		try {
			 sheet.getSettings().setProtected(true); //设置xls的保护，单元格为只读的
			sheet.getSettings().setDefaultColumnWidth(20); // 设置列的默认宽度
			//CellView cellView = new CellView();   
			// sheet.setRowView(2,false);//行高自动扩展
			// setRowView(int row, int height);//--行高
//			 setColumnView(int col,int width); //--列宽
			 sheet.setColumnView(0, 9);// 设置第一列宽度
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 插入单元格数据
	 * @param sheet
	 * @param col
	 * @param row
	 * @param data
	 */
	public void insertOneCellData(WritableSheet sheet, Integer col,
			Integer row, Object data, WritableCellFormat format) {
		try {
			if (data instanceof Double) {
				jxl.write.Number labelNF = new jxl.write.Number(col, row,
						(Double) data, format);
				sheet.addCell(labelNF);
			} else if (data instanceof Boolean) {
				jxl.write.Boolean labelB = new jxl.write.Boolean(col, row,
						(Boolean) data, format);
				sheet.addCell(labelB);
			} else if (data instanceof Date) {
				jxl.write.DateTime labelDT = new jxl.write.DateTime(col, row,
						(Date) data, format);
				sheet.addCell(labelDT);
			} else {
				Label label = new Label(col, row, data+"", format);
				sheet.addCell(label);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	/**
	 * 得到数据格式
	 * 
	 * @return
	 */
	public WritableCellFormat getDataCellFormat(CellType type) {
		WritableCellFormat wcf = null;
		try {
				WritableFont wf = new WritableFont(WritableFont.TIMES, 10,
						WritableFont.NO_BOLD, false);// 最后一个为是否italic
				wcf = new WritableCellFormat(wf);
			// 对齐方式
			wcf.setAlignment(Alignment.CENTRE);
			wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
			// 边框
			wcf.setBorder(Border.LEFT, BorderLineStyle.THIN);
			wcf.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
			wcf.setBorder(Border.RIGHT, BorderLineStyle.THIN);
			// 背景色
			wcf.setBackground(Colour.WHITE);
			wcf.setWrap(true);// 自动换行
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return wcf;
	}
	/**
	 * 得到数据表头格式
	 * 
	 * @return
	 */
	public WritableCellFormat getTitleCellFormat() {
		WritableCellFormat wcf = null;
		try {
			// 字体样式
			WritableFont wf = new WritableFont(WritableFont.TIMES, 12,
					WritableFont.BOLD, false);// 最后一个为是否italic
			wf.setColour(Colour.BLACK);
			wcf = new WritableCellFormat(wf);
			// 对齐方式
			wcf.setAlignment(Alignment.CENTRE);
			wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
			// 边框
			wcf.setBorder(Border.ALL, BorderLineStyle.THIN);

			wcf.setWrap(true);
			// 背景色
			wcf.setBackground(Colour.GREY_25_PERCENT);
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return wcf;
	}
}
