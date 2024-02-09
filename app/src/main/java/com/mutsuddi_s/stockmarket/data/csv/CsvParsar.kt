package com.mutsuddi_s.stockmarket.data.csv

import java.io.InputStream

interface CsvParsar<T> {

     //parse function to parse something
     suspend fun  parse(stream: InputStream): List<T>
}