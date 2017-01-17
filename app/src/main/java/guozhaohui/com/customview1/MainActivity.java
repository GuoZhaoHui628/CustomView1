package guozhaohui.com.customview1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private CustomTitleView customTitleView;
    private EditText et1;
    private Button bt1;
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        customTitleView = (CustomTitleView) this.findViewById(R.id.custom1);
        et1 = (EditText) this.findViewById(R.id.et1);
        bt1 = (Button) this.findViewById(R.id.bt1);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content = et1.getText().toString();
                Toast.makeText(MainActivity.this,"是否正确-->"+customTitleView.isJudgeAgree(content),Toast.LENGTH_SHORT).show();
            }
        });


    }
}
