package keri.projectx.init;

import com.google.common.collect.Lists;
import keri.ninetaillib.config.ConfigManagerBase;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;

public class ProjectXConfig extends ConfigManagerBase {

    private final String commentUseFastItemRendering = "Fast Item Rendering";
    public boolean useFastItemRendering;

    public ProjectXConfig(FMLPreInitializationEvent event) {
        super(event);
    }

    @Override
    public ArrayList<Pair<String, String>> getCategories() {
        return Lists.newArrayList(
                Pair.of(this.commentUseFastItemRendering, "Enable/disable fast item rendering. Should improve performace on older machines.")
        );
    }

    @Override
    public void getConfigFlags(Configuration config) {
        this.useFastItemRendering = config.get(this.commentUseFastItemRendering, "useFastItemRendering", false).getBoolean();
    }

}
