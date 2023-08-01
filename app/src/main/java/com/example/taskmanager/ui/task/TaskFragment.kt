package com.example.taskmanager.ui.task

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.example.taskmanager.App
import com.example.taskmanager.R
import com.example.taskmanager.databinding.FragmentTaskBinding
import com.example.taskmanager.model.Task
import com.example.taskmanager.ui.notes.NotesFragment.Companion.TASK_KEY

class TaskFragment : Fragment() {

    private lateinit var binding: FragmentTaskBinding
    private var task: Task? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        etTaskTitle.doOnTextChanged { text, _, _, _ ->
            if(text.isNullOrEmpty()){
                titleContainer.error = getString(R.string.this_field_must_be_filled)
            } else{
                titleContainer.error = null
            }
        }

        task = arguments?.getSerializable(TASK_KEY) as? Task
        if (task != null) {
            etTaskTitle.setText(task?.title.toString())
            etTaskDesc.setText(task?.desc.toString())
            btnSave.text = getString(R.string.update)
        }

        btnSave.setOnClickListener {

            val titleText = etTaskTitle.text?.toString()?.trim()
            if (titleText.isNullOrEmpty()) {
                titleContainer.error = getString(R.string.this_field_must_be_filled)
                return@setOnClickListener
            } else {
                titleContainer.error = null
            }

            if (task == null && etTaskTitle.text?.isNotEmpty() == true) {
                saveTask()
            } else if (etTaskTitle.text?.isNotEmpty() == true) {
                updateTask()
            }

            findNavController().navigateUp()
        }
    }


    private fun updateTask() {
        val data = task?.copy(
            title = binding.etTaskTitle.text.toString(),
            desc = binding.etTaskDesc.text.toString()
        )
        if (data != null && data.title?.isNotEmpty() == true) {
            App.db.taskDao().update(data)
        } else {

        }
    }

    fun saveTask() {
        val data = Task(
            title = binding.etTaskTitle.text.toString(),
            desc = binding.etTaskDesc.text.toString()
        )
        App.db.taskDao().insert(data)
    }
}