package educapstoneprojectscs2019mogreen_s19.nau.cefns.httpswww.mogreenprototype;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class Map_Menu extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener {
    int FINE_LOCATION_GRANTED;
    LatLng currentTap;
    DrawerLayout mDrawerLayout;
    NavigationView nv;
    FirebaseUser user;
    String personGivenName, personEmail;
    Uri personPhoto;
    LatLngBounds flagsLatlng;
    Intent full_report, quick_report;
    boolean settingsFlag = false;
    BitmapDescriptor greenM, blueM, battery;
    ArrayList<markerHandler> marker1Holder = new ArrayList<>();
    ArrayList<markerHandler> marker2Holder = new ArrayList<>();
    ArrayList<markerHandler> marker3Holder = new ArrayList<>();
    private Marker currentLoc;
    private GoogleMap mMap;
    private FirebaseFirestore db;


    //Method generateBitmapDescriptorFromRes used from
    // https://gist.github.com/EfeBudak/a9ec13d9d582c6cc7f11156e6775ea47#file-bitmaputils-java
    //as a workaround for using images in map markers.

    public static BitmapDescriptor generateBitmapDescriptorFromRes(
            Context context, int resId) {

        Drawable drawable = ContextCompat.getDrawable(context, resId);
        drawable.setBounds(
                0,
                0,
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_menu);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);




        //Create Bitmap from drawable resources (icons)


        blueM = generateBitmapDescriptorFromRes(this, R.drawable.bluerec30px);
        greenM = generateBitmapDescriptorFromRes(this, R.drawable.greenrec30px);
        battery = generateBitmapDescriptorFromRes(this, R.drawable.batterymarker30px);



        //Toolbar listener

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionbar.setDisplayShowTitleEnabled(false);




        nv = findViewById(R.id.nav_view);
        nv.getMenu().setGroupVisible(R.id.marker_settings,false);
        MenuItem itemSwitch1 = nv.getMenu().findItem(R.id.markerholder1);
        itemSwitch1.setActionView(R.layout.switch_marker1);
        MenuItem itemSwitch2 = nv.getMenu().findItem(R.id.markerholder2);
        itemSwitch2.setActionView(R.layout.switch_marker2);
        MenuItem itemSwitch3 = nv.getMenu().findItem(R.id.markerholder3);
        itemSwitch3.setActionView(R.layout.switch_marker3);
        final Switch marker1S = nv.getMenu().findItem(R.id.markerholder1).getActionView().findViewById(R.id.marker1);
        final Switch marker2S = nv.getMenu().findItem(R.id.markerholder2).getActionView().findViewById(R.id.marker2);
        final Switch marker3S = nv.getMenu().findItem(R.id.markerholder3).getActionView().findViewById(R.id.marker3);
        marker1S.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(marker1S.isChecked()) {
                    for (markerHandler i : marker1Holder) {
                        i.setShown();
                    }
                } else{
                    for(markerHandler i : marker1Holder){
                        i.setHidden();
                    }
                }

            }
        });
        marker2S.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(marker2S.isChecked()) {
                    for (markerHandler i : marker2Holder) {
                        i.setShown();
                    }
                }

                else{
                    for(markerHandler i : marker2Holder){
                        i.setHidden();
                    }
                }

            }
        });
        marker3S.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(marker3S.isChecked()) {
                    for (markerHandler i : marker3Holder) {
                        i.setShown();
                    }
                }

                else{
                    for(markerHandler i : marker3Holder){
                        i.setHidden();
                    }
                }

            }
        });






        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id) {
                    case R.id.Full_report:

                        full_report = new Intent(Map_Menu.this, Pop.class);
                        try {
                            full_report.putExtra("ZONE", currentTap.toString());
                            startActivity(full_report);
                        } catch (Exception e) {
                            Context context = getApplicationContext();
                            String msg = "Please tap a location on the map";
                            Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        mDrawerLayout.closeDrawers();

                        return true;


                    case R.id.Quick_Report:

                        quick_report = new Intent(Map_Menu.this, Quick_Report.class);

                        try{
                        quick_report.putExtra("ZONE", currentTap.toString());
                        startActivity(quick_report);

                        }catch(Exception e)
                        {
                            Context context = getApplicationContext();
                            String msg= "Please tap a location on the map";
                            Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        mDrawerLayout.closeDrawers();
                        return true;


                    case R.id.Settings:

                        if(settingsFlag)
                        {
                            nv.getMenu().setGroupVisible(R.id.marker_settings,false);
                            settingsFlag = false;
                        }

                        else
                            {
                                nv.getMenu().setGroupVisible(R.id.marker_settings,true);
                                settingsFlag = true;
                            }
                        return true;


                    case R.id.logout:

                        FirebaseAuth.getInstance().signOut();
                        Intent signOut = new Intent(getApplicationContext(), Login_Page.class);
                        startActivity(signOut);
                        return true;
                    case R.id.Help:
                        Intent help = new Intent(getApplicationContext(),help_page.class);
                        startActivity(help);
                        mDrawerLayout.closeDrawers();
                        return true;
                    default:
                        return true;
                }
            }
        });




        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Respond when the drawer is opened
                        user = FirebaseAuth.getInstance().getCurrentUser();
                        if (user != null) {


                            personGivenName = user.getDisplayName();
                            personEmail = user.getEmail();
                            personPhoto = user.getPhotoUrl();
                        }
                        TextView name = findViewById(R.id.userName);
                        TextView email = findViewById(R.id.userEmail);
                        ImageView userPic = findViewById(R.id.userImage);
                        try {
                            Picasso.get().load(personPhoto.toString()).into(userPic);
                        }catch(Exception e){
                            return;
                        }


                        name.setText(personGivenName);
                        email.setText(personEmail);
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );




    }

    public void onBackPressed() {

        return;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        UiSettings mUiSettings;
        mUiSettings = mMap.getUiSettings();
        //mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationButtonClickListener(this);


        //Adds bounds for map
        LatLng flag1 = new LatLng(35.174725, -111.660692);
        LatLng flag2 = new LatLng(35.193198,  -111.649685);
        flagsLatlng = new LatLngBounds(flag1, flag2);

        mUiSettings.setZoomControlsEnabled(true);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    FINE_LOCATION_GRANTED);
        }


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if(currentTap != null){
                    currentLoc.remove();
                }
                currentTap = latLng;
                currentLoc = mMap.addMarker(new MarkerOptions().position(currentTap).title("Report Spot"));
            }
        });






        //Applies Bounds, Modify padding to zoom out or in.
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(flagsLatlng,1500, 1500, 0));

        //Grabs polygons from database and places them.

        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
        db.collection("NAU_zones").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        zone Zone = document.toObject(zone.class);
                        Zone.create(mMap);
                    }
                }
            }
        });
        db.collection("NAU_markers").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        markerHandler marker1 = document.toObject(markerHandler.class);
                        String type = marker1.getType();
                        marker1.setConext(getBaseContext());
                        if(type.equals("GreenMarker"))
                        {
                            marker1.setImage(greenM);
                            marker1Holder.add(marker1);
                        }
                        else if(type.equals("BlueMarker"))
                        {
                            marker1.setImage(blueM);
                            marker2Holder.add(marker1);

                        }
                        else if(type.equals("BatteryMarker"))
                        {
                            marker1.setImage(battery);
                            marker3Holder.add(marker1);
                        }
                        marker1.initiate(mMap);
                        marker1.toggleHidden();


                    }
                }
            }
        });



        //Opens popup menu
        googleMap.setOnPolygonClickListener(new GoogleMap.OnPolygonClickListener() {
            public void onPolygonClick(Polygon polygon) {
                startActivity(new Intent(Map_Menu.this, Pop.class));

            }
        });




    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        if(currentTap != null){
            currentLoc.remove();
        }
        currentTap = new LatLng(location.getLatitude(), location.getLongitude());
        currentLoc = mMap.addMarker(new MarkerOptions().position(currentTap).title("Report Spot"));

    }

    @Override
    public boolean onMyLocationButtonClick() {

        if(currentTap != null){
            currentLoc.remove();
        }

        currentTap = new LatLng(mMap.getCameraPosition().target.latitude,
                mMap.getCameraPosition().target.longitude);
        currentLoc = mMap.addMarker(new MarkerOptions().position(currentTap).title("Report Spot"));

        return false;

    }



    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_loc_default:
                mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(flagsLatlng,1500, 1500, 0));
                return true;
            case R.id.action_refresh:
                finish();
                startActivity(getIntent());
                return true;
            case R.id.action_full_report:
                full_report = new Intent(Map_Menu.this, Pop.class);
                try {
                    full_report.putExtra("ZONE", currentTap.toString());
                    startActivity(full_report);
                }catch(Exception e){
                    Context context = getApplicationContext();
                    String msg= "Please tap a location on the map";
                    Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
                    toast.show();
                }
                return true;
            case R.id.action_quick_report:
                quick_report =  new Intent(Map_Menu.this, Quick_Report.class);
                try {
                    quick_report.putExtra("ZONE", currentTap.toString());
                    startActivity(quick_report);
                }catch(Exception e){
                    Context context = getApplicationContext();
                    String msg= "Please tap a location on the map";
                    Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
                    toast.show();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.map_toolbar, menu);




        return true;
    }

}
