package GOF2;

public final class RadioMessage {

    

    /** waypoint x reached */
    public static final int TRIG_0 = 0;
    /** enemies x,y,z,... are active */
	public static final int TRIG_1 = 1;
     /** enemies x,y,z,... are dead and friendly */
	public static final int TRIG_2 = 2;
    /** No enemies left */
	public static final int TRIG_3 = 3;
    /** No friends left */
	public static final int TRIG_4 = 4;
    /** x ms passed */
	public static final int TRIG_5 = 5;
    /** RadioMessage x triggered*/
	public static final int TRIG_6 = 6;
	public static final int TRIG_7 = 7;
    /** enemy x is active */
	public static final int TRIG_8 = 8;
	public static final int TRIG_9 = 9;
    /** enemies x,y,z,... are active and friendly */
	public static final int TRIG_10 = 10;
	/** Objective x achieved */
	public static final int TRIG_11 = 11;
    /** enemy x lost half hp */
	public static final int TRIG_12 = 12;
	public static final int TRIG_13 = 13;
    /** enemy x's mission crate captured */
	public static final int TRIG_14 = 14;
	public static final int TRIG_15 = 15;
    /** not friend is active */
	public static final int TRIG_16 = 16;
    /** every ship except x is dead */
	public static final int TRIG_17 = 17;
	public static final int TRIG_18 = 18;
    /** any of x,y,z lost 1/4 of hp */
	public static final int TRIG_19 = 19;
    /** x enemies is dead */
	public static final int TRIG_20 = 20;
    /** enemy x stunned */
	public static final int TRIG_21 = 21;
    /** captured x cargo */
	public static final int TRIG_22 = 22;
    /** scoped on a station */
	public static final int TRIG_23 = 23;
    /** enemy x not active, not dead */
	public static final int TRIG_24 = 24;

    private Radio radio;
    private final int textId;
    private final int imageId;
    private final int trigType;
    private final int trigCondition;
    private final int[] conditionsGroup;
    private boolean triggered;
    private boolean finished;
    private int lastWaypoint_;
    /**
     *
     * @param text text index
     * @param face face index
     * @param trigger trigger type
     * @param trig_param trigger parameter
     */
    public RadioMessage(final int text, final int face, final int trigger, final int trigParam) {
        this.textId = text;
        this.imageId = face;
        this.trigType = trigger;
        this.trigCondition = trigParam;
        this.conditionsGroup = new int[]{trigParam};
        this.triggered = false;
        this.finished = false;
    }

    public RadioMessage(final int var1, final int var2, final int var3, final int[] var4) {
        this.textId = var1;
        this.imageId = var2;
        this.trigType = var3;
        this.trigCondition = var4[0];
        this.conditionsGroup = var4;
        this.triggered = false;
    }

    public final int getTextID() {
        return this.textId;
    }

    public final int getImageID() {
        return this.imageId;
    }

    public final void setRadio(final Radio var1) {
        this.radio = var1;
    }

    public final boolean isTriggered() {
        return this.triggered;
    }

    public final boolean isOver() {
        return this.finished;
    }

    public final void finish() {
        this.finished = true;
    }
    
    /** TODO uinline */
    public final boolean triggered(final long var1, final PlayerEgo playerEgo) {
        if (this.triggered) {
            return false;
        }
        boolean triggered = false;
        final Player[] coPlayers = playerEgo.player.getEnemies();
        int i;
        switch(this.trigType) {
        case TRIG_0:
            if (playerEgo.getRoute() != null) {
                triggered = playerEgo.getRoute().getCurrent() > this.lastWaypoint_ && this.lastWaypoint_ == this.trigCondition;
                this.lastWaypoint_ = playerEgo.getRoute().getCurrent();
            }
            break;
        case TRIG_1:
            for (i = 0; i < this.conditionsGroup.length; i++) {
                if (coPlayers[this.conditionsGroup[i]].isDead()) {
                    triggered = true;
                    break;
                }
            }
            break;
        case TRIG_2:
            for (i = 0; i < this.conditionsGroup.length; i++) {
                if (coPlayers[this.conditionsGroup[i]].friend && coPlayers[this.conditionsGroup[i]].isDead()) {
                    triggered = true;
                    break;
                }
            }
            break;
        case TRIG_3:
            triggered = playerEgo.level.getEnemiesLeft() <= 0;
            break;
        case TRIG_4:
            triggered = playerEgo.level.getFriendsLeft() <= 0;
            break;
        case TRIG_5:
            triggered = var1 >= this.trigCondition;
            break;
        case TRIG_6:
            triggered = this.radio.getMessageFromQueue(this.trigCondition).triggered;
            break;
        case TRIG_7:
        case TRIG_13:
        default:
            break;
        case TRIG_8:
            for (i = 0; i < this.conditionsGroup.length; i++) {
                // #BUG? does it make any sense
                if (!coPlayers[i].isAsteroid() && coPlayers[this.conditionsGroup[i]].isActive()) { 
                    triggered = true;
                    break;
                }
            }
            break;
        case TRIG_9:
            triggered = true;
            for (i = 0; i<this.conditionsGroup.length; i++) {


                if (!coPlayers[this.conditionsGroup[i]].isDead()) {
                    triggered = false;
                    break;
                }
            }
            break;
        case TRIG_10:
            for (i = 0; i < this.conditionsGroup.length; i++) {
                if (coPlayers[this.conditionsGroup[i]].friend && coPlayers[this.conditionsGroup[i]].isActive()) {
                    triggered = true;
                    break;
                }
            }
            break;
        case TRIG_11:
            triggered = ((Objective)null).achieved((int)var1);
            break;
        case TRIG_12:
            for (i = 0; i<this.conditionsGroup.length; i++) {
                if (coPlayers[this.conditionsGroup[i]].getHitpoints() < coPlayers[this.conditionsGroup[i]].getMaxHitpoints() / 2) {
                    triggered = true;
                    break;
                }
            }
            break;
        case TRIG_14:
            PlayerFighter kiPlayer = (PlayerFighter)playerEgo.player.getEnemies()[this.trigCondition].getKIPlayer();
            triggered = kiPlayer.lostMissionCrateToEgo();
            break;
        case TRIG_15:
            triggered = false;
            for (i = 0; i < coPlayers.length; i++) {
                if (coPlayers[i].isDead() && !coPlayers[i].isAsteroid()) {
                    triggered = true;
                    break;
                }

            }
            break;
        case TRIG_16:
            for(i = 0; i < coPlayers.length; i++) {
                if (!coPlayers[i].isAsteroid() && coPlayers[i].isActive() && !coPlayers[i].isAlwaysFriend()) {
                    triggered = true;
                    break;
                }
            }
            break;
        case TRIG_17:
            triggered = true;

            for (i = 0; i < coPlayers.length; i++) {
                if (i != this.trigCondition && !coPlayers[i].isAsteroid() && !coPlayers[i].isDead()) {
                    triggered = false;
                   break;
                }
            }
            break;
        case TRIG_18:
            triggered = ((PlayerFighter)playerEgo.player.getEnemies()[this.trigCondition].getKIPlayer()).lostCargo() 
                        || ((PlayerFighter)playerEgo.player.getEnemies()[this.trigCondition].getKIPlayer()).unk151_();
            break;
        case TRIG_19:
            for (i = 0; i <this.conditionsGroup.length; i++) {
                if (coPlayers[this.conditionsGroup[i]].getHitpoints() < (coPlayers[this.conditionsGroup[i]].getMaxHitpoints() * 0.75)) {
                    triggered = true;
                    break;
                }
            }
            break;
        case TRIG_20:
            triggered = false;            
            for (i = 0; i<coPlayers.length; i++) {
                if (coPlayers[i].isDead()) {
                    ++i;
                }

                if (i >= this.trigCondition) {
                    triggered = true;
                    break;
                }
            }
            break;
        case TRIG_21:
            if (!playerEgo.player.getEnemies()[this.trigCondition].getKIPlayer().stunned) {
                break;
            }

            triggered = true;
            break;
        case TRIG_22:
            triggered = playerEgo.level.capturedCargoCount >= this.trigCondition;
            break;
        case TRIG_23:
            triggered = playerEgo.radar.targetedLandmark != null 
                        && playerEgo.radar.targetedLandmark instanceof PlayerStation;
            break;
        case TRIG_24:
            triggered = !playerEgo.player.getEnemies()[this.trigCondition].isActive() 
                        && !playerEgo.player.getEnemies()[this.trigCondition].isDead();
            break;
        }


        this.triggered = triggered;
        if (this.triggered) {
            this.radio.setCurrentMessage(this);
        }

        return triggered;
    }
}
