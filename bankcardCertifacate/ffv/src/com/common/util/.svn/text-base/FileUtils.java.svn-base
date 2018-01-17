package com.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.common.util.file.FileType;
import com.common.util.file.FileTypeJudge;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

/**
 * @author 龙志勇
 */
public class FileUtils {
	/**
	 * 拷贝文件
	 * 
	 * @param objectiveFile目标文件
	 * @param resourse源文件
	 */
	public static boolean copy(String objectiveFile, String resourse) {
		File r = new File(resourse);
		File o = new File(objectiveFile);
		return copyNIO(o, r);
	}

	public static FileInputStream readFile(String resourse) {
		FileInputStream in = null;
		try {
			File f = new File(resourse);
			in = new FileInputStream(f);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		}
		return in;
	}

	public static boolean delete(String filePath) {
		File f = new File(filePath);
		return f.delete();
	}

	@Deprecated
	public static boolean saveFile(String path, FileInputStream in) {
		FileOutputStream ou = null;
		File f1 = new File(path);
		boolean flag = true;
		try {
			ou = new FileOutputStream(f1);
			int i;
			if(in!=null)
			while ((i = in.read()) != -1) {
				ou.write(i);
			}
			if (in != null) {
				in.close();
			}
			if (ou != null)
				ou.close();
		} catch (Exception e) {
			flag = false;
		} finally {
			try {
				if (in != null)
					in.close();
				if (ou != null)
					ou.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}

	public static boolean saveFile(String path, String fileName, File ObjFile)
			throws Exception {
		File f = new File(path);
		if(!f.exists()){f.mkdirs();}
		File nf = new File(path + File.separator + fileName);
		nf.createNewFile();
		return copyNIO(nf, ObjFile);
	}
	public static void saveFile(String path, String fileName, byte[] data) throws Exception{
		File f = new File(path);
		if(!f.exists()){f.mkdirs();}
		File nf = new File(path + File.separator + fileName);
		nf.createNewFile();
		org.apache.commons.io.FileUtils.writeByteArrayToFile(nf, data);
	}
	/**
	 * IO 方式拷贝文件
	 * 
	 * @param objectiveFile
	 * @param resourse
	 * @return
	 */
	@Deprecated
	public static boolean copy(File objectiveFile, File resourse) {
		FileInputStream in = null;
		FileOutputStream ou = null;
		boolean flag = true;
		try {
			in = new FileInputStream(resourse);
			ou = new FileOutputStream(objectiveFile);
			int i;
			while ((i = in.read()) != -1) {
				ou.write(i);
			}
			if (in != null) {
				in.close();
			}
			if (ou != null) {
				ou.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			flag = false;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (ou != null) {
					ou.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}

	/**
	 * NIO 方式拷贝文件
	 * 
	 * @param objectiveFile
	 * @param resourse
	 * @return
	 */
	public static boolean copyNIO(File objectiveFile, File resourse) {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		boolean flag = true;
		try {
			fis = new FileInputStream(resourse);
			fos = new FileOutputStream(objectiveFile);
			FileChannel fic = fis.getChannel();
			FileChannel foc = fos.getChannel();
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			while (true) {
				buffer.clear();
				int r = fic.read(buffer);
				if (r == -1) {
					break;
				}
				// flip方法让缓冲区可以将新读入的数据写入另一个通道
				buffer.flip();
				// 从输出通道中将数据写入缓冲区
				foc.write(buffer);
			}
		} catch (FileNotFoundException e) {
			flag = false;
			e.printStackTrace();
		} catch (IOException e) {
			flag = false;
			e.printStackTrace();
		} finally {
			if (null != fis) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != fos) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return flag;
	}

	/**
	 * 把一个文件或者文件夹压缩
	 * 
	 * @param objectiveFile
	 * @return
	 */
	public static InputStream readFileToZip(String folder) throws Exception {
		ByteOutputStream bo = new ByteOutputStream();
		ZipOutputStream out = new ZipOutputStream(bo);
		zip(out, new File(folder), "");
		ByteInputStream bi = bo.newInputStream();
		out.close();
		bo.close();
		return bi;
	}

	private static ZipOutputStream zip(ZipOutputStream out, File f, String base)
			throws Exception {
		if (f.isDirectory()) {
			File[] fl = f.listFiles();
			out.putNextEntry(new ZipEntry(base + File.separator));
			base = base.length() == 0 ? "" : base + File.separator;
			for (int i = 0; i < fl.length; i++) {
				zip(out, fl[i], base + fl[i].getName());
			}
		} else {
			out.putNextEntry(new ZipEntry(base));
			FileInputStream in = new FileInputStream(f);
			int b;
			while ((b = in.read()) != -1) {
				out.write(b);
			}
			in.close();
		}
		return out;
	}

	public static boolean deletefile(String delpath) {
		try {

			File file = new File(delpath);
			// 当且仅当此抽象路径名表示的文件存在且 是一个目录时，返回 true
			if (!file.isDirectory()) {
				file.delete();
			} else if (file.isDirectory()) {
				String[] filelist = file.list();
				for (int i = 0; i < filelist.length; i++) {
					File delfile = new File(delpath + File.separator + filelist[i]);
					if (!delfile.isDirectory()) {
						delfile.delete();
						//System.out.println(delfile.getAbsolutePath() + "删除文件成功");
					} else if (delfile.isDirectory()) {
						deletefile(delpath + File.separator + filelist[i]);
					}
				}
				//System.out.println(file.getAbsolutePath() + "删除成功");
				file.delete();
			}

		} catch (Exception e) {
			//System.out.println("deletefile() Exception:" + e.getMessage());
		}
		return true;
	}
	
	public static FileType getType(File file) throws Exception{
		return FileTypeJudge.getType(file);
	}
	
	public static void download(File file,String dowloadName, HttpServletResponse response){
		FileInputStream in=null;
		try {
			in=new FileInputStream(file);
			download(in,dowloadName,response);
		} catch (FileNotFoundException e) {
			try {
				if(in!=null)
				in.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	public static void download(InputStream in,String dowloadName, HttpServletResponse response){
		OutputStream os=null;
		try {
			os = response.getOutputStream();
			response.reset(); // 清空输出流
			response.setContentType("application/octet-stream");
			if(StringUtils.isEmpty(dowloadName)){
				dowloadName=UUID.randomUUID().toString();
			}
			dowloadName=URLEncoder.encode(dowloadName, "UTF-8");//下载的文件名字
			response.addHeader("Content-Disposition","attachment;filename=" +dowloadName );
			int s=0;
			while((s=in.read())!=-1){
				os.write(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(os!=null)
				os.flush();
				if(in!=null)
				in.close();
				if(os!=null)
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
