package br.com.dominiodaaplicao.solrural.activity

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import br.com.dominiodaaplicao.solrural.R
import com.squareup.picasso.Picasso

class BeneficiosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beneficios)


        val imageView: ImageView = findViewById(R.id.contentImage)
        val voltarBtn: ImageButton = findViewById(R.id.voltarBtn)
        val textView: TextView = findViewById(R.id.contentText)


        voltarBtn.setOnClickListener{
            finish()
        }

        Picasso.get()
            .load(R.drawable.energia_agrovoltaica)
            .resize(200, 200)
            .centerInside()
            .into(imageView)

        textView.text = getString(R.string.beneficios)
    }
}