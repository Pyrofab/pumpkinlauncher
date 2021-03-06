package pumpkinlauncher.item;

import com.google.common.collect.Lists;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFireworkCharge;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ItemPumpkinAmmo extends Item {

    public ItemPumpkinAmmo() {
        setRegistryName("pumpkinammo");
        setUnlocalizedName("pumpkinammo");
        setCreativeTab(CreativeTabs.COMBAT);
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("deprecation")
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        NBTTagCompound nbttagcompound = stack.getTagCompound();

        if (nbttagcompound != null) {
            if (nbttagcompound.hasKey("power") && nbttagcompound.getByte("power") > 0) {
                tooltip.add(I18n.translateToLocal("item.pumpkinammo.power") + " " + nbttagcompound.getByte("power"));
            }
            if (nbttagcompound.hasKey("bounceAmount") && nbttagcompound.getByte("bounceAmount") > 0) {
                tooltip.add(I18n.translateToLocal("item.pumpkinammo.bounce") + " " + nbttagcompound.getByte("bounceAmount"));
            }
            if (nbttagcompound.hasKey("isFiery") && nbttagcompound.getBoolean("isFiery")) {
                tooltip.add(I18n.translateToLocal("item.pumpkinammo.isfiery"));
            }
            if (nbttagcompound.hasKey("canDestroyBlocks") && !nbttagcompound.getBoolean("canDestroyBlocks")) {
                tooltip.add(I18n.translateToLocal("item.pumpkinammo.nogriefing"));
            }
            if (nbttagcompound.hasKey("fireworks")) {
                NBTTagCompound fireworksCompound = (NBTTagCompound) nbttagcompound.getTag("fireworks");
                if (fireworksCompound.hasKey("Flight", 99)) {
                    tooltip.add(I18n.translateToLocal("item.fireworks.flight") + " " + fireworksCompound.getByte("Flight"));
                }

                NBTTagList nbttaglist = fireworksCompound.getTagList("Explosions", 10);
                if (!nbttaglist.hasNoTags()) {
                    for (int i = 0; i < nbttaglist.tagCount(); ++i) {
                        NBTTagCompound subCompound = nbttaglist.getCompoundTagAt(i);
                        List<String> list = Lists.newArrayList();
                        ItemFireworkCharge.addExplosionInfo(subCompound, list);

                        if (!list.isEmpty()) {
                            for (int j = 1; j < list.size(); ++j) {
                                list.set(j, "  " + list.get(j));
                            }
                            tooltip.addAll(list);
                        }
                    }
                }
            }
        }
    }
}
