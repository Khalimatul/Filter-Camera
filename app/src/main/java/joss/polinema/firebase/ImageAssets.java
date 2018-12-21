package joss.polinema.firebase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Islamiyah Chimpony on 12/17/2018.
 */

public class ImageAssets {

    //Variabel ini adalah variabel pengelompokan untuk head kedalam array dan mengambil gambar head dari resource drawable
    private static List<Integer> heads = new ArrayList<Integer>(){{
        add(R.drawable.satu1);
        add(R.drawable.dua2);
        add(R.drawable.tiga3);
        add(R.drawable.empat4);
        add(R.drawable.lima5);
        add(R.drawable.enam6);
    }};

    //Variabel ini adalah variabel pengelompokan untuk body kedalam array
//    private static List<Integer> bodies = new ArrayList<Integer>() {{
//        add(R.drawable.body1);
//        add(R.drawable.body2);
//        add(R.drawable.body3);
//    }};

    //Variabel ini adalah variabel pengelompokan untuk legs kedalam array
//    private static List<Integer> legs = new ArrayList<Integer>() {{
//        add(R.drawable.legs1);
//        add(R.drawable.legs2);
//        add(R.drawable.legs3);
//
//    }};

    //ini adalah method yg digunakana untuk memanggil/mengambil variable heads
    public static List<Integer> getHeads() {

        return heads;
    }
    //ini adalah method yg digunakana untuk memanggil/mengambil variable bodies
//    public static List<Integer> getBodies() {
//
//        return bodies;
//    }

    //ini adalah method yg digunakana untuk memanggil/mengambil variable legs
//    public static List<Integer> getLegs() {
//
//        return legs;
//    }

}
