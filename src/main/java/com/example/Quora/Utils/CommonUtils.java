package com.example.Quora.Utils;

import java.util.Set;

public class CommonUtils {

	public static final Set<String> STOP_WORDS = Set.of(
	        "what", "is", "the", "a", "an", "in", "on", "at", "to", "for"
	);
	
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
