package com.example.radioapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.radioapp.clicklistener.Datain
import com.example.radioapp.api.PreferencesModule
import com.example.radioapp.databinding.FragmentSortBinding
import dagger.hilt.android.AndroidEntryPoint
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class SortFragment(val datain:Datain) : DialogFragment() {

    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding:FragmentSortBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSortBinding.inflate(inflater, container, false)

        context?.let { PreferencesModule.init(it) }
        binding.radioFirst.setOnClickListener {
          datain.datasender("yes")
        }

         binding.radioSecond.setOnClickListener {


             datain.datasender2("yes")

         }

        binding.btnCancel.setOnClickListener {
            dismiss()
        }



        return binding.root
    }


    override fun onResume() {
        super.onResume()
    }


}



