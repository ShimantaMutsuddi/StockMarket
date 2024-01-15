package com.mutsuddi_s.stockmarket.data.mapper

import com.mutsuddi_s.stockmarket.data.local.CompanyListingEntity
import com.mutsuddi_s.stockmarket.domain.model.CompanyListing

fun CompanyListingEntity.toCompanyListing() : CompanyListing{
    return CompanyListing(
        name=name,
        symbol = symbol,
        exchange= exchange
    )
}

fun CompanyListing.toCompanyListingEntity() : CompanyListingEntity{
    return CompanyListingEntity(
        name=name,
        symbol = symbol,
        exchange= exchange
    )
}