/*

package com.example.machinetest1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView



class UserGridListAdapter(private val userList: List<User>) :
    RecyclerView.Adapter<UserGridListAdapter.UserViewHolder>() {



    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userEmail: TextView = itemView.findViewById(R.id.tvEmail)
        val userPhone: TextView = itemView.findViewById(R.id.tvPhone)
        val userFirstNameTextView: TextView = itemView.findViewById(R.id.tvFirstName)
        val userLastNameTextView: TextView = itemView.findViewById(R.id.tvLastName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.rv_grid_view, parent, false)
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = userList[position]

        holder.userEmail.text = currentUser.email
        holder.userFirstNameTextView.text = currentUser.firstName
        holder.userLastNameTextView.text = currentUser.lastName
        holder.userPhone.text = currentUser.phone

    }

    override fun getItemCount(): Int {
        return userList.size
    }
}
*/
