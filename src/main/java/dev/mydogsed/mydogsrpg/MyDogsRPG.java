package dev.mydogsed.mydogsrpg;

import dev.mydogsed.mydogsrpg.commands.PlayerInfoCommands;
import dev.mydogsed.mydogsrpg.commands.SimpleSlashCommand;
import dev.mydogsed.mydogsrpg.commands.framework.RegistrySlashCommandListener;
import dev.mydogsed.mydogsrpg.commands.framework.CommandRegistry;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.exceptions.InvalidTokenException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.Scanner;

public class MyDogsRPG extends ListenerAdapter {

    public static JDA jda;

    public static Logger logger = LoggerFactory.getLogger(MyDogsRPG.class);

    public static CommandRegistry commandRegistry = CommandRegistry.getInstance();

    public static void main(String[] args) {
        // Log the bot in
        try {
            jda = JDABuilder.createDefault(getApiKey())
                    .addEventListeners(new MyDogsRPG())
                    .build();
        } catch (FileNotFoundException e) {
            logger.error("API Key file not found.");
            logger.error("You must create the BOT_KEY.apikey file in the same directory as the .jar file.");
            logger.error("(Checking in {} for key file)", Paths.get("").toAbsolutePath());
        } catch (InvalidTokenException e) {
            logger.error("The provided token is invalid.");
        } catch (IllegalArgumentException e){
            logger.error("One of the provided arguments is invalid.");
        }
        // From this point on, all code will be handled via events.
    }

    // Register all commands and things after the bot is logged in
    @Override
    public void onReady(@NotNull ReadyEvent event) {
        registerSlashCommands();
        registerCommandExecutors();
        registerListeners();

        logger.info("MyDogsRPG is ready!");
    }

    // Utility method to get the API key from the file present in the same directory
    public static String getApiKey() throws FileNotFoundException {
        File file = new File("./BOT_KEY.apikey");
        Scanner scanner = new Scanner(file);
        return scanner.next();
    }

    // Register the slash commands to discord
    public static void registerSlashCommands(){
        MyDogsRPG.jda.updateCommands()
                .addCommands(
                        Commands.slash("hello", "Says hello!")
                                .setGuildOnly(true),
                        Commands.slash("profile", "Shows the current profile")
                                .setGuildOnly(true)
                ).queue();
        logger.info("Registered Slash Commands");
    }

    // Register the command Executors so the commands actually do something lmao
    public static void registerCommandExecutors(){
        commandRegistry.register("hello", new SimpleSlashCommand("Hello!"));
        commandRegistry.registerMethods(PlayerInfoCommands.class);
        logger.info("Registered Command Executors");
    }

    public static void registerListeners(){
        MyDogsRPG.jda.addEventListener(new RegistrySlashCommandListener());
        logger.info("Registered Event Listeners");
    }
}
