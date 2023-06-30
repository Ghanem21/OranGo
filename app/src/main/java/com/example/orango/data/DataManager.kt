package com.example.orango.data

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.orango.R
import com.example.orango.data.models.OnBoardingData
import com.example.orango.data.models.Product
import com.example.orango.data.models.SettingAndServicesOption
import com.example.orango.ui.home.OfferViewPager
import com.example.orango.ui.onBoarding.ViewPagerFragment
import com.example.orango.util.BODY
import com.example.orango.util.IMAGE_ID
import com.example.orango.util.PRODUCT_ID
import com.example.orango.util.TITLE

object DataManager {
    private val onBoardingData = mutableListOf<OnBoardingData>()
    val onBoardingFragmentList = mutableListOf<Fragment>()

    private val offerProducts = ArrayList<Product>()
    val offerFragmentList = ArrayList<Fragment>()

    var services = mutableListOf<SettingAndServicesOption>()
    var settings = mutableListOf<SettingAndServicesOption>()

    init {
        initOnBoardingData()
        initOnBoardingFragmentList()
        initOfferFragment()
        services.addAll(intiServiceList())
        settings.addAll(intiSettingList())
    }

    private fun initOnBoardingData() {
        var onBoardingData = OnBoardingData(R.drawable.time_onboard,R.string.no_more_long_queues,R.string.you_can_now_skip_long_queues_at_stores_while_shopping)
        DataManager.onBoardingData.add(onBoardingData)

        onBoardingData = OnBoardingData(R.drawable.scan_onboard,R.string.scan_title,R.string.scan_body)
        DataManager.onBoardingData.add(onBoardingData)

        onBoardingData = OnBoardingData(R.drawable.credit_onboard,R.string.credit_title,R.string.credit_body)
        DataManager.onBoardingData.add(onBoardingData)

        onBoardingData = OnBoardingData(R.drawable.email_onboard,R.string.email_title,R.string.email_body)
        DataManager.onBoardingData.add(onBoardingData)
    }

    private fun initOnBoardingFragmentList(){
        for (item in onBoardingData){
            val bundle = Bundle()

            bundle.putInt(IMAGE_ID,item.imageId)
            bundle.putInt(TITLE,item.title)
            bundle.putInt(BODY,item.body)

            val fragment = ViewPagerFragment()
            fragment.arguments = bundle

            onBoardingFragmentList.add(fragment)

        }
    }

    private fun initOfferFragment(){
        for (product in offerProducts){
            val bundle = Bundle()

            bundle.putInt(PRODUCT_ID,product.id)

            val fragment = OfferViewPager()
            fragment.arguments = bundle

            offerFragmentList.add(fragment)
        }
    }

    private fun intiServiceList(): List<SettingAndServicesOption> {
        val suggestedMeals = SettingAndServicesOption(R.drawable.meals,"Suggested Meals")
        //val notes = SettingAndServicesOption(R.drawable.note,"Notes")
        val favourites = SettingAndServicesOption(R.drawable.baseline_filled_heart_24,"Favourites")

        return listOf(suggestedMeals,favourites)
    }

    private fun intiSettingList(): List<SettingAndServicesOption> {
        val points = SettingAndServicesOption(R.drawable.stars_points,"Points")
        val receiptHistory = SettingAndServicesOption(R.drawable.history,"Receipts History")
        val paymentDetails = SettingAndServicesOption(R.drawable.payment_card,"Payment Details")
        val contactUs = SettingAndServicesOption(R.drawable.contact,"Contact Us")
        val aboutUs = SettingAndServicesOption(R.drawable.about,"About Us")
        val logout = SettingAndServicesOption(R.drawable.logout,"Logout")
        val deleteAccount = SettingAndServicesOption(R.drawable.delete_pin,"Delete Account")

        return listOf(points,receiptHistory,paymentDetails,contactUs,aboutUs,logout,deleteAccount)
    }
}