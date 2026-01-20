package com.example.taller2.Ventanas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.taller2.MyLog
import com.example.taller2.components.DefaultColumn
import com.example.taller2.components.DefaultOutlinedTextField
import com.example.taller2.data.CocheViewModel
import kotlinx.coroutines.launch
import kotlin.let


@Composable
fun CocheForm(
    navController: NavController, modifier: Modifier, viewModel: CocheViewModel, option:String) {


    val uiScope = rememberCoroutineScope()


    DefaultColumn {
        Text("LibroForm: " + option)

        DefaultOutlinedTextField(
            texto = viewModel.matricula,
            onTextoChange = { nuevoTexto -> viewModel.onMatriculaChanged(nuevoTexto) },
            placeholder = "Matricula"
        )

        DefaultOutlinedTextField(
            texto = viewModel.modelo,
            onTextoChange = { nuevoTexto -> viewModel.onModeloChanged(nuevoTexto) },
            placeholder = "Modelo"
        )
        DefaultOutlinedTextField(
            texto = viewModel.color,
            onTextoChange = { nuevoTexto -> viewModel.onColorChanged(nuevoTexto) },
            placeholder = "Color"
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                navController.navigate("ver")
                viewModel.limpiarFormulario()
            }) { Text("Cancelar") }
            Button(onClick = {
                uiScope.launch {
                    if("crear".equals(option)) {
                        MyLog.d("Crear aceptar")
                        viewModel.guardarCoche() {navController.popBackStack()}
                    }
                    if ("editar".equals(option)) {
                        MyLog.d("editar aceptar")
                        viewModel.editarCoche() {navController.popBackStack()}
                    }
                }
            }) { Text("Aceptar") }
        }
        viewModel.errorMensaje?.let { Text(it,color = Color.Red) }
    }
}
