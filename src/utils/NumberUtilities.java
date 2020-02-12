package utils;

public class NumberUtilities {
	public static Integer tryParseToInt(String str) {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException ex) {
			return null;
		}
	}
	
	public static Double tryParseToDouble(String str) {
		try {
			return Double.parseDouble(str);
		} catch (NumberFormatException ex) {
			return null;
		}
	}
}
