/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.util.math.Vec3d
 */
package com.schnurritv.sexmod.util;

import java.util.Random;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.vector.Vector3d;

public class Reference {
    public static final String MOD_ID = "sexmod";
    public static final String NAME = "Sex Mod";
    public static final String VERSION = "1.0";
    public static final String ACCEPTED_VERSION = "[1.16.5]";
    public static final String CLIENT = "com.schnurritv.sexmod.proxy.ClientProxy";
    public static final String COMMON = "com.schnurritv.sexmod.proxy.CommonProxy";
    public static final Random RANDOM = new Random();
    public static final int ENTITY_JENNY = 177013;
    public static final int ENTITY_ELLIE = 228922;
    public static final int GUI_GIRL = 0;
    public static MinecraftServer server;

    public static Vector3d Lerp(Vector3d start, Vector3d end, double step) {
        if (step == 0.0) {
            return end;
        }
        try {
            Vector3d distance = end.subtract(start);
            return start.add(distance.x / step, distance.y / step, distance.z / step);
        }
        catch (NullPointerException e) {
            System.out.println("couldn't calculate distance @Reference.Lerp");
            System.out.println(start);
            System.out.println(end);
            System.out.println(step);
            return end;
        }
    }

    public static double Lerp(double start, double end, double step) {
        return start + (end - start) * step;
    }
}
