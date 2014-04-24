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

/**
 * Ran when a command needs to be run on the server.
 *
 * @author rylinaux
 */
public class DispatchTask implements Runnable {

    /**
     * The plugin instance.
     */
    private final NetworkExecutor plugin;

    /**
     * The command to run.
     */
    private final String message;

    /**
     * Construct the object.
     *
     * @param message the command to run.
     */
    public DispatchTask(NetworkExecutor plugin, String message) {
        this.plugin = plugin;
        this.message = message;
    }

    /**
     * Dispatches a command to the server.
     */
    @Override
    public void run() {
        plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), message);
        plugin.getLogger().log(Level.INFO, "Command '/" + message + "' has been received.");
    }

}
