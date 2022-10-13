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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.radioapp.ViewModel.RadioViewModel
import com.example.radioapp.Model.ListRadio
import com.example.radioapp.util.NetworkResult
import com.example.radioapp.Adapter.RadioListAdapter
import com.example.radioapp.Model.ListRecommed
import com.example.radioapp.PlayActivity
import com.example.radioapp.util.ToastUtil
import dagger.hilt.android.AndroidEntryPoint
import com.example.radioapp.api.PreferencesModule
import com.example.radioapp.api.RadioRequest
import com.example.radioapp.databinding.FragmentRadioBinding
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList

@AndroidEntryPoint
class RadioFragment() : Fragment() {

    lateinit var binding: FragmentRadioBinding
    var programsList: ArrayList<ListRadio.RadioChannel> = ArrayList()
    var sharcountry: String = "in"

    ////////////paginantion////////
    private var offset: Int = 1
    private var canCallApi = true

    private val radioViewModel by viewModels<RadioViewModel>()
    private lateinit var adapter: RadioListAdapter

    private var loading = true
    var pastVisiblesItems = 0
    var visibleItemCount: Int = 0
    var totalItemCount: Int = 0

    var totalPages: Int = 0;

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


        val mLayoutManager: LinearLayoutManager = LinearLayoutManager(binding.root.context)
        binding.recyclerview.layoutManager = mLayoutManager

        adapter = RadioListAdapter(requireContext())
        binding.recyclerview.adapter = adapter

        binding.recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) { //check for scroll down
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            Log.v("...", "Last Item Wow !");
                            // Do pagination.. i.e. fetch new data

                            if (offset <= totalPages) {
                                offset++

                                fragmentPagination(offset)
                            }

                            loading = true;
                        }
                    }
                }
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


                       if (offset == 1)
                           programsList.clear()
//
                           //
                           //                                if (programsList.size > 0)



                            it?.let {
                                totalPages = it.totalpages
                                programsList.addAll(it.data)
                                adapter.setdata(programsList)
                                binding.progressCir.visibility = View.GONE
                                adapter.notifyItemRangeInserted(
                                    programsList.size,
                                    it.data.size
                                )

                            }

                        } else {

                            context?.let {
                                ToastUtil.showCustomToast(
                                    it,
                                    "not succesfully" + response.message
                                )
                            }

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

        sharcountry = id


        val radioRequest = RadioRequest(
            cc = "US",
            lc = "eng",
            c_code = id,
            curentpage = offset.toString()

        )

        programsList.clear()
        radioViewModel.getRadiolist(radioRequest)
    }


    fun fragmentPagination(offset: Int) {

        val radioRequest = RadioRequest(
            cc = "US",
            lc = "eng",
            c_code = sharcountry,
            curentpage = offset.toString()

        )

        radioViewModel.getRadiolist(radioRequest)
    }


    //////// search radio fragment by radio name
    @SuppressLint("NotifyDataSetChanged")
    fun searchRadio(serchkeyword: String) {

        val serchkeyword2: String = serchkeyword.lowercase()
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

    fun radiosort(context:Context, boolean: String) {

        if(boolean.equals("yes") ){
            programsList.sortByDescending { it -> it.name }
            //
            Log.e(" ","Sortedlistdescending:"+programsList.sortedBy{ it -> it.name })
            adapter.setdata(programsList)
            adapter.notifyDataSetChanged()
        }

    }

    fun radiosort2(context:Context, boolean2: String) {


        if(boolean2.equals("yes")) {

            Collections.sort(programsList, object : Comparator<ListRadio.RadioChannel> {
                override fun compare(lhs: ListRadio.RadioChannel, rhs: ListRadio.RadioChannel): Int {
                    return lhs.name!!.compareTo(rhs.name!!)
                }
            })

            // programsList.sortedBy{it.name}
            adapter.setdata(programsList)
            adapter.notifyDataSetChanged()

        }

    }


}







