package com.example.ideationhub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class MainActivity3 : AppCompatActivity() {
    lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        supportActionBar?.hide()
        database = FirebaseDatabase.getInstance()

        val id = findViewById<EditText>(R.id.idIn)
        val password = findViewById<EditText>(R.id.passwordIn)
        val login = findViewById<Button>(R.id.login)

        login.setOnClickListener {
            val uniqueId = id.text.toString()
            val pass = password.text.toString()

            if (uniqueId.isNotEmpty() && pass.isNotEmpty()) {
                database.reference.child("user").child(uniqueId).get().addOnSuccessListener {
                    if (it.exists()) {
                        val name1 = it.child("name").value
                        val id1 = it.child("id").value
                        val pass1 = it.child("pass").value

                        if (id1 == uniqueId) {
                            if (pass1 == pass) {
                                Toast.makeText(applicationContext, "Welcome $name1", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this, MainActivity4::class.java)
                                intent.putExtra("name", name1.toString())
                                startActivity(intent)
                                finish() // Finish the current activity to prevent going back to it using the back button
                            } else {
                                Toast.makeText(applicationContext, "Incorrect password", Toast.LENGTH_SHORT).show()
                                password.text.clear()
                            }
                        } else {
                            Toast.makeText(applicationContext, "Incorrect ID", Toast.LENGTH_SHORT).show()
                            id.text.clear()
                            password.text.clear()
                        }
                    } else {
                        Toast.makeText(this@MainActivity3, "User does not exist", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@MainActivity3, MainActivity2::class.java)
                        startActivity(intent)
                        finish() // Finish the current activity to prevent going back to it using the back button
                    }
                }.addOnFailureListener {
                    Toast.makeText(this@MainActivity3, "Failed", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this@MainActivity3, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        val signUp = findViewById<TextView>(R.id.inToUp)
        signUp.setOnClickListener {
            val intent = Intent(this@MainActivity3, MainActivity2::class.java)
            startActivity(intent)
        }
    }
}
