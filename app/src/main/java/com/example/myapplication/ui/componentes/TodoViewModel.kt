package com.example.myapplication.ui.componentes

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


data class TodoItem(  val id: String = ""
                    , val title: String
                    , val isCompleted: Boolean = false
                    , val fechaCompletado: String = "")

class TodoViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance().collection("todos")

    private val _todoList = MutableStateFlow<List<TodoItem>>(emptyList())
    val todoList: StateFlow<List<TodoItem>> = _todoList

    init {
        fetchTodos()
    }


    private fun fetchTodos() {

        db.addSnapshotListener { snapshot, e ->
            if (e != null) {
                return@addSnapshotListener
            }

            val todos = snapshot?.documents?.mapNotNull { document ->
                document.toObject(TodoItem::class.java)?.copy(id = document.id)
            } ?: emptyList()

            _todoList.value = todos
        }
    }

    fun addTodo(title: String) {
        val newTodo = TodoItem(title = title)
        db.add(newTodo)
    }

    fun updateTodoStatus(todo: TodoItem, isCompleted: Boolean) {
        db.document(todo.id).update("isCompleted", isCompleted)
    }

    fun deleteTodo(todo: TodoItem) {
        db.document(todo.id).delete()
    }
}