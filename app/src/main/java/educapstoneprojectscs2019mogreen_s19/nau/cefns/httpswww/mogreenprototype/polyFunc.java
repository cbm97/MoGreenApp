package educapstoneprojectscs2019mogreen_s19.nau.cefns.httpswww.mogreenprototype;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;

/*
  All methods that involve creating a shape is included here
 */

public class polyFunc {

    public void polyFunc(){

    }

    //Creates a shape from added LatLng points and returns that shape
    public PolygonOptions addShape(LatLng point1, LatLng point2, LatLng point3, LatLng point4 ){
        PolygonOptions squares = new PolygonOptions();
        squares.add(point1);
        squares.add(point2);
        squares.add(point3);
        squares.add(point4);
        squares.add(point1);
        return squares;
    }


    //Creates LatLng object
    public LatLng pointCreate(float lat, float longa){
        LatLng tempo = new LatLng(lat, longa);
        return tempo;
    }
}
