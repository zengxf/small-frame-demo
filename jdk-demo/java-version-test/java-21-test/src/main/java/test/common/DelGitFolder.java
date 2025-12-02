package test.common;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Java 21 递归删除所有的 .git 目录
 * <p/>
 * Created by ZXFeng on 2025/12/2
 */
public class DelGitFolder {

    public static void main(String[] args) throws IOException {
        // 1️⃣ 设定根目录，可改成任意路径
        String root = "D:\\MyData\\pri-project\\Evil";
        Path rootPath = Paths.get(root);

        // 2️⃣ 递归遍历文件树
        Files.walkFileTree(rootPath, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                // 判断目录名是否为 .git
                if (dir.getFileName().toString().equals(".git")) {
                    deleteDirectoryRecursively(dir);
                    System.out.println("Deleted: " + dir);
                    // 跳过当前目录下的子目录遍历
                    return FileVisitResult.SKIP_SUBTREE;
                }
                return FileVisitResult.CONTINUE;
            }
        });

        System.out.println("✅ 所有 .git 目录已删除完成。");
    }

    // 递归删除目录及其内容
    private static void deleteDirectoryRecursively(Path path) throws IOException {
        if (!Files.exists(path))
            return;
        Files.walk(path)
                .sorted((a, b) -> b.compareTo(a)) // 先删除文件，再删除目录
                .forEach(p -> {
                    System.out.println(p);
                    try {
                        p.toFile().setWritable(true);
                        Files.setAttribute(p, "dos:readonly", false);
                        Files.delete(p);
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.err.println("删除失败: " + p + " -> " + e.getMessage());
                        System.exit(-1);
                    }
                });
    }

}
