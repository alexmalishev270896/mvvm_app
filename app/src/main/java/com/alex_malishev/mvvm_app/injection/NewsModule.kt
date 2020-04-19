package com.alex_malishev.mvvm_app.injection

import com.alex_malishev.data_layer.db.DaoProvider
import com.alex_malishev.data_layer.db.NewsDao
import com.alex_malishev.data_layer.local.INewsLocalSource
import com.alex_malishev.data_layer.local.NewsLocalSource
import com.alex_malishev.data_layer.remote.INewsRemoteSource
import com.alex_malishev.data_layer.repository.NewsRepository
import com.alex_malishev.data_layer.utils.ServiceGenerator
import com.alex_malishev.domain_layer.repository.news.INewsRepository
import dagger.Module
import dagger.Provides

@Module
class NewsModule {

    @Provides
    fun provideNewsRepository(
        newsLocalSource: INewsLocalSource,
        newsRemoteSource: INewsRemoteSource
    ): INewsRepository {
        return NewsRepository(newsRemoteSource, newsLocalSource)
    }

    @Provides
    fun provideNewsLocalSource(newsDao: NewsDao): INewsLocalSource {
        return NewsLocalSource(newsDao)
    }

    @Provides
    fun provideNewsDao(daoProvider: DaoProvider) = daoProvider.newsDao()

    @Provides
    fun provideNewsRemoteSource(serviceGenerator: ServiceGenerator): INewsRemoteSource {
        return serviceGenerator.createService(INewsRemoteSource::class.java)
    }
}