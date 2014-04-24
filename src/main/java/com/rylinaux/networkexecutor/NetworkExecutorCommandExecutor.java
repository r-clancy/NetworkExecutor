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

import com.google.common.base.Joiner;

import com.rylinaux.networkexecutor.task.PublishTask;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class NetworkExecutorCommandExecutor implements CommandExecutor {

    /**
     * The plugin instance.
     */
    private NetworkExecutor plugin;

    /**
     * Construct the object.
     *
     * @param plugin the plugin instance.
     */
    public NetworkExecutorCommandExecutor(NetworkExecutor plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("networkexecutor.use")) {
            sender.sendMessage(NetworkExecutor.PREFIX + ChatColor.RED + "You do not have permission to do this.");
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(NetworkExecutor.PREFIX + ChatColor.RED + "You must specify a command to run.");
            return true;
        }

        String cmd = Joiner.on(" ").join(args);

        if (cmd.startsWith("/"))
            cmd = cmd.substring(1);

        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new PublishTask(plugin, cmd));
        sender.sendMessage(NetworkExecutor.PREFIX + "Command '/" + cmd + "' has been sent.");

        return true;

    }

}
