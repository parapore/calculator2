package to.msn.wings.caluculator;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// todo +-*/ボタン押下後　→　BSボタンタップ　→　＝ボタンタップでエラー出る
public class MainActivity extends AppCompatActivity {
    List<String> stringList = new ArrayList<>();//入力文字を格納するリスト
    int decimalPointCount = 0; // 小数点ボタンの連続クリック防止用変数。
    int ClickedFormulaOnce = 0; // +-/*が計算式に1個でも含まれるかの判定用。+-*/が1個も無いと計算できないので。
    int formulaContinuousClick = 0; // +-/*ボタンの連続クリック防止用変数。
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 縦画面固定。
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
    // 計算用メソッド
    public void calculation(TextView txt) {
        // TextViewの文字を全て取得して、配列に格納する。
        String txtAll = txt.getText().toString();
        // 区切り文字自身も配列に含める正規表現
        //正規表現((?<=[+×÷-])|(?=[+×÷-]))の意味は+-×÷の直前と直後で区切るという意味。
        String[] stringArray = txtAll.split("((?<=[+×÷-])|(?=[+×÷-]))", 0);
        // 配列に格納後、TextViewの文字を全て消去する。
        txt.setText(null);
        // 配列を全てArrayListに格納する。
        Collections.addAll(stringList, stringArray);
        //BigDecimalはdoubleと違い誤差が出ない。
        BigDecimal bigDecimalResultValue = new BigDecimal("0.0");
        // 掛け算割り算の処理
        for(int i = 1; i < stringList.size(); i += 2) {
            if(stringList.get(i).equals("×")) {
                bigDecimalResultValue = new BigDecimal(stringList.get(i-1)).multiply(new BigDecimal(stringList.get(i+1)));
                // 計算済みの数字と式をstringListから削除する。　例：「2*3+1」の「2*3」の部分を削除する。
                stringList.remove(i-1);
                stringList.remove(i-1);
                stringList.remove(i-1);
                // 計算結果をstringListに加える。　例：「2*3+1」を「6+1」にする。
                stringList.add(i-1, String.valueOf(bigDecimalResultValue));
                // stringListを3つ削除して1つ足したので、iから2を引く。
                i -= 2;
            } else if (stringList.get(i).equals("÷")) {
                // Bigdecimalの割り算は割り切れない数の時ArithmeticExceptionエラーが出る。そのためtrycathで囲む。
                // これをやっとくと割り切れる時は小数点以下に無駄に00000がつかない。
                // 割り切れない時だけ小数点第11位を四捨五入する。
                try {
                    bigDecimalResultValue = new BigDecimal(stringList.get(i-1)).divide(new BigDecimal(stringList.get(i+1)));
                } catch (ArithmeticException e) {
                    bigDecimalResultValue = new BigDecimal(stringList.get(i-1)).divide(new BigDecimal(stringList.get(i+1)), 10, RoundingMode.HALF_UP);//四捨五入。小数点第10位まで表示。
                }
                stringList.remove(i-1);
                stringList.remove(i-1);
                stringList.remove(i-1);
                stringList.add(i-1, String.valueOf(bigDecimalResultValue));
                i -= 2;
            }
        }
        // 足し算引き算の処理
        // 掛け算割り算はすでに処理済みなので、単純に前から順に足し算引き算をしていくだけ。
        while(stringList.size() > 1) {
            if(stringList.get(1).equals("+")) {
                bigDecimalResultValue = new BigDecimal(stringList.get(0)).add(new BigDecimal(stringList.get(2)));
                stringList.remove(0);
                stringList.remove(0);
                stringList.remove(0);
                stringList.add(0, String.valueOf(bigDecimalResultValue));
            } else if (stringList.get(1).equals("-")) {
                bigDecimalResultValue = new BigDecimal(stringList.get(0)).subtract(new BigDecimal(stringList.get(2)));
                stringList.remove(0);
                stringList.remove(0);
                stringList.remove(0);
                stringList.add(0, String.valueOf(bigDecimalResultValue));
            }
        }
        if(String.valueOf(bigDecimalResultValue).equals("3")) {
            // 3の時だけ画像を表示する
            // IDを元にImageViewオブジェクトを取得
            ImageView iv = this.findViewById(R.id.imageView);
            txt.setText("世界のな〇あつ");
            // drawableフォルダにあるなべあつイメージを設定
            iv.setImageResource(R.drawable.hengao2);
        } else {
            txt.setText(String.valueOf(bigDecimalResultValue));
        }
        // 結果表示後にリストをクリア。
        stringList.clear();
    }
    public void btnCurrent_onClick(View view) {
        // テキストビューを取得
        TextView txt = findViewById(R.id.textView);
        // スイッチ文でクリックされたボタンを判定。テキストに表示する。
        switch (view.getId()) {
            case R.id.button0:
                txt.append("0");
                formulaContinuousClick = 0;
                break;
            case R.id.button1:
                txt.append("1");
                formulaContinuousClick = 0;
                break;
            case R.id.button2:
                txt.append("2");
                formulaContinuousClick = 0;
                break;
            case R.id.button3:
                txt.append("3");
                formulaContinuousClick = 0;
                break;
            case R.id.button4:
                txt.append("4");
                formulaContinuousClick = 0;
                break;
            case R.id.button5:
                txt.append("5");
                formulaContinuousClick = 0;
                break;
            case R.id.button6:
                txt.append("6");
                formulaContinuousClick = 0;
                break;
            case R.id.button7:
                txt.append("7");
                formulaContinuousClick = 0;
                break;
            case R.id.button8:
                txt.append("8");
                formulaContinuousClick = 0;
                break;
            case R.id.button9:
                txt.append("9");
                formulaContinuousClick = 0;
                break;
            case R.id.buttonBS:
                formulaContinuousClick = 0;
                // Editableインスタンス取得
                Editable editable = Editable.Factory.getInstance().newEditable(txt.getText());
                // ボタンを押すごとに最後尾1文字を削除する処理
                if(editable.length() > 0){
                    //deleteの第1引数が削除したい文字のstart,第2引数が削除したい文字のend
                    editable.delete(editable.length()-1, editable.length());
                }
                // TextViewにセットする
                txt.setText(editable, TextView.BufferType.EDITABLE);
                break;
            case R.id.buttonClear:
                decimalPointCount = 0;
                ClickedFormulaOnce = 0;
                formulaContinuousClick = 0;
                stringList.clear();
                //画像のクリア用
                ImageView iv = this.findViewById(R.id.imageView);
                iv.setImageDrawable(null);
                txt.setText(null);
                break;
            case R.id.buttonPoint:
                // 小数点を連打できないようにする判定。
                if (decimalPointCount == 0) {
                    txt.append(".");
                    decimalPointCount = 1;
                }
                break;
            // ここからは+-/*をクリックしたときの処理。
            // formulaContinuousClickは+-*/=の連続クリック防止用。
            // ClickedFormulaOnceは+-*/を1度でもクリックしたかの判定用。
            case R.id.buttonPlus:
                formulaContinuousClick++;
                // +-/*を押した時の処理。連続タップの場合は無視する。
                // なおかつTextViewに何もない場合も無視する。
                if(formulaContinuousClick == 1 && !(txt.getText().toString().equals(""))) {
                    ClickedFormulaOnce = 1;
                    txt.append("+");
                }
                decimalPointCount = 0; //小数点のクリックカウントを0に戻す。
                break;
            case R.id.buttonMinus:
                formulaContinuousClick++;
                if(formulaContinuousClick == 1 && !(txt.getText().toString().equals(""))) {
                    ClickedFormulaOnce = 1;
                    txt.append("-");
                }
                decimalPointCount = 0;
                break;
            case R.id.buttonMulti:
                formulaContinuousClick++;
                ClickedFormulaOnce = 1;
                if(formulaContinuousClick == 1 && !(txt.getText().toString().equals(""))) {
                    ClickedFormulaOnce = 1;
                    txt.append("×");
                }
                decimalPointCount = 0;
                break;
            case R.id.buttonDiv:
                formulaContinuousClick++;
                ClickedFormulaOnce = 1;
                if(formulaContinuousClick == 1 && !(txt.getText().toString().equals(""))) {
                    ClickedFormulaOnce = 1;
                    txt.append("÷");
                }
                decimalPointCount = 0;
                break;
            case R.id.buttonEqual:
                if(ClickedFormulaOnce == 1) {
                    formulaContinuousClick++;
                }
                if (ClickedFormulaOnce == 1 && formulaContinuousClick == 1) {
                    formulaContinuousClick = 0;
                    ClickedFormulaOnce = 0;
                    // 計算結果が小数点付きの時に小数点をさらに付けられないようにする。　例「3.3+0.1＝3.4」→小数点クリック→「3.4.」など
                    decimalPointCount = 1;
                    if(txt.getText().toString().length() > 56) {
                        Context context = getApplicationContext();
                        Toast.makeText(context, "文字数が上限56文字を超えています。", Toast.LENGTH_LONG).show();
                    } else {
                        calculation(txt);
                    }
                }
                break;
        }
    }
}