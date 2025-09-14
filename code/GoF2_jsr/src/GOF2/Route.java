package GOF2;

import AE.Math.AEVector3D;

public final class Route {
    private Waypoint[] waypoints;
    private boolean loop = false;
    private int current = 0;

    public Route(final int[] points) {
        this.waypoints = new Waypoint[points.length / 3];

        for(int i = 0; i < points.length; i += 3) {
            this.waypoints[i / 3] = new Waypoint(points[i], points[i + 1], points[i + 2], this);
        }

    }

    public final void onRelease() {
        this.waypoints = null;
    }

    public final void setNewCoords(final AEVector3D firstPoint) {
        this.waypoints[0].x = firstPoint.x;
        this.waypoints[0].y = firstPoint.y;
        this.waypoints[0].z = firstPoint.z;
    }

    public final void reset() {
        for(int i = 0; i < this.waypoints.length; ++i) {
            this.waypoints[i].reset();
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

        for(int i = 0; i < var1.waypoints.length; ++i) {
            if (this.waypoints[i].reached_) {
                var1.waypoints[i].reached_ = true;
            }
        }

        var1.current = this.current;
        return var1;
    }

    public final Route clone() {
        final int[] points = new int[this.waypoints.length * 3];

        for(int i = 0; i < this.waypoints.length; ++i) {
            points[i * 3] = this.waypoints[i].x;
            points[i * 3 + 1] = this.waypoints[i].y;
            points[i * 3 + 2] = this.waypoints[i].z;
        }

        Route var4 = new Route(points);
        var4.loop = this.loop;
        return var4;
    }
}
