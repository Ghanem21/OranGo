package com.example.orango

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.data.roomDB.ProductsDatabase
import com.example.data.roomDB.ProductsEntity
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


        val db = ProductsDatabase.getInstance(this)

        val products = db.productsDao()
        val productlists : List<ProductsEntity> = products.getProducts()
        Log.e("productlists", productlists.toString())

        products.insertProducts(ProductsEntity(
            1,"Fruits", 2, "tomato_img", 1, "line 2",
            20, 200, "Tomato", 7))

        Log.e("productlists", products.getProducts().toString())

    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}