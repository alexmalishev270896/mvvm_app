package com.alex_malishev.data_layer

import android.app.Application
import com.alex_malishev.data_layer.injection.DaggerDataLayerComponent
import com.alex_malishev.data_layer.injection.DataLayerComponent
import com.alex_malishev.data_layer.injection.DatabaseModule
import com.alex_malishev.data_layer.injection.NetModule
import com.alex_malishev.data_layer.utils.registerConnectivityMonitor

object DataLayer {
    const val API_KEY = "cb2ed900351d4bf9ac59509b448cc82d"

    private lateinit var context: Application

    @JvmStatic
    fun init(context: Application): DataLayerComponent {
        this.context = context
        context.registerConnectivityMonitor()
        return createDataLayerComponent(context)
    }

    @JvmStatic
    fun get() = context

    private fun createDataLayerComponent(context: Application): DataLayerComponent {
        return DaggerDataLayerComponent.builder()
            .application(context)
            .netModule(NetModule)
            .databaseModule(DatabaseModule)
            .build()
    }
}