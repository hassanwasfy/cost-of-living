package interactor.addons

import fakeDataSource.addons.FakeDataSourceForCrookPerson
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.function.Executable

class GetCheapestCityForCrookPersonInteractorTest {


    private lateinit var interactor: GetCheapestCityForCrookPersonInteractor
    private lateinit var fakeDataSource:FakeDataSourceForCrookPerson

    @BeforeEach
    fun setUp() {
        fakeDataSource = FakeDataSourceForCrookPerson()
    }

    @Test
    fun execute() {
        interactor = GetCheapestCityForCrookPersonInteractor(
            fakeDataSource.trueFalseCityEntityList())

        val data = interactor.execute()

        val expected = "Cairo"

        assertEquals(expected,data)
    }

    @Test
    fun should_Throw_Exception_When_List_Is_Empty(){
        interactor = GetCheapestCityForCrookPersonInteractor(
            fakeDataSource.getListWithNullSalaryAndNullPrice())

        val data = Executable {interactor.execute()}

        val expected = NullPointerException()::class.java


        assertThrows(expected,data)

    }

    @Test
    fun should_Return_True_When_Data_Quality_High() {
        interactor = GetCheapestCityForCrookPersonInteractor(
            fakeDataSource.trueFalseCityEntityList())

        val expected = interactor.excludeFalseDataQuality(
            fakeDataSource.trueFalseCityEntityList().getAllCitiesData()[0])

        assertTrue(expected)
    }

    @Test
    fun should_Return_False_When_Data_Quality_Low() {
        interactor = GetCheapestCityForCrookPersonInteractor(
            fakeDataSource.trueFalseCityEntityList())

        val expected = interactor.excludeFalseDataQuality(
            fakeDataSource.trueFalseCityEntityList().getAllCitiesData()[1])

        assertFalse(expected)
    }

    @Test
    fun should_Return_False_When_Salary_Is_Null() {
        interactor = GetCheapestCityForCrookPersonInteractor(
            fakeDataSource.trueFalseCityEntityList())

        val expected = interactor.excludeNullSalaryAndNullMinutePrice(
            fakeDataSource.getListWithNullSalaryAndNullPrice().getAllCitiesData()[0])

        assertFalse(expected)
    }

    @Test
    fun should_Return_False_When_Price_Is_Null() {
        interactor = GetCheapestCityForCrookPersonInteractor(
            fakeDataSource.trueFalseCityEntityList())

        val expected = interactor.excludeNullSalaryAndNullMinutePrice(
            fakeDataSource.getListWithNullSalaryAndNullPrice().getAllCitiesData()[1])

        assertFalse(expected)
    }

    @Test
    fun calculateMinutePricePercentToSalary() {
        interactor = GetCheapestCityForCrookPersonInteractor(
            fakeDataSource.trueFalseCityEntityList())

        val result = interactor.calculateMinutePricePercentToSalary(
                fakeDataSource.percentLogicTest().getAllCitiesData()[0])


        val expected = 10

        assertEquals(expected,result)
    }
}