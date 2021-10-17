package com.codingschool.e_charge.data.room

import androidx.room.*

@Entity(tableName = "user", indices = [Index(value = ["email"], unique = true)])
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    val user_id: Int,
    @ColumnInfo(name = "email")
    val email: String,
    val user_name: String,
    val password: String,
    val salt: String,
    @ColumnInfo(name = "recommended_user_id")
    val recommended_user_id: Int?
) {
    constructor(
        email: String,
        user_name: String,
        password: String,
        salt: String,
        recommended_user_id: Int?
    ) : this(
        0,
        email, user_name, password, salt, recommended_user_id
    )
}

