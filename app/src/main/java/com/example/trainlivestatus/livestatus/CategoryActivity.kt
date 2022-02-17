package com.example.trainlivestatus.livestatus

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trainlivestatus.R
import com.example.trainlivestatus.adapter.CategoryAdapter
import com.example.trainlivestatus.databinding.ActivityCategoryBinding
import com.example.trainlivestatus.model.CategoryModel
import java.util.ArrayList

class CategoryActivity : AppCompatActivity() {

    private var Image_array = intArrayOf(
        R.drawable.ic_fare_in,
        R.drawable.ic_search_train,
        R.drawable.ic_train_schedule,
        R.drawable.ic_live_train,
        R.drawable.ic_seat_availa,
        R.drawable.ic_live_station)

    var list = ArrayList<CategoryModel>()

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