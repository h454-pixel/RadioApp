package com.example.radioapp.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.radioapp.databinding.FragmentRadioBinding
import com.example.radioapp.ViewModel.RadioViewModel
import com.example.radioapp.Model.ListRadio

import com.example.radioapp.util.NetworkResult
import com.example.radioapp.Adapter.RadioListAdapter
import com.example.radioapp.PlayActivity
import com.example.radioapp.util.ToastUtil
import dagger.hilt.android.AndroidEntryPoint
import com.example.radioapp.api.PreferencesModule
import com.example.radioapp.api.RadioRequest
import kotlin.collections.ArrayList

@AndroidEntryPoint
class RadioFragment() : Fragment() {

    lateinit var binding: FragmentRadioBinding
    var programsList: ArrayList<ListRadio.RadioChannel> = ArrayList()
    var sharcountry: String = "in"


//
//    fun setdata(coutryclicklistner: CountryClickListener) {
//        //   ToastUtil.showNormalToast(this@MainActivity,"select country")
//    }

    private val radioViewModel by viewModels<RadioViewModel>()
    private lateinit var adapter: RadioListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            //   param1 = it.getString(ARG_PARAM1)
            //    param2 = it.getString(ARG_PARAM2)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRadioBinding.inflate(inflater, container, false)
        context?.let { PreferencesModule.init(it) }
        setupUI()
        setupObserver()


        return binding.root
    }

    private fun setupUI() {

        sharcountry = PreferencesModule.read("first").toString()
        adapter = RadioListAdapter(requireContext())
        binding.recyclerview.adapter = adapter
        val radioRequest = RadioRequest(
            cc = "US",
            lc = "eng",
            c_code = "in",
            curentpage = "1"
        )

        radioViewModel.getRadiolist(radioRequest)


    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupObserver() {

        radioViewModel.getlistradio.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let {
                        if (it.success == 1) {

                            //      context?.let { ToastUtil.showCustomToast(it,"Welcome") }
                            if (programsList != null) {

                                programsList.clear()
                            }

                            programsList.addAll(it.data);
                            adapter.setdata(programsList)
                            binding.progressCir.visibility = View.GONE
                            adapter.notifyDataSetChanged()
                           // searchRadio("")
                        } else {



                        }

                    }
                }
                is NetworkResult.Error -> {
                    Log.e("Tag123", "fail")
                    context?.let { ToastUtil.showCustomToast(it, "Network error") }
                    binding.progressCir.visibility = View.VISIBLE

                }

                else -> {

                }
            }
        }
    }
///// upadate radio data by selecting country name
    fun fragmentRefresh(context: Context, id: String) {
    context.let { ToastUtil.showCustomToast(it, "select country: " + id) }

    val radioRequest = RadioRequest(
            cc = "US",
            lc = "eng",
            c_code = id,
            curentpage = "1"
        )

        radioViewModel.getRadiolist(radioRequest)
    }



    //////// search radio fragment by radio name
    @SuppressLint("NotifyDataSetChanged")
    fun searchRadio(serchkeyword: String) {

        val serchkeyword2:String  =serchkeyword.lowercase()
        if (serchkeyword.isNotEmpty()) {
            val updatedProgramsList: ArrayList<ListRadio.RadioChannel> = ArrayList()
            for (radioChannel in programsList) {
                val c_name2 = radioChannel.name?.lowercase()
                if (c_name2 != null) {
                    if (c_name2.contains(serchkeyword2)) {
                        updatedProgramsList.add(radioChannel)
                    }
                }
            }
            adapter.setdata(updatedProgramsList)
            adapter.notifyDataSetChanged()
        } else {

            adapter.setdata(programsList)
            adapter.notifyDataSetChanged()
        }
    }
}







