package me.ohvalsgod.guardsplus.guard.inventory;

import me.ohvalsgod.guardsplus.GuardsPlus;
import me.ohvalsgod.guardsplus.guard.Guard;
import me.ohvalsgod.guardsplus.util.InventoryUtils;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GuardInventoryHandler implements Listener {

    private GuardsPlus plus;
    private YamlConfiguration config;
    private Map<String, ItemStack[][]> permissionsToInventoryMap;

    public GuardInventoryHandler(GuardsPlus plus) {
        this.plus = plus;

        plus.getServer().getPluginManager().registerEvents(this, plus);

        File file = new File(plus.getDataFolder() + "/" + "guards.yml");

        if (!file.getParentFile().exists()) {
            if (file.getParentFile().mkdirs()) {
                this.config = YamlConfiguration.loadConfiguration(file);
            } else {
                plus.getLogger().warning("Could not create 'guards.yml'");
            }
        }

        permissionsToInventoryMap = new HashMap<>();

        //  Load guard permissions and inventories
        try {
            for (String key : plus.getGuardsConfig().getConfiguration().getConfigurationSection("guards").getKeys(false)) {
                permissionsToInventoryMap.put("guardsplus." + key, new ItemStack[][]{
                        InventoryUtils.itemStackArrayFromBase64(config.getString("guards." + key + ".inventory")),
                        InventoryUtils.itemStackArrayFromBase64(config.getString("guards." + key + ".armor"))
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getGuardPermission(Player player) {
        for (String string : permissionsToInventoryMap.keySet()) {
            if (player.hasPermission(string)) {
                return string;
            }
        }
        return "";
    }

    public ItemStack[][] getDefaultGuardInventory(Player player) {
        return permissionsToInventoryMap.get(getGuardPermission(player));
    }

    public boolean hasGuardPermissions(Player player) {
        return !getGuardPermission(player).isEmpty();
    }

    public ItemStack[][] getNextInventory(Guard guard) {
        if (config.contains(guard.getUuid().toString())) {
            try {
                return new ItemStack[][]{
                        InventoryUtils.itemStackArrayFromBase64(config.getString(guard.getUuid().toString() + ".inventory")),
                        InventoryUtils.itemStackArrayFromBase64(config.getString(guard.getUuid().toString() + ".armor"))
                };
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        //  Check for guard permissions
        if (hasGuardPermissions(player)) {
            Guard guard = new Guard(player.getUniqueId());

            //  Grab their next inventory from config
            guard.setNextInventory(getNextInventory(guard));

            if (guard.getNextInventory() == null) {
                //  If they dont have an inventory saved in config (i.e. new guard), grab the default one for their permission
                guard.setNextInventory(getDefaultGuardInventory(player));
            }
        }
    }

}
