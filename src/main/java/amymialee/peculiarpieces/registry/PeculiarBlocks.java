package amymialee.peculiarpieces.registry;

import amymialee.peculiarpieces.PeculiarPieces;
import amymialee.peculiarpieces.blockentities.BigBarrelBlockEntity;
import amymialee.peculiarpieces.blockentities.BigDispenserBlockEntity;
import amymialee.peculiarpieces.blockentities.BigDropperBlockEntity;
import amymialee.peculiarpieces.blockentities.CouriporterBlockEntity;
import amymialee.peculiarpieces.blockentities.CreativeBarrelBlockEntity;
import amymialee.peculiarpieces.blockentities.EntangledScaffoldingBlockEntity;
import amymialee.peculiarpieces.blockentities.EquipmentStandBlockEntity;
import amymialee.peculiarpieces.blockentities.FishTankBlockEntity;
import amymialee.peculiarpieces.blockentities.FlagBlockEntity;
import amymialee.peculiarpieces.blockentities.PedestalBlockEntity;
import amymialee.peculiarpieces.blockentities.PotionPadBlockEntity;
import amymialee.peculiarpieces.blockentities.RedstoneTriggerBlockEntity;
import amymialee.peculiarpieces.blockentities.WarpBlockEntity;
import amymialee.peculiarpieces.blocks.*;
import amymialee.peculiarpieces.items.CustomScaffoldingItem;
import amymialee.peculiarpieces.items.FlagBlockItem;
import amymialee.peculiarpieces.items.LivingLadderItem;
import amymialee.peculiarpieces.items.MidairBlockItem;
import amymialee.peculiarpieces.items.ToggleableBlockItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameMode;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class PeculiarBlocks {
    // Blocks
    //Torch Levers
    public static final Block TORCH_LEVER = registerBlock("torch_lever", PeculiarItems.MOD_ITEMS, new TorchLeverBlock(FabricBlockSettings.create().noCollision().breakInstantly().luminance(state -> state.get(TorchLeverBlock.POWERED) ? 2 : 14).sounds(BlockSoundGroup.WOOD), ParticleTypes.FLAME));
    public static final Block SOUL_TORCH_LEVER = registerBlock("soul_torch_lever", PeculiarItems.MOD_ITEMS, new TorchLeverBlock(FabricBlockSettings.create().noCollision().breakInstantly().luminance(state -> state.get(TorchLeverBlock.POWERED) ? 2 : 10).sounds(BlockSoundGroup.WOOD), ParticleTypes.SOUL_FIRE_FLAME));
    //Plates
    public static final Block GRABBING_TRAP = registerBlock("grabbing_trap", PeculiarItems.MOD_ITEMS, new GrabbingTrapBlock(FabricBlockSettings.copyOf(Blocks.BLACK_WOOL).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block POTION_PAD = registerBlock("potion_pad", PeculiarItems.MOD_ITEMS, new PotionPadBlock(FabricBlockSettings.copyOf(Blocks.BLACK_WOOL).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block JUMP_PAD = registerBlock("jump_pad", PeculiarItems.MOD_ITEMS, new ToggleableBlockItem(3, new JumpPadBlock(FabricBlockSettings.copyOf(Blocks.BLACK_WOOL).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)), new FabricItemSettings()));
    public static final Block PUSH_PAD = registerBlock("push_pad", PeculiarItems.MOD_ITEMS, new ToggleableBlockItem(3, new PushPadBlock(FabricBlockSettings.copyOf(Blocks.BLACK_WOOL).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)), new FabricItemSettings()));
    //Pressure Plates
    public static final Block PLAYER_PLATE = registerBlock("player_plate", PeculiarItems.MOD_ITEMS, new OpenPressurePlate(false, 2, FabricBlockSettings.create().mapColor(MapColor.BLACK).noCollision().strength(0.5F).sounds(BlockSoundGroup.STONE), SoundEvents.BLOCK_STONE_PRESSURE_PLATE_CLICK_OFF, SoundEvents.BLOCK_STONE_PRESSURE_PLATE_CLICK_ON));
    public static final Block INVISIBLE_PLATE_LIGHT = registerBlock("invisible_plate_light", PeculiarItems.MOD_ITEMS, new OpenPressurePlate(true, 0, FabricBlockSettings.create().mapColor(MapColor.GRAY).noCollision().strength(0.5F).sounds(BlockSoundGroup.GLASS), SoundEvents.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_OFF, SoundEvents.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_ON));
    public static final Block INVISIBLE_PLATE_HEAVY = registerBlock("invisible_plate_heavy", PeculiarItems.MOD_ITEMS, new OpenPressurePlate(true, 1, FabricBlockSettings.create().mapColor(MapColor.GRAY).noCollision().strength(0.5F).sounds(BlockSoundGroup.GLASS), SoundEvents.BLOCK_STONE_PRESSURE_PLATE_CLICK_OFF, SoundEvents.BLOCK_STONE_PRESSURE_PLATE_CLICK_ON));
    public static final Block INVISIBLE_PLAYER_PLATE = registerBlock("invisible_player_plate", PeculiarItems.MOD_ITEMS, new OpenPressurePlate(true, 2, FabricBlockSettings.create().mapColor(MapColor.GRAY).noCollision().strength(0.5F).sounds(BlockSoundGroup.GLASS), SoundEvents.BLOCK_STONE_PRESSURE_PLATE_CLICK_OFF, SoundEvents.BLOCK_STONE_PRESSURE_PLATE_CLICK_ON));
    //Redstone Items
    public static final Block REDSTONE_CLAMP = registerBlock("redstone_clamp", PeculiarItems.MOD_ITEMS, new RedstoneClampBlock(FabricBlockSettings.create().breakInstantly().sounds(BlockSoundGroup.WOOD)));
    public static final Block REDSTONE_FILTER = registerBlock("redstone_filter", PeculiarItems.MOD_ITEMS, new RedstoneFilterBlock(FabricBlockSettings.create().breakInstantly().sounds(BlockSoundGroup.WOOD)));
    public static final Block REDSTONE_HURDLE = registerBlock("redstone_hurdle", PeculiarItems.MOD_ITEMS, new RedstoneHurdleBlock(FabricBlockSettings.create().breakInstantly().sounds(BlockSoundGroup.WOOD)));
    public static final Block REDSTONE_STATIC = registerBlock("redstone_static", PeculiarItems.MOD_ITEMS, new RedstoneStaticBlock(FabricBlockSettings.create().breakInstantly().sounds(BlockSoundGroup.WOOD)));
    public static final Block REDSTONE_MONO = registerBlock("redstone_mono", PeculiarItems.MOD_ITEMS, new RedstoneMonoBlock(FabricBlockSettings.create().breakInstantly().sounds(BlockSoundGroup.WOOD)));
    public static final Block REDSTONE_RANDOM = registerBlock("redstone_random", PeculiarItems.MOD_ITEMS, new RedstoneRandomizerBlock(FabricBlockSettings.create().breakInstantly().sounds(BlockSoundGroup.WOOD)));
    public static final Block REDSTONE_FLIP = registerBlock("redstone_flip", PeculiarItems.MOD_ITEMS, new RedstoneFlipBlock(FabricBlockSettings.create().breakInstantly().sounds(BlockSoundGroup.WOOD)));
    public static final Block REDSTONE_CLOCK = registerBlock("redstone_clock", PeculiarItems.MOD_ITEMS, new RedstoneClockBlock(FabricBlockSettings.create().breakInstantly().sounds(BlockSoundGroup.WOOD)));
    public static final Block REDSTONE_TRIGGER = registerBlock("redstone_trigger", PeculiarItems.MOD_ITEMS, new RedstoneTriggerBlock(FabricBlockSettings.create().strength(3.5f).sounds(BlockSoundGroup.LODESTONE)));
    //Misc
    public static final Block LIVING_LADDER = registerBlock("living_ladder", PeculiarItems.MOD_ITEMS, new LivingLadderItem(new LivingLadderBlock(FabricBlockSettings.copy(Blocks.LADDER)), new FabricItemSettings().rarity(Rarity.UNCOMMON)));
    public static final Block EQUIPMENT_STAND = registerBlock("equipment_stand", PeculiarItems.MOD_ITEMS, new EquipmentStandBlock(FabricBlockSettings.create().strength(1.5f, 6.0f).nonOpaque().solidBlock(PeculiarBlocks::never).suffocates(PeculiarBlocks::never).blockVision(PeculiarBlocks::never)));
    public static final Block TOUGHENED_SCAFFOLDING = registerBlock("toughened_scaffolding", PeculiarItems.MOD_ITEMS, new CustomScaffoldingItem(24, new ToughenedScaffoldingBlock(FabricBlockSettings.create().mapColor(MapColor.IRON_GRAY).noCollision().sounds(BlockSoundGroup.METAL).dynamicBounds()), new FabricItemSettings().rarity(Rarity.RARE)));
    public static final Block ENTANGLED_SCAFFOLDING = registerBlock("entangled_scaffolding", PeculiarItems.MOD_ITEMS, new CustomScaffoldingItem(24, new EntangledScaffoldingBlock(FabricBlockSettings.create().mapColor(MapColor.PALE_PURPLE).noCollision().sounds(BlockSoundGroup.AMETHYST_CLUSTER).dynamicBounds()), new FabricItemSettings().rarity(Rarity.RARE)));
    public static final Block FISH_TANK = registerBlock("fish_tank", PeculiarItems.MOD_ITEMS, new FishTankBlock(FabricBlockSettings.copy(Blocks.GLASS).luminance(state -> state.get(FishTankBlock.POWERED) ? 2 : 11)));
    public static final Block HEAVY_GLASS = registerBlock("heavy_glass", PeculiarItems.MOD_ITEMS, new HeavyGlassBlock(FabricBlockSettings.create().pistonBehavior(PistonBehavior.PUSH_ONLY).strength(5.0f, 6.0f).sounds(BlockSoundGroup.LODESTONE).nonOpaque().solidBlock(PeculiarBlocks::never).suffocates(PeculiarBlocks::never).blockVision(PeculiarBlocks::never)));
    public static final Block HEAVY_GLASS_TILES = registerBlock("heavy_glass_tiles", PeculiarItems.MOD_ITEMS, new HeavyGlassBlock(FabricBlockSettings.create().pistonBehavior(PistonBehavior.PUSH_ONLY).strength(5.0f, 6.0f).sounds(BlockSoundGroup.LODESTONE).nonOpaque().solidBlock(PeculiarBlocks::never).suffocates(PeculiarBlocks::never).blockVision(PeculiarBlocks::never)));
    public static final Block HEAVY_STONE = registerBlock("heavy_stone", PeculiarItems.MOD_ITEMS, new Block(FabricBlockSettings.create().pistonBehavior(PistonBehavior.PUSH_ONLY).strength(5.0f, 6.0f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block COURIPORTER_BLOCK = registerBlock("couriporter", PeculiarItems.MOD_ITEMS, new CouriporterBlock(FabricBlockSettings.copy(Blocks.LODESTONE)));
    public static final Block BURNING_SPONGE = registerBlock("burning_sponge", PeculiarItems.MOD_ITEMS, new BurningSpongeBlock(FabricBlockSettings.create().strength(0.6f).sounds(BlockSoundGroup.GRASS)));
    public static final Block MIDAIR_BLOCK = registerBlock("midair_block", PeculiarItems.MOD_ITEMS, new MidairBlockItem(new MidairBlock(FabricBlockSettings.create().breakInstantly().luminance(3)), new FabricItemSettings()));
    //Glass
    public static final Block ENTITY_GLASS = registerBlock("entity_glass", PeculiarItems.MOD_ITEMS, new SolidGlassBlock(false, false, FabricBlockSettings.copy(Blocks.GLASS).nonOpaque().solidBlock(PeculiarBlocks::never).suffocates(PeculiarBlocks::never).blockVision(PeculiarBlocks::never)));
    public static final Block TINTED_ENTITY_GLASS = registerBlock("tinted_entity_glass", PeculiarItems.MOD_ITEMS, new SolidGlassBlock(true, false, FabricBlockSettings.copy(Blocks.TINTED_GLASS).nonOpaque().solidBlock(PeculiarBlocks::never).suffocates(PeculiarBlocks::never).blockVision(PeculiarBlocks::never)));
    public static final Block PLAYER_GLASS = registerBlock("player_glass", PeculiarItems.MOD_ITEMS, new SolidGlassBlock(false, true, FabricBlockSettings.copy(Blocks.GLASS).nonOpaque().solidBlock(PeculiarBlocks::never).suffocates(PeculiarBlocks::never).blockVision(PeculiarBlocks::never)));
    public static final Block TINTED_PLAYER_GLASS = registerBlock("tinted_player_glass", PeculiarItems.MOD_ITEMS, new SolidGlassBlock(true, true, FabricBlockSettings.copy(Blocks.TINTED_GLASS).nonOpaque().solidBlock(PeculiarBlocks::never).suffocates(PeculiarBlocks::never).blockVision(PeculiarBlocks::never)));
    //Target Blocks
    public static final Block FAST_TARGET_BLOCK = registerBlock("fast_target", PeculiarItems.MOD_ITEMS, new FastTargetBlock(FabricBlockSettings.create().mapColor(MapColor.OFF_WHITE).strength(0.5f).sounds(BlockSoundGroup.GRASS)));
    public static final Block DARK_FAST_TARGET_BLOCK = registerBlock("dark_fast_target", PeculiarItems.MOD_ITEMS, new FastTargetBlock(FabricBlockSettings.create().mapColor(MapColor.OFF_WHITE).strength(0.5f).sounds(BlockSoundGroup.GRASS)));
    //Gadgets
    public static final Block SLIPPERY_STONE = registerBlock("slippery_stone", PeculiarItems.MOD_ITEMS, new Block(FabricBlockSettings.create().strength(1.25F, 4.0F).slipperiness(1f / 0.91f)));
    public static final Block SHOCK_ABSORBER = registerBlock("shock_absorber", PeculiarItems.MOD_ITEMS, new ShockAbsorberBlock(FabricBlockSettings.copyOf(Blocks.LIGHT_BLUE_WOOL).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block INVERTED_REDSTONE_LAMP = registerBlock("inverted_redstone_lamp", PeculiarItems.MOD_ITEMS, new InvertedRedstoneLampBlock(FabricBlockSettings.create().luminance(state -> state.get(Properties.LIT) ? 15 : 0).strength(0.3f).sounds(BlockSoundGroup.GLASS).allowsSpawning((a, b, c, d) -> true)));
    public static final Block BIG_BARREL = registerBlock("big_barrel", PeculiarItems.MOD_ITEMS, new BigBarrelBlock(FabricBlockSettings.copy(Blocks.BARREL)));
    public static final Block BIG_DROPPER = registerBlock("big_dropper", PeculiarItems.MOD_ITEMS, new BigDropperBlock(FabricBlockSettings.copy(Blocks.DROPPER)));
    public static final Block BIG_DISPENSER = registerBlock("big_dispenser", PeculiarItems.MOD_ITEMS, new BigDispenserBlock(FabricBlockSettings.copy(Blocks.DISPENSER)));
    public static final Block IGNITION = registerBlock("ignition", PeculiarItems.MOD_ITEMS, new IgnitionBlock(FabricBlockSettings.copy(Blocks.DROPPER)));
    public static final Block BLOCK_BREAKER = registerBlock("block_breaker", PeculiarItems.MOD_ITEMS, new BlockBreakerBlock(false, FabricBlockSettings.copy(Blocks.DISPENSER)));
    public static final Block SILK_BREAKER = registerBlock("silk_breaker", PeculiarItems.MOD_ITEMS, new BlockBreakerBlock(true, FabricBlockSettings.copy(Blocks.DISPENSER)));
    public static final Block BLOCK_DETECTOR = registerBlock("block_detector", PeculiarItems.MOD_ITEMS, new BlockDetectorBlock(FabricBlockSettings.copy(Blocks.OBSERVER)));
    public static final Block WARP_BLOCK = registerBlock("warp_block", PeculiarItems.MOD_ITEMS, new WarpBlock(FabricBlockSettings.copy(Blocks.LODESTONE)));
    //Pedestals
    public static final Block OAK_PEDESTAL = registerBlock("oak_pedestal", PeculiarItems.MOD_ITEMS, new PedestalBlock(FabricBlockSettings.copy(Blocks.BARREL)));
    public static final Block BIRCH_PEDESTAL = registerBlock("birch_pedestal", PeculiarItems.MOD_ITEMS, new PedestalBlock(FabricBlockSettings.copy(Blocks.BARREL)));
    public static final Block SPRUCE_PEDESTAL = registerBlock("spruce_pedestal", PeculiarItems.MOD_ITEMS, new PedestalBlock(FabricBlockSettings.copy(Blocks.BARREL)));
    public static final Block JUNGLE_PEDESTAL = registerBlock("jungle_pedestal", PeculiarItems.MOD_ITEMS, new PedestalBlock(FabricBlockSettings.copy(Blocks.BARREL)));
    public static final Block ACACIA_PEDESTAL = registerBlock("acacia_pedestal", PeculiarItems.MOD_ITEMS, new PedestalBlock(FabricBlockSettings.copy(Blocks.BARREL)));
    public static final Block DARK_OAK_PEDESTAL = registerBlock("dark_oak_pedestal", PeculiarItems.MOD_ITEMS, new PedestalBlock(FabricBlockSettings.copy(Blocks.BARREL)));
    public static final Block CRIMSON_PEDESTAL = registerBlock("crimson_pedestal", PeculiarItems.MOD_ITEMS, new PedestalBlock(FabricBlockSettings.copy(Blocks.BARREL)));
    public static final Block WARPED_PEDESTAL = registerBlock("warped_pedestal", PeculiarItems.MOD_ITEMS, new PedestalBlock(FabricBlockSettings.copy(Blocks.BARREL)));
    public static final Block MANGROVE_PEDESTAL = registerBlock("mangrove_pedestal", PeculiarItems.MOD_ITEMS, new PedestalBlock(FabricBlockSettings.copy(Blocks.BARREL)));
    public static final Block CHERRY_PEDESTAL = registerBlock("cherry_pedestal", PeculiarItems.MOD_ITEMS, new PedestalBlock(FabricBlockSettings.copy(Blocks.BARREL)));
    public static final Block STONE_PEDESTAL = registerBlock("stone_pedestal", PeculiarItems.MOD_ITEMS, new PedestalBlock(FabricBlockSettings.copy(Blocks.BARREL).sounds(BlockSoundGroup.STONE)));
    public static final Block DEEPSLATE_PEDESTAL = registerBlock("deepslate_pedestal", PeculiarItems.MOD_ITEMS, new PedestalBlock(FabricBlockSettings.copy(Blocks.BARREL).sounds(BlockSoundGroup.STONE)));
    public static final Block BLACKSTONE_PEDESTAL = registerBlock("blackstone_pedestal", PeculiarItems.MOD_ITEMS, new PedestalBlock(FabricBlockSettings.copy(Blocks.BARREL).sounds(BlockSoundGroup.STONE)));
    public static final Block QUARTZ_PEDESTAL = registerBlock("quartz_pedestal", PeculiarItems.MOD_ITEMS, new PedestalBlock(FabricBlockSettings.copy(Blocks.BARREL).sounds(BlockSoundGroup.STONE)));
    public static final Block PRISMARINE_PEDESTAL = registerBlock("prismarine_pedestal", PeculiarItems.MOD_ITEMS, new PedestalBlock(FabricBlockSettings.copy(Blocks.BARREL).sounds(BlockSoundGroup.STONE)));
    //Phasing Doors
    public static final Block STONE_PHASING_DOOR = registerBlock("stone_phasing_door", PeculiarItems.MOD_ITEMS, new PhasingDoorBlock(FabricBlockSettings.create().strength(3.0f).sounds(BlockSoundGroup.STONE).nonOpaque()));
    public static final Block DEEPSLATE_PHASING_DOOR = registerBlock("deepslate_phasing_door", PeculiarItems.MOD_ITEMS, new PhasingDoorBlock(FabricBlockSettings.create().strength(3.0f).sounds(BlockSoundGroup.STONE).nonOpaque()));
    public static final Block BLACKSTONE_PHASING_DOOR = registerBlock("blackstone_phasing_door", PeculiarItems.MOD_ITEMS, new PhasingDoorBlock(FabricBlockSettings.create().strength(3.0f).sounds(BlockSoundGroup.STONE).nonOpaque()));
    public static final Block QUARTZ_PHASING_DOOR = registerBlock("quartz_phasing_door", PeculiarItems.MOD_ITEMS, new PhasingDoorBlock(FabricBlockSettings.create().strength(3.0f).sounds(BlockSoundGroup.STONE).nonOpaque()));
    public static final Block PRISMARINE_PHASING_DOOR = registerBlock("prismarine_phasing_door", PeculiarItems.MOD_ITEMS, new PhasingDoorBlock(FabricBlockSettings.create().strength(3.0f).sounds(BlockSoundGroup.STONE).nonOpaque()));
    //Flags
    public static final Block FLAG = registerBlock("flag", PeculiarItems.MOD_ITEMS, new FlagBlockItem(new FlagBlock(FabricBlockSettings.create().noCollision().strength(1.0f).sounds(BlockSoundGroup.WOOD)), new FabricItemSettings()));
    //Elevators
    public static final Block WHITE_ELEVATOR = registerBlock("white_elevator", null, new ElevatorBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block ORANGE_ELEVATOR = registerBlock("orange_elevator", null, new ElevatorBlock(FabricBlockSettings.copyOf(Blocks.ORANGE_WOOL).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block MAGENTA_ELEVATOR = registerBlock("magenta_elevator", null, new ElevatorBlock(FabricBlockSettings.copyOf(Blocks.MAGENTA_WOOL).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block LIGHT_BLUE_ELEVATOR = registerBlock("light_blue_elevator", null, new ElevatorBlock(FabricBlockSettings.copyOf(Blocks.LIGHT_BLUE_WOOL).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block YELLOW_ELEVATOR = registerBlock("yellow_elevator", null, new ElevatorBlock(FabricBlockSettings.copyOf(Blocks.YELLOW_WOOL).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block LIME_ELEVATOR = registerBlock("lime_elevator", null, new ElevatorBlock(FabricBlockSettings.copyOf(Blocks.LIME_WOOL).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block PINK_ELEVATOR = registerBlock("pink_elevator", null, new ElevatorBlock(FabricBlockSettings.copyOf(Blocks.PINK_WOOL).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block GRAY_ELEVATOR = registerBlock("gray_elevator", null, new ElevatorBlock(FabricBlockSettings.copyOf(Blocks.GRAY_WOOL).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block LIGHT_GRAY_ELEVATOR = registerBlock("light_gray_elevator", null, new ElevatorBlock(FabricBlockSettings.copyOf(Blocks.LIGHT_GRAY_WOOL).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block CYAN_ELEVATOR = registerBlock("cyan_elevator", null, new ElevatorBlock(FabricBlockSettings.copyOf(Blocks.CYAN_WOOL).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block PURPLE_ELEVATOR = registerBlock("purple_elevator", null, new ElevatorBlock(FabricBlockSettings.copyOf(Blocks.PURPLE_WOOL).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block BLUE_ELEVATOR = registerBlock("blue_elevator", null, new ElevatorBlock(FabricBlockSettings.copyOf(Blocks.BLUE_WOOL).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block BROWN_ELEVATOR = registerBlock("brown_elevator", null, new ElevatorBlock(FabricBlockSettings.copyOf(Blocks.BROWN_WOOL).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block GREEN_ELEVATOR = registerBlock("green_elevator", null, new ElevatorBlock(FabricBlockSettings.copyOf(Blocks.GREEN_WOOL).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block RED_ELEVATOR = registerBlock("red_elevator", null, new ElevatorBlock(FabricBlockSettings.copyOf(Blocks.RED_WOOL).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block BLACK_ELEVATOR = registerBlock("black_elevator", null, new ElevatorBlock(FabricBlockSettings.copyOf(Blocks.BLACK_WOOL).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    //Rotating Elevators
    public static final Block WHITE_ROTATING_ELEVATOR = registerBlock("white_rotating_elevator", null, new RotatingElevatorBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block ORANGE_ROTATING_ELEVATOR = registerBlock("orange_rotating_elevator", null, new RotatingElevatorBlock(FabricBlockSettings.copyOf(Blocks.ORANGE_WOOL).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block MAGENTA_ROTATING_ELEVATOR = registerBlock("magenta_rotating_elevator", null, new RotatingElevatorBlock(FabricBlockSettings.copyOf(Blocks.MAGENTA_WOOL).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block LIGHT_BLUE_ROTATING_ELEVATOR = registerBlock("light_blue_rotating_elevator", null, new RotatingElevatorBlock(FabricBlockSettings.copyOf(Blocks.LIGHT_BLUE_WOOL).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block YELLOW_ROTATING_ELEVATOR = registerBlock("yellow_rotating_elevator", null, new RotatingElevatorBlock(FabricBlockSettings.copyOf(Blocks.YELLOW_WOOL).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block LIME_ROTATING_ELEVATOR = registerBlock("lime_rotating_elevator", null, new RotatingElevatorBlock(FabricBlockSettings.copyOf(Blocks.LIME_WOOL).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block PINK_ROTATING_ELEVATOR = registerBlock("pink_rotating_elevator", null, new RotatingElevatorBlock(FabricBlockSettings.copyOf(Blocks.PINK_WOOL).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block GRAY_ROTATING_ELEVATOR = registerBlock("gray_rotating_elevator", null, new RotatingElevatorBlock(FabricBlockSettings.copyOf(Blocks.GRAY_WOOL).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block LIGHT_GRAY_ROTATING_ELEVATOR = registerBlock("light_gray_rotating_elevator", null, new RotatingElevatorBlock(FabricBlockSettings.copyOf(Blocks.LIGHT_GRAY_WOOL).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block CYAN_ROTATING_ELEVATOR = registerBlock("cyan_rotating_elevator", null, new RotatingElevatorBlock(FabricBlockSettings.copyOf(Blocks.CYAN_WOOL).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block PURPLE_ROTATING_ELEVATOR = registerBlock("purple_rotating_elevator", null, new RotatingElevatorBlock(FabricBlockSettings.copyOf(Blocks.PURPLE_WOOL).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block BLUE_ROTATING_ELEVATOR = registerBlock("blue_rotating_elevator", null, new RotatingElevatorBlock(FabricBlockSettings.copyOf(Blocks.BLUE_WOOL).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block BROWN_ROTATING_ELEVATOR = registerBlock("brown_rotating_elevator", null, new RotatingElevatorBlock(FabricBlockSettings.copyOf(Blocks.BROWN_WOOL).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block GREEN_ROTATING_ELEVATOR = registerBlock("green_rotating_elevator", null, new RotatingElevatorBlock(FabricBlockSettings.copyOf(Blocks.GREEN_WOOL).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block RED_ROTATING_ELEVATOR = registerBlock("red_rotating_elevator", null, new RotatingElevatorBlock(FabricBlockSettings.copyOf(Blocks.RED_WOOL).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block BLACK_ROTATING_ELEVATOR = registerBlock("black_rotating_elevator", null, new RotatingElevatorBlock(FabricBlockSettings.copyOf(Blocks.BLACK_WOOL).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));

    // Creative Items
    //Misc
    public static final Block CREATIVE_BARREL = registerBlock("creative_barrel", PeculiarItems.CREATIVE_ITEMS, new CreativeBarrelBlock(FabricBlockSettings.create().sounds(BlockSoundGroup.LODESTONE).strength(-1.0f, 3600000.8f).nonOpaque().solidBlock(PeculiarBlocks::never).suffocates(PeculiarBlocks::never).blockVision(PeculiarBlocks::never)));
    //Checkpoints
    public static final Block CHECKPOINT = registerBlock("checkpoint", PeculiarItems.CREATIVE_ITEMS, new BlockItem(new CheckpointBlock(FabricBlockSettings.copyOf(Blocks.STRUCTURE_VOID).noCollision()), new FabricItemSettings().rarity(Rarity.EPIC)));
    public static final Block CHECKPOINT_REMOVER = registerBlock("checkpoint_remover", PeculiarItems.CREATIVE_ITEMS, new BlockItem(new CheckpointRemoverBlock(FabricBlockSettings.copyOf(Blocks.STRUCTURE_VOID).noCollision()), new FabricItemSettings().rarity(Rarity.EPIC)));
    public static final Block CHECKPOINT_RETURNER = registerBlock("checkpoint_returner", PeculiarItems.CREATIVE_ITEMS, new BlockItem(new CheckpointReturnerBlock(FabricBlockSettings.copyOf(Blocks.STRUCTURE_VOID).noCollision()), new FabricItemSettings().rarity(Rarity.EPIC)));
    public static final Block CHECKPOINT_DAMAGER = registerBlock("checkpoint_damager", PeculiarItems.CREATIVE_ITEMS, new BlockItem(new CheckpointDamageBlock(FabricBlockSettings.copyOf(Blocks.STRUCTURE_VOID).noCollision()), new FabricItemSettings().rarity(Rarity.EPIC)));
    //Entity Barriers
    public static final Block ENTITY_BARRIER = registerBlock("entity_barrier", PeculiarItems.CREATIVE_ITEMS, new BlockItem(new EntityBarrierBlock(false, FabricBlockSettings.copyOf(Blocks.BARRIER).strength(-1.0f, 3600000.8f).nonOpaque().solidBlock(PeculiarBlocks::never).suffocates(PeculiarBlocks::never).blockVision(PeculiarBlocks::never)), new FabricItemSettings().rarity(Rarity.EPIC)));
    public static final Block PLAYER_BARRIER = registerBlock("player_barrier", PeculiarItems.CREATIVE_ITEMS, new BlockItem(new EntityBarrierBlock(true, FabricBlockSettings.copyOf(Blocks.BARRIER).strength(-1.0f, 3600000.8f).nonOpaque().solidBlock(PeculiarBlocks::never).suffocates(PeculiarBlocks::never).blockVision(PeculiarBlocks::never)), new FabricItemSettings().rarity(Rarity.EPIC)));
    //Gamemode Blocks
    public static final Block ADVENTURE_BLOCKER = registerBlock("adventure_blocker", PeculiarItems.CREATIVE_ITEMS, new BlockItem(new GameModeBlockerBlock(GameMode.ADVENTURE, FabricBlockSettings.copyOf(Blocks.BARRIER).strength(-1.0f, 3600000.8f).noCollision()), new FabricItemSettings().rarity(Rarity.EPIC)));
    public static final Block ADVENTURE_BARRIER = registerBlock("adventure_barrier", PeculiarItems.CREATIVE_ITEMS, new BlockItem(new GameModeBarrierBlock(GameMode.ADVENTURE, FabricBlockSettings.copyOf(Blocks.BARRIER).strength(-1.0f, 3600000.8f).nonOpaque().solidBlock(PeculiarBlocks::never).suffocates(PeculiarBlocks::never).blockVision(PeculiarBlocks::never)), new FabricItemSettings().rarity(Rarity.EPIC)));
    public static final Block ADVENTURE_SETTER = registerBlock("adventure_setter", PeculiarItems.CREATIVE_ITEMS, new BlockItem(new GameModeSetterBlock(GameMode.ADVENTURE, FabricBlockSettings.copyOf(Blocks.BARRIER).strength(-1.0f, 3600000.8f).noCollision()), new FabricItemSettings().rarity(Rarity.EPIC)));
    public static final Block SURVIVOR_BLOCKER = registerBlock("survivor_blocker", PeculiarItems.CREATIVE_ITEMS, new BlockItem(new GameModeBlockerBlock(GameMode.SURVIVAL, FabricBlockSettings.copyOf(Blocks.BARRIER).strength(-1.0f, 3600000.8f).noCollision()), new FabricItemSettings().rarity(Rarity.EPIC)));
    public static final Block SURVIVOR_BARRIER = registerBlock("survivor_barrier", PeculiarItems.CREATIVE_ITEMS, new BlockItem(new GameModeBarrierBlock(GameMode.SURVIVAL, FabricBlockSettings.copyOf(Blocks.BARRIER).strength(-1.0f, 3600000.8f).nonOpaque().solidBlock(PeculiarBlocks::never).suffocates(PeculiarBlocks::never).blockVision(PeculiarBlocks::never)), new FabricItemSettings().rarity(Rarity.EPIC)));
    public static final Block SURVIVOR_SETTER = registerBlock("survivor_setter", PeculiarItems.CREATIVE_ITEMS, new BlockItem(new GameModeSetterBlock(GameMode.SURVIVAL, FabricBlockSettings.copyOf(Blocks.BARRIER).strength(-1.0f, 3600000.8f).noCollision()), new FabricItemSettings().rarity(Rarity.EPIC)));

    // Block Entities
    public static BlockEntityType<WarpBlockEntity> WARP_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, "warp_block", FabricBlockEntityTypeBuilder.create(WarpBlockEntity::new, WARP_BLOCK).build(null));
    public static BlockEntityType<CouriporterBlockEntity> COURIPORTER_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, "couriporter", FabricBlockEntityTypeBuilder.create(CouriporterBlockEntity::new, COURIPORTER_BLOCK).build(null));
    public static BlockEntityType<EntangledScaffoldingBlockEntity> ENTANGLED_SCAFFOLDING_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, "entangled_scaffolding", FabricBlockEntityTypeBuilder.create(EntangledScaffoldingBlockEntity::new, ENTANGLED_SCAFFOLDING).build(null));
    public static BlockEntityType<BigBarrelBlockEntity> BIG_BARREL_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, "big_barrel", FabricBlockEntityTypeBuilder.create(BigBarrelBlockEntity::new, BIG_BARREL).build(null));
    public static BlockEntityType<BigDropperBlockEntity> BIG_DROPPER_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, "big_dropper", FabricBlockEntityTypeBuilder.create(BigDropperBlockEntity::new, BIG_DROPPER).build(null));
    public static BlockEntityType<BigDispenserBlockEntity> BIG_DISPENSER_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, "big_dispenser", FabricBlockEntityTypeBuilder.create(BigDispenserBlockEntity::new, BIG_DISPENSER).build(null));
    public static BlockEntityType<PotionPadBlockEntity> POTION_PAD_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, "potion_pad", FabricBlockEntityTypeBuilder.create(PotionPadBlockEntity::new, POTION_PAD).build(null));
    public static BlockEntityType<PedestalBlockEntity> PEDESTAL_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, "pedestal", FabricBlockEntityTypeBuilder.create(PedestalBlockEntity::new, OAK_PEDESTAL, BIRCH_PEDESTAL, SPRUCE_PEDESTAL, JUNGLE_PEDESTAL, ACACIA_PEDESTAL, DARK_OAK_PEDESTAL, CRIMSON_PEDESTAL, WARPED_PEDESTAL, MANGROVE_PEDESTAL, CHERRY_PEDESTAL, STONE_PEDESTAL, DEEPSLATE_PEDESTAL, BLACKSTONE_PEDESTAL, QUARTZ_PEDESTAL, PRISMARINE_PEDESTAL).build(null));
    public static BlockEntityType<FishTankBlockEntity> FISH_TANK_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, "fish_tank", FabricBlockEntityTypeBuilder.create(FishTankBlockEntity::new, FISH_TANK).build(null));
    public static BlockEntityType<FlagBlockEntity> FLAG_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, "flag", FabricBlockEntityTypeBuilder.create(FlagBlockEntity::new, FLAG).build(null));
    public static BlockEntityType<RedstoneTriggerBlockEntity> REDSTONE_TRIGGER_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, "redstone_trigger", FabricBlockEntityTypeBuilder.create(RedstoneTriggerBlockEntity::new, REDSTONE_TRIGGER).build(null));
    public static BlockEntityType<CreativeBarrelBlockEntity> CREATIVE_BARREL_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, "creative_barrel", FabricBlockEntityTypeBuilder.create(CreativeBarrelBlockEntity::new, CREATIVE_BARREL).build(null));
    public static BlockEntityType<EquipmentStandBlockEntity> EQUIPMENT_STAND_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, "equipment_stand", FabricBlockEntityTypeBuilder.create(EquipmentStandBlockEntity::new, EQUIPMENT_STAND).build(null));

    public static void init() {}

    private static Block registerBlock(String name, ArrayList<Item> list, Block block) {
        return registerBlock(name, list, new BlockItem(block, new FabricItemSettings()));
    }

    private static Block registerBlock(String name, ArrayList<Item> list, BlockItem block) {
        Registry.register(Registries.BLOCK, PeculiarPieces.id(name), block.getBlock());
        PeculiarItems.registerItem(name, list, block);
        return block.getBlock();
    }

    private static Block registerBlock(String name, Block block) {
        Registry.register(Registries.BLOCK, PeculiarPieces.id(name), block);
        return block;
    }

    private static boolean never(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }
}