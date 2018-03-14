package io.github.wzieba.compass.mvp.compass

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.github.wzieba.compass.NavigatorApplication
import io.github.wzieba.compass.mvp.compass.di.CompassComponent
import io.github.wzieba.compass.mvp.compass.di.CompassModule
import io.github.wzieba.compass.mvp.compass.di.DaggerCompassComponent
import io.github.wzieba.compass.mvp.compass.presenter.CompassPresenter
import io.github.wzieba.compass.mvp.compass.view.CompassView
import javax.inject.Inject

class CompassActivity : AppCompatActivity() {

    private lateinit var activityComponent: CompassComponent

    @Inject
    lateinit var presenter: CompassPresenter

    @Inject
    lateinit var view: CompassView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityComponent = DaggerCompassComponent.builder()
                .applicationComponent(NavigatorApplication.instance.applicationComponent)
                .compassModule(CompassModule(this))
                .build()

        activityComponent.inject(this)

        setContentView(view.asView())
    }
}
