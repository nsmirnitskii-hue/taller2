package com.example.taller2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.taller2.Ventanas.CocheForm
import com.example.taller2.Ventanas.VentanaVer
import com.example.taller2.data.CocheViewModel
import com.example.taller2.data.TallerDatabase
import com.example.taller2.data.TallerRepository
import com.example.taller2.ui.theme.Taller2Theme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Taller2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    gestorVentanas(Modifier.padding(innerPadding))
                }
            }
        }
    }
}
@Composable
fun gestorVentanas(modifier: Modifier) {
    val context = LocalContext.current

    val repositorio = TallerRepository(TallerDatabase.getDatabase(context).cocheDao())

    val factory = object : androidx.lifecycle.ViewModelProvider.Factory {
        override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
            return CocheViewModel(repositorio) as T
        }
    }

    val cocheViewModel: CocheViewModel = viewModel(factory = factory)

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "ver") {
        composable(
            route = "cocheForm/{option}",
            arguments = listOf(
                navArgument("option") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val option : String = backStackEntry.arguments?.getString("option") ?: "crear"
            CocheForm(navController, modifier, cocheViewModel, option)
        }
        composable("ver") { VentanaVer(navController, modifier, cocheViewModel) }

    }
}