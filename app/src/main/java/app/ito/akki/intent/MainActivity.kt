package app.ito.akki.intent

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //遷移先から戻ってきた時どこから戻ってきたか判別するための合言葉として,
    // Int型の整数をインテントを実行する際に渡している
    val readRequestCode: Int = 42


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //intentButtonクリック時にSecondActivityへ画面遷移
        intentButton.setOnClickListener{
            //thisは今いるActivity
            //ここで右辺のSecondActivityは遷移先のActivity名を指している
            val toSecondActivityIntent = Intent(this, SecondActivity::class.java)
            //先ほど設定したインテントの実行
            startActivity(toSecondActivityIntent)
        }

        playStoreButton.setOnClickListener{
            val playStoreIntent = Intent(Intent.ACTION_VIEW)
            //遷移先に渡すデータ
            playStoreIntent.data = Uri.parse("https://play.google.com/store/apps")
            //遷移先のアプリの情報
            //遷移先のアプリ　Intent型変数.setPackage(アプリケーションID)
            playStoreIntent.setPackage("com.android.vending")
            startActivity(playStoreIntent)
        }

        //mapButtonクリック時に地図を開く
        //ここからは暗黙的インテント
        mapButton.setOnClickListener{
            val mapIntent = Intent(Intent.ACTION_VIEW)
            mapIntent.data = Uri.parse("geo:35.6473,139.7360")
            //暗黙的インテントを使うときは必ず呼び出せるアプリを持っているか確認する
            //resolveActivity(packageManager)==nullの時呼び出せるアプリがない
            if (mapIntent.resolveActivity(packageManager) != null){
                startActivity(mapIntent)
            }
        }

        browserButton.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW)
            browserIntent.data = Uri.parse("https://life-is-tech.com")
            if (browserIntent.resolveActivity(packageManager) != null){
                startActivity(browserIntent)
            }
        }

        galleryButton.setOnClickListener {
            //Intent.ACTION_OPEN_DOCUMENT,addCategory(Intent.CATEGORY_OPENABLE)とすることによって
            //別のアプリが管理しているファイルを取得することができるようになる
            val galleryIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            galleryIntent.addCategory(Intent.CATEGORY_OPENABLE)
            //typeにimage/*を選択することによって選択できるファイルタイプを画像に指定している
            galleryIntent.type = "image/*"
            //遷移先のアクティビティから結果を受け取ることができる
            startActivityForResult(galleryIntent, readRequestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == readRequestCode && resultCode == Activity.RESULT_OK){
            data?.data?.also{ uri ->
                imageView.setImageURI(uri)
            }

        }
    }
}