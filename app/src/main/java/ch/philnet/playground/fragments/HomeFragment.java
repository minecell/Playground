package ch.philnet.playground.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.philnet.android.ui.Snackbar;
import ch.philnet.playground.R;

public class HomeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.homefragment, container, false);
    }

    public void frtest(View v) {
        Snackbar.Show((Activity) v.getContext(), "frtest()");
    }
}
