package dev.mydogsed.mydogsrpg.players;

import net.dv8tion.jda.api.entities.UserSnowflake;

import java.util.HashMap;
import java.util.Map;

public class PlayerProfilesRegistry {

    // Static field to hold the instance of the registry
    private static PlayerProfilesRegistry instance;

    // The Map actually used to register PlayerProfile instances
    private final Map<UserSnowflake, PlayerProfile> map;

    // Getter to return a reference to the instance
    public static PlayerProfilesRegistry getInstance() {
        if (instance == null){
            instance = new PlayerProfilesRegistry();
        }
        return instance;
    }

    private PlayerProfilesRegistry() {
        map = new HashMap<>();
    }

    public void register(UserSnowflake user, PlayerProfile profile) {
        map.put(user, profile);
    }

    public PlayerProfile getProfile(UserSnowflake user) {
        return map.get(user);
    }

    public boolean containsProfile(UserSnowflake user) {
        return map.containsKey(user);
    }

}
