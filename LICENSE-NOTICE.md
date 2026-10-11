# Better MC 6 e4mc Compatibility Patch

## Scope

This repository contains only the Vonix.Network compatibility patch source and release documentation. The original e4mc JAR is not redistributed.

## Target

- Minecraft 26.1.2
- NeoForge 26.1.2.93 or later (validated live on 26.1.2.109)
- Java 25
- Better MC 6 server
- e4mc 6.2.x as the separately installed base mod

## Repository contents

- `src/main/java/network/vonix/e4mccompat/mixin/E4mcClientMixin.java` — narrow permission API bridge
- `src/main/java/network/vonix/e4mccompat/mod/E4mcCompatPatch.java` — mod entrypoint
- `src/main/resources/e4mc_compat_patch.mixins.json` — Mixin configuration
- `artifacts/e4mc_compat_patch-1.0.0.jar` — release artifact
- `README.md` — cause, source reference, installation, and verification details

## Reproducible build

The original candidate build was produced with the pinned project Gradle wrapper and NeoForge ModDev toolchain. The release artifact is copied from the verified candidate output without repackaging.

Build command:

```text
./gradlew clean build --no-daemon --stacktrace
```

## Integrity

```text
SHA-256  ef6490f462dfae30c712c4f5c4899ffed12dd48ea084ffd0bcd8be9eba0d42d9
Bytes    3029
```

## License

The patch source and artifact are Vonix.Network work. e4mc is a separate third-party mod; users must obtain it from its original distribution and comply with its license and attribution terms.
