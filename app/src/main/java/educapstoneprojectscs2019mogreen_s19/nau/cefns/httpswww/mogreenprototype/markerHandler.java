package educapstoneprojectscs2019mogreen_s19.nau.cefns.httpswww.mogreenprototype;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.GeoPoint;

public class markerHandler {
    BitmapDescriptor icon;
    GoogleMap mMap;
    public GeoPoint point;
    public String markerType;
    Marker thisMarker;
    Boolean markerFlag = true;
    Context context;


    public void markerHandler(){

    }

    public void markerHandler(GeoPoint point, String markerType)
    {
        this.point = point;
        this.markerType = markerType;
    }

    public void setPoint(GeoPoint point){
        this.point = point;

    }

    public String getType(){
        return this.markerType;
    }



    public void initiate(GoogleMap map){
        mMap = map;
        double latitude = point.getLatitude();;
        double longitude= point.getLongitude();;


        thisMarker = mMap.addMarker(new MarkerOptions().position(new com.google.android.gms.maps.model.LatLng(latitude, longitude))
                .icon(icon));

        //
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

    public void setImage(BitmapDescriptor image){
        icon = image;
    }

    public void setConext(Context context){
        this.context = context;
    }





}


