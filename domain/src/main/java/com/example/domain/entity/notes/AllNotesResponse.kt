package com.example.domain.entity.notes


data class AllNotesResponse (
    val notes: List<Note>,
    val msg: String,
    val status: Boolean
)