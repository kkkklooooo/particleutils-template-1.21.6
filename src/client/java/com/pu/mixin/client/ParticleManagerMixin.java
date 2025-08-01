package com.pu.mixin.client;

import com.google.common.collect.EvictingQueue;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.ParticleGroup;
import net.minecraft.resource.ResourceFinder;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.profiler.Profilers;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;

@Mixin(ParticleManager.class)
public class ParticleManagerMixin{
	@Shadow private static final Logger LOGGER = LogUtils.getLogger();
	@Shadow private static final ResourceFinder FINDER = ResourceFinder.json("particles");
	@Shadow private static final int MAX_PARTICLE_COUNT = 16384;
	@Shadow protected ClientWorld world;
	@Shadow private final Map<ParticleTextureSheet, Queue<Particle>> particles = Maps.newIdentityHashMap();
	@Shadow private final Queue<EmitterParticle> newEmitterParticles = Queues.newArrayDeque();
	@Shadow private final Random random = Random.create();
	@Shadow private final Int2ObjectMap<ParticleFactory<?>> factories = new Int2ObjectOpenHashMap();
	@Shadow private final Queue<Particle> newParticles = Queues.newArrayDeque();
	@Shadow private final Object2IntOpenHashMap<ParticleGroup> groupCounts = new Object2IntOpenHashMap();
	@Shadow private void tickParticles(Collection<Particle> particles) {
		if (!particles.isEmpty()) {
			Iterator<Particle> iterator = particles.iterator();

			while(iterator.hasNext()) {
				Particle particle = (Particle)iterator.next();
				this.tickParticle(particle);
				if (!particle.isAlive()) {
					particle.getGroup().ifPresent((group) -> this.addTo(group, -1));
					iterator.remove();
				}
			}
		}

	}
	@Shadow private void tickParticle(Particle particle) {
		try {
			particle.tick();
		} catch (Throwable throwable) {
			CrashReport crashReport = CrashReport.create(throwable, "Ticking Particle");
			CrashReportSection crashReportSection = crashReport.addElement("Particle being ticked");
			Objects.requireNonNull(particle);
			crashReportSection.add("Particle", particle::toString);
			ParticleTextureSheet var10002 = particle.getType();
			Objects.requireNonNull(var10002);
			crashReportSection.add("Particle Type", var10002::toString);
			throw new CrashException(crashReport);
		}
	}
	@Shadow  private void addTo(ParticleGroup group, int count) {
		this.groupCounts.addTo(group, count);}
	/**
	 * @author
	 * @reason
	 */
	@Overwrite
    public void tick()
	{
		this.particles.forEach((sheet, queue) -> {
			Profilers.get().push(sheet.toString());
			this.tickParticles(queue);
			Profilers.get().pop();
		});
		if (!this.newEmitterParticles.isEmpty()) {
			List<EmitterParticle> list = Lists.newArrayList();

			for(EmitterParticle emitterParticle : this.newEmitterParticles) {
				emitterParticle.tick();
				if (!emitterParticle.isAlive()) {
					list.add(emitterParticle);
				}
			}

			this.newEmitterParticles.removeAll(list);
		}

		Particle particle;
		if (!this.newParticles.isEmpty()) {
			while((particle = (Particle)this.newParticles.poll()) != null) {
				((Queue)this.particles.computeIfAbsent(particle.getType(), (sheet) -> EvictingQueue.create(100000))).add(particle);
			}
		}
	}



}
