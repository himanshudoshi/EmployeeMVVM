package com.techm.employee.data.network

import com.techm.employee.data.model.Employees
import com.techm.employee.data.model.EmployeesDetails
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

/**
 *  Created Interface for Network Operations
 *  Created Companion object to Initialize Retrofit
 */
interface EmployeesApi {

    @GET("employees")
    suspend fun getData(): Response<Employees>
@FormUrlEncoded
    @POST("create")
    suspend fun createUser(
        @Field("name") name:String,
        @Field("salary") salary:Int,
        @Field("age") age:Int
    ):Response<Employees>

    companion object {
        private const val BASE_URL = "http://dummy.restapiexample.com/api/v1/"

        operator fun invoke(): EmployeesApi {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(EmployeesApi::class.java)
        }
    }
}