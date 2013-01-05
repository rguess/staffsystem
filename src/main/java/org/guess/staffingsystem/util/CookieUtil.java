package org.guess.staffingsystem.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieUtil {

	public static String getValue(HttpServletRequest request, String cName) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if (cName.equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

	public static Cookie getCookie(HttpServletRequest request, String cName) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if (cName.equals(cookie.getName())) {
					return cookie;
				}
			}
		}
		return null;
	}
	
	//通过cookie获取username
	public static String getByUsernameByCookie(HttpServletRequest request, String cName){
		
		String value = getValue(request, cName);
		if(value == null){
			return null;
		}
		System.out.println(value.substring(0, value.lastIndexOf("####")));
		return value.substring(0, value.lastIndexOf("####"));
	}
	
	public static void main(String[] args) {
		String str = "aaaa####bbbb";
		System.out.println(str.substring(0, str.lastIndexOf("####")));
	}
}
