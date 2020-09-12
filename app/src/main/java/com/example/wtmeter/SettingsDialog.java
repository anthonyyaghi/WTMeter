package com.example.wtmeter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class SettingsDialog extends AppCompatDialogFragment {
    private EditText ipET, refreshET;
    private SensorSettings sensorSettings;

    public SettingsDialog(SensorSettings sensorSettings) {
        this.sensorSettings = sensorSettings;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        builder.setView(view).setTitle("Settings")
        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        })
        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sensorSettings.setIp(ipET.getText().toString());
                sensorSettings.setRefreshRate(Long.parseLong(refreshET.getText().toString()));
            }
        });

        ipET = view.findViewById(R.id.ipET);
        ipET.setText(sensorSettings.getIp());
        refreshET = view.findViewById(R.id.refreshET);
        refreshET.setText(Long.toString(sensorSettings.getRefreshRate()));

        return builder.create();
    }
}
