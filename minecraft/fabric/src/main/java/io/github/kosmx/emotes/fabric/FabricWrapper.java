package io.github.kosmx.emotes.fabric;

import io.github.kosmx.emotes.arch.ServerCommands;
import io.github.kosmx.emotes.common.CommonData;
import io.github.kosmx.emotes.executor.EmoteInstance;
import io.github.kosmx.emotes.fabric.executor.FabricEmotesMain;
import io.github.kosmx.emotes.fabric.network.EmoteCustomPayload;
import io.github.kosmx.emotes.fabric.network.ServerNetwork;
import io.github.kosmx.emotes.main.MainLoader;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.logging.Level;

public class FabricWrapper implements ModInitializer {

    public static final Logger logger = LoggerFactory.getLogger(CommonData.MOD_NAME);
    public static MinecraftServer SERVER_INSTANCE;

    @Override
    public void onInitialize() {
        EmoteInstance.instance = new FabricEmotesMain();
        MainLoader.main(null);
        setupFabric(); //Init keyBinding, networking etc...
    }

    private static void setupFabric(){
        PayloadTypeRegistry.playC2S().register(EmoteCustomPayload.TYPE, EmoteCustomPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(EmoteCustomPayload.TYPE, EmoteCustomPayload.CODEC);
        if(FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            ClientInit.initClient();
        }

        ServerNetwork.instance.init();
        subscribeEvents();
    }


    public static void log(Level level, String msg){
        if (level.intValue() <= Level.INFO.intValue()) {
            logger.debug(msg);
        } else if (level.intValue() <= Level.WARNING.intValue()) {
            logger.warn(msg);
        } else {
            logger.error(msg);
        }
    }

    public static void log(Level level, String msg, Throwable e){
        if (level.intValue() <= Level.INFO.intValue()) {
            logger.debug(msg, e);
        } else if (level.intValue() <= Level.WARNING.intValue()) {
            logger.warn(msg, e);
        } else {
            logger.error(msg, e);
        }
    }

    private static void subscribeEvents() {
        ServerWorldEvents.LOAD.register((server, world) -> {
            SERVER_INSTANCE = server; //keep it for later use
        });

        CommandRegistrationCallback.EVENT.register(ServerCommands::register);
    }
}
