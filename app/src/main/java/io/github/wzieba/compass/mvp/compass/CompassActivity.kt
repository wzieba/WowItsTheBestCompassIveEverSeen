package io.github.wzieba.compass.mvp.compass

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import io.github.wzieba.compass.NavigatorApplication
import io.github.wzieba.compass.mvp.compass.di.CompassComponent
import io.github.wzieba.compass.mvp.compass.di.CompassModule
import io.github.wzieba.compass.mvp.compass.di.DaggerCompassComponent
import io.github.wzieba.compass.mvp.compass.presenter.CompassPresenter
import io.github.wzieba.compass.mvp.compass.view.CompassView
import java.io.Serializable
import java.util.*
import javax.inject.Inject

const val VIEW_MODEL = "viewModel"

class CompassActivity : AppCompatActivity() {

    private lateinit var activityComponent: CompassComponent

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
                        presenter.onCreate(getStateFromBundle(savedInstanceState))
                        setContentView(view.asView())
                    } else {
                        finish()
                    }
                }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        val bundleToSave = getBundleFromState(presenter.getStateForSave())
        if (bundleToSave != null)
            outState?.putAll(bundleToSave)
    }

    override fun onResume() {
        super.onResume()
        if (rxPermissions.isGranted(Manifest.permission.ACCESS_COARSE_LOCATION))
            presenter.onResume()
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    private fun getStateFromBundle(bundle: Bundle?): Map<String, Serializable>? {
        if (bundle == null || bundle.isEmpty) {
            return null
        }
        val state = HashMap<String, Serializable>()
        bundle.keySet().forEach { key ->
            val value = bundle.getSerializable(key)
            if (value != null) {
                state[key] = value
            }
        }
        return state
    }

    private fun getBundleFromState(state: Map<String, Serializable>?): Bundle? {
        if (state == null || state.isEmpty()) {
            return null
        }
        val bundle = Bundle()
        state.entries.forEach { entry ->
            bundle.putSerializable(entry.key, entry.value)
        }
        return bundle
    }
}
