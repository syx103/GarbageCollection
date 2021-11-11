package com.example.identify.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.base.ImageRecognitionResultFragmentPath
import com.example.identify.R
import com.example.identify.adapter.ResultRVAdapter
import com.example.identify.model.DataList
import com.example.identify.model.ResultNewsList
import com.example.identify.viewmodel.QuestionViewModel

@Route(path = ImageRecognitionResultFragmentPath)
class ImageRecognitionResultFragment(private val resultLists: MutableList<DataList>) : Fragment(), View.OnClickListener {
    private lateinit var rv: RecyclerView
    private lateinit var back: ImageView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_image_recognition_result, container, false)
        rv = view.findViewById(R.id.result_rv)
        back = view.findViewById(R.id.image_back)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        ARouter.getInstance().inject(this)
        back.setOnClickListener(this)
        val adapter = ResultRVAdapter(resultLists)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.addItemDecoration(DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL))

    }

    override fun onClick(v: View?) {
        requireActivity().supportFragmentManager.popBackStack()
    }
}