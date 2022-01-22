package com.bcebhagalpur.vvm

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class NoteRvAdapter(val context: Context, val noteClickInterface: NoteClickInterface,
                    val noteClickDeleteInterface:NoteClickDeleteInterface ):
    RecyclerView.Adapter<NoteRvAdapter.ViewHolder>() {


    private val allNotes=ArrayList<Note>()

        inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
            val cardItem=itemView.findViewById<CardView>(R.id.cardItem)
            val txtTimeSamp=itemView.findViewById<TextView>(R.id.txtTimeStamp)
            val txtTitle=itemView.findViewById<TextView>(R.id.txtTitle)
            val imgDelete=itemView.findViewById<ImageView>(R.id.imgDelete)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.note_rv_item,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtTitle.setText(allNotes.get(position).noteTitle)
        holder.txtTimeSamp.setText("Last updated : "+allNotes.get(position).timeStamp)
        holder.imgDelete.setOnClickListener {
            noteClickDeleteInterface.onDeleteIconClick(allNotes.get(position))
        }
        holder.itemView.setOnClickListener {
            noteClickInterface.onNoteClick(allNotes.get(position))
        }
    }

    override fun getItemCount(): Int {
        return allNotes.size
    }

    fun updateList(newList: List<Note>){
        allNotes.clear()
        allNotes.addAll(newList)
        notifyDataSetChanged()
    }

}

interface  NoteClickDeleteInterface{
    fun onDeleteIconClick(note:Note)
}
interface NoteClickInterface{
    fun onNoteClick(note: Note)
}