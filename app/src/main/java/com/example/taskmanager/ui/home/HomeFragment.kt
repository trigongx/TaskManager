package com.example.taskmanager.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.taskmanager.App
import com.example.taskmanager.R

import com.example.taskmanager.databinding.FragmentHomeBinding
import com.example.taskmanager.model.Task
import com.example.taskmanager.ui.task.TaskFragment.Companion.RESULT_KEY
import com.example.taskmanager.ui.task.TaskFragment.Companion.RESULT_REQUEST_KEY
import com.example.taskmanager.ui.task.adapter.TaskAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val adapter = TaskAdapter(this::onLongClickItem)
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvTask.layoutManager = GridLayoutManager(requireContext(),2)
        binding.rvTask.adapter = adapter

        val data = App.db.taskDao().getAll()
        adapter.addTasks(data)

        binding.fab.setOnClickListener {
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
            .setPositiveButton("Yes") {
                dialog,show ->
                App.db.taskDao().delete(task)
                val allTasks =  App.db.taskDao().getAll()
                adapter.addTasks(allTasks)
            }
            .setNegativeButton(getString(R.string.no)){
                dialog,show ->
            }.show()


    }
}