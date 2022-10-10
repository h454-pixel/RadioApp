package com.example.radioapp.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.radioapp.Adapter.CountryListAdapter
import com.example.radioapp.MainActivity
import com.example.radioapp.ViewModel.RadioViewModel
import com.example.radioapp.Model.ListCountry
import com.example.radioapp.Model.ListRadio
import com.example.radioapp.databinding.FragmentCountryBinding
import com.example.radioapp.util.NetworkResult
import com.example.radioapp.api.PreferencesModule
import com.example.radioapp.clicklistener.CountryClickListener
import com.example.radioapp.util.ToastUtil

import dagger.hilt.android.AndroidEntryPoint

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
@AndroidEntryPoint
class CountryFragment(val coutryclicklistner : CountryClickListener) : DialogFragment() {

    lateinit var binding: FragmentCountryBinding
    private var programsList: ArrayList<ListCountry.Country> = ArrayList()
    private val  radioViewModel by viewModels<RadioViewModel>()
    private lateinit var adapter:CountryListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)



        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCountryBinding.inflate(inflater, container, false)
        context?.let { PreferencesModule.init(it) }
        // radioViewModeL = ViewModelProvider(this).get(RadioViewModel::class.java)
        setupListener()
        setupUI()
        setupObserver()


        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged", "SuspiciousIndentation")
    private fun setupObserver() {
        radioViewModel.getlistcountry.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let {
                        if (it.success == 1) {

                         programsList.addAll(it.data);
                            adapter.setdata(programsList)
                          binding.progressCir.visibility = View.GONE
                            adapter.notifyDataSetChanged()

                        }

                    }
                }

                is NetworkResult.Error -> {
                    binding.progressCir.visibility = View.VISIBLE
                    Log.e("Tag123","fail")
                    context?.let { ToastUtil.showCustomToast(it, "Network error") }
                }

                else -> {

                }
            }
        }
    }

    private fun setupUI() {
        adapter = CountryListAdapter(requireContext(),coutryclicklistner)
        // binding. = LinearLayoutManager(this)
        binding.rcyCountry.adapter = adapter

        radioViewModel.getcountrylist("eng", "us")

    }

    @SuppressLint("NotifyDataSetChanged")
    fun searchRadio(serchkeyword: String) {
           val serchkeyword2:String  =serchkeyword.lowercase()

        if (serchkeyword.isNotEmpty()) {
            val updatedProgramsList: ArrayList<ListCountry.Country> = ArrayList()
            for (radioChannel in programsList) {
                 val c_name2 = radioChannel.c_name.lowercase()

                if (c_name2.contains(serchkeyword2)) {
                    updatedProgramsList.add(radioChannel)
                }
            }
            adapter.setdata(updatedProgramsList)
            adapter.notifyDataSetChanged()
        } else {

            adapter.setdata(programsList)
            adapter.notifyDataSetChanged()
        }
    }
    private fun setupListener() {
        binding.searchBar.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                // Your piece of code on keyboard search click
                MainActivity.searchText = binding.searchBar.text.toString()
                Log.e("tagsearch", " " + MainActivity.searchText)
                searchRadio(MainActivity.searchText)
            }
            return@OnEditorActionListener true

        })

    }
}