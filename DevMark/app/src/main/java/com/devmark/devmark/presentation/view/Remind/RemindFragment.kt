package com.devmark.devmark.presentation.view.remind

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.devmark.devmark.data.utils.LoggerUtils
import com.devmark.devmark.presentation.view.MainActivity
import com.devmark.devmark.databinding.FragmentRemindBinding
import com.devmark.devmark.presentation.utils.UiState
import com.devmark.devmark.presentation.view.bookmark.BookmarkFragment
import com.devmark.devmark.presentation.view.workspace.OnItemClickListener

class RemindFragment : Fragment() {
    private val viewModel: RemindViewModel by viewModels()
    private lateinit var binding: FragmentRemindBinding
    private lateinit var remindRvAdapter: RemindRvAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // BottomNav 출력
        (requireActivity() as MainActivity).changeNaviVisibility(true)
        binding = FragmentRemindBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observer()
        setRemindRv()
        viewModel.fetchData()
    }

    private fun observer() {
        viewModel.uiState.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Failure -> showErrorToast("리마인드 알림 조회 실패: ${it.error}")
                is UiState.Loading -> {}
                is UiState.Success -> {
                    remindRvAdapter.setData(it.data)
                }
            }
        }
    }

    private fun showErrorToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        LoggerUtils.error(message)
    }

    private fun setRemindRv() {
        remindRvAdapter = RemindRvAdapter()
            .apply {
                this.setItemClickListener(object : OnItemClickListener {
                    override fun onClick(id: Int) {
                        (requireActivity() as MainActivity).replaceFragmentWithBackstack(
                            BookmarkFragment(id)
                        )
                    }
                })
            }

        binding.rvRemind.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = remindRvAdapter
        }
    }
}