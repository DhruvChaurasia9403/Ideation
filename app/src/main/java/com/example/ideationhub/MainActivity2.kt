package com.example.ideationhub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity2 : AppCompatActivity() {
    lateinit var database : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        supportActionBar?.hide()
        database = FirebaseDatabase.getInstance().getReference("user")
        supportActionBar?.hide()
        val name = findViewById<EditText>(R.id.name)
        val pass = findViewById<EditText>(R.id.passwordup)
        val email = findViewById<EditText>(R.id.mail)
        val id = findViewById<EditText>(R.id.idup)
        val signup =  findViewById<Button>(R.id.signup)
        val login = findViewById<TextView>(R.id.inToUp)

        signup.setOnClickListener {
            val name1 = name.text.toString()
            val pass1 = pass.text.toString()
            val email1 = email.text.toString()
            val id1 = id.text.toString()
            val User = user(name1, pass1, email1, id1 )
            if (name1.isNotEmpty() && pass1.isNotEmpty() && email1.isNotEmpty() && id1.isNotEmpty()) {
                database.child(id1).setValue(User).addOnSuccessListener {
                    name.text.clear()
                    pass.text.clear()
                    email.text.clear()
                    id.text.clear()
                    Toast.makeText(this, "Welcome $name1", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity4::class.java)
                    intent.putExtra("name", name1.toString())
                    startActivity(intent)
                }.addOnFailureListener {
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Enter all the Fields", Toast.LENGTH_SHORT).show()
            }
        }

        login.setOnClickListener {
            val intent = Intent(this, MainActivity3::class.java)
            startActivity(intent)
        }
    }
}