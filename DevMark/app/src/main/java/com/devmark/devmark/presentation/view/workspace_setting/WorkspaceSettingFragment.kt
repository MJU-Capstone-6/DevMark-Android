package com.devmark.devmark.presentation.view.workspace_setting

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.devmark.devmark.data.utils.LoggerUtils
import com.devmark.devmark.presentation.view.MainActivity
import com.devmark.devmark.databinding.FragmentSettingWorkspaceBinding
import com.devmark.devmark.presentation.custom.CustomDialog
import com.devmark.devmark.presentation.utils.UiState
import com.devmark.devmark.presentation.view.MainViewModel

class WorkspaceSettingFragment(private val workspaceId: Int) : Fragment() {
    private lateinit var binding: FragmentSettingWorkspaceBinding
    private val viewModel: WorkSpaceSettingViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var clipboard: ClipboardManager
    private lateinit var listAdapter: MemberInfoLvAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as MainActivity).changeNaviVisibility(false)
        binding = FragmentSettingWorkspaceBinding.inflate(layoutInflater)

        clipboard = requireContext().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager

        observer()
        initListener()
        listAdapter = MemberInfoLvAdapter(requireContext(), listOf())
        binding.lvWorkspaceSetting.adapter = listAdapter
        viewModel.getWorkspaceSetting(workspaceId)

        return binding.root
    }

    private fun initListener() {
        binding.ibBackWorkspaceSetting.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.tvExitWorkspace.setOnClickListener {
            CustomDialog.getInstance(CustomDialog.DialogType.WS_EXIT, null).apply {
                setOnOKClickedListener {
                    viewModel.deleteWorkspace(mainViewModel.workspaceId)
                }
            }.show(requireActivity().supportFragmentManager, "")
        }
        binding.ivInviteCode.setOnClickListener {
            viewModel.getInviteCode(workspaceId)
        }
        binding.ivBookmarkInfo.setOnClickListener {
            viewModel.getBookmarkCode(workspaceId)
        }
    }

    private fun observer() {
        viewModel.inviteState.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Success -> {
                    binding.tvInviteCodeWorkspaceSetting.text = it.data
                    clipboard.setPrimaryClip(ClipData.newPlainText("InviteCode", it.data))
                    Toast.makeText(requireContext(), "클립보드에 복사되었습니다.", Toast.LENGTH_SHORT).show()
                }

                is UiState.Loading -> {}
                is UiState.Failure -> {
                    binding.tvInviteCodeWorkspaceSetting.text = it.error
                }
            }
        }

        viewModel.bookmarkCodeState.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Success -> {
                    binding.tvBookmarkInfoWorkspaceSetting.text = it.data
                    clipboard.setPrimaryClip(ClipData.newPlainText("Bookmark Code", it.data))
                    Toast.makeText(requireContext(), "클립보드에 복사되었습니다.", Toast.LENGTH_SHORT).show()
                }

                is UiState.Loading -> {}
                is UiState.Failure -> {
                    binding.tvBookmarkInfoWorkspaceSetting.text = it.error
                }
            }
        }

        viewModel.exitState.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Failure -> {
                    Toast.makeText(
                        requireContext(),
                        "워크스페이스 나가기 실패: ${it.error}",
                        Toast.LENGTH_SHORT
                    ).show()
                    LoggerUtils.error(it.error.toString())
                }

                is UiState.Loading -> {}
                is UiState.Success -> {
                    (requireActivity() as MainActivity).backToSelectWorkspaceActivity()
                }
            }
        }

        viewModel.uiState.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Failure -> {
                    Toast.makeText(
                        requireContext(),
                        "워크스페이스 정보 조회 실패: ${it.error}",
                        Toast.LENGTH_SHORT
                    ).show()
                    LoggerUtils.error(it.error.toString())
                }

                is UiState.Loading -> {}
                is UiState.Success -> {
                    var bookmarkSum = 0
                    binding.tvWorkspaceTitle.text = it.data.name
                    binding.tvWorkspaceDesc.text = it.data.description
                    listAdapter.setData(it.data.userBookmarkCount)
                    it.data.userBookmarkCount.forEach { bookmarkSum += it.bookmarkCount }
                    binding.tvTotalBookmarkNum.text = bookmarkSum.toString()
                    setListViewHeightBasedOnChildren(binding.lvWorkspaceSetting)
                }
            }
        }
    }

    private fun setListViewHeightBasedOnChildren(listView: ListView): Boolean {
        val listAdapter = listView.adapter ?: return false // Adapter가 null이면 함수 종료

        val numberOfItems = listAdapter.count

        var totalItemsHeight = 0
        for (itemPos in 0 until numberOfItems) {
            val item = listAdapter.getView(itemPos, null, listView)
            val px = 500 * (listView.resources.displayMetrics.density)
            item.measure(
                View.MeasureSpec.makeMeasureSpec(px.toInt(), View.MeasureSpec.AT_MOST),
                View.MeasureSpec.makeMeasureSpec(
                    (32 * listView.resources.displayMetrics.density).toInt(),
                    View.MeasureSpec.EXACTLY
                )
            )
            totalItemsHeight += item.measuredHeight // 각 아이템의 높이를 더함
        }

        val totalDividersHeight = listView.dividerHeight * (numberOfItems - 1)
        val totalPadding = listView.paddingTop + listView.paddingBottom
        val params = listView.layoutParams
        params.height = totalItemsHeight + totalDividersHeight + totalPadding
        listView.dividerHeight = 0
        listView.layoutParams = params
        listView.requestLayout()

        return true
    }

}