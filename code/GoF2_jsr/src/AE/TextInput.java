package AE;

import GOF2.Main.GOF2Canvas;

/** Unused in GoF2 - legacy from Deep.
 * 	Handles text input from phone keyboard.
 * @author fishlabs
 *
 */
public final class TextInput {
	private static boolean lowerCase;
	private static final char[][] upperCaseKeyMap = {
			{'A', 'B', 'C', '2'}, 
			{'D', 'E', 'F', '3'},
			{'G', 'H', 'I', '4'}, 
			{'J', 'K', 'L', '5'},	
			{'M', 'N', 'O', '6'},
			{'P', 'Q', 'R', 'S', '7'},	
			{'T', 'U', 'V', '8'}, 
			{'W', 'X', 'Y', 'Z', '9'},
			{'0'}, 						
			{'1', '@', '.', '-', '_'}, 		
			{' '}
	};
	private static final char[][] lowerCaseKeyMap = {
         {'a', 'b', 'c', '2'},
         {'d', 'e', 'f', '3'},
         {'g', 'h', 'i', '4'},
         {'j', 'k', 'l', '5'},
         {'m', 'n', 'o', '6'},
         {'p', 'q', 'r', 's', '7'},
         {'t', 'u', 'v', '8'},
         {'w', 'x', 'y', 'z', '9'}, 
         {'0'}, 
         {'1', '@', '.', '-', '_'}, 
         {' '}
   };
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
		case GOF2Canvas.UP:
			return 0;
		case GOF2Canvas.LEFT:
			return 2;
		case GOF2Canvas.RIGHT:
			return 4;
		case GOF2Canvas.DOWN:
			return 6;
		case GOF2Canvas.KEY_5:
			return 3;
		case GOF2Canvas.KEY_7:
			return 5;
		case GOF2Canvas.KEY_9:
			return 7;
		case GOF2Canvas.STAR:
			return -2;
		case GOF2Canvas.HASH:
			return 10;
		case GOF2Canvas.KEY_1:
			return 9;
		case GOF2Canvas.KEY_3:
			return 1;
		case GOF2Canvas.KEY_0:
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
			if (var1 != GOF2Canvas.RSB && var1 != GOF2Canvas.KEY_C) {
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
		} else if ((this.timeSincePressed > 1000 || this.nextChar == ' ') && this.keyPressed != 0 && this.keyPressed != GOF2Canvas.RSB && this.keyPressed != GOF2Canvas.KEY_C) {
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
}