package com.example.constraintlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import java.text.DecimalFormat
import java.util.*

class MainActivity : AppCompatActivity() , TextWatcher, TextToSpeech.OnInitListener {
    private lateinit var tts: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val edtConta = findViewById<EditText>(R.id.editConta)
        edtConta.addTextChangedListener(this)
        // Initialize TTS engine
        tts = TextToSpeech(this, this)

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
       Log.d("PDM23","Antes de mudar")
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        Log.d("PDM23","Mudando")
    }

    override fun afterTextChanged(s: Editable?) {
        Log.d ("PDM23", "Depois de mudar")
        Log.d ("PDM23", s.toString())
    }

    fun clickFalar(v: View){

        val txtResult = findViewById<TextView>(R.id.txtviewResult)
        val textoResultado = txtResult.text.toString()
        tts.speak(textoResultado, TextToSpeech.QUEUE_FLUSH, null, null)
    }
    fun clickRachar(v: View){

        val valor_compra = findViewById<EditText>(R.id.editConta)
        val num_amigos = findViewById<EditText>(R.id.editNumberAmigos)
        val txtResult = findViewById<TextView>(R.id.txtviewResult)
        val float_valor_compra : Float? = valor_compra.text.toString().toFloatOrNull() ?: (0.00 as Float)
        val int_num_amigos : Int = num_amigos.text.toString().toIntOrNull() ?: 1
//        Log.d("PDM23", "Valores: $float_valor_compra $int_num_amigos")
        var textoResultado = "A conta deu ..."
        if(float_valor_compra != null && int_num_amigos != null){
            val df = DecimalFormat("#.###")
            val result : Float = float_valor_compra.div(int_num_amigos)
            Log.d("PDM23", "Result: $result")
            textoResultado = "A conta deu R$ "+df.format(result)
            txtResult.setText(textoResultado)
        }
        txtResult.text = textoResultado
        tts.speak(textoResultado, TextToSpeech.QUEUE_FLUSH, null, null)

    }
    override fun onDestroy() {
            // Release TTS engine resources
            tts.stop()
            tts.shutdown()
            super.onDestroy()
        }

    override fun onInit(status: Int) {
            if (status == TextToSpeech.SUCCESS) {
                // TTS engine is initialized successfully
                tts.language = Locale.getDefault()
                Log.d("PDM23","Sucesso na Inicialização")
            } else {
                // TTS engine failed to initialize
                Log.e("PDM23", "Failed to initialize TTS engine.")
            }
        }


}

