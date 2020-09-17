package internquiz.com;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView usernames[] = {(TextView) findViewById(R.id.username), (TextView) findViewById(R.id.username1),
                (TextView) findViewById(R.id.username2) };
        TextView descriptions[] = {(TextView) findViewById(R.id.description), (TextView) findViewById(R.id.description1),
                (TextView) findViewById(R.id.description2) };
        TextView languages[] = {(TextView) findViewById(R.id.language), (TextView) findViewById(R.id.language1),
                (TextView) findViewById(R.id.language2) };
        ImageView images[] = {(ImageView) findViewById(R.id.imageView), (ImageView) findViewById(R.id.imageView1),
                (ImageView) findViewById(R.id.imageView2)};

        TableRow tablerows[] = {(TableRow) findViewById(R.id.tablerow), (TableRow) findViewById(R.id.tablerow1),
                (TableRow) findViewById(R.id.tablerow2)} ;

        URL url = null;
        try {
            url = new URL("https://api.github.com/gists/public");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Networking networking = new Networking();
        networking.execute(url);
        try {


            String jsonString = networking.get(10000, TimeUnit.MILLISECONDS);
            JsonArray jsonArray = new Gson().fromJson(jsonString, JsonArray.class);

            for(int i = 0; i<usernames.length; i++) {
                JsonObject jsonObject = new Gson().fromJson(jsonArray.get(i), JsonObject.class);
                JsonObject owner = new Gson().fromJson(jsonObject.get("owner").toString(), JsonObject.class);

                JsonObject files = new Gson().fromJson(jsonObject.get("files").toString(), JsonObject.class);
                final JsonObject file = new Gson().fromJson(files.get((files.keySet().toArray())[0].toString()).toString(),JsonObject.class);



                usernames[i].setText(owner.get("login").getAsString());
                descriptions[i].setText(jsonObject.get("description").getAsString());
                languages [i].setText(file.get("language").getAsString());
                new DownloadImageTask(images[i])
                        .execute(owner.get("avatar_url").getAsString());
                tablerows[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, Usercode.class);
                        intent.putExtra("code", file.get("raw_url").getAsString());
                        startActivity(intent);
                    }
                });

            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }



    }


}
