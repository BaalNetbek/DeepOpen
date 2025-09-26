package GOF2;

public final class Mission {
	
	public static final int STORY_0 = 	0;
	public static final int STORY_1 = 	1;
	public static final int STORY_2 = 	2;
	public static final int STORY_3 = 	3;
	public static final int STORY_4 = 	4;
	public static final int STORY_5 = 	5;
	public static final int STORY_6 = 	6;
	public static final int STORY_7 = 	7;
	public static final int STORY_8 = 	8;
	public static final int STORY_9 = 	9;
	public static final int STORY_10 = 10;
	public static final int STORY_11 = 11;
	public static final int STORY_12 = 12;
	public static final int STORY_13 = 13;
	public static final int STORY_14 = 14;
	public static final int STORY_15 = 15;
	public static final int STORY_16 = 16;
	public static final int STORY_17 = 17;
	public static final int STORY_18 = 18;
	public static final int STORY_19 = 19;
	public static final int STORY_20 = 20;
	public static final int STORY_21 = 21;
	public static final int STORY_22 = 22;
	public static final int STORY_23 = 23;
	public static final int STORY_24 = 24;
	public static final int STORY_25 = 25;
	public static final int STORY_26 = 26;
	public static final int STORY_27 = 27;
	public static final int STORY_28 = 28;
	public static final int STORY_29 = 29;
	public static final int STORY_30 = 30;
	public static final int STORY_31 = 31;
	public static final int STORY_32 = 32;
	public static final int STORY_33 = 33;
	public static final int STORY_34 = 34;
	public static final int STORY_35 = 35;
	public static final int STORY_36 = 36;
	public static final int STORY_37 = 37;
	public static final int STORY_38 = 38;
	public static final int STORY_39 = 39;
	public static final int STORY_40 = 40;
	public static final int STORY_41 = 41;
	public static final int STORY_42 = 42;
	public static final int STORY_43 = 43;
	public static final int STORY_44 = 44;
	public static final int STORY_45 = 45;
	
	public static final int TYPE_NONE = -1;
	/**	Courier */
	public static final int TYPE_0 = 0;
	public static final int TYPE_1 = 1;
	public static final int TYPE_2 = 2;
	public static final int TYPE_3 = 3;
	public static final int TYPE_4 = 4;
	public static final int TYPE_5 = 5;
	public static final int TYPE_6 = 6;
	public static final int TYPE_7 = 7;
    /** Purchase */
	public static final int TYPE_8 = 8;
	public static final int TYPE_9 = 9;
	public static final int TYPE_10 = 10;
    /** Passenger/Reach destination */
	public static final int TYPE_11 = 11;
    /** Challenge */
	public static final int TYPE_12 = 12;
	public static final int TYPE_13 = 13;
	public static final int TYPE_14 = 14;
	public static final int TYPE_15 = 15;
	public static final int TYPE_16 = 16;
	public static final int TYPE_17 = 17;
	public static final int TYPE_18 = 18;
	public static final int TYPE_19 = 19;
	public static final int TYPE_20 = 20;
	public static final int TYPE_21 = 21;
	public static final int TYPE_22 = 22;
	public static final int TYPE_23 = 23;
	public static final int TYPE_24 = 24;
	public static final int TYPE_25 = 25;
	
	public static final Mission emptyMission_ = new Mission();
	private Agent client;
	private int missionType;
	private String clientName = "";
	private String targetName = "";
	private byte[] clientFace;
	private int clientRace;
	private int reward;
	private int bonus_;
	private int targetStationId;
	private String targetStationName = "";
	private int difficulty;
	private boolean startedStoryMission;
	private boolean failed = false;
	private boolean won = false;
	private int commodityId;
	/** turns into defending fighters count in Protection mission **/
	private int commodityAmmout;
	private int tasksTreshold_;
	private boolean visibleOnMap;

	public Mission(final int type, final String clientName, final byte[] clientFace, final int clientRace, final int reward, final int targetStation, final int difficulty) {
		this.missionType = type;
		this.clientName = clientName;
		this.clientFace = clientFace;
		this.clientRace = clientRace;
		this.reward = reward;
		this.targetStationId = targetStation;
		this.targetStationName = Galaxy.getStation(targetStation).getName();
		this.difficulty = difficulty;
		this.startedStoryMission = false;
		this.visibleOnMap = true;
	}

	public Mission(final int var1, final int var2, final int var3) {
		this.missionType = var1;
		this.reward = var2;
		this.targetStationId = var3;
		this.targetStationName = Galaxy.getStation(var3).getName();
		this.startedStoryMission = true;
		this.visibleOnMap = true;
	}

	private Mission() {
		this.missionType = -1;
		this.startedStoryMission = false;
		this.visibleOnMap = false;
	}

	public final boolean isEmpty() {
		return this.missionType == -1;
	}

	public final void setVisible(final boolean var1) {
		this.visibleOnMap = var1;
	}

	public final boolean isVisible() {
		return this.visibleOnMap;
	}

	public final void setFailed(final boolean var1) {
		this.failed = true;
	}

	public final boolean hasFailed() {
		return this.failed;
	}

	public final void setWon(final boolean var1) {
		this.won = var1;
	}

	public final boolean hasWon() {
		return this.won;
	}
	/* setProductionGood */
	public final void setCommodity(final int itemId, final int ammount) {
		this.commodityId = itemId;
		this.commodityAmmout = ammount;
	}
	/* getProductionGoodIndex */
	public final int getCommodityIndex() {
		return this.commodityId;
	}
	/* getProductionGoodAmount */
	public final int getCommodityAmount_() {
		return this.commodityAmmout;
	}

	public final void setStatusValue(final int var1) {
		this.tasksTreshold_ = var1;
	}

	public final int getStatusValue_() {
		return this.tasksTreshold_;
	}

	public final int getType() {
		return this.missionType;
	}

	public final boolean isOutsideMission() {
		return this.missionType == 10 || this.missionType == 9 || this.missionType == 0 || this.missionType == 1
		      || this.missionType == 7 || this.missionType == 11 || this.missionType == 4 || this.missionType == 2
		      || this.missionType == 3 || this.missionType == 5 || this.missionType == 6;
	}

	public final String getClientName() {
		return this.clientName;
	}

	public final byte[] getClientImage() {
		return this.clientFace;
	}

	public final int getClientRace() {
		return this.clientRace;
	}

	public final int getReward() {
		return this.reward;
	}

	public final int getBonus() {
		return this.bonus_;
	}

	public final void setBonus(final int var1) {
		this.bonus_ = var1;
	}

	public final boolean isInstantActionMission() {
		return false;
	}

	public final boolean isCampaignMission() {
		return this.startedStoryMission;
	}

	public final void setCampaignMission(final boolean var1) {
		this.startedStoryMission = var1;
	}

	public final int getTargetStation() {
		return this.targetStationId;
	}

	public final String getTargetStationName() {
		return this.targetStationName;
	}

	public final void setTargetStation(final int var1) {
		this.targetStationId = var1;
		this.targetStationName = Galaxy.getStation(this.targetStationId).getName();
	}

	public final int getDifficulty() {
		return this.difficulty;
	}

	public final Agent getAgent() {
		return this.client;
	}

	public final void setAgent(final Agent var1) {
		this.client = var1;
	}

	public final String getTargetName() {
		return this.targetName;
	}

	public final void setTargetName(final String var1) {
		this.targetName = var1;
	}

	public final void setType(final int var1) {
		this.missionType = var1;
	}
}
