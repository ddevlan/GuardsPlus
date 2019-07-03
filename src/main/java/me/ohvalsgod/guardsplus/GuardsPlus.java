package me.ohvalsgod.guardsplus;

import com.besaba.revonline.pastebinapi.Pastebin;
import com.besaba.revonline.pastebinapi.impl.PastebinImpl;
import lombok.Getter;
import me.ohvalsgod.guardsplus.command.TemporaryItemstackSerializerCommand;
import me.ohvalsgod.guardsplus.config.GuardsConfig;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class GuardsPlus extends JavaPlugin {

    @Getter
    private static GuardsPlus instance;

    private GuardsConfig guardsConfig, langConfig;
    private Pastebin pastebinAPI;

    @Override
    public void onEnable() {
        instance = this;

        this.pastebinAPI = new PastebinImpl("-- dont steal my dev key :( --");

        this.guardsConfig = new GuardsConfig("config", instance);
        this.langConfig = new GuardsConfig("lang", instance);

        getCommand("debugserialize").setExecutor(new TemporaryItemstackSerializerCommand());
    }

    @Override
    public void onDisable() {

    }
}
