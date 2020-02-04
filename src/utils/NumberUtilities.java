package utils;

public class NumberUtilities {
	public static Integer tryParseToInt(String str) {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException ex) {
			return null;
		}
	}
}
