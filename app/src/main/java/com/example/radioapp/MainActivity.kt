package com.example.radioapp
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.radioapp.ViewModel.ShareViewModel
import com.example.radioapp.fragment.CountryFragment
import com.example.radioapp.fragment.RadioFragment
import com.example.radioapp.fragment.RecomdFragment
import com.example.radioapp.fragment.SortFragment
import com.example.radioapp.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
  lateinit var  binding:ActivityMainBinding
//    lateinit var img:ImageView
//    lateinit var img2:ImageView
  companion object {
    var searchText = ""

}
    var delay: Long = 1000 // 1 seconds after user stops typing
    var last_text_edit: Long = 0
    var handler: Handler = Handler(Looper.myLooper()!!)
    private lateinit var model: ShareViewModel

    val input_finish_checker = Runnable {
        if (System.currentTimeMillis() > last_text_edit + delay - 500) {
            searchText = binding.search1.text.toString()
            model.select(true)
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.getRoot()
        setContentView(view)

//        img=findViewById(R.id.img_flag)
//        img2=findViewById(R.id.img_sort)
//


        val tab = findViewById<TabLayout>(R.id.bottom_nav)
        val viewpager = findViewById<ViewPager>(R.id.view1)
        setupViewPager(viewpager)
        tab!!.setupWithViewPager(viewpager)
        model = ViewModelProvider(this).get(ShareViewModel::class.java)


        setupListener()





       binding.imgFlag.setOnClickListener {
            val dialogFragment = CountryFragment()
            dialogFragment.show(supportFragmentManager, "My  Fragment")


        }

      binding.imgSort.setOnClickListener {

           val dialogFragment1 = SortFragment()
            dialogFragment1.show(supportFragmentManager, "My  Fragment")

       }



    }

    private fun setupListener() {


            searchText = binding.search1.text.toString()

            binding.search1.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence, start: Int, count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence, start: Int, before: Int,
                    count: Int
                ) {

//                    val str: String = s.toString()
//                    binding.etSearch.setText(str)


//
//                imageButton.setOnClickListener{
//                searchText = ""
//            }


                    //You need to remove this to run only once
                    handler.removeCallbacks(input_finish_checker)
                }

                override fun afterTextChanged(s: Editable) {

                    //avoid triggering event when text is empty
                    if (s.length > 0) {
                        last_text_edit = System.currentTimeMillis()
                        handler.postDelayed(input_finish_checker, delay)
                    } else {
                        searchText = ""
                       RadioFragment.canCallApi = true
                        model.select(true)
                    }
                }
            })
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




