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
                            user.avatar?.let {
                                Glide.with(requireContext()).load(it).into(ivAvatar)
                            }
                            tvPhone.text = getString(R.string.phone, user.phone)
                            tvUsername.text = getString(R.string.username, user.username)
                            tvCity.text = getString(R.string.city, user.city ?: BLANK)
                            tvBirthday.text = getString(R.string.birthday, user.birthday ?: BLANK)
                            user.birthday?.let {
                                Log.d(TAG, "initUser: user.birthday = ${user.birthday}")
                                Log.d(TAG, "initUser: month = ${user.birthday!!.substring(3..4)}")
                                Log.d(TAG, "initUser: dayOfMonth = ${user.birthday!!.substring(0..2).toInt()}")
                                tvZodiacSign.setZodiacSign(
                                    month = it.substring(3..4),
                                    dayOfMonth = it.substring(0..2).toInt()
                                )
                            }
                            tvStatus.text = getString(R.string.status, user.status ?: BLANK)
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
                    EditUserProfileFragment.AVATAR_KEY to user.avatar,
                    EditUserProfileFragment.CITY_KEY to user.city,
                    EditUserProfileFragment.BIRTHDAY_KEY to user.birthday,
                    EditUserProfileFragment.STATUS_KEY to user.status
                )
            )
        }
    }

    private companion object {
        private const val TAG = "UserProfileFragment"
        private const val BLANK = ""
    }
}