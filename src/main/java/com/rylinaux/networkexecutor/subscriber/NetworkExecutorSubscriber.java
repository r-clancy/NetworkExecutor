package com.rylinaux.networkexecutor.subscriber;

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
import com.rylinaux.networkexecutor.task.DispatchTask;

import redis.clients.jedis.JedisPubSub;

/**
 * Listen for incoming commands to be executed.
 *
 * @author rylinaux
 */
public class NetworkExecutorSubscriber extends JedisPubSub {

    /**
     * The plugin instance.
     */
    private NetworkExecutor plugin;

    /**
     * Construct the object.
     *
     * @param plugin the plugin instance.
     */
    public NetworkExecutorSubscriber(NetworkExecutor plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onMessage(String channel, String message) {
        plugin.getServer().getScheduler().runTask(plugin, new DispatchTask(plugin, message));
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {
        // Nothing to do.
    }

    @Override
    public void onSubscribe(String channel, int subscribedChannels) {
        // Nothing to do.
    }

    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {
        // Nothing to do.
    }

    @Override
    public void onPUnsubscribe(String pattern, int subscribedChannels) {
        // Nothing to do.
    }

    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {
        // Nothing to do.
    }

}