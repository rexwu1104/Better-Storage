package com.snow_kitten.better_storage.block.custom;

import com.snow_kitten.better_storage.block.entity.BlockEntities;
import com.snow_kitten.better_storage.block.entity.DataHardwareBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class DataHardwareBlock extends BaseEntityBlock {
    public static final BooleanProperty DRIVER_1 = BooleanProperty.create("driver_1");
    public static final BooleanProperty DRIVER_2 = BooleanProperty.create("driver_2");
    public static final BooleanProperty DRIVER_3 = BooleanProperty.create("driver_3");
    public static final BooleanProperty DRIVER_4 = BooleanProperty.create("driver_4");
    public static final BooleanProperty DRIVER_5 = BooleanProperty.create("driver_5");
    public static final BooleanProperty DRIVER_6 = BooleanProperty.create("driver_6");
    public static final BooleanProperty DRIVER_7 = BooleanProperty.create("driver_7");
    public static final BooleanProperty DRIVER_8 = BooleanProperty.create("driver_8");
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public DataHardwareBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState()
                .setValue(DRIVER_1, false)
                .setValue(DRIVER_2, false)
                .setValue(DRIVER_3, false)
                .setValue(DRIVER_4, false)
                .setValue(DRIVER_5, false)
                .setValue(DRIVER_6, false)
                .setValue(DRIVER_7, false)
                .setValue(DRIVER_8, false));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, DRIVER_1, DRIVER_2, DRIVER_3, DRIVER_4, DRIVER_5, DRIVER_6, DRIVER_7, DRIVER_8);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DataHardwareBlockEntity(pos, state);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean moving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof  DataHardwareBlockEntity) {
                ((DataHardwareBlockEntity) entity).drops();
            }
        }

        super.onRemove(state, level, pos, newState, moving);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide()) {
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof DataHardwareBlockEntity) {
                NetworkHooks.openScreen((ServerPlayer) player, (DataHardwareBlockEntity) entity, pos);
            } else {
                throw new IllegalStateException("Container provider is missing");
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, BlockEntities.DATA_HARDWARE_ENTITY.get(),
                DataHardwareBlockEntity::tick);
    }
}
