package AE;

public class Group extends GraphNode {
	protected GraphNode head = null;

	public final void uniqueAppend_(final GraphNode newNode) {
		if (newNode != null && newNode != this) {
			GraphNode var2 = this.head;

			boolean var10000;
			while(true) {
				if (var2 == null) {
					var10000 = true;
					break;
				}

				if (var2 == newNode) {
					var10000 = false;
					break;
				}

				var2 = var2.parent;
			}

			if (var10000) {
				newNode.group = this;
				newNode.parent = this.head;
				this.head = newNode;
			}
		}

	}

	public final void removeNode(final GraphNode var1) {
		if (var1 != null && this.head != null) {
			if (var1 == this.head) {
				this.head = this.head.parent;
				var1.group = null;
				var1.parent = null;
			} else {
				for(GraphNode var2 = this.head; var2 != null; var2 = var2.parent) {
					if (var2.parent == var1) {
						var2.parent = var2.parent.parent;
						var1.group = null;
						var1.parent = null;
						return;
					}
				}

			}
		}
	}

	public final GraphNode getEndNode() {
		return this.head;
	}

	public final void appendToRender(final AECamera var1, final Renderer var2) {
		if (this.draw) {
			GraphNode var3;
			switch(var1.isInViewFrustum(this.boundingSphere)) {
			case 1:
				for(var3 = this.head; var3 != null; var3 = var3.parent) {
					var3.appendToRender(var1, var2);
				}

				return;
			case 2:
				for(var3 = this.head; var3 != null; var3 = var3.parent) {
					var3.forceAppendToRender(var1, var2);
				}
			}
		}

	}

	public final void forceAppendToRender(final AECamera var1, final Renderer var2) {
		if (this.draw) {
			for(GraphNode var3 = this.head; var3 != null; var3 = var3.parent) {
				var3.forceAppendToRender(var1, var2);
			}
		}

	}

	public void updateTransform(final boolean var1) {
		if (this.boundsDirty_ || var1) {
			if (this.transformDirty_ || var1) {
				if (this.group != null) {
					this.localTransformation = this.group.localTransformation.multiplyTo(this.compositeTransformation, this.localTransformation);
				} else {
					this.localTransformation.set(this.compositeTransformation);
				}
			}

			GraphNode var2;
			for(var2 = this.head; var2 != null; var2 = var2.parent) {
				var2.updateTransform(this.transformDirty_ || var1);
			}

			this.boundingSphere.setXYZR(this.localTransformation.getPositionX(), this.localTransformation.getPositionY(), this.localTransformation.getPositionZ(), 0);

			for(var2 = this.head; var2 != null; var2 = var2.parent) {
				this.boundingSphere.merge(var2.boundingSphere);
			}

			this.boundsDirty_ = false;
			this.transformDirty_ = false;
		}

	}

	protected final String getString(String var1, int var2) {
		for(int var3 = 0; var3 < var2; ++var3) {
			var1 = var1 + "  ";
		}

		var1 = var1 + " |\t" + this.boundingSphere.center.x + "\t\t" + this.boundingSphere.center.y + "\t\t" + this.boundingSphere.center.z + "\t\t" + this.boundingSphere.radius + "\n";
		++var2;

		for(GraphNode var4 = this.head; var4 != null; var4 = var4.parent) {
			var1 = var4.getString(var1, var2);
		}

		return var1;
	}

	public final void setAnimationRangeInTime(final int var1, final int var2) {
		for(GraphNode var3 = this.head; var3 != null; var3 = var3.parent) {
			var3.setAnimationRangeInTime(var1, var2);
		}

	}

	public final void setAnimationMode(final byte var1) {
		for(GraphNode var2 = this.head; var2 != null; var2 = var2.parent) {
			var2.setAnimationMode(var1);
		}

	}

	public final void disableAnimation() {
		for(GraphNode var1 = this.head; var1 != null; var1 = var1.parent) {
			var1.disableAnimation();
		}

	}

	public final boolean hasAnimation() {
		boolean var1 = false;

		for(GraphNode var2 = this.head; var2 != null; var2 = var2.parent) {
			var1 |= var2.hasAnimation();
		}

		return var1;
	}
}
