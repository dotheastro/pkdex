package com.siena.pokedex.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.siena.pokedex.R;
import com.siena.pokedex.adapters.PokemonListAdapter;
import com.siena.pokedex.models.Pokemon;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Siena Aguayo on 12/27/14.
 */
public class PokeListFragment extends Fragment {
  public PokeListFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_pokemon_listview, container, false);
    Realm realm = Realm.getDefaultInstance();
    RealmResults<Pokemon> allPokemon = realm.allObjectsSorted(Pokemon.class, "id", Sort.ASCENDING);
    Context activityContext = getActivity();

    RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.poke_listview);
    recyclerView.setHasFixedSize(true);
    recyclerView.setAdapter(new PokemonListAdapter(activityContext, allPokemon));
    recyclerView.setLayoutManager(new LinearLayoutManager(activityContext));

    ((ActionBarActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    return rootView;
  }
}
