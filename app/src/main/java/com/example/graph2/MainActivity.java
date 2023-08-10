package com.example.graph2;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends Activity implements View.OnClickListener {

    private SurfaceView graphCanvas;
    private Button addDataButton;

    private SurfaceHolder holder;
    private Paint paint;
    private List<Float> dataPoints;
    private List<Long> timestamps;
    private Random random;

    private float startX = 0;
    private float offsetX = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        graphCanvas = findViewById(R.id.graphCanvas);
        addDataButton = findViewById(R.id.addDataButton);

        addDataButton.setOnClickListener(this);

        holder = graphCanvas.getHolder();
        paint = new Paint();
        dataPoints = new ArrayList<>();
        timestamps = new ArrayList<>();
        random = new Random();

        Button moveLeftButton = findViewById(R.id.moveLeftButton);
        Button moveRightButton = findViewById(R.id.moveRightButton);

        moveLeftButton.setOnClickListener(this);
        moveRightButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.addDataButton) {
            float randomValue = random.nextFloat() * 100; // Generate a random value between 0 and 100
            dataPoints.add(randomValue);
            timestamps.add(System.currentTimeMillis()); // Add current timestamp
            System.out.println("Added random value: " + randomValue + " at time: " + getCurrentTime());
            drawGraph();
        } else if (v.getId() == R.id.moveLeftButton) {
            offsetX += 20; // Move the graph to the left by 20 pixels
            drawGraph();
        } else if (v.getId() == R.id.moveRightButton) {
            offsetX -= 20; // Move the graph to the right by 20 pixels
            drawGraph();
        }
    }

    private String getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        return dateFormat.format(new Date());
    }

    private void drawGraph() {
        if (holder.getSurface().isValid()) {
            Canvas canvas = holder.lockCanvas();
            canvas.drawColor(Color.WHITE); // Clear canvas

            paint.setColor(Color.LTGRAY);
            for (int i = 1; i <= 10; i++) {
                float y = i * graphCanvas.getHeight() / 10;
                canvas.drawLine(0, y, graphCanvas.getWidth(), y, paint);

                // Draw gradation numbers on the Y-axis
                paint.setColor(Color.BLACK);
                float value = 100 - (i * 10); // Assuming the range is 0-100
                canvas.drawText(String.valueOf(value), 10, y - 10, paint);
            }

            paint.setColor(Color.BLUE);
            float x = startX + offsetX;
            float lastY = graphCanvas.getHeight() - dataPoints.get(0);

            int distanceBetweenPoints = 60; // Set the desired distance between points

            for (int i = 0; i < dataPoints.size(); i++) {
                float y = graphCanvas.getHeight() - dataPoints.get(i);
                canvas.drawLine(x, lastY, x + distanceBetweenPoints, y, paint);
                x += distanceBetweenPoints;
                lastY = y;

                // Draw timestamp on the X-axis
                paint.setColor(Color.BLACK);
                canvas.drawText(getCurrentTimeFromTimestamp(timestamps.get(i)), x - 10, graphCanvas.getHeight() - 10, paint);
            }

            holder.unlockCanvasAndPost(canvas);
        }
    }

    private String getCurrentTimeFromTimestamp(long timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        return dateFormat.format(new Date(timestamp));
    }
}