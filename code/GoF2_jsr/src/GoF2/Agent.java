package GOF2;
/**
 * This class represents SpaceLounge characters when they are inside or outside the bar.
 * @author fishlabs 2009
 */
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
	/**unused*/
	private boolean reward;
	private byte[] face;
	private Mission mission;
	public boolean wasAskedForDifficulty;
	public boolean wasAskedForLocation;

	public Agent(final int message, final String name, final int stationIdx, final int systemIdx, final int race, final boolean isMale, final int var7, final int var8, final int var9) {
		this.messageId = message;
		this.fullName = name;
		this.stationId = stationIdx;
		this.systemId = systemIdx;
		this.race = race;
		this.male = isMale;
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
		this.origin_ = message >= 0 ? 0 : 1;
		this.wingmanFriendsCount = 0;
		this.accepted = false;
		this.reward = false;
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

	public final void setImageParts(final byte[] face) {
		this.face = face;
	}

	public final byte[] getImageParts() {
		return this.face;
	}
	/** setType */
	public final void setOffer(final int var1) {
		this.type = var1;
	}

	public final void setMission(final Mission mission) {
		this.mission = mission;
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

	public final void setCosts(final int diplomaticBribe) {
		this.diplomacyPrice = diplomaticBribe;
	}

	public final int getCosts() {
		return this.diplomacyPrice;
	}

	public final int getWingmanFriendsCount_() {
		return this.wingmanFriendsCount;
	}

	public final String getWingmanName(final int unused) {
		return this.wingman1Name;
	}

	public final String[] getWingmenNames() {
		String[] names = new String[1 + this.wingmanFriendsCount];
		names[0] = this.fullName;
		if (this.wingmanFriendsCount > 0) {
			names[1] = this.wingman1Name;
		}

		if (this.wingmanFriendsCount > 1) {
			names[2] = this.wingman2Name;
		}

		return names;
	}

	public final String getMessage() {
		return this.message;
	}

	public final void setMessage(final String text) {
		this.message = text;
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

	public final void setSellItemData(final int itemIdx, final int ammount, final int price) {
		this.itemId = itemIdx;
		this.itemQuantity = ammount;
		this.sellingPrice = price;
	}

	public final boolean hasAcceptedOffer() {
		return this.accepted;
	}

	public final void setOfferAccepted(final boolean accepted) {
		this.accepted = accepted;
	}
	/**unused*/
	public final boolean hasReward() {
		return this.reward;
	}
	/**unused*/
	public final void giveRewardAtNextChat(final boolean var1) {
		this.reward = var1;
	}

	public final String getStationName() {
		return this.stationName;
	}

	public final void setStationName(final String name) {
		this.stationName = name.equals("") ? null : name;
	}

	public final void setSystemName(final String name) {
		this.systemName = name.equals("") ? null : name;
	}
}
