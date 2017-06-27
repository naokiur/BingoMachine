package jp.ne.naokiur.bingomachine.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;

import jp.ne.naokiur.bingomachine.R;
import jp.ne.naokiur.bingomachine.common.exception.ValidateException;
import jp.ne.naokiur.bingomachine.service.BingoGame;

public class BeginningFragment extends DialogFragment {

    private OnFragmentInteractionListener mListener;

    private View layout;
    private TextView message;
    private EditText title;
    private EditText maxNumber;

    public BeginningFragment() {
    }

    public static BeginningFragment newInstance() {
        BeginningFragment fragment = new BeginningFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = getActivity().getLayoutInflater();

        layout = inflater.inflate(R.layout.fragment_beginning, null);
        message = (TextView) layout.findViewById(R.id.message);

        title = (EditText) layout.findViewById(R.id.edit_title);

        maxNumber = (EditText) layout.findViewById(R.id.edit_max_number);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(layout).setTitle(R.string.text_fragment_title);
        Button button = (Button) layout.findViewById(R.id.button_begin_new_game);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String titleText = title.getText().toString();
                String maxNumberText = maxNumber.getText().toString();
                message.setText(StringUtils.EMPTY);

                if (!StringUtils.isNumeric(maxNumberText) || maxNumberText.length() > 3) {
                    message.setText(getString(R.string.message_error_invalid_max_number));
                    return;
                }

                BingoGame bingoGame = new BingoGame(titleText, Integer.valueOf(maxNumberText), getContext());
                try {
                    bingoGame.register();
                } catch (ValidateException e) {
                    message.setText((e.getMessage()));
                    return;
                }

                Intent intent = new Intent(getActivity(), FullscreenActivity.class);
                intent.putExtra("maxNumber", bingoGame.getMaxNumber());
                intent.putExtra("gameId", bingoGame.getGameId());

                startActivity(intent);
            }
        });

        return builder.create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_beginning, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
