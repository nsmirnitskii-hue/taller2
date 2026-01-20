package com.example.taller2.data


import kotlinx.coroutines.flow.Flow

class TallerRepository(private val cochedao: CocheDao,
    private val mecanicoDao: MecanicoDao,
    private val reparacionDao: ReparacionDao) {

    val todosLosCoches: Flow<List<Coche>> = cochedao.getAll()

    suspend fun insertarMecanico(mecanico: Mecanico) {
        mecanicoDao.insert(mecanico)
    }
    suspend fun insertarReparacion(reparacion: Reparacion) {
        reparacionDao.insert(reparacion)
    }
    suspend fun insertarCoche(coche: Coche) {
        cochedao.insert(coche)
    }

    suspend fun getCochebyId(id: Int): Flow<Coche> {
        return cochedao.getById(id)
    }

    suspend fun eliminarCoche(coche: Coche) {
        cochedao.delete(coche)
    }

    suspend fun actualizarCoche(coche: Coche) {
        cochedao.update(coche)
    }

    // Borrar por ID directamente
    suspend fun eliminarPorId(id: Int) {
        cochedao.deleteById(id)
    }

    // Ejemplo de filtrado simple (puedes ajustar según tus DAOs)
    fun filtrarCoches(matricula: String?, modelo: String): Flow<List<Coche>> =
        cochedao.filtrarCoches(matricula, modelo)
}