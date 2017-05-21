package jp.ne.naokiur.bingomachine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class InitialActivity extends AppCompatActivity {
    InterstitialAd interstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewIntential();
            }
        });

        requestNewIntential();

        Button button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                    requestNewIntential();
                    startBingo();

                } else {
                    startBingo();

                }
            }
        });
    }

    private void requestNewIntential() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("SEE_YOUR_LOGCAT_TO_GET_YOUR_DEVICE_ID").build();
        interstitialAd.loadAd(adRequest);
    }

    private void startBingo() {

        startActivity(new Intent(this, FullscreenActivity.class));
    }

}
