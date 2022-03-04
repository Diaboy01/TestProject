package me.marvin.command;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(commandSender instanceof Player) {
            // Spieler




            commandSender.sendMessage("Hura, du bist ein Spieler!");

            if(args.length == 1) {
                if(args[0].equalsIgnoreCase("i")) {
                    Player player = (Player) commandSender;
                    Inventory inventory = Bukkit.createInventory(null, 9*6, "§9Gratis Diamanten");

                    for(int slot = 0; slot < inventory.getSize(); slot++) {
                        ItemStack itemStack = new ItemStack(Material.EMERALD, 1);
                        ItemMeta itemMeta = itemStack.getItemMeta();
                        itemMeta.setDisplayName("§bDiamant");
                        List<String> lore = new ArrayList<>();
                        lore.add("Hallo :)");
                        itemMeta.setLore(lore);
                        itemMeta.addEnchant(Enchantment.LUCK,3,true);
                        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        itemStack.setItemMeta(itemMeta);

                        inventory.setItem(slot, itemStack);
                    }

                    player.openInventory(inventory);
                }
            }
        } else {
            // Konsole

            commandSender.sendMessage("Dieser Befehl lässt sich nur als Spieler ausführen.");
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        if(args.length == 1) {
            return Lists.newArrayList("i");
        }

        return Collections.emptyList();
    }
}
