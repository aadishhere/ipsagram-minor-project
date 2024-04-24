package com.aadish.ipsagram.data.service.module

import com.aadish.ipsagram.data.service.AuthService
import com.aadish.ipsagram.data.service.PostService
import com.aadish.ipsagram.data.service.EventService
import com.aadish.ipsagram.data.service.UserService
import com.aadish.ipsagram.data.service.impl.AuthServiceImpl
import com.aadish.ipsagram.data.service.impl.PostServiceImpl
import com.aadish.ipsagram.data.service.impl.EventServiceImpl
import com.aadish.ipsagram.data.service.impl.UserServiceImpl
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideAuthService(): AuthService = AuthServiceImpl(
        auth = Firebase.auth,
    )

    @Provides
    fun provideUserService(): UserService = UserServiceImpl(
        firestore = Firebase.firestore,
        auth = AuthServiceImpl(
            auth = Firebase.auth,
        )
    )

    @Provides
    fun provideStorageService(): PostService = PostServiceImpl(
        firestore = Firebase.firestore
    )

    @Provides
    fun provideEventService(): EventService = EventServiceImpl(
        Firebase.firestore
    )
}

//@Module
//@InstallIn(SingletonComponent::class)
//abstract class ServiceModule {
//
//    abstract provide
//}