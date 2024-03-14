package com.example.myapplication

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.health.connect.datatypes.units.Pressure
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), SensorEventListener {

    lateinit var sensorManager: SensorManager
    var lightSensor: Sensor? = null
    var pressureSensor: Sensor? = null
    var rotateSensor: Sensor? = null
    lateinit var lighttv: TextView
    lateinit var pressuretv: TextView
    lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        val list: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)
        println("Size=${list.size}")
        println(list.joinToString("\n"))

        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        if(lightSensor != null) {
            sensorManager.registerListener(this,lightSensor,2)
        }
        lighttv = findViewById(R.id.lightText)
        pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)
        if(pressureSensor != null) {
            sensorManager.registerListener(this,pressureSensor,2)
        }
        pressuretv = findViewById(R.id.pressureText)
        rotateSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        if(rotateSensor != null) {
            sensorManager.registerListener(this,rotateSensor,2)
        }
        imageView = findViewById(R.id.imageView)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(lightSensor == null) {
            lighttv.text = "No light sensor!"
        } else if(event!!.sensor.type == Sensor.TYPE_LIGHT) {
            lighttv.text = "Light: ${event.values[0]}"
        }
        if(pressureSensor == null) {
            pressuretv.text = "No pressure sensor!"
        } else if(event!!.sensor.type == Sensor.TYPE_PRESSURE) {
            pressuretv.text = "Pressure: ${event.values[0]}"
        }
        if(event!!.sensor.type == Sensor.TYPE_GYROSCOPE) {
            imageView.rotationX = event.values[0]*10
            imageView.rotationY = event.values[1]*10
            imageView.rotation = event.values[2]*10
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

}