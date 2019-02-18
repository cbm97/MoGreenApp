package educapstoneprojectscs2019mogreen_s19.nau.cefns.httpswww.mogreenprototype;

import android.graphics.Bitmap;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.type.LatLng;

public class markerHandler {
    Bitmap icon;
    GoogleMap mMap;

    public void markerHandler(Bitmap image, GoogleMap map){
        icon = image;
        mMap = map;
    }

    public void initiate(){

        //LatLng loca;
        mMap.addMarker(new MarkerOptions().position(new com.google.android.gms.maps.model.LatLng(35.174725, -111.660692))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.logo)));
    }


}
