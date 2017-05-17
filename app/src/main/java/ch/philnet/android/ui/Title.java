package ch.philnet.android.ui;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class Title {

    private Activity act;
    private ArrayList<String> oldTitles;

    public Title(Activity _act) {
        act = _act;
        oldTitles = new ArrayList<String>();
    }

    public void setTitle(String newTitle) {
        oldTitles.add(act.getTitle().toString());
        act.setTitle(newTitle);
    }

    public boolean reverseTitle() {
        if(oldTitles.size() > 0) {
            int iMaxIndex = oldTitles.size() - 1;
            act.setTitle(oldTitles.get(iMaxIndex));
            oldTitles.remove(iMaxIndex);
            return true;
        } else {
            return false;
        }
    }

}
