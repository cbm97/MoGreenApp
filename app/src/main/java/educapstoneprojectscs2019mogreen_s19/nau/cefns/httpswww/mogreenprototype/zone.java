package educapstoneprojectscs2019mogreen_s19.nau.cefns.httpswww.mogreenprototype;

import android.graphics.Color;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;


    /*
    Handles the individual zones including the ability to add points, change points,
    change colors and change the visibility of a zone.

    Public void zone : Initiates a zone from four LatLng objects and a GoogleMap map reference
        Default color is bright green

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

public class zone {
    private LatLng point1, point2, point3, point4;
    PolygonOptions squares = new PolygonOptions();
    Polygon polyline;
    private GoogleMap mapRef;
    boolean active = false;
    int alphaStroke = 50, alphaFill = 70;


    public void zone(LatLng one, LatLng two, LatLng three, LatLng four, GoogleMap googlemap){
        point1 = one;
        point2 = two;
        point3 = three;
        point4 = four;
        mapRef = googlemap;
        squares.add(one, two, three, four);
        squares.strokeColor(Color.argb(50, 0, 255, 0));
        squares.fillColor(Color.argb(70, 0,255, 0));
        polyline = mapRef.addPolygon(squares);

    }


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
