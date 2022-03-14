package me.marvin.api;

import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.UUID;

public class PlayerWorldTimings<staic> {

    public static final long TIME_LIMIT = 1000 * 60 * 10; // 10min

    private static HashMap<UUID, PlayerWorldTimings> cache = new HashMap<>();

    public static PlayerWorldTimings getTimings(UUID uuid) {
        if(cache.containsKey(uuid)) {
            return cache.get(uuid);
        }

        return createEntry(uuid, 0L);
    }

    public static void dispose(UUID uuid) {
        cache.remove(uuid);
    }

    public static PlayerWorldTimings createEntry(UUID uuid, long timeSpend) {
        PlayerWorldTimings playerWorldTimings = new PlayerWorldTimings(uuid, timeSpend);
        cache.put(uuid, playerWorldTimings);
        return playerWorldTimings;
    }

    private long timeSpend;
    private UUID uuid;

    private long timing;
    private boolean started;

    private PlayerWorldTimings(UUID uuid, long startTime) {
        this.uuid = uuid;
        this.timeSpend = startTime;

        this.timing = 0L;
        this.started = false;
    }

    public UUID getUniqueId() {
        return this.uuid;
    }

    public void startCounting() {
        this.started = true;
        this.timing = System.currentTimeMillis();
    }

    public void stopCounting() {
        this.started = false;
        this.timeSpend += System.currentTimeMillis() - this.timing; //TODO Original: this.timeSpend += System.currentTimeMillis() - this.timing
        this.timing = 0L;
    }

    public long getTimeSpend() {
        if(this.started) {
            Bukkit.broadcastMessage("timeSpend: "+timeSpend);
            Bukkit.broadcastMessage("this.timing: "+this.timing);
            Bukkit.broadcastMessage("System.currentTimeMillis: "+System.currentTimeMillis());
            return timeSpend -=  this.timing - System.currentTimeMillis(); //TODO Original: return timeSpend = System.currentTimeMillis() - this.timing;
        } else {
            return timeSpend;
        }
    }

    public boolean exceedsTimeLimit(long timeLimit) {
        return getTimeSpend() >= timeLimit;
    }

    public boolean isCounting() {
        return this.started;
    }

    public void appendData(long timeSpend) {
        this.timeSpend = timeSpend;
    }
}
