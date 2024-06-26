package com.e_book.StickyNotes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.e_book.R
import com.e_book.RoomDatabase.StickyNote

class StickyNoteAdapter(
    private val editNote: (StickyNote) -> Unit,
    private val deleteNote: (StickyNote) -> Unit
) : RecyclerView.Adapter<StickyNoteAdapter.StickyNoteViewHolder>() {

    private var notes = emptyList<StickyNote>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StickyNoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_sticky_note, parent, false)
        return StickyNoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: StickyNoteViewHolder, position: Int) {
        val currentNote = notes[position]
        holder.bind(currentNote)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    fun setNotes(notes: List<StickyNote>) {
        this.notes = notes
        notifyDataSetChanged()
    }

    inner class StickyNoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.titleView)
        private val content: TextView = itemView.findViewById(R.id.contentView)
        private val editButton: ImageButton = itemView.findViewById(R.id.editbtn)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.deletebtn)

        fun bind(note: StickyNote) {
            title.text = note.title
            content.text = note.content
            editButton.setOnClickListener {
                editNote.invoke(note)
            }
            deleteButton.setOnClickListener {
                showDeleteConfirmationDialog(note)
            }
        }

        private fun showDeleteConfirmationDialog(note: StickyNote) {
            AlertDialog.Builder(itemView.context)
                .setTitle("Delete Sticky Note")
                .setMessage("Are you sure you want to delete this sticky note?")
                .setPositiveButton("Yes") { dialog, _ ->
                    deleteNote.invoke(note)
                    dialog.dismiss()
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
                .show()
        }
    }
}
