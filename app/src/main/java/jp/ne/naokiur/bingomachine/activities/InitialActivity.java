package jp.ne.naokiur.bingomachine.activities;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import jp.ne.naokiur.bingomachine.R;

public class InitialActivity extends AppCompatActivity implements BeginningFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);

        Button button = (Button) findViewById(R.id.button_new_game);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BeginningFragment.newInstance().show(getSupportFragmentManager(), "Dialog");
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new AlertDialog.Builder(this)
                    .setTitle("Close App")
                    .setMessage("May I close this Application ?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            InitialActivity.this.finish();
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            }).show();

            return true;
        }

        return false;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }
}
