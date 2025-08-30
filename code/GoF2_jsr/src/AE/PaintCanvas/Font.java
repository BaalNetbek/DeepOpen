package AE.PaintCanvas;

import javax.microedition.lcdui.Graphics;

import AE.ImageFont;

public final class Font {
	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	public static final int VCENTER = 4;
	public static final int HCENTER = 8;
	public static final int TOP = 16;
	public static final int BOTTOM = 32;
   
   	public static final int WHITE = 0;
	public static final int GRAY = 1;
	public static final int ORANGE = 2;
	public static final int VOID = 3;
   
	private static ImageFont[] symbolMaps;
	private static Graphics graphics;

	public static void OnRelease() {
		if (symbolMaps != null) {
			for(int i = 0; i < symbolMaps.length; ++i) {
				symbolMaps[i] = null;
			}

			symbolMaps = null;
		}

		System.gc();
	}

	public static void setGraphics(final Graphics gfx) {
		graphics = gfx;
		if (symbolMaps != null) {
			for(int i = 0; i < symbolMaps.length; ++i) {
				ImageFont.setGraphics(graphics);
			}
		}

	}

	public static void addCharMap(final String path, final int id, final int rows, final int cols) {
		if (graphics != null) {
			if (symbolMaps == null) {
			   symbolMaps = new ImageFont[1];
			   symbolMaps[0] = new ImageFont(path, graphics, id, rows, cols);
				return;
			}

			final ImageFont[] pngFont = new ImageFont[symbolMaps.length + 1];
			System.arraycopy(symbolMaps, 0, pngFont, 0, symbolMaps.length);
			pngFont[symbolMaps.length] = new ImageFont(path, graphics, id, rows, cols);
			symbolMaps = pngFont;
		}

	}

	public static void setSymMapSetOffsetY(final int offset, final int font) {
		if (font >= 0 && font < symbolMaps.length) {
			symbolMaps[font].setOffsetY(offset);
		}
	}

	public static void drawString(final String str, final int x, final int y, final int font) {
		if (font >= 0 && font < symbolMaps.length) {
			symbolMaps[font].drawString(str, x, y);
		}
	}

	public static void drawString(final String str, int x, int y, final int font, final int anchor) {
		if ((anchor & HCENTER) != 0) {
			x -= symbolMaps[font].stringWidth(str) >> 1;
		}

		if ((anchor & VCENTER) != 0) {
			y -= symbolMaps[font].getSpacingY() >> 1;
		} else if ((anchor & BOTTOM) != 0) {
			y -= symbolMaps[font].getSpacingY();
		}

		if ((anchor & RIGHT) != 0) {
			symbolMaps[font].drawStringRightAlligned(str, x, y);
		} else {
			symbolMaps[font].drawString(str, x, y);
		}
	}

	public static void drawLines(final String[] str, final int x, final int y, final int font) {
		if (font >= 0 && font < symbolMaps.length) {
			for(int i = 0; i < str.length; ++i) {
				symbolMaps[font].drawString(str[i], x, y + i * getSpacingY());
			}

		}
	}

	public static void drawLinesWithIndent(final String[] str, final int x, final int y, final int font, final int indent_w, final int indent_h) {
		if (font >= 0 && font < symbolMaps.length) {
			int indent_rows = indent_h > 0 ? indent_h / getSpacingY() + 1 : 0;

			for(int i = 0; i < str.length; ++i) {
				symbolMaps[font].drawString(
				  str[i],
				  i < indent_rows ? x + indent_w : x,
				  y + i * getSpacingY()
				);
			}

		}
	}

	public static void drawLinesAligned(final String[] lines, final int x, final int y, final int font, final int anchor) {
		final int var5 = getSpacingY();
		if (font >= 0 && font < symbolMaps.length) {
			int x_offset = 0;

			for(int i = 0; i < lines.length; ++i) {
				if ((anchor & HCENTER) != 0) {
					x_offset = -(symbolMaps[font].stringWidth(lines[i]) >> 1);
				}

				if ((anchor & RIGHT) != 0) {
					symbolMaps[font].drawStringRightAlligned(lines[i], x, y + i * var5);
				} else {
					symbolMaps[font].drawString(lines[i], x + x_offset, y + i * var5);
				}
			}

		}
	}

	public static int getStringWidth(final String str) {
		return getTextWidth(str, 0);
	}

	public static int getTextWidth(final String str, final int font) {
		return font >= 0 && font < symbolMaps.length ? symbolMaps[font].stringWidth(str) : 0;
	}

	public static void setSpacingX(final int spacing) {
		symbolMaps[0].setSpacingX(spacing);
	}

	public static void setSpacingX(final int spacing, final int font) {
		if (font >= 0 && font < symbolMaps.length) {
			symbolMaps[font].setSpacingX(spacing);
		}
	}

	public static int getSpacingY() {
		return symbolMaps[0].getSpacingY();
	}

	public static int getTotalSpacingY(final String[] str) {
		return str.length * symbolMaps[0].getSpacingY();
	}

	public static void setSpacingY(final int spacing) {
		symbolMaps[0].setSpacingY(spacing);
	}

	public static void setSpacingY(final int spacing, final int font) {
		if (font >= 0 && font < symbolMaps.length) {
			symbolMaps[font].setSpacingY(spacing);
		}
	}
	/**
	 * Splits text to lines taking accounting for indentation in top-left corner. Used for making space for face image.
	 * @param str - text
	 * @param width - full width
	 * @param font
	 * @param indent_w
	 * @param indent_h
	 * @return lines
	 */
	public static String[] splitToLines(String str, final int width, final int font, final int indent_w, final int indent_h) {
		int lines_cnt = 0;
		String line = null;
		str = str + "\n";
		int indent_rows = indent_h > 0 ? indent_h / getSpacingY() + 1 : 0;
		
		for(int cursor = 0; cursor < str.length(); cursor += line.length()) {
			line = truncateStringLine(
             str.substring(cursor, str.length()),
             lines_cnt < indent_rows ? width - indent_w : width,
             font,
             false
			);
			++lines_cnt;
		}
		final String[] lines = new String[lines_cnt];
		
		int cursor = 0;
		for(int i = 0; i < lines_cnt; ++i) {
			lines[i] = truncateStringLine(
    			str.substring(cursor, str.length()),
    			i < indent_rows ? width - indent_w : width,
    			font,
    			false
			);
			cursor += lines[i].length();
			lines[i].trim();
		}

		return lines;
	}

	public static String[] splitToLines(String str, final int width) {
		final byte font = 0;
		int numlines = 0;
		String line = null;
		str = str + "\n";
		
		for(int cursor = 0; cursor < str.length(); cursor += line.length()) {
			line = truncateStringLine(str.substring(cursor, str.length()), width, font, false);
			++numlines;
		}

		final String[] lines = new String[numlines];
		int cursor = 0;
		for(int i = 0; i < numlines; ++i) {
			lines[i] = truncateStringLine(str.substring(cursor, str.length()), width, font, false);
			cursor += lines[i].length();
			lines[i].trim();
		}

		return lines;
	}

	public static String truncateStringLine(final String str, final int width, final int font, final boolean brakeWords) {
		int lastBrakePos = 0;
		int cursor_x = (font >= 0 && font < symbolMaps.length ? symbolMaps[font].getTileHeight() : 0) >> 1;

		for(int i = 0; i < str.length(); ++i) {
			if (brakeWords || str.charAt(i) == ' ' || str.charAt(i) == '\n' || str.charAt(i) == '\r') {
				lastBrakePos = i;
			}

			cursor_x += symbolMaps[font].subStringWidth(str, i, i + 1);
			if (cursor_x >= width) {
				if (0 < lastBrakePos) {
					return str.substring(0, lastBrakePos + 1);
				}

				return str.substring(0, i + 1);
			}

			if (str.charAt(i) == '\n' || str.charAt(i) == '\r') {
				return str.charAt(i) == '\n' ? str.substring(0, i + 1).replace('\n', ' ') : str.substring(0, i + 1).replace('\r', ' ');
			}
		}

		if (0 < str.length() - 1) {
			return str.substring(0, str.length());
		}
		return "";
	}
}
