package GOF2;

/**
 * Standing stands for 'reputation'. 
 * Manages player standing with nations that are at war.
 * There are two conflicts:
 * VOSSK_TERRAN (-100 means player is friend of Vossk, 100 friend of Terrans)
 * and 
 * MIDO_NIVELIAN (-100 friend Mido, 100 friend Nivelians)
 * 
 * @author Fishlabs 2009
 */
public final class Standing {
    public static final int VOSSK_TERRAN = 0;
    public static final int MIDO_NIVELIAN = 0;
	
	private int[] stand = new int[2];
    
    public Standing() {
        this.stand[VOSSK_TERRAN] = 30;
        this.stand[MIDO_NIVELIAN] = 0;
    }

    public final void setStanding(final int[] stands) {
        this.stand = stands;
    }

    public final int[] getStanding() {
        return this.stand;
    }
    /**
     * 
     * @param conflict VOSSK_TERRAN or MIDO_NIVELIAN
     * @return number in range < -100, 100 >
     */
    public final int getStanding(final int conflict) {
        return this.stand[conflict];
    }

    public final void rehabilitate(final int var1) {
        switch (var1) {
        case Globals.VOSSK:
            this.stand[VOSSK_TERRAN] = 35;
            break;
        case Globals.TERRAN:
            this.stand[VOSSK_TERRAN] = -35;
            break;
        case Globals.MIDORIAN:
            this.stand[MIDO_NIVELIAN] = 35;
            break;
        case Globals.NIVELIAN:
        	this.stand[MIDO_NIVELIAN] = -35;
        	break;
        default:
            break;
        }
    }

    public final boolean atWar() {
        final int vossk_terran = this.stand[VOSSK_TERRAN];
        final int mido_nivelian = this.stand[MIDO_NIVELIAN];
        return vossk_terran > 60 || vossk_terran < -60 || mido_nivelian > 60 || mido_nivelian < -60;
    }

    public final boolean isEnemy(final int race) {
        switch (race) {
    	case Globals.VOSSK:
            return this.stand[VOSSK_TERRAN] > 60;
        case Globals.TERRAN:
            return this.stand[VOSSK_TERRAN] < -60;
        case Globals.MIDORIAN:
            return this.stand[MIDO_NIVELIAN] > 60;
        case Globals.NIVELIAN:
            return this.stand[MIDO_NIVELIAN] < -60;
        default:
            return false;
        }
    }

    public final boolean isFriend(final int race) {
        switch (race) {
        case Globals.VOSSK:
            return this.stand[VOSSK_TERRAN] < -60;
        case Globals.TERRAN:
            return this.stand[VOSSK_TERRAN] > 60;
        case Globals.MIDORIAN:
            return this.stand[MIDO_NIVELIAN] < -60;
        case Globals.NIVELIAN:
            return this.stand[MIDO_NIVELIAN] > 60;
        default:
            return false;
        }
    }

    public static int enemyRace(final int race) {
        switch(race) {
        case Globals.TERRAN:
            return Globals.VOSSK;
        case Globals.VOSSK:
            return Globals.TERRAN;
        case Globals.NIVELIAN:
            return Globals.MIDORIAN;
        case Globals.MIDORIAN:
            return Globals.NIVELIAN;
        default:
            return Globals.PIRATE;
        }
    }

    private void add(final int conflict, final int change) {
        this.stand[conflict] += change;
        
        if (this.stand[conflict] > 100) {
            this.stand[conflict] = 100;
        } else if (this.stand[conflict] < -100) {
            this.stand[conflict] = -100;
        }
    }

    public final void applyKill(final int killedRace) {
        final int localRace = Status.inAlienOrbit() ? Globals.VOID : Status.getSystem().getRace();
        int unhappyRace = killedRace;
        byte change = 5;
        if (killedRace == Globals.PIRATE) {
            switch(localRace) {
            case Globals.TERRAN:
                unhappyRace = Globals.VOSSK;
                break;
            case Globals.VOSSK:
                unhappyRace = Globals.TERRAN;
                break;
            case Globals.NIVELIAN:
                unhappyRace = Globals.MIDORIAN;
                break;
            case Globals.MIDORIAN:
                unhappyRace = Globals.NIVELIAN;
            }

            change = 1;
        }

        applyDelict(unhappyRace, change);
    }

    public final void applyStealCargo(final int sufferingRace) {
        applyDelict(sufferingRace, 2);
    }

    public final void increase(final int sufferingRace) {
        applyDelict(sufferingRace, -7);
    }

    public final void applyDisable(final int sufferingRace) {
        applyDelict(sufferingRace, 2);
    }

    private void applyDelict(final int race, final int dislike) {
        switch (race) {
        case Globals.VOSSK:
            add(VOSSK_TERRAN, dislike);
            break;
        case Globals.TERRAN:
            add(VOSSK_TERRAN, -dislike);
            break;
        case Globals.MIDORIAN:
            add(MIDO_NIVELIAN, dislike);
            break;
        case Globals.NIVELIAN:
        	add(MIDO_NIVELIAN, -dislike);
        	break;
        default:
            break;
        }
    }
}
