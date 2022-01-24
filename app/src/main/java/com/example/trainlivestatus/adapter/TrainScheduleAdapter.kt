package com.example.trainlivestatus.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trainlivestatus.R
import com.example.trainlivestatus.databinding.CoachPositionViewItemBinding
import com.example.trainlivestatus.databinding.TrainDayViewItemBinding
import com.example.trainlivestatus.databinding.TrainDetailsViewItemBinding
import com.example.trainlivestatus.databinding.TrainNameViewItemBinding
import com.example.trainlivestatus.model.InterstnModel
import kotlin.collections.ArrayList

class TrainScheduleAdapter(
    val context: Context,
    val interstnModel: InterstnModel,
    trainNo: String?
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var trainNo: String? = null

    class TrainNameViewHolder(itemview: TrainNameViewItemBinding) :
        RecyclerView.ViewHolder(itemview.root) {

        val binding: TrainNameViewItemBinding = itemview
    }

    class CoachPositionViewHolder(itemview: CoachPositionViewItemBinding) :
        RecyclerView.ViewHolder(itemview.root) {

        val binding: CoachPositionViewItemBinding = itemview
    }

    class TrainDayViewHolder(itemview: TrainDayViewItemBinding) :
        RecyclerView.ViewHolder(itemview.root) {

        val binding: TrainDayViewItemBinding = itemview
    }

    class TrainDetailsListViewHolder(itemView: TrainDetailsViewItemBinding) :
        RecyclerView.ViewHolder(itemView.root) {

        val binding: TrainDetailsViewItemBinding = itemView
    }


    init {

        this.trainNo = trainNo
        val weekday = arrayOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
    }

    companion object {

        const val TRAIN_NAME_TYPE = 0
        const val COACH_POSITION_TYPE = 1
        const val TRAIN_DAY_TYPE = 2
        const val TRAIN_DETAILS_TYPE = 3

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {

            TRAIN_NAME_TYPE -> TrainNameViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.train_name_view_item,
                    parent,
                    false
                )
            )
            COACH_POSITION_TYPE -> CoachPositionViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.coach_position_view_item,
                    parent,
                    false
                )
            )
            TRAIN_DAY_TYPE -> TrainDayViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.train_day_view_item,
                    parent,
                    false
                )
            )
            else -> TrainDetailsListViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.train_details_view_item,
                    parent,
                    false
                )
            )
        }

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


        when (holder.itemViewType) {

            TRAIN_NAME_TYPE -> {

                val trainNameViewHolder = holder as TrainNameViewHolder

                trainNameViewHolder.binding.apply {

                    tvTrainName.text = interstnModel.trainName + "-" + trainNo

                    btnLiveStatus.setOnClickListener {

                        /* val intent = Intent(context, LivestatusallActivity::class.java)
                         intent.putExtra("trainNo", trainNo)
                         intent.putExtra("trainName", searchTrainModel.trainName)
                         context.startActivity(intent)*/
                    }

                }


            }

            COACH_POSITION_TYPE -> {

                if (interstnModel.coachPosition != null) {

                    val coach: List<String> = interstnModel.coachPosition.split(" ")

                    if (coach.size > 5) {

                        val coachPositionViewHolder = holder as CoachPositionViewHolder

                        val img: ArrayList<String> = ArrayList()

                        coachPositionViewHolder.binding.linearLayout2.visibility = View.VISIBLE
                        coachPositionViewHolder.binding.rv.visibility = View.VISIBLE

                        for (i in coach.indices) {

                            img.add(coach[i])
                        }

                        coachPositionViewHolder.binding.rv.layoutManager =
                            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                        coachPositionViewHolder.binding.rv.adapter = TrainCoachRecoAdapter(context, img)
                    }
                }

            }


        }
    }

    override fun getItemCount(): Int {

        return 4
    }
}