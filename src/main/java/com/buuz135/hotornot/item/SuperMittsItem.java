package com.buuz135.hotornot.item;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import com.buuz135.hotornot.HotOrNot;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SuperMittsItem extends Item implements IBauble {
    public SuperMittsItem() {
        setRegistryName(HotOrNot.MOD_ID, "supermitts");
        setMaxStackSize(1);
        setMaxDamage(0);
        setTranslationKey(HotOrNot.MOD_ID + ".supermitts");
        setCreativeTab(CreativeTabs.TOOLS);
    }
    @Override
    public BaubleType getBaubleType(ItemStack itemStack) {
        return BaubleType.RING;
    }

    public EnumRarity getRarity(ItemStack stack)
    {
        return EnumRarity.EPIC;
    }
}

