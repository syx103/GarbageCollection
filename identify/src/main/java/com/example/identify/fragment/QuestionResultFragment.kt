package com.example.identify.fragment

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.base.QuestionResultFragmentPath
import com.example.identify.QuestionBean
import com.example.identify.viewmodel.QuestionViewModel
import com.example.identify.R

@Route(path = QuestionResultFragmentPath)
class QuestionResultFragment : Fragment() {
    private lateinit var viewModel: QuestionViewModel
    private lateinit var question: LinearLayout
    private lateinit var totalScore: TextView
    private var score = 0
    private lateinit var myAnswer: LinearLayout
    private lateinit var correctAnswer: LinearLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_question_result, container, false)
        question = view.findViewById(R.id.l1)
        totalScore = view.findViewById(R.id.score)
        myAnswer = view.findViewById(R.id.l2)
        correctAnswer = view.findViewById(R.id.l3)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        ARouter.getInstance().inject(this)
        viewModel = ViewModelProvider(requireActivity()).get(QuestionViewModel::class.java)
        val questionList = viewModel.questionPageLists.value
        (0 until questionList!!.size).forEach {
            if(questionList[it].newslist[0].explain == questionList[it].selectAnswer){
                score += 10
            }
        }
        totalScore.append("$score")
        initAlls(questionList)
    }

    private fun initAlls(questionList: MutableList<QuestionBean>){
        (0 until questionList.size).forEach {
            val questionText = TextView(context)
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.topMargin = 70
            questionText.layoutParams = params
            questionText.text = questionList[it].newslist[0].name
            questionText.typeface = Typeface.DEFAULT_BOLD
            questionText.setTextColor(Color.BLACK)
            question.addView(questionText)

            val myAnswerText = TextView(context)
            val correctAnswerText = TextView(context)
            myAnswerText.layoutParams = params
            correctAnswerText.layoutParams = params


            if(questionList[it].newslist[0].explain == questionList[it].selectAnswer){
                myAnswerText.setTextColor(Color.BLACK)
                correctAnswerText.setTextColor(Color.BLACK)
            }else{
                myAnswerText.setTextColor(Color.RED)
                correctAnswerText.setTextColor(Color.RED)
            }
            myAnswerText.text = questionList[it].selectAnswer
            myAnswerText.typeface = Typeface.DEFAULT_BOLD
            myAnswer.addView(myAnswerText)

            correctAnswerText.text = questionList[it].newslist[0].explain
            correctAnswerText.typeface = Typeface.DEFAULT_BOLD
            correctAnswer.addView(correctAnswerText)
        }
    }
}