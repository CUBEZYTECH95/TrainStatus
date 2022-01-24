package com.example.trainlivestatus.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trainlivestatus.R
import com.example.trainlivestatus.clicklistner.ItemClick
import com.example.trainlivestatus.livestatus.FairInquiryActivity
import com.example.trainlivestatus.livestatus.SearchTrainActivity
import com.example.trainlivestatus.model.CategoryModel

class CategoryAdapter(val context: Context, val list: List<CategoryModel>) :
    RecyclerView.Adapter<CategoryAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

        val view = LayoutInflater.from(context).inflate(R.layout.item_dashboard, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, @SuppressLint("RecyclerView") position: Int) {

        holder.iv_rc.layoutManager = GridLayoutManager(context, 2)
        holder.iv_rc.adapter = DashbAdapter(context, list, object : ItemClick {
            override fun click(pos: Int) {

                if (pos==0){

                    context.startActivity(Intent(context,FairInquiryActivity::class.java))
                }

                if (pos==1){

                    context.startActivity(Intent(context,SearchTrainActivity::class.java))
                }

            }
        })

    }

    override fun getItemCount(): Int {

        return 1
    }

    class Holder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

        val iv_rc: RecyclerView = itemView.findViewById(R.id.iv_rc)

    }
}
