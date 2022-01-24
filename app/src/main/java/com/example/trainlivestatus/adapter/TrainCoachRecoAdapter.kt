package com.example.trainlivestatus.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.trainlivestatus.R
import com.example.trainlivestatus.databinding.TrainCoachItemBinding

class TrainCoachRecoAdapter(val context: Context, val img: ArrayList<String>) :
    RecyclerView.Adapter<TrainCoachRecoAdapter.CoachViewHolder>() {

    var pxWidth = 0
    var layoutParams: LinearLayout.LayoutParams? = null


    init {

        this.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        val display = context.resources.displayMetrics
        this.pxWidth = display.widthPixels

    }

    class CoachViewHolder(itemview: TrainCoachItemBinding) :
        RecyclerView.ViewHolder(itemview.root) {

        val itemLayoutBinding: TrainCoachItemBinding = itemview
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CoachViewHolder {

        return CoachViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.train_coach_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CoachViewHolder, position: Int) {


        holder.itemLayoutBinding.apply {

            if (position == 0) {
                layoutParams!!.setMargins(pxWidth / 2 - 250, 0, 0, 0)
                trainCoachCon.layoutParams = layoutParams
            } else {
                layoutParams!!.setMargins(0, 0, 0, 0)
                trainCoachCon.layoutParams = layoutParams
            }
            when {

                img[position] == "L" -> {
                    ivTrain.setImageResource(R.drawable.ic_train_engine)
                }
                img[position] == "EOG" -> {
                    ivTrain.setImageResource(R.drawable.ic_train_genrater)
                }
                else -> {
                    ivTrain.setImageResource(R.drawable.ic_train_genrater)
                }
            }
        }

    }

    override fun getItemCount(): Int {

        return img.size

    }
}