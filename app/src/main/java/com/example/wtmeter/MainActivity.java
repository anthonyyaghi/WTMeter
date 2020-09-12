package com.example.wtmeter;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    final ScheduledExecutorService[] worker1 = {Executors.newSingleThreadScheduledExecutor()};
    final ScheduledExecutorService[] worker2 = {Executors.newSingleThreadScheduledExecutor()};
    final ScheduledExecutorService[] worker3 = {Executors.newSingleThreadScheduledExecutor()};
    final ScheduledExecutorService[] worker4 = {Executors.newSingleThreadScheduledExecutor()};
    final ScheduledExecutorService[] worker5 = {Executors.newSingleThreadScheduledExecutor()};

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

        sensorSettings1 = new SensorSettings("", 1000, "sensor1");
        sensorSettings2 = new SensorSettings("", 1000, "sensor2");
        sensorSettings3 = new SensorSettings("", 1000, "sensor3");
        sensorSettings4 = new SensorSettings("", 1000, "sensor4");
        sensorSettings5 = new SensorSettings("", 1000, "sensor5");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            readSettings(sensorSettings1);
            readSettings(sensorSettings2);
            readSettings(sensorSettings3);
            readSettings(sensorSettings4);
            readSettings(sensorSettings5);
        }


        temp1Updater = new TempUpdater(sensorSettings1, temp1, series1);
        temp2Updater = new TempUpdater(sensorSettings2, temp2, series2);
        temp3Updater = new TempUpdater(sensorSettings3, temp3, series3);
        temp4Updater = new TempUpdater(sensorSettings4, temp4, series4);
        temp5Updater = new TempUpdater(sensorSettings5, temp5, series5);


        active1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isActive1) {
                    isActive1 = false;
                    graph.removeSeries(series1);
                    active1.setBackgroundColor(Color.TRANSPARENT);
                    worker1[0].shutdown();
                    temp1.setText("-");
                } else {
                    isActive1 = true;
                    graph.addSeries(series1);
                    active1.setBackgroundColor(Color.GREEN);
                    worker1[0] = Executors.newSingleThreadScheduledExecutor();
                    worker1[0].scheduleAtFixedRate(temp1Updater, 0, sensorSettings1.getRefreshRate(), TimeUnit.MILLISECONDS);
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
                    worker2[0].shutdown();
                    temp2.setText("-");
                } else {
                    isActive2 = true;
                    graph.addSeries(series2);
                    active2.setBackgroundColor(Color.GREEN);
                    worker2[0] = Executors.newSingleThreadScheduledExecutor();
                    worker2[0].scheduleAtFixedRate(temp2Updater, 0, sensorSettings2.getRefreshRate(), TimeUnit.MILLISECONDS);
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
                    worker3[0].shutdown();
                    temp3.setText("-");
                } else {
                    isActive3 = true;
                    graph.addSeries(series3);
                    active3.setBackgroundColor(Color.GREEN);
                    worker3[0] = Executors.newSingleThreadScheduledExecutor();
                    worker3[0].scheduleAtFixedRate(temp3Updater, 0, sensorSettings3.getRefreshRate(), TimeUnit.MILLISECONDS);
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
                    worker4[0].shutdown();
                    temp4.setText("-");
                } else {
                    isActive4 = true;
                    graph.addSeries(series4);
                    active4.setBackgroundColor(Color.GREEN);
                    worker4[0] = Executors.newSingleThreadScheduledExecutor();
                    worker4[0].scheduleAtFixedRate(temp4Updater, 0, sensorSettings4.getRefreshRate(), TimeUnit.MILLISECONDS);
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
                    worker5[0].shutdown();
                    temp5.setText("-");
                } else {
                    isActive5 = true;
                    graph.addSeries(series5);
                    active5.setBackgroundColor(Color.GREEN);
                    worker5[0] = Executors.newSingleThreadScheduledExecutor();
                    worker5[0].scheduleAtFixedRate(temp5Updater, 0, sensorSettings5.getRefreshRate(), TimeUnit.MILLISECONDS);
                }
            }
        });


        setting1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsDialog dialog = new SettingsDialog(sensorSettings1, getFilesDir().toString());
                dialog.show(getSupportFragmentManager(), "Sensor 1 Settings");
            }
        });

        setting2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsDialog dialog = new SettingsDialog(sensorSettings2, getFilesDir().toString());
                dialog.show(getSupportFragmentManager(), "Sensor 2 Settings");
            }
        });

        setting3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsDialog dialog = new SettingsDialog(sensorSettings3, getFilesDir().toString());
                dialog.show(getSupportFragmentManager(), "Sensor 3 Settings");
            }
        });

        setting4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsDialog dialog = new SettingsDialog(sensorSettings4, getFilesDir().toString());
                dialog.show(getSupportFragmentManager(), "Sensor 4 Settings");
            }
        });

        setting5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsDialog dialog = new SettingsDialog(sensorSettings5, getFilesDir().toString());
                dialog.show(getSupportFragmentManager(), "Sensor 5 Settings");
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void readSettings(SensorSettings sensorSettings) {
        try (
                InputStream file = new FileInputStream(getFilesDir() + "/" + sensorSettings.getName());
                InputStream buffer = new BufferedInputStream(file);
                ObjectInput input = new ObjectInputStream(buffer);
        ) {
            SensorSettings saved = (SensorSettings) input.readObject();
            sensorSettings.setIp(saved.getIp());
            sensorSettings.setRefreshRate(saved.getRefreshRate());
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void clearTempCallbacks() {
        try {
            worker1[0].shutdown();
            worker2[0].shutdown();
            worker3[0].shutdown();
            worker4[0].shutdown();
            worker5[0].shutdown();
        } catch (Exception e) {

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        clearTempCallbacks();
    }

    private class TempUpdater implements Runnable {
        private SensorSettings sensorSettings;
        private TextView tv;
        private LineGraphSeries<DataPoint> series;

        public TempUpdater(SensorSettings sensorSettings, TextView tv, LineGraphSeries<DataPoint> series) {
            this.sensorSettings = sensorSettings;
            this.tv = tv;
            this.series = series;
        }

        @Override
        public void run() {
            final double y = Double.parseDouble(getTemperature());
            double x = (System.currentTimeMillis() - startTime) / 1000.0;
            series.appendData(new DataPoint(x, y),
                    series.getHighestValueX() > DISPLAY_DATA_LENGTH,
                    (int) (DISPLAY_DATA_LENGTH * 1000 / sensorSettings.getRefreshRate()));
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tv.setText(Double.toString(y));
                }
            });
        }

        public String getTemperature() {
            HttpURLConnection c = null;
            try {
                URL u = new URL(sensorSettings.getIp());
                c = (HttpURLConnection) u.openConnection();
                c.setRequestMethod("GET");
                c.setRequestProperty("Content-length", "0");
                c.setUseCaches(false);
                c.setAllowUserInteraction(false);
                c.setConnectTimeout(5000);
                c.setReadTimeout(5000);
                c.connect();
                int status = c.getResponseCode();

                switch (status) {
                    case 200:
                    case 201:
                        BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        br.close();
                        return sb.toString();
                }

            } catch (MalformedURLException ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (c != null) {
                    try {
                        c.disconnect();
                    } catch (Exception ex) {
                        Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            return null;
        }
    }

}
