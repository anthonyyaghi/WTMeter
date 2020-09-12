package com.example.wtmeter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final int DISPLAY_DATA_LENGTH = 20;
    GraphView graph;

    TextView temp1;
    ImageView active1, setting1;

    TextView temp2;
    ImageView active2, setting2;

    TextView temp3;
    ImageView active3, setting3;

    TextView temp4;
    ImageView active4, setting4;

    TextView temp5;
    ImageView active5, setting5;

    LineGraphSeries<DataPoint> series1 = new LineGraphSeries<>();
    LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>();
    LineGraphSeries<DataPoint> series3 = new LineGraphSeries<>();
    LineGraphSeries<DataPoint> series4 = new LineGraphSeries<>();
    LineGraphSeries<DataPoint> series5 = new LineGraphSeries<>();

    boolean isActive1, isActive2, isActive3, isActive4, isActive5;

    Handler temp1Handler, temp2Handler, temp3Handler, temp4Handler, temp5Handler;
    TempUpdater temp1Updater, temp2Updater, temp3Updater, temp4Updater, temp5Updater;

    SensorSettings sensorSettings1, sensorSettings2, sensorSettings3, sensorSettings4, sensorSettings5;

    long startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startTime = System.currentTimeMillis();

        graph = findViewById(R.id.graph);
        Viewport vp = graph.getViewport();
        vp.setXAxisBoundsManual(true);
        vp.setMinX(0);
        vp.setMaxX(DISPLAY_DATA_LENGTH);
        vp.setYAxisBoundsManual(true);
        vp.setMinY(0);
        vp.setMaxY(350);

        temp1 = findViewById(R.id.temp1);
        temp1.setTextColor(Color.BLACK);
        active1 = findViewById(R.id.active1);
        setting1 = findViewById(R.id.setting1);

        temp2 = findViewById(R.id.temp2);
        temp2.setTextColor(Color.RED);
        active2 = findViewById(R.id.active2);
        setting2 = findViewById(R.id.setting2);

        temp3 = findViewById(R.id.temp3);
        temp3.setTextColor(Color.BLUE);
        active3 = findViewById(R.id.active3);
        setting3 = findViewById(R.id.setting3);

        temp4 = findViewById(R.id.temp4);
        temp4.setTextColor(Color.GREEN);
        active4 = findViewById(R.id.active4);
        setting4 = findViewById(R.id.setting4);

        temp5 = findViewById(R.id.temp5);
        temp5.setTextColor(Color.MAGENTA);
        active5 = findViewById(R.id.active5);
        setting5 = findViewById(R.id.setting5);

        isActive1 = false;
        isActive2 = false;
        isActive3 = false;
        isActive4 = false;
        isActive5 = false;

        series1.setColor(Color.BLACK);
        series2.setColor(Color.RED);
        series3.setColor(Color.BLUE);
        series4.setColor(Color.GREEN);
        series5.setColor(Color.MAGENTA);

        sensorSettings1 = new SensorSettings("", 1000);
        sensorSettings2 = new SensorSettings("", 1000);
        sensorSettings3 = new SensorSettings("", 1000);
        sensorSettings4 = new SensorSettings("", 1000);
        sensorSettings5 = new SensorSettings("", 1000);

        temp1Handler = new Handler();
        temp1Updater = new TempUpdater(temp1Handler, sensorSettings1, temp1, series1);

        temp2Handler = new Handler();
        temp2Updater = new TempUpdater(temp2Handler, sensorSettings2, temp2, series2);

        temp3Handler = new Handler();
        temp3Updater = new TempUpdater(temp3Handler, sensorSettings3, temp3, series3);

        temp4Handler = new Handler();
        temp4Updater = new TempUpdater(temp4Handler, sensorSettings4, temp4, series4);

        temp5Handler = new Handler();
        temp5Updater = new TempUpdater(temp5Handler, sensorSettings5, temp5, series5);


        active1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isActive1) {
                    isActive1 = false;
                    graph.removeSeries(series1);
                    active1.setBackgroundColor(Color.TRANSPARENT);
                    temp1Handler.removeCallbacks(temp1Updater);
                    temp1.setText("-");
                } else {
                    isActive1 = true;
                    graph.addSeries(series1);
                    active1.setBackgroundColor(Color.GREEN);
                    temp1Handler.postDelayed(temp1Updater, sensorSettings1.getRefreshRate());
                }
            }
        });
        active2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isActive2) {
                    isActive2 = false;
                    graph.removeSeries(series2);
                    active2.setBackgroundColor(Color.TRANSPARENT);
                    temp2Handler.removeCallbacks(temp2Updater);
                    temp2.setText("-");
                } else {
                    isActive2 = true;
                    graph.addSeries(series2);
                    active2.setBackgroundColor(Color.GREEN);
                    temp2Handler.postDelayed(temp2Updater, sensorSettings2.getRefreshRate());
                }
            }
        });
        active3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isActive3) {
                    isActive3 = false;
                    graph.removeSeries(series3);
                    active3.setBackgroundColor(Color.TRANSPARENT);
                    temp3Handler.removeCallbacks(temp3Updater);
                    temp3.setText("-");
                } else {
                    isActive3 = true;
                    graph.addSeries(series3);
                    active3.setBackgroundColor(Color.GREEN);
                    temp3Handler.postDelayed(temp3Updater, sensorSettings3.getRefreshRate());
                }
            }
        });
        active4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isActive4) {
                    isActive4 = false;
                    graph.removeSeries(series4);
                    active4.setBackgroundColor(Color.TRANSPARENT);
                    temp4Handler.removeCallbacks(temp4Updater);
                    temp4.setText("-");
                } else {
                    isActive4 = true;
                    graph.addSeries(series4);
                    active4.setBackgroundColor(Color.GREEN);
                    temp4Handler.postDelayed(temp4Updater, sensorSettings4.getRefreshRate());
                }
            }
        });
        active5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isActive5) {
                    isActive5 = false;
                    graph.removeSeries(series5);
                    active5.setBackgroundColor(Color.TRANSPARENT);
                    temp5Handler.removeCallbacks(temp5Updater);
                    temp5.setText("-");
                } else {
                    isActive5 = true;
                    graph.addSeries(series5);
                    active5.setBackgroundColor(Color.GREEN);
                    temp5Handler.postDelayed(temp5Updater, sensorSettings5.getRefreshRate());
                }
            }
        });

        setting1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsDialog dialog = new SettingsDialog(sensorSettings1);
                dialog.show(getSupportFragmentManager(), "Sensor 1 Settings");
            }
        });

        setting2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsDialog dialog = new SettingsDialog(sensorSettings2);
                dialog.show(getSupportFragmentManager(), "Sensor 2 Settings");
            }
        });

        setting3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsDialog dialog = new SettingsDialog(sensorSettings3);
                dialog.show(getSupportFragmentManager(), "Sensor 3 Settings");
            }
        });

        setting4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsDialog dialog = new SettingsDialog(sensorSettings4);
                dialog.show(getSupportFragmentManager(), "Sensor 4 Settings");
            }
        });

        setting5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsDialog dialog = new SettingsDialog(sensorSettings5);
                dialog.show(getSupportFragmentManager(), "Sensor 5 Settings");
            }
        });
    }

    private void clearTempCallbacks() {
        if (temp1Handler != null) temp1Handler.removeCallbacksAndMessages(null);
        if (temp2Handler != null) temp2Handler.removeCallbacksAndMessages(null);
        if (temp3Handler != null) temp3Handler.removeCallbacksAndMessages(null);
        if (temp4Handler != null) temp4Handler.removeCallbacksAndMessages(null);
        if (temp5Handler != null) temp5Handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        clearTempCallbacks();
    }

    private class TempUpdater implements Runnable {
        private Handler handler;
        private SensorSettings sensorSettings;
        private TextView tv;
        private LineGraphSeries<DataPoint> series;

        public TempUpdater(Handler handler, SensorSettings sensorSettings, TextView tv, LineGraphSeries<DataPoint> series) {
            this.handler = handler;
            this.sensorSettings = sensorSettings;
            this.tv = tv;
            this.series = series;
        }

        @Override
        public void run() {
            try {
                //TODO get data from IP address
                Random rand = new Random();
                int y = rand.nextInt(300);

                double x = (System.currentTimeMillis() - startTime) / 1000.0;
                series.appendData(new DataPoint(x, y),
                        series.getHighestValueX() > DISPLAY_DATA_LENGTH,
                        DISPLAY_DATA_LENGTH);
                tv.setText(Integer.toString(y));
            } finally {
                this.handler.postDelayed(this, sensorSettings.getRefreshRate());
            }
        }
    }

}
