package network.vonix.e4mccompat.mixin;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.permissions.Permission;
import net.minecraft.server.permissions.PermissionLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Server-only binary compatibility patch for e4mc 6.2.x on NeoForge
 * 26.1.2.93+. The original e4mc jar remains installed and the client pack is
 * unchanged. The redirect replaces only e4mc's stale command permission call.
 *
 * Verified target: link.e4mc.E4mcClient.lambda$registerCommands$0
 * invoking CommandSourceStack.hasPermission(I)Z. Confirmed byte-identical by
 * javap in e4mc 6.2.1, 6.2.2 and 6.2.3 (instruction offset 21 in each).
 */
@Mixin(targets = "link.e4mc.E4mcClient")
public abstract class E4mcClientMixin {
    @Redirect(
            method = "lambda$registerCommands$0(Lnet/minecraft/commands/CommandSourceStack;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/commands/CommandSourceStack;hasPermission(I)Z"
            ),
            require = 1
    )
    private static boolean vonix$permissionBridge(CommandSourceStack source, int legacyLevel) {
        return source.permissions().hasPermission(
                new Permission.HasCommandLevel(PermissionLevel.byId(legacyLevel))
        );
    }
}
