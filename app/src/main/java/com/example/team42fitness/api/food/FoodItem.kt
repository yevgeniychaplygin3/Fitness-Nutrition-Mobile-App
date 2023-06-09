package com.example.team42fitness.api.food

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.FromJson
import com.squareup.moshi.Json

import com.squareup.moshi.JsonClass
import com.squareup.moshi.ToJson
import java.io.Serializable


@JsonClass(generateAdapter = true)
@Entity
data class FoodItem(
    @PrimaryKey
    val fdcId: Int,
    val description: String,
    val brandName: String?,

    @Json(name="foodNutrients")
    val nutrients: List<Nutrients>
    ): Serializable

//SearchResultFood:
//      type: object
//      required:
//              - fdcId
//              - description
//      properties:
//              fdcId:
//                      description: Unique ID of the food.
//                      type: integer
//                      example: 45001529
//              dataType:
//                      description: The type of the food data.
//                      type: string
//                      example: "Branded"
//              description:
//                      description: The description of the food.
//                      type: string
//                      example: "BROCCOLI"
//              foodCode:
//                      description: Any A unique ID identifying the food within FNDDS.
//                      type: string
//              foodNutrients:
//                      type: array
//                              items:
//                              $ref: '#/components/schemas/AbridgedFoodNutrient'
//              publicationDate:
//                      description: Date the item was published to FDC.
//                      type: string
//                      example: "4/1/2019"
//              scientificName:
//                      description: The scientific name of the food.
//                      type: string
//              brandOwner:
//                      description: Brand owner for the food. Only applies to Branded Foods.
//                      type: string
//                      example: "Supervalu, Inc."
//              gtinUpc:
//                      description: GTIN or UPC code identifying the food. Only applies to Branded Foods.
//                      type: string
//                      example: "041303020937"
//              ingredients:
//                      description: The list of ingredients (as it appears on the product label). Only applies to Branded Foods.
//                      type: string
//              ndbNumber:
//                      description: Unique number assigned for foundation foods. Only applies to Foundation and SRLegacy Foods.
//                      type: integer
//              additionalDescriptions:
//                      description: Any additional descriptions of the food.
//                      type: string
//                      example: "Coon; sharp cheese; Tillamook; Hoop; Pioneer; New York; Wisconsin; Longhorn"
//              allHighlightFields:
//                      description: allHighlightFields
//                      type: string
//                      score:
//              description: Relative score indicating how well the food matches the search criteria.
//                      type: number
//                      format: float

/**
 * This class represents an item in the `list` field of the JSON response from the FoodData Central API.
 */
//AbridgedFoodNutrient:
//  required:
//      - id
//      - nutrientNumber
//      - unit
//  properties:
//      number:
//          type: integer
//          format: uint
//          example: 303
//      name:
//          type: string
//          example: "Iron, Fe"
//      amount:
//          type: number
//          format: float
//          example: 0.53
//      unitName:
//          type: string
//          example: "mg"
//      derivationCode:
//          type: string
//          example: "LCCD"
//      derivationDescription:
//          type: string
//          example: "Calculated from a daily value percentage per serving size measure"
@JsonClass(generateAdapter = true)
data class Nutrients (
    @Json(name="nutrientName")
    val name: String,

    @Json(name="unitName")
    val unit: String,

    @Json(name="value") // api spec seems to be misleading
    val amount: Float,

    ): Serializable
    
@JsonClass(generateAdapter = true)
data class FoodDataPropertiesJson(
    val fdcId: Int, // Unique ID of the food
    val description: String, // The Description of the food
    val ingredients: String?,
    val additionalDescriptions: String?, // Any additional descriptions of the food
    val score: Float?, // relative score indicating how well the food matches the search
    val foodNutrients: List<Nutrients>,
    val brandName: String?
)



/**
 * This class is a custom JSON adapter for use with Moshi.  It uses the classes above to represent
 * and parse the JSON response from the FoodData Central API, then it takes data from the parsed JSON
 * and uses it to build a [FoodItem] object, which becomes the ultimate return value from the
 * JSON parsing.
 */
class FoodListJsonAdapter {
    @FromJson
    fun FoodListFromJson(list: FoodDataPropertiesJson) = FoodItem(
        fdcId = list.fdcId,
        description = list.description,
        nutrients = list.foodNutrients,
        brandName = list.brandName
    )

    @ToJson
    fun FoodListToJson(food: FoodItem): String {
        throw UnsupportedOperationException("encoding FoodItem to JSON is not supported")
    }
}
