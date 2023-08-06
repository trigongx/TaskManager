package com.example.taskmanager.ui.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.example.taskmanager.R
import com.example.taskmanager.data.local.Pref
import com.example.taskmanager.databinding.FragmentProfileBinding
import com.example.taskmanager.utils.loadImage
import com.google.firebase.auth.FirebaseAuth
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val pref: Pref by lazy {
        Pref(requireContext())
    }
    private val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.sign_out){
            auth.signOut()
            findNavController().navigate(R.id.authFragment)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etName.setText(pref.getName())

        binding.etName.addTextChangedListener {
            pref.saveName(binding.etName.text.toString())
        }

        if (pref.getImage()?.isNotEmpty() == true) {
            pref.getImage()?.let { binding.imgProfile.loadImage(it) }
        }

        binding.imgProfile.setOnClickListener {
            doCircleAvatar()
        }

    }


    private fun doCircleAvatar() {
        CropImage.activity()
            .setAspectRatio(1, 1)
            .setRequestedSize(1080, 1080)
            .setCropShape(CropImageView.CropShape.OVAL)
            .start(requireActivity(), this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val resultUri = CropImage.getActivityResult(data).uri
            pref.saveImage(resultUri.toString())
            pref.getImage()?.let { binding.imgProfile.loadImage(it) }
        }
    }


}
