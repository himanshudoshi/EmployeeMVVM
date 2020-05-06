package com.telstra.telstramvvm.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object Coroutines {


    fun main(work: suspend (() -> Unit)) =

        CoroutineScope(Dispatchers.IO).launch {

            work()
        }
}