package jp.ne.naokiur.bingomachine.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import jp.ne.naokiur.bingomachine.R;

public class LoadAppActivity extends AppCompatActivity {
    private InterstitialAd interstitialAd;
    private  static final int LOAD_ADVERTISEMENT = 4500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_app);

        loadInterstitial();
    }

    @Override
    protected void onStart() {
        super.onStart();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                LoadAppActivity.this.startActivity(new Intent(LoadAppActivity.this, InitialActivity.class));

                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                    requestNewInterstitial();
                }

            }
        }, LOAD_ADVERTISEMENT);
    }

    private void loadInterstitial() {
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.banner_ad_unit_id));
        requestNewInterstitial();

        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
            }
        });
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("SEE_YOUR_LOGCAT_TO_GET_YOUR_DEVICE_ID").build();
        interstitialAd.loadAd(adRequest);
    }
}
