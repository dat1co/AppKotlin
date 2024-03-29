package com.example.myapplication

import PokemonApiClient
import PokemonDetails
import android.R.attr.contentDescription
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PokemonList(this)
                }
        }
    }
}

data class Pokemon(
    val name: String,
    val img: String,
    val types: List<String>,
    val height: Int,
    val weight: Int
)

@Composable
fun PokemonList(context: Context) {
    var searchText by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }
    var selectedPokemon: PokemonResponse? by remember { mutableStateOf(null) }

    var pokemons by remember { mutableStateOf<List<PokemonResponse>>(emptyList()) }

    LaunchedEffect(Unit) {
        pokemons = (1..10).map { id ->
            PokemonApiClient.pokemonApiService.getPokemon(id)
        }
    }


    LazyColumn(
        modifier = Modifier.padding(16.dp)
    ) {
        items(pokemons) { pokemon ->
            PokemonListItem(pokemon = pokemon, context = context) { selectedPokemon = it }
        }
    }

    selectedPokemon?.let {
        PokemonDetails(pokemon = it) {
            selectedPokemon = null
        }
    }
}

@Composable
fun GlideImage(
    modifier: Modifier = Modifier,
    imageURL: String,
    contentDiscriptuon: String,
) {
    val density = LocalDensity.current.density
    val requestOptions = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .override((50 * density).toInt(), (50 * density).toInt())
        .centerCrop()

    Image(
        painter = rememberImagePainter(
            data = imageURL,
            builder = {
                size((50 * density).toInt(), (50 * density).toInt())
            }
        ),
        contentDescription = (contentDescription ?: "").toString(),
        modifier = modifier
    )
}

@Composable
fun PokemonListItem(pokemon: PokemonResponse,context: Context, onClick: (PokemonResponse) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clickable {
                        onClick(pokemon)
                    }
                    .padding(16.dp)
            ) {
                GlideImage(
                    modifier = Modifier.size(110.dp),
                    imageURL = pokemon.sprites.front_default,
                    contentDiscriptuon = null.toString()
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = pokemon.name,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp)
            )
        }
    }
}

@Composable
fun PokemonDetails(pokemon: Pokemon, onClose: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Name: ${pokemon.name}",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
    GlideImage(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .weight(1f),
        imageURL = pokemon.img,
        contentDiscriptuon = "Pokemon Image")
    }
}


