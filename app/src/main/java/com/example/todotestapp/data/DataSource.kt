package com.example.todotestapp.data

import com.example.todotestapp.R
import com.example.todotestapp.model.TaskData

class DataSource() {

    fun loadTasks(): List<TaskData>{
        return listOf<TaskData>(
                TaskData(R.string.title1, R.string.description1),
                TaskData(R.string.title2, R.string.description2),
                TaskData(R.string.title3, R.string.description3),
                TaskData(R.string.title4, R.string.description4),
                TaskData(R.string.title5, R.string.description5),
                TaskData(R.string.title6, R.string.description6),
                TaskData(R.string.title7, R.string.description7),
                TaskData(R.string.title8, R.string.description8),
                TaskData(R.string.title9, R.string.description9)
        )
    }
}