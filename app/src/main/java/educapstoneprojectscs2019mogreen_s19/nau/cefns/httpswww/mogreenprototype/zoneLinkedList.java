package educapstoneprojectscs2019mogreen_s19.nau.cefns.httpswww.mogreenprototype;

import com.google.android.gms.maps.model.LatLng;

public class zoneLinkedList {

    private int n; //number of elements
    private Node pre;
    private Node post;

    class Node
    {
        private void node() {
            //Name of location
            String title;

            //Points to build polygon
            LatLng point1;
            LatLng point2;
            LatLng point3;
            LatLng point4; //May not be needed

            //Other needed for Linked list
            Node next;
            Node prev;
        }


    }

    public zoneLinkedList()
    {
        pre = new Node();
        post = new Node();
        //pre.next = post;

    }


    private void add(String name, LatLng pointA, LatLng pointB,
                      LatLng pointC, LatLng pointD)
    {
        String title = name;
        LatLng point1 = pointA;
        LatLng point2 = pointB;
        LatLng point3 = pointC;
        LatLng point4 = pointD;

        Node next;
        Node prev;

    }



}

