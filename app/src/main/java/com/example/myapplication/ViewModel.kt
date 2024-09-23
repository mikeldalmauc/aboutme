import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.myapplication.Artwork
import com.example.myapplication.ArtworkStyle
import com.example.myapplication.R

class GalleryViewModel : ViewModel() {
    var isSingleColumn = mutableStateOf(false)
    var artworks = mutableStateOf(sampleArtworks())
}

fun sampleArtworks(): List<Artwork> {
    return listOf(
        Artwork(
            "Black Woman",
            "Black Woman",
            "Watercolour portrait",
            "2023-01-01",
            ArtworkStyle.DIGITAL,
            R.drawable.black_woman
        ),
        Artwork(
            "Dark Angel",
            "Darl Angel",
            "Dark Fallen Angel concept art",
            "2023-02-01",
            ArtworkStyle.DIGITAL,
            R.drawable.darkangel
        ),
        Artwork(
            "Hat Girl",
            "Hat Girl",
            "Digital portrait of girl with hat",
            "2023-02-01",
            ArtworkStyle.DIGITAL,
            R.drawable.hatgirl
        ),
        Artwork(
            "Liz",
            "Liz",
            "Fanart of Liz from ",
            "2023-02-01",
            ArtworkStyle.DIGITAL,
            R.drawable.liz
        ),
        Artwork(
            "Malenia",
            "Malean",
            "Fan art of Malenia from Elden Ring",
            "2023-02-01",
            ArtworkStyle.DIGITAL,
            R.drawable.malenia
        ),
        Artwork(
            "Ona",
            "Ona Morgan",
            "Digital portrait of Ona Morgan",
            "2023-02-01",
            ArtworkStyle.DIGITAL,
            R.drawable.onamorgan
        ),
        Artwork(
            "ribbon",
            "Ribbon",
            "Painting of a ribbon",
            "2023-02-01",
            ArtworkStyle.DIGITAL,
            R.drawable.ribbon
        ),
        Artwork(
            "Sardines",
            "Sardines",
            "Sardines watercolour painting",
            "2023-02-01",
            ArtworkStyle.WATERCOLOUR,
            R.drawable.sardines
        ),
        Artwork(
            "Squid Game",
            "Squid Game",
            "Portrait of the main character from Squid Game",
            "2023-02-01",
            ArtworkStyle.WATERCOLOUR,
            R.drawable.squid
        ),
        Artwork(
            "Tattoo Girl",
            "Tattoo Girl",
            "Digital portrait of tattoo girl",
            "2023-02-01",
            ArtworkStyle.DIGITAL,
            R.drawable.tattoogirl
        ),
        Artwork(
            "Tomb Raider",
            "Tomb Raider",
            "Kara de Levigne as Lara Croft",
            "2023-02-01",
            ArtworkStyle.DIGITAL,
            R.drawable.tombrider
        ),
    )
}
