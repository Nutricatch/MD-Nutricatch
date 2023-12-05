package com.nutricatch.dev.data.repository

import androidx.lifecycle.liveData
import com.nutricatch.dev.data.ResultState
import com.nutricatch.dev.data.api.ApiService
import com.nutricatch.dev.model.LatestPost
import com.nutricatch.dev.model.LatestPostResponse
import retrofit2.HttpException
import java.net.UnknownHostException

class PostRepository private constructor(private val apiService: ApiService) {
    fun getLatestPost() = liveData {
        emit(ResultState.Loading)

        try {
            /// TODO ambil dari api jika sudah ada
            val postResponse = LatestPostResponse(
                false, "Success", arrayListOf(
                    LatestPost(
                        1,
                        "Hidup Sehat",
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris aliquet ante quam, eget hendrerit est tempus in. Cras pellentesque maximus purus et faucibus. Fusce congue at felis a vestibulum. Integer ac tellus nibh. Donec vel placerat eros. In ut augue ipsum. Nulla et neque sed libero tempor posuere. Ut id aliquet ex. Aenean ac risus et tellus aliquet egestas non et ipsum. Etiam mi diam, ultricies vel nisi et, tempus maximus lacus. Donec at bibendum sapien. Nunc varius felis id orci consectetur, sit amet viverra lorem sodales. Maecenas quis bibendum eros. Donec ut porta ante, fringilla facilisis est. Etiam ullamcorper diam magna, at elementum nibh semper ac.",
                        ""
                    ),
                    LatestPost(
                        2,
                        "Dengan Baik",
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris aliquet ante quam, eget hendrerit est tempus in. Cras pellentesque maximus purus et faucibus. Fusce congue at felis a vestibulum. Integer ac tellus nibh. Donec vel placerat eros. In ut augue ipsum. Nulla et neque sed libero tempor posuere. Ut id aliquet ex. Aenean ac risus et tellus aliquet egestas non et ipsum. Etiam mi diam, ultricies vel nisi et, tempus maximus lacus. Donec at bibendum sapien. Nunc varius felis id orci consectetur, sit amet viverra lorem sodales. Maecenas quis bibendum eros. Donec ut porta ante, fringilla facilisis est. Etiam ullamcorper diam magna, at elementum nibh semper ac.",
                        ""
                    ),
                    LatestPost(
                        3,
                        "Dan Bijak",
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris aliquet ante quam, eget hendrerit est tempus in. Cras pellentesque maximus purus et faucibus. Fusce congue at felis a vestibulum. Integer ac tellus nibh. Donec vel placerat eros. In ut augue ipsum. Nulla et neque sed libero tempor posuere. Ut id aliquet ex. Aenean ac risus et tellus aliquet egestas non et ipsum. Etiam mi diam, ultricies vel nisi et, tempus maximus lacus. Donec at bibendum sapien. Nunc varius felis id orci consectetur, sit amet viverra lorem sodales. Maecenas quis bibendum eros. Donec ut porta ante, fringilla facilisis est. Etiam ullamcorper diam magna, at elementum nibh semper ac.",
                        ""
                    ),
                    LatestPost(
                        4,
                        "Hidup Sehat",
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris aliquet ante quam, eget hendrerit est tempus in. Cras pellentesque maximus purus et faucibus. Fusce congue at felis a vestibulum. Integer ac tellus nibh. Donec vel placerat eros. In ut augue ipsum. Nulla et neque sed libero tempor posuere. Ut id aliquet ex. Aenean ac risus et tellus aliquet egestas non et ipsum. Etiam mi diam, ultricies vel nisi et, tempus maximus lacus. Donec at bibendum sapien. Nunc varius felis id orci consectetur, sit amet viverra lorem sodales. Maecenas quis bibendum eros. Donec ut porta ante, fringilla facilisis est. Etiam ullamcorper diam magna, at elementum nibh semper ac.",
                        ""
                    ),
                    LatestPost(
                        5,
                        "Dengan Baik",
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris aliquet ante quam, eget hendrerit est tempus in. Cras pellentesque maximus purus et faucibus. Fusce congue at felis a vestibulum. Integer ac tellus nibh. Donec vel placerat eros. In ut augue ipsum. Nulla et neque sed libero tempor posuere. Ut id aliquet ex. Aenean ac risus et tellus aliquet egestas non et ipsum. Etiam mi diam, ultricies vel nisi et, tempus maximus lacus. Donec at bibendum sapien. Nunc varius felis id orci consectetur, sit amet viverra lorem sodales. Maecenas quis bibendum eros. Donec ut porta ante, fringilla facilisis est. Etiam ullamcorper diam magna, at elementum nibh semper ac.",
                        ""
                    ),
                    LatestPost(
                        6,
                        "Dan Bijak",
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris aliquet ante quam, eget hendrerit est tempus in. Cras pellentesque maximus purus et faucibus. Fusce congue at felis a vestibulum. Integer ac tellus nibh. Donec vel placerat eros. In ut augue ipsum. Nulla et neque sed libero tempor posuere. Ut id aliquet ex. Aenean ac risus et tellus aliquet egestas non et ipsum. Etiam mi diam, ultricies vel nisi et, tempus maximus lacus. Donec at bibendum sapien. Nunc varius felis id orci consectetur, sit amet viverra lorem sodales. Maecenas quis bibendum eros. Donec ut porta ante, fringilla facilisis est. Etiam ullamcorper diam magna, at elementum nibh semper ac.",
                        ""
                    ),
                    LatestPost(
                        7,
                        "Hidup Sehat",
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris aliquet ante quam, eget hendrerit est tempus in. Cras pellentesque maximus purus et faucibus. Fusce congue at felis a vestibulum. Integer ac tellus nibh. Donec vel placerat eros. In ut augue ipsum. Nulla et neque sed libero tempor posuere. Ut id aliquet ex. Aenean ac risus et tellus aliquet egestas non et ipsum. Etiam mi diam, ultricies vel nisi et, tempus maximus lacus. Donec at bibendum sapien. Nunc varius felis id orci consectetur, sit amet viverra lorem sodales. Maecenas quis bibendum eros. Donec ut porta ante, fringilla facilisis est. Etiam ullamcorper diam magna, at elementum nibh semper ac.",
                        ""
                    ),
                    LatestPost(
                        8,
                        "Dengan Baik",
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris aliquet ante quam, eget hendrerit est tempus in. Cras pellentesque maximus purus et faucibus. Fusce congue at felis a vestibulum. Integer ac tellus nibh. Donec vel placerat eros. In ut augue ipsum. Nulla et neque sed libero tempor posuere. Ut id aliquet ex. Aenean ac risus et tellus aliquet egestas non et ipsum. Etiam mi diam, ultricies vel nisi et, tempus maximus lacus. Donec at bibendum sapien. Nunc varius felis id orci consectetur, sit amet viverra lorem sodales. Maecenas quis bibendum eros. Donec ut porta ante, fringilla facilisis est. Etiam ullamcorper diam magna, at elementum nibh semper ac.",
                        ""
                    ),
                    LatestPost(
                        9,
                        "Dan Bijak",
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris aliquet ante quam, eget hendrerit est tempus in. Cras pellentesque maximus purus et faucibus. Fusce congue at felis a vestibulum. Integer ac tellus nibh. Donec vel placerat eros. In ut augue ipsum. Nulla et neque sed libero tempor posuere. Ut id aliquet ex. Aenean ac risus et tellus aliquet egestas non et ipsum. Etiam mi diam, ultricies vel nisi et, tempus maximus lacus. Donec at bibendum sapien. Nunc varius felis id orci consectetur, sit amet viverra lorem sodales. Maecenas quis bibendum eros. Donec ut porta ante, fringilla facilisis est. Etiam ullamcorper diam magna, at elementum nibh semper ac.",
                        ""
                    ),
                    LatestPost(
                        10,
                        "Hidup Sehat",
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris aliquet ante quam, eget hendrerit est tempus in. Cras pellentesque maximus purus et faucibus. Fusce congue at felis a vestibulum. Integer ac tellus nibh. Donec vel placerat eros. In ut augue ipsum. Nulla et neque sed libero tempor posuere. Ut id aliquet ex. Aenean ac risus et tellus aliquet egestas non et ipsum. Etiam mi diam, ultricies vel nisi et, tempus maximus lacus. Donec at bibendum sapien. Nunc varius felis id orci consectetur, sit amet viverra lorem sodales. Maecenas quis bibendum eros. Donec ut porta ante, fringilla facilisis est. Etiam ullamcorper diam magna, at elementum nibh semper ac.",
                        ""
                    ),
                    LatestPost(
                        11,
                        "Dengan Baik",
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris aliquet ante quam, eget hendrerit est tempus in. Cras pellentesque maximus purus et faucibus. Fusce congue at felis a vestibulum. Integer ac tellus nibh. Donec vel placerat eros. In ut augue ipsum. Nulla et neque sed libero tempor posuere. Ut id aliquet ex. Aenean ac risus et tellus aliquet egestas non et ipsum. Etiam mi diam, ultricies vel nisi et, tempus maximus lacus. Donec at bibendum sapien. Nunc varius felis id orci consectetur, sit amet viverra lorem sodales. Maecenas quis bibendum eros. Donec ut porta ante, fringilla facilisis est. Etiam ullamcorper diam magna, at elementum nibh semper ac.",
                        ""
                    ),
                    LatestPost(
                        12,
                        "Dan Bijak",
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris aliquet ante quam, eget hendrerit est tempus in. Cras pellentesque maximus purus et faucibus. Fusce congue at felis a vestibulum. Integer ac tellus nibh. Donec vel placerat eros. In ut augue ipsum. Nulla et neque sed libero tempor posuere. Ut id aliquet ex. Aenean ac risus et tellus aliquet egestas non et ipsum. Etiam mi diam, ultricies vel nisi et, tempus maximus lacus. Donec at bibendum sapien. Nunc varius felis id orci consectetur, sit amet viverra lorem sodales. Maecenas quis bibendum eros. Donec ut porta ante, fringilla facilisis est. Etiam ullamcorper diam magna, at elementum nibh semper ac.",
                        ""
                    ),
                )
            )
//            val postResponse = apiService.getLatestPost()
            emit(ResultState.Success(postResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.message()
            when (val code = e.code()) {
                in 300..399 -> {
                    emit(ResultState.Error("Need To Reconfigure. Please Contact Administrator"))
                }

                in 400..499 -> {
                    emit(ResultState.Error("Request Error. code $code $errorBody"))
                }

                in 500..599 -> {
                    emit(ResultState.Error("Server Error"))
                }
            }
        } catch (e: UnknownHostException) {
            emit(ResultState.Error("Error... Please check your connection"))
        } catch (e: Exception) {
            emit(ResultState.Error("Unknown Error"))
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: PostRepository? = null
        fun getInstance(apiService: ApiService) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: PostRepository(apiService)
        }.also { INSTANCE = it }
    }
}