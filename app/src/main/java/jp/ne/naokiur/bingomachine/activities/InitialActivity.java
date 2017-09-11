package jp.ne.naokiur.bingomachine.activities;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import jp.ne.naokiur.bingomachine.R;

public class InitialActivity extends AppCompatActivity implements BeginningFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);

        TextView animation = (TextView) findViewById(R.id.text_app_title_B);
        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.updown);
        animator.setTarget(animation);
        animator.start();

        Button button = (Button) findViewById(R.id.button_new_game);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BeginningFragment.newInstance().show(getSupportFragmentManager(), "Dialog");
            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }
}
