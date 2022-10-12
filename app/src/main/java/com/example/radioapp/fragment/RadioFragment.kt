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
import com.hudlr.utils.paginationrecyclerview.OnPageChangeListener
import kotlin.collections.ArrayList

@AndroidEntryPoint
class RadioFragment() : Fragment() {

    lateinit var binding: FragmentRadioBinding
    var programsList: ArrayList<ListRadio.RadioChannel> = ArrayList()
    var sharcountry: String = "in"

    ////////////paginantion////////
    private var offset: String = "1"
    private var canCallApi = true

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
//        val radioRequest = RadioRequest(
//            cc = "US",
//            lc = "eng",
//            c_code = "in",
//            curentpage = "1"
//        )
//
//        radioViewModel.getRadiolist(radioRequest)


        binding.recyclerview.setOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageChange(page: Int) {
                //Page change listener for recycler view
                if (programsList.size > 0)
                    offset = programsList.get(programsList.size - 1).st_id!!



                if (canCallApi) {
                    Log.e("Taglist", "fail"+programsList)
                    val radioRequest = RadioRequest(
                        cc = "US",
                        lc = "eng",
                        c_code = "in",
                        curentpage = offset
                    )

                    radioViewModel.getRadiolist(radioRequest)

                }

                Log.e("Taglist", "fail"+programsList)


                }
        })




    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupObserver() {

        radioViewModel.getlistradio.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let {
                        if (it.success == 1) {

                            //      context?.let { ToastUtil.showCustomToast(it,"Welcome") }
//                            if (programsList != null) {
//
//                                programsList.clear()
//                            }

//                            programsList.addAll(it.data);
//                            adapter.setdata(programsList)
//                            binding.progressCir.visibility = View.GONE
//                            adapter.notifyDataSetChanged()
                           // searchRadio("")


                            if (offset.equals("1")) {
                                if (programsList!= null && programsList.size > 0)
                                  programsList.clear()
                            }
                            it?.let {
                                if (it.data.size > 0) {
//                                    if (it.data.size < 20)
//                                        canCallApi = false
                               //     else
                                        canCallApi = true

                                   programsList.addAll(it.data)
                                    adapter.setdata(programsList)
                                    binding.progressCir.visibility = View.GONE
                                    adapter.notifyItemRangeInserted(
                                       programsList.size,
                                        it.data.size
                                    )

                                //    adapter.notifyDataSetChanged()

                                } else {
                                    canCallApi = false
                                }
                            }

                        } else {

                            context?.let { ToastUtil.showCustomToast(it, "not succesfully"+response.message) }

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
@SuppressLint("SuspiciousIndentation")
fun fragmentRefresh(context: Context, id: String) {
    context.let { ToastUtil.showCustomToast(it, "select country: " + id) }


    val radioRequest = RadioRequest(
            cc = "US",
            lc = "eng",
            c_code = id,
          //  curentpage = "1"
           curentpage = offset

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







