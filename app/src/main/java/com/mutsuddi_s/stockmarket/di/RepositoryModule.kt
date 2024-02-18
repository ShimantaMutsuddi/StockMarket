package com.mutsuddi_s.stockmarket.di

import com.mutsuddi_s.stockmarket.data.csv.CompanyListingParser
import com.mutsuddi_s.stockmarket.data.csv.CsvParsar
import com.mutsuddi_s.stockmarket.data.csv.IntraDayInfoParsar
import com.mutsuddi_s.stockmarket.data.repository.StockRepositoryImpl
import com.mutsuddi_s.stockmarket.domain.model.CompanyListing
import com.mutsuddi_s.stockmarket.domain.model.IntraDayInfo
import com.mutsuddi_s.stockmarket.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule{

    @Binds // binds for abstract function
    @Singleton
    abstract fun bindCompanyListingsParser(
        companyListingParser: CompanyListingParser
    ): CsvParsar<CompanyListing>

    @Binds // binds for abstract function
    @Singleton
    abstract fun bindIntraDayInfoParser(
        intraDayInfoParsar: IntraDayInfoParsar
    ): CsvParsar<IntraDayInfo>


    @Binds
    @Singleton
    abstract fun bindStockRepository(
        stockRepositoryImpl: StockRepositoryImpl
    ): StockRepository
}