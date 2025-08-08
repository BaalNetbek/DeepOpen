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

	public final void updateTransform(final boolean forced) {
		GraphNode node;
		if (this.controllers != null) {
			if (this.boundsDirty_ || forced) {
				if (this.transformDirty_ || forced) {
					if (this.group != null) {
						this.localTransformation = this.group.localTransformation.multiplyTo(this.compositeTransformation, this.localTransformation);
					} else {
						this.localTransformation.set(this.compositeTransformation);
					}
				}

				node = this.head;

				while(true) {
					if (node == null) {
						this.boundingSphere.setXYZR(this.localTransformation.getPositionX(), this.localTransformation.getPositionY(), this.localTransformation.getPositionZ(), 0);

						for(node = this.head; node != null; node = node.parent) {
							this.boundingSphere.merge(node.boundingSphere);
						}

						this.boundsDirty_ = false;
						this.transformDirty_ = false;
						break;
					}

					node.updateTransform(this.transformDirty_ || forced);
					node = node.parent;
				}
			}

			for(int var3 = this.controllers.length - 1; var3 >= 0; --var3) {
				if (this.controllers[var3].isInLookAtMode()) {
					this.controllers[var3].update();
				}
			}
		}

		if (this.boundsDirty_ || forced) {
			if (this.transformDirty_ || forced) {
				if (this.group != null) {
					this.localTransformation = this.group.localTransformation.multiplyTo(this.compositeTransformation, this.localTransformation);
				} else {
					this.localTransformation.set(this.compositeTransformation);
				}
			}

			for(node = this.head; node != null; node = node.parent) {
				node.updateTransform(this.transformDirty_ || forced);
			}

			this.boundingSphere.setXYZR(this.localTransformation.getPositionX(), this.localTransformation.getPositionY(), this.localTransformation.getPositionZ(), 0);

			for(node = this.head; node != null; node = node.parent) {
				this.boundingSphere.merge(node.boundingSphere);
			}

			this.boundsDirty_ = false;
			this.transformDirty_ = false;
		}

	}
}
