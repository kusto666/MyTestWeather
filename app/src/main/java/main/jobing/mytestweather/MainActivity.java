package main.jobing.mytestweather;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    /**
     * Для простоты и удобства используем уже сформированную строку
     * с запросом погоды в Лондоне на данный момент
     * <p>
     * другие примеры запросов можете глянуть здесь
     * {@see <a href="http://openweathermap.org/current">openweathermap</a>}
     * также Вам понадобится свой API ключ appid
     */
    URL url = null;
    public static final String WEATHER_URL =
            "http://api.openweathermap.org/data/2.5/weather?q=London,uk&units=metric&appid=241de9349721df959d8800c12ca4f1f3&lang=ru";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);



        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    // создаем URL из строки
                    url = JsonUtils.createUrl(WEATHER_URL);
                    System.out.println("url: ==  " + url);
                    // загружаем Json в виде Java строки
                    String resultJson = null;
                    try {
                        resultJson = JsonUtils.parseUrl(url);
                    }
                    catch (Exception ex)
                    {
                        System.out.println("ex.getMessage();");
                        ex.printStackTrace();
                    }
                    System.out.println("\nПолученный JSON:\n" + resultJson);
                    JsonUtils.parseCurrentWeatherJson(resultJson);

                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();






        // формируем новый JSON объект из нужных нам погодных данных
     //   String json = JsonUtils.buildWeatherJson();
     //   System.out.println("\nСозданный нами JSON:\n" + json);

        // преобразуем массив с json содержимым в строку со значениями через запятую
        // то есть в строку, удобную для сохранения в CSV файл
       // String csvString = JsonUtils.convertJsonToCsv(resultJson);
       // System.out.println("\nСтрока для CSV файла:\n" + csvString);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}