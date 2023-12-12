package com.example.jelajahiapp.data

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jelajahiapp.di.Injection
import com.example.jelajahiapp.ui.screen.authorization.viewmodel.UserViewModel
import com.example.jelajahiapp.ui.screen.community.viewmodel.CommunityViewModel


class ViewModelFactory private constructor(
    private val repository: JelajahiRepository,
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(UserViewModel::class.java) -> {
                UserViewModel(repository) as T
            }
            modelClass.isAssignableFrom(CommunityViewModel::class.java) -> {
                CommunityViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(
            context: Context,
        ): ViewModelFactory {
            if (instance == null) {
                synchronized(ViewModelFactory::class.java) {
                    instance = ViewModelFactory(
                        Injection.getRepository(context)
                    )
                }
            }
            return instance as ViewModelFactory
        }
    }
}
