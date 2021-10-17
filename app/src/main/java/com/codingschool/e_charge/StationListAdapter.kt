package com.codingschool.e_charge

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codingschool.e_charge.data.room.Station
import com.codingschool.e_charge.data.room.StationAndPlugs
import com.codingschool.e_charge.data.room.Type
import com.codingschool.e_charge.databinding.StationItemBinding

class StationListAdapter : RecyclerView.Adapter<StationListAdapter.StationViewHolder>() {

    private var list: List<StationAndPlugs> = emptyList()
    private var plugTypeList: List<Type> = emptyList()

    class StationViewHolder(private val binding: StationItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setBinding(
            station: Station,
            availablePlugs: String
        ) {
            binding.tvAddress.text = station.toString()
            binding.tvPlugsDetails.text = availablePlugs
        }
    }

    fun setData(list: List<StationAndPlugs>, plugTypeList: List<Type>) {
        this.list = list
        this.plugTypeList = plugTypeList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StationViewHolder {
        val binding = StationItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return StationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StationViewHolder, position: Int) {
        val station = list[position].station
        val plugList = list[position].plugs

        var availablePlugs = ""
        for (t in plugTypeList) {
            val count = plugList.count { it.type_id == t.type_id }
            if (count > 0) availablePlugs += t.name + ": " + count + "   "
        }
        holder.setBinding(station, availablePlugs)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}