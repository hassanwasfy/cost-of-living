package interactor

import model.CityEntity
import model.ClothesPrices

class GetTopFiveCitesNameInClothesFromFamousBrandsInteractor(

    private val dataSource: CostOfLivingDataSource,
) {
    val limit=5
    fun execute(): List<String> {

        return dataSource
            .getAllCitiesData()
            .filter(::excludeNullClothesPrices)
            .sortedByDescending { avgPrice(it.clothesPrices) }
            .take(limit)
            .map { it.cityName }

    }

    fun excludeNullClothesPrices(city: CityEntity): Boolean {
        return city.clothesPrices.onePairOfJeansLevis50oneOrSimilar != null && city.clothesPrices.onePairOfMenLeatherBusinessShoes != null
                && city.clothesPrices.onePairOfNikeRunningShoesMidRange != null && city.clothesPrices.oneSummerDressInAChainStoreZaraHAndM != null

    }

}

fun avgPrice(clothesPrices: ClothesPrices): Float {
    var price = (clothesPrices.onePairOfJeansLevis50oneOrSimilar!! + clothesPrices.onePairOfMenLeatherBusinessShoes!! +
            clothesPrices.onePairOfNikeRunningShoesMidRange!! + clothesPrices.oneSummerDressInAChainStoreZaraHAndM!!) / 4
    return price

}