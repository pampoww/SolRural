import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import br.com.dominiodaaplicao.solrural.R
import br.com.dominiodaaplicao.solrural.element.Usuario
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PerfilActivity : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        database = FirebaseDatabase.getInstance()

        // Obter o UID do usuário (você pode obter isso de outras fontes, como a autenticação do Firebase)
        val uidDoUsuario = "uid_do_usuario"

        // Referência ao nó do usuário no Firebase Realtime Database
        val usuarioRef = database.getReference("usuarios").child(uidDoUsuario)

        // Ler os dados do usuário
        usuarioRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val usuario = dataSnapshot.getValue(Usuario::class.java)

                // Exibir os dados do usuário na tela
                val itensCultivadosTextView = findViewById<TextView>(R.id.itensCultivadosTextView)
                val tamanhoDaPropriedadeTextView = findViewById<TextView>(R.id.tamanhoDaPropriedadeTextView)
                val quantidadeDeSafrasPorAnoTextView = findViewById<TextView>(R.id.quantidadeDeSafrasPorAnoTextView)

                if (usuario != null) {
                    itensCultivadosTextView.text = usuario.itensCultivados
                    tamanhoDaPropriedadeTextView.text = usuario.tamanhoDaPropriedade
                    quantidadeDeSafrasPorAnoTextView.text = usuario.quantidadeDeSafrasPorAno
                } else {
                    // O usuário não foi encontrado
                    // Exibir uma mensagem de erro
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Falha ao ler os dados do usuário
                // Exibir uma mensagem de erro
            }
        })
    }
}
