package jp.ne.naokiur.bingomachine.activities;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import jp.ne.naokiur.bingomachine.R;
import jp.ne.naokiur.bingomachine.service.BingoNumber;
import jp.ne.naokiur.bingomachine.service.HistoryItem;
import jp.ne.naokiur.bingomachine.service.dao.BingoProcessDao;

public class FullscreenActivity extends AppCompatActivity {

    private Integer maxNumber;
    private long gameId;
    private Button reset;
    private Button rollingNumber;
    private GridView history;
    private SparseArray<HistoryItem> historyStatuses;

    private GestureDetector gestureDetector;

    private final Handler handler = new Handler();
    private final BingoProcessDao bingoProcessDao = new BingoProcessDao(this);

    private final View.OnClickListener resetClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            bingoProcessDao.deleteAll();
            switchEnableBingoRollButton();
            ((TextView) findViewById(R.id.text_rolling_number)).setText("");

            historyStatuses = initializeHistoryStatuses();
            history.setAdapter(new HistoryAdapter(getBaseContext(), historyStatuses));
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
            List<Integer> currentBingo = bingoProcessDao.selectByGameId(gameId);

            for (Integer current : currentBingo) {
                // If historyStatuses does not have 'current' as their key, indexOfKey() will return '-1'.
                if (historyStatuses.indexOfKey(current) >= 0) {
                    historyStatuses.get(current).setDrawn(true);
                }
            }

//            history.setAdapter(new HistoryAdapter(getBaseContext(), historyStatuses));
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

    private final GestureDetector.SimpleOnGestureListener mGestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            new RollingThread().start();

            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        Intent intent = getIntent();
        gestureDetector = new GestureDetector(this, mGestureListener);

        TextView animation = (TextView) findViewById(R.id.animation);
        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.updown);
        animator.setTarget(animation);
        animator.start();

        maxNumber = intent.getIntExtra("maxNumber", 0);
        gameId = intent.getLongExtra("gameId", 0);
//
////        bingo = (Button) findViewById(R.id.button_roll_bingo);
        reset = (Button) findViewById(R.id.button_reset);
        rollingNumber = (Button) findViewById(R.id.text_rolling_number);
////        history = (GridView) findViewById(R.id.history);
//        rollingNumber.setOnClickListener(rollBingoClickListener);

        rollingNumber.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });

        reset.setOnClickListener(resetClickListener);

        historyStatuses = initializeHistoryStatuses();

//        history.setAdapter(new HistoryAdapter(this, historyStatuses));
    }

    private void switchEnableBingoRollButton() {
        List<Integer> historyList = bingoProcessDao.selectByGameId(gameId);

        // TODO This has bug when reset button is tapped despite the game is not finished.
        if (historyList.size() >= maxNumber) {
            rollingNumber.setEnabled(false);
        } else {
            rollingNumber.setEnabled(!rollingNumber.isEnabled());

        }
    }

    private SparseArray<HistoryItem> initializeHistoryStatuses() {
        return new SparseArray<HistoryItem>() {
            {
                for (int i = 1; i <= maxNumber; i++) {
                    put(i, new HistoryItem(i, false));
                }
            }

        };
    }
}
