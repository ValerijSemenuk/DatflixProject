package com.datflix.models

import kotlinx.serialization.Serializable

@Serializable
data class Cast(
    val id: Int,                // Унікальний ID ролі
    val actorId: Int,           // Посилання на актора (Actor.id)
    val movieId: Int,           // Посилання на фільм (Movie.id)
    val characterName: String,  // Назва ролі ("Тоні Старк")
    val isMainRole: Boolean,    // Головна роль чи другорядна
    val orderCredit: Int        // Порядок у титрах (1 = перший)
) {
    // Додаткові методи
    fun isLeadRole(): Boolean = orderCredit <= 3  // Перші 3 ролі = головні
}