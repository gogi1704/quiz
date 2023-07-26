package com.quizApp.ui.adapters.levelAdapter

import androidx.recyclerview.widget.DiffUtil
import com.quizApp.data.level.LevelData

class LevelAdapterCallback : DiffUtil.ItemCallback<LevelData>() {
    override fun areItemsTheSame(oldItem: LevelData, newItem: LevelData): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: LevelData, newItem: LevelData): Boolean =
        oldItem.levelModelId == newItem.levelModelId
}