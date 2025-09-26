package GOF2;

import AE.AEResourceManager;
import AE.AEGeometry;
import AE.BoundingAAB;
import AE.BoundingVolume;
import AE.GlobalStatus;
import AE.Math.AEMath;
import AE.Math.AEVector3D;
import AE.PaintCanvas.AEGraphics3D;
import AE.Math.AEMatrix;

public final class PlayerStation extends PlayerStaticFar {
	private final int[] partsCollisions = {
   //offset==>x,      y,      z,   |  x,     y,     z,  <==dimension   
		    -10,    -791,   -1776,  2509,  3625,  22248, // #BUG, #TODO offset.z -> 1776 (flip sign)
		    -18,    -786,   2466,   2499,  4681,  30734,
		    -10,    -1851,  29,     7568,  13971, 7568,
		    21,     -2629,  -6,     8627,  15527, 8627,
		    21,     -9152,  -7,     20335, 28573, 20340,
		    -192,   -1795,  0,      11008, 13859, 13952,
		    0,      -2268,  -7,     19164, 14804, 19177,
		    -1250,  -4456,  1261,   15033, 19181, 15027, // #BUG, #TODO offset.z -> -1261, x -> 1250 (flip sign)
		    -11,    3719,   -29,    2512,  12400, 25744,
		    18,     -729,   0,      2499,  3502,  16384,
		    18,     -729,   0,      2499,  3502,  8192,
		    10,     13,     -4,     11307, 10240, 11305,
		    0,      -7924,  0,      19284, 26417, 19284,
		    10,     571,    -4,     26296, 10240, 26296,
		    0,      -1179,  0,      12650, 11632, 12650,
		    -20,    -648,   3,      21118, 12522, 11317,
		    -1004,  -659,   3,      15913, 11559, 11389,
		    -2,     -940,   7,      11358, 7008,  7610,
		    11,     -1241,  -970,   11392, 8260,  9453,
		    -839,   -3912,  2790,   13113, 12952, 13093,
		    21,     1102,   28,     12370, 7866,  12443,
		    -356,   3009,   455,    15605, 16258, 15792,
		    -10,    135,    -2,     10652, 10483, 10652,
		    -10,    3533,   -2,     11360, 17279, 11360,
		    3,      1034,   -8,     16067, 12281, 16067,
		    -30,    -1170,  1263,   17529, 15612, 20229,
		    37,     -1069,  0,      33845, 11291, 33770,
		    -10,    1016,   -2,     7641,  12244, 7631,
		    1,      2565,   28,     11733, 15342, 7568,
		    -10,    6381,   22,     7568,  22975, 7582,
		    -1783,  7259,   22,     11115, 24731, 7582,
		    1117,   6019,   22,     11364, 22251, 7582,
		    18,     -459,   16,     2499,  4043,  16350,
		    0,      0,      0,      1940,  1940,  1940,
		    0,      0,      0,      1940,  1940,  1940,
		    0,      -5143,  0,      55879, 69796, 55879,
		    0,      -4444,  0,      22222, 55555, 22222, //void station (needs fix)
		    -9617,  8646,   -9617,  11781, 10784, 11781, // #BUG
		    0,      -15497, 0,      4866,  13737, 5263,				
		    0,      -4021,  0,      11493, 9778,  11493,
		    0,      12391,  0,      16896, 10793, 16896,
		    0,      3931,   0,      11240, 6126,  11240,
		    -10361, 25852,  -11525, 25379, 16129, 23051, // offset for vossk station parts is very off. #TODO set it to 0.
		    0,      0,      0,      0,     0,     0
	      };
	private AEGeometry[] stationParts;
	private int[] partPositions;
    private int collidingPart;
    private int maxPartDeflection;

    public PlayerStation(final Station var1) {
        super(-1, (AEGeometry)null, 0, 0, 0);
        this.player.setRadius(15000);
        //new FileRead();
        int[] stationParts = null;
        if (!Status.inAlienOrbit()) {
            stationParts = FileRead.loadStationParts(var1.getIndex(), Status.getSystem().getRace());
        }

        //int var3;
        if (stationParts == null) {
            this.stationParts = new AEGeometry[1];
            Status.inAlienOrbit();
            this.stationParts[0] = AEResourceManager.getGeometryResource(3337);
            this.stationParts[0].setRenderLayer(2);
        } else {
            this.stationParts = new AEGeometry[stationParts.length / 7];
            
            int j;
            for(int i = 0; i < this.stationParts.length; ++i) {
                j = i * 7;
                this.stationParts[i] = AEResourceManager.getGeometryResource(stationParts[j + FileRead.RESOURCE_ID]);
                this.stationParts[i].setRotationOrder((short)0); //ZYX
				this.stationParts[i].moveTo(
						stationParts[j + FileRead.POSITION_X],
						stationParts[j + FileRead.POSITION_Y],
						stationParts[j + FileRead.POSITION_Z]
				);
				this.stationParts[i].setRotation(
						stationParts[j + FileRead.ROTATION_X],
						stationParts[j + FileRead.ROTATION_Y],
						stationParts[j + FileRead.ROTATION_Z]
				);
                this.stationParts[i].setRenderLayer(2);
                if (this.stationParts[i].getID() == 3334 ||
            		this.stationParts[i].getID() == 3335) {
                    this.stationParts[i].setDraw(false);
                }
            }
        }

        this.maxPartDeflection = 0;
        this.boundingBoxes = new BoundingVolume[this.stationParts.length];
        final AEMatrix var8 = new AEMatrix();
        
        for(int i = 0; i < this.stationParts.length; ++i) {
            final int stationPartIdx = (this.stationParts[i].getID() - 3301) * 6;
            this.tempVector_ = this.stationParts[i].getPosition(this.tempVector_);
            if (AEMath.abs(this.tempVector_.x) > this.maxPartDeflection) {
                this.maxPartDeflection = AEMath.abs(this.tempVector_.x);
            }

            if (AEMath.abs(this.tempVector_.y) > this.maxPartDeflection) {
                this.maxPartDeflection = AEMath.abs(this.tempVector_.y);
            }

            if (AEMath.abs(this.tempVector_.z) > this.maxPartDeflection) {
                this.maxPartDeflection = AEMath.abs(this.tempVector_.z);
            }

            final int var4 = this.tempVector_.x;
            final int var5 = this.tempVector_.y;
            final int var6 = this.tempVector_.z;
			var8.setRotation(
					this.stationParts[i].getEulerX(),
					this.stationParts[i].getEulerY(),
					this.stationParts[i].getEulerZ()
			);
			virtDistToCam_.set(
					this.partsCollisions[stationPartIdx + BoundingAAB.OFFSET_X],
					this.partsCollisions[stationPartIdx + BoundingAAB.OFFSET_Y],
					this.partsCollisions[stationPartIdx + BoundingAAB.OFFSET_Z]
			);
			this.position = var8.transformVectorNoScale(virtDistToCam_, this.position);
			virtDistToCam_.set(
					this.partsCollisions[stationPartIdx + BoundingAAB.DIMENSION_X] + 5000,
					this.partsCollisions[stationPartIdx + BoundingAAB.DIMENSION_Y] + 5000,
					this.partsCollisions[stationPartIdx + BoundingAAB.DIMENSION_Z] + 5000
			);
			this.tempVector_ = var8.transformVectorNoScale(virtDistToCam_, this.tempVector_);
			this.boundingBoxes[i] = new BoundingAAB(
					var4, var5, var6,
					this.position.x, this.position.y, this.position.z,
					this.tempVector_.x, this.tempVector_.y, this.tempVector_.z
			);
			if (Status.getSystem() != null) {
				stationParts = Globals.getRaceUVkeyframeId_(Status.getSystem().getRace());
				this.stationParts[i].setAnimationRangeInTime(stationParts[0], stationParts[1]);
				this.stationParts[i].disableAnimation();
			}
        }

        this.maxPartDeflection += 5000;
        this.partPositions = new int[this.stationParts.length * 3];

        for(int i = 0; i < this.stationParts.length; ++i) {
            this.partPositions[i * 3] = this.stationParts[i].getPosX();
            this.partPositions[i * 3 + 1] = this.stationParts[i].getPosY();
            this.partPositions[i * 3 + 2] = this.stationParts[i].getPosZ();
        }

    }

    public final void update(final long var1) {
        if (this.stationParts != null) {
            for(int i = 0; i < this.stationParts.length; ++i) {
                this.tempVector_ = GlobalStatus.renderer.getCamera().getLocalPos(this.tempVector_);
                this.position.set(this.partPositions[i * 3], this.partPositions[i * 3 + 1], this.partPositions[i * 3 + 2]);
                this.position.subtract(this.tempVector_, virtDistToCam_);
                int var2 = virtDistToCam_.getLength();
                if (var2 > AEGraphics3D.CLAMP_TOP) {
                    virtDistToCam_.normalize();
                    virtDistToCam_.scale(AEGraphics3D.CLAMP_TOP);
                    virtDistToCam_.add(this.tempVector_);
                    this.stationParts[i].moveTo(virtDistToCam_);
                    var2 = (int)((float)AEGraphics3D.CLAMP_TOP / var2 * AEMath.TO_Q);
                    this.stationParts[i].setScale(var2, var2, var2);
                } else {
                    this.stationParts[i].setScale(AEMath.Q_1, AEMath.Q_1, AEMath.Q_1);
                    this.stationParts[i].moveTo(this.partPositions[i * 3], this.partPositions[i * 3 + 1], this.partPositions[i * 3 + 2]);
                }
            }

        }
    }

    public final void appendToRender() {
        if (this.stationParts != null) {
            for(int i = 0; i < this.stationParts.length; ++i) {
                GlobalStatus.renderer.drawNodeInVF(this.stationParts[i]);
            }

        }
    }

    public final AEVector3D getPosition(final AEVector3D var1) {
        var1.set(0, 0, 0);
        return var1;
    }

    public final boolean outerCollide(final int x, final int y, final int z) {
        return x < this.player.radius &&
            x > -this.player.radius &&
            y <  this.player.radius &&
            y > -this.player.radius &&
            z <  this.player.radius &&
            z > -this.player.radius;
    }

    public final boolean outerCollide(final AEVector3D collidee) {
        final int z = collidee.z;
        final int y = collidee.y;
        final int x = collidee.x;
        if (x < this.maxPartDeflection &&
            x > -this.maxPartDeflection &&
            y < this.maxPartDeflection &&
            y > -this.maxPartDeflection && 
            z < this.maxPartDeflection &&
            z > -this.maxPartDeflection && 
            this.boundingBoxes != null) 
        {
            for(int i = 0; i < this.boundingBoxes.length; ++i) {
                if (this.boundingBoxes[i].isPointInBounding(x, y, z)) {
                    this.collidingPart = i;
                    return true;
                }
            }
        }

        return false;
    }

    public final AEVector3D getProjectionVector_(final AEVector3D var1) {
        return this.boundingBoxes != null ? this.boundingBoxes[this.collidingPart].getProjectionVector(var1) : null;
    }

    public final void OnRelease() {
        if (this.stationParts != null) {
            for(int i = 0; i < this.stationParts.length; ++i) {
                if (this.stationParts[i] != null) {
                    this.stationParts[i].OnRelease();
                    this.stationParts[i] = null;
                }
            }
        }

        this.stationParts = null;
        this.partPositions = null;
    }
}
