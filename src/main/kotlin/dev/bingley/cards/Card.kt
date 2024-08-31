package dev.bingley.cards

enum class CardType {
    GEAR, // For weapons and tools
    MOB, // For mobs
    BLOCKS, // For blocks,
    STRUCTURE, // For structures
    BIOME // For biomes
}

enum class CardTier {
    WOOD, STONE, IRON, GOLD, DIAMOND, OBSIDIAN, NETHERITE
}

data class Card(
    val type: CardType,
    val tier: CardTier,
    val name: String,
    val enchantments: List<String> = emptyList()
)