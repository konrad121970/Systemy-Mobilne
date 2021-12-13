package com.konrad.sensorapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SensorActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SensorAdapter adapter;
    private static final String KEY_SUBTITLE_VISIBLE = "subtitleVisible";
    private boolean subtitleVisible;
    private SensorManager sensorManager;
    private List<SensorWrapper> sensorList;
    public static final String SENSOR_APP_TAG = "SensorActivity";
    public static final String KEY_EXTRA_SENSOR_INDEX = "SensorActivity.sensor";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensor_activity);
        if (savedInstanceState != null) {
            subtitleVisible = savedInstanceState.getBoolean(KEY_SUBTITLE_VISIBLE);
        }

        recyclerView = findViewById(R.id.sensor_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        sensorList = new ArrayList<SensorWrapper>();
        for (int i = 0; i < sensors.size(); ++i) {
            Sensor s = sensors.get(i);
            Log.d(SENSOR_APP_TAG, s.getName() + "; " + s.getVendor() + "; " + s.getMaximumRange());
            sensorList.add(new SensorWrapper(s, i, false));
        }
        sensorList.get(30).setSelectable(true);
        sensorList.get(35).setSelectable(true);

        if (adapter == null) {
            adapter = new SensorAdapter(sensorList);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
        updateSubtitle();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sensor_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
            case R.id.show_subtitle:
                subtitleVisible = !subtitleVisible;
                invalidateOptionsMenu();
                updateSubtitle();
                return true;
        }
    }

    public void updateSubtitle() {
        String subtitle = null;
        if (subtitleVisible)
            subtitle = getString(R.string.subtitle_format, sensorList.size());
        getSupportActionBar().setSubtitle(subtitle);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_SUBTITLE_VISIBLE, subtitleVisible);
    }

    private class SensorHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView iconImageView;
        private TextView nameTextView;
        private SensorWrapper sensor;

        public SensorHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.sensor_list_item, parent, false));
            itemView.setOnClickListener(this);

            iconImageView = itemView.findViewById(R.id.sensor_icon);
            nameTextView = itemView.findViewById(R.id.sensor_name);
        }

        public void bind(SensorWrapper sensor) {
            this.sensor = sensor;
            iconImageView.setImageResource(R.drawable.ic_sensor);
            nameTextView.setText(sensor.getSensor().getName());
            if (sensor.isSelectable())
                nameTextView.setBackgroundColor(Color.rgb(181, 181, 13));
            else
                nameTextView.setBackgroundColor(Color.TRANSPARENT);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(SensorActivity.this, SensorDetailsActivity.class);
            intent.putExtra(KEY_EXTRA_SENSOR_INDEX, sensor.getIndex());
            startActivity(intent);
        }
    }

    private class SensorAdapter extends RecyclerView.Adapter<SensorHolder> {
        private List<SensorWrapper> sensors;

        public SensorAdapter(List<SensorWrapper> tasks) {
            this.sensors = tasks;
        }

        @NonNull
        @Override
        public SensorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(SensorActivity.this);
            return new SensorHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull SensorHolder holder, int position) {
            SensorWrapper sensor = sensors.get(position);
            holder.bind(sensor);
        }

        @Override
        public int getItemCount() {
            return sensors.size();
        }
    }
}