package io.github.nurjavier8789.lateNightSMPPlugin2612.listener;

import io.github.nurjavier8789.lateNightSMPPlugin2612.commandList.EventCommand;

import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class LNSMPPEventListener implements Listener {
    private final JavaPlugin plugin;

    public LNSMPPEventListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(org.bukkit.event.player.PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (EventCommand.isEventActive) {
            if (EventCommand.eventBossBar != null) {
                EventCommand.eventBossBar.addPlayer(player);
            }
            
            if (plugin.getConfig().getString("event.impact").equalsIgnoreCase("moon-gravity")) {
                EventCommand.aktifkanMoonGravity(player);
            }
        } else {
            EventCommand.hapusMoonGravity(player);
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return; 
        }
        
        if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
            if (EventCommand.isEventActive && plugin.getConfig().getString("event.impact").equalsIgnoreCase("moon-gravity")) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerMine(BlockBreakEvent event) {
        if (!EventCommand.isEventActive || !plugin.getConfig().getString("event.impact").equalsIgnoreCase("fortune-drop")) {
            return;
        }

        Block block = event.getBlock();
        if (
            block.getType() == Material.DIAMOND_ORE || block.getType() == Material.DEEPSLATE_DIAMOND_ORE ||
            block.getType() == Material.COAL_ORE || block.getType() == Material.DEEPSLATE_COAL_ORE ||
            block.getType() == Material.COPPER_ORE || block.getType() == Material.DEEPSLATE_COPPER_ORE ||
            block.getType() == Material.GOLD_ORE || block.getType() == Material.DEEPSLATE_GOLD_ORE ||
            block.getType() == Material.REDSTONE_ORE || block.getType() == Material.DEEPSLATE_REDSTONE_ORE ||
            block.getType() == Material.EMERALD_ORE || block.getType() == Material.DEEPSLATE_EMERALD_ORE ||
            block.getType() == Material.LAPIS_ORE || block.getType() == Material.DEEPSLATE_LAPIS_ORE ||
            block.getType() == Material.NETHER_GOLD_ORE || block.getType() == Material.NETHER_QUARTZ_ORE ||
            block.getType() == Material.IRON_ORE || block.getType() == Material.DEEPSLATE_IRON_ORE
        ) {
            ItemStack virtualPickaxe = new ItemStack(Material.DIAMOND_PICKAXE);
            virtualPickaxe.addUnsafeEnchantment(Enchantment.FORTUNE, 18);

            Collection<ItemStack> simulasiDrops = block.getDrops(virtualPickaxe);
            event.setDropItems(false);

            for (ItemStack drop : simulasiDrops) {
                block.getWorld().dropItemNaturally(block.getLocation(), drop);
            }
        }
    }

    @EventHandler
    public void onMobDeath(EntityDeathEvent event) {
        if (!EventCommand.isEventActive || !plugin.getConfig().getString("event.impact").equalsIgnoreCase("fortune-drop")) {
            return;
        }
        
        Player killer = event.getEntity().getKiller();
        if (killer == null) {
            return;
        }

        for (ItemStack drop : event.getDrops()) {
            if (drop.getMaxStackSize() > 1) {
                int ekstraDrop = ThreadLocalRandom.current().nextInt(0, 19); 
                
                drop.setAmount(drop.getAmount() + ekstraDrop);
            }
        }
    }
}
