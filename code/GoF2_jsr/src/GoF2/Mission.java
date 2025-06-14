package GoF2;

public final class Mission {
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
	private int commodityAmmout;
	private int tasksTreshold_;
	private boolean visibleOnMap;

	public Mission(final int var1, final String var2, final byte[] var3, final int var4, final int var5, final int var6, final int var7) {
		this.missionType = var1;
		this.clientName = var2;
		this.clientFace = var3;
		this.clientRace = var4;
		this.reward = var5;
		this.targetStationId = var6;
		this.targetStationName = Galaxy.getStation(var6).getName();
		this.difficulty = var7;
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

	public final void setCommodity(final int var1, final int var2) {
		this.commodityId = var1;
		this.commodityAmmout = var2;
	}

	public final int getCommodityIndex() {
		return this.commodityId;
	}

	public final int getCommodityAmount_() {
		return this.commodityAmmout;
	}

	public final void setTasksTreshold_(final int var1) {
		this.tasksTreshold_ = var1;
	}

	public final int getStatusValue_() {
		return this.tasksTreshold_;
	}

	public final int getType() {
		return this.missionType;
	}

	public final boolean isOutsideMission() {
		return this.missionType == 10 || this.missionType == 9 || this.missionType == 0 || this.missionType == 1 || this.missionType == 7 || this.missionType == 11 || this.missionType == 4 || this.missionType == 2 || this.missionType == 3 || this.missionType == 5 || this.missionType == 6;
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

	public final void setStartedStoryMission(final boolean var1) {
		this.startedStoryMission = true;
	}

	public final int setCampaignMission() {
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
		this.missionType = 11;
	}
}
