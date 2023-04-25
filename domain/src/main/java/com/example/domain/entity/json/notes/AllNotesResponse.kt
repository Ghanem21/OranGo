package com.example.domain.entity.json.notes


data class AllNotesResponse (
    val notes: List<Note>,
    val msg: String,
    val status: Boolean
)