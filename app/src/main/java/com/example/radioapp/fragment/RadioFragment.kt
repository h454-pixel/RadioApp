package com.example.radioapp.fragment
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.radioapp.databinding.FragmentRadioBinding
import com.example.radioapp.ViewModel.RadioViewModel
import com.example.radioapp.Model.ListRadio

import com.example.radioapp.util.NetworkResult
import com.example.radioapp.Adapter.RadioListAdapter
import com.example.radioapp.util.ToastUtil
 import com.example.radioapp.ViewModel.ShareViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.example.radioapp.api.PreferencesModule
import com.example.radioapp.api.RadioRequest
import kotlin.collections.ArrayList

@AndroidEntryPoint
class RadioFragment : Fragment() {

    lateinit var binding: FragmentRadioBinding

    //    ///////searching////
//    private var offset = "0"
//    private var searchKey = ""
//    private lateinit var model: ShareViewModel
//    //////////////////
    var sharcountry: String = " "
    var allowresfresh = false

    companion object {
        var programsList: ArrayList<ListRadio.RadioChannel> = ArrayList()
        var canCallApi = false
    }

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
        // radioViewModeL = ViewModelProvider(this).get(RadioViewModel::class.java)

          setupUI()
          setupObserver()


        return binding.root
    }

    private fun setupUI() {

        sharcountry = PreferencesModule.read("first").toString()

        context?.let { ToastUtil.showNormalToast(it, "read" + sharcountry) }
        // val data = ListRadio.DataX("india","pop","a.jpg","dilip", " hindu","id3","www.google.com")
        // programsList.add( data)

        // model = ViewModelProvider(requireActivity()).get(ShareViewModel::class.java)
        adapter = RadioListAdapter(requireContext())
        // binding. = LinearLayoutManager(this)
        binding.recyclerview.adapter = adapter

//        searchKey = MainActivity.searchText
//        radioViewModel.getRadiolist("US", "eng", "in", searchKey)


        val radioRequest = RadioRequest(
            cc = "US",
            lc = "eng",
            c_code = sharcountry,
            curentpage = "1"
        )


        radioViewModel.getRadiolist(radioRequest)


    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupObserver() {

//        model.callApi.observe(requireActivity()) {
//            if (it) {
//                searchKey = MainActivity.searchText
//                offset = "0"
//                canCallApi = true
//                programsList.clear()
//                adapter.notifyDataSetChanged()
//                radioViewModel.getRadiolist("US", "eng", "in", searchKey)
//            }


        radioViewModel.getlistradio.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let {
                        if (it.success == 1) {

                            //      context?.let { ToastUtil.showCustomToast(it,"Welcome") }
                            programsList.addAll(it.data);
                            binding.progressCir.visibility = View.GONE
                            adapter.notifyDataSetChanged()

                        } else {

                            Log.e("Tag123", "fail" + it.message)

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




//    @SuppressLint("NotifyDataSetChanged")
//    override fun onResume() {
//        super.onResume()
//
//
//        if (allowresfresh)
//        {
//            context?.let { ToastUtil.showNormalToast(it," "+allowresfresh) }
//            allowresfresh = false;
//            setupUI()
//            setupObserver()
//            val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
//            ft.detach(this).attach(this).commit()
//        }
//        setupUI()
//        setupObserver()
//
//    }
//
//
//    override fun onPause() {
//        super.onPause()
//      allowresfresh = true
//
//    }




    }






