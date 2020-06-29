package com.geekbrains.team.filmlibrary.adapters

interface OnLikeClickListener {
    fun onLikeClick(id: Int, like: Boolean)
    fun onWishClick(id: Int)
}