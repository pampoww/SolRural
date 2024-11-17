package br.com.dominiodaaplicao.solrural.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.dominiodaaplicao.solrural.R
import br.com.dominiodaaplicao.solrural.element.Fazendas
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EditarActivity : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase
    private lateinit var usuarioRef: DatabaseReference
    private lateinit var itensCultivadosEditText: EditText
    private lateinit var tamanhoDaPropriedadeEditText: EditText
    private lateinit var quantidadeDeSafrasEditText: EditText
    private lateinit var salvarButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar)

        database = FirebaseDatabase.getInstance()
        val uidDoUsuario = "uid_do_usuario"
        usuarioRef = database.getReference("usuarios").child(uidDoUsuario)

        itensCultivadosEditText = findViewById(R.id.itensCultivadosEditText)
        tamanhoDaPropriedadeEditText = findViewById(R.id.tamanhoDaPropriedadeEditText)
        quantidadeDeSafrasEditText = findViewById(R.id.safrasPorAno)
        salvarButton = findViewById(R.id.saveButton)

        val backButton: ImageButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }

        carregarDadosDoFirebase()

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

            usuarioRef.setValue(usuario).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Informações atualizadas com sucesso!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Erro ao salvar informações.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun carregarDadosDoFirebase() {
        usuarioRef.get().addOnSuccessListener { snapshot ->
            val usuario = snapshot.getValue(Fazendas::class.java)
            if (usuario != null) {
                itensCultivadosEditText.setText(usuario.itensCultivados)
                tamanhoDaPropriedadeEditText.setText(usuario.tamanhoDaPropriedade)
                quantidadeDeSafrasEditText.setText(usuario.quantidadeDeSafrasPorAno)
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Erro ao carregar dados", Toast.LENGTH_SHORT).show()
        }
    }
}
