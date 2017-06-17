package keri.projectx.multiblock;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class StructurePartDefinitions {

    private Map<Character, IBlockState> definitions = Maps.newHashMap();

    private StructurePartDefinitions(Map<Character, IBlockState> definitions){
        this.definitions = definitions;
    }

    public ImmutableMap<Character, IBlockState> getDefinitions(){
        return ImmutableMap.copyOf(this.definitions);
    }

    public static class Builder {

        private Map<Character, IBlockState> aliases = Maps.newHashMap();

        @SuppressWarnings("deprecation")
        public Builder addDefinition(char c, String definition){
            if(definition.contains(",")){
                int blockStart = 0;
                int blockEnd = definition.indexOf(',');
                int metaStart = blockEnd + 1;
                int metaEnd = definition.length();
                String blockString = definition.substring(blockStart, blockEnd);
                String metaString = definition.substring(metaStart, metaEnd);
                Block block = Block.getBlockFromName(blockString);

                if(block == null){
                    throw new IllegalArgumentException(String.format("Could not find block '%s'!", blockString));
                }

                if(!StringUtils.isNumeric(metaString)){
                    throw new IllegalArgumentException(String.format("'%d' is no valid number for metadata!", metaString));
                }

                int meta = Integer.parseInt(metaString);
                this.aliases.put(c, block.getStateFromMeta(meta));
            }
            else{
                Block block = Block.getBlockFromName(definition);

                if(block == null){
                    throw new IllegalArgumentException(String.format("Could not find block '%s'", definition));
                }

                this.aliases.put(c, block.getDefaultState());
            }

            return this;
        }

        public Builder addDefinition(char c, IBlockState definition){
            if(definition == null){
                throw new IllegalArgumentException("Structure part definition can't be null!");
            }

            this.aliases.put(c, definition);
            return this;
        }

        public StructurePartDefinitions build(){
            return new StructurePartDefinitions(this.aliases);
        }

    }

}
