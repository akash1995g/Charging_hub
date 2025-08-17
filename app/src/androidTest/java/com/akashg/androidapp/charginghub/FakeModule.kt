package com.akashg.androidapp.charginghub

import com.akashg.androidapp.charginghub.di.AppModule
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class],
)
object FakeModule
