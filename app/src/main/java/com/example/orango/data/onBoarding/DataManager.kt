package com.example.orango.data.onBoarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.orango.R
import com.example.orango.ui.onBoarding.OnBoardingViewPager
import com.example.orango.ui.onBoarding.ViewPagerFragment
import com.example.orango.util.*

object DataManager {
    private val onBoardingData = ArrayList<OnBoardingData>()
    val fragmentList = ArrayList<Fragment>()

    init {
        initData()
        initFragmentList()
    }

    private fun initData() {
        var onBoardingData = OnBoardingData(R.drawable.time_onboard,R.string.no_more_long_queues,R.string.you_can_now_skip_long_queues_at_stores_while_shopping)
        this.onBoardingData.add(onBoardingData)

        onBoardingData = OnBoardingData(R.drawable.scan_onboard,R.string.scan_title,R.string.scan_body)
        this.onBoardingData.add(onBoardingData)

        onBoardingData = OnBoardingData(R.drawable.credit_onboard,R.string.credit_title,R.string.credit_body)
        this.onBoardingData.add(onBoardingData)

        onBoardingData = OnBoardingData(R.drawable.email_onboard,R.string.email_title,R.string.email_body)
        this.onBoardingData.add(onBoardingData)
    }

    private fun initFragmentList(){
        for (item in onBoardingData){
            val bundle = Bundle()

            bundle.putInt(IMAGE_ID,item.imageId)
            bundle.putInt(TITLE,item.title)
            bundle.putInt(BODY,item.body)

            val fragment = ViewPagerFragment()
            fragment.arguments = bundle

            fragmentList.add(fragment)

        }
    }
}