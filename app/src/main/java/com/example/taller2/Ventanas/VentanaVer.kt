package com.example.taller2.Ventanas

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.taller2.MyLog
import com.example.taller2.components.DefaultColumn
import com.example.taller2.components.DefaultOutlinedTextField
import com.example.taller2.data.Coche
import com.example.taller2.data.CocheViewModel
import kotlinx.coroutines.launch


// Define anchos fijos por columna (ajústalos a tu gusto/maqueta)
private val COL1_WIDTH = 90.dp
private val COL2_WIDTH = 90.dp
private val COL3_WIDTH = 70.dp
private val ACTIONS_WIDTH = 96.dp   // suficiente para dos IconButton
private val ROW_HORIZONTAL_PADDING = 8.dp
private val ROW_VERTICAL_PADDING = 12.dp


@Composable
fun VentanaVer(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: CocheViewModel
) {
    val coches by viewModel.cocheFiltrados.collectAsState()
    val uiScope = rememberCoroutineScope()
    val context = LocalContext.current


    // FUNCIONES ELIMINAR, EDITAR Y CREAR
    fun eliminarCoche(id: Int) {
        MyLog.d("VentanaVer.eliminarCoche")
        uiScope.launch {
            viewModel.eliminarCoche(id)
            Toast.makeText(context,"El Coche ha sido eliminado",Toast.LENGTH_SHORT).show()
        }
    }

    fun editarCoche(id: Int) {
        MyLog.d("VentanaVer.editarCoche")
        uiScope.launch {
            viewModel.observarCoche(id)
            navController.navigate("CocheForm/editar")
        }
    }

    fun crearCoche() {
        MyLog.d("VentanaVer.crearCoche")
        navController.navigate("CocheForm/crear")
    }

    DefaultColumn(modifier = modifier) {
        Text("VentanaVer")

        Button({ viewModel.insertarDatosPrueba() }) { Text("Insertar datos de prueba") }
        // BOTONES
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                crearCoche()
            }) {
                Text("Añadir Coche")
            }
            Button(onClick = {
                MyLog.d("Boton aplicarFiltros")
                viewModel.aplicarFiltros(viewModel.filtrarMatricula, viewModel.filtrarModelo)
            })
            { Text("Aplicar Filtros") }
        }

        // FORMULARIOS
        DefaultOutlinedTextField(
            viewModel.filtrarMatricula,
            { nuevo -> viewModel.onFiltroMatriculaChanged(nuevo) },
            "Filtro Matricula"
        )
        DefaultOutlinedTextField(
            viewModel.filtrarModelo,
            { nuevo -> viewModel.onFiltroModeloChanged(nuevo) },
            "Filtro Modelo"
        )

        // COLUMNA DE VISUALICACIÓN DE DATOS
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = ROW_HORIZONTAL_PADDING,
                            vertical = ROW_VERTICAL_PADDING
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Matricula",
                        modifier = Modifier.width(COL1_WIDTH),
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Modelo",
                        modifier = Modifier.width(COL2_WIDTH),
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Color",
                        modifier = Modifier.width(COL3_WIDTH),
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                    Row(
                        modifier = Modifier.width(ACTIONS_WIDTH),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = "Acciones",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }

            // Carga de filas y funciones eliminar y editar
            items(coches) { coche ->
                CocheItem(
                    Coche = coche,
                    onEditClick = { cocheSeleccionado ->
                        editarCoche(cocheSeleccionado.id)
                    },
                    onDeleteClick = { cocheSeleccionado ->
                        eliminarCoche(cocheSeleccionado.id)
                    }
                )
            }
        }
    }
}

@Composable
fun CocheItem(
    Coche: Coche,
    onEditClick: (Coche) -> Unit,
    onDeleteClick: (Coche) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = ROW_HORIZONTAL_PADDING, vertical = ROW_VERTICAL_PADDING),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = Coche.matricula,
                modifier = Modifier.width(COL1_WIDTH),
                style = MaterialTheme.typography.bodyLarge.copy(
                    lineBreak = LineBreak.Paragraph,
                    hyphens = Hyphens.Auto
                ),
                softWrap = true,
                textAlign = TextAlign.Center
            )

            Text(
                text = Coche.modelo,
                modifier = Modifier.width(COL2_WIDTH),
                style = MaterialTheme.typography.bodyLarge.copy(
                    lineBreak = LineBreak.Paragraph,
                    hyphens = Hyphens.Auto
                ),
                softWrap = true,
                textAlign = TextAlign.Center
            )

            Text(
                text = Coche.color,
                modifier = Modifier.width(COL3_WIDTH),
                style = MaterialTheme.typography.bodyLarge,
                softWrap = true,
                textAlign = TextAlign.Center
            )

            Row(
                modifier = Modifier.width(ACTIONS_WIDTH),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { onEditClick(Coche) }) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Editar Coche"
                    )
                }
                IconButton(onClick = { onDeleteClick(Coche) }) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Eliminar Coche",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}
