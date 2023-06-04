package com.example.testformangofzco.presentation.user_profile

import android.app.DatePickerDialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Base64
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.testformangofzco.R
import com.example.testformangofzco.databinding.FragmentEditUserProfileBinding
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class EditUserProfileFragment : Fragment(R.layout.fragment_edit_user_profile) {

    private val binding by viewBinding(FragmentEditUserProfileBinding::bind)

    private val avatar by lazy { arguments?.getString(AVATAR_KEY) }
    private val city by lazy { arguments?.getString(CITY_KEY) }
    private val birthday by lazy { arguments?.getString(BIRTHDAY_KEY) }
    private val status by lazy { arguments?.getString(STATUS_KEY) }

    private lateinit var takePhoto: ActivityResultLauncher<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initScreen()

        changePhoto()

        setBirthday()

        navigation()
    }

    private fun initScreen() {
        binding.apply {
            avatar?.let {
                Glide.with(this@EditUserProfileFragment).load(it).into(binding.ivAvatar)
            }
            etCity.setText(getString(R.string.city, city ?: BLANK))
            btnBirthday.text = getString(R.string.birthday, birthday ?: BLANK)
            etStatus.setText(getString(R.string.status, status ?: BLANK))
        }
    }

    private fun changePhoto() {
        takePhoto = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri == null) binding.ivAvatar.setImageResource(R.drawable.dummy_avatar)
            else {
                binding.ivAvatar.setImageURI(uri)

                val inputStream = requireActivity().contentResolver.openInputStream(uri)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                val byteArrayOutputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream)
                val byteArray = byteArrayOutputStream.toByteArray()

                // TODO send base64String to the server
                val base64String = Base64.encodeToString(byteArray, Base64.DEFAULT)

                // TODO send filename to the server
                val filename = getFilename(uri)
            }
        }
        binding.ivAvatar.setOnClickListener { takePhoto.launch("image/*") }
    }

    private fun getFilename(uri: Uri): String? {
        val cursor = requireActivity().contentResolver
            .query(
                uri,
                null,
                null,
                null,
                null
            )
        cursor?.use {
            if (cursor.moveToFirst()) {
                val displayNameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (displayNameIndex != -1) return cursor.getString(displayNameIndex)
            }
        }
        return null
    }

    private fun navigation() {
        binding.tbRegistration.setNavigationOnClickListener { findNavController().popBackStack() }
        binding.ivApply.setOnClickListener { findNavController().popBackStack() }
    }

    private fun setBirthday() {
        val calendar = Calendar.getInstance()

        val datePickerDialog =
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val format = "dd.MM.yyyy"
                val sdf = SimpleDateFormat(format, Locale.getDefault())
                binding.btnBirthday.text = sdf.format(calendar.time)
            }

        binding.btnBirthday.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                datePickerDialog,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    companion object {
        const val AVATAR_KEY = "AVATAR_KEY"
        const val CITY_KEY = "CITY_KEY"
        const val BIRTHDAY_KEY = "BIRTHDAY_KEY"
        const val STATUS_KEY = "STATUS_KEY"
        private const val BLANK = ""
    }
}