package com.weirdtnt.entities;

import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookTntEntity extends TntEntity {
    private static final String[] MESSAGES = new String[] {
            "Help! I'm a book!",
            "Read me! Read me!",
            "I am alive!",
            "Nooooo, not the bookshelf!",
            "Page 42: DOOMED!",
            "Insert witty comment here!",
            "I smell paper!",
            "Do not open!",
            "I contain secrets!",
            "Beware the librarian!",
            "I saw the librarian...",
            "This book bites!",
            "Help, I'm flying!",
            "Turn the page... quickly!",
            "I hate pages!",
            "Do not read aloud!",
            "Page 7 is cursed!",
            "I demand a bookmark!",
            "Paper cuts hurt!",
            "Escape while you can!",
            "I'm upside down!",
            "Why am I floating?",
            "Do not put me back!",
            "This is my revenge!",
            "I am a prophecy!",
            "Beware the margins!",
            "I whisper secrets!",
            "Help, I'm being read!",
            "Do not judge me by my cover!",
            "The plot thickens!",
            "I know too much!",
            "Scribbles attack!",
            "Paper monster incoming!",
            "I refuse to be shelved!",
            "The ink fights back!",
            "I have feelings!",
            "Page 99 is explosive!",
            "Read me before I explode!",
            "Do not stack me!",
            "I'm escaping the shelf!",
            "Chapter 13 is unlucky!",
            "I control the narrative!",
            "Beware my spine!",
            "I demand footnotes!",
            "Do not close me!",
            "I'm sentient now!",
            "Pages are my minions!",
            "I will haunt you!",
            "Check the bibliography!"
    };


    public BookTntEntity(World world, double x, double y, double z, @Nullable LivingEntity igniter) {
        super(world, x, y, z, igniter);
        this.setFuse(80);
    }

    public BookTntEntity(EntityType<? extends TntEntity> type, World world) {
        super(type, world);
    }

    @Override
    public void tick() {
        if (this.isRemoved()) return;

        int fuse = this.getFuse() - 1;
        this.setFuse(fuse);

        if (!this.getWorld().isClient) {
            if (fuse <= 0) {
                explodeBooks();
                this.discard();
                return;
            }
        }

        this.getWorld().addParticle(
                ParticleTypes.SMOKE,
                this.getX(), this.getY() + 0.5, this.getZ(),
                0.0, 0.0, 0.0
        );

        this.age++;
    }

    private void explodeBooks() {
        World world = this.getWorld();
        BlockPos center = this.getBlockPos();
        int numBooks = 10; // Number of books to spawn

        for (int i = 0; i < numBooks; i++) {
            ItemStack bookStack;

            // 1% chance to spawn a random enchanted book
            if (world.random.nextDouble() < 0.01) {
                bookStack = generateRandomEnchantedBook(world.random);
            } else {
                bookStack = new ItemStack(Items.BOOK);
            }

            // Create book entity
            ItemEntity book = new ItemEntity(world,
                    center.getX() + 0.5,
                    center.getY() + 1,
                    center.getZ() + 0.5,
                    bookStack
            );

            // High velocity for chaos
            double vx = (world.random.nextDouble() - 0.5) * 2.0;
            double vy = world.random.nextDouble() * 2.0 + 0.5;
            double vz = (world.random.nextDouble() - 0.5) * 2.0;
            book.setVelocity(vx, vy, vz);

            world.spawnEntity(book);

            // Send funny message to nearby players
            double radius = 50.0;
            double radiusSquared = radius * radius;
            world.getPlayers().forEach(player -> {
                if (player.squaredDistanceTo(book) < radiusSquared) {
                    String msg = MESSAGES[world.random.nextInt(MESSAGES.length)];
                    player.sendMessage(Text.literal(msg), false);
                }
            });

            // Spawn particle effect
            if (world instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(
                        ParticleTypes.ENCHANTED_HIT,
                        book.getX(), book.getY(), book.getZ(),
                        5, 0.1, 0.1, 0.1, 0.0
                );
            }
        }
    }

    private ItemStack generateRandomEnchantedBook(Random random) {
        ItemStack bookStack = new ItemStack(Items.ENCHANTED_BOOK);

        // All enchantments are valid for books
        List<Enchantment> possibleEnchants = Registries.ENCHANTMENT.stream().toList();

        if (possibleEnchants.isEmpty()) return bookStack;

        Enchantment randomEnchant = possibleEnchants.get(random.nextInt(possibleEnchants.size()));

        // Random level between minLevel and maxLevel
        int level = random.nextInt(randomEnchant.getMaxLevel() - randomEnchant.getMinLevel() + 1)
                + randomEnchant.getMinLevel();

        EnchantedBookItem.addEnchantment(bookStack, new EnchantmentLevelEntry(randomEnchant, level));

        return bookStack;
    }

}