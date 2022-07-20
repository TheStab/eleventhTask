package com.example.recyclerviewdifferentitems

import android.service.autofill.UserData
import androidx.recyclerview.widget.DiffUtil

class DiffUtil(): DiffUtil.ItemCallback<UserData>() {
    override fun areItemsTheSame(old: UserData, new: UserData) = old.userEmail == new.userEmail
    override fun areContentsTheSame(old: UserData, new: UserData) = old == new
}