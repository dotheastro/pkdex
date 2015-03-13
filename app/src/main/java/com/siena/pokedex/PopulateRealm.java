package com.siena.pokedex;

import android.database.Cursor;
import com.siena.pokedex.models.Pokemon;
import com.siena.pokedex.models.PokemonSpeciesName;
import com.siena.pokedex.models.PokemonType;
import com.siena.pokedex.models.TypeName;
import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by Siena Aguayo on 2/28/15.
 */
public class PopulateRealm {
  public static void addEverything(Realm realm, DataAdapter dataAdapter) {
    addPokemonData(realm, dataAdapter);
    addTypeData(realm, dataAdapter);
    addTypeNames(realm, dataAdapter);
    addSpeciesNames(realm, dataAdapter);
  }

  public static void addSpeciesNames(Realm realm, DataAdapter dataAdapter) {
    Cursor cursor = dataAdapter.getData(
        "SELECT pokemon_species_id, local_language_id, name, genus FROM pokemon_species_names");
    cursor.moveToFirst();
    for (int i = 0; i < cursor.getCount(); i++) {
      realm.beginTransaction();
      PokemonSpeciesName pokemonSpeciesName = realm.createObject(PokemonSpeciesName.class);
      pokemonSpeciesName.setPokemonSpeciesId(cursor.getInt(0));
      pokemonSpeciesName.setLocalLanguageId(cursor.getInt(1));
      pokemonSpeciesName.setName(cursor.getString(2) != null ? cursor.getString(2) : "");
      String genus = cursor.getString(3);
      if (genus != null) {
        pokemonSpeciesName.setGenus(genus);
      } else {
        pokemonSpeciesName.setGenus("");
      }
      realm.commitTransaction();
      cursor.moveToNext();
    }
    cursor.close();
  }

  public static void addTypeNames(Realm realm, DataAdapter dataAdapter) {
    Cursor cursor = dataAdapter.getData("SELECT type_id, local_language_id, name FROM type_names");
    cursor.moveToFirst();
    for (int i = 0; i < cursor.getCount(); i++) {
      realm.beginTransaction();
      TypeName typeName = realm.createObject(TypeName.class);
      typeName.setTypeId(cursor.getInt(0));
      typeName.setLocalLanguageId(cursor.getInt(1));
      typeName.setName(cursor.getString(2));
      realm.commitTransaction();
      cursor.moveToNext();
    }
    cursor.close();
  }

  public static void addTypeData(Realm realm, DataAdapter dataAdapter) {
    Cursor cursor = dataAdapter.getData("SELECT pokemon_id, type_id, slot FROM pokemon_types");
    cursor.moveToFirst();
    for (int i = 0; i < cursor.getCount(); i++) {
      realm.beginTransaction();
      PokemonType type = realm.createObject(PokemonType.class);
      type.setPokemonId(cursor.getInt(0));
      type.setTypeId(cursor.getInt(1));
      type.setSlot(cursor.getInt(2));
      realm.commitTransaction();

      Pokemon pokemon = realm.where(Pokemon.class).equalTo("id", type.getPokemonId()).findFirst();

      if (pokemon != null) {
        if (pokemon.getTypes() == null) {
          pokemon.setTypes(new RealmList<PokemonType>());
        }
        pokemon.getTypes().add(type);
      }

      cursor.moveToNext();
    }
    cursor.close();
  }

  public static void addPokemonData(Realm realm, DataAdapter dataAdapter) {
    final Cursor testdata = dataAdapter.getAllPokemonData();
    for (int i = 0; i < 719; i++) {
      realm.executeTransaction(new Realm.Transaction() {
        @Override
        public void execute(Realm realm) {
          Pokemon poke = realm.createObject(Pokemon.class);
          //_id|identifier|species_id|height|weight|base_experience|order|is_default
          poke.setId(testdata.getInt(0));
          poke.setIdentifier(testdata.getString(1));
          poke.setSpeciesId(testdata.getInt(2));
          poke.setHeight(testdata.getInt(3));
          poke.setWeight(testdata.getInt(4));
          poke.setBaseExperience(testdata.getInt(5));
          poke.setOrder(testdata.getInt(6));
          poke.setDefault(testdata.getInt(7) == 1);
        }
      });

      testdata.moveToNext();
    }
  }
}
