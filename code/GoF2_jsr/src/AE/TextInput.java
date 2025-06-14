package AE;
/** Unused in GoF2 - legacy from Deep.
 * 	Handles text input from phone keyboard.
 * @author fishlabs
 *
 */
public final class TextInput {
	private static boolean lowerCase;
	private static final char[][] upperCaseKeyMap = {{'A', 'B', 'C', '2'}, {'D', 'E', 'F', '3'}, {'G', 'H', 'I', '4'}, {'J', 'K', 'L', '5'}, {'M', 'N', 'O', '6'}, {'P', 'Q', 'R', 'S', '7'}, {'T', 'U', 'V', '8'}, {'W', 'X', 'Y', 'Z', '9'}, {'0'}, {'1', '@', '.', '-', '_'}, {' '}};
	private static final char[][] lowerCaseKeyMap;
	private final int maxLen = 10;
	private int lastPressedKey = 0;
	private int keyPressStrike;
	private int keyPressedIndex;
	private int timeSincePressed;
	private char nextChar = ' ';
	private String text = "";
	private int keyPressed;
	private final boolean allowAtSymbol = true;

	public TextInput(final int var1) {
	}

	private static int getKeyIndex(final int var0) {
		switch(var0) {
		case 2:
			return 0;
		case 4:
			return 2;
		case 32:
			return 4;
		case 64:
			return 6;
		case 256:
			return 3;
		case 512:
			return 5;
		case 1024:
			return 7;
		case 2048:
			return -2;
		case 4096:
			return 10;
		case 32768:
			return 9;
		case 65536:
			return 1;
		case 131072:
			return 8;
		default:
			return -1;
		}
	}

	public final void handleKeystate(final int var1) {
		this.timeSincePressed = 0;
		if (getKeyIndex(var1) == -2) {
			lowerCase = !lowerCase;
		} else {
			this.keyPressed = var1;
			if (var1 != 8192 && var1 != 524288) {
				if (var1 != this.lastPressedKey && this.nextChar != ' ' && this.text.length() < this.maxLen - 1) {
					this.text = this.text + this.nextChar;
					this.keyPressStrike = 0;
				}

				this.keyPressedIndex = getKeyIndex(var1);
				if (this.keyPressedIndex < 0) {
					return;
				}

				if (this.keyPressStrike >= upperCaseKeyMap[this.keyPressedIndex].length) {
					this.keyPressStrike = 0;
				}

				if (lowerCase) {
					this.nextChar = lowerCaseKeyMap[this.keyPressedIndex][this.keyPressStrike++];
				} else {
					this.nextChar = upperCaseKeyMap[this.keyPressedIndex][this.keyPressStrike++];
				}

				if (!this.allowAtSymbol && this.nextChar == '@') {
					if (lowerCase) {
						this.nextChar = lowerCaseKeyMap[this.keyPressedIndex][this.keyPressStrike++];
					} else {
						this.nextChar = upperCaseKeyMap[this.keyPressedIndex][this.keyPressStrike++];
					}
				}

				if (this.text.length() > 0 && this.text.charAt(this.text.length() - 1) != ' ') {
					this.nextChar = String.valueOf(this.nextChar).charAt(0);
				}

				this.lastPressedKey = var1;
			} else if (this.text.length() > 0) {
				this.timeSincePressed = 0;
				this.nextChar = ' ';
				this.keyPressStrike = 0;
				this.text = this.text.substring(0, this.text.length() - 1);
			}

		}
	}

	public final int update(final int var1) {
		this.timeSincePressed += var1;
		if (this.keyPressed == 16384) {
			return 0;
		}
		if (this.text.length() >= this.maxLen - 1) {
			return 2;
		} else if ((this.timeSincePressed > 1000 || this.nextChar == ' ') && this.keyPressed != 0 && this.keyPressed != 8192 && this.keyPressed != 524288) {
			this.timeSincePressed = 0;
			this.lastPressedKey = 0;
			this.text = this.text + this.nextChar;
			this.nextChar = ' ';
			this.keyPressed = 0;
			this.keyPressStrike = 0;
			return 0;
		} else {
			return 2;
		}
	}

	public final char getNextChar() {
		return this.nextChar;
	}

	public final String getText() {
		return this.text;
	}

	static {
		lowerCaseKeyMap = new char[][]{{'a', 'b', 'c', '2'}, {'d', 'e', 'f', '3'}, {'g', 'h', 'i', '4'}, {'j', 'k', 'l', '5'}, {'m', 'n', 'o', '6'}, {'p', 'q', 'r', 's', '7'}, {'t', 'u', 'v', '8'}, {'w', 'x', 'y', 'z', '9'}, upperCaseKeyMap[8], upperCaseKeyMap[9], upperCaseKeyMap[10]};
	}
}
