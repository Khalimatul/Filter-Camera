package joss.polinema.firebase;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.provider.CalendarContract.CalendarCache.URI;
import static joss.polinema.firebase.R.id.imgPreview;

public class CameraActivity extends AppCompatActivity {
    Button mButtonPicture, mButtonGray, mButtonNegatif;
    ImageView mImageView;
    File mFileURI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        //inisiasi object bertipe BodyPartsFrament
        BodyPartsFragment bodyFragment;
        //inisiasi adapter untuk mengaktifkan fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        //pengecekan agar fragment hanya dibuat sekali saja.
        if(savedInstanceState == null) {
            //membuat object untuk heads  dengan men-setting list gambar yang dipanggil serta index nya.
            //setimageids untu mengisi image pada bodypartsfragment variabel imageids
            bodyFragment = new BodyPartsFragment();
            bodyFragment.setImageIds(ImageAssets.getHeads());
            bodyFragment.setmListIndex(0);
            //transaksi fragment
            fragmentManager.beginTransaction().add(R.id.heads_container, bodyFragment).commit();

        }

        mButtonPicture = (Button) findViewById(R.id.btnCapture);
        mButtonGray = (Button) findViewById(R.id.btnGray);
        mButtonNegatif = (Button) findViewById(R.id.btnNegatif);
        mImageView = (ImageView) findViewById(imgPreview);
        mButtonPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureImage();
            }
        });
        mButtonGray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mImageView.buildDrawingCache();
                Bitmap bitmap = mImageView.getDrawingCache();
                Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),
                        Bitmap.Config.ARGB_8888);
                for (int i = 0; i < bitmap.getWidth(); i++) {
                    for (int j = 0; j < bitmap.getHeight(); j++) {
                        int p = bitmap.getPixel(i,j);
                        int r = Color.red(p);
                        int g = Color.green(p);
                        int b = Color.blue(p);
                        r = g = b = (int) (r+g+b)/3;
                        newBitmap .setPixel(i, j, Color.rgb(r, g, b));
                    }
                }
                mImageView.setImageBitmap(newBitmap );
            }
        });
        mButtonNegatif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mImageView.buildDrawingCache();
                Bitmap bitmap = mImageView.getDrawingCache();
                Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),
                        Bitmap.Config.ARGB_8888);
                for (int i = 0; i < bitmap.getWidth(); i++) {
                    for (int j = 0; j < bitmap.getHeight(); j++) {
                        int p = bitmap.getPixel(i,j);
                        int r = 255 - Color.red(p);
                        int g = 255 - Color.green(p);
                        int b = 255 - Color.blue(p);
                        newBitmap .setPixel(i, j, Color.argb(255, r, g, b));
                    }
                }
                mImageView.setImageBitmap(newBitmap);
            }
        });
// Checking camera availability
        if (!isDeviceSupportCamera()) {
            Toast.makeText(getApplicationContext(), "Camera di device anda tidak tersedia",
                    Toast.LENGTH_LONG).show();
            finish();
    }
}
    /*
    * Capturing Camera Image will lauch camera app requrest image capture
    */
    private void captureImage() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            mFileURI = getMediaFileName();
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, URI.fromFile(mFileURI));
            startActivityForResult(takePictureIntent, 100);
        }
    }
    /**
     * activity result akan dipanggi setelah camera ditutup
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == RESULT_OK) {
// BitmapFactory.Options bmOptions = new BitmapFactory.Options();
// // rescale bitmap jika aplikasi force close
// // semakin besar ukuran rescale maka image/gambar yang ditampilkan semakinkecil
// bmOptions.inSampleSize = 4;
//
// Bitmap bitmap = BitmapFactory.decodeFile(mFileURI.getPath(), bmOptions);
// mImageView.setVisibility(View.VISIBLE);
// mImageView.setImageBitmap(bitmap);
            Glide.with(getApplicationContext()).load(new
                    File(mFileURI.getPath())).into(mImageView);
            mImageView.setVisibility(View.VISIBLE);
            mButtonNegatif.setVisibility(View.VISIBLE);
            mButtonGray.setVisibility(View.VISIBLE);
        }
    }
    /**
     * mengecek pada perangkat mobile memiliki kamera atau tidak
     * */
    private boolean isDeviceSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
// this device has a camera
            return true;
        } else {
// no camera on this device
            return false;
        }
    }
    private static File getMediaFileName() {
// Lokasi External sdcard
        File mediaStorageDir = new
                File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "CameraDemo");
// Buat directori tidak direktori tidak eksis
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("CameraDemo", "Gagal membuat directory"+ "CameraDemo");
                return null;
            }
        }
// Membuat nama file
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile = null;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp
                + ".jpg");
        return mediaFile;
    }
    public void signout(View view) {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(CameraActivity.this, "User Signed Out", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }
}
