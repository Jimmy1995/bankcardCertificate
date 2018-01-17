/*
 * CharResponseWrapper 
 *@author longzy
 */

package com.common.util.httpWrapper;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
public class CharResponseWrapper extends HttpServletResponseWrapper {
	protected CharArrayWriter charWriter;// 将响应缓存在这个写入器中
	protected PrintWriter writer;
	protected boolean getOutputStreamCalled;
	protected boolean getWriterCalled;

	public CharResponseWrapper(HttpServletResponse response) {
		super(response);
		charWriter = new CharArrayWriter();
	}

	public ServletOutputStream getOutputStream() throws IOException {
		if (getWriterCalled) {
			throw new IllegalStateException("getWriter already called");
		}
		getOutputStreamCalled = true;
		return super.getOutputStream();
	}

	public PrintWriter getWriter() throws IOException {
		if (writer != null) {
			return writer;
		}
		if (getOutputStreamCalled) {
			throw new IllegalStateException("getOutputStream already called");
		}
		getWriterCalled = true;
		writer = new PrintWriter(charWriter);
		return writer;
	}

	public String toString()// 将响应数据用字符串返回
	{
		String s = "";
		if (writer != null) {
			s = charWriter.toString();
		}
		return s;
	}

	public char[] toCharArray() {// 将响应数据以字符数组返回
		return (charWriter.toCharArray());
	}
}
