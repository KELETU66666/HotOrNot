package com.buuz135.hotornot.client;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.common.Config;
import com.buuz135.hotornot.HotOrNot;
import com.buuz135.hotornot.proxy.CommonProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class LayerGloves implements LayerRenderer<EntityPlayer> {
    protected ModelPlayer modelPlayer;
    public final ResourceLocation mittsLeft;
    public final ResourceLocation mittsRight;
    //public final ResourceLocation superMitts;

    public LayerGloves(boolean smallArms, RenderPlayer renderPlayer) {
        this.modelPlayer = renderPlayer.getMainModel();
        mittsLeft = new ResourceLocation(HotOrNot.MOD_ID, "textures/layer/mitts_left_" + (smallArms ? "slim" : "normal") + ".png");
        mittsRight = new ResourceLocation(HotOrNot.MOD_ID, "textures/layer/mitts_right_" + (smallArms ? "slim" : "normal") + ".png");
        //superMitts = new ResourceLocation(HotOrNot.MOD_ID, "textures/layer/super_mitts_" + (smallArms ? "slim" : "normal") + ".png");
    }

    @Override
    public final void doRenderLayer(@Nonnull EntityPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if(!Config.renderBaubles || player.getActivePotionEffect(MobEffects.INVISIBILITY) != null) return;

        GlStateManager.enableLighting();
        GlStateManager.enableRescaleNormal();

        GlStateManager.pushMatrix();
        renderArm(EnumHandSide.LEFT, player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        renderArm(EnumHandSide.RIGHT, player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        GlStateManager.popMatrix();
    }

    private void renderArm(EnumHandSide hand, @Nonnull EntityPlayer player, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (!setTextures(player, hand)) return;

        if (hand == EnumHandSide.LEFT) {
            modelPlayer.bipedLeftArm.showModel = true;
            modelPlayer.bipedLeftArmwear.showModel = true;
            modelPlayer.bipedRightArm.showModel = false;
            modelPlayer.bipedRightArmwear.showModel = false;
        } else {
            modelPlayer.bipedLeftArm.showModel = false;
            modelPlayer.bipedLeftArmwear.showModel = false;
            modelPlayer.bipedRightArm.showModel = true;
            modelPlayer.bipedRightArmwear.showModel = true;
        }

        modelPlayer.render(player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

        if (hand == EnumHandSide.LEFT) {
            modelPlayer.bipedLeftArmwear.showModel = false;
            modelPlayer.bipedLeftArm.showModel = false;
        } else {
            modelPlayer.bipedRightArmwear.showModel = false;
            modelPlayer.bipedRightArm.showModel = false;
        }
    }

    private boolean setTextures(EntityPlayer player, EnumHandSide hand) {
        ItemStack stack = BaublesApi.getBaublesHandler(player).getStackInSlot(BaubleType.RING.getValidSlots()[hand == EnumHandSide.LEFT ? 0 : 1]);
        ResourceLocation textures = getTextures(stack);
        if (textures != null) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(textures);
            return true;
        }
        return false;
    }

    private @Nullable ResourceLocation getTextures(ItemStack stack) {
        if (stack.getItem() == CommonProxy.MITTS && stack.getItemDamage() == 0) {
            return mittsLeft;
        } else if (stack.getItem() == CommonProxy.MITTS && stack.getItemDamage() == 1) {
            return mittsRight;
        } /*else if (stack.getItem() == CommonProxy.SUPERMITTS) {
            return superMitts;
        }*/
        return null;
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}