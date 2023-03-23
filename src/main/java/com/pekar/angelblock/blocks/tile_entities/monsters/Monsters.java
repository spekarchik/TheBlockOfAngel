package com.pekar.angelblock.blocks.tile_entities.monsters;

public class Monsters
{
    private static byte id = 0;

    public static IMonster Blaze = new Blaze(id++);
    public static IMonster Creeper = new Creeper(id++);
    public static IMonster ElderGuardian = new ElderGuardian(id++);
    public static IMonster EnderDragon = new EnderDragon(id++);
    public static IMonster Enderman = new Enderman(id++);
    public static IMonster Ghast = new Ghast(id++);
    public static IMonster Guardian = new Guardian(id++);
    public static IMonster Hoglin = new Hoglin(id++);
    public static IMonster MagmaCube = new MagmaCube(id++);
    public static IMonster Phantom = new Phantom(id++);
    public static IMonster Piglin = new Piglin(id++);
    public static IMonster Pillager = new Pillager(id++);
    public static IMonster Shulker = new Shulker(id++);
    public static IMonster Skeleton = new Skeleton(id++);
    public static IMonster Slime = new Slime(id++);
    public static IMonster Spider = new Spider(id++);
    public static IMonster Warden = new Warden(id++);
    public static IMonster Witch = new Witch(id++);
    public static IMonster Wither = new Wither(id++);
    public static IMonster WitherSkeleton = new WitherSkeleton(id++);
    public static IMonster Zombie = new Zombie(id++);
    public static IMonster ZombieVillager = new ZombieVillager(id++);
}
