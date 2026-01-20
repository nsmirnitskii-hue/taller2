package com.example.taller2.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taller2.MyLog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class CocheViewModel(
    private val repositorio: TallerRepository
) : ViewModel() {

    private val _cocheFiltrados = MutableStateFlow<List<Coche>>(emptyList())
    val cocheFiltrados: StateFlow<List<Coche>> = _cocheFiltrados

    var idCoche by mutableIntStateOf(0)
        private set
    var matricula by mutableStateOf("")
        private set
    var modelo by mutableStateOf("")
        private set
    var color by mutableStateOf("")
        private set
    var fechaEntrada by mutableStateOf("")
        private set

    var nombreResponsable by mutableStateOf("")
        private set
    var id_mecanico by mutableStateOf("")
        private set
    var filtrarMatricula by mutableStateOf("")
        private set
    var filtrarModelo by mutableStateOf("")
        private set
    var errorMensaje by mutableStateOf<String?>(null)
        private set

    init {
        viewModelScope.launch {
            repositorio.todosLosCoches.collect { lista ->
                _cocheFiltrados.value = lista
            }
        }
    }

    fun aplicarFiltros(matricula: String, modelo: String){
        viewModelScope.launch {
            if (filtrarMatricula.isEmpty() && filtrarModelo.isEmpty()){
                repositorio.todosLosCoches.collect { coches ->
                    _cocheFiltrados.value = coches
                }
            }else{
                repositorio.filtrarCoches(matricula, modelo).collect {coches ->
                    _cocheFiltrados.value = coches
                }
            }
        }
    }
    fun guardarCoche(onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val nuevo = Coche(
                    matricula = matricula.trim(),
                    modelo = modelo.trim(),
                    color = color.trim(),
                    fechaEntrada = fechaEntrada.trim(),
                    nombreResponsable = nombreResponsable.trim(),
                    id_mecanico = id_mecanico.toInt()
                )
                repositorio.insertarCoche(nuevo)
                onSuccess()
                limpiarFormulario()
            } catch (e: Exception) {
                errorMensaje = "Error al guardar: ${e.message}"
            }
        }
    }

    suspend fun eliminarCoche(cocheId: Int) {
        MyLog.d("viewModel.eliminarCoche + ${cocheId}")
        repositorio.eliminarCoche(Coche(cocheId,"","","","","",0))
    }
    fun editarCoche(onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val actualizado = Coche(
                    id = idCoche,
                    matricula = matricula.trim(),
                    modelo = modelo.trim(),
                    color = color.trim(),
                    fechaEntrada = fechaEntrada.trim(),
                    nombreResponsable = nombreResponsable.trim(),
                    id_mecanico = id_mecanico.toInt()
                )
                repositorio.actualizarCoche(actualizado)
                onSuccess()
                limpiarFormulario()
            } catch (e: Exception) {
                errorMensaje = "Error al editar: ${e.message}"
            }
        }
    }

    fun observarCoche(id: Int) {
        viewModelScope.launch {
            try {
                repositorio.getCochebyId(id)                // Flow<Libro>
                    .distinctUntilChanged()             // evita actualizaciones idénticas
                    .collect { coche ->
                        idCoche = coche.id
                        matricula = coche.matricula
                        modelo = coche.modelo
                        color = coche.color
                    }
            } catch (e: Exception) {
                errorMensaje = "Error al observar libro: ${e.message}"
            }
        }
    }

    fun limpiarFormulario() {
        matricula = ""; modelo = ""; color = ""; fechaEntrada = ""

        errorMensaje = null
    }

    fun onMatriculaChanged(nuevoTexto: String) { matricula = nuevoTexto }

    fun onFiltroMatriculaChanged(nuevoTexto: String) { filtrarMatricula = nuevoTexto }

    fun onModeloChanged(nuevoTexto: String) { modelo = nuevoTexto }

    fun onFiltroModeloChanged(nuevo: String) { filtrarModelo = nuevo }
    fun onColorChanged(nuevoTexto: String) { color = nuevoTexto }

    fun onFechaentradaChanged(nuevoTexto: String) { fechaEntrada = nuevoTexto }

    fun onNombreResponsableChanged(nuevoTexto: String) { nombreResponsable = nuevoTexto }

    fun insertarDatosPrueba() {
        viewModelScope.launch {
            val c1 = Coche(0,"AAAAA","Ford","Rojo","01/01/2020","Mikel",0)
            val c2 = Coche(0,"EEEEE","Hyundai","Blnco","01/01/2020","Mikel",0)
            val c3 = Coche(0,"FFFFF","Seat","Azul","01/01/2020","Mikel",0)
            val c4 = Coche(0,"GGGGG","Ford","Negro","01/01/2020","Mikel",0)
            repositorio.insertarCoche(c1)
            repositorio.insertarCoche(c2)
            repositorio.insertarCoche(c3)
            repositorio.insertarCoche(c4)
        }

    }
}