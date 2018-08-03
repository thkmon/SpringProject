package com.thkmon.util.data;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import com.thkmon.exception.MsgException;

public class ReqInfo {
	
	private ArrayList<String> paramKeyList = null;
	private ArrayList<String> paramValueList = null;
	private ArrayList<Integer> paramMarkList = null;
	
	
	public ReqInfo(HttpServletRequest req) {
		if (req == null) {
			return;
		}
		
		Enumeration<String> paramNames = req.getParameterNames();
		if (paramNames != null) {
			paramKeyList = new ArrayList<String>();
			paramValueList = new ArrayList<String>();
			paramMarkList = new ArrayList<Integer>();
			
			String oneKey = null;
			String oneValue = null;
			while (paramNames.hasMoreElements()) {
				oneKey = paramNames.nextElement();
				if (oneKey == null || oneKey.length() == 0) {
					continue;
				}
				
				paramKeyList.add(oneKey);
				
				oneValue = req.getParameter(oneKey);
				if (oneValue == null) {
					oneValue = "";
				}
				
				paramValueList.add(oneValue);
				paramMarkList.add(0);
			}
		}
	}
	
	
	public String getParamNotEmpty(String name) throws MsgException {
		String value = getParam(name);
		if (value == null || value.length() == 0) {
			throw new MsgException("ReqInfo getParamNotEmpty : key [" + name + "] is null or empty");
		}
		
		return value;
	}
	
	public String getParam(String name) {
		if (name == null || name.length() == 0) {
			return "";
		}
		
		if (paramKeyList == null || paramKeyList.size() == 0) {
			return "";
		}
		
		String result = "";
		String oneKey = null;
		int paramCount = paramKeyList.size();
		for (int i=0; i<paramCount; i++) {
			oneKey = paramKeyList.get(i);
			if (oneKey == null || oneKey.length() == 0) {
				continue;
			}
			
			if (name.equals(oneKey)) {
				result = paramValueList.get(i);
				
				// key에 대한 value를 꺼낼 때마다 숫자 1을 마킹한다.
				paramMarkList.set(i, 1);
				break;
			}
		}
		
		if (result == null) {
			result = "";
		}
		
		return result;
	}
	
	
	/**
	 * 모든 파라미터가 마킹됐는지 검사한다.
	 * 
	 * @throws Exception
	 */
	public void checkAllMarked() throws MsgException {
		if (paramMarkList == null || paramMarkList.size() == 0) {
			return;
		}
		
		int oneMark = 0;
		int count = paramMarkList.size();
		for (int i=0; i<count; i++) {
			oneMark = paramMarkList.get(i);
			if (oneMark == 1) {
				continue;
			}
			
			String oneKey = paramKeyList.get(i);
			if (oneKey == null || oneKey.length() == 0) {
				continue;
			}
			
			throw new MsgException("ReqInfo checkAllMark : key [" + oneKey + "] is not marked");
		}
	}
	
	
	public String toString() {
		if (paramKeyList == null || paramKeyList.size() == 0) {
			return "ReqInfo : empty";
		}
		
		if (paramValueList == null || paramValueList.size() == 0) {
			return "ReqInfo : empty";
		}
		
		int keyCount = paramKeyList.size();
		int valueCount = paramValueList.size();
		
		if (keyCount != valueCount) {
			return "ReqInfo : invalid";
		}
		
		StringBuffer buff = new StringBuffer();
		
		String oneKey = null;
		String oneValue = null;
		
		for (int i=0; i<keyCount; i++) {
			oneKey = paramKeyList.get(i);
			oneValue = paramValueList.get(i);
			
			if (oneKey == null) {
				oneKey = "";
			}
			
			if (oneValue == null) {
				oneValue = "";
			}
			
			if (buff.length() > 0) {
				buff.append(" / ");
			}
			
			buff.append("[").append(oneKey).append("] == [");
			buff.append(oneValue).append("]");
		}
		
		return "ReqInfo : " + buff.toString();
	}
}
