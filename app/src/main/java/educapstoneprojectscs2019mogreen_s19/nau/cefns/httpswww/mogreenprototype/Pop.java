package educapstoneprojectscs2019mogreen_s19.nau.cefns.httpswww.mogreenprototype;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Pop extends Map_Menu {

    int imageFlag = 0;
    Bitmap bitmap;
    String FILENAME = "None", emailGot, message;
    TextView reportC, locat;
    ImageView image;
    FirebaseUser emailGet;
    Spinner spinner;
    Map<String, Object> reportHolder = new HashMap<>();
    private int MY_PERMISSIONS_REQUEST_CAMERA = 0;
    private FirebaseFirestore mDatabase;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.popwindow);


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_CAMERA);
        }


        mDatabase = FirebaseFirestore.getInstance();


        final Button takePicture = findViewById(R.id.picturebutton);
        reportC = findViewById(R.id.reportContent);
        Button submit = findViewById(R.id.submitbutton);


        //dropdown menu

        spinner = (Spinner) findViewById(R.id.report_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.report_choices,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);


        locat = findViewById(R.id.name);
        Bundle b = getIntent().getExtras();
        message = b.getString("ZONE");
        locat.setText(message);


        /*Popupwindow views and dimentions. Other settings can be viewed in
          styles.xml
         */
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * .8));
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        getWindow().setAttributes(params);


        //Disables picture button if camera cant be accessed
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            takePicture.setEnabled(false);
        }

        //Takes a picture
        takePicture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                takePicture.setClickable(false);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    startActivityForResult(intent, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                takePicture.setClickable(true);

            }


        });


        //Submit button workings
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                //Error message for lack of content
                Context context = getApplicationContext();
                String text = "";
                int duration = Toast.LENGTH_LONG;
                Toast noReport;


                //Getting report content
                String reportContent = reportC.getText().toString();
                if (spinner.getSelectedItem().toString().equals("Report Type")) {
                    text = "Please Select Report Type\n";
                }
                if (reportContent.equals("")) {
                    text = text + "Please add details to report\n";
                }

                if (text.equals("")) {
                    reportHolder.put("description", reportContent);
                    reportHolder.put("task_type", spinner.getSelectedItem().toString());
                    reportHolder.put("is_completed", false);
                    reportHolder.put("task_location", message);
                    Date date = new Date();
                    reportHolder.put("time_stamp", new com.google.firebase.Timestamp(date));


                    emailGet = FirebaseAuth.getInstance().getCurrentUser();


                    if (emailGet != null) {

                        emailGot = emailGet.getEmail();

                    }


                    reportHolder.put("user", emailGot);
                    if (imageFlag == 1) {
                        sendImage();
                    } else {
                        reportHolder.put("image", "None");
                    }

                    //Sends report from a hashmap containing any used information.
                    mDatabase.collection("tasks").add(reportHolder);
                    finish();

                } else {
                    noReport = Toast.makeText(context, text, duration);
                    noReport.show();
                }

            }
        });


    }

    public void onBackPressed() {

        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //Crates file name from timestamp in nanoseconds
        Timestamp time = new Timestamp(System.currentTimeMillis());
        FILENAME = "" + time;

        //Shows image in activity
        try {
            super.onActivityResult(requestCode, resultCode, data);
            bitmap = (Bitmap) data.getExtras().get("data");
            image = findViewById(R.id.imageView2);
            image.setImageBitmap(bitmap);
        } catch (Exception e) {
        }

        //Stores file in system
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        try {
            File photoFile = File.createTempFile(FILENAME, ".jpg", storageDir);
            reportHolder.put("image", FILENAME);
            imageFlag = 1;


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void sendImage() {


        //Creates storage instance and references
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference pictureRef = storageRef.child(FILENAME + ".jpeg");
        StorageReference reportImageRef = storageRef.child("images/" + FILENAME + ".jpeg");

        image.setDrawingCacheEnabled(true);
        image.buildDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageData = baos.toByteArray();

        UploadTask uploadTask = pictureRef.putBytes(imageData);
    }
}