package me.ohvalsgod.guardsplus.guard;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

@Getter
public class Guard {

    private UUID uuid;
    @Setter
    private boolean onDuty;
    @Setter
    private ItemStack[][] nextInventory;

    public Guard(UUID uuid) {
        this.uuid = uuid;
    }

    private void swapInventory() {
        ItemStack[] inventoryContents = nextInventory[0];
        ItemStack[] armorContents = nextInventory[1];

        Player player = toPlayer();

        nextInventory[0] = player.getInventory().getContents();
        nextInventory[0] = player.getInventory().getArmorContents();

        player.getInventory().setContents(inventoryContents);
        player.getInventory().setArmorContents(armorContents);

        player.updateInventory();
    }

    public void toggleDuty() {
        onDuty = !onDuty;
        swapInventory();
        //TODO send broadcast
    }

    public Player toPlayer() {
        return Bukkit.getPlayer(uuid);
    }

}
