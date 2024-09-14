package dev.mydogsed.mydogsrpg.commands.framework;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class CommandRegistry {

    // Static field to hold the (only) instance of the registry
    private static CommandRegistry instance;

    // The Map actually used to register PlayerProfile instances
    private final Map<String, SlashCommand> map;

    // Getter to return a reference to the instance
    public static CommandRegistry getInstance() {
        if (instance == null){
            instance = new CommandRegistry();
        }
        return instance;
    }

    private CommandRegistry() {
        map = new HashMap<>();
    }

    public void register (String name, SlashCommand executor) {
        map.put(name, executor);
    }

    /*
    Register multiple commands from a single class as command executors using an annotation shorthand
     */
    public void registerMethods(Class<?> clazz){
        for (Method method : clazz.getDeclaredMethods()) {
            if (!method.isAnnotationPresent(SlashCommandMethod.class)) {
                continue;
            }
            SlashCommandMethod methodAnnotation = method.getAnnotation(SlashCommandMethod.class);
            String name = methodAnnotation.value();
            register(name, new SlashCommand() {
                @Override
                public void onCommand(SlashCommandInteractionEvent event) {
                    try {
                        method.invoke(event);
                    }
                    catch (IllegalAccessException | InvocationTargetException e) {
                        LoggerFactory.getLogger(CommandRegistry.class).warn(e.getMessage());
                    }

                }
            });
        }

    }

    public SlashCommand getExecutor(String name) {
        return map.get(name);
    }

    public boolean containsExecutor(String name) {
        return map.containsKey(name);
    }

}

