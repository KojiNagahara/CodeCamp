package sameFileChecker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * テキストファイルをメモリ内にキャッシュして比較するための要素オブジェクト。
 * リストに格納することを想定しているので、計算量を安全に減らすため
 * 同じファイルがあることが確定済みのものについてフラグを立てる運用とする。
 * @author KojiNagahara
 */
public class TextFileCache {
	private Path source;
	private List<String> contents;
	private boolean isChecked = false;

	/**
	 * コンストラクタ。検査済みのpathが指定されていることを事前条件とする。
	 * @param path キャッシュ作成対象のpath
	 */
	public TextFileCache(Path path) throws IOException {
		source = path;
		contents = Files.readAllLines(source);
	}

	/**
	 * キャッシュされたファイルのファイル名を返す。
	 * @return ファイル名
	 */
	public String getFileName() {
		return source.getFileName().toString();
	}

	/**
	 * チェック状態を返す
	 * @return trueなら既に同じファイルが存在する、falseならまだ同じファイルが存在しない。
	 */
	public boolean isChecked() {
		return isChecked;
	}

	/**
	 * チェック状態をtrueにする。
	 * 今回falseに戻すことは考えない。
	 */
	public void check() {
		isChecked = true;
	}

	/**
	 * ここでは同一性の基準をファイルの内容だけに限定する。
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) { return false; }
		if (!(obj instanceof TextFileCache)) { return false; }
		return contents.equals(((TextFileCache)obj).contents);
	}

	/**
	 * equalsの実装に準じる。
	 */
	@Override
	public int hashCode() {
		return contents.hashCode();
	}
}
