package com.example.activityandnavigationex.ui.coroutineex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.activityandnavigationex.R
import com.example.activityandnavigationex.databinding.FragmentCoroutineMenuBinding

class CoroutineMenuFragment : Fragment() {

    private var binding: FragmentCoroutineMenuBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCoroutineMenuBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.run {
            txtProgressEx.setOnClickListener {
                findNavController().navigate(R.id.progressFragment)
            }

            txtTimerEx.setOnClickListener {
                findNavController().navigate(R.id.timerFragment)
            }
        }
    }
}