package my.assigment.neverbored.di


import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import my.assigment.neverbored.Constansts
import my.assigment.neverbored.viewModel.MainViewModel
import my.assigment.neverbored.api.TmdbApiService
import my.assigment.neverbored.db.TvShowsDatabase
import my.assigment.neverbored.repositories.MainRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideApi(): TmdbApiService = Retrofit.Builder()
        .baseUrl(Constansts.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(TmdbApiService::class.java)


    @Singleton
    @Provides
    fun provideMainRepository(@ApplicationContext app: Context): MainRepository{
        return MainRepository(provideApi(), provideDao(provideTvShowsDatabase(app)))
    }

    @Singleton
    @Provides
    fun provideMainViewModel(@ApplicationContext app: Context): MainViewModel{
        return MainViewModel(provideMainRepository(app))
    }

    @Singleton
    @Provides
    fun provideTvShowsDatabase(@ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        TvShowsDatabase::class.java,
        "tv_shows_db"
    ).fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideDao(db: TvShowsDatabase) = db.getShowsDao()
}