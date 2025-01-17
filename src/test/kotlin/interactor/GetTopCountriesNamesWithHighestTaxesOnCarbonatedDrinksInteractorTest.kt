package interactor

import fakeDataSource.FakeDataSourceForTaxesOnCarbonatedDrinks
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.function.Executable
import kotlin.IllegalArgumentException

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GetTopCountriesNamesWithHighestTaxesOnCarbonatedDrinksInteractorTest {

    private lateinit var getTopTenCountriesNamesWithHighestTaxesOnCarbonatedDrinksInteractor:
            GetTopCountriesNamesWithHighestTaxesOnCarbonatedDrinksInteractor

    private lateinit var fakeDataSourceForTaxesOnCarbonatedDrinks: FakeDataSourceForTaxesOnCarbonatedDrinks

    @BeforeEach
    fun setUp() {
        fakeDataSourceForTaxesOnCarbonatedDrinks = FakeDataSourceForTaxesOnCarbonatedDrinks()
        getTopTenCountriesNamesWithHighestTaxesOnCarbonatedDrinksInteractor =
            GetTopCountriesNamesWithHighestTaxesOnCarbonatedDrinksInteractor(
                fakeDataSourceForTaxesOnCarbonatedDrinks.getTwentyFakeCityData())
    }

    @Test
    fun should_Throw_Exception_When_Input_ZeroOrLess() {
        // Given
        val limit = -5
        // When
        //throws exception
        // Then
        val executable =
            Executable { getTopTenCountriesNamesWithHighestTaxesOnCarbonatedDrinksInteractor.execute(limit) }
        assertThrows(IllegalArgumentException::class.java, executable)
    }


    //region data quality test
    @Test
    fun should_Return_True_When_theDataQualityIsHigh() {
        // given
        val testCity = fakeDataSourceForTaxesOnCarbonatedDrinks.checkDataQuality()
        // When
        val result = excludeLowQualityData(testCity)
        // then
        assertTrue(result)
    }

    @Test
    fun should_ReturnFalse_When_theDataQualityIsLow() {
        // given
        val testCity = fakeDataSourceForTaxesOnCarbonatedDrinks.getLowQualityAssertion()
        // when
        val result = excludeLowQualityData(testCity)
        // then
        assertFalse(result)
    }
    //endregion

    //region country test
    @Test
    fun should_Return_True_When_countryIsValid() {
        // given
        val testCity = fakeDataSourceForTaxesOnCarbonatedDrinks.getValidCountry()
        // when
        val result = excludeInvalidCountries(testCity)
        // then
        assertTrue(result)
    }

    @Test
    fun should_Return_False_When_countryIsEmpty() {
        // given
        val testCity = fakeDataSourceForTaxesOnCarbonatedDrinks.getEmptyCountry()
        // when
        val result = excludeInvalidCountries(testCity)
        // then
        assertFalse(result)
    }

    @Test
    fun should_Return_False_When_countryLengthIsInvalid() {
        // given
        val testCity = fakeDataSourceForTaxesOnCarbonatedDrinks.getInvalidLengthCity()
        // when
        val result = excludeInvalidCountries(testCity)
        // then
        assertFalse(result)
    }

    @Test
    fun should_Return_False_When_countryNameIsInvalidWithFirstCharacter() {
        // given
        val testCity = fakeDataSourceForTaxesOnCarbonatedDrinks.getInvalidLengthCity()
        // when
        val result = excludeInvalidCountries(testCity)
        // then
        assertFalse(result)
    }

    @Test
    fun should_Return_False_When_CountryNameContainDigit() {
        // given
        val city = fakeDataSourceForTaxesOnCarbonatedDrinks.getCountryNameContainsDigits()
        // when
        val result = excludeInvalidCountries(city)
        // then
        assertFalse(result)
    }

    @Test
    fun should_Return_False_When_countryNameContainSymbols() {
        // given
        val city = fakeDataSourceForTaxesOnCarbonatedDrinks.getCountryNameContainsSymbols()
        // when
        val result = excludeInvalidCountries(city)
        // then
        assertFalse(result)
    }
    //endregion

    //region drink price test
    @Test
    fun should_Return_True_When_drinkPriceIsNotNull() {
        // given
        val testCity = fakeDataSourceForTaxesOnCarbonatedDrinks.getDataHasDrinkPriceIsNotNull()

        // when
        val result = excludeInvalidDrinksPrice(testCity)
        // then
        assertTrue(result)
    }

    @Test
    fun should_Return_False_When_drinkPriceIsNull() {
        // given
        val testCity = fakeDataSourceForTaxesOnCarbonatedDrinks.getNullDrinkPrice()
        // when
        val result = excludeInvalidDrinksPrice(testCity)
        // then
        assertFalse(result)
    }

    @Test
    fun should_Return_True_When_drinkPriceIsValid() {
        // given
        val city = fakeDataSourceForTaxesOnCarbonatedDrinks.getValidPrice()
        // when
        val result = excludeInvalidDrinksPrice(city)
        // then
        assertTrue(result)
    }

    @Test
    fun should_Return_When_drinkPriceIsInvalid() {
        // given
        val city = fakeDataSourceForTaxesOnCarbonatedDrinks.getInvalidDrinkPrices()
        // when
        val result = excludeInvalidDrinksPrice(city)
        // then
        assertFalse(result)
    }
    //endregion

}
