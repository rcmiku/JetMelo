package com.rcmiku.music.utils

import android.os.Build

class UserAgentUtil {

    companion object {

        val DEFAULT_USER_AGENT: String = buildString {
            val validRelease = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                Build.VERSION.RELEASE_OR_CODENAME.isNotEmpty()
            } else {
                Build.VERSION.RELEASE.isNotEmpty()
            }

            val validId = !Build.ID.isNullOrEmpty()
            val includeModel = "REL" == Build.VERSION.CODENAME && !Build.MODEL.isNullOrEmpty()

            append("NeteaseMusic/3.7.01.250103035128(3007001)")
            append(";Dalvik/").append(
                System.getProperty("java.vm.version")
            )

            append(" (Linux; U; Android")
            if (validRelease) {
                append(" ").append(
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        Build.VERSION.RELEASE_OR_CODENAME
                    } else {
                        Build.VERSION.RELEASE
                    }
                )
            }

            if (includeModel || validId) {
                append(";")
                if (includeModel) {
                    append(" ").append(Build.MODEL)
                }
                if (validId) {
                    append(" Build/").append(Build.ID)
                }
            }

            append(")")
        }
    }
}