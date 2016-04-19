package buildcraft.transport;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import buildcraft.core.BCMisc;
import buildcraft.core.BuildCraftCore;
import buildcraft.lib.CreativeTabManager;
import buildcraft.lib.RegistryHelper;

@Mod(modid = BuildCraftTransport.MODID, name = "BuildCraft|Transport", dependencies = "required-after:buildcraftcore", version = BCMisc.VERSION)
public class BuildCraftTransport {
    public static final String MODID = "buildcrafttransport";

    @Mod.Instance(MODID)
    public static BuildCraftTransport INSTANCE = null;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent evt) {
        RegistryHelper.useOtherModConfigFor(MODID, BuildCraftCore.MODID);

        CreativeTabManager.createTab("buildcraft.pipe");

        BCTransportItems.preInit();
        // BCTransportBlocks.preInit();

        // CreativeTabManager.setItem("buildcraft.pipe", BCCoreItems.wrench);
        NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, TransportProxy_BC8.getProxy());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent evt) {
        TransportProxy_BC8.getProxy().fmlInit();
        BCTransportRecipes.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent evt) {

    }
}
