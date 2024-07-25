package io.github.kosmx.emotes.bungee.executor;

import io.github.kosmx.emotes.bungee.BungeeWrapper;
import io.github.kosmx.emotes.executor.EmoteInstance;
import io.github.kosmx.emotes.executor.Logger;

import java.nio.file.Path;
import java.nio.file.Paths;

public class BungeeInstance extends EmoteInstance {
    final java.util.logging.Logger logger;
    final BungeeWrapper plugin;

    public BungeeInstance(BungeeWrapper plugin) {
        this.logger = plugin.getLogger();
        this.plugin = plugin;
    }

    @Override
    public Logger getLogger() {
        return (Logger) this.logger;
    }


    @Override
    public boolean isClient() {
        return false;
    }

    @Override
    public Path getGameDirectory() {
        return Paths.get("");
    }
}