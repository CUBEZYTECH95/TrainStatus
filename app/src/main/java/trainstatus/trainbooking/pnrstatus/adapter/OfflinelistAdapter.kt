package trainstatus.trainbooking.pnrstatus.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import trainstatus.trainbooking.pnrstatus.R
import trainstatus.trainbooking.pnrstatus.clicklistner.OfflineSeatClick
import trainstatus.trainbooking.pnrstatus.model.PopularListModel

class OfflinelistAdapter(private val populartarinlist: ArrayList<PopularListModel>, val offlineSeatClick: OfflineSeatClick) :RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.train_autocomp_item, parent, false)

        return MyViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val holder : MyViewHolder = holder as MyViewHolder

        holder.tvTrainNo.text = populartarinlist[position].number.toString()

        holder.tv_train_name.text = populartarinlist[position].trainname

        holder.tv_source.text = "("+populartarinlist[position].origin+")"+" "+populartarinlist[position].originName

        holder.tv_destination.text = "("+populartarinlist[position].destination+")"+" "+populartarinlist[position].destinationName

        holder.itemView.setOnClickListener {

            offlineSeatClick.click(populartarinlist[position])
        }

    }

    override fun getItemCount(): Int {

      return  populartarinlist.size

    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tvTrainNo: TextView = view.findViewById(R.id.tv_train_no)
        val tv_train_name: TextView = view.findViewById(R.id.tv_train_name)
        val tv_source: TextView = view.findViewById(R.id.tv_source)
        val tv_destination: TextView = view.findViewById(R.id.tv_destination)
    }
}