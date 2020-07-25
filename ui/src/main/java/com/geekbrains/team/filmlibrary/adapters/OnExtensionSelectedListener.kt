package com.geekbrains.team.filmlibrary.adapters

import com.geekbrains.team.domain.base.UseCase
import com.geekbrains.team.domain.base.UseCaseAbs
import com.geekbrains.team.domain.base.model.Param

interface OnExtensionSelectedListener {
    fun onExtensionSelected(useCase: UseCaseAbs, param: Param)
}