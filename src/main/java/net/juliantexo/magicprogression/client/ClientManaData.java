package net.juliantexo.magicprogression.client;

import static net.juliantexo.magicprogression.util.MathHelper.mapNumber;

public class ClientManaData {
    private static int playerMana;

    public static int getPlayerMana() {
        return playerMana;
    }

    public static void setPlayerMana(int playerMana) {
        ClientManaData.playerMana = playerMana;
    }

    public static int getScaledManaForGui(){
        int mana = playerMana;
        int maxMana = 200;
        int manaBarSize = 182;

        return maxMana != 0 && mana != 0 ? (int)mapNumber(mana, 0, maxMana, 0, manaBarSize) : 0;
    }
}
