package com.cm6123.wormhole;

import com.cm6123.wormhole.player.Player;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PlayerChecks {

    @Test
    public void shouldUpdateLocationCorrectly() {
        Player aPlayer = new Player("test player 1");
        aPlayer.setLoc(2);
        aPlayer.updateLoc(3);
        assertEquals(aPlayer.getLoc(), 5);
    }

    @Test
    public void shouldFailIfNotUpdateLocationCorrectly() {
        Player aPlayer = new Player("test player 1");
        aPlayer.setLoc(3);
        aPlayer.updateLoc(4);
        assertNotEquals(aPlayer.getLoc(), 8);
    }
}
