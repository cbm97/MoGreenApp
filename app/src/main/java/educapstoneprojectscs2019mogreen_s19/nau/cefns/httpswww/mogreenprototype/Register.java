package educapstoneprojectscs2019mogreen_s19.nau.cefns.httpswww.mogreenprototype;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    private FirebaseAuth mAuth;
    Button register, back;
    EditText email, password, password2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        //inputs from user
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        password2 = findViewById(R.id.passwordrepeat);

        //Initiate buttons
        register = findViewById(R.id.submit);
        back = (Button) findViewById(R.id.back);

        //Create listeners
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                finish();
            }

        });

        register.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String givenEmail = email.getText().toString();
                String pass1 = password.getText().toString();
                String pass2 = password2.getText().toString();


                //Error for bad email
                if(!emailVerify(givenEmail)){
                    Toast.makeText(Register.this, "Please enter a valid email",
                            Toast.LENGTH_SHORT).show();
                }else if(!pass1.equals(pass2)){
                    Toast.makeText(Register.this, "Passwords do not match",
                            Toast.LENGTH_SHORT).show();
                }


                //Verification check
                if(emailVerify(givenEmail) && pass1.equals(pass2)){
                    mAuth.createUserWithEmailAndPassword(givenEmail, pass2)
                            .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(Register.this, "Registration successful",
                                                Toast.LENGTH_SHORT).show();
                                        try {
                                            TimeUnit.SECONDS.sleep(2);
                                        } catch(InterruptedException e) {
                                        }
                                        finish();
                                    }else{
                                        Toast.makeText(Register.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }

            }
        });
        mAuth = FirebaseAuth.getInstance();
    }


    private void createAccount(String username, String password){

    }


    public boolean emailVerify(String email){
        String verif = email;
        Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
        Matcher m = p.matcher(verif);
        return m.matches();
    }
}
