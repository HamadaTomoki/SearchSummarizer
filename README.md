# SearchSummarizer App

<img src="https://user-images.githubusercontent.com/68803158/161241709-0228fcf7-f8a4-4ea8-97a2-40b27a6a58aa.png" width=70%>

Search Summarizerは基本的なブラウザ機能に加え、新たに再検索を効率化する機能を提供するモバイルアプリです。複数の検索URLをまとめてアクセス1つすることで再検索と共有をスムーズにします。 

<br>

## 【Details】

私が学生生活の3年間で一番行ったことはWeb検索です。検索にも慣れ、作業をしているうちにどうにか効率化できないかと思うようになりました。検索を効率化するためにブックマーク, リーディングリスト, カテゴリ別でURLを保存できるサービスといった再検索を効率化するサービスを利用してきましたがどれも１つずつアクセスしなければいけないことに気が付きました。そのとき、注目したのが「URLのリンク(アクセス)を１つにまとめる」という考えです。

<br>

## 【Usecase】

<img src="https://user-images.githubusercontent.com/68803158/161241705-55bbf7c7-8e50-4ffb-9e81-55b9b13076e7.png" width=80%>
<img src="https://user-images.githubusercontent.com/68803158/161241700-7b678ac9-19b6-4e9f-9a4a-29b1fbe94043.png" width=80%>

<br>

## 【Demo】

[![Watch the video](https://user-images.githubusercontent.com/68803158/161249027-f3a8837d-8546-4259-9d20-f3ba3a47bdfc.png)](https://drive.google.com/file/d/1ZTxLXWcE4RGXpVZj1cxzT2-s_Ak6RFAS/preview)

> URL共有機能のAndroid App Linksはdubug用のkeystoreを使用しているので、debug環境でしか動作しません。


<br>

## 【DEV】

- 実行環境  
Kotlin, Android, Jetpack Compose, Ktor, Heroku

- 追加依存関係(Gradle)   
ktlint, dokka, accompanist, coil, koin, webkit, navigation-compose, datastore, kotliin-seliarizeration, ktor-client 

- kdoc(/app/build/kdoc/index.html)  
dokkaを使用してSearchSummarizerAppのdocumentをしています。


