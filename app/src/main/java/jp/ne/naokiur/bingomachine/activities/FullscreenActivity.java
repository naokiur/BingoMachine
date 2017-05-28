package jp.ne.naokiur.bingomachine.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import jp.ne.naokiur.bingomachine.R;
import jp.ne.naokiur.bingomachine.service.BingoNumber;
import jp.ne.naokiur.bingomachine.service.BingoProcessDao;

public class FullscreenActivity extends AppCompatActivity {

    private Button bingo;
    private Button reset;
    private TextView rollingNumber;

    private final Handler handler = new Handler();
    private final BingoProcessDao bingoProcessDao = new BingoProcessDao(this);

    private final View.OnClickListener rollBingoClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            new RollingThread().start();

        }
    };

    private final View.OnClickListener resetClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            bingoProcessDao.deleteAll();
            switchEnableBingoRollButton();
            ((TextView) findViewById(R.id.text_rolling_number)).setText("");
            ((TextView) findViewById(R.id.text_history_number)).setText("");
        }
    };

    private class SwitchRunnable implements Runnable {

        @Override
        public void run() {
            switchEnableBingoRollButton();
        }
    }

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

            long beginTime = Calendar.getInstance().getTimeInMillis();
            long endTime = beginTime;
            long term = endTime - beginTime;

            TextView historyView = (TextView) findViewById(R.id.text_history_number);
            BingoNumber bingoNumber = new BingoNumber(bingoProcessDao.selectAll());

            while (term < 700) {
//          while (term < 7000) {
                for (int i = 1; i <= BingoNumber.MAX_BINGO_NUMBER; i++) {
                    handler.post(new RenderingRunnable(rollingNumber, String.valueOf(i)));
                }

                endTime = Calendar.getInstance().getTimeInMillis();
                term = endTime - beginTime;

            }

            handler.post(new RenderingRunnable(rollingNumber, String.valueOf(bingoNumber.getNumber())));
            bingoProcessDao.insert(bingoNumber.getNumber());
            handler.post(new RenderingRunnable(historyView, bingoNumber.createHistoryNumbers(bingoProcessDao.selectAll())));
            handler.post(new SwitchRunnable());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        bingo = (Button) findViewById(R.id.button_roll_bingo);
        reset = (Button) findViewById(R.id.button_reset);
        rollingNumber = (TextView) findViewById(R.id.text_rolling_number);

        switchEnableBingoRollButton();

        bingo.setOnClickListener(rollBingoClickListener);
        reset.setOnClickListener(resetClickListener);
    }

    private void switchEnableBingoRollButton() {
        List<Integer> historyList = bingoProcessDao.selectAll();

        if (historyList.size() >= BingoNumber.MAX_BINGO_NUMBER) {
            bingo.setEnabled(false);
        } else {
            bingo.setEnabled(true);

        }
    }

}
