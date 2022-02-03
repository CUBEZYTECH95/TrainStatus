package com.example.trainlivestatus.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.trainlivestatus.R
import com.example.trainlivestatus.databinding.FindStationItemBinding
import com.example.trainlivestatus.model.FindStationModel
import java.util.*

class FindStationAdapter(
    var context: Activity,
    var arrayList: List<FindStationModel>,
    var contactListFiltered: List<FindStationModel>? = null
) : RecyclerView.Adapter<FindStationAdapter.Holder>(), Filterable {


    init {

        this.contactListFiltered = arrayList
    }

    class Holder(itemview: FindStationItemBinding) : RecyclerView.ViewHolder(itemview.root) {

        val itemLayoutBinding: FindStationItemBinding = itemview
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

        val binding: FindStationItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.find_station_item,
            parent,
            false
        )
        return Holder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {

        var cityname = arrayList[position].ecityname
        cityname = cityname!!.replace("\"", "")
        var citycode = arrayList[position].citycode
        citycode = citycode!!.replace("\"", "")


        holder.itemLayoutBinding.cityname.text = cityname + "\u0020" + citycode

        var citylocal = arrayList[position].citylocale
        citylocal = citylocal!!.replace("\"", "")
        holder.itemLayoutBinding.citylocal.text = citylocal
        holder.itemView.setOnClickListener {
            val intent = Intent()
            intent.putExtra("citycode", arrayList[position].citycode)
            intent.putExtra("cityname", arrayList[position].ecityname)
            context.setResult(Activity.RESULT_OK, intent)
            context.finish()
        }



    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun getFilter(): Filter {

        return customFilter

    }

    private val customFilter = object : Filter() {

        override fun performFiltering(p0: CharSequence?): FilterResults {

            val results = FilterResults()

            if (p0 != null && p0.isNotEmpty()) {

                val filterList: MutableList<FindStationModel> = ArrayList()

                for (i in contactListFiltered?.indices!!) {

                    if (contactListFiltered!![i].ecityname!!.lowercase(Locale.getDefault())
                            .contains(p0.toString().lowercase(Locale.getDefault()))
                    ) {
                        filterList.add(contactListFiltered!![i])
                    }
                }

                results.count = filterList.size
                results.values = filterList

            } else {

                results.count = contactListFiltered?.size!!

                results.values = contactListFiltered
            }

            return results
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun publishResults(p0: CharSequence?, results: FilterResults?) {

            arrayList = results?.values as List<FindStationModel>
            notifyDataSetChanged()
        }


    }

}