package com.codingschool.e_charge.data.room

import androidx.room.*

@Entity(
    tableName = "station",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["user_id"],
            childColumns = ["creator_user_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Station(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "station_id")
    val station_id: Int,
    val street: String,
    val number: String,
    val zip: String,
    val town: String,
    val country: String,
    @ColumnInfo(name = "creator_user_id")
    val user_id: Int
) {
    constructor(
        street: String,
        number: String,
        zip: String,
        town: String,
        country: String,
        user_id: Int
    ) : this(
        0,
        street, number, zip, town, country, user_id
    )

    override fun toString(): String {
        return let { it.street + " " + it.number + " , " + it.zip + " " + it.town + " (" + it.country + ")" }
    }
}

data class UserAndStations(
    @Embedded
    val user: User,

    @Relation(
        parentColumn = "user_id",
        entityColumn = "creator_user_id"
    )
    val stations: List<Station>
)


