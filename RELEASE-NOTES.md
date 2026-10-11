# BMC6 e4mc Compatibility Patch v1.0.0

Server-side compatibility patch for the e4mc **6.2.x** line on **Minecraft 26.1.2 / NeoForge 26.1.2.93+ / Java 25**.

## Included file

`e4mc_compat_patch-1.0.0.jar`

SHA-256:

`ef6490f462dfae30c712c4f5c4899ffed12dd48ea084ffd0bcd8be9eba0d42d9`

## Changes from v0.1.0

- **Dependency range widened from `[6.2.1,6.2.2)` to `[6.2.0,6.3)`.**
  The v0.1.0 range excluded e4mc 6.2.2, which is what Better MC 6 now ships.
  NeoForge treats a failed range check as a hard load error, so the server
  refused to start:
  `Mod e4mc_compat_patch requires e4mc 6.2.1 or above, and below 6.2.2.
   Currently, e4mc is 6.2.2`
- Version promoted to `1.0.0` and confirmed working on the live Better MC 6 server.

## Version coverage

The redirect target was verified with `javap` against every published e4mc 6.2.x
NeoForge build. All three are identical at the injection site:

| e4mc | `lambda$registerCommands$0(CommandSourceStack)Z` | `CommandSourceStack.hasPermission(I)Z` |
|---|---|---|
| 6.2.1 | present | `invokevirtual` at offset 21 |
| 6.2.2 | present | `invokevirtual` at offset 21 |
| 6.2.3 | present | `invokevirtual` at offset 21 |

Because the target shape is unchanged across the line, the declared range covers
all of 6.2.x rather than pinning a single patch release.

## Compatibility

- Better MC 6
- Minecraft 26.1.2
- NeoForge 26.1.2.93 or later (validated live on 26.1.2.109)
- Java 25
- Original e4mc 6.2.x required separately

The original e4mc JAR is not included or modified. No client-pack change is required.

## Verified on live server

Boot evidence from Better MC 6 (`58bc95ef`) after installing this JAR:

```text
e4mc 6.2.2 (e4mc)
e4mc Compatibility Patch 1.0.0 (e4mc_compat_patch)
Done (1.958s)! For help, type "help"
```

No mixin injector failure was reported. The remote JAR SHA-256 was read back from
the server and matched the local build hash exactly.
