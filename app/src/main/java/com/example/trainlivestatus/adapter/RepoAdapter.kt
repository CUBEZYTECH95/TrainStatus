package com.example.trainlivestatus.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.trainlivestatus.R
import com.example.trainlivestatus.clicklistner.ItemClick
import com.example.trainlivestatus.model.NameOrCodeModelItem

class RepoAdapter constructor(val context: Context) :
    ListAdapter<NameOrCodeModelItem, RecyclerView.ViewHolder>(DiffUtils) {


    object DiffUtils : DiffUtil.ItemCallback<NameOrCodeModelItem>() {

        override fun areItemsTheSame(
            oldItem: NameOrCodeModelItem,
            newItem: NameOrCodeModelItem
        ): Boolean {
            return oldItem.C == newItem.C
        }

        override fun areContentsTheSame(
            oldItem: NameOrCodeModelItem,
            newItem: NameOrCodeModelItem
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.train_autocomp_item, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


        val photo = getItem(position)

        val holder: MyViewHolder = holder as MyViewHolder
        holder.tvTrainNo.text = photo.C

        holder.itemView.setOnClickListener {

            Toast.makeText(context, "${photo.C}", Toast.LENGTH_SHORT).show()

        }

    }


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tvTrainNo: TextView = view.findViewById(R.id.tv_train_no)
    }

}