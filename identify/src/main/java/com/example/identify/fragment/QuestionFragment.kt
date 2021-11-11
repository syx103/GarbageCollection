package com.example.identify.fragment

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.base.HomePageFragmentPath
import com.example.base.QuestionFragmentPath
import com.example.identify.*
import com.example.identify.adapter.PageAdapter
import com.example.identify.viewmodel.QuestionViewModel

@Route(path = QuestionFragmentPath)
class QuestionFragment : Fragment(), View.OnClickListener {

    private lateinit var viewModel: QuestionViewModel
    private lateinit var questionsPager: ViewPager2
    private lateinit var pageAdapter: PageAdapter
    private lateinit var back: ImageView
    private var fragmentLists = mutableListOf<Fragment>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.question_fragment, container, false)
        questionsPager = view.findViewById(R.id.many_questions)
        back = view.findViewById(R.id.back)
        return view
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(QuestionViewModel::class.java)
        ARouter.getInstance().inject(this)
        initFragmentLists()
        back.setOnClickListener(this)
        pageAdapter = PageAdapter(requireActivity(), fragmentLists)
        questionsPager.adapter = pageAdapter
        questionsPager.isUserInputEnabled = false
        viewModel.currentIndex.observe(requireActivity()) {
            questionsPager.setCurrentItem(it, false)
        }
    }

    private fun initFragmentLists() {
        repeat((0..9).count()) {
            fragmentLists.add(QuestionItemFragment())
        }
        fragmentLists.add(QuestionResultFragment())
    }

    override fun onClick(v: View?) {
        requireActivity().supportFragmentManager.popBackStack()
    }

}