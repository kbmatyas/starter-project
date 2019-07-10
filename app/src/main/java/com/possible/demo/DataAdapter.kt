package com.possible.demo

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import androidx.databinding.DataBindingUtil

import java.util.ArrayList

import com.possible.demo.databinding.MainRowBinding

class DataAdapter(private val dataList: ArrayList<Follower>) : RecyclerView.Adapter<DataAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.main_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        //for now just gets all json info for each follower of a user and sets that in the viewHolder
        Log.v("position:", position.toString())

        /***Data Binding (for Follower Name and Follower Image)***/
        viewHolder.bind(dataList[position])

        //(pre databinding) get follower name
        //viewHolder.followerName.text = dataList[position].login

        //get follower avatar image
//        Picasso.with(viewHolder.followerImage.context)
//                .load(dataList[position].avatarUrl)
//                .resize(100, 100)
//                .into(viewHolder.followerImage)


        //attempt before Picasso
        //DownloadImage(viewHolder.followerImage).execute(dataList[position].avatarUrl)

        //viewHolder.followerImage.setImageURI(Uri.parse(dataList[position].avatarUrl))
    }

    override fun getItemCount(): Int {
        Log.v("dataListSize:", dataList.size.toString())
        return dataList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var binding: MainRowBinding? = null

        //pre databinding method
        //val followerName: TextView = view.findViewById<View>(R.id.follower_text) as TextView
        //val followerImage: ImageView = view.findViewById<View>(R.id.follower_image) as ImageView

        fun bind(follower: Follower) {
            binding?.follower = follower
        }

        init {
            binding = DataBindingUtil.bind(view)
        }
    }
}

//to handle follower image binding
@BindingAdapter("imageUrl")
fun bindFollowerImage(imageView: ImageView, url: String?) {
    Picasso.with(imageView.context)
            .load(url)
            .resize(100, 100)
            .into(imageView)
}
