package com.devmark.devmark.presentation.view.bookmark

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.devmark.devmark.R
import com.devmark.devmark.data.utils.LoggerUtils
import com.devmark.devmark.databinding.FragmentBookmarkBinding
import com.devmark.devmark.presentation.utils.UiState
import com.devmark.devmark.presentation.view.MainActivity
import com.devmark.devmark.presentation.view.workspace.CommentRvAdapter
import com.devmark.devmark.presentation.view.workspace.OnItemClickListener

class BookmarkFragment(private val bookmarkId: Int): Fragment() {
    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!
    private val viewModel: BookmarkViewModel by viewModels()
    private lateinit var adapter: CommentRvAdapter
    private lateinit var clipboard: ClipboardManager
    private lateinit var bookmarkLink: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as MainActivity).changeNaviVisibility(false)
        _binding = FragmentBookmarkBinding.inflate(layoutInflater)
        clipboard = requireContext().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        observer()
        initListener()
        return binding.root
    }

    private fun observer() {
        viewModel.detailState.observe(viewLifecycleOwner){
            when(it){
                is UiState.Failure -> {
                    Toast.makeText(requireContext(), "북마크 정보 조회 실패: ${it.error}", Toast.LENGTH_SHORT).show()
                    LoggerUtils.error("북마크 정보 조회 실패: ${it.error}")
                }
                is UiState.Loading -> {}
                is UiState.Success -> {
                    bookmarkLink = it.data.link

                    binding.apply {
                        tvBookmarkTitle.text = it.data.title
                        tvSummary.text = it.data.summary
                        btnCategoryEdit.text = it.data.categoryName
                    }
                }
            }
        }

        viewModel.commentState.observe(viewLifecycleOwner){
            when(it){
                is UiState.Failure -> {
                    Toast.makeText(requireContext(), "댓글 조회 실패: ${it.error}", Toast.LENGTH_SHORT).show()
                    LoggerUtils.error("댓글 조회 실패: ${it.error}")
                }
                is UiState.Loading -> {}
                is UiState.Success -> {
                    adapter.setData(it.data)
                }
            }
        }
    }

    private fun initListener() {
        binding.ibBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.btnReadable.setOnClickListener {
            val rust = ContextCompat.getColor(requireContext(), R.color.rust)
            val alabaster = ContextCompat.getColor(requireContext(), R.color.alabaster)

            with(binding.btnReadable){
                if (currentTextColor == rust) {
                    setTextColor(alabaster)
                } else if (currentTextColor == alabaster) {
                    setTextColor(rust)
                }
            }
        }

        binding.ibCopy.setOnClickListener {
            clipboard.setPrimaryClip(ClipData.newPlainText("북마크 링크", bookmarkLink))
            Toast.makeText(requireContext(), "클립보드에 복사되었습니다.", Toast.LENGTH_SHORT).show()
        }

        binding.ibLink.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(bookmarkLink))
            startActivity(intent)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchData(bookmarkId)
        setCommentRv()
        viewModel.fetchComment(bookmarkId)
    }

    private fun setCommentRv(){
        adapter = CommentRvAdapter().apply {
            this.setItemClickListener(object : OnItemClickListener {
                override fun onClick(item: Int) {
                    LoggerUtils.info("댓글 테스트: $item")
                }
            })
        }
        // todo 이후 더미 데이터 제거 필요
        binding.rvComment.adapter = adapter
        binding.rvComment.layoutManager = LinearLayoutManager(requireContext())
        adapter.setData(listOf())
    }
}