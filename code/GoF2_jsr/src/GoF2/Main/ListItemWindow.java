package GoF2.Main;

import javax.microedition.lcdui.Image;

import AE.AECamera;
import AE.GlobalStatus;
import AE.Group;
import AE.PaintCanvas.Font;
import AE.PaintCanvas.ImageFactory;
import AE.Math.AEMath;
import GoF2.FileRead;
import GoF2.GameText;
import GoF2.Globals;
import GoF2.Item;
import GoF2.Layout;
import GoF2.ListItem;
import GoF2.Ship;
import GoF2.SolarSystem;
import GoF2.Status;
import GoF2.TextBox;

public final class ListItemWindow {
	private final short[] descriptions = {
	      327, 327, 327, 327, 327, 327, 327, 327, 328, 329, 329, 330, 331, 331, 331, 331, 331, 331, 331, 331, 331, 331,
	      332, 332, 333, 334, 335, 336, 337, 337, 337, 338, 338, 339, 340, 340, 341, 342, 341, 343, 343, 344, 344, 344,
	      345, 346, 347, 348, 348, 348, 349, 349, 349, 349, 350, 351, 351, 351, 352, 353, 0, 0, 0, 354, 354, 354, 354,
	      355, 356, 356, 356, 357, 357, 357, 358, 359, 360, 360, 360, 360, 361, 362, 362, 362, 362, 363, 364, 364, 364,
	      364, 365, 366, 366, 366, 367, 367, 368, 369, 369, 369, 369, 369, 369, 369, 369, 369, 369, 369, 369, 369, 369,
	      369, 369, 369, 369, 369, 369, 369, 369, 369, 369, 369, 369, 369, 369, 369, 369, 369, 369, 369, 369, 369, 370,
	      370, 370, 370, 370, 370, 370, 370, 370, 370, 370, 370, 370, 370, 370, 370, 370, 370, 370, 370, 370, 370, 371,
	      371, 371, 371, 371, 371, 371, 371, 371, 371, 371, 372, 372, 372, 372, 372, 372, 372, 372, 372, 372, 372};
	private final short[] LISTITEMWINDOW_HIDDEN_ATTRIBUTES = {
	      0, 1, 4, 5, 6, 7, 8, 35, 36};
	private final String[] _LISTITEMWINDOW_UNITS = {
	      null, null, null, null, null, null, null, null, null, null, null, "ms", "m", "km/h", "m", null, null, "ms",
	      null, "%", "%", null, "ms", "%", "ms", "ms", "%", "ms", null, null, "%", "%", null, "ms", "ms", null, null};
	  private ListItem contextItem;
    private Image items;
    private Image itemTypes;
    private Image ships;
    private Image shipsColor;
    private final TextBox textBox;
    private final TextBox highlightedText;
    private boolean show3DShip;
    private AECamera shipPreviewCam;
    private AECamera lastCam;
    private Group ship;
    private float yaw;
    private float pitch;
    private float speedPitchDown;
    private float speedPitchUp;
    private float speedYawCCW;
    private float speedYawCW;
    private String lowPriceSysRow;
    private String highPriceSysRow;

    public ListItemWindow() {
        this.textBox = new TextBox(10, 40, GlobalStatus.screenWidth - 20, GlobalStatus.screenHeight - 40 - 16 - 20, "");
        this.textBox.setFont(1);
        this.highlightedText = new TextBox(10, 40, GlobalStatus.screenWidth - 20, GlobalStatus.screenHeight - 40 - 16 - 20, "");
        this.highlightedText.setHide(true);
        this.highlightedText.setFont(0);
    }

    public final void setup(final ListItem var1, final Image var2, final Image var3, final Image var4, Image var5, final boolean var6) {
        this.contextItem = var1;
        this.items = var2;
        this.itemTypes = var3;
        this.ships = var4;
        this.shipsColor = var5;
        String var9 = "";
        String var10 = "";
        if (!var1.isItem() && !var1.isBluePrint() && !var1.isPendingProduct()) {
            if (var1.isShip()) {
                var9 += GlobalStatus.gameText.getText(60);
                var10 += var1.ship.getBaseHP();
                var9 += "\n" + GlobalStatus.gameText.getText(61);
                var10 += "\n" + var1.ship.getBaseLoad();
                var9 += "\n" + GlobalStatus.gameText.getText(123);
                var10 += "\n" + var1.ship.getSlots(0);
                var9 += "\n" + GlobalStatus.gameText.getText(124);
                var10 += "\n" + var1.ship.getSlots(1);
                var9 += "\n" + GlobalStatus.gameText.getText(125);
                var10 += "\n" + var1.ship.getSlots(2);
                var9 += "\n" + GlobalStatus.gameText.getText(127);
                var10 += "\n" + var1.ship.getSlots(3);
                var9 += "\n" + GlobalStatus.gameText.getText(59);
                var10 += "\n" + (int)(var1.ship.getHandling() * 100.0F);
                var9 += "\n" + GlobalStatus.gameText.getText(36);
                var10 += "\n" + Layout.formatCredits(var1.getPrice());
                this.textBox.setText(var9);
                this.highlightedText.setText(var10);
                this.yaw = 1900.0F;
                this.pitch = 0.0F;
                this.speedPitchDown = 0.0F;
                this.speedPitchUp = 0.0F;
                this.speedYawCCW = 0.0F;
                this.speedYawCW = 0.0F;
            }

        } else {
            final Item var11 = var1.isItem() ? var1.item : var1.isBluePrint() ? Globals.getItems()[var1.bluePrint.getIndex()] : Globals.getItems()[var1.producedGood.index];


            int var14;
            for(int i = 0; i < 37; ++i) {
                boolean var7 = false;

                for(int j = 0; j < this.LISTITEMWINDOW_HIDDEN_ATTRIBUTES.length; ++j) {
                    if (this.LISTITEMWINDOW_HIDDEN_ATTRIBUTES[j] == i) {
                        var7 = true;
                        break;
                    }
                }
                int attrVal = var11.getAttribute(i);
                if (!var7 && attrVal  != Item.NULL_ATTRIBUTE) {
                    if (!var9.equals("")) {
                        var9 += "\n";
                        var10 += "\n";
                    }

                    var9 += GlobalStatus.gameText.getText(GameText.LISTITEMWINDOW_KEY_TEXT_IDS[i]);
                    if (((i == Item.SHOW_CARGO) || (i == Item.SHOW_ASTEROID)) || (i == Item.TRACTOR_AUTOMATIC)) {
                        var10 += GlobalStatus.gameText.getText(attrVal == 0 ? 39 : 38);
                    } else if (i == Item.SUB_TYPE) {
                        var10 += GlobalStatus.gameText.getText(attrVal + 98);
                    } else {
                        if (i == Item.VELOCITY) {
                            attrVal *= 250;
                        } 
                        if (i == Item.RANGE) {
                            var14 = (attrVal = (int)(attrVal / 3600.0F * (var11.getAttribute(Item.VELOCITY) * 250))) % 100;
                            attrVal = (attrVal + var14) % 100 == 0 ? attrVal + var14 : attrVal - var14;
                        }

                        var10 += attrVal;
                        if (this._LISTITEMWINDOW_UNITS[i] != null) {
                            var10 += this._LISTITEMWINDOW_UNITS[i];
                        }
                    }
                }
            }

            if (!var1.isBluePrint() && !var1.isPendingProduct() && var6) {
                var9 += "\n" + GlobalStatus.gameText.getText(36);
                var10 += "\n" + Layout.formatCredits(var11.getSinglePrice());
            }

            if (var1.isItem()) {
                //new FileRead();
                var5 = null;
                final SolarSystem[] var15 = FileRead.loadSystemsBinary();
                if (Status.lowestItemPrices[var1.getIndex()] > 0) {
                    if (Status.lowestItemPriceSystems[var1.getIndex()] == Status.getSystem().getIndex()) {
                        this.lowPriceSysRow = GlobalStatus.gameText.getText(95);
                    } else {
                        this.lowPriceSysRow = GlobalStatus.gameText.getText(93);
                    }

                    this.lowPriceSysRow = Status.replaceTokens(this.lowPriceSysRow, Status.lowestItemPrices[var1.getIndex()] + "", "#C");
                    this.lowPriceSysRow = Status.replaceTokens(this.lowPriceSysRow, var15[Status.lowestItemPriceSystems[var1.getIndex()]].getName(), "#S");
                } else {
                    this.lowPriceSysRow = null;
                }

                if (Status.highestItemPrices[var1.getIndex()] > 0) {
                    if (Status.highestItemPriceSystems[var1.getIndex()] == Status.getSystem().getIndex()) {
                        this.highPriceSysRow = GlobalStatus.gameText.getText(96);
                    } else {
                        this.highPriceSysRow = GlobalStatus.gameText.getText(94);
                    }

                    this.highPriceSysRow = Status.replaceTokens(this.highPriceSysRow, Status.highestItemPrices[var1.getIndex()] + "", "#C");
                    this.highPriceSysRow = Status.replaceTokens(this.highPriceSysRow, var15[Status.highestItemPriceSystems[var1.getIndex()]].getName(), "#S");
                } else {
                    this.highPriceSysRow = null;
                }

                final String var16 = GlobalStatus.gameText.getText(this.descriptions[var1.getIndex()]);
                var9 += "\n\n" + var16;
                if (this.lowPriceSysRow != null) {
                    var9 += "\n\n" + this.lowPriceSysRow;
                }

                if (this.highPriceSysRow != null) {
                    var9 += "\n\n" + this.highPriceSysRow;
                }
            } else if (var1.isBluePrint() || var1.isPendingProduct()) {
                final String var13 = GlobalStatus.gameText.getText(this.descriptions[var1.getIndex()]);
                var9 += "\n\n" + var13;
            }

            this.textBox.setText(var9);
            this.highlightedText.setText(var10);
            int space = this.textBox.getTextHeight_() - this.highlightedText.getTextHeight_();

            for(var14 = 0; var14 < space; ++var14) {
                var10 += "\n";
            }

            this.highlightedText.setText(var10);
        }
    }

    public final void scrollDown(final int var1) {
        this.textBox.scrollDown(var1);
        this.highlightedText.scrollDown(var1);
    }

    public final void scrollUp(final int var1) {
        this.textBox.scrollUp(var1);
        this.highlightedText.scrollUp(var1);
    }

    public final boolean shows3DShip() {
        return this.show3DShip;
    }

    public final void draw() {
        if (!this.show3DShip) {
            Layout.drawBG();
            Layout.drawWindowFrame(GlobalStatus.gameText.getText(212));
        }

        if (!this.contextItem.isItem() && !this.contextItem.isBluePrint() && !this.contextItem.isPendingProduct()) {
            if (this.contextItem.isShip()) {
                ImageFactory.drawShip(this.contextItem.ship.getIndex(), this.contextItem.ship.getRace(), this.ships, this.shipsColor, 5, 27, 6);
                Font.drawString(GlobalStatus.gameText.getText(532 + this.contextItem.ship.getIndex()), 5 + ImageFactory.faceWidth + 5, 21, 1);
            }
        } else {
            Item var1;
            ImageFactory.drawItem((var1 = this.contextItem.isItem() ? this.contextItem.item : this.contextItem.isBluePrint() ? Globals.getItems()[this.contextItem.bluePrint.getIndex()] : Globals.getItems()[this.contextItem.producedGood.index]).getIndex(), var1.getType(), this.items, this.itemTypes, 5, 27, 6);
            Font.drawString(GlobalStatus.gameText.getText(569 + var1.getIndex()), 5 + ImageFactory.itemFrameWidth + 5, 18, 1);
        }

        this.textBox.draw();
        this.highlightedText.draw();
    }

    public final void updateRotation(final int var1, final int var2) {
        if (this.show3DShip) {
            if ((var1 & 4) != 0) {
                this.speedYawCCW = var2;
            } else {
                this.speedYawCCW *= 0.9F;
            }

            if ((var1 & 32) != 0) {
                this.speedYawCW = var2;
            } else {
                this.speedYawCW *= 0.9F;
            }

            if ((var1 & 64) != 0) {
                this.speedPitchDown = var2;
            } else {
                this.speedPitchDown *= 0.9F;
            }

            if ((var1 & 2) != 0) {
                this.speedPitchUp = var2;
            } else {
                this.speedPitchUp *= 0.9F;
            }

            this.pitch += this.speedPitchDown - this.speedPitchUp;
            if (this.pitch > 768.0F) {
                this.pitch = 768.0F;
            } else if (this.pitch < -256.0F) {
                this.pitch = -256.0F;
            }

            this.yaw += this.speedYawCCW - this.speedYawCW;
        }

    }

    public final void updateCamera_(final boolean var1) {
        this.show3DShip = var1;
        if (var1) {
            this.lastCam = GlobalStatus.renderer.getCamera();
            this.shipPreviewCam = AECamera.create(GlobalStatus.screenWidth, GlobalStatus.screenHeight, 1000, 10, 31768);
            this.shipPreviewCam.translate(0, 400, -Ship.SHIP_PREVIEW_SCALING[this.contextItem.getIndex()]);
            this.shipPreviewCam.rotateEuler(AEMath.Q_PI_SIXTEENTH, AEMath.Q_PI_HALF, 0);
            Group var2 = Globals.getShipGroup(this.contextItem.getIndex(), this.contextItem.ship.getRace());
            var2.translate(0, 0, Ship.SHIP_HANGAR_OFFSETS[this.contextItem.getIndex()]);
            this.ship = new Group();
            this.ship.uniqueAppend_(var2);
        } else if (this.lastCam != null) {
            GlobalStatus.renderer.setActiveCamera(this.lastCam);
        }
    }

    public final void render() {
        this.ship.setRotation((int)this.pitch, (int)this.yaw, 0);
        Layout.drawBG();

        try {
            GlobalStatus.renderer.setActiveCamera(this.shipPreviewCam);
            GlobalStatus.graphics3D.bindTarget(GlobalStatus.graphics);
            GlobalStatus.renderer.drawNodeInVF(this.ship);
            GlobalStatus.renderer.renderFrame(System.currentTimeMillis());
            GlobalStatus.graphics3D.clear();
            GlobalStatus.graphics3D.releaseTarget();
        } catch (final Exception var2) {
            GlobalStatus.graphics3D.releaseTarget();
            var2.printStackTrace();
        }

        Layout.drawNonFullScreenWindow(GlobalStatus.gameText.getText(212), false);
    }
}
