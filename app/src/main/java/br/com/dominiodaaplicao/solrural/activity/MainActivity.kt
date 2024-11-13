package br.com.dominiodaaplicao.solrural.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import br.com.dominiodaaplicao.solrural.R
import androidx.cardview.widget.CardView
import com.squareup.picasso.Picasso


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        window.decorView.setBackgroundColor(Color.WHITE)

        val logoImageView: ImageView = findViewById(R.id.logoImageView)
        val imgLerSobre: ImageView = findViewById(R.id.imgLerSobre)
        val imgVer: ImageView = findViewById(R.id.imgVer)
        val imgCadastro : ImageView = findViewById(R.id.imgCadastro)
        val setaVer: ImageView = findViewById(R.id.setaVer)
        val setaCadastro: ImageView = findViewById((R.id.setaCadastro))
        val setaLerSobre: ImageView = findViewById(R.id.setaLerSobre)

        Picasso.get()
            .load(R.drawable.sol_rural)
            .resize(400, 170)
            .centerInside()
            .into(logoImageView)

        Picasso.get()
            .load(R.drawable.agrovoltaico)
            .resize(80, 80)
            .centerInside()
            .into(imgLerSobre)

        Picasso.get()
            .load(R.drawable.fazendas_cadastradas)
            .resize(80, 80)
            .centerInside()
            .into(imgVer)

        Picasso.get()
            .load(R.drawable.cadastro)
            .resize(80, 80)
            .centerInside()
            .into(imgCadastro)

        Picasso.get()
            .load(R.drawable.seta_direita)
            .resize(46, 35)
            .centerInside()
            .into(setaVer)

        Picasso.get()
            .load(R.drawable.seta_direita)
            .resize(46, 35)
            .centerInside()
            .into(setaCadastro)

        Picasso.get()
            .load(R.drawable.seta_direita)
            .resize(46, 35)
            .centerInside()
            .into(setaLerSobre)

        val cardLerSobre: CardView = findViewById(R.id.cardLerSobre)
        val cardParteDoProjeto: CardView = findViewById(R.id.cardParteDoProjeto)
        val cardCadastro: CardView = findViewById(R.id.cardCadastro)
        val botaoLogin: Button = findViewById(R.id.btn_login)

        cardLerSobre.setOnClickListener {

            val intent = Intent(this, LerSobreActivity::class.java)
            startActivity(intent)
        }

        cardParteDoProjeto.setOnClickListener {

            val intent = Intent(this, ParteDoProjetoActivity::class.java)
            startActivity(intent)
        }

        cardCadastro.setOnClickListener {

            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }

        botaoLogin.setOnClickListener {

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}