package jp.ne.naokiur.bingomachine;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.Calendar;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private static Handler mHandler;
    private Activity activity;
    private TextView mRollingNumber;
    private View mRolling;
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
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
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
            while (term < 7000) {
                System.out.println(term);
                for (int i = 1; i <= 50; i++) {
//                    new TextRenderingThread(rollingNumber, String.valueOf(i)).start();
                    mHandler.post(new RenderingRunnable(rollingNumber, String.valueOf(i)));
                }

                endTime = Calendar.getInstance().getTimeInMillis();
                term = endTime - beginTime;

            }
        }
    }


    private class TextRenderingThread extends Thread {
        private final TextView targetView;
        private final String text;

        //        private TextRenderingThread(Handler handler, TextView targetView, String text) {
//            this.handler = handler;
//            this.targetView = targetView;
//            this.text = text;
//        }
        private TextRenderingThread(TextView targetView, String text) {
            this.targetView = targetView;
            this.text = text;
        }

        @Override
        public void run() {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    targetView.setText(text);

                    FrameLayout parentLayout = (FrameLayout) targetView.getParent();
                    FrameLayout rolling = (FrameLayout) findViewById(R.id.rolling);
                    targetView.invalidate();
//
//
//
//
//
//
//
//
                    parentLayout.invalidate();
                    rolling.invalidate();

                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            });
        }

    }

    int number = 0;
//    private  RollingHandler mHandler;
//    private class RollingHandler extends Handler {
//        @Override
//        public void handleMessage(Message msg) {
//            mRollingNumber.invalidate();
//            if (mHandler != null) {
//
//            }
//        }
//
//        public void sleep(long mill) {
//            removeMessages(0);
//            sendMessageDelayed(obtainMessage(0), mill);
//        }
//    }
    private final View.OnClickListener rollBingoClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            TextView rollingNumber = (TextView) findViewById(R.id.rollingNumber);
            new RollingThread().start();

//            long beginTime = Calendar.getInstance().getTimeInMillis();
//            long endTime = beginTime;
//            long term = endTime - beginTime;
//            while (term < 700) {
//                System.out.println(term);

//                for (int i = 1; i <= 50; i++) {
//                    number = i;

//                    new Thread(new Runnable() {
//                    new Thread() {
//                        @Override
//                        public void run() {
//                            mHandler.post(new Runnable() {
//                                @Override
//                                public void run() {
////                            try {
////                                Thread.sleep(200);
////                            } catch (InterruptedException e) {
////                                e.printStackTrace();
////                            }
//                                    for (int i = 1; i <= 50; i++) {
//                                        mRollingNumber.setText(String.valueOf(i));
//                                        mRollingNumber.invalidate();
//                                        mRolling.invalidate();
//
//                                    }
//
////                            activity.runOnUiThread(new Runnable() {
////                                @Override
////                                public void run() {
////                                    mRollingNumber.setText(number);
////                                    mRollingNumber.invalidate();
////                                }
////                            });
//                                }
//
//                            });
//                        }
//                    }.start();
////                }
//
//                endTime = Calendar.getInstance().getTimeInMillis();
//                term = endTime - beginTime;
//
//            }
//            mHandler.removeCallbacksAndMessages(null);

//                for (int i = 1; i <= 50; i++) {
//                    number = i;
//                    new Thread() {
//                        @Override
//                        public void run() {
//                            TextView rollingNumber = (TextView) findViewById(R.id.rollingNumber);
//                            try {
//                                Thread.sleep(100);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
////                            mHandler.post(new Runnable() {
////                                @Override
////                                public void run() {
////                                    FrameLayout rolling = (FrameLayout) findViewById(R.id.rolling);
////                                    TextView rollingNumber = (TextView) findViewById(R.id.rollingNumber);
////                                    rollingNumber.setText("a");
////                                    rollingNumber.invalidate();
////                                    rolling.invalidate();
////
////                                }
////                            });
//                            mHandler.post(new RenderingRunnable(rollingNumber, String.valueOf(number)));
//
//                        }
//                    }.start();
//                    endTime = Calendar.getInstance().getTimeInMillis();
//                    term = endTime - beginTime;
//                    System.out.println(term);
//                    new Thread() {
//                        @Override
//                        public void run() {
//                            TextView rollingNumber = (TextView) findViewById(R.id.rollingNumber);
//                            mHandler.post(new RenderingRunnable(rollingNumber, String.valueOf(number)));
//
//                        }
//                    }.start();
//                }
//            }
//            activity.runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                            TextView rollingNumber = (TextView) findViewById(R.id.rollingNumber);
//                            FrameLayout rolling= (FrameLayout) findViewById(R.id.rolling);
//                            rollingNumber.setText("aaa");
//                            rollingNumber.invalidate();
//                            rolling.invalidate();
//
//                }
//            });
//
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            activity.runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    TextView rollingNumber = (TextView) findViewById(R.id.rollingNumber);
//                    FrameLayout rolling= (FrameLayout) findViewById(R.id.rolling);
//                    rollingNumber.setText("ccc");
//                    rollingNumber.invalidate();
//                    rolling.invalidate();
//
//                }
//            });

//            mHandler.post(new Runnable() {
//                @Override
//                public void run() {
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//                    TextView rollingNumber = (TextView) findViewById(R.id.rollingNumber);
//                    FrameLayout rolling= (FrameLayout) findViewById(R.id.rolling);
//                    rollingNumber.setText("222");
//                    rollingNumber.invalidate();
//                    rolling.invalidate();
//
//                }
//            });
//
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            mHandler.post(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                    TextView rollingNumber = (TextView) findViewById(R.id.rollingNumber);
//                    FrameLayout rolling= (FrameLayout) findViewById(R.id.rolling);
//                    rollingNumber.setText("111");
//                    rollingNumber.invalidate();
//                    rolling.invalidate();
//
//                }
//            });
//
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            mHandler.post(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                    TextView rollingNumber = (TextView) findViewById(R.id.rollingNumber);
//                    FrameLayout rolling= (FrameLayout) findViewById(R.id.rolling);
//                    rollingNumber.setText("000");
//                    rollingNumber.invalidate();
//                    rolling.invalidate();
//
//                }
//            });
//
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            new Thread() {
//                @Override
//                public void run() {
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    mHandler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            TextView rollingNumber = (TextView) findViewById(R.id.rollingNumber);
//                            FrameLayout rolling= (FrameLayout) findViewById(R.id.rolling);
//                            rollingNumber.setText("aaa");
//                            rollingNumber.invalidate();
//                            rolling.invalidate();
//
//                        }
//                    });
//
//                }
//            }.start();
//            System.out.println("totyu");
//
//            new Thread() {
//                @Override
//                public void run() {
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    mHandler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            TextView rollingNumber = (TextView) findViewById(R.id.rollingNumber);
//                            rollingNumber.setText("bbb");
//                            rollingNumber.invalidate();
//
//                        }
//                    });
//
//                }
//            }.start();
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            mHandler.post(new Runnable() {
//                @Override
//                public void run() {
//                    TextView rollingNumber = (TextView) findViewById(R.id.rollingNumber);
//                    rollingNumber.setText("ccc");
//                    rollingNumber.invalidate();
//
//                }
//            });
            System.out.println("End");


//            mHandler = new Handler();
//            TextView rollingNumber = (TextView) findViewById(R.id.rollingNumber);
//
//            long beginTime = Calendar.getInstance().getTimeInMillis();
//            long endTime = beginTime;
//            long term = endTime - beginTime;
//            while (term < 7000) {
//                for (int i = 1; i <= 50; i++) {
////                    new TextRenderingThread(mHandler, rollingNumber, String.valueOf(i)).start();
//
//                    new TextRenderingThread(rollingNumber, String.valueOf(i)).start();
//                }
//
//                endTime = Calendar.getInstance().getTimeInMillis();
//                term = endTime - beginTime;
//                System.out.println(term);
//            }
            System.out.println("End");
//
//
//
//            FrameLayout rolling = (FrameLayout) findViewById(R.id.rolling);
//            TextView rollingNumber = (TextView) findViewById(R.id.rollingNumber);
//            long beginTime = Calendar.getInstance().getTimeInMillis();
//            long endTime = beginTime;
//            long term = endTime - beginTime;
//
//            while (term < 700) {
//                                for (int i = 1; i <= 50; i++) {
//            new Thread() {
//                @Override
//                public void run() {
//                    mHandler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                                    rollingNumber.setText(String.valueOf(3).toString());
//                                    System.out.println("First");
//                                    rollingNumber.invalidate();
//                                    rolling.invalidate();
//                                    endTime = Calendar.getInstance().getTimeInMillis();
//                                    term = endTime - beginTime;
////                                }
//
//                            }
//                    });
//                }
//            }.start();
//
//                                }
//
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        mHandler = new Handler();

        setContentView(R.layout.activity_fullscreen);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);
        mRolling = findViewById(R.id.rolling);
        mRollingNumber = (TextView) findViewById(R.id.rollingNumber);

        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

//        mHandler = new RollingHandler();
        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        findViewById(R.id.button_roll_bingo).setOnClickListener(rollBingoClickListener);

        mRollingNumber.setText("aa");
        mRollingNumber.invalidate();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                activity.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        mRollingNumber.setText("bbb");
//                        mRollingNumber.invalidate();
//                    }
//                });
//            }
//
//        }).start();

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
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}
