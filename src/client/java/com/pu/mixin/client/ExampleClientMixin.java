package com.pu.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.Particle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Particle.class)
public class ExampleClientMixin {
	@Shadow protected int age;

	@Inject(at = @At("TAIL"), method = "tick")
	private void init(CallbackInfo info) {
		age=0;
		// This code is injected into the start of MinecraftClient.run()V
	}
}