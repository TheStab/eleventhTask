package com.example.recyclerviewdifferentitems

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.autofill.UserData
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var addsBtn: FloatingActionButton
    private lateinit var recV: RecyclerView
    private lateinit var userList: ArrayList<UserData>
    private lateinit var userAdapter: RecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userList = ArrayList()
        addsBtn = findViewById(R.id.addingBtn)
        recV = findViewById(R.id.mRecycler)
        userAdapter = RecyclerViewAdapter(this, userList)
        recV.layoutManager = LinearLayoutManager(this)
        recV.adapter = userAdapter
        addsBtn.setOnClickListener { addInfo() }

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun addInfo() {
        val inflater = LayoutInflater.from(this)
        val v = inflater.inflate(R.layout.activity_user, null)
        val userFirstName = v.findViewById<EditText>(R.id.userFirstName)
        val userLastName = v.findViewById<EditText>(R.id.userLastName)
        val userEmail = v.findViewById<EditText>(R.id.userEmail)

        val addDialog = AlertDialog.Builder(this)

        addDialog.setView(v)
        addDialog.setPositiveButton("Ok") { dialog, _ ->
            val firstName = userFirstName.text.toString()
            val lastName = userLastName.text.toString()
            val email = userEmail.text.toString()
            userList.add(
                com.example.recyclerviewdifferentitems.model.UserData(
                    "First name: $firstName",
                    "Last name: $lastName",
                    "Email: $email"
                )
            )
            userAdapter.notifyDataSetChanged()
            Toast.makeText(this, "Adding User Information Success", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        addDialog.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
            Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show()

        }
        addDialog.create()
        addDialog.show()
    }
}

