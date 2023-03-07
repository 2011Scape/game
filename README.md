# Tek5
A port of the RSMod v1 that prepares it for use with a higher revision client (revision 667)

The original repository for RSMod can be found at: [RSMod V1](https://github.com/Tomm0017/rsmod)

# Community

Follow development updates, and find more information here [Tek5, Rune-Server](https://www.rune-server.ee/runescape-development/rs-503-client-and-server/projects/703764-tek5-open-source-high-revision-conversion-rsmod-v1.html), and you can find our community at our Discord found here [Tek5, Discord](https://discord.gg/fPbNdGdTRh)


# Required Files

File Server: [tek5-fs](https://github.com/alycii/tek5-fs)

The file server acts a standalone JS5 server, this runs completely separate from the main server. The file-server.properties files contains important information such as the cache location, JS5 RSA private and modulus keys, and the prefetchKeys. You shouldn't need to change any of these if you don't need to, but it is highly recommended to change the RSA keys. Simply plop your 667 cache in the /cache/ folder, and run it! If you make any cache edits, just simply restart this server.

---

667 Client: [667 deob](https://mega.nz/file/4hNzzaRa#kYw12OJY5RtszIn9IVkRMixVHI2pTLvMruqpgG9WUI0)

A slightly modified 667 deobfuscated client that has pre-set RSA keys for JS5 and login, as well as the lobby disabled. All of these can be modified in the RS2Loader class.

---

667 cache, xtea keys: [667 cache](https://archive.openrs2.org/caches/runescape/278/disk.zip) - [667 xtea keys](https://archive.openrs2.org/caches/runescape/278/keys.json)

These are links directly from [OpenRS2](https://archive.openrs2.org/), place the cache in ./data/cache/ and the keys in ./data/xteas/

# Installation, running

Simply import the file-server, and the game-server as gradle projects, and create gradle run tasks for each. You'll want to start the file-server first, and then the game-server. You can simply leave the file-server on as long as you wish, and it only needs to be restarted when you've made changes to your cache. Once the game-server is online, you can simply run the 667 client with RS2Loader and log-in!

If you have any questions or concerns on installing this project, join our [Discord](https://discord.gg/fPbNdGdTRh) to get help.
