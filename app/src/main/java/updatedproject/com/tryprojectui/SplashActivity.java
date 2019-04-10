package updatedproject.com.tryprojectui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import maes.tech.intentanim.CustomIntent;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(SharedPrefManager.getInstance(this).getEnrollment() != null) {
            finish();
            startActivity(new Intent(this, Profile.class));
            return;
        }else{
            setContentView(R.layout.activity_splash);
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        Thread.sleep(2000);
                    }catch (Exception e ) {
                        e.printStackTrace();
                    }finally{
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        CustomIntent.customType(SplashActivity.this, "fadein-to-fadeout");
                    }
                }
            });thread.start();
        }
    }
}
