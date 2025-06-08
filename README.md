# SwingFarm

[![Minecraft](https://img.shields.io/badge/Minecraft-1.21.x-green.svg)](https://minecraft.net)
[![Fabric](https://img.shields.io/badge/Fabric-Supported-orange.svg)](https://fabricmc.net)
[![License](https://img.shields.io/badge/License-CC0--1.0-blue.svg)](LICENSE)

**SwingFarm** は、Minecraft Java Edition 1.21.x 向けのクライアントサイド MOD です。Mob 処理トラップでの待機時間を自動化し、目の前に現れた敵対 Mob を自動で攻撃します。

## 🎯 主な機能

- **自動攻撃システム**: プレイヤーの前方 3 ブロック以内の敵対 Mob を自動検出・攻撃
- **ショートカットキー**: `Ctrl + A` で機能の ON/OFF 切り替え
- **視線チェック**: 壁越しの攻撃を防止
- **攻撃クールダウン**: 自然な攻撃間隔（1 秒）を実現
- **安全機能**: 剣を装備している時のみ動作

## 📋 動作条件

MOD が動作するための条件：

- プレイヤーが生存していること
- メインハンドに剣（SwordItem）を装備していること
- プレイヤーの前方 3 ブロック以内に敵対 Mob が存在すること
- その Mob がプレイヤーから見える位置にいること
- 機能が ON になっていること（`Ctrl + A`で切り替え）

## 🛠️ 必要環境

- **Minecraft**: Java Edition 1.21.x
- **Mod Loader**: Fabric Loader 0.16.0 以上
- **依存関係**: Fabric API
- **Java**: 17 以上

## 📦 インストール方法

1. [Fabric Loader](https://fabricmc.net/use/installer/)をインストール
2. [Fabric API](https://modrinth.com/mod/fabric-api)をダウンロード
3. [Releases](../../releases)から最新の SwingFarm-fabric-1.21.3-x.x.x.jar をダウンロード
4. `.minecraft/mods`フォルダに Fabric API と SwingFarm の jar ファイルを配置
5. Minecraft を起動

## 🎮 使用方法

1. **Minecraft にログイン**
2. **剣をメインハンドに装備**
3. **`Ctrl + A`を押して機能を ON**
   - チャットに「自動攻撃機能: 有効」と表示されます
4. **Mob 処理トラップの前で待機**
5. **敵対 Mob が現れると自動で攻撃開始**
6. **`Ctrl + A`で機能を OFF**

### キーバインド設定

- **設定 → 操作設定 → キー設定**で「SwingFarm」カテゴリを確認
- デフォルトは`Ctrl + A`ですが、お好みに変更可能

## 🎨 スクリーンショット

### チャットメッセージ

- 🟢 有効時: `[SwingFarm] 自動攻撃機能: 有効`
- 🔴 無効時: `[SwingFarm] 自動攻撃機能: 無効`

## 🔧 開発者向け情報

### ビルド方法

```bash
git clone https://github.com/yourusername/SwingFarm.git
cd SwingFarm
./gradlew build
```

### 技術仕様

- **イベント**: `ClientTickEvents.END_CLIENT_TICK`を使用
- **攻撃範囲**: プレイヤー前方 3 ブロック以内
- **攻撃判定**: `HostileEntity`クラスベースの敵対 Mob 判定
- **視線チェック**: レイキャスト（Raycast）を使用
- **クールダウン**: 20tick（1 秒）

### プロジェクト構造

```
SwingFarm/
├── src/
│   ├── main/resources/
│   │   ├── fabric.mod.json
│   │   └── assets/swingfarm/lang/
│   │       ├── en_us.json
│   │       └── ja_jp.json
│   └── client/java/com/example/swingfarm/
│       └── SwingFarmMod.java
├── build.gradle
├── gradle.properties
└── settings.gradle
```

## ⚠️ 注意事項

- **クライアントサイド MOD**のため、サーバーにインストールする必要はありません
- **PvP サーバー**での使用は利用規約を確認してください
- **自動化 MOD**のため、一部サーバーで禁止されている場合があります

## 🐛 バグ報告・要望

バグを発見した場合や機能要望がある場合は、[Issues](../../issues)からご報告ください。

### 報告時の情報

- Minecraft バージョン
- Fabric Loader バージョン
- Fabric API バージョン
- SwingFarm バージョン
- 発生した問題の詳細

## 📜 ライセンス

このプロジェクトは[CC0-1.0 License](LICENSE)の下で公開されています。

## 🤝 貢献

プルリクエストや改善提案は大歓迎です！

1. プロジェクトをフォーク
2. 機能ブランチを作成 (`git checkout -b feature/AmazingFeature`)
3. 変更をコミット (`git commit -m 'Add some AmazingFeature'`)
4. ブランチにプッシュ (`git push origin feature/AmazingFeature`)
5. プルリクエストを開く

## 📞 サポート

- **Discord**: [リンクがあれば記載]
- **Twitter**: [リンクがあれば記載]
- **Email**: [連絡先があれば記載]

---

**Happy Farming! 🗡️⚔️**
