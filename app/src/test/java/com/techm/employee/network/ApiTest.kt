package com.techm.mvvmdemo.network

import com.techm.mvvmdemo.data.model.Facts
import com.techm.mvvmdemo.data.model.FactsItem
import com.techm.mvvmdemo.data.network.FactsApi
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class ApiTest {

    private lateinit var api: FactsApi

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        api = mockk()
    }

    @Test
    fun `GIVEN interface WHEN calling Facts detail api THEN Facts response`() {
        // Given
        coEvery{ api.getData()} returns getData()

        // When
        runBlocking { api.getData() }

        // Then
        coVerify { api.getData() }
    }

    fun getData() : Response<Facts> {
        val title = "About Canada"
        val list : MutableList<FactsItem> = mutableListOf()
        list.add(FactsItem("", "", "Beavers"))
        return Response.success(Facts(list,title))
    }

}