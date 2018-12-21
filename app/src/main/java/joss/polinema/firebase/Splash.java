package joss.polinema.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash extends AppCompatActivity {
    TextView tvSplash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//        super.onCreate(savedInstanceState);

        //menghilangkan ActionBar
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
       // setContentView(R.layout.activity_splash);

        TextView tvSplash = (TextView) findViewById(R.id.tvSplash);
        ImageView img = (ImageView)findViewById(R.id.imgSplash);

        //digunakan untuk membuka activity selanjutnya
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Intent intent = new Intent(Splash.this, MainActivity.class);
               // Splash.this.startActivity(intent);
                //membuka CameraActivity
//                startActivity(new Intent(getApplicationContext(),MainActivity.class));
//                finish();
                Intent intent = new Intent(Splash.this, Login2Activity.class);
                Splash.this.startActivity(intent);
            }
        }, 3000L); //3000 L = 3 detik
    }
}
