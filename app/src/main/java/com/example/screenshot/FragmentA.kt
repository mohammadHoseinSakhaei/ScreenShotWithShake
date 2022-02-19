package com.example.screenshot

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import java.util.*

class FragmentA :Fragment() {

    private val REQUEST_EXTERNAL_STORAGe = 1
    private val permissionstorage = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    private var mSensorManager: SensorManager?=null
    private var mAccel = 0f
    private var mAccelCurrent = 0f
    private var mAccelLast = 0f
    private var b :Bitmap?= null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_a,container,false)
    }


    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        this.mSensorManager  = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mSensorManager?.registerListener(
            mSensorListener, mSensorManager?.getDefaultSensor(
                Sensor.TYPE_ACCELEROMETER
            ),
            SensorManager.SENSOR_DELAY_NORMAL
        )
        mAccel = 10f
        mAccelCurrent = SensorManager.GRAVITY_EARTH
        mAccelLast = SensorManager.GRAVITY_EARTH




        verifystoragepermissions(requireActivity())
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        val btn : Button = view.findViewById(R.id.clickme)
        btn.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_fragmentA_to_fraagmentB,sendData(b))
        }

        val btn2: Button = view.findViewById(R.id.clickandgotoshake)
        btn2.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_fragmentA_to_shakeActivity)
        }

        val btn3: Button = view.findViewById(R.id.gotoC)
        btn3.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_fragmentA_to_fragmentC)
        }
        super.onViewCreated(view, savedInstanceState)
    }


    private val mSensorListener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]
            mAccelLast = mAccelCurrent
            mAccelCurrent = Math.sqrt((x * x + y * y + z * z).toDouble()).toFloat()
            val delta = mAccelCurrent - mAccelLast
            mAccel = mAccel * 0.9f + delta
            if (mAccel > 12) {
                view?.let {
                    b = ScreenShot.takescreenshotOfRootView(it)
                }

            }
        }

        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
    }

    override fun onResume() {
        mSensorManager!!.registerListener(
            mSensorListener, mSensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_NORMAL
        )
        super.onResume()
    }

    override fun onPause() {
        mSensorManager!!.unregisterListener(mSensorListener)
        super.onPause()
    }


    private fun verifystoragepermissions(activity: Activity?) {
        val permissions = ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        // If storage permission is not given then request for External Storage Permission
        if (permissions != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), permissionstorage, REQUEST_EXTERNAL_STORAGe)
        }
    }

    fun sendData(bitMap:Bitmap?):Bundle{
        val bundle = Bundle()
        bundle.putParcelable("bit",bitMap)
        return bundle
    }
}