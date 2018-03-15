package io.github.wzieba.compass.mvp.compass

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.android.gms.location.*
import com.tbruyelle.rxpermissions2.RxPermissions
import io.github.wzieba.compass.NavigatorApplication
import io.github.wzieba.compass.mvp.compass.di.CompassComponent
import io.github.wzieba.compass.mvp.compass.di.CompassModule
import io.github.wzieba.compass.mvp.compass.di.DaggerCompassComponent
import io.github.wzieba.compass.mvp.compass.presenter.CompassPresenter
import io.github.wzieba.compass.mvp.compass.view.CompassView
import javax.inject.Inject

class CompassActivity : AppCompatActivity() {

    private lateinit var activityComponent: CompassComponent
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    @Inject
    lateinit var presenter: CompassPresenter
    @Inject
    lateinit var view: CompassView
    @Inject
    lateinit var rxPermissions: RxPermissions

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityComponent = DaggerCompassComponent.builder()
                .applicationComponent(NavigatorApplication.instance.applicationComponent)
                .compassModule(CompassModule(this))
                .build()

        activityComponent.inject(this)

        rxPermissions
                .request(Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe { granted: Boolean ->
                    if (granted) {
                        setContentView(view.asView())
                        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
                        fusedLocationClient.requestLocationUpdates(LocationRequest.create(), object : LocationCallback() {
                            override fun onLocationResult(locationResult: LocationResult) {
                                super.onLocationResult(locationResult)
                                Log.d("Test", locationResult.toString())
                            }
                        }, null)
                    } else {
                        finish()
                    }
                }
    }


    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }
}
