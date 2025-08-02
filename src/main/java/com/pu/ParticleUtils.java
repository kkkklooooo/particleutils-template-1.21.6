package com.pu;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.ColumnPosArgumentType;
import net.minecraft.command.argument.PosArgument;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.math.ColumnPos;
import net.minecraft.util.math.Vec3d;
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




        CommandRegistrationCallback.EVENT.register((d, r, e) -> {
            d.register(CommandManager.literal("draw")
                    .then(CommandManager.literal("line")

                            .then(CommandManager.argument("StartPosX", FloatArgumentType.floatArg())
                                    .then(CommandManager.argument("StartPosY", FloatArgumentType.floatArg())
                                            .then(CommandManager.argument("StartPosZ", FloatArgumentType.floatArg())
                                                    .then(CommandManager.argument("EndX", FloatArgumentType.floatArg())
                                                            .then(CommandManager.argument("EndY", FloatArgumentType.floatArg())
                                                                    .then(CommandManager.argument("EndZ", FloatArgumentType.floatArg())
                                                                            .then(CommandManager.argument("num", IntegerArgumentType.integer())
                                                                                    .then(CommandManager.argument("type", StringArgumentType.string())
                                                                                    .executes(ctx -> {

                                                                                        float x = FloatArgumentType.getFloat(ctx, "StartPosX");
                                                                                        float y = FloatArgumentType.getFloat(ctx, "StartPosY");
                                                                                        float z = FloatArgumentType.getFloat(ctx, "StartPosZ");
                                                                                        float x1 = FloatArgumentType.getFloat(ctx, "EndX");
                                                                                        float y1 = FloatArgumentType.getFloat(ctx, "EndY");
                                                                                        float z1 = FloatArgumentType.getFloat(ctx, "EndZ");
                                                                                        int num = IntegerArgumentType.getInteger(ctx, "num");
                                                                                        String s = StringArgumentType.getString(ctx, "type");

                                                                                        String c = DrawLine(x, y, z, x1, y1, z1, num, s);
                                                                                        ExecuteMultiLineAsync(c, ctx.getSource(), 2);

                                                                                        ctx.getSource().sendFeedback(() -> Text.literal("ok"), false);
                                                                                        return 1;
                                                                                    }))))))))))
                                            .then(CommandManager.literal("circle")
                                                    .then(CommandManager.argument("x", FloatArgumentType.floatArg())
                                                            .then(CommandManager.argument("y", FloatArgumentType.floatArg())
                                                                    .then(CommandManager.argument("z", FloatArgumentType.floatArg())
                                                                            .then(CommandManager.argument("r", FloatArgumentType.floatArg())
                                                                                    .then(CommandManager.argument("axisx", FloatArgumentType.floatArg())
                                                                                            .then(CommandManager.argument("axisy", FloatArgumentType.floatArg())
                                                                                                    .then(CommandManager.argument("axisz", FloatArgumentType.floatArg())
                                                                                                            .then(CommandManager.argument("num", IntegerArgumentType.integer())
                                                                                                                    .then(CommandManager.argument("type", StringArgumentType.string())
                                                                                                                            .executes(ctx ->
                                                                                                                            {
                                                                                                                                float x = FloatArgumentType.getFloat(ctx, "x");
                                                                                                                                float y = FloatArgumentType.getFloat(ctx, "y");
                                                                                                                                float z = FloatArgumentType.getFloat(ctx, "z");
                                                                                                                                float radius = FloatArgumentType.getFloat(ctx, "r");
                                                                                                                                Vector3 vector3 = new Vector3(FloatArgumentType.getFloat(ctx, "axisx"), FloatArgumentType.getFloat(ctx, "axisy"), FloatArgumentType.getFloat(ctx, "axisz"));
                                                                                                                                int num = IntegerArgumentType.getInteger(ctx, "num");
                                                                                                                                String s = StringArgumentType.getString(ctx, "type");
																																String c=DrawCircle(x,y,z,radius,num,vector3,s);
																																//String c = DrawBall(x, y, z, radius, num,s);
																																ExecuteMultiLineAsync(c, ctx.getSource(), 2);

																																ctx.getSource().sendFeedback(() -> Text.literal("ok"), false);
																																return 1;
                                                                                                                            })
																													)
																											)
																									)
																							)
																					)
																			)
																	)
															)
													)
											).then(
							CommandManager.literal("rings")
									.then(CommandManager.argument("x",FloatArgumentType.floatArg())
									.then(CommandManager.argument("y",FloatArgumentType.floatArg())
											.then(CommandManager.argument("z",FloatArgumentType.floatArg())
											.then(CommandManager.argument("rMax",FloatArgumentType.floatArg())
															.then(CommandManager.argument("rMin",FloatArgumentType.floatArg())
																	.then(CommandManager.argument("axisx",FloatArgumentType.floatArg())
																			.then(CommandManager.argument("axisy",FloatArgumentType.floatArg())
																					.then(CommandManager.argument("axisz",FloatArgumentType.floatArg())
																							.then(CommandManager.argument("numRings",IntegerArgumentType.integer())
																									.then(CommandManager.argument("circleDivNum",IntegerArgumentType.integer())
																											.then(CommandManager.argument("type",StringArgumentType.string())
																													.executes(ctx -> {
																														float x = FloatArgumentType.getFloat(ctx, "x");
																														float y = FloatArgumentType.getFloat(ctx, "y");
																														float z = FloatArgumentType.getFloat(ctx, "z");
																														float rMax = FloatArgumentType.getFloat(ctx, "rMax");
																														float rMin = FloatArgumentType.getFloat(ctx, "rMin");
																														Vector3 vector3 = new Vector3(FloatArgumentType.getFloat(ctx, "axisx"), FloatArgumentType.getFloat(ctx, "axisy"), FloatArgumentType.getFloat(ctx, "axisz"));
																														int numRings = IntegerArgumentType.getInteger(ctx, "numRings");
																														int circleDivNum = IntegerArgumentType.getInteger(ctx, "circleDivNum");
																														String s = StringArgumentType.getString(ctx, "type");

																														String c=DrawRings(x,y,z,rMax,rMin,vector3,numRings,circleDivNum,s);
																														ExecuteMultiLineAsync(c, ctx.getSource(), 2);

																														ctx.getSource().sendFeedback(() -> Text.literal("ok"), false);
																														return 1;
																													})
																											)
																									)
																					)
																			)
																		)
																)
															)
													))
									)
					)

					)		);


        });
        }
	public static String DrawLine(float x,float y, float z,float x1,float y1,float z1,int num,String type){
		String res="";
		Vector3 start = new Vector3(x,y,z);
		Vector3 end = new Vector3(x1,y1,z1);
		Vector3 delta =Vector3.div(Vector3.sub(end,start),num);
		for(int i=0;i<num;i++){
			Vector3 pos = Vector3.add(start,Vector3.mul(delta,i));
			res+="particle "+type.trim()+" %s %s %s 0 0 0 0 1 force\n".formatted(pos.x,pos.y,pos.z);
		}
		return res;


	}

	public static String DrawCircle(float x,float y, float z,float r,int num,Vector3 N,String type){
		String res="";
		Vector3 vv;

		if(N.z==0&&N.y==0){
			vv = new Vector3(0,1,0);
		}else {
			vv = new Vector3(0, N.z, N.y);
		}
		//vv=N;
		float delta = (float) (2*Math.PI/num);

		vv=vv.Normalize();

		vv=vv.Mul(r);
		ParticleUtils.LOGGER.warn("dir:%s,axis:%s".formatted(vv,N));
		for (int i=0;i<num;i++){
			Vector3 v = Vector3.rotate(vv,N.Normalize(),delta*i);

			res+="particle minecraft:%s %s %s %s 0 0 0 0 1 force\n".formatted(type.trim(),0.01+x+v.x,0.01+y+v.y,0.01+z+v.z);
		}
		return res;

	/*
		double angle = 8*Math.PI/num;
		if(r==0){
			return "particle minecraft:end_rod %s %s %s 0 0 0 0 1 force\n".formatted(x,y,z);
		}
		for(int i=0;i<num/4;i++){
			double a = angle*i;
			Vector3 v= new Vector3((float) (x+r*Math.cos(a)),0,(float) (z+r*Math.sin(a)));
			v=Vector3.rotate(v,axis,rad);
			float x1 = v.x;
			float z1 = v.z;
			res+="particle minecraft:end_rod %s %s %s 0 0 0 0 1 force\n".formatted(x1,y,z1);
			res+="particle minecraft:end_rod %s %s %s 0 0 0 0 1 force\n".formatted(-x1,y,z1);
			res+="particle minecraft:end_rod %s %s %s 0 0 0 0 1 force\n".formatted(-x1,y,-z1);
			res+="particle minecraft:end_rod %s %s %s 0 0 0 0 1 force\n".formatted(x1,y,-z1);

		}
		return res;*/
	}

	public static String DrawBall(float x,float y, float z,float r,int num,String type){
		String res="";
		float height = y-r;
		float delta = r*2/num;

		for(int i=0;i<num;i++){
			float radius = (float) Math.sqrt(r*r-(-r+delta*i)*(-r+delta*i));
			res+=DrawCircle(x,height+delta*i,z,radius,num,new Vector3(0,1,0),type);
		}
		return res;
	}

	public static String DrawRings(float x, float y,float z,float rMax,float rMin,Vector3 N,int numRings,int circleDivNum,String type){
		String res="";
		float delta = (rMax-rMin)/numRings;
		for(int i=0;i<numRings;i++){
			float r = rMin+delta*i;
			res+=DrawCircle(x,y,z,r, (int) (circleDivNum+20*delta*i),N,type);
		}
		return res;
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