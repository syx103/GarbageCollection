package com.example.identify.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.base.QuestionItemFragmentPath
import com.example.identify.model.NetServiceManager
import com.example.identify.QuestionBean
import com.example.identify.viewmodel.QuestionViewModel
import com.example.identify.R

@Route(path = QuestionItemFragmentPath)
class QuestionItemFragment() : Fragment() {
    private lateinit var question: TextView
    private lateinit var currentPageText: TextView
    private var index: Int = 0
    private lateinit var viewModel: QuestionViewModel
    private lateinit var selectRatioGroup: RadioGroup
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_question_item, container, false)
        question = view.findViewById(R.id.question)
        currentPageText = view.findViewById(R.id.current_index)
        selectRatioGroup = view.findViewById(R.id.question_answer)
        return view
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        ARouter.getInstance().inject(this)
        viewModel = ViewModelProvider(requireActivity()).get(QuestionViewModel::class.java)
        index = viewModel.currentIndex.value!!
        if (index < 10) {
            NetServiceManager(viewModel).requestNetwork()
        }
        viewModel.questionPageLists.observe(requireActivity()) {
            question.text = it[it.size - 1].newslist[0].name
            currentPageText.text = "${it.size}"
            initRatioView(it)
        }
    }

    private fun initRatioView(questionList: MutableList<QuestionBean>) {
        selectRatioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.choice_recyclable ->
                    questionList[questionList.size - 1].selectAnswer = "可回收垃圾"
                R.id.choice_household_food ->
                    questionList[questionList.size - 1].selectAnswer = "湿垃圾"
                R.id.choice_residual ->
                    questionList[questionList.size - 1].selectAnswer = "干垃圾"
                else ->
                    questionList[questionList.size - 1].selectAnswer = "有害垃圾"
            }
            updateIndex()
        }
    }

    private fun updateIndex() {
        viewModel.updateIndex(index + 1)
    }

}