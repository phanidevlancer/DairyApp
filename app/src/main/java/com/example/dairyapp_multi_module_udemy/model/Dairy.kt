package com.example.dairyapp_multi_module_udemy.model

import com.example.dairyapp_multi_module_udemy.utils.toRealmInstant
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.ObjectId
import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import java.time.Instant

class Dairy : RealmObject {

    @PrimaryKey
    var _id: ObjectId = ObjectId.create()
    var ownerId: String = ""
    var title: String = ""
    var description: String = ""
    var mood: String = ""
    var images: RealmList<String> = realmListOf()
    var date: RealmInstant = Instant.now().toRealmInstant()

}