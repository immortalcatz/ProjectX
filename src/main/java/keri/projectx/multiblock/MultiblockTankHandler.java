package keri.projectx.multiblock;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.util.math.BlockPos;

import java.util.List;
import java.util.Map;

public class MultiblockTankHandler {

    public static final MultiblockTankHandler INSTANCE = new MultiblockTankHandler();
    private Map<BlockPos, List<BlockPos>> tankMap = Maps.newHashMap();

    public void addMultiblockTank(BlockPos masterPos, List<BlockPos> subBlocks){
        if(masterPos != null){
            if(subBlocks != null && !subBlocks.isEmpty()){
                this.tankMap.put(masterPos, subBlocks);
            }
            else{
                throw new IllegalArgumentException("Subblock list can't be null or empty!");
            }
        }
        else{
            throw new IllegalArgumentException("Master position can't be null!");
        }
    }

    public ImmutableMap<BlockPos, List<BlockPos>> getTankMap(){
        return ImmutableMap.copyOf(this.tankMap);
    }

}
