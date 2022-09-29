package com.example.radioapp.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.radioapp.Adapter.CountryListAdapter
import com.example.radioapp.Adapter.RadioListAdapter
import com.example.radioapp.Model.ListRadio
import com.example.radioapp.R
import com.example.radioapp.ViewModel.RadioViewModel
import com.example.radioapp.Model.ListCountry
import com.example.radioapp.databinding.FragmentCountryBinding
import com.example.radioapp.databinding.FragmentRadioBinding
import com.example.radioapp.util.NetworkResult
import com.example.radioapp.util.ToastUtil
import dagger.hilt.android.AndroidEntryPoint

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
@AndroidEntryPoint
class CountryFragment : DialogFragment() {

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
        // radioViewModeL = ViewModelProvider(this).get(RadioViewModel::class.java)

        setupObserver()
        setupUI()

        return binding.root
    }

    private fun setupObserver() {
        radioViewModel.getlistradio.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let {
                        if (it.success == 200) {

                        }

                    }
                }

                is NetworkResult.Error -> {

                    Log.e("Tag123","fail")
                    context?.let { ToastUtil.showNormalToast(it,"fail") }
                }

                else -> {

                }
            }
        }
    }

    private fun setupUI() {
        adapter = CountryListAdapter(requireContext(), programsList)
        // binding. = LinearLayoutManager(this)
        binding.rcyCountry.adapter = adapter

        radioViewModel.getcountrylist("eng", "us")

    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CountryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}