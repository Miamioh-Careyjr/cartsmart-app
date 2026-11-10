package com.example.homescreenapp


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.homescreenapp.ui.theme.HomeScreenAppTheme


// ---- Palette ------------------------------------------------------------
private val CartGreen = Color(0xFF278667)
private val CartDarkGreen = Color(0xFF16634D)
private val CartBackground = Color(0xFFF5FAF7)
private val CartBorder = Color(0xFF73AFC1)
private val CartGray = Color(0xFF777777)


// ---- Model --------------------------------------------------------------
data class InfoSection(
    val title: String,
    val description: String,
    val button: String?,
    @DrawableRes val image: Int,
    val imageFirst: Boolean
)


// ---- Activity -----------------------------------------------------------
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HomeScreenAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CartSmartScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}


// ---- Screen -------------------------------------------------------------
@Composable
fun CartSmartScreen(modifier: Modifier = Modifier) {
    val sections = listOf(
        InfoSection(
            title = "Simplifying the grocery store experience",
            description = "Compare a vast amount of grocery prices and find the most affordable ones, " +
                    "and start the day great by making shopping decisions that make a difference.",
            button = "Price comparison",
            image = R.drawable.pricecomparison,
            imageFirst = true
        ),
        InfoSection(
            title = "Connect and share with others",
            description = "Chat with other shoppers, exchange experiences and share recommendations, " +
                    "and discover all new information to use with your grocery shopping.",
            button = "Chat",
            image = R.drawable.shopperchat,
            imageFirst = false
        ),
        InfoSection(
            title = "Discover a vast selection of stores available",
            description = "Browse the vast selection of stores that are available in CartSmart for your shopping journey. " +
                    "See what products are available and where before starting your shopping trip.",
            button = "Stores available",
            image = R.drawable.storeaisle,
            imageFirst = true
        ),
        InfoSection(
            title = "Keep track of how much you spend with CartSmart",
            description = "Monitor your purchases and understand how much you spend on grocery " +
                    "shopping today.",
            button = null,
            image = R.drawable.piggybank,
            imageFirst = false
        ),
        InfoSection(
            title = "Thank you so much for using CartSmart",
            description = "CartSmart intends to further refine the way that normal grocery shopping is done. " +
                    "Join together in a community that strives for better grocery shopping experiences.",
            button = null,
            image = R.drawable.community,
            imageFirst = true
        )
    )


    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(CartBackground),
        contentPadding = PaddingValues(bottom = 40.dp)
    ) {
        item { HeroSection() }
        item { SectionHeading("PRICE COMPARISONS") }
        items(sections) { section -> InfoRow(section) }
        item { SectionHeading("CARTSMART FEATURES") }
        item { FeatureSection() }
        item { SavingSection() }
        item { FooterSection() }
    }
}


// ---- Hero ---------------------------------------------------------------
@Composable
private fun HeroSection() {
    Column(modifier = Modifier.padding(20.dp)) {
        Text(
            text = "CartSmart Grocery Savings",
            color = CartGreen,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )


        Spacer(Modifier.height(20.dp))


        Image(
            painter = painterResource(R.drawable.groceryhero),
            contentDescription = "Grocery cart",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clip(RoundedCornerShape(14.dp))
        )


        Spacer(Modifier.height(24.dp))


        Text(
            text = "CartSmart:\ngrocery savings",
            color = CartGreen,
            fontSize = 31.sp,
            lineHeight = 31.sp,
            fontWeight = FontWeight.ExtraBold
        )


        Spacer(Modifier.height(10.dp))


        Text(
            text = "This app is designed to help shoppers save money " +
                    "on grocery shopping and helps them achieve low prices.",
            color = CartDarkGreen,
            fontSize = 14.sp
        )


        Spacer(Modifier.height(18.dp))


        Button(
            onClick = { /* Navigate to product search */ },
            colors = ButtonDefaults.buttonColors(containerColor = CartGray),
            shape = RoundedCornerShape(2.dp)
        ) {
            Text("Start searching")
        }


        TextButton(onClick = { /* Open search */ }) {
            Text(
                text = "START SAVING NOW  →",
                color = CartGreen,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Composable
private fun SectionHeading(text: String) {
    Text(
        text = text,
        color = CartGreen,
        fontSize = 25.sp,
        fontWeight = FontWeight.ExtraBold,
        modifier = Modifier.padding(
            start = 20.dp,
            end = 20.dp,
            top = 30.dp,
            bottom = 16.dp
        )
    )
}


// ---- Alternating image / text rows --------------------------------------
@Composable
private fun InfoRow(section: InfoSection) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
        if (section.imageFirst) {
            SectionImage(section)
            Spacer(Modifier.height(12.dp))
            SectionCard(section)
        } else {
            SectionCard(section)
            Spacer(Modifier.height(12.dp))
            SectionImage(section)
        }
    }
}


@Composable
private fun SectionImage(section: InfoSection) {
    Image(
        painter = painterResource(section.image),
        contentDescription = section.title,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .height(210.dp)
            .clip(RoundedCornerShape(12.dp))
    )
}


@Composable
private fun SectionCard(section: InfoSection) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = CartBackground),
        border = BorderStroke(width = 1.dp, color = CartBorder)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = section.title,
                color = CartGreen,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )


            Spacer(Modifier.height(10.dp))


            Text(
                text = section.description,
                color = CartDarkGreen,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )


            section.button?.let { label ->
                Spacer(Modifier.height(18.dp))


                Button(
                    onClick = { /* Handle action */ },
                    colors = ButtonDefaults.buttonColors(containerColor = CartGray),
                    shape = RoundedCornerShape(2.dp)
                ) {
                    Text(label)
                }
            }
        }
    }
}


// ---- Green feature cards ------------------------------------------------
@Composable
private fun FeatureSection() {
    val features = listOf(
        "Price Comparison" to
                "Compare prices across different stores/locations to find the best deals and save money on everyday essentials.",
        "Connect with Others" to
                "Our chatting feature allows other shopping enthusiasts to interact with one another for information.",
        "Store Selection" to
                "Our store selection feature allows to view the stores we have available in our database for a look into the prices of nearby stores."
    )


    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        features.forEach { (title, description) ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                colors = CardDefaults.cardColors(containerColor = CartGreen),
                shape = RoundedCornerShape(10.dp)
            ) {
                Column(
                    modifier = Modifier.padding(22.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = title,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )


                    Spacer(Modifier.height(28.dp))


                    Text(
                        text = description,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontSize = 13.sp
                    )


                    Spacer(Modifier.height(18.dp))


                    FilledIconButton(
                        onClick = { },
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = Color.White.copy(alpha = 0.25f)
                        )
                    ) {
                        Text("→", color = Color.White)
                    }
                }
            }
        }
    }
}


// ---- Call-to-action + related sections ----------------------------------
@Composable
private fun SavingSection() {
    Column(modifier = Modifier.padding(top = 30.dp)) {
        Image(
            painter = painterResource(R.drawable.freshfood),
            contentDescription = "Fresh groceries",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
        )


        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Start Saving Today!",
                color = CartGreen,
                fontSize = 30.sp,
                lineHeight = 30.sp,
                fontWeight = FontWeight.ExtraBold
            )


            Spacer(Modifier.height(20.dp))


            ActionButton(
                title = "Chat with other shoppers",
                subtitle = "Interact with others"
            )
            ActionButton(
                title = "Store selection",
                subtitle = "See what is available"
            )
            ActionButton(
                title = "Start comparing prices",
                subtitle = "Compare now"
            )
        }


        Text(
            text = "CartSmart's\nVarious Features",
            color = CartGreen,
            fontSize = 28.sp,
            lineHeight = 27.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(horizontal = 20.dp)
        )


        Text(
            text = "Discuss with other shoppers, compare grocery prices, and find nearby stores " +
                    "that make grocery shopping that much easier.",
            color = CartDarkGreen,
            modifier = Modifier.padding(20.dp)
        )


        Image(
            painter = painterResource(R.drawable.phonemockup),
            contentDescription = "CartSmart app preview",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
        )
    }
}


@Composable
private fun ActionButton(title: String, subtitle: String) {
    Button(
        onClick = { },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = CartGreen),
        shape = RoundedCornerShape(7.dp),
        contentPadding = PaddingValues(14.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = title, fontWeight = FontWeight.Bold)
            Text(
                text = subtitle,
                fontSize = 11.sp,
                color = Color.White.copy(alpha = 0.8f)
            )
        }
    }
}


// ---- Footer -------------------------------------------------------------
@Composable
private fun FooterSection() {
    Column(modifier = Modifier.padding(20.dp)) {
        Text(
            text = "CartSmart App",
            color = CartGreen,
            fontWeight = FontWeight.Bold
        )


        Spacer(Modifier.height(14.dp))


        Text(
            text = "Achieve affordable and\nsatisfying prices today!",
            color = CartGreen,
            fontSize = 27.sp,
            lineHeight = 28.sp,
            fontWeight = FontWeight.ExtraBold
        )


        Spacer(Modifier.height(70.dp))


        Text(
            text = "GET IN TOUCH NOW!",
            color = CartGreen,
            fontWeight = FontWeight.Bold
        )


        Spacer(Modifier.height(8.dp))


        Text(
            text = "Phone: 513-858-9655 \nEmail: contact@cartsmart.app\nSocial Media: @CartSmartApp",
            color = CartDarkGreen,
            fontSize = 12.sp
        )
    }
}


// ---- Preview ------------------------------------------------------------
@Preview(showBackground = true)
@Composable
fun CartSmartPreview() {
    HomeScreenAppTheme {
        CartSmartScreen()
    }
}
