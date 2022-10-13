package com.example.radioapp


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.radioapp.clicklistener.CountryClickListener
import com.example.radioapp.databinding.ActivityMainBinding
import com.example.radioapp.fragment.*
import com.example.radioapp.clicklistener.Datain
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() ,Datain,CountryClickListener {
  lateinit var  binding:ActivityMainBinding
 lateinit var radioFragment:RadioFragment
 lateinit var recomdFragemt:RecomdFragment
    lateinit var countryFragment:CountryFragment


 companion object {
    var searchText = ""

}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(layoutInflater)

        val view: View = binding.getRoot()
        setContentView(view)

         setupListener()


        val tab = findViewById<TabLayout>(R.id.bottom_nav)
        val viewpager = findViewById<ViewPager>(R.id.view1)
        setupViewPager(viewpager)
        tab!!.setupWithViewPager(viewpager)


        binding.imgFlag.setOnClickListener {
            val dialogFragment = CountryFragment(this)
            dialogFragment.show(supportFragmentManager, "My  Fragment")


        }

      binding.imgSort.setOnClickListener {

           val dialogFragment1 = SortFragment(this)
            dialogFragment1.show(supportFragmentManager, "My  Fragment")


       }



    }

    @SuppressLint("SuspiciousIndentation")
    private fun setupListener() {
        countryFragment = CountryFragment(this)
        binding.search1.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                // Your piece of code on keyboard search click

                searchText = binding.search1.text.toString()

                   Log.e("tagsearch"," "+ searchText)

                radioFragment.searchRadio(searchText)



                }
                return@OnEditorActionListener true

        })

    }


    @SuppressLint("SuspiciousIndentation")
    private fun setupViewPager(viewPager: ViewPager) {
      radioFragment = RadioFragment()
        recomdFragemt = RecomdFragment()

       // radioFragment.setdata(this)

        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(radioFragment, "Radio")
        adapter.addFragment(  recomdFragemt,"Recommend")
        viewPager.adapter = adapter
    }

    inner class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
        private val mFragmentList = ArrayList<Fragment>()
        private val mFragmentTitleList = ArrayList<String>()

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence {
            return mFragmentTitleList[position]
        }


    }

    override fun datasender(boolean: String) {

        recomdFragemt.fragmentRefresh(this@MainActivity ,boolean)


    }

    override fun datasender2(boolean2: String) {

//        val tag = "android:switcher:" + R.id.view1.toString() + ":" + 1
//
//        val f: RecomdFragment? = supportFragmentManager.findFragmentByTag(tag) as RecomdFragment?
//
//        if (f != null) {
//            f.getData2(boolean2)
//        }
//        Log.e("Tag", " "+ boolean2)

        recomdFragemt.fragmentRefresh2(this@MainActivity , boolean2)

    }


    ////// for  radio fragment update
    override fun clickCountry(id: String) {


     radioFragment.fragmentRefresh(this@MainActivity ,id)

    }







}




