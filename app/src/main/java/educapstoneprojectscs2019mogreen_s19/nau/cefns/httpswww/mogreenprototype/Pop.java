package educapstoneprojectscs2019mogreen_s19.nau.cefns.httpswww.mogreenprototype;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;


//Class for popup menu
public class Pop extends Map_Menu {

    static final int REQUEST_IMAGE_CAPTURE = 1;

        protected void onCreate(Bundle savedInstanceState) {



            super.onCreate(savedInstanceState);

            setContentView(R.layout.popwindow);

            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);

            int width = dm.widthPixels;
            int height = dm.heightPixels;

            getWindow().setLayout((int)(width*.8), (int)(height*.6));
            WindowManager.LayoutParams params = getWindow().getAttributes();
            params.gravity = Gravity.CENTER;
            getWindow().setAttributes(params);


        }



}
