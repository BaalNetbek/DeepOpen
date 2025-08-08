package GOF2;

public final class RadioMessage {
    private Radio radio;
    private final int textId;
    private final int imageId;
    private final int triggerCondition;
    private final int triggerType;
    private final int[] conditionsGroup;
    private boolean triggered;
    private boolean finished;
    private int lastWaypoint_;
    /**
     *
     * @param var1 text index
     * @param var2 face index
     * @param var3 trigger condition
     * @param var4 trigger type
     */
    public RadioMessage(final int var1, final int var2, final int var3, final int var4) {
        this.textId = var1;
        this.imageId = var2;
        this.triggerCondition = var3;
        this.triggerType = var4;
        this.conditionsGroup = new int[]{var4};
        this.triggered = false;
        this.finished = false;
    }

    public RadioMessage(final int var1, final int var2, final int var3, final int[] var4) {
        this.textId = 854;
        this.imageId = 0;
        this.triggerCondition = 9;
        this.triggerType = var4[0];
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

    public final boolean triggered(final long var1, final PlayerEgo playerEgo) {
        if (this.triggered) {
            return false;
        }
        boolean triggered = false;
        final Player[] coPlayers = playerEgo.player.getEnemies();
        int i;
        switch(this.triggerCondition) {
        case 0:
            if (playerEgo.getRoute() != null) {
                triggered = playerEgo.getRoute().getCurrent() > this.lastWaypoint_ && this.lastWaypoint_ == this.triggerType;
                this.lastWaypoint_ = playerEgo.getRoute().getCurrent();
            }
            break;
        case 1:
            for (i = 0; i < this.conditionsGroup.length; i++) {
                if (coPlayers[this.conditionsGroup[i]].isDead()) {
                    triggered = true;
                    break;
                }
            }
            break;
        case 2:
            for (i = 0; i < this.conditionsGroup.length; i++) {
                if (coPlayers[this.conditionsGroup[i]].friend && coPlayers[this.conditionsGroup[i]].isDead()) {
                    triggered = true;
                    break;
                }
            }
            break;
        case 3:
            triggered = playerEgo.level.getEnemiesLeft() <= 0;
            break;
        case 4:
            triggered = playerEgo.level.getFriendsLeft() <= 0;
            break;
        case 5:
            triggered = var1 >= this.triggerType;
            break;
        case 6:
            triggered = this.radio.getMessageFromQueue(this.triggerType).triggered;
            break;
        case 7:
        case 13:
        default:
            break;
        case 8:
            for (i = 0; i < this.conditionsGroup.length; i++) {
                if (!coPlayers[i].isAsteroid() && coPlayers[this.conditionsGroup[i]].isActive()) {
                    triggered = true;
                    break;
                }
            }
            break;
        case 9:
            triggered = true;
            for (i = 0; i<this.conditionsGroup.length; i++) {


                if (!coPlayers[this.conditionsGroup[i]].isDead()) {
                    triggered = false;
                    break;
                }
            }
            break;
        case 10:
            for (i = 0; i < this.conditionsGroup.length; i++) {
                if (coPlayers[this.conditionsGroup[i]].friend && coPlayers[this.conditionsGroup[i]].isActive()) {
                    triggered = true;
                    break;
                }
            }
            break;
        case 11:
            triggered = ((Objective)null).achieved((int)var1);
            break;
        case 12:
            for (i = 0; i<this.conditionsGroup.length; i++) {
                if (coPlayers[this.conditionsGroup[i]].getHitpoints() < coPlayers[this.conditionsGroup[i]].getMaxHitpoints() / 2) {
                    triggered = true;
                    break;
                }
            }
            break;
        case 14:
            triggered = ((PlayerFighter)playerEgo.player.getEnemies()[this.triggerType].getKIPlayer()).lostMissionCrateToEgo();
            break;
        case 15:
            triggered = false;
            for (i = 0; i < coPlayers.length; i++) {
                if (coPlayers[i].isDead() && !coPlayers[i].isAsteroid()) {
                    triggered = true;
                    break;
                }

            }
            break;
        case 16:
            for(i = 0; i < coPlayers.length; i++) {
                if (!coPlayers[i].isAsteroid() && coPlayers[i].isActive() && !coPlayers[i].isAlwaysFriend()) {
                    triggered = true;
                    break;
                }
            }
            break;
        case 17:
            triggered = true;

            for (i = 0; i < coPlayers.length; i++) {
                if (i != this.triggerType && !coPlayers[i].isAsteroid() && !coPlayers[i].isDead()) {
                    triggered = false;
                   break;
                }
            }
            break;
        case 18:
            triggered = ((PlayerFighter)playerEgo.player.getEnemies()[this.triggerType].getKIPlayer()).lostCargo() 
                        || ((PlayerFighter)playerEgo.player.getEnemies()[this.triggerType].getKIPlayer()).unk151_();
            break;
        case 19:
            for (i = 0; i <this.conditionsGroup.length; i++) {
                if (coPlayers[this.conditionsGroup[i]].getHitpoints() < (coPlayers[this.conditionsGroup[i]].getMaxHitpoints() * 0.75)) {
                    triggered = true;
                    break;
                }
            }
            break;
        case 20:
            triggered = false;            
            for (i = 0; i<coPlayers.length; i++) {
                if (coPlayers[i].isDead()) {
                    ++i;
                }

                if (i >= this.triggerType) {
                    triggered = true;
                    break;
                }
            }
            break;
        case 21:
            if (!playerEgo.player.getEnemies()[this.triggerType].getKIPlayer().stunned) {
                break;
            }

            triggered = true;
            break;
        case 22:
            triggered = playerEgo.level.capturedCargoCount >= this.triggerType;
            break;
        case 23:
            triggered = playerEgo.radar.targetedLandmark != null 
                        && playerEgo.radar.targetedLandmark instanceof PlayerStation;
            break;
        case 24:
            triggered = !playerEgo.player.getEnemies()[this.triggerType].isActive() 
                        && !playerEgo.player.getEnemies()[this.triggerType].isDead();
            break;
        }


        this.triggered = triggered;
        if (this.triggered) {
            this.radio.setCurrentMessage(this);
        }

        return triggered;
    }
}
