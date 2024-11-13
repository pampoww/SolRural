package br.com.dominiodaaplicao.solrural.activity

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import br.com.dominiodaaplicao.solrural.R

class LerSobreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ler_sobre)


        val imageView: ImageView = findViewById(R.id.contentImage)
        val backButton: ImageButton = findViewById(R.id.backButton)
        val textView: TextView = findViewById(R.id.contentText)


        backButton.setOnClickListener{
            finish()
        }

        Picasso.get()
            .load(R.drawable.exemplo_agrovoltaico)
            .resize(200, 200)
            .centerInside()
            .into(imageView)

        textView.text = getString(R.string.ler_sobre_texto)
    }
}