package com.rylinaux.networkexecutor.task;

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

import com.rylinaux.networkexecutor.NetworkExecutor;

import java.util.logging.Level;

import redis.clients.jedis.Jedis;

/**
 * Ran when a command is to be sent to other servers.
 *
 * @author rylinaux
 */
public class PublishTask implements Runnable {

    /**
     * The plugin instance.
     */
    private final NetworkExecutor plugin;

    /**
     * The command to send to other servers.
     */
    private final String message;

    /**
     * Construct the object.
     *
     * @param plugin the plugin instance.
     */
    public PublishTask(NetworkExecutor plugin, String message) {
        this.plugin = plugin;
        this.message = message;
    }

    /**
     * Sends a command to other servers.
     */
    @Override
    public void run() {

        Jedis jedis = plugin.getJedisPool().getResource();

        try {
            jedis.publish(NetworkExecutor.CHANNEL, message);
            plugin.getLogger().log(Level.INFO, "Sent command '/" + message + "' to all servers.");
        } catch (Exception e) {
            plugin.getJedisPool().returnBrokenResource(jedis);
            plugin.getLogger().log(Level.WARNING, "Exception occurred when publishing message '" + message + "' to channel '" + NetworkExecutor.CHANNEL + "'.", e);
        } finally {
            plugin.getJedisPool().returnResource(jedis);
        }

    }

}
