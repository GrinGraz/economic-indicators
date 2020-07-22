package cl.cruz.economicindicators.data.repository.datasource.local

import androidx.room.DatabaseView

@DatabaseView(
    "SELECT user.id, user.name, user.departmentId," +
            "department.name AS departmentName FROM user " +
            "INNER JOIN department ON user.departmentId = department.id"
)
data class EconomicIndicatorDetailView(
    val code: String?,
    val name: String?,
    val measureUnit: String?,
    val date: String?,
    val value: Double?
)

@DatabaseView(
    "SELECT user.id, user.name, user.departmentId," +
            "department.name AS departmentName FROM user " +
            "INNER JOIN department ON user.departmentId = department.id"
)
data class EconomicIndicatorView(
    val code: String?,
    val name: String?,
    val value: Double?
)