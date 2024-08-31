package dev.bingley.cards

import java.util.UUID

class CardManager {
    private val playerCards = mutableMapOf<UUID, MutableList<Card>>()

    fun addCard(player: UUID, cardId: String) {
        val card = CardRegistry.getCard(cardId) ?: return
        playerCards.computeIfAbsent(player) { mutableListOf() }.add(card)
    }

    fun getCards(player: UUID): List<Card> {
        return playerCards[player] ?: emptyList()
    }

    fun hasCard(player: UUID, cardId: String): Boolean {
        val card = CardRegistry.getCard(cardId) ?: return false
        return playerCards[player]?.contains(card) ?: false
    }

    fun removeCard(player: UUID, cardId: String) {
        val card = CardRegistry.getCard(cardId) ?: return
        playerCards.computeIfAbsent(player) { mutableListOf() }.remove(card)
    }
}