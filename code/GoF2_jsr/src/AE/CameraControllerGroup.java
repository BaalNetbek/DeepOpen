package AE;

public final class CameraControllerGroup extends Group {
	private CameraController_[] controllers = null;

	public final void addController(final CameraController_ var1) {
		if (this.controllers == null) {
			this.controllers = new CameraController_[1];
			this.controllers[0] = var1;
		} else {
			final CameraController_[] var2 = new CameraController_[this.controllers.length + 1];
			System.arraycopy(this.controllers, 0, var2, 0, this.controllers.length);
			var2[this.controllers.length] = var1;
			this.controllers = var2;
		}
	}

	public final void updateTransform(final boolean var1) {
		GraphNode var2;
		if (this.controllers != null) {
			if (this.boundsDirty_ || var1) {
				if (this.transformDirty_ || var1) {
					if (this.group != null) {
						this.localTransformation = this.group.localTransformation.multiplyTwo(this.compositeTransformation, this.localTransformation);
					} else {
						this.localTransformation.set(this.compositeTransformation);
					}
				}

				var2 = this.head;

				while(true) {
					if (var2 == null) {
						this.boundingSphere.setXYZR(this.localTransformation.getPositionX(), this.localTransformation.getPositionY(), this.localTransformation.getPositionZ(), 0);

						for(var2 = this.head; var2 != null; var2 = var2.parent) {
							this.boundingSphere.merge(var2.boundingSphere);
						}

						this.boundsDirty_ = false;
						this.transformDirty_ = false;
						break;
					}

					var2.updateTransform(this.transformDirty_ || var1);
					var2 = var2.parent;
				}
			}

			for(int var3 = this.controllers.length - 1; var3 >= 0; --var3) {
				if (this.controllers[var3].isInLookAtMode()) {
					this.controllers[var3].update();
				}
			}
		}

		if (this.boundsDirty_ || var1) {
			if (this.transformDirty_ || var1) {
				if (this.group != null) {
					this.localTransformation = this.group.localTransformation.multiplyTwo(this.compositeTransformation, this.localTransformation);
				} else {
					this.localTransformation.set(this.compositeTransformation);
				}
			}

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
}
