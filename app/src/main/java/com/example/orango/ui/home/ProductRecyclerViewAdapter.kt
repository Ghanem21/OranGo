package com.example.orango.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.data.repo.RepoImpl
import com.example.data.roomDB.OranGoDataBase
import com.example.data.roomDB.entities.ProductEntity
import com.example.domain.entity.json.auth.logIn.CustomerData
import com.example.orango.R
import com.example.orango.databinding.CardBestSellingInHomeScreenBinding
import com.example.orango.databinding.CardProductBinding
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductRecyclerViewAdapter(
    private val products: MutableList<ProductEntity>,
    private val cardNumber: Int
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    inner class ProductViewHolder(private val binding: CardBestSellingInHomeScreenBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductEntity) {
            binding.productNameInBestselling.text = product.productName
            binding.productPrice.text = product.price.toString() + " L.E"
            Glide.with(binding.root.context)
                .load(product.image)
                .apply(RequestOptions().override(1600, 1600).timeout(6000))
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.broken_img)
                .into(binding.productImgInBestselling)

            binding.root.setOnClickListener {
                val navController = binding.root.findNavController()
                navController.navigate(R.id.productFragment, bundleOf("productId" to product.id))
            }
        }
    }

    inner class FavouriteViewHolder(private val binding: CardProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val repo: RepoImpl

        private var savedCustomerData : CustomerData? = null

        private val sharedPreferences by lazy {
            binding.root.context.getSharedPreferences(
                "my_shared_preferences",
                Context.MODE_PRIVATE
            )
        }

        init {
            val database = OranGoDataBase.getInstance(binding.root.context)
            repo = RepoImpl(database)
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    val customerDataJson = sharedPreferences.getString("customer_data", null)
                    savedCustomerData = Gson().fromJson(customerDataJson, CustomerData::class.java)
                }catch (ex:Exception){
                    Toast.makeText(binding.root.context,ex.message,Toast.LENGTH_SHORT).show()
                    ex.printStackTrace()
                }
            }
        }
        fun bind(product: ProductEntity) {
            binding.favouriteIcon.setImageResource(R.drawable.heart_icon)
            binding.productName.text = product.productName
            binding.productLocation.text = product.location
            binding.priceBeforeDicount.text = binding.root.context.getString(
                R.string.product_price_before_discount,
                (product.price - (product.price * (product.offerValue / 100f))).toString()
            )
            binding.priceAfterDicount.text = product.price.toString() + " L.E"
            Glide.with(binding.root.context)
                .load(product.image)
                .apply(RequestOptions().override(1600, 1600).timeout(6000))
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.broken_img)
                .into(binding.productImg)

            if (product.liked == 0)
                binding.favouriteIcon.setImageResource(R.drawable.heart_icon)
            else
                binding.favouriteIcon.setImageResource(R.drawable.baseline_favorite_24)

            binding.root.setOnClickListener {
                val navController = binding.root.findNavController()
                navController.navigate(R.id.productFragment, bundleOf("productId" to product.id))
            }

            binding.favouriteIcon.setOnClickListener {
                updateFavourite(product)
            }
        }

        private fun updateFavourite(product: ProductEntity) {
            CoroutineScope(Dispatchers.Main).launch {
                product.let {
                    val liked = if (it.liked == 0) 1 else 0
                    val product = ProductEntity(
                        it.id,
                        it.categoryId,
                        it.categoryName,
                        it.image,
                        liked,
                        it.location,
                        it.offerValue,
                        it.price,
                        it.productName,
                        it.quantity,
                        it.sold_units
                    )
                    if (product.liked == 0)
                        binding.favouriteIcon.setImageResource(R.drawable.heart_icon)
                    else
                        binding.favouriteIcon.setImageResource(R.drawable.baseline_favorite_24)

                    try {
                        savedCustomerData?.user?.id?.let { it1 ->
                            repo.updateFavorites(
                                it1,
                                product
                            )
                        }
                    } catch (ex: Exception) {
                        Toast.makeText(binding.root.context, ex.message, Toast.LENGTH_SHORT).show()
                        ex.printStackTrace()
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (cardNumber) {
            1 -> {
                val binding = CardBestSellingInHomeScreenBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return ProductViewHolder(binding)
            }

            else -> {
                val binding = CardProductBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return FavouriteViewHolder(binding)
            }
        }
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val product = products[position]
        when (cardNumber) {
            1 -> (holder as ProductViewHolder).bind(product)
            else -> (holder as FavouriteViewHolder).bind(product)
        }
    }

    fun updateList(products: List<ProductEntity>) {
        this.products.clear()
        this.products.addAll(products)
        notifyDataSetChanged()
    }
}