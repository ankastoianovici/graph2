/*package com.example.graph2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private GraphView scatterPlot;
    private LineGraphSeries<DataPoint> series;
    private int xAxisValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scatterPlot = findViewById(R.id.lineChart);
        Button addButton = findViewById(R.id.addButton);

        series = new LineGraphSeries<>();
        scatterPlot.addSeries(series);

        scatterPlot.getViewport().setScalable(true);
        scatterPlot.getViewport().setScrollable(true);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float randomValue = new Random().nextInt(101);
                addPoint(randomValue);
            }
        });
    }

    private void addPoint(float value) {
        series.appendData(new DataPoint(xAxisValue++, value), true, 100);
    }
}
*/
/*package com.example.graph2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private GraphView scatterPlot;
    private LineGraphSeries<DataPoint> series;
    private double timeValue = 0; // Time value in seconds
    private final double TIME_INTERVAL = 1.0; // Time interval between data points in seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scatterPlot = findViewById(R.id.lineChart);
        Button addButton = findViewById(R.id.addButton);

        series = new LineGraphSeries<>();
        scatterPlot.addSeries(series);

        scatterPlot.getViewport().setScalable(true);
        scatterPlot.getViewport().setScrollable(true);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float randomValue = new Random().nextInt(101);
                addPoint(randomValue);
            }
        });
    }

    private void addPoint(float value) {
        series.appendData(new DataPoint(timeValue, value), true, 100);
        timeValue += TIME_INTERVAL;

        // Update viewport to display the latest data points
        double maxX = timeValue;
        double minX = Math.max(0, maxX - 10); // Show the last 10 seconds of data
        scatterPlot.getViewport().setMinX(minX);
        scatterPlot.getViewport().setMaxX(maxX);
        scatterPlot.onDataChanged(true, true);
    }
}*/
package com.example.graph2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private GraphView scatterPlot;
    private LineGraphSeries<DataPoint> series;
    private long xAxisValue = 0; // Time value in milliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scatterPlot = findViewById(R.id.lineChart);
        Button addButton = findViewById(R.id.addButton);

        series = new LineGraphSeries<>();
        scatterPlot.addSeries(series);

        scatterPlot.getViewport().setScalable(true);
        scatterPlot.getViewport().setScrollable(true);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float randomValue = new Random().nextInt(101);
                addPoint(System.currentTimeMillis(), randomValue);
            }
        });

        // Customize y-axis label formatter
        scatterPlot.getGridLabelRenderer().setLabelFormatter(new MyLabelFormatter());
    }

    private class MyLabelFormatter extends DefaultLabelFormatter {
        private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

        @Override
        public String formatLabel(double value, boolean isValueX) {
            if (isValueX) {
                // Format x-axis time
                return dateFormat.format(new Date((long) value));
            } else {
                // Format y-axis value
                return super.formatLabel(value, isValueX);
            }
        }
    }

    private void addPoint(long timeMillis, float value) {
        series.appendData(new DataPoint(timeMillis, value), true, 100);
    }
}
