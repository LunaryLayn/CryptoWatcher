package com.swissborg.data.mapper

import com.swissborg.data.model.TickerDataModel
import com.swissborg.data.util.DataUtil
import com.swissborg.domain.model.TickerModel
import okhttp3.internal.Util

fun TickerDataModel.toDto() = TickerModel(
    symbol = symbol.removePrefix("t").removeSuffix(":USD").removeSuffix("USD"),
    bid = bid,
    bidSize = bidSize,
    ask = ask,
    askSize = askSize,
    dailyChange = dailyChange,
    dailyChangePerc = dailyChangePerc*100,
    lastPrice = lastPrice,
    volume = volume,
    high = high,
    low = low
)

