package ch.philnet.android;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import ch.philnet.playground.R;

public class Permission {

    private final int STORAGE_PERMISSION = 1;
    private Activity activity;

    public boolean permissionsGranted = false;

    public Permission(Activity act) {
        activity = act;
        boolean readGranted = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        boolean writeGranted = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if(readGranted && writeGranted) {
            permissionsGranted = true;
        }
    }

    public void ShowStoragePermissionRequest() {
        boolean readGranted = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        boolean writeGranted = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (readGranted == false || writeGranted == false) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder
                    .setMessage("Um Locations fürs Mittagessen anzeigen zu können, braucht Playground Zugriff auf deinen Gerätespeicher.\n" +
                    "Du kannst diese Berechtigung in den Einstellungen nachträglich (de-)aktivieren")
                    .setTitle("Speicherberechtigungen")
                    .setIcon(R.drawable.ic_perm_media_black_24dp)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION);
                }
            }).create().show();
        }
    }

    public void HandleStoragePermissionRequest(int requestCode, String permissions[], int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            permissionsGranted = true;
        } else {
            NavigationView navigationView = (NavigationView) activity.findViewById(R.id.nav_view);
            navigationView.getMenu().findItem(R.id.nFLZurich).setEnabled(false);
            navigationView.getMenu().findItem(R.id.nFLWinterthur).setEnabled(false);
            permissionsGranted = true;
        }
    }
}
