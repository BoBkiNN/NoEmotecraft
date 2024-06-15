package io.github.kosmx.emotes.bukkit.executor;

import io.github.kosmx.emotes.bukkit.BukkitWrapper;
import io.github.kosmx.emotes.executor.EmoteInstance;
import io.github.kosmx.emotes.executor.Logger;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;

public class BukkitInstance extends EmoteInstance {
    final java.util.logging.Logger logger;
    final BukkitWrapper plugin;

    public BukkitInstance(BukkitWrapper plugin){
        this.logger = plugin.getLogger();
        this.plugin = plugin;
    }

    @Override
    public Logger getLogger() {
        return new Logger() {
            @Override
            public void writeLog(Level level, String msg) {
                logger.log(level, msg);
            }

            @Override
            public void log(Level level, String msg, Throwable exc) {
                logger.log(level, msg, exc);
            }
        };
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
