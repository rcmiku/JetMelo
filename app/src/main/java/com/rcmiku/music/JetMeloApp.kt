package com.rcmiku.music

import android.app.Application
import androidx.media3.common.util.UnstableApi
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import coil3.disk.DiskCache
import coil3.disk.directory
import coil3.memory.MemoryCache
import coil3.request.CachePolicy
import coil3.request.crossfade
import com.rcmiku.music.constants.ncmCookieKey
import com.rcmiku.music.playback.PlayerController
import com.rcmiku.music.utils.SongListUtil
import com.rcmiku.music.utils.UserAgentUtil
import com.rcmiku.music.utils.dataStore
import com.rcmiku.ncmapi.utils.CookieProvider
import com.rcmiku.ncmapi.utils.FileProvider
import com.rcmiku.ncmapi.utils.UserAgentProvider
import com.rcmiku.ncmapi.utils.json
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@HiltAndroidApp
class JetMeloApp : Application(), SingletonImageLoader.Factory {

    @androidx.annotation.OptIn(UnstableApi::class)
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate() {
        super.onCreate()
        PlayerController.init(this)
        FileProvider.init(cacheDir.resolve("ncm"))
        SongListUtil.init(filesDir.resolve("playlist"))
        UserAgentProvider.init(UserAgentUtil.DEFAULT_USER_AGENT)
        GlobalScope.launch {
            UserAgentProvider.init(UserAgentUtil.DEFAULT_USER_AGENT)
            dataStore.data
                .map { it[ncmCookieKey] }
                .distinctUntilChanged()
                .collect { ncmCookie ->
                    if (ncmCookie?.isNotEmpty() == true)
                        CookieProvider.init(json.decodeFromString(ncmCookie))
                }
        }
    }

    override fun newImageLoader(context: PlatformContext): ImageLoader {
        return ImageLoader.Builder(context)
            .crossfade(true)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .memoryCache {
                MemoryCache.Builder()
                    .maxSizeBytes(64 * 1024 * 1024L)
                    .strongReferencesEnabled(true)
                    .build()
            }
            .diskCachePolicy(CachePolicy.ENABLED)
            .diskCache {
                DiskCache.Builder()
                    .maxSizeBytes(512 * 1024 * 1024L)
                    .directory(cacheDir.resolve("coil"))
                    .build()
            }
            .build()
    }

}
