package com.example.testformangofzco.presentation.user_profile

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.testformangofzco.App
import com.example.testformangofzco.R
import com.example.testformangofzco.databinding.FragmentUserProfileBinding
import com.example.testformangofzco.utils.showToast
import com.example.testformangofzco.viewmodels.user_profile.UserProfileViewModel
import com.example.testformangofzco.viewmodels.user_profile.UserProfileViewModelFactory
import javax.inject.Inject

class UserProfileFragment : Fragment(R.layout.fragment_user_profile) {

    @Inject
    lateinit var userProfileViewModelFactory: UserProfileViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, userProfileViewModelFactory)[UserProfileViewModel::class.java]
    }

    private val binding by viewBinding(FragmentUserProfileBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        inject()

        initUser()
    }

    private fun inject() {
        App.component.injectUserProfileFragment(userProfileFragment = this)
    }

    private fun initUser() {
        viewModel.getUser()
        lifecycleScope.launchWhenCreated {
            viewModel.user.collect { state ->
                state.on(
                    error = {
                        showToast(message = getString(R.string.error, it.message))
                        binding.pbUserProfile.isGone = true
                        binding.svUserProfile.isGone = false
                    },
                    loading = {
                        binding.pbUserProfile.isGone = false
                        binding.svUserProfile.isGone = true
                    },
                    success = { user ->
                        binding.apply {
                            user.avatar?.let {
                                Glide.with(requireContext()).load(it).into(ivAvatar)
                            }
                            tvPhone.text = getString(R.string.phone, user.phone)
                            tvUsername.text = getString(R.string.username, user.username)
                            tvCity.text = getString(R.string.city, user.city ?: BLANK)
                            tvBirthday.text = getString(R.string.birthday, user.birthday ?: BLANK)
                            tvZodiacSign.text = getString(R.string.zodiac_sign, BLANK) // TODO
                            tvAbout.text = getString(R.string.about, BLANK) // TODO
                        }
                        binding.pbUserProfile.isGone = true
                        binding.svUserProfile.isGone = false
                    }
                )
            }
        }
    }

    private companion object {
        private const val BLANK = ""
    }
}