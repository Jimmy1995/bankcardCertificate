package com.common.web;

import java.util.HashMap;

import javax.servlet.http.HttpSession;
@SuppressWarnings("unchecked")
public class MySessionContext {
	private static MySessionContext instance=new MySessionContext();
    private HashMap map;

    private MySessionContext() {
        map = new HashMap();
    }

    public static MySessionContext getInstance() {
        return instance;
    }

    public synchronized void AddSession(HttpSession session) {
        if (session != null) {
            map.put(session.getId(), session);
        }
    }

    public synchronized void DelSession(HttpSession session) {
        if (session != null) {
            map.remove(session.getId());
        }
    }

    public synchronized HttpSession getSession(String session_id) {
        if (session_id == null) return null;
        return (HttpSession) map.get(session_id);
    }


}