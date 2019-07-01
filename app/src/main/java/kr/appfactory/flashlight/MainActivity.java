package kr.appfactory.flashlight;


import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    static  Camera camera = null;
    private Context mContext=MainActivity.this;
    private static final int REQUEST = 112;

    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= 23) {
            String[] PERMISSIONS = {android.Manifest.permission.CAMERA};
            if (!hasPermissions(mContext, PERMISSIONS)) {
                ActivityCompat.requestPermissions((Activity) mContext, PERMISSIONS, REQUEST );
            } else {
                //do here
            }
        } else {
            //do here
        }

        setContentView(R.layout.activity_main);
        camera = Camera.open();


        Button onButton = (Button) findViewById(R.id.btn_flash_on);
        Button offButton = (Button) findViewById(R.id.btn_flash_off);

        offButton.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        camera.release();
    }

    public void onClick(View view) {

        Button onButton = (Button) findViewById(R.id.btn_flash_on);
        Button offButton = (Button) findViewById(R.id.btn_flash_off);

            if(view.getId() == R.id.btn_flash_on){
                Camera.Parameters param = camera.getParameters();
                param.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                camera.setParameters(param);
                camera.startPreview();

                onButton.setVisibility(View.INVISIBLE);
                offButton.setVisibility(View.VISIBLE);

            }else if(view.getId() == R.id.btn_flash_off){
                Camera.Parameters param = camera.getParameters();
                param.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                camera.setParameters(param);
                camera.stopPreview();
                onButton.setVisibility(View.VISIBLE);
                offButton.setVisibility(View.INVISIBLE);
            }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}
