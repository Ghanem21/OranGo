package com.example.orango

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.data.remote.Api
import com.example.data.remote.ApiService
import com.example.data.roomDB.OranGoDataBase
import com.example.orango.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        navController = navHostFragment.navController

        lifecycleScope.launch(Dispatchers.IO) {
            val feedbackResponse = Api.retrofitService.addFeedback(1,"hello")
            Log.e("feedbackMsg", feedbackResponse.msg.toString())
            Log.e("feedbackError", feedbackResponse.error.toString())
            Log.e("feedbackStatus", feedbackResponse.status.toString())
        }




//        val db = OranGoDataBase.getInstance(this)
//
//        val dao = db.orangoDao
//        val productlists : List<ProductsEntity> = dao.getProducts()
//        Log.e("productlists", productlists.toString())
//
//        dao.insertProducts(ProductsEntity(
//            1,"Fruits", 2, "tomato_img", 1, "line 2",
//            20, 200, "Tomato", 7))
//
//        Log.e("productlists", dao.getProducts().toString())

    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}