package com.example.afentanes.twitsaver.layout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.example.afentanes.twitsaver.R;

public class SaveTwitDialogFragment extends DialogFragment {


    DialogInterface.OnClickListener listener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setMessage(getResources().getString(R.string.save_twit_dialog_message))
                .setPositiveButton(getResources().getString(R.string.save_twit), listener)
                .setNegativeButton(R.string.cancel_twit, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });


        return builder.create();
    }

    public void setSaveListener(DialogInterface.OnClickListener listener){
       this.listener=listener;
    }
}
