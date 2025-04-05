package Main;

import AbyssEngine.AEImage;
import AbyssEngine.AEMath;
import AbyssEngine.AEVector3D;
import AbyssEngine.AbstractMesh;
import AbyssEngine.Agent;
import AbyssEngine.Camera;
import AbyssEngine.Class_1025;
import AbyssEngine.Face_;
import AbyssEngine.FileRead;
import AbyssEngine.GameStatus;
import AbyssEngine.Generator;
import AbyssEngine.IndexManager;
import AbyssEngine.Item;
import AbyssEngine.Layout;
import AbyssEngine.ListEntry;
import AbyssEngine.Medals;
import AbyssEngine.Mission;
import AbyssEngine.Popup;
import AbyssEngine.SolarSystem;
import AbyssEngine.Status;
import AbyssEngine.SymbolMapManager_;
import javax.microedition.lcdui.Image;

public final class Bar {
   private StarMap var_50;
   private Popup var_1a7;
   private ShipPreview var_1c0;
   private Camera var_1ca;
   private Image var_1e0;
   private Image var_22a;
   private int var_2a5;
   private boolean var_2c7;
   private boolean var_325;
   private boolean var_351;
   private boolean var_35f;
   private int var_3d2;
   private Agent[] var_436;
   private int var_46f;
   private int var_4c5;
   private int var_4df;
   private int var_53c;
   private String[] var_573;
   private int var_5ce;
   private int var_607;
   private boolean var_628;
   private boolean var_637;
   private Image[][] var_660;
   private Class_26fscene var_691;
   private Class_1025 var_6d3;
   private Class_1025 var_731;
   private AEVector3D var_773 = new AEVector3D();
   private boolean[] var_7b1;

   public Bar() {
      this.sub_5d();
      int var1 = -1;

      for(int var2 = 0; var2 < this.var_436.length; ++var2) {
         Agent var3;
         if (((var3 = this.var_436[var2]).sub_1b1() == 6 || var3.sub_1b1() == 0 && var3.sub_4f9() != null && var3.sub_4f9().getType() == 12) && var3.isAccepted()) {
            var1 = var2;
            break;
         }
      }

      if (var1 >= 0) {
         Agent[] var4 = new Agent[this.var_436.length - 1];
         if (var1 == 0) {
            System.arraycopy(this.var_436, 1, var4, 0, var4.length);
         } else {
            System.arraycopy(this.var_436, 0, var4, 0, var1);
            System.arraycopy(this.var_436, var1 + 1, var4, var1, var4.length - var1);
         }

         Status.getStation().setBarAgents(var4);
         this.sub_5d();
      }

      if (this.var_691 == null) {
         this.var_691 = new Class_26fscene(4);
      }

      while(!this.var_691.isLoaded()) {
         this.var_691.loadResources();
      }

      this.var_6d3 = new Class_1025(GameStatus.renderer.getCamera().sub_29c(), this.var_691.level.getShips()[0].var_25d.sub_29c());
      this.var_731 = new Class_1025(GameStatus.renderer.getCamera().sub_31f(), this.var_691.level.getShips()[0].var_25d.sub_31f() - 1000);
   }

   public final void sub_5d() {
      boolean var1 = false;
      this.var_436 = Status.getStation().getBarAgents();
      if (this.var_436 != null) {
         this.var_660 = new Image[this.var_436.length][];
      }

      int var2;
      for(var2 = 0; var2 < this.var_436.length; ++var2) {
         this.var_660[var2] = Face_.faceFromByteArray(this.var_436[var2].sub_471());
      }

      this.var_4c5 = 9;
      this.var_4df = 21;
      this.var_53c = Face_.faceWidth + 10;
      this.var_1a7 = new Popup();
      var1 = false;
      this.var_637 = false;
      if (this.var_691 != null) {
         this.var_691.sub_2c();
      }

      this.var_2c7 = false;
      this.var_7b1 = new boolean[20];

      for(var2 = 0; var2 < this.var_7b1.length; ++var2) {
         this.var_7b1[var2] = false;
      }

      this.var_1ca = GameStatus.renderer.getCamera();
   }

   public final void sub_7e() {
      this.var_660 = null;
      if (this.var_691 != null) {
         this.var_691.freeResources();
      }

      this.var_691 = null;
   }

   public final boolean sub_c7(int var1) {
      if (this.var_628) {
         if (!this.var_50.sub_a9(var1)) {
            this.var_628 = false;
            this.var_691.sub_2c();
         }

         return true;
      } else if (this.var_351) {
         if (var1 == 256) {
            this.var_351 = false;
         }

         if (var1 == 4) {
            this.var_1a7.sub_c6();
         } else if (var1 == 32) {
            this.var_1a7.sub_ff();
         }

         return true;
      } else {
         boolean var7;
         switch(this.var_2a5) {
         case 0:
            var7 = false;
            if (var1 == 2 || var1 == 32) {
               --this.var_3d2;
               if (this.var_3d2 < 0) {
                  this.var_3d2 = this.var_436.length - 1;
               }

               var7 = true;
            }

            if (var1 == 64 || var1 == 4) {
               ++this.var_3d2;
               if (this.var_3d2 >= this.var_436.length) {
                  this.var_3d2 = 0;
               }

               var7 = true;
            }

            if (var7) {
               AbstractMesh var11 = this.var_691.level.getShips()[this.var_3d2].var_25d;
               this.var_6d3.sub_fe(GameStatus.renderer.getCamera().sub_29c(), var11.sub_29c());
               this.var_731.sub_fe(GameStatus.renderer.getCamera().sub_31f(), var11.sub_31f() - 1000);
            }

            if (var1 == 8192) {
               GameStatus.renderer.sub_19(this.var_1ca);
               return false;
            }

            if (var1 == 16384 || var1 == 256) {
               this.sub_169();
            }
            break;
         case 1:
         case 3:
            if (var1 == 8192) {
               if (this.var_5ce >= 3) {
                  this.var_5ce -= 4;
               } else if (this.var_2a5 == 1) {
                  this.var_2a5 = 0;
                  this.var_5ce = 0;
               }
            }

            if (var1 == 16384 || var1 == 256) {
               var7 = false;
               if (this.var_5ce >= this.var_573.length - 4) {
                  var7 = true;
               } else {
                  this.var_5ce += 4;
                  if (this.var_436[this.var_3d2].sub_1b1() != 1 && this.var_2a5 == 1 && this.var_5ce >= this.var_573.length - 4 || this.var_2a5 == 3 && this.var_5ce >= this.var_573.length) {
                     var7 = true;
                  }
               }

               if (var7) {
                  this.var_607 = 0;
                  Agent var9;
                  if ((var9 = this.var_436[this.var_3d2]).sub_1b1() == 1) {
                     this.var_2a5 = 0;
                  } else {
                     this.var_2a5 = this.var_2a5 == 1 ? 2 : 0;
                     if (this.var_2a5 == 0 && this.var_2c7) {
                        this.var_50 = ((InsideStation)GameStatus.scenes[1]).var_85d;
                        if (this.var_50 == null) {
                           ((InsideStation)GameStatus.scenes[1]).var_85d = new StarMap(false, (Mission)null, true, var9.sub_569());
                           this.var_50 = ((InsideStation)GameStatus.scenes[1]).var_85d;
                        } else {
                           this.var_50.sub_4e(false, (Mission)null, true, var9.sub_569());
                        }

                        this.var_628 = true;
                        this.var_2c7 = false;
                     }
                  }
               }
            }
            break;
         case 2:
            int var2 = this.var_436[this.var_3d2].sub_4f9() != null && this.var_436[this.var_3d2].sub_4f9().sub_453() ? 5 : (this.var_436[this.var_3d2].sub_1b1() != 2 && this.var_436[this.var_3d2].sub_1b1() != 3 ? 3 : 4);
            if (this.var_35f) {
               if (var1 == 8192) {
                  this.var_35f = false;
               }

               return true;
            }

            int var3;
            Agent var6;
            if (this.var_325) {
               if (var1 == 4) {
                  this.var_1a7.sub_c6();
               }

               if (var1 == 32) {
                  this.var_1a7.sub_ff();
               }

               if (var1 != 256) {
                  return true;
               }

               if (this.var_1a7.sub_9a()) {
                  String var8 = GameStatus.langManager.getLangString(484 + GameStatus.random.nextInt(3));
                  switch((var6 = this.var_436[this.var_3d2]).sub_1b1()) {
                  case 0:
                  case 5:
                     if (Status.getFreelanceMission() != null && !Status.getFreelanceMission().isEmpty()) {
                        if (Status.getFreelanceMission().getType() != 0 && Status.getFreelanceMission().getType() != 3 && Status.getFreelanceMission().getType() != 5) {
                           if (Status.getFreelanceMission().getType() == 11) {
                              Status.sub_236(0);
                           }
                        } else {
                           Item[] var12;
                           if ((var12 = Status.getShip().getCargo()) != null) {
                              for(int var14 = 0; var14 < var12.length; ++var14) {
                                 if (var12[var14].sub_95c() && var12[var14].getIndex() == 116) {
                                    Status.getShip().sub_755(var12[var14]);
                                    this.var_637 = true;
                                    break;
                                 }
                              }
                           }
                        }

                        if (!Status.getFreelanceMission().getAgent().sub_41f()) {
                           Status.getFreelanceMission().getAgent().setAccepted(false);
                        }

                        Status.setFreelanceMission(Mission.var_dc);
                     }

                     if (var6.sub_4f9().getType() == 0) {
                        Item var13;
                        (var13 = IndexManager.getItems()[116].anyAmountCopies(var6.sub_4f9().getCommodityAmount())).sub_986(true);
                        Status.getShip().addItem(var13);
                     } else if (var6.sub_4f9().getType() == 11) {
                        Status.sub_236(var6.sub_4f9().getCommodityAmount());
                     }

                     if (var6.sub_4f9().getType() == 12) {
                        var8 = GameStatus.langManager.getLangString(490);
                     } else {
                        var8 = var8 + " " + GameStatus.langManager.getLangString(487 + GameStatus.random.nextInt(3));
                     }

                     if (var6.sub_4f9().sub_453()) {
                        if (!var6.var_99e) {
                           ++Status.acceptedNotAskingDifficulty;
                        }

                        if (!var6.var_9cd) {
                           ++Status.acceptedNotAskingLocation;
                        }
                     }

                     Status.setFreelanceMission(var6.sub_4f9());
                     Status.getFreelanceMission().setAgent(var6);
                     this.sub_5d();
                     this.var_637 = true;
                  case 1:
                  default:
                     break;
                  case 2:
                     Status.changeCredits(-var6.sub_7d5());
                     Status.getShip().addItem(IndexManager.getItems()[var6.sub_773()].anyAmountCopies(var6.sub_7ae()));
                     if (var6.sub_773() >= 132 && var6.sub_773() < 154) {
                        Status.drinksVar1[var6.sub_773() - 132] = true;
                     }

                     this.var_637 = true;
                     break;
                  case 3:
                     Status.changeCredits(-var6.sub_7d5());

                     for(var3 = 0; var3 < Status.getBluePrints().length; ++var3) {
                        if (Status.getBluePrints()[var3].getIndex() == var6.sub_582()) {
                           Status.getBluePrints()[var3].unlock();
                           break;
                        }
                     }

                     this.var_637 = true;
                     break;
                  case 4:
                     Status.changeCredits(-var6.sub_7d5());
                     Status.sub_e9(var6.sub_569(), true);
                     this.var_637 = true;
                     this.var_2c7 = true;
                     break;
                  case 6:
                     Status.commandedWingmen += 1 + var6.sub_634();
                     Status.setWingmenNames(var6.sub_6c1());
                     Status.wingmenRace_ = var6.getRace();
                     Status.wingmenTimeRemaining = 600000;
                     Status.var_8f8wingmenVar2 = var6.sub_471();
                     if (Medals.gotAllMedals()) {
                        Status.changeCredits(var6.sub_5d0());
                     } else {
                        Status.changeCredits(-var6.sub_5d0());
                     }
                     break;
                  case 7:
                     Status.getStanding().sub_d7(var6.getRace());
                     Status.changeCredits(-var6.sub_5d0());
                  }

                  var6.setAccepted(true);
                  this.var_573 = SymbolMapManager_.sub_3b2(var8, GameStatus.screenWidth - this.var_53c - this.var_4c5);
                  this.var_2a5 = 3;
                  this.var_5ce = 0;
               }

               this.var_325 = false;
               return true;
            }

            if (var1 == 2) {
               --this.var_607;
               if (this.var_607 < 0) {
                  this.var_607 = var2 - 1;
               }
            }

            if (var1 == 64) {
               ++this.var_607;
               if (this.var_607 >= var2) {
                  this.var_607 = 0;
               }
            }

            if (var1 == 16384 || var1 == 256) {
               boolean var5 = false;
               switch(this.var_607) {
               case 0:
                  var3 = (var6 = this.var_436[this.var_3d2]).sub_1b1();
                  String var4 = "";
                  switch(var3) {
                  case 0:
                  case 5:
                     if (var6.sub_4f9().getType() == 0) {
                        var3 = var6.sub_4f9().getCommodityAmount();
                        if (!Status.getShip().sub_839(var3)) {
                           var4 = Status.replaceTokens(GameStatus.langManager.getLangString(162), "" + var3, "#Q");
                           var5 = true;
                        }
                     } else if (var6.sub_4f9().getType() == 11 && (var3 = var6.sub_4f9().getCommodityAmount()) > Status.getShip().sub_982()) {
                        var4 = Status.replaceTokens(GameStatus.langManager.getLangString(163), "" + var3, "#Q");
                        var5 = true;
                     }

                     if (!var5) {
                        if (var6.sub_41f()) {
                           var4 = Status.replaceTokens(Status.replaceTokens(GameStatus.langManager.getLangString(499), Layout.formatCredits(var6.sub_4f9().getReward()), "#C"), GameStatus.langManager.getLangString(179 + var6.sub_4f9().getType()), "#M");
                        } else {
                           var4 = GameStatus.langManager.getLangString(498);
                        }
                     }
                  case 1:
                  default:
                     break;
                  case 2:
                  case 3:
                  case 4:
                     if (Status.getCredits() < var6.sub_7d5()) {
                        this.var_1a7.sub_8f(Status.replaceTokens(GameStatus.langManager.getLangString(83), Layout.formatCredits(var6.sub_7d5() - Status.getCredits()), "#C"), false);
                        this.var_351 = true;
                        return true;
                     }

                     if (var3 == 3) {
                        var4 = Status.replaceTokens(Status.replaceTokens(GameStatus.langManager.getLangString(503), GameStatus.langManager.getLangString(569 + IndexManager.getItems()[var6.sub_582()].getIndex()), "#P"), Layout.formatCredits(var6.sub_7d5()), "#C");
                     } else if (var3 == 4) {
                        var4 = GameStatus.langManager.getLangString(504);
                        new FileRead();
                        SolarSystem[] var10 = null;
                        var10 = FileRead.loadSystemsBinary();
                        var4 = Status.replaceTokens(Status.replaceTokens(var4, var10[var6.sub_569()].getName(), "#S"), Layout.formatCredits(var6.sub_7d5()), "#C");
                     } else if (var3 == 2) {
                        var4 = Status.replaceTokens(Status.replaceTokens(Status.replaceTokens(GameStatus.langManager.getLangString(502), "" + var6.sub_7ae(), "#Q"), GameStatus.langManager.getLangString(569 + var6.sub_773()), "#P"), Layout.formatCredits(var6.sub_7d5()), "#C");
                     }
                     break;
                  case 6:
                     if (Status.getCredits() < var6.sub_5d0()) {
                        this.var_1a7.sub_8f(Status.replaceTokens(GameStatus.langManager.getLangString(83), Layout.formatCredits(var6.sub_5d0() - Status.getCredits()), "#C"), false);
                        this.var_351 = true;
                        return true;
                     }

                     if (Status.getWingmenNames() != null) {
                        this.var_1a7.sub_8f(GameStatus.langManager.getLangString(424), false);
                        this.var_351 = true;
                        return true;
                     }

                     var4 = Status.replaceTokens(Status.replaceTokens(GameStatus.langManager.getLangString(Medals.gotAllMedals() ? 501 : 500), "" + (1 + var6.sub_634()), "#Q"), Layout.formatCredits(var6.sub_5d0()), "#C");
                     break;
                  case 7:
                     if (Status.getCredits() < var6.sub_5d0()) {
                        this.var_1a7.sub_8f(Status.replaceTokens(GameStatus.langManager.getLangString(83), Layout.formatCredits(var6.sub_5d0() - Status.getCredits()), "#C"), false);
                        this.var_351 = true;
                        return true;
                     }

                     var4 = Status.replaceTokens(GameStatus.langManager.getLangString(515), Layout.formatCredits(var6.sub_5d0()), "#C");
                  }

                  if (var5) {
                     this.var_1a7.sub_8f(var4, false);
                     this.var_351 = true;
                  } else {
                     this.var_1a7.sub_8f(var4, true);
                     this.var_325 = true;
                  }
                  break;
               case 1:
                  this.var_573 = SymbolMapManager_.sub_3b2(GameStatus.langManager.getLangString(479 + GameStatus.random.nextInt(4)), GameStatus.screenWidth - this.var_53c - this.var_4c5);
                  this.var_2a5 = 3;
                  this.var_5ce = 0;
                  ++Status.missionsRejected;
                  break;
               case 2:
                  this.var_2a5 = 1;
                  this.var_573 = SymbolMapManager_.sub_3b2(this.var_436[this.var_3d2].getMessage(), GameStatus.screenWidth - this.var_53c - this.var_4c5);
                  this.var_5ce = 0;
                  ++Status.askedToRepeate;
                  break;
               case 3:
                  if (this.var_436[this.var_3d2].sub_1b1() != 2 && this.var_436[this.var_3d2].sub_1b1() != 3) {
                     this.var_436[this.var_3d2].var_9cd = true;
                     if (this.var_436[this.var_3d2].sub_4f9().setCampaignMission() == Status.getStation().getId()) {
                        this.var_573 = SymbolMapManager_.sub_3b2(GameStatus.langManager.getLangString(441), GameStatus.screenWidth - this.var_53c - this.var_4c5);
                        this.var_2a5 = 1;
                        this.var_5ce = 0;
                     } else {
                        this.var_50 = ((InsideStation)GameStatus.scenes[1]).var_85d;
                        if (this.var_50 == null) {
                           ((InsideStation)GameStatus.scenes[1]).var_85d = new StarMap(true, this.var_436[this.var_3d2].sub_4f9(), false, -1);
                           this.var_50 = ((InsideStation)GameStatus.scenes[1]).var_85d;
                        } else {
                           this.var_50.sub_4e(true, this.var_436[this.var_3d2].sub_4f9(), false, -1);
                        }

                        this.var_628 = true;
                     }
                  } else {
                     if (this.var_1c0 == null) {
                        this.var_1c0 = new ShipPreview();
                        this.var_1e0 = AEImage.loadCryptedImage("/data/interface/items.png");
                        this.var_22a = AEImage.loadCryptedImage("/data/interface/item_types.png");
                     }

                     if (this.var_436[this.var_3d2].sub_1b1() == 2) {
                        this.var_1c0.sub_5c(new ListEntry(IndexManager.getItems()[this.var_436[this.var_3d2].sub_773()]), this.var_1e0, this.var_22a, (Image)null, (Image)null, false);
                     } else {
                        for(var3 = 0; var3 < Status.getBluePrints().length; ++var3) {
                           if (Status.getBluePrints()[var3].getIndex() == this.var_436[this.var_3d2].sub_582()) {
                              this.var_1c0.sub_5c(new ListEntry(IndexManager.getItems()[Status.getBluePrints()[var3].getIndex()]), this.var_1e0, this.var_22a, (Image)null, (Image)null, false);
                              break;
                           }
                        }
                     }

                     this.var_35f = true;
                  }
                  break;
               case 4:
                  this.var_436[this.var_3d2].var_99e = true;
                  var3 = (int)((float)this.var_436[this.var_3d2].sub_4f9().getDifficulty() / 10.0F * 5.0F);
                  this.var_573 = SymbolMapManager_.sub_3b2(GameStatus.langManager.getLangString(var3 + 443), GameStatus.screenWidth - this.var_53c - this.var_4c5);
                  this.var_2a5 = 1;
                  this.var_5ce = 0;
               }

               if (this.var_436[this.var_3d2].sub_41f()) {
                  this.var_436[this.var_3d2].sub_3c2(1);
               }
            }
         }

         return true;
      }
   }

   public final void sub_107(int var1, int var2) {
      if (this.var_35f) {
         this.var_1c0.sub_17d();
         Layout.sub_3c6("", GameStatus.langManager.getLangString(65));
      } else if (this.var_628) {
         this.var_50.sub_101(var1, var2);
      } else {
         Layout.screenFillMenuBackground();
         GameStatus.graphics.setClip(1, 15, GameStatus.screenWidth - 1, GameStatus.screenHeight - 16);
         this.var_6d3.sub_b7(var2);
         this.var_731.sub_b7(var2);
         var2 = this.var_6d3.sub_135();
         int var3 = this.var_731.sub_135();
         GameStatus.renderer.getCamera().moveTo(var2, 500, var3);
         if (this.var_691 != null) {
            this.var_691.renderScene(var1);
         }

         GameStatus.graphics.setClip(0, 0, GameStatus.screenWidth, GameStatus.screenHeight);
         Layout.sub_2db(GameStatus.langManager.getLangString(218), false);
         Bar var5 = this;
         if (this.var_2a5 == 0) {
            this.var_46f = GameStatus.screenHeight - 16 - (this.var_436.length + 1) * SymbolMapManager_.sub_2c2() + 2;
         } else if (this.var_2a5 == 2) {
            if (this.var_436[this.var_3d2].sub_1b1() != 2 && this.var_436[this.var_3d2].sub_1b1() != 3) {
               if (this.var_436[this.var_3d2].sub_4f9() != null && this.var_436[this.var_3d2].sub_4f9().sub_453()) {
                  this.var_46f = GameStatus.screenHeight - 16 - 6 * SymbolMapManager_.sub_2c2() + 2;
               } else {
                  this.var_46f = GameStatus.screenHeight - 16 - 4 * SymbolMapManager_.sub_2c2() + 2;
               }
            } else {
               this.var_46f = GameStatus.screenHeight - 16 - 5 * SymbolMapManager_.sub_2c2() + 2;
            }
         } else {
            this.var_46f = GameStatus.screenHeight;
         }

         if (this.var_2a5 == 2) {
            Layout.drawFilledTitleBarWindow((String)null, 3, this.var_46f, GameStatus.screenWidth - 7, GameStatus.screenHeight - this.var_46f - 16 - 3);
         }

         if (this.var_2a5 != 0) {
            Layout.drawFilledTitleBarWindow((String)null, 3, 15, GameStatus.screenWidth - 7, Face_.faceHeight + 7);
            Face_.drawFace(this.var_660[this.var_3d2], 7, 19, 0);
         }

         if (this.var_2a5 == 0) {
            Layout.sub_3c6(GameStatus.langManager.getLangString(494), GameStatus.langManager.getLangString(65));
            this.var_773 = this.var_691.level.getShips()[this.var_3d2].var_25d.sub_22a(this.var_773);
            AEVector3D var10000 = this.var_773;
            var10000.x -= 100;
            var10000 = this.var_773;
            var10000.y += 500;
            GameStatus.renderer.getCamera().sub_fa(this.var_773);
            SymbolMapManager_.sub_102(!this.var_436[this.var_3d2].sub_547() && !this.var_436[this.var_3d2].sub_3f0() ? GameStatus.langManager.getLangString(229 + this.var_436[this.var_3d2].getRace()) : this.var_436[this.var_3d2].fullName, this.var_773.x, this.var_773.y, 2);
            if (this.var_436[this.var_3d2].sub_547()) {
               SymbolMapManager_.sub_102(this.var_436[this.var_3d2].sub_4f9() != null ? GameStatus.langManager.getLangString(179 + this.var_436[this.var_3d2].sub_4f9().getType()) : (this.var_436[this.var_3d2].sub_1b1() == 6 ? GameStatus.langManager.getLangString(146) : (this.var_436[this.var_3d2].sub_1b1() == 2 ? GameStatus.langManager.getLangString(145) : (this.var_436[this.var_3d2].sub_1b1() == 7 ? GameStatus.langManager.getLangString(514) : ""))), this.var_773.x, this.var_773.y + SymbolMapManager_.sub_2c2(), 1);
            }
         } else {
            var2 = 0;

            for(var3 = this.var_5ce; var3 < var5.var_5ce + 4 && var3 < var5.var_573.length; ++var3) {
               String var4 = var3 != var5.var_573.length - 1 && var3 == var5.var_5ce + 4 - 1 ? ".." : "";
               SymbolMapManager_.sub_102(var5.var_573[var3] + (Layout.sub_119() ? var4 : ""), var5.var_53c, var5.var_4df + var2++ * SymbolMapManager_.sub_2c2(), 1);
            }

            if (var5.var_2a5 != 1 && var5.var_2a5 != 3) {
               SymbolMapManager_.sub_102(GameStatus.langManager.getLangString(495), var5.var_4c5, var5.var_46f + 3 + 0 * SymbolMapManager_.sub_2c2(), var5.var_607 == 0 ? 2 : 1);
               SymbolMapManager_.sub_102(GameStatus.langManager.getLangString(496), var5.var_4c5, var5.var_46f + 3 + 1 * SymbolMapManager_.sub_2c2(), var5.var_607 == 1 ? 2 : 1);
               SymbolMapManager_.sub_102(GameStatus.langManager.getLangString(497), var5.var_4c5, var5.var_46f + 3 + 2 * SymbolMapManager_.sub_2c2(), var5.var_607 == 2 ? 2 : 1);
               if ((var3 = var5.var_436[var5.var_3d2].sub_1b1()) != 2 && var3 != 3) {
                  if (var3 == 0 && var5.var_436[var5.var_3d2].sub_4f9().sub_453()) {
                     SymbolMapManager_.sub_102(GameStatus.langManager.getLangString(440), var5.var_4c5, var5.var_46f + 3 + 3 * SymbolMapManager_.sub_2c2(), var5.var_607 == 3 ? 2 : 1);
                     SymbolMapManager_.sub_102(GameStatus.langManager.getLangString(442), var5.var_4c5, var5.var_46f + 3 + 4 * SymbolMapManager_.sub_2c2(), var5.var_607 == 4 ? 2 : 1);
                  }
               } else {
                  SymbolMapManager_.sub_102(GameStatus.langManager.getLangString(415), var5.var_4c5, var5.var_46f + 3 + 3 * SymbolMapManager_.sub_2c2(), var5.var_607 == 3 ? 2 : 1);
               }

               Layout.sub_3c6(GameStatus.langManager.getLangString(253), "");
            } else {
               Layout.sub_3c6(GameStatus.langManager.getLangString(73), var5.var_5ce > 0 ? GameStatus.langManager.getLangString(74) : (var5.var_2a5 == 1 ? GameStatus.langManager.getLangString(246) : ""));
            }
         }

         if (this.var_325 || this.var_351) {
            this.var_1a7.sub_19a();
         }

      }
   }

   private void sub_169() {
      Agent var1;
      String var8;
      if (!(var1 = this.var_436[this.var_3d2]).sub_41f()) {
         var8 = null;
         if (var1.sub_35e() > 0) {
            var8 = GameStatus.langManager.getLangString(492);
         } else {
            if (!var1.isAccepted()) {
               ++Status.barInteractions;
               if (var1.sub_1b1() == 4) {
                  var8 = GameStatus.langManager.getLangString(505 + GameStatus.random.nextInt(2));
                  var8 = var8 + " " + GameStatus.langManager.getLangString(516 + var1.sub_112());
                  var8 = var8 + " " + GameStatus.langManager.getLangString(508);
                  new FileRead();
                  SolarSystem[] var11 = FileRead.loadSystemsBinary();
                  var8 = Status.replaceTokens(Status.replaceTokens(var8, var11[var1.sub_569()].getName(), "#S"), Layout.formatCredits(var1.sub_7d5()), "#C");
               } else {
                  var8 = GameStatus.langManager.getLangString(505 + GameStatus.random.nextInt(2));
                  var8 = var8 + " " + GameStatus.langManager.getLangString(516 + var1.sub_112());
                  var8 = Status.replaceTokens(Status.replaceTokens(var8 + " " + GameStatus.langManager.getLangString(507), GameStatus.langManager.getLangString(569 + IndexManager.getItems()[var1.sub_582()].getIndex()), "#N"), Layout.formatCredits(var1.sub_7d5()), "#C");
               }

               var8 = var8 + "\n" + GameStatus.langManager.getLangString(475 + GameStatus.random.nextInt(3));
               this.var_573 = SymbolMapManager_.sub_3b2(var8, GameStatus.screenWidth - this.var_53c - this.var_4c5);
               this.var_436[this.var_3d2].setMessage(var8);
               this.var_5ce = 0;
               this.var_2a5 = 1;
               return;
            }

            var8 = GameStatus.langManager.getLangString(492);
         }

         this.var_573 = SymbolMapManager_.sub_3b2(var8, GameStatus.screenWidth - this.var_53c - this.var_4c5);
         this.var_5ce = 0;
         this.var_2a5 = 3;
      } else {
         if (var1.sub_547() && var1.sub_1b1() != 7) {
            if (var1.isAccepted()) {
               var8 = GameStatus.langManager.getLangString(var1.sub_1b1() == 5 ? 491 : (var1.sub_1b1() != 6 && (var1.sub_4f9() == null || var1.sub_4f9().getType() != 12) ? 492 : 493));
               this.var_573 = SymbolMapManager_.sub_3b2(var8, GameStatus.screenWidth - this.var_53c - this.var_4c5);
               this.var_5ce = 0;
               this.var_2a5 = 3;
               return;
            }

            this.var_573 = SymbolMapManager_.sub_3b2(var1.getMessage(), GameStatus.screenWidth - this.var_53c - this.var_4c5);
         } else {
            ++Status.barInteractions;
            int var2;
            boolean var3 = (var2 = var1.sub_1b1()) != 1 && var2 != 7;
            boolean var4 = var2 == 0 || var2 == 5;
            var8 = var3 ? GameStatus.langManager.getLangString(390 + GameStatus.random.nextInt(6)) : "";
            String var9 = var3 ? GameStatus.langManager.getLangString(396 + GameStatus.random.nextInt(2)) : "";
            String var10 = var4 ? GameStatus.langManager.getLangString(398 + GameStatus.random.nextInt(6)) : "";
            String var5 = "";
            var9 = Status.replaceTokens(var9, var1.getFullName(), "#N");
            Generator var6 = new Generator();
            int var7;
            switch(var1.sub_1b1()) {
            case 0:
               var5 = var5 + GameStatus.langManager.getLangString(425 + var1.sub_4f9().getType());
               if (var1.sub_4f9().getType() == 5 || var1.sub_4f9().getType() == 3) {
                  var5 = var5 + " " + GameStatus.langManager.getLangString(438);
               }

               var5 = Status.replaceTokens(Status.replaceTokens(Status.replaceTokens(Status.replaceTokens(var5, GameStatus.langManager.getLangString(448 + var1.sub_4f9().getCommodityIndex()), "#P"), "" + var1.sub_4f9().getCommodityAmount(), "#Q"), var1.sub_4f9().getTargetStationName(), "#S"), var1.sub_4f9().getTargetName(), "#N");
               var5 = Status.replaceTokens(var5 + "\n" + GameStatus.langManager.getLangString(404 + GameStatus.random.nextInt(3)), Layout.formatCredits(var1.sub_4f9().getReward()), "#C");
               break;
            case 1:
               boolean var15 = false;
               boolean var16 = false;

               do {
                  var7 = GameStatus.random.nextInt(20);
                  if (!this.var_7b1[var7]) {
                     this.var_7b1[var7] = true;
                     var16 = true;
                  }
               } while(!var16);

               if (var1.getRace() != 0 && var7 == 16) {
                  var7 = 4;
               }

               var5 = Status.replaceTokens(Status.replaceTokens(Status.replaceTokens(GameStatus.langManager.getLangString(var7 + 455), IndexManager.sub_139(), "#S"), var1.getFullName(), "#N"), GameStatus.langManager.getLangString(723 + GameStatus.random.nextInt(10)), "#ORE");
               break;
            case 2:
               var5 = var5 + GameStatus.langManager.getLangString(407 + GameStatus.random.nextInt(4));
               var5 = Status.replaceTokens(Status.replaceTokens(Status.replaceTokens(var5 + "\n" + GameStatus.langManager.getLangString(412 + GameStatus.random.nextInt(2)), "" + var1.sub_7ae(), "#Q"), GameStatus.langManager.getLangString(569 + var1.sub_773()), "#P"), Layout.formatCredits(var1.sub_7d5()), "#C");
               if (var1.sub_7ae() > 1) {
                  var5 = Status.replaceTokens(var5 + " " + GameStatus.langManager.getLangString(414), Layout.formatCredits(var1.sub_7d5() / var1.sub_7ae()), "#C");
               }
            case 3:
            case 4:
            default:
               break;
            case 5:
               var1.sub_4e1(var6.createFreelanceMission(var1));
               var5 = Status.replaceTokens(Status.replaceTokens(Status.replaceTokens(var5 + GameStatus.langManager.getLangString(416 + GameStatus.random.nextInt(2)), "" + var1.sub_4f9().getCommodityAmount(), "#Q"), GameStatus.langManager.getLangString(569 + var1.sub_4f9().getCommodityIndex()), "#P"), Layout.formatCredits(var1.sub_4f9().getReward()), "#C");
               break;
            case 6:
               var5 = Status.replaceTokens(var5 + GameStatus.langManager.getLangString((Medals.gotAllMedals() ? 421 : 418) + var1.sub_634()), Layout.formatCredits(var1.sub_5d0()), "#C");
               if (var1.sub_634() > 0) {
                  var5 = Status.replaceTokens(var5, "" + var1.sub_689(1), "#W");
               }
               break;
            case 7:
               int var13 = var1.getRace();
               int var12 = Status.getStanding().getStanding(var13 != 2 && var13 != 3 ? 0 : 1);
               if (!Status.getStanding().isEnemy(var13)) {
                  var5 = GameStatus.langManager.getLangString(513);
                  this.var_573 = SymbolMapManager_.sub_3b2(var5, GameStatus.screenWidth - this.var_53c - this.var_4c5);
                  this.var_5ce = 0;
                  this.var_2a5 = 3;
                  return;
               }

               var7 = (int)((float)AEMath.abs(var12) / 100.0F * 16000.0F);
               var12 = var13 == 2 ? 509 : (var13 == 3 ? 510 : (var13 == 0 ? 511 : 512));
               var5 = Status.replaceTokens(GameStatus.langManager.getLangString(var12), Layout.formatCredits(var7), "#C");
               var1.sub_5c4(var7);
            }

            var1.setMessage(var5);
            if (var1.sub_1b1() == 1) {
               this.var_573 = SymbolMapManager_.sub_3b2(var5, GameStatus.screenWidth - this.var_53c - this.var_4c5);
               var1.sub_3c2(1);
            } else if (var1.sub_1b1() == 7) {
               this.var_573 = SymbolMapManager_.sub_3b2(var5, GameStatus.screenWidth - this.var_53c - this.var_4c5);
            } else {
               String var14;
               if (var1.sub_4f9() != null && var1.sub_4f9().getType() == 12) {
                  var5 = Status.replaceTokens(GameStatus.langManager.getLangString(437), Layout.formatCredits(var1.sub_4f9().getReward()), "#C");
                  var1.setMessage(var5);
                  var14 = "\n" + GameStatus.langManager.getLangString(475 + GameStatus.random.nextInt(3));
                  this.var_573 = SymbolMapManager_.sub_3b2(var5 + var14, GameStatus.screenWidth - this.var_53c - this.var_4c5);
               } else {
                  var5 = "\n" + var5;
                  var14 = "\n" + GameStatus.langManager.getLangString(475 + GameStatus.random.nextInt(3));
                  this.var_573 = SymbolMapManager_.sub_3b2(var8 + " " + var9 + " " + var10 + var5 + var14, GameStatus.screenWidth - this.var_53c - this.var_4c5);
               }
            }
         }

         var1.setAgentsStationName(Status.getStation().getName());
         var1.setAgentsSystemName(Status.getSystem().getName());
         this.var_2a5 = 1;
         this.var_5ce = 0;
      }
   }

   public final boolean sub_1b2() {
      return this.var_637;
   }

   public final void sub_1db(boolean var1) {
      this.var_637 = false;
   }
}
