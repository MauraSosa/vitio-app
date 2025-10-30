package com.example.vitioo


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class LoginActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var tts: TextToSpeech
    private lateinit var etUsuario: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tts = TextToSpeech(this, this)

        etUsuario = findViewById(R.id.etUsuario)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)

        val prefs = getSharedPreferences("VitioPrefs", Context.MODE_PRIVATE)
        val savedUser = prefs.getString("usuario", null)

        if (savedUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("usuario", savedUser)
            startActivity(intent)
            finish()
        }

        btnLogin.setOnClickListener {
            val usuario = etUsuario.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (usuario == "vitio" && password == "1234") {
                prefs.edit().putString("usuario", usuario).apply()

                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("usuario", usuario)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                tts.speak("Acceso denegado", TextToSpeech.QUEUE_FLUSH, null, "")
            }
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts.language = Locale("es", "MX")
        }
    }

    override fun onDestroy() {
        tts.stop()
        tts.shutdown()
        super.onDestroy()
    }
}
