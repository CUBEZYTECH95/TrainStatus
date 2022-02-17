package com.example.trainlivestatus.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trainlivestatus.R
import com.example.trainlivestatus.databinding.RouteDetailsListItemBinding
import com.example.trainlivestatus.livestatus.IntermediatestnActivity
import com.example.trainlivestatus.livestatus.LiveTrainActivity
import com.example.trainlivestatus.livestatus.TrainTimeActivity
import com.example.trainlivestatus.model.TrainBtwnStnsListItem

class RouteDetailsListAdapter(
    val context: Context,
    private val filteredList: List<TrainBtwnStnsListItem>,
    val date: String?
) : RecyclerView.Adapter<RouteDetailsListAdapter.TrainDetailsViewHolder>() {


    companion object {

        var avalClass = ArrayList<String>()
    }

    class TrainDetailsViewHolder(itemView: RouteDetailsListItemBinding) :
        RecyclerView.ViewHolder(itemView.root) {

        val binding: RouteDetailsListItemBinding = itemView

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrainDetailsViewHolder {

        return TrainDetailsViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.route_details_list_item, parent, false
            )
        )

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TrainDetailsViewHolder, position: Int) {

        val listItem: TrainBtwnStnsListItem = filteredList[position]

        if (filteredList[position].runningSun.equals("Y")) {

            holder.binding.cbsun.background =
                ContextCompat.getDrawable(context, R.drawable.custom_checkbox_full)
            holder.binding.cbsun.setTextColor(context.resources.getColor(R.color.white))
        }

        if (filteredList[position].runningMon.equals("Y")) {

            holder.binding.cbmon.background =
                ContextCompat.getDrawable(context, R.drawable.custom_checkbox_full)
            holder.binding.cbmon.setTextColor(context.resources.getColor(R.color.white))
        }

        if (filteredList[position].runningTue.equals("Y")) {

            holder.binding.cbtue.background =
                ContextCompat.getDrawable(context, R.drawable.custom_checkbox_full)
            holder.binding.cbtue.setTextColor(context.resources.getColor(R.color.white))
        }

        if (filteredList[position].runningWed.equals("Y")) {

            holder.binding.cbwed.background =
                ContextCompat.getDrawable(context, R.drawable.custom_checkbox_full)
            holder.binding.cbwed.setTextColor(context.resources.getColor(R.color.white))
        }

        if (filteredList[position].runningThu.equals("Y")) {

            holder.binding.cbthu.background =
                ContextCompat.getDrawable(context, R.drawable.custom_checkbox_full)
            holder.binding.cbthu.setTextColor(context.resources.getColor(R.color.white))
        }

        if (filteredList[position].runningFri.equals("Y")) {

            holder.binding.cbfri.background =
                ContextCompat.getDrawable(context, R.drawable.custom_checkbox_full)
            holder.binding.cbfri.setTextColor(context.resources.getColor(R.color.white))
        }

        if (filteredList[position].runningSat.equals("Y")) {

            holder.binding.cbsat.background =
                ContextCompat.getDrawable(context, R.drawable.custom_checkbox_full)
            holder.binding.cbsat.setTextColor(context.resources.getColor(R.color.white))
        }

        avalClass.clear()
        avalClass = listItem.avlClasses?.array as ArrayList<String>

        for (i in avalClass.indices) {

            if (avalClass[i] == "1A") {

                holder.binding.ch1a.background =
                    ContextCompat.getDrawable(context, R.drawable.custom_checkbox_full)
                holder.binding.ch1a.setTextColor(context.resources.getColor(R.color.white))
            }

            if (avalClass[i] == "2A") {

                holder.binding.ch2a.background =
                    ContextCompat.getDrawable(context, R.drawable.custom_checkbox_full)
                holder.binding.ch2a.setTextColor(context.resources.getColor(R.color.white))
            }

            if (avalClass[i] == "3A") {

                holder.binding.ch3a.background =
                    ContextCompat.getDrawable(context, R.drawable.custom_checkbox_full)
                holder.binding.ch3a.setTextColor(context.resources.getColor(R.color.white))
            }

            if (avalClass[i] == "CC") {

                holder.binding.chCc.background =
                    ContextCompat.getDrawable(context, R.drawable.custom_checkbox_full)
                holder.binding.chCc.setTextColor(context.resources.getColor(R.color.white))
            }

            if (avalClass[i] == "EA") {

                holder.binding.chEa.background =
                    ContextCompat.getDrawable(context, R.drawable.custom_checkbox_full)
                holder.binding.chEa.setTextColor(context.resources.getColor(R.color.white))
            }

            if (avalClass[i] == "EC") {

                holder.binding.chEc.background =
                    ContextCompat.getDrawable(context, R.drawable.custom_checkbox_full)
                holder.binding.chEc.setTextColor(context.resources.getColor(R.color.white))
            }

            if (avalClass[i] == "2S") {

                holder.binding.ch2s.background =
                    ContextCompat.getDrawable(context, R.drawable.custom_checkbox_full)
                holder.binding.ch2s.setTextColor(context.resources.getColor(R.color.white))
            }

            if (avalClass[i] == "SL") {

                holder.binding.chSl.background =
                    ContextCompat.getDrawable(context, R.drawable.custom_checkbox_full)
                holder.binding.chSl.setTextColor(context.resources.getColor(R.color.white))
            }
        }

        holder.binding.farerv.layoutManager = GridLayoutManager(context, 2)
        holder.binding.farerv.adapter =
            FareAdapter(context, filteredList[position].avaiblitycache, avalClass)

        holder.binding.tvsource.text = listItem.fromStnCode
        holder.binding.tvdestinations.text = listItem.toStnCode
        holder.binding.tvdep.text = listItem.departureTime
        holder.binding.tvarrive.text = listItem.arrivalTime
        holder.binding.tvtrainname.text = listItem.trainName.toString() + "-" + listItem.trainNumber
        holder.binding.tvdistance.text = listItem.distance.toString() + " KM"
        holder.binding.tvDuration.text = listItem.duration.toString() + " HRS"

        val time: List<String> = listItem.duration?.split(":")!!

        val hrs = time[0].toInt()
        val minute = time[1].toInt()
        val totalMinute = hrs * 60 + minute
        val i = listItem.distance!! * 60 / totalMinute.toFloat()
        holder.binding.tvSpeed.text = String.format("%.2f", i) + " Km/hr"

        holder.binding.btnlive.setOnClickListener {

            val intent = Intent(context, LiveTrainActivity::class.java)
            intent.putExtra("trainNo", listItem.trainNumber)
            intent.putExtra("trainName", listItem.trainName)
            context.startActivity(intent)

        }


        holder.binding.btnSeatAvab.setOnClickListener {

            val intent = Intent(context, TrainTimeActivity::class.java)
            intent.putExtra("type", 1)
            intent.putExtra("trainNo", listItem.trainNumber)
            intent.putExtra("from", listItem.fromStnCode)
            intent.putExtra("to", listItem.toStnCode)
            intent.putExtra("date", date)
            intent.putExtra("trainName", listItem.trainName)
            intent.putExtra("mon", listItem.runningMon)
            intent.putExtra("tue", listItem.runningTue)
            intent.putExtra("wed", listItem.runningWed)
            intent.putExtra("thu", listItem.runningThu)
            intent.putExtra("fri", listItem.runningFri)
            intent.putExtra("sat", listItem.runningSat)
            intent.putExtra("sun", listItem.runningSun)
            context.startActivity(intent)
        }

        holder.binding.btnRouteSchedule.setOnClickListener {
            val intent = Intent(context, IntermediatestnActivity::class.java)
            intent.putExtra("trainNo", listItem.trainNumber)
            context.startActivity(intent)
        }


    }

    override fun getItemCount(): Int {

        return filteredList.size
    }


}