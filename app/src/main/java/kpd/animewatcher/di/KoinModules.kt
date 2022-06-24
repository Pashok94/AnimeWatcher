package kpd.animewatcher.di

import com.google.gson.GsonBuilder
import kpd.animewatcher.model.repository.IRepository
import kpd.animewatcher.model.repository.RepositoryImpl
import kpd.animewatcher.model.repository.remoteRepository.RetryCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import kpd.animewatcher.model.repository.localRepository.ILocalRepository
import kpd.animewatcher.model.repository.localRepository.LocalRepositoryImpl
import kpd.animewatcher.model.repository.remoteRepository.AnimeApi
import kpd.animewatcher.model.repository.remoteRepository.IRemoteRepository
import kpd.animewatcher.model.repository.remoteRepository.RemoteRepositoryImpl
import kpd.animewatcher.viewModel.viewModels.MainViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val remoteRepositoryModule = module {
    fun provideRemoteRepository(
        minerApi: AnimeApi
    ): IRemoteRepository {
        return RemoteRepositoryImpl(minerApi)
    }

    fun provideAnimeApi(
        client: OkHttpClient,
        baseUrl: String,
        callAdapterFactory: RetryCallAdapterFactory
    ): AnimeApi {
        return Retrofit.Builder()
            .baseUrl(
                baseUrl
            )
            .addCallAdapterFactory(callAdapterFactory)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setLenient().create()
                )
            )
            .client(client)
            .build().create(AnimeApi::class.java)
    }

    fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor)
        httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        return httpClient.build()
    }

    fun provideInterceptor(): Interceptor {
        return Interceptor {
            it.proceed(it.request())
        }
    }

    fun provideRetryCallFactory(): RetryCallAdapterFactory {
        return RetryCallAdapterFactory()
    }

    fun provideAnimeBaseUrl(): String {
        return "https://api.aniapi.com"
    }

    single(named("animeBaseUrl")) { provideAnimeBaseUrl() }

    single { provideInterceptor() }

    single { provideOkHttpClient(interceptor = get()) }

    single { provideRetryCallFactory() }

    single {
        provideAnimeApi(
            client = get(),
            baseUrl = get((named("animeBaseUrl"))),
            callAdapterFactory = get()
        )
    }

    single {
        provideRemoteRepository(
            minerApi = get()
        )
    }
}

val localRepositoryModule = module {
    fun provideLocalRepository(): ILocalRepository {
        return LocalRepositoryImpl()
    }

    single { provideLocalRepository() }
}

val repositoryModule = module {
    fun provideRepository(
        localRepository: ILocalRepository,
        remoteRepository: IRemoteRepository
    ): IRepository = RepositoryImpl(localRepository, remoteRepository)

    single {
        provideRepository(
            localRepository = get(),
            remoteRepository = get()
        )
    }
}

val viewModelModule = module {
    viewModel { MainViewModel(repository = get()) }
}