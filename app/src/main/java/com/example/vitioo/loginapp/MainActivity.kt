package com.example.vitioo


import android.content.Context
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var tts: TextToSpeech
    private lateinit var tvBienvenida: TextView
    private lateinit var btnCerrar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tts = TextToSpeech(this, this)

        tvBienvenida = findViewById(R.id.tvBienvenida)
        btnCerrar = findViewById(R.id.btnCerrar)

        val usuario = intent.getStringExtra("usuario")
        tvBienvenida.text = "Bienvenido a Vitio, $usuario"

        btnCerrar.setOnClickListener {
            val prefs = getSharedPreferences("VitioPrefs", Context.MODE_PRIVATE)
            prefs.edit().clear().apply()
            finish()
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts.language = Locale("es", "MX")
            val mensaje = "Bienvenido a Vitio, tu tienda de suplementos."
            tts.speak(mensaje, TextToSpeech.QUEUE_FLUSH, null, "")
        }
    }

    override fun onDestroy() {
        tts.stop()
        tts.shutdown()
        super.onDestroy()
    }
}
