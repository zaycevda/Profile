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
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.domain.models.Avatar
import com.example.domain.models.UserShort
import com.example.testformangofzco.App
import com.example.testformangofzco.R
import com.example.testformangofzco.databinding.FragmentEditUserProfileBinding
import com.example.testformangofzco.utils.showToast
import com.example.testformangofzco.viewmodels.user_profile.EditUserProfileViewModel
import com.example.testformangofzco.viewmodels.user_profile.EditUserProfileViewModelFactory
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class EditUserProfileFragment : Fragment(R.layout.fragment_edit_user_profile) {

    @Inject
    lateinit var editUserProfileViewModelFactory: EditUserProfileViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, editUserProfileViewModelFactory)[EditUserProfileViewModel::class.java]
    }

    private val binding by viewBinding(FragmentEditUserProfileBinding::bind)

    private val username by lazy { arguments?.getString(USERNAME_KEY) }
    private val avatar by lazy { arguments?.getString(AVATAR_KEY) }
    private val city by lazy { arguments?.getString(CITY_KEY) }
    private val birthday by lazy { arguments?.getString(BIRTHDAY_KEY) }
    private val status by lazy { arguments?.getString(STATUS_KEY) }

    private var filename: String? = null
    private var base64Avatar: String? = null

    private lateinit var takePhoto: ActivityResultLauncher<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        inject()

        initScreen()

        editUser()

        changePhoto()

        setBirthday()

        navigation()
    }

    private fun inject() {
        App.component.injectEditUserProfileFragment(editUserProfileFragment = this)
    }

    private fun initScreen() {
        binding.apply {
            avatar?.let {
                Glide.with(this@EditUserProfileFragment)
                    .load(it)
                    .into(binding.ivAvatar)
            }
            etCity.setText(city)
            btnBirthday.text = birthday
            etStatus.setText(status)
        }
    }

    private fun editUser() {
        binding.ivApply.setOnClickListener {
            val avatar =
                if (filename != null && base64Avatar != null)
                    Avatar(
                        filename = filename!!,
                        base64 = base64Avatar!!
                    )
                else null

            val userShort = UserShort(
                username = username!!,
                name = BLANK,
                birthday = binding.btnBirthday.text.toString(),
                city = binding.etCity.text.toString(),
                status = binding.etStatus.text.toString(),
                avatar = avatar
            )

            viewModel.editUser(userShort = userShort)
            lifecycleScope.launchWhenCreated {
                viewModel.avatars.collect { state ->
                    state.on(
                        error = {
                            showToast(message = getString(R.string.error, it.message))
                            binding.pbEditUserProfile.isGone = true
                            binding.llEditUserProfile.isGone = false
                        },
                        loading = {
                            binding.pbEditUserProfile.isGone = false
                            binding.llEditUserProfile.isGone = true
                        },
                        success = {
                            findNavController().popBackStack()
                            binding.pbEditUserProfile.isGone = true
                            binding.llEditUserProfile.isGone = false
                        }
                    )
                }
            }
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

                val base64Avatar = Base64.encodeToString(byteArray, Base64.DEFAULT)

                filename = getFilename(uri)
                this.base64Avatar = base64Avatar

                val image = Base64.decode(base64Avatar, Base64.DEFAULT)
                Glide.with(requireContext())
                    .load(image)
                    .into(binding.ivAvatar)
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
    }

    private fun setBirthday() {
        val calendar = Calendar.getInstance()

        val datePickerDialog =
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val format = "yyyy-MM-dd"
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
        const val USERNAME_KEY = "USERNAME_KEY"
        const val AVATAR_KEY = "AVATAR_KEY"
        const val CITY_KEY = "CITY_KEY"
        const val BIRTHDAY_KEY = "BIRTHDAY_KEY"
        const val STATUS_KEY = "STATUS_KEY"
        private const val BLANK = ""
    }
}