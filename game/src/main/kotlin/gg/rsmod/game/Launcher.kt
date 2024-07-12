package gg.rsmod.game

import java.awt.GraphicsEnvironment
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.nio.file.StandardOpenOption
import java.util.zip.ZipInputStream
import javax.swing.JOptionPane

object Launcher {
    @JvmStatic
    fun main(args: Array<String>) {
        val filestore = Paths.get("./data", "cache")

        // Check and install cache if necessary
        if (!verifyCacheFiles(filestore)) {
            val userResponse =
                if (isDesktopAvailable()) {
                    promptUserWithDialog()
                } else {
                    println("No desktop environment detected. Defaulting to yes for cache download.")
                    "yes"
                }

            if (userResponse.equals("yes", ignoreCase = true)) {
                downloadAndInstallCache(filestore)
            } else {
                println("Cache download declined. Exiting.")
                return
            }
        }

        val server = Server()
        server.startServer(apiProps = Paths.get("./data/api.yml"))
        server.startGame(
            filestore = filestore,
            gameProps = Paths.get("./game.yml"),
            packets = Paths.get("./data", "packets.yml"),
            blocks = Paths.get("./data", "blocks.yml"),
            devProps = Paths.get("./dev-settings.yml"),
            args = args,
        )
    }

    private fun isDesktopAvailable(): Boolean {
        return !GraphicsEnvironment.isHeadless()
    }

    private fun promptUserWithDialog(): String {
        val options = arrayOf("Yes", "No")
        val response =
            JOptionPane.showOptionDialog(
                null,
                "The cache path directory is empty. Do you want to download and install the 667 cache?",
                "Cache Download",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0],
            )
        return if (response == JOptionPane.YES_OPTION) "yes" else "no"
    }

    private fun downloadAndInstallCache(cachePath: Path) {
        val cacheMirrors =
            listOf(
                URL("https://2011scape.com/downloads/cache.zip"),
                URL("https://archive.openrs2.org/caches/runescape/278/disk.zip"),
            )
        val zipFile = cachePath.resolve("cache.zip")

        for (cacheUrl in cacheMirrors) {
            try {
                Files.createDirectories(cachePath) // Ensure the cache directory exists
                println("Downloading cache from $cacheUrl.")

                // Download with progress
                downloadFileWithProgress(cacheUrl, zipFile)

                println("Download complete. Unzipping cache...")
                unzip(zipFile, cachePath.parent) // Extract to parent directory
                Files.delete(zipFile)
                println("Unzip complete.")
                return // Exit the loop once download and extraction are successful
            } catch (e: Exception) {
                println("Failed to download from $cacheUrl. Trying next URL if available.")
                e.printStackTrace()
            }
        }

        println("All cache download attempts failed.")
    }

    private fun downloadFileWithProgress(
        url: URL,
        destination: Path,
    ) {
        url.openStream().use { input ->
            Files.newOutputStream(destination, StandardOpenOption.CREATE).use { output ->
                val buffer = ByteArray(1024)
                var bytesRead: Int
                var totalBytesRead = 0L
                val fileSize = url.openConnection().contentLengthLong

                while (input.read(buffer).also { bytesRead = it } != -1) {
                    output.write(buffer, 0, bytesRead)
                    totalBytesRead += bytesRead
                    printProgress(totalBytesRead, fileSize)
                }
                println() // Move to the next line after download completes
            }
        }
    }

    private fun printProgress(
        bytesRead: Long,
        totalBytes: Long,
    ) {
        val progress = (bytesRead * 100) / totalBytes
        print("\rDownloading: $progress%")
    }

    private fun unzip(
        zipFilePath: Path,
        destDir: Path,
    ) {
        ZipInputStream(Files.newInputStream(zipFilePath)).use { zip ->
            var entry = zip.nextEntry
            while (entry != null) {
                val entryName = entry.name
                val normalizedPath = Paths.get(entryName).normalize()
                val filePath = destDir.resolve(normalizedPath)

                if (!entry.isDirectory) {
                    Files.createDirectories(filePath.parent)
                    Files.copy(zip, filePath, StandardCopyOption.REPLACE_EXISTING)
                    println("Extracted file: $filePath")
                } else {
                    Files.createDirectories(filePath)
                    println("Created directory: $filePath")
                }

                zip.closeEntry()
                entry = zip.nextEntry
            }
        }
    }

    private fun verifyCacheFiles(filestore: Path): Boolean {
        val requiredFiles =
            listOf(
                "main_file_cache.dat2",
                "main_file_cache.idx0",
                "main_file_cache.idx1",
                "main_file_cache.idx10",
                "main_file_cache.idx11",
                "main_file_cache.idx12",
                "main_file_cache.idx13",
                "main_file_cache.idx14",
                "main_file_cache.idx15",
                "main_file_cache.idx16",
                "main_file_cache.idx17",
                "main_file_cache.idx18",
                "main_file_cache.idx19",
                "main_file_cache.idx2",
                "main_file_cache.idx20",
                "main_file_cache.idx21",
                "main_file_cache.idx22",
                "main_file_cache.idx23",
                "main_file_cache.idx24",
                "main_file_cache.idx25",
                "main_file_cache.idx255",
                "main_file_cache.idx26",
                "main_file_cache.idx27",
                "main_file_cache.idx28",
                "main_file_cache.idx29",
                "main_file_cache.idx3",
                "main_file_cache.idx30",
                "main_file_cache.idx31",
                "main_file_cache.idx32",
                "main_file_cache.idx33",
                "main_file_cache.idx34",
                "main_file_cache.idx35",
                "main_file_cache.idx36",
                "main_file_cache.idx4",
                "main_file_cache.idx5",
                "main_file_cache.idx6",
                "main_file_cache.idx7",
                "main_file_cache.idx8",
                "main_file_cache.idx9",
            )

        for (file in requiredFiles) {
            val filePath = filestore.resolve(file)
            if (Files.notExists(filePath)) {
                return false
            }
        }
        return true
    }
}
