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
import androidx.fragment.app.viewModels
import com.devmark.devmark.presentation.view.MainActivity
import com.devmark.devmark.domain.model.Member
import com.devmark.devmark.databinding.FragmentSettingWorkspaceBinding
import com.devmark.devmark.presentation.utils.UiState

class WorkspaceSettingFragment(private val workspaceId: Int) : Fragment() {
    private lateinit var binding: FragmentSettingWorkspaceBinding
    private val viewModel: WorkSpaceSettingViewModel by viewModels()
    private lateinit var clipboard: ClipboardManager

    private var memberList = arrayListOf<Member>(
        Member("문장훈", 20),
        Member("이성진", 30),
        Member("최건호", 40),
        Member("함범준", 35),
        Member("홍길동", 120)
    )

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

        binding.lvWorkspaceSetting.adapter = MemberInfoLvAdapter(requireContext(), memberList)
        setListViewHeightBasedOnChildren(binding.lvWorkspaceSetting)

        return binding.root
    }

    private fun initListener(){
        binding.ibBackWorkspaceSetting.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.tvExitWorkspace.setOnClickListener {
            (requireActivity() as MainActivity).backToSelectWorkspaceActivity()
            requireActivity().finish()
        }
        binding.ivInviteCode.setOnClickListener {
            viewModel.getInviteCode(workspaceId)
        }
    }

    private fun observer(){
        viewModel.inviteState.observe(viewLifecycleOwner){
            when(it){
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