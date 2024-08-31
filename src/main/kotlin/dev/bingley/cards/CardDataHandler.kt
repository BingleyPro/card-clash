package dev.bingley.cards

import net.minecraft.nbt.CompoundTag
import java.util.UUID

class CardDataHandler {
    fun savePlayerData(player: UUID, nbt: CompoundTag) {
        val cardList = CompoundTag()
        val playerCards = CardManager().getCards(player)
        val cardArray = playerCards.map { card ->
            val cardData = CompoundTag()
            cardData.putString("type", card.type.name)
            cardData.putString("tier", card.tier.name)
            cardData.putString("name", card.name)
            cardData.putString("enchantments", card.enchantments.joinToString(","))
            cardData
        }
        cardList.put("cards", cardArray)
        nbt.put("card_data", cardList)
    }

    fun loadPlayerData(player: ServerPlayerEntity, nbt: NbtCompound) {
        val cardList = nbt.getCompound("card_data").getList("cards", 10) // List of NbtCompound
        cardList.forEach {
            val cardData = it as NbtCompound
            val type = CardType.valueOf(cardData.getString("type"))
            val tier = CardTier.valueOf(cardData.getString("tier"))
            val name = cardData.getString("name")
            val enchantments = cardData.getString("enchantments").split(",")
            val card = Card(type, tier, name, enchantments)
            CardManager().addCard(player, card)
        }
    }
}
