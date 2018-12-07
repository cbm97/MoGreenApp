package educapstoneprojectscs2019mogreen_s19.nau.cefns.httpswww.mogreenprototype;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class Map_Menu extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_menu);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


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
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Add a marker in Sydney and move the camera
        LatLng flagstaff = new LatLng(35.19, -111.65);
        mMap.addMarker(new MarkerOptions().position(flagstaff));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(flagstaff));
        PolygonOptions squares = new PolygonOptions();
            squares.add(new LatLng(35.178345, -111.656999),
                    new LatLng(35.178309, -111.656157),
                    new LatLng(35.177801, -111.656087),
                    new LatLng(35.177748, -111.656956),
                    new LatLng(35.178345, -111.656999));
                    squares.strokeColor(Color.GREEN);
                    squares.fillColor(Color.GREEN);

        Polygon polyline = mMap.addPolygon(squares);
        polyline.setClickable(true);
        googleMap.setOnPolygonClickListener(new GoogleMap.OnPolygonClickListener() {
            public void onPolygonClick(Polygon polygon) {
                startActivity(new Intent(Map_Menu.this, Pop.class));

            }
        });
    }

    public void addShape(LatLng point1,LatLng point2, LatLng point3, LatLng point4 ){
        PolygonOptions squares = new PolygonOptions();
            squares.add(point1);
            squares.add(point2);
            squares.add(point3);
            squares.add(point4);
            squares.add(point1);

    }

    public LatLng pointCreate(float lat, float longa){
        LatLng tempo = new LatLng(lat, longa);
        return tempo;
    }


}



