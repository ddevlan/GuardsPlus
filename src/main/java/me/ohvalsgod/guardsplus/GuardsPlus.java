package me.ohvalsgod.guardsplus;

import lombok.Getter;
import me.ohvalsgod.guardsplus.config.GuardsConfig;
import me.ohvalsgod.guardsplus.guard.inventory.GuardInventoryHandler;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class GuardsPlus extends JavaPlugin {

    /* Guards plugin shit */
    @Getter
    private static GuardsPlus instance;
    private GuardsConfig guardsConfig, langConfig, pastebinConfig;
    private GuardInventoryHandler guardInventoryHandler;

    @Override
    public void onEnable() {
        instance = this;

        this.guardsConfig = new GuardsConfig("config", instance);
        this.langConfig = new GuardsConfig("lang", instance);
        this.pastebinConfig = new GuardsConfig("pastebin", instance);

        this.guardInventoryHandler = new GuardInventoryHandler(instance);
    }

    @Override
    public void onDisable() {

    }

}
