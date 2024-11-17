package br.com.dominiodaaplicao.solrural.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import br.com.dominiodaaplicao.solrural.R
import br.com.dominiodaaplicao.solrural.element.Fazendas
import com.google.firebase.database.*

class PerfilActivity : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase
    private lateinit var usuarioRef: DatabaseReference
    private lateinit var itensCultivadosEditText: EditText
    private lateinit var tamanhoDaPropriedadeEditText: EditText
    private lateinit var quantidadeDeSafrasEditText: EditText
    private lateinit var itensCultivadosTextView: TextView
    private lateinit var tamanhoDaPropriedadeTextView: TextView
    private lateinit var quantidadeDeSafrasTextView: TextView
    private lateinit var salvarButton: Button
    private lateinit var editButton: Button
    private lateinit var deleteButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        database = FirebaseDatabase.getInstance()
        val uidDoUsuario = "uid_do_usuario"
        usuarioRef = database.getReference("usuarios").child(uidDoUsuario)

        itensCultivadosEditText = findViewById(R.id.itensCultivadosEditText)
        tamanhoDaPropriedadeEditText = findViewById(R.id.tamanhoDaPropriedadeEditText)
        quantidadeDeSafrasEditText = findViewById(R.id.quantidadeDeSafrasPorAnoEditText)

        itensCultivadosTextView = findViewById(R.id.itensCultivadosTextView)
        tamanhoDaPropriedadeTextView = findViewById(R.id.tamanhoDaPropriedadeTextView)
        quantidadeDeSafrasTextView = findViewById(R.id.quantidadeDeSafrasPorAnoTextView)

        salvarButton = findViewById(R.id.salvarButton)
        editButton = findViewById(R.id.editButton)
        deleteButton = findViewById(R.id.deleteButton)
        val backButton = findViewById<ImageButton>(R.id.backButton)



        backButton.setOnClickListener {
            finish()
        }

        usuarioRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val usuario = snapshot.getValue(Fazendas::class.java)
                mostrarModoEdicao(usuario == null)

                if (usuario != null) {
                    itensCultivadosTextView.text = "Itens Cultivados: ${usuario.itensCultivados}"
                    tamanhoDaPropriedadeTextView.text = "Tamanho da Propriedade: ${usuario.tamanhoDaPropriedade} hectares"
                    quantidadeDeSafrasTextView.text = "Safras por Ano: ${usuario.quantidadeDeSafrasPorAno}"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@PerfilActivity, "Erro ao carregar dados", Toast.LENGTH_SHORT).show()
            }
        })

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
                    Toast.makeText(this, "Informações salvas com sucesso!", Toast.LENGTH_SHORT).show()
                    mostrarModoEdicao(false)
                } else {
                    Toast.makeText(this, "Erro ao salvar informações.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        editButton.setOnClickListener {
            mostrarModoEdicao(true)


        }

        deleteButton.setOnClickListener {

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Confirmar Exclusão")
            builder.setMessage("Tem certeza que deseja excluir estas informações?")


            builder.setPositiveButton("Sim") { dialog, which ->

                usuarioRef.removeValue().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Informações excluídas com sucesso!", Toast.LENGTH_SHORT).show()

                        itensCultivadosEditText.text.clear()
                        tamanhoDaPropriedadeEditText.text.clear()
                        quantidadeDeSafrasEditText.text.clear()
                        itensCultivadosTextView.text = ""
                        tamanhoDaPropriedadeTextView.text = ""
                        quantidadeDeSafrasTextView.text = ""

                        mostrarModoEdicao(true)

                    } else {
                        Toast.makeText(this, "Erro ao excluir informações.", Toast.LENGTH_SHORT).show()
                    }
                }
                dialog.dismiss()
            }

            builder.setNegativeButton("Não") { dialog, which ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()

        }
    }

    private fun mostrarModoEdicao(emEdicao: Boolean) {
        val visibilidadeEdicao = if (emEdicao) View.VISIBLE else View.GONE
        val visibilidadeExibicao = if (emEdicao) View.GONE else View.VISIBLE

        itensCultivadosEditText.visibility = visibilidadeEdicao
        tamanhoDaPropriedadeEditText.visibility = visibilidadeEdicao
        quantidadeDeSafrasEditText.visibility = visibilidadeEdicao
        salvarButton.visibility = visibilidadeEdicao

        itensCultivadosTextView.visibility = visibilidadeExibicao
        tamanhoDaPropriedadeTextView.visibility = visibilidadeExibicao
        quantidadeDeSafrasTextView.visibility = visibilidadeExibicao
        editButton.visibility = visibilidadeExibicao
        deleteButton.visibility = visibilidadeExibicao

        if (emEdicao) {
            itensCultivadosEditText.setText(extrairValor(itensCultivadosTextView.text.toString(), "Itens Cultivados: "))
            tamanhoDaPropriedadeEditText.setText(extrairValor(tamanhoDaPropriedadeTextView.text.toString(), "Tamanho da Propriedade: ").replace(" hectares", ""))
            quantidadeDeSafrasEditText.setText(extrairValor(quantidadeDeSafrasTextView.text.toString(), "Safras por Ano: "))
        }
    }


    private fun extrairValor(texto: String, prefixo: String): String {
        val regex = Regex("$prefixo(.*)")
        val matchResult = regex.find(texto)
        return matchResult?.groupValues?.getOrNull(1)?.trim() ?: ""
    }
}