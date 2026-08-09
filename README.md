# e4mc NeoForge Compatibility Patch

Server-side compatibility patch for **e4mc 6.2.1** on **Minecraft 26.1.2 / NeoForge 26.1.2.93 / Java 25**.

## Affected components

This was not a conflict between two gameplay mods. The compatibility boundary is between:

1. **e4mc 6.2.1** — mod ID `e4mc`, specifically `link.e4mc.E4mcClient`.
2. **Minecraft 26.1.2 / NeoForge 26.1.2.93** — the command-permission API consumed by e4mc. NeoForge is the loader/platform, not a second bundled mod.

The problem was reproduced in Better MC 6:

https://www.curseforge.com/minecraft/modpacks/better-mc-bmc6

The same failure can affect other packs that combine e4mc 6.2.1 with this Minecraft/NeoForge API combination.

## What this fixes

The e4mc command registration path still called the removed legacy method:

```text
CommandSourceStack.hasPermission(int)
```

On NeoForge 26.1.2.93, that stale binary call caused command-tree delivery to fail when a player joined, producing:

```text
java.lang.NoSuchMethodError:
  boolean net.minecraft.commands.CommandSourceStack.hasPermission(int)
```

The original failure path was:

```text
Player joins
  -> NeoForge sends the command tree
  -> e4mc E4mcClient.lambda$registerCommands$0(CommandSourceStack)
  -> stale CommandSourceStack.hasPermission(int) call
  -> NoSuchMethodError
  -> server crash during command-tree delivery
```

## Fix

This patch leaves e4mc unchanged and adds a narrowly scoped server-side Mixin redirect for:

```text
E4mcClient.lambda$registerCommands$0(CommandSourceStack)
```

It maps the old integer permission level to the current NeoForge permission API:

```java
source.permissions().hasPermission(
    new Permission.HasCommandLevel(PermissionLevel.byId(legacyLevel))
)
```

No client-pack modification is required. Install the original e4mc 6.2.1 JAR and this compatibility patch in the server `mods/` directory.

## Artifact

- File: `e4mc_compat_patch-0.1.0.jar`
- SHA-256: `35383856bbc25f492f2aee1e37fe61dd0c6bceb753ff9c35f6694b631c855648`
- Size: 3,009 bytes

The original e4mc artifact is not redistributed. Its pinned SHA-256 in the reproduced environment was:

```text
41ee0d18fafe39f7422712b342248ebffbf392910bdb8d160c928e575edb29f0
```

## Verification

The release artifact was rebuilt after correcting its internal metadata from `0.1.0-candidate` to `0.1.0`:

```text
./gradlew clean build --no-daemon --stacktrace
BUILD SUCCESSFUL
```

The owner confirmed that BMC6 works with the patch during the owner-run client test.

## Installation

1. Keep the original `e4mc-neoforge-6.2.1.jar` in `mods/`.
2. Add `e4mc_compat_patch-0.1.0.jar` to the same server `mods/` directory.
3. Keep the client modpack unchanged.
4. Start the server and use the normal e4mc client flow.

This release is pinned to e4mc 6.2.1 and NeoForge 26.1.2.93. Do not assume compatibility with other versions without testing.

## License and attribution

The patch source and artifact are Vonix.Network work. e4mc is a separate third-party mod and must be obtained from its original distribution under its own license and attribution requirements.
