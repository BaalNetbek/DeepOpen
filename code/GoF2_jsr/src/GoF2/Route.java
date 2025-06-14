package GoF2;

import AE.Math.AEVector3D;

public final class Route {
	private Waypoint[] waypoints;
	private boolean loop = false;
	private int current = 0;

	public Route(final int[] var1) {
		this.waypoints = new Waypoint[var1.length / 3];

		for(int var2 = 0; var2 < var1.length; var2 += 3) {
			this.waypoints[var2 / 3] = new Waypoint(var1[var2], var1[var2 + 1], var1[var2 + 2], this);
		}

	}

	public final void onRelease() {
		this.waypoints = null;
	}

	public final void setNewCoords(final AEVector3D var1) {
		this.waypoints[0].x = var1.x;
		this.waypoints[0].y = var1.y;
		this.waypoints[0].z = var1.z;
	}

	public final void reset() {
		for(int var1 = 0; var1 < this.waypoints.length; ++var1) {
			this.waypoints[var1].reset();
		}

		this.current = 0;
	}

	public final void setLoop(final boolean var1) {
		this.loop = var1;
	}

	public final Waypoint getWaypoint(final int var1) {
		return var1 < this.waypoints.length ? this.waypoints[var1] : null;
	}

	public final Waypoint getDockingTarget_() {
		return this.current < this.waypoints.length ? this.waypoints[this.current] : null;
	}

	public final Waypoint getLastWaypoint() {
		return this.waypoints[this.waypoints.length - 1];
	}

	public final int getCurrent() {
		return this.current;
	}

	public final void reachWaypoint_(final int var1) {
		if (this.current < this.waypoints.length - 1) {
			this.current = 1;
		}

		this.waypoints[0].setActive(false);
		this.waypoints[0].reached_ = true;
	}

	public final int length() {
		return this.waypoints.length;
	}

	public final void update(int var1, final int var2, final int var3) {
		if (this.current < this.waypoints.length && var1 - this.waypoints[this.current].x < 2000 && var1 - this.waypoints[this.current].x > -2000 && var2 - this.waypoints[this.current].y < 2000 && var2 - this.waypoints[this.current].y > -2000 && var3 - this.waypoints[this.current].z < 2000 && var3 - this.waypoints[this.current].z > -2000) {
			this.waypoints[this.current].setActive(false);
			this.waypoints[this.current].reached_ = true;
			++this.current;
			if (this.loop && this.current > this.waypoints.length - 1) {
				this.current = 0;

				for(var1 = 0; var1 < this.waypoints.length; ++var1) {
					this.waypoints[var1].reset();
				}
			}

			if (this.current < this.waypoints.length) {
				this.waypoints[this.current].setActive(true);
			}
		}

	}

	public final boolean isLooped() {
		return this.loop;
	}

	public final Route getExactClone() {
		final Route var1 = clone();

		for(int var2 = 0; var2 < var1.waypoints.length; ++var2) {
			if (this.waypoints[var2].reached_) {
				var1.waypoints[var2].reached_ = true;
			}
		}

		var1.current = this.current;
		return var1;
	}

	public final Route clone() {
		final int[] var1 = new int[this.waypoints.length * 3];

		for(int var2 = 0; var2 < this.waypoints.length; ++var2) {
			var1[var2 * 3] = this.waypoints[var2].x;
			var1[var2 * 3 + 1] = this.waypoints[var2].y;
			var1[var2 * 3 + 2] = this.waypoints[var2].z;
		}

		Route var4;
		final Route var10000 = var4 = new Route(var1);
		final boolean var3 = this.loop;
		var10000.loop = var3;
		return var4;
	}
}
