package com.mutsuddi_s.stockmarket.presentation.company_listings

sealed class CompanyListingsEvent {
    object Refresh: CompanyListingsEvent()

    // to perform the search and map the new search results to the
    // state to show it in ui
    data class OnSearchQueryChange(val query: String): CompanyListingsEvent()
}