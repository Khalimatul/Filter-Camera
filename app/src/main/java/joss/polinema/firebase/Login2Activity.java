package joss.polinema.firebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login2Activity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String TAG = "Login";
    EditText emailL, passwordL;
    String sEmailL, sPasswordL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        Button buttonregister = (Button) findViewById(R.id.btn3);
        buttonregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login2Activity.this, RegisterActivity.class);
                Login2Activity.this.startActivity(intent);
            }
        });

        Button buttongoogle = (Button) findViewById(R.id.btn4);
        buttongoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login2Activity.this, MainActivity.class);
                Login2Activity.this.startActivity(intent);
            }
        });

        passwordL = ((EditText) findViewById(R.id.passwordLogin));
        emailL = ((EditText) findViewById(R.id.emailLogin));

        mAuth = FirebaseAuth.getInstance();
        Button btnregister = (Button) findViewById(R.id.btn2);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sPasswordL = passwordL.getText().toString();
                sEmailL = emailL.getText().toString();
                login(sEmailL, sPasswordL);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    private void login(String username, String password) {
        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(Login2Activity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(Login2Activity.this, "Success",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login2Activity.this, CameraActivity.class);
                            Login2Activity.this.startActivity(intent);

                        }

                        // ...
                    }
                });
    }
}
