package com.app.lineage.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;


import com.app.lineage.ui.user.AddMeActivity;
import com.app.lineage6.R;

public class SignInDialog extends DialogFragment {

    private CheckBox checkBox;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_signin, null);

        // Get references to dialog views
        checkBox = dialogView.findViewById(R.id.checkbox_remember);

        builder.setView(dialogView)
                .setTitle("Sign In")
                .setMessage("Are you signed in?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle signed in scenario
                        dismiss();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle not signed in scenario
                        if (checkBox.isChecked()) {
                            // Remember user preference not to ask again
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putBoolean("show_signin_dialog", false);
                            editor.apply();
                        }
                        showAdditionalDetailsDialog();
                        dismiss();
                    }
                });

        return builder.create();
    }

    private void showAdditionalDetailsDialog() {
        Intent intent = new Intent(getActivity(), AddMeActivity.class);
        startActivity(intent);
    }
}

