package com.example.taller2.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun LibroForm(
    matricula: String,
    modelo: String,
    color: String,
    fechaEntrada: String,
    labelVentana: String, // Ejemplo: "Nuevo Libro" o "Editar Libro"
    onTituloChange: (String) -> Unit,
    onAutorChange: (String) -> Unit,
    onPublicacionChange: (String) -> Unit,
    onAceptarClick: () -> Unit,
    onCancelarClick: () -> Unit
) {
    DefaultColumn {
        Text(labelVentana)

        DefaultOutlinedTextField(
            texto = matricula,
            onTextoChange = { nuevoTexto -> (nuevoTexto) },
            placeholder = "Matricula"
        )

        DefaultOutlinedTextField(
            texto = modelo,
            onTextoChange = { nuevoTexto -> onAutorChange(nuevoTexto) },
            placeholder = "Autor"
        )
        DefaultOutlinedTextField(
            texto = color,
            onTextoChange = { nuevoTexto -> onPublicacionChange(nuevoTexto) },
            placeholder = "Año de publicacion"
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = onCancelarClick) { Text("Cancelar") }
            Button(onClick = onAceptarClick) { Text("Aceptar") }
        }
    }
}
