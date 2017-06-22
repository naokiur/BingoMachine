package jp.ne.naokiur.bingomachine.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import jp.ne.naokiur.bingomachine.R;

public class BeginningFragment extends DialogFragment {

    private OnFragmentInteractionListener mListener;

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
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View layout = inflater.inflate(R.layout.fragment_beginning, null);

        builder.setView(layout).setTitle("新しいビンゴをはじめます").setPositiveButton(R.string.text_app_title, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                EditText title = (EditText) layout.findViewById(R.id.edit_title);
                EditText maxNumber = (EditText) layout.findViewById(R.id.edit_max_number);

                System.out.println(title.getText() + ":" + maxNumber.getText());

                Intent intent = new Intent(getActivity(), FullscreenActivity.class);
//                intent.putExtra("maxNumber", bingoGame.getMaxNumber());
//                intent.putExtra("gameId", bingoGame.getGameId());

                startActivity(intent);
            }
        });

        return builder.create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
