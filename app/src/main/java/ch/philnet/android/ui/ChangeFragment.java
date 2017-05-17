package ch.philnet.android.ui;

import android.app.Fragment;
import android.app.FragmentManager;

import ch.philnet.playground.MainActivity;
import ch.philnet.playground.fragments.FoodLocationsFragment;
import ch.philnet.playground.R;
import ch.philnet.playground.fragments.HomeFragment;
import ch.philnet.playground.fragments.SettingsFragment;

public class ChangeFragment {

    private MainActivity act;
    private FragmentManager fragmentManager;
    public enum Screens {
        Home, Settings, FLZurich, FLWinterthur
    }

    public ChangeFragment(MainActivity ma) {
        act = ma;
        fragmentManager = act.getFragmentManager();
    }

    //Change the fragment
    private void ChangeScreenBase(Screens sc) {
        switch(sc) {
            case Home:
                ReplaceTransaction(new HomeFragment());

                break;
            case FLZurich:
                FoodLocationsFragment FLFragZ = new FoodLocationsFragment();
                FLFragZ.setArguments("ZH", act);
                ReplaceTransaction(FLFragZ);
                break;
            case FLWinterthur:
                FoodLocationsFragment FLFragW = new FoodLocationsFragment();
                FLFragW.setArguments("WI", act);
                ReplaceTransaction(FLFragW);
                break;
            case Settings:
                SettingsFragment settingsFragment = new SettingsFragment();
                settingsFragment.setArguments(act);
                ReplaceTransaction(settingsFragment);
                break;
            default:
                Snackbar.Show(act, "Diese Funktion steht demnächst zur Verfügung");
                break;
        }
    }

    //Change fragment & title
    public void ChangeScreen(String title, Screens sc) {
        act.title.setTitle(title);
        ChangeScreenBase(sc);
    }

    private void ReplaceTransaction(Fragment fragment) {
        fragmentManager.beginTransaction().replace(R.id.blank, fragment).addToBackStack("").commit();
    }
}
