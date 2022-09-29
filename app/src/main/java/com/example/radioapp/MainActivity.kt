package com.example.radioapp
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.radioapp.fragment.RadioFragment
import com.example.radioapp.fragment.RecomdFragment
import com.google.android.material.tabs.TabLayout
import com.example.radioapp.fragment.SortFragment
import com.example.radioapp.fragment.CountryFragment
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var img:ImageView;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        img=findViewById(R.id.img_sort)
        val tab = findViewById<TabLayout>(R.id.bottom_nav)
        val viewpager = findViewById<ViewPager>(R.id.view1)

        setupViewPager(viewpager)
        tab!!.setupWithViewPager(viewpager)


        img.setOnClickListener {
            val dialogFragment = CountryFragment()
            dialogFragment.show(supportFragmentManager, "My  Fragment")

//            val dialogFragment = SortFragment()
//            dialogFragment.show(supportFragmentManager, "My  Fragment")
        }


    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(RadioFragment(), "Radio")
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
}


//        val firstFragment= RadioFragment()
//        val secondFragment=RecomdFragment()
//      //  val thirdFragment=ThirdFragment()
//
//        setCurrentFragment(firstFragment)
//
//        bottomNav.setOnNavigationItemSelectedListener {
//            when(it.itemId){
//                R.id.radio->setCurrentFragment(firstFragment)
//                R.id.Recommend->setCurrentFragment(secondFragment)
//
//
//            }
//            true
//        }
//
//    }
//
//    private fun setCurrentFragment(fragment:Fragment)=
//        supportFragmentManager.beginTransaction().apply {
//            replace(R.id.fragment_1,fragment)
//            commit()
//        }
//    }


//    private val navListener =
//        BottomNavigationView.OnNavigationItemSelectedListener { item -> // By using switch we can easily get
//            // the selected fragment
//            // by using there id.
//            var selectedFragment: Fragment? = null
//            when (item.itemId) {
//               R.id.radio-> selectedFragment = RadioFragment()
//                R.id.Recommend -> selectedFragment =RecomdFragment()
//
//            }
//            // It will help to replace the
//            // one fragment to other.
//            if (selectedFragment != null) {
//                supportFragmentManager.beginTransaction()
//                    .replace(R.id.fragment_1, selectedFragment)
//                    .commit()
//            }
//            true
//        }
//




