package com.example.testapp.authentication


/*class PreferencesDataStoreFactory @Inject constructor(@ApplicationContext val context: Context) {

    /*private val preferencesSerializer =
        androidx.datastore.preferences.core.PreferencesSerializer( // Preferences schema definition
            schema = PreferencesSchema(
                mapOf(
                    "user_id" to PreferenceDataStoreType.STRING,
                    "username" to PreferenceDataStoreType.STRING
                )
            )
        )*/

    private val USER_PREFERENCES_NAME: String = "HELLO"

    fun create(): ReadOnlyProperty<Context, DataStore<androidx.datastore.preferences.core.Preferences>> {
        return
    }
}

@Module
@InstallIn(ZamovApplication::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideDataStoreFactory(context: Context): PreferencesDataStoreFactory {
        return PreferencesDataStoreFactory(context)
    }

    @Provides
    @Singleton
    fun provideDataStore(dataStoreFactory: PreferencesDataStoreFactory): DataStore<UserData> {
        return dataStoreFactory.create().
    }
}*/