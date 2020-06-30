import com.it.utils.FileUtils;
import javazoom.jl.player.Player;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author 18576756475
 * @version V1.0
 * @ClassName MusicPlay
 * @Description TODO
 * @Date 2019-12-09 10:16:32
 **/
public class MusicPlay {

    private static String MP3 = "mp3";

    public static void main(String[] args) {
        File file=new File("E:\\oppo\\笔记\\记录.txt");
        try {
            java.awt.Desktop.getDesktop().open(file.getParentFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void play(String path){
        List<File> dirFiles = FileUtils.getDirFiles(path);

        if (null != dirFiles && !dirFiles.isEmpty()){
            dirFiles.forEach(file -> {
                String musFileUrl = file.getPath();
                String fileType = musFileUrl.substring(musFileUrl.lastIndexOf(".")+1);
                if (fileType.equals(MP3)){
                    playMusic(musFileUrl);

                }
            });
        }
    }

    public void playMusic(String musFileUrl){
        try {
            BufferedInputStream buffer = new BufferedInputStream(new FileInputStream(musFileUrl));
            Player player = new Player(buffer);
            player.play();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
