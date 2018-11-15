package sameFileChecker;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 応用問題の回答例。
 * @author KojiNagahara
 */
public class SameFileChecker {

	public static void main(String[] args) throws InterruptedException {
		try {
			Path dir = Paths.get("D:\\codecamp\\FileStorage");
			// テスト用のファイルを作成する
			createTestFile(dir);

			// ファイル名のリストをとる
			List<Path> files = Files.list(dir).collect(Collectors.toList());

			// 遅くてメモリ消費量の少ない実装
			System.out.format(LocalDateTime.now() + "=======processSlowAndSmall\n");
			processSlowAndSmall(files);
			System.out.format(LocalDateTime.now() + "=======ended\n");

			// 速くてメモリ消費量の多い実装
			System.out.println(LocalDateTime.now() + "=======processFastAndLarge");
			processFastAndLarge(files);
			System.out.format(LocalDateTime.now() + "=======ended\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * テスト用のファイルを準備する
	 * @param dir ファイルを作成するディレクトリ
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private static void createTestFile(Path dir) throws IOException, InterruptedException {
		// 既に存在するディレクトリなら削除
		if (Files.exists(dir)) {
			try (Stream<Path> walk = Files.walk(dir, FileVisitOption.FOLLOW_LINKS)) {
				walk.sorted(Comparator.reverseOrder())
						.map(Path::toFile)
						.forEach(File::delete);
				dir.toFile().delete();
			} catch (IOException e) {
				throw e;
			}
		}
		Files.createDirectory(dir);

		for (int i = 0; i < 10000; i++) {
			String fileName = LocalDateTime.now()
					.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssnnnnnnnnn")) + ".txt";
			Path file = Paths.get(dir.toString(), fileName);
			List<String> contents = new ArrayList<>();
			// 重複ありの状態を作る場合
			if ((i == 100) || (i == 3274) || (i == 9990)) {
				contents.add("Same File");
			} else {
				contents.add(fileName);
			}
			// 重複なしの状態を作る場合
			// contents.add(fileName);
			Files.write(file, contents);
			// ファイル名が重複してしまう事態を防ぐために1ミリ秒待つ
			Thread.sleep(1);
		}
	}

	/**
	 * 遅くてメモリ消費量の少ないファイル比較の実装。
	 * @param files ファイルのリスト
	 */
	private static void processSlowAndSmall(List<Path> files) throws IOException {
		for (int i = 0; i < files.size() - 1; i++) {
			Path file1 = files.get(i);
			List<String> contents1 = Files.readAllLines(file1);

			for (int j = i + 1; j < files.size(); j++) {
				Path file2 = files.get(j);
				List<String> contents2 = Files.readAllLines(file2);
				if (contents1.equals(contents2)) {
					System.out.format("同一内容のファイルが見つかりました：%s, %s\n",
							file1.getFileName().toString(),
							file2.getFileName().toString());
				}
			}

			if (999 == i % 1000) {
				System.out.format("%d 件処理完了\n", i+1);
			}
		}
	}

	/**
	 * 速くてメモリ消費量の多いファイル比較の実装。
	 * @param files ファイルのリスト
	 */
	private static void processFastAndLarge(List<Path> files) throws IOException {
		// 一度ファイルの内容をすべてメモリ上にキャッシュする
		List<TextFileCache> cacheList = new ArrayList<>();
		for (Path file : files) {
			cacheList.add(new TextFileCache(file));
		}

		for (int i = 0; i < cacheList.size() - 1; i++) {
			TextFileCache cache = cacheList.get(i);
			if (cache.isChecked()) {
				System.out.format("%d 個目のファイルをスキップ\n", i+1);
				continue;
			}

			for (int j = i + 1; j < cacheList.size(); j++) {
				TextFileCache another = cacheList.get(j);
				if (another.equals(cache)) {
					System.out.format("同一内容のファイルが見つかりました：%s, %s\n",
							cache.getFileName(),
							another.getFileName());
					another.check();
				}
			}

			if (999 == i % 1000) {
				System.out.format("%d 件処理完了\n", i+1);
			}
		}
	}
}
