package com.example.taskmanager.ui.book

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.taskmanager.R
import com.example.taskmanager.databinding.FragmentAddBookBinding
import com.example.taskmanager.model.Book
import com.example.taskmanager.utils.showToast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AddBookFragment : Fragment() {

    private lateinit var binding: FragmentAddBookBinding

    private val db: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    private val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBookBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSave.setOnClickListener {
            val data = Book(
                name_book = binding.etBook.text.toString(),
                name_author = binding.etAuthor.text.toString(),
            )

            db.collection(auth.currentUser?.uid.toString())
                .add(data)
                .addOnSuccessListener {
                    findNavController().navigate(R.id.navigation_books)
                }.addOnFailureListener {
                    showToast(it.message.toString())
                }
        }
    }


}