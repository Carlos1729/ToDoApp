package com.example.todotestapp.data.repository

object GlobalVariable {
    var INACTIVEFLAG  = false
    var ADMINOWNTASKS = false
    var ROLEOFUSER = ""
    val hashMapOrder: HashMap<Int?, String?> = HashMap<Int?, String?>()
    val hashMapStatus: HashMap<Int?, String?> = HashMap<Int?, String?>()
    val hashMapPriority: HashMap<Int?, String?> = HashMap<Int?, String?>()
    init
    {
        hashMapOrder[1] = "DESC"
        hashMapOrder[2] = "ASC"
        hashMapOrder[null] = null
        hashMapOrder[0] = null
        hashMapStatus[1] = "completed"
        hashMapStatus[2] = "pending"
        hashMapStatus[null] = null
        hashMapStatus[0] = null
        hashMapPriority[1] = "high"
        hashMapPriority[2] = "medium"
        hashMapPriority[3] = "low"
        hashMapPriority[null] = null
        hashMapPriority[0] = null
    }

}