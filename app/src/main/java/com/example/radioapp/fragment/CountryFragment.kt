package com.example.radioapp.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.radioapp.Adapter.CountryListAdapter
import com.example.radioapp.ViewModel.RadioViewModel
import com.example.radioapp.Model.ListCountry
import com.example.radioapp.databinding.FragmentCountryBinding
import com.example.radioapp.util.NetworkResult
import com.example.radioapp.util.ToastUtil
import com.example.radioapp.api.PreferencesModule

import dagger.hilt.android.AndroidEntryPoint

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
@AndroidEntryPoint
class CountryFragment : DialogFragment() ,CountryListAdapter.Clickonsingle {

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
                          binding.progressCir.visibility = View.GONE
                            adapter.notifyDataSetChanged()

                        }

                    }
                }

                is NetworkResult.Error -> {
                    binding.progressCir.visibility = View.VISIBLE
                    Log.e("Tag123","fail")
                    context?.let { ToastUtil.showNormalToast(it,"fail") }
                }

                else -> {

                }
            }
        }
    }

    private fun setupUI() {
        adapter = CountryListAdapter(requireContext(), programsList,this)
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

    override fun getclickonsingle(id: String) {

        PreferencesModule.write("first",id).toString()
        context?.let { ToastUtil.showNormalToast(it,"write"+id) }

    }
}