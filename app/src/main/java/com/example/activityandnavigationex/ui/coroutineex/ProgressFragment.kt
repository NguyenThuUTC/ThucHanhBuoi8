package com.example.activityandnavigationex.ui.coroutineex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.activityandnavigationex.databinding.FragmentProgressBinding

class ProgressFragment : Fragment() {

    val progressViewmodel: ProgressViewmodel by viewModels()
    var binding: FragmentProgressBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProgressBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressViewmodel.progress.observe(viewLifecycleOwner) { progress ->
            binding?.txtPercent?.text = "$progress%"
            binding?.progressPercent?.progress = progress
        }

        binding?.run {
            btnStart.setOnClickListener {
                progressViewmodel.startDownload()
            }

            btnStop.setOnClickListener {
                progressViewmodel.stopDownload()
            }
        }//ctrl+alt+l -> format code
    }

}