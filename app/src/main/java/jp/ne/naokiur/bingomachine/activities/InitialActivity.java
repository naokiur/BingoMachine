package jp.ne.naokiur.bingomachine.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import jp.ne.naokiur.bingomachine.R;
import jp.ne.naokiur.bingomachine.strage.DatabaseHelper;

public class InitialActivity extends AppCompatActivity implements BeginningFragment.OnFragmentInteractionListener {
    private EditText title;
    private EditText maxNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);

        title = (EditText) findViewById(R.id.edit_title);
        maxNumber = (EditText) findViewById(R.id.edit_max_number);

        DatabaseHelper helper = new DatabaseHelper(this);
        helper.getWritableDatabase();

        Button button = (Button) findViewById(R.id.button_new_game);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String titleText = title.getText().toString();
//                String maxNumberText = maxNumber.getText().toString();
//
//                if (!StringUtils.isNumeric(maxNumberText) || maxNumberText.length() > 3) {
//                    displayToast(getString(R.string.message_error_invalid_max_number));
//                    return;
//                }
//
//                BingoGame bingoGame = new BingoGame(titleText, Integer.valueOf(maxNumberText), getBaseContext());
//                try {
//                    bingoGame.register();
//                } catch (ValidateException e) {
//                    displayToast(e.getMessage());
//                    return;
//                }
//
//                Intent intent = new Intent(getBaseContext(), FullscreenActivity.class);
//                intent.putExtra("maxNumber", bingoGame.getMaxNumber());
//                intent.putExtra("gameId", bingoGame.getGameId());

//                startActivity(intent);
                BeginningFragment.newInstance().show(getSupportFragmentManager(), "dialog");

            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }
}
