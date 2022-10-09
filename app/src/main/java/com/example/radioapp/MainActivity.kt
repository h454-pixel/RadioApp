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
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() ,Datain,CountryClickListener {
  lateinit var  binding:ActivityMainBinding
 lateinit var radioFragment:RadioFragment



    //    lateinit var img:ImageView
//    lateinit var img2:ImageView
  companion object {
    var searchText = ""

}
//    var delay: Long = 1000 // 1 seconds after user stops typing
//    var last_text_edit: Long = 0
//    var handler: Handler = Handler(Looper.myLooper()!!)
//    private lateinit var model: ShareViewModel
//
//    val input_finish_checker = Runnable {
//        if (System.currentTimeMillis() > last_text_edit + delay - 500) {
//            searchText = binding.search1.text.toString()
//            model.select(true)
//        }
//    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.getRoot()
        setContentView(view)
setupListener()
//        img=findViewById(R.id.img_flag)
//        img2=findViewById(R.id.img_sort)
//


        val tab = findViewById<TabLayout>(R.id.bottom_nav)
        val viewpager = findViewById<ViewPager>(R.id.view1)
        setupViewPager(viewpager)
        tab!!.setupWithViewPager(viewpager)
        //model = ViewModelProvider(this).get(ShareViewModel::class.java)


        ///setupListener()





       binding.imgFlag.setOnClickListener {
            val dialogFragment = CountryFragment(this)
            dialogFragment.show(supportFragmentManager, "My  Fragment")


        }

      binding.imgSort.setOnClickListener {

           val dialogFragment1 = SortFragment()
            dialogFragment1.show(supportFragmentManager, "My  Fragment")

       }



    }

    @SuppressLint("SuspiciousIndentation")
    private fun setupListener() {




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

        radioFragment.setdata(this)

        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(radioFragment, "Radio")
        adapter.addFragment(RecomdFragment(),"Recommend")
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

    override fun datasender(boolean: Boolean) {



       val tag = "android:switcher:" + R.id.view1.toString() + ":" + 1

        val f: RecomdFragment? = supportFragmentManager.findFragmentByTag(tag) as RecomdFragment?

        if (f != null) {
            f.getData(boolean)
        }
        Log.e("Tag", " "+ boolean)



    }

    override fun datasender2(boolean2: Boolean) {

        val tag = "android:switcher:" + R.id.view1.toString() + ":" + 1

        val f: RecomdFragment? = supportFragmentManager.findFragmentByTag(tag) as RecomdFragment?

        if (f != null) {
            f.getData2(boolean2)
        }
        Log.e("Tag", " "+ boolean2)



    }

    override fun clickCountry(id: String) {

      ///  ToastUtil.showNormalToast(this@MainActivity,"select country")

     radioFragment.fragmentRefresh(this@MainActivity ,id)

    }




}




