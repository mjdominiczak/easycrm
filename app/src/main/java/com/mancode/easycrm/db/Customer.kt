package com.mancode.easycrm.db

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

@Entity
data class Customer(
    @Embedded
    val raw: CustomerRaw,
    @Relation(
        parentColumn = "customerId",
        entityColumn = "customerId"
    )
    val address: Address,
    @Relation(
        parentColumn = "customerId",
        entityColumn = "customerId"
    )
    val contacts: List<Contact>,
    @Relation(
        parentColumn = "customerId",
        entityColumn = "customerId"
    )
    val notes: List<Note>
)
