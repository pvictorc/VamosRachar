package com.example.constraintlayout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
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
        val edtAmigos = findViewById<EditText>(R.id.editNumberAmigos)
        edtConta.addTextChangedListener(this)
        edtAmigos.addTextChangedListener(this)
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
        val d = Log.d("PDM23", s.toString())

        val btnRachar = findViewById<Button>(R.id.btnRachar)
        btnRachar.callOnClick()
    }

    fun clickFalar(v: View){

        val txtResult = findViewById<TextView>(R.id.txtviewResult)
        val textoResultado = txtResult.text.toString()
        tts.speak(textoResultado, TextToSpeech.QUEUE_FLUSH, null, null)
    }
    fun clickRachar(v :View){

        val edtValorCompra = findViewById<EditText>(R.id.editConta)
        val edtNumAmigos = findViewById<EditText>(R.id.editNumberAmigos)
        val txtResult = findViewById<TextView>(R.id.txtviewResult)
        try {
            val float_valor_compra : Float = edtValorCompra.text.toString().toFloatOrNull() ?: (0.00 as Float)
            val int_num_amigos : Int = edtNumAmigos.text.toString().toIntOrNull() ?: 1
            Log.d("PDM23", "Valores: $float_valor_compra $int_num_amigos")
            var texto_resultado = "A conta deu ..."
            if(!float_valor_compra.isNaN() && int_num_amigos >=1){
                val df = DecimalFormat("#.##")
                val result : Float = float_valor_compra.div(int_num_amigos)
                Log.d("PDM23", "Result: $result")
                texto_resultado = "A conta deu R$ "+df.format(result)
                txtResult.setText(texto_resultado)
            }
            txtResult.text = texto_resultado

        } catch (e: Exception){
            throw Exception("Preencher valores corretamente")
        }
    }

    fun compartilhar(v: View){
        val txtResult = findViewById<TextView>(R.id.txtviewResult)
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type="text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, txtResult.text)
        startActivity(Intent.createChooser(shareIntent,getString(R.string.send_to)))
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

