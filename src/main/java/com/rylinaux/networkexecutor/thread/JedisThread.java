package com.rylinaux.networkexecutor.thread;

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
import com.rylinaux.networkexecutor.subscriber.NetworkExecutorSubscriber;

import java.util.logging.Level;

import redis.clients.jedis.Jedis;

/**
 * Subscribes to Jedis in a new thread.
 *
 * @author rylinaux
 */
public class JedisThread extends Thread {

    /**
     * The plugin instance.
     */
    private NetworkExecutor plugin;

    /**
     * Construct the object.
     *
     * @param plugin the plugin instance.
     */
    public JedisThread(NetworkExecutor plugin) {
        this.plugin = plugin;
    }

    /**
     * Subscribe to the proper channel.
     */
    @Override
    public void run() {

        Jedis jedis = plugin.getJedisPool().getResource();

        try {
            jedis.subscribe(new NetworkExecutorSubscriber(plugin), NetworkExecutor.CHANNEL);
            plugin.getLogger().log(Level.INFO, "Successfully subscribed to channel '" + NetworkExecutor.CHANNEL + "'.");
        } catch (Exception e) {
            plugin.getJedisPool().returnBrokenResource(jedis);
            plugin.getLogger().log(Level.WARNING, "Exception occurred when subscribing to channel '" + NetworkExecutor.CHANNEL + "'.", e);
        } finally {
            plugin.getJedisPool().returnResource(jedis);
        }

    }

}
