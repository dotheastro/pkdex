package com.siena.pokedex.models.persisted;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Siena Aguayo on 3/25/15.
 */
public class EncounterSlot extends RealmObject {
  // _id	version_group_id	encounter_method_id	slot	rarity
  @PrimaryKey private long id;
  private int versionGroupId, slot, rarity;
  private EncounterMethod encounterMethod;

  public EncounterSlot() {
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public int getVersionGroupId() {
    return versionGroupId;
  }

  public void setVersionGroupId(int versionGroupId) {
    this.versionGroupId = versionGroupId;
  }

  public int getSlot() {
    return slot;
  }

  public void setSlot(int slot) {
    this.slot = slot;
  }

  public int getRarity() {
    return rarity;
  }

  public void setRarity(int rarity) {
    this.rarity = rarity;
  }

  public EncounterMethod getEncounterMethod() {
    return encounterMethod;
  }

  public void setEncounterMethod(EncounterMethod encounterMethod) {
    this.encounterMethod = encounterMethod;
  }
}
