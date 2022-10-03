package com.example.radioapp.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.radioapp.Model.ListRecommed
import com.example.radioapp.ViewModel.RadioViewModel
import com.example.radioapp.databinding.FragmentRecomdBinding
import com.example.radioapp.util.NetworkResult
import com.example.radioapp.util.ToastUtil
import com.example.radioapp.Adapter.RecommedListAdapter
import dagger.hilt.android.AndroidEntryPoint


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
@AndroidEntryPoint
class RecomdFragment : Fragment() {

    lateinit var binding: FragmentRecomdBinding

   companion object {
       var programsList: ArrayList<ListRecommed.Recommed> = ArrayList()
   }

    private val  radioViewModel by viewModels<RadioViewModel>()
    private lateinit var adapter: RecommedListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding =FragmentRecomdBinding.inflate(inflater, container, false)
        setupUI()
        setupObserver()

        return binding.root
    }

    private fun setupUI() {

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
                            binding.progressCir.visibility = View.GONE
                            adapter.notifyDataSetChanged()
                        }
                    } }

                is NetworkResult.Error -> {
                    binding.progressCir.visibility = View.VISIBLE
                    Log.e("Tag123","fail" +response.message)
                    context?.let { ToastUtil.showNormalToast(it,"Network error"+response.message) }
                }
                else -> {

                }
            }
        }

    }
//
//    companion object {
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            RecomdFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
    }
