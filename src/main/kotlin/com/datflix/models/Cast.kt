package com.datflix.models

data class Cast(
    var id: String? = null,
    var actorId: String? = null,
    var movieId: String? = null,
    var characterName: String? = null,
    var isMainRole: Boolean = false,
    var orderCredit: Int = 0
) {
    fun isLeadRole(): Boolean = orderCredit <= 3
}
