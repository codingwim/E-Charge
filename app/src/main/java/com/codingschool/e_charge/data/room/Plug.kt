package com.codingschool.e_charge.data.room

import androidx.room.*

@Entity(
    tableName = "plug",
    foreignKeys = [ForeignKey(
        entity = Station::class,
        parentColumns = ["station_id"],
        childColumns = ["plug_station_id"],
        onDelete = ForeignKey.CASCADE
    ),
        ForeignKey(
            entity = Type::class,
            parentColumns = ["type_id"],
            childColumns = ["plug_type_id"]
        )
    ]
)
data class Plug(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "plug_id")
    val plug_id: Int,
    @ColumnInfo(name = "plug_station_id")
    val station_id: Int,
    @ColumnInfo(name = "plug_type_id")
    val type_id: Int
) {
    constructor(
        station_id: Int,
        type_id: Int
    ) : this(
        0,
        station_id, type_id
    )
}

data class StationAndPlugs(
    @Embedded
    val station: Station,

    @Relation(
        parentColumn = "station_id",
        entityColumn = "plug_station_id"
    )
    val plugs: List<Plug>
)

