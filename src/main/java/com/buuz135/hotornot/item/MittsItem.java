package com.buuz135.hotornot.item;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import baubles.api.cap.IBaublesItemHandler;
import com.buuz135.hotornot.HotOrNot;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

import static baubles.api.BaubleType.RING;

public class MittsItem extends Item implements IBauble {

    public static final String NBTTAG_DURABILITY = "Durability";
    private static final int MaxDurability = 1000;
    private static boolean checkPopup = false;

    public MittsItem() {
        setRegistryName(HotOrNot.MOD_ID, "mitts");
        setMaxStackSize(1);
        setMaxDamage(0);
        setHasSubtypes(true);
        setUnlocalizedName(HotOrNot.MOD_ID + ".mitts");
        setCreativeTab(CreativeTabs.TOOLS);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        createOrInitNBTTag(stack);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (!isInCreativeTab(tab)) {
            return;
        }

        for (int i = 0; i < 2; i++) {
            items.add(new ItemStack(this, 1, i));
        }
    }

    private void createOrInitNBTTag(ItemStack pItemStack) {
        if (pItemStack.getTagCompound() == null) {
            pItemStack.setTagCompound(new NBTTagCompound());
            pItemStack.getTagCompound().setInteger(NBTTAG_DURABILITY, MaxDurability);
        }
    }

    public static int getNBTDurability(ItemStack pItemStack) {
        if(pItemStack.getTagCompound() == null)
            {
                pItemStack.setTagCompound(new NBTTagCompound());
                pItemStack.getTagCompound().setInteger(NBTTAG_DURABILITY, MaxDurability);
                return pItemStack.getTagCompound().getInteger(NBTTAG_DURABILITY);
            }
        return pItemStack.getTagCompound().getInteger(NBTTAG_DURABILITY);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack pItemStack, @Nullable World worldIn, List<String> list, ITooltipFlag flagIn) {
        list.add("Protecting your fingers since 1890");
        list.add(String.format("Durability: %d/%d", getNBTDurability(pItemStack), MaxDurability));
        if (pItemStack.getTagCompound().getInteger(NBTTAG_DURABILITY) <= 1) {
            list.add("This glove is too damaged to protect you. You need to repair it");
        }
    }
    
    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemStack)  {
        return RING;
    }

    @Override
    public void onWornTick(ItemStack itemStack, EntityLivingBase entity) {
        if (checkPopup) {
            IBaublesItemHandler baublesInv = BaublesApi.getBaublesHandler((EntityPlayer) entity);
            ItemStack ring1 = baublesInv.getStackInSlot(1);
            ItemStack ring2 = baublesInv.getStackInSlot(2);

            if (!(ring1.getItem() instanceof MittsItem) || !(ring2.getItem() instanceof MittsItem)) {
                return;
            }

            if (ring1.getItemDamage() != 0 && ring2.getItemDamage() != 1) {
                if(!entity.world.isRemote)
                entity.sendMessage(new TextComponentString(TextFormatting.RED.toString() + TextFormatting.BOLD + I18n.translateToLocal("item.hotornot.mitts.tooltip")));
                entity.playSound(SoundEvents.ENTITY_VILLAGER_NO, 1, 1);
            }

            checkPopup = false;
        }
    }

    @Override
    public void onEquipped(ItemStack arg0, EntityLivingBase entity) {
        checkPopup = true;
    }
}
