package ch.philnet.playground.fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import ch.philnet.foodlocation.FLHandler;
import ch.philnet.foodlocation.FoodLocation;
import ch.philnet.playground.FoodLocationsAdapter;
import ch.philnet.playground.MainActivity;
import ch.philnet.playground.R;

public class FoodLocationsFragment extends ListFragment {
    //region Class & instantiating
    private FLHandler flHandler;

    public void setArguments(String _loc, MainActivity _act) {
        flHandler = new FLHandler(_loc, _act);
    }
    //endregion

    //region Instantiating list adapter
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //java.util.Collections.sort(displayLocs);
        FoodLocationsAdapter adapter = new FoodLocationsAdapter(getActivity(), R.layout.listviewitem, flHandler.getFoodLocationList(), flHandler);
        setListAdapter(adapter);
    }
    //endregion

    //region Handle list item clicks
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        ImageView ivFavorite = (ImageView) v.findViewById(R.id.liFavorite);
        FoodLocation foodLocation = flHandler.getFoodLocationList().get(position);
        boolean favoriteState = foodLocation.getFavorite();
        try {
            if(favoriteState) {
                ivFavorite.setImageResource(R.drawable.ic_star_border_accent_24dp);
            } else {
                ivFavorite.setImageResource(R.drawable.ic_star_accent_24dp);
            }
            foodLocation.setFavorite(!favoriteState);
            //json.WriteJSON(new FileOutputStream(fileFL), foodLocationList);
        } catch (Exception e) {
            /*Dialog.Show(act, "Kritischer Fehler", e.getMessage());
            Object test = json.getWriter();
            Dialog.Show(act, "Kritischer Fehler", json.getWriter().toString());*/
        }
    }
    //endregion
}
