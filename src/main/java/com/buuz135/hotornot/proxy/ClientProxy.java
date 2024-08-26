package com.buuz135.hotornot.proxy;

import com.buuz135.hotornot.client.LayerGloves;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Map;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        addRenderLayers();
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

    @Override
    public void modelRegistryEvent(ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(MITTS, 0, new ModelResourceLocation(MITTS.getRegistryName() + "left", "inventory"));
        ModelLoader.setCustomModelResourceLocation(MITTS, 1, new ModelResourceLocation(MITTS.getRegistryName() + "right", "inventory"));
        ModelLoader.setCustomModelResourceLocation(SUPERMITTS, 0, new ModelResourceLocation(SUPERMITTS.getRegistryName(), "inventory"));
    }

    private static void addRenderLayers() {
        Map<String, RenderPlayer> skinMap = Minecraft.getMinecraft().getRenderManager().getSkinMap();

        addLayersToSkin(skinMap.get("default"), false);
        addLayersToSkin(skinMap.get("slim"), true);
    }

    private static void addLayersToSkin(RenderPlayer renderPlayer, boolean slim) {
        renderPlayer.addLayer(new LayerGloves(slim, renderPlayer));
    }
}
