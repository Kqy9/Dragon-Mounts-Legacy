package com.github.kay9.dragonmounts.data.providers;

import com.github.kay9.dragonmounts.DMLRegistry;
import com.github.kay9.dragonmounts.DragonMountsLegacy;
import com.github.kay9.dragonmounts.abilities.*;
import com.github.kay9.dragonmounts.dragon.TameableDragon;
import com.github.kay9.dragonmounts.dragon.breed.BreedRegistry;
import com.github.kay9.dragonmounts.dragon.breed.DragonBreed;
import com.github.kay9.dragonmounts.dragon.egg.HatchableEggBlock;
import com.github.kay9.dragonmounts.habitats.*;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Either;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.*;
import java.util.concurrent.CompletableFuture;

import static com.google.common.collect.ImmutableMap.of;

class DragonBreedProvider extends DatapackBuiltinEntriesProvider
{
    DragonBreedProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup)
    {
        super(output, lookup, build(), Set.of(DragonMountsLegacy.MOD_ID));
    }

    private static RegistrySetBuilder build()
    {
        return new RegistrySetBuilder().add(BreedRegistry.REGISTRY_KEY, context ->
        {

                    registerBuiltIn(context, DragonBreed.BuiltIn.AETHER,
                            0x718AA9,
                            0xE6E6E6,
                            Optional.empty(),
                            of(Attributes.FLYING_SPEED, TameableDragon.BASE_SPEED_FLYING * 1.225f),
                            list(),
                            list(
                                    new HeightHabitat(3, false, 200)
                            ),
                            immunities(context, DamageTypes.ON_FIRE, DamageTypes.IN_FIRE),
                            Optional.empty());

                    registerBuiltIn(context, DragonBreed.BuiltIn.END,
                            0x161616,
                            0xff63e8,
                            Optional.of(ParticleTypes.PORTAL),
                            of(Attributes.MAX_HEALTH, TameableDragon.BASE_HEALTH * 1.5),
                            list(), // teleport ability?
                            list(
                                    DragonBreathHabitat.INSTANCE
                            ),
                            immunities(context, DamageTypes.ON_FIRE, DamageTypes.IN_FIRE),
                            Optional.empty());

                    registerBuiltIn(context, DragonBreed.BuiltIn.FIRE,
                            0x912400,
                            0xff9819,
                            Optional.of(ParticleTypes.FLAME),
                            of(Attributes.ATTACK_DAMAGE, TameableDragon.BASE_DAMAGE + 2),
                            list(
                                    HotFeetAbility.INSTANCE
                            ),
                            ImmutableList.of(
                                    new NearbyBlocksHabitat(1, BlockTags.create(DragonMountsLegacy.id("fire_dragon_habitat_blocks"))),
                                    new FluidHabitat(3, FluidTags.LAVA)),
                            immunities(context, DamageTypes.ON_FIRE, DamageTypes.IN_FIRE, DamageTypes.LAVA, DamageTypes.HOT_FLOOR),
                            Optional.empty());

                    registerBuiltIn(context, DragonBreed.BuiltIn.FOREST,
                            0x054a00,
                            0x0a9600,
                            Optional.of(ParticleTypes.HAPPY_VILLAGER),
                            of(Attributes.MOVEMENT_SPEED, TameableDragon.BASE_SPEED_GROUND + 0.2),
                            list(
                                    GreenToesAbility.INSTANCE
                            ),
                            list(
                                    new NearbyBlocksHabitat(0.5f, BlockTagProvider.FOREST_DRAGON_HABITAT_BLOCKS),
                                    new BiomeHabitat(2, BiomeTags.IS_JUNGLE)
                            ),
                            immunities(context, DamageTypes.ON_FIRE, DamageTypes.IN_FIRE),
                            Optional.empty());

                    registerBuiltIn(context, DragonBreed.BuiltIn.GHOST,
                            0xc4c4c4,
                            0xc2f8ff,
                            Optional.empty(),
                            of(Attributes.ARMOR_TOUGHNESS, 4.0D),
                            list(
                                    ReaperStepAbility.INSTANCE
                            ),
                            list(
                                    new PickyHabitat(list(
                                            new HeightHabitat(1, true, 0),
                                            new LightHabitat(2, true, 3)
                                    ))
                            ),
                            immunities(context, DamageTypes.DROWN, DamageTypes.ON_FIRE, DamageTypes.IN_FIRE),
                            sound(DMLRegistry.GHOST_DRAGON_AMBIENT.get()));

                    registerBuiltIn(context, DragonBreed.BuiltIn.ICE,
                            0xffffff,
                            0x00E1FF,
                            Optional.of(ParticleTypes.SNOWFLAKE),
                            of(Attributes.ATTACK_DAMAGE, TameableDragon.BASE_DAMAGE + 2),
                            list(
                                    FrostWalkerAbility.create(3),
                                    SnowStepperAbility.INSTANCE
                            ),
                            list(
                                    new NearbyBlocksHabitat(0.5f, BlockTagProvider.ICE_DRAGON_HABITAT_BLOCKS)
                            ),
                            immunities(context, DamageTypes.DROWN, DamageTypes.FREEZE, DamageTypes.ON_FIRE, DamageTypes.IN_FIRE),
                            Optional.empty());

                    registerBuiltIn(context, DragonBreed.BuiltIn.NETHER,
                            0x912400,
                            0x2e0b00,
                            Optional.of(ParticleTypes.SOUL_FIRE_FLAME),
                            of(Attributes.ARMOR, 8d),
                            list(),
                            list(
                                    new NearbyBlocksHabitat(0.5f, BlockTagProvider.NETHER_DRAGON_HABITAT_BLOCKS),
                                    new BiomeHabitat(3, BiomeTags.IS_NETHER)
                            ),
                            immunities(context, DamageTypes.ON_FIRE, DamageTypes.IN_FIRE, DamageTypes.LAVA, DamageTypes.HOT_FLOOR),
                            Optional.empty());

                    registerBuiltIn(context, DragonBreed.BuiltIn.WATER,
                            0x0062ff,
                            0x5999ff,
                            Optional.of(ParticleTypes.DRIPPING_WATER),
                            of(Attributes.MAX_HEALTH, TameableDragon.BASE_HEALTH * 1.25),
                            list(
                                    HydroStepAbility.INSTANCE
                            ),
                            list(
                                    new FluidHabitat(1f, FluidTags.WATER),
                                    new NearbyBlocksHabitat(0.5f, BlockTagProvider.WATER_DRAGON_HABITAT_BLOCKS)
                            ),
                            immunities(context, DamageTypes.DROWN, DamageTypes.ON_FIRE, DamageTypes.IN_FIRE),
                            Optional.empty());
        });
    }

    private static void registerBuiltIn(BootstapContext<DragonBreed> context, ResourceKey<DragonBreed> id, int primaryColor, int secondaryColor, Optional<ParticleOptions> hatchParticles, Map<Attribute, Double> attributes, List<Ability.Factory<Ability>> abilities, List<Habitat> habitats, HolderSet<DamageType> immunities, Optional<Holder<SoundEvent>> ambientSound)
    {
        Either<Integer, String> reproConfigTarget = Either.right("config:" + id.location().getPath());
        context.register(id, builtIn(primaryColor, secondaryColor, hatchParticles, attributes, abilities, habitats, immunities, ambientSound, reproConfigTarget));
    }

    public static DragonBreed builtIn(int primaryColor, int secondaryColor, Optional<ParticleOptions> hatchParticles, Map<Attribute, Double> attributes, List<Ability.Factory<Ability>> abilities, List<Habitat> habitats, HolderSet<DamageType> immunities, Optional<Holder<SoundEvent>> ambientSound, Either<Integer, String> reproduction)
    {
        return new DragonBreed(primaryColor, secondaryColor, hatchParticles, attributes, abilities, habitats, immunities, ambientSound, BuiltInLootTables.EMPTY, TameableDragon.BASE_GROWTH_TIME, HatchableEggBlock.DEFAULT_HATCH_CHANCE, TameableDragon.BASE_SIZE_MODIFIER, BuiltInRegistries.ITEM.getOrCreateTag(ItemTags.FISHES), BuiltInRegistries.ITEM.getOrCreateTag(ItemTags.FISHES), reproduction);
    }

    @SuppressWarnings("unchecked")
    protected static <T> ImmutableList<T> list(Object... objs)
    {
        return (ImmutableList<T>) ImmutableList.copyOf(objs);
    }

    @SafeVarargs
    private static HolderSet<DamageType> immunities(BootstapContext<DragonBreed>context, ResourceKey<DamageType>... types)
    {
        var damageTypeLookup = context.lookup(Registries.DAMAGE_TYPE);
        var items = new ArrayList<ResourceKey<DamageType>>();
        Collections.addAll(items, types);
        items.add(DamageTypes.CACTUS); // assume cactus needles don't hurt thick scaled lizards
        items.add(DamageTypes.SWEET_BERRY_BUSH); // assume thorns also don't hurt thick scaled lizards
        items.add(DamageTypes.DRAGON_BREATH); // inherited from it anyway
        return HolderSet.direct(damageTypeLookup::getOrThrow, items);
    }

    private static Optional<Holder<SoundEvent>> sound(SoundEvent event)
    {
        return Optional.of(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(event));
    }
}
