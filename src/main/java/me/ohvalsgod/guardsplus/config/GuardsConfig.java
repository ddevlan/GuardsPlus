package me.ohvalsgod.guardsplus.config;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;

public class GuardsConfig {

    private File file;
    @Getter
    private FileConfiguration configuration;
    private String root;

    public GuardsConfig(String name, JavaPlugin plugin) {
        this.file = new File(plugin.getDataFolder() + "/" + name + ".yml");


        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }

        if (!file.exists()) {
            plugin.saveResource(name + ".yml", true);
        }

        this.root = "";
        this.configuration = YamlConfiguration.loadConfiguration(file);
    }

    public String getString(String path) {
        return translate(configuration.getString(this.root.isEmpty() ? path:this.root + "." + path));
    }

    public List<String> getStringList(String path) {
        return translate(configuration.getStringList(this.root.isEmpty() ? path:this.root + "." + path));
    }

    public Double getDouble(String path) {
        return configuration.getDouble(this.root.isEmpty() ? path:this.root + "." + path);
    }

    public List<Double> getDoubleList(String path) {
        return configuration.getDoubleList(this.root.isEmpty() ? path:this.root + "." + path);
    }

    public Boolean getBoolean(String path) {
        //TODO: format booleans for yes/no y/n t/f etc.
        return configuration.getBoolean(this.root.isEmpty() ? path:this.root + "." + path);
    }

    public Integer getInteger(String path) {
        return configuration.getInt(this.root.isEmpty() ? path:this.root + "." + path);
    }

    public List<Integer> getIntegerList(String path) {
        return configuration.getIntegerList(this.root.isEmpty() ? path:this.root + "." + path);
    }

    public GuardsConfig root(String root) {
        this.root = root;
        return this;
    }

    private String translate(String source) {
        return ChatColor.translateAlternateColorCodes('&', source);
    }

    private List<String> translate(List<String> source) {
        source.forEach(this::translate);
        return source;
    }

    public void reload() {
        this.configuration = YamlConfiguration.loadConfiguration(file);
    }

}
