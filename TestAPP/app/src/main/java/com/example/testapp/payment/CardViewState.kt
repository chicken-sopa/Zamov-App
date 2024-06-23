package com.example.testapp.payment


sealed class CardViewState {

    object Loading : CardViewState()

    object ShowComponent : CardViewState()

    object Error : CardViewState()
}
