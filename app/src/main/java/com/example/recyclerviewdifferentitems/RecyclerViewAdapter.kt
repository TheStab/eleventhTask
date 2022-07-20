package com.example.recyclerviewdifferentitems

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.service.autofill.UserData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter(val c: Context, val userList:ArrayList<UserData>):RecyclerView.Adapter<RecyclerViewAdapter.UserViewHolder>(DiffUtil())
{

    companion object {
        private const val FIRST = 1
        private const val DEFAULT = "2"
    }

    private lateinit var switch: (Int) -> Unit

    inner class UserViewHolder(v: View):RecyclerView.ViewHolder(v){
        var firstName: TextView = v.findViewById(R.id.firstNameTxt)
        var lastName: TextView = v.findViewById(R.id.lastNameTxt)
        var email: TextView = v.findViewById(R.id.emailTxt)
        private var mMenus: ImageView = v.findViewById(R.id.mMenus)

        init {
            mMenus.setOnClickListener { popupMenus(it) }
        }


        @SuppressLint("NotifyDataSetChanged", "DiscouragedPrivateApi")
        private fun popupMenus(v: View) {
            val position = userList[adapterPosition]
            val popupMenus = PopupMenu(c,v)
            popupMenus.inflate(R.menu.show_menu)
            popupMenus.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.editText->{
                        val view = LayoutInflater.from(c).inflate(R.layout.activity_user,null)
                        val firstName = view.findViewById<EditText>(R.id.userFirstName)
                        val lastName = view.findViewById<EditText>(R.id.userLastName)
                        val email = view.findViewById<EditText>(R.id.userEmail)
                        AlertDialog.Builder(c)
                            .setView(view)
                            .setPositiveButton("Ok"){
                                    dialog,_->
                                position.userFirstName = "First name: " + firstName.text.toString()
                                position.userLastName = "Last name: " + lastName.text.toString()
                                position.userEmail = "Email: " + email.text.toString()
                                notifyDataSetChanged()
                                Toast.makeText(c,"User Information is Edited", Toast.LENGTH_SHORT).show()
                                dialog.dismiss()

                            }
                            .setNegativeButton("Cancel"){
                                    dialog,_->
                                dialog.dismiss()

                            }
                            .create()
                            .show()

                        true
                    }
                    R.id.delete->{
                        AlertDialog.Builder(c)
                            .setTitle("Delete")
                            .setIcon(R.drawable.ic_warning)
                            .setMessage("Are you sure delete this Information")
                            .setPositiveButton("Yes"){
                                    dialog,_->
                                userList.removeAt(adapterPosition)
                                notifyDataSetChanged()
                                Toast.makeText(c,"Deleted this Information", Toast.LENGTH_SHORT).show()
                                dialog.dismiss()
                            }
                            .setNegativeButton("No"){
                                    dialog,_->
                                dialog.dismiss()
                            }
                            .create()
                            .show()

                        true
                    }
                    else-> true
                }

            }
            popupMenus.show()
            val popup = PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true
            val menu = popup.get(popupMenus)
            menu.javaClass.getDeclaredMethod("setForceShowIcon",Boolean::class.java)
                .invoke(menu,true)
        }
    }

    inner class Item1ViewHolder(binding: listItemFirstLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val name = binding.name

        init {
            if (swith.isInitialized)
                switch = {}
            binding.delete.setOnClickListener { switch(adapterPosition) }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder  {
        if (viewType == FIRST) Item1ViewHolder(
            listItemFirstLayoutBinding.inflate(LayoutInflater.from(parent.context))
        ) else listItemSecondViewHolder(
            listItemSecondLayoutBinding.inflate(LayoutInflater.from(parent.context))
        )
        val inflater = LayoutInflater.from(parent.context)
        val v  = inflater.inflate(R.layout.list_item_first,parent,false)
        return UserViewHolder(v)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val newList = userList[position]
        holder.firstName.text = newList.userFirstName
        holder.lastName.text = newList.userLastName
        holder.email.text = newList.userEmail
    }

    override fun getItemCount(): Int {
        return  userList.size
    }
}