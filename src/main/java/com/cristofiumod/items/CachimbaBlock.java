package com.cristofiumod.items;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
// import net.minecraft.creativetab.CreativeTabs; ¿?¿?¿?¿?¿?

public class CachimbaBlock extends Block {
    public CachimbaBlock() {
        super(getBlockProperties());
        // this.setCreativeTab(¿?¿?¿?)
        // this.soundType = SoundType.GLASS;
    }

    private static Properties getBlockProperties() {
        AbstractBlock.Properties properties = Properties.create(Material.GLASS);
        properties.sound(SoundType.GLASS);

        return properties;
    }
}
