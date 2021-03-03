package fr.juliettebois.dmii.myapplication.ui.home

import android.media.session.MediaSession
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.juliettebois.dmii.myapplication.dal.online.utils.Result
import fr.juliettebois.dmii.myapplication.models.Category
import fr.juliettebois.dmii.myapplication.repositories.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: CategoryRepository) : ViewModel() {

    private val _token: MutableLiveData<MediaSession.Token> = MutableLiveData()
    val token: LiveData<MediaSession.Token>
        get() = _token

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String>
        get() = _error

    private val _categories: MutableLiveData<List<Category>> = MutableLiveData()
    val categories: LiveData<List<Category>>
        get() = _categories

    // TODO voir pour mettre le loadCategory ici et non dans le Home Fragment
    fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.getCategories()) {
                is Result.Success -> {
                    _categories.postValue(result.data)
                }
                is Result.Error -> {
                    _error.postValue(result.message)
                }
            }
        }
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
}
