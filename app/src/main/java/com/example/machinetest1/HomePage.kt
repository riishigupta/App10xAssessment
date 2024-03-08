/*
package com.example.machinetest1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.machinetest1.databinding.HomepageBinding


class HomePage : AppCompatActivity() {
    private lateinit var mBinding: HomepageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(  this, R.layout.homepage)
        val userList = createDummyUserList()
        recyclerListViewAdapter(userList)
//        val imageUrl = "your_image_url"

        // Load the image into the ImageView using Glide
         Glide.with(this)
             .load("https://mmfinfotech.co/machine_test/storage/app/public/profileImg/default.png")
             .placeholder(R.drawable.ic_launcher_foreground) // Optional placeholder image while loading
             .error(R.drawable.ic_launcher_foreground) // Optional e|rror image if loading fails
             .into(mBinding.ivprofileimg)


        */
/*   Glide.with(this).load("https://mmfinfotech.co/machine_test/storage/app/public/profileImg/default.png").apply(options).into(imageView)*//*


        mBinding.ivGrid.setOnClickListener {
            recyclerGridViewAdapter(userList)
            mBinding.ivGrid.setImageResource(R.drawable.ic_grid_blue)
            mBinding.ivlist.setImageResource(R.drawable.ic_list)
        }
        mBinding.ivlist.setOnClickListener {
            mBinding.ivGrid.setImageResource(R.drawable.ic_grid)
            mBinding.ivlist.setImageResource(R.drawable.ic_list_blue)
            recyclerListViewAdapter(userList)
        }


    }

    private fun recyclerGridViewAdapter(userList: List<User>) {
        val layoutManager = LinearLayoutManager(this)
        mBinding.recyclerView.layoutManager = layoutManager

        val userAdapter = UserGridListAdapter(userList)
        mBinding.recyclerView.adapter = userAdapter
    }


    private fun recyclerListViewAdapter(userList: List<User>) {
        val layoutManager = GridLayoutManager(this, 2)
        mBinding.recyclerView.layoutManager = layoutManager

        val userAdapter = UserListAdapter(userList)
        mBinding.recyclerView.adapter = userAdapter
    }
}

private fun createDummyUserList(): List<User> {
    // Create and return a list of 20 dummy users
    val dummyUserList = mutableListOf<User>()

    for (i in 1..20) {
        dummyUserList.add(
            User(
                userId = i.toString(),
                firstName = "First$i",
                lastName = "Last$i",
                email = "user$i@example.com",
                phone = "123-456-789$i"
            )
        )
    }

    return dummyUserList
}

data class User(
    val userId: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String
    // Add other user properties as needed
)*/
