package com.codingschool.e_charge

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codingschool.e_charge.data.room.User
import com.codingschool.e_charge.data.room.UserAndStations
import com.codingschool.e_charge.databinding.UserItemBinding

class UserListAdapter : RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {

    private var list: List<UserAndStations> = emptyList()
    private lateinit var clickListener: (Int) -> Unit
    private lateinit var longClickListener: (Int) -> Unit

    class UserViewHolder(private val binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setBinding(
            user: User,
            numberOfStations: Int,
            clickListener: (Int) -> Unit,
            longClickListener: (Int) -> Unit
        ) {
            binding.tvUserName.text = user.user_name
            binding.tvStationsAmount.text = numberOfStations.toString()
            binding.root.setOnClickListener {
                clickListener(user.user_id)
            }
            binding.root.setOnLongClickListener {
                longClickListener(user.user_id)
                true
            }
        }
    }

    fun setData(list: List<UserAndStations>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserViewHolder {
        val binding = UserItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = list[position].user
        val numberOfStations = list[position].stations.size
        holder.setBinding(user, numberOfStations, clickListener, longClickListener)
    }

    fun addClickListener(listener: (Int) -> Unit) {
        this.clickListener = listener
    }

    fun addLongClickListener(listener: (Int) -> Unit) {
        this.longClickListener = listener
    }

    override fun getItemCount(): Int {
        return list.size
    }
}