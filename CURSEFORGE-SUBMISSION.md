# CurseForge project submission package

## Submission title

BMC6 e4mc Compatibility Patch

## Short description

Server-side compatibility patch for Better MC 6 e4mc on Minecraft 26.1.2 / NeoForge 26.1.2.93. Fixes the legacy e4mc command-permission API mismatch without changing the client pack. Keep the original e4mc 6.2.1 mod installed.

Detailed cause, fix, target versions, installation, provenance, and SHA-256:

```md
https://github.com/Vonix-Network/bmc6-e4mc-compat
```

Better MC 6 reference:

```md
https://www.curseforge.com/minecraft/modpacks/better-mc-bmc6
```

## Suggested CurseForge metadata

- Category: Server Utility / Server-side mod
- Minecraft version: select the exact live CurseForge label for the pinned 26.1.2 runtime
- Mod loader: NeoForge
- File version: 0.1.0
- Release type: Release
- Required dependency: e4mc 6.2.1, required on the server
- Client requirement: optional / client pack unchanged
- Project URL: https://github.com/Vonix-Network/bmc6-e4mc-compat
- License: MIT for the patch; e4mc remains third-party and is not bundled

## Upload artifact

`artifacts/e4mc_compat_patch-0.1.0.jar`

SHA-256:

`35383856bbc25f492f2aee1e37fe61dd0c6bceb753ff9c35f6694b631c855648`

## File changelog

Initial release. Adds a narrow NeoForge 26.1.2.93 permission-API compatibility bridge for e4mc 6.2.1 on Better MC 6 servers. The original e4mc JAR is required separately and the client modpack does not need to change.

## Acceptance notes

- Do not upload the original e4mc JAR.
- Do not describe this as a replacement or fork of e4mc.
- Keep the e4mc dependency explicit.
- Use the exact version and loader fields accepted by the CurseForge form; do not guess from a display label.
- Attach no copyrighted imagery unless rights are confirmed.
