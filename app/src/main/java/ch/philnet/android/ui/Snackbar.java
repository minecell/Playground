package ch.philnet.android.ui;

import android.app.Activity;
import ch.philnet.playground.R;

public class Snackbar {

    public static void Show(Activity act, String message) {
        android.support.design.widget.Snackbar sb = android.support.design.widget.Snackbar.make(
                act.findViewById(R.id.blank),
                message,
                android.support.design.widget.Snackbar.LENGTH_LONG);
        sb.show();
    }
}