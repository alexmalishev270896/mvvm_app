package com.alex_malishev.mvvm_app.injection

import android.app.Application
import com.alex_malishev.data_layer.injection.DataLayerComponent
import com.alex_malishev.mvvm_app.MvvmApplication
import com.alex_malishev.mvvm_app.injection.qualifiers.PerApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@PerApp
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    ActivityModule::class,
    ViewModelModule::class,
    SchedulerModule::class
], dependencies = [DataLayerComponent::class])
interface AppComponent: AndroidInjector<MvvmApplication> {

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(application: Application): Builder
        fun dataLayer(dataLayerComponent: DataLayerComponent):Builder
        fun build(): AppComponent
    }
}