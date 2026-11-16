package com.weirdtnt.entities;

import org.jetbrains.annotations.Nullable;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;

public class BookTntEntity extends PrimedTnt {
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


    public BookTntEntity(Level world, double x, double y, double z, @Nullable LivingEntity igniter) {
        super(world, x, y, z, igniter);
        this.setFuse(80);
    }

    public BookTntEntity(EntityType<? extends PrimedTnt> type, Level world) {
        super(type, world);
    }

    @Override
    public void tick() {
        if (this.isRemoved()) return;

        int fuse = this.getFuse() - 1;
        this.setFuse(fuse);

        if (!this.level().isClientSide()) {
            if (fuse <= 0) {
                explodeBooks();
                this.discard();
                return;
            }
        }

        this.level().addParticle(
                ParticleTypes.SMOKE,
                this.getX(), this.getY() + 0.5, this.getZ(),
                0.0, 0.0, 0.0
        );

        this.tickCount++;
    }

    private void explodeBooks() {
        Level world = this.level();
        BlockPos center = this.blockPosition();
        int numBooks = 10; // Number of books to spawn

        for (int i = 0; i < numBooks; i++) {
            ItemStack bookStack;

            // 1% chance to spawn a random enchanted book
            if (world.getRandom().nextDouble() < 0.01) {
                bookStack = generateRandomEnchantedBook(world);
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
            double vx = (world.getRandom().nextDouble() - 0.5) * 2.0;
            double vy = world.getRandom().nextDouble() * 2.0 + 0.5;
            double vz = (world.getRandom().nextDouble() - 0.5) * 2.0;
            book.setDeltaMovement(vx, vy, vz);

            world.addFreshEntity(book);

            // Send funny message to nearby players
            double radius = 50.0;
            double radiusSquared = radius * radius;
            world.players().forEach(player -> {
                if (player.distanceToSqr(book) < radiusSquared) {
                    String msg = MESSAGES[world.getRandom().nextInt(MESSAGES.length)];
                    player.sendSystemMessage(Component.literal(msg));
                }
            });

            // Spawn particle effect
            if (world instanceof ServerLevel serverWorld) {
                serverWorld.sendParticles(
                        ParticleTypes.ENCHANTED_HIT,
                        book.getX(), book.getY(), book.getZ(),
                        5, 0.1, 0.1, 0.1, 0.0
                );
            }
        }
    }

    private ItemStack generateRandomEnchantedBook(Level world) {
        RandomSource random = world.getRandom();
        ItemStack bookStack = new ItemStack(Items.ENCHANTED_BOOK);

        // All enchantments are valid for books
        List<net.minecraft.core.Holder.Reference<Enchantment>> possibleEnchants = world.registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .listElements()
                .toList();

        if (possibleEnchants.isEmpty()) return bookStack;

        net.minecraft.core.Holder<Enchantment> randomEnchant =
                possibleEnchants.get(random.nextInt(possibleEnchants.size()));

        // Enchantment levels now always run from 1 to the max level
        int enchantLevel = 1 + random.nextInt(randomEnchant.value().getMaxLevel());

        bookStack.enchant(randomEnchant, enchantLevel);

        return bookStack;
    }

}