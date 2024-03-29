
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object PokemonApiClient {

    private const val BASE_URL = "https://pokeapi.co/api/v2/"

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .build()
            chain.proceed(newRequest)
        }
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val pokemonApiService: PokemonApiService by lazy {
        retrofit.create(PokemonApiService::class.java)
    }
}
