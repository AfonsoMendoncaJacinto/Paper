package io.papermc.paper.event.player;

import net.kyori.adventure.text.Component;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public class PlayerBedFailEnterEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final FailReason failReason;
    private final Block bed;
    private boolean willExplode;
    private @Nullable Component message;

    private boolean cancelled;

    @ApiStatus.Internal
    public PlayerBedFailEnterEvent(final Player player, final FailReason failReason, final Block bed, final boolean willExplode, final @Nullable Component message) {
        super(player);
        this.failReason = failReason;
        this.bed = bed;
        this.willExplode = willExplode;
        this.message = message;
    }

    public FailReason getFailReason() {
        return this.failReason;
    }

    public Block getBed() {
        return this.bed;
    }

    public boolean getWillExplode() {
        return this.willExplode;
    }

    public void setWillExplode(final boolean willExplode) {
        this.willExplode = willExplode;
    }

    public @Nullable Component getMessage() {
        return this.message;
    }

    public void setMessage(final @Nullable Component message) {
        this.message = message;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    /**
     * {@inheritDoc}
     * <p>
     * <b>NOTE</b>: This does not cancel the player getting in the bed, but any messages/explosions
     * that may occur because of the interaction.
     */
    @Override
    public void setCancelled(final boolean cancel) {
        this.cancelled = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    public enum FailReason {
        /**
         * The world doesn't allow sleeping (ex. Nether or The End). Entering
         * the bed is prevented and the bed explodes.
         */
        NOT_POSSIBLE_HERE,
        /**
         * Entering the bed is prevented due to it not being night nor
         * thundering currently.
         * <p>
         * If the event is forcefully allowed during daytime, the player will
         * enter the bed (and set its bed location), but might get immediately
         * thrown out again.
         */
        NOT_POSSIBLE_NOW,
        /**
         * Entering the bed is prevented due to the player being too far away.
         */
        TOO_FAR_AWAY,
        /**
         * Bed is obstructed.
         */
        OBSTRUCTED,
        /**
         * Entering the bed is prevented due to there being some other problem.
         */
        OTHER_PROBLEM,
        /**
         * Entering the bed is prevented due to there being monsters nearby.
         */
        NOT_SAFE
    }
}
