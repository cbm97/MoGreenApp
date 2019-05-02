package educapstoneprojectscs2019mogreen_s19.nau.cefns.httpswww.mogreenprototype;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Quick_Report extends Map_Menu {

    private FirebaseFirestore mDatabase;
    Spinner spinner;
    int duration, cleanFlag = 0;
    ToggleButton isClean;
    Context context;
    String text, emailGot = "", message;
    Map<String, Object> reportHolder;
    Toast noReport;
    FirebaseUser emailGet;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick__report);


        /*Popupwindow views and dimentions. Other settings can be viewed in
          styles.xml
         */

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * .2));
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        getWindow().setAttributes(params);


        spinner = (Spinner) findViewById(R.id.report_type);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.report_choices,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        //New adapter for the clean choices
        final ArrayAdapter<CharSequence> adapter_c = ArrayAdapter.createFromResource(this, R.array.report_choices_c,
                android.R.layout.simple_spinner_item);
        adapter_c.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        isClean = (ToggleButton) findViewById(R.id.cleanSwitch);
        isClean.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cleanFlag = 1;
                    spinner.setAdapter(adapter_c);
                } else {
                    spinner.setAdapter(adapter);
                    cleanFlag = 0;
                }
            }
        });



        mDatabase = FirebaseFirestore.getInstance();
        Bundle b = getIntent().getExtras();
        message = b.getString("ZONE");
        emailGet = FirebaseAuth.getInstance().getCurrentUser();


        if (emailGet != null) {

            emailGot = emailGet.getEmail();

        }







        duration = Toast.LENGTH_LONG;
        context = getApplicationContext();
        Button submit = findViewById(R.id.q_r_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (spinner.getSelectedItem().toString().equals("Report Type")) {
                    text = "Please Select Report Type\n";
                    noReport = Toast.makeText(context, text, duration);
                    noReport.show();
                } else {

                    reportHolder = new HashMap<>();
                    reportHolder.put("task_type", spinner.getSelectedItem().toString());
                    reportHolder.put("user", emailGot);
                    reportHolder.put("task_location", message);
                    Date date = new Date();
                    reportHolder.put("time_stamp", new Timestamp(date));

                    if(cleanFlag == 0)
                    {
                        reportHolder.put("is_checked", "n");
                        mDatabase.collection("clean_reports").add(reportHolder);
                    }
                    else {

                        reportHolder.put("description", "q_r");
                        reportHolder.put("is_completed", false);
                        reportHolder.put("image", "None");
                        mDatabase.collection("tasks").add(reportHolder);
                    }
                    finish();
                }


            }
        });
    }
}

