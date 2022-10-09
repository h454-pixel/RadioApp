package com.example.radioapp.fragment

import android.annotation.SuppressLint
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
import com.example.radioapp.Model.util.NetworkResult
import com.example.radioapp.Model.util.ToastUtil
import dagger.hilt.android.AndroidEntryPoint


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
@AndroidEntryPoint
class RecomdFragment : Fragment() {

    lateinit var binding: FragmentRecomdBinding

     val programsList: ArrayList<ListRecommed.Recommed> = ArrayList()

  var boolean:Boolean? =null
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
//        if (boolean == true){
//
//
//            programsList.sortWith(Comparator.comparing { a -> a.name })
//
//        }

             adapter = RecommedListAdapter(requireContext(), programsList)
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
                            binding.progressCir.visibility = View.GONE
                            adapter.notifyDataSetChanged()
                        }
                    }
                }

                is NetworkResult.Error -> {
                    binding.progressCir.visibility = View.VISIBLE
                    Log.e("Tag123", "fail" + response.message)
//                    context?.let {
//                        ToastUtil.showNormalToast(
//                            it,
//                            "Network error" + response.message
//                        )
//                    }
                }
                else -> {

                }
            }
        }

    }

//    companion object {
//
//        var programsList: ArrayList<ListRecommed.Recommed> = ArrayList()
////        @JvmStatic
////        fun newInstance(param1: Boolean) =
////            RecomdFragment().apply {
////                arguments = Bundle().apply {
////                    putString(ARG_PARAM1, param1)
////
////                }
////            }
//    }



    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        Log.e("Tagstart", "entervalue" + boolean)

        if (boolean == true) {
            programsList.sortByDescending { list -> list.name }

            adapter.notifyDataSetChanged()

        } else if (boolean2 == true)

            programsList.sortedBy{ list -> list.name }

        adapter.notifyDataSetChanged()
    }



    fun getData(data: Any) {
        Log.e("Tagi11", "Rec "+data)

     boolean = data as Boolean

    }

    fun getData2(data2: Boolean) {

         boolean2 = data2 as Boolean


    }


}









