package com.example.radioapp.fragment
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
import com.example.radioapp.util.ToastUtil
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class RadioFragment : Fragment() {

    lateinit var binding: FragmentRadioBinding

    companion object{
     var programsList: ArrayList<ListRadio.DataX> = ArrayList()
 }

    private val  radioViewModel by viewModels<RadioViewModel>()
    private lateinit var adapter: RadioListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            //   param1 = it.getString(ARG_PARAM1)
            //    param2 = it.getString(ARG_PARAM2)

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRadioBinding.inflate(inflater, container, false)
       // radioViewModeL = ViewModelProvider(this).get(RadioViewModel::class.java)

        setupUI()
    setupObserver()


        return binding.root
    }

    private fun setupUI() {

       // val data = ListRadio.DataX("india","pop","a.jpg","dilip", " hindu","id3","www.google.com")
       // programsList.add( data)


        adapter = RadioListAdapter(requireContext())
            // binding. = LinearLayoutManager(this)
       binding.recyclerview.adapter = adapter

      radioViewModel.getRadiolist("US", "eng", "in", "2")

    }

    private fun setupObserver() {

        radioViewModel.getlistradio.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let {
                        if (it.success == 1) {


                            programsList.addAll(it.data);
                           adapter.notifyDataSetChanged()

                        }else{

                            Log.e("Tag123","fail"+it.message)

                        }



                    } }
                is NetworkResult.Error -> {

                    Log.e("Tag123","fail")
                    context?.let { ToastUtil.showNormalToast(it,"fail") }
                }

                else -> {

                }
            }
        }
    }
}