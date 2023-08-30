package test.jdkapi.awt;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 提取图片文件
 * <p>
 * Created by zengxf on 2019-04-16
 */
public class ExtractImage {

	static String SRC_PATH = "G:\\2019AWlookbook\\2019AWlookbook";
	static String OUT_PATH = SRC_PATH + "_JPG";

	public static void main(String[] args) throws IOException {
		mkdir(OUT_PATH);

		Files.find(Paths.get(SRC_PATH), 10, (p, a) -> {
			boolean sign = !p.toString().equals(SRC_PATH) && (Files.isDirectory(p) || isImage(p));
			return sign;
		}).forEach(f -> {
			String src = f.toString();
			String disc = src.replace(SRC_PATH, "");
			String out = OUT_PATH + disc;
			if (Files.isDirectory(f)) {
				mkdir(out);
			} else {
				try {
					handleImage(f.toString(), out);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		System.out.println("完成!!!!");
	}

	static void handleImage(String src, String out) throws IOException {
		System.out.println("源图 => 输出：\t" + src + " \t " + out);
		Files.copy(Paths.get(src), Paths.get(out));
	}

	static boolean isImage(Path path) {
		return path.getFileName().toString().toLowerCase().endsWith(".jpg");
	}

	static void mkdir(String path) {
		try {
			Path filePath = Paths.get(path);
			if (Files.exists(filePath) && Files.isDirectory(filePath))
				return;
			Files.createDirectory(filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
