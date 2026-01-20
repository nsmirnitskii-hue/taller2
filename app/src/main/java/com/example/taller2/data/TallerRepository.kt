package com.example.taller2.data


import kotlinx.coroutines.flow.Flow

class TallerRepository(private val dao: CocheDao) {

    val todosLosCoches: Flow<List<Coche>> = dao.getAll()

    suspend fun insertarCoche(coche: Coche) {
        dao.insert(coche)
    }

    suspend fun getCochebyId(id: Int): Flow<Coche> {
        return dao.getById(id)
    }

    suspend fun eliminarCoche(coche: Coche) {
        dao.delete(coche)
    }

    suspend fun actualizarCoche(coche: Coche) {
        dao.update(coche)
    }

    // Borrar por ID directamente
    suspend fun eliminarPorId(id: Int) {
        dao.deleteById(id)
    }

    // Ejemplo de filtrado simple (puedes ajustar según tus DAOs)
    fun filtrarCoches(matricula: String?, modelo: String): Flow<List<Coche>> =
        dao.filtrarCoches(matricula, modelo)
}