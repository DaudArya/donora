package com.sigarda.donora.data.network.service

import retrofit2.Response
import com.sigarda.donora.data.network.models.notification.NotificationModel
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


interface FCMInterface  {

    @Headers(
        "Authorization: key=AAAAyqI0CU4:APA91bEw6q6JAIN4cWoIibuwYsrMNQa655ongYfRF4W3jfu1hcTn3cvueUmyBZPoRl9ztKdFFlz1Y689R7ufWyBPrOMvvBj2CLY8VLnCgWS-rCx7mS8MuTsqTlzNrIde0QtpTegNoMgH"
        ,
        "Content-Type:application/json"
    )

    @POST("fcm/send")
    suspend fun sendNotification( @Body notificationModel: NotificationModel): Response<ResponseBody>

}