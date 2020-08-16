package com.cristofiumod.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.stream.Stream;

public class CachimbaBlock extends Block {

    private static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    private static final VoxelShape SHAPE_N = getCachimbaNorthShapes();
    private static final VoxelShape SHAPE_S = getCachimbaSouthShapes();
    private static final VoxelShape SHAPE_E = getCachimbaEastShapes();
    private static final VoxelShape SHAPE_W = getCachimbaWestShapes();

    public CachimbaBlock() {
        super(Block.Properties.create(Material.WOOD)
                .hardnessAndResistance(0.5F)
                .sound(SoundType.GLASS));
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.get(FACING)) {
            case SOUTH:
                return SHAPE_S;
            case EAST:
                return SHAPE_E;
            case WEST:
                return SHAPE_W;
            case NORTH:
            default:
                return SHAPE_N;
        }
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public float getAmbientOcclusionLightValue(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return 1.0F;
    }

    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        int health = player.getFoodStats().getFoodLevel();
        player.getFoodStats().addStats(health >= 6 ? 6 - health : -1, 6F);
        player.addPotionEffect(new EffectInstance(Effects.HUNGER, 1300, 0));
        player.addPotionEffect(new EffectInstance(Effects.NAUSEA, 1200, 0));
        player.addPotionEffect(new EffectInstance(Effects.LEVITATION, 800, 0));
        player.addPotionEffect(new EffectInstance(Effects.BLINDNESS, 1100, 0));
        player.addPotionEffect(new EffectInstance(Effects.SLOW_FALLING, 900, 0));
        player.playSound(SoundEvents.ENTITY_ENDER_DRAGON_GROWL, this.soundType.getVolume() * 0.5F, this.soundType.getPitch() * 0.75F);

        return ActionResultType.SUCCESS;
    }

    // Vortex shapes
    private static VoxelShape getCachimbaSouthShapes() {
        return Stream.of(
                Block.makeCuboidShape(5, 0, 5, 11, 1, 11),
                Block.makeCuboidShape(4, 1, 4, 12, 2, 12),
                Block.makeCuboidShape(4, 2, 4, 12, 3, 12),
                Block.makeCuboidShape(5, 3, 5, 11, 4, 11),
                Block.makeCuboidShape(6, 4, 6, 10, 5, 10),
                Block.makeCuboidShape(7, 5, 7, 9, 7, 9),
                Block.makeCuboidShape(7, 7, 7, 9, 8, 9),
                Block.makeCuboidShape(6, 8, 6, 10, 10, 10),
                Block.makeCuboidShape(7, 10, 7, 9, 14, 9),
                Block.makeCuboidShape(5, 14, 5, 11, 14, 11),
                Block.makeCuboidShape(7, 14, 7, 9, 16, 9),
                Block.makeCuboidShape(6, 16, 6, 10, 16, 10),
                Block.makeCuboidShape(9, 6, 7, 10, 7, 8),
                Block.makeCuboidShape(10, 7, 7, 11, 8, 8),
                Block.makeCuboidShape(11, 8, 7, 12, 9, 8),
                Block.makeCuboidShape(12, 8, 7, 14, 9, 8),
                Block.makeCuboidShape(14, 7, 7, 15, 8, 8),
                Block.makeCuboidShape(14, 6, 8, 15, 7, 9),
                Block.makeCuboidShape(14, 5, 9, 15, 6, 10),
                Block.makeCuboidShape(14, 4, 10, 15, 5, 11),
                Block.makeCuboidShape(14, 2, 11, 15, 4, 12),
                Block.makeCuboidShape(14, 1, 12, 15, 2, 13),
                Block.makeCuboidShape(13, 1, 13, 14, 2, 14),
                Block.makeCuboidShape(12, 0, 14, 13, 1, 15),
                Block.makeCuboidShape(11, 0, 14, 12, 1, 15),
                Block.makeCuboidShape(10, 1, 14, 11, 2, 15),
                Block.makeCuboidShape(9, 1, 14, 10, 2, 15),
                Block.makeCuboidShape(7, 2, 14, 9, 3, 15),
                Block.makeCuboidShape(6, 3, 14, 7, 4, 15),
                Block.makeCuboidShape(5, 4, 14, 6, 5, 15),
                Block.makeCuboidShape(4, 5, 14, 5, 6, 15),
                Block.makeCuboidShape(3, 6, 14, 4, 7, 15),
                Block.makeCuboidShape(3, 7, 14, 4, 12, 15),
                Block.makeCuboidShape(3, 12, 14, 4, 13, 15)
        ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();
    }

    private static VoxelShape getCachimbaNorthShapes() {
        return Stream.of(
                Block.makeCuboidShape(5, 0, 5, 11, 1, 11),
                Block.makeCuboidShape(4, 1, 4, 12, 2, 12),
                Block.makeCuboidShape(4, 2, 4, 12, 3, 12),
                Block.makeCuboidShape(5, 3, 5, 11, 4, 11),
                Block.makeCuboidShape(6, 4, 6, 10, 5, 10),
                Block.makeCuboidShape(7, 5, 7, 9, 7, 9),
                Block.makeCuboidShape(7, 7, 7, 9, 8, 9),
                Block.makeCuboidShape(6, 8, 6, 10, 10, 10),
                Block.makeCuboidShape(7, 10, 7, 9, 14, 9),
                Block.makeCuboidShape(5, 14, 5, 11, 14, 11),
                Block.makeCuboidShape(7, 14, 7, 9, 16, 9),
                Block.makeCuboidShape(6, 16, 6, 10, 16, 10),
                Block.makeCuboidShape(6, 6, 8, 7, 7, 9),
                Block.makeCuboidShape(5, 7, 8, 6, 8, 9),
                Block.makeCuboidShape(4, 8, 8, 5, 9, 9),
                Block.makeCuboidShape(2, 8, 8, 4, 9, 9),
                Block.makeCuboidShape(1, 7, 8, 2, 8, 9),
                Block.makeCuboidShape(1, 6, 7, 2, 7, 8),
                Block.makeCuboidShape(1, 5, 6, 2, 6, 7),
                Block.makeCuboidShape(1, 4, 5, 2, 5, 6),
                Block.makeCuboidShape(1, 2, 4, 2, 4, 5),
                Block.makeCuboidShape(1, 1, 3, 2, 2, 4),
                Block.makeCuboidShape(2, 1, 2, 3, 2, 3),
                Block.makeCuboidShape(3, 0, 1, 4, 1, 2),
                Block.makeCuboidShape(4, 0, 1, 5, 1, 2),
                Block.makeCuboidShape(5, 1, 1, 6, 2, 2),
                Block.makeCuboidShape(6, 1, 1, 7, 2, 2),
                Block.makeCuboidShape(7, 2, 1, 9, 3, 2),
                Block.makeCuboidShape(9, 3, 1, 10, 4, 2),
                Block.makeCuboidShape(10, 4, 1, 11, 5, 2),
                Block.makeCuboidShape(11, 5, 1, 12, 6, 2),
                Block.makeCuboidShape(12, 6, 1, 13, 7, 2),
                Block.makeCuboidShape(12, 7, 1, 13, 12, 2),
                Block.makeCuboidShape(12, 12, 1, 13, 13, 2)
        ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();
    }

    private static VoxelShape getCachimbaEastShapes() {
        return Stream.of(
                Block.makeCuboidShape(5, 0, 5, 11, 1, 11),
                Block.makeCuboidShape(4, 1, 4, 12, 2, 12),
                Block.makeCuboidShape(4, 2, 4, 12, 3, 12),
                Block.makeCuboidShape(5, 3, 5, 11, 4, 11),
                Block.makeCuboidShape(6, 4, 6, 10, 5, 10),
                Block.makeCuboidShape(7, 5, 7, 9, 7, 9),
                Block.makeCuboidShape(7, 7, 7, 9, 8, 9),
                Block.makeCuboidShape(6, 8, 6, 10, 10, 10),
                Block.makeCuboidShape(7, 10, 7, 9, 14, 9),
                Block.makeCuboidShape(5, 14, 5, 11, 14, 11),
                Block.makeCuboidShape(7, 14, 7, 9, 16, 9),
                Block.makeCuboidShape(6, 16, 6, 10, 16, 10),
                Block.makeCuboidShape(7, 6, 6, 8, 7, 7),
                Block.makeCuboidShape(7, 7, 5, 8, 8, 6),
                Block.makeCuboidShape(7, 8, 4, 8, 9, 5),
                Block.makeCuboidShape(7, 8, 2, 8, 9, 4),
                Block.makeCuboidShape(7, 7, 1, 8, 8, 2),
                Block.makeCuboidShape(8, 6, 1, 9, 7, 2),
                Block.makeCuboidShape(9, 5, 1, 10, 6, 2),
                Block.makeCuboidShape(10, 4, 1, 11, 5, 2),
                Block.makeCuboidShape(11, 2, 1, 12, 4, 2),
                Block.makeCuboidShape(12, 1, 1, 13, 2, 2),
                Block.makeCuboidShape(13, 1, 2, 14, 2, 3),
                Block.makeCuboidShape(14, 0, 3, 15, 1, 4),
                Block.makeCuboidShape(14, 0, 4, 15, 1, 5),
                Block.makeCuboidShape(14, 1, 5, 15, 2, 6),
                Block.makeCuboidShape(14, 1, 6, 15, 2, 7),
                Block.makeCuboidShape(14, 2, 7, 15, 3, 9),
                Block.makeCuboidShape(14, 3, 9, 15, 4, 10),
                Block.makeCuboidShape(14, 4, 10, 15, 5, 11),
                Block.makeCuboidShape(14, 5, 11, 15, 6, 12),
                Block.makeCuboidShape(14, 6, 12, 15, 7, 13),
                Block.makeCuboidShape(14, 7, 12, 15, 12, 13),
                Block.makeCuboidShape(14, 12, 12, 15, 13, 13)
        ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();
    }

    private static VoxelShape getCachimbaWestShapes() {
        return Stream.of(
                Block.makeCuboidShape(5, 0, 5, 11, 1, 11),
                Block.makeCuboidShape(4, 1, 4, 12, 2, 12),
                Block.makeCuboidShape(4, 2, 4, 12, 3, 12),
                Block.makeCuboidShape(5, 3, 5, 11, 4, 11),
                Block.makeCuboidShape(6, 4, 6, 10, 5, 10),
                Block.makeCuboidShape(7, 5, 7, 9, 7, 9),
                Block.makeCuboidShape(7, 7, 7, 9, 8, 9),
                Block.makeCuboidShape(6, 8, 6, 10, 10, 10),
                Block.makeCuboidShape(7, 10, 7, 9, 14, 9),
                Block.makeCuboidShape(5, 14, 5, 11, 14, 11),
                Block.makeCuboidShape(7, 14, 7, 9, 16, 9),
                Block.makeCuboidShape(6, 16, 6, 10, 16, 10),
                Block.makeCuboidShape(8, 6, 9, 9, 7, 10),
                Block.makeCuboidShape(8, 7, 10, 9, 8, 11),
                Block.makeCuboidShape(8, 8, 11, 9, 9, 12),
                Block.makeCuboidShape(8, 8, 12, 9, 9, 14),
                Block.makeCuboidShape(8, 7, 14, 9, 8, 15),
                Block.makeCuboidShape(7, 6, 14, 8, 7, 15),
                Block.makeCuboidShape(6, 5, 14, 7, 6, 15),
                Block.makeCuboidShape(5, 4, 14, 6, 5, 15),
                Block.makeCuboidShape(4, 2, 14, 5, 4, 15),
                Block.makeCuboidShape(3, 1, 14, 4, 2, 15),
                Block.makeCuboidShape(2, 1, 13, 3, 2, 14),
                Block.makeCuboidShape(1, 0, 12, 2, 1, 13),
                Block.makeCuboidShape(1, 0, 11, 2, 1, 12),
                Block.makeCuboidShape(1, 1, 10, 2, 2, 11),
                Block.makeCuboidShape(1, 1, 9, 2, 2, 10),
                Block.makeCuboidShape(1, 2, 7, 2, 3, 9),
                Block.makeCuboidShape(1, 3, 6, 2, 4, 7),
                Block.makeCuboidShape(1, 4, 5, 2, 5, 6),
                Block.makeCuboidShape(1, 5, 4, 2, 6, 5),
                Block.makeCuboidShape(1, 6, 3, 2, 7, 4),
                Block.makeCuboidShape(1, 7, 3, 2, 12, 4),
                Block.makeCuboidShape(1, 12, 3, 2, 13, 4)
        ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();
    }
}
