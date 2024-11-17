package br.com.dominiodaaplicao.solrural.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.dominiodaaplicao.solrural.R
import br.com.dominiodaaplicao.solrural.element.Fazendas
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class PerfilActivity : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase
    private lateinit var usuarioRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        database = FirebaseDatabase.getInstance()
        val uidDoUsuario = "uid_do_usuario"
        usuarioRef = database.getReference("usuarios").child(uidDoUsuario)

        val itensCultivadosEditText = findViewById<EditText>(R.id.itensCultivadosEditText)
        val tamanhoDaPropriedadeEditText = findViewById<EditText>(R.id.tamanhoDaPropriedadeEditText)
        val quantidadeDeSafrasEditText = findViewById<EditText>(R.id.quantidadeDeSafrasPorAnoEditText)
        val salvarButton = findViewById<Button>(R.id.salvarButton)

        salvarButton.setOnClickListener {
            val itensCultivados = itensCultivadosEditText.text.toString()
            val tamanhoPropriedade = tamanhoDaPropriedadeEditText.text.toString()
            val quantidadeSafras = quantidadeDeSafrasEditText.text.toString()

            val usuario = Fazendas(
                uid = uidDoUsuario,
                itensCultivados = itensCultivados,
                tamanhoDaPropriedade = tamanhoPropriedade,
                quantidadeDeSafrasPorAno = quantidadeSafras
            )

            // Salvar no Firebase
            usuarioRef.setValue(usuario).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Informações salvas com sucesso!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Erro ao salvar informações.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
