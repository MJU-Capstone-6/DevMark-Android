package com.devmark.devmark.presentation.Recommend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.devmark.devmark.databinding.FragmentRecommendBinding
import com.devmark.devmark.databinding.FragmentWorkspaceBinding

class RecommendFragment : Fragment() {
    private lateinit var binding: FragmentRecommendBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecommendBinding.inflate(layoutInflater)
        return binding.root
    }
}