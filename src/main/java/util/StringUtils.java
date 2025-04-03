package util;

public class StringUtils {
	private StringUtils() {
	}

	public static boolean isEmpty(String str) {
		if (str == null) {
			return true;
		}

		return str.trim().isEmpty();
	}

	public static boolean isInteger(String str) {
		if (str == null) {
			return false;
		}

		try {
			Integer.parseInt(str);
			return true;

		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static boolean isDouble(String str) {
		if (str == null) {
			return false;
		}

		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}

	}

}
