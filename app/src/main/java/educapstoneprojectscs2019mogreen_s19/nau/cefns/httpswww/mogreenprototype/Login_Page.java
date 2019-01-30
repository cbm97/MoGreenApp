package educapstoneprojectscs2019mogreen_s19.nau.cefns.httpswww.mogreenprototype;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login_Page extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__page);
        final EditText inputEmail = (EditText) findViewById(R.id.username);
        final EditText inputPassword = (EditText) findViewById(R.id.password);
        Button login = (Button) findViewById(R.id.login);
        Button register = findViewById(R.id.register_button);

        //password authentication
        mAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v){
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Login_Page.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Intent intent = new Intent(Login_Page.this, Map_Menu.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }

                        });

            }

        });


        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent reg_activity =  new Intent(Login_Page.this, Register.class);
                startActivity(reg_activity);
            }

        });


    }



}
