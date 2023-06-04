package com.example.testformangofzco.presentation.user_profile

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.domain.models.User
import com.example.testformangofzco.App
import com.example.testformangofzco.R
import com.example.testformangofzco.databinding.FragmentUserProfileBinding
import com.example.testformangofzco.presentation.user_profile.EditUserProfileFragment.Companion.AVATAR_KEY
import com.example.testformangofzco.presentation.user_profile.EditUserProfileFragment.Companion.BIRTHDAY_KEY
import com.example.testformangofzco.presentation.user_profile.EditUserProfileFragment.Companion.CITY_KEY
import com.example.testformangofzco.presentation.user_profile.EditUserProfileFragment.Companion.STATUS_KEY
import com.example.testformangofzco.presentation.user_profile.EditUserProfileFragment.Companion.USERNAME_KEY
import com.example.testformangofzco.utils.safelyNavigate
import com.example.testformangofzco.utils.setZodiacSign
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

    private var user = User.emptyUser()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        inject()

        initUser()

        navigation()
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
                        this@UserProfileFragment.user = user
                        binding.apply {
                            user.avatars?.avatar?.let {
                                Log.d(TAG, "initUser: $BASE_URL$it")
                                Glide.with(requireContext())
                                    .load("$BASE_URL$it")
                                    .into(binding.ivAvatar)
                            }
                            tvPhone.text = user.phone
                            tvUsername.text = user.username
                            tvCity.text = user.city
                            tvBirthday.text = user.birthday
                            if (!user.birthday.isNullOrBlank())
                                tvZodiacSign.setZodiacSign(
                                    month = user.birthday!!.substring(FIFTH..SIXTH),
                                    dayOfMonth = user.birthday!!.substring(EIGHTH..NINTH).toInt()
                                )
                            tvStatus.text = user.status
                        }
                        binding.pbUserProfile.isGone = true
                        binding.svUserProfile.isGone = false
                    }
                )
            }
        }
    }

    private fun navigation() {
        binding.ivEdit.setOnClickListener {
            findNavController().safelyNavigate(
                R.id.action_userProfileFragment_to_editUserProfileFragment,
                bundleOf(
                    USERNAME_KEY to user.username,
                    AVATAR_KEY to "$BASE_URL${user.avatars?.avatar}",
                    CITY_KEY to user.city,
                    BIRTHDAY_KEY to user.birthday,
                    STATUS_KEY to user.status
                )
            )
        }
    }

    private companion object {
        private const val BASE_URL = "https://plannerok.ru/"
        private const val TAG = "UserProfileFragment"
        private const val FIFTH = 5
        private const val SIXTH = 6
        private const val EIGHTH = 8
        private const val NINTH = 9
    }
}