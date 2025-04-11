<div align="center">
<h1>2011scape</h1>
<a href="https://github.com/2011Scape/game">
  <img src="https://i.imgur.com/IKFkP0S.jpeg" alt="2011Scape">
</a>

[![License](https://img.shields.io/badge/license-ISC-blue?style=for-the-badge&logo=open-source-initiative&logoColor=white)](https://opensource.org/licenses/ISC)
[![GitHub issues](https://img.shields.io/github/issues/2011Scape/game?style=for-the-badge&label=Issues%20%E2%9A%A0%EF%B8%8F&color=gold)](https://github.com/2011Scape/game/issues)
[![GitHub forks](https://img.shields.io/github/forks/2011Scape/game?style=for-the-badge&logo=github&logoColor=white)](https://github.com/2011Scape/game/forks)
[![Contributors](https://img.shields.io/github/contributors/2011Scape/game?style=for-the-badge&logo=github&color=darkgreen)](https://github.com/2011Scape/game/graphs/contributors)
[![Discord](https://img.shields.io/discord/1055304546521469019?label=chat&logo=discord&logoColor=white&style=for-the-badge&color=5865F2)](https://discord.gg/jDbBAKjhxh)
[![Gitpod](https://img.shields.io/badge/Gitpod-orange?style=for-the-badge&logo=gitpod&logoColor=white)](https://gitpod.io/#https://github.com/2011Scape/game)

<h1>Relive the 2011 RuneScape</h1>

<p>Relive the 2011 RuneScape experience with modern server emulation</p>

<a href="#features">Features</a> &nbsp;&bull;&nbsp;
<a href="#quick-setup">Quick setup</a> &nbsp;&bull;&nbsp;
<a href="#setup-guide">Setup Guide</a> &nbsp;&bull;&nbsp;
<a href="#quick-links">Quick Links</a> &nbsp;&bull;&nbsp;
<a href="https://rune-server.org/threads/667-2011scape-an-emulation-of-runescape-in-2011-powered-by-rsmod.706352/" target="_blank">Blog</a> &nbsp;&bull;&nbsp;
<a href="https://github.com/2011Scape/game/issues">Bugs</a>

![2011scape in game picture](https://i.imgur.com/TNXa63G.png)

</div>

## Features

- **Classic 2011 Experience**: Relive the nostalgia of 2011 RuneScape with a faithful emulation of the past.
- **Modern Server Emulation**: Enjoy the stability and performance of modern server technology while keeping the core 2011 RuneScape experience intact.
- **Partial Content Available**: While we're still in the process of adding content, you can enjoy a selection of skills, NPCs, and combat mechanics from the 2011 era.
- **Ongoing Development**: We're actively working on adding missing content such as quests, mini-games, NPCs, combat mechanics, bosses, and more to fully recreate the 2011 RuneScape experience.
- **Community-Driven Progress**: Join an active community where player feedback helps guide the development and improvements of the server.
- **Nostalgic Atmosphere**: Immerse yourself in the look and feel of 2011 RuneScape with familiar areas, mini-games, and events.


## Quick Setup

### Required Tools, Files

Before you begin, ensure that you have the following tools and files ready:

- [IntelliJ IDE](https://www.jetbrains.com/idea/download/)
- [GitHub Desktop](https://desktop.github.com/)
- [667 Cache](https://archive.openrs2.org/caches/runescape/278/disk.zip)
- [667 xteas](https://github.com/2011Scape/installation-guide/releases/download/v1.0/xteas.json)
- [JDK 11](https://www.techspot.com/downloads/5553-java-jdk.html)

- Note: JDK 11 is required for the RS-Client repository, using newer versions of the Java Development Kit is currently untested, and Java 11 is the recommended option
- You can skip any of these downloads if you have them already installed, or saved somewhere.

## Setup Guide

1. **Clone Repositories**:
   - Fork and clone the following repositories:
     - [Game Repository](https://github.com/2011Scape/2011Scape)
     - [File Server Repository](https://github.com/2011Scape/file-server)
     - [Client Repository](https://github.com/2011Scape/stronghold-client)

2. **Install Dependencies**:
   - Java 11 (or higher) installed.
   - [GitHub Desktop](https://desktop.github.com/) for cloning repositories.
   - [IntelliJ IDEA](https://www.jetbrains.com/idea/) for development.

3. **Download Files**:
   - [667 Cache](https://archive.openrs2.org/caches/runescape/278/disk.zip)
   - [667 Xteas](https://github.com/2011Scape/installation-guide/releases/download/v1.0/xteas.json)

4. **Set Up Game Server**:
   - Open the game repository in IntelliJ IDEA and wait for Gradle setup.
   - Extract the cache into `/data/cache/` and place `xteas.json` into `/data/xteas/`.
   - Run the server with the Gradle run configuration or using `./game run`.

5. **Set Up File Server**:
   - Clone the [File Server repository](https://github.com/2011Scape/file-server).
   - Copy cache files into `/file-server/cache`.
   - Run the File Server using `gradlew.bat run` (Windows) or `./file-server run` (Linux).

6. **Set Up Client**:
   - Clone the [Client repository](https://github.com/2011Scape/stronghold-client).
   - Open `2011Scape-client` in IntelliJ, right-click `RS2Loader.java`, and run it.

Now, the server should be running, and the client should be ready to play!

## Bugs

If you encounter any issues or bugs, please let us know on our [Issues page](https://github.com/2011Scape/game/issues) of the problem so we can address it promptly.

## Quick Links

### Community
- [![Discord](https://img.shields.io/badge/Discord%20%20-blue?style=for-the-badge&logo=discord&logoColor=white)](https://discord.gg/jDbBAKjhxh)
- [![Reddit](https://img.shields.io/badge/Reddit%20%20-red?style=for-the-badge&logo=reddit&logoColor=white)](https://www.reddit.com/r/2011scape/)

### 2011Scape Resources
- [Game Repository](https://github.com/2011Scape/game)
- [File Server Repository](https://github.com/2011Scape/file-server)
- [Client Repository](https://github.com/2011Scape/2011scape-client)
- [Full Installation Guide](https://github.com/2011Scape/installation-guide)

### Additional Resources
- [OpenRS2 Archive](https://archive.openrs2.org/)
- [Lost City, 2004 Emulator](https://discord.gg/hN3tHUmZEN)
- [2009Scape, 2009 Emulator](https://2009scape.org)
