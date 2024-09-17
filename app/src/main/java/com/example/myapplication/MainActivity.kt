package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Content()
            }
        }
    }
}

@Composable
fun Content() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Imagen de fondo
        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = "Fondo",
            modifier = Modifier.fillMaxSize(),
            colorFilter = ColorFilter.lighting(Color
            ),
            contentScale = ContentScale.FillHeight // Ajusta la imagen para que ocupe todo el tamaño del contenedor
        )

        // LazyColumn con fondo semi-transparente
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0.1f, 0.1f, 0.1f, 0.9f)) // Fondo semi-transparente
                .padding(vertical = 20.dp)
        ) {
            item {
                Profile()
                About()
                Social()
            }
        }
    }
}


@Composable
fun Profile() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally // Alinea todo el contenido al centro
    ) {
        Image(
            painter = painterResource(id = R.drawable.profile_picture),
            contentDescription = "Personal Profile Picture",
            modifier = Modifier
                .size(220.dp)
                .clip(CircleShape)
                .border(BorderStroke(6.dp, Color.White), shape = CircleShape)
        )
        HorizontalDivider(color = Color.DarkGray, thickness = 1.dp, modifier = Modifier
            .padding(vertical = 10.dp))
        Text(
            text = "Mikel Dalmau",
            fontSize = 24.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        )
        HorizontalDivider(color = Color.DarkGray, thickness = 1.dp)
        Text(
            text = "I am a former Software Engineer now teaching the way of the keyboard to young guns " +
                    "with a passion for sports, all kinds of food, and painting.",
            fontSize = 16.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            lineHeight = 24.sp
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ContentPreview() {
    MyApplicationTheme {
        Content()
    }
}

@Composable
fun About(){
    Column (modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp)
        , verticalArrangement = Arrangement.SpaceEvenly
    )
    { // Row for the social media icons
        Subject(R.drawable.sharp_school_24, "Education", "Software Engineer")
        Subject(R.drawable.round_sports_gymnastics_24, "Sport", "Crossfit")
        Subject(R.drawable.baseline_fastfood_24, "Favourite Food", "All of them")
        Subject(R.drawable.baseline_brush_24, "Hobby", "Digital and watercolor painting")

    }
}
@Preview(showBackground = false)
@Composable
fun AboutPreview(){
    MyApplicationTheme {
        About()
    }
}

@Composable
fun Subject(@DrawableRes icon: Int, title: String, body: String?) {
    Row (modifier = Modifier.fillMaxWidth()
            .height(100.dp)
            .padding(vertical = 5.dp, horizontal = 16.dp)
            .border(BorderStroke(Dp.Hairline, Color.White), shape = RoundedCornerShape(16.dp))
        , horizontalArrangement = Arrangement.spacedBy(16.dp)
    ){
        Image(
            painter = painterResource(id = icon),
            contentDescription = "Github Icon",
            modifier = Modifier
                .size(60.dp) // Ajusta el tamaño del ícono según el alto que desees
                .align(Alignment.CenterVertically)// Alinea verticalmente el ícon
                .offset(x = 5.dp, y = 0.dp), // Ajusta la posición del ícono
            colorFilter = ColorFilter.lighting(Color.White, Color.White)
        )
        Column (verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.align(Alignment.CenterVertically) // Alinea verticalmente el texto
        ){
            Text(
                text = title,
                fontSize = 24.sp,
                color = Color.White,
                textAlign = TextAlign.Left,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = body ?: "",
                fontSize = 16.sp,
                color = Color.White,
                textAlign = TextAlign.Left,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
fun SubjectPreview(){
    MyApplicationTheme {
        Subject(R.drawable.sharp_school_24, "Education", "Software Engineer")
    }
}

@Composable
fun Social(){
    val context = LocalContext.current

    Row (modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp)
        , horizontalArrangement = Arrangement.SpaceEvenly
    )
    { // Row for the social media icons
        Image(painter = painterResource(id = R.drawable.ic_github)
            , contentDescription = "Github Icon"
            , modifier = Modifier.size(45.dp)
                .clickable {
                    val linkedinUrl = "https://github.com/mikeldalmauc"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(linkedinUrl))
                    context.startActivity(intent)
                }
            , colorFilter = ColorFilter.lighting(Color.White, Color.White)
        )
        Image(painter = painterResource(id = R.drawable.ic_linkedin)
            , contentDescription = "LinkedIn Icon"
            , modifier = Modifier.size(45.dp)
                .clickable {
                    val linkedinUrl = "https://www.linkedin.com/in/mkldalmau/"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(linkedinUrl))
                    context.startActivity(intent)
                }
            , colorFilter = ColorFilter.lighting(Color.White, Color.White)
        )
        Image(painter = painterResource(id = R.drawable.ic_instagram)
            , contentDescription = "Twitter Icon"
            , modifier = Modifier.size(45.dp)
                .clickable {
                    val linkedinUrl = "https://www.instagram.com/mikel_dalmau/"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(linkedinUrl))
                    context.startActivity(intent)
                }
            , colorFilter = ColorFilter.lighting(Color.White, Color.White)
        )
    }
}