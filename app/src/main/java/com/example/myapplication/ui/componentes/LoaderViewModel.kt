import androidx.compose.runtime.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoaderViewModel : ViewModel() {

    // Estado del progreso
    private val _progress = MutableLiveData<Float>()
    val progress: LiveData<Float> = _progress

    init {
        _progress.value = 0f
    }

    // Función para simular el progreso
    fun startProgress() {
        viewModelScope.launch {
            _progress.value = 0f
            while (_progress.value!! < 1f) {
                delay(100)  // Simula la espera en cada ciclo
                _progress.value = _progress.value!! + 0.01f  // Incrementa el progreso
            }
        }
    }
}
