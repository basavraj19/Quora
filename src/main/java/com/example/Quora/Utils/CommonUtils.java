package com.example.Quora.Utils;

public class CommonUtils {

	public static boolean isValidObject(final Object obj) {
		if (obj != null) {
			return true;
		}

		return false;
	}

	public static boolean isValidString(final String str) {
		if (str != null && str.length() > 0) {
			return true;
		}

		return false;
	}
}
