package com.telstra.mvvmdemo.utils

import java.io.IOException

/**
 * Exception Class use to handle network exceptions
 */
class ApiException(message: String) : IOException(message)
class NoInternetException(message: String) : IOException(message)