package GoF2;

public final class Agent {
	public String fullName;
	public String wingman1Name;
	public String wingman2Name;
	private int itemId;
	private int itemQuantity;
	private int sellingPrice;
	private final int messageId;
	private final int stationId;
	private final int systemId;
	private final int race;
	private final boolean male;
	private int event;
	private final int origin_;
	private int type;
	public int wingmanFriendsCount;
	private int diplomacyPrice;
	private final int secretSystemId;
	private final int blueprintItemId;
	private String message = "";
	private String stationName;
	public String systemName;
	private boolean accepted;
	private boolean unused_0_;
	private byte[] face;
	private Mission mission;
	public boolean wasAskedForDifficulty;
	public boolean wasAskedForLocation;

	public Agent(final int var1, final String var2, final int var3, final int var4, final int var5, final boolean var6, final int var7, final int var8, final int var9) {
		this.messageId = var1;
		this.fullName = var2;
		this.stationId = var3;
		this.systemId = var4;
		this.race = var5;
		this.male = var6;
		this.event = 0;
		this.secretSystemId = var7;
		if (var7 >= 0) {
			this.type = 4;
		}

		this.blueprintItemId = var8;
		if (var8 >= 0) {
			this.type = 3;
		}

		this.sellingPrice = var9;
		this.origin_ = var1 >= 0 ? 0 : 1;
		this.wingmanFriendsCount = 0;
		this.accepted = false;
		this.unused_0_ = false;
		this.wasAskedForDifficulty = false;
		this.wasAskedForLocation = false;
	}

	public final int getMessageId() {
		return this.messageId;
	}

	public final String getFullName() {
		return this.fullName;
	}

	public final int getType() {
		return this.type;
	}

	public final int getRace() {
		return this.race;
	}

	public final int getSystemId() {
		return this.systemId;
	}

	public final int getStationId() {
		return this.stationId;
	}

	public final boolean isMale() {
		return this.male;
	}

	public final int getEvent() {
		return this.event;
	}

	public final void setEvent(final int var1) {
		this.event = var1;
	}

	public final boolean isSpecialAgent() {
		return this.origin_ == 0;
	}

	public final boolean isGenericAgent_() {
		return this.origin_ == 1;
	}

	public final void setImageParts(final byte[] var1) {
		this.face = var1;
	}

	public final byte[] getFace() {
		return this.face;
	}

	public final void setType(final int var1) {
		this.type = var1;
	}

	public final void setMission(final Mission var1) {
		this.mission = var1;
	}

	public final Mission getMission() {
		return this.mission;
	}

	public final boolean isKnown() {
		return this.event > 0;
	}

	public final int getSellSystemIndex() {
		return this.secretSystemId;
	}

	public final int getSellBlueprintIndex() {
		return this.blueprintItemId;
	}

	public final void setCosts(final int var1) {
		this.diplomacyPrice = var1;
	}

	public final int getCosts() {
		return this.diplomacyPrice;
	}

	public final int getWingmanFriendsCount_() {
		return this.wingmanFriendsCount;
	}

	public final String getWingmanName(final int var1) {
		return this.wingman1Name;
	}

	public final String[] getWingmenNames() {
		String[] var1;
		(var1 = new String[1 + this.wingmanFriendsCount])[0] = this.fullName;
		if (this.wingmanFriendsCount > 0) {
			var1[1] = this.wingman1Name;
		}

		if (this.wingmanFriendsCount > 1) {
			var1[2] = this.wingman2Name;
		}

		return var1;
	}

	public final String getMessage() {
		return this.message;
	}

	public final void setMessage(final String var1) {
		this.message = var1;
	}

	public final int getSellItemIndex() {
		return this.itemId;
	}

	public final int getSellItemQuantity() {
		return this.itemQuantity;
	}

	public final int getSellItemPrice() {
		return this.sellingPrice;
	}

	public final void setSellItem(final int var1, final int var2, final int var3) {
		this.itemId = var1;
		this.itemQuantity = var2;
		this.sellingPrice = var3;
	}

	public final boolean isAccepted() {
		return this.accepted;
	}

	public final void setAccepted(final boolean var1) {
		this.accepted = var1;
	}

	public final boolean getUnused0_() {
		return this.unused_0_;
	}

	public final void setUnused0_(final boolean var1) {
		this.unused_0_ = var1;
	}

	public final String getStationName() {
		return this.stationName;
	}

	public final void setAgentsStationName(final String var1) {
		this.stationName = var1.equals("") ? null : var1;
	}

	public final void setAgentsSystemName(final String var1) {
		this.systemName = var1.equals("") ? null : var1;
	}
}
