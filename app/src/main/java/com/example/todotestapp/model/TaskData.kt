package com.example.todotestapp.model

import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TaskData (
    @StringRes val stringTitleId : Int,
    @StringRes val stringDescriptionId: Int
):Parcelable
{

}