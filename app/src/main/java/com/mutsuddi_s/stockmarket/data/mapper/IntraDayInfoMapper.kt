package com.mutsuddi_s.stockmarket.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.mutsuddi_s.stockmarket.data.remote.dto.CompanyInfoDto
import com.mutsuddi_s.stockmarket.data.remote.dto.IntraDayInfoDto
import com.mutsuddi_s.stockmarket.domain.model.CompanyInfo
import com.mutsuddi_s.stockmarket.domain.model.IntraDayInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.O)
fun IntraDayInfoDto.toIntradayInfo() : IntraDayInfo{
     val pattern = "yyyy-MM-dd HH:mm:ss"
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    val localDateTime= LocalDateTime.parse(timestamp, formatter)

    return IntraDayInfo(
        date = localDateTime,
        close=close
    )

}


fun CompanyInfoDto.toCompanyInfo(): CompanyInfo {

    return  CompanyInfo(
        symbol = symbol ?: "",
        description = description ?: "",
        name = name ?: "",
        country = country ?: "",
        industry = industry ?: "",
    )

}
