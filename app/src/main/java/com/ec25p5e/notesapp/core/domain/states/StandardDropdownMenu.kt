package com.ec25p5e.notesapp.core.domain.states

import com.ec25p5e.notesapp.core.util.Resource

data class StandardDropdownMenu(
    val text: String = "",
    val error: Error? = null,
)