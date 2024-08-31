package dev.bingley.cards

object CardRegistry {
    private val cards = mutableMapOf<String, Card>()

    init {
        // Define cards
        cards["wooden_sword"] = Card(
            type = CardType.GEAR,
            tier = CardTier.WOOD,
            name = "Wooden Sword"
        )
        cards["stone_sword"] = Card(
            type = CardType.GEAR,
            tier = CardTier.STONE,
            name = "Stone Sword"
        )
    }

    fun getCard(id: String) : Card? {
        return cards[id]
    }
}