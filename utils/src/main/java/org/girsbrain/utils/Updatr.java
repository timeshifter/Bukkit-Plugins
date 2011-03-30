package org.girsbrain.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;

/**
 * @author jlogsdon
 */
public class Updatr {
    public static final String UPDATR_PREFIX = "http://enthouse.net/updatr/";

    private BasePlugin plugin;
    private List<PluginVersion> versions = new ArrayList<PluginVersion>();
    private final Yaml yaml = new Yaml(new SafeConstructor());

    private PluginVersion currentVersion;
    private PluginVersion latestVersion;

    public Updatr(BasePlugin instance) {
        plugin = instance;
        loadInformation();
    }

    public PluginVersion getCurrentVersion() {
        if (currentVersion == null) {
            for (PluginVersion version : versions) {
                if (version.compare(plugin.getDescription().getVersion()) == 0) {
                    currentVersion = version;
                    break;
                }
            }
        }

        return currentVersion;
    }

    public PluginVersion getLatestVersion() {
        if (latestVersion == null) {
            for (PluginVersion version : versions) {
                if (latestVersion == null) {
                    latestVersion = version;
                } else if (version.compare(latestVersion) > 0) {
                    latestVersion = version;
                }
            }
        }

        return latestVersion;
    }

    public String getInformationUri() {
        return Updatr.UPDATR_PREFIX + plugin.getDescription().getName() + ".updatr";
    }

    public URL getInformationUrl() throws MalformedURLException {
        return new URL(getInformationUri());
    }

    public boolean hasPluginUpdate() {
        if (versions.isEmpty()) {
            return false;
        }

        return versions.size() > 0 && getLatestVersion().compare(getCurrentVersion()) > 0;
    }

    public boolean updateLibraries() {
        if (versions.isEmpty()) {
            return true;
        }
        PluginVersion version = getCurrentVersion();

        for (Library library : version.getLibraries()) {
            if (!updateLibrary(library)) {
                return false;
            }
        }

        return true;
    }

    private boolean updateLibrary(Library lib) {
        try {
            File localPath = lib.getLocalFile(plugin);
            URL remotePath = lib.getDownloadUrl();

            if (localPath.exists()) {
                return true;
            }

            plugin.getLogger().warning(lib.getName() + " needs an update! Downloading...");

            URLConnection con = remotePath.openConnection();
            con.setUseCaches(false);
            InputStream in = con.getInputStream();
            OutputStream out = new FileOutputStream(localPath);

            byte[] buffer = new byte[0x10000];
            int totalSize = 0, count = 0;

            while (true) {
                count = in.read(buffer);
                if (count < 0) break;
                out.write(buffer, 0, count);
                totalSize += count;
            }

            plugin.getLogger().info("Finished! " + totalSize + " bytes received!");
            return true;
        } catch (Exception e) {
            plugin.getLogger().severe("Failed to download: " + e.getMessage());
            return false;
        }
    }

    private void loadInformation() {
        try {
            InputStream stream = getInformationUrl().openStream();
            loadVersions((List<Map<String, Object>>) yaml.load(stream));
        } catch (Exception e) {
            plugin.getLogger().severe("Failed to load updatr information: " + e.getMessage());
        }
    }

    private void loadVersions(List<Map<String, Object>> versions) {
        for (Map<String, Object> info : versions) {
            loadVersion(info);
        }
    }

    private void loadVersion(Map<String, Object> info) {
        PluginVersion version = new PluginVersion(info.get("version").toString());
        plugin.getLogger().info("Adding Version " + version.getVersion());

        for (String key : info.keySet()) {
            if (!key.equals("version")) {
                Library lib = new Library(key, info.get(key).toString());
                plugin.getLogger().info("  Adding Library " + lib.getName() + "-" + lib.getVersion());
                version.addLibrary(lib);
            }
        }

        versions.add(version);
    }

    /**
     * If versions equal return 0
     * If me > you return 1
     * If you > me return -1
     *
     * @param me
     * @param you
     * @return
     */
    public static int versionCompare(String me, String you) {
        if (me.equals(you)) {
            return 0;
        }

        String[] _me = Pattern.compile(".", Pattern.LITERAL).split(me);
        String[] _you = Pattern.compile(".", Pattern.LITERAL).split(you);

        for (int i = 0; i < _me.length || i < _you.length; i++) {
            try {
                int _m = (i >= _me.length) ? 0 : Integer.parseInt(_me[i]);
                int _y = (i >= _you.length) ? 0 : Integer.parseInt(_you[i]);

                if (_m == _y) continue;
                if (_m < _y) return -1;
                if (_m > _y) return 1;
            } catch (Exception e) {
            }
        }

        return 0;
    }

    class PluginVersion {
        private List<Library> libraries = new ArrayList<Library>();
        private String version;

        public PluginVersion(String version) {
            this.version = version;
        }

        public void addLibrary(Library lib) {
            libraries.add(lib);
        }

        public List<Library> getLibraries() {
            return libraries;
        }

        public String getVersion() {
            return version;
        }

        public int compare(PluginVersion other) {
            return Updatr.versionCompare(version, other.getVersion());
        }

        private int compare(String other) {
            return Updatr.versionCompare(version, other);
        }
    }
    class Library {
        private String name;
        private String version;

        public Library(String name, String version) {
            this.name = name;
            this.version = version;
        }

        public String getName() {
            return name;
        }

        public String getVersion() {
            return version;
        }

        public String getDownloadUri() {
            return Updatr.UPDATR_PREFIX + "lib/" + name + "/" + name + "-" + version + ".jar";
        }

        private URL getDownloadUrl() throws MalformedURLException {
            return new URL(getDownloadUri());
        }

        public String getLocalPath(BasePlugin plugin) {
            return "./lib/" + name + "-" + version + ".jar";
        }

        public File getLocalFile(BasePlugin plugin) {
            return new File(getLocalPath(plugin));
        }

        public int compare(Library other) {
            return Updatr.versionCompare(version, other.getVersion());
        }

        private int compare(String other) {
            return Updatr.versionCompare(version, other);
        }
    }
}