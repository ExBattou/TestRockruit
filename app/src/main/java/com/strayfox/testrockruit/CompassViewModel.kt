import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strayfox.testrockruit.ApiService
import com.strayfox.testrockruit.retrofit
import kotlinx.coroutines.launch

// ViewModel
class CompassViewModel() : ViewModel() {
    private val apiService: ApiService = retrofit.create(ApiService::class.java)

    private val _characters = MutableLiveData<Array<Char>>()
    val characters: LiveData<Array<Char>> get() = _characters

    private val _words = MutableLiveData<Array<String>>()
    val words: LiveData<Array<String>> get() = _words

    fun fetchData() {
        viewModelScope.launch {
            try {
                val response = apiService.getAboutPage()
                if (response.isSuccessful) {
                    response.body()?.let { body ->
                        _characters.value = getEveryTenthCharacter(body)
                        _words.value = countWords(body)
                    }
                } else {
                    // Handle error response
                }
            } catch (e: Exception) {
                // Handle network or other errors
            }
        }
    }

    private fun getEveryTenthCharacter(input: String): Array<Char> {
        val result = mutableListOf<Char>()
        for (i in 9 until input.length step 10) {
            result.add(input[i])
        }
        return result.toTypedArray()
    }

    private fun countWords(input: String): Array<String> {
        val wordCounts = mutableMapOf<String, Int>()
        val words = input.split("\\s+".toRegex()).map { it.lowercase() }
        for (word in words) {
            if (word.isNotEmpty()) {
                wordCounts[word] = wordCounts.getOrDefault(word, 0) + 1
            }
        }
        val result = wordCounts.map { (word, count) -> "$word: $count" }
        return result.toTypedArray()
    }
}
