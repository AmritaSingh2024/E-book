package com.e_book.StickyNotes

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.e_book.BaseActivity
import com.e_book.R
import com.e_book.RoomDatabase.StickyNote
import com.e_book.viewmodel.StickyNoteViewModel
import com.e_book.viewmodel.StickyNoteViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class StickyNotesActivity : BaseActivity() {

    private lateinit var viewModel: StickyNoteViewModel
    private lateinit var adapter: StickyNoteAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sticky_notes)

        recyclerView = findViewById(R.id.recyclerView)
        fab = findViewById(R.id.fab)

        val application = requireNotNull(this).application
        val factory = StickyNoteViewModelFactory(application)
        viewModel = ViewModelProvider(this, factory).get(StickyNoteViewModel::class.java)

        setupRecyclerView()

        fab.setOnClickListener {
            showAddNoteDialog()
        }
    }

    private fun setupRecyclerView() {
        adapter = StickyNoteAdapter(
            editNote = { note -> showEditNoteDialog(note) },
            deleteNote = { note -> viewModel.delete(note) }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.fab_margin)
        recyclerView.addItemDecoration(SpacesItemDecoration(spacingInPixels))

        viewModel.allNotes.observe(this, { stickyNotes ->
            stickyNotes?.let { adapter.setNotes(it) }
        })

    }

    private fun showAddNoteDialog() {
        val builder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val dialogLayout = inflater.inflate(R.layout.dialog_add_note, null)
        val editTextTitle = dialogLayout.findViewById<EditText>(R.id.titleEditText)
        val editTextContent = dialogLayout.findViewById<EditText>(R.id.contentEditText)

        builder.setView(dialogLayout)
            .setTitle("Add Sticky Note")
            .setPositiveButton("Save") { dialog, _ ->
                val title = editTextTitle.text.toString()
                val content = editTextContent.text.toString()
                if (title.isNotEmpty() && content.isNotEmpty()) {
                    val newNote = StickyNote(
                        title = title,
                        content = content
                    )
                    viewModel.insert(newNote)
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }

    private fun showEditNoteDialog(note: StickyNote) {
        val builder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val dialogLayout = inflater.inflate(R.layout.dialog_add_note, null)
        val editTextTitle = dialogLayout.findViewById<EditText>(R.id.titleEditText)
        val editTextContent = dialogLayout.findViewById<EditText>(R.id.contentEditText)

        editTextTitle.setText(note.title)
        editTextContent.setText(note.content)

        builder.setView(dialogLayout)
            .setTitle("Edit Sticky Note")
            .setPositiveButton("Update") { dialog, _ ->
                val title = editTextTitle.text.toString()
                val content = editTextContent.text.toString()
                if (title.isNotEmpty() && content.isNotEmpty()) {
                    val updatedNote = note.copy(
                        title = title,
                        content = content
                    )
                    viewModel.update(updatedNote)
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }
}
