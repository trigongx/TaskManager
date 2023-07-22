package com.example.taskmanager.ui.onboarding.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.taskmanager.databinding.ItemOnboardingBinding
import com.example.taskmanager.model.OnBoarding

class OnBoardingAdapter(private val onClick:() -> Unit): Adapter<OnBoardingAdapter.OnBoardingViewHolder>() {

    private val data = arrayListOf<OnBoarding>(
        OnBoarding("Много возможностей","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.","https://images.ctfassets.net/spoqsaf9291f/2HM5BPlsww5fF5gJDpI71B/94b7676aa48c86aa4d3fe90583812b4a/New_to_Notion_-_Chapter_Hero.png"),
        OnBoarding("Всегда рядом","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.","https://cdn.wallpapersafari.com/37/54/u6EoIC.png"),
        OnBoarding("Удобное пользование","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.","https://logowik.com/content/uploads/images/notion1411.jpg")
    )


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingViewHolder {
        return OnBoardingViewHolder(ItemOnboardingBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: OnBoardingViewHolder, position: Int) {
        holder.bind(data.get(position))
    }
    inner class OnBoardingViewHolder(private val binding:ItemOnboardingBinding):
        ViewHolder(binding.root){

        fun bind(onBoarding: OnBoarding) {
            binding.tvTitle.text = onBoarding.title
            binding.tvDesc.text = onBoarding.desc
            Glide.with(binding.ivBoard).load(onBoarding.image).into(binding.ivBoard)
            binding.btnStart.isVisible = adapterPosition == data.lastIndex
            binding.tvSkip.isVisible = adapterPosition != data.lastIndex
            binding.btnStart.setOnClickListener {
                onClick()
            }
            binding.tvSkip.setOnClickListener {
                onClick()
            }
        }

    }
}