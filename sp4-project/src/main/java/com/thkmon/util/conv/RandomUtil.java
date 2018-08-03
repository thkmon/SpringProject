package com.thkmon.util.conv;

public class RandomUtil {

	public String getRandomString(int length) {
		
		StringBuffer buff = new StringBuffer();
		for (int i=0; i<length; i++) {
			buff.append(getRandomSingleString());
		}
		
		return buff.toString();
	}
	
	
	public String getRandomSingleString() {
		// 1 ~ 36 난수생성
		int i = getRandomInt(1, 36);
		i = i - 1;
		
		if (i < 10) {
			return String.valueOf(i);
		}
		
		char c = (char)(i + 55);
		
		// 1 ~ 2 난수생성
		int k = getRandomInt(1, 2);
		if (k == 1) {
			return String.valueOf(c).toLowerCase();
		} else {
			return String.valueOf(c).toUpperCase();
		}
	}

	
	public int getRandomInt(int from, int to) {
		if (from < 1) {
			from = 1;
		}
		
		if (to < 1) {
			to = 1;
		}
		
		int i = (int)(Math.random() * to) + from;
		return i;
	}
}
