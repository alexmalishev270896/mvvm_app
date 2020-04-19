package com.alex_malishev.data_layer.utils

import java.io.IOException

class TokenExpiredException : IOException() {
    override val message: String
        get() = "Token expired. Need to login again."
}