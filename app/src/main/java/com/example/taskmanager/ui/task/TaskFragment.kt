package com.example.taskmanager.ui.task

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.taskmanager.App
import com.example.taskmanager.R
import com.example.taskmanager.databinding.FragmentTaskBinding
import com.example.taskmanager.model.Task
import com.example.taskmanager.ui.home.HomeFragment.Companion.TASK_KEY

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
        task = arguments?.getSerializable(TASK_KEY) as? Task
        if (task != null) {
            etTaskTitle.setText(task?.title.toString())
            etTaskDesc.setText(task?.desc.toString())
            btnSave.text = getString(R.string.update)
        } else {
            etTaskTitle.setText("")
            etTaskDesc.setText("")
            btnSave.text = getString(R.string.save)
        }

        btnSave.setOnClickListener {
            if (task == null) {
                saveTask()
            } else {
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
        if (data != null) {
            App.db.taskDao().update(data)
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