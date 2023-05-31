
package com.example.eitheror.ui.addquiz

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.eitheror.databinding.FragmentAddQuizBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddQuizBottomSheetFragment : BottomSheetDialogFragment() {
    lateinit var binding: FragmentAddQuizBottomSheetBinding
    var selectedCategories = arrayListOf<String>()
    val data = MutableLiveData<ArrayList<String>>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddQuizBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addQuizConfirm.setOnClickListener {
            onCheckboxClicked()
            if(selectedCategories.isEmpty()){
                Toast.makeText(requireContext(), "카테고리를 하나 이상 선택해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            data.value = selectedCategories
            dismiss()
        }
    }


    private fun onCheckboxClicked() {
        if (binding.addQuizCategoryFun.isChecked) selectedCategories.add("재미")
        if (binding.addQuizCategoryDaily.isChecked) selectedCategories.add("일상")
        if (binding.addQuizCategoryLove.isChecked) selectedCategories.add("연인")
        if (binding.addQuizCategoryFood.isChecked) selectedCategories.add("음식")
        if (binding.addQuizCategoryTaste.isChecked) selectedCategories.add("취향")
        if (binding.addQuizCategoryIf.isChecked) selectedCategories.add("만약")
        if (binding.addQuizCategoryJob.isChecked) selectedCategories.add("직장")
        if (binding.addQuizCategoryCaution.isChecked) selectedCategories.add("비위조심")
        if (binding.addQuizCategoryLife.isChecked) selectedCategories.add("인생")
    }

}