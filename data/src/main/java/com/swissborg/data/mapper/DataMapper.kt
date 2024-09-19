package com.swissborg.data.mapper

import com.swissborg.data.model.TickerDataModel
import com.swissborg.domain.model.TickerModel

fun TickerDataModel.toDto() = TickerModel(
    symbol = symbol,
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
