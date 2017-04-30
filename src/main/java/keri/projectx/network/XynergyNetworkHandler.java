package keri.projectx.network;

import com.google.common.collect.Maps;
import net.minecraft.util.math.BlockPos;

import java.util.List;
import java.util.Map;

public class XynergyNetworkHandler {

    public static final XynergyNetworkHandler INSTANCE = new XynergyNetworkHandler();
    private Map<BlockPos, List<BlockPos>> connections = Maps.newHashMap();

}
