package com.bcebhagalpur.vvm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(),NoteClickDeleteInterface,NoteClickInterface {

    private lateinit var recyclerNotes:RecyclerView
    private lateinit var fabNotes:FloatingActionButton
    private lateinit var viewModel: NoteViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerNotes=findViewById(R.id.recyclerNotes)
        fabNotes=findViewById(R.id.fabNotes)
        recyclerNotes.layoutManager=LinearLayoutManager(this)

        val noteRvAdapter=NoteRvAdapter(this,this,this)
        recyclerNotes.adapter=noteRvAdapter
        viewModel=ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)
        viewModel.allNotes.observe(this,{list->
            list?.let {
                noteRvAdapter.updateList(it)
            }

        })
        fabNotes.setOnClickListener {
            val intent=Intent(this@MainActivity,AddNoteActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    override fun onDeleteIconClick(note: Note) {
        viewModel.deleteNote(note)
        Toast.makeText(this,"${note.noteTitle} Deleted" ,Toast.LENGTH_SHORT).show()
    }

    override fun onNoteClick(note: Note) {
        val intent=Intent(this@MainActivity,AddNoteActivity::class.java)
        intent.putExtra("noteType","Edit")
        intent.putExtra("noteTitle",note.noteTitle)
        intent.putExtra("noteDescription",note.noteDescription)
        intent.putExtra("noteId",note.id)
        startActivity(intent)
        this.finish()
    }
}