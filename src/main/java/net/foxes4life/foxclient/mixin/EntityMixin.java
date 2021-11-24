package net.foxes4life.foxclient.mixin;

import net.foxes4life.foxclient.MainClient;
import net.foxes4life.foxclient.client.Freelook;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin implements Freelook {
    private float cameraY;
    private float cameraX;

    @Inject(method = "changeLookDirection", at = @At("HEAD"), cancellable = true)
    public void changeLookDirection(double cursorDeltaX, double cursorDeltaY, CallbackInfo ci) {
        //noinspection ConstantConditions
        if(!((Object) this instanceof ClientPlayerEntity)) return;
        if(MainClient.freeLook.isPressed()) {
            float f = (float)cursorDeltaY * 0.15F;
            float g = (float)cursorDeltaX * 0.15F;

            this.cameraY = MathHelper.clamp(this.cameraY + f, -90F, 90F);
            this.cameraX += g;
            ci.cancel();
        }
    }

    @Override
    public float getCameraX() {
        return this.cameraX;
    }

    @Override
    public float getCameraY() {
        return this.cameraY;
    }

    @Override
    public void setCameraX(float x) {
        this.cameraX = x;
    }

    @Override
    public void setCameraY(float y) {
        this.cameraY = y;
    }
}
