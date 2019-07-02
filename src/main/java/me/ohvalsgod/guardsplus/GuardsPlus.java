package me.ohvalsgod.guardsplus;

import lombok.Getter;
import me.ohvalsgod.guardsplus.config.GuardsConfig;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class GuardsPlus extends JavaPlugin {

    @Getter
    private static GuardsPlus instance;

    private GuardsConfig guardsConfig, langConfig;

    @Override
    public void onEnable() {
        instance = this;

        this.guardsConfig = new GuardsConfig("config", instance);
        this.guardsConfig = new GuardsConfig("lang", instance);

    }

    @Override
    public void onDisable() {

    }
}
