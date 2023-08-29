package com.example.taskmanager.ui.books

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.taskmanager.R
import com.example.taskmanager.databinding.FragmentBooksBinding
import com.example.taskmanager.model.Book
import com.example.taskmanager.ui.book.adapter.BookAdapter
import com.example.taskmanager.utils.showToast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class BooksFragment : Fragment() {

    private var _binding: FragmentBooksBinding? = null

    private val binding get() = _binding!!

    private val db: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    private val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val adapter = BookAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentBooksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvBooks.adapter = adapter
        db.collection(auth.currentUser?.uid.toString())
            .get().addOnSuccessListener {

                val data = it.toObjects(Book::class.java)
                adapter.addBook(data)

            }.addOnFailureListener {
                showToast(it.message.toString())
            }
        binding.btnCreateBook.setOnClickListener {
            findNavController().navigate(R.id.addBookFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}