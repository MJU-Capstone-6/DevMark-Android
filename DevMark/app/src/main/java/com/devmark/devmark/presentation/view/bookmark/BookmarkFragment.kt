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
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.devmark.devmark.R
import com.devmark.devmark.data.utils.LoggerUtils
import com.devmark.devmark.databinding.FragmentBookmarkBinding
import com.devmark.devmark.domain.model.bookmark.BookmarkDetailEntity
import com.devmark.devmark.domain.model.bookmark.CommentEntity
import com.devmark.devmark.presentation.utils.UiState
import com.devmark.devmark.presentation.utils.capitalizeFirstLetter
import com.devmark.devmark.presentation.view.MainActivity
import com.devmark.devmark.presentation.view.MainViewModel
import com.devmark.devmark.presentation.view.workspace.OnItemClickListener

class BookmarkFragment(private val bookmarkId: Int) : Fragment() {

    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!
    private var isBookmark: Boolean = true
    private val viewModel: BookmarkViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var adapter: CommentRvAdapter
    private lateinit var clipboard: ClipboardManager
    private lateinit var bookmarkLink: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        (requireActivity() as MainActivity).changeNaviVisibility(false)
        clipboard = requireContext().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager

        setupObservers()
        setupListeners()
        setupCommentRecyclerView()
        fetchInitialData()
        return binding.root
    }

    private fun fetchInitialData() {
        viewModel.fetchData(bookmarkId)
        viewModel.fetchComment(bookmarkId)
    }

    private fun setupObservers() {
        viewModel.detailState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Failure -> showErrorToast("북마크 정보 조회 실패: ${state.error}")
                is UiState.Loading -> {}
                is UiState.Success -> updateBookmarkDetails(state.data)
            }
        }

        viewModel.commentState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Failure -> showErrorToast("실패: ${state.error}")
                is UiState.Loading -> {}
                is UiState.Success -> adapter.setData(state.data)
            }
        }

        viewModel.updateState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Failure -> showErrorToast("카테고리 수정 실패: ${state.error}")
                is UiState.Loading -> {}
                is UiState.Success -> updateCategory(state.data.categoryName)
            }
        }
    }

    private fun showErrorToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        LoggerUtils.error(message)
    }

    private fun updateBookmarkDetails(data: BookmarkDetailEntity) {
        bookmarkLink = data.link
        binding.apply {
            tvBookmarkTitle.text = data.title
            tvSummary.text = data.summary
            btnCategoryEdit.text = data.categoryName
        }
    }

    private fun updateCategory(categoryName: String) {
        binding.btnCategoryEdit.text = categoryName
    }

    private fun setupListeners() {
        binding.ibBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.btnReadable.setOnClickListener {
            toggleTextColor()
        }

        binding.ibBookmark.setOnClickListener {
            if (isBookmark) {
                viewModel.deleteBookmark(bookmarkId)
                binding.ibBookmark.setImageResource(R.drawable.ic_bookmark_remove)
            } else {
                viewModel.addBookmark()
                binding.ibBookmark.setImageResource(R.drawable.ic_bookmark_setting)
            }
            isBookmark = !isBookmark
        }

        binding.ibCopy.setOnClickListener {
            copyToClipboard(bookmarkLink)
        }

        binding.ibLink.setOnClickListener {
            openLink(bookmarkLink)
        }

        binding.btnCategoryEdit.setOnClickListener {
            showUpdateCategoryDialog()
        }

        binding.btnPost.setOnClickListener {
            viewModel.postComment(bookmarkId, binding.etComment.text.toString())
        }
    }

    private fun toggleTextColor() {
        val rust = ContextCompat.getColor(requireContext(), R.color.rust)
        val alabaster = ContextCompat.getColor(requireContext(), R.color.alabaster)

        binding.btnReadable.apply {
            setTextColor(if (currentTextColor == rust) alabaster else rust)
        }
    }

    private fun copyToClipboard(text: String) {
        clipboard.setPrimaryClip(ClipData.newPlainText("북마크 링크", text))
        Toast.makeText(requireContext(), "클립보드에 복사되었습니다.", Toast.LENGTH_SHORT).show()
    }

    private fun openLink(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    private fun showUpdateCategoryDialog() {
        val categoryNames = mainViewModel.categoryList.map { it.second }
        UpdateCategoryDialog(categoryNames).apply {
            setButtonClickListener(object : UpdateCategoryDialog.OnButtonClickListener {
                override fun onButtonClicked(categoryName: String) {
                    val requestCategory =
                        mainViewModel.categoryList.find { it.second.lowercase() == categoryName.lowercase() }?.second
                            ?: categoryName.capitalizeFirstLetter()
                    viewModel.updateCategory(requestCategory)
                }
            })
        }.show(requireActivity().supportFragmentManager, "")
    }

    private fun setupCommentRecyclerView() {
        adapter = CommentRvAdapter().apply {
            setItemClickListener(object : CommentRvAdapter.OnCommentClickListener {
                override fun onClick(type: String, item: CommentEntity) {
                    if (type == "delete") viewModel.deleteComment(bookmarkId, item.commentId)
                    else viewModel.updateComment(bookmarkId, item.commentId, item.commentContext)
                }
            })
        }
        binding.rvComment.apply {
            adapter = this@BookmarkFragment.adapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        adapter.setData(emptyList())
    }

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            // 뒤로가기 막기
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        callback.remove()
        _binding = null
    }
}
