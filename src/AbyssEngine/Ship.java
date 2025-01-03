package AbyssEngine;

public final class Ship {
   public static final short[] previewZoomOut = new short[]{622, 1338, 1066, 783, 1181, 912, 939, 1471, 1199, 1192, 763, 1633, 1103, 2000, 2000, 2000, 1802, 1930, 1590, 1205, 1024, 1623, 1266, 1038, 1370, 1370, 1110, 1276, 1722, 1553, 975, 1175, 627, 1185, 916, 767, 1738};
   public static final short[] previewPivotShift = new short[]{24, -51, -37, 78, -197, 8, -14, 37, -104, 121, -100, -381, -132, 0, 0, 0, -520, -137, -206, 0, -182, -347, 2, -61, -116, -254, -316, -40, -182, -372, -68, -160, -142, 131, -102, -72, -402};
   private int id;
   private int baseHP;
   private int basePrice;
   private int baseCargo;
   private int cargo;
   private int price;
   private int[] itemTypeSlots;
   private float handling;
   private int shield;
   private int additionalArmour;
   private int shieldRegenTime;
   private int extendedCargo;
   private int boostPercent;
   private int boostRegenTime;
   private int boostDuration;
   private int extendedHandlingPercent;
   private int var_84c;
   private int firePower;
   private boolean repairBot;
   private boolean jumpDrive;
   private boolean cloak;
   private int race;
   private Item[] equipped;
   private Item[] cargoHold;

   public Ship(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, float var9) {
      this.id = var1;
      this.baseHP = var2;
      this.baseCargo = var3;
      this.cargo = 0;
      this.price = var4;
      this.basePrice = var4;
      this.itemTypeSlots = new int[4];
      this.itemTypeSlots[0] = var5;
      this.itemTypeSlots[1] = var6;
      this.itemTypeSlots[2] = var7;
      this.itemTypeSlots[3] = var8;
      this.handling = var9 / 100.0F;
      this.equipped = new Item[var5 + var6 + var7 + var8];
      this.cargoHold = null;
      this.race = 0;
      this.refreshValue();
   }

   public final void setRace(int var1) {
      this.race = var1;
   }

   public final int getRace() {
      return this.race;
   }

   public final int getIndex() {
      return this.id;
   }

   public final int getPrice() {
      return this.id == 10 && Medals.gotAllMedals() ? '썐' : this.price;
   }

   public final float getHandling() {
      return this.handling;
   }

   public final int getBaseHP() {
      return this.baseHP;
   }

   public final int getCombinedHP() {
      return this.baseHP + this.shield + this.additionalArmour;
   }

   public final int getAdditionalArmour() {
      return this.additionalArmour;
   }

   public final int getBaseArmour() {
      return this.baseHP;
   }

   public final int getShield() {
      return this.shield;
   }

   public final int getShieldRegenTime() {
      return this.shieldRegenTime;
   }

   public final int getBoostPercent() {
      return this.boostPercent;
   }

   public final int getBoostDuration() {
      return this.boostDuration;
   }

   public final int getBoostRegenTime() {
      return this.boostRegenTime;
   }

   public final int getAddHandlingPercent() {
      return this.extendedHandlingPercent;
   }

   public final boolean hasBooster() {
      return this.boostPercent > 0;
   }

   public final boolean hasJumpDrive() {
      return this.jumpDrive;
   }

   public final boolean hasCloak() {
      return this.cloak;
   }

   public final boolean hasRepairBot() {
      return this.repairBot;
   }

   public final int getFirePower() {
      return this.firePower;
   }

   public final int getBaseLoad() {
      return this.baseCargo;
   }

   public final int getCargoPlus() {
      return this.baseCargo + this.extendedCargo;
   }

   public final int getCurrentLoad() {
      return this.cargo;
   }

   public final Item[] getEquipment() {
      return this.equipped;
   }

   public final Item[] getCargo() {
      return this.cargoHold;
   }

   public final void refreshCargoSpaceUnsafe(Item[] var1) {
      this.cargoHold = var1;
      this.cargo = 0;

      for(int var2 = 0; var2 < this.cargoHold.length; ++var2) {
         this.cargo += this.cargoHold[var2].getAmount();
      }

      this.refreshValue();
      if (Status.maxFreeCargo < this.getFreeCargo()) {
         Status.maxFreeCargo = this.getFreeCargo();
      }

   }

   public final Item getEquipedOfSubType(int var1) {
      for(int var2 = 0; var2 < this.equipped.length; ++var2) {
         if (this.equipped[var2] != null && this.equipped[var2].getSubType() == var1) {
            return this.equipped[var2];
         }
      }

      return null;
   }

   public final boolean isSimilarEquiped(int var1) {
      if (this.equipped == null) {
         return false;
      } else {
         for(int var2 = 0; var2 < this.equipped.length; ++var2) {
            if (this.equipped[var2] != null && this.equipped[var2].getIndex() == var1) {
               return true;
            }
         }

         return false;
      }
   }

   public final boolean sub_6fa() {
      if (this.cargoHold == null) {
         return false;
      } else {
         for(int var1 = 0; var1 < this.cargoHold.length; ++var1) {
            if (this.cargoHold[var1] != null && this.cargoHold[var1].getType() == this.id) {
               return true;
            }
         }

         return false;
      }
   }

   public final void sub_755(Item var1) {
      this.sub_7fc(var1.getIndex(), var1.getAmount());
   }

   public final void sub_79a() {
      this.cargoHold = null;
   }

   public final boolean sub_7a6(int var1) {
      return this.sub_7fc(131, 9999999);
   }

   public final boolean sub_7fc(int var1, int var2) {
      if (this.cargoHold == null) {
         return false;
      } else {
         boolean var3 = false;

         for(int var4 = 0; var4 < this.cargoHold.length; ++var4) {
            if (this.cargoHold[var4].getIndex() == var1) {
               this.cargoHold[var4].changeAmount(-var2);
               if (this.cargoHold[var4].getAmount() <= 0) {
                  var3 = true;
               }
               break;
            }
         }

         if (var3) {
            this.refreshCargoSpace(Item.sub_797(this.cargoHold, true));
         } else {
            this.refreshCargoSpace(this.cargoHold);
         }

         return var3;
      }
   }

   public final void refreshCargoSpace(Item[] var1) {
      this.cargoHold = var1;
      this.cargo = 0;
      if (var1 != null) {
         for(int var2 = 0; var2 < this.cargoHold.length; ++var2) {
            this.cargo += this.cargoHold[var2].getAmount();
         }
      }

      this.refreshValue();
      if (Status.maxFreeCargo < this.getFreeCargo()) {
         Status.maxFreeCargo = this.getFreeCargo();
      }

   }

   public final boolean sub_839(int var1) {
      return this.cargo + var1 <= this.baseCargo + this.extendedCargo;
   }

   public final int getFreeCargo() {
      return this.getCargoPlus() - this.cargo;
   }

   public final void addItem(Item var1) {
      Item[] var2 = new Item[]{var1};
      this.refreshCargoSpace(Item.merge(this.cargoHold, var2));
   }

   public final void setEquipment(Item[] var1) {
      this.equipped = var1;
      this.refreshValue();
   }

   public final void sub_908(Item var1) {
      if (this.equipped != null) {
         for(int var2 = 0; var2 < this.equipped.length; ++var2) {
            if (this.equipped[var2] != null && this.equipped[var2].equals(var1)) {
               this.equipped[var2] = null;
               return;
            }
         }

      }
   }

   public final Item[] getEquipment(int var1) {
      if (var1 < this.itemTypeSlots.length && this.itemTypeSlots[var1] != 0) {
         Item[] var2 = new Item[this.itemTypeSlots[var1]];
         int var3 = 0;

         int var4;
         for(var4 = 0; var4 < var1; ++var4) {
            var3 += this.itemTypeSlots[var4];
         }

         var4 = 0;

         for(int var5 = var3; var5 < var3 + this.itemTypeSlots[var1]; ++var5) {
            if (var4 < var2.length) {
               var2[var4++] = this.equipped[var5];
            } else {
               System.err.println("Ship.getEquipment() failed");
            }
         }

         return var2;
      } else {
         return null;
      }
   }

   public final void setEquipment(Item var1, int var2) {
      var2 = var2;

      for(int var3 = 0; var3 < var1.getType(); ++var3) {
         var2 += this.itemTypeSlots[var3];
      }

      if (var2 < this.equipped.length) {
         this.equipped[var2] = var1;
         this.refreshValue();
      } else {
         System.out.println("Ship.setEquipment() Array Index out of bounds");
      }
   }

   private void refreshValue() {
      this.repairBot = false;
      this.jumpDrive = false;
      this.cloak = false;
      this.shield = 0;
      this.shieldRegenTime = 0;
      this.extendedCargo = 0;
      this.boostRegenTime = 0;
      this.boostPercent = 0;
      this.boostDuration = 0;
      this.extendedHandlingPercent = 0;
      this.additionalArmour = 0;
      this.firePower = 0;
      this.var_84c = 0;
      this.basePrice = this.price;

      for(int var1 = 0; var1 < this.equipped.length; ++var1) {
         if (this.equipped[var1] != null) {
            switch(this.equipped[var1].getSubType()) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 8:
               this.firePower += this.equipped[var1].getAttribute(9);
            case 4:
            case 5:
            case 6:
            case 7:
            case 11:
            case 13:
            case 17:
            case 19:
            default:
               break;
            case 9:
               this.shield = this.equipped[var1].getAttribute(16);
               this.shieldRegenTime = this.equipped[var1].getAttribute(17);
               break;
            case 10:
               this.additionalArmour = this.equipped[var1].getAttribute(18);
               break;
            case 12:
               this.extendedCargo += this.equipped[var1].getAttribute(20);
               break;
            case 14:
               this.boostPercent = this.equipped[var1].getAttribute(23);
               this.boostDuration = this.equipped[var1].getAttribute(25);
               this.boostRegenTime = this.equipped[var1].getAttribute(24);
               break;
            case 15:
               this.repairBot = true;
               break;
            case 16:
               this.extendedHandlingPercent = this.equipped[var1].getAttribute(26);
               break;
            case 18:
               this.jumpDrive = true;
               break;
            case 20:
               this.var_84c += this.equipped[var1].getAttribute(32);
               break;
            case 21:
               this.cloak = true;
            }

            this.basePrice += this.equipped[var1].sub_2ed();
         }
      }

      this.extendedCargo = (int)((float)this.baseCargo * (float)this.extendedCargo / 100.0F);
      int var10001 = this.basePrice;
      Ship var4 = this;
      int var2 = 0;
      if (this.cargoHold != null) {
         for(int var3 = 0; var3 < var4.cargoHold.length; ++var3) {
            if (var4.cargoHold[var3] != null) {
               var2 += var4.cargoHold[var3].sub_2ed();
            }
         }
      }

      this.basePrice = var10001 + var2;
   }

   public final int sub_982() {
      return this.var_84c;
   }

   public final void sub_98c(Item var1, int var2) {
      for(int var3 = 0; var3 < this.equipped.length; ++var3) {
         if (this.equipped[var3] != null && this.equipped[var3].equals(var1) && var3 == var2) {
            this.equipped[var3] = null;
            break;
         }
      }

      this.refreshValue();
   }

   public final void sub_9be(Item var1) {
      for(int var2 = 0; var2 < this.equipped.length; ++var2) {
         if (this.equipped[var2] != null && this.equipped[var2].equals(var1)) {
            this.equipped[var2] = null;
            break;
         }
      }

      this.refreshValue();
   }

   public final int sub_9ca(int var1) {
      return this.itemTypeSlots[var1];
   }

   public final int sub_9e3() {
      int var1 = 0;

      for(int var2 = 0; var2 < this.itemTypeSlots.length; ++var2) {
         if (this.itemTypeSlots[var2] > 0) {
            ++var1;
         }
      }

      return var1;
   }

   public final int countEquippedOfType(int var1) {
      int var2 = 0;

      for(int var3 = 0; var3 < this.equipped.length; ++var3) {
         if (this.equipped[var3] != null && this.equipped[var3].getType() == var1) {
            ++var2;
         }
      }

      return var2;
   }

   public final Ship cloneBase() {
      return new Ship(this.id, this.baseHP, this.baseCargo, this.price, this.itemTypeSlots[0], this.itemTypeSlots[1], this.itemTypeSlots[2], this.itemTypeSlots[3], this.handling * 100.0F);
   }

   public final void setSellingPrice() {
      this.price = (int)((float)IndexManager.getShips()[this.id].getPrice() / 1.25F);
   }

   public final void adjustPrice() {
      if (Status.getStation() != null) {
         this.price = IndexManager.getShips()[this.id].getPrice() - (int)((float)Status.getStation().getTecLevel() / 100.0F * (float)IndexManager.getShips()[this.id].getPrice());
      }

   }
}
