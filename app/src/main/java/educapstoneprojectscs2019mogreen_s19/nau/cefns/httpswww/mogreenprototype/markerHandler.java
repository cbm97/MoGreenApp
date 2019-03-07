package educapstoneprojectscs2019mogreen_s19.nau.cefns.httpswww.mogreenprototype;

import android.graphics.Bitmap;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.GeoPoint;
import com.google.type.LatLng;

public class markerHandler {
    Bitmap icon;
    GoogleMap mMap;
    public GeoPoint point;
    Marker thisMarker;
    Boolean markerFlag = true;


    public void markerHandler(){

    }

    public void markerHandler(GeoPoint point){
        this.point = point;
    }

    public void setPoint(GeoPoint point){
        this.point = point;

    }



    public void initiate(GoogleMap map){
        mMap = map;
        double latitude = point.getLatitude();;
        double longitude= point.getLongitude();;
        //LatLng loca;
        thisMarker = mMap.addMarker(new MarkerOptions().position(new com.google.android.gms.maps.model.LatLng(latitude, longitude))
                );

        //.icon(BitmapDescriptorFactory.fromResource(R.drawable.logo)
    }

    public void toggleHidden(){
        if(markerFlag){
            thisMarker.setVisible(false);
            markerFlag = true;
        }
        else{
            thisMarker.setVisible(true);
            markerFlag = false;
        }
    }

    public void setHidden(){
        thisMarker.setVisible(false);
    }

    public void setShown(){
        thisMarker.setVisible(true);
    }

    public void setImage(Bitmap image){
        icon = image;
    }


}
