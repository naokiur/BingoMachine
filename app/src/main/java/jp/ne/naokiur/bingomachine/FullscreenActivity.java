package jp.ne.naokiur.bingomachine;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;

import jp.ne.naokiur.bingomachine.service.BingoNumber;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity {

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler handler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);

        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        findViewById(R.id.button_roll_bingo).setOnClickListener(rollBingoClickListener);

    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        handler.removeCallbacks(mShowPart2Runnable);
        handler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        handler.removeCallbacks(mHidePart2Runnable);
        handler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        handler.removeCallbacks(mHideRunnable);
        handler.postDelayed(mHideRunnable, delayMillis);
    }
}
