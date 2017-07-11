package keri.projectx.util;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by Adam on 7/5/2017.
 */
public class XFluidUtil {
    @CapabilityInject(IFluidHandler.class)
    private static final Capability<IFluidHandler> FLUID_HANDLER = null;

    public static
    @Nonnull
    Capability<IFluidHandler> getFluidCapability() {
        return FLUID_HANDLER;
    }

    public static
    @Nullable
    IFluidHandler getFluidHandlerCapability(@Nullable ICapabilityProvider provider, @Nullable EnumFacing side) {
        if (provider != null && provider.hasCapability(getFluidCapability(), side)) {
            return provider.getCapability(getFluidCapability(), side);
        }
        return null;
    }

    public static
    @Nullable
    IFluidHandler getFluidHandlerCapability(@Nonnull ItemStack stack) {
        return getFluidHandlerCapability(stack, null);
    }

    public static
    @Nonnull
    FluidAndStackResult tryFillContainer(@Nonnull ItemStack target, @Nullable FluidStack source) {
        if (target.isEmpty() || source == null || source.getFluid() == null || source.amount <= 0) {
            return new FluidAndStackResult(ItemStack.EMPTY, null, target, source);
        }

        ItemStack filledStack = target.copy();
        filledStack.setCount(1);
        IFluidHandler handler = FluidUtil.getFluidHandler(target);
        if (handler == null) {
            return new FluidAndStackResult(ItemStack.EMPTY, null, target, source);
        }

        int filledAmount = handler.fill(source.copy(), true);
        if (filledAmount <= 0 || filledAmount > source.amount) {
            return new FluidAndStackResult(ItemStack.EMPTY, null, target, source);
        }

        if (handler instanceof IFluidHandlerItem) {
            filledStack = ((IFluidHandlerItem) handler).getContainer();
        }

        FluidStack resultFluid = source.copy();
        resultFluid.amount = filledAmount;

        ItemStack remainderStack = target.copy();
        remainderStack.shrink(1);

        FluidStack remainderFluid = source.copy();
        remainderFluid.amount -= filledAmount;
        if (remainderFluid.amount <= 0) {
            remainderFluid = null;
        }

        return new FluidAndStackResult(filledStack, resultFluid, remainderStack, remainderFluid);

    }

    public static class FluidAndStackResult {
        public final
        @Nonnull
        FluidAndStack result;
        public final
        @Nonnull
        FluidAndStack remainder;

        public FluidAndStackResult(@Nonnull FluidAndStack result, @Nonnull FluidAndStack remainder) {
            this.result = result;
            this.remainder = remainder;
        }

        public FluidAndStackResult(FluidStack fluidStackResult, @Nonnull ItemStack itemStackResult, FluidStack fluidStackRemainder,
                                   @Nonnull ItemStack itemStackRemainder) {
            this.result = new FluidAndStack(fluidStackResult, itemStackResult);
            this.remainder = new FluidAndStack(fluidStackRemainder, itemStackRemainder);
        }

        public FluidAndStackResult(@Nonnull ItemStack itemStackResult, FluidStack fluidStackResult, @Nonnull ItemStack itemStackRemainder,
                                   FluidStack fluidStackRemainder) {
            this.result = new FluidAndStack(fluidStackResult, itemStackResult);
            this.remainder = new FluidAndStack(fluidStackRemainder, itemStackRemainder);
        }
    }

    public static class FluidAndStack {
        public final
        @Nullable
        FluidStack fluidStack;
        public final
        @Nonnull
        ItemStack itemStack;

        public FluidAndStack(@Nullable FluidStack fluidStack, @Nonnull ItemStack itemStack) {
            this.fluidStack = fluidStack;
            this.itemStack = itemStack;
        }

        public FluidAndStack(@Nonnull ItemStack itemStack, @Nullable FluidStack fluidStack) {
            this.fluidStack = fluidStack;
            this.itemStack = itemStack;
        }
    }

}
