package dev.mydogsed.mydogsrpg.commands;

import dev.mydogsed.mydogsrpg.commands.framework.SlashCommandMethod;
import dev.mydogsed.mydogsrpg.players.PlayerProfile;
import dev.mydogsed.mydogsrpg.players.PlayerProfilesRegistry;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.requests.RestAction;

public class PlayerInfoCommands {

    // This message is sent when the user tries to execute a command for which they need a profile.
    public RestAction<Message> noProfileMessage(InteractionHook hook){
        return hook.editOriginal("You don't have a profile! Use /start to create one!");
    }

    @SlashCommandMethod("start")
    public void startCommand(SlashCommandInteractionEvent event) {
        InteractionHook hook = event.getHook();
        event.deferReply().queue();
        if (PlayerProfilesRegistry.getInstance().containsProfile(event.getUser())){
            hook.editOriginal("You already have a profile! You cannot start a new profile!").queue();
            return;
        }
        PlayerProfilesRegistry.getInstance().register(hook.getInteraction().getUser(), new PlayerProfile());
        hook.editOriginal("You have started a new profile!").queue();
    }

    @SlashCommandMethod("profile")
    public void profileCommand(SlashCommandInteractionEvent event) {
        InteractionHook hook = event.getHook();
        event.deferReply().queue();
        if (!PlayerProfilesRegistry.getInstance().containsProfile(event.getUser())){
            noProfileMessage(hook).queue();
            return;
        }
        hook.editOriginal(String.format("You have %d")).queue();
    }


}
