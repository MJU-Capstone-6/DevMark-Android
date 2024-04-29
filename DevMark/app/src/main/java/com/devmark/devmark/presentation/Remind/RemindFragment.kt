package com.devmark.devmark.presentation.Remind

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.devmark.devmark.databinding.FragmentRemindBinding
import com.devmark.devmark.databinding.FragmentWorkspaceBinding

class RemindFragment: Fragment(){
    private lateinit var binding: FragmentRemindBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRemindBinding.inflate(layoutInflater)
        return binding.root
    }
}