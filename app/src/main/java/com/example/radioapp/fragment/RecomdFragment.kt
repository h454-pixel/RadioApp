package com.example.radioapp.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.radioapp.Adapter.RecommedListAdapter
import com.example.radioapp.Model.ListRecommed
import com.example.radioapp.ViewModel.RadioViewModel
import com.example.radioapp.api.PreferencesModule
import com.example.radioapp.databinding.FragmentRecomdBinding
import com.example.radioapp.util.NetworkResult
import com.example.radioapp.util.ToastUtil
import dagger.hilt.android.AndroidEntryPoint
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class RecomdFragment : Fragment() {

    lateinit var binding: FragmentRecomdBinding
    var programsList: ArrayList<ListRecommed.Recommed> = ArrayList()

  var boolean:String? =null
  var boolean2:Boolean?=null

    private val radioViewModel by viewModels<RadioViewModel>()
    private lateinit var adapter: RecommedListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
        }


    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRecomdBinding.inflate(inflater, container, false)
        context?.let { PreferencesModule.init(it) }
        //  boolean =

        Log.e("Tag1211", " "+PreferencesModule.read("sorta"))
        setupUI()
        setupObserver()
        return binding.root
    }

    private fun setupUI() {
        Log.e("Tag12111", "entry"+boolean)

        adapter = RecommedListAdapter(requireContext())
             // binding. = LinearLayoutManager(this)
             binding.recyclerview.adapter = adapter
             radioViewModel.getlistrecommed()


    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupObserver() {
        radioViewModel.getlistrecommed.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let {
                        if (it.success == 1) {
                            programsList.addAll(it.data);
                            adapter.setdata(programsList)

                            Log.e("tag101", "program list" + programsList)

                            binding.progressCir.visibility = View.GONE
                            adapter.notifyDataSetChanged()
                        }
                    }
                }

                is NetworkResult.Error -> {
                    binding.progressCir.visibility = View.VISIBLE
                    Log.e("Tag123", "fail" + response.message)
                    context?.let { ToastUtil.showCustomToast(it, "Network error") }
                }
                else -> {

                }
            }
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun fragmentRefresh(context:Context, boolean: String) {


        if(boolean.equals("yes") ){
            programsList.sortByDescending { it -> it.name }
           //
            Log.e(" ","Sortedlistdescending:"+programsList.sortedBy{ it -> it.name })
            adapter.setdata(programsList)
            adapter.notifyDataSetChanged()
        }


        }

    @SuppressLint("NotifyDataSetChanged")
    fun fragmentRefresh2(context:Context, boolean2: String) {

        if(boolean2.equals("yes")) {
         //   programsList.sortedWith(compareBy {it -> it.name })
            programsList.sortedBy{it.name}
            adapter.setdata(programsList)
            adapter.notifyDataSetChanged()

        }

    }


}












