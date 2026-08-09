# BMC6 e4mc Compatibility Patch

Server-side compatibility patch for **Better MC 6** on **Minecraft 26.1.2 / NeoForge 26.1.2.93 / Java 25**.

## What this fixes

Better MC 6's e4mc integration failed on the 26.1.2 NeoForge server because e4mc 6.2.1 was compiled against an older command-permission API. Its command registration path still called the removed legacy `CommandSourceStack.hasPermission(int)` method. On the newer server API, that stale call could prevent the e4mc command path from functioning and could surface as a `NoSuchMethodError` during the relevant runtime path.

## Fix

This patch leaves the original e4mc mod unchanged and adds a narrowly scoped server-side Mixin redirect for:

```text
E4mcClient.lambda$registerCommands$0(CommandSourceStack)
```

Only the stale integer permission check is redirected. The legacy permission level is converted to NeoForge 26.1.2's permission representation:

```java
source.permissions().hasPermission(
    new Permission.HasCommandLevel(PermissionLevel.byId(legacyLevel))
)
```

No client-pack modification is required. Install the original e4mc 6.2.1 JAR and this compatibility patch in the server `mods/` directory.

## Artifacts

- `artifacts/e4mc_compat_patch-0.1.0.jar`
- SHA-256: `35383856bbc25f492f2aee1e37fe61dd0c6bceb753ff9c35f6694b631c855648`
- Size: 3,009 bytes

The base e4mc artifact used for compatibility work was pinned by SHA-256:

```text
41ee0d18fafe39f7422712b342248ebffbf392910bdb8d160c928e575edb29f0
```

## Source of the error

The affected reference environment was the **Better MC 6** modpack:

https://www.curseforge.com/minecraft/modpacks/better-mc-bmc6

The incompatibility was between the pack's e4mc 6.2.1 binary and the newer NeoForge command-permission API—not a world, port, or client-pack configuration problem.

## Verification record

The release artifact was built after changing the internal mod metadata from `0.1.0-candidate` to `0.1.0` with:

```text
./gradlew clean build --no-daemon --stacktrace
```

Build result: `BUILD SUCCESSFUL`.

A disposable dedicated-server boot reached `Done` with e4mc 6.2.1 present and did not show `NoSuchMethodError` or `FATAL` in the fresh log. The owner subsequently confirmed that BMC6 worked with this patch during the owner-run client test.

## Installation

1. Keep the pack's original `e4mc-neoforge-6.2.1.jar` in `mods/`.
2. Copy `e4mc_compat_patch-0.1.0.jar` into the same server `mods/` directory.
3. Keep the client modpack unchanged.
4. Start the server and use the normal e4mc client flow.

This patch is intended for the pinned BMC6 / NeoForge target only. Do not assume compatibility with other Minecraft or NeoForge versions.

## License and attribution

This repository contains the Vonix.Network compatibility patch source and compiled artifact. The original e4mc mod remains the property of its original authors and is not redistributed here. Respect the original e4mc license and attribution requirements when using the base mod.
