package com.mancode.easycrm.db

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

@Entity
data class Customer(
    @Embedded
    val raw: CustomerRaw,
    @Relation(
        parentColumn = "id",
        entityColumn = "customerId"
    )
    val address: Address,
    @Relation(
        parentColumn = "id",
        entityColumn = "customerId"
    )
    val contacts: List<Contact>,
    @Relation(
        parentColumn = "id",
        entityColumn = "customerId"
    )
    val notes: List<Note>
)
