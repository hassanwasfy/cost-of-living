package interactor

import model.CityEntity

class GetFastestCitiesToBuyApartmentInteractor(
    private val dataSource: CostOfLivingDataSource
) {
    fun execute(limit: Int): List<Pair<String, Double>> {
        if (limit < 1) throw IllegalArgumentException("Invalid Argument!")
        return dataSource.getAllCitiesData()
            .filter { excludeInvalidSalaries(it) && excludeInvalidApartmentPrice(it) && excludeLowQualityData(it) }
            .map { Pair(it.cityName, calculateNumberOfYearsToBuyAPT(it)) }
            .sortedBy { it.second }
            .take(limit)
    }


    fun excludeInvalidSalaries(cityEntity: CityEntity) =
        cityEntity.averageMonthlyNetSalaryAfterTax != null &&
                cityEntity.averageMonthlyNetSalaryAfterTax > 0

    fun excludeLowQualityData(cityEntity: CityEntity) = cityEntity.dataQuality

    fun excludeInvalidApartmentPrice(cityEntity: CityEntity) =
        cityEntity.realEstatesPrices.pricePerSquareMeterToBuyApartmentOutsideOfCentre != null &&
                cityEntity.realEstatesPrices.pricePerSquareMeterToBuyApartmentOutsideOfCentre > 0

    fun calculateNumberOfYearsToBuyAPT(it: CityEntity): Double {
        val priceToBuy100MeterApartmentOutsideOfCentre =
            100.0 * it.realEstatesPrices.pricePerSquareMeterToBuyApartmentOutsideOfCentre!!
        val averageYearlyNetSalaryAfterTax = it.averageMonthlyNetSalaryAfterTax!! * 12
        return priceToBuy100MeterApartmentOutsideOfCentre / averageYearlyNetSalaryAfterTax
    }
}