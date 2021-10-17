package com.codingschool.e_charge.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "type")
data class Type(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "type_id")
    val type_id: Int,
    val name: String
) {
    constructor(
        name: String
    ) : this(
        0,
        name
    )
}
