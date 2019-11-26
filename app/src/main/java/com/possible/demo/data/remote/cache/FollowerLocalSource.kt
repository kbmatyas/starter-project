package com.possible.demo.data.remote.cache

import com.possible.demo.data.model.User
import javax.inject.Inject

class FollowerLocalSource @Inject constructor() {

    private val followerMap = mutableMapOf<String, Pair<Long, List<User>>>()

    fun cacheFollowersForUser(user: String, followers: List<User>) {
        followerMap[user] = System.currentTimeMillis() to followers
    }

    fun getCacheForUser(user: String): List<User>? {
        val local = followerMap[user]

        return if (local == null || System.currentTimeMillis() - local.first > FOLLOWER_LIST_VALIDITY) null else local.second
    }

    companion object {
        private const val FOLLOWER_LIST_VALIDITY = 10L * 60 * 1000 // 10 minutes in millis
    }
}