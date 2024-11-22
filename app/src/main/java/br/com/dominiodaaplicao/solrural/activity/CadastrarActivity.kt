package br.com.dominiodaaplicao.solrural.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.dominiodaaplicao.solrural.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class CadastrarActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        auth = Firebase.auth

        val nomeEditText = findViewById<EditText>(R.id.nome)
        val emailEditText = findViewById<EditText>(R.id.email)
        val senhaEditText = findViewById<EditText>(R.id.senha)
        val registrarBtn = findViewById<Button>(R.id.registrarBtn)

        val voltarBtn: ImageButton = findViewById(R.id.voltarBtn)

        voltarBtn.setOnClickListener {
            finish()
        }

        registrarBtn.setOnClickListener {
            val nome = nomeEditText.text.toString()
            val email = emailEditText.text.toString()
            val senha = senhaEditText.text.toString()

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (senha.length < 6) {
                Toast.makeText(this, "A senha deve ter pelo menos 6 caracteres.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = task.result?.user
                        if (user != null) {
                            val uid = user.uid
                            Toast.makeText(this, "Conta criada com sucesso! UID: $uid", Toast.LENGTH_SHORT).show()
                        }

                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {

                        Toast.makeText(this, "Erro ao criar a conta: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}
