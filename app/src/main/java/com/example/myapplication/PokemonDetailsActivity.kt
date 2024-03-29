
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.myapplication.PokemonResponse

class PokemonDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
    }
}

@Composable
fun PokemonDetails(pokemon: PokemonResponse, onClose: () -> Unit) {
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
        Text(
            text = "Height: ${pokemon.height}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = "Types: ${pokemon.types.joinToString(", ")}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = "Weight: ${pokemon.weight}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        GlideImage(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .weight(1f),
            imageURL = pokemon.sprites.front_default,
            contentDiscriptuon = "Pokemon Image"
        )
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
        contentDescription = contentDiscriptuon,
        modifier = modifier
    )
}
