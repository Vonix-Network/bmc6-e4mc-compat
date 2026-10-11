# e4mc NeoForge Compatibility Patch

Server-side compatibility patch for the **e4mc 6.2.x** line on **Minecraft 26.1.2 / NeoForge 26.1.2.93+ / Java 25**.

## Affected components

This was not a conflict between two gameplay mods. The compatibility boundary is between:

1. **e4mc 6.2.x** — mod ID `e4mc`, specifically `link.e4mc.E4mcClient`.
2. **Minecraft 26.1.2 / NeoForge 26.1.2.93+** — the command-permission API consumed by e4mc. NeoForge is the loader/platform, not a second bundled mod.

The problem was reproduced in Better MC 6:

https://www.curseforge.com/minecraft/modpacks/better-mc-bmc6

The same failure can affect other packs that combine e4mc 6.2.x with this Minecraft/NeoForge API combination.

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

No client-pack modification is required. Install the original e4mc 6.2.x JAR and this compatibility patch in the server `mods/` directory.

## Artifact

- File: `e4mc_compat_patch-1.0.0.jar`
- SHA-256: `ef6490f462dfae30c712c4f5c4899ffed12dd48ea084ffd0bcd8be9eba0d42d9`
- Size: 3,029 bytes

The original e4mc artifact is not redistributed. Its pinned SHA-256 in the reproduced environment was:

```text
41ee0d18fafe39f7422712b342248ebffbf392910bdb8d160c928e575edb29f0
```

## Version coverage

Starting with v1.0.0 the declared e4mc range is `[6.2.0,6.3)`. The redirect target was
verified with `javap` in every published e4mc 6.2.x NeoForge build; all three expose
`lambda$registerCommands$0(CommandSourceStack)Z` invoking
`CommandSourceStack.hasPermission(I)Z` at the same instruction offset, so one range
covers the line.

## Verification

Built from pinned source with the pinned Gradle wrapper:

```text
./gradlew clean build --no-daemon
BUILD SUCCESSFUL
```

Validated live on Better MC 6 (`58bc95ef`, NeoForge 26.1.2.109, e4mc 6.2.2):

```text
e4mc Compatibility Patch 1.0.0 (e4mc_compat_patch)
Done (1.958s)! For help, type "help"
```

The remote JAR SHA-256 was read back from the server and matched the local build hash.

## Installation

1. Keep the original `e4mc-neoforge-6.2.x.jar` in `mods/`.
2. Add `e4mc_compat_patch-1.0.0.jar` to the same server `mods/` directory.
3. Remove any older `e4mc_compat_patch-*.jar` — installing two versions at once is a duplicate-modId conflict.
4. Keep the client modpack unchanged.
5. Start the server and use the normal e4mc client flow.

The declared range covers e4mc 6.2.0 through 6.2.x on NeoForge 26.1.2.93 or later. Do not assume compatibility with a future 6.3 line without testing.

## License and attribution

The patch source and artifact are Vonix.Network work. e4mc is a separate third-party mod and must be obtained from its original distribution under its own license and attribution requirements.
