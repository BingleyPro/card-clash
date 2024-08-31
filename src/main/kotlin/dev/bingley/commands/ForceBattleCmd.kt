package dev.bingley.commands

import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.context.CommandContext
import dev.bingley.BattleManager
import dev.bingley.CardClash
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.arguments.EntityArgument
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.minecraft.commands.Commands as CommandManager

object ForceBattleCmd {
    fun register() {
        CommandRegistrationCallback.EVENT.register { dispatcher, _, _ ->
            dispatcher.register(
                CommandManager.literal("forcebattle")
                    .then(
                        CommandManager.argument("player1", EntityArgument.player())
                            .then(
                                CommandManager.argument("player2", EntityArgument.player())
                                    .then(
                                        CommandManager.argument("arena", IntegerArgumentType.integer())
                                            .executes { context ->
                                                execute(context)
                                            }
                                    )
                            )
                    )
            )
        }
    }

    private fun execute(context: CommandContext<CommandSourceStack>): Int {
        val player1 = EntityArgument.getPlayer(context, "player1")
        val player2 = EntityArgument.getPlayer(context, "player2")
        val arena = IntegerArgumentType.getInteger(context, "arena")

        val battleManager = CardClash.battleManager
        val result = battleManager.startBattle(player1.uuid, player2.uuid, arena)

        return when (result) {
            BattleManager.BattleStartResult.SUCCESS -> {
                player1.sendSystemMessage(Component.literal("You are now in a battle with ${player2.name.string} in arena $arena"))
                player2.sendSystemMessage(Component.literal("You are now in a battle with ${player1.name.string} in arena $arena"))

                context.source.sendSystemMessage(Component.literal("Player ${player1.name.string} is now in a battle with player ${player2.name.string} in arena $arena"))
                1 // Success
            }
            BattleManager.BattleStartResult.SAME_PLAYER_ERROR -> {
                context.source.sendFailure(Component.literal("Error: The same player cannot battle themself!"))
                0 // Failed
            }
            BattleManager.BattleStartResult.PLAYER_IN_BATTLE_ERROR -> {
                context.source.sendFailure(Component.literal("Error: One or both of the players are already in a battle."))
                0 // Failed
            }
        }
    }
}