package com.example.testformangofzco.presentation.account_creation.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter

private typealias OnClick = (countryCode: String) -> Unit

class CountryCodesAdapter(
    private val onClick: OnClick
) : ListAdapter<CountryCode, CountryCodesViewHolder>(CountryCodesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CountryCodesViewHolder.create(parent)

    override fun onBindViewHolder(holder: CountryCodesViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            onClick(getItem(position).code)
        }
    }
}