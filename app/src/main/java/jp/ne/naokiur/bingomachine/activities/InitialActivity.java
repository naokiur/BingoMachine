package jp.ne.naokiur.bingomachine.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import org.apache.commons.lang3.StringUtils;

import jp.ne.naokiur.bingomachine.R;
import jp.ne.naokiur.bingomachine.service.BingoGame;
import jp.ne.naokiur.bingomachine.strage.DatabaseHelper;

public class InitialActivity extends AppCompatActivity {
    InterstitialAd interstitialAd;
    EditText title;
    EditText maxNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);

        title = (EditText) findViewById(R.id.edit_title);
        maxNumber = (EditText) findViewById(R.id.edit_max_number);

        DatabaseHelper helper = new DatabaseHelper(this);
        helper.getWritableDatabase();

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.banner_ad_unit_id));

        requestNewInterstitial();

        Button button = (Button) findViewById(R.id.button_begin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titleText = title.getText().toString();
                String maxNumberText = maxNumber.getText().toString();

                if (StringUtils.isNumeric(maxNumberText)) {
                    new IllegalArgumentException();
                }

                BingoGame bingoGame = new BingoGame(titleText, Integer.valueOf(maxNumberText), getBaseContext());
                bingoGame.register();

                Intent intent = new Intent(getBaseContext(), FullscreenActivity.class);
                intent.putExtra("maxNumber", bingoGame.getMaxNumber());
                intent.putExtra("gameId", bingoGame.getGameId());

                startActivity(intent);

                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                    requestNewInterstitial();

                }
            }
        });

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
