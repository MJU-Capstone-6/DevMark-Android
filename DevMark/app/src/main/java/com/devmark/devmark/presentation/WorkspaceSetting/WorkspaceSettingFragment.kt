package com.devmark.devmark.presentation.WorkspaceSetting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.devmark.devmark.MainActivity
import com.devmark.devmark.data.Member
import com.devmark.devmark.databinding.FragmentSettingWorkspaceBinding
import com.devmark.devmark.presentation.Setting.SettingFragment

class WorkspaceSettingFragment : Fragment() {
    private lateinit var binding: FragmentSettingWorkspaceBinding
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

        binding.lvWorkspaceSetting.adapter = MemberInfoLvAdapter(requireContext(), memberList)
        setListViewHeightBasedOnChildren(binding.lvWorkspaceSetting)

        binding.ibBackWorkspaceSetting.setOnClickListener {
            // 이전 화면으로 뒤로가기
            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.tvExitWorkspace.setOnClickListener {
            (requireActivity() as MainActivity).backToSelectWorkspaceActivity()
            requireActivity().finish()
        }

        return binding.root
    }

    fun setListViewHeightBasedOnChildren(listView: ListView): Boolean {
        val listAdapter = listView.adapter ?: return false // Adapter가 null이면 함수 종료

        val numberOfItems = listAdapter.count

        // Get total height of all items.
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

        // Get total height of all item dividers.
        val totalDividersHeight = listView.dividerHeight * (numberOfItems - 1)

        // Get padding
        val totalPadding = listView.paddingTop + listView.paddingBottom

        // 리스트 뷰 높이 설정
        val params = listView.layoutParams
        params.height = totalItemsHeight + totalDividersHeight + totalPadding
        listView.dividerHeight = 0
        listView.layoutParams = params
        listView.requestLayout()

        return true
    }

}