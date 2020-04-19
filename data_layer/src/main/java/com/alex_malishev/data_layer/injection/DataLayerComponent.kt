package com.alex_malishev.data_layer.injection

import android.app.Application
import com.alex_malishev.data_layer.db.AppDb
import com.alex_malishev.data_layer.db.DaoProvider
import com.alex_malishev.data_layer.utils.ServiceGenerator
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Component(modules = [NetModule::class, DatabaseModule::class])
@Singleton
interface DataLayerComponent {
    fun context(): Application
    fun daoProvider(): DaoProvider
    fun serviceGenerator(): ServiceGenerator

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun netModule(netModule: NetModule):Builder
        fun databaseModule(databaseModule: DatabaseModule):Builder
        fun build(): DataLayerComponent
    }
}