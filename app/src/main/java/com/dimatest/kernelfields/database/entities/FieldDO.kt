package com.dimatest.kernelfields.database.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "fields")
data class FieldDO(
    var fieldNo: String?,
    var pol: String?,
    @PrimaryKey
    var fieldID: Long,
    var farmName: String?,
    var farmID: Long?,
    var cornType: String?,
    var fieldNoDescr: String?,
    var area: Double?,
    var minX: Double?,
    var maxX: Double?,
    var minY: Double?,
    var maxY: Double?,
    var cultList: String?
): Parcelable