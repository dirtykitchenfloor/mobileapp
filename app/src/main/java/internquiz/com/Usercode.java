package internquiz.com;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Usercode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usercode);

        TextView codes = (TextView) findViewById(R.id.codes);
        codes.setMovementMethod(new ScrollingMovementMethod());

        Intent intent = getIntent();
        //codes.setText("hello");
        try {
            //String out = new Scanner(new URL(intent.getStringExtra("code")).openStream(), "UTF-8").useDelimiter("\\A").next();
            Networking networking = new Networking();
            networking.execute(new URL(intent.getStringExtra("code")));
            String out = networking.get(10000, TimeUnit.MILLISECONDS);
            codes.setText(out);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

    }
}
