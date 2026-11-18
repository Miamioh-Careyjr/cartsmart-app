package com.example.cartsmartmessageboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.Firebase
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                MessageBoard()
            }
        }
    }
}

@Composable
fun MessageBoard() {
    var text by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("Connecting...") }
    val messages = remember { mutableStateListOf<String>() }

    // Posts live in Firestore at rooms/general/posts.
    // Change "general" to add more chatrooms later.
    val posts = remember {
        Firebase.firestore
            .collection("rooms").document("general")
            .collection("posts")
    }

    // Listen for changes so every phone sees new posts as they arrive
    DisposableEffect(Unit) {
        val listener = posts
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    status = "Error: ${error.message}"
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    messages.clear()
                    snapshot.documents.forEach { doc ->
                        messages.add(doc.getString("text") ?: "")
                    }
                    status = if (snapshot.metadata.isFromCache) "Offline (cached)" else "Live"
                }
            }
        onDispose { listener.remove() }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(16.dp)
    ) {
        Text("CartSmart Message Board", style = MaterialTheme.typography.headlineSmall)
        Text("Status: $status", style = MaterialTheme.typography.bodySmall)
        Spacer(Modifier.height(12.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Post a deal or stock update") },
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(8.dp))
            Button(onClick = {
                if (text.isNotBlank()) {
                    // Send the post to Firestore with a server timestamp
                    posts.add(
                        hashMapOf(
                            "text" to text.trim(),
                            "createdAt" to FieldValue.serverTimestamp()
                        )
                    )
                    text = ""
                }
            }) { Text("Send") }
        }
        Spacer(Modifier.height(12.dp))
        LazyColumn {
            items(messages) { msg ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Text(msg, modifier = Modifier.padding(12.dp))
                }
            }
        }
    }
}
