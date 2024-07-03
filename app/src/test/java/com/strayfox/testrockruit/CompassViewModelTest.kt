package com.strayfox.testrockruit

import CompassViewModel
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertArrayEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class CompassViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val apiService = mockk<ApiService>()
    private lateinit var viewModel: CompassViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = CompassViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchData updates characters and words LiveData`() = runTest {
        // Arrange
        val responseBody = "This is a test string to check the 10th, 20th, 30th characters and so on."
        val response = Response.success(responseBody)
        coEvery { apiService.getAboutPage() } returns response

        val charactersObserver = mockk<Observer<Array<Char>>>(relaxed = true)
        val wordsObserver = mockk<Observer<Array<String>>>(relaxed = true)

        viewModel.characters.observeForever(charactersObserver)
        viewModel.words.observeForever(wordsObserver)

        // Act
        viewModel.fetchData()
        advanceUntilIdle()

        // Assert
        val expectedCharacters = arrayOf('i', 'e', 'k', 'h', 'c', 'r', '0', '0')
        val expectedWords = arrayOf("this: 1", "is: 1", "a: 1", "test: 1", "string: 1", "to: 1", "check: 1", "the: 1", "10th,: 1", "20th,: 1", "30th: 1", "characters: 1", "and: 1", "so: 1", "on.: 1")

        viewModel.characters.value?.let { assertArrayEquals(expectedCharacters, it) }
        viewModel.words.value?.let { assertArrayEquals(expectedWords, it) }
    }
}
