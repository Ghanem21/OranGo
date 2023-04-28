package com.example.orango

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.orango.databinding.ActivityMainBinding

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

//        lifecycleScope.launch(Dispatchers.IO) {
//            val feedbackResponse = Api.retrofitService.addFeedback(1,"hello")
//            Log.e("feedbackMsg", feedbackResponse.msg.toString())
//            Log.e("feedbackError", feedbackResponse.error.toString())
//            Log.e("feedbackStatus", feedbackResponse.status.toString())
//        }
//



//        val db = OranGoDataBase.getInstance(this)
//
//        val dao = db.orangoDao
//        val productlists : LiveData<List<ProductEntity>> = dao.getProducts()
//        Log.e("productlists", productlists.toString())
//
//        dao.addProduct(
//            listOf(
//                ProductEntity(
//                    1,2, "veg", "tomato_img", 1, "line 2",
//                    20, 200, "Tomato", 7,5)
//            )
//        )
//
//        Log.e("productlists", dao.getProducts().toString())






//        val product : List<ProductJson> = listOf(
//            ProductJson(1 , "veg", 2, "dbvkjsd" ,
//        1,"fvsddfv",20,50,"carrot",8,3)
//        )
//
//        val productEntity = product.asDatabaseModel()
//
//        Log.d("TEST", "${productEntity[0]}")


//        val db = OranGoDataBase.getInstance(this)
//        val dao = db.orangoDao
//        lifecycleScope.launch(Dispatchers.IO) {
//            dao.addProduct(productEntity)
//        }
//
//        val dao = db.orangoDao
//        val repo = RepoImpl(db)
//        repo.insertFavorite(productEntity)

    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}