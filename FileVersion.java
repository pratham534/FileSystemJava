package filesys.src;

import java.util.ArrayList;
import java.util.List;

public class FileVersion {
    private List<String> versions;

    public FileVersion() {
        versions = new ArrayList<>();
    }

    public void saveVersion(String content) {
        versions.add(content);
    }

    public String getVersion(int versionNumber) {
        if (versionNumber <= 0 || versionNumber > versions.size()) {
            System.out.println("No such version of the file exists");
            return "";
        }
        return versions.get(versionNumber - 1);
    }

    public int getVersionCount() {
        return versions.size();
    }

    public void updateVersionHistory(int versionNumber) {
        if (versionNumber > 0 && versionNumber <= versions.size()) {
            versions.subList(versionNumber, versions.size()).clear();
        }
    }
}
