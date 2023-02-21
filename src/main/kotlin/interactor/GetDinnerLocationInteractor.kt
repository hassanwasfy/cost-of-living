package interactor

import model.*
import kotlin.math.abs

class GetDinnerLocationInteractor(private val dataSource: CostOfLivingDataSource) {
    fun execute(countryList: List<String>): CityEntity = dataSource.getAllCitiesData()
        .filter { it.country in countryList && excludeNullMealPrices(it.mealsPrices) }
        .sortedBy { getMealPricesAverage(it.mealsPrices) }
        .let { getClosestCity(it, getAverageBetweenTwoCities(it.first().mealsPrices, it.last().mealsPrices)) }
}
fun excludeNullMealPrices(mealsPrices: MealsPrices) = mealsPrices.mealInexpensiveRestaurant != null
        && mealsPrices.mealAtMcDonaldSOrEquivalent != null
        && mealsPrices.mealFor2PeopleMidRangeRestaurant != null

fun getMealPricesAverage(mealsPrices: MealsPrices) =
    (mealsPrices.mealInexpensiveRestaurant!! +
            mealsPrices.mealAtMcDonaldSOrEquivalent!! + mealsPrices.mealFor2PeopleMidRangeRestaurant!!) / 3

fun getAverageBetweenTwoCities(cheapestCityMealsPrices: MealsPrices, mostExpensiveCityMealsPrices: MealsPrices) =
    (getMealPricesAverage(cheapestCityMealsPrices) + getMealPricesAverage(mostExpensiveCityMealsPrices)) / 2

fun getClosestCity(
    citiesSortedByMealPrice: List<CityEntity>,
    averageBetweenTwoCities: Float,
): CityEntity =
    citiesSortedByMealPrice.minByOrNull { abs(getMealPricesAverage(it.mealsPrices) - averageBetweenTwoCities) }!!



