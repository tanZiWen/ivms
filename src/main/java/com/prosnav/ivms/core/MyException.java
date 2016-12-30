package com.prosnav.ivms.core;

import java.util.Map;

public class MyException {
	public static void validInitParams(Map<String, String> vaildMsg) {
		for (String key : vaildMsg.keySet())  {
			if(key == null || key == "") {
				try {
					throw new Exception(vaildMsg.get(key));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	public static void validInitParam(String key, String errMsg) {
		if(key == null || key == "") {
			try {
				throw new Exception(errMsg);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
