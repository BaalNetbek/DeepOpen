package GoF2.Main;

import javax.microedition.lcdui.Image;

import AE.AEFile;
import AE.AbstractMesh;
import AE.Camera;
import AE.EaseInOut;
import AE.GlobalStatus;
import AE.Math.AEMath;
import AE.Math.AEVector3D;
import AE.PaintCanvas.Font;
import AE.PaintCanvas.ImageFactory;
import GoF2.Achievements;
import GoF2.Agent;
import GoF2.FileRead;
import GoF2.Generator;
import GoF2.Globals;
import GoF2.Item;
import GoF2.Layout;
import GoF2.ListItem;
import GoF2.Mission;
import GoF2.Popup;
import GoF2.SolarSystem;
import GoF2.Status;

public final class SpaceLounge {
	private StarMap starMap;
	private Popup popup;
	private ListItemWindow intemInfoWindow;
	private Camera camera;
	private Image items;
	private Image itemTypes;
	private int chatType_;
	private boolean newSystemWaitingToBeSeen;
	private boolean missionConfirmPopup;
	private boolean transactionConfirmPopup_;
	private boolean itemDescriptionOpen;
	private int selectedAgent;
	private Agent[] agents;
	private int answearsPosY_;
	private int answearsPosX;
	private int chatTextPosY;
	private int chatTextPosX;
	private String[] chatRows;
	private int chatScroll;
	private int chatAnswear;
	private boolean starMapOpen;
	private boolean hangarUpdate;
	private Image[][] clientsFaces;
	private CutScene scene;
	private final EaseInOut cameraIntegratorX_;
	private final EaseInOut cameraIntegratorZ_;
	private AEVector3D cameraPos = new AEVector3D();
	private boolean[] mentionedOres;

	public SpaceLounge() {
		init();
		int var1 = -1;

		for(int var2 = 0; var2 < this.agents.length; ++var2) {
			Agent var3;
			if (((var3 = this.agents[var2]).getType() == 6 || var3.getType() == 0 && var3.getMission() != null && var3.getMission().getType() == 12) && var3.isAccepted()) {
				var1 = var2;
				break;
			}
		}

		if (var1 >= 0) {
			final Agent[] var4 = new Agent[this.agents.length - 1];
			if (var1 == 0) {
				System.arraycopy(this.agents, 1, var4, 0, var4.length);
			} else {
				System.arraycopy(this.agents, 0, var4, 0, var1);
				System.arraycopy(this.agents, var1 + 1, var4, var1, var4.length - var1);
			}

			Status.getStation().setBarAgents(var4);
			init();
		}

		if (this.scene == null) {
			this.scene = new CutScene(4);
		}

		while(!this.scene.isLoaded()) {
			this.scene.OnInitialize();
		}

		this.cameraIntegratorX_ = new EaseInOut(GlobalStatus.renderer.getCamera().getPosX(), this.scene.level.getEnemies()[0].mainMesh_.getPosX());
		this.cameraIntegratorZ_ = new EaseInOut(GlobalStatus.renderer.getCamera().getPosZ(), this.scene.level.getEnemies()[0].mainMesh_.getPosZ() - 1000);
	}

	public final void init() {
		this.agents = Status.getStation().getBarAgents();
		if (this.agents != null) {
			this.clientsFaces = new Image[this.agents.length][];
		}

		int var2;
		for(var2 = 0; var2 < this.agents.length; ++var2) {
			this.clientsFaces[var2] = ImageFactory.faceFromByteArray(this.agents[var2].getFace());
		}

		this.answearsPosX = 9;
		this.chatTextPosY = 21;
		this.chatTextPosX = ImageFactory.faceWidth + 10;
		this.popup = new Popup();
		this.hangarUpdate = false;
		if (this.scene != null) {
			this.scene.resetCamera();
		}

		this.newSystemWaitingToBeSeen = false;
		this.mentionedOres = new boolean[20];

		for(var2 = 0; var2 < this.mentionedOres.length; ++var2) {
			this.mentionedOres[var2] = false;
		}

		this.camera = GlobalStatus.renderer.getCamera();
	}

	public final void OnRelease() {
		this.clientsFaces = null;
		if (this.scene != null) {
			this.scene.OnRelease();
		}

		this.scene = null;
	}

	public final boolean handleKeystate(final int var1) {
		if (this.starMapOpen) {
			if (!this.starMap.handleKeystate(var1)) {
				this.starMapOpen = false;
				this.scene.resetCamera();
			}

			return true;
		}
		if (this.transactionConfirmPopup_) {
			if (var1 == 256) {
				this.transactionConfirmPopup_ = false;
			}

			if (var1 == 4) {
				this.popup.left();
			} else if (var1 == 32) {
				this.popup.right();
			}
		} else {
			boolean var7;
			switch(this.chatType_) {
			case 0:
				var7 = false;
				if (var1 == 2 || var1 == 32) {
					--this.selectedAgent;
					if (this.selectedAgent < 0) {
						this.selectedAgent = this.agents.length - 1;
					}

					var7 = true;
				}

				if (var1 == 64 || var1 == 4) {
					++this.selectedAgent;
					if (this.selectedAgent >= this.agents.length) {
						this.selectedAgent = 0;
					}

					var7 = true;
				}

				if (var7) {
					final AbstractMesh var11 = this.scene.level.getEnemies()[this.selectedAgent].mainMesh_;
					this.cameraIntegratorX_.SetRange(GlobalStatus.renderer.getCamera().getPosX(), var11.getPosX());
					this.cameraIntegratorZ_.SetRange(GlobalStatus.renderer.getCamera().getPosZ(), var11.getPosZ() - 1000);
				}

				if (var1 == 8192) {
					GlobalStatus.renderer.setActiveCamera(this.camera);
					return false;
				}

				if (var1 == 16384 || var1 == 256) {
					startChat();
				}
				break;
			case 1:
			case 3:
				if (var1 == 8192) {
					if (this.chatScroll >= 3) {
						this.chatScroll -= 4;
					} else if (this.chatType_ == 1) {
						this.chatType_ = 0;
						this.chatScroll = 0;
					}
				}

				if (var1 == 16384 || var1 == 256) {
					var7 = false;
					if (this.chatScroll >= this.chatRows.length - 4) {
						var7 = true;
					} else {
						this.chatScroll += 4;
						if (this.agents[this.selectedAgent].getType() != 1 && this.chatType_ == 1 && this.chatScroll >= this.chatRows.length - 4 || this.chatType_ == 3 && this.chatScroll >= this.chatRows.length) {
							var7 = true;
						}
					}

					if (var7) {
						this.chatAnswear = 0;
						Agent var9;
						if ((var9 = this.agents[this.selectedAgent]).getType() == 1) {
							this.chatType_ = 0;
						} else {
							this.chatType_ = this.chatType_ == 1 ? 2 : 0;
							if (this.chatType_ == 0 && this.newSystemWaitingToBeSeen) {
								this.starMap = ((ModStation)GlobalStatus.scenes[1]).starMap;
								if (this.starMap == null) {
									((ModStation)GlobalStatus.scenes[1]).starMap = new StarMap(false, (Mission)null, true, var9.getSellSystemIndex());
									this.starMap = ((ModStation)GlobalStatus.scenes[1]).starMap;
								} else {
									this.starMap.init(false, (Mission)null, true, var9.getSellSystemIndex());
								}

								this.starMapOpen = true;
								this.newSystemWaitingToBeSeen = false;
							}
						}
					}
				}
				break;
			case 2:
				final int var2 = this.agents[this.selectedAgent].getMission() != null && this.agents[this.selectedAgent].getMission().isOutsideMission() ? 5 : this.agents[this.selectedAgent].getType() != 2 && this.agents[this.selectedAgent].getType() != 3 ? 3 : 4;
				if (this.itemDescriptionOpen) {
					if (var1 == 8192) {
						this.itemDescriptionOpen = false;
					}

					return true;
				}

				int var3;
				Agent var6;
				if (this.missionConfirmPopup) {
					if (var1 == 4) {
						this.popup.left();
					}

					if (var1 == 32) {
						this.popup.right();
					}

					if (var1 != 256) {
						return true;
					}

					if (this.popup.getChoice()) {
						String var8 = GlobalStatus.gameText.getText(484 + GlobalStatus.random.nextInt(3));
						switch((var6 = this.agents[this.selectedAgent]).getType()) {
						case 0:
						case 5:
							if (Status.getFreelanceMission() != null && !Status.getFreelanceMission().isEmpty()) {
								if (Status.getFreelanceMission().getType() != 0 && Status.getFreelanceMission().getType() != 3 && Status.getFreelanceMission().getType() != 5) {
									if (Status.getFreelanceMission().getType() == 11) {
										Status.setPassengers(0);
									}
								} else {
									Item[] var12 = Status.getShip().getCargo();
									if (var12 != null) {
										for(int var14 = 0; var14 < var12.length; ++var14) {
											if (var12[var14].setUnsaleable() && var12[var14].getIndex() == 116) {
												Status.getShip().removeCargo(var12[var14]);
												this.hangarUpdate = true;
												break;
											}
										}
									}
								}

								if (!Status.getFreelanceMission().getAgent().isGenericAgent_()) {
									Status.getFreelanceMission().getAgent().setAccepted(false);
								}

								Status.setFreelanceMission(Mission.emptyMission_);
							}

							if (var6.getMission().getType() == 0) {
								Item var13;
								(var13 = Globals.getItems()[116].makeItem(var6.getMission().getCommodityAmount_())).setUnsaleable(true);
								Status.getShip().addCargo(var13);
							} else if (var6.getMission().getType() == 11) {
								Status.setPassengers(var6.getMission().getCommodityAmount_());
							}

							if (var6.getMission().getType() == 12) {
								var8 = GlobalStatus.gameText.getText(490);
							} else {
								var8 = var8 + " " + GlobalStatus.gameText.getText(487 + GlobalStatus.random.nextInt(3));
							}

							if (var6.getMission().isOutsideMission()) {
								if (!var6.wasAskedForDifficulty) {
									++Status.acceptedNotAskingDifficulty;
								}

								if (!var6.wasAskedForLocation) {
									++Status.acceptedNotAskingLocation;
								}
							}

							Status.setFreelanceMission(var6.getMission());
							Status.getFreelanceMission().setAgent(var6);
							init();
							this.hangarUpdate = true;
						case 1:
						default:
							break;
						case 2:
							Status.changeCredits(-var6.getSellItemPrice());
							Status.getShip().addCargo(Globals.getItems()[var6.getSellItemIndex()].makeItem(var6.getSellItemQuantity()));
							if (var6.getSellItemIndex() >= 132 && var6.getSellItemIndex() < 154) {
								Status.drinkTypesPossesed[var6.getSellItemIndex() - 132] = true;
							}

							this.hangarUpdate = true;
							break;
						case 3:
							Status.changeCredits(-var6.getSellItemPrice());

							for(var3 = 0; var3 < Status.getBluePrints().length; ++var3) {
								if (Status.getBluePrints()[var3].getIndex() == var6.getSellBlueprintIndex()) {
									Status.getBluePrints()[var3].unlock();
									break;
								}
							}

							this.hangarUpdate = true;
							break;
						case 4:
							Status.changeCredits(-var6.getSellItemPrice());
							Status.setSystemVisibility(var6.getSellSystemIndex(), true);
							this.hangarUpdate = true;
							this.newSystemWaitingToBeSeen = true;
							break;
						case 6:
							Status.commandedWingmen += 1 + var6.getWingmanFriendsCount_();
							Status.setWingmenNames(var6.getWingmenNames());
							Status.wingmanRace = var6.getRace();
							Status.wingmenTimeRemaining = 600000;
							Status.wingmanFace = var6.getFace();
							if (Achievements.gotAllMedals()) {
								Status.changeCredits(var6.getCosts());
							} else {
								Status.changeCredits(-var6.getCosts());
							}
							break;
						case 7:
							Status.getStanding().rehabilitate(var6.getRace());
							Status.changeCredits(-var6.getCosts());
						}

						var6.setAccepted(true);
						this.chatRows = Font.splitToLines(var8, GlobalStatus.screenWidth - this.chatTextPosX - this.answearsPosX);
						this.chatType_ = 3;
						this.chatScroll = 0;
					}

					this.missionConfirmPopup = false;
					return true;
				}

				if (var1 == 2) {
					--this.chatAnswear;
					if (this.chatAnswear < 0) {
						this.chatAnswear = var2 - 1;
					}
				}

				if (var1 == 64) {
					++this.chatAnswear;
					if (this.chatAnswear >= var2) {
						this.chatAnswear = 0;
					}
				}

				if (var1 == 16384 || var1 == 256) {
					boolean var5 = false;
					switch(this.chatAnswear) {
					case 0:
						var3 = (var6 = this.agents[this.selectedAgent]).getType();
						String var4 = "";
						switch(var3) {
						case 0:
						case 5:
							if (var6.getMission().getType() == 0) {
								var3 = var6.getMission().getCommodityAmount_();
								if (!Status.getShip().spaceAvailable(var3)) {
									var4 = Status.replaceTokens(GlobalStatus.gameText.getText(162), "" + var3, "#Q");
									var5 = true;
								}
							} else if (var6.getMission().getType() == 11 && (var3 = var6.getMission().getCommodityAmount_()) > Status.getShip().getMaxPassengers()) {
								var4 = Status.replaceTokens(GlobalStatus.gameText.getText(163), "" + var3, "#Q");
								var5 = true;
							}

							if (!var5) {
								if (var6.isGenericAgent_()) {
									var4 = Status.replaceTokens(Status.replaceTokens(GlobalStatus.gameText.getText(499), Layout.formatCredits(var6.getMission().getReward()), "#C"), GlobalStatus.gameText.getText(179 + var6.getMission().getType()), "#M");
								} else {
									var4 = GlobalStatus.gameText.getText(498);
								}
							}
						case 1:
						default:
							break;
						case 2:
						case 3:
						case 4:
							if (Status.getCredits() < var6.getSellItemPrice()) {
								this.popup.set(Status.replaceTokens(GlobalStatus.gameText.getText(83), Layout.formatCredits(var6.getSellItemPrice() - Status.getCredits()), "#C"), false);
								this.transactionConfirmPopup_ = true;
								return true;
							}

							switch (var3) {
							case 3:
								var4 = Status.replaceTokens(Status.replaceTokens(GlobalStatus.gameText.getText(503), GlobalStatus.gameText.getText(569 + Globals.getItems()[var6.getSellBlueprintIndex()].getIndex()), "#P"), Layout.formatCredits(var6.getSellItemPrice()), "#C");
								break;
							case 4: {
								var4 = GlobalStatus.gameText.getText(504);
								//new FileRead();
								SolarSystem[] var10 = FileRead.loadSystemsBinary();
								var4 = Status.replaceTokens(Status.replaceTokens(var4, var10[var6.getSellSystemIndex()].getName(), "#S"), Layout.formatCredits(var6.getSellItemPrice()), "#C");
								break;
							}
							case 2:
								var4 = Status.replaceTokens(Status.replaceTokens(Status.replaceTokens(GlobalStatus.gameText.getText(502), "" + var6.getSellItemQuantity(), "#Q"), GlobalStatus.gameText.getText(569 + var6.getSellItemIndex()), "#P"), Layout.formatCredits(var6.getSellItemPrice()), "#C");
								break;
							default:
								break;
							}
							break;
						case 6:
							if (Status.getCredits() < var6.getCosts()) {
								this.popup.set(Status.replaceTokens(GlobalStatus.gameText.getText(83), Layout.formatCredits(var6.getCosts() - Status.getCredits()), "#C"), false);
								this.transactionConfirmPopup_ = true;
								return true;
							}

							if (Status.getWingmenNames() != null) {
								this.popup.set(GlobalStatus.gameText.getText(424), false);
								this.transactionConfirmPopup_ = true;
								return true;
							}

							var4 = Status.replaceTokens(Status.replaceTokens(GlobalStatus.gameText.getText(Achievements.gotAllMedals() ? 501 : 500), "" + (1 + var6.getWingmanFriendsCount_()), "#Q"), Layout.formatCredits(var6.getCosts()), "#C");
							break;
						case 7:
							if (Status.getCredits() < var6.getCosts()) {
								this.popup.set(Status.replaceTokens(GlobalStatus.gameText.getText(83), Layout.formatCredits(var6.getCosts() - Status.getCredits()), "#C"), false);
								this.transactionConfirmPopup_ = true;
								return true;
							}

							var4 = Status.replaceTokens(GlobalStatus.gameText.getText(515), Layout.formatCredits(var6.getCosts()), "#C");
						}

						if (var5) {
							this.popup.set(var4, false);
							this.transactionConfirmPopup_ = true;
						} else {
							this.popup.set(var4, true);
							this.missionConfirmPopup = true;
						}
						break;
					case 1:
						this.chatRows = Font.splitToLines(GlobalStatus.gameText.getText(479 + GlobalStatus.random.nextInt(4)), GlobalStatus.screenWidth - this.chatTextPosX - this.answearsPosX);
						this.chatType_ = 3;
						this.chatScroll = 0;
						++Status.missionsRejected;
						break;
					case 2:
						this.chatType_ = 1;
						this.chatRows = Font.splitToLines(this.agents[this.selectedAgent].getMessage(), GlobalStatus.screenWidth - this.chatTextPosX - this.answearsPosX);
						this.chatScroll = 0;
						++Status.askedToRepeate;
						break;
					case 3:
						if (this.agents[this.selectedAgent].getType() != 2 && this.agents[this.selectedAgent].getType() != 3) {
							this.agents[this.selectedAgent].wasAskedForLocation = true;
							if (this.agents[this.selectedAgent].getMission().getTargetStation() == Status.getStation().getId()) {
								this.chatRows = Font.splitToLines(GlobalStatus.gameText.getText(441), GlobalStatus.screenWidth - this.chatTextPosX - this.answearsPosX);
								this.chatType_ = 1;
								this.chatScroll = 0;
							} else {
								this.starMap = ((ModStation)GlobalStatus.scenes[1]).starMap;
								if (this.starMap == null) {
									((ModStation)GlobalStatus.scenes[1]).starMap = new StarMap(true, this.agents[this.selectedAgent].getMission(), false, -1);
									this.starMap = ((ModStation)GlobalStatus.scenes[1]).starMap;
								} else {
									this.starMap.init(true, this.agents[this.selectedAgent].getMission(), false, -1);
								}

								this.starMapOpen = true;
							}
						} else {
							if (this.intemInfoWindow == null) {
								this.intemInfoWindow = new ListItemWindow();
								this.items = AEFile.loadCryptedImage("/data/interface/items.png");
								this.itemTypes = AEFile.loadCryptedImage("/data/interface/item_types.png");
							}

							if (this.agents[this.selectedAgent].getType() == 2) {
								this.intemInfoWindow.setup(new ListItem(Globals.getItems()[this.agents[this.selectedAgent].getSellItemIndex()]), this.items, this.itemTypes, (Image)null, (Image)null, false);
							} else {
								for(var3 = 0; var3 < Status.getBluePrints().length; ++var3) {
									if (Status.getBluePrints()[var3].getIndex() == this.agents[this.selectedAgent].getSellBlueprintIndex()) {
										this.intemInfoWindow.setup(new ListItem(Globals.getItems()[Status.getBluePrints()[var3].getIndex()]), this.items, this.itemTypes, (Image)null, (Image)null, false);
										break;
									}
								}
							}

							this.itemDescriptionOpen = true;
						}
						break;
					case 4:
						this.agents[this.selectedAgent].wasAskedForDifficulty = true;
						var3 = (int)(this.agents[this.selectedAgent].getMission().getDifficulty() / 10.0F * 5.0F);
						this.chatRows = Font.splitToLines(GlobalStatus.gameText.getText(var3 + 443), GlobalStatus.screenWidth - this.chatTextPosX - this.answearsPosX);
						this.chatType_ = 1;
						this.chatScroll = 0;
					}

					if (this.agents[this.selectedAgent].isGenericAgent_()) {
						this.agents[this.selectedAgent].setEvent(1);
					}
				}
			}
		}
		return true;
	}

	public final void draw(final int var1, int var2) {
		if (this.itemDescriptionOpen) {
			this.intemInfoWindow.draw();
			Layout.drawFooter("", GlobalStatus.gameText.getText(65));
		} else if (this.starMapOpen) {
			this.starMap.update(var1, var2);
		} else {
			Layout.drawBG();
			GlobalStatus.graphics.setClip(1, 15, GlobalStatus.screenWidth - 1, GlobalStatus.screenHeight - 16);
			this.cameraIntegratorX_.Increase(var2);
			this.cameraIntegratorZ_.Increase(var2);
			var2 = this.cameraIntegratorX_.GetValue();
			int var3 = this.cameraIntegratorZ_.GetValue();
			GlobalStatus.renderer.getCamera().moveTo(var2, 500, var3);
			if (this.scene != null) {
				this.scene.renderScene(var1);
			}

			GlobalStatus.graphics.setClip(0, 0, GlobalStatus.screenWidth, GlobalStatus.screenHeight);
			Layout.drawNonFullScreenWindow(GlobalStatus.gameText.getText(218), false);
			if (this.chatType_ == 0) {
				this.answearsPosY_ = GlobalStatus.screenHeight - 16 - (this.agents.length + 1) * Font.getFontSpacingY() + 2;
			} else if (this.chatType_ == 2) {
				if (this.agents[this.selectedAgent].getType() != 2 && this.agents[this.selectedAgent].getType() != 3) {
					if (this.agents[this.selectedAgent].getMission() != null && this.agents[this.selectedAgent].getMission().isOutsideMission()) {
						this.answearsPosY_ = GlobalStatus.screenHeight - 16 - 6 * Font.getFontSpacingY() + 2;
					} else {
						this.answearsPosY_ = GlobalStatus.screenHeight - 16 - 4 * Font.getFontSpacingY() + 2;
					}
				} else {
					this.answearsPosY_ = GlobalStatus.screenHeight - 16 - 5 * Font.getFontSpacingY() + 2;
				}
			} else {
				this.answearsPosY_ = GlobalStatus.screenHeight;
			}

			if (this.chatType_ == 2) {
				Layout.drawFilledTitleBarWindow((String)null, 3, this.answearsPosY_, GlobalStatus.screenWidth - 7, GlobalStatus.screenHeight - this.answearsPosY_ - 16 - 3);
			}

			if (this.chatType_ != 0) {
				Layout.drawFilledTitleBarWindow((String)null, 3, 15, GlobalStatus.screenWidth - 7, ImageFactory.faceHeight + 7);
				ImageFactory.drawChar(this.clientsFaces[this.selectedAgent], 7, 19, 0);
			}

			if (this.chatType_ == 0) {
				Layout.drawFooter(GlobalStatus.gameText.getText(494), GlobalStatus.gameText.getText(65));
				this.cameraPos = this.scene.level.getEnemies()[this.selectedAgent].mainMesh_.getLocalPos(this.cameraPos);
				AEVector3D var10000 = this.cameraPos;
				var10000.x -= 100;
				var10000 = this.cameraPos;
				var10000.y += 500;
				GlobalStatus.renderer.getCamera().getScreenPosition(this.cameraPos);
				Font.drawString(!this.agents[this.selectedAgent].isKnown() && !this.agents[this.selectedAgent].isSpecialAgent() ? GlobalStatus.gameText.getText(229 + this.agents[this.selectedAgent].getRace()) : this.agents[this.selectedAgent].fullName, this.cameraPos.x, this.cameraPos.y, 2);
				if (this.agents[this.selectedAgent].isKnown()) {
					Font.drawString(this.agents[this.selectedAgent].getMission() != null ? GlobalStatus.gameText.getText(179 + this.agents[this.selectedAgent].getMission().getType()) : this.agents[this.selectedAgent].getType() == 6 ? GlobalStatus.gameText.getText(146) : this.agents[this.selectedAgent].getType() == 2 ? GlobalStatus.gameText.getText(145) : this.agents[this.selectedAgent].getType() == 7 ? GlobalStatus.gameText.getText(514) : "", this.cameraPos.x, this.cameraPos.y + Font.getFontSpacingY(), 1);
				}
			} else {
				var2 = 0;

				for(var3 = this.chatScroll; var3 < this.chatScroll + 4 && var3 < this.chatRows.length; ++var3) {
					final String var4 = var3 != this.chatRows.length - 1 && var3 == this.chatScroll + 4 - 1 ? ".." : "";
					Font.drawString(this.chatRows[var3] + (Layout.quickClockHigh_() ? var4 : ""), this.chatTextPosX, this.chatTextPosY + var2 * Font.getFontSpacingY(), 1);
					var2++;
				}

				if (this.chatType_ != 1 && this.chatType_ != 3) {
					Font.drawString(GlobalStatus.gameText.getText(495), this.answearsPosX, this.answearsPosY_ + 3 + 0 * Font.getFontSpacingY(), this.chatAnswear == 0 ? 2 : 1);
					Font.drawString(GlobalStatus.gameText.getText(496), this.answearsPosX, this.answearsPosY_ + 3 + 1 * Font.getFontSpacingY(), this.chatAnswear == 1 ? 2 : 1);
					Font.drawString(GlobalStatus.gameText.getText(497), this.answearsPosX, this.answearsPosY_ + 3 + 2 * Font.getFontSpacingY(), this.chatAnswear == 2 ? 2 : 1);
					var3 = this.agents[this.selectedAgent].getType();
					if (var3 != 2 && var3 != 3) {
						if (var3 == 0 && this.agents[this.selectedAgent].getMission().isOutsideMission()) {
							Font.drawString(GlobalStatus.gameText.getText(440), this.answearsPosX, this.answearsPosY_ + 3 + 3 * Font.getFontSpacingY(), this.chatAnswear == 3 ? 2 : 1);
							Font.drawString(GlobalStatus.gameText.getText(442), this.answearsPosX, this.answearsPosY_ + 3 + 4 * Font.getFontSpacingY(), this.chatAnswear == 4 ? 2 : 1);
						}
					} else {
						Font.drawString(GlobalStatus.gameText.getText(415), this.answearsPosX, this.answearsPosY_ + 3 + 3 * Font.getFontSpacingY(), this.chatAnswear == 3 ? 2 : 1);
					}

					Layout.drawFooter(GlobalStatus.gameText.getText(253), "");
				} else {
					Layout.drawFooter(GlobalStatus.gameText.getText(73), this.chatScroll > 0 ? GlobalStatus.gameText.getText(74) : this.chatType_ == 1 ? GlobalStatus.gameText.getText(246) : "");
				}
			}

			if (this.missionConfirmPopup || this.transactionConfirmPopup_) {
				this.popup.draw();
			}

		}
	}

	private void startChat() {
		Agent var1;
		String var8;
		if (!(var1 = this.agents[this.selectedAgent]).isGenericAgent_()) {
			var8 = null;
			if (var1.getEvent() > 0) {
			} else {
				if (!var1.isAccepted()) {
					++Status.barInteractions;
					if (var1.getType() == 4) {
						var8 = GlobalStatus.gameText.getText(505 + GlobalStatus.random.nextInt(2));
						var8 = var8 + " " + GlobalStatus.gameText.getText(516 + var1.getMessageId());
						var8 = var8 + " " + GlobalStatus.gameText.getText(508);
						//new FileRead();
						final SolarSystem[] var11 = FileRead.loadSystemsBinary();
						var8 = Status.replaceTokens(Status.replaceTokens(var8, var11[var1.getSellSystemIndex()].getName(), "#S"), Layout.formatCredits(var1.getSellItemPrice()), "#C");
					} else {
						var8 = GlobalStatus.gameText.getText(505 + GlobalStatus.random.nextInt(2));
						var8 = var8 + " " + GlobalStatus.gameText.getText(516 + var1.getMessageId());
						var8 = Status.replaceTokens(Status.replaceTokens(var8 + " " + GlobalStatus.gameText.getText(507), GlobalStatus.gameText.getText(569 + Globals.getItems()[var1.getSellBlueprintIndex()].getIndex()), "#N"), Layout.formatCredits(var1.getSellItemPrice()), "#C");
					}

					var8 = var8 + "\n" + GlobalStatus.gameText.getText(475 + GlobalStatus.random.nextInt(3));
					this.chatRows = Font.splitToLines(var8, GlobalStatus.screenWidth - this.chatTextPosX - this.answearsPosX);
					this.agents[this.selectedAgent].setMessage(var8);
					this.chatScroll = 0;
					this.chatType_ = 1;
					return;
				}
			}
			var8 = GlobalStatus.gameText.getText(492);

			this.chatRows = Font.splitToLines(var8, GlobalStatus.screenWidth - this.chatTextPosX - this.answearsPosX);
			this.chatScroll = 0;
			this.chatType_ = 3;
		} else {
			if (var1.isKnown() && var1.getType() != 7) {
				if (var1.isAccepted()) {
					var8 = GlobalStatus.gameText.getText(var1.getType() == 5 ? 491 : var1.getType() != 6 && (var1.getMission() == null || var1.getMission().getType() != 12) ? 492 : 493);
					this.chatRows = Font.splitToLines(var8, GlobalStatus.screenWidth - this.chatTextPosX - this.answearsPosX);
					this.chatScroll = 0;
					this.chatType_ = 3;
					return;
				}

				this.chatRows = Font.splitToLines(var1.getMessage(), GlobalStatus.screenWidth - this.chatTextPosX - this.answearsPosX);
			} else {
				++Status.barInteractions;
				int var2;
				final boolean var3 = (var2 = var1.getType()) != 1 && var2 != 7;
				final boolean var4 = var2 == 0 || var2 == 5;
				var8 = var3 ? GlobalStatus.gameText.getText(390 + GlobalStatus.random.nextInt(6)) : "";
				String var9 = var3 ? GlobalStatus.gameText.getText(396 + GlobalStatus.random.nextInt(2)) : "";
				final String var10 = var4 ? GlobalStatus.gameText.getText(398 + GlobalStatus.random.nextInt(6)) : "";
				String var5 = "";
				var9 = Status.replaceTokens(var9, var1.getFullName(), "#N");
				final Generator var6 = new Generator();
				int var7;
				switch(var1.getType()) {
				case 0:
					var5 = var5 + GlobalStatus.gameText.getText(425 + var1.getMission().getType());
					if (var1.getMission().getType() == 5 || var1.getMission().getType() == 3) {
						var5 = var5 + " " + GlobalStatus.gameText.getText(438);
					}

					var5 = Status.replaceTokens(Status.replaceTokens(Status.replaceTokens(Status.replaceTokens(var5, GlobalStatus.gameText.getText(448 + var1.getMission().getCommodityIndex()), "#P"), "" + var1.getMission().getCommodityAmount_(), "#Q"), var1.getMission().getTargetStationName(), "#S"), var1.getMission().getTargetName(), "#N");
					var5 = Status.replaceTokens(var5 + "\n" + GlobalStatus.gameText.getText(404 + GlobalStatus.random.nextInt(3)), Layout.formatCredits(var1.getMission().getReward()), "#C");
					break;
				case 1:
					boolean var16 = false;

					do {
						var7 = GlobalStatus.random.nextInt(20);
						if (!this.mentionedOres[var7]) {
							this.mentionedOres[var7] = true;
							var16 = true;
						}
					} while(!var16);

					if (var1.getRace() != Globals.TERRAN && var7 == 16) {
						var7 = 4;
					}

					var5 = Status.replaceTokens(Status.replaceTokens(Status.replaceTokens(GlobalStatus.gameText.getText(var7 + 455), Globals.getRandomPlanetName(), "#S"), var1.getFullName(), "#N"), GlobalStatus.gameText.getText(723 + GlobalStatus.random.nextInt(10)), "#ORE");
					break;
				case 2:
					var5 = var5 + GlobalStatus.gameText.getText(407 + GlobalStatus.random.nextInt(4));
					var5 = Status.replaceTokens(Status.replaceTokens(Status.replaceTokens(var5 + "\n" + GlobalStatus.gameText.getText(412 + GlobalStatus.random.nextInt(2)), "" + var1.getSellItemQuantity(), "#Q"), GlobalStatus.gameText.getText(569 + var1.getSellItemIndex()), "#P"), Layout.formatCredits(var1.getSellItemPrice()), "#C");
					if (var1.getSellItemQuantity() > 1) {
						var5 = Status.replaceTokens(var5 + " " + GlobalStatus.gameText.getText(414), Layout.formatCredits(var1.getSellItemPrice() / var1.getSellItemQuantity()), "#C");
					}
				case 3:
				case 4:
				default:
					break;
				case 5:
					var1.setMission(var6.createFreelanceMission(var1));
					var5 = Status.replaceTokens(Status.replaceTokens(Status.replaceTokens(var5 + GlobalStatus.gameText.getText(416 + GlobalStatus.random.nextInt(2)), "" + var1.getMission().getCommodityAmount_(), "#Q"), GlobalStatus.gameText.getText(569 + var1.getMission().getCommodityIndex()), "#P"), Layout.formatCredits(var1.getMission().getReward()), "#C");
					break;
				case 6:
					var5 = Status.replaceTokens(var5 + GlobalStatus.gameText.getText((Achievements.gotAllMedals() ? 421 : 418) + var1.getWingmanFriendsCount_()), Layout.formatCredits(var1.getCosts()), "#C");
					if (var1.getWingmanFriendsCount_() > 0) {
						var5 = Status.replaceTokens(var5, "" + var1.getWingmanName(1), "#W");
					}
					break;
				case 7:
					final int var13 = var1.getRace();
					int var12 = Status.getStanding().getStanding(var13 != 2 && var13 != 3 ? 0 : 1);
					if (!Status.getStanding().isEnemy(var13)) {
						var5 = GlobalStatus.gameText.getText(513);
						this.chatRows = Font.splitToLines(var5, GlobalStatus.screenWidth - this.chatTextPosX - this.answearsPosX);
						this.chatScroll = 0;
						this.chatType_ = 3;
						return;
					}

					var7 = (int)(AEMath.abs(var12) / 100.0F * 16000.0F);
					var12 = var13 == 2 ? 509 : var13 == 3 ? 510 : var13 == 0 ? 511 : 512;
					var5 = Status.replaceTokens(GlobalStatus.gameText.getText(var12), Layout.formatCredits(var7), "#C");
					var1.setCosts(var7);
				}

				var1.setMessage(var5);
				if (var1.getType() == 1) {
					this.chatRows = Font.splitToLines(var5, GlobalStatus.screenWidth - this.chatTextPosX - this.answearsPosX);
					var1.setEvent(1);
				} else if (var1.getType() == 7) {
					this.chatRows = Font.splitToLines(var5, GlobalStatus.screenWidth - this.chatTextPosX - this.answearsPosX);
				} else {
					String var14;
					if (var1.getMission() != null && var1.getMission().getType() == 12) {
						var5 = Status.replaceTokens(GlobalStatus.gameText.getText(437), Layout.formatCredits(var1.getMission().getReward()), "#C");
						var1.setMessage(var5);
						var14 = "\n" + GlobalStatus.gameText.getText(475 + GlobalStatus.random.nextInt(3));
						this.chatRows = Font.splitToLines(var5 + var14, GlobalStatus.screenWidth - this.chatTextPosX - this.answearsPosX);
					} else {
						var5 = "\n" + var5;
						var14 = "\n" + GlobalStatus.gameText.getText(475 + GlobalStatus.random.nextInt(3));
						this.chatRows = Font.splitToLines(var8 + " " + var9 + " " + var10 + var5 + var14, GlobalStatus.screenWidth - this.chatTextPosX - this.answearsPosX);
					}
				}
			}

			var1.setAgentsStationName(Status.getStation().getName());
			var1.setAgentsSystemName(Status.getSystem().getName());
			this.chatType_ = 1;
			this.chatScroll = 0;
		}
	}

	public final boolean hangarNeedsUpdate() {
		return this.hangarUpdate;
	}

	public final void setHangarUpdate(final boolean var1) {
		this.hangarUpdate = false;
	}
}
