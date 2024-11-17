package br.com.dominiodaaplicao.solrural.activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.dominiodaaplicao.solrural.R
import br.com.dominiodaaplicao.solrural.element.Fazendas
import com.google.firebase.database.*

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

        val itensCultivadosTextView = findViewById<TextView>(R.id.itensCultivadosTextView)
        val tamanhoDaPropriedadeTextView = findViewById<TextView>(R.id.tamanhoDaPropriedadeTextView)
        val quantidadeDeSafrasTextView = findViewById<TextView>(R.id.quantidadeDeSafrasPorAnoTextView)

        val salvarButton = findViewById<Button>(R.id.salvarButton)
        val editButton = findViewById<Button>(R.id.editButton)
        val deleteButton = findViewById<Button>(R.id.deleteButton)
        val backButton = findViewById<ImageButton>(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }



        itensCultivadosTextView.visibility = View.GONE
        tamanhoDaPropriedadeTextView.visibility = View.GONE
        quantidadeDeSafrasTextView.visibility = View.GONE

        editButton.visibility = View.GONE
        deleteButton.visibility = View.GONE


        usuarioRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val usuario = snapshot.getValue(Fazendas::class.java)
                if (usuario != null) {
                    itensCultivadosTextView.text = "Itens Cultivados: ${usuario.itensCultivados}"
                    tamanhoDaPropriedadeTextView.text = "Tamanho da Propriedade: ${usuario.tamanhoDaPropriedade} hectares"
                    quantidadeDeSafrasTextView.text = "Safras por Ano: ${usuario.quantidadeDeSafrasPorAno}"


                    itensCultivadosEditText.visibility = View.GONE
                    tamanhoDaPropriedadeEditText.visibility = View.GONE
                    quantidadeDeSafrasEditText.visibility = View.GONE
                    salvarButton.visibility = View.GONE

                    itensCultivadosTextView.visibility = View.VISIBLE
                    tamanhoDaPropriedadeTextView.visibility = View.VISIBLE
                    quantidadeDeSafrasTextView.visibility = View.VISIBLE
                    editButton.visibility = View.VISIBLE
                    deleteButton.visibility = View.VISIBLE


                } else {

                    itensCultivadosEditText.visibility = View.VISIBLE
                    tamanhoDaPropriedadeEditText.visibility = View.VISIBLE
                    quantidadeDeSafrasEditText.visibility = View.VISIBLE
                    salvarButton.visibility = View.VISIBLE

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
                } else {
                    Toast.makeText(this, "Erro ao salvar informações.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        editButton.setOnClickListener {

            itensCultivadosEditText.visibility = View.VISIBLE
            tamanhoDaPropriedadeEditText.visibility = View.VISIBLE
            quantidadeDeSafrasEditText.visibility = View.VISIBLE
            salvarButton.visibility = View.VISIBLE


            itensCultivadosTextView.visibility = View.GONE
            tamanhoDaPropriedadeTextView.visibility = View.GONE
            quantidadeDeSafrasTextView.visibility = View.GONE
            editButton.visibility = View.GONE
            deleteButton.visibility = View.GONE

            itensCultivadosEditText.setText(itensCultivadosTextView.text.toString().replace("Itens Cultivados: ", ""))
            tamanhoDaPropriedadeEditText.setText(tamanhoDaPropriedadeTextView.text.toString().replace("Tamanho da Propriedade: ", "").replace(" hectares", ""))
            quantidadeDeSafrasEditText.setText(quantidadeDeSafrasTextView.text.toString().replace("Safras por Ano: ", ""))

        }

        deleteButton.setOnClickListener {
            usuarioRef.removeValue().addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Informações excluídas com sucesso!", Toast.LENGTH_SHORT).show()


                } else {
                    Toast.makeText(this, "Erro ao excluir informações.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}