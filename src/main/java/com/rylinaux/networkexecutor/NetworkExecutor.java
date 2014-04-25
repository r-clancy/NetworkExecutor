package com.rylinaux.networkexecutor;

/*
 * #%L
 * NetworkExecutor
 * %%
 * Copyright (C) 2014 NetworkExecutor
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */

import com.rylinaux.networkexecutor.task.SubscribeTask;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * A simple Redis backed plugin used to execute commands across all servers.
 *
 * @author rylinaux
 */
public class NetworkExecutor extends JavaPlugin {

    /**
     * The channel to subscribe to.
     */
    public static final String CHANNEL = "NetworkExecutor";

    /**
     * The prefix of the plugin.
     */
    public static final String PREFIX = ChatColor.GRAY + "[" + ChatColor.GREEN + "NetExec" + ChatColor.GRAY + "] ";

    /**
     * The Jedis pool.
     */
    private JedisPool jedisPool;

    /**
     * Enable the plugin.
     */
    @Override
    public void onEnable() {

        this.saveDefaultConfig();

        String hostname = this.getConfig().getString("redis.hostname");
        int port = this.getConfig().getInt("redis.port");
        String password = this.getConfig().getString("redis.password");

        // Check if the given Redis needs a password.
        if (password == null || password.isEmpty()) {
            jedisPool = new JedisPool(new JedisPoolConfig(), hostname, port, 0);
        } else {
            jedisPool = new JedisPool(new JedisPoolConfig(), hostname, port, 0, password);
        }

        this.getServer().getScheduler().runTaskAsynchronously(this, new SubscribeTask(this));

        this.getCommand("networkexecutor").setExecutor(new NetworkExecutorCommandExecutor(this));

    }

    /**
     * Disable the plugin.
     */
    @Override
    public void onDisable() {
        if (jedisPool != null) {
            jedisPool.destroy();
        }
    }

    /**
     * Get the jedis pool.
     *
     * @return the jedis pool.
     */
    public JedisPool getJedisPool() {
        return jedisPool;
    }

}
