package br.com.dominiodaaplicao.solrural.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.dominiodaaplicao.solrural.R
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var senhaEditText: EditText
    private lateinit var entrarBtn: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var backButton: ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        emailEditText = findViewById(R.id.email)
        senhaEditText = findViewById(R.id.senha)
        entrarBtn = findViewById(R.id.entrarBtn)
        backButton = findViewById(R.id.backButton)


        backButton.setOnClickListener {
            finish()
        }

        entrarBtn.setOnClickListener {
            signInWithEmailAndPassword()
        }
    }

    private fun signInWithEmailAndPassword() {
        val email = emailEditText.text.toString().trim()
        val password = senhaEditText.text.toString().trim()

        if (TextUtils.isEmpty(email)) {
            emailEditText.error = "Por favor, insira seu email"
            return
        }
        if (TextUtils.isEmpty(password)) {
            senhaEditText.error = "Por favor, insira sua senha"
            return
        }

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("Firebase", "signInWithEmail:success")

                    val intent = Intent(this, PerfilActivity::class.java)
                    startActivity(intent)
                    finish()

                } else {
                    Log.w("Firebase", "signInWithEmail:failure", task.exception)

                    val exception = task.exception
                    val errorMessage =  when(exception?.message){
                        "The email address is badly formatted." -> "Formato de e-mail inválido."
                        "There is no user record corresponding to this identifier. The user may have been deleted." -> "Nenhum usuário encontrado para este e-mail. Verifique se o e-mail está correto."
                        "The user's credential is no longer valid. The user must sign in again." -> "Credenciais inválidas. Faça login novamente."
                        else ->  "Erro de autenticação. Verifique suas credenciais."

                    }

                    Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()

                    senhaEditText.text.clear()

                }
            }
    }
}