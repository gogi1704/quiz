package com.quizApp.ui.adapters.levelAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.quizApp.R
import com.quizApp.data.level.LevelData
import com.quizApp.data.level.StatusState
import com.quizApp.databinding.QuizItemLayoutBinding

interface LevelClickListener {
    fun click(levelId: Int , countCompleted:Int)
}

class LevelAdapter(private val listener: LevelClickListener) :
    ListAdapter<LevelData, LevelAdapter.LevelViewHolder>(LevelAdapterCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LevelViewHolder {
        val binding =
            QuizItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return LevelViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: LevelViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class LevelViewHolder(
        private val binding: QuizItemLayoutBinding,
        private val listener: LevelClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: LevelData) {
            with(binding) {
                textLevelName.text = item.levelName
                textCounterCompleted.text = item.countCompleted.toString()
                when (item.statusState) {
                    StatusState.Complete -> imageStatusState.setImageResource(R.drawable.ic_complete)
                    StatusState.Lock -> imageStatusState.setImageResource(R.drawable.ic_lock)
                    StatusState.Started -> imageStatusState.setImageResource(R.drawable.ic_process)
                }
                main.setOnClickListener {
                    when(item.statusState){
                        StatusState.Complete -> {
                        }
                        StatusState.Lock -> {

                        }
                        StatusState.Started -> {
                            listener.click(item.levelModelId ,item.countCompleted)
                        }
                    }

                }
            }
        }

    }
}