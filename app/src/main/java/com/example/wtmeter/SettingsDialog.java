package com.example.wtmeter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class SettingsDialog extends AppCompatDialogFragment {
    private EditText ipET, refreshET;
    private SensorSettings sensorSettings;
    String filesDir;

    public SettingsDialog(SensorSettings sensorSettings, String filesDir) {
        this.sensorSettings = sensorSettings;
        this.filesDir = filesDir;
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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    saveSettings();
                }
            }
        });

        ipET = view.findViewById(R.id.ipET);
        ipET.setText(sensorSettings.getIp());
        refreshET = view.findViewById(R.id.refreshET);
        refreshET.setText(Long.toString(sensorSettings.getRefreshRate()));

        return builder.create();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void saveSettings() {
        try (
                OutputStream file = new FileOutputStream(filesDir + "/" + sensorSettings.getName());
                OutputStream buffer = new BufferedOutputStream(file);
                ObjectOutput output = new ObjectOutputStream(buffer);
        ){
            output.writeObject(sensorSettings);
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}
