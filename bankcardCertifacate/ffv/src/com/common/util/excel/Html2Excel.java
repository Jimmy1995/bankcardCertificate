package com.common.util.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.servlet.http.HttpServletResponse;

import jxl.CellType;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.PageOrientation;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 * @des 导出excel的工具类,将Html中class='gridView'的表格导出为excel
 * @author 龙志勇longzy
 */
public class Html2Excel {
	/**
	 * @param response
	 * @param html字符串
	 */
	public void export(HttpServletResponse response,String html) {
		Document doc = Jsoup.parse(html); 
		Elements root=doc.getElementsByClass("gridView");
		Elements mdocTR=root.select("tr");//找到 tr<table class="gridView"><tr></tr></table>
		Iterator<Element> itTR=mdocTR.iterator();
		Elements mdocTH=root.select("th");//找到 th<table class="gridView"><tr><th></th></tr></table>
		Iterator<Element> itTH=mdocTH.iterator();
		
		
		WritableWorkbook workbook = null;
		OutputStream os = null;
		//ZipOutputStream zip = null;

		try {
				os = response.getOutputStream();
				response.reset(); // 清空输出流
				response.setContentType("application/x-download");
				String name=URLEncoder.encode(doc.title()+".xls", "UTF-8");
				response.addHeader("Content-Disposition","attachment;filename="+name);//文件名字
				response.setContentType("application/msexcel"); // 定义输出类型
				//response.setContentType( "application/x-zip-compressed" );

				//zip = new ZipOutputStream(new BufferedOutputStream(os));
				//ZipEntry entry = new ZipEntry(doc.title()+".xls");
				//zip.putNextEntry(entry);

			WorkbookSettings setting = new WorkbookSettings();
			java.util.Locale locale = new java.util.Locale("zh", "CN");
			setting.setLocale(locale);
			setting.setEncoding("GBK");
			workbook = Workbook.createWorkbook(os, setting);
			WritableSheet sheet = workbook.createSheet("第一页", 0); // 添加第一个工作表
			sheet.setPageSetup(PageOrientation.LANDSCAPE);
			ExcelUtil e=new ExcelUtil();
			e.initialSheetSetting(sheet);
			
			int row=0;//excel中的行
			int colTH=0;//excel中的列
			while(itTH.hasNext()){//遍历TH,插入excel表头
				e.insertOneCellData(sheet,colTH,row,itTH.next().text(), e.getTitleCellFormat());
				colTH++;
			}
			while(itTR.hasNext()){//遍历tr(行)//excel的内容
				Iterator<Element> itc=itTR.next().select("td").iterator();
				int col=0;//excel中的列
				while(itc.hasNext()){//遍历这个tr里面的td(列)，插入excel中
					e.insertOneCellData(sheet,col,row,itc.next().text(), e.getDataCellFormat(CellType.STRING_FORMULA));
					col++;
				}
				row++;
			}
			
			
			
			// 将定义好的单元格添加到工作表中
			workbook.write();
			workbook.close();
			os.close();
		}catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}catch(IOException e1){
			e1.printStackTrace();
		}catch(WriteException e2){
			e2.printStackTrace();
		}finally{
			if (null != workbook) {
				try {
					workbook.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			if (null != os) {
				try {
					os.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
}
