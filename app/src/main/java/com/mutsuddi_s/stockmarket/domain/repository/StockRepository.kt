package com.mutsuddi_s.stockmarket.domain.repository

import com.mutsuddi_s.stockmarket.domain.model.CompanyInfo
import com.mutsuddi_s.stockmarket.domain.model.CompanyListing
import com.mutsuddi_s.stockmarket.domain.model.IntraDayInfo
import com.mutsuddi_s.stockmarket.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun getCompanyListings(

        // if TRUE : we get the remote data again( swaipe refresh layout)
        //if FALSE : we get the data from the cache
        fetchFromRemote:Boolean,
        //search query in the companyListings
        query: String

    ): Flow<Resource<List<CompanyListing>>>


    suspend fun getIntraDayInfo(
        symbol: String

    ): Resource<List<IntraDayInfo>>

    suspend fun getCompanyInfo(
        symbol: String
    ): Resource<CompanyInfo>
}