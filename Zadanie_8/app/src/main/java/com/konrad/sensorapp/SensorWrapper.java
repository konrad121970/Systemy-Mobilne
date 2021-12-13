package com.konrad.sensorapp;

import android.hardware.Sensor;
import lombok.Getter;
import lombok.Setter;

@Getter
public class SensorWrapper {
    private Sensor sensor;
    private int index;
    @Setter
    private boolean selectable;

    public SensorWrapper(Sensor sensor, int index, boolean selectable) {
        this.sensor = sensor;
        this.index = index;
        this.selectable = selectable;
    }
}
