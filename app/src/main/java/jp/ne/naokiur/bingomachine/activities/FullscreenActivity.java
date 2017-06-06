package jp.ne.naokiur.bingomachine.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;

import java.util.Calendar;
import java.util.List;

import jp.ne.naokiur.bingomachine.R;
import jp.ne.naokiur.bingomachine.service.BingoNumber;
import jp.ne.naokiur.bingomachine.service.BingoProcessDao;

public class FullscreenActivity extends AppCompatActivity {

    private Integer maxNumber;
    private long gameId;
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

    private class HistoryRunnable implements Runnable {

        @Override
        public void run() {
            TextView historyView = (TextView) findViewById(R.id.text_history_number);

            historyView.setText(StringUtils.join(bingoProcessDao.selectByGameId(gameId), " "));
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
            handler.post(new SwitchRunnable());

            long beginTime = Calendar.getInstance().getTimeInMillis();
            long endTime = beginTime;
            long term = endTime - beginTime;

            BingoNumber bingoNumber = new BingoNumber(bingoProcessDao.selectByGameId(gameId), maxNumber);

            while (term < 700) {
//          while (term < 7000) {
                for (int i = 1; i <= maxNumber; i++) {
                    handler.post(new RenderingRunnable(rollingNumber, String.valueOf(i)));
                }

                endTime = Calendar.getInstance().getTimeInMillis();
                term = endTime - beginTime;

            }

            handler.post(new RenderingRunnable(rollingNumber, String.valueOf(bingoNumber.getNumber())));
            bingoProcessDao.insert(gameId, bingoNumber.getNumber());
            handler.post(new SwitchRunnable());
            handler.post(new HistoryRunnable());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        Intent intent = getIntent();

        maxNumber = intent.getIntExtra("maxNumber", 0);
        gameId = intent.getLongExtra("gameId", 0);
        bingo = (Button) findViewById(R.id.button_roll_bingo);
        reset = (Button) findViewById(R.id.button_reset);
        rollingNumber = (TextView) findViewById(R.id.text_rolling_number);

        bingo.setOnClickListener(rollBingoClickListener);
        reset.setOnClickListener(resetClickListener);
    }

    private void switchEnableBingoRollButton() {
        List<Integer> historyList = bingoProcessDao.selectByGameId(gameId);

        if (historyList.size() >= maxNumber) {
            bingo.setEnabled(false);
        } else {
            bingo.setEnabled(!bingo.isEnabled());

        }
    }
}
