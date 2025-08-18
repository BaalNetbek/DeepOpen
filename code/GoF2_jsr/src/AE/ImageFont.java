package AE;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class ImageFont {
	private Image fontTexture;
	private short[] posX;
	private short[] posY;
	private byte[] widths;
	private byte spacingY;
	private byte tileHeight;
	private byte tileWidth;
	private static Graphics graphics;
	private byte spacingX;
	private byte offsetY = 0;
	/**
	 * 
	 * @param path
	 * @param gfx
	 * @param id  - legacy
	 * @param rows
	 * @param cols
	 */
	
	public ImageFont(final String path, final Graphics gfx, int id, final int rows, final int cols) {
		graphics = gfx;

		try {
			this.fontTexture = Image.createImage(path);
			this.posX = new short[rows * cols];
			this.posY = new short[this.posX.length];
			this.widths = new byte[this.posX.length];
			this.tileHeight = this.spacingY = (byte)(this.fontTexture.getHeight() / rows);
			this.tileWidth = (byte)(this.fontTexture.getWidth() / cols);
			final int[] fontRGBdata = new int[this.fontTexture.getWidth() * this.fontTexture.getHeight()];
			this.fontTexture.getRGB(fontRGBdata, 0, this.fontTexture.getWidth(), 0, 0, this.fontTexture.getWidth(), this.fontTexture.getHeight());

			for(int i = 0; i < cols; ++i) {
				for(int j = 0; j < rows; ++j) {
					this.posX[i + cols * j] = (short)(i * this.tileWidth);
					this.posY[i + cols * j] = (short)(j * this.spacingY);
					this.widths[i + cols * j] = getSymbolWidth(i * this.tileWidth, j * this.spacingY, fontRGBdata);
				}
			}
			// gof font specific 
			this.widths[charToSymbolIdx('1')] = this.widths[charToSymbolIdx('0')];
			this.spacingX = 1;
			this.widths[0] = 4;
		} catch (final Exception e) {
			this.fontTexture = null;
		}

		System.gc();
	}

	public static void setGraphics(final Graphics var0) {
		graphics = var0;
	}

	public final void setSpacingX(final int var1) {
		this.spacingX = (byte)var1;
	}
	/** 
	 * @param x
	 * @param y
	 * @param fontRGBdata
	 * @return distance from left side to the most right of symbol
	 */
	private byte getSymbolWidth(final int x, final int y, final int[] fontRGBdata) {
		byte width = (byte)(this.tileWidth - 1);

		for(byte i = 0; i < this.tileWidth; ++i) {
			for(byte j = 0; j < this.spacingY; ++j) {
				int pixel = fontRGBdata[i + x + (j + y) * this.fontTexture.getWidth()];
				//not fully transparent and not fully white
				if (pixel >>> 24 > 0 && pixel != 0xffffffff) {
					width = i;
					break;
				}
			}
		}

		return (byte)(width + 1);
	}

	public final int stringWidth(final String text) {
		int width = 0;
		for(int i = 0; i < text.length(); ++i) {
			int sym = charToSymbolIdx(text.charAt(i));
			if (sym == -1) {
				sym = charToSymbolIdx('.');

				for(int j = 0; j < 3; ++j) {
					width += this.widths[sym] + this.spacingX;
				}
			} else {
				width += this.widths[sym] + this.spacingX;
			}
		}

		return width;
	}

	public final int subStringWidth(final String text, int start, int end) {
		int width = 0;
		start = start >= 0 ? start : 0; //max
		end = end <= text.length() ? end : text.length(); //min

		for(int i = start; i < end; i++) {
			int symIdx = charToSymbolIdx(text.charAt(i));
			if (symIdx == -1) {
				symIdx = charToSymbolIdx('.');
				
				//width += 3*(this.widths[symIdx] + this.spacingX);
				for(int j = 0; j < 3; ++j) {
					width += this.widths[symIdx] + this.spacingX;
				}
			} else {
				width += this.widths[symIdx] + this.spacingX;
			}
		}

		return width;
	}

	private static int charToSymbolIdx(final char c) {
		switch(c) {
		case ' ':
			return 0;
		case '!':
			return 1;
		case '"':
			return 2;
		case '#':
			return 3;
		case '$':
			return 4;
		case '%':
			return 5;
		case '&':
			return 6;
		case '\'':
		case '`':
		case '’':
			return 7;
		case '(':
			return 8;
		case ')':
			return 9;
		case '*':
			return 10;
		case '+':
			return 11;
		case ',':
			return 12;
		case '-':
			return 13;
		case '.':
			return 14;
		case '/':
			return 15;
		case '0':
			return 16;
		case '1':
			return 17;
		case '2':
			return 18;
		case '3':
			return 19;
		case '4':
			return 20;
		case '5':
			return 21;
		case '6':
			return 22;
		case '7':
			return 23;
		case '8':
			return 24;
		case '9':
			return 25;
		case ':':
			return 26;
		case ';':
			return 27;
		case '<':
			return 28;
		case '=':
			return 29;
		case '>':
			return 30;
		case '?':
			return 31;
		case '@':
			return 32;
		case 'A':
		case 'А':
			return 33;
		case 'B':
		case 'В':
			return 34;
		case 'C':
		case 'С':
			return 35;
		case 'D':
			return 36;
		case 'E':
		case 'Е':
			return 37;
		case 'F':
			return 38;
		case 'G':
			return 39;
		case 'H':
		case 'Н':
			return 40;
		case 'I':
			return 41;
		case 'J':
			return 42;
		case 'K':
		case 'К':
			return 43;
		case 'L':
			return 44;
		case 'M':
		case 'М':
			return 45;
		case 'N':
			return 46;
		case 'O':
		case 'О':
			return 47;
		case 'P':
		case 'Р':
			return 48;
		case 'Q':
			return 49;
		case 'R':
			return 50;
		case 'S':
			return 51;
		case 'T':
		case 'Т':
			return 52;
		case 'U':
			return 53;
		case 'V':
			return 54;
		case 'W':
			return 55;
		case 'X':
		case 'Х':
			return 56;
		case 'Y':
			return 57;
		case 'Z':
			return 58;
		case '[':
			return 59;
		case '\\':
			return 60;
		case ']':
			return 61;
		case '^':
			return 62;
		case '_':
			return 63;
		case 'a':
		case 'ª':
		case 'а':
			return 64;
		case 'b':
			return 65;
		case 'c':
		case 'с':
			return 66;
		case 'd':
			return 67;
		case 'e':
		case 'е':
			return 68;
		case 'f':
			return 69;
		case 'g':
			return 70;
		case 'h':
			return 71;
		case 'i':
			return 72;
		case 'j':
			return 73;
		case 'k':
			return 74;
		case 'l':
			return 75;
		case 'm':
			return 76;
		case 'n':
			return 77;
		case 'o':
		case 'º':
		case 'о':
			return 78;
		case 'p':
		case 'р':
			return 79;
		case 'q':
			return 80;
		case 'r':
			return 81;
		case 's':
			return 82;
		case 't':
			return 83;
		case 'u':
			return 84;
		case 'v':
			return 85;
		case 'w':
			return 86;
		case 'x':
		case 'х':
			return 87;
		case 'y':
			return 88;
		case 'z':
			return 89;
		case '{':
			return 90;
		case '|':
			return 91;
		case '}':
			return 92;
		case '~':
			return 93;
		case '\u0099':
			return 96;
		case '¡':
			return 97;
		case '©':
			return 95;
		case '«':
			return 98;
		case '®':
			return 94;
		case '»':
			return 99;
		case '¿':
			return 100;
		case 'À':
			return 101;
		case 'Á':
			return 102;
		case 'Â':
			return 103;
		case 'Ã':
			return 104;
		case 'Ä':
			return 105;
		case 'Æ':
			return 106;
		case 'Ç':
			return 107;
		case 'È':
			return 108;
		case 'É':
			return 109;
		case 'Ê':
			return 110;
		case 'Ë':
			return 111;
		case 'Ì':
			return 112;
		case 'Í':
			return 113;
		case 'Î':
			return 114;
		case 'Ï':
			return 115;
		case 'Ñ':
			return 116;
		case 'Ò':
			return 117;
		case 'Ó':
			return 118;
		case 'Ô':
			return 119;
		case 'Õ':
			return 120;
		case 'Ö':
			return 121;
		case 'Ù':
			return 122;
		case 'Ú':
			return 123;
		case 'Û':
			return 124;
		case 'Ü':
			return 125;
		case 'Ý':
			return 203;
		case 'ß':
			return 126;
		case 'à':
			return 128;
		case 'á':
			return 129;
		case 'â':
			return 130;
		case 'ã':
			return 131;
		case 'ä':
			return 132;
		case 'æ':
			return 133;
		case 'ç':
			return 134;
		case 'è':
			return 135;
		case 'é':
			return 136;
		case 'ê':
			return 137;
		case 'ë':
			return 138;
		case 'ì':
			return 139;
		case 'í':
			return 140;
		case 'î':
			return 141;
		case 'ï':
			return 142;
		case 'ñ':
			return 143;
		case 'ò':
			return 144;
		case 'ó':
			return 145;
		case 'ô':
			return 146;
		case 'õ':
			return 147;
		case 'ö':
			return 148;
		case 'ù':
			return 149;
		case 'ú':
			return 150;
		case 'û':
			return 151;
		case 'ü':
			return 152;
		case 'ý':
			return 213;
		case 'ÿ':
			return 153;
		case 'Ą':
			return 223;
		case 'ą':
			return 231;
		case 'Ć':
			return 224;
		case 'ć':
			return 232;
		case 'Č':
			return 204;
		case 'č':
			return 214;
		case 'Ď':
			return 205;
		case 'ď':
			return 215;
		case 'Ę':
			return 225;
		case 'ę':
			return 233;
		case 'Ě':
			return 206;
		case 'ě':
			return 216;
		case 'Ł':
			return 226;
		case 'ł':
			return 234;
		case 'Ń':
			return 227;
		case 'ń':
			return 235;
		case 'Ň':
			return 207;
		case 'ň':
			return 217;
		case 'Œ':
			return 154;
		case 'œ':
			return 155;
		case 'Ř':
			return 208;
		case 'ř':
			return 218;
		case 'Ś':
			return 228;
		case 'ś':
			return 236;
		case 'Š':
			return 209;
		case 'š':
			return 219;
		case 'Ť':
			return 210;
		case 'ť':
			return 220;
		case 'Ů':
			return 211;
		case 'ů':
			return 221;
		case 'Ÿ':
			return 127;
		case 'Ź':
			return 229;
		case 'ź':
			return 237;
		case 'Ż':
			return 230;
		case 'ż':
			return 238;
		case 'Ž':
			return 212;
		case 'ž':
			return 222;
		case 'Б':
			return 156;
		case 'Г':
			return 157;
		case 'Д':
			return 158;
		case 'Ж':
			return 159;
		case 'З':
			return 160;
		case 'И':
			return 161;
		case 'Й':
			return 162;
		case 'Л':
			return 163;
		case 'П':
			return 164;
		case 'У':
			return 165;
		case 'Ф':
			return 166;
		case 'Ц':
			return 167;
		case 'Ч':
			return 168;
		case 'Ш':
			return 169;
		case 'Щ':
			return 170;
		case 'Ъ':
			return 171;
		case 'Ы':
			return 172;
		case 'Ь':
			return 173;
		case 'Э':
			return 174;
		case 'Ю':
			return 175;
		case 'Я':
			return 176;
		case 'б':
			return 177;
		case 'в':
			return 178;
		case 'г':
			return 179;
		case 'д':
			return 180;
		case 'ж':
			return 181;
		case 'з':
			return 182;
		case 'и':
			return 183;
		case 'й':
			return 184;
		case 'к':
			return 185;
		case 'л':
			return 186;
		case 'м':
			return 187;
		case 'н':
			return 188;
		case 'п':
			return 189;
		case 'т':
			return 190;
		case 'у':
			return 191;
		case 'ф':
			return 192;
		case 'ц':
			return 193;
		case 'ч':
			return 194;
		case 'ш':
			return 195;
		case 'щ':
			return 196;
		case 'ъ':
			return 197;
		case 'ы':
			return 198;
		case 'ь':
			return 199;
		case 'э':
			return 200;
		case 'ю':
			return 201;
		case 'я':
			return 202;
		default:
			return 0;
		}
	}

	public final void setOffsetY(final int var1) {
		this.offsetY = (byte)var1;
	}

	public final void drawString(final String text, final int x, final int y) {
		int cursor = 0;
		for(int i = 0; i < text.length(); ++i) {
			int sym = charToSymbolIdx(text.charAt(i));
			if (sym == -1) {
				sym = charToSymbolIdx('.');

				for(int j = 0; j < 3; ++j) {
					graphics.drawRegion(this.fontTexture, this.posX[sym], this.posY[sym], this.widths[sym], this.tileHeight, 0, x + cursor, y + this.offsetY, 0);
					cursor += this.widths[sym] + this.spacingX;
				}
			} else {
				graphics.drawRegion(this.fontTexture, this.posX[sym], this.posY[sym], this.widths[sym], this.tileHeight, 0, x + cursor, y + this.offsetY, 0);
				cursor += this.widths[sym] + this.spacingX;
			}
		}

	}

	public final void drawStringRightAlligned(final String text, final int x, final int y) {
		int cursor = 0;
		for(int i = text.length() - 1; i >= 0; --i) {
			int sym = charToSymbolIdx(text.charAt(i));
			if (sym == -1) {
				sym = charToSymbolIdx('.');

				for(int j = 0; j < 3; ++j) {
					cursor += this.widths[sym];
					graphics.drawRegion(this.fontTexture, this.posX[sym], this.posY[sym], this.widths[sym], this.tileHeight, 0, x - cursor, y + this.offsetY, 0);
					cursor += this.spacingX;
				}
			} else {
				cursor += this.widths[sym];
				graphics.drawRegion(this.fontTexture, this.posX[sym], this.posY[sym], this.widths[sym], this.tileHeight, 0, x - cursor, y + this.offsetY, 0);
				cursor += this.spacingX;
			}
		}

	}

	public final int getSpacingY() {
		return this.spacingY;
	}

	public final int getTileHeight() {
		return this.tileHeight;
	}

	public final void setSpacingY(final int var1) {
		this.spacingY = (byte)var1;
	}
}
