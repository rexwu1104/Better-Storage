package com.snow_kitten.better_storage.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class DataCableBlockEntity extends BlockEntity {
    public DataCableBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntities.DATA_CABLE_ENTITY.get(), pos, state);
    }
}
