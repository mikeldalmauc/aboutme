package com.example.myapplication.ui.componentes

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Date


data class TodoItem(
    val id: String = "",
    val sortId: Int = 0,
    val title: String,
    val completed: Boolean = false,
    val fechaCompletado: Timestamp? = null
) {
    constructor() : this("", 0, "", false, null)

    fun copy(id: String) = TodoItem(id, sortId, title, completed, fechaCompletado)
}

class TodoViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance().collection("todos")

    private val _todoList = MutableStateFlow<List<TodoItem>>(emptyList())
    val todoList: StateFlow<List<TodoItem>> = _todoList

    init {
        fetchTodos()
    }

    private fun fetchTodos() {
        Log.d("TodoViewModel", "Fetching todos...")

        db.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.e("TodoViewModel", "Error fetching todos: ${e.message}", e)
                return@addSnapshotListener
            }

            if (snapshot == null || snapshot.isEmpty) {
                Log.d("TodoViewModel", "No documents found.")
            } else {
                Log.d("TodoViewModel", "Snapshot size: ${snapshot.size()}")
            }

            val todos = snapshot?.documents?.mapNotNull { document ->
                Log.d("TodoViewModel", "Found document with ID: ${document.id}")
                document.toObject(TodoItem::class.java)?.copy(id = document.id)
            } ?: emptyList()

            if (todos.isEmpty()) {
                Log.d("TodoViewModel", "No valid todos were mapped from the snapshot.")
            }

            _todoList.value = todos
            Log.d("TodoViewModel", "Todo list updated with ${todos.size} items.")
        }
    }


    fun addTodo(title: String) {
        Log.i("TodoViewModel", "Adding todo...")

        val maxSortId = _todoList.value
            .filter { it.completed.not() }
            .fold(0) { acc, todo -> maxOf(acc, todo.sortId) }

        val newTodo = TodoItem(
            id = Timestamp.now().toString(),
            sortId = maxSortId + 1,
            title = title,
            completed = false,
            fechaCompletado = null
        )

        db.add(newTodo)
    }

    fun updateTodoStatus(todo: TodoItem, completed: Boolean) {
        Log.i("TodoViewModel", "Udpate todo...")

        when(completed){
            true -> db.document(todo.id).update("completed", true,"fechaCompletado", Timestamp.now())
            false -> db.document(todo.id).update("completed", false, "fechaCompletado", null)
        }
    }

    fun deleteTodo(todo: TodoItem) {
        Log.i("TodoViewModel", "Delete todo...")

        db.document(todo.id).delete()
    }
}