package com.example.trainlivestatus.livestatus

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trainlivestatus.R
import com.example.trainlivestatus.adapter.CategoryAdapter
import com.example.trainlivestatus.databinding.ActivityCategoryBinding
import com.example.trainlivestatus.model.CategoryModel

class CategoryActivity : BaseClass() {

    lateinit var binding: ActivityCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_category)

        for (item in Image_array.indices) {

            val categoryModel = CategoryModel();
            categoryModel.image = Image_array[item]
            list.add(categoryModel)
        }


        binding.ivRc.layoutManager = LinearLayoutManager(this)
        binding.ivRc.adapter = CategoryAdapter(this, list)
    }
}