package com.pu;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.ColumnPosArgumentType;
import net.minecraft.command.argument.PosArgument;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.math.ColumnPos;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

public class ParticleUtils implements ModInitializer {
	public static final String MOD_ID = "particleutils";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");


		CommandRegistrationCallback.EVENT.register((d,r,e)->{
			d.register(CommandManager.literal("line")

							.then(CommandManager.argument("StartPosX", FloatArgumentType.floatArg())
					.then(CommandManager.argument("StartPosY", FloatArgumentType.floatArg())
					.then(CommandManager.argument("StartPosZ", FloatArgumentType.floatArg())
							.then(CommandManager.argument("Length",FloatArgumentType.floatArg())
					.executes(ctx->{

						float x = FloatArgumentType.getFloat(ctx,"StartPosX");
						float y = FloatArgumentType.getFloat(ctx,"StartPosY");
						float z = FloatArgumentType.getFloat(ctx,"StartPosZ");
						float l = FloatArgumentType.getFloat(ctx,"Length");
						ctx.getSource().getDispatcher().execute("tp %s %s %s".formatted(x,y,z),ctx.getSource());
						String c = DrawLine(100,100,100,0,0,0,1000);
						c+=DrawCircle(0,0,0,5,1000);
						ParticleUtils.LOGGER.warn(c);
						ExecuteMultiLineAsync(c,ctx.getSource(),2);

				ctx.getSource().sendFeedback(()-> Text.literal("ok"),false);
				return 1;
			}))))));
		});





	}
	public static String DrawLine(float x,float y, float z,float x1,float y1,float z1,int num){
		String res="";
		Vector3 start = new Vector3(x,y,z);
		Vector3 end = new Vector3(x1,y1,z1);
		Vector3 delta =Vector3.div(Vector3.sub(end,start),num);
		for(int i=0;i<num;i++){
			Vector3 pos = Vector3.add(start,Vector3.mul(delta,i));
			res+="particle minecraft:end_rod %s %s %s 0 0 0 0 1 force\n".formatted(pos.x,pos.y,pos.z);
		}
		return res;


	}

	public static String DrawCircle(float x,float y, float z,float r,int num,Vector3 axis ,float degree){
		String res="";
		double angle = 8*Math.PI/num;
		if(r==0){
			return "particle minecraft:end_rod %s %s %s 0 0 0 0 1 force\n".formatted(x,y,z);
		}
		for(int i=0;i<num/4;i++){
			double a = angle*i;
			float x1 = (float) (x+r*Math.cos(a));
			float z1 = (float) (z+r*Math.sin(a));
			res+="particle minecraft:end_rod %s %s %s 0 0 0 0 1 force\n".formatted(x1,y,z1);
			res+="particle minecraft:end_rod %s %s %s 0 0 0 0 1 force\n".formatted(-x1,y,z1);
			res+="particle minecraft:end_rod %s %s %s 0 0 0 0 1 force\n".formatted(-x1,y,-z1);
			res+="particle minecraft:end_rod %s %s %s 0 0 0 0 1 force\n".formatted(x1,y,-z1);

		}
		return res;
	}

	public static String DrawBall(float x,float y, float z,float r,int num){
		String res="";
		float height = y-r;
		float delta = r*2/num;
		for(int i=0;i<num;i++){

		}

	}


	public static void ExecuteMultiLine(String str, ServerCommandSource source){
		String[] lines = str.split("\n");
		for(String line:lines){
			try {
				source.getDispatcher().execute(line, source);
			}catch (Exception e){
				ParticleUtils.LOGGER.warn("Failed to execute line: "+line);
				throw new RuntimeException(e);
			}
		}
	}

	public static void ExecuteMultiLineAsync(String str, ServerCommandSource source, long delayMs) {
		String[] lines = str.split("\n");

		// 使用CompletableFuture链式执行
		CompletableFuture<Void> future = CompletableFuture.completedFuture(null);

		for (int i = 0; i < lines.length; i++) {
			final String line = lines[i];
			final int index = i;

			future = future.thenCompose(v -> {
				// 延迟执行（除了第一行）
				CompletableFuture<Void> delayFuture = CompletableFuture.runAsync(() -> {
					if (index > 0) {
						try {
							Thread.sleep(delayMs);
						} catch (InterruptedException e) {
							Thread.currentThread().interrupt();
							throw new RuntimeException(e);
						}
					}
				});

				return delayFuture.thenRunAsync(() -> {
					try {
						source.getDispatcher().execute(line, source);
					} catch (Exception e) {
						ParticleUtils.LOGGER.warn("Failed to execute line: " + line);
						throw new RuntimeException(e);
					}
				}, source.getServer());
			});
		}

		// 处理最终的异常
		future.exceptionally(throwable -> {
			ParticleUtils.LOGGER.error("Error in async command execution", throwable);
			return null;
		});
	}



}