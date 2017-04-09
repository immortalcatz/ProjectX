package keri.projectx.init;

import keri.ninetaillib.config.ConfigManagerBase;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;

public class ProjectXConfig extends ConfigManagerBase {

    public ProjectXConfig(FMLPreInitializationEvent event) {
        super(event);
    }

    @Override
    public ArrayList<Pair<String, String>> getCategories() {
        return null;
    }

    @Override
    public void getConfigFlags(Configuration config) {

    }

}
