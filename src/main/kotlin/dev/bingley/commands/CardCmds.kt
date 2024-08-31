package dev.bingley.commands

import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.context.CommandContext
import dev.bingley.BattleManager
import dev.bingley.CardClash
import dev.bingley.cards.CardManager
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.arguments.EntityArgument
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.minecraft.commands.Commands as CommandManager

object CardCmds {
    private val cardManager = CardManager()

    fun register() {
        CommandRegistrationCallback.EVENT.register { dispatcher, _, _ ->
            dispatcher.register(
                CommandManager.literal("addcard")
                    .then(
                        CommandManager.argument("player", EntityArgument.player())
                            .then(
                                CommandManager.argument("cardId", StringArgumentType.string())
                                    .executes { context ->
                                        addCard(context)
                                    }
                            )
                    )
            )
            dispatcher.register(
                CommandManager.literal("listcards")
                    .executes { context ->
                        listCards(context)
                    }
            )
        }
    }

    private fun addCard(context: CommandContext<ServerCommandSource>): Int {
        val player = EntityArgument.getPlayer(context, "player")
        val cardId = StringArgumentType.getString(context, "cardId")

        cardManager.addCard(player.uuid, cardId)
        context.source.sendFeedback(Text.literal("Card $cardId added to ${player.name.string}"), true)

        return 1 // Success
    }

    private fun listCards(context: CommandContext<ServerCommandSource>): Int {
        val player = context.source.player
        val cards = cardManager.getCards(player)

        if (cards.isEmpty()) {
            player.sendMessage(Text.literal("You have no cards."), false)
        } else {
            val cardList = cards.joinToString { it.name }
            player.sendMessage(Text.literal("Your cards: $cardList"), false)
        }

        return 1 // Success
    }
}