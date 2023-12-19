package com.example.thirtydaysapp.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Wellness(
    @StringRes val stringResourceDay: Int,
    @StringRes val stringResourceQuote: Int,
    @StringRes val stringResourceId: Int,
    @DrawableRes val imageResourceId: Int
)
