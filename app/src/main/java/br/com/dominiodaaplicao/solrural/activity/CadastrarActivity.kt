package br.com.dominiodaaplicao.solrural.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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

        // Inicializar o Firebase Authentication
        auth = Firebase.auth

        // Obter referências para os elementos da interface
        val nomeEditText = findViewById<EditText>(R.id.nome)
        val emailEditText = findViewById<EditText>(R.id.email)
        val passwordEditText = findViewById<EditText>(R.id.password)
        val registerButton = findViewById<Button>(R.id.registerButton)

        // Configurar o botão de registro
        registerButton.setOnClickListener {
            val nome = nomeEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Validar os campos de entrada
            if (nome.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validar a senha (6 dígitos)
            if (password.length != 6 || !password.all { it.isDigit() }) {
                Toast.makeText(this, "A senha deve conter 6 dígitos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Criar uma nova conta de usuário
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = task.result?.user
                        if (user != null) {
                            // Exemplo: Obter o UID do usuário
                            val uid = user.uid
                            Toast.makeText(this, "Conta criada com sucesso! UID: $uid", Toast.LENGTH_SHORT).show()
                        }

                        // Redirecionar para a próxima tela (ex: tela principal)
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // Erro ao criar a conta
                        Toast.makeText(this, "Erro ao criar a conta: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}
