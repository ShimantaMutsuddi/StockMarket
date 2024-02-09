package com.mutsuddi_s.stockmarket.data.csv

import com.mutsuddi_s.stockmarket.domain.model.CompanyListing
import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CompanyListingParser @Inject constructor(): CsvParsar<CompanyListing> {
    override suspend fun parse(stream: InputStream): List<CompanyListing> {
        val csvReader = CSVReader(InputStreamReader(stream)) // CSVReader ইনিশিয়ালাইজ করা
        return withContext(Dispatchers.IO) { // IO ডিসপ্যাচারের সাথে এক্সেকিউশন করা
            csvReader
                .readAll() // সমস্ত লাইন পড়ে আনা
                .drop(1) // শুরুতে প্রথম লাইন প্রস্তুতি করে না, অনুলিপ্ট করা
                .mapNotNull { line ->
                    val symbol = line.getOrNull(0) // লাইন থেকে সিম্বল ধরা
                    val name = line.getOrNull(1) // লাইন থেকে নাম ধরা
                    val exchange = line.getOrNull(3) // লাইন থেকে এক্সচেঞ্জ ধরা
                    CompanyListing(
                        name = name ?: return@mapNotNull null, // নাম না থাকলে ফাংশন থেকে বের হতে
                        symbol = symbol ?: return@mapNotNull null, // সিম্বল না থাকলে ফাংশন থেকে বের হতে
                        exchange = exchange ?: return@mapNotNull null // এক্সচেঞ্জ না থাকলে ফাংশন থেকে বের হতে
                    )
                }
                .also {
                    csvReader.close() // CSVReader বন্ধ করা
                }
        }
    }
}
