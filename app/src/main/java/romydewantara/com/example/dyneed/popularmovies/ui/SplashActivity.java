package romydewantara.com.example.dyneed.popularmovies.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import romydewantara.com.example.dyneed.popularmovies.R;
import romydewantara.com.example.dyneed.popularmovies.ui.MainActivity;

public class SplashActivity extends AppCompatActivity {

    private boolean splashActive = true;
    private long ms = 0;
    private long splashTime = 3000;
    private boolean paused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();

        Thread myThread =
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            while (splashActive && ms < splashTime) {
                                if (!paused) {
                                    ms = ms + 100;
                                    sleep(100);
                                }
                            }
                        } catch (Exception e) {

                        } finally {
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }
                };

        myThread.start();
    }
}
