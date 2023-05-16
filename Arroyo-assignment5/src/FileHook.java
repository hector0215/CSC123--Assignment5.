import java.io.FileInputStream;
import java.io.InputStream;

public class FileHook extends Template {
    private String filePath;

    public FileHook(String filePath) {
        this.filePath = filePath;
    }

    @Override
    protected InputStream getInputStream() throws Exception {
        try {
            return new FileInputStream(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}