package io.github.nurjavier8789.lateNightSMPPlugin2612.commandList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import io.github.nurjavier8789.lateNightSMPPlugin2612.model.chatModels;

public class ChatCommand implements CommandExecutor, TabCompleter {
    private final chatModels chatModels;

    public ChatCommand(chatModels chatModels) {
        this.chatModels = chatModels;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§7[§bLate Night SMP Plugin§7] §fCommand ini hanya untuk pemain!");
            return true;
        }
        
        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("chat")) {
            if (args.length == 0) {
                player.sendMessage("§eChannel aktif saat ini: §a" + chatModels.getChannel(player.getUniqueId()).toUpperCase());
                if (player.hasPermission("latenightsmpplugin.moderator.chat")) {
                    player.sendMessage("§7Gunakan: /chat <global|mod> [pesan]");
                } else {
                    player.sendMessage("§7Gunakan: /chat <global> [pesan]");
                }
                return true;
            }

            String targetChannel = args[0].toLowerCase();

            if (!targetChannel.equals("global") && !targetChannel.equals("mod")) {
                if (player.hasPermission("latenightsmpplugin.moderator.chat")) {
                    player.sendMessage("§cChannel tidak valid! §7Channel tersedia: §fGLOBAL, MOD");
                } else {
                    player.sendMessage("§cChannel tidak valid!");
                }
                return true;
            }

            if (targetChannel.equals("mod") && !player.hasPermission("latenightsmpplugin.moderator.chat")) {
                player.sendMessage("§cKamu tidak memiliki izin untuk masuk ke channel MOD!");
                return true;
            }

            if (args.length > 1) {
                String[] pesanArgs = new String[args.length - 1];
                System.arraycopy(args, 1, pesanArgs, 0, args.length - 1);
                
                sendMsgInstant(player, targetChannel, pesanArgs);
                return true;
            }

            chatModels.setChannel(player.getUniqueId(), targetChannel);
            player.sendMessage("§aBerhasil pindah ke channel: §e" + targetChannel.toUpperCase());
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();
        List<String> commands = new ArrayList<>();

        if (command.getName().equalsIgnoreCase("chat")) {
            if (args.length == 1) {
                commands.add("GLOBAL");

                if (sender.hasPermission("latenightsmpplugin.moderator.chat")) {
                    commands.add("MOD");
                }
                
                StringUtil.copyPartialMatches(args[0], commands, completions);
            } 
            else {
                return Collections.emptyList();
            }
        }

        Collections.sort(completions);
        return completions;
    }

    private void sendMsgInstant(Player player, String targetChannel, String[] args) {
        StringBuilder msg = new StringBuilder();
        for (String kata : args) {
            msg.append(kata).append(" ");
        }
        
        String oldChannel = chatModels.getChannel(player.getUniqueId());
        
        chatModels.setChannel(player.getUniqueId(), targetChannel);
        player.chat(msg.toString().trim());
        
        chatModels.setChannel(player.getUniqueId(), oldChannel); 
    }
}
