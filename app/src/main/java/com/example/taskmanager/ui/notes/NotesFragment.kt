package com.example.taskmanager.ui.notes

import android.app.ActionBar
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.taskmanager.App
import com.example.taskmanager.R

import com.example.taskmanager.databinding.FragmentNotesBinding
import com.example.taskmanager.model.Task
import com.example.taskmanager.ui.task.adapter.TaskAdapter

class NotesFragment : Fragment() {

    private var _binding: FragmentNotesBinding? = null

    private val adapter = TaskAdapter(this::onLongClickItem, this::onClick)
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvTask.adapter = adapter
        loadAllData()
        binding.btnCreateNote.setOnClickListener {
            findNavController().navigate(R.id.taskFragment)
        }
    }

    private fun onLongClickItem(task: Task) {
        showTaskDeleteDialog(task)
    }

    private fun showTaskDeleteDialog(task: Task) {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle(getString(R.string.delete_task))
            .setMessage(getString(R.string.are_you_sure_you_want_to_delete_this_task))
            .setCancelable(true)
            .setPositiveButton(getString(R.string.yes)) { dialog, show ->
                App.db.taskDao().delete(task)
                loadAllData()
            }
            .setNegativeButton(getString(R.string.no)) { dialog, show ->
            }.show()


    }

    private fun onClick(task: Task) {
        findNavController().navigate(R.id.taskFragment, bundleOf(TASK_KEY to task))
    }

    private fun loadAllData(){
        val data = App.db.taskDao().getAll()
        adapter.addTasks(data)
    }

    companion object{
        const val TASK_KEY = "task.key"
    }
}