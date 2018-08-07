package com.thkmon.util.result;

import java.net.URLEncoder;

import com.thkmon.exception.MsgException;

public class ResultUtil {
	
	public static String makeErrorJSON(MsgException e) {
		String msg = "";
		if (e != null && e.getMessage() != null) {
			msg = e.getMessage();
		}
		
		return makeErrorJSON(msg);
	}
	
	
	public static String makeErrorJSON(String msg) {
		if (msg != null && msg.length() > 0) {
			msg = msg.replace("\"", "");
			try {
				msg = URLEncoder.encode(msg, "UTF-8");
				
			} catch (Exception ie) {
				// 무시
			}
		}
		
		StringBuffer buff = new StringBuffer();
		buff.append("{");
		buff.append("\"result\" : 0,");
		buff.append("\"message\" : \"").append(msg).append("\", ");
		buff.append("\"info\" : {");
		buff.append("}");
		buff.append("}");
		
		String result = buff.toString();
		return result;
	}
	
	
	public static String makeSuccessJSON() {
		StringBuffer buff = new StringBuffer();
		buff.append("{");
		buff.append("\"result\" : 1,");
		buff.append("\"message\" : \"\", ");
		buff.append("\"info\" : {");
		buff.append("}");
		buff.append("}");
		
		String result = buff.toString();
		return result;
	}
}