package com.anugrah.proyekakhir_anugrahputraaf.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anugrah.proyekakhir_anugrahputraaf.Model.CategoryModel
import com.anugrah.proyekakhir_anugrahputraaf.Model.ItemsModel
import com.anugrah.proyekakhir_anugrahputraaf.Model.SliderModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener

class MainViewModel : ViewModel() {

    private val firebaseDatabase = FirebaseDatabase.getInstance()

    private val _category=MutableLiveData<MutableList<CategoryModel>>()
    private val _banners = MutableLiveData<List<SliderModel>>()
    private val _recommended=MutableLiveData<MutableList<ItemsModel>>()


    val banners: LiveData<List<SliderModel>> = _banners
    val categories:LiveData<MutableList<CategoryModel>> = _category
    val recommended:LiveData<MutableList<ItemsModel>> = _recommended

    fun loadFiltered(id:String) {
        val Ref=firebaseDatabase.getReference("Items")
        val query:Query=Ref.orderByChild("categoryId").equalTo(id)
        query.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists= mutableListOf<ItemsModel>()
                for(childSnapshot in snapshot.children) {
                    val list =childSnapshot.getValue(ItemsModel::class.java)
                    if (list != null) {
                        lists.add(list)
                    }
                }
                _recommended.value = lists
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun loadRecommended(){
        val Ref=firebaseDatabase.getReference("Items")
        val query:Query=Ref.orderByChild("ShowRecommended").equalTo(true)
        query.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists= mutableListOf<ItemsModel>()
                for(childSnapshot in snapshot.children) {
                    val list =childSnapshot.getValue(ItemsModel::class.java)
                    if (list != null) {
                        lists.add(list)
                    }
                }
                _recommended.value = lists
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    fun loadBanners() {
        val ref = firebaseDatabase.getReference("Banner")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<SliderModel>()
                for (childSnapshot in snapshot.children) {
                    val list = childSnapshot.getValue(SliderModel::class.java)
                    if (list != null) {
                        lists.add(list)
                    }
                }
                _banners.value = lists
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    fun loadcategory(){
        val Ref=firebaseDatabase.getReference("Category")
        Ref.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
               val lists= mutableListOf<CategoryModel>()
                for (chilSnapshot in snapshot.children) {
                    val list = chilSnapshot.getValue(CategoryModel::class.java)
                    if (list!= null) {
                        lists.add(list)
                    }
                }
                _category.value=lists
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}