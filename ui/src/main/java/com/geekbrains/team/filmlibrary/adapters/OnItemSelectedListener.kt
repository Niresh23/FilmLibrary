package com.geekbrains.team.filmlibrary.adapters

interface OnItemSelectedListener {
    fun openMovieDetails(id: Int)
    fun showProgress()
    fun hideProgress()
    fun openSeriesDetails(id: Int)
    fun openExtensionFragment(enum: String)
    fun openExtensionFragmentWithParameter(enum: String, id: Int)
}