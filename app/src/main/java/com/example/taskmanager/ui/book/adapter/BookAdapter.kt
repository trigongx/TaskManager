package com.example.taskmanager.ui.book.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanager.databinding.ItemTaskBinding
import com.example.taskmanager.model.Book

class BookAdapter(
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    private val list = arrayListOf<Book>()

    fun addBook(books: List<Book>) {
        list.clear()
        list.reverse()
        list.addAll(books)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        return BookViewHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class BookViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(book: Book) = with(binding) {
            tvTaskTitle.text = book.name_book
            tvTaskDesc.text = book.name_author

        }
    }
}