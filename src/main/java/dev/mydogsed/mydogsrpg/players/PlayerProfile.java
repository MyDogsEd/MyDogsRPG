package dev.mydogsed.mydogsrpg.players;

// Represents a player in the RPG game. A player is created in the PlayerRegistry when a discord user
// uses the /start command to start a profile for themselves in the bot.
public class PlayerProfile {

    private int coins;


    public int getCoins() {
        return this.coins;
    }

    public void addCoins(int coins){
        this.coins += coins;
    }

    public void removeCoins (int coins){
        this.coins -= coins;
    }

    public PlayerProfile() {
        this.coins = 0;
    }
}
