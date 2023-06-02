package com.example.testformangofzco.presentation.adapters

import androidx.recyclerview.widget.DiffUtil

class CountryCodesDiffCallback : DiffUtil.ItemCallback<CountryCode>() {
    override fun areItemsTheSame(oldItem: CountryCode, newItem: CountryCode) = oldItem == newItem
    override fun areContentsTheSame(oldItem: CountryCode, newItem: CountryCode) = oldItem == newItem
}