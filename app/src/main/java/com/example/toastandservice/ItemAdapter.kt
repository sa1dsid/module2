package com.example.toastandservice
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter() : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    val contactsList = mutableListOf<String>()

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val contactsTextView = itemView.findViewById<TextView>(R.id.ContactsTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.contactsTextView.text = contactsList[position]
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItems(newItems: List<String>) {
        contactsList.clear()
        contactsList.addAll(newItems)
        notifyDataSetChanged()    }

    override fun getItemCount(): Int = contactsList.size
}