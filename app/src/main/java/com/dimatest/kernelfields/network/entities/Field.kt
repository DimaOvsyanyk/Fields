package com.dimatest.kernelfields.network.entities

import com.dimatest.kernelfields.database.entities.FieldDO
import com.squareup.moshi.Json

data class Field(
    @field:Json(name = "FieldNo") var fieldNo: String?,
    @field:Json(name = "pol") var pol: String?,
    @field:Json(name = "field_ID") var fieldID: Long,
    @field:Json(name = "FarmName") var farmName: String?,
    @field:Json(name = "farm_ID") var farmID: Long?,
    @field:Json(name = "CornType") var cornType: String?,
    @field:Json(name = "FieldNoDescr") var fieldNoDescr: String?,
    @field:Json(name = "Area") var area: Double?,
    @field:Json(name = "min_X") var minX: Double?,
    @field:Json(name = "max_X") var maxX: Double?,
    @field:Json(name = "min_Y") var minY: Double?,
    @field:Json(name = "max_Y") var maxY: Double?,
    @field:Json(name = "CultList") var cultList: String?
) {

    fun toFieldDO() = FieldDO(
        fieldNo,
        pol,
        fieldID,
        farmName,
        farmID,
        cornType,
        fieldNoDescr,
        area,
        minX,
        maxX,
        minY,
        maxY,
        cultList
    )

}