package GOF2;

public final class SolarSystem {
    private final String name;
    private final int id;
    private final int safety;
    private final int race;
    private final int posX;
    private final int posY;
    private final int posZ;
    private final int jumpgateStationId;
    private final int starTextureId;
    private final int[] stationIDs;
    private final int[] neighbourSystems;
    private final boolean visibleByDeafult;
    public int starR;
    public int starG;
    public int starB;

    public SolarSystem(final int id, final String name, final int safety, final boolean startsVisible, final int race, final int x, final int y, final int z, final int jumpgateStation, final int starTexture, final int[] starRGB, final int[] stations, final int[] neigbours, final int[] var14) {
        this.id = id;
        this.name = name;
        this.safety = safety;
        this.visibleByDeafult = startsVisible;
        this.race = race;
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        this.jumpgateStationId = jumpgateStation;
        this.starTextureId = starTexture;
        this.starR = starRGB[0];
        this.starG = starRGB[1];
        this.starB = starRGB[2];
        this.stationIDs = stations;
        this.neighbourSystems = neigbours;
    }

    public final int getIndex() {
        return this.id;
    }

    public final String getName() {
        return this.name;
    }

    public final int getSafety() {
        return this.safety;
    }

    public final int getRace() {
        return this.race;
    }

    public final int getPosX() {
        return this.posX;
    }

    public final int getPosY() {
        return this.posY;
    }

    public final int getPosZ() {
        return this.posZ;
    }

    public final int getStarTextureIndex() {
        return this.starTextureId;
    }

    public final int getJumpgateStationIndex() {
        return this.jumpgateStationId;
    }

    public final int getJumpGateEnumIndex() {
        return getStationEnumIndex(this.jumpgateStationId);
    }

    public final int getStationEnumIndex(final int var1) {
        for(int i = 0; i < this.stationIDs.length; ++i) {
            if (this.stationIDs[i] == var1) {
                return i;
            }
        }

        return -1;
    }

    public final boolean currentOrbitHasJumpgate() {
        return this.jumpgateStationId == Status.getStation().getIndex();
    }

    public final boolean stationIsInSystem(final int var1) {
        for(int i = 0; i < this.stationIDs.length; ++i) {
            if (this.stationIDs[i] == var1) {
                return true;
            }
        }

        return false;
    }

    public final boolean isFullyDiscovered() {
        for(int i = 0; i < this.stationIDs.length; ++i) {
            if (!Galaxy.getVisitedStations()[this.stationIDs[i]]) {
                return false;
            }
        }

        return true;
    }

    public final int[] getStations() {
        return this.stationIDs;
    }

    public final int[] getNeighbourSystems() {
        return this.neighbourSystems;
    }

    public final boolean inJumpageRange(final int var1) {
        if (var1 == this.id) {
            return true;
        }
        if (this.neighbourSystems != null) {
            for(int i = 0; i < this.neighbourSystems.length; ++i) {
                if (this.neighbourSystems[i] == var1) {
                    return true;
                }
            }
        }

        return false;
    }

    public final boolean isVisibleByDeafult() {
        return this.visibleByDeafult;
    }
}
