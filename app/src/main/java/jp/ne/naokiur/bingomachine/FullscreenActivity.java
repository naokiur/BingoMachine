package jp.ne.naokiur.bingomachine;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;

import jp.ne.naokiur.bingomachine.service.BingoNumber;

public class FullscreenActivity extends AppCompatActivity {

    private final Handler handler = new Handler();

    private class RenderingRunnable implements Runnable {
        private final TextView targetView;
        private final String text;

        private RenderingRunnable(TextView targetView, String text) {
            this.targetView = targetView;
            this.text = text;
        }

        @Override
        public void run() {
            targetView.setText(text);
            targetView.invalidate();

        }
    }

    private class RollingThread extends Thread {

        @Override
        public void run() {
            final TextView rollingNumber = (TextView) findViewById(R.id.rollingNumber);

            long beginTime = Calendar.getInstance().getTimeInMillis();
            long endTime = beginTime;
            long term = endTime - beginTime;

            TextView hisoryView = (TextView) findViewById(R.id.historyNumberText);
            BingoNumber bingoNumber = new BingoNumber(hisoryView.getText().toString());

            while (term < 700) {
//          while (term < 7000) {
                for (int i = 1; i <= BingoNumber.MAX_BINGO_NUMBER; i++) {
                    handler.post(new RenderingRunnable(rollingNumber, String.valueOf(i)));
                }

                endTime = Calendar.getInstance().getTimeInMillis();
                term = endTime - beginTime;

            }

            handler.post(new RenderingRunnable(rollingNumber, String.valueOf(bingoNumber.getNumber())));
            handler.post(new RenderingRunnable(hisoryView, bingoNumber.createHistoryNumbers(hisoryView.getText().toString())));
        }
    }

    private final View.OnClickListener rollBingoClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            new RollingThread().start();
        }
    };

    private final View.OnClickListener resetClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            ((TextView) findViewById(R.id.rollingNumber)).setText("");
            ((TextView) findViewById(R.id.historyNumberText)).setText("");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);

        findViewById(R.id.button_roll_bingo).setOnClickListener(rollBingoClickListener);
        findViewById(R.id.reset).setOnClickListener(resetClickListener);

    }
}
