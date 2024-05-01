package com.devmark.devmark.presentation.Remind

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.devmark.devmark.MainActivity
import com.devmark.devmark.databinding.FragmentRemindBinding

class RemindFragment : Fragment() {
    private lateinit var binding: FragmentRemindBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as MainActivity).changeNaviVisibility(true)
        binding = FragmentRemindBinding.inflate(layoutInflater)
        return binding.root
    }
}