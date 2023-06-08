package com.example.orango.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPager(private val fragmentList: MutableList<Fragment>, fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager,lifecycle){
    override fun getItemCount(): Int =
        fragmentList.size


    override fun createFragment(position: Int): Fragment = fragmentList[position]

    fun updateList(fragmentList: List<Fragment>){
        this.fragmentList.clear()
        this.fragmentList.addAll(fragmentList)
        notifyDataSetChanged()
    }

}