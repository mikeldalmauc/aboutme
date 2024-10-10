package com.example.myapplication.ui.componentes

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


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

    private val _todosNotDone = MutableStateFlow<List<TodoItem>>(emptyList())
    val todosNotDone: StateFlow<List<TodoItem>> = _todosNotDone

    private val _todoDone = MutableStateFlow<List<TodoItem>>(emptyList())
    val todoDone: StateFlow<List<TodoItem>> = _todoDone

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
            _todosNotDone.value = todos.filterNot { it.completed }
            _todoDone.value = todos.filter { it.completed }

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
        Log.d("TodoViewModel", "Delete todo...")

        db.document(todo.id).delete()
    }

    fun floatItem(item: TodoItem){
        Log.d("TodoViewModel", "float item ${item.title}, ${item.sortId}")

        val itemIndex = _todosNotDone.value.indexOf(item)
        val targetItem = _todosNotDone.value.elementAtOrElse(itemIndex - 1) { item }
        swapItemPosition(item, targetItem)
    }

    fun sinkItem(item: TodoItem){
        Log.d("TodoViewModel", "sink item ${item.title}, ${item.sortId}")

        val itemIndex = _todosNotDone.value.indexOf(item)
        val targetItem = _todosNotDone.value.elementAtOrElse(itemIndex + 1) { item }
        swapItemPosition(item, targetItem)
    }

    fun swapItemPosition(originItem: TodoItem, targetItem: TodoItem){
        Log.d("TodoViewModel", "Swap items ${originItem.title}, ${originItem.sortId}  by ${targetItem.title}, ${targetItem.sortId}")

        if(originItem != targetItem){
            db.document(originItem.id).update("sortId", targetItem.sortId)
            db.document(targetItem.id).update("sortId", originItem.sortId)
        }
        fetchTodos()
    }

}