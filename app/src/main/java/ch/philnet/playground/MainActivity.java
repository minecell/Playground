package ch.philnet.playground;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Set;

import ch.philnet.android.Permission;
import ch.philnet.android.UI;
import ch.philnet.android.ui.ChangeFragment;
import ch.philnet.android.ui.Snackbar;
import ch.philnet.android.ui.Title;
import ch.philnet.playground.fragments.HomeFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ChangeFragment changeFragment;
    private NotificationCompat.Builder mBuilder;
    public static NavigationView navigationView;

    public final String foodLocations = "FoodLocations.json";
    public static FloatingActionButton fab;
    public static Title title;
    public static Permission permission;
    public static BluetoothAdapter bluetoothAdapter;
    public int bluetoothRequestCode;
    private BroadcastReceiver bluetoothReceiver;

    private final String padding = "\n\n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //region Init activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitylayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final Activity act = this;
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mBuilder = new NotificationCompat.Builder(this);

        //Bluetooth receiver
        bluetoothReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                Log.d("Bluetooth Test-Modul", String.format("%sParing-bereites Gerät gefunden.%s", padding, padding));
                String action = intent.getAction();
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    Log.d("Bluetooth Test-Modul", String.format("%sParing-bereites Gerät: %s @ %s%s", padding, device.getName(), device.getAddress(), padding));
                }
            }
        };
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(bluetoothReceiver, filter);


        //endregion

        //Instantiate custom classes
        title = new Title(this);
        changeFragment = new ChangeFragment(this);
        permission = new Permission(this);

        setTitle("Start");
        getFragmentManager().beginTransaction().replace(R.id.blank, new HomeFragment()).addToBackStack("").commit();
    }

    @Override
    public void onStart() {
        super.onStart();
        MenuItem miVersion = navigationView.getMenu().findItem(R.id.nVersion);
        String versionCode = "Fehler";
        try {
            versionCode = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (Exception e) {}
        miVersion.setTitle(String.format("Version: %s", versionCode));
        getSupportActionBar().setSubtitle(String.format("Playground %s", versionCode));

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Snackbar.Show(this, "Dieses Gerät unterstützt kein Bluetooth!");
        }
    }

    //region Handle navdrawer and back button clicks
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(title.reverseTitle())
                getFragmentManager().popBackStack();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.empty, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nHome: changeFragment.ChangeScreen("Start", ChangeFragment.Screens.Home); break;
            case R.id.nFLZurich:
                permission.ShowStoragePermissionRequest();
                if(permission.permissionsGranted)
                    changeFragment.ChangeScreen("Locations in Zürich", ChangeFragment.Screens.FLZurich); break;

            case R.id.nFLWinterthur:
                permission.ShowStoragePermissionRequest();
                if(permission.permissionsGranted)
                    changeFragment.ChangeScreen("Locations in Winterthur", ChangeFragment.Screens.FLWinterthur); break;
            case R.id.nSettings: changeFragment.ChangeScreen("Einstellungen", ChangeFragment.Screens.Settings); break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        permission.HandleStoragePermissionRequest(requestCode, permissions, grantResults);
    }
    //endregion

    //region onClick-Methods
    public void btnShare(View v) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "Ich empfehle dir, die App Android Playground mal anzusehen! Grüsse\n_Geteilt via *Android Playground*_");
        intent.setPackage("com.whatsapp");
        startActivity(intent);

    }

    public void btnBluetoothEnable(View v) {
        //Request Bluetooth activation, if diabled
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, bluetoothRequestCode);
        }

        //If Bluetooth was enabled, output a List of paired devices
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            String Paired = "Die folgenden Geräte sind mit diesem Smartphone gepairt:\n[Gerätename] \u2022 [MAC-Adresse]\n";
            for (BluetoothDevice device : pairedDevices) {
                Paired += String.format("%s \u2022 %s\n", device.getName(), device.getAddress());
            }
            UI.ShowDialog(this, "Aktivierung erfolgreich", Paired);
        }
    }

    public void btnBluetoothPair(View v) {
        Intent bluetoothSettings = new Intent();
        bluetoothSettings.setAction(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS);
        startActivity(bluetoothSettings);
    }

    public void btnFeedback(View v) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("message/rfc822");
        intent.setData(Uri.parse("mailto:apps@philnet.ch?subject=Feedback zu Android Playground"));
        Intent mailer = Intent.createChooser(intent, "E-Mail senden");
        startActivity(mailer);
    }

    public void btnReport(View v) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("message/rfc822");
        intent.setData(Uri.parse("mailto:apps@philnet.ch?subject=Fehler in Android Playground"));
        Intent mailer = Intent.createChooser(intent, "E-Mail senden");
        startActivity(mailer);
    }

    //endregion
}