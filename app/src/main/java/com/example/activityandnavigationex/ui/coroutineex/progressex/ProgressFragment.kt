package com.example.activityandnavigationex.ui.coroutineex.progressex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.activityandnavigationex.R
import com.example.activityandnavigationex.databinding.FragmentProgressBinding
import com.example.activityandnavigationex.ui.coroutineex.progressex.ProgressViewmodel

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
            binding?.txtPercent?.text = getString(R.string.download_percent, progress)
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