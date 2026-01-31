package com.example.activityandnavigationex.ui.coroutineex.timerex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.activityandnavigationex.R
import com.example.activityandnavigationex.databinding.FragmentTimerBinding
import com.example.activityandnavigationex.ui.common.utils.formatTime

class TimerFragment : Fragment() {
    private var binding: FragmentTimerBinding? = null
    private val viewModel: TimerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTimerBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.timeMillis.observe(viewLifecycleOwner) { seconds ->
            binding?.tvTime?.text = seconds.formatTime(requireContext())
        }

        viewModel.isRunning.observe(viewLifecycleOwner) { running ->
            binding?.imgStartStop?.setImageResource(if (running) R.drawable.ic_pause else R.drawable.ic_start)
        }

        binding?.imgStartStop?.setOnClickListener {
            if (viewModel.isRunning.value == true) {
                viewModel.stop()
            } else {
                viewModel.startOrResume()
            }
        }

        binding?.imgReset?.setOnClickListener {
            viewModel.reset()
        }
    }
}
