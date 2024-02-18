package com.mutsuddi_s.stockmarket.data.csv

import android.os.Build
import androidx.annotation.RequiresApi
import com.mutsuddi_s.stockmarket.data.mapper.toIntradayInfo
import com.mutsuddi_s.stockmarket.data.remote.dto.IntraDayInfoDto
import com.mutsuddi_s.stockmarket.domain.model.CompanyListing
import com.mutsuddi_s.stockmarket.domain.model.IntraDayInfo
import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IntraDayInfoParsar @Inject constructor(): CsvParsar<IntraDayInfo> {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun parse(stream: InputStream): List<IntraDayInfo> {
        val csvReader = CSVReader(InputStreamReader(stream)) // CSVReader ইনিশিয়ালাইজ করা
        return withContext(Dispatchers.IO) { // IO ডিসপ্যাচারের সাথে এক্সেকিউশন করা
            csvReader
                .readAll() // সমস্ত লাইন পড়ে আনা
                .drop(1) // শুরুতে প্রথম লাইন প্রস্তুতি করে না, অনুলিপ্ট করা
                .mapNotNull { line ->
                    val timestamp = line.getOrNull(0) ?: return@mapNotNull null// লাইন থেকে সিম্বল ধরা
                    val close = line.getOrNull(4)?: return@mapNotNull null // লাইন থেকে নাম ধরা
                    val dto= IntraDayInfoDto(timestamp,close.toDouble())
                    dto.toIntradayInfo()
                }
                .filter {
                    it.date.dayOfMonth== LocalDateTime.now().minusDays(1).dayOfMonth
                }
                .sortedBy {
                    it.date.hour
                }
                .also {
                    csvReader.close() // CSVReader বন্ধ করা
                }
        }
    }
}
