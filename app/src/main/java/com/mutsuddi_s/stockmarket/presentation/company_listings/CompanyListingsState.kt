package com.mutsuddi_s.stockmarket.presentation.company_listings

import com.mutsuddi_s.stockmarket.domain.model.CompanyListing

data class CompanyListingsState(
    val companis: List<CompanyListing> = emptyList(),
    val isLoading: Boolean= false,
    val isRefreshing: Boolean= false,
    val searchQuery: String=""
)

