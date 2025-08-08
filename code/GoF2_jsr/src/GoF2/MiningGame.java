package GOF2;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import AE.AEFile;
import AE.GlobalStatus;
import AE.Math.AEMath;
import AE.PaintCanvas.Font;
import AE.PaintCanvas.ImageFactory;

public final class MiningGame {
    private int nextLevelTreshold;
    private final int levels;
    private int curLevel;
    private int levelProgress;
    private int nextMinedProgress;
    private int nextMinedThreshold;
    private float cursorAcceleration;
    private float driftSpeed;
    private float driftSuppress;
    private float pressCumulativeAcceleration;
    private float cursorPos;
    private float failProgress;
    private float miningProgress;
    private float minedTons;
    private int minedIndicatorHighlightCounter;
    private int frameTime;
    private float minigSpeed;
    private float controlability;
    private Image background;
    private Image cursor;
    private Image greenComplete;
    private Image greenEmpty;
    private Image redArea;
    private final Image items;
    private final Hud hud;
    private final int minedItemId;
    private final int boardPosX;
    private final int boardPosY;
    private final int failedBarPosX;
    private final int barsPadding;
    private boolean succeed;
    private final int[] _LAYER_WITDTHS = {44, 39, 34, 29, 23, 18, 13};

    public MiningGame(final int var1, final int var2, final Hud var3) {
        if (this.background == null) {
            this.background = AEFile.loadImage("/data/interface/mining_background.png", true);
            this.cursor = AEFile.loadImage("/data/interface/mining_cursor.png", true);
            this.greenComplete = AEFile.loadImage("/data/interface/mining_green_complete.png", true);
            this.greenEmpty = AEFile.loadImage("/data/interface/mining_green_empty.png", true);
            this.redArea = AEFile.loadImage("/data/interface/mining_redarea.png", true);
        }

        this.hud = var3;
        this.items = var3.items;
        this.boardPosX = GlobalStatus.screenWidth / 2 - this.background.getWidth() / 2;
        this.boardPosY = GlobalStatus.screenHeight / 2 + this.background.getHeight() / 2;
        this.failedBarPosX = this.background.getWidth();
        this.barsPadding = 2;
        this.nextMinedThreshold = -1;
        this.driftSpeed = 1.0F;
        this.succeed = false;
        this.minedItemId = var2;
        this.levels = var1;
        final Item var5 = Status.getShip().getFirstEquipmentOfSort(Item.MINING_LASER);
        this.nextLevelTreshold = 6000;
        if (var5 != null) {
            final float var6 = var5.getAttribute(Item.MINING_CONTROL) / 100.0F;
            this.driftSuppress = 25.0F + var6 * 55.0F;
            this.controlability = 50.0F + var6 * 200.0F;
            this.minigSpeed = var5.getAttribute(Item.MINING_EFFICIENCY) / 100.0F;
        }

        this.minedIndicatorHighlightCounter = 400;
    }

    public final boolean gameLost() {
        return this.failProgress >= 2500.0F;
    }

    public final int getMiningProgressRounded() {
        return (int)this.miningProgress;
    }

    public final boolean gotCore() {
        return this.succeed && this.levels == 7;
    }

    public final boolean update(final int var1, final int var2) {
        if (this.curLevel >= this.levels) {
            return false;
        }
        this.frameTime = var1;
        int var3;
        final int var4 = -(var3 = (2 * this.barsPadding + 3 * this._LAYER_WITDTHS[this.curLevel]) / 2);
        this.nextMinedProgress += var1;
        if (AEMath.abs((int)this.cursorPos) > this._LAYER_WITDTHS[this.curLevel] / 2) {
            this.failProgress += var1;
            if (this.failProgress > 2500.0F) {
                this.failProgress = 2500.0F;
                this.miningProgress = 0.0F;
                return false;
            }
        } else {
            this.levelProgress += var1;
            float var5 = 0.15F + (this.curLevel + 1) / 7.0F * 2.35F;
            var5 *= this.minigSpeed;
            this.miningProgress += var5 / 1000.0F * var1;
        }

        if (this.levelProgress > this.nextLevelTreshold) {
            this.levelProgress = 0;
            this.curLevel++;
            this.nextLevelTreshold = (int)(this.nextLevelTreshold * 0.83F);
            if (this.curLevel >= this.levels) {
                this.succeed = true;
                return false;
            }

            this.nextMinedProgress = this.nextMinedThreshold + 1;
            this.cursorPos *= 0.88F;
        }

        if (this.nextMinedProgress > this.nextMinedThreshold) {
            this.nextMinedProgress = 0;
            this.nextMinedThreshold = 500 + (int)(GlobalStatus.random.nextInt(100) / 100.0F * 2000.0F);
            this.cursorAcceleration = GlobalStatus.random.nextInt(2) == 0 ? -1.0F : 1.0F;
            this.driftSpeed = 0.0F;
            this.pressCumulativeAcceleration /= 2.0F;
        }

        this.driftSpeed = var1 / this.driftSuppress * this.cursorAcceleration;
        if ((var2 & 32) != 0) {
            this.pressCumulativeAcceleration -= var1 / this.controlability;
        } else if ((var2 & 4) != 0) {
            this.pressCumulativeAcceleration += var1 / this.controlability;
        } else {
            this.pressCumulativeAcceleration = 0.0F;
        }

        this.cursorPos += this.driftSpeed - this.pressCumulativeAcceleration;
        if (this.cursorPos > var3) {
            this.cursorPos = var3;
        } else if (this.cursorPos < var4) {
            this.cursorPos = var4;
        }

        return true;
    }

    public final void render2D() {
        GlobalStatus.graphics.drawImage(this.background, this.boardPosX, this.boardPosY, 36);
        int var1 = this.levels * 7;
        int var2 = (this.curLevel + 1) * 7;
        GlobalStatus.graphics.setClip(0, this.boardPosY - var1, GlobalStatus.screenWidth, var1);
        GlobalStatus.graphics.drawImage(this.greenEmpty, this.boardPosX + 50, this.boardPosY, 36);
        if (this.curLevel > 0) {
            var1 = this.curLevel * 7;
            GlobalStatus.graphics.setClip(0, this.boardPosY - var1, GlobalStatus.screenWidth, var1);
            GlobalStatus.graphics.drawImage(this.greenComplete, this.boardPosX + 50, this.boardPosY, 36);
        }

        var1 = this._LAYER_WITDTHS[this.curLevel];
        int var3 = (int)((float)this.levelProgress / (float)this.nextLevelTreshold * var1);
        GlobalStatus.graphics.setClip((GlobalStatus.screenWidth >> 1) - (var3 >> 1), this.boardPosY - var2, var3, 7);
        GlobalStatus.graphics.drawImage(this.greenComplete, this.boardPosX + 50, this.boardPosY, 36);
        var2 = this.boardPosY - this.curLevel * 7;
        var3 = this.boardPosX + (this.failedBarPosX - 2 * this.barsPadding - var1 * 3) / 2;
        final int var4 = (int)(this.failProgress / 2500.0F * (var1 + 5));
        GlobalStatus.graphics.setClip(var3 + var1 - var4, var2 - 5, var4, 5);
        GlobalStatus.graphics.drawImage(this.redArea, this.boardPosX, this.boardPosY, 36);
        GlobalStatus.graphics.setClip(var3 + var1 * 2 + 2 * this.barsPadding, var2 - 5, var4, 5);
        GlobalStatus.graphics.drawImage(this.redArea, this.boardPosX, this.boardPosY, 36);
        GlobalStatus.graphics.setClip(0, 0, GlobalStatus.screenWidth, GlobalStatus.screenHeight);
        var1 = (GlobalStatus.screenWidth >> 1) + (int)this.cursorPos;
        GlobalStatus.graphics.drawImage(this.cursor, var1, var2 + 2, 33);
        var1 = this.boardPosY - this.background.getHeight();
        if (this.levels == 7) {
            ImageFactory.drawItemFrameless(this.minedItemId - 154 + 165, this.items, (GlobalStatus.screenWidth >> 1) - 1, var1 + 10, Graphics.HCENTER | Graphics.VCENTER);
        }

        if ((int)this.miningProgress > (int)this.minedTons) {
            this.minedTons = this.miningProgress;
            this.minedIndicatorHighlightCounter = 0;
        }

        final boolean var5 = this.minedIndicatorHighlightCounter < 400;
        String var6;
        Font.drawString(var6 = "    t " + GlobalStatus.gameText.getText(569 + this.minedItemId), GlobalStatus.screenWidth >> 1, this.boardPosY + Font.getFontSpacingY(), 2, 24);
        if (var5) {
            this.hud.drawBigDigits((int)this.miningProgress, (GlobalStatus.screenWidth >> 1) - (Font.getTextWidth(var6, 0) >> 1) - 16, this.boardPosY + Font.getFontSpacingY() - 2, false);
            this.minedIndicatorHighlightCounter += this.frameTime;
        } else {
            final String var10000 = (int)this.miningProgress + "";
            final int var10001 = (GlobalStatus.screenWidth >> 1) - (Font.getTextWidth(var6, 0) >> 1);
            Font.drawString(var10000, var10001 + Font.getTextWidth("   ", 0), this.boardPosY + Font.getFontSpacingY(), 2, 18);
        }
    }
}
