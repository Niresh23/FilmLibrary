package com.geekbrains.team.filmlibrary.adapters

import com.geekbrains.team.domain.base.UseCase

interface OnExtensionSelectedListener<T, S> {
    fun onExtensionSelected(id: Int, useCase: UseCase<T, S>)
}