package com.common.web.filter.wrapper;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
 
 /**
  * 功能描述
  */
public class StatusExposingServletResponse extends HttpServletResponseWrapper {
     private Integer status = SC_OK;;
 
     public Integer getStatus() {
         return status;
     }
     public StatusExposingServletResponse(HttpServletResponse response) {
         super(response);
     }
 
     @Override
     public void sendError(int sc, String msg) {
         try {
			super.sendError(sc, msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
        status = sc;
     }
 
     @Override
     public void sendError(int sc) {
         try {
			super.sendError(sc);
		} catch (IOException e) {
			e.printStackTrace();
		}
         status = sc;
     }
 
     @Override
     public void sendRedirect(String location) {
         try {
			super.sendRedirect(location);
		} catch (IOException e) {
			e.printStackTrace();
		}
         status = SC_MOVED_TEMPORARILY;
     }
 
     @Override
     public void setStatus(int sc) {
         super.setStatus(sc);
         status = sc;
     }
 
     @Override
     public void setStatus(int sc, String sm) {
         super.setStatus(sc, sm);
         status = sc;
     }
 
 }