package pumpkinlauncher;

import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import pumpkinlauncher.item.ItemPumpkinAmmo;
import pumpkinlauncher.item.ItemPumpkinLauncher;
import pumpkinlauncher.item.RecipePumpkinAmmo;
import pumpkinlauncher.proxy.CommonProxy;

@SuppressWarnings({"unused", "WeakerAccess"})
@Mod(modid=PumpkinLauncher.MODID, name=PumpkinLauncher.MODNAME, version=PumpkinLauncher.MODVERSION, updateJSON="https://github.com/ochotonida/pumpkinlauncher/blob/master/update.json")
public class PumpkinLauncher {

    public static final String MODID = "pumpkinlauncher";
    public static final String MODNAME = "Jack-O'-Launcher";
    public static final String MODVERSION = "1.12.2-1.3.0";

    public static final Item PUMPKIN_LAUNCHER = new ItemPumpkinLauncher();
    public static final Item PUMPKIN_AMMO = new ItemPumpkinAmmo();

    @Mod.Instance
    public static PumpkinLauncher instance;

    @SidedProxy(serverSide = "pumpkinlauncher.proxy.CommonProxy", clientSide = "pumpkinlauncher.proxy.ClientProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @net.minecraftforge.fml.common.Mod.EventBusSubscriber
    public static class RegistrationHandler {

        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Item> event) {
            IForgeRegistry<Item> registry = event.getRegistry();
            registry.register(PUMPKIN_LAUNCHER);
            registry.register(PUMPKIN_AMMO);
        }

        @SubscribeEvent
        public static void registerItemModels(ModelRegistryEvent event) {
            proxy.registerItemRenderer(PUMPKIN_LAUNCHER, 0, "pumpkinlauncher");
            proxy.registerItemRenderer(PUMPKIN_AMMO, 0, "pumpkinammo");
        }

        @SubscribeEvent
        public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
            event.getRegistry().register(new RecipePumpkinAmmo());
        }
    }
}
