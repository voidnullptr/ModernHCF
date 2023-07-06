package dev.stackz.modernhcf.listener;

import org.bukkit.attribute.AttributeInstance;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.event.player.PlayerJoinEvent;
public class LegacyCombat implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        AttributeInstance attribute = event.getPlayer().getAttribute(Attribute.GENERIC_ATTACK_SPEED);
        if (attribute != null) {
            for (AttributeModifier modifier : attribute.getModifiers()) {
                attribute.removeModifier(modifier);
            }

            AttributeModifier modifier = new AttributeModifier("NoCooldown", 1000, AttributeModifier.Operation.ADD_NUMBER);
            attribute.addModifier(modifier);
        }
    }
}
