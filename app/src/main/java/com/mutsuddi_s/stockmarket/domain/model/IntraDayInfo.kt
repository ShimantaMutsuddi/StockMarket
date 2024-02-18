package com.mutsuddi_s.stockmarket.domain.model

import java.time.LocalDateTime


data class IntraDayInfo(
    val date: LocalDateTime,
    val close: Double,

    )