package com.example.cadastroautenticacao

import android.os.Bundle
import android.util.Patterns
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.cadastroautenticacao.ui.theme.CadastroAutenticacaoTheme
import java.util.regex.Pattern

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CadastroAutenticacaoTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    CadastroForm()
                }
            }
        }
    }
}

@Composable
fun CadastroForm() {
    var cpf by remember { mutableStateOf("") }
    var nome by remember { mutableStateOf("") }
    var endereco by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var mensagemErro by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Autenticação Cadastral", style = MaterialTheme.typography.titleLarge)

        // Campo CPF
        OutlinedTextField(
            value = cpf,
            onValueChange = { cpf = it },
            label = { Text("CPF") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        // Campo Nome
        OutlinedTextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome Completo") },
            modifier = Modifier.fillMaxWidth()
        )

        // Campo Endereço
        OutlinedTextField(
            value = endereco,
            onValueChange = { endereco = it },
            label = { Text("Endereço") },
            modifier = Modifier.fillMaxWidth()
        )

        // Campo Telefone
        OutlinedTextField(
            value = telefone,
            onValueChange = { telefone = it },
            label = { Text("Telefone Celular") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth()
        )

        // Botão para validar
        Button(
            onClick = {
                mensagemErro = validarCadastro(cpf, nome, endereco, telefone)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Validar")
        }

        // Mensagem de Erro ou Sucesso
        if (mensagemErro.isNotEmpty()) {
            Text(
                text = mensagemErro,
                color = if (mensagemErro == "Cadastro validado com sucesso!") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

// Função para validar os campos
fun validarCadastro(cpf: String, nome: String, endereco: String, telefone: String): String {
    // Validação de CPF
    if (!validarCPF(cpf)) {
        return "CPF inválido."
    }

    // Validação de Nome
    if (nome.isBlank()) {
        return "Nome não pode estar vazio."
    }

    // Validação de Endereço
    if (endereco.isBlank()) {
        return "Endereço não pode estar vazio."
    }

    // Validação de Telefone
    if (!Patterns.PHONE.matcher(telefone).matches() || telefone.length < 10) {
        return "Telefone inválido."
    }

    return "Cadastro validado com sucesso!"
}

// Função para validar CPF
fun validarCPF(cpf: String): Boolean {
    val cpfPattern = Pattern.compile("\\d{11}")
    return cpfPattern.matcher(cpf).matches()
}
