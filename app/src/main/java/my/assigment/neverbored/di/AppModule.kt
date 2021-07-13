package my.assigment.neverbored.di


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import my.assigment.neverbored.Constransts
import my.assigment.neverbored.viewModel.MainViewModel
import my.assigment.neverbored.api.TmdbApiService
import my.assigment.neverbored.repositories.MainRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApi(): TmdbApiService = Retrofit.Builder()
        .baseUrl(Constransts.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(TmdbApiService::class.java)


    @Singleton
    @Provides
    fun provideMainRepository(): MainRepository{
        return MainRepository(provideApi())
    }

    @Singleton
    @Provides
    fun provideMainViewModel(): MainViewModel{
        return MainViewModel(provideMainRepository())
    }
}