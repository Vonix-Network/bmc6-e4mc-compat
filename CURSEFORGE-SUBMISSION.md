# CurseForge project submission package

## Submission title

e4mc NeoForge Compatibility Patch

## Short description

Server-side compatibility patch for **e4mc 6.2.x** on **Minecraft 26.1.2 / NeoForge 26.1.2.93+**. Fixes the command-permission API mismatch reproduced in Better MC 6 when a joining player's command tree was built. The affected components are e4mc 6.2.x (`link.e4mc.E4mcClient`) and the Minecraft/NeoForge command-permission API; this can affect other packs using the same combination. No client-pack change is required. Keep the original e4mc mod installed.

Detailed cause, fix, installation, provenance, and SHA-256:

```md
https://github.com/Vonix-Network/bmc6-e4mc-compat
```

Reference reproduction pack:

```md
https://www.curseforge.com/minecraft/modpacks/better-mc-bmc6
```

## Suggested metadata

- Project name: `e4mc NeoForge Compatibility Patch`
- Category: Server Utility / server-side compatibility
- Minecraft version: select the exact live CurseForge label for the pinned 26.1.2 runtime
- Mod loader: NeoForge
- File version: `1.0.0`
- Release type: Release
- Required dependency: e4mc 6.2.x (declared range `[6.2.0,6.3)`), required on the server
- Client requirement: optional / client pack unchanged
- Project URL: https://github.com/Vonix-Network/bmc6-e4mc-compat
- License: MIT for the patch; e4mc remains third-party and is not bundled

## Upload artifact

`artifacts/e4mc_compat_patch-1.0.0.jar`

SHA-256:

`ef6490f462dfae30c712c4f5c4899ffed12dd48ea084ffd0bcd8be9eba0d42d9`

## File changelog

Widens the declared e4mc range to `[6.2.0,6.3)` so the patch loads against e4mc 6.2.2 and later 6.2.x builds; the previous range excluded 6.2.2 and caused a hard FML dependency failure on Better MC 6. The redirect target was verified unchanged across e4mc 6.2.1, 6.2.2 and 6.2.3. Validated on a live Better MC 6 server (NeoForge 26.1.2.109, e4mc 6.2.2). The original e4mc JAR is required separately and the client modpack does not need to change.

## Acceptance notes

- Do not upload the original e4mc JAR.
- Do not describe this as a replacement or fork of e4mc.
- Keep the e4mc dependency explicit.
- Use the exact version and loader fields accepted by the CurseForge form; do not guess from a display label.
- Attach no copyrighted imagery unless rights are confirmed.
