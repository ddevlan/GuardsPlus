package me.ohvalsgod.guardsplus;

import com.besaba.revonline.pastebinapi.Pastebin;
import com.besaba.revonline.pastebinapi.impl.factory.PastebinFactory;
import com.besaba.revonline.pastebinapi.response.Response;
import lombok.AccessLevel;
import lombok.Getter;
import me.ohvalsgod.guardsplus.command.TemporaryItemstackSerializerCommand;
import me.ohvalsgod.guardsplus.config.GuardsConfig;
import me.ohvalsgod.guardsplus.guard.inventory.GuardInventoryHandler;
import org.apache.commons.lang.StringUtils;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class GuardsPlus extends JavaPlugin {

    /* Guards plugin shit */
    @Getter
    private static GuardsPlus instance;
    private GuardsConfig guardsConfig, langConfig, pastebinConfig;
    private GuardInventoryHandler guardInventoryHandler;

    /* Pastebin shit */
    //  TODO make a wrapper for this, should be much easier to setup.
    @Getter(AccessLevel.NONE)
    private PastebinFactory pastebinFactory;
    private Pastebin pastebinAPI;
    private String userKey;

    @Override
    public void onEnable() {
        instance = this;

        this.guardsConfig = new GuardsConfig("config", instance);
        this.langConfig = new GuardsConfig("lang", instance);
        this.pastebinConfig = new GuardsConfig("pastebin", instance);

        this.guardInventoryHandler = new GuardInventoryHandler(instance);

        if (init_pastebin()) {
            getCommand("debugserialize").setExecutor(new TemporaryItemstackSerializerCommand());
        }
    }

    @Override
    public void onDisable() {

    }

    private boolean init_pastebin() {
        this.pastebinFactory = new PastebinFactory();
        this.pastebinAPI = pastebinFactory.createPastebin(pastebinConfig.getString("dev_key"));

        Response<String> loginResponse = pastebinAPI.login(pastebinConfig.getString("username"), pastebinConfig.getString("password"));

        if (loginResponse.hasError()) {
            System.out.println(StringUtils.repeat("#", 64));
            System.out.println("# Could not login to pastebin, some commands will be disabled. #");
            System.out.println(StringUtils.repeat("#", 64));
            return false;
        }
        this.userKey = loginResponse.get();
        return true;
    }

}
