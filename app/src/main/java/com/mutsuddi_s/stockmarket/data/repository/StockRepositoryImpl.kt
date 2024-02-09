package com.mutsuddi_s.stockmarket.data.repository


import com.mutsuddi_s.stockmarket.data.csv.CompanyListingParser
import com.mutsuddi_s.stockmarket.data.csv.CsvParsar
import com.mutsuddi_s.stockmarket.data.local.StockDatabase
import com.mutsuddi_s.stockmarket.data.mapper.toCompanyListing
import com.mutsuddi_s.stockmarket.data.mapper.toCompanyListingEntity
import com.mutsuddi_s.stockmarket.data.remote.StockApi
import com.mutsuddi_s.stockmarket.domain.model.CompanyListing
import com.mutsuddi_s.stockmarket.domain.repository.StockRepository
import com.mutsuddi_s.stockmarket.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    val stockApi: StockApi,
    val stockDatabase: StockDatabase,
    val companyListingParser: CsvParsar<CompanyListing>
) : StockRepository {

    private val stockDao = stockDatabase.dao
    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {

        return flow {
            //initial loading before access data
            emit(Resource.Loading(true))
            val localListings = stockDao.searchCompanyListing(query)
            emit(Resource.Success(

                data = localListings
                    //to map it to domain level class
                    //so that we can use it out viewmodel and presentation
                    .map { it.toCompanyListing() }
            ))
            //to check if we actually do want to make an api call
            // bcoz if we have have data in cache and we don't swipe to refresh
            // then we don't have to make an api call
            val isDbEmpty = localListings.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote

            if(shouldJustLoadFromCache){
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteListings= try {
                val response =  stockApi.getListings()
                companyListingParser.parse(response.byteStream())

            }catch (e: IOException){ // pArsing goes wrong
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null

            } catch (e: HttpException){ // when ivalid response

                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }

            remoteListings?.let { listings->
                stockDao.clearCompanyListings()
                stockDao.insertCompanyListings(
                    listings.map { it.toCompanyListingEntity() }
                )
                emit(Resource.Success(
                    data = stockDao
                        .searchCompanyListing("")
                        .map{ it.toCompanyListing()}
                ))
                emit(Resource.Loading(false))

            }


        }
    }
}