package com.example.newsapp.utils

sealed class AppError(message: String) : Throwable(message) {
    class NetworkError(message: String) : AppError(message)
    class DatabaseError(message: String) : AppError(message)
    class UnknownError(message: String) : AppError(message)
}