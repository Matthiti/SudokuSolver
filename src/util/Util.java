package util;

public class Util {

	public static boolean isDigit(String s) {
		if (!(s.length() == 1)) {
			return false;
		}
		
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
