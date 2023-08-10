package com.ec25p5e.notesapp.feature_profile.domain.use_case

import com.ec25p5e.notesapp.core.data.local.datastore_pref.DataStorePreferenceConstants.USER_ID
import com.ec25p5e.notesapp.core.data.local.datastore_pref.DataStorePreferenceImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class GetOwnUserIdCase(
    private val preferenceImpl: DataStorePreferenceImpl
) {

    operator fun invoke(): String {
        return runBlocking(Dispatchers.IO) {
            preferenceImpl.getPreference(USER_ID, "").first()
        }
    }
}