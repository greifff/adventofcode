package adventofcode.util;

/**
 * Provides Ansi color support.
 */
public class Ansi {

	/**
	 * Sets the console foreground color.
	 * 
	 * @param rgbColorCode can be specified as a hexadecimal in the form 0xRRGGBB
	 */
	public static void foreground(int rgbColorCode) {
		int red = (rgbColorCode >> 16) & 255;
		int green = (rgbColorCode >> 8) & 255;
		int blue = rgbColorCode & 255;
		foreground(red, green, blue);
	}

	/**
	 * Sets the console foreground color.
	 * 
	 * @param red   red channel, 0..255
	 * @param green green channel, 0..255
	 * @param blue  blue channel, 0..255
	 */
	public static void foreground(int red, int green, int blue) {
		System.out.print("\u001B[38;2;" + red + ";" + green + ";" + blue + "m");
	}

	/**
	 * Sets the console background color.
	 * 
	 * @param rgbColorCode can be specified as a hexadecimal in the form 0xRRGGBB
	 */
	public static void background(int rgbColorCode) {
		int red = (rgbColorCode >> 16) & 255;
		int green = (rgbColorCode >> 8) & 255;
		int blue = rgbColorCode & 255;
		background(red, green, blue);
	}

	/**
	 * Sets the background foreground color.
	 * 
	 * @param red   red channel, 0..255
	 * @param green green channel, 0..255
	 * @param blue  blue channel, 0..255
	 */
	public static void background(int red, int green, int blue) {
		System.out.print("\u001B[48;2;" + red + ";" + green + ";" + blue + "m");
	}
}
