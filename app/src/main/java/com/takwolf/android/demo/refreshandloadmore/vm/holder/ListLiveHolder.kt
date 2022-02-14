package com.takwolf.android.demo.refreshandloadmore.vm.holder

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ListAdapter

open class ListLiveHolder<Entity>(entities: List<Entity>? = null) {
    val entitiesData: MutableLiveData<MutableList<Entity>?> = MutableLiveData(entities?.toMutableList())

    fun getList(): List<Entity> {
        return entitiesData.value?.toList() ?: listOf()
    }

    fun setList(entities: List<Entity>) {
        entitiesData.value = entities.toMutableList()
    }

    fun appendList(addedEntities: List<Entity>) {
        val entities = entitiesData.value ?: mutableListOf()
        entities.addAll(addedEntities)
        entitiesData.value = entities
    }

    fun clearList() {
        entitiesData.value = null
    }
}

fun <Entity> ListLiveHolder<Entity>.setupView(
    owner: LifecycleOwner,
    adapter: ListAdapter<Entity, *>,
) {
    entitiesData.observe(owner) { entities ->
        adapter.submitList(entities?.toList())
    }
}
