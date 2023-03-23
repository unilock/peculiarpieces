package amymialee.peculiarpieces.compat;

//
//public class PeculiarWaliaPlugin implements IWailaPlugin {
//    @Override
//    public void register(IRegistrar registrar) {
//        registrar.addComponent(PeculiarRedstoneProvider.INSTANCE, BODY, RedstoneClampBlock.class);
//        registrar.addComponent(PeculiarRedstoneProvider.INSTANCE, BODY, RedstoneClockBlock.class);
//        registrar.addComponent(PeculiarRedstoneProvider.INSTANCE, BODY, RedstoneFilterBlock.class);
//        registrar.addComponent(PeculiarRedstoneProvider.INSTANCE, BODY, RedstoneFlipBlock.class);
//        registrar.addComponent(PeculiarRedstoneProvider.INSTANCE, BODY, RedstoneHurdleBlock.class);
//        registrar.addComponent(PeculiarRedstoneProvider.INSTANCE, BODY, RedstoneRandomizerBlock.class);
//        registrar.addComponent(PeculiarRedstoneProvider.INSTANCE, BODY, RedstoneStaticBlock.class);
//        registrar.addComponent(PeculiarPlateProvider.INSTANCE, BODY, JumpPadBlock.class);
//        registrar.addComponent(PeculiarPlateProvider.INSTANCE, BODY, PushPadBlock.class);
//    }
//}
//
//enum PeculiarRedstoneProvider implements IBlockComponentProvider {
//    INSTANCE;
//    @Override
//    public void appendBody(ITooltip tooltip, IBlockAccessor accessor, IPluginConfig config) {
//        if (accessor.getBlock() == PeculiarBlocks.REDSTONE_CLAMP) {
//            tooltip.addLine(new PairComponent(Text.translatable("tooltip.peculiarpieces.clamp"), Text.literal(String.valueOf(accessor.getBlockState().get(RedstoneClampBlock.CLAMP)))));
//        } else if (accessor.getBlock() == PeculiarBlocks.REDSTONE_CLOCK) {
//            tooltip.addLine(new PairComponent(Text.translatable("tooltip.peculiarpieces.delay"), Text.literal(String.valueOf(accessor.getBlockState().get(RedstoneClockBlock.DELAY)))));
//        } else if (accessor.getBlock() == PeculiarBlocks.REDSTONE_FILTER) {
//            tooltip.addLine(new PairComponent(Text.translatable("tooltip.peculiarpieces.filter"), Text.literal(String.valueOf(accessor.getBlockState().get(RedstoneFilterBlock.FILTER)))));
//        } else if (accessor.getBlock() instanceof RedstoneFlipBlock) {
//            tooltip.addLine(new PairComponent(Text.translatable("tooltip.waila.state"), Text.translatable("tooltip.waila.state_" + (accessor.getBlockState().get(RedstoneFlipBlock.ENABLED) ? "on" : "off"))));
//        } else if (accessor.getBlock() == PeculiarBlocks.REDSTONE_HURDLE) {
//            tooltip.addLine(new PairComponent(Text.translatable("tooltip.peculiarpieces.hurdle"), Text.literal(String.valueOf(accessor.getBlockState().get(RedstoneHurdleBlock.HURDLE)))));
//        } else if (accessor.getBlock() == PeculiarBlocks.REDSTONE_RANDOM) {
//            tooltip.addLine(new PairComponent(Text.translatable("tooltip.peculiarpieces.power"), Text.literal(String.valueOf(accessor.getBlockState().get(RedstoneRandomizerBlock.OUTPUT)))));
//        } else if (accessor.getBlock() == PeculiarBlocks.REDSTONE_STATIC) {
//            tooltip.addLine(new PairComponent(Text.translatable("tooltip.peculiarpieces.power"), Text.literal(String.valueOf(accessor.getBlockState().get(RedstoneStaticBlock.STATIC)))));
//        }
//    }
//}
//
//enum PeculiarPlateProvider implements IBlockComponentProvider {
//    INSTANCE;
//    @Override
//    public void appendBody(ITooltip tooltip, IBlockAccessor accessor, IPluginConfig config) {
//        if (accessor.getBlock() == PeculiarBlocks.JUMP_PAD) {
//            tooltip.addLine(new PairComponent(Text.translatable("tooltip.peculiarpieces.power"), Text.literal(String.valueOf(accessor.getBlockState().get(JumpPadBlock.POWER)))));
//        } else if (accessor.getBlock() == PeculiarBlocks.PUSH_PAD) {
//            tooltip.addLine(new PairComponent(Text.translatable("tooltip.peculiarpieces.power"), Text.literal(String.valueOf(accessor.getBlockState().get(PushPadBlock.POWER)))));
//        }
//    }
//}
