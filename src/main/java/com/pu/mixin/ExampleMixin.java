package com.pu.mixin;

import com.fasterxml.jackson.core.TreeNode;
import com.google.common.collect.Sets;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

@Mixin(Entity.class)
public class ExampleMixin {
	@Shadow private final Set<String> commandTags = Sets.newHashSet();
	/**
	 * @author
	 * @reason
	 */
	@Overwrite
	public boolean addCommandTag(String tag) {
		return this.commandTags.size() >= 1024 ? false : this.commandTags.add(tag);
	}
}