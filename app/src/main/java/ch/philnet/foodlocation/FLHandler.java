package ch.philnet.foodlocation;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import ch.philnet.android.JSON;
import ch.philnet.android.UI;
import ch.philnet.android.ui.Snackbar;
import ch.philnet.playground.MainActivity;

public class FLHandler {
    private String pickedLocId;
    private List<FoodLocation> foodLocationList;
    private JSON json;
    private MainActivity act;
    private File foodLocations;

    public FLHandler(String _loc, MainActivity _act) {
        pickedLocId = _loc;
        act = _act;
        json = new JSON();
        foodLocationList = new ArrayList<>();

        //Create/open file from path
        File dirFL = new File(Environment.getExternalStorageDirectory() + "/Android/data/ch.philnet.playground/files/");
        dirFL.mkdirs();
        foodLocations = new File(dirFL, act.foodLocations);
    }

    private void FLDefaultList() {
        foodLocationList.add(new FoodLocation("Migros", "Menu B & Restaurant", "ZH", true));
        foodLocationList.add(new FoodLocation("Millenium", "Döner", "ZH", false));
        foodLocationList.add(new FoodLocation("Burger Meister", "Burger & Pommes", "ZH", false));
        foodLocationList.add(new FoodLocation("Burger King", "Burger & Pommes", "ZH", false));
        foodLocationList.add(new FoodLocation("Migros", "Take-Away & Restaurant", "WI", true));
        foodLocationList.add(new FoodLocation("SubWay", "Sandwiches", "WI", true));
        foodLocationList.add(new FoodLocation("Mac Donalds", "Burger & Pommes", "WI", false));
        foodLocationList.add(new FoodLocation("Burger King", "Burger & Pommes", "WI", false));
    }

    public List<FoodLocation> getFoodLocationList() {
        foodLocationList = new ArrayList<>();
        try {
            foodLocationList = json.ReadJSON(new FileInputStream(foodLocations));
        } catch (FileNotFoundException fnfe) {
            FLDefaultList();
        } catch (Exception e) {
            Snackbar.Show(act, e.getMessage());
        }
        return foodLocationList;
    }
    
    public String getPickedLocId() {
        return pickedLocId;
    }

    public void toggleFLState(FoodLocation _foodLocation) {
        boolean favoriteState = _foodLocation.getFavorite();
        try {
            _foodLocation.setFavorite(!favoriteState);
            //json.WriteJSON(new FileOutputStream(foodLocationsZH), foodLocationList);
        } catch (Exception e) {
            UI.ShowDialog(act, "Kritischer Fehler", e.getMessage());
        }
    }
}
