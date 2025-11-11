# 問題2B-2

`oop.ex2B2.MyBoolean`クラスは、`int`を`value`フィールドに保持し、それを（C言語の方式で）真偽値として返すことを目的としたクラスである。
以下のメソッドを実装し、`Main2B2`のコメントアウトを外すとしたの実行例が得られるようにせよ（提出時は実行例が得られる状態とすること）。なお、メソッドの可視性はすべて`public`とする。

1. `int`の引数を取るコンストラクタ。フィールド`value`に引数を保存する。
2. `boolean`を返すメソッド`getValue`。`value`の内容が`0`ならば`false`を、それ以外ならば`true`を返す。
3. `int`を取り、何も返さない（返し値の型が`void`）メソッド`setValue`。`value`に引数を保存する。
4. `String`を返すメソッド`toString`。実行例のように、`boolean`として見たときの結果に加えて、内部の`value`の値を表示する。
5. `Object`を取り、`boolean`を返すメソッド`equals`。引数が`MyBoolean`のインスタンスであり、かつ真偽値として見たときに同一の結果を返すならば`true`、そうでなければ`false`を返す。
6. `int`を返すメソッド`hashCode`。`value`の内容が`0`ならば`0`を、それ以外ならば`1`を返す。

なお、`equals`では引数が`MyBoolean`クラスのインスタンスであるかを判定しなければならないが、これは`instanceof`演算子によって実現できる。例えば`o instanceof MyBoolean`で「`o`が`MyBoolean`クラス（またはそのサブクラス）のインスタンスか」が`boolean`として得られる。
キャストの失敗を例外`ClassCastException`をキャッチすることで検出する方法もあるが、一般的とは言えない。

注1：4から6はすべて`Object`クラスに存在するメソッドをオーバーライドしている。`@Override`というアノテーションをメソッド宣言の先頭に付けると、オーバーライドしていることが保証でき、スペルミスによる名前の誤りの予防や、引数を誤ってオーバーロードとなるなどのミスの発見に役立つ。（本課題の採点には付けなくても影響しない）

注2：`equals`メソッドをオーバーライドする場合、`hashCode`メソッドをオーバーライドし、それらの挙動について守らなければならない規則が存在する。本課題の要求はそれに則ったものである。 <!-- これらの規約を言語として強制できていないのは、やや危険なところである。ただし、必須の項目以外にオプショナルなものもあるので、すべてを強制してはいけないなど、「規約」と一言で言っても扱いが難しいものでもある。 -->
詳細は`equals`メソッドの仕様 https://docs.oracle.com/javase/jp/8/docs/api/java/lang/Object.html#equals-java.lang.Object- および `hashCode`の仕様 https://docs.oracle.com/javase/jp/8/docs/api/java/lang/Object.html#hashCode-- を参照すること。


### 実行例

    false
    false (0)
    true
    true (10)
    等価性判定とhashCode
    false
    0
    false
    1
    b1の中身を-1に変更
    true (-1)
    再度等価性判定とhashCode
    true
    1
    true
    1