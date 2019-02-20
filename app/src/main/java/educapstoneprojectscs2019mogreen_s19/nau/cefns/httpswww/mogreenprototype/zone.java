package educapstoneprojectscs2019mogreen_s19.nau.cefns.httpswww.mogreenprototype;


import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Keep;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.firebase.firestore.GeoPoint;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;


    /*
    Handles the individual zones including the ability to add points, change points,
    change colors and change the visibility of a zone.
    Public void zone : Initiates a zone from four LatLng objects and a GoogleMap map reference
        Default color is bright green
    Public void initiate : sets an onClick listener for the zone.
    Public void setColor : Allows the color to be manually changed by taking a rgb value.
        Parameters: int red , green & blue take an integer value between 0 and 255.
        Optional :
        int AlphaS & AlphaF : Change the alpha values for both the stroke color and the fill color
                              Defaulted at 70 for fill and 50 for stroke.
    Public void toggleListener: Flips the clickable status of a zone.
    Public void forceClickable : Forces the clickable status of a zone to either on or off bassd on
                                 the entered parameter
        Parameters : Boolean bool : forced status of the zone. False being inactive
                                    and True being Clickable
     */
@Keep
public class zone {
    public List<GeoPoint> points;
    public String color;
    PolygonOptions squares = new PolygonOptions();
    Polygon polyline;
    public int red, green, blue;
    private GoogleMap mapRef;
    boolean active = false;
    int alphaStroke = 50, alphaFill = 70;
    List<LatLng> latList = new ArrayList();



    public zone(){

    }

    public zone(String colora, List<GeoPoint> sauce){
        points = sauce;
        color = colora;
        red =  Integer.valueOf( colora.substring(1, 3), 16 );
        green =  Integer.valueOf( colora.substring(3, 5), 16 );
        blue =  Integer.valueOf( colora.substring(5, 7), 16 );


    }

    public void setColor(String colora){
        color = colora;
        red =  Integer.valueOf( colora.substring(1, 3), 16 );
        green =  Integer.valueOf( colora.substring(3, 5), 16 );
        blue =  Integer.valueOf( colora.substring(5, 7), 16 );
    }

    public void getPoints(List<GeoPoint> sauce){
        points = sauce;
    }

    public void create(GoogleMap googlemap){
        mapRef = googlemap;
        for(GeoPoint please : points){
            double latitude = please.getLatitude();;
            double longitude= please.getLongitude();;
            latList.add(new LatLng(latitude, longitude));
        }
        squares.addAll(latList);
        squares.strokeColor(Color.argb(50, red, green, blue));
        squares.fillColor(Color.argb(70, red, green, blue));
        polyline = mapRef.addPolygon(squares);
        polyline.setClickable(false);


    }

    /*public void initiate(){

        mapRef.setOnPolygonClickListener(new GoogleMap.OnPolygonClickListener() {
            public void onPolygonClick(Polygon polygon) {
                Intent tent = new Intent(getApplicationContext(), Pop.class);
                startActivity(tent);

            }
        });
    }*/



    public void setColor(int red, int green, int blue){
        squares.strokeColor(Color.argb(alphaStroke, red, green, blue));
        squares.fillColor(Color.argb(alphaFill, red, green, blue));

    }

    public void setColor(int red, int green, int blue, int alphaS, int alphaF) {
        alphaFill = alphaF; alphaStroke = alphaS;
        squares.strokeColor(Color.argb(alphaS, red, green, blue));
        squares.fillColor(Color.argb(alphaF, red, green, blue));
    }

    public void toggleListener(){
        if (active){
            polyline.setClickable(false);
        }else{ polyline.setClickable(true);}

    }

    public void forceClickable(boolean bool){
        polyline.setClickable(bool);
    }
}